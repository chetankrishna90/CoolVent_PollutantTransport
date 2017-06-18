
/**
 * Zone.java
 * Holds the information of each zone, but with contaminants
 *
 * Created 4/28/2003
 * Major cleanup by MAMB 6/16/2009
 * Major cleanup by FADE 3/12
 * Edited by CK 07/15. Added contaminant dispersion calculations
 * @author
 * @version 3/27/2012
 */
public class Zone implements java.io.Serializable{
    //<editor-fold defaultstate="collapsed" desc="Class variables">
    //Constants
    private static final double CP = 1010; // Unit: J/kg-K
    private static final double CV = 723; //FADE added Cv for the ideal gas energy constitutive relation (11/28/11). Units: [J/kg-k]
    private static final double GAMMA = CP / CV; //FADE: gamma=cp/cv
    private static final double R = 287.04;
    private static final double ATM_PRESSURE = 101325;
    private static final double TO_KELVIN = 273.15;
    private static final double VISCOS_REF = 18.27E-6; //FADE: Reference viscosity for Sutherland's equation
    private static final double TEMP_REF = 291.15; //FADE: Reference temperature for Sutherland's equation
    private static final double SUTH_CONST = 120;  //FADE: Sutherland's constant
    private static final double SUTH_EXP = 3 / 2;  //FADE: Sutherland's exponent
    private static final double CONV_SOLAR_FRAC = 0.2; //Default 0.2
    private static final double CONV_OCC_FRAC = 0.5; //Default 0.5 --this is an average for high and low airspeeds. see HoF 05 30.14
    private static final double RAD_SOLAR_FRAC = 1 - CONV_SOLAR_FRAC; //Default 0.8
    private static final double RAD_OCC_FRAC = 1 - CONV_OCC_FRAC; //Default 0.65
    //Variables
    private double volume;
    private double floor_area;
    private double elevation;
    private double air_temp;
    private double air_density;
    private double air_viscosity;
    private double humidityratio = 0;
    private double pressure; //Includes static pressure and height pressure
    private double mass;
    private boolean isIntzone;
    public Source source = new Source();
    public int ceilingIndex;
    public int floorIndex;
    public int ceilingIndex1;
    public int floorIndex1;
    public double SurfaceAzimuthAngle;
    public String wall;
    public double floorNumber;
    public double bldgOrient;
    public double SAA;
    public double windowsize;
    public double sideglazingarea;
    public double ZoneiSolarHeatLoad;
    public double ceilingT; //C
    public double floorT; //C
    public double coolingLoad = 0; //FADE
    public double heatingLoad = 0;  //Fde
    public double TotalConvectiveHeatLoad;
    public double TotalRadiativeHeatLoad;
    public double SolarHeatLoad;
    public double windowTransmissivity = 0.6; //FADE: What about this?
    //MAMB Nov 2011
    public double floortoceilingheight;
    public boolean isOcczone;
    //MAMB Nov 2011 end.
    public double facadewidth = 0;
    public double a_terrain = 0;
    public double b_terrain = 0;
    public double windowR = 0.17; //FADE: What about this?
    public boolean windowsclosed = false;
    public boolean isHumidityControl = false;
    public boolean isACon = false;
    public boolean isHeaton = false;
    //</editor-fold>
	public double cont; //Contaminant concentration. In this case, PM2.5
	public double gen; // Generation rate for contaminant species. C
	public double rem; // Removal coefficient for contaminant species. 
	boolean isPM = false;

	public void isPM(boolean isPM)
	{
		this.isPM = isPM;
	}
    public Zone(double volume, double floor_area, double elevation) {
        this.volume = volume;
        this.floor_area = floor_area;
        this.elevation = elevation;
        this.ceilingIndex = -1;
        this.floorIndex = -1;
        this.ceilingIndex1 = -1;
        this.floorIndex1 = -1;
        this.SurfaceAzimuthAngle = 180;
        this.sideglazingarea = 0;
        this.floortoceilingheight = 0;
        this.isOcczone = false;
        this.ceilingT = 20.0;
        this.floorT = 20.0;
    }

    //SUPERFADE
    public Zone(double volume, double elevation) {
        this.volume = volume;
        this.elevation = elevation;
    }
    //SUPERFADE
	
	//CK
	public void setCont(double cont) {
		this.cont = cont;
            
	}
	
	public double getCont() {
		return this.cont;
	}
	
	public void setGen(double gen) {
		this.gen = gen;
	}
	
	public double getGen() {
		return this.gen;
	}
	
	public void setRem(double rem) {
		this.rem = rem;
	}
	
	public double getRem() {
		return this.rem;
	}
    //CK
	
	//CK: INSERT METHODS FOR CHEMICAL KINETIC REACTIONS HERE IF NEEDED//
	
    public String getWall() {
        return this.wall;
    }

    public void setWall(String wallnb) {
        this.wall = wallnb;
    }

    public double getBldgOrientation() {
        return this.bldgOrient;
    }

    public void setBldgOrientation(double bldgOrientation) {
        this.bldgOrient = bldgOrientation;
    }

    public double getSurfaceAzimuthAngle() {
        return this.SAA;
    }

    public void setSurfaceAzimuthAngle(double SurfaceAzimuthAngle) {
        this.SAA = SurfaceAzimuthAngle;
    }

    public double getWindowArea() {
        return this.windowsize;
    }

    public void setWindowArea(double windowsize) {
        this.windowsize = windowsize;
    }

    public double getSideGlazingArea() {
        return this.sideglazingarea;
    }

    public void setSideGlazingArea(double sideglazingarea) {
        this.sideglazingarea = sideglazingarea;
    }    

    public double calSolarHeatLoad(double latitude, double declination, double solarheatload_xhour, double solardiffuseheatload_hourx, int timeofday) {
        double costheta = 0;
        double hourangle = -(15.0 / 3600.0) * ((double) timeofday - 12.0 * 3600.0) * 3.141592 / 180.0;
        double latrad = latitude * 3.141592 / 180.0;
        double decrad = declination * 3.141592 / 180.0;
        double a1 = -Math.sin(decrad) * Math.cos(latrad) * Math.cos(this.getSurfaceAzimuthAngle());
        double a2 = Math.cos(decrad) * Math.sin(latrad) * Math.cos(this.getSurfaceAzimuthAngle()) * Math.cos(hourangle);
        double a3 = Math.cos(decrad) * Math.sin(this.getSurfaceAzimuthAngle()) * Math.sin(hourangle);
        if (Math.acos(a1 + a2 + a3) > 3.1416 / 2.0) {
            costheta = 0;
        } //for angles greater than 90, the sun is not directly hitting on the surface
        else {
            costheta = (a1 + a2 + a3);
        }
        double SolarDirectHeatLoad = solarheatload_xhour * windowTransmissivity * this.getSideGlazingArea() * costheta;
        double SolarDiffuseHeatLoad = solardiffuseheatload_hourx * windowTransmissivity * this.getSideGlazingArea() * 0.45;//vertical diffuse=0.45 horizontal diffuse
        return SolarDirectHeatLoad + SolarDiffuseHeatLoad;
    }

    public double getSolarHeatLoad() {
        return ZoneiSolarHeatLoad;
    }

    public void setSolarHeatLoad(double SHLoads) {
        ZoneiSolarHeatLoad = SHLoads;
    }
    
    public double getCoolingHeatLoad() {
        return this.coolingLoad;
    }

    public void setCoolingHeatLoad(double CLoads) {
        this.coolingLoad = CLoads;
    }
    
    public double getHeatingHeatLoad() {
        return this.heatingLoad;
    }

    public void setHeatingHeatLoad(double HLoads) {
        this.heatingLoad = HLoads;
    }

    public double getTotalConvectiveHeatLoad() {
        return TotalConvectiveHeatLoad;
    }

    public void setTotalConvectiveHeatLoad(double SolarHL, double OccHL, int tmasscheck) {
        if (tmasscheck == 0) {
            TotalConvectiveHeatLoad = SolarHL + OccHL + this.getHeatingHeatLoad() - this.getCoolingHeatLoad(); //FADE added heating and cooling load
        } else {
            TotalConvectiveHeatLoad = SolarHL * CONV_SOLAR_FRAC + OccHL * CONV_OCC_FRAC + this.getHeatingHeatLoad() - this.getCoolingHeatLoad();
        }
        //<editor-fold defaultstate="collapsed" desc="Console output">
            /*System.out.println("solar " + SolarHL + "   " + SolarHL*CONV_SOLAR_FRAC);
            System.out.println(" occ  " + OccHL + "   " + OccHL*CONV_OCC_FRAC);
            System.out.println("total conv" + TotalConvectiveHeatLoad);*/
        //</editor-fold>
    }

    public double getTotalRadiativeHeatLoad() {
        return TotalRadiativeHeatLoad;
    }

    public void setTotalRadiativeHeatLoad(double SolarHL, double OccHL, int tmasscheck) {
        if (tmasscheck == 0) {
            TotalRadiativeHeatLoad = 0;
        } else {
            TotalRadiativeHeatLoad = SolarHL * RAD_SOLAR_FRAC + OccHL * RAD_OCC_FRAC; //RAD_SOLAR_FRAC, RAD_OCC_FRAC
        }
        //<editor-fold defaultstate="collapsed" desc="Console output">
            /*System.out.println("solar " + SolarHL + "   " + SolarHL*0.8);
            System.out.println(" occ  " + OccHL + "   " + OccHL*0.65);
            System.out.println("total rad " + TotalRadiativeHeatLoad);*/
        //</editor-fold>
    }

    public boolean getIsIntZone() {
        return this.isIntzone;
    }

    public void setIsIntZone(boolean isInt) {
        isIntzone = isInt;
    }

    public double getVolume() {
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getFloor_area() {
        return this.floor_area;
    }

    public void setFloor_area(double floor_area) {
        this.floor_area = floor_area;
    }

    public double getElevation() {
        return this.elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public double getAir_temp() {
        return this.air_temp;
    }

    public void setAir_temp(double air_temp) {
        this.air_temp = air_temp;
    }
    
    public double getHumidityRatio() {
        return this.humidityratio;
    }
    
    public void setHumidityRatio(double hr) {
        this.humidityratio = hr;
    }

    public double getPressure() {
        return this.pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getAir_density() {
        return this.air_density;
    }

    public void calAir_density() {
        air_density = ATM_PRESSURE / (R * (this.air_temp + TO_KELVIN));
    }

    public double getAir_viscosity() {
        this.calAir_viscosity();
        return this.air_viscosity;
    }

    public void calAir_viscosity() {
        air_viscosity = VISCOS_REF * (TEMP_REF + SUTH_CONST) / ((this.air_temp + TO_KELVIN) + SUTH_CONST) * Math.pow((this.air_temp + TO_KELVIN) / (TEMP_REF), (SUTH_EXP)); //FADE: Use Sutherland's formula to calculate viscosity
    }

    public double getMass() {
        this.calMass();
        return this.mass;
    }

    public void calMass() {
        this.calAir_density();
        this.mass = air_density * volume;
    }

    public double getCp() {
        return CP;
    }

    public double getCv() {
        return CV;
    }

    public double getGamma() {
        return GAMMA;
    }

    //2011 Nov MAMB
    public double getFloorToCeilingHeight() {
        return this.floortoceilingheight;
    }

    public void setFloorToCeilingHeight(double floortoceilingheight) {
        this.floortoceilingheight = floortoceilingheight;
    }

    public boolean getIsOccZone() {
        return this.isOcczone;
    }

    public void setIsOccZone(boolean isOcc) {
        this.isOcczone = isOcc;
    }
    
    public void setIsWindowsClosed(boolean isClosed) { //FADE
        this.windowsclosed = isClosed;
    }
    
    public boolean getIsWindowsClosed() {
        return this.windowsclosed;
    }
    
    public void setIsHumidityControl(boolean isControl) {
        this.isHumidityControl = isControl;
    }
    
    public boolean getIsHumidityControl() {
        return this.isHumidityControl;
    }
    
    public void setIsACon(boolean isAC) {
        this.isACon = isAC;
    }
    
    public boolean getIsACon() {
        return this.isACon;
    }
    
    public void setIsHeaton(boolean isHeat) {
        this.isHeaton = isHeat;
    }
    
    public boolean getIsHeaton() {
        return this.isHeaton;
    }
    
    public void setFloorNumber(double floornum) {
        this.floorNumber = floornum;
    }
    
    public double getFloorNumber() {
        return this.floorNumber;
    }
    
    public void printZone() {
        System.out.println("Air temperature:" + air_temp + "  \nAir density: " + air_density + "  \npressure:" + pressure);
        System.out.println("Is IntZone=" + isIntzone + "  \nVolume=" + volume + "   \nfloor_area=" + floor_area + "  \nelevation=" + elevation + "     \noccupancy heat source=" + source.getOccupancyHeat_source());
	    }
	
	
	//CK
	public void displayPM(boolean isPM)
	{
		if(isPM)
		{
			System.out.println("PM2.5 Concentration" + cont + " \nGeneration rate" + gen+ " \nRemoval rate"+rem);
		}
		
	}
	//CK
	
    public boolean useRadiantModel() {
        if ((this.ceilingIndex >= 0 || this.ceilingIndex1 >= 0) && (this.floorIndex >= 0 || this.floorIndex1 >= 0)) {
            return true;
        } else {
            return false;
        }
    }
    
    public double getFacadeWidth() {return this.facadewidth;}
    public void setFacadeWidth(double facadewidth){ this.facadewidth=facadewidth;}
}