
import java.util.Arrays;

/* 20090616 This code suffered a major cleanup by MAMB.
 * Define_network.java
 *
 * Created on 2003??4??30??, ????3:14
 /*
 * Define_network.java
 *
 * Created on 2003??4??30??, ????3:14
 */
//import java.util.Arrays; //FADE added this
//Modified by Jinchao Yuan on 03/17/2005
//package mmpn1;
/**
 *
 * @author Administrator
 */
public class DefineNetwork implements java.io.Serializable {

    public int zoneNumber;
    public int flowpathNumber;
    public int sourceNumber;
    public int TMassNumber;
    public Zone[] zones = new Zone[zoneNumber];
    public Flowpath[][] flowpaths = new Flowpath[zoneNumber][zoneNumber];
    public Source[] sources = new Source[sourceNumber];
    public TMass[] TMasses = new TMass[TMassNumber];

    /**
     * Creates a new instance of Define_network
     */
    public DefineNetwork(int zoneNumber) {
        this.zoneNumber = zoneNumber;
        this.zones = new Zone[zoneNumber];
        this.flowpaths = new Flowpath[zoneNumber][zoneNumber];
    }

    public DefineNetwork(int zoneNumber, int TMassNumber) {
        this.zoneNumber = zoneNumber;
        this.TMassNumber = TMassNumber;
        this.zones = new Zone[zoneNumber];
        this.flowpaths = new Flowpath[zoneNumber][zoneNumber];
        this.TMasses = new TMass[TMassNumber];
    }

    public double setInitialFlow(double area, Zone I, double delta_p, boolean connection, double cd) {
        if (connection == true) {
            I.calAir_density();
            System.out.println("DP "+delta_p);            
            return (Math.signum(delta_p) * I.getAir_density() * 0.000000001 * area * cd * Math.sqrt(2 * Math.abs(delta_p) / I.getAir_density()));
//            return (Math.signum(delta_p) * 0.000000001 * area * cd * Math.sqrt(2 * Math.abs(delta_p) / I.getAir_density())); //MAMB thinks that this function is completely useless. Plus, it generates a NaN error. //FADE: agree
        } else {
            return 0.0;
        }
    }

    public void calAir_temp_trapezoidal(double[][] flowrate, double Delta_time, double[] prevAir_temp, double[] prevTMass_temp, boolean[][] TMassConnection, boolean[][] TMassIntConnection, double relaxation, double heatsourceMultiplier, boolean radiantOn, double latitude, double declination, double solarHeatLoad_normal_hourX, double solarHeatLoad_diffuse_hourX, int timeofday, int TMassCheck, boolean oneZone) {
        double[][] A = new double[zoneNumber][zoneNumber];
        double[] b = new double[zoneNumber];

        int solvermethod = 0;
//        double R0 = 1 / 12.0;
//        double R1 = 1 / 12.0;
//        double R2 = 1 / 6.0;
//        double R3 = 1 / 6.0;

        //FADE 11/27/11:
        ///*******************************************************************************************************************************
        //The energy constitutive relation for an ideal gas is u=m*cv*T (it is NOT m*cp*T). Therefore, energy conservation takes the form:
        //M/dt*Tnew + 1/2*gamma*f(out)*Tnew - 1/2*gamma*f(in)*Tin_new + (every)1/2*1/(1/(h*A)+L/(2*k*A))/cv*Tnew = M/dt*Told + 1/2*gamma*f(in)*Tin_old - 1/2*gamma*f(out)*Told + S/cv + (every)1/2*1/(1/(h*A)+L/(2*k*A))/cv*Tmass
        //where gamma = cp/cv = 1.4 (for air), and f is mass flow rate (kg/s)
        //This equation in matrix form is still strictly diagonally dominant
        //All the equations were modified accordingly
        //*******************************************************************************************************************************          
        for (int i = 0; i < zoneNumber; i++) {
            if (zones[i].getIsIntZone() == true) { //if it's not ambient zone
                zones[i].setSolarHeatLoad(zones[i].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday));
                zones[i].setTotalConvectiveHeatLoad(zones[i].getSolarHeatLoad(), zones[i].source.getOccupancyHeat_source(), TMassCheck);//20080829 MAMB
                zones[i].setTotalRadiativeHeatLoad(zones[i].getSolarHeatLoad(), zones[i].source.getOccupancyHeat_source(), TMassCheck);//20080829 MAMB
                zones[i].calMass(); //air mass
            }
//            if (!TMasses[i].getType().equals("floor") && !TMasses[i].getType().equals("ceiling")){
//                TMasses[i].setSolarHeatLoad(TMasses[i].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_normal_hourX, timeofday));
//            }
        }
        for (int i = 0; i < zoneNumber; i++) {
            if (zones[i].getIsIntZone() == true) { //if it's not ambient zone
//                System.out.println("Zone :" + i+ " Solar: "+zones[i].getSolarHeatLoad());
//                System.out.println("Zone :" + i+ " Convective: "+zones[i].getTotalConvectiveHeatLoad()+ " Rad: "+zones[i].getTotalRadiativeHeatLoad());
                b[i] = 0.0;
                for (int j = 0; j < zoneNumber; j++) {
                    if (j != i) {
                        A[i][j] = 0.0;
                        if (flowrate[j][i] >= 0.0) {  //incoming flow
//                            System.out.println("Incoming flow from zone " + j+ " of : "+flowrate[j][i]+" and temp: "+ prevAir_temp[j]);
                            if (j == 0) { //ambientZone
                                b[i] += flowrate[j][i] * zones[j].getGamma() * prevAir_temp[0]; //FADE: \dot{m}*cp/cv*T
                                if (oneZone) {
                                    A[i][i] += relaxation * Math.abs(flowrate[i][j]) * zones[i].getGamma(); //FADE
                                    b[i] += (1.0 - relaxation) * (-Math.abs(flowrate[j][i])) * zones[i].getGamma() * prevAir_temp[i];
                                }
                            } else { //intZone
                                A[i][j] += relaxation * (-flowrate[j][i]) * zones[j].getGamma(); //FADE
                                b[i] += (1.0 - relaxation) * flowrate[j][i] * zones[j].getGamma() * prevAir_temp[j]; //FADE
                            }
                        } else { // outgoing flow
//                            System.out.println("Outgoing flow to zone " + j+ " of : "+flowrate[j][i]+" and temp: "+ prevAir_temp[j]);
                            A[i][i] += relaxation * (-flowrate[j][i]) * zones[i].getGamma(); //FADE
                            b[i] += (1.0 - relaxation) * flowrate[j][i] * zones[i].getGamma() * prevAir_temp[i]; //FADE
                            if (oneZone) {
                                b[i] += Math.abs(flowrate[j][i]) * zones[j].getGamma() * prevAir_temp[0]; //FADE: \dot{m}*cp/cv*T];
                            }
                        }
                    }
                }

///***********************RADIANT IS NEVER ON****************************************************************
//                if (radiantOn == true && zones[i].useRadiantModel() == true) {
//                    //apply the radiance model
//                    R0 = 1.0 / 2.0 / TMasses[zones[i].ceilingIndex].getkAoX() * TMasses[zones[i].ceilingIndex].getArea();
//                    //if (zones[i].ceilingIndex1 >= 0)
//                    //R0 = 1/(1/R0 + 2.0*TMasses[zones[i].ceilingIndex1].getkAoX()/TMasses[zones[i].ceilingIndex1].getArea());
//
//                    R1 = TMasses[zones[i].ceilingIndex].getArea() / TMasses[zones[i].ceilingIndex].getHA();
//                    R2 = 1.0 / 6.0;
//                    R3 = 1.0 / 10.0;
//
//                    double AInv[][] = new double[2][2];
//                    double beforeTi = 1.0 / zones[i].getCp() * TMasses[zones[i].ceilingIndex].getArea() * calculateEffectiveH_Aside(R0, R1, R2, R3, AInv);
//                    A[i][i] -= relaxation * beforeTi;
//                    b[i] += (1.0 - relaxation) * prevAir_temp[i] * beforeTi;
//
//                    double Solar = heatsourceMultiplier * 0.8 * zones[i].source.getOccupancyHeat_source() * 0.8;
//                    //MAMB 200709
//                    //double Solar = heatsourceMultiplier* 0.8* zones[i].calTotalHeatLoad(zones[i], latitude, declination, solarHeatLoad_normal_hourX, timeofday) *0.8;
//                    //MAMB 200709 end
//
//                    double infred = heatsourceMultiplier * 0.8 * zones[i].source.getOccupancyHeat_source() * 0.2;
//                    //MAMB 200709
//                    //double infred = heatsourceMultiplier* 0.8* zones[i].calTotalHeatLoad(zones[i], latitude, declination, solarHeatLoad_normal_hourX, timeofday) *0.2;
//                    //MAMB 200709 end
//
//                    b[i] += 1.0 / zones[i].getCp() * TMasses[zones[i].ceilingIndex].getArea() * calculateEffectiveH_Bside(TMasses[zones[i].floorIndex].getArea(), R0, R1, R3, AInv, prevTMass_temp[zones[i].ceilingIndex], prevTMass_temp[zones[i].floorIndex], Solar, infred, zones[i]);
//
//                    //source term
//                    b[i] += zones[i].getMass() / Delta_time * prevAir_temp[i] + heatsourceMultiplier * 0.2 * zones[i].source.getOccupancyHeat_source() / zones[i].getCp();
//                    } else {
///***********************RADIANT IS NEVER ON**************************************************************** end
//Coupling the thermal mass temperature solving
                for (int j = 0; j < TMassNumber; j++) {
                    if (TMassConnection[j][i] == true) {
                        A[i][i] += relaxation * 1.0 / (1.0 / TMasses[j].getHA(prevAir_temp[i]) + 1.0 / 2.0 / TMasses[j].getkAoX()) / zones[i].getCv(); //FADE
                        b[i] += (prevTMass_temp[j] - (1.0 - relaxation) * prevAir_temp[i]) * 1.0 / (1.0 / TMasses[j].getHA(prevAir_temp[i]) + 1.0 / 2.0 / TMasses[j].getkAoX()) / zones[i].getCv();
//                      A[i][i] += relaxation * 1.0 / (1.0 / TMasses[j].getHA() + 1.0 / 2.0 / TMasses[j].getkAoX()) / zones[i].getCp();
//                      b[i] += (prevTMass_temp[j] - (1.0 - relaxation) * prevAir_temp[i]) * 1.0 / (1.0 / TMasses[j].getHA() + 1.0 / 2.0 / TMasses[j].getkAoX()) / zones[i].getCp();
                        //b[i] += (prevTMass_temp[j]- (1.0-relaxation)* prevAir_temp[i])* 1.0/(1.0/TMasses[j].getHA()+1.0/2.0/TMasses[j].getkAoX()) / zones[i].getCp()+zones[i].getTotalRadiativeHeatLoad()*TMasses[j].getAbsorbsSolar(); //MAMB 20080828 <<--ADDING THIS LINE RISED TEMPS UP TO 100C!!!
                    }
                }

                b[i] += zones[i].getMass() / Delta_time * prevAir_temp[i] + heatsourceMultiplier * zones[i].getTotalConvectiveHeatLoad() / zones[i].getCv(); //FADE
//              b[i] += zones[i].getMass() / Delta_time * prevAir_temp[i] + heatsourceMultiplier * zones[i].getTotalConvectiveHeatLoad() / zones[i].getCp(); //20080611 MAMB edited    

                //} closing the if radiantOn
                A[i][i] += zones[i].getMass() / Delta_time;

            } else { //if ambient zone
                b[i] = prevAir_temp[0];
                for (int j = 0; j < zoneNumber; j++) {
                    A[i][j] = 0.0;
                }
                A[i][i] = 1.0;
            }
        }

//Solve this system Ax = b
        double d = 1.0;
        int[] indx = new int[zoneNumber];
        double[] solution = new double[zoneNumber];

        if (solvermethod == 0) {
            LinearSolver.LUSolver(A, zoneNumber - 1, indx, b, d);
        } else {
            LinearSolver.Jacob(A, zoneNumber - 1, b, new double[zoneNumber], 10000, solution);
            //LUD.Jacob(A, zoneNumber-1, b, new double[zoneNumber], 10000, solution);
        }
        for (int i = 1; i < zoneNumber; i++) {
            if (solvermethod == 1) {
                b[i] = solution[i];
            }
//            System.out.println("Zone " + i+" prev temp: "+zones[i].getAir_temp());
            zones[i].setAir_temp(b[i]);
//            System.out.println("Zone " + i+" new temp: "+zones[i].getAir_temp());
        }
//        System.out.println("Temperatures: "+Arrays.toString(b));

        //FADE: Tmass solution
        if (TMassNumber != 0) {
            double[] a = new double[TMassNumber];
            b = new double[TMassNumber];
            double[] c = new double[TMassNumber];
            double[] e = new double[TMassNumber];

            for (int i = 0; i < TMassNumber; i++) { //Row i
                for (int j = 0; j < zoneNumber; j++) {
                    if (TMassConnection[i][j] == true) { //FADENEW: Tmass i is external layer and is in contact with zone j
                        b[i] += 0.5 * 1 / (1 / TMasses[i].getHA(zones[j].getAir_temp()) + 0.5 / TMasses[i].getkAoX());
                        if (zones[j].getIsIntZone() == true) {
                            e[i] += prevTMass_temp[i] * (-0.5 * 1 / (1 / TMasses[i].getHA(zones[j].getAir_temp()) + 0.5 / TMasses[i].getkAoX())) + zones[j].getAir_temp() * 1 / (1 / TMasses[i].getHA(zones[j].getAir_temp()) + 0.5 / TMasses[i].getkAoX()) + zones[j].getTotalRadiativeHeatLoad() * TMasses[i].getAbsorbsSolar();
                        } else {
                            e[i] += prevTMass_temp[i] * (-0.5 * 1 / (1 / TMasses[i].getHA(zones[j].getAir_temp()) + 0.5 / TMasses[i].getkAoX())) + zones[j].getAir_temp() * 1 / (1 / TMasses[i].getHA(zones[j].getAir_temp()) + 0.5 / TMasses[i].getkAoX()) + TMasses[j].getSolarHeatLoad();
                        }
                    }
                }
                for (int j = 0; j < TMassNumber; j++) {
                    if (j == i) {
                        b[i] += TMasses[j].getMass() * TMasses[j].getCp() / Delta_time;
//                    System.out.println("layer "+i+" mass: "+TMasses[j].getMass()+" cp: "+TMasses[j].getCp()+" Dt: "+Delta_time);
                        e[j] += prevTMass_temp[j] * TMasses[j].getMass() * TMasses[j].getCp() / Delta_time;
                    }
                    if (TMassIntConnection[i][j] == true) {
                        b[i] += 0.5 * TMasses[j].getkAoX();
//                    System.out.println("layer "+i+" and layer: "+j+" 0.5/R: "+0.5 * TMasses[j].getkAoX());
                        e[i] += prevTMass_temp[i] * (-0.5 * TMasses[j].getkAoX()) + prevTMass_temp[j] * 0.5 * TMasses[j].getkAoX();
                        if (j == i + 1) {
                            c[i] += -0.5 * TMasses[j].getkAoX();
                        } else {
                            a[i] += -0.5 * TMasses[j].getkAoX();
                        }
                    }
                }
            }
            //<editor-fold defaultstate="collapsed" desc="Previous code">
            //Jinchao Yuan 11/13/2005 reinvoked the wall explicit because the wall explicit or implict does not matter so much as the air methods
            //However, they used the temperature that has been just calculated. which is actually a new implict method
//        double tempHeat1 = 0.0;
//        double tempHeat2 = 0.0;
//        double tempHeat3 = 0.0;
//        double tempHeat4 = 1;
//        boolean isExternalLayer = false;
//
//        for (int i = 0; i < TMassNumber; i++) {
//            isExternalLayer = false;
//            for (int j = 1; j < zoneNumber; j++) { //Set the temperature of the external layers
//                if (TMassConnection[i][j] == true) { //FADENEW: Tmass i is external layer and is in contact with zone j
//                    isExternalLayer = true;
////                    System.out.println("Layer " + i + " is connected to zone " + j);
//                    //<editor-fold defaultstate="collapsed" desc="Previous code">
//                    //**************NOT USING RADIANT******************
//                    //                    if (zones[j].useRadiantModel() == true && radiantOn == true){
//                    //                        if (zones[j].ceilingIndex == i){
//                    //                            tempHeat += (zones[j].ceilingT - prevTMass_temp[i]) / (1.0 / 2.0 / TMasses[i].getkAoX());
//                    //                        } else{ // (zones[j].floorIndex == i)
//                    //                            tempHeat += (zones[j].floorT - prevTMass_temp[i]) / (1.0 / 2.0 / TMasses[i].getkAoX());
//                    //                        }
//                    //                    }else{
//                    //**************NOT USING RADIANT****************** end
//                    //                    tempHeat = tempHeat + (zones[j].getAir_temp() - prevTMass_temp[i]) / (1.0 / TMasses[i].getHA() + 1.0 / 2.0 / TMasses[i].getkAoX()) + zones[j].getTotalRadiativeHeatLoad() * TMasses[i].getAbsorbsSolar(); //MAMB 20080828 added radiative heat
//                    //</editor-fold>
//                    tempHeat1 = 1 / TMasses[i].getkAoX() * zones[j].getAir_temp() + 1 / TMasses[i].getkAoX() * 1 / TMasses[i].getHA(zones[j].getAir_temp()) * zones[j].getTotalRadiativeHeatLoad() * TMasses[i].getAbsorbsSolar(); //MAMB 20080828 added radiative heat //FADENEW: R_A*T_B+R_B*R_A*Q
//                    tempHeat2 = 1 / TMasses[i].getHA(zones[j].getAir_temp()) + 1 / TMasses[i].getkAoX(); //FADENEW: R_B+R_A
////                    tempHeat1 = tempHeat1 + TMasses[i].getHA(zones[j].getAir_temp()) * zones[j].getAir_temp() + zones[j].getTotalRadiativeHeatLoad() * TMasses[i].getAbsorbsSolar(); //MAMB 20080828 added radiative heat //FADENEW
////                    tempHeat2 = tempHeat2 + TMasses[i].getHA(zones[j].getAir_temp()); //FADENEW                    
//                    for (int k = 0; k < TMassNumber; k++) {
//                        if (TMassIntConnection[i][k] == true) {
////                            System.out.println("Layer " + i + " is connected to layer " + k);
//                            tempHeat1 += 1 / TMasses[i].getHA(zones[j].getAir_temp()) * prevTMass_temp[k]; //FADENEW: R_A*T_B+R_B*T_A+R_B*R_A*Q
//                            break; //FADENEW: external layer is connected to only one internal layer, so this should make things a little bit faster
//                        }
//                    }
//                    tempHeat3 = Math.exp(-tempHeat2 * Delta_time / (1 / TMasses[i].getHA(zones[j].getAir_temp()) * 1 / TMasses[i].getkAoX() * TMasses[i].getMass() * TMasses[i].getCp()));
//                    TMasses[i].setTemperature((1 - tempHeat3) * tempHeat1 / tempHeat2 + prevTMass_temp[i] * tempHeat3);
//                    System.out.println("Layer "+i+" prev temp "+prevTMass_temp[i]+" new temp "+TMasses[i].getTemperature());
//                    break; //FADENEW: each layer is connected to only one zone, so this should make things a little bit faster
//                }
//            }
//            if (!isExternalLayer) {
//                tempHeat1 = 0.0;
//                tempHeat2 = 0.0;
//                tempHeat4 = 1;
//                for (int k = 0; k < TMassNumber; k++) {
//                    if (TMassIntConnection[i][k] == true) {
////                        System.out.println("Layer "+i+" is internal and connected to "+k+" prevTMass "+prevTMass_temp[k]);
//                        tempHeat1 += 1 / TMasses[k].getkAoX() * prevTMass_temp[k]; //FADENEW: R_A*T_B + R_B*T_A
//                        tempHeat2 += 1 / TMasses[k].getkAoX(); //FADENEW: R_B+R_A
//                        tempHeat4 *= 1 / TMasses[k].getkAoX(); //FADENEW: R_B*R_A
//                    }
//                }
//                tempHeat3 = Math.exp(-tempHeat2 * Delta_time / (tempHeat4 * TMasses[i].getMass() * TMasses[i].getCp()));
//                TMasses[i].setTemperature((1 - tempHeat3) * tempHeat1 / tempHeat2 + prevTMass_temp[i] * tempHeat3);
//                System.out.println("Layer "+i+" prev temp "+prevTMass_temp[i]+" new temp "+TMasses[i].getTemperature());
//            }
//        }
            //            //FADE: Thermal mass - quick fix for thickness dependence problem
            //            prevTMass_temp[i]=(prevTMass_temp[i] + Delta_time * (tempHeat / TMasses[i].getCp() / TMasses[i].getMass()));
            //            tempHeat = 0; //FADE: For the internal connections, tempHeat is no longer an energy interaction variable, but the sum of the temperature(s) of the layer(s) adjacent to the layer of interest
            //            int countintconn = 0;
            //            for (int j = 0; j < TMassNumber; j++) {
            //                if (TMassIntConnection[i][j] == true){
            //                    //FADE: Old thermal mass with thickness dependence problem
            //                    //tempHeat = tempHeat + (prevTMass_temp[j] - prevTMass_temp[i]) / (1.0 / 2.0 / TMasses[i].getkAoX() + 1.0 / 2.0 / TMasses[j].getkAoX());
            //                    //FADE: New thermal mass - quick fix
            //                    tempHeat = tempHeat + prevTMass_temp[j];
            //                    countintconn++;
            //                }
            //            }
            //            //FADE: Old thermal mass with thickness dependence problem
            //            //TMasses[i].setTemperature(prevTMass_temp[i] + Delta_time * (tempHeat / TMasses[i].getCp() / TMasses[i].getMass()));
            //            //FADE: New thermal mass - quick fix
            //            if (countintconn > 1){
            //                TMasses[i].setTemperature(0.5*(tempHeat - (tempHeat - 2*prevTMass_temp[i])*Math.exp((-2*Delta_time)/(TMasses[i].getMass() * TMasses[i].getCp()*(1.0 / 2.0 / TMasses[i].getkAoX()))))); //FADE: This is a lumped capacity solution for the layer
            //            } else {
            //                TMasses[i].setTemperature(tempHeat - (tempHeat - prevTMass_temp[i])*Math.exp((-Delta_time)/(TMasses[i].getMass() * TMasses[i].getCp()*(1.0 / 2.0 / TMasses[i].getkAoX())))); //FADE: This is a lumped capacity solution for the layer
            //            }
            //        }
            //</editor-fold>
            //FADE: Tridiagonal algorithm
//        LinearSolver.TDM(TMassNumber-1, b, e, a, c);
            double[] cp = new double[TMassNumber];
            double[] ep = new double[TMassNumber];
            for (int i = 0; i < TMassNumber; i++) {
                if (i == 0) {
                    cp[i] = c[i] / b[i];
                    ep[i] = e[i] / b[i];
                } else {
                    cp[i] = c[i] / (b[i] - a[i] * cp[i - 1]);
                    ep[i] = (e[i] - a[i] * ep[i - 1]) / (b[i] - a[i] * cp[i - 1]);
                }
            }
            TMasses[TMassNumber - 1].setTemperature(ep[TMassNumber - 1]);
            for (int i = TMassNumber - 2; i >= 0; i--) {
                TMasses[i].setTemperature(ep[i] - cp[i] * TMasses[i + 1].getTemperature());
            }
//        for (int i = 0; i < TMassNumber; i++) {
//            System.out.println("TMass " + i + " old temp: " + prevTMass_temp[i] + " new temp: " + TMasses[i].getTemperature());
//        }
        }
    }

    public void calNew_P(Zone[] zones, double[][] flowrate, Flowpath[][] flowPath, double[][] delta_p, double[] pressures, boolean[][] connection) {
        double[][] J = new double[zoneNumber][zoneNumber];
        double[] B = new double[zoneNumber];
        double[] C = new double[zoneNumber];
        for (int i = 0; i < zoneNumber; i++) {
            C[i] = 0.0;
            //Calculate J[][].
        }
        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                if (j == i) {
                    double temp = 0.0;
                    for (int ii = 0; ii < zoneNumber; ii++) {
                        if (connection[ii][j] == true && flowPath[ii][j] != null) {
                            if (Math.abs(delta_p[ii][j]) >= 1.0E-15) {
                                temp = temp - flowPath[ii][j].getPowerN() * flowrate[ii][j] / delta_p[ii][j];
                            }
                        }
                    }
                    J[i][j] = temp;
                } else {
                    if (connection[j][i] == true && flowPath[j][i] != null) {
                        if (Math.abs(delta_p[j][i]) >= 1.0E-15) {
                            J[i][j] = flowPath[j][i].getPowerN() * flowrate[j][i] / delta_p[j][i];
                        }
                    }
                }

//                                                                     System.out.println("J["+i+"]["+j+"]="+J[i][j]);
            }
        }
        for (int i = 0; i < zoneNumber; i++) {
            double temp = 0.0;
            for (int j = 0; j < zoneNumber; j++) {
                temp = temp + Math.abs(J[i][j]);
            }
            if (Math.abs(2.0 * Math.abs(J[i][i]) - temp) >= 1.0E-5) {
                System.out.println("Alert! J problem! " + (2.0 * Math.abs(J[i][i]) - temp) + " The " + i + " J[" + i + "][" + i + "]: " + J[i][i]);
                System.exit(0);
            }
        }

        //Calculate B[], C[] and new P*;
        for (int i = 0; i < zoneNumber; i++) {
            double temp = 0.0;
            for (int j = 0; j < zoneNumber; j++) {
                temp = temp + flowrate[j][i];
            }
            B[i] = temp;
        }

        //Here, we use GS method to get the new C[];
        double SOR = 0.75;
        double[] C_old = new double[zoneNumber];
        double C_error = 1.0;
        int cont = 0;

        while (C_error >= 1.0E-3 && cont <= 2000000) {

            cont++;
            if (cont >= 2000000) {
                System.out.println("Alert, the C_error diverged!");
                for (int i = 0; i < zoneNumber; i++) {
                    System.out.println("B[" + i + "]= " + B[i] + "    C[" + i + "]= " + C[i]);
                    for (int j = 0; j < zoneNumber; j++) {
                        System.out.println("J[" + i + "][" + j + "]=" + J[i][j]);
                    }
                }
                System.exit(0);
            }
            C_error = 0.0;
            for (int i = 0; i < zoneNumber; i++) {
                C_old[i] = C[i];
                if (zones[i].getIsIntZone() != true) {
                    C[i] = 0.0;
                }
            }

            for (int i = 0; i < zoneNumber; i++) {
                double temp1 = 0.0;
                double temp2 = 0.0;
                for (int j = 0; j < i; j++) {
                    temp1 = temp1 + J[i][j] * C[j];
                }
                for (int j = i + 1; j < zoneNumber; j++) {
                    temp2 = temp2 + J[i][j] * C[j];
                }
                if (zones[i].getIsIntZone() == true && Math.abs(J[i][i]) >= 1.0E-10) {
                    C[i] = (1.0 - SOR) * C[i] + SOR * (B[i] - temp1 - temp2) / J[i][i];
                }
                if (C_error <= Math.abs(C[i] - C_old[i])) {
                    C_error = Math.abs(C[i] - C_old[i]);
                }
            }
        }

        //Calculate the new {P}*
        for (int i = 0; i < zoneNumber; i++) {
            pressures[i] = pressures[i] - SOR * C[i];
            zones[i].setPressure(pressures[i]);
        }
    }

    public double calculateEffectiveH_Aside(double R0, double R1, double R2, double R3, double[][] AInv) {
        //   [ a b ]              [ d -b ]
        //A =|     |  => Inv(A) = |      | /det(A)
        //   [ c d ]              [ -c a ] 
        double A[][] = new double[2][2];
        double h_Aside = 10;

        A[0][0] = 1.0 / R0 + 1.0 / R1 + 1.0 / R2;
        A[0][1] = -1.0 / R2;
        A[1][0] = -1.0 / R2;
        A[1][1] = 1.0 / (R0 + R3) + 1.0 / R1 + 1.0 / R2;

        double detA = A[0][0] * A[1][1] - A[0][1] * A[1][0];
        AInv[0][0] = A[1][1] / detA;
        AInv[1][1] = A[0][0] / detA;
        AInv[0][1] = -A[0][1] / detA;
        AInv[1][0] = -A[1][0] / detA;

        h_Aside = 1.0 / R1 * (-2 + 1.0 / R1 * (AInv[0][0] + AInv[0][1] + AInv[1][0] + AInv[1][1]));

        return h_Aside;
    }

    public double calculateEffectiveH_Bside(double Area, double R0, double R1, double R3, double[][] AInv, double TCeiling1st, double TFloor1st, double Solar, double infred, Zone space) {
        double b[] = new double[2];
        b[0] = TCeiling1st / R0 + 0.6 * infred / Area;
        b[1] = TFloor1st / (R0 + R3) + 0.4 * infred / Area + Solar / Area;

        double h_Bside = 1.0 / R1 * (b[0] * (AInv[0][0] + AInv[1][0]) + b[1] * (AInv[0][1] + AInv[1][1]));
        space.ceilingT = (space.getAir_temp() / R1 + b[0]) * AInv[0][0] + (space.getAir_temp() / R1 + b[1]) * AInv[0][1];
        space.floorT = (space.getAir_temp() / R1 + b[0]) * AInv[1][0] + (space.getAir_temp() / R1 + b[1]) * AInv[1][1];

        //System.out.println("Zone Ceiling Temperature" + space.ceilingT);
        //System.out.println("Zone Floor Temperature" + space.floorT);
        return h_Bside;
    }
}
