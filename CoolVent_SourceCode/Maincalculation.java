
import java.util.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Arrays; //FADE added this
import Jama.*; //CK edits (2015)

/**
 * Maincalculation.java (Description)
 *
 * Created 2004 Major cleanup by MAMB 6/16/2009 Major cleanup by FADE 3/12
 *
 * @author
 * @version 3/27/2012
 */
public class Maincalculation implements java.io.Serializable {

    //<editor-fold defaultstate="collapsed" desc="Class variables">
    //Control    
    private static final boolean PRINT_TRAINING_DAYS = false; //FADE: if true, includes training days in output
    //Constants
    private static final String FILE_NAME = "TemperatureOutput.txt";
    private static final String A_FILE_NAME = "AirflowOutput.txt";
    private static final String STRAT_FILE_NAME = "StratificationOutput.txt";
    private static final String FAN_FILE_NAME = "FanOutput.txt";
    private static final String O_FILE_NAME = "Outdoor.txt";
    private static final String C_FILE_NAME = "OutdoorConc.txt"; //CK EDIT: outdoor concentration data
    private static final int NumberOfPollutants = 1; //CK EDIT: Building capability to analyze multiple pollutants 
    private static final String FILT_FILE_NAME = "Filters.txt"; //CK EDIT: Takes the filter data (tuples of efficiency, rated flow and zone number and creates Filter objects
    private static final String WFILT_FILE_NAME = "WindowFilters.txt"; //CK EDIT: Takes filter data (essentially a matrix of filters with some efficiency, corresponding to the flow matrix. 
    //CK EDIT: i.e. the [i][j] element indicates filter efficiency for airflow j->i (from j to i)
    private static final String C_OUT_FILE_NAME = "Zone_Concentrations.txt";
    private static final String PRESSURE_FILE_NAME = "PressureOutput.txt";//sdr_hulic - added .txt output for pressure
    private static final String RECOMMEND_FILE_NAME = "RecommendOutput.txt";//sdr_hulic - added .txt output for recommendation
    private static final int TRAINING_DAYS = 0;
    private static final int ACC = 3; //Acceleration factor for training days
    private static final int MAXITER = 1000;
    private static final int STEADY_STATE_CONTROLLER = 33580;//8000; 20080406 Changed it to reach real steady state (at least without thermal mass!) //FADE: This number seems to come from Mount Sinai (Brisson, 2011)
    private static final double RELAXATION = 0.5;
    private static final double LAMBDATOL = 0.5;
    private static double HEAT_SOURCE_MULTIPLIER = 1.0; //FADE: Seems useless
    private static final boolean RADIANT_ON = false; //FADE: Seems useless
    private static final double G = 9.81; //Grativity acceleration
    private static final double HOURS_IN_DAY = 24;
    private static final double SECS_IN_MIN = 60;
    private static final double SECS_IN_HR = 3600;
    private static final double SECS_IN_DAY = HOURS_IN_DAY * SECS_IN_HR;
    private static final int O_DATA = 13;
    private static final int HEATING_GAIN = 20; //W/(m^2 K)
    private static final double D = 1.0;
    private static final DecimalFormat D_F = new DecimalFormat("0.00"); // makes it easier to read the stratification results file. i don't need more than 4 decimals anyway. MAMB
    //private static final double K = 1.01; //FADE: For a difference of 1% between opening and fan at low speed
    private static final int SENSIBLE_HEAT_PERSON = 70; //W FADE: Sensible heat load per person - light office work
    private static final int LATENT_HEAT_PERSON = 45; //W FADE: Sensible heat load per person - light office work
    private static final double H_FG = 2.3e6; //J/Kg FADE: Latent heat of vaporaization - water
    private static final double PEOPLE_SENSIBLE_AVG_FRAC = 0.185; //FADE: Average fraction of total sensible heat that comes from people (office and residence)
    private static final double MAX_COST = 600; //FADE: C-s
    //Variables
    private double Delta_time; //s.
    private double ori_Delta_time;
    private double training_Delta_time;
    private int trainingsteps;
    private int Controller; //FADE: time steps
    private int bldgtype;
    private int zoneNumber;
    private int flowpathNumber;
    private int TMassNumber; //Jinchao Yuan 03/17/2005
    private DefineNetwork nvCase;
    private double flowrate_error;
    private double temp_error;
    private int controller1;
    private double[][] flowrate;
    private int numericalMethod; //Jinchao yuan added on 11/06/2005
    private int istransient;
    private double[] pressures;
    private double[][] delta_p;
    private double[] tempFx;
    private double outdoorTemperature;
    private int readcounter;
    //private double openingfrac;
    private double nightfrac;
    private double tempdata[] = new double[O_DATA];
    private double checkdx;
    private double checkFx;
    private int newtoni;
    private double lambda;
    private double T_user;
//    private double tempDiff; //FADENEW: No longer used
//    private double totalEfficiency; //FADENEW: No longer used
    private boolean surge = false;
    private boolean fanSelect;
    double gamma = 1;
    boolean oneZone = false;
    double prevcw = 0;
    boolean hybfaon = false;
    boolean hybprevfaon = false;
    boolean hybacon = false;
    boolean hybheaton = false;
    double[] coolingelec; //FADENEW: Cooling energy
    double[] heatingener; //FADENEW: Keeps track of energy used for heating
    private boolean pModFinish = false; //sdr_hulic add boolean to finish delta P modifier
    private boolean pMod = false; //sdr_hulic add boolean to indicate change in pressure has been activated    
    private double[][] flowrateOld; //sdr_hulic array to save previous flowrate
    private double[][] flowrateComp; //sdr_hulic array to compare previous and current flowrate
    private double[][] delta_pMod; //sdr_hulic create array to store delta P modifications
    private double density = 1.204; //define static density to use for pressureMod calcs
    private double kViscos = 0.00001568; // define static kinematic viscosity for pressure mod calcs m^2/s
    private double roughness = 0.00004; // define surface roughness for shaft, value for concrete used [m]
    private boolean shaft1Save = false; // find flowpath for shaft1
    private boolean shaft2Save = false; // find flowpath for shaft2
    private boolean insideShaft1Save = false; // find flowpath for shaft1 entrance from floor
    private boolean insideShaft2Save = false; // find flowpath for shaft2 entrance from floor

    private double shaft1Area = 0;
    private double shaft1Perimeter = 0;
    private double floorHeight = 0;

    private double shaft2Area = 0;
    private double shaft2Perimeter = 0;

    private double insideShaft1Area = 0;
    private double insideShaft1Perimeter = 0;

    private double insideShaft2Area = 0;
    private double insideShaft2Perimeter = 0;

    private double roof1Length = 0;
    private double roof2Length = 0;
    private double maxFlowrateDifference = 0.02;
    //</editor-fold>
	
	//CK edits (2015): test case
    
    /*private double[] concentrationData; // to read ambient concentration timeseries data from a file*/ //CK EDIT - NOT needed anymore
    private double[][] masses_t; //Matrix of masses inside each zone
    private double[][] masses_th; //mass of air inside zone at time = t+h or t+Delta_t
    private double[] zoneConcs_prev; //array with zone pollutant concentrations
    private double[] zoneConcs_new;
    private double outdoorMass = 1.0E10; //mass balance uses outdoor zone mass, which is set to "infinity" //CK EDIT - need to change later. This is the same value as 'thermal' mass used elsewhere in this program
    private double[] gen_rem_t;
    private double[] gen_rem_th;    
    private double outdoorConc = 0.0;///1000000.0; //Need to read outdoor concentration data. This is just an initialization
    private double[][] newconcs_tmp; //temporary 2-d array to hold results of resultant zone concentrations
    private double[][] flows_t;
    private double[][] flows_th;
    private int PolControlCheck = 0; //toggle variable for pollution based window operation
    private double PolLimit = 75.0; //pollutant concentration limit beyond which windows will be closed
        
        //CK edits for this section end here 
        
        //The next method reads casedata
    public Maincalculation(DataPool caseData) throws IOException {
        //<editor-fold defaultstate="collapsed" desc="Local variables">
        zoneNumber = caseData.zoneNumber + 1; //FADE: Has one extra zone (zone 0 = outside)
        flowpathNumber = caseData.openingNumber; //FADE: Remember that openings are counted from 0
        TMassNumber = caseData.TMassNumber;
        numericalMethod = caseData.numericalMethod;
        bldgtype = caseData.buildingType;
        Delta_time = caseData.timestepsize;
        ori_Delta_time = Delta_time;
        training_Delta_time = Delta_time * ACC;
        Controller = caseData.totaltimesteps;
        istransient = caseData.varParFromOutdoorTXT; //transient or not
        //T_user = caseData.getTuser();
        //fanSelect = caseData.getfanSelect();
        nvCase = new DefineNetwork(zoneNumber, TMassNumber);
        //<editor-fold defaultstate="collapsed" desc="Console output">
        //System.out.println("TMassNumber Right after the nvCase is initialized: " + nvCase.TMassNumber);
        //</editor-fold>
        boolean[][] connect = new boolean[zoneNumber][zoneNumber];
        boolean[][] TMassConnection = new boolean[TMassNumber][zoneNumber];
        boolean[][] TMassIntConnection = new boolean[TMassNumber][TMassNumber];
        double[] prevAir_temp = new double[zoneNumber];
        double[] prevTMass_temp = new double[TMassNumber];
        // FADE hybrid predictor
        
        //CK edits (2015): defining variables for pollutant concentrations
        masses_t = new double[zoneNumber][zoneNumber];
        masses_th = new double[zoneNumber][zoneNumber]; 
        zoneConcs_prev = new double[zoneNumber];
        zoneConcs_new = new double[zoneNumber];
        gen_rem_t = new double[zoneNumber];
        gen_rem_th = new double[zoneNumber]; // need to get generation & removal constants from the InputRead file
        newconcs_tmp = new double[zoneNumber][1]; //temporary 2-d array to hold results of resultant zone concentrations after Matrix solutions
        flows_t = new double[zoneNumber][zoneNumber];
        flows_th = new double[zoneNumber][zoneNumber];
        double[] t_axis = new double[Controller];
        int iterator = 0;
        
        //CK edits end for this section
        
        double[] Tend = new double[zoneNumber];
        double[] hybridTMassTemp = new double[zoneNumber];
        double[] hybridPrevTMassTemp = new double[zoneNumber];
        // FADE hybrid predictor
        int[][] fanConnect = new int[zoneNumber][zoneNumber];
//        String cityname;
//        String month;
        double h_meteo = 0;
        double declination = 0;
        double latitude = 0;
        double solarHeatLoad_normal_hourX = 0;
        double solarHL_normal_hourX = 0;
//        double An; //FADENEW: Solar constant
//        double Bn = 0; //FADENEW: Solar constant
//        double Ad; //FADENEW: Solar constant
//        double Bd = 0; //FADENEW: Solar constant
        int timeofday = 0;
        double solarHeatLoad_diffuse_hourX = 0;
        double solarHL_diffuse_hourX = 0;
        double outdoorTemp = 0;
        double deltaTemp = 0;
        double deltaWindVel = 0;
        double deltaSolarDir = 0;
        double deltaSolarDiff = 0;
        double checkFxold = 0;
        double windVel = 0;
        double windVelocity = 0;
        int elapsedtime = 0;
        double X_Hwc = 0;
        double X_Hw = 0;
        int op = 1;
        int flagop = 0;
        double Hw = 0; //vertical location of the window through which the flow enters the room
        double Hwc = 0; //dinstance from window to ceiling
        double Aw = 0; // area of the window through which the flow enters the room
        double Q = 0; // flowrate
        int z_in = 0;
//        int profile = 0;
        double ToutTin = 0; // from 0 to 1
        double deltaT = 0;
        double hinge_height = 0; // from 0 to 1
        double sf = 1;
        double phi = 1;
        double fan_BHP = 0;
        double fan_Efficiency = 0;
        double fanelec = 0;
        double maxTemp = 0;
        double Humidityratio = 0;
        double deltaHumidityratio = 0;
        coolingelec = new double[zoneNumber];
        heatingener = new double[zoneNumber];
        //</editor-fold>
        
        //Build the network
        FormNetwork(caseData, nvCase, zoneNumber, flowpathNumber, TMassNumber, connect, fanConnect, TMassConnection, TMassIntConnection, prevAir_temp, prevTMass_temp); //FADE: Network is defined in nvCases (class variable of Maincalculation)
        
        
        //Save calculation results
        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME)); //FADE: Temp
        BufferedWriter abw = new BufferedWriter(new FileWriter(A_FILE_NAME)); //FADE: Airflow
        BufferedWriter sbw = new BufferedWriter(new FileWriter(STRAT_FILE_NAME)); //FADE: Stratification
        BufferedWriter fbw = new BufferedWriter(new FileWriter(FAN_FILE_NAME)); //FADE: Hybrid ventilation energy consumption   
        BufferedWriter pbw = new BufferedWriter(new FileWriter(PRESSURE_FILE_NAME)); //sdr_hulic - added pressure .txt output
        BufferedWriter rbw = new BufferedWriter(new FileWriter(RECOMMEND_FILE_NAME)); //sdr_hulic - added recommendation .txt output
        BufferedWriter predbw = new BufferedWriter(new FileWriter("predictor.txt")); //FADEDELETE
        BufferedWriter concw = new BufferedWriter(new FileWriter("Concentrations.txt")); //CK Edit: Concentration data for each zone
        BufferedWriter conch = new BufferedWriter(new FileWriter("Concentrations_hourly.txt")); //CK Edit: Concentration data for each zone at each hour
        BufferedWriter temph = new BufferedWriter(new FileWriter("Temperatures_hourly.txt")); //CK edit: temperatures at each hour
        concw.write("Step\t");
        for (int i = 0; i < zoneNumber; i++) {
            concw.write("Zone" + i + "\t"); 
            }
        concw.newLine();
        conch.write("Hour\t");
        temph.write("Hour\t");
        for (int i = 0; i < zoneNumber; i++) {
            conch.write("Zone" + i + "\t");
            temph.write("Zone" + i + "\t");
            }
        conch.newLine();
        temph.newLine();
        //CK Edits end here
        //Comment following lines to read data in MatLab
        for (int i = 0; i < zoneNumber; i++) {
            bw.write("ZT " + i + "\t"); //FADENEW: New output format
            predbw.write("PZT " + i + "\t"); //FADEDELETE
//            bw.write(" " + prevAir_temp[i] + " ");
        }
        for (int i = 0; i < zoneNumber; i++) {
            bw.write("ZHR " + i + "\t"); //FADENEW: New output format
            predbw.write("PTMT " + i + "\t"); //FADEDELETE
        }
        for (int i = 0; i < TMassNumber; i++) {
            bw.write("TMass " + i + "\t"); //FADENEW
//            bw.write(" " + prevTMass_temp[i] + " ");
        }
        bw.newLine();
        predbw.newLine(); //FADEDELETE

        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                if (connect[i][j] == true) {
                    abw.write("[" + i + "][" + j + "]\t"); //FADENEW
//                    abw.write(" flowrate[" + i + "][" + j + "] ");
                }
            }
        }
        //abw.newLine(); //FADENEW

        //sdr_hulic - label pressure output
        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                if (connect[i][j] == true) {
                    pbw.write(" delta_p[" + i + "][" + j + "] ");
                }
            }
        }
        pbw.newLine();

        //sdr_hulic - label recommendation output
        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                if (connect[i][j] == true) {
                    rbw.write(" Q/sqrt(p)[" + i + "][" + j + "] ");
                }
            }
        }
        rbw.newLine();

        //End MatLab
        //MAMB: Write StratificationOutput
        sbw.write(" Three columns for each zone: (1) Tin (2) Tout-Tin (3) Tceiling-Tfloor (4) hinge height (or room height if linear profile). As many sets of 4 columns as occupied zones (indicated in the following line).");
        sbw.newLine();
        //MAMB: Write second line, indicating which are the occupied zones.
        for (int z = 1; z < zoneNumber; z++) { //Remember that zones are counted from 0
            if (nvCase.zones[z].isOcczone) { //if it's an occupied zone -- NEED TO DEFINE THE OPENING and the flowrate
                sbw.write(z + "\t\t\t\t"); //FADE: 4 x \t because each zone has 4 columns
            }
        }
        sbw.newLine();

        //Calculate the initial hydrostatic pressure for each zone and the initial delta pressure
        pressures = new double[zoneNumber];
        delta_p = new double[zoneNumber][zoneNumber];
        for (int i = 0; i < zoneNumber; i++) {
            nvCase.zones[i].calAir_density();
            nvCase.zones[i].setPressure(nvCase.zones[i].getAir_density() * G * (-nvCase.zones[i].getElevation())); //reference p=-rho.g.h
            pressures[i] = nvCase.zones[i].getPressure();
//            System.out.println("Hydrostatic pressure in zone " + i + "  " + pressures[i]);
        }

        //sdr_hulic initiate new arrays
        flowrateOld = new double[zoneNumber][zoneNumber];
        flowrateComp = new double[zoneNumber][zoneNumber];
        delta_pMod = new double[zoneNumber][zoneNumber];
        //sdr_hulic define relevant parameters for friction calculations
        if (bldgtype == 4) {
            //define parameters needed for delta_pMod calcs
            for (int i = 0; i < zoneNumber; i++) {
                for (int j = 0; j < zoneNumber; j++) {
                    if (nvCase.flowpaths[i][j] != null) {
                        if (connect[i][j] == true && "Shaft1".equals(nvCase.flowpaths[i][j].getOpeningType()) && shaft1Save == false) {
                            shaft1Area = nvCase.flowpaths[i][j].getArea();
                            shaft1Perimeter = 2 * nvCase.flowpaths[i][j].getHeight() + 2 * nvCase.flowpaths[i][j].getWidth();
                            floorHeight = nvCase.flowpaths[i][j].getElevation(); // will get elevation of first instance of horizontal opening, which is floorheight                                               
                            shaft1Save = true;
                        } else if (connect[i][j] == true && "Shaft2".equals(nvCase.flowpaths[i][j].getOpeningType()) && shaft2Save == false) {
                            shaft2Area = nvCase.flowpaths[i][j].getArea();
                            shaft2Perimeter = 2 * nvCase.flowpaths[i][j].getHeight() + 2 * nvCase.flowpaths[i][j].getWidth();
                            //double shaft2SecLength = nvCase.flowpaths[i][j].getElevation(); // will get elevation of first instance of horizontal opening, which is floorheight
                            shaft2Save = true;
                        } else if (connect[i][j] == true && "InsideShaft1".equals(nvCase.flowpaths[i][j].getOpeningType()) && insideShaft1Save == false) {
                            insideShaft1Area = nvCase.flowpaths[i][j].getArea();
                            insideShaft1Perimeter = 2 * nvCase.flowpaths[i][j].getHeight() + 2 * nvCase.flowpaths[i][j].getWidth();
                            insideShaft1Save = true;
                            //dbw.write("insideShaft1 area is " + insideShaft1Area);
                        } else if (connect[i][j] == true && "InsideShaft2".equals(nvCase.flowpaths[i][j].getOpeningType()) && insideShaft2Save == false) {
                            insideShaft2Area = nvCase.flowpaths[i][j].getArea();
                            insideShaft2Perimeter = 2 * nvCase.flowpaths[i][j].getHeight() + 2 * nvCase.flowpaths[i][j].getWidth();
                            insideShaft2Save = true;
                        } else if (connect[i][j] == true && "Roof1".equals(nvCase.flowpaths[i][j].getOpeningType()) && i == 0) {
                            roof1Length = nvCase.flowpaths[i][j].getElevation() - nvCase.flowpaths[j - 1][j].getElevation();
                        } else if (connect[i][j] == true && "Roof2".equals(nvCase.flowpaths[i][j].getOpeningType()) && i == 0) {
                            roof2Length = nvCase.flowpaths[i][j].getElevation() - nvCase.flowpaths[j - 1][j].getElevation();
                        }
                    }
                }
            }
        } // end if bldgtype == 4

        flowrate = new double[zoneNumber][zoneNumber]; //CK: calculating Delta p across zones
        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                if (connect[i][j] == true) {
                    if (nvCase.flowpaths[i][j] != null) {
//                        System.out.println("i "+i+ " j "+j);
                        nvCase.flowpaths[i][j].calDelta_p(nvCase.zones[i], nvCase.zones[j], nvCase.zones[i].getIsIntZone(), oneZone, prevcw);
                        delta_p[i][j] = nvCase.flowpaths[i][j].getDelta_p();
                        rbw.write(" " + delta_p[i][j] + " "); //sdr_hulic
                    }
                }
            }
        }
        rbw.newLine(); //sdr_hulic
        
//        System.out.println("Initial delta_p: "+Arrays.deepToString(delta_p));

        //Set the initial flowrate values
        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                if (connect[i][j] == true) {
                    if (fanConnect[i][j] == 1) {
                        flowrate[i][j] = 0.0001;
                    } else if (fanConnect[i][j] == -1) {
                        flowrate[i][j] = -0.0001;
                    } else {
//                        System.out.println("DP "+i+" "+j);
                        flowrate[i][j] = nvCase.setInitialFlow(nvCase.flowpaths[i][j].getHeight() * nvCase.flowpaths[i][j].getWidth(), nvCase.zones[j], delta_p[i][j], connect[i][j], nvCase.flowpaths[i][j].getCd());
                    }
//                    abw.write(D_F.format(flowrate[i][j]) + "\t"); //FADENEW //Writing initial flow
                }
            }
        }
        abw.newLine();
//        System.out.println("Initial flowrate: "+Arrays.deepToString(flowrate));

        //Initialize gamma
        boolean first = true;
        if (fanSelect) { //FADENEW: New fanSelect only changes speed, not fan type
            gamma = 0.833; //FADE: Start at maximum fan speed
            for (int i = 0; i < zoneNumber; i++) {
                for (int j = 0; j < zoneNumber; j++) {
                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                        if (first) {
                            fbw.write(nvCase.flowpaths[i][j].fanType);
                            first = false;
                        }
                    }
                }
            }
            //<editor-fold defaultstate="collapsed" desc="Previous fan code">
            //            for (int i = 0; i < zoneNumber; i++) {
            //                for (int j = 0; j < zoneNumber; j++) {
            //                    if (connect[j][i] == true && nvCase.flowpaths[j][i] != null) {
            //                        if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
            //                            if ((nvCase.flowpaths[i][j].getHeight() * nvCase.flowpaths[i][j].getWidth()) > (Math.PI / 4 * Math.pow(nvCase.flowpaths[i][j].fanD, 2))) { //Fan smaller than opening
            //                                sf = Math.floor((nvCase.flowpaths[i][j].getHeight() * nvCase.flowpaths[i][j].getWidth()) / (Math.PI / 4 * Math.pow(nvCase.flowpaths[i][j].fanD, 2)));
            //                                phi = 1;
            //                            } else { //Fan larger than opening
            //                                phi = nvCase.flowpaths[i][j].fanD / nvCase.flowpaths[i][j].getWidth();
            //                                sf = 1;
            //                            }
            //                            if (fanConnect[i][j] == -1 && delta_p[i][j] < 0) { //DeltaP_fan,rise is negative
            //                                double Q_op = sf / Math.pow(phi, 2) * Math.sqrt(delta_p[i][j] / nvCase.flowpaths[i][j].a2);
            //                                double g = Math.pow(phi, 2) * (delta_p[i][j] * Math.pow(sf, 2) - nvCase.flowpaths[i][j].a2 * Math.pow(K * Q_op * Math.pow(phi, 2), 2));
            //                                double h = -nvCase.flowpaths[i][j].a1 * K * Q_op * sf * Math.pow(phi, 3);
            //                                double l = -nvCase.flowpaths[i][j].a0 * Math.pow(sf, 2);
            //                                gamma = 1 / (2 * g) * (-h + Math.sqrt(Math.pow(h, 2) - 4 * g * l)); //This gamma has to be high, because DeltaP,hydro is low
            //                                //<editor-fold defaultstate="collapsed" desc="Console output">
            ////                                FADE: Checking gamma+
            ////                                System.out.println("sf =" + sf);
            ////                                System.out.println("phi =" + phi);
            ////                                System.out.println("g =" + g);
            ////                                System.out.println("h =" + h);
            ////                                System.out.println("l =" + l);
            //                                System.out.println("gamma+ =" + gamma);
            ////                                double d = nvCase.flowpaths[i][j].a2 * Math.pow(gamma, 2) * Math.pow(phi, 6) / Math.pow(sf, 2);
            ////                                System.out.println("d =" + d);
            ////                                double e = nvCase.flowpaths[i][j].a1 * gamma * Math.pow(phi, 3) / sf;
            ////                                System.out.println("e =" + e);
            ////                                double f = nvCase.flowpaths[i][j].a0 - delta_p[i][j] * Math.pow(gamma, 2) * Math.pow(phi, 2);
            ////                                System.out.println("f =" + f);
            ////                                double Q_check = -e / (2 * d) - 1 / (2 * d) * Math.sqrt(Math.pow(e, 2) - 4 * d * f);
            ////                                System.out.println("Q_op =" + Q_op);
            ////                                System.out.println("Q_ck =" + Q_check);
            //                                //</editor-fold>
            //                            }
            //                        }
            //                    }
            //                }
            //            }
            //</editor-fold>
        } else {
            for (int i = 0; i < zoneNumber; i++) {
                for (int j = 0; j < zoneNumber; j++) {
                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                        gamma = nvCase.flowpaths[i][j].gamma;
                        if (first) {
                            fbw.write(nvCase.flowpaths[i][j].fanType);
                            first = false;
                        }
                    }
                }
            }
        }
        //FADE: Write FanOuput
        
        if (first) { //FADENEW: Might need to change this, because fan could be off first, but then on. Also, we have ac now.
            fbw.write("None");
        }
        fbw.newLine();
        fbw.write("Eta" + "\t" + "BHP" + "\t" + "Gamma" + "\t"); //FADENEW: New fan output will include energy consumption information
        for (int i = 1; i < zoneNumber; i++) {
            if (nvCase.zones[i].getIsOccZone()) {
                fbw.write("Zone " + i + "\t" + "\t");
            }
        }
        fbw.newLine();

        //Preparing variables used in iterations
        controller1 = 0;
        for (int i = 0; i < zoneNumber; i++) {
            prevAir_temp[i] = nvCase.zones[i].getAir_temp();
            if (caseData.getTMassCheck() == 1) {
                hybridTMassTemp[i] = nvCase.zones[i].getAir_temp();
                hybridPrevTMassTemp[i] = nvCase.zones[i].getAir_temp();
            }
        }
//        System.out.println(Arrays.toString(prevAir_temp));

        for (int i = 0; i < TMassNumber; i++) {
            prevTMass_temp[i] = nvCase.TMasses[i].getTemperature();
        }
        //CK edits (test case) bogus initial values (REMOVE later)
        for (int i = 1;i< zoneNumber;i++)
        {
            nvCase.zones[i].setCont(0.0);
        }        
        // CK edits for test case end here
        //CK edits (2015): Preparing variables used in iterations for pollutant concentrations
        masses_t[0][0] = outdoorMass;
        masses_th[0][0] = outdoorMass;          
        
        zoneConcs_prev[0] = outdoorConc;
        flows_t = flowrateConvert(); 
        
        //bogus initial zone concentration values (REMOVE later)
        for(int i = 1;i<zoneNumber;i++)
        {
            zoneConcs_prev[i] = 0.0;
            //System.out.println("initial:"+zoneConcs_prev[i]);
            System.out.println("initial volumes "+nvCase.zones[i].getVolume());
        }
        zoneConcs_prev[0] = outdoorConc;
        zoneConcs_new[0] = outdoorConc;
        nvCase.zones[0].setCont(outdoorConc);
        populateFilters(zoneNumber); //CK EDIT: REMOVE THIS LATER!
        populateWindowFilters(zoneNumber);
        //populateWindowFilters(zoneNumber);//CK EDIT: REMOVE THIS LATER!
        //CK edits end for this section
        //<editor-fold defaultstate="collapsed" desc="This might not be used">
        /**
         * ******************************FADE: This might not be
         * used**********************************************************
         * //01/26/2006 added about divergent identification (by Jinchao) int
         * quesize = 4; double temperaturedataque[][] = new
         * double[quesize][zoneNumber];
         *
         * //get actual number of flowpaths int acflowpathNumber = 0; for (int
         * i = 0; i < zoneNumber; i++) { for (int j = 0; j < zoneNumber; j++) {
         * if (connect[i][j] == true) { ++acflowpathNumber; } } } double
         * flowdataque[][] = new double[quesize][acflowpathNumber]; int
         * flowindex[][] = new int[acflowpathNumber][2]; for (int k = 0; k <
         * quesize; k++) { System.arraycopy(prevAir_temp, 0,
         * temperaturedataque[k], 0, zoneNumber); int mm = 0; for (int i = 0; i
         * < zoneNumber; i++) { for (int j = 0; j < zoneNumber; j++) { if
         * (connect[i][j] == true) { flowdataque[k][mm] = flowrate[i][j];
         * flowindex[mm][0] = i; flowindex[mm][1] = j; ++mm; } } } }
         * //01/26/2006 added about divergent identification (by Jinchao) END.
         * //********************************FADE: This might not be
         * used*********************************************************
         */
        //</editor-fold>
        trainingsteps = (int) (TRAINING_DAYS * SECS_IN_DAY / training_Delta_time);
        if (istransient != 0) {
            Controller += trainingsteps;
        }
        flowrate_error = 1.0;
        temp_error = 1.0;

        //Load the outdoor air temperature, wind velocity, and solar radiation
        BufferedReader br = new BufferedReader(new FileReader(O_FILE_NAME));
        /*cityname = */br.readLine();
        /*month = */br.readLine();
        //declination = Double.parseDouble(br.readLine()); //HFC deletes this for continuous simulation, because it is different for different months
        latitude = Double.parseDouble(br.readLine());
        h_meteo = Double.parseDouble(br.readLine());
        
        //CK edits: Need to add file reader here for outdoor concentration data
        BufferedReader OC_br = new BufferedReader (new FileReader(C_FILE_NAME));
        /*cityname = */OC_br.readLine();
        /*date = */OC_br.readLine();
        /*pollutants = */OC_br.readLine();        
        //CK edits end here. 
        
        //CK EDIT (3/13/16): Adding filter data reader here
        BufferedReader Fil_br = new BufferedReader (new FileReader(FILT_FILE_NAME)); //if a zone has 0 filtration, then the corresponding input line reads '0 0 0'
        int filt_counter = 0; //counter that goes through each zone. 
        String Filter_input;
        Filters filters[] = new Filters[zoneNumber];  
        Filters offfilters[] = new Filters[zoneNumber];
        Filters onfilters[] = new Filters[zoneNumber];
        Filter_input = Fil_br.readLine();
        StringTokenizer filter_tokenizer;
        while(filt_counter<zoneNumber)
        {          
            filter_tokenizer = new StringTokenizer(Filter_input);
            double zone = Double.parseDouble(filter_tokenizer.nextToken());            
            double efficiency = Double.parseDouble(filter_tokenizer.nextToken());
            double airflow = Double.parseDouble(filter_tokenizer.nextToken());      
            filters[filt_counter] = new Filters(zone, efficiency, airflow); //initializing a new filter object with data read
            onfilters[filt_counter] = new Filters(zone, efficiency, airflow);
            offfilters[filt_counter] = new Filters(zone, 0.0, 0.0);
            filt_counter++;
            Filter_input = Fil_br.readLine();
        }
        for (int i = 0;i <zoneNumber; i++) { //populating filter data as sinks
            zoneConcs_prev[i] = nvCase.zones[i].getCont();
            for(int j = 0; j<zoneNumber;j++)
            {
                if(i==0 && j==0)
                {
                    masses_t[i][j] = outdoorMass;
                }
                if((i>0) && (j>0) && (j==i))
                {
                    masses_t[i][j] = nvCase.zones[i].getAir_density()*nvCase.zones[i].getVolume();
                }    
                else if((i>0) && (j>0) && (j!=i))
                {
                    masses_t[i][j] = 0.0;
                }
            //gen_rem_t[i] = nvCase.zones[i].getGen() - nvCase.zones[i].getRem(); //CK edits, remove this later, to account for time-varying gen/removal coefficients
            gen_rem_t[i] = 0.0 + filters[i].removal(nvCase.zones[i].getCont());
            }            
        }
        Fil_br.close();
        //CK EDITS for this section end here
        
        //CK EDIT (3/17/16): Adding window filter data reader here. Edit later
        BufferedReader WFil_br = new BufferedReader (new FileReader(WFILT_FILE_NAME)); //if a zone has 0 filtration, then the corresponding input line reads '0 0 0'
        int wfilt_counter = 0; //counter that goes through each zone. 
        String wFilter_input;
        WindowFilters wfilters[][] = new WindowFilters[zoneNumber][zoneNumber];        
        wFilter_input = WFil_br.readLine();
        StringTokenizer wfilter_tokenizer;
       
        while(wfilt_counter<zoneNumber)
        {        
            int j = 0;
            wfilter_tokenizer = new StringTokenizer(wFilter_input);
            while(wfilter_tokenizer.hasMoreTokens())
            {
                double e = Double.parseDouble(wfilter_tokenizer.nextToken());
                wfilters[wfilt_counter][j] = new WindowFilters(e,0.0); //initializing with some efficiency e, and pressure drop 0.
                j++;
            }            
            
            wfilt_counter++;            
            j = 0;
            wFilter_input = WFil_br.readLine();
        }
        WFil_br.close();
        //CK EDITS for this section end here
        
        //<editor-fold defaultstate="collapsed" desc="Console output">
//        if (istransient == 1) {
//            System.out.println("City:   " + cityname);
//            System.out.println("Month:   " + month);
//        } else {
//            System.out.println("Steady State Case");
//        }
        //</editor-fold>
        //FADENEW:------------------------------- 
        double ftemp = 0;
        double fwind = 0;
        double fsolarHLN = 0;
        double fsolarHLD = 0;
        double fHR = 0;
        boolean prevwindowsclosed = false;
        //FADENEW:-------------------------------
        
        
        
        //Iteration for solutions.
        iterations: //FADE added a label for the large while statement
        while (temp_error >= 1E-7 * (1 - istransient) && controller1 <= STEADY_STATE_CONTROLLER * (1 - istransient) + Controller * istransient) { //FADENEW
            // HFC: if transient, it becomes while (controller1 < Controller)
            //sdr_hulic
            //CK edits: defining initial flowrate for mass balance calculation
            flows_t = flowrateConvert();
            //CK EDIT: Filter operation Schedule
            if(((elapsedtime%SECS_IN_DAY>=18.0*SECS_IN_HR)&&(elapsedtime%SECS_IN_DAY<=24.0*SECS_IN_HR))||((elapsedtime%SECS_IN_DAY>=0)&&(elapsedtime%SECS_IN_DAY<=9.0*SECS_IN_HR)))
            {
                filters = onfilters;
            }
            else
            {
                filters = offfilters;
            }
            //CK EDIT: Cooking schedule
            double generation = 26.7;
            double gen = 0.0;
            if(((elapsedtime%SECS_IN_DAY>=18.0*SECS_IN_HR)&&(elapsedtime%SECS_IN_DAY<=21.0*SECS_IN_HR))||((elapsedtime%SECS_IN_DAY>=8.0*SECS_IN_HR)&&(elapsedtime%SECS_IN_DAY<=10.0*SECS_IN_HR)))
            {
                gen = generation;
            }
            else
            {
                gen = 0.0;
            }
            masses_t[0][0] = outdoorMass;
            for(int i = 1;i<zoneNumber;i++) 
            {
            masses_t[i][i] = nvCase.zones[i].getVolume()*nvCase.zones[i].getAir_density(); //populating zone air masses for pollutant mass balance
            //System.out.print("Masses: ["+i+"]"+masses_t[i][i]);
            gen_rem_t[i] = 0.0 + filters[i].removal(nvCase.zones[i].getCont()); //CK EDIT: populating removal from filter data
            if(i == 1||i==3) {gen_rem_t[i] = gen+ filters[i].removal(nvCase.zones[i].getCont()) ;}
            //gen_rem_t[i] = gen_rem_t[i] - nvCase.zones[i].getCont()*0.39/3600*nvCase.zones[i].getVolume()*nvCase.zones[i].getAir_density();
            }
            
            /*
            System.out.println();
            for (int i =0;i<zoneNumber;i++)
            {
                for(int j =0;j<zoneNumber;j++)
                { System.out.print("Flows_t: "+flows_t[i][j]+",");} 
                System.out.println();
            }
             for (int i =0;i<zoneNumber;i++)
            {
                for(int j =0;j<zoneNumber;j++)
                { System.out.print("flowrate_t: "+flowrate[i][j]+",");} 
                System.out.println();
            }
            */
            /* CK EDIT: CONCENTRATION PRINTS
            System.out.println("concentrations at time step: "+controller1+" + "+Controller+" secs "+controller1*Delta_time); //CK EDIT
                    for(int i = 0;i<zoneNumber;i++)
                    {
                        System.out.print("Zone "+i+" concentration: "+nvCase.zones[i].getCont()+" ; ");
                    }
                 System.out.println();  */ 
                    //CK edit ends here
            //CK edits end here
                 
            if (temp_error <= 1E-6 * (1 - istransient) && pModFinish == false && bldgtype == 4) {
                //modifying pressure based on flowrate, so prog knows to add pressure mod
                pMod = true;
                //compare current airflow to saved airflow
                maxFlowrateDifference = 0; //reset flowrate difference
                for (int i = 0; i < zoneNumber; i++) {
                    for (int j = 0; j < zoneNumber; j++) {
                        if (nvCase.flowpaths[i][j] != null) {
                            if (connect[i][j] == true) {
                                flowrateComp[i][j] = (flowrate[i][j] - flowrateOld[i][j]) / flowrate[i][j];
                                // if flowrates differ by less than 2% when pressure mod is considered, finish modifying pressures
                                if (flowrateComp[i][j] >= maxFlowrateDifference) {
                                    maxFlowrateDifference = flowrateComp[i][j];
                                }
                                //dbw.write("flowrateComp[" + i + "][" + j + "] = " + flowrateComp[i][j]);
                                //dbw.newLine();
                            }
                        }
                    }
                }
                // if flowrates differ by more than 0.05%%, calc delta P mod for each relevant opening

                for (int i = 0; i < zoneNumber; i++) {
                    for (int j = 0; j < zoneNumber; j++) {
                        if (nvCase.flowpaths[i][j] != null) {
                            if (connect[i][j] == true && "Shaft1".equals(nvCase.flowpaths[i][j].getOpeningType())) {
                                delta_pMod[i][j] = calcFrictionDP(shaft1Area, shaft1Perimeter, floorHeight, flowrate[i][j]);
                            } else if (connect[i][j] == true && "Shaft2".equals(nvCase.flowpaths[i][j].getOpeningType())) {
                                delta_pMod[i][j] = calcFrictionDP(shaft2Area, shaft2Perimeter, floorHeight, flowrate[i][j]);
                            } else if (connect[i][j] == true && "InsideShaft1".equals(nvCase.flowpaths[i][j].getOpeningType())) {
                                //delta_pMod[i][j] = calcSideEjectorDP(5, 5, 1.3, 1.1);
                                if (i < caseData.numFloors && flowrate[i][j] > 0) {
                                    delta_pMod[i][j] = calcSideEjectorDP(shaft1Area, insideShaft1Area, flowrate[Math.max(i, j) - 1][Math.max(i, j)], flowrate[i][j]);
                                    delta_pMod[j][i] = -1 * delta_pMod[i][j];
                                }
                            } else if (connect[i][j] == true && "InsideShaft2".equals(nvCase.flowpaths[i][j].getOpeningType())) {
                                if (i < caseData.numFloors && flowrate[i][j] > 0) {
                                    delta_pMod[i][j] = calcSideEjectorDP(shaft1Area, insideShaft1Area, flowrate[Math.max(i, j) - 1][Math.max(i, j)], flowrate[i][j]);
                                    delta_pMod[j][i] = -1 * delta_pMod[i][j];
                                }
                            } else if (connect[i][j] == true && "Roof1".equals(nvCase.flowpaths[i][j].getOpeningType())) {
                                delta_pMod[i][j] = calcFrictionDP(shaft1Area, shaft1Perimeter, roof1Length, flowrate[i][j]);
                            } else if (connect[i][j] == true && "Roof2".equals(nvCase.flowpaths[i][j].getOpeningType())) {
                                delta_pMod[i][j] = calcFrictionDP(shaft2Area, shaft2Perimeter, roof2Length, flowrate[i][j]);
                                //dbw.write("flowrate through roof2 = " + flowrate[i][j]);
                                //dbw.newLine();
                            }
                        }
                    }
                } //end delta_pMod calcs 
                
                          
                //populate flowrateOld with current flowrate
                for (int i = 0; i < zoneNumber; i++) {
                    for (int j = 0; j < zoneNumber; j++) {
                        if (nvCase.flowpaths[i][j] != null) {
                            if (connect[i][j] == true) {
                                flowrateOld[i][j] = flowrate[i][j];
                            }
                        }
                    }
                }
            } // end delta_pMod if statement
            
            boolean increase = false;
            boolean fdata = false; //FADENEW
            //Update flow rate
            Calculation.calFlowrate(nvCase.zones, zoneNumber, connect, fanConnect, flowrate, nvCase.flowpaths, delta_p, fanSelect, gamma, oneZone);
            flowrate_error = Calculation.calFlowrate_error(flowrate, zoneNumber, nvCase.zones); //FADE: not used (?)
//            System.out.println("Iteration 1 flow: " + Arrays.deepToString(flowrate));
                       

            String temptParameters;
            //FADE: Training days
            
            if (istransient != 0) { //If is transient do this
                //Do thermal mass training
                if (controller1 < trainingsteps) { //FADENEW
                    //if (controller1 <= trainingsteps) { //FADENEW
                    Delta_time = training_Delta_time;
                } else {
                    Delta_time = ori_Delta_time;
                    //System.out.println("training is over");
                }
                if (elapsedtime % SECS_IN_DAY == 0 && controller1 <= trainingsteps) { //FADE: Training days. Every 24 hours restart the buffered reader to repeat the first 24 hours
                    fdata = true; //FADENEW
                    br.close();
                    br = new BufferedReader(new FileReader(O_FILE_NAME));
                    timeofday = 0; //Set time of day = 0
                    /*cityname = */br.readLine();
                    /*month = */br.readLine();
                    latitude = Double.parseDouble(br.readLine());
                    h_meteo = Double.parseDouble(br.readLine());
                    br.readLine();
                    prevwindowsclosed = false;
                    for (int i = 0; i < zoneNumber; i++) {
//                        if (i < zoneNumber - 1) {
                        coolingelec[i] = 0; //FADENEW: restarting cooling electricity in each zone (includes ambient for no special reason)
                        heatingener[i] = 0; //FADENEW: restarting heating electricity in each zone (includes ambient)
                        fanelec = 0; //FADENEW: restarting fan electricity consumption
//                        }
                    }
                    //CK Edits: Similarly, restarting the buffered reader for outdoor concentrations for training days. Not sure if this is needed however.
                    OC_br.close();
                    OC_br = new BufferedReader(new FileReader(C_FILE_NAME));  
                    /*cityname = */OC_br.readLine();
                    /*date = */OC_br.readLine();
                    /*pollutants = */OC_br.readLine();        
                    //CK edits end here. 
                } else if (elapsedtime % SECS_IN_DAY == 0) { //if 1 day elapsed
                    timeofday = 0;
                }
                //Actions for every hour
                if (controller1 % (SECS_IN_HR / Delta_time) == 0) { //If 1 hour...
//                    if (controller1 > trainingsteps) { //FADENEW
                    System.out.println(controller1 / (SECS_IN_HR / Delta_time) + " " + Controller / (SECS_IN_HR / Delta_time)); //FADENEW
                    System.out.println(controller1+" "+Controller);                    
                    System.out.println("concentrations at hour: "+controller1/(SECS_IN_HR/Delta_time)+" out of "+Controller/(SECS_IN_HR/Delta_time)+" secs "+controller1*Delta_time); //CK EDIT Starts here
                    double kd = (controller1/(SECS_IN_HR/Delta_time));
                    int ki = (int) kd;
                    conch.write(String.valueOf(ki)+"\t");
                    temph.write(String.valueOf(ki) + "\t");
                    for(int i = 0;i<zoneNumber;i++)
                    {
                        System.out.print("Zone "+i+" concentration: "+nvCase.zones[i].getCont()+" ; "); //CK EDIT: HOURLY CONCENTRATIONS
                    }
                    System.out.println(); //CK EDIT ends here
                    //CK EDIT: Output hourly concentrations
                    for(int i = 0;i<zoneNumber; i++)
                    {
                    //concw.write(D_F.format((nvCase.zones[i].getCont()*1000000))+" \t"); CK EDIT: This was just for testing with bogus initialized values
                        conch.write(D_F.format((nvCase.zones[i].getCont())) + " \t");
                        temph.write(D_F.format(nvCase.zones[i].getAir_temp())+ "\t");
                    }
                    conch.newLine();
                    temph.newLine();
                    
                    
                    //CK EDIT: CHECKING FOR UNSTABLE PARAMETERS
                    for (int i = 0;i<zoneNumber;i++)
                    {
                        for(int j = 0;j<zoneNumber;j++)
                        {
                            System.out.print("Flowrate: ["+i+"],["+j+"]: "+flowrate[i][j]+" ");
                        }
                        System.out.println();
                    }
                    /*
                    for (int i = 0;i<zoneNumber;i++)
                    {
                        for(int j = 0;j<zoneNumber;j++)
                        {
                            System.out.print("Flows_t ["+i+"],["+j+"] "+flows_t[i][j]/nvCase.zones[j].getAir_density()+" ");
                        }
                        System.out.println();
                    } //CK EDIT CHECKING FOR UNSTABLE PARAMETERS
                    */
                    
                    for (int i =0;i<zoneNumber;i++)
                     {
                        for(int j =0;j<zoneNumber;j++)
                        { System.out.print("Flows_th ["+i+"],["+j+"]"+flows_th[i][j]/nvCase.zones[j].getAir_density()+",");} 
                        System.out.println();
                      }
                    
                    
                    temptParameters = br.readLine(); //Following line of output.txt
//                    System.out.println(temptParameters);
                    StringTokenizer st = new StringTokenizer(temptParameters);
                    readcounter = 0;
                    while (st.hasMoreTokens() && readcounter <= 12) { //FADENEW: New Outdoor.txt has: Temp, WindVel, WindDir, SDirR, SDiffR, DTemp, DWVel, DWDir, DSDirR, DSDiffR, omega, Domega, Declination
                        tempdata[readcounter] = Double.parseDouble(st.nextToken()); //parses the text and converts it into numbers
                        readcounter++;
                    }
                    outdoorTemp = tempdata[0];
                    windVel = tempdata[1];
                    //no need to read windDir here.
                    
                    //CK EDITS: Reading for outdoor concentrations in this hour
                    String pollutionParameters = OC_br.readLine(); //Next line of OutdoorConc.txt, which is the input file for outdoor concentration data
                    StringTokenizer p_st = new StringTokenizer(pollutionParameters);
                    int readcounter_p = 0;
                    double[] concValues = new double[NumberOfPollutants];
                    while(p_st.hasMoreTokens() && readcounter_p <NumberOfPollutants) {
                        concValues[readcounter_p] = Double.parseDouble(p_st.nextToken());
                        readcounter_p++;
                    }
                    outdoorConc = concValues[0];
                    System.out.println("Read outdoorConc: "+concValues[0]);
                    //CK Edits end here
                    solarHL_normal_hourX = tempdata[3]; //solar normal heat loads for each hour.
                    solarHL_diffuse_hourX = tempdata[4];
                    deltaTemp = tempdata[5];
                    deltaWindVel = tempdata[6];
                    //FADE: tempdata[7] is not used
                    deltaSolarDir = tempdata[8];
                    deltaSolarDiff = tempdata[9];
                    Humidityratio = tempdata[10]; //HFC adds this for control
                    deltaHumidityratio = tempdata[11]; //HFC adds this for control //FADE: Might not be used
                    declination = tempdata[12]; //HFC adds this for control

                    if ((controller1 < trainingsteps) && (caseData.dailySim == 1)) { //FADENEW: For the training days in multimonth and daily simulation, the deltas should be based on the first month.
                        if (fdata) { //FADENEW: Save the first values for deltas in multimonth and daily simulation
                            ftemp = outdoorTemp;
                            fwind = windVel;
                            fsolarHLN = solarHL_normal_hourX;
                            fsolarHLD = solarHL_diffuse_hourX;
                            fHR = Humidityratio;
                        }
                        if (timeofday != 0) {
                            fdata = false;
                            if (timeofday == ((HOURS_IN_DAY * SECS_IN_HR) - SECS_IN_HR)) {
                                deltaTemp = ftemp - outdoorTemp;
                                deltaWindVel = fwind - windVel;
                                deltaSolarDir = solarHL_normal_hourX - fsolarHLN;
                                deltaSolarDiff = solarHL_diffuse_hourX - fsolarHLD;
                                deltaHumidityratio = Humidityratio - fHR;
                            }
                        }
                    }
//                    System.out.println("C1 = "+controller1 + " elapsed = "+elapsedtime+" Tdb = "+outdoorTemp+" delta = "+deltaTemp); //FADENEW

                    //<editor-fold defaultstate="collapsed" desc="Console output">
//                    System.out.println("Outdoor temp = "+outdoorTemp);
//                    System.out.println("Wind velocity = "+windVel);
//                    System.out.println("solarHL_normal_hourX = "+solarHL_normal_hourX);
//                    System.out.println("solarHL_diffuse_hourX = "+solarHL_diffuse_hourX);
//                    System.out.println("deltaTemp = "+deltaTemp);
//                    System.out.println("deltaWindVel = "+deltaWindVel);
//                    System.out.println("deltaSolarDir = "+deltaSolarDir);
//                    System.out.println("deltaSolarDiff = "+deltaSolarDiff);
//                    System.out.println("Humidityratio = "+Humidityratio);
//                    System.out.println("deltaHumidityratio = "+deltaHumidityratio);
//                    System.out.println("declination = "+declination);
                    //</editor-fold>
//                    if ((elapsedtime % SECS_IN_DAY < caseData.getOccupancyScheduleOn() * SECS_IN_HR) || (elapsedtime % SECS_IN_DAY >= (caseData.getOccupancyScheduleOff()) * SECS_IN_HR)) { //FADE: Off-peak routine
//                        nightfrac = caseData.getOffPeakLoadFrac();
//                        for (int nighti = 1; nighti < zoneNumber; nighti++) {
//                            nvCase.zones[nighti].source.setOccupancyHeat_source(nightfrac * caseData.zoneInputData[nighti].source.getOccupancyHeat_source()
//                                    + (outdoorTemperature - nvCase.zones[nighti].getAir_temp()) * nvCase.zones[nighti].getSideGlazingArea() * 1 / caseData.zoneInputData[nighti].windowR * 1.2 //FADE: Changed constants for values that the user can define
//                                    //+ (outdoorTemperature - nvCase.zones[nighti].getAir_temp()) * nvCase.zones[nighti].getSideGlazingArea() * 5.9 * 1.2//HFC: 5.9 for window 1/R, 1.2 for window ratio
//                                    + Math.min(0, (caseData.getTAC() - nvCase.zones[nighti].getAir_temp())) * COOLING_GAIN * nvCase.zones[nighti].getFloor_area() //HFC: this accounts for air conditioning energy
//                                    + Math.max(0, (caseData.IALimit - nvCase.zones[nighti].getAir_temp()) * HEATING_GAIN * nvCase.zones[nighti].getFloor_area())); //FADE: Added heating energy            
//                        }
//                    } else { //FADE: Day time
//                        for (int dayi = 1; dayi < zoneNumber; dayi++) {
//                            nvCase.zones[dayi].source.setOccupancyHeat_source(caseData.zoneInputData[dayi].source.getOccupancyHeat_source()
//                                    + Math.min(0, (26 - nvCase.zones[dayi].getAir_temp())) * 20 * nvCase.zones[dayi].getFloor_area()//HFC: this accounts for air conditioning energy
//                                    + (outdoorTemperature - nvCase.zones[dayi].getAir_temp()) * nvCase.zones[dayi].getSideGlazingArea() * 5.9 * 1.2);//HFC: 5.9 for window 1/R, 1.2 for window ratio
//                        }
//                    }
                    //Windows, fan and AC operation
                    //FADENEW: Each window has individual opening fraction now
//                    int zonesWC = 0; //FADENEW: zonesWC counts the number of zones where heating or cooling is on. It is used to close all the windows if all the occupied zones are in AC/heating mode.
                    boolean windowsclosed = false;
                    for (int i = 0; i < zoneNumber; i++) {
//                        if (i < zoneNumber - 1) {
//                        coolingelec[i] = 0; //FADENEW: restarting cooling electricity in each zone (includes ambient for no special reason)
//                        heatingener[i] = 0; //FADENEW: restarting heating electricity in each zone (includes ambient)
//                        }
                        for (int j = 0; j < zoneNumber; j++) {
                            if (connect[i][j] == true) {
                                nvCase.flowpaths[i][j].setOpeningFraction(1); //Opening all the windows
                            }
                        }
                        //FADE: Restart conditions
                        nvCase.zones[i].setCoolingHeatLoad(0);
                        nvCase.zones[i].setHeatingHeatLoad(0);
                        nvCase.zones[i].setIsWindowsClosed(false);
                        nvCase.zones[i].setIsHumidityControl(false);
                    }
//                    fanelec = 0; //FADENEW: restarting fan electricity consumption

                    //Set initial values of heat load
                    if ((elapsedtime % SECS_IN_DAY < caseData.getOccupancyScheduleOn() * SECS_IN_HR) || (elapsedtime % SECS_IN_DAY >= (caseData.getOccupancyScheduleOff()) * SECS_IN_HR)) { //FADE: Here we change the total heat load depending on the time of the day
//                        System.out.println("Nigh");
                        nightfrac = caseData.getOffPeakLoadFrac();
                        for (int nighti = 1; nighti < zoneNumber; nighti++) {
                            nvCase.zones[nighti].source.setOccupancyHeat_source(nightfrac * caseData.zoneInputData[nighti].source.getOccupancyHeat_source());
                        }
                    } else {
//                        System.out.println("Daytime");
                        nightfrac = 1;
                        for (int dayi = 1; dayi < zoneNumber; dayi++) {
                            nvCase.zones[dayi].source.setOccupancyHeat_source(caseData.zoneInputData[dayi].source.getOccupancyHeat_source());
                        }
                    }
                    //Night cooling
                    if (caseData.nightcoolCheck == 1) { //Initializes nightcooling for the first day if nightcoolCheck is on.
//                        System.out.println("Nightcooling is available");
                        //<editor-fold defaultstate="collapsed" desc="Old code">
//                        if (elapsedtime % SECS_IN_DAY >= caseData.nightcoolOff * SECS_IN_HR) { //Day: close windows and turn off fan and AC
//                        for (int aci = 1; aci < zoneNumber; aci++) { //First check if outdoor temp is above interior temperature
//                            if (nvCase.zones[aci].getIsOccZone()) {
//                                if (nvCase.zones[aci].getAir_temp() < outdoorTemp) {
//                                    for (int i = 0; i < zoneNumber; i++) { //FADENEW: Close the windows in the zone that is cold
//                                        if (connect[i][aci] == true) {
//                                            nvCase.flowpaths[i][aci].setOpeningFraction(0.05);
//                                            nvCase.flowpaths[aci][i].setOpeningFraction(0.05);
//                                        }
//                                    }
//                                    zonesAC++;
//                                }
//                            } else {
//                                zonesAC++;
//                            }
//                        }
                        //</editor-fold>
                        if ((elapsedtime % SECS_IN_DAY < caseData.nightcoolOff * SECS_IN_HR) && (elapsedtime % SECS_IN_DAY >= caseData.nightcoolOn * SECS_IN_HR)) { //Night: open windows and turn on fan.
//                            System.out.println("Open windows and turn fan on");
                            for (int i = 0; i < zoneNumber; i++) {
                                for (int j = 0; j < zoneNumber; j++) {
                                    if (connect[i][j] == true) {
                                        nvCase.flowpaths[i][j].setOpeningFraction(1); //Opening all the windows
                                    }
                                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                        nvCase.flowpaths[i][j].turnOnFan();
                                    }
                                }
                            }
                        } else {
//                            System.out.println("Close windows and turn fan off");
                            windowsclosed = true;
                            for (int i = 0; i < zoneNumber; i++) {
                                for (int j = 0; j < zoneNumber; j++) {
                                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                        nvCase.flowpaths[i][j].turnOffFan();
                                    }
                                }
                            }
                        }
                        //<editor-fold defaultstate="collapsed" desc="Old code">
//                        if (elapsedtime % SECS_IN_DAY >= caseData.nightcoolOff * SECS_IN_HR) {
//                            System.out.println("Close windows and turn fan off");
//                            windowsclosed = true;
//                            for (int i = 0; i < zoneNumber; i++) {
//                                for (int j = 0; j < zoneNumber; j++) {
//                                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
//                                        nvCase.flowpaths[i][j].turnOffFan();
//                                    }
//                                }
//                            }
//                        } else if (elapsedtime % SECS_IN_DAY >= caseData.nightcoolOn * SECS_IN_HR) { //Night: open windows and turn on fan.
//                            System.out.println("Open windows and turn fan on");
//                            for (int i = 0; i < zoneNumber; i++) {
//                                for (int j = 0; j < zoneNumber; j++) {
//                                    if (connect[i][j] == true) {
//                                        nvCase.flowpaths[i][j].setOpeningFraction(1); //Opening all the windows
//                                    }
//                                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
//                                        nvCase.flowpaths[i][j].turnOnFan();
//                                    }
//                                }
//                            }
//                        }
                        //</editor-fold>
                    }
                    
                    
                    //Window operation closes windows even if in night cooling
                    if (caseData.closewindowOACheck == 1) { //Close windows if outdoor temperature falls below threshold
                        if (outdoorTemp <= caseData.OALimit) { //Close windows and turn off fan
//                            openingfrac = 0.05;
                            windowsclosed = true;
                            for (int i = 0; i < zoneNumber; i++) {
                                for (int j = 0; j < zoneNumber; j++) {
                                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                        nvCase.flowpaths[i][j].turnOffFan();
                                    }
                                }
                            }
                        }
                    }
                        /*if ((outdoorTemp >= 29.0)||(outdoorTemp <= 16.0)) { //CK EDIT: Daytime comfort. We'll use this to determine heat load. Close windows and turn off fan - ideally turn on AC
//                            openingfrac = 0.05;
                            windowsclosed = true;
                            for (int i = 0; i < zoneNumber; i++) {
                                for (int j = 0; j < zoneNumber; j++) {
                                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                        nvCase.flowpaths[i][j].turnOffFan();
                                    }
                                }
                            }
                        }
                        else {
                            int marker = 0;
                            for (int openi = 1; openi < zoneNumber; openi++) {
                            if (nvCase.zones[openi].getIsOccZone() && nvCase.zones[openi].getAir_temp() > outdoorTemp) { marker = 1; break;
                                }
                            }
                            if (marker == 1) {windowsclosed = false;}
                        }*/
                    //CK Edit: Pollution based window control - takes priority over night cooling and OA check
                    if (PolControlCheck == 1){
                        if(outdoorConc >= PolLimit) {
                            windowsclosed = true;
                            for (int i = 0; i < zoneNumber; i++) {
                                for (int j = 0; j < zoneNumber; j++) {
                                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                        nvCase.flowpaths[i][j].turnOffFan();
                                    }
                                }
                                
                            }
                        }
                        int polmarker = 0;
                        for (int i = 0; i < zoneNumber; i++) {
                                if ((nvCase.zones[i].getCont()>= PolLimit)&&(nvCase.zones[i].getCont() > outdoorConc)) {
                                    polmarker = 1;
                                    break;
                                }
                            }
                        if(polmarker ==1) {System.out.println("PolMarker :" +polmarker); windowsclosed = false;
                        for (int i = 0; i < zoneNumber; i++) {
                                for (int j = 0; j < zoneNumber; j++) {
                                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                        nvCase.flowpaths[i][j].turnOnFan();
                                    }
                                }
                                
                            }
                        }
                    }
                    //CK Edits end 
                    
                    hybheaton = false;
                    if (caseData.closewindowIACheck == 1) { //Close windows and turn on heating if zone temperature falls below threshold
                        for (int openi = 1; openi < zoneNumber; openi++) {
                            if (nvCase.zones[openi].getIsOccZone() && nvCase.zones[openi].getAir_temp() < caseData.IALimit) { //FADENEW: Heating is on only in the zones where the themperature is lower.
//                                System.out.println("Heating on in zone " + openi + " temp is " + nvCase.zones[openi].getAir_temp());
//                                openingfrac = 0.05;
                                if (caseData.getControlCheck() == 0) {
                                    windowsclosed = true;
                                } else {
                                    for (int i = 0; i < zoneNumber; i++) { //FADENEW: Close the windows in the zone that is cold
                                        if (connect[i][openi] == true) {
                                            nvCase.flowpaths[i][openi].setOpeningFraction(0.05);
                                            nvCase.flowpaths[openi][i].setOpeningFraction(0.05);
                                        }
                                    }
                                }
                                nvCase.zones[openi].setIsWindowsClosed(true);
                                hybheaton = true;
                            }
                        }
                    }

                    //Hybrid ventilation has highest priority
                    hybfaon = false;
                    hybacon = false;

                    if (caseData.fanCheck != 0) { //If there is a fan in hybrid ventilation mode
                        boolean highHR = false;
                        Tend[0] = outdoorTemp + deltaTemp / 2;
                        double tHRend = (caseData.getOmegaFan() - Humidityratio) / deltaHumidityratio;
                        if (deltaHumidityratio >= 0 && tHRend <= 5 / 6) {
                            highHR = true;
                        } else if (deltaHumidityratio < 0 && tHRend >= 1 / 6) {
                            highHR = true;
                        }
                        double flowmultiplier;
                        double Hin;
                        double mtot;
                        if (highHR) {
                            if (prevwindowsclosed) {
                                flowmultiplier = 1 / 0.05;
                            } else {
                                flowmultiplier = 1;
                            }
                            for (int i = 1; i < zoneNumber; i++) {
                                Tend[i] = nvCase.zones[i].getAir_temp();
                                hybridTMassTemp[i] = hybridPrevTMassTemp[i];
                            }
                            double maxcost = 0;
                            for (int k = 1; k <= 3; k++) {
                                maxcost = 0;
                                for (int i = 1; i < zoneNumber; i++) {
                                    Hin = 0;
                                    mtot = 0;
                                    for (int j = 0; j < zoneNumber; j++) {
                                        if (flowrate[j][i] > 0) {
//                                            if (i == 7) System.out.println("j " +j+ " cpj "+nvCase.zones[j].getCp()+" Tendj "+Tend[j]+" airj "+nvCase.zones[j].getAir_temp());                                            
                                            Hin += flowmultiplier * flowrate[j][i] * nvCase.zones[j].getCp() * (Tend[j] + nvCase.zones[j].getAir_temp()) / 2;
                                            mtot += flowmultiplier * flowrate[j][i];
                                        }
                                    }
//                                      System.out.println("Zone "+i+" Hin "+Hin+" mtot "+mtot);
                                    if (caseData.getTMassCheck() == 0 || (caseData.getTMassCheck() == 1 && !nvCase.zones[i].getIsOccZone())) {
                                        double Tin = Hin / (mtot * nvCase.zones[i].getCp());
                                        double tau = nvCase.zones[i].getMass() / (mtot * nvCase.zones[i].getGamma());
                                        double Qin = (nightfrac * caseData.zoneInputData[i].source.getOccupancyHeat_source() + nvCase.zones[i].calSolarHeatLoad(latitude, declination, (solarHL_normal_hourX + deltaSolarDir), (solarHL_diffuse_hourX + deltaSolarDiff), (timeofday + 1800)));
                                        double ft0 = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - nvCase.zones[i].getAir_temp();
                                        double ftf = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - caseData.getTFan();
                                        double Tendtemp  = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - ft0 * Math.exp(-SECS_IN_HR / tau);
                                        if (Tendtemp == Tendtemp) {
                                            Tend[i] = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - ft0 * Math.exp(-SECS_IN_HR / tau);
                                        }
                                        if (nvCase.zones[i].getIsOccZone()) {
                                            if (nvCase.zones[i].getAir_temp() < caseData.getTFan()) { //FADE: Cases A, B or E
                                                if (Tend[i] > caseData.getTFan()) { //FADE: Case A
                                                    double t = -tau * Math.log(Math.abs(ftf / ft0));
                                                    if (t == t) { //FADE: This shouldn't be necessary!! something went wrong before this line
                                                        maxcost = Math.max(maxcost, (SECS_IN_HR - t) * (Tend[i] - caseData.getTFan()));
                                                    }
                                                } //FADE: Cases B and E have no cost
                                            } else { //FADE: Case C, D or F
                                                if (Tend[i] < caseData.getTFan()) { //FADE: Case D
                                                    double t = -tau * Math.log(Math.abs(ftf / ft0));
                                                    if (t == t) { //FADE: This shouldn't be necessary!! something went wrong before this line
                                                        maxcost = Math.max(maxcost, t * (nvCase.zones[i].getAir_temp() - caseData.getTFan()));
                                                    }
                                                } else { //FADE: Cases C and F have cost for the entire hour
                                                    maxcost = Math.max(maxcost, SECS_IN_HR * (Tend[i] + nvCase.zones[i].getAir_temp()) / 2);
                                                }
                                            }
                                        }
                                    } else {
                                        double TMasshA = 11 * nvCase.zones[i].getFloor_area();
                                        double Qsun = nvCase.zones[i].calSolarHeatLoad(latitude, declination, (solarHL_normal_hourX + deltaSolarDir), (solarHL_diffuse_hourX + deltaSolarDiff), (timeofday + 1800));
                                        double Qocc = nightfrac * caseData.zoneInputData[i].source.getOccupancyHeat_source();
                                        double Qinair = 0.2 * Qsun + 0.5 * Qocc;
                                        double Qinmass = 0.8 * Qsun + 0.5 * Qocc;
                                        double facto = 1;
                                        if (caseData.zoneInputData[i].getFloorNumber() == caseData.getNumFloors()) {
                                            Qinmass /= 3;
                                            facto = 1.5;
                                        }
                                        double B = (Qinair + Hin) / (TMasshA + mtot * nvCase.zones[i].getCp()) + Qinmass / TMasshA;
                                        double C = mtot * nvCase.zones[i].getCp() / (TMasshA + mtot * nvCase.zones[i].getCp());
                                        double tau2 = facto * caseData.getTMassSlabMass() * 880 / (C * TMasshA);
                                        double theta0 = B - C * hybridTMassTemp[i];
                                        double TMtempf = (TMasshA + mtot * nvCase.zones[i].getCp()) / TMasshA * caseData.getTFan() - (Qinair + Hin) / TMasshA;
                                        double thetaf = B - C * TMtempf;
                                        double TMtemp = (B - theta0 * Math.exp(-SECS_IN_HR / tau2)) / C;
                                        double Tendtemp = (Qinair + TMasshA * TMtemp + Hin) / (TMasshA + mtot * nvCase.zones[i].getCp());
                                        if (Tendtemp == Tendtemp) {
                                            Tend[i] = Tendtemp;
                                        }
                                        if (nvCase.zones[i].getAir_temp() < caseData.getTFan()) { //FADE: Cases A, B or E
                                            if (Tend[i] > caseData.getTFan()) { //FADE: Case A
                                                double t2 = -tau2 * Math.log(Math.abs(thetaf / theta0));
                                                if (t2 == t2) { //FADE: This shouldn't be necessary!! something wrong before this line
                                                    maxcost = Math.max(maxcost, (SECS_IN_HR - t2) * (Tend[i] - caseData.getTFan()));
                                                }
                                            } //FADE: Cases B and E have no cost
                                        } else { //FADE: Case C, D or F
                                            if (Tend[i] < caseData.getTFan()) { //FADE: Case D
                                                double t2 = -tau2 * Math.log(Math.abs(thetaf / theta0));
                                                if (t2 == t2) { //FADE: This shouldn't be necessary!! something wrong before this line
                                                    maxcost = Math.max(maxcost, t2 * (nvCase.zones[i].getAir_temp() - caseData.getTFan()));
                                                }
                                            } else { //FADE: Cases C and F have cost for the entire hour
                                                maxcost = Math.max(maxcost, SECS_IN_HR * (Tend[i] + nvCase.zones[i].getAir_temp()) / 2);
//                                                System.out.println("max cost1 "+maxcost+" tend "+Tend[i]);
                                            }
                                        }
                                    }
                                }
                            }
                            if (maxcost > MAX_COST) {
                                if (caseData.getControlCheck() == 0) { //FADENEW: Close all windows
                                    //System.out.println(" Not using individual control, so need to close all windows ");
                                    windowsclosed = false;
                                    hybfaon = true;
                                }
                            } else {
                                hybfaon = false;
                            }
                        } else {
                            hybfaon = false;
                        }
                    }
                    
                    if (caseData.ACCheck == 1) { //FADE: Air conditioning option is on
                        boolean highHR = false;
                        Tend[0] = outdoorTemp + deltaTemp / 2;
                        double tHRend = (caseData.getOmegaAC() - Humidityratio) / deltaHumidityratio;
                        if (deltaHumidityratio >= 0 && tHRend <= 5 / 6) {
                            highHR = true;
                        } else if (deltaHumidityratio < 0 && tHRend >= 1 / 6) {
                            highHR = true;
                        }
//                        System.out.println("HR is high "+highHR);
                        double flowmultiplier;
                        double Hin;
                        double mtot;
                        if (highHR) {
                            if (prevwindowsclosed) {
                                flowmultiplier = 1 / 0.05;
                            } else {
                                flowmultiplier = 1;
                            }
                            for (int i = 1; i < zoneNumber; i++) {
                                Tend[i] = nvCase.zones[i].getAir_temp();
                                hybridTMassTemp[i] = hybridPrevTMassTemp[i];
                            }
                            //System.out.println("AC option is on");
//                        int conn = 0;
                            double maxcost = 0;
                            for (int k = 1; k <= 3; k++) {
                                maxcost = 0;
                                for (int i = 1; i < zoneNumber; i++) {
                                    Hin = 0;
                                    mtot = 0;
                                    for (int j = 0; j < zoneNumber; j++) {
                                        if (flowrate[j][i] > 0) {
//                                            if (i == 7) System.out.println("j " +j+ " cpj "+nvCase.zones[j].getCp()+" Tendj "+Tend[j]+" airj "+nvCase.zones[j].getAir_temp());                                            
                                            Hin += flowmultiplier * flowrate[j][i] * nvCase.zones[j].getCp() * (Tend[j] + nvCase.zones[j].getAir_temp()) / 2;
                                            mtot += flowmultiplier * flowrate[j][i];
                                        }
                                    }
//                                    System.out.println("Zone "+i+" Hin "+Hin+" mtot "+mtot);
                                    if (caseData.getTMassCheck() == 0 || (caseData.getTMassCheck() == 1 && !nvCase.zones[i].getIsOccZone())) {
                                        double Tin = Hin / (mtot * nvCase.zones[i].getCp());
                                        double tau = nvCase.zones[i].getMass() / (mtot * nvCase.zones[i].getGamma());
                                        double Qin = (nightfrac * caseData.zoneInputData[i].source.getOccupancyHeat_source() + nvCase.zones[i].calSolarHeatLoad(latitude, declination, (solarHL_normal_hourX + deltaSolarDir), (solarHL_diffuse_hourX + deltaSolarDiff), (timeofday + 1800)));
                                        double ft0 = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - nvCase.zones[i].getAir_temp();
                                        double ftf = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - caseData.getTAC();
                                        double Tendtemp  = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - ft0 * Math.exp(-SECS_IN_HR / tau);
                                        if (Tendtemp == Tendtemp) {
                                            Tend[i] = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - ft0 * Math.exp(-SECS_IN_HR / tau);
                                        }
//                                        if (k == 3) {
//                                            System.out.println("time of day "+timeofday/3600+" Zone "+ i +" prev air temp = "+nvCase.zones[i].getAir_temp()+" final air temp = "+Tend[i]);
//                                        }
                                        if (nvCase.zones[i].getIsOccZone()) {
                                            if (nvCase.zones[i].getAir_temp() < caseData.getTAC()) { //FADE: Cases A, B or E
                                                if (Tend[i] > caseData.getTAC()) { //FADE: Case A
                                                    double t = -tau * Math.log(Math.abs(ftf / ft0));
                                                    if (t == t) { //FADE: This shouldn't be necessary!! something wrong before this line
                                                        maxcost = Math.max(maxcost, (SECS_IN_HR - t) * (Tend[i] - caseData.getTAC()));
                                                    }
                                                } //FADE: Cases B and E have no cost
                                            } else { //FADE: Case C, D or F
                                                if (Tend[i] < caseData.getTAC()) { //FADE: Case D
                                                    double t = -tau * Math.log(Math.abs(ftf / ft0));
                                                    if (t == t) { //FADE: This shouldn't be necessary!! something wrong before this line
                                                        maxcost = Math.max(maxcost, t * (nvCase.zones[i].getAir_temp() - caseData.getTAC()));
                                                    }
                                                } else { //FADE: Cases C and F have cost for the entire hour
                                                    maxcost = Math.max(maxcost, SECS_IN_HR * (Tend[i] + nvCase.zones[i].getAir_temp()) / 2);
                                                }
                                            }
                                        }
                                    } else {
                                        double TMasshA = 11 * nvCase.zones[i].getFloor_area();
                                        double Qsun = nvCase.zones[i].calSolarHeatLoad(latitude, declination, (solarHL_normal_hourX + deltaSolarDir), (solarHL_diffuse_hourX + deltaSolarDiff), (timeofday + 1800));
                                        double Qocc = nightfrac * caseData.zoneInputData[i].source.getOccupancyHeat_source();
                                        double Qinair = 0.2 * Qsun + 0.5 * Qocc;
                                        double Qinmass = 0.8 * Qsun + 0.5 * Qocc;
                                        double facto = 1;
                                        if (caseData.zoneInputData[i].getFloorNumber() == caseData.getNumFloors()) {
                                            Qinmass /= 3;
                                            facto = 1.5;
                                        }
                                        double B = (Qinair + Hin) / (TMasshA + mtot * nvCase.zones[i].getCp()) + Qinmass / TMasshA;
                                        double C = mtot * nvCase.zones[i].getCp() / (TMasshA + mtot * nvCase.zones[i].getCp());
                                        double tau2 = facto * caseData.getTMassSlabMass() * 880 / (C * TMasshA);
                                        double theta0 = B - C * hybridTMassTemp[i];
                                        double TMtempf = (TMasshA + mtot * nvCase.zones[i].getCp()) / TMasshA * caseData.getTAC() - (Qinair + Hin) / TMasshA;
                                        double thetaf = B - C * TMtempf;
                                        double TMtemp = (B - theta0 * Math.exp(-SECS_IN_HR / tau2)) / C;
                                        double Tendtemp = (Qinair + TMasshA * TMtemp + Hin) / (TMasshA + mtot * nvCase.zones[i].getCp());
                                        if (Tendtemp == Tendtemp) {
                                            Tend[i] = Tendtemp;
                                        }
//                                        if (k == 3) {
//                                            System.out.println("time of day "+timeofday/3600+" Zone "+ i +" prev air temp = "+nvCase.zones[i].getAir_temp()+" final air temp = "+Tend[i]);
//                                        }
                                        if (nvCase.zones[i].getAir_temp() < caseData.getTAC()) { //FADE: Cases A, B or E
                                            if (Tend[i] > caseData.getTAC()) { //FADE: Case A
                                                double t2 = -tau2 * Math.log(Math.abs(thetaf / theta0));
//                                                System.out.println("t2 "+t2);
                                                if (t2 == t2) { //FADE: This shouldn't be necessary!! something went wrong before this line
                                                    maxcost = Math.max(maxcost, (SECS_IN_HR - t2) * (Tend[i] - caseData.getTAC()));
                                                }
                                            } //FADE: Cases B and E have no cost
                                        } else { //FADE: Case C, D or F
                                            if (Tend[i] < caseData.getTAC()) { //FADE: Case D
                                                double t2 = -tau2 * Math.log(Math.abs(thetaf / theta0));
                                                if (t2 == t2) { //FADE: This shouldn't be necessary!! something wrong before this line
                                                    maxcost = Math.max(maxcost, t2 * (nvCase.zones[i].getAir_temp() - caseData.getTAC()));
                                                }
                                            } else { //FADE: Cases C and F have cost for the entire hour
                                                maxcost = Math.max(maxcost, SECS_IN_HR * (Tend[i] + nvCase.zones[i].getAir_temp()) / 2);
//                                                System.out.println("max cost1 "+maxcost+" tend "+Tend[i]);
                                            }
                                        }
                                    }
                                }
                            }
//                            System.out.println("max cost2 "+maxcost);
                            if (maxcost > MAX_COST) {
                                if (caseData.getControlCheck() == 0) { //FADENEW: Close all windows
//                                    System.out.println(" Not using individual control, so need to close all windows ");
                                    windowsclosed = true;
                                    hybacon = true;
                                    hybfaon = false;
                                } else {
                                    hybacon = false;
                                }
                            } else {
                                hybacon = false;
                            }
                        } else {
                            hybacon = false;
                        }
                    }
                                
                    if (hybfaon && !windowsclosed) { //FADE: Fan should be on
                        for (int i = 0; i < zoneNumber; i++) {
                            for (int j = 0; j < zoneNumber; j++) {
                                if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                    nvCase.flowpaths[i][j].turnOnFan();
                                }
                            }
                        }
                    } else { //Fan should be off according to hybrid ventilation configuration, but... 
                        if (caseData.nightcoolCheck == 1 && elapsedtime % SECS_IN_DAY >= caseData.nightcoolOn * SECS_IN_HR) { //Fan could be on during night cooling
                            hybfaon = true;
                            hybacon  = false;
//                            openingfrac = 1;
                        } else {
//                                 openingfrac = 0.05;
                            for (int i = 0; i < zoneNumber; i++) {
                                for (int j = 0; j < zoneNumber; j++) {
                                    if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                        nvCase.flowpaths[i][j].turnOffFan();
                                    }
                                }
                            }
                        }
                    }
                    
                    //FADENEW: Close all the windows
//                    System.out.println("I'm here with windowsclosed "+windowsclosed);
                    windowcheck:
                    if (!windowsclosed) {
                        for (int openi = 0; openi < zoneNumber; openi++) {
                            if ((nvCase.zones[openi].getIsOccZone() && nvCase.zones[openi].getIsWindowsClosed()) || !nvCase.zones[openi].getIsOccZone()) {
                                windowsclosed = true;
                            } else {
                                windowsclosed = false;
                                break windowcheck;
                            }
                        }
                    }

                    if (windowsclosed) {
                        System.out.println("Closing all windows");
                        for (int i = 0; i < zoneNumber; i++) {
                            for (int j = 0; j < zoneNumber; j++) {
                                if (connect[i][j] == true) {
                                    nvCase.flowpaths[i][j].setOpeningFraction(0.05);
                                }
                                if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                    nvCase.flowpaths[i][j].turnOffFan();
                                }
                            }
                            nvCase.zones[i].setIsWindowsClosed(true);
                        }
                        hybfaon = false;
                    }
                    
                    //FADE update the predictor
                    if (caseData.getTMassCheck() == 1) { //FADE: Update the temperature of predictor
                        double flowmultiplier = 1;
                        if (prevwindowsclosed) { //FADE: windows closed
                            if (hybheaton || hybacon || windowsclosed) { //FADE: Windows were closed and need to stay closed and/or ac (or heating) is on
                                flowmultiplier = 1; //Keep them closed
                            } else if (hybfaon) {
                                flowmultiplier = 10 / 0.05; //Open them and run fan: fan is assumed to increase the airflow by 10. The point is just to have the convection resistance to outdoor temp lower than the convection resistance to TM.
                            } else if (!windowsclosed) { //FADE: window were closed but need to be open now
                                flowmultiplier = 1 / 0.05;
                            }
                        } else { //FADE: windows open
                            if (hybheaton || hybacon || windowsclosed) { //FADE: Windows were open and need to be closed
                                if (hybprevfaon) {
                                    flowmultiplier = 0.005; //Close windows and turn fan off
                                } else {
                                    flowmultiplier = 0.05; //just close windows
                                }
                            } else if (hybfaon) { //FADE: windows were open, need to stay open and need to turn the fan on
                                if (hybprevfaon) {
                                    flowmultiplier = 1;
                                } else {
                                    flowmultiplier = 10;
                                }
                            }
                        }
                        double TMtemp = 0;
                        double Hin;
                        double mtot;
                        for (int i = 1; i < zoneNumber; i++) {
                            Tend[i] = nvCase.zones[i].getAir_temp();
                            hybridTMassTemp[i] = hybridPrevTMassTemp[i];
                        }
                        for (int k = 1; k <= 3; k++) {
                            for (int i = 1; i < zoneNumber; i++) {
                                Hin = 0;
                                mtot = 0;
                                for (int j = 0; j < zoneNumber; j++) {
                                    if (flowrate[j][i] > 0) {
                                        Hin += flowmultiplier * flowrate[j][i] * nvCase.zones[j].getCp() * (Tend[j] + nvCase.zones[j].getAir_temp()) / 2;
                                        mtot += flowmultiplier * flowrate[j][i];
                                    }
                                }
                                if (!nvCase.zones[i].getIsOccZone()) {
                                    double Tin = Hin / (mtot * nvCase.zones[i].getCp());
                                    double tau = nvCase.zones[i].getMass() / (mtot * nvCase.zones[i].getGamma());
                                    double Qin = (nightfrac * caseData.zoneInputData[i].source.getOccupancyHeat_source() + nvCase.zones[i].calSolarHeatLoad(latitude, declination, (solarHL_normal_hourX + deltaSolarDir), (solarHL_diffuse_hourX + deltaSolarDiff), (timeofday + 1800)));
                                    double ft0 = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - nvCase.zones[i].getAir_temp();
                                    double Tendtemp = Qin / (mtot * nvCase.zones[i].getCp()) + Tin - ft0 * Math.exp(-SECS_IN_HR / tau);
                                    if (Tendtemp == Tendtemp) {
                                        Tend[i] = Tendtemp;
                                    }
                                } else {
                                    double TMasshA = 11 * nvCase.zones[i].getFloor_area();
                                    double Qsun = nvCase.zones[i].calSolarHeatLoad(latitude, declination, (solarHL_normal_hourX + deltaSolarDir), (solarHL_diffuse_hourX + deltaSolarDiff), (timeofday + 1800));
                                    double Qocc = nightfrac * caseData.zoneInputData[i].source.getOccupancyHeat_source();
                                    double Qinair = 0.2 * Qsun + 0.5 * Qocc;
                                    double Qinmass = 0.8 * Qsun + 0.5 * Qocc;
                                    double facto = 1;
                                    if (caseData.zoneInputData[i].getFloorNumber() == caseData.getNumFloors()) {
                                            Qinmass /= 3;
                                            facto = 1.5;
                                    }
                                    double C = mtot * nvCase.zones[i].getCp() / (TMasshA + mtot * nvCase.zones[i].getCp());
                                    double JK = Qinair + Hin - (TMasshA + mtot * nvCase.zones[i].getCp()) * caseData.getTAC();
                                    double tau2 = facto * caseData.getTMassSlabMass() * 880 / (C * TMasshA);
                                    double Rest = TMasshA / C * (((Qinair + Hin) / (TMasshA + mtot * nvCase.zones[i].getCp()) + Qinmass / TMasshA) * (1 - Math.exp(-SECS_IN_HR / tau2)) + C * hybridTMassTemp[i] * Math.exp(-SECS_IN_HR / tau2)) + JK;
                                    double Qcool = 0;
                                    if (hybacon) {
                                        Qcool = Math.max(0, Rest / (1 + TMasshA / C * (1 - Math.exp(-SECS_IN_HR / tau2)) / (TMasshA + mtot * nvCase.zones[i].getCp())));
                                    }
                                    double B = (Qinair - Qcool + Hin) / (TMasshA + mtot * nvCase.zones[i].getCp()) + Qinmass / TMasshA;
                                    double theta0 = B - C * hybridTMassTemp[i];
                                    TMtemp = (B - theta0 * Math.exp(-SECS_IN_HR / tau2)) / C;
                                    double Tendtemp = (Qinair - Qcool + TMasshA * TMtemp + Hin) / (TMasshA + mtot * nvCase.zones[i].getCp());
                                    if (Tendtemp == Tendtemp) {
                                        Tend[i] = Tendtemp;
                                    }
                                }
                                if (k == 3) {
                                    if (TMtemp == TMtemp) {
                                        hybridPrevTMassTemp[i] = TMtemp;
                                    }
                                }
                            }
                        }
                    }
                    prevwindowsclosed = windowsclosed;
                    hybprevfaon = hybfaon;

                    loadCw(caseData, nvCase, connect, zoneNumber, tempdata[1], tempdata[2]); //FADENEW: Calculate Cw for each zone
//                    loadCw(caseData, nvCase, connect, zoneNumber, tempdata[1], tempdata[2], openingfrac); //Calculate Cw for each zone
                    //<editor-fold defaultstate="collapsed" desc="Console output">
                    //System.out.println(timeofday / 3600);
                    //</editor-fold>

//                    if (fanSelect) {
//                        gamma = 0.833; //FADE: Start at maximum fan speed
//                        //<editor-fold defaultstate="collapsed" desc="Previous fan code">
//                        //            for (int i = 0; i < zoneNumber; i++) {
//                        //                for (int j = 0; j < zoneNumber; j++) {
//                        //                    if (connect[j][i] == true && nvCase.flowpaths[j][i] != null) {
//                        //                        if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
//                        //                            if ((nvCase.flowpaths[i][j].getHeight() * nvCase.flowpaths[i][j].getWidth()) > (Math.PI / 4 * Math.pow(nvCase.flowpaths[i][j].fanD, 2))) { //Fan smaller than opening
//                        //                                sf = Math.floor((nvCase.flowpaths[i][j].getHeight() * nvCase.flowpaths[i][j].getWidth()) / (Math.PI / 4 * Math.pow(nvCase.flowpaths[i][j].fanD, 2)));
//                        //                                phi = 1;
//                        //                            } else { //Fan larger than opening
//                        //                                phi = nvCase.flowpaths[i][j].fanD / nvCase.flowpaths[i][j].getWidth();
//                        //                                sf = 1;
//                        //                            }
//                        //                            if (fanConnect[i][j] == -1 && delta_p[i][j] < 0) { //DeltaP_fan,rise is negative
//                        //                                double Q_op = sf / Math.pow(phi, 2) * Math.sqrt(delta_p[i][j] / nvCase.flowpaths[i][j].a2);
//                        //                                double g = Math.pow(phi, 2) * (delta_p[i][j] * Math.pow(sf, 2) - nvCase.flowpaths[i][j].a2 * Math.pow(K * Q_op * Math.pow(phi, 2), 2));
//                        //                                double h = -nvCase.flowpaths[i][j].a1 * K * Q_op * sf * Math.pow(phi, 3);
//                        //                                double l = -nvCase.flowpaths[i][j].a0 * Math.pow(sf, 2);
//                        //                                gamma = 1 / (2 * g) * (-h + Math.sqrt(Math.pow(h, 2) - 4 * g * l)); //This gamma has to be high, because DeltaP,hydro is low
//                        //                                //<editor-fold defaultstate="collapsed" desc="Console output">
//                        ////                                FADE: Checking gamma+
//                        ////                                System.out.println("sf =" + sf);
//                        ////                                System.out.println("phi =" + phi);
//                        ////                                System.out.println("g =" + g);
//                        ////                                System.out.println("h =" + h);
//                        ////                                System.out.println("l =" + l);
//                        //                                System.out.println("gamma+ =" + gamma);
//                        ////                                double d = nvCase.flowpaths[i][j].a2 * Math.pow(gamma, 2) * Math.pow(phi, 6) / Math.pow(sf, 2);
//                        ////                                System.out.println("d =" + d);
//                        ////                                double e = nvCase.flowpaths[i][j].a1 * gamma * Math.pow(phi, 3) / sf;
//                        ////                                System.out.println("e =" + e);
//                        ////                                double f = nvCase.flowpaths[i][j].a0 - delta_p[i][j] * Math.pow(gamma, 2) * Math.pow(phi, 2);
//                        ////                                System.out.println("f =" + f);
//                        ////                                double Q_check = -e / (2 * d) - 1 / (2 * d) * Math.sqrt(Math.pow(e, 2) - 4 * d * f);
//                        ////                                System.out.println("Q_op =" + Q_op);
//                        ////                                System.out.println("Q_ck =" + Q_check);
//                        //                                //</editor-fold>
//                        //                            }
//                        //                        }
//                        //                    }
//                        //                }
//                        //            }
//                        //</editor-fold>
//                    } else {
//                        for (int i = 0; i < zoneNumber; i++) {
//                            for (int j = 0; j < zoneNumber; j++) {
//                                if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
//                                    gamma = nvCase.flowpaths[i][j].gamma;
//                                }
//                            }
//                        }
//                    }
                } //end of if (controller1 % (3600/Delta_time) ==0 ) i.e. every hour

//                System.out.println("Windows are "+openingfrac);
                //Interpolating values between one hour and the other
                //FADENEW: New interpolation, because previous code was giving wrong temperature at the hour
                outdoorTemperature = outdoorTemp + (timeofday % SECS_IN_HR) / SECS_IN_HR * deltaTemp; //FADENEW
                solarHeatLoad_normal_hourX = solarHL_normal_hourX + (timeofday % SECS_IN_HR) / SECS_IN_HR * deltaSolarDir; //FADENEW: [W/m^2]
                solarHeatLoad_diffuse_hourX = solarHL_diffuse_hourX + (timeofday % SECS_IN_HR) / SECS_IN_HR * deltaSolarDiff; //FADENEW: [W/m^2]
                windVelocity = windVel + (timeofday % SECS_IN_HR) / SECS_IN_HR * deltaWindVel;
                //<editor-fold defaultstate="collapsed" desc="FADENEW: Old interpolation">
                //                int interpDummy=0;
                //                if( (timeofday - (timeofday / ((int) SECS_IN_HR)) * ((int) SECS_IN_HR))==0){
                //                    interpDummy=1;
                //                }
                //                outdoorTemperature = outdoorTemp + (timeofday - (timeofday / ((int) SECS_IN_HR)) * ((int) SECS_IN_HR)) / SECS_IN_HR* deltaTemp+interpDummy*deltaTemp;
                //                System.out.println("outdoorTemperature is: "+outdoorTemperature);
                //                solarHeatLoad_normal_hourX = solarHL_normal_hourX + (timeofday - (timeofday / ((int) SECS_IN_HR)) * ((int) SECS_IN_HR)) / SECS_IN_HR * deltaSolarDir+interpDummy*deltaSolarDir; //solar normal heat loads for each hour.
                //                solarHeatLoad_diffuse_hourX = solarHL_diffuse_hourX + (timeofday - (timeofday / ((int) SECS_IN_HR)) * ((int) SECS_IN_HR)) / SECS_IN_HR * deltaSolarDiff+interpDummy*deltaSolarDiff; //20071021 added
                //                windVelocity = windVel + (timeofday - (timeofday / ((int) SECS_IN_HR)) * ((int) SECS_IN_HR)) / SECS_IN_HR * deltaWindVel+interpDummy*deltaWindVel;

                //                outdoorTemperature = outdoorTemp + (timeofday - (timeofday / ((int) SECS_IN_HR)) * ((int) SECS_IN_HR)) / (double) SECS_IN_HR * deltaTemp;
                //                solarHeatLoad_normal_hourX = solarHL_normal_hourX + (timeofday - (timeofday / ((int) SECS_IN_HR)) * ((int) SECS_IN_HR)) / (double) SECS_IN_HR * deltaSolarDir; //solar normal heat loads for each hour.
                //                solarHeatLoad_diffuse_hourX = solarHL_diffuse_hourX + (timeofday - (timeofday / ((int) SECS_IN_HR)) * ((int) SECS_IN_HR)) / (double) SECS_IN_HR * deltaSolarDiff; //20071021 added
                //                windVelocity = windVel + (timeofday - (timeofday / ((int) SECS_IN_HR)) * ((int) SECS_IN_HR)) / (double) SECS_IN_HR * deltaWindVel;
                //                outdoorTemperature = outdoorTemp + (timeofday - ((timeofday / ((int) SECS_IN_DAY)) * ((int) SECS_IN_DAY))) / SECS_IN_DAY * deltaTemp;
                //                solarHeatLoad_normal_hourX = solarHL_normal_hourX + (timeofday - ((timeofday / ((int) SECS_IN_DAY)) * ((int) SECS_IN_DAY))) / SECS_IN_DAY * deltaSolarDir; //solar normal heat loads for each hour.
                //                solarHeatLoad_diffuse_hourX = solarHL_diffuse_hourX + (timeofday - ((timeofday / ((int) SECS_IN_DAY)) * ((int) SECS_IN_DAY))) / SECS_IN_DAY * deltaSolarDiff; //20071021 added
                //                windVelocity = windVel + (timeofday - ((timeofday / ((int) SECS_IN_DAY)) * ((int) SECS_IN_DAY))) / SECS_IN_DAY * deltaWindVel;
                //</editor-fold>
                //Set wind velocity as boundary cond on each zone.
                loadWindVelocity(nvCase, connect, zoneNumber, windVelocity, h_meteo); //FADE
                //loadWindVelocity(nvCase, connect, zoneNumber, windVelocity);

                //Set outdoor temperature & humidity
                nvCase.zones[0].setAir_temp(outdoorTemperature);
                if (elapsedtime == 0) {
                    prevAir_temp[0] = outdoorTemperature;
                }
                nvCase.zones[0].setHumidityRatio(Humidityratio);

                if (hybacon) {
                    for (int aci = 1; aci < zoneNumber; aci++) {
                        if (nvCase.zones[aci].getIsOccZone() && nvCase.zones[aci].getIsWindowsClosed()) { //FADENEW: Turn on ac only if window is closed
//                            System.out.println("MCP: "+nvCase.zones[aci].getAir_density()*nvCase.zones[aci].getVolume()*nvCase.zones[aci].getCp()+ " deltaT "+(nvCase.zones[aci].getAir_temp()-caseData.getTAC()));
                            if (caseData.getTMassCheck() == 0) {
                            nvCase.zones[aci].setCoolingHeatLoad((Math.max(0, nvCase.zones[aci].getAir_density() * nvCase.zones[aci].getVolume() * nvCase.zones[aci].getCv() * (nvCase.zones[aci].getAir_temp() - caseData.getTAC()) / Delta_time) + nvCase.zones[aci].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday)) + nvCase.zones[aci].source.getOccupancyHeat_source());
//                                    System.out.println("zone " + aci + " after " + nvCase.zones[aci].source.getOccupancyHeat_source());
                            //Cooling electricity due to temperature above threshold:
                            } else {
                                double QTMass = 0;
                                for (int j = 0; j < TMassNumber; j++) {
                                    if (TMassConnection[j][aci]) {
                                    //    System.out.println("TMass "+j+" is connected to zone "+aci+" temperatures: "+nvCase.TMasses[j].getTemperature()+" and "+nvCase.zones[aci].getAir_temp());
                                  //      System.out.println("hA "+nvCase.TMasses[j].getHA(nvCase.zones[aci].getAir_temp()));
                                        QTMass += nvCase.TMasses[j].getHA(nvCase.zones[aci].getAir_temp()) * (nvCase.zones[aci].getAir_temp() - nvCase.TMasses[j].getTemperature()); //Assumed so air temperature is higher 
                                    }
                                }
                                //System.out.println("QTMass "+QTMass);
//                                System.out.println("Zone "+aci+" need to remove "+(-nvCase.zones[aci].getAir_density() * nvCase.zones[aci].getVolume() * nvCase.zones[aci].getCv() * (caseData.getTAC() - nvCase.zones[aci].getAir_temp()) / Delta_time)+" Mass takes "+QTMass+" and people, sun give "+(nvCase.zones[aci].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday) + nvCase.zones[aci].source.getOccupancyHeat_source()));
                                nvCase.zones[aci].setCoolingHeatLoad(Math.max(0, (0.2 * nvCase.zones[aci].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday) + 0.5 * nvCase.zones[aci].source.getOccupancyHeat_source()- QTMass - (nvCase.zones[aci].getAir_density() * nvCase.zones[aci].getVolume() * nvCase.zones[aci].getCv() * (caseData.getTAC() - nvCase.zones[aci].getAir_temp()) / Delta_time))));
//                                System.out.println("Cooling is  "+nvCase.zones[aci].getCoolingHeatLoad());
                            }
                            coolingelec[aci] += nvCase.zones[aci].getCoolingHeatLoad() *Delta_time / 1000;
                            double inf = 0; //FADE: total incoming flowrate
                            double inh = 0; //FADE: flow in * enthalpy
                            for (int i = 0; i < zoneNumber; i++) {
                                if (connect[i][aci] == true && flowrate[i][aci] > 0) {
                                    inf += flowrate[i][aci];
                                    inh += flowrate[i][aci] * (nvCase.zones[i].getAir_temp() + nvCase.zones[i].getHumidityRatio() * (2501.3 + 1.86 * nvCase.zones[i].getAir_temp())); //FADE: KJ/s
                                }
                            }
                            if (inf != 0) {
                                double mdotvapor = (LATENT_HEAT_PERSON * nightfrac * caseData.zoneInputData[aci].source.getOccupancyHeat_source() * PEOPLE_SENSIBLE_AVG_FRAC / SENSIBLE_HEAT_PERSON / H_FG);
                                inh += mdotvapor * (2501.3 + 1.86 * nvCase.zones[aci].getAir_temp());
                            }
                            double hrtemp = (inh - inf * (nvCase.zones[aci].getAir_temp() + caseData.getOmegaAC() * (2501.3 + 1.86 * nvCase.zones[aci].getAir_temp()))) * Delta_time;
                            if (hrtemp > 0 && nvCase.zones[aci].getHumidityRatio() > caseData.getOmegaAC()) {
//                                System.out.println("Zone " + aci + " is humidity controlled");
                                nvCase.zones[aci].setIsHumidityControl(true);
                                coolingelec[aci] += hrtemp;
                            } else {
//                                System.out.println("Zone "+aci+" is not humidity controlled");
                                nvCase.zones[aci].setIsHumidityControl(false);
                            }
                        }
                    }
                }

                if (hybheaton) {
                    for (int heati = 1; heati < zoneNumber; heati++) {
                        if (nvCase.zones[heati].getIsOccZone()) {
                            if (nvCase.zones[heati].getIsWindowsClosed()) {
                                //FADE: first find incoming flowrate. Each occupied zone has only one inlet and one outlet (except for shaft!)
                                double incomflow = 0;
                                double incomtemp = 0;
                                incomflowr:
                                for (int heaticonn = 0; heaticonn < zoneNumber; heaticonn++) {
                                    if (flowrate[heaticonn][heati] > 0) {
                                        if (flowrate[heaticonn][heati] > 1) {
                                            incomflow = flowrate[heaticonn][heati] * 0.05;
                                        } else {
                                            incomflow = flowrate[heaticonn][heati];
                                        }

                                        incomflow = flowrate[heaticonn][heati] * 0.05;
                                        incomtemp = nvCase.zones[heaticonn].getAir_temp();
                                        break incomflowr;
                                    }
                                }
//                                System.out.println("I see that heating is on in zone " + heati);
//                                System.out.println("I need " + (nvCase.zones[heati].getMass() * nvCase.zones[heati].getCv() * (caseData.IALimit - nvCase.zones[heati].getAir_temp())));
//                                System.out.println("I have: people = " + (nightfrac * caseData.zoneInputData[heati].source.getOccupancyHeat_source())*3600 + " and sun = " + (nvCase.zones[heati].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday))*3600);
//                                System.out.println("I add: " + Math.max(0, nvCase.zones[heati].getMass() * nvCase.zones[heati].getCv() * (caseData.IALimit - nvCase.zones[heati].getAir_temp()) / SECS_IN_HR - nightfrac * caseData.zoneInputData[heati].source.getOccupancyHeat_source() - nvCase.zones[heati].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday)));
                                double tau = nvCase.zones[heati].getMass() / (incomflow * nvCase.zones[heati].getGamma());
                                double timin = 3600 - (timeofday % SECS_IN_HR);
                                double expfact = Math.exp(-timin / tau);
                                double Qhincomenth = incomflow * nvCase.zones[heati].getCp() / (1 - expfact) * (caseData.IALimit - nvCase.zones[heati].getAir_temp() * expfact) - incomtemp * incomflow * nvCase.zones[heati].getCp();
//                                System.out.println("Qhincomenth "+Qhincomenth);
                                nvCase.zones[heati].source.setOccupancyHeat_source(nightfrac * caseData.zoneInputData[heati].source.getOccupancyHeat_source() + Math.max(0, (Qhincomenth - nightfrac * caseData.zoneInputData[heati].source.getOccupancyHeat_source() - nvCase.zones[heati].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday))));
//                               System.out.println("Qheating "+Math.max(0, (Qhincomenth - nightfrac * caseData.zoneInputData[heati].source.getOccupancyHeat_source() - nvCase.zones[heati].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday))) * Delta_time / 1000);
                                heatingener[heati] += Math.max(0, (Qhincomenth - nightfrac * caseData.zoneInputData[heati].source.getOccupancyHeat_source() - nvCase.zones[heati].calSolarHeatLoad(latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday))) * Delta_time / 1000; //FADENEW
                            }
                        }
                    }
                }

                if (hybfaon) {
                    for (int i = 0; i < zoneNumber; i++) {
                        for (int j = 0; j < zoneNumber; j++) {
                            if (nvCase.flowpaths[i][j] != null) {
//                            if (nvCase.zones[i].getIsIntZone() && nvCase.zones[j].getIsIntZone()) { //FADENEW: No longer used
//                                tempDiff = Math.abs(maxTemp - T_user);
//                            }
                                if (fanConnect[i][j] == 1 && nvCase.flowpaths[i][j].isOn) {
                                    fan_BHP = Math.abs(delta_p[i][j] * (flowrate[i][j] / nvCase.zones[i].getAir_density())); //FADE: Was divided by the density twice for unknown reasons
                                    fan_Efficiency = nvCase.flowpaths[i][j].b2 * Math.pow(phi, 6) * Math.pow(gamma, 2) / Math.pow(sf, 2) * Math.pow((flowrate[i][j] / nvCase.zones[i].getAir_density()), 2) + nvCase.flowpaths[i][j].b1 * Math.pow(phi, 3) * gamma / sf * Math.abs((flowrate[i][j] / nvCase.zones[i].getAir_density()));
                                    if (fan_Efficiency > 0) {
                                        fanelec += fan_BHP / fan_Efficiency * Delta_time / 1000; //Fan power is in kW
                                    }
                                }
                            }
                        }
                    }
                }
                //Increase elapsedtime and timeof day by Delta_time
                elapsedtime += Delta_time;
                timeofday += Delta_time; // timeofday keeps track of the hour in each day
            } //end of "if istransient != 0"

            boolean conte = true;
            
            while (conte) { //FADE: iterate to change gamma according to user specifications
                //Calculate the new delta_p
                for (int i = 0; i < zoneNumber; i++) {
                    for (int j = 0; j < zoneNumber; j++) {
                        if (nvCase.flowpaths[i][j] != null) {
                            if (connect[i][j] == true) {
                                nvCase.flowpaths[i][j].calDelta_p(nvCase.zones[i], nvCase.zones[j], nvCase.zones[i].getIsIntZone(), oneZone, prevcw);
                                delta_p[i][j] = nvCase.flowpaths[i][j].getDelta_p();

                                //sdr_hulic - if pMod has run, add delta_pMod to delta_p
                                if (pMod = true) {
                                    delta_p[i][j] += delta_pMod[i][j];
                                }
                            }
                        }
                    }
                }
//                System.out.println("Iteration 2 deltaP: "+Arrays.deepToString(delta_p));

                //Calculate the new flow rate
                
                Calculation.calFlowrate(nvCase.zones, zoneNumber, connect, fanConnect, flowrate, nvCase.flowpaths, delta_p, fanSelect, gamma, oneZone);
//                System.out.println("Iteration 2 flow: " + Arrays.deepToString(flowrate));
                
                //Newton-Raphson method
                boolean again = false;
                checkdx = 1.0;
                checkFx = 1.0;
                newtoni = 0;
                checkFxold = 1E-6;
//                System.out.println(gamma);
                nr: //FADE added a label for Newton-Raphson method
                
                while (((checkdx > 1.0e-6 || checkFx > 1.0E-6) && newtoni < MAXITER) || again) {
                    newtoni++;
                    lambda = 1.0;
                    double[][] J = new double[zoneNumber][zoneNumber];
                    double[] B = new double[zoneNumber];
                    double[] C = new double[zoneNumber];
                    for (int i = 0; i < zoneNumber; i++) {
                        C[i] = 0.0;
                    }
                    /*FADE: Jacobian matrix
                     J_{i,j}=Sigmasum_{i}[D_F_{j,i}/dP_{j}] using current estimates of P
                     B_{i}=Sigmasum_{j}[F_{j,i} using current estimates of P*/
                    double f;
                    for (int i = 1; i < zoneNumber; i++) {
                        //boolean flag = false; //FADE: Flag for fan. Not used
                        B[i] = 0.0;
                        for (int j = 0; j < zoneNumber; j++) {
                            if (connect[j][i] == true && nvCase.flowpaths[j][i] != null) {
                                if (((fanConnect[i][j] == 1 || fanConnect[i][j] == -1) && nvCase.flowpaths[j][i].isOn) && ((oneZone && nvCase.zones[i].getIsIntZone()) || !oneZone)) {//FADE added fan
                                    if ((nvCase.flowpaths[i][j].getHeight() * nvCase.flowpaths[i][j].getWidth()) > (Math.PI / 4 * Math.pow(nvCase.flowpaths[i][j].fanD, 2))) { //Fan smaller than opening
                                        sf = Math.floor((nvCase.flowpaths[i][j].getHeight() * nvCase.flowpaths[i][j].getWidth()) / (Math.PI / 4 * Math.pow(nvCase.flowpaths[i][j].fanD, 2)));
                                        phi = 1;
                                    } else { //Fan larger than opening
                                        phi = nvCase.flowpaths[i][j].fanD / nvCase.flowpaths[i][j].getWidth();
                                        sf = 1;
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="Previous fan code">
                                    //                                if (fanSelect) { //FADE: CoolVent will select the best fan according to user specifications
                                    //                                    double a = nvCase.flowpaths[i][j].a2 * Math.pow(Q_user, 2) * Math.pow(phi, 6) / Math.pow(sf, 2) - Math.abs(delta_p[i][j] * Math.pow(phi, 2));
                                    //                                    double b = nvCase.flowpaths[i][j].a1 * Q_user * Math.pow(phi, 3) / sf;
                                    //                                    double c = nvCase.flowpaths[i][j].a0;
                                    //                                    gamma = -b / (2 * a) - Math.sqrt(Math.pow(b, 2) - 4 * a * c) / (2 * a);
                                    //                                    if (gamma < 0.833) { //Limit fan speed to 1.2 times the rated speed
                                    //                                        gamma = 0.833;
                                    //                                    }
                                    //                                }
                                    //</editor-fold>
                                    double d = nvCase.flowpaths[i][j].a2 * Math.pow(gamma, 2) * Math.pow(phi, 6) / Math.pow(sf, 2);
                                    double e = nvCase.flowpaths[i][j].a1 * gamma * Math.pow(phi, 3) / sf;
                                    if (fanConnect[i][j] == 1) { //FADE: DeltaP_fan,rise = Pi - Pj
                                        f = nvCase.flowpaths[i][j].a0 - delta_p[j][i] * Math.pow(gamma, 2) * Math.pow(phi, 2);
                                    } else { //FADE: DeltaP_fan,rise = Pj - Pi
                                        f = nvCase.flowpaths[i][j].a0 - delta_p[i][j] * Math.pow(gamma, 2) * Math.pow(phi, 2);
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="Console output">
//                                    System.out.println("sf ="+sf);
//                                    System.out.println("phi ="+phi);
//                                    System.out.println("d ="+d);
//                                    System.out.println("e ="+e);
//                                    System.out.println("f ="+f);
//                                    System.out.println("disc ="+(Math.pow(e, 2)-4 * d * f));
                                    //</editor-fold>
                                    if (4 * d * f > Math.pow(e, 2)) { //FADE: Fan operation is unstable or flow reversal (not implemented, yet).
                                        if (fanSelect) { //Fan speed can be changed
                                            if (gamma <= 0.833) { //Gamma cannot be decreased
                                                surge = true;
                                                System.err.println("SURGE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                                                break iterations;
                                            } else { //Gamma can be decreased
//                                                System.out.println("Increasing omega");
                                                gamma *= 0.9; //Increase the speed of the fan to reach DeltaP_rise
                                                for (int k = 0; k < zoneNumber; k++) {
                                                    for (int l = 0; l < zoneNumber; l++) {
                                                        if (nvCase.flowpaths[k][l] != null) {
                                                            if (connect[k][l] == true) {
                                                                nvCase.flowpaths[k][l].calDelta_p(nvCase.zones[k], nvCase.zones[l], nvCase.zones[k].getIsIntZone(), oneZone, prevcw);
                                                                delta_p[k][l] = nvCase.flowpaths[k][l].getDelta_p();
                                                            }
                                                        }
                                                    }
                                                }
                                                Calculation.calFlowrate(nvCase.zones, zoneNumber, connect, fanConnect, flowrate, nvCase.flowpaths, delta_p, fanSelect, gamma, oneZone);
                                                newtoni--;
                                                again = true;
                                                increase = true;
                                            }
                                        }
                                    } else if (fanConnect[i][j] == 1 || fanConnect[i][j] == -1) {
                                        J[i][j] -= nvCase.flowpaths[j][i].getPowerN() * nvCase.zones[i].getAir_density() * (Math.pow((gamma * phi), 2)) / Math.sqrt(Math.pow(e, 2) - 4 * d * f);
                                        J[i][i] += nvCase.flowpaths[j][i].getPowerN() * nvCase.zones[i].getAir_density() * (Math.pow((gamma * phi), 2)) / Math.sqrt(Math.pow(e, 2) - 4 * d * f);
                                        again = false;
//                                    } else if (fanConnect[i][j] == -1) { //Flow goes from J (intZone) to I (ambientZone)
//                                        J[i][j] -= nvCase.flowpaths[j][i].getPowerN() * nvCase.zones[i].getAir_density() * (Math.pow((gamma * phi), 2)) / Math.sqrt(Math.pow(e, 2) - 4 * d * f);
//                                        J[i][i] += nvCase.flowpaths[j][i].getPowerN() * nvCase.zones[i].getAir_density() * (Math.pow((gamma * phi), 2)) / Math.sqrt(Math.pow(e, 2) - 4 * d * f);
                                    }
                                    //<editor-fold defaultstate="collapsed" desc="Previous fan code">
                                    /**
                                     * ********************************not used
                                     * now******************************** if
                                     * (fanConnect[j][i] == 1) { if
                                     * (constantPfan != 0) { //JInchao Yuan
                                     * 02/26/2006: The code for constant
                                     * pressure head fan J[i][j] = 0.0;
                                     * //J[i][i]=1.0; //B[i] =0.0; flag = true;
                                     * } else { J[i][j] += -1.0 /
                                     * nvCase.flowpaths[j][i].slope; J[i][i] +=
                                     * 1.0 / nvCase.flowpaths[j][i].slope;
                                     *
                                     * B[i] += flowrate[j][i]; } //JInchao yuan
                                     * 02/26/2006///////the code for constant
                                     * pressure head fan///////// } else if
                                     * (fanConnect[j][i] == -1) { //JInchao yuan
                                     * 02/26/2006///////the code for constant
                                     * pressure head fan///////// if
                                     * (constantPfan != 0) { J[i][j] = 0.0; flag
                                     * = true; } //JInchao yuan
                                     * 02/26/2006///////the code for constant
                                     * pressure head fan///////// else { J[i][j]
                                     * += 1.0 / nvCase.flowpaths[j][i].slope;
                                     * J[i][i] += -1.0 /
                                     * nvCase.flowpaths[j][i].slope; B[i] +=
                                     * flowrate[j][i]; } } else //when
                                     * fanConnect[j][i] ==0
                                     * *********************************not used
                                     * now*******************************
                                     */
                                    //</editor-fold>
                                } else {
                                    if (Math.abs(delta_p[j][i]) >= 1.0E-10) {
                                        J[i][j] -= nvCase.flowpaths[j][i].getPowerN() * flowrate[j][i] / delta_p[j][i];
                                        J[i][i] += nvCase.flowpaths[j][i].getPowerN() * flowrate[j][i] / delta_p[j][i];
                                    } else { //FADE: Added laminar regime to avoid divergence of J when delta_p[j][i] -> zero. Laminar coefficient is in [m^3]
                                        J[i][j] -= nvCase.flowpaths[j][i].getPowerN() * nvCase.zones[i].getAir_density() / nvCase.zones[i].getAir_viscosity();
                                        J[i][i] += nvCase.flowpaths[j][i].getPowerN() * nvCase.zones[i].getAir_density() / nvCase.zones[i].getAir_viscosity();
                                    }
                                }
                                if (oneZone) {
                                    if (Math.abs(delta_p[j][i]) >= 1.0E-10) {
                                        J[i][j] -= nvCase.flowpaths[j][i].getPowerN() * flowrate[j][i] / delta_p[j][i];
                                        J[i][i] += nvCase.flowpaths[j][i].getPowerN() * flowrate[j][i] / delta_p[j][i];
                                    } else { //FADE: Added laminar regime to avoid divergence of J when delta_p[j][i] -> zero. Laminar coefficient is in [m^3]
                                        J[i][j] -= nvCase.flowpaths[j][i].getPowerN() * nvCase.zones[i].getAir_density() / nvCase.zones[i].getAir_viscosity();
                                        J[i][i] += nvCase.flowpaths[j][i].getPowerN() * nvCase.zones[i].getAir_density() / nvCase.zones[i].getAir_viscosity();
                                    }
                                    B[i] = flowrate[j][i] - flowrate[i][j];//this is F(xold)
                                } else {
                                    B[i] += flowrate[j][i];//this is F(xold)
                                }
                            }
                        }
                        //<editor-fold defaultstate="collapsed" desc="Previous fan code">
                        /**
                         * ********************************not used
                         * now******************************** if (constantPfan
                         * !=0) { if (flag==true) { for (int j = 0;
                         * j<zoneNumber; j++) J[i][j]=0.0; J[i][i]=1.0; B[i]
                         * =0.0; } } *********************************not used
                         * now*******************************
                         */
                        //</editor-fold>
                    }//end of for (int i=1; i<zoneNumber; i++)

                    //Solve [J][C] = [B] for the correction vector
                    int[] indx = new int[zoneNumber];
                    //<editor-fold defaultstate="collapsed" desc="Console output">
//                    System.out.println("In");
//                    System.out.println("J "+Arrays.deepToString(J));
//                    System.out.println("B "+Arrays.toString(B));
                    //</editor-fold>                    
                    LinearSolver.LUSolver(J, zoneNumber - 1, indx, B, D);
                    //<editor-fold defaultstate="collapsed" desc="Console output">
//                    System.out.println("Out");
//                    System.out.println("J "+Arrays.deepToString(J));
//                    System.out.println("B "+Arrays.toString(B));
                    //</editor-fold>

                    //Update pressures: [P]_{new} = [P]_{old} + [B]. Note that [B] = -[C] out of LinearSolver.LUSolver
                    double oldpressures[] = new double[zoneNumber + 1];
                    for (int i = 1; i < zoneNumber; i++) {
                        oldpressures[i] = pressures[i];
                        pressures[i] += B[i];
                        nvCase.zones[i].setPressure(pressures[i]);
                    }
                    for (int i = 0; i < zoneNumber; i++) {
                        for (int j = 0; j < zoneNumber; j++) {
                            if (nvCase.flowpaths[i][j] != null) {
                                if (connect[i][j] == true) {
                                    nvCase.flowpaths[i][j].calDelta_p(nvCase.zones[i], nvCase.zones[j], nvCase.zones[i].getIsIntZone(), oneZone, prevcw);
                                    delta_p[i][j] = nvCase.flowpaths[i][j].getDelta_p();

                                    //sdr_hulic - if pMod has run, add delta_pMod to delta_p
                                    if (pMod = true) {
                                        delta_p[i][j] += delta_pMod[i][j];
                                    }
                                }
                            }
                        }
                    }
//                    System.out.println("Pressure: " + Arrays.toString(pressures));
//                    System.out.println("Iteration 3 deltaP: " + Arrays.deepToString(delta_p));

                    //Update the flowrate
                    Calculation.calFlowrate(nvCase.zones, zoneNumber, connect, fanConnect, flowrate, nvCase.flowpaths, delta_p, fanSelect, gamma, oneZone);
//                    System.out.println("Iteration 3 flow: " + Arrays.deepToString(flowrate));
                    
                    //Control of the residual growth
                    checkdx = 0.0;
                    checkFx = 0.0;
                    tempFx = new double[zoneNumber];
                    if (oneZone) {
                        checkFx = Math.abs(flowrate[0][1] - flowrate[1][0]);
                    } else {
                        for (int i = 1; i < zoneNumber; i++) {
                            tempFx[i] = 0.0;
                            for (int j = 0; j < zoneNumber; j++) {
                                tempFx[i] += flowrate[j][i];
                            }
                            checkFx += Math.abs(tempFx[i]);
                        }
                    }
                    while ((checkFx > checkFxold && lambda > LAMBDATOL)) {
                        lambda /= 2.0;
                        checkdx = 0.0;
                        checkFx = 0.0;
                        for (int i = 0; i < zoneNumber; i++) {
                            pressures[i] = oldpressures[i] + lambda * B[i];
                            nvCase.zones[i].setPressure(pressures[i]);
                            checkdx += Math.abs(lambda * B[i]);
                        }
                        for (int i = 0; i < zoneNumber; i++) {
                            for (int j = 0; j < zoneNumber; j++) {
                                if (nvCase.flowpaths[i][j] != null) {
                                    if (connect[i][j] == true) {
                                        nvCase.flowpaths[i][j].calDelta_p(nvCase.zones[i], nvCase.zones[j], nvCase.zones[i].getIsIntZone(), oneZone, prevcw);
                                        delta_p[i][j] = nvCase.flowpaths[i][j].getDelta_p();
                                        //sdr_hulic - if pMod has run, add delta_pMod to delta_p
                                        if (pMod = true) {
                                            delta_p[i][j] += delta_pMod[i][j];
                                        }
                                    }
                                }
                            }
                        }
//                        System.out.println("Iteration 4 P: " + Arrays.toString(pressures));
//                        System.out.println("Iteration 4 deltaP: " + Arrays.deepToString(delta_p));
                        Calculation.calFlowrate(nvCase.zones, zoneNumber, connect, fanConnect, flowrate, nvCase.flowpaths, delta_p, fanSelect, gamma, oneZone); //CK: I guess this is calculating the new flowrate
//                        System.out.println("Iteration 4 flow: " + Arrays.deepToString(flowrate));
                        for (int i = 1; i < zoneNumber; i++) {
                            tempFx[i] = 0.0;
                            for (int j = 0; j < zoneNumber; j++) {
                                tempFx[i] += flowrate[j][i];
                            }
                            checkFx += Math.abs(tempFx[i]);
                        }
                    }//end of while (checkFx > checkFxold && lambda > LAMBDATOL)
                    checkFxold = checkFx;
//                    System.out.println(checkdx + " " + checkFx + " " +  newtoni);

                    //<editor-fold defaultstate="collapsed" desc="FADENEW: Console output">
//                    if (newtoni == MAXITER) {
//                        System.err.println("********************Convergence not achieved in Newton-Raphson method****************************");
////                        System.out.println("deltaP: " + Arrays.deepToString(delta_p));
////                        System.out.println("flow: " + Arrays.deepToString(flowrate));
//                    }
                    //</editor-fold>
                }//end of while( (checkdx > 1.0e-6 || checkFx >1.0E-6) && newtoni < MAXITER)

//                System.out.println("IIIIIIIIIIIIIIIIIIIIIIMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMDDDDDDDDDDDDDDDDDDDDDDDDDDOOOOOOOOOOOOOOOOOONEEEEEEEEEEEEEEEEENENENE");
                //Update air temperature
                
                nvCase.calAir_temp_trapezoidal(flowrate, Delta_time, prevAir_temp, prevTMass_temp, TMassConnection, TMassIntConnection, RELAXATION, HEAT_SOURCE_MULTIPLIER, RADIANT_ON, latitude, declination, solarHeatLoad_normal_hourX, solarHeatLoad_diffuse_hourX, timeofday, caseData.TMassCheck, oneZone); //used the second reloaded function
                temp_error = Calculation.calTemp_error(nvCase.zones, zoneNumber, prevAir_temp);
//                System.out.println("Airtemp "+Arrays.toString(prevAir_temp));               
                
                //<editor-fold defaultstate="collapsed" desc="Console output">
//                if (newtoni >= MAXITER) {
//                System.out.println("deltaP: " + Arrays.deepToString(delta_p));
//                System.out.println("flow: " + Arrays.deepToString(flowrate));
//                System.err.println("********************Convergence not achieved in Newton-Raphson method****************************");
//                }
                //</editor-fold>                
                //Update humidity ratio --- Not ideal way of doing this
                 /*FADE: Mass conservation is:
                 * Air:     d(m_air)_CV/dt = \dot{m_air}_in - \dot{m_air}_out \approxeq 0 -> \dot{m_air}_in = \dot{m_air}_out = \dot{m_air} (this is the quasi-steady state assumption)
                 * Vapor:   d(\omega * m_air)_CV/dt = \dot{omega * m_air}_in - \dot{omega * m_air}_out + \dot{m_vapor}_occupants
                 * Well mixed assumption + quasi-steady state: \dot{omega}_CV \approxeq \dot{omega}_out = \dot{omega}_in + \dot{m_vapor}_occupants / \dot{m_air}
                 * To approximate water vapor from occupants: For an office, sensible loads for lighting and equipment total 22.8 W/m^2. This means that
                 * only 30 - 22.8 = 7.2 W/m^2 are due to people. For a residence, they account for ~2.6 W/m^2. Average is 18.5% of total load.
                 * Now, each person doing light office work accounts for 70 W of sensible heat and 45 W of latent heat. Using the ratio of these heat loads
                 * I can find the equivalent latent load from the occupant sensible load input. Thus:
                 * \dot{m_vapor}_occupants = 45 * (occ_heat_load * 0.185) / 70 * floorarea / h_fg; where h_fg is the latent heat of vaporization
                 * NOTES: Vapor is not considered in the energy conservation equation. We might need to include this in CoolVent*/
                
                for (int reap = 1; reap <= 3; reap++) {
                    double infr = 0; //FADE: Keeps the total incoming flowrate in zone i
                    double inhr = 0; //FADE: Incoming water vapor mass to zone i
//                    System.out.println("Ambient HR " + nvCase.zones[0].getHumidityRatio());
                    for (int i = 1; i < zoneNumber; i++) { //FADE: Ambient zone not considered in mass conservation equation -> starting at i = 1
                        for (int j = 0; j < zoneNumber; j++) {
                            if (nvCase.flowpaths[i][j] != null && flowrate[j][i] > 0) { //FADE: Incoming flowrate
                                infr += flowrate[j][i];
                                inhr += nvCase.zones[j].getHumidityRatio() * flowrate[j][i];
                            }
                        }
                        if (infr != 0 && !nvCase.zones[i].getIsHumidityControl()) {
//                            System.out.println("Zone "+i+" prev omega "+nvCase.zones[i].getHumidityRatio()+" new omega "+(inhr / infr + (LATENT_HEAT_PERSON * nightfrac * caseData.zoneInputData[i].source.getOccupancyHeat_source() * PEOPLE_SENSIBLE_AVG_FRAC / SENSIBLE_HEAT_PERSON / H_FG / infr)));
                            nvCase.zones[i].setHumidityRatio(inhr / infr + (LATENT_HEAT_PERSON * nightfrac * caseData.zoneInputData[i].source.getOccupancyHeat_source() * PEOPLE_SENSIBLE_AVG_FRAC / SENSIBLE_HEAT_PERSON / H_FG / infr));
                        } else {
//                            System.out.println("Zone "+i+" is humidity controlled with HR "+caseData.getOmegaAC());
                            nvCase.zones[i].setHumidityRatio(caseData.getOmegaAC());
                        }
//                    System.out.println("Zone "+i+" HR: "+ nvCase.zones[i].getHumidityRatio());
                        infr = 0;
                        inhr = 0;
                    }
                }

                //Find zone with highest temperature
                maxTemp = 0;
                
                for (int i = 0; i < zoneNumber; i++) {
                    if (nvCase.zones[i].getIsIntZone() && nvCase.zones[i].getAir_temp() > maxTemp) {
                        maxTemp = nvCase.zones[i].getAir_temp();
                    }
                }
                //Change gamma according to user specifications
                if (T_user >= maxTemp && fanSelect && gamma <= 10 && !increase) { //FADE: As long as the maximum temperature in the zones is lower than the setpoint, increase gamma (decrease fan speed) until gamma = 10
                    gamma *= 1.1;
                    conte = true;
                } else { //If the maximum temp in the zones is higher than the set point, decrease gamma (increase fan speed) until gamma = 0.833
                    conte = false;
                    if (gamma >= 0.833 && fanSelect) {
                        gamma *= 0.9;
                    }
                }
                //System.out.println("maxTemp = "+maxTemp);
                //System.out.println("Gamma = "+gamma);
            } //end of while(conte)
            
            for (int i = 0; i < zoneNumber; i++) {
                prevAir_temp[i] = nvCase.zones[i].getAir_temp();
            }
            for (int i = 0; i < TMassNumber; i++) {
                prevTMass_temp[i] = nvCase.TMasses[i].getTemperature();
            }
            
//            System.out.println("Airtemp "+Arrays.toString(prevAir_temp));
            //<editor-fold defaultstate="collapsed" desc="Not used">
            /**
             * ************************FADE: This part of the code is not
             * used************************************************* for (int i
             * = 0; i < quesize - 1; i++) { for (int k = 0; k < zoneNumber; k++)
             * { temperaturedataque[i][k] = temperaturedataque[i + 1][k]; //push
             * in an elment to the queue and release one as well } } if
             * (doaverage != 0) { //FADE: doaverage is always zero!!! for (int k
             * = 1; k < zoneNumber; k++) { temperaturedataque[quesize - 1][k] =
             * prevAir_temp[k]; } double tStd[] = new double[zoneNumber]; double
             * tempupdate = 0.0;
             *
             * //calculate the varince of the data for (int k = 1; k <
             * zoneNumber; k++) { if (Math.abs(temperaturedataque[quesize -
             * 1][k] - temperaturedataque[quesize - 2][k]) > 1.0) { //print
             * warnings System.out.println("The calculation may be vibrating
             * since the std of the " + k + "th zone has reached " + tStd[k] +
             * "."); //FADE: tSTd is zero!!! //average out by time close
             * tempupdate = 0.0; for (int j = 1; j <= quesize; j++) { tempupdate
             * += temperaturedataque[j - 1][k] * j / ((quesize + 1) * quesize /
             * 2.0); } prevAir_temp[k] = tempupdate;
             * nvCase.zones[k].setAir_temp(tempupdate); } } }
             * *************************FADE: This part of the code is not
             * used************************************************
             */
            //</editor-fold>
            //FADENEW: With new daily simulation, printing the results every 3 time steps is too much. I think that it is better if we fix the time step at 15 minutes. So, let's just print the results every 1/4 hour.
            
            if (Math.IEEEremainder(controller1, 30) == 0 && (controller1 >= trainingsteps * istransient || PRINT_TRAINING_DAYS)) {//FADENEW: First line in output is at 12:00 am, and then every 15 minutes. (Every 15 minutes is 30. Every hour is 120)
                //if (Math.IEEEremainder(controller1, 3) == 1 && controller1 > trainingsteps * istransient) { FADENEW: This is no longer true: //HFC: time step 90s, write a line every 30min in simulation
//                for (int eleci = 1; eleci < zoneNumber; eleci++) {
//                    coolingelec += (0 - Math.min (0,(caseData.getTAC() - nvCase.zones[eleci].getAir_temp()))* COOLING_GAIN * nvCase.zones[eleci].getFloor_area()/caseData.getCOPAC()); //FADE: Changed from kWh to W (same format as other energy consumption data
////                    coolingelec += (0 - Math.min (0,(26 - nvCase.zones[eleci].getAir_temp()))* 20 * nvCase.zones[eleci].getFloor_area()/2000/3);//HFC: 2000 converts W to kWh, 3 is COP of chiller
////                    if ((elapsedtime % SECS_IN_DAY >= caseData.getOccupancyScheduleOn() * SECS_IN_HR) && (elapsedtime % SECS_IN_DAY < (caseData.getOccupancyScheduleOff()) * SECS_IN_HR)) {
////                        coolingelec += Math.max(0,(caseData.zoneInputData[eleci].getHumidityRatio() - 0.012)) * COOLING_GAIN / caseData.getCOPAC();
////                    }
////                      coolingelec += Math.max(0,(88*36*(Humidityratio-12)+88*80)*25/36)/2000/3;//HFC: current max. occupants is set at 88, others same as before
//                }
                for (int i = 0; i < zoneNumber; i++) {
                    bw.write(D_F.format(prevAir_temp[i]) + "\t"); // writing data
                }
                for (int i = 0; i < zoneNumber; i++) {
                    bw.write(D_F.format(nvCase.zones[i].getHumidityRatio() * 1000) + "\t"); // FADE: HR in g/kg
                }
                for (int i = 0; i < TMassNumber; i++) {
                    bw.write(D_F.format(prevTMass_temp[i]) + "\t");
                }
                bw.newLine();
                
                //FADEDELETE-----------------------------------------------------------------
                for (int i = 0; i < zoneNumber; i++) {
                    predbw.write(D_F.format(Tend[i]) + "\t");
                } 

                for (int i = 0; i < zoneNumber; i++) {
                    predbw.write(D_F.format(hybridTMassTemp[i]) + "\t");
                }
                predbw.newLine();
                //FADEDELETE-----------------------------------------------------------------
                
                for (int i = 0; i < zoneNumber; i++) {
                    for (int j = 0; j < zoneNumber; j++) {
                        if (connect[i][j] == true) {
                            abw.write(D_F.format(flowrate[i][j]) + "\t");
                        }
                    }
                }
                abw.newLine(); // writing data

                //sdr_hulic - write pressure outputs
                for (int i = 0; i < zoneNumber; i++) {
                    for (int j = 0; j < zoneNumber; j++) {
                        if (connect[i][j] == true) {
                            pbw.write(" " + delta_p[i][j] + " ");
                        }
                    }
                }
                pbw.newLine(); // writing data

                //sdr_hulic - write recommendation outputs
                for (int i = 0; i < zoneNumber; i++) {
                    for (int j = 0; j < zoneNumber; j++) {
                        if (connect[i][j] == true) {
                            rbw.write(" " + (Math.abs(flowrate[i][j]) / Math.sqrt(Math.abs(delta_p[i][j]))) + " ");
                        }
                    }
                }
                rbw.newLine(); // writing data

                //Fan power and efficiency
//                boolean flagOn = false;
//                for (int i = 0; i < zoneNumber; i++) {
//                    for (int j = 0; j < zoneNumber; j++) {
//                        if (nvCase.flowpaths[i][j] != null) {
////                            if (nvCase.zones[i].getIsIntZone() && nvCase.zones[j].getIsIntZone()) { //FADENEW: No longer used
////                                tempDiff = Math.abs(maxTemp - T_user);
////                            }
//                            if (fanConnect[i][j] == 1 && nvCase.flowpaths[i][j].isOn) {
//                                fan_Power = Math.abs(delta_p[i][j] * (flowrate[i][j] / nvCase.zones[i].getAir_density())); //FADE: Was divided by the density twice for unknown resons
//                                fan_Efficiency = nvCase.flowpaths[i][j].b2 * Math.pow(phi, 6) * Math.pow(gamma, 2) / Math.pow(sf, 2) * Math.pow((flowrate[i][j] / nvCase.zones[i].getAir_density()), 2) + nvCase.flowpaths[i][j].b1 * Math.pow(phi, 3) * gamma / sf * Math.abs((flowrate[i][j] / nvCase.zones[i].getAir_density()));
////                                totalEfficiency += fan_Efficiency; //FADENEW: No longer used
//                                fan_BHP = fan_Power / (fan_Efficiency * 1000); //Fan power is in kW
//                                flagOn = true;
//                            }
//                        }
//                    }
//                }
//                if (flagOn) {
//                    if (fanelec < 0 || fan_Efficiency < 0) {
//                        fbw.write(D_F.format(0) + "\t" + D_F.format(0) + "\t" + D_F.format(100) + "\t");
//                    } else {
                fbw.write(D_F.format(fan_Efficiency) + "\t" + D_F.format(fanelec) + "\t" + D_F.format(gamma) + "\t");
//                    }
//                } else {
//                    fbw.write(D_F.format(0) + "\t" + D_F.format(0) + "\t" + D_F.format(100) + "\t");
//                }
                for (int i = 0; i < zoneNumber; i++) {
                    if (nvCase.zones[i].getIsOccZone()) {
//                        System.out.println("i is "+i);
                        fbw.write(D_F.format(coolingelec[i] / caseData.getCOPAC()) + "\t" + D_F.format(heatingener[i]) + "\t"); //Cooling and heating energy are in kW
                    }
                }

                fbw.newLine();

                //*************************************STRATIFICATION!!!! ADDED BY MAMB************************************************************************
                // if (((istransient != 0) && (controller1 > trainingsteps) && (controller1 % (3600 / Delta_time) == 0)) || (istransient == 0)) { // every hour
                for (int z = 1; z < zoneNumber; z++) { //Remember that zones are counted from 0
                    int isvalidzone = 0;
                    //MAMB I think I can live without the following
                    //Hw = 0; //vertical location of the window through which the flow enters the room
                    //Hwc = 0; //dinstance from window to ceiling
                    //Aw = 0; // area of the window through which the flow enters the room
                    //Q = 0; // flowrate
                    //z_in = 0;
                    //profile = 0;
                    //ToutTin = 0; // from 0 to 1
                    //deltaT = 0;
                    //hinge_height = 0; // from 0 to 1
                    //sdr_hulic edited if statement to include ventilation shaft type
                    if (((bldgtype < 2 || bldgtype == 4) && ((nvCase.zones[z].wall.equals("N_wall")) || (nvCase.zones[z].wall.equals("S_wall")))) || ((bldgtype == 2) && (nvCase.zones[z].getIsIntZone()))) { //if it's an occupied zone in a non-single-sided case
                        isvalidzone = 1;
                        op = 0;
                        flagop = 0;
                        while ((flagop < 1) && (op < zoneNumber)) {
                            if (nvCase.flowpaths[op][z] != null) {
                                if (Math.signum(flowrate[op][z]) == 1 || oneZone) { // if the air is flowing from op to z (there is one and only once instance where this happens)
                                    flagop++;
                                    Hw = nvCase.flowpaths[op][z].getUpperOpeningHeight();
                                    Hwc = nvCase.zones[z].getFloorToCeilingHeight() - Hw;
                                    Aw = nvCase.flowpaths[op][z].getUpperOpeningArea();
                                    Q = Math.abs(flowrate[op][z]);
                                    z_in = op;
                                }
                            }
                            op++;
                        }// end of while
                    } //end of if
                    else if (bldgtype == 3) {
                        if (z == 1) {
                            isvalidzone = 1;
                        }
                        if (Math.signum(flowrate[0][1]) == 1) { // if the air is flowing from ambient to 1lower zone
                            Hw = nvCase.flowpaths[0][1].getUpperOpeningHeight();
                            Aw = nvCase.flowpaths[0][1].getUpperOpeningArea();
                        } else {
                            Hw = nvCase.flowpaths[2][0].getUpperOpeningHeight();
                            Aw = nvCase.flowpaths[2][0].getUpperOpeningArea();
                        }
                        z_in = 0;
                        Hwc = nvCase.zones[1].getFloorToCeilingHeight() - Hw;
                        Q = Math.abs(flowrate[0][1]);
                    }
                    if (isvalidzone == 1) {
                        ToutTin = nvCase.zones[z].getAir_temp() - nvCase.zones[z_in].getAir_temp();
                        /*VERY IMPORTANT NOTE: we shouldn't need to use the term Math.abs(ToutTin), since we'd
                         * expect Tout> Tin always (in the zones where there are heat gains).
                         * There's something strange in CoolVent's calculations that is
                         * allowing Tout<Tin. It only happens in very few instances, so I'm not over
                         * worried about the impact of using the absolute. But it IS definitely a patch that
                         * you, new CoolVent victim, might want to take a look at. MAMB 2012.
                         */
                        X_Hwc = Math.log10(9.81 * 1 / 300 * Math.abs(ToutTin) * Aw * (nvCase.zones[z].getFacadeWidth() / 2) / Math.pow(Q, 2) * Math.pow(Hwc, 3));
                        X_Hw = Math.log10(9.81 * 1 / 300 * Math.abs(ToutTin) * Aw * (nvCase.zones[z].getFacadeWidth() / 2) / Math.pow(Q, 2) * Math.pow(Hw, 3));

                        if (X_Hwc < 1.7) { // define temperature profile
                            hinge_height = 1; //dimensionless nvCase.zones[z].getFloorToCeilingHeight();
                        } else {
                            hinge_height = (0.866 - 0.1652 * X_Hwc); //dimensionless  * nvCase.zones[z].getFloorToCeilingHeight();
                            if (hinge_height < 0) {
                                hinge_height = 0;
                            }
                        }
                        deltaT = (-0.1493 * X_Hw + 0.5787); //* ToutTin;
                        if (deltaT < 0) {
                            deltaT = 0;
                        } else if (deltaT > 1) {
                            deltaT = 1;
                        }
                        deltaT = deltaT * ToutTin;                        
                        sbw.write(D_F.format(nvCase.zones[z_in].getAir_temp()) + "\t" + D_F.format(ToutTin) + "\t" + D_F.format(deltaT) + "\t" + D_F.format(hinge_height) + "\t"); // write stratification data + "\t" +  D_F.format(X_Hwc) + "\t" +  D_F.format(X_Hw)
                        //sbw.write(D_F.format(nvCase.zones[z_in].getAir_temp()) + "\t" + D_F.format(ToutTin) + "\t" + D_F.format(Q) + "\t" + D_F.format(Hwc) + "\t" + D_F.format(Hw) + "\t" + D_F.format(Aw) +"\t" + D_F.format(nvCase.zones[z].getFloor_area()) + "\t" + D_F.format(X_Hwc) + "\t" + D_F.format(X_Hw)  + "\t" + D_F.format(deltaT) + "\t" + D_F.format(hinge_height) + "\t");
                        //counter = counter + 1;
                    }

                } // end of for z = zones
                sbw.newLine();
               
            } // time count
            //*************************END OF STRATIFICATION (MAMB)************************************************************************
            
            //Update deltaP to account for new temperature
            for (int i = 0; i < zoneNumber; i++) {
                for (int j = 0; j < zoneNumber; j++) {
                    if (connect[i][j] == true) {
                        nvCase.flowpaths[i][j].calDelta_p(nvCase.zones[i], nvCase.zones[j], nvCase.zones[i].getIsIntZone(), oneZone, prevcw);
                        delta_p[i][j] = nvCase.flowpaths[i][j].getDelta_p();                        
                        //sdr_hulic - if pMod has run, add delta_pMod to delta_p
                        if (pMod = true) {
                            delta_p[i][j] += delta_pMod[i][j];                           
                        }
                    }
                }
            }
            //System.out.println("Iteration 5 deltaP: "+Arrays.deepToString(delta_p));
            //CK edits: now that we have corrected the flowrate for this step, we can calculate pollutant concentration
            for (int i = 0;i <zoneNumber; i++) {
            //zoneConcs_prev[i] = nvCase.zones[i].getCont(); //CK edits-change later
            for(int j = 0; j<zoneNumber;j++)
            {
                if(i == 0 && j==0) { masses_th[i][j] = outdoorMass; }
                if((i>0) && (j>0) && (j==i))
                {
                    masses_th[i][j] = nvCase.zones[i].getAir_density()*nvCase.zones[i].getVolume();
                }    
                else if((i>0) && (j>0) && (j!=i))
                {
                    masses_th[i][j] = 0;
                }
                else if((i==0)&&(j==0))
                {
                    masses_th[i][j] = outdoorMass;
                }
            //gen_rem_th[i] = nvCase.zones[i].getGen() - nvCase.zones[i].getRem(); //CK EDIT: Change later to vouch for time-varying gen/rem coefficients
                gen_rem_th[i] = 0.0 + filters[i].removal(nvCase.zones[i].getCont());
            }
            }//CK edits (2015): Before flowrate is updated, calculate the pollutant concentrations
            
            
            
                
            flows_th = flowrateConvert();
            
            LUDcmp L = new LUDcmp();
            L.populate(zoneNumber);
            outdoorMass = 1000000.0*nvCase.zones[0].getAir_density(); //CK edit: arbitrary volume*air density            
            zoneConcs_prev[0] = outdoorConc;
            zoneConcs_new[0] = outdoorConc;
            masses_t[0][0] = outdoorMass;
            masses_th[0][0] = outdoorMass;
            for (int i = 1; i<zoneNumber;i++)
             {
                zoneConcs_prev[i] = nvCase.zones[i].getCont(); //update zoneConcs for this iteration                          
                masses_th[i][i] = nvCase.zones[i].getVolume()*nvCase.zones[i].getAir_density(); //update masses for this time step
             }
            
            Matrix newconcs = L.LUDcmpCal(flows_t, flows_th, zoneConcs_prev, masses_t, masses_th, gen_rem_t, gen_rem_th, Delta_time, outdoorConc); //Calculating new zone concentrations
            /*for (int m = 0;m<zoneNumber; m++)
            {
                for (int n = 0; n<zoneNumber; n++)
                {
                    System.out.println("Window Filter ["+m+"]["+n+"] :"+wfilters[m][n].getE());
                }
            }*/
            newconcs_tmp = newconcs.getArray();
                //populating new concentrations            
            zoneConcs_prev[0] = outdoorConc;
            zoneConcs_new[0] = outdoorConc;
            nvCase.zones[0].setCont(outdoorConc);
            concw.write(controller1+"\t");            
             for (int i = 1; i<zoneNumber;i++)
             {
                nvCase.zones[i].setCont(newconcs_tmp[i][0]); //update zone concentrations with new values at end of time step
                zoneConcs_new[i] = newconcs_tmp[i][0]; //assign new values to zoneConcs_new (although this step is not necessary)
                zoneConcs_prev[i] = zoneConcs_new[i]; //update zoneConcs for next iteration                       
              //  System.out.println("Zone "+i+" concentration = "+nvCase.zones[i].getCont());
             }             
             for(int i = 0;i<zoneNumber; i++)
             {
                  //concw.write(D_F.format((nvCase.zones[i].getCont()*1000000))+" \t"); CK EDIT: This was just for testing with bogus initialized values
                 concw.write(D_F.format((nvCase.zones[i].getCont()                                                                                                                                      )) + " \t");
             }
             concw.newLine();
             //CK edits end here             
             //t_axis[iterator] = controller1; //CK Edit for plotting
             //iterator++; //CK Edit for plotting
            controller1++; // to enhance efficiency            
        } //end of Iterations
        
//        if (surge) { //Fan operation is unstable
//            fbw.write("Surge");
//            fbw.newLine();
//        }
        concw.close(); //CK Edit
        conch.close(); //CK EDIT
        temph.close(); //CK EDIT
        bw.close();
        abw.close();
        br.close();
        sbw.close();
        fbw.close();
        pbw.close(); //sdr_hulic
        rbw.close(); //sdr_hulic
        
        predbw.close();
    }

    public double getFlowrate_error() {
        return flowrate_error;
    }

    public double getTemp_error() {
        return temp_error;
    }

    public int getController() {
        return controller1;
    }

    public Zone[] getCaseZone() {
        return nvCase.zones;
    }

    public Flowpath[][] getFlowpath() {
        return nvCase.flowpaths;
    }

    public double[][] getFlowrate() {
        return flowrate;
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public int getFlowpathNumber() {
        return flowpathNumber;
    }

//    public double getTempDiff() { //FADENW: No longer used
//        return tempDiff;
//    }
//
//    public boolean getSurge() { //FADENW: No longer used
//        return surge;
//    }
//
//    public double getTotalEfficiency() { //FADENW: No longer used
//        return totalEfficiency;
//    }
    private void FormNetwork(DataPool caseData, DefineNetwork nvCase, int zoneNumber, int flowpathNumber, int TMassNumber, boolean[][] connect, int[][] fanConnect, boolean[][] TMassConnection, boolean[][] TMassIntConnection, double[] prevAir_temp, double[] prevTMass_temp) {
        //Define nvCase's TMass
        for (int i = 0; i < TMassNumber; i++) {
            nvCase.TMasses[i] = new TMass(caseData.TMassInputData[i].getVolume(), caseData.TMassInputData[i].getArea(), caseData.TMassInputData[i].getPerimeter(), caseData.TMassInputData[i].getDensity(), caseData.TMassInputData[i].getk()); //FADENEW
            nvCase.TMasses[i].setCp(caseData.TMassInputData[i].getCp());
            nvCase.TMasses[i].setType(caseData.TMassInputData[i].getType());
            //nvCase.TMasses[i].setH(caseData.TMassInputData[i].getH());
            nvCase.TMasses[i].setTemperature(caseData.TMassInputData[i].getTemperature());
            nvCase.TMasses[i].setIsAdiabatic(caseData.TMassInputData[i].getIsAdiabatic());
            nvCase.TMasses[i].calMass();
            //nvCase.TMasses[i].calHA();
            nvCase.TMasses[i].setk(caseData.TMassInputData[i].getk()); //FADE: Seems that we already did this
            nvCase.TMasses[i].setAbsorbsSolar(caseData.TMassInputData[i].getAbsorbsSolar());
        }
        //Copy TMass connections from caseData to Maincalculation class arrays
        for (int i = 0; i < TMassNumber; i++) {
            System.arraycopy(caseData.TMassConnection[i], 0, TMassConnection[i], 0, zoneNumber); //FADE: Changed to System.arraycopy: public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
        }
        for (int i = 0; i < TMassNumber; i++) {
            System.arraycopy(caseData.TMassIntConnection[i], 0, TMassIntConnection[i], 0, TMassNumber); //FADE: Changed to System.arraycopy
        }
        //Define the zones in nvCase. nvCase is a DefineNetwork
        for (int i = 0; i < zoneNumber; i++) {
            nvCase.zones[i] = new Zone(caseData.zoneInputData[i].getVolume(), caseData.zoneInputData[i].getFloor_area(), caseData.zoneInputData[i].getElevation());
            nvCase.zones[i].setAir_temp(caseData.zoneInputData[i].getAir_temp());
            nvCase.zones[i].calAir_density();
            nvCase.zones[i].setIsIntZone(caseData.zoneInputData[i].getIsIntZone());
            nvCase.zones[i].setIsOccZone(caseData.zoneInputData[i].getIsOccZone()); //MAMB 2011
            nvCase.zones[i].source.setOccupancyHeat_source(caseData.zoneInputData[i].source.getOccupancyHeat_source());
            nvCase.zones[i].source.setAirflow_source(caseData.zoneInputData[i].source.getAirflow_source());
            nvCase.zones[i].source.setAirflow_temp(caseData.zoneInputData[i].source.getAirflow_temp());
            nvCase.zones[i].setWall(caseData.zoneInputData[i].getWall());
            nvCase.zones[i].setBldgOrientation(caseData.zoneInputData[i].getBldgOrientation());
            nvCase.zones[i].setSurfaceAzimuthAngle(caseData.zoneInputData[i].getSurfaceAzimuthAngle());
            nvCase.zones[i].setSideGlazingArea(caseData.zoneInputData[i].getSideGlazingArea());
            nvCase.zones[i].ceilingIndex = caseData.zoneInputData[i].ceilingIndex;
            nvCase.zones[i].ceilingIndex1 = caseData.zoneInputData[i].ceilingIndex1;
            nvCase.zones[i].floorIndex = caseData.zoneInputData[i].floorIndex;
            nvCase.zones[i].floorIndex1 = caseData.zoneInputData[i].floorIndex1;
            nvCase.zones[i].setFloorToCeilingHeight(caseData.zoneInputData[i].getFloorToCeilingHeight()); //MAMB Dec 2011
            nvCase.zones[i].setFacadeWidth(caseData.zoneInputData[i].getFacadeWidth());
            nvCase.zones[i].a_terrain = caseData.a_terrain;
            nvCase.zones[i].b_terrain = caseData.b_terrain;
        }
        //Define nvCase's flowpaths
        int prevnumber1 = 0;
        int prevnumber2 = 0;
        for (int i = 0; i < flowpathNumber; i++) {
            int number1 = caseData.openingInputData[i].getConnectionZoneNum1();
            int number2 = caseData.openingInputData[i].getConnectionZoneNum2();
            if (number1 == prevnumber1 && number2 == prevnumber2) { //FADE: Same flowpath for different openings
//                System.err.println("This is a one zone X-ventilation building. prevcw= "+prevcw);
                caseData.openingInputData[i].setCw(caseData.openingInputData[i].getCw());
                oneZone = true;
            } else {
                prevnumber1 = number1;
                prevnumber2 = number2;
                prevcw = caseData.openingInputData[i].getCw();
            }
            boolean isFan = caseData.openingInputData[i].isFan;
            nvCase.flowpaths[number1][number2] = new Flowpath(caseData.openingInputData[i].getHeight(), caseData.openingInputData[i].getWidth(), caseData.openingInputData[i].getElevation(), caseData.openingInputData[i].getIsLargeOpen(), caseData.openingInputData[i].getWind_v(), caseData.openingInputData[i].getCw(), caseData.openingInputData[i].openingType);
            if (isFan == true) { //FADE modified isFan
                nvCase.flowpaths[number1][number2].turnintoFan(caseData.openingInputData[i].a2, caseData.openingInputData[i].a1, caseData.openingInputData[i].a0, caseData.openingInputData[i].fanD, caseData.openingInputData[i].gamma, caseData.openingInputData[i].b2, caseData.openingInputData[i].b1, caseData.openingInputData[i].m_eta, caseData.openingInputData[i].fanType);
                fanConnect[number1][number2] = -1; //FADE: the fan blows from zone number2 to zone number1 in the roof. This means from intZone to ambientZone
            }
            nvCase.flowpaths[number1][number2].setCd(caseData.openingInputData[i].getCd());
            nvCase.flowpaths[number1][number2].setPowerN(caseData.openingInputData[i].getPowerN());
            nvCase.flowpaths[number1][number2].setConnectionZoneNums(number1, number2);
            connect[number1][number2] = true;
            nvCase.flowpaths[number1][number2].setUpperOpeningArea(caseData.openingInputData[i].getUpperOpeningArea()); //MAMB 2011 Dec
            nvCase.flowpaths[number1][number2].setUpperOpeningHeight(caseData.openingInputData[i].getUpperOpeningHeight()); //MAMB 2011 Dec
            nvCase.flowpaths[number1][number2].setWindowHeight(caseData.openingInputData[i].getWindowHeight()); //MAMB 2011 Dec
            nvCase.flowpaths[number1][number2].setDoubleOpeningCheck(caseData.openingInputData[i].getDoubleOpeningCheck()); //MAMB 2011 Dec
            nvCase.flowpaths[number1][number2].setOpeningFraction(caseData.openingInputData[i].getOpeningFraction()); //FADENEW
            nvCase.flowpaths[number1][number2].setOpeningAzimuth(caseData.openingInputData[i].getOpeningAzimuth()); //SUPERFADE;
            nvCase.flowpaths[number1][number2].setSurfaceSideRatio(caseData.openingInputData[i].getSurfaceSideRatio()); //SUPERFADE;
            nvCase.flowpaths[number1][number2].setOpeningy(caseData.openingInputData[i].getOpeningy()); //SUPERFADE;
            nvCase.flowpaths[number1][number2].setOpeningx(caseData.openingInputData[i].getOpeningx()); //SUPERFADE;
            nvCase.flowpaths[number2][number1] = new Flowpath(caseData.openingInputData[i].getHeight(), caseData.openingInputData[i].getWidth(), caseData.openingInputData[i].getElevation(), caseData.openingInputData[i].getIsLargeOpen(), caseData.openingInputData[i].getWind_v(), caseData.openingInputData[i].getCw(), caseData.openingInputData[i].openingType);
            if (isFan == true) { //FADE modified isFan. This is now an extraction fan
                nvCase.flowpaths[number2][number1].turnintoFan(caseData.openingInputData[i].a2, caseData.openingInputData[i].a1, caseData.openingInputData[i].a0, caseData.openingInputData[i].fanD, caseData.openingInputData[i].gamma, caseData.openingInputData[i].b2, caseData.openingInputData[i].b1, caseData.openingInputData[i].m_eta, caseData.openingInputData[i].fanType);
                fanConnect[number2][number1] = 1; //the fan blows from zone number2 to zone number1 in the roof. This means from intZone to ambientZone
            }
            nvCase.flowpaths[number2][number1].setCd(caseData.openingInputData[i].getCd());
            nvCase.flowpaths[number2][number1].setPowerN(caseData.openingInputData[i].getPowerN());
            nvCase.flowpaths[number2][number1].setConnectionZoneNums(number2, number1);
            connect[number2][number1] = true;
            nvCase.flowpaths[number2][number1].setUpperOpeningArea(caseData.openingInputData[i].getUpperOpeningArea()); //MAMB 2011 Dec
            nvCase.flowpaths[number2][number1].setUpperOpeningHeight(caseData.openingInputData[i].getUpperOpeningHeight()); //MAMB 2011 Dec
            nvCase.flowpaths[number2][number1].setWindowHeight(caseData.openingInputData[i].getWindowHeight()); //MAMB 2011 Dec
            nvCase.flowpaths[number2][number1].setDoubleOpeningCheck(caseData.openingInputData[i].getDoubleOpeningCheck()); //MAMB 2011 Dec
            nvCase.flowpaths[number2][number1].setOpeningFraction(caseData.openingInputData[i].getOpeningFraction()); //FADENEW
            nvCase.flowpaths[number2][number1].setOpeningAzimuth(caseData.openingInputData[i].getOpeningAzimuth()); //SUPERFADE;
            nvCase.flowpaths[number2][number1].setSurfaceSideRatio(caseData.openingInputData[i].getSurfaceSideRatio()); //SUPERFADE;
            nvCase.flowpaths[number2][number1].setOpeningy(caseData.openingInputData[i].getOpeningy()); //SUPERFADE;
            nvCase.flowpaths[number2][number1].setOpeningx(caseData.openingInputData[i].getOpeningx()); //SUPERFADE;
        }
        //Jinchao Yuan 03/18/2005: Initializing the air and thermal mass temperatures
        for (int i = 0; i < zoneNumber; i++) {
            prevAir_temp[i] = nvCase.zones[i].getAir_temp();
        }
        for (int i = 0; i < TMassNumber; i++) {
            prevTMass_temp[i] = nvCase.TMasses[i].getTemperature();
        }
    }

    private void loadWindVelocity(DefineNetwork nvCase, boolean[][] connect, int zoneNumber, double windVelocity, double h_meteo) {
        double H = 0;
        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                if (connect[i][j] == true) {
                    //nvCase.flowpaths[i][j].setWind_v(windVelocity);
                    if (nvCase.flowpaths[i][j].elevation > H) {
                        H = nvCase.flowpaths[i][j].elevation; //FADE: Need a better way to get H!!!
                    }
                }
            }
        }
        //adjusting velocity to account for terrain type and opening height
        double a_meteo = 1; //Assuming flat terrain with some isolated obstacles.
        double b_meteo = 0.15; //Assuming flat terrain with some isolated obstacles.

        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                if (connect[i][j] == true) {
                    double windVel = Math.pow(10 / h_meteo, b_meteo) * Math.pow((H / 10), nvCase.zones[i].b_terrain) * (nvCase.zones[i].a_terrain / a_meteo) * windVelocity;
                    nvCase.flowpaths[i][j].setWind_v(windVel);
                    //System.out.println("Normal " + nvCase.flowpaths[i][j].getWind_v()+" Adjusted "+windVel);
                }
            }
        }
    }

    // sdr_hulic - new method to calculate frictional loss in section of shaft
    private double calcFrictionDP(double area, double perimeter, double length, double flowrate) {
        double velocity = Math.abs((flowrate / area));
        double hDiameter = 4 * area / perimeter;
        double DP = 0.5 * Math.pow((1.8 * Math.log10((6.9 * kViscos) / (hDiameter * velocity) + Math.pow(roughness / (3.7 * hDiameter), 1.11))), -2) * length * density * Math.pow(velocity, 2) / hDiameter;
        if (flowrate < 0) {
            DP *= -1;
        }
        return DP;
    }

    private double calcSideEjectorDP(double stArea, double sArea, double stFlowrate, double sFlowrate) {
        //intiate variables
        double coefA = 0;
        double sVelocity = sFlowrate / sArea;
        double comFlowrate = stFlowrate + sFlowrate;
        double comVelocity = comFlowrate / stArea;
        double DP = 0;
        double DPSign = 1; // by default, the sign of DP is + 

        // if the shaft flowrate is negativce (meaning flow down the shaft), then don't regard ejector effect
        if (stFlowrate < 0) {
            System.out.println("VERY VERY WRONG ");

            //stVelocity = Math.abs(stVelocity);
            //stFlowrate = Math.abs(stFlowrate);
            DP = 0;
            return DP;
        }

        //determine value of A from Table 7-1 in Idelchik
        if (sArea / stArea <= 0.35) {
            coefA = 1;
        }

        if (sArea / stArea > 0.35) {
            if (sFlowrate / stFlowrate <= 0.4) {
                coefA = 0.9 * (1 - sFlowrate / stFlowrate);
            } else {
                coefA = 0.55;
            }
        }
        DP = DPSign * 0.5 * density * Math.pow(sVelocity, 2) / Math.pow(sVelocity / comVelocity, 2) * (coefA * (1 + Math.pow(sVelocity / comVelocity, 2) - 2 * Math.pow(1 - sFlowrate / comFlowrate, 2)));
        return DP;
    }

    //FADENEW#########################################################################
    private void loadCw(DataPool caseData, DefineNetwork nvCase, boolean[][] connect, int zoneNumber, double wind_v, double windDirection) {
        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                if (connect[i][j] == true) {
                    double Cw_value = caseData.calcCw(nvCase.flowpaths[i][j].openingType, nvCase.flowpaths[i][j].getOpeningAzimuth(), caseData.bldgOrientation, windDirection, wind_v, nvCase.flowpaths[i][j].getSurfaceSideRatio(), caseData.getDisNS(), caseData.getDisWE(), nvCase.flowpaths[i][j].getOpeningy(), (caseData.getBuildingHeight() - caseData.getRoofHeight()), nvCase.flowpaths[i][j].getOpeningx());
                    nvCase.flowpaths[i][j].setCw(Cw_value);
                }
            }
        }
    }
    
    private double[][] flowrateConvert() //CK EDIT: Method to convert flowrates calculated by CoolVent to 'K' matrix in the mass balance Matrix manipulations
    {
        double[][] fl_t = new double[zoneNumber][zoneNumber];
        for (int i = 0;i<zoneNumber;i++)
        {
            for(int j = 0;j<zoneNumber;j++) {fl_t[i][j] = 0.0;}
        }
        for (int i =0;i<zoneNumber;i++) 
        {
            //double flowsum = 0;
            for (int j = 0;j<zoneNumber;j++)
            {
                if((i!=j)&& (flowrate[i][j] < 0.0)) //outgoing flowrate from i to j
                {
                    fl_t[i][i] += flowrate[i][j];//*nvCase.zones[i].getAir_density(); //in the equivalent 'K' matrix, this outward flow from i->j needs to be accounted for in the i,i index
                    fl_t[j][i] = (-1.0)*flowrate[i][j];//*nvCase.zones[i].getAir_density(); //the outward flow from i to j; i->j counts as a +ve inward flow to zone j in 'K' matrix i.e. at the index j,i     
                }
                else if((i!=j) && (flowrate[i][j]>=0.0)) //incoming flow from j to i
                {
                    fl_t[i][j] = flowrate[i][j];//*nvCase.zones[j].getAir_density(); //the inward flow to i from j, j->i is represented in the 'K' matrix at index i,j     
                    //fl_t[j][j]+= (-1.0)*flowrate[i][j]*nvCase.zones[j].getAir_density();//the outward flow from j needs to be accounted for in 'K' matrix
                }                
            }
            //fl_t[i][i] = flowsum;
        }
        return fl_t;
    }
    //end of CK edits
    
    
    //CK Edit: method to read pollution concentration data at 'k' second intervals from a file
    double [] read(String FileName) throws IOException
    {
        
        BufferedReader br = new BufferedReader(new FileReader(FileName));
        br.readLine(); //City Name
        br.readLine(); //Month Name        
        String C_parameters = br.readLine(); //interval (in seconds) for outdoor reading
        StringTokenizer st_c = new StringTokenizer(C_parameters);        
        int k = Integer.parseInt(st_c.nextToken()); //defining interval length
        double [] concData = new double[24*60*60/k]; //if we have 24-hr data at a k-second interval, then we need to define a 24*60*60/k interval array to hold this data
        int readcounter = 0;
        while(C_parameters!=null)
        {
            concData[readcounter] = Double.parseDouble(st_c.nextToken());
            readcounter++;
            C_parameters = br.readLine();
            st_c = new StringTokenizer(C_parameters);
        }
        return concData;
    }
    
    //CK Edit: Quick method to convert kg/kg pollutant value to micrograms/meter^3
    double kg_convert(double val, int index)
    {
        return val*(1E9)/(1/nvCase.zones[index].getAir_density());
    }    
    
    //CK EDIT: Just a quick method to populate filters with initial values for testing
    double kg_invert(double val, int index)
    {
        return (val/(1E9))*(1/nvCase.zones[index].getAir_density());
    }
    void populateFilters(int zonenumber) throws IOException
    {
        BufferedWriter brf = new BufferedWriter(new FileWriter(FILT_FILE_NAME));
        for(int i = 0;i <zonenumber; i++)
        {
            brf.write(i+"\t"+"0.95"+"\t"+"0.5");            //CK EDIT: the numbers are: zone number; efficiency; airflow
            brf.newLine();
        }
        brf.close();
    }
    
    void populateWindowFilters(int zonenumber) throws IOException //CK EDIT: JUST A quick method for populating window filter data with initial values. FOR TESTING ONLY. DELETE LATER
    {
        BufferedWriter brf = new BufferedWriter(new FileWriter(WFILT_FILE_NAME));
        for (int i = 0 ;i<zonenumber; i++)
        {
            for (int j = 0 ;j<zonenumber;j++)
            {
                if(i == j)
                {
                    brf.write("0.0"); //makes no sense to have filter efficiency within a zone
                }
                else if(((i==0)||(j==0))&&(i!=j))
                {
                    brf.write("0.97"); //populating efficiency of filter. Assuming its bidirectional. We're assuming negligible pressure drop across all filters right now
                }
                else {brf.write("0.0");}
                if(j<zonenumber-1) {brf.write("\t");}
                
                
            }
            brf.newLine();
        }
        brf.close();
    }
    
//    private void loadCw(DataPool caseData, DefineNetwork nvCase, boolean[][] connect, int zoneNumber, double wind_v, double windDirection, double openingFraction) {
//        for (int i = 0; i < zoneNumber; i++) {
//            for (int j = 0; j < zoneNumber; j++) {
//                if (connect[i][j] == true) {
//                    double Cw_value = caseData.calcCw(nvCase.flowpaths[i][j].openingType, caseData.bldgOrientation, windDirection, wind_v, caseData.getDisNS(), caseData.getDisWE(), nvCase.flowpaths[i][j].getElevation(), (caseData.getBuildingHeight()-caseData.getRoofHeight()));
//                    nvCase.flowpaths[i][j].setCw(Cw_value);
//
//                    if (nvCase.flowpaths[i][j].openingType.equals("S_wall") || nvCase.flowpaths[i][j].openingType.equals("N_wall") || nvCase.flowpaths[i][j].openingType.equals("E_wall") || nvCase.flowpaths[i][j].openingType.equals("W_wall")) {
//                        nvCase.flowpaths[i][j].openingFraction = openingFraction;
//                    }
//                }
//            }
//        }
//    }
    //FADENEW#########################################################################
    //CK EDIT: 2015
    /* I'm implementing addition methods for the pollution transport and balance equations. These are based on the methods available in CONTAM. 
    Essentially, we have q = M.x, where M is a 2-d matrix of masses (of air) in each zone, x is a column matrix of concentrations in each zone and therefor, q = mass of pollutants in each zone
    We have q' = dq/dt = K.x + e, where K is a 2-d matrix of flowrates in/out of each zone. The total outward flowrate from a zone 'i' is represented in K[i][i] (as a negative value). 
    Flows from zone j to i should be represented in K[i][j] as a positive value (where applicable). If two zones are not connected, then K[i][j] = 0
    We proceed to solve for q[t+h] = q[t] + (1-theta)*h*q'[t] + theta*h*q'[t+h] in the implicit form. Here, theta represents the selection of Dt and h is the timestep dt; theta is 0.5 by default
    In its final form, we have M.x[t+h] = M.x[t] + (1-theta)*h*(K.x+e)[t] + theta*h*(K.x+e)[t+h]; All quantities are known except x[t+h], which we solve for accordingly in the form A.x = b;
    */
}
