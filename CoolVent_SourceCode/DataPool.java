
//import java.util.*;
/**
 * DataPool.java
 * Holds all the input data from the interface
 *
 * Created 2004
 * Major cleanup by MAMB 6/16/2009
 * Major cleanup by FADE 3/12
 * 
 * @author
 * @version 3/27/2012
 */
public class DataPool implements java.io.Serializable {
    //<editor-fold defaultstate="collapsed" desc="Class variables">

//    public static final double XL = 0.5; //FADE: XL for Swami and Chandra cp correlation for high-rise buildings
//    public static final double Xr = 0; //FADE: Xr for Swami and Chandra cp correlation for high-rise buildings
    public int zoneNumber;
    public int openingNumber;
    public double a_terrain;
    public double b_terrain;
    public double SCF;
    public double wind_v;
    public double windDirection;
    public double dis_w_e;
    public double dis_n_s;
    public double buildingH;
    public double roofH;
    public int buildingType; //20090912
    public Zone[] zoneInputData;
    public Zone ambientZone;
    public Flowpath[] openingInputData;
    public boolean[][] connection;
    public String wall;
    public double SAA;
    public double sideglazingarea;
    public TMass[] TMassInputData;
    public boolean[][] TMassConnection;
    public int TMassNumber;
    public boolean[][] TMassIntConnection;
    public int numericalMethod; //0 = explicit, 1 = implicit
    public int timestepsize;
    public int totaltimesteps;
    public int varParFromOutdoorTXT; //defines variabletemperature in Maincalculation
    public double bldgOrientation;
    public double occupancyScheduleOn;
    public double occupancyScheduleOff;
    public double offPeakLoadFrac;
    public int nightcoolCheck;
    public double nightcoolOn;
    public double nightcoolOff;
    public int TMassCheck;
    public double TMassSlabMass; //FADE: This is the mass (kg) of the slab (floor or ceiling). Each zone is in contact with two of these
    public int closewindowOACheck;
    public double OALimit;
    public int closewindowIACheck;
    public double IALimit;
    public String caseName;
    public double disNS;
    public double disWE;
    public double intopeningarea;
    public int ACCheck = 0;
    public double TAC = 28;
    public double omegaAC = 16;
    public double TFan = 23;
    public double omegaFan = 14;
    public double COPAC = 3;
    public int controlCheck = 0; //FADENEW
    public int fanCheck;
    public double windowR = 0.17; //FADE: What about this?
    public int dailySim = 0; //FADENEW
    public double openingfrac = 1; //FADENEW
    public int numFloors; //sdr_hulic added int to store number of floors
    public int numShafts; //sdr_hulic added int to store number of shafts
    //</editor-fold>
    public int re_reference = 1; //CK EDIT: For re-referencing the wind velocity

    public DataPool() {
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public void addAmbientZone(double temperature, double aterrain, double bterrain, double circumBuildingH, double windvelocity, double windDirect, double dis_W_E, double dis_N_S, double buildingHeight, double roofheight) {
        ambientZone = new Zone(1.0E10, 1.0E10, 0);
        ambientZone.setAir_temp(temperature);
        ambientZone.calAir_density();
        ambientZone.setIsIntZone(false);
        ambientZone.setIsOccZone(false);
        ambientZone.source.setOccupancyHeat_source(0.0);
        ambientZone.source.setAirflow_source(0.0);
        ambientZone.source.setAirflow_temp(0.0);
        a_terrain = aterrain;
        b_terrain = bterrain;
        SCF = circumBuildingH;
        wind_v = windvelocity;  
        if(re_reference == 1) {wind_v = 1.5*wind_v;} //CK EDIT
        windDirection = windDirect;
        dis_w_e = dis_W_E;
        dis_n_s = dis_N_S;
        buildingH = buildingHeight;
        roofH = roofheight;
    }
    

    public void setZoneNum(int zoneNum) {
        zoneNumber = zoneNum;
        zoneInputData = new Zone[zoneNumber + 1];
        connection = new boolean[zoneNumber + 1][zoneNumber + 1];
        zoneInputData[0] = ambientZone;
    }

    public void setOpeningNum(int openingNum) {
        openingNumber = openingNum;
        openingInputData = new Flowpath[openingNum];
    }

    public void setBldgType(int buildingtype) {
        buildingType = buildingtype;
    }

    //sdr_hulic - add method to set and get floor number
    
    public void setNumFloors(int floorNumber)
    {
    	numFloors = floorNumber;
    }
    
    public int getNumFloors()
    {
    	return numFloors;
    }
      
    
    //sdr_hulic - add method to set number shafts
    
    public void setNumShafts(int shaftNumber)
    {
    	numShafts = shaftNumber;
    }
    
    public int getNumShafts()
    {
    	return numShafts;
    }
    
    public void setTMassNum(int TMassNum) {
        TMassNumber = TMassNum;
        TMassInputData = new TMass[TMassNum];
        TMassConnection = new boolean[TMassNumber][zoneNumber + 1];//Jinchao Yuan 03/17/2005
        TMassIntConnection = new boolean[TMassNumber][TMassNumber]; //added by Jinchao Yuan 05/03/2005
    }

//    public void addTMass(int TMassCount, double TMassVolume, double TMassArea, double TMassDensity, double TMassCp, double TMassH, double TMassTemperature) {
//        TMassInputData[TMassCount] = new TMass(TMassVolume, TMassArea, TMassDensity);
//        TMassInputData[TMassCount].setCp(TMassCp);
//        TMassInputData[TMassCount].setH(TMassH);
//        TMassInputData[TMassCount].setTemperature(TMassTemperature);
//        TMassInputData[TMassCount].setIsAdiabatic(false);
//        TMassInputData[TMassCount].calMass();
//        TMassInputData[TMassCount].calHA();
//    }

    public void addTMass(int TMassCount, double TMassVolume, double TMassArea, double TMassPerimeter, double TMassDensity, double TMassCp, String TMassType, double k, double TMassTemperature, int AbsorbsSolar) { //FADENEW
        TMassInputData[TMassCount] = new TMass(TMassVolume, TMassArea, TMassPerimeter, TMassDensity, k); //FADENEW
        TMassInputData[TMassCount].setCp(TMassCp);
        TMassInputData[TMassCount].setType(TMassType);
        TMassInputData[TMassCount].setTemperature(TMassTemperature);
        TMassInputData[TMassCount].setIsAdiabatic(false);
        TMassInputData[TMassCount].calMass();
        //TMassInputData[TMassCount].calHA();
        TMassInputData[TMassCount].getkAoX();
        TMassInputData[TMassCount].setAbsorbsSolar(AbsorbsSolar);
        if (TMassType.equals("N_wall")) {
            SAA = (180.0 + this.bldgOrientation) * 3.141592 / 180.0;
        } else if (TMassType.equals("S_wall")) {
            SAA = this.bldgOrientation * 3.141592 / 180.0;
        } else if (TMassType.equals("E_wall")) {
            SAA = (270.0 + this.bldgOrientation) * 3.141592 / 180.0;
        } else if (TMassType.equals("W_wall")) {
            SAA = (90.0 + this.bldgOrientation) * 3.141592 / 180.0;
        } else {
            SAA = 180.0 * 3.141592 / 180.0;
        }
        TMassInputData[TMassCount].setSurfaceAzimuthAngle(SAA);
    }

    public void addZone(int zoneCount, double zoneVolume, double zoneFloorArea, double zoneElevation, double zoneAirTemp, double OccupancyHeatLoad, String wall, double floorNumber, double bldgOrient, double sideglazingarea, double floortoceilingheight, boolean isoccupied, double facadewidth) {
        zoneInputData[zoneCount] = new Zone(zoneVolume, zoneFloorArea, zoneElevation);
        zoneInputData[zoneCount].setAir_temp(zoneAirTemp);
        zoneInputData[zoneCount].calAir_density();
        zoneInputData[zoneCount].setIsIntZone(true);
        zoneInputData[zoneCount].setIsOccZone(isoccupied);
        zoneInputData[zoneCount].source.setOccupancyHeat_source(OccupancyHeatLoad);
        zoneInputData[zoneCount].source.setAirflow_source(0.0);
        zoneInputData[zoneCount].source.setAirflow_temp(0.0);
        zoneInputData[zoneCount].setWall(wall);
        zoneInputData[zoneCount].setBldgOrientation(bldgOrient);
        zoneInputData[zoneCount].setFloorNumber(floorNumber);
        zoneInputData[zoneCount].setFacadeWidth(facadewidth);
        if (wall.equals("N_wall")) {
            SAA = (180.0 + bldgOrient) * 3.141592 / 180.0;
        } else if (wall.equals("S_wall")) {
            SAA = bldgOrient * 3.141592 / 180.0;
        } else {
            SAA = 180.0 * 3.141592 / 180.0;
        }
        zoneInputData[zoneCount].setSurfaceAzimuthAngle(SAA);
        //<editor-fold defaultstate="collapsed" desc="Console output">
        //System.out.println("zone " + zoneCount + " Orientation " + wall + " SurfaceAzimuthAngle " + SAA / 3.1416 * 180);
        //</editor-fold>
        zoneInputData[zoneCount].setSideGlazingArea(sideglazingarea);
        zoneInputData[zoneCount].setFloorToCeilingHeight(floortoceilingheight);
    }
    
    //SUPERFADE
    public void addAmbientZone(double temperature, double aterrain, double bterrain, double circumBuildingH, double windvelocity, double windDirect) {
        ambientZone = new Zone(1.0E10, 1.0E10, 0);
        ambientZone.setAir_temp(temperature);
        ambientZone.calAir_density();
        ambientZone.setIsIntZone(false);
        ambientZone.setIsOccZone(false);
        ambientZone.source.setOccupancyHeat_source(0.0);
        ambientZone.source.setAirflow_source(0.0);
        ambientZone.source.setAirflow_temp(0.0);
        a_terrain = aterrain;
        b_terrain = bterrain;
        SCF = circumBuildingH;
        wind_v = windvelocity;
        //CK EDIT:
        if(re_reference == 1) {wind_v = 1.5*wind_v;}
        windDirection = windDirect;
    }
    
    public void addZone(int zoneCount, double zoneVolume, double zoneElevation, double zoneAirTemp, double OccupancyHeatLoad, double floorNumber, double floortoceilingheight, boolean isoccupied) {
        zoneInputData[zoneCount] = new Zone(zoneVolume, zoneElevation);
        zoneInputData[zoneCount].setAir_temp(zoneAirTemp);
        zoneInputData[zoneCount].calAir_density();
        zoneInputData[zoneCount].setIsIntZone(true);
        zoneInputData[zoneCount].setIsOccZone(isoccupied);
        zoneInputData[zoneCount].source.setOccupancyHeat_source(OccupancyHeatLoad);
        zoneInputData[zoneCount].source.setAirflow_source(0.0);
        zoneInputData[zoneCount].source.setAirflow_temp(0.0);
        zoneInputData[zoneCount].setFloorNumber(floorNumber);
        zoneInputData[zoneCount].setFloorToCeilingHeight(floortoceilingheight);
    }
    
    public void addOpening(int flowPathCount, double openingArea, double operableArea, double openingElev, double openingLongitude, double surfaceLength, double surfaceHeight, double surfaceSideRatio, String openingLocation, double openingAngle, int connectNum1, int connectNum2, double cd) {
        double Cw_value = calcCw(openingLocation, openingAngle, bldgOrientation, this.windDirection, wind_v, surfaceSideRatio, dis_n_s, dis_w_e, openingElev / surfaceHeight, this.buildingH, openingLongitude / surfaceLength); //FADE: New definition of building height does not include the roof height
        //calculate the Cd_value
        double Cd_value = 0.65; //20080414 MAMB changed this from 0.75 to 0.65
        double powN = 0.5;
        //sdr_hulic changed all if statements below to use user supplied cd value in method call if provided - be sure to add if statement for N_wall and S_wall as shown below
        if (openingLocation.equals("Roof")) {
            if (cd != 0)
            	{
            	Cd_value = cd;
            	}
            else
            	{
            Cd_value = 0.85;
            	}
        } else if (openingLocation.equals("Roof1")) {
        	 if (cd != 0)
         	{
         	Cd_value = cd;
         	}
         else
         	{
         	Cd_value = 0.85;
         	}
        } else if (openingLocation.equals("Roof2")) {
        	 if (cd != 0)
         	{
         	Cd_value = cd;
         	}
         else
         	{
         	Cd_value = 0.85;
         	}
        } else if (openingLocation.equals("N_wall")) {//sdr_hulic added this for N_wall
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 0.65;
        	}
       }
        else if (openingLocation.equals("S_wall")) {//sdr_hulic added this for S_wall
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 0.65;
        	}
        } else if (openingLocation.equals("Door")) {
            Cd_value = 0.75; //20080414 MAMB added this.
        } else if (openingLocation.equals("Atrium")) {
        	 if (cd != 0)
         	{
         	Cd_value = cd;
         	}
         else
         	{
         	Cd_value = 1;
         	} //FADE added this
        } else if (openingLocation.equals("Shaft1")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } else if (openingLocation.equals("Shaft2")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        }else if (openingLocation.equals("InsideShaft1")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } else if (openingLocation.equals("InsideShaft2")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } else if (openingLocation.equals("InsideShaft1Grnd")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } else if (openingLocation.equals("InsideShaft2Grnd")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        }else if (openingLocation.equals("InsideUpper")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } 
        else if (openingLocation.equals("Inside")) {//FADE added this
            if (cd == 0) {
                this.intopeningarea = openingArea;
                if (intopeningarea < 0.1 * Math.max(zoneInputData[connectNum1].getVolume() / zoneInputData[connectNum1].getFloor_area(), zoneInputData[connectNum2].getVolume() / zoneInputData[connectNum2].getFloor_area())) {
                    Cd_value = 0.65;
                } else {
                    Cd_value = 1;
                }
            } else {
                Cd_value = cd;
            }
        }
        openingInputData[flowPathCount] = new Flowpath(openingArea, operableArea, openingElev, openingLocation);
        openingInputData[flowPathCount].setCw(Cw_value);
        openingInputData[flowPathCount].setCd(Cd_value);
        openingInputData[flowPathCount].setOpeningLongitude(openingLongitude);
        openingInputData[flowPathCount].setSurfaceLength(surfaceLength);
        openingInputData[flowPathCount].setSurfaceHeight(surfaceHeight);
        openingInputData[flowPathCount].setSurfaceSideRatio(surfaceSideRatio);
        openingInputData[flowPathCount].setOpeningAzimuth(openingAngle);
        openingInputData[flowPathCount].setOpeningx(openingLongitude / surfaceLength);
        openingInputData[flowPathCount].setOpeningy(openingElev / surfaceHeight);
        openingInputData[flowPathCount].setPowerN(powN);
        openingInputData[flowPathCount].setConnectionZoneNums(connectNum1, connectNum2);
    }
    //SUPERFADE

    public void addOpening(int flowPathCount, double openingH, double openingW, double openingElev, String openingLocation, int connectNum1, int connectNum2, double upperopeningheight, double upperopeningarea, int doubleopeningcheck, double cd) {
        //Calculate the Cw value
        double Cw_value = calcCw(openingLocation, 1, bldgOrientation, this.windDirection, wind_v, 1, dis_n_s, dis_w_e, openingElev / this.buildingH, this.buildingH, 0.5); //FADE: New definition of building height does not include the roof height
        //calculate the Cd_value
        double Cd_value = 0.65; //20080414 MAMB changed this from 0.75 to 0.65
        double powN = 0.5;
        //sdr_hulic changed all if statements below to use user supplied cd value in method call if provided - be sure to add if statement for N_wall and S_wall as shown below
        if (openingLocation.equals("Roof")) {
            if (cd != 0)
            	{
            	Cd_value = cd;
            	}
            else
            	{
            Cd_value = 0.85;
            	}
        } else if (openingLocation.equals("Roof1")) {
        	 if (cd != 0)
         	{
         	Cd_value = cd;
         	}
         else
         	{
         	Cd_value = 0.85;
         	}
        } else if (openingLocation.equals("Roof2")) {
        	 if (cd != 0)
         	{
         	Cd_value = cd;
         	}
         else
         	{
         	Cd_value = 0.85;
         	}
        } else if (openingLocation.equals("N_wall")) {//sdr_hulic added this for N_wall
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 0.65;
        	}
       }
        else if (openingLocation.equals("S_wall")) {//sdr_hulic added this for S_wall
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 0.65;
        	}
        } else if (openingLocation.equals("Door")) {
            Cd_value = 0.75; //20080414 MAMB added this.
        } else if (openingLocation.equals("Atrium")) {
        	 if (cd != 0)
         	{
         	Cd_value = cd;
         	}
         else
         	{
         	Cd_value = 1;
         	} //FADE added this
        } else if (openingLocation.equals("Shaft1")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } else if (openingLocation.equals("Shaft2")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        }else if (openingLocation.equals("InsideShaft1")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } else if (openingLocation.equals("InsideShaft2")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } else if (openingLocation.equals("InsideShaft1Grnd")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } else if (openingLocation.equals("InsideShaft2Grnd")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        }else if (openingLocation.equals("InsideUpper")) {
       	 if (cd != 0)
        	{
        	Cd_value = cd;
        	}
        else
        	{
        	Cd_value = 1;
        	} //sdr_hulic     
        } 
        else if (openingLocation.equals("Inside")) {//FADE added this
            if (cd == 0) {
                intopeningarea = openingH * openingW;
                if (intopeningarea < 0.1 * Math.max(zoneInputData[connectNum1].getVolume() / zoneInputData[connectNum1].getFloor_area(), zoneInputData[connectNum2].getVolume() / zoneInputData[connectNum2].getFloor_area())) {
                    Cd_value = 0.65;
                } else {
                    Cd_value = 1;
                }
            } else {
                Cd_value = cd;
            }
        }
//********************* NEVER USED ************************************        
        //cracks
                /*if (openingH<0.15||openingW<0.15)
        {
        //powN=0.5+0.5*Math.exp(-Math.min(openingH,openingW)/2);
        //Cd_value=0.0097*Math.pow(0.0092,powN)*Math.max(openingH,openingW);
        Cd_value=0.65;
        }
        else if (openingH>=0.5&&openingW>=0.5&&openingH<=1.0&&openingW<=1.0) Cd_value=0.75;
        else if (openingH>1.0&&openingW>1.0&&openingH<1.5&&openingW<1.5) Cd_value=0.83;
        else if (openingH>=1.5&&openingW>=1.5) Cd_value=0.93;*/
//********************* NEVER USED ************************************end

        //FADE: This "isLargeOpen" thing seems useless
        boolean isLargeOpen = false;
        if (openingLocation.equalsIgnoreCase("Inside") || openingLocation.equals("Door") || openingLocation.equals("Atrium")|| openingLocation.equals("Shaft1")|| openingLocation.equals("Shaft2")|| openingLocation.equals("InsideUpper")) {  //20080414 MAMB added Door term // sdr_hulic added "shaft" and "InsideUpper" opening type
            if (openingH > 0.52 * Math.max(zoneInputData[connectNum1].getVolume() / zoneInputData[connectNum1].getFloor_area(), zoneInputData[connectNum2].getVolume() / zoneInputData[connectNum2].getFloor_area())) {
                isLargeOpen = false;
            }
        }
        //FADE: This "isLargeOpen" thing seems useless

        openingInputData[flowPathCount] = new Flowpath(openingH, openingW, openingElev, isLargeOpen, wind_v, Cw_value, openingLocation);
        openingInputData[flowPathCount].setCd(Cd_value);
        openingInputData[flowPathCount].setPowerN(powN);
        openingInputData[flowPathCount].setConnectionZoneNums(connectNum1, connectNum2);
        openingInputData[flowPathCount].setUpperOpeningHeight(upperopeningheight);
        openingInputData[flowPathCount].setUpperOpeningArea(upperopeningarea);
        openingInputData[flowPathCount].setDoubleOpeningCheck(doubleopeningcheck);
        
        openingInputData[flowPathCount].setOpeningx(0.5);
        openingInputData[flowPathCount].setOpeningy(openingElev / this.buildingH);
    }

    public void createConnection() {
        for (int i = 0; i < zoneNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                connection[i][j] = false;
            }
        }

        for (int i = 0; i < openingNumber; i++) {
            if (openingInputData[i] != null) {
                connection[openingInputData[i].getConnectionZoneNum1()][openingInputData[i].getConnectionZoneNum2()] = true;
                connection[openingInputData[i].getConnectionZoneNum2()][openingInputData[i].getConnectionZoneNum1()] = true;
            }
        }
    }

    public void initializeTMassConnection() {
        for (int i = 0; i < TMassNumber; i++) {
            for (int j = 0; j < zoneNumber; j++) {
                TMassConnection[i][j] = false;
            }
            for (int j = 0; j < TMassNumber; j++) {
                TMassIntConnection[i][j] = false;
            }
        }
    }

    public void createTMassConnection(int TMassIndex, int zoneIndex) {
        TMassConnection[TMassIndex][zoneIndex] = true;
//        System.out.println("Layer "+TMassIndex+" with zone "+zoneIndex);
    }

    public void createTMassIntConnection(int TMassIndex1, int TMassIndex2) {
        TMassIntConnection[TMassIndex1][TMassIndex2] = true;
        TMassIntConnection[TMassIndex2][TMassIndex1] = true;
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

    public int getOpeningNumber() {
        return openingNumber;
    }

    public int getTMassNumber() {
        return TMassNumber;
    }

    public double getDisNS() {
        return disNS;
    }

    public void setDisNS(double DisNS) {
        disNS = DisNS;
    }

    public double getDisWE() {
        return disWE;
    }

    public void setDisWE(double DisWE) {
        disWE = DisWE;
    }

    public void setNumericalMethod(int numMethod) {
        numericalMethod = numMethod;
    }

    public void setTimestepsize(int stepsize) {
        timestepsize = stepsize;
    }

    public void setTotalTimesteps(int timesteps) {
        totaltimesteps = timesteps;
    }

    public void setVarParameters(int varParFromOutdoorTXT, int daily) { //FADENEW
        this.varParFromOutdoorTXT = varParFromOutdoorTXT;
        this.dailySim = daily;
    }

    public void setBldgOrientation(double orientationangle) {
        this.bldgOrientation = orientationangle;
    }

    public void setOccupancyScheduleOn(double occSchOn) {
        this.occupancyScheduleOn = occSchOn;
    }

    public double getOccupancyScheduleOn() {
        return this.occupancyScheduleOn;
    }

    public void setOccupancyScheduleOff(double occSchOff) {
        this.occupancyScheduleOff = occSchOff;
    }

    public double getOccupancyScheduleOff() {
        return this.occupancyScheduleOff;
    }

    public void setOffPeakLoadFrac(double offPLF) {
        this.offPeakLoadFrac = offPLF;
    }

    public double getOffPeakLoadFrac() {
        return this.offPeakLoadFrac;
    }

    public void setACCheck(int check) {
        this.ACCheck = check;
    }
    
    public void setTAC(double T) {
        this.TAC = T;
    }

    public double getTAC() {
        return this.TAC;
    }

    public void setOmegaAC(double omega) {
        this.omegaAC = omega;
    }

    public double getOmegaAC() {
        return this.omegaAC;
    }
    
    public void setFanCheck(int fan) {
        this.fanCheck = fan;
    }

    public void setTFan(double T) {
        this.TFan = T;
    }

    public double getTFan() {
        return this.TFan;
    }

    public void setOmegaFan(double omega) {
        this.omegaFan = omega;
    }

    public double getOmegaFan() {
        return this.omegaFan;
    }
    
    public void setCOPAC(double cop) {
        this.COPAC = cop;
    }

    public double getCOPAC() {
        return this.COPAC;
    }
    
    //FADENEWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW
    public  void setControlCheck(int cc) {
        this.controlCheck = cc;
    }
    
    public int getControlCheck() {
        return this.controlCheck;
    }
    
    public int getTMassCheck() {
        return this.TMassCheck;
    }
    
    public void setTMassCheck(int TMC) {
        this.TMassCheck = TMC;
    }
    
    public double getTMassSlabMass() {
        return this.TMassSlabMass;
    }
    
    public void setTMassSlabMass(double TMmass) {
        this.TMassSlabMass = TMmass;
    }
    //FADENEWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW

//    public void setfanSelect(boolean select) {
//        this.fanSelect = select;
//    }
//
//    public boolean getfanSelect() {
//        return this.fanSelect;
//    }

    public double getBuildingHeight() {
        return this.buildingH;
    }

    public double getRoofHeight() {
        return this.roofH;
    }

//    public double getSCF() {
//        return this.SCF;
//    }

    public void setWindowR(double windowr) {
        this.windowR = windowr;
    }

    public double getWindowR() {
        return this.windowR;
    }

    public static double calcCw(String openingLoc, double openingAzimuth, double bldgDir, double windDirData, double windvel, double s, double disNS, double disWE, double y, double bldgelev, double x) { //SUPERFADE
        // Swami and Chandra model (1988)
        double anw = 0; //Wind angle: angle between wind and facade. If wind hitting window, anw=0.
        double SideRatio = s;
        double Cw_value = 1.1;
        double G = 0;
        double XL = x; //SUPERFADE: This keeps default value of 0.5. x and y are nondimensional
        double Xr = (XL - 0.5)/0.5;
        double ZH = y;
        double windDirection = windDirData; //Direction that wind is GOING TO (left to right = 90)
        if (!openingLoc.equals("Other")) {
            double tempWindDir = windDirData + 180 - bldgDir; //WindDirData: direction the wind is COMING FROM (left to right = 270) changed by MAMB to match windDirData (we were 180 off)
            if (tempWindDir >= 360.0) {
                windDirection = tempWindDir - 360.0;
            } else if (tempWindDir >= 0.0 && tempWindDir < 360.0) {
                windDirection = tempWindDir;
            } else {
                windDirection = 360 + tempWindDir;
            }
            if (openingLoc.equals("Inside") || openingLoc.equals("Door") || windvel <= 0.0001 || openingLoc.equals("Atrium")|| openingLoc.equals("Shaft1") || openingLoc.equals("Shaft2") || openingLoc.equals("InsideShaft1") || openingLoc.equals("InsideShaft2") || openingLoc.equals("InsideShaft1Grnd") || openingLoc.equals("InsideShaft2Grnd") || openingLoc.equals("InsideUpper")) { //sdr_hulic added "shaft" and "InsideUpper" opening types
                return 0.0;  //20080414 MAMB added Door term //FADE added Atrium term
            } else if (openingLoc.equals("Roof") || openingLoc.equals("Roof1") || openingLoc.equals("Roof2")) {
                return -0.3;
            } else {
                if (openingLoc.equals("E_wall")) {
                    SideRatio = disWE / disNS;
                    if (windDirection <= 90.0) {
                        anw = windDirection + 90.0;
                    } else if (windDirection > 90.0 && windDirection <= 270.0) {
                        anw = 270.0 - windDirection;
                    } else if (windDirection > 270.0) {
                        anw = windDirection - 270.0;
                    }
                }
                if (openingLoc.equals("S_wall")) {
                    SideRatio = disNS / disWE;
                    if (windDirection > 180.0) {
                        anw = 360.0 - windDirection;
                    } else {
                        anw = windDirection;
                    }
                }
                if (openingLoc.equals("W_wall")) {
                    SideRatio = disWE / disNS;
                    if (windDirection <= 270.0) {
                        anw = Math.abs(windDirection - 90.0);
                    } else {
                        anw = Math.abs(450.0 - windDirection);
                    }
                }
                if (openingLoc.equals("N_wall")) {
                    SideRatio = disNS / disWE;
                    anw = Math.abs(180.0 - windDirection);
                }
            }
        } else { //SUPERFADE
            windDirection %= 360;
            if (windDirection < 0)
            {
                windDirection += 360;
            }
            anw = Math.min(Math.abs(windDirection - openingAzimuth), 360 - Math.abs(windDirection - openingAzimuth)); //SUPERFADE: Check this. MAMB has more to get this and said something about being off by 180
            if (Math.sin(Math.toRadians(windDirection - openingAzimuth)) < 0)
            {
                XL = 1 - x;
                Xr = (XL - 0.5)/0.5;
            }
        }
        G = Math.log(SideRatio);
        double anwrad = anw * Math.PI / 180;
        double shortwall = Math.min(disNS, disWE);
        if ((bldgelev / shortwall) <= 0.4) { //FADE: Low-rise building            {
            Cw_value = .6 * Math.log(1.248
                    - 0.703 * Math.sin(anwrad / 2)
                    - 1.175 * Math.pow(Math.sin(anwrad), 2)
                    + 0.131 * Math.pow(Math.sin(2 * anwrad * G), 3)
                    + 0.769 * Math.cos(anwrad / 2)
                    + 0.07 * Math.pow(G * Math.sin(anwrad / 2), 2)
                    + 0.717 * Math.pow(Math.cos(anwrad / 2), 2));  //Valid for low-rise buildings
        } else {
            Cw_value = 0.068
                    - 0.839 * anwrad
                    + 1.733 * Math.cos(2 * anwrad)
                    - 1.556 * ZH * Math.sin(anwrad) * Math.pow(SideRatio, 0.169)
                    - 0.922 * Math.cos(2 * anwrad) * Math.pow(SideRatio, 0.279)
                    + 0.344 * Math.sin(2 * anwrad)
                    - 0.801 * ZH * Math.cos(anwrad)
                    + 1.118 * Math.cos(Xr)
                    - 0.961 * Math.cos(Xr * anwrad)
                    + 0.691 * Math.cos(Xr * anwrad) * Math.pow(SideRatio, 0.245)
                    + 2.515 * ZH * Math.sin(anwrad)
                    + 0.399 * Xr * Math.sin(anwrad)
                    - 0.431 * XL
                    + 0.046 * Math.cos(Xr) * Math.pow(SideRatio, 0.85);  //Valid for high-rise buildings
        }
//        System.out.println("Cw= "+Cw_value);
         // CK EDIT: Correction factor for plan density
        double multiplier = 1.0; 
        double multiplier_windangle = anw*180/Math.PI;
        
        //CK EDIT: For plan area density = 11.1%
        /*if (multiplier_windangle == 0.0) //if incident angle is 0 
        {
            multiplier = 0.21;
        }
        else
        {
            multiplier_windangle %= 90 ; //CK EDIT: direction the wind is coming from
        
            if(multiplier_windangle == 0.0)
            {
                multiplier = 0.05; //CK edit: if 90 degrees
            }
            else if (multiplier_windangle>0 & multiplier_windangle<=15) 
            {
                multiplier = ((multiplier_windangle-0)*(0.28-0.21)/(15-0)) + 0.21; //CK EDIT: linear interpolation
            }
            else if (multiplier_windangle>15 & multiplier_windangle<=30)
            {
                multiplier = ((multiplier_windangle-15)*(0.67-0.28)/(30-15)) + 0.28;
            }
            else if (multiplier_windangle>30 & multiplier_windangle<=45)
            {
                multiplier = ((multiplier_windangle-30)*(0.71-0.67)/(45-30)) + 0.67;
            }
            else if (multiplier_windangle>45 & multiplier_windangle<=60)
            {
                multiplier = ((multiplier_windangle-45)*(0.69-0.71)/(60-45)) + 0.71;
            }
            else if (multiplier_windangle>60 & multiplier_windangle<=75)
            {
                multiplier = ((multiplier_windangle-60)*(1.00-0.69)/(75-60)) + 0.69;
            }
            else if (multiplier_windangle>75 & multiplier_windangle<=90)
            {
                multiplier = ((multiplier_windangle-0)*(0.28-0.21)/(15-0)) + 0.21;
            }
        }*/
        //CK EDIT: for plan density = 25.00%
        /*if (multiplier_windangle == 0.0) //if incident angle is 0 
        {
            multiplier = 0.10;
        }
        else
        {
            multiplier_windangle %= 90 ; //CK EDIT: direction the wind is coming from
        
            if(multiplier_windangle == 0.0)
            {
                multiplier = 0.01; //CK edit: if 90 degrees
            }
            else if (multiplier_windangle>0 & multiplier_windangle<=15) 
            {
                multiplier = ((multiplier_windangle-0)*(0.11-0.10)/(15-0)) + 0.10; //CK EDIT: linear interpolation
            }
            else if (multiplier_windangle>15 & multiplier_windangle<=30)
            {
                multiplier = ((multiplier_windangle-15)*(0.22-0.11)/(30-15)) + 0.11;
            }
            else if (multiplier_windangle>30 & multiplier_windangle<=45)
            {
                multiplier = ((multiplier_windangle-30)*(0.44-0.22)/(45-30)) + 0.22;
            }
            else if (multiplier_windangle>45 & multiplier_windangle<=60)
            {
                multiplier = ((multiplier_windangle-45)*(0.44-0.44)/(60-45)) + 0.44;
            }
            else if (multiplier_windangle>60 & multiplier_windangle<=75)
            {
                multiplier = ((multiplier_windangle-60)*(0.05-0.44)/(75-60)) + 0.44;
            }
            else if (multiplier_windangle>75 & multiplier_windangle<=90)
            {
                multiplier = ((multiplier_windangle-0)*(0.01-0.05)/(15-0)) + 0.05;
            }
        }*/
		
	if (multiplier_windangle == 0.0) //if incident angle is 0 
        {
            multiplier = -0.087;
        }
        else
        {
            multiplier_windangle %= 180 ; //CK EDIT: direction the wind is coming from
        
            if(multiplier_windangle == 0.0)
            {
                multiplier = -0.087; //CK edit: if attack angle 0
            }
            else if (multiplier_windangle>0 & multiplier_windangle<=15) 
            {
                multiplier = ((multiplier_windangle-0)*(-0.1103+0.087)/(15-0)) -0.087; 
			}	
            else if (multiplier_windangle>15 & multiplier_windangle<=30)
            {
                multiplier = ((multiplier_windangle-15)*(-0.103+0.1103)/(30-15)) -0.1103;
            }
            else if (multiplier_windangle>30 & multiplier_windangle<=45)
            {
                multiplier = ((multiplier_windangle-30)*(-0.0992+0.103)/(45-30)) - 0.103;
            }
            else if (multiplier_windangle>45 & multiplier_windangle<=60)
            {
                multiplier = ((multiplier_windangle-45)*(-0.1041+0.0992)/(60-45)) -0.0992;
            }
            else if (multiplier_windangle>60 & multiplier_windangle<=75)
            {
                multiplier = ((multiplier_windangle-60)*(-0.1049+0.1041)/(75-60)) -0.1041;
            }
            else if (multiplier_windangle>75 & multiplier_windangle<=90)
            {
                multiplier = ((multiplier_windangle-0)*(-0.0965+0.1049)/(15-0)) -0.1049;
            }
			else if (multiplier_windangle>90 & multiplier_windangle<=105) 
            {
                multiplier = ((multiplier_windangle-0)*(-0.1117+0.0965)/(15-0)) -0.0965; 
			}	
            else if (multiplier_windangle>105 & multiplier_windangle<=120)
            {
                multiplier = ((multiplier_windangle-15)*(-0.1054+0.1117)/(30-15)) -0.1117;
            }
            else if (multiplier_windangle>120 & multiplier_windangle<=135)
            {
                multiplier = ((multiplier_windangle-30)*(-0.1031+0.1054)/(45-30)) - 0.1054;
            }
            else if (multiplier_windangle>135 & multiplier_windangle<=150)
            {
                multiplier = ((multiplier_windangle-45)*(-0.1051+0.1031)/(60-45)) -0.1031;
            }
            else if (multiplier_windangle>150 & multiplier_windangle<=165)
            {
                multiplier = ((multiplier_windangle-60)*(-0.1112+0.1031)/(75-60))-0.1112;
            }
            else if (multiplier_windangle>165 & multiplier_windangle<=180)
            {
                multiplier = ((multiplier_windangle-0)*(-0.095+0.1112)/(15-0))-0.1112;
            }
        }
	
		return multiplier;//C-Wind pressure
                
        //return Cw_value*multiplier;
    }
	
	
	
	
}