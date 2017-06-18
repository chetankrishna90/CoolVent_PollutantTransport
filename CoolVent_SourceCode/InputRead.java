
import java.lang.Math.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * InputRead.java Reads the input from Input.txt and calls Maincalculation
 *
 * Created 2004 Major cleanup by MAMB 6/16/2009 Major cleanup by FADE 3/12
 *
 * @author
 * @version 3/27/2012
 */
public class InputRead {
    //<editor-fold defaultstate="collapsed" desc="Class variables">

    private static final int TIME_STEP_SIZE = 30; //seconds
    private static final double FOURIER_CRIT = 0.5; //FADE: Fo < FOURIER_CRIT
    private static final double SAFETY_F = 1.2; //FADE: Safety factor for the Fourier criteria
    private static final int SEGS_IN_DAY = 24 * 3600;
    private static final int[] DAYS_IN_MONTH = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final boolean SUPER_COOLVENT = false; //SUPERFADE: Simulate any network

    //</editor-fold>
    public static void main(String[] args) throws IOException {
        //<editor-fold defaultstate="collapsed" desc="Local variables">
        int numShafts = 1; //sdr_hulic added parameter for number of shafts
        double shaft1Width = 5.0;
        double shaft1Length = 30.0;
        double shaft1Height = 5.0;//height shaft1 rises above roof
        double shaft2Width = 5.0;
        double shaft2Length = 5.0;
        double shaft2Height = 5.0;
        double shaft1Opening = 2.0; //opening area from floor to shaft
        double shaft2Opening = 2.0;
        double shaft1Exhaust = 2.0;
        double shaft2Exhaust = 2.0;

        //sdr_hulic customizable Cd values
        double CdA = 0;
        double CdB = 0;
        double CdC = 0;
        double CdD = 0;
//        double CdE = 0;
//        double CdF = 0;
//        double CdG = 0;
//        double cdCheck2 = 0;
//        double cdgndCheck2 = 0;
        int caseNum = 1; //FADE: Total number of cases
        int caseCount = 0; //FADE: counter for cases. Starts at 0 because first index in array is 0
        int zoneNum = 10;
        int openingNum = 4;
        double ambienttemperature = 20;
        double a_terrain = 1;
        double b_terrain = 0.15;
        double circumBuildingH = 0.5;
        double SCF = 1;
        double windvelocity = 2.0; //m/s
        double bldgOrientation = 0.0; //degrees
        double windDirect = 22.5; //degrees
        double dis_W_E = 5.0;
        double dis_N_S = 5.0;
        double buildingHeight = 3 * 3.0 + 3.0;
        int bldgtype = 0; //0 for luton type; 1 for chimney type; 2 for cross type; 3 for single sided ventilation; 4 for ventilation shaft - sdr_hulic-add new comment
        //Luton + Chimney Type Data
        int floornumber = 10;
        double floorwidth = 10.0;
        double atriumwidth = 5.0;
        double floorlength = 10.0;
        double floorheight = 3.0;
        double roofheight = 3.0; //sdr changed it to 3 from 0
        double floortoceilingheight = 0.0;
        double sidewindowsize = 1.0; //m2
        double topwindowsize = 3.0; //m2
        double sideglazingarea = 6.0; //m2
        double windowheight = 0; //This is not the height of the window itself, but the distance from the floor to the center of the window
        double deltaH;
        int doubleOpeningsCheck;
        double intarea = 10.0; //FADE: This is the internal area to calculate the internal resistance to airflow
        double intareaheight = 0;
        double cvinternalwindowheight = 0;
//        double cdCheck = 0; //FADE: This is the discharge coefficient
//        double intgndarea = 0;
//        double intgndareaheight = 0;
//        double cdgndCheck = 0;
        int fanCheck = 0; //FADE: Fan variable
        double a2 = 0; //FADE: Fan variable for performance
        double a1 = 0; //FADE: Fan variable for performance
        double a0 = 0; //FADE: Fan variable for performance
        double fanD = 0; //FADE: Fan variable
        double gamma = 0; //FADE: Fan variable
        double b2 = 0; //FADE: Fan variable for efficiency
        double b1 = 0; //FADE: Fan variable for efficiency
        double m_eta = 0.9; //FADE: Fan (motor) variable for efficiency
        double T_fan = 26; //FADE: Temperature at which fan is turned on
        double omega_fan = 12; //FADE: Humidity ratio at which fan is turned on
        int ACCheck = 0;
        double T_AC = 28; //FADE: Temperature at which AC is turned on
        double omega_AC = 14; //FADE: Humidity ratio at which AC is turned on
        double COP_AC = 3; //FADE: Chiller COP
        int controlCheck = 0; //FADENEW: 1 for individula control of windows and ac in each zone
        String fanType = "Light";
        //Cross Ventliation Type Data
        int cvsection = 3;
        double cvinternalwindowsize = 1.5;
        //Single-sided Type Data
        int sslayer = 2;
        double lowerwindowheight = 0;
        double lowerwindowsize = 0;
        double upperwindowheight = 2.5;
        double upperwindowsize = 0.6;
        double stratwindowheight = 0; //Height of the window that is meaningful to stratifiaction MAMB
        double upperwindowheightW = 0; //FADE added. We need to check all these window specifications we have!
        double stratwindowsize = 0; //Size of the window that is meaningful to stratifiaction MAMB
        //Additional Data
        double initialzonetemperature = 22;
        double OccupancyHeatLoads = 30; //W/m2
        double persondensity = 0.1;
        int occupancyscheduleON = 8;
        int occupancyscheduleOFF = 18;
        double offpeakloadfraction = 0.2;
        int TMassCheck = 1;
        double TMassThick = 1.0;
        double TMassAreaFraction = 1.0;
        double TMassk = 1.0;
        int adiabaticWalls = 1;
        double hfloor = 12.0;
        double hceiling = 12.0;
        int transimu = 0;
        //int multisimu =  0; //FADENEW: Multisim is gone - replaced by daily sim
        int monthIni = 4;
        int monthEnd = 9;
        int dailysim = 0; //FADENEW: 0 for monthly simulation; 1 for daily simulation
        int nightcoolCheck = 0; //nightcooling strategy
        double nightcoolOff = 7; //time of day (hour) at which windows close
        double nightcoolOn = 19; //time of day (hour) at which windows open
        int closewindowOACheck = 1;
        int closewindowIACheck = 1;
        double OALimit = 12;
        double IALimit = 15;
        //</editor-fold>

        //Read from Input file
        String filename = "Input.txt"; //20111125 MAMB Reads new Input.txt format where variable names are also included ALL OF THE "READING DATA" HAS BEEN MODIFIED BY MAMB IN NOV 2011.
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        ArrayList<Double> InputData = new ArrayList<Double>(); //FADE changed InputData to an ArrayList. This should be easier to handle, since we don't have to worry about array indices
        if (!SUPER_COOLVENT) { //SUPERFADE
            while ((line = br.readLine()) != null) {
                String datavalue[] = line.split("\t");
                InputData.add((Double.valueOf(datavalue[0])));
            }
            br.close();

            //Assign read values to variables
            Iterator idit = InputData.iterator(); //FADE changed inputData to an ArrayList. This should be easier to handle, since we don't have to worry about array indices
            ambienttemperature = (Double) idit.next();
            a_terrain = (Double) idit.next();
            b_terrain = (Double) idit.next();
//        circumBuildingH = (Double) idit.next(); //FADE: This is no more
            windvelocity = (Double) idit.next();
            bldgOrientation = (Double) idit.next();
            windDirect = (Double) idit.next();
            buildingHeight = (Double) idit.next();
            bldgtype = ((Double) idit.next()).intValue();
            floorwidth = (Double) idit.next();
            floorlength = (Double) idit.next();
            floorheight = (Double) idit.next();
            floortoceilingheight = (Double) idit.next();
            floornumber = ((Double) idit.next()).intValue(); //FADE: now every building needs this

            //Assign read values to variables according to building type
            if (bldgtype == 0 || bldgtype == 1) { //Luton + Chimney Type Data
                atriumwidth = (Double) idit.next();
                roofheight = (Double) idit.next();
                sidewindowsize = (Double) idit.next();
                topwindowsize = (Double) idit.next();
                intarea = (Double) idit.next();
                intareaheight = (Double) idit.next();
//            cdCheck = (Double) idit.next();
//            intgndarea = (Double) idit.next();
//            intgndareaheight = (Double) idit.next();
//            cdgndCheck = (Double) idit.next();
                fanCheck = ((Double) idit.next()).intValue(); //0 = no fan, 1 = light, 2 = medium, 3 = heavy , 4 = user-defined
                a2 = (Double) idit.next();
                a1 = (Double) idit.next();
                a0 = (Double) idit.next();
                b2 = (Double) idit.next();
                b1 = (Double) idit.next();
                fanD = (Double) idit.next();
                gamma = (Double) idit.next();
                T_fan = (Double) idit.next();
                omega_fan = (Double) idit.next();
                ACCheck = ((Double) idit.next()).intValue();
                T_AC = (Double) idit.next();
                omega_AC = (Double) idit.next();
                COP_AC = (Double) idit.next();
                controlCheck = ((Double) idit.next()).intValue(); //FADENEW
                if (fanCheck == 1) { //FADE: Light-duty, predefined fan
                    a2 = -40.186;
                    a1 = 97.263;
                    a0 = 68.076;
                    fanD = 0.508;
                    b2 = -0.19629;
                    b1 = 0.59134;
                    gamma = 1;
                    fanType = "Light";
                } else if (fanCheck == 2) { //FADE: Medium-duty, predefined fan. Too good to fail (if surge in fan operation, then something is wrong with code since this fan cannot fail)
                    a2 = -1.1342;
                    a1 = 0;
                    a0 = 150;
                    fanD = 1.0668;
                    b2 = -0.01400;
                    b1 = 0.16454;
                    gamma = 1;
                    fanType = "Medium";
                } else if (fanCheck == 3) { //FADE: Heavy-duty, predefined fan
                    a2 = -0.3372;
                    a1 = 4.5357;
                    a0 = 187.19;
                    fanD = 1.524;
                    b2 = -0.00193;
                    b1 = 0.06048;
                    gamma = 1;
                    fanType = "Heavy";
                }
            } else if (bldgtype == 2) { //Cross Ventliation Type Data
                cvsection = ((Double) idit.next()).intValue();
                cvinternalwindowsize = (Double) idit.next();
                cvinternalwindowheight = (Double) idit.next();
                sidewindowsize = (Double) idit.next();
                System.out.println("Sidewindowsize: "+sidewindowsize);
//            cdCheck = (Double) idit.next();
                fanCheck = ((Double) idit.next()).intValue();
                a2 = (Double) idit.next();
                a1 = (Double) idit.next();
                a0 = (Double) idit.next();
                b2 = (Double) idit.next();
                b1 = (Double) idit.next();
                fanD = (Double) idit.next();
                gamma = (Double) idit.next();
                T_fan = (Double) idit.next();
                omega_fan = (Double) idit.next();
                ACCheck = ((Double) idit.next()).intValue();
                T_AC = (Double) idit.next();
                omega_AC = (Double) idit.next();
                COP_AC = (Double) idit.next();
                controlCheck = ((Double) idit.next()).intValue(); //FADENEW
                if (fanCheck == 1) { //Light-duty, predefined fan
                    a2 = -40.186;
                    a1 = 97.263;
                    a0 = 68.076;
                    fanD = 0.508;
                    b2 = -0.19629;
                    b1 = 0.59134;
                    gamma = 1;
                    fanType = "Light";
                } else if (fanCheck == 2) { //Medium-duty, predefined fan. Too good to fail
                    a2 = -1.1342;
                    a1 = 0;
                    a0 = 150;
                    fanD = 1.0668;
                    b2 = -0.01400;
                    b1 = 0.16454;
                    gamma = 1;
                    fanType = "Medium";
                } else if (fanCheck == 3) { //Heavy-duty, predefined fan
                    a2 = -0.3372;
                    a1 = 4.5357;
                    a0 = 187.19;
                    fanD = 1.524;
                    b2 = -0.00193;
                    b1 = 0.06048;
                    gamma = 1;
                    fanType = "Heavy";
                }
            } //************** THIS NEEDS FAN-RELATED EDITS FROM FADE TO MATCH RECENT CHANGES!!!!********************
            //sdr_hulic - add new building type data input 
            else if (bldgtype == 4) { //ventilation shaft Type Data
                numShafts = ((Double) idit.next()).intValue();
                shaft1Width = (Double) idit.next();
                shaft1Length = (Double) idit.next();
                shaft1Height = (Double) idit.next();
                shaft1Exhaust = (Double) idit.next();
                shaft1Opening = (Double) idit.next();
                sidewindowsize = (Double) idit.next();

                if (numShafts == 2) //FADENEW: NOT IMPLEMENTED IN INTERFACE
                {
                    shaft2Width = (Double) idit.next();
                    shaft2Length = (Double) idit.next();
                    shaft2Height = (Double) idit.next();
                    shaft2Exhaust = (Double) idit.next();
                    shaft2Opening = (Double) idit.next();
                }

                fanCheck = ((Double) idit.next()).intValue();
                a2 = (Double) idit.next();
                a1 = (Double) idit.next();
                a0 = (Double) idit.next();
                b2 = (Double) idit.next();
                b1 = (Double) idit.next();
                fanD = (Double) idit.next();
                gamma = (Double) idit.next();
                T_fan = (Double) idit.next();
                omega_fan = (Double) idit.next();
                ACCheck = ((Double) idit.next()).intValue();
                T_AC = (Double) idit.next();
                omega_AC = (Double) idit.next();
                COP_AC = (Double) idit.next();
                controlCheck = ((Double) idit.next()).intValue(); //FADENEW
                if (fanCheck == 1) { //FADE: Light-duty, predefined fan
                    a2 = -40.186;
                    a1 = 97.263;
                    a0 = 68.076;
                    fanD = 0.508;
                    b2 = -0.19629;
                    b1 = 0.59134;
                    gamma = 1;
                    fanType = "Light";
                } else if (fanCheck == 2) { //FADE: Medium-duty, predefined fan. Too good to fail (if surge in fan operation, then something is wrong with code since this fan cannot fail)
                    a2 = -1.1342;
                    a1 = 0;
                    a0 = 150;
                    fanD = 1.0668;
                    b2 = -0.01400;
                    b1 = 0.16454;
                    gamma = 1;
                    fanType = "Medium";
                } else if (fanCheck == 3) { //FADE: Heavy-duty, predefined fan
                    a2 = -0.3372;
                    a1 = 4.5357;
                    a0 = 187.19;
                    fanD = 1.524;
                    b2 = -0.00193;
                    b1 = 0.06048;
                    gamma = 1;
                    fanType = "Heavy";
                }
            }
//************** end of THIS NEEDS FAN-RELATED EDITS FROM FADE TO MATCH RECENT CHANGES!!!!********************

            //Assign read values to variables
            doubleOpeningsCheck = ((Double) idit.next()).intValue();
            deltaH = (Double) idit.next();
            lowerwindowsize = (Double) idit.next();
            upperwindowsize = (Double) idit.next();
            sideglazingarea = (Double) idit.next();
            upperwindowheight = (Double) idit.next(); //windowheight if doubleopeningscheck == 0, upperwindowheight if doubleopeningsheight == 1;
            upperwindowheightW = (Double) idit.next();
            initialzonetemperature = (Double) idit.next();
            OccupancyHeatLoads = (Double) idit.next();
            persondensity = (Double) idit.next();
            occupancyscheduleON = ((Double) idit.next()).intValue();
            occupancyscheduleOFF = ((Double) idit.next()).intValue();
            offpeakloadfraction = (Double) idit.next();
            TMassCheck = ((Double) idit.next()).intValue();
            TMassThick = (Double) idit.next();
            TMassAreaFraction = (Double) idit.next();
            TMassk = (Double) idit.next();
            transimu = ((Double) idit.next()).intValue();
            //multisimu = ((Double) idit.next()).intValue(); //FADENEW: Multisim is gone - replaced by daily sim
            monthIni = ((Double) idit.next()).intValue();
            monthEnd = ((Double) idit.next()).intValue();
            dailysim = ((Double) idit.next()).intValue(); //FADENEW
            adiabaticWalls = ((Double) idit.next()).intValue(); //FADENEW
            hfloor = (Double) idit.next();
            hceiling = (Double) idit.next();
            nightcoolCheck = ((Double) idit.next()).intValue();
            nightcoolOn = ((Double) idit.next()).intValue();
            nightcoolOff = ((Double) idit.next()).intValue();
            closewindowOACheck = ((Double) idit.next()).intValue();
            OALimit = ((Double) idit.next()).intValue();
            closewindowIACheck = ((Double) idit.next()).intValue();
            IALimit = ((Double) idit.next()).intValue();

//        System.out.println("ACCheck "+ACCheck+" TAC "+T_AC+" OAC "+omega_AC);
            //sdr_hulic read custom Cd values
            CdA = (Double) idit.next(); //Cd inlet
            CdB = (Double) idit.next(); //Cd internal
            CdC = (Double) idit.next(); //Cd shaft
            CdD = (Double) idit.next(); //Cd exhaust

            //sdr_hulic if two shafts, include Cd E F G //FADE: MAMB thinks that both shafts should be equal
//        if (numShafts == 2) {
//            CdE = (Double) idit.next();
//            CdF = (Double) idit.next();
//            CdG = (Double) idit.next();
//        }
            //sdr_hulic equate CdCheck2 (for second shaft) to CdCheck specified in UI
//        cdCheck2 = cdCheck;
//        cdgndCheck2 = cdgndCheck;
            //sdr_hulic - determine proper Cd value for "Inside" opening. gives priority to customizable CdB
//        if (CdB != 0) {
//            cdCheck = CdB;
//            cdgndCheck = CdB;
//        }
            //sdr_hulic if CdE customizable Cd value is specified, then assign it to cdCheck2 and cdgndCheck2
//        if (CdE != 0) {
//            cdCheck2 = CdE;
//            cdgndCheck2 = CdE;
//        }
            //<editor-fold defaultstate="collapsed" desc="FADE: Print inputs">
            boolean print = false; 
            if (print) {
                System.out.println("ambienttemperature = " + ambienttemperature);
                System.out.println("a_terrain = " + a_terrain);
                System.out.println("b_terrain = " + b_terrain);
                System.out.println("windvelocity = " + windvelocity);
                System.out.println("bldgOrientation = " + bldgOrientation);
                System.out.println("windDirect = " + windDirect);
                System.out.println("buildingHeight = " + buildingHeight);
                System.out.println("bldgtype = " + bldgtype);
                System.out.println("floorwidth = " + floorwidth);
                System.out.println("floorlength = " + floorlength);
                System.out.println("floorheight = " + floorheight);
                System.out.println("floortoceilingheight = " + floortoceilingheight);
                System.out.println("floornumber = " + floornumber);
                if (bldgtype == 0 || bldgtype == 1) { //Luton + Chimney Type Data
                    System.out.println("atriumwidth = " + atriumwidth);
                    System.out.println("roofheight = " + roofheight);
                    System.out.println("sidewindowsize = " + sidewindowsize);
                    System.out.println("topwindowsize = " + topwindowsize);
                    System.out.println("intarea = " + intarea);
                    System.out.println("intareaheight = " + intareaheight);
//                System.out.println("cdCheck = " + cdCheck);
//                System.out.println("intgndarea = " + intgndarea);
//                System.out.println("intgndareaheight = " + intgndareaheight);
//                System.out.println("cdgndCheck = " + cdgndCheck);
                    System.out.println("T_fan = " + T_fan);
                    System.out.println("omega_fan = " + omega_fan);
                    System.out.println("ACCheck = " + ACCheck);
                    System.out.println("T_AC = " + T_AC);
                    System.out.println("omega_AC = " + omega_AC);
                    System.out.println("COP_AC = " + COP_AC);
                    System.out.println("controlCheck = " + controlCheck);
                } else if (bldgtype == 2) { //Cross Ventliation Type Data
                    System.out.println("cvsection = " + cvsection);
                    System.out.println("cvinternalwindowsize = " + cvinternalwindowsize);
                    System.out.println("cvinternalwindowheight = " + cvinternalwindowheight);
                    System.out.println("sidewindowsize = " + sidewindowsize);
                } else if (bldgtype == 4) { //ventilation shaft Type Data
                    System.out.println("numShafts = " + numShafts);
                    System.out.println("shaft1Width = " + shaft1Width);
                    System.out.println("shaft1Length = " + shaft1Length);
                    System.out.println("shaft1Height = " + shaft1Height);
                    System.out.println("shaft1Exhaust = " + shaft1Exhaust);
                    System.out.println("shaft1Opening = " + shaft1Opening);
                    System.out.println("sidewindowsize = " + sidewindowsize);
                    if (numShafts == 2) {
                        System.out.println("shaft2Width = " + shaft2Width);
                        System.out.println("shaft2Length = " + shaft2Length);
                        System.out.println("shaft2Height = " + shaft2Height);
                        System.out.println("shaft2Exhaust = " + shaft2Exhaust);
                        System.out.println("shaft2Opening = " + shaft2Opening);
                    }
                }
                System.out.println("fanCheck = " + fanCheck);
                System.out.println("a2 = " + a2);
                System.out.println("a1 = " + a1);
                System.out.println("a0 = " + a0);
                System.out.println("b2 = " + b2);
                System.out.println("b1 = " + b1);
                System.out.println("fanD = " + fanD);
                System.out.println("gamma = " + gamma);
                System.out.println("doubleOpeningsCheck = " + doubleOpeningsCheck);
                System.out.println("deltaH = " + deltaH);
                System.out.println("lowerwindowsize = " + lowerwindowsize);
                System.out.println("upperwindowsize = " + upperwindowsize);
                System.out.println("sideglazingarea = " + sideglazingarea);
                System.out.println("upperwindowheight = " + upperwindowheight);
                System.out.println("upperwindowheightW = " + upperwindowheightW);
                System.out.println("initialzonetemperature = " + initialzonetemperature);
                System.out.println("OccupancyHeatLoads = " + OccupancyHeatLoads);
                System.out.println("persondensity = " + persondensity);
                System.out.println("occupancyscheduleON = " + occupancyscheduleON);
                System.out.println("occupancyscheduleOFF = " + occupancyscheduleOFF);
                System.out.println("offpeakloadfraction = " + offpeakloadfraction);
                System.out.println("TMassCheck = " + TMassCheck);
                System.out.println("TMassThick = " + TMassThick);
                System.out.println("TMassAreaFraction = " + TMassAreaFraction);
                System.out.println("TMassk = " + TMassk);
                System.out.println("transimu = " + transimu);
                System.out.println("monthIni = " + monthIni);
                System.out.println("monthEnd = " + monthEnd);
                System.out.println("dailysim = " + dailysim);
                System.out.println("hfloor = " + hfloor);
                System.out.println("hceiling = " + hceiling);
                System.out.println("nightcoolCheck = " + nightcoolCheck);
                System.out.println("nightcoolOn = " + nightcoolOn);
                System.out.println("nightcoolOff = " + nightcoolOff);
                System.out.println("closewindowOACheck = " + closewindowOACheck);
                System.out.println("OALimit = " + OALimit);
                System.out.println("closewindowIACheck = " + closewindowIACheck);
                System.out.println("IALimit = " + IALimit);
                System.out.println("CdA = " + CdA);
                System.out.println("CdB = " + CdB);
                System.out.println("CdC = " + CdC);
                System.out.println("CdD = " + CdD);
            }
//</editor-fold>

            if (doubleOpeningsCheck == 1) {
//            System.out.println("here I am");
                lowerwindowheight = upperwindowheight - deltaH; // not needed? //FADE: Needed
                windowheight = (Math.pow(upperwindowsize, 2) * upperwindowheight + Math.pow(lowerwindowsize, 2) * lowerwindowheight) / (Math.pow(upperwindowsize, 2) + Math.pow(lowerwindowsize, 2)); //FADE
//            windowheight = (upperwindowsize * upperwindowheight + lowerwindowsize * lowerwindowheight) / (upperwindowsize + lowerwindowsize); //FADE does not understand this; thinks should be with the sizes^2 as above (only for buoyancy). Ask MAMB
                stratwindowheight = upperwindowheight; // this is a first step, and not true if lower window is larger than upper. (MAMB)
                stratwindowsize = upperwindowsize; // this is a first step, and not true if lower window is larger than upper. (MAMB)
            } else {
                windowheight = upperwindowheight;
                stratwindowheight = windowheight; // this is true and won't change. (MAMB)
                stratwindowsize = sidewindowsize;  // this is true and won't change. (MAMB)
                //reset the following to make sure this value doesn't enter any calculation (MAMB) In the future this can probably be erased.
                deltaH = 0;
                lowerwindowheight = 0;
                lowerwindowsize = 0;
            }

            double zonefloorarea = floorwidth * floorlength;  //2011 Nov MAMB adding these to make the sintax below more readable
            double zonefloorperimeter = 2 * (floorwidth + floorlength); //FADENEW
            double zonevolume = zonefloorarea * floortoceilingheight; //2011 Nov MAMB ATTENTION!!! This calculates the REAL zone volume of an occupied space.
            double occupancyheatgain = zonefloorarea * OccupancyHeatLoads;  //2011 Nov MAMB adding these to make the sintax below more readable
            double people = zonefloorarea * persondensity;  //2011 Nov MAMB adding these to make the sintax below more readable

            if (bldgtype == 0) {
                zoneNum = 3 * floornumber + 1;
                openingNum = 4 * floornumber + floornumber + 1;
                dis_W_E = 2 * floorwidth + atriumwidth;
                dis_N_S = floorlength;
            } else if (bldgtype == 1) {
                zoneNum = 2 * floornumber + 1;
                openingNum = 2 * floornumber + floornumber + 1;
                dis_W_E = floorwidth + atriumwidth;
                dis_N_S = floorlength;
            } else if (bldgtype == 2) {
//            if (cvsection != 1) {
                zoneNum = cvsection * floornumber; //FADE
//            } else {
//                zoneNum = cvsection + 1;
//            }
                openingNum = (cvsection + 1) * floornumber; //FADE
                dis_W_E = floorwidth * cvsection;
                dis_N_S = floorlength;
            } //sdr_hulic -added new zone types for ventilation shaft building type
            else if (bldgtype == 4) {
                zoneNum = (numShafts + 1) * floornumber + numShafts;
                openingNum = 2 * numShafts * floornumber + floornumber + numShafts;
                dis_W_E = floorwidth; //distance excludes width of shaft, which is often located in space or doesn't run the length of the floor and thus shouldn't be considered
                dis_N_S = floorlength;
            } //sdr_hulic end
            else {
                zoneNum = sslayer * floornumber;
                openingNum = floornumber * (sslayer + 1);
                dis_W_E = floorwidth;
                dis_N_S = floorlength;
            }

            //FADE: Compute SCF***This needs to be checked
//        SCF = 1;
//        if (a_terrain == 1) //Flat terrain with isolated obstacles
//        {
//            if (circumBuildingH/buildingHeight <= 1) //No obstruction or local shielding
//            {
//                SCF = 1;
//            } else { //Light local shielding with few obstruction
//                SCF = 0.88;
//            }
//        } else if (a_terrain == 0.85) //Rural areas with low buildings
//        {
//            if (circumBuildingH/buildingHeight <= 1) //No obstruction or local shielding
//            {
//                SCF = 1;
//            } else if (circumBuildingH/Math.min(dis_W_E,dis_N_S) <= 0.4) //Light local shielding with few obstructions
//            {
//                SCF = 0.88;
//            } else { //Moderate local shielding
//                SCF = 0.74;
//            }
//        } else if (a_terrain == 0.67) //Urban, industrial or forest areas
//        {
//            if (circumBuildingH/Math.min(dis_W_E,dis_N_S) <= 0.175)
//            {
//                SCF = 0.74;
//            } else if (circumBuildingH/Math.min(dis_W_E,dis_N_S) <= 0.875)
//            {
//                SCF = 0.57;
//            } else { //Very heavy shielding
//                SCF = 0.31;
//            }
//        } else if (a_terrain == 0.47) //Urban, industrial or forest areas
//        {
//            if (circumBuildingH/Math.min(dis_W_E,dis_N_S) <= 0.875)
//            {
//                SCF = 0.57;
//            } else { //Very heavy shielding
//                SCF = 0.31;
//            }
//        }
            int WallNum = 0;

            //FADE: Trying to fix thermal mass problems
            int WallLayer = 0;
            double TMassmass = 1;
            if (TMassCheck == 1) {
                WallLayer = Math.max(1, (int) Math.floor(TMassThick / (SAFETY_F * Math.pow(TIME_STEP_SIZE * TMassk / (2500 * 880 * FOURIER_CRIT), 0.5))));
                TMassmass = TMassAreaFraction * TMassThick * zonefloorarea * 2500;
//            System.out.println("The L "+((TMassThick/(SAFETY_F*Math.pow(TIME_STEP_SIZE*TMassk/(2500*880*FOURIER_CRIT),0.5))))+" Number of layers: "+WallLayer);
            }
            int TMassNum = WallLayer;
            
            //Add first case
            Manage Manage = new Manage(caseNum);
            Manage.addCase(caseCount);
            Manage.caseManage[caseCount].setBldgOrientation(bldgOrientation); //FADE: caseManage is a DataPool array
            Manage.caseManage[caseCount].addAmbientZone(ambienttemperature, a_terrain, b_terrain, SCF, windvelocity, windDirect, dis_W_E, dis_N_S, buildingHeight, roofheight);
            Manage.caseManage[caseCount].setZoneNum(zoneNum);
            Manage.caseManage[caseCount].setOpeningNum(openingNum);
            Manage.caseManage[caseCount].setBldgType(bldgtype);
            Manage.caseManage[caseCount].setDisNS(dis_N_S);
            Manage.caseManage[caseCount].setDisWE(dis_W_E);
            Manage.caseManage[caseCount].setOccupancyScheduleOn(occupancyscheduleON);
            Manage.caseManage[caseCount].setOccupancyScheduleOff(occupancyscheduleOFF);
            Manage.caseManage[caseCount].setOffPeakLoadFrac(offpeakloadfraction);
            Manage.caseManage[caseCount].setTAC(T_AC);
            Manage.caseManage[caseCount].setOmegaAC(omega_AC);
            Manage.caseManage[caseCount].setTFan(T_fan);
            Manage.caseManage[caseCount].setOmegaFan(omega_fan);
            Manage.caseManage[caseCount].setACCheck(ACCheck);
            Manage.caseManage[caseCount].setFanCheck(fanCheck);
            Manage.caseManage[caseCount].setCOPAC(COP_AC);
            Manage.caseManage[caseCount].setControlCheck(controlCheck);
            Manage.caseManage[caseCount].setTMassCheck(TMassCheck);
            Manage.caseManage[caseCount].setTMassSlabMass(TMassmass);
            //sdr_hulic - add storage of number of floors and shafts
            Manage.caseManage[caseCount].setNumFloors(floornumber);
            Manage.caseManage[caseCount].setNumShafts(numShafts);
            Manage.caseManage[caseCount].nightcoolCheck = nightcoolCheck;
            Manage.caseManage[caseCount].nightcoolOn = nightcoolOn;
            Manage.caseManage[caseCount].nightcoolOff = nightcoolOff;
            Manage.caseManage[caseCount].closewindowOACheck = closewindowOACheck;
            Manage.caseManage[caseCount].OALimit = OALimit;
            Manage.caseManage[caseCount].closewindowIACheck = closewindowIACheck;
            Manage.caseManage[caseCount].IALimit = IALimit;

            //FADENEW: New thermal mass connections from Karine-----------------------------------------------------------------------------------------
            //Luton zones and flow paths
            if (bldgtype == 0) {
                if (TMassCheck == 1) { //FADE
                    WallNum = 2 * (floornumber + 1); //Karine: for the 2 sides, and corrected for added floor
                    TMassNum = WallNum * WallLayer;
                }
                //FADE: Add walls
                if (adiabaticWalls == 0) {
                    WallNum += 8 * floornumber + 5; //FADE: ((N+E+W)*2towers+(E+W)*1atrium)*#floors + (N+S+E+W+R)*1atrium
                    TMassNum += 2 * (8 * floornumber + 5); //FADE
                }

                //if (TMassCheck == 1) { //FADE
                //System.out.println("TMass num = "+TMassNum);
                Manage.caseManage[caseCount].setTMassNum(TMassNum); //Add from the left lower corner -->ASSUMED TO BE North Facade
                //}
                for (int i = 1; i <= floornumber; i++) { //FADE: Add right tower
                    Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * ((i - 1) % floornumber + 1.0 / 2.0), initialzonetemperature, occupancyheatgain, "N_wall", i, bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov modified by MAMB
                    if (TMassCheck == 1) {
                        if (i == 1) { //Karine: ground floor
                            for (int j = 0; j < WallLayer - 1; j++) {
                                Manage.caseManage[caseCount].addTMass(j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB20080828                           
                                Manage.caseManage[caseCount].createTMassIntConnection(j, j + 1);
                            }// Karine: no ceiling connection
                            Manage.caseManage[caseCount].addTMass(WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "floor", TMassk, initialzonetemperature, 1);//karine corrected 06/13
                            Manage.caseManage[caseCount].createTMassConnection(WallLayer - 1, 1); //Karine: Connection between top of slab and airZone 1
                        }
                        for (int j = 0; j < WallLayer - 1; j++) { //From j=0 to 8, 10 to 18, etc -->hceiling (weird)
                            Manage.caseManage[caseCount].addTMass(WallLayer + (i - 1) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB 20080828
                            Manage.caseManage[caseCount].createTMassIntConnection(WallLayer + (i - 1) * WallLayer + j, WallLayer + (i - 1) * WallLayer + j + 1); //Connect TM layers internally from 10-19
                        }
                        Manage.caseManage[caseCount].createTMassConnection(WallLayer + (i - 1) * WallLayer + 0, i); // ceiling connection //connect TM 10 with Z 1, and TM 20 with Z 2, etc.
                        //FADE: add roof
                        Manage.caseManage[caseCount].addTMass(WallLayer + i * WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "roof", TMassk, initialzonetemperature, 1); //20070924 modified by MAMB //for j=9, 19 -->hfloor //Karine changed 06/2013
                        Manage.caseManage[caseCount].createTMassConnection(WallLayer + i * WallLayer - 1, 0); //FADE: roof connected to outdoor
                        if (i < floornumber) { //for i = 1 to floornumber-1
                            Manage.caseManage[caseCount].createTMassConnection(WallLayer + (i - 1) * WallLayer + WallLayer - 1, i + 1); //floor connection //connect TM 19 with Z 2, and TM 29 with Z 3
                        }
                    } //end of TMassCheck
                }
                for (int i = floornumber + 1; i <= 2 * floornumber; i++) { //FADE: Add atrium
                    Manage.caseManage[caseCount].addZone(i, atriumwidth * floorlength * floorheight, floorlength * atriumwidth, floorheight * ((i - 1) % floornumber + 1.0 / 2.0), initialzonetemperature + 1, 0.0, "Atrium", i % (floornumber + 1) + 1, bldgOrientation, 0, 0, false, floorlength); //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium //FADE: Why is the zoneAirTemp = initialzonetemperature+1?
                }
                Manage.caseManage[caseCount].addZone(2 * floornumber + 1, atriumwidth * floorlength * roofheight, floorlength * atriumwidth, floorheight * floornumber + 1.0 / 2.0 * roofheight, initialzonetemperature + 1, 0.0, "Roof", floornumber + 1, bldgOrientation, 0, 0, false, floorlength); //FADE: Add roof //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium

                for (int i = 2 * floornumber + 2; i <= 3 * floornumber + 1; i++) { //FADE: Add left tower
                    Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * ((i - 2) % floornumber + 1.0 / 2.0), initialzonetemperature, occupancyheatgain, "S_wall", i % (floornumber + 1) + 1, bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov modified by MAMB
                    if (TMassCheck == 1) {
                        if (i == 2 * floornumber + 2) { //Karine: ground floor
                            for (int j = 0; j < WallLayer - 1; j++) {
                                Manage.caseManage[caseCount].addTMass(WallLayer * (floornumber + 1) + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB20080828                           
                                Manage.caseManage[caseCount].createTMassIntConnection(WallLayer * (floornumber + 1) + j, WallLayer * (floornumber + 1) + j + 1);
                            }// Karine: no ceiling connection
                            Manage.caseManage[caseCount].addTMass(WallLayer * (floornumber + 1) + WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea, zonefloorperimeter, 2500, 880, "floor", TMassk, initialzonetemperature, 1);//karine corrected 06/13
                            Manage.caseManage[caseCount].createTMassConnection(WallLayer * (floornumber + 1) + WallLayer - 1, i); //Karine: Connection between top of slab and airZone
                        }

                        for (int j = 0; j < WallLayer - 1; j++) {
                            Manage.caseManage[caseCount].addTMass((2 * WallLayer) + (i - 2 * floornumber - 2 + floornumber) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB 20080828
                            Manage.caseManage[caseCount].createTMassIntConnection((2 * WallLayer) + (i - 2 * floornumber - 2 + floornumber) * WallLayer + j, (2 * WallLayer) + (i - 2 * floornumber - 2 + floornumber) * WallLayer + j + 1);
                        }
                        Manage.caseManage[caseCount].createTMassConnection((2 * WallLayer) + (i - 2 * floornumber - 2 + floornumber) * WallLayer, i); //Connect ceilgin
                        //FADE: add roof
                        Manage.caseManage[caseCount].addTMass((2 * WallLayer) + (i - 2 * floornumber - 1 + floornumber) * WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "roof", TMassk, initialzonetemperature, 1); //MAMB 20080828
                        Manage.caseManage[caseCount].createTMassConnection((2 * WallLayer) + (i - 2 * floornumber - 1 + floornumber) * WallLayer - 1, 0);
                        if (i < 3 * floornumber + 1) {
                            Manage.caseManage[caseCount].createTMassConnection((2 * WallLayer) + (i - 2 * floornumber - 1 + floornumber) * WallLayer - 1, i + 1);//Connect floor
                        }
                    }
                }
                //Add openings: public void addOpening(int flowPathCount, double openingH, double openingW, double openingElev, String openingLocation, int connectNum1, int connectNum2, double upperopeningheight, double upperopeningarea, int doubleopeningcheck, double cd)
                for (int i = 0; i < floornumber; i++) { //FADE: Add east external opening
                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), floorheight * i + windowheight, "N_wall", 0, i + 1, stratwindowheight, stratwindowsize, doubleOpeningsCheck, CdA); //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
                }
                for (int i = floornumber; i < 2 * floornumber; i++) { //FADE: Add east internal openings
//                if (i == floornumber) { //FADE: Internal opening area is different at the gnd level
//                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intgndarea), Math.sqrt(intgndarea), intgndareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, intarea, 0, cdgndCheck);
//                } else {
                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intarea), Math.sqrt(intarea), intareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, intarea, 0, CdB);
//                }
                }
                for (int i = 2 * floornumber; i < 3 * floornumber; i++) { //FADE: Add west internal openings
//                if (i == 2 * floornumber) { //FADE: Internal opening area is different at the gnd level               
//                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intgndarea), Math.sqrt(intgndarea), intgndareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 2, floortoceilingheight * 0.5, intarea, 0, cdgndCheck);
//                } else {
                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intarea), Math.sqrt(intarea), intareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 2, floortoceilingheight * 0.5, intarea, 0, CdB);
//                }
                }
                for (int i = 3 * floornumber; i < 4 * floornumber; i++) { //FADE: Add west external openings
                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), floorheight * (i % floornumber) + windowheight, "S_wall", 0, i - floornumber + 2, stratwindowheight, stratwindowsize, doubleOpeningsCheck, CdA); //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
                }
                for (int i = 4 * floornumber; i < 5 * floornumber; i++) { //FADE: Add internal opening (atrium)
                    Manage.caseManage[caseCount].addOpening(i, atriumwidth, floorlength, floorheight * (i % floornumber + 1), "Atrium", (i % floornumber) + 1 + floornumber, (i % floornumber) + 2 + floornumber, 0, 0, 0, CdC); //FADE: Changed the opening location 'inside' for 'atrium'
                }
                Manage.caseManage[caseCount].addOpening(5 * floornumber, Math.sqrt(topwindowsize), Math.sqrt(topwindowsize), floorheight * floornumber + roofheight, "Roof", 0, 2 * floornumber + 1, 0, 0, 0, CdD); //Add top opening (roof)
                //FADE: Top opening becomes a fan
                if (fanCheck != 0 && fanCheck != 4) {
                    Manage.caseManage[caseCount].openingInputData[5 * floornumber].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
                }
                //Add walls: walls are two-layer TMasses with no heat capacity
                if (adiabaticWalls == 0) {
                    int l = 0;
                    if (TMassCheck == 1) {
                        l = 2 * (floornumber + 1) * WallLayer;
                    }
                    for (int i = 1; i <= floornumber; i++) { //Add right tower
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                    }
                    for (int i = floornumber + 1; i <= (2 * floornumber) + 1; i++) { //Add attrium
                        Manage.caseManage[caseCount].addTMass(l, atriumwidth * floorheight * 5e-2, atriumwidth * floorheight, 2 * (atriumwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, atriumwidth * floorheight * 5e-2, atriumwidth * floorheight, 2 * (atriumwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, atriumwidth * floorheight * 5e-2, atriumwidth * floorheight, 2 * (atriumwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, atriumwidth * floorheight * 5e-2, atriumwidth * floorheight, 2 * (atriumwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                    }
                    //FADE: add atrium roof
                    Manage.caseManage[caseCount].addTMass(l, floorlength * roofheight * 5e-2, floorlength * roofheight, 2 * (roofheight + floorlength), 0, 880, "N_wall", TMassk, initialzonetemperature, 0);
                    Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, (2 * floornumber) + 1);//Connect inner layer
                    l++;
                    Manage.caseManage[caseCount].addTMass(l, floorlength * roofheight * 5e-2, floorlength * roofheight, 2 * (roofheight + floorlength), 0, 880, "N_wall", TMassk, initialzonetemperature, 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                    l++;
                    Manage.caseManage[caseCount].addTMass(l, floorlength * roofheight * 5e-2, floorlength * roofheight, 2 * (roofheight + floorlength), 0, 880, "S_wall", TMassk, initialzonetemperature, 0);
                    Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, (2 * floornumber) + 1);//Connect inner layer
                    l++;
                    Manage.caseManage[caseCount].addTMass(l, floorlength * roofheight * 5e-2, floorlength * roofheight, 2 * (roofheight + floorlength), 0, 880, "S_wall", TMassk, initialzonetemperature, 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                    l++;
                    Manage.caseManage[caseCount].addTMass(l, (floorlength * atriumwidth - topwindowsize) * 5e-2, (floorlength * atriumwidth - topwindowsize), 2 * (atriumwidth + floorlength), 0, 880, "roof", TMassk, initialzonetemperature, 0);
                    Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, (2 * floornumber) + 1);//Connect inner layer
                    l++;
                    Manage.caseManage[caseCount].addTMass(l, (floorlength * atriumwidth - topwindowsize) * 5e-2, (floorlength * atriumwidth - topwindowsize), 2 * (atriumwidth + floorlength), 0, 880, "roof", TMassk, initialzonetemperature, 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                    l++;
                    for (int i = (2 * floornumber) + 2; i <= (3 * floornumber) + 1; i++) { //Add left tower
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                    }
                }
                //Chimney zones and flow paths
            } else if (bldgtype == 1) {
                if (TMassCheck == 1) { //FADE
                    WallNum = floornumber + 1;//Karine: corrected for added ground floor
                    TMassNum = WallNum * WallLayer;
                }
                //FADE: Add walls
                if (adiabaticWalls == 0) {
                    WallNum += 6 * floornumber + 5; //FADE: ((N+E+W)*1tower+(E+W+S)*1atrium)*#floors + (N+S+E+W+R)*1atrium
                    TMassNum += 2 * (6 * floornumber + 5); //FADE
                }
//                if (TMassCheck == 1) {
                Manage.caseManage[caseCount].setTMassNum(TMassNum); //Add from the left lower corner <-- ASSUMED TO BE NORTH
//                }
                for (int i = 1; i <= floornumber; i++) {
                    Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * ((i - 1) % floornumber + 1.0 / 2.0), initialzonetemperature, occupancyheatgain, "N_wall", i, bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov MAMB
//                System.out.println("height of zone "+i+" is "+(floorheight * ((i-1) % floornumber + 1.0 / 2.0)));
                    if (TMassCheck == 1) {
                        if (i == 1) { //Karine: ground floor
                            for (int j = 0; j < WallLayer - 1; j++) {
                                Manage.caseManage[caseCount].addTMass(j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB20080828                           
//                            System.out.println("Added layer "+j+" no absorption ceiling");
                                Manage.caseManage[caseCount].createTMassIntConnection(j, j + 1);
//                            System.out.println("Connected layer "+j+" and layer "+(j + 1));
                            }// Karine: no ceiling connection
                            Manage.caseManage[caseCount].addTMass(WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "floor", TMassk, initialzonetemperature, 1);//karine corrected 06/13
//                        System.out.println("Added layer "+(WallLayer - 1)+" absorption floor");
                            Manage.caseManage[caseCount].createTMassConnection(WallLayer - 1, 1); //Karine: Connection between top of slab and airZone 1
//                        System.out.println("Connected layer "+(WallLayer - 1)+" and zone "+1);
                        }
                        for (int j = 0; j < WallLayer - 1; j++) {
                            Manage.caseManage[caseCount].addTMass(WallLayer + (i - 1) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB20080828//Karine:changed 06/2013
//                        System.out.println("Added layer "+(WallLayer + (i - 1) * WallLayer + j)+" no absorption ceiling");
                            Manage.caseManage[caseCount].createTMassIntConnection(WallLayer + (i - 1) * WallLayer + j, WallLayer + (i - 1) * WallLayer + j + 1);//Karine:changed 06/2013
//                        System.out.println("Connected layer "+(WallLayer + (i - 1) * WallLayer + j)+" and layer "+(WallLayer + (i - 1) * WallLayer + j + 1));
                        }
                        Manage.caseManage[caseCount].createTMassConnection(WallLayer + (i - 1) * WallLayer + 0, i); // ceiling connection //connect TM 10 with Z 1, and TM 20 with Z 2, etc.
//                    System.out.println("Connected layer "+(WallLayer + (i - 1) * WallLayer + 0)+" and zone "+i);

                        Manage.caseManage[caseCount].addTMass(WallLayer + i * WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "floor", TMassk, initialzonetemperature, 1); //MAMB; Karine corrected 06/13
//                    System.out.println("Added layer "+(WallLayer + i * WallLayer - 1)+" absorption floor");
                        if (i < floornumber) {
                            Manage.caseManage[caseCount].createTMassConnection(WallLayer + i * WallLayer - 1, i + 1); //floor connection //connect TM 19 with Z 2, and TM 29 with Z 3
//                        System.out.println("Connected layer "+(WallLayer + i * WallLayer - 1)+" and zone "+i + 1);
                        }
                    }
                }
                for (int i = floornumber + 1; i <= 2 * floornumber; i++) { //Add chimney zones
                    Manage.caseManage[caseCount].addZone(i, atriumwidth * floorlength * floorheight, floorlength * atriumwidth, floorheight * ((i - 1) % floornumber + 1.0 / 2.0), initialzonetemperature + 1, 0.0, "Internal", i % (floornumber + 1) + 1, bldgOrientation, 0, 0, false, floorlength); //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium
//                System.out.println("height of zone "+i+" is "+(floorheight * ((i-1) % floornumber + 1.0 / 2.0)));
                }
                Manage.caseManage[caseCount].addZone(2 * floornumber + 1, atriumwidth * floorlength * roofheight, floorlength * atriumwidth, floorheight * floornumber + 1.0 / 2.0 * roofheight, initialzonetemperature + 1, 0.0, "Roof", floornumber + 1, bldgOrientation, 0, 0, false, floorlength); //Add roof //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium
//            System.out.println("height of zone "+(2 * floornumber + 1)+" is "+(floorheight * floornumber + 1.0 / 2.0 * roofheight));
                for (int i = 0; i < floornumber; i++) { //Add east wall path
                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), floorheight * i + windowheight, "N_wall", 0, i + 1, stratwindowheight, stratwindowsize, doubleOpeningsCheck, CdA); //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
//                System.out.println("height of opening "+i+" is "+(floorheight * i + windowheight));
                }
                for (int i = floornumber; i < 2 * floornumber; i++) { //Add left internal openings
//                if (i == floornumber) { //FADE: Internal opening area is different at the gnd level
//                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intgndarea), Math.sqrt(intgndarea), intgndareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, intarea, 0, cdgndCheck);
//                    System.out.println("height of opening "+i+" is "+(intgndareaheight + ((i % floornumber) * floorheight)));
//                } else {
                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intarea), Math.sqrt(intarea), intareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, intarea, 0, CdB); //Add internal horizontal openings
//                    System.out.println("height of opening "+i+" is "+(intareaheight + ((i % floornumber) * floorheight)));
//                }
                }
                for (int i = 2 * floornumber; i < 3 * floornumber; i++) {
                    Manage.caseManage[caseCount].addOpening(i, atriumwidth, floorlength, floorheight * (i % floornumber + 1), "Atrium", i + 1 - floornumber, i + 2 - floornumber, 0, 0, 0, CdC);
//                System.out.println("height of opening "+i+" is "+(floorheight * (i % floornumber + 1)));
                }
                Manage.caseManage[caseCount].addOpening(3 * floornumber, Math.sqrt(topwindowsize), Math.sqrt(topwindowsize), floorheight * floornumber + roofheight, "Roof", 0, 2 * floornumber + 1, 0, 0, 0, CdD); //Add top opening
//            System.out.println("height of opening "+(3 * floornumber)+" is "+(floorheight * floornumber + roofheight));
                //FADE: Top opening becomes a fan
                if (fanCheck != 0 && fanCheck != 4) {
                    Manage.caseManage[caseCount].openingInputData[3 * floornumber].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
                }
                //Add walls: walls are two-layer TMasses with no heat capacity
                if (adiabaticWalls == 0) {
                    int l = 0;
                    if (TMassCheck == 1) {
                        l = (floornumber + 1) * WallLayer;
                    }
                    for (int i = 1; i <= floornumber; i++) { //Add right tower
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                    }
                    for (int i = floornumber + 1; i <= (2 * floornumber) + 1; i++) { //Add attrium
                        Manage.caseManage[caseCount].addTMass(l, atriumwidth * floorheight * 5e-2, atriumwidth * floorheight, 2 * (atriumwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, atriumwidth * floorheight * 5e-2, atriumwidth * floorheight, 2 * (atriumwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, atriumwidth * floorheight * 5e-2, atriumwidth * floorheight, 2 * (atriumwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, atriumwidth * floorheight * 5e-2, atriumwidth * floorheight, 2 * (atriumwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorlength * floorheight * 5e-2, floorlength * floorheight, 2 * (floorlength + floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorlength * floorheight * 5e-2, floorlength * floorheight, 2 * (floorlength + floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                    }
                    //FADE: add atrium roof
                    Manage.caseManage[caseCount].addTMass(l, floorlength * roofheight * 5e-2, floorlength * roofheight, 2 * (roofheight + floorlength), 0, 880, "N_wall", TMassk, initialzonetemperature, 0);
                    Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, (2 * floornumber) + 1);//Connect inner layer
                    l++;
                    Manage.caseManage[caseCount].addTMass(l, floorlength * roofheight * 5e-2, floorlength * roofheight, 2 * (roofheight + floorlength), 0, 880, "N_wall", TMassk, initialzonetemperature, 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                    l++;
                    Manage.caseManage[caseCount].addTMass(l, (floorlength * atriumwidth - topwindowsize) * 5e-2, (floorlength * atriumwidth - topwindowsize), 2 * (atriumwidth + floorlength), 0, 880, "roof", TMassk, initialzonetemperature, 0);
                    Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, (2 * floornumber) + 1);//Connect inner layer
                    l++;
                    Manage.caseManage[caseCount].addTMass(l, (floorlength * atriumwidth - topwindowsize) * 5e-2, (floorlength * atriumwidth - topwindowsize), 2 * (atriumwidth + floorlength), 0, 880, "roof", TMassk, initialzonetemperature, 1);
                    Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                }
                //Cross-ventilation zones and flow paths
            } else if (bldgtype == 2) { //Karine only 1 floor! //FADE: any number of floors
                if (TMassCheck == 1) { //FADE
                    WallNum = cvsection * (floornumber + 1); //Karine: corrected for added ground floor //FADE: now a multistory building
                    TMassNum = WallNum * WallLayer;
                }
                //FADE: Add walls
                if (adiabaticWalls == 0) {
                    WallNum += 2 * (cvsection + 1) * floornumber; //FADE: (N+S+(E+W)*#sections)*#floor
                    TMassNum += 2 * (2 * (cvsection + 1) * floornumber); //FADE
//                System.out.println("WallNum "+WallNum+" TMassNum "+TMassNum);
                }
//                if (TMassCheck == 1) {
                Manage.caseManage[caseCount].setTMassNum(TMassNum);
//                }
                for (int k = 1; k <= floornumber; k++) { //FADE
//                System.out.println("k = "+k);
                    for (int i = 1 + (k - 1) * cvsection; i <= k * cvsection; i++) { //FADE
//                    System.out.println("i = "+i);
                        if (i == 1 + (k - 1) * cvsection) { //FADE
                            Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * (k - 1.0 / 2.0), initialzonetemperature, occupancyheatgain, "N_wall", k, bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov modified by MAMB); //20071228 MAMB removed Math.random() in zone temperature on the above lines
                        } else if (i == k * cvsection) {
                            Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * (k - 1.0 / 2.0), initialzonetemperature, occupancyheatgain, "S_wall", k, bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov modified by MAMB); //20071228 MAMB removed Math.random() in zone temperature on the above lines
                        } else {
                            Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * (k - 1.0 / 2.0), initialzonetemperature, occupancyheatgain, "Internal", k, bldgOrientation, 0, floortoceilingheight, true, floorlength); //20071228 MAMB removed Math.random() in zone temperature on the above lines
                        }

                        if (TMassCheck == 1) {
                            if (k == 1 && i == 1) { //Karine: adding floor! //FADE
                                for (int l = 1; l <= cvsection; l++) { //FADE: l is a zone counter only for ground slab
                                    for (int j = 0; j < WallLayer - 1; j++) {
                                        Manage.caseManage[caseCount].addTMass(j + (l - 1) * WallLayer, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB20080828                           
//                                    System.out.println("Added layer "+(j + (l - 1) * WallLayer)+" no absorption ceiling");
                                        Manage.caseManage[caseCount].createTMassIntConnection(j + (l - 1) * WallLayer, j + (l - 1) * WallLayer + 1);
//                                    System.out.println("Connected layer "+(j + (l - 1) * WallLayer)+" and layer "+(j + (l - 1) * WallLayer + 1));
                                    }// Karine: no ceiling connection
                                    Manage.caseManage[caseCount].addTMass(l * WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea, zonefloorperimeter, 2500, 880, "floor", TMassk, initialzonetemperature, 1);//karine corrected 06/13
//                                System.out.println("Added layer "+(l * WallLayer - 1)+" absorption floor");
                                    Manage.caseManage[caseCount].createTMassConnection(l * WallLayer - 1, l); //Karine: Connection between top of slab and airZone 1
//                                System.out.println("Connected layer "+(l * WallLayer - 1)+" and zone "+l);
                                }
                            }
                            for (int j = 0; j < WallLayer - 1; j++) {
//                            System.out.println("j = "+j);
                                Manage.caseManage[caseCount].addTMass(j + (i - 1 + cvsection) * WallLayer, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB 20080828
//                            System.out.println("Added layer "+(j + (i - 1 + cvsection) * WallLayer)+" no absorption ceiling");
                                Manage.caseManage[caseCount].createTMassIntConnection(j + (i - 1 + cvsection) * WallLayer, j + (i - 1 + cvsection) * WallLayer + 1);
//                            System.out.println("Connected layer "+(j + (i - 1 + cvsection) * WallLayer)+" and layer "+(j + (i - 1 + cvsection) * WallLayer + 1));
                            }
                            Manage.caseManage[caseCount].createTMassConnection((i - 1 + cvsection) * WallLayer, i);
//                        System.out.println("Connected layer "+((i - 1 + cvsection) * WallLayer)+" and zone "+i);
                            if (k < floornumber) {
                                Manage.caseManage[caseCount].addTMass((i + cvsection) * WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "floor", TMassk, initialzonetemperature, 1); //Karine: added floor TMASSes
//                            System.out.println("Added layer "+((i + cvsection) * WallLayer - 1)+" absorption floor");
                                Manage.caseManage[caseCount].createTMassConnection((i + cvsection) * WallLayer - 1, i + cvsection);//Karine: ceiling connection IS CORRECT NOW b4: Manage.caseManage[caseCount].createTMassConnection((i-1) * WallLayer , i-1);
//                            System.out.println("Connected layer "+((i + cvsection) * WallLayer - 1)+" and section "+(i + cvsection));
                            } else {
                                Manage.caseManage[caseCount].addTMass((i + cvsection) * WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //Karine: added floor TMASSes
//                            System.out.println("Added layer "+((i + cvsection) * WallLayer - 1)+" no absorption ceiling");
                            }
                        }
                    }
                    // Add openings
                    for (int i = (k - 1) * (cvsection + 1); i < k * (cvsection + 1); i++) { //FADE
                        if (i == (k - 1) * (cvsection + 1)) { //FADE
                            Manage.caseManage[caseCount].addOpening(i, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), windowheight + floorheight * (k - 1), "N_wall", 0, (i - k + 2), stratwindowheight, stratwindowsize, doubleOpeningsCheck, CdA); //Add east wall path //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
//                        System.out.println("Added opening "+i+" is Nwall and connects 0 and "+(i - k + 2));
                        } else if (i == k * (cvsection + 1) - 1) {
                            Manage.caseManage[caseCount].addOpening(i, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), windowheight + floorheight * (k - 1), "S_wall", 0, (i - k + 1), stratwindowheight, stratwindowsize, doubleOpeningsCheck, CdA); //Add west opening //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
//                        System.out.println("Added opening "+i+" is Swall and connects 0 and "+(i - k + 1));
                            if (fanCheck != 0 && fanCheck != 4) { //FADE: Opening becomes a fan
                                Manage.caseManage[caseCount].openingInputData[i].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
                            }
                        } else {
                            Manage.caseManage[caseCount].addOpening(i, Math.sqrt(cvinternalwindowsize), Math.sqrt(cvinternalwindowsize), cvinternalwindowheight + floorheight * (k - 1), "Inside", i - (k - 1), i - k + 2, floortoceilingheight * 1.0 / 2.0, cvinternalwindowsize, 0, CdB);
//                        System.out.println("Added opening "+i+" is Inside and connects "+ (i - (k - 1)) + " and "+(i - k + 2));
                        }
                    }
                }
                if (adiabaticWalls == 0) {
                    //Add walls: walls are two-layer TMasses with no heat capacity
                    int l = 0;
                    if (TMassCheck == 1) {
                        l = (cvsection * (floornumber + 1)) * WallLayer;
                    }
                    for (int i = 1; i <= floornumber; i++) {
//                    System.out.println("Floor# "+i);
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 0);
//                    System.out.println("Added TMass "+l);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
//                    System.out.println("Connected TMass "+l+ " and TMass "+(l+1));
                        Manage.caseManage[caseCount].createTMassConnection(l, (cvsection * (i - 1) + 1));//Connect inner layer
//                    System.out.println("Connected TMass "+l+" and zone "+(cvsection*(i-1)+1));
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 1);
//                    System.out.println("Added TMass "+l);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
//                    System.out.println("Connected TMass "+l+" and zone 0");
                        l++;
                        for (int j = 1 + (i - 1) * cvsection; j <= i * cvsection; j++) { //FADE
                            Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 0);
//                        System.out.println("Added TMass "+l);
                            Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
//                        System.out.println("Connected TMass "+l+ " and TMass "+(l+1));
                            Manage.caseManage[caseCount].createTMassConnection(l, j);//Connect inner layer
//                        System.out.println("Connected TMass "+l+" and zone "+j);
                            l++;
                            Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 1);
//                        System.out.println("Added TMass "+l);
                            Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
//                        System.out.println("Connected TMass "+l+" and zone 0");
                            l++;
                            Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 0);
//                        System.out.println("Added TMass "+l);
                            Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
//                        System.out.println("Connected TMass "+l+ " and TMass "+(l+1));
                            Manage.caseManage[caseCount].createTMassConnection(l, j);//Connect inner layer
//                        System.out.println("Connected TMass "+l+" and zone "+j);
                            l++;
                            Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 1);
//                        System.out.println("Added TMass "+l);
                            Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
//                        System.out.println("Connected TMass "+l+" and zone 0");
                            l++;
                        }
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 0);
//                    System.out.println("Added TMass "+l);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
//                    System.out.println("Connected TMass "+l+ " and TMass "+(l+1));
                        Manage.caseManage[caseCount].createTMassConnection(l, i * cvsection);//Connect inner layer
//                    System.out.println("Connected TMass "+l+" and zone "+(i*cvsection));
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 1);
//                    System.out.println("Added TMass "+l);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
//                    System.out.println("Connected TMass "+l+" and zone 0");
                        l++;
                    }
                }
            } else if (bldgtype == 4) { //sdr_hulic - added new construction for ventilation shaft building type //ventilation shaft zones and flow paths
                if (TMassCheck == 1) { //FADE
                    WallNum = floornumber + 1; //FADE: adding floor
                    TMassNum = WallNum * WallLayer;
                }
                //FADE: Add walls: ventilation shaft not included
                if (adiabaticWalls == 0) {
                    WallNum += 4 * floornumber; //FADE: (N+S+E+W)*#floor
                    TMassNum += 2 * (4 * floornumber); //FADE
                }
//                if (TMassCheck == 1) {
                Manage.caseManage[caseCount].setTMassNum(TMassNum); //Add from the left lower corner <-- ASSUMED TO BE NORTH
//                }
                for (int i = 1; i <= floornumber; i++) {
                    //sdr_hulic - changed how floor elevations are defined
                    Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, i * floorheight - ((1.0 / 2.0) * floorheight), initialzonetemperature, occupancyheatgain, "N_wall", i, bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov MAMB
                    if (TMassCheck == 1) {
                        if (i == 1) { // FADE: ground floor
                            for (int j = 0; j < WallLayer - 1; j++) {
                                Manage.caseManage[caseCount].addTMass(j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB20080828                           
                                Manage.caseManage[caseCount].createTMassIntConnection(j, j + 1);
                            }// Karine: no ceiling connection
                            Manage.caseManage[caseCount].addTMass(WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea, zonefloorperimeter, 2500, 880, "floor", TMassk, initialzonetemperature, 1);//karine corrected 06/13
                            Manage.caseManage[caseCount].createTMassConnection(WallLayer - 1, 1); //Karine: Connection between top of slab and airZone 1
                        }
                        for (int j = 0; j < WallLayer - 1; j++) {
                            Manage.caseManage[caseCount].addTMass(WallLayer + (i - 1) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "ceiling", TMassk, initialzonetemperature, 0); //MAMB20080828//Karine:changed 06/2013
                            Manage.caseManage[caseCount].createTMassIntConnection(WallLayer + (i - 1) * WallLayer + j, WallLayer + (i - 1) * WallLayer + j + 1);//Karine:changed 06/2013
                        }
                        Manage.caseManage[caseCount].createTMassConnection(WallLayer + (i - 1) * WallLayer + 0, i); // ceiling connection //connect TM 10 with Z 1, and TM 20 with Z 2, etc.

                        Manage.caseManage[caseCount].addTMass(WallLayer + i * WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea, zonefloorperimeter, 2500, 880, "floor", TMassk, initialzonetemperature, 1); //MAMB; Karine corrected 06/13
                        if (i < floornumber) {
                            Manage.caseManage[caseCount].createTMassConnection(WallLayer + i * WallLayer - 1, i + 1); //floor connection //connect TM 19 with Z 2, and TM 29 with Z 3
                        }
//                    for (int j = 0; j < WallLayer - 1; j++) {
//                    Manage.caseManage[caseCount].addTMass((i - 1) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, 2500, 880, hceiling, TMassk, initialzonetemperature, 0); //MAMB20080828
//                        Manage.caseManage[caseCount].createTMassIntConnection((i - 1) * WallLayer + j, (i - 1) * WallLayer + j + 1);
//                    }
//                Manage.caseManage[caseCount].addTMass(i * WallLayer - 1, zonefloorarea * 0.01, zonefloorarea, 2500, 880, hfloor, TMassk, initialzonetemperature, 1); //MAMB
//                    Manage.caseManage[caseCount].createTMassConnection((i - 1) * WallLayer + 0, i);
//                    if (i < floornumber) {
//                        Manage.caseManage[caseCount].createTMassConnection(i * WallLayer - 1, i + 1); //((i-1)*WallLayer + WallLayer-1, i+1);
//                    }
                    }
                }
                for (int i = floornumber + 1; i <= 2 * floornumber; i++) { //Add ventilation shaft1
                    Manage.caseManage[caseCount].addZone(i, shaft1Width * shaft1Length * floorheight, shaft1Width * shaft1Length, (i - floornumber) * floorheight - ((1.0 / 2.0) * floorheight), initialzonetemperature + 1, 0.0, "Internal", i % (floornumber + 1) + 1, bldgOrientation, 0, 0, false, floorlength); // does facade length = floorlength matter? //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium
                }
                Manage.caseManage[caseCount].addZone(2 * floornumber + 1, shaft1Width * shaft1Length * shaft1Height, shaft1Width * shaft1Length, floorheight * floornumber + 1.0 / 2.0 * shaft1Height, initialzonetemperature + 1, 0.0, "Roof", floornumber + 1, bldgOrientation, 0, 0, false, floorlength); //Add roof // does facade length = floorlength matter? //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium

                // if two shafts, add zones for second shaft
                if (numShafts == 2) {

                    for (int i = 2 * floornumber + 2; i <= 3 * floornumber + 1; i++) {
                        Manage.caseManage[caseCount].addZone(i, shaft2Width * shaft2Length * floorheight, shaft2Width * shaft2Length, (i - (2 * floornumber + 1)) * floorheight - 1.0 / 2.0 * floorheight, initialzonetemperature + 1, 0.0, "Internal", i % (floornumber + 1) + 1, bldgOrientation, 0, 0, false, floorlength); // does facade length = floorlength matter? //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium
                    }
                    Manage.caseManage[caseCount].addZone(3 * floornumber + 2, shaft2Width * shaft2Length * shaft2Height, shaft2Width * shaft2Length, floorheight * floornumber + 1.0 / 2.0 * shaft2Height, initialzonetemperature + 1, 0.0, "Roof", floornumber + 1, bldgOrientation, 0, 0, false, floorlength); //Add roof // does facade length = floorlength matter? //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium

                }
                for (int i = 0; i < floornumber; i++) { //Add east wall path
                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), floorheight * i + windowheight, "N_wall", 0, i + 1, stratwindowheight, stratwindowsize, doubleOpeningsCheck, CdA);//sdr_hulic use CdA for external opening //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much

                }
                // add openings for shaft1
                for (int i = floornumber; i < 2 * floornumber; i++) { //Add left internal openings
                    if (i == floornumber) { //FADE: Internal opening area is different at the gnd level //sdr_hulic ignores this difference for ventilation shafts
                        Manage.caseManage[caseCount].addOpening(i, Math.sqrt(shaft1Opening), Math.sqrt(shaft1Opening), intareaheight + ((i % floornumber) * floorheight), "InsideShaft1Grnd", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, shaft1Width * shaft1Length, 0, CdB); // add internal opening from occupied zone to shaft at ground floor

                    } else {
                        Manage.caseManage[caseCount].addOpening(i, Math.sqrt(shaft1Opening), Math.sqrt(shaft1Opening), intareaheight + ((i % floornumber) * floorheight), "InsideShaft1", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, shaft1Width * shaft1Length, 0, CdB); // add internal opening from occupied zone to shaft for all but ground floor

                    }
                }
                for (int i = 2 * floornumber; i < 3 * floornumber; i++) {
                    Manage.caseManage[caseCount].addOpening(i, shaft1Width, shaft1Length, floorheight * (i % floornumber + 1), "Shaft1", i + 1 - floornumber, i + 2 - floornumber, 0, 0, 0, CdC); //add internal shaft horizontal opening

                }
                Manage.caseManage[caseCount].addOpening(3 * floornumber, Math.sqrt(shaft1Exhaust), Math.sqrt(shaft1Exhaust), floorheight * floornumber + shaft1Height, "Roof1", 0, 2 * floornumber + 1, 0, 0, 0, CdD); //Add top opening

                //FADE: Top opening becomes a fan
                if (fanCheck != 0 && fanCheck != 4) {
                    Manage.caseManage[caseCount].openingInputData[3 * floornumber].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
                }
                if (numShafts == 2) {

                    // add openings for shaft2
                    for (int i = 3 * floornumber + 1; i < 4 * floornumber + 1; i++) { //Add left internal openings
                        if (i == 3 * floornumber + 1) { //FADE: Internal opening area is different at the gnd level //sdr_hulic ignores this difference for ventilation shafts
                            Manage.caseManage[caseCount].addOpening(i, Math.sqrt(shaft2Opening), Math.sqrt(shaft2Opening), intareaheight + (((i - 1 - floornumber) % floornumber) * floorheight), "InsideShaft2Grnd", i - 3 * floornumber, i - (floornumber - 1), floortoceilingheight * 0.5, shaft2Width * shaft2Length, 0, CdB);

                        } else {
                            Manage.caseManage[caseCount].addOpening(i, Math.sqrt(shaft2Opening), Math.sqrt(shaft2Opening), intareaheight + (((i - 1 - floornumber) % floornumber) * floorheight), "InsideShaft2", i - 3 * floornumber, i - (floornumber - 1), floortoceilingheight * 0.5, shaft2Width * shaft2Length, 0, CdB); //Add internal horizontal openings

                        }
                    }
                    for (int i = 4 * floornumber + 1; i < 5 * floornumber + 1; i++) {
                        Manage.caseManage[caseCount].addOpening(i, shaft2Width, shaft2Length, floorheight * ((i - 1 - floornumber) % floornumber + 1), "Shaft2", i + 1 - 2 * floornumber, i + 2 - 2 * floornumber, 0, 0, 0, CdC);

                    }
                    Manage.caseManage[caseCount].addOpening(5 * floornumber + 1, Math.sqrt(shaft2Exhaust), Math.sqrt(shaft2Exhaust), floorheight * floornumber + shaft2Height, "Roof2", 0, 3 * floornumber + 2, 0, 0, 0, CdD); //Add top opening
                    //sdr_hulic adds fan to second shaft
                    if (fanCheck != 0 && fanCheck != 4) {
                        Manage.caseManage[caseCount].openingInputData[5 * floornumber + 1].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
                    }
                } //end if shafts = 2
                //Add walls: walls are two-layer TMasses with no heat capacity
                if (adiabaticWalls == 0) {
                    int l = 0;
                    if (TMassCheck == 1) {
                        l = (floornumber + 1) * WallLayer;
                    }
                    for (int i = 1; i <= floornumber; i++) { //Add right tower
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight - sidewindowsize) * 5e-2, (floorlength * floorheight - sidewindowsize), 2 * (floorlength + floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * floorheight * 5e-2, floorwidth * floorheight, 2 * (floorwidth + floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight) * 5e-2, (floorlength * floorheight), 2 * (floorlength + floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * floorheight) * 5e-2, (floorlength * floorheight), 2 * (floorlength + floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                    }
                }
            } //sdr_hulic end else_if for vent shaft
            //Single-sided zones and flow paths
            else {
                if (TMassCheck == 1) { //FADE
                    WallNum = floornumber + 1; //FADE: multistory building
                    TMassNum = WallNum * WallLayer;
//                  WallNum = sslayer; //Karine: it is 2 by default
//                  TMassNum = WallNum * WallLayer;
                }
                //FADE: Add walls
                if (adiabaticWalls == 0) {
                    WallNum += 2 * 4 * floornumber; //FADE: (N+S+E+W)*2zones*#floor
                    TMassNum += 2 * (2 * 4 * floornumber); //FADE
//                System.out.println("WallNum "+WallNum+" TMassNum "+TMassNum);
                }
//                if (TMassCheck == 1) {
                Manage.caseManage[caseCount].setTMassNum(TMassNum);
//                }
                //FADE thinks this ratio1 thing is wrong. For a single window it sets zone 1 height at 1/8 floorheight, and the other zone at 5/8 floorheight. Use windowheight instead
//            double ratio1 = (lowerwindowheight + upperwindowheight) / 2.0 / floorheight;
                double ratio1 = windowheight / floorheight;
                double ratio2 = 1 - ratio1;
                //<editor-fold defaultstate="collapsed" desc="Console output">
//                System.out.println("doubleOpeningsCheck " + doubleOpeningsCheck);
//             System.out.println("lowerwindowsize " + lowerwindowsize);
                //</editor-fold>
                for (int k = 1; k <= floornumber; k++) { //FADE
                    for (int i = 1; i <= sslayer; i++) { // Karine: only difference betw Zone 1 and 2 is the heat load allocation
                        if (i == 1) {
                            //Manage.caseManage[caseNum].addZone(i,floorwidth*floorlength*ratio1*floorheight, floorlength*floorwidth, ratio1*floorheight/2.0, initialzonetemperature+Math.random(), OccupancyHeatLoads*floorwidth*floorlength*ratio1, "N_wall", bldgOrientation, sideglazingarea); //This assigns the heat sources to the lower zone corresponding to ratio1
                            Manage.caseManage[caseCount].addZone(i + (k - 1) * sslayer, zonefloorarea * ratio1 * floorheight, zonefloorarea, floorheight * (k - 1 + ratio1 / 2.0), initialzonetemperature, occupancyheatgain, "N_wall", k, bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //This assigns all heat sources to the lower zone
//                        System.out.println("Added zone "+(i + (k - 1) * sslayer)+" height "+(floorheight * (k-1 + ratio1/2.0)));
                            if (TMassCheck == 1) {
                                if (k == 1) //FADE: Ground
                                {
                                    for (int j = 0; j < WallLayer - 1; j++) {
                                        Manage.caseManage[caseCount].addTMass(j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "Floor", TMassk, initialzonetemperature, 0); //MAMB20080828
//                                    System.out.println("Added layer "+j+" no absorption Floor");
                                        Manage.caseManage[caseCount].createTMassIntConnection(j, j + 1);
//                                    System.out.println("Connected layer "+j+" and layer "+(j+1));
                                    }
                                    Manage.caseManage[caseCount].addTMass(WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "Floor", TMassk, initialzonetemperature, 1); //MAMB20080828
//                                System.out.println("Added layer "+(WallLayer - 1)+" absorption Floor");
                                    Manage.caseManage[caseCount].createTMassConnection(WallLayer - 1, i);
//                                System.out.println("Connected layer "+(WallLayer - 1)+" and zone "+i);
                                }
                            }//Karine: connection to zone 1, i.e. i
                        } else {
                            //Manage.caseManage[caseNum].addZone(i,floorwidth*floorlength*ratio2*floorheight, floorlength*floorwidth, (ratio1 + ratio2/2.0)*floorheight, initialzonetemperature+Math.random(), OccupancyHeatLoads*floorwidth*floorlength*ratio2, "N_wall", bldgOrientation, upperwindowsize+lowerwindowsize); //This assigns the heat sources to the upper zone corresponding to ratio2
                            Manage.caseManage[caseCount].addZone(i + (k - 1) * sslayer, zonefloorarea * ratio2 * floorheight, zonefloorarea, (k - 1 + ratio1 + ratio2 / 2.0) * floorheight, initialzonetemperature, occupancyheatgain * 0, "N_wall", k, bldgOrientation, upperwindowsize + lowerwindowsize, 0, false, floorlength); //This does not assign any all heat sources to the upper zone
//                        System.out.println("Added zone "+(i + (k - 1) * sslayer)+" height "+((k - 1 + ratio1 + ratio2 / 2.0) * floorheight));
                            if (TMassCheck == 1) {
                                for (int j = 0; j < WallLayer - 1; j++) {
                                    Manage.caseManage[caseCount].addTMass(k * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "Ceiling", TMassk, initialzonetemperature, 0); //MAMB 20080828
//                                System.out.println("Added layer "+(k * WallLayer + j)+" no absorption ceiling");
                                    Manage.caseManage[caseCount].createTMassIntConnection(k * WallLayer + j, k * WallLayer + j + 1);
//                                System.out.println("Connected layer "+(k * WallLayer + j)+" and zone "+(k * WallLayer + j + 1));
                                }
                                Manage.caseManage[caseCount].addTMass(WallLayer * (k + 1) - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, zonefloorperimeter, 2500, 880, "Floor", TMassk, initialzonetemperature, 1); //MAMB 20080828
//                            System.out.println("Added layer "+(WallLayer * (k + 1) - 1)+" absorption Floor");
                                Manage.caseManage[caseCount].createTMassConnection(k * WallLayer, i + (k - 1) * sslayer); // Karine: fixed ceiling thermal mass layer numbering, b4 Manage.caseManage[caseCount].createTMassConnection((i - 1) * WallLayer, i - 1);
//                            System.out.println("Connected layer "+(k * WallLayer)+" and zone "+(i + (k - 1) * sslayer));
                                if (k < floornumber) {
                                    Manage.caseManage[caseCount].createTMassConnection(WallLayer * (k + 1) - 1, k * sslayer + 1);
//                                System.out.println("Connected layer "+(WallLayer * (k + 1) - 1)+" and zone "+(k * sslayer + 1));
                                }
                            }//Karine: connection to zone 2, i.e. i+1
                        }
                    }
                    Manage.caseManage[caseCount].addOpening((k - 1) * 3, Math.sqrt(lowerwindowsize), Math.sqrt(lowerwindowsize), (k - 1) * floorheight + lowerwindowheight, "N_wall", 0, 2 * k - 1, lowerwindowheight, upperwindowsize, 0, 0); //East wall path
//            System.out.println("Added opening "+((k - 1) * 3)+" connecting zone 0 and "+(2*k - 1));
                    Manage.caseManage[caseCount].addOpening(3 * k - 2, Math.sqrt(floorlength * floorwidth), Math.sqrt(floorlength * floorwidth), (k - 1 + ratio1) * floorheight, "Inside", 2 * k - 1, 2 * k, 0, 0, 0, 0);
//            System.out.println("Added opening "+(3*k - 2)+" connecting zone "+ (2*k - 1) +" and "+(2*k));
                    Manage.caseManage[caseCount].addOpening(3 * k - 1, Math.sqrt(upperwindowsize), Math.sqrt(upperwindowsize), (k - 1) * floorheight + upperwindowheight, "N_wall", 0, 2 * k, upperwindowheight, upperwindowsize, 0, 0); //West opening
//            System.out.println("Added opening "+(3*k - 1)+" connecting zone 0 and "+(2*k));
                }
                //Add walls: walls are two-layer TMasses with no heat capacity
                if (adiabaticWalls == 0) {
                    int l = 0;
                    if (TMassCheck == 1) {
                        l = (floornumber + 1) * WallLayer;
                    }
                    for (int i = 1; i <= floornumber * 2; i += 2) {
//                    System.out.println("Semifloor# "+i);
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * ratio1 * floorheight - ratio1 * sidewindowsize) * 5e-2, (floorlength * ratio1 * floorheight - ratio1 * sidewindowsize), 2 * (floorlength + ratio1 * floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * ratio1 * floorheight - ratio1 * sidewindowsize) * 5e-2, (floorlength * ratio1 * floorheight - ratio1 * sidewindowsize), 2 * (floorlength + ratio1 * floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * ratio2 * floorheight - ratio2 * sidewindowsize) * 5e-2, (floorlength * ratio2 * floorheight - ratio2 * sidewindowsize), 2 * (floorlength + ratio2 * floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i + 1);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * ratio2 * floorheight - ratio2 * sidewindowsize) * 5e-2, (floorlength * ratio2 * floorheight - ratio2 * sidewindowsize), 2 * (floorlength + ratio2 * floorheight), 0, 880, "N_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * ratio1 * floorheight * 5e-2, floorwidth * ratio1 * floorheight, 2 * (floorwidth + ratio1 * floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * ratio1 * floorheight * 5e-2, floorwidth * ratio1 * floorheight, 2 * (floorwidth + ratio1 * floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * ratio2 * floorheight * 5e-2, floorwidth * ratio2 * floorheight, 2 * (floorwidth + ratio2 * floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i + 1);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * ratio2 * floorheight * 5e-2, floorwidth * ratio2 * floorheight, 2 * (floorwidth + ratio2 * floorheight), 0, 880, "E_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * ratio1 * floorheight * 5e-2, floorwidth * ratio1 * floorheight, 2 * (floorwidth + ratio1 * floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * ratio1 * floorheight * 5e-2, floorwidth * ratio1 * floorheight, 2 * (floorwidth + ratio1 * floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * ratio2 * floorheight * 5e-2, floorwidth * ratio2 * floorheight, 2 * (floorwidth + ratio2 * floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i + 1);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, floorwidth * ratio2 * floorheight * 5e-2, floorwidth * ratio2 * floorheight, 2 * (floorwidth + ratio2 * floorheight), 0, 880, "W_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * ratio1 * floorheight) * 5e-2, (floorlength * ratio1 * floorheight), 2 * (floorlength + ratio1 * floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * ratio1 * floorheight) * 5e-2, (floorlength * ratio1 * floorheight), 2 * (floorlength + ratio1 * floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * ratio2 * floorheight) * 5e-2, (floorlength * ratio2 * floorheight), 2 * (floorlength + ratio2 * floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 0);
                        Manage.caseManage[caseCount].createTMassIntConnection(l, l + 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, i + 1);//Connect inner layer
                        l++;
                        Manage.caseManage[caseCount].addTMass(l, (floorlength * ratio2 * floorheight) * 5e-2, (floorlength * ratio2 * floorheight), 2 * (floorlength + ratio2 * floorheight), 0, 880, "S_wall", TMassk, initialzonetemperature, 1);
                        Manage.caseManage[caseCount].createTMassConnection(l, 0);//Connect outer layer
                        l++;
                    }
                }
            }
                        
            //<editor-fold defaultstate="collapsed" desc="FADENEW: Old code">
            //        //Luton zones and flow paths
            //        if (bldgtype == 0) {
            //            WallNum = 2 * floornumber;
            //            TMassNum = WallNum * WallLayer;
            //            if (TMassCheck == 1) {
            //                Manage.caseManage[caseCount].setTMassNum(TMassNum); //Add from the left lower corner -->ASSUMED TO BE North Facade
            //            }
            //            for (int i = 1; i <= floornumber; i++) { //FADE: Add right tower
            //                Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * ((i - 1) % floornumber + 1.0 / 2.0), initialzonetemperature, occupancyheatgain, "N_wall", bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov modified by MAMB
            //                if (TMassCheck == 1) {
            //                    for (int j = 0; j < WallLayer - 1; j++) { //From j=0 to 8, 10 to 18, etc -->hceiling (weird)
            //                        Manage.caseManage[caseCount].addTMass((i - 1) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, 2500, 880, hceiling, TMassk, initialzonetemperature, 0); //MAMB 20080828
            //                        Manage.caseManage[caseCount].createTMassIntConnection((i - 1) * WallLayer + j, (i - 1) * WallLayer + j + 1);
            //                    }
            //                    Manage.caseManage[caseCount].addTMass(i * WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, 2500, 880, hfloor, TMassk, initialzonetemperature, 1); //20070924 modified by MAMB //for j=9, 19 -->hfloor
            //                    Manage.caseManage[caseCount].createTMassConnection((i - 1) * WallLayer + 0, i); // ceiling connection //connect TM 0 with Z 1, and TM 10 with Z 2, etc.
            //                    if (i < floornumber) { //for i = 1 to floornumber-1
            //                        Manage.caseManage[caseCount].createTMassConnection((i - 1) * WallLayer + WallLayer - 1, i + 1); //floor connection //connect TM 9 with Z 2, and TM 19 with Z 3
            //                    }
            //                }
            //            }
            //            for (int i = floornumber + 1; i <= 2 * floornumber; i++) { //FADE: Add atrium
            //                Manage.caseManage[caseCount].addZone(i, atriumwidth * floorlength * floorheight, floorlength * atriumwidth, floorheight * ((i - 1) % floornumber + 1.0 / 2.0), initialzonetemperature + 1, 0.0, "Atrium", bldgOrientation, 0, 0, false, floorlength); //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium //FADE: Why is the zoneAirTemp = initialzonetemperature+1?
            //            }
            //            Manage.caseManage[caseCount].addZone(2 * floornumber + 1, atriumwidth * floorlength * roofheight, floorlength * atriumwidth, floorheight * floornumber + 1.0 / 2.0 * roofheight, initialzonetemperature, 0.0, "Roof", bldgOrientation, 0, 0, false, floorlength); //FADE: Add roof //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium
            //            for (int i = 2 * floornumber + 2; i <= 3 * floornumber + 1; i++) { //FADE: Add left tower
            //                Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * ((i - 2) % floornumber + 1.0 / 2.0), initialzonetemperature, occupancyheatgain, "S_wall", bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov modified by MAMB
            //                if (TMassCheck == 1) {
            //                    for (int j = 0; j < WallLayer - 1; j++) {
            //                        Manage.caseManage[caseCount].addTMass((i - 2 * floornumber - 2 + floornumber) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, 2500, 880, hceiling, TMassk, initialzonetemperature, 0); //MAMB 20080828
            //                        Manage.caseManage[caseCount].createTMassIntConnection((i - 2 * floornumber - 2 + floornumber) * WallLayer + j, (i - 2 * floornumber - 2 + floornumber) * WallLayer + j + 1);
            //                    }
            //                    Manage.caseManage[caseCount].addTMass((i - 2 * floornumber - 1 + floornumber) * WallLayer - 1, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, 2500, 880, hfloor, TMassk, initialzonetemperature, 1); //MAMB 20080828
            //                    Manage.caseManage[caseCount].createTMassConnection((i - 2 * floornumber - 2 + floornumber) * WallLayer, i); //Connect
            //                    if (i < 3 * floornumber + 1) {
            //                        Manage.caseManage[caseCount].createTMassConnection((i - 2 * floornumber - 1 + floornumber) * WallLayer - 1, i + 1);
            //                    }
            //                }
            //            }
            //            //Add openings: public void addOpening(int flowPathCount, double openingH, double openingW, double openingElev, String openingLocation, int connectNum1, int connectNum2, double upperopeningheight, double upperopeningarea, int doubleopeningcheck, double cd)
            //            for (int i = 0; i < floornumber; i++) { //FADE: Add east external opening
            //                Manage.caseManage[caseCount].addOpening(i, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), floorheight * i + windowheight, "N_wall", 0, i + 1, stratwindowheight, stratwindowsize, doubleOpeningsCheck, 0); //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
            //            }
            //            for (int i = floornumber; i < 2 * floornumber; i++) { //FADE: Add east internal openings
            //                if (i == floornumber) { //FADE: Internal opening area is different at the gnd level
            //                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intgndarea), Math.sqrt(intgndarea), intgndareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, intarea, 0, cdgndCheck);
            //                } else {
            //                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intarea), Math.sqrt(intarea), intareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, intarea, 0, cdCheck);
            //                }
            //            }
            //            for (int i = 2 * floornumber; i < 3 * floornumber; i++) { //FADE: Add west internal openings
            //                if (i == 2 * floornumber) { //FADE: Internal opening area is different at the gnd level
            //                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intgndarea), Math.sqrt(intgndarea), intgndareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 2, floortoceilingheight * 0.5, intarea, 0, cdgndCheck);
            //                } else {
            //                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intarea), Math.sqrt(intarea), intareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 2, floortoceilingheight * 0.5, intarea, 0, cdCheck);
            //                }
            //            }
            //            for (int i = 3 * floornumber; i < 4 * floornumber; i++) { //FADE: Add west external openings
            //                Manage.caseManage[caseCount].addOpening(i, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), floorheight * (i % floornumber) + windowheight, "S_wall", 0, i - floornumber + 2, stratwindowheight, stratwindowsize, doubleOpeningsCheck, 0); //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
            //            }
            //            for (int i = 4 * floornumber; i < 5 * floornumber; i++) { //FADE: Add internal opening (atrium)
            //                Manage.caseManage[caseCount].addOpening(i, atriumwidth, floorlength, floorheight * (i % floornumber + 1), "Atrium", (i % floornumber) + 1 + floornumber, (i % floornumber) + 2 + floornumber, 0, 0, 0, 0); //FADE: Changed the opening location 'inside' for 'atrium'
            //            }
            //            Manage.caseManage[caseCount].addOpening(5 * floornumber, Math.sqrt(topwindowsize), Math.sqrt(topwindowsize), floorheight * floornumber + roofheight, "Roof", 0, 2 * floornumber + 1, 0, 0, 0, 0); //Add top opening (roof)
            //            //FADE: Top opening becomes a fan
            //            if (fanCheck != 0 && fanCheck != 4) {
            //                Manage.caseManage[caseCount].openingInputData[5 * floornumber].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
            //            }
            //            //Chimney zones and flow paths
            //        } else if (bldgtype == 1) {
            //            WallNum = floornumber;
            //            TMassNum = WallNum * WallLayer;
            //            if (TMassCheck == 1) {
            //                Manage.caseManage[caseCount].setTMassNum(TMassNum); //Add from the left lower corner <-- ASSUMED TO BE NORTH
            //            }
            //            for (int i = 1; i <= floornumber; i++) {
            //                Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * (i % floornumber - 1.0 / 2.0), initialzonetemperature, occupancyheatgain, "N_wall", bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov MAMB
            //                if (TMassCheck == 1) {
            //                    for (int j = 0; j < WallLayer - 1; j++) {
            //                        Manage.caseManage[caseCount].addTMass((i - 1) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, 2500, 880, hceiling, TMassk, initialzonetemperature, 0); //MAMB20080828
            //                        Manage.caseManage[caseCount].createTMassIntConnection((i - 1) * WallLayer + j, (i - 1) * WallLayer + j + 1);
            //                    }
            //                    Manage.caseManage[caseCount].addTMass(i * WallLayer - 1, zonefloorarea * 0.01, zonefloorarea, 2500, 880, hfloor, TMassk, initialzonetemperature, 1); //MAMB
            //                    Manage.caseManage[caseCount].createTMassConnection((i - 1) * WallLayer + 0, i);
            //                    if (i < floornumber) {
            //                        Manage.caseManage[caseCount].createTMassConnection(i * WallLayer - 1, i + 1); //((i-1)*WallLayer + WallLayer-1, i+1);
            //                    }
            //                }
            //            }
            //            for (int i = floornumber + 1; i <= 2 * floornumber; i++) { //Add chimney zones
            //                Manage.caseManage[caseCount].addZone(i, atriumwidth * floorlength * floorheight, floorlength * atriumwidth, floorheight * (i % floornumber - 1.0 / 2.0), initialzonetemperature + 1, 0.0, "Internal", bldgOrientation, 0, 0, false, floorlength); //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium
            //            }
            //            Manage.caseManage[caseCount].addZone(2 * floornumber + 1, atriumwidth * floorlength * roofheight, floorlength * atriumwidth, floorheight * floornumber + 1.0 / 2.0 * roofheight, initialzonetemperature, 0.0, "Roof", bldgOrientation, 0, 0, false, floorlength); //Add roof //2011 Nov MAMB: all zeros at the end are because stratification can't be estimated in atrium
            //            for (int i = 0; i < floornumber; i++) { //Add east wall path
            //                Manage.caseManage[caseCount].addOpening(i, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), floorheight * i + windowheight, "N_wall", 0, i + 1, stratwindowheight, stratwindowsize, doubleOpeningsCheck, 0); //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
            //            }
            //            for (int i = floornumber; i < 2 * floornumber; i++) { //Add left internal openings
            //                if (i == floornumber) { //FADE: Internal opening area is different at the gnd level
            //                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intgndarea), Math.sqrt(intgndarea), intgndareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, intarea, 0, cdgndCheck);
            //                } else {
            //                    Manage.caseManage[caseCount].addOpening(i, Math.sqrt(intarea), Math.sqrt(intarea), intareaheight + ((i % floornumber) * floorheight), "Inside", i + 1 - floornumber, i + 1, floortoceilingheight * 0.5, intarea, 0, cdCheck); //Add internal horizontal openings
            //                }
            //            }
            //            for (int i = 2 * floornumber; i < 3 * floornumber; i++) {
            //                Manage.caseManage[caseCount].addOpening(i, atriumwidth, floorlength, floorheight * (i % floornumber + 1), "Atrium", i + 1 - floornumber, i + 2 - floornumber, 0, 0, 0, 0);
            //            }
            //            Manage.caseManage[caseCount].addOpening(3 * floornumber, Math.sqrt(topwindowsize), Math.sqrt(topwindowsize), floorheight * floornumber + roofheight, "Roof", 0, 2 * floornumber + 1, 0, 0, 0, 0); //Add top opening
            //            //FADE: Top opening becomes a fan
            //            if (fanCheck != 0 && fanCheck != 4) {
            //                Manage.caseManage[caseCount].openingInputData[3 * floornumber].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
            //            }
            //            //Cross-ventilation zones and flow paths
            //        } else if (bldgtype == 2) {
            //            WallNum = cvsection;
            //            TMassNum = WallNum * WallLayer;
            //            if (TMassCheck == 1) {
            //                Manage.caseManage[caseCount].setTMassNum(TMassNum);
            //            }
            //            for (int i = 1; i <= cvsection; i++) {
            //                if (i == 1) {
            //                    Manage.caseManage[caseCount].addZone(1, zonevolume, zonefloorarea, floorheight * 1.0 / 2.0, initialzonetemperature, occupancyheatgain, "N_wall", bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov modified by MAMB); //20071228 MAMB removed Math.random() in zone temperature on the above lines
            //                } else if (i == cvsection) {
            //                    Manage.caseManage[caseCount].addZone(cvsection, zonevolume, zonefloorarea, floorheight * 1.0 / 2.0, initialzonetemperature, occupancyheatgain, "S_wall", bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //2011 Nov modified by MAMB); //20071228 MAMB removed Math.random() in zone temperature on the above lines
            //                } else {
            //                    Manage.caseManage[caseCount].addZone(i, zonevolume, zonefloorarea, floorheight * 1.0 / 2.0, initialzonetemperature, occupancyheatgain, "Internal", bldgOrientation, 0, floortoceilingheight, true, floorlength); //20071228 MAMB removed Math.random() in zone temperature on the above lines
            //                }
            //                if (TMassCheck == 1) {
            //                    for (int j = 0; j <= WallLayer - 1; j++) {
            //                        Manage.caseManage[caseCount].addTMass((i - 1) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, 2500, 880, hceiling, TMassk, initialzonetemperature, 0); //MAMB 20080828
            //                        if (j < WallLayer - 1) {
            //                            Manage.caseManage[caseCount].createTMassIntConnection((i - 1) * WallLayer + j, (i - 1) * WallLayer + j + 1);
            //                        }
            //                    }
            //                    Manage.caseManage[caseCount].createTMassConnection((i - 1) * WallLayer, i - 1);
            //                }
            //            }
            //
            //            Manage.caseManage[caseCount].addOpening(0, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), windowheight, "N_wall", 0, 1, stratwindowheight, stratwindowsize, doubleOpeningsCheck, 0); //Add east wall path //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
            //            for (int i = 1; i < cvsection; i++) {
            //                Manage.caseManage[caseCount].addOpening(i, Math.sqrt(cvinternalwindowsize), Math.sqrt(cvinternalwindowsize), cvinternalwindowheight, "Inside", i, i + 1, floortoceilingheight * 1.0 / 2.0, cvinternalwindowsize, 0, cdCheck);
            //            }
            //                Manage.caseManage[caseCount].addOpening(cvsection, Math.sqrt(sidewindowsize), Math.sqrt(sidewindowsize), upperwindowheightW, "S_wall", 0, cvsection, stratwindowheight, stratwindowsize, doubleOpeningsCheck, 0); //Add west opening //2011 Nov MAMB now windowheight is defined by the user -- shouldn't change the buoyancy calcs that much
            //            //FADE: Opening becomes a fan
            //            if (fanCheck != 0 && fanCheck != 4) {
            //                Manage.caseManage[caseCount].openingInputData[cvsection].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
            //            }
            //            //Single-sided zones and flow paths
            //        } else {
            //            WallNum = sslayer;
            //            TMassNum = WallNum * WallLayer;
            //            if (TMassCheck == 1) {
            //                Manage.caseManage[caseCount].setTMassNum(TMassNum);
            //            }
            //            double ratio1 = (lowerwindowheight + upperwindowheight) / 2.0 / floorheight;
            //            double ratio2 = 1 - ratio1;
            //            //<editor-fold defaultstate="collapsed" desc="Console output">
            //                /*System.out.println("ratio1" + ratio1);
            //            //System.out.println("ratio2" + ratio2);*/
            //            //</editor-fold>
            //            for (int i = 1; i <= sslayer; i++) {
            //                if (i == 1) {
            //                    //Manage.caseManage[caseNum].addZone(i,floorwidth*floorlength*ratio1*floorheight, floorlength*floorwidth, ratio1*floorheight/2.0, initialzonetemperature+Math.random(), OccupancyHeatLoads*floorwidth*floorlength*ratio1, "N_wall", bldgOrientation, sideglazingarea); //This assigns the heat sources to the lower zone corresponding to ratio1
            //                    Manage.caseManage[caseCount].addZone(i, zonefloorarea * ratio1 * floorheight, zonefloorarea, ratio1 * floorheight / 2.0, initialzonetemperature, occupancyheatgain, "N_wall", bldgOrientation, sideglazingarea, floortoceilingheight, true, floorlength); //This assigns all heat sources to the lower zone
            //                    if (TMassCheck == 1) {
            //                        for (int j = 0; j <= WallLayer - 1; j++) {
            //                            Manage.caseManage[caseCount].addTMass((i - 1) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, 2500, 880, hfloor, TMassk, initialzonetemperature, 1); //MAMB20080828
            //                            if (j < WallLayer - 1) {
            //                                Manage.caseManage[caseCount].createTMassIntConnection((i - 1) * WallLayer + j, (i - 1) * WallLayer + j + 1);
            //                            }
            //                        }
            //                        Manage.caseManage[caseCount].createTMassConnection((i - 1) * WallLayer, i - 1); //20080326 MAMB MAMBTHIS IS WRONG! It is connecting the thermal mass to zone 0! (=i-1)
            //                    }
            //                } else {//if (i== sslayer)
            //                    //Manage.caseManage[caseNum].addZone(i,floorwidth*floorlength*ratio2*floorheight, floorlength*floorwidth, (ratio1 + ratio2/2.0)*floorheight, initialzonetemperature+Math.random(), OccupancyHeatLoads*floorwidth*floorlength*ratio2, "N_wall", bldgOrientation, upperwindowsize+lowerwindowsize); //This assigns the heat sources to the upper zone corresponding to ratio2
            //                    Manage.caseManage[caseCount].addZone(i, zonefloorarea * ratio2 * floorheight, zonefloorarea, (ratio1 + ratio2 / 2.0) * floorheight, initialzonetemperature, occupancyheatgain * 0, "N_wall", bldgOrientation, upperwindowsize + lowerwindowsize, 0, false, floorlength); //This does not assign any all heat sources to the upper zone
            //                    if (TMassCheck == 1) {
            //                        for (int j = 0; j <= WallLayer - 1; j++) {
            //                            Manage.caseManage[caseCount].addTMass((i - 1) * WallLayer + j, zonefloorarea * TMassAreaFraction * TMassThick / WallLayer, zonefloorarea * TMassAreaFraction, 2500, 880, hceiling, TMassk, initialzonetemperature, 0); //MAMB 20080828
            //                            if (j < WallLayer - 1) {
            //                                Manage.caseManage[caseCount].createTMassIntConnection((i - 1) * WallLayer + j, (i - 1) * WallLayer + j + 1);
            //                            }
            //                        }
            //                        Manage.caseManage[caseCount].createTMassConnection((i - 1) * WallLayer, i - 1);
            //                    }
            //                }
            //            }
            //            Manage.caseManage[caseCount].addOpening(0, Math.sqrt(lowerwindowsize), Math.sqrt(lowerwindowsize), lowerwindowheight, "N_wall", 0, 1, lowerwindowheight, upperwindowsize, 0, 0); //East wall path
            //            Manage.caseManage[caseCount].addOpening(1, Math.sqrt(floorlength * floorwidth), Math.sqrt(floorlength * floorwidth), ratio1 * floorheight, "Inside", 1, 2, 0, 0, 0, 0);
            //            Manage.caseManage[caseCount].addOpening(sslayer, Math.sqrt(upperwindowsize), Math.sqrt(upperwindowsize), upperwindowheight, "N_wall", 0, sslayer, upperwindowheight, upperwindowsize, 0, 0); //West opening
            //        }
            //</editor-fold>
            //FADENEW: New thermal mass connections from Karine-----------------------------------------------------------------------------------------
        } else {//SUPERFADE
            zoneNum = 0;
            openingNum = 0;
            int TMassNum = 0;
            Manage Manage = new Manage(caseNum);
            Manage.addCase(caseCount);
            while ((line = br.readLine()) != null) {
                int j = 0;
                String datavalue[] = line.split("\t");
                if (datavalue[0].equals("A")) { //Ambient Zone
                    Manage.caseManage[caseCount].addAmbientZone(Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]));
                } else if (datavalue[0].equals("Z")) { //Zones
                    Manage.caseManage[caseCount].addZone(Integer.parseInt(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Boolean.parseBoolean(datavalue[++j]));
                    zoneNum++;
                } else if (datavalue[0].equals("O")) { //Openings
                    Manage.caseManage[caseCount].addOpening(Integer.parseInt(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), datavalue[++j], Double.valueOf(datavalue[++j]), Integer.parseInt(datavalue[++j]), Integer.parseInt(datavalue[++j]), Double.valueOf(datavalue[++j]));
                    if (Boolean.parseBoolean(datavalue[++j])) { //Fan
                        Manage.caseManage[caseCount].openingInputData[Integer.parseInt(datavalue[1])].turnintoFan(Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), Double.valueOf(datavalue[++j]), datavalue[++j]);
                    }
                    openingNum++;
                } else if (datavalue[0].equals("M")) { //Thermal mass
                    for (int i = Integer.parseInt(datavalue[1]); i < Integer.parseInt(datavalue[2]); i++) {
                        Manage.caseManage[caseCount].addTMass(i, Double.valueOf(datavalue[3]), Double.valueOf(datavalue[4]), Double.valueOf(datavalue[5]), Double.valueOf(datavalue[6]), Double.valueOf(datavalue[7]), datavalue[8], Double.valueOf(datavalue[9]), Double.valueOf(datavalue[10]), Integer.parseInt(datavalue[11]));
                        Manage.caseManage[caseCount].createTMassIntConnection(i, i + 1);
                        TMassNum++;
                    }
                    Manage.caseManage[caseCount].addTMass(Integer.parseInt(datavalue[2]), Double.valueOf(datavalue[3]), Double.valueOf(datavalue[4]), Double.valueOf(datavalue[5]), Double.valueOf(datavalue[6]), Double.valueOf(datavalue[7]), datavalue[8], Double.valueOf(datavalue[9]), Double.valueOf(datavalue[10]), Integer.parseInt(datavalue[11]));
                    TMassNum++;
                    if (Integer.parseInt(datavalue[12]) >= 0) {
                        Manage.caseManage[caseCount].createTMassConnection(Integer.parseInt(datavalue[1]), Integer.parseInt(datavalue[12]));
                    }
                    if (Integer.parseInt(datavalue[13]) >= 0) {
                        Manage.caseManage[caseCount].createTMassConnection(Integer.parseInt(datavalue[2]), Integer.parseInt(datavalue[13]));
                    }
                } else if (datavalue[0].equals("S")) { //Settings
                    Manage.caseManage[caseCount].setOccupancyScheduleOn(Integer.parseInt(datavalue[++j]));
                    Manage.caseManage[caseCount].setOccupancyScheduleOff(Integer.parseInt(datavalue[++j]));
                    Manage.caseManage[caseCount].setOffPeakLoadFrac(Double.valueOf(datavalue[++j]));
                    Manage.caseManage[caseCount].setTAC(Double.valueOf(datavalue[++j]));
                    Manage.caseManage[caseCount].setOmegaAC(Double.valueOf(datavalue[++j]));
                    Manage.caseManage[caseCount].setTFan(Double.valueOf(datavalue[++j]));
                    Manage.caseManage[caseCount].setOmegaFan(Double.valueOf(datavalue[++j]));
                    Manage.caseManage[caseCount].setACCheck(Integer.parseInt(datavalue[++j]));
                    Manage.caseManage[caseCount].setFanCheck(Integer.parseInt(datavalue[++j]));
                    Manage.caseManage[caseCount].setCOPAC(Double.valueOf(datavalue[++j]));
                    Manage.caseManage[caseCount].setControlCheck(Integer.parseInt(datavalue[++j]));
                    Manage.caseManage[caseCount].setTMassCheck(Integer.parseInt(datavalue[++j]));
                    Manage.caseManage[caseCount].setNumFloors(Integer.parseInt(datavalue[++j]));
                    Manage.caseManage[caseCount].setNumShafts(Integer.parseInt(datavalue[++j]));
                    Manage.caseManage[caseCount].nightcoolCheck = Integer.parseInt(datavalue[++j]);
                    Manage.caseManage[caseCount].nightcoolOn = Integer.parseInt(datavalue[++j]);
                    Manage.caseManage[caseCount].nightcoolOff = Integer.parseInt(datavalue[++j]);
                    Manage.caseManage[caseCount].closewindowOACheck = Integer.parseInt(datavalue[++j]);
                    Manage.caseManage[caseCount].OALimit = Double.valueOf(datavalue[++j]);
                    Manage.caseManage[caseCount].closewindowIACheck = Integer.parseInt(datavalue[++j]);
                    Manage.caseManage[caseCount].IALimit = Double.valueOf(datavalue[++j]);
                }
            }
            br.close();

            Manage.caseManage[caseCount].setZoneNum(zoneNum);
            Manage.caseManage[caseCount].setOpeningNum(openingNum);
            Manage.caseManage[caseCount].setTMassNum(TMassNum);
            //Manage.caseManage[caseCount].setTMassSlabMass(TMassmass);
        }
        Manage.caseManage[caseCount].setTimestepsize(TIME_STEP_SIZE);      
       

        //FADENEW333333333333333333333333333333333333333333333333333333333333333333333333333333333
        if (transimu != 0) { //FADE: Set the number of time steps
            int numofdays = 0; //FADENEW: moved around this section of the code
            if (dailysim == 1) { //FADENEW: This is a daily simulation
                //Get the total number of days. First position in array is 0
                for (int n = monthIni; n <= monthEnd; n++) {
                    numofdays += DAYS_IN_MONTH[n];
                }
            } else {
                numofdays = 1;
            }
//            System.out.println(numofdays + "");
            Manage.caseManage[caseCount].setTotalTimesteps(numofdays * SEGS_IN_DAY / TIME_STEP_SIZE); //FADENEW
        }
        //Manage.caseManage[caseCount].setTotalTimesteps(SEGS_IN_DAY / TIME_STEP_SIZE); //FADE: Changed this to allow for multiple month simulation

        if (transimu != 0) {
            Manage.caseManage[caseCount].setVarParameters(transimu, dailysim); //FADENEW: Flag for multimonths and daily simulation.
        }
        //FADENEW333333333333333333333333333333333333333333333333333333333333333333333333333333333

        Manage.caseManage[caseCount].setNumericalMethod(1);
        //<editor-fold defaultstate="collapsed" desc="Console output">
//        for (int i = 0; i <= zoneNum; i++) {
//            System.out.println("----------------------Zone " + i);
//            Manage.caseManage[caseNum].zoneInputData[i].printZone();
//        }
//
//        for (int i = 0; i < openingNum; i++) {
//            System.out.println("----------------------Opening " + i);
//            Manage.caseManage[caseNum].openingInputData[i].printFlowpath();
//        }
//        System.out.println("----------------------------");
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="FADENEW: Old code">
        //Prepare simulation with all fans
//        int lastopen = 0;
//        double caseCalLError = 0;
//        double caseCalMError = 0;
//        double caseCalHError = 0;
//        double caseCalLEfficiency = 0;
//        double caseCalMEfficiency = 0;
//        double caseCalHEfficiency = 0;
//        boolean caseCalLSurge = false;
//        boolean caseCalMSurge = false;
//        boolean caseCalHSurge = false;
        //        if (fanCheck == 4) { //Look for best fan
        //            if (bldgtype == 0) {
        //                lastopen = 5 * floornumber;
        //            } else if (bldgtype == 1) {
        //                lastopen = 3 * floornumber;
        //            } else if (bldgtype == 2) {
        //                lastopen = cvsection;
        //            }
        //            for (int i = 1; i < caseNum; i++) {
        //                caseCount++;
        //                Manage.addCase(caseCount);
        //                Manage.caseManage[caseCount] = (DataPool) DeepCopy.copy(Manage.caseManage[0]); //Copy the first case (case 0)
        //                if (caseCount == 1) { //Light-duty, predefined fan
        //                    a2 = -40.186;
        //                    a1 = 97.263;
        //                    a0 = 68.076;
        //                    fanD = 0.508;
        //                    gamma = 1;
        //                    b2 = -0.19629;
        //                    b1 = 0.59134;
        //                    fanType = "Light";
        //                    Manage.caseManage[caseCount].openingInputData[lastopen].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
        //                    Manage.caseManage[caseCount].setTuser(Tuser);
        //                    Manage.caseManage[caseCount].setfanSelect(true);
        //                } else if (caseCount == 2) { //Medium-duty, predefined fan
        //                    a2 = -1.1342;
        //                    a1 = 0;
        //                    a0 = 150;
        //                    fanD = 1.0668;
        //                    gamma = 1;
        //                    b2 = -0.01400;
        //                    b1 = 0.16454;
        //                    fanType = "Medium";
        //                    Manage.caseManage[caseCount].openingInputData[lastopen].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
        //                    Manage.caseManage[caseCount].setTuser(Tuser);
        //                    Manage.caseManage[caseCount].setfanSelect(true);
        //                } else if (caseCount == 3) { //Heavy-duty, predefined fan
        //                    a2 = -0.3372;
        //                    a1 = 4.5357;
        //                    a0 = 187.19;
        //                    fanD = 1.524;
        //                    gamma = 1;
        //                    b2 = -0.00193;
        //                    b1 = 0.06048;
        //                    fanType = "Heavy";
        //                    Manage.caseManage[caseCount].openingInputData[lastopen].turnintoFan(a2, a1, a0, fanD, gamma, b2, b1, m_eta, fanType);
        //                    Manage.caseManage[caseCount].setTuser(Tuser);
        //                    Manage.caseManage[caseCount].setfanSelect(true);
        //                }
        //            }
        //        }
        //</editor-fold>
        Maincalculation caseCal = new Maincalculation(Manage.caseManage[0]); //No fan case     
        
        //<editor-fold defaultstate="collapsed" desc="FADENEW: Old code">
        //        if (fanCheck == 4) { //Look for best fan
        ////            System.out.println("Light");
        //            Maincalculation caseCalL = new Maincalculation(Manage.caseManage[1]);
        //            caseCalLError = caseCalL.getTempDiff();
        //            caseCalLSurge = caseCalL.getSurge();
        //            caseCalLEfficiency = caseCalL.getTotalEfficiency();
        ////            System.out.println("Medium");
        //            Maincalculation caseCalM = new Maincalculation(Manage.caseManage[2]);
        //            caseCalMError = caseCalM.getTempDiff();
        //            caseCalMSurge = caseCalM.getSurge();
        //            caseCalMEfficiency = caseCalM.getTotalEfficiency();
        ////            System.out.println("Heavy");
        //            Maincalculation caseCalH = new Maincalculation(Manage.caseManage[3]);
        //            caseCalHError = caseCalH.getTempDiff();
        //            caseCalHSurge = caseCalH.getSurge();
        //            caseCalHEfficiency = caseCalH.getTotalEfficiency();
        //            //Check for best fan operation
        //            if (caseCalLEfficiency > caseCalMEfficiency && caseCalLEfficiency > caseCalHEfficiency && !caseCalLSurge) { //caseCalLError is the smallest
        //                Maincalculation caseCalF = new Maincalculation(Manage.caseManage[1]);
        //            } else if (caseCalMEfficiency > caseCalLEfficiency && caseCalMEfficiency > caseCalHEfficiency && !caseCalMSurge) { //caseCalMError is the smallest
        //                Maincalculation caseCalF = new Maincalculation(Manage.caseManage[2]);
        //            } else if (!caseCalHSurge) { //caseCalHError is the smallest
        //                Maincalculation caseCalF = new Maincalculation(Manage.caseManage[3]);
        //                double caseCalFError = caseCalF.getTempDiff();
        //                double caseCalFEfficiency = caseCalF.getTotalEfficiency();
        //            } else { //Fan is not benefitial FADE: Because medium-duty fan is too good to fail, this should never happen
        //                Maincalculation caseCalF = new Maincalculation(Manage.caseManage[0]);
        //            }
        //        }
        //</editor-fold>
        //Write results to console
//        //<editor-fold defaultstate="collapsed" desc="Console output">
//        int zoneNumber = caseCal.getZoneNumber();
//        Zone[] caseZones = caseCal.getCaseZone();
//        Flowpath[][] caseFlowpath = caseCal.getFlowpath();
//        double[][] flowrate = caseCal.getFlowrate();
//        System.out.println("Outside air temperature is " + caseZones[0].getAir_temp() + " Ã‚Â°C, and outside free wind velocity is " + Manage.caseManage[caseCount].wind_v + " m/s");
//        System.out.println("The zone air parameters are calcualted as: ");
//        for (int i = 0; i < zoneNumber; i++) {
//            System.out.println("Zone " + i + " elevation is " + caseZones[i].getElevation() + " m. The calculated air temperature in this zone is " + caseZones[i].getAir_temp() + " Ã‚Â°C, and pressure is " + caseZones[i].getPressure() + " Pa");
//        }
//        System.out.println("The flowrates between zones are calculated as: ");
//        for (int i = 0; i < zoneNumber; i++) {
//            for (int j = 0; j < zoneNumber; j++) {
//                if (caseFlowpath[i][j] != null && Math.abs(flowrate[i][j]) >= 0.00001) {
//                    if (flowrate[i][j] >= 0.0) {
//                        System.out.println("Flowrate from zone " + i + " to zone " + j + " equals to " + (flowrate[i][j] * 100) / 100.0 + " kg/s. ");
//                    }
//                    if (Math.abs(Manage.caseManage[caseCount].wind_v) >= 0.0001 && flowrate[i][j] >= 0.0) {
//                        System.out.println("The wind pressure coefficient is " + (Math.round(caseFlowpath[i][j].getCw() * 100) / 100.0) + " and the discharge coefficient is " + (Math.round(caseFlowpath[i][j].getCd() * 100) / 100.0));
//                    }
//                }
//            }
//        }
        //</editor-fold>
    }
}
