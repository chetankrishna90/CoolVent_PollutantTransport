
/**
 * Zone.java
 * Holds the information of each flow path
 *
 * Created 4/28/2003
 * Major cleanup by MAMB 6/16/2009
 * Major cleanup by FADE 3/12
 * 
 * @author
 * @version 3/27/2012
 */
public class Flowpath implements java.io.Serializable {
    //<editor-fold defaultstate="collapsed" desc="Class variables">
    //Constants

    private static final double G = 9.81;
    //Variables
    private double height;
    private double width;
    private double area;
    public double elevation;
    public boolean isLargeOpen;
    private double powerN;
    private double delta_p;
    private double Cd;
    private double wind_v;
    private double Cw;
    private int connectionZoneNum1;
    private int connectionZoneNum2;
    public boolean isFan;
    public double a2;
    public double a1;
    public double a0;
    public double b2;
    public double b1;
    public double m_eta;
    public double fanD;
    public double gamma;
    public String fanType;
    public boolean isOn;
    public String openingType;
    public double openingFraction;
    public double upperopeningheight;
    public double upperopeningarea;
    public double windowheight;
    public int doubleopeningcheck;
    public double dpFan = 0.0; //FADE: What about this?
    public double openingLongitude;
    public double surfaceLength;
    public double surfaceHeight;
    public double surfaceSideRatio = 1;
    public double openingAzimuth = 1;
    public double openingx = 0.5;
    public double openingy = 0;
            
    
    //</editor-fold>

    public Flowpath(double height, double width, double elevation, boolean isLargeOpen, double wind_v, double Cw, String openingType) {
        this.height = height;
        this.width = width;
        this.elevation = elevation;
        this.isLargeOpen = isLargeOpen;
        this.wind_v = wind_v;
        this.Cw = Cw;
        this.isFan = false;
        this.openingType = openingType;
        this.area = height * width;
        this.openingFraction = 1;
    }
    
    public Flowpath(double openingarea, double operablearea, double elevation, String openingType) {
        this.area = operablearea;
        this.elevation = elevation;
        this.isFan = false;
        this.openingType = openingType;
        this.openingFraction = 1;
    }

    public void turnintoFan(double a, double b, double c, double d, double e, double f, double g, double i, String type) { //FADE included a new instance of the method turnintoFan
        this.isFan = true;
        this.a2 = a;
        this.a1 = b;
        this.a0 = c;
        this.fanD = d;
        this.gamma = e;
        this.b2 = f;
        this.b1 = g;
        this.m_eta = i;
        this.fanType = type;
        this.isOn = true;
        this.Cd = 0.6;
    }

    public void turnOnFan() {
        this.isOn = true;
    }

    public void turnOffFan() {
        this.isOn = false;
    }

    public double getHeight() {
        return this.height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return this.width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getElevation() {
        return this.elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }

    public boolean getIsLargeOpen() {
        return this.isLargeOpen;
    }

    public void setIsLargeOpen(boolean isLargeOpen) {
        this.isLargeOpen = isLargeOpen;
    }

    public double getPowerN() {
        return this.powerN;
    }

    public void setPowerN(double powerN) {
        this.powerN = powerN;
    }

    public double getDelta_p() {
        return this.delta_p;
    }

    public double getArea() {
        return this.area;
    }

    public void calDelta_p(Zone I, Zone J, boolean isIntzone, boolean oneZone, double prevcw) { //isIntzone is for zone i
        I.calAir_density();
        J.calAir_density();

        if (isIntzone == false) { //FADE: All Zones inside the building are isIntZone==true. All Openings are isIntZone==false. All ambientZones are isIntZone==false
//            System.out.println("////////////////////////////////////////////////////////////////////DeltaP01");
//            System.out.println("P0,0 " + I.getPressure());
//            System.out.println("rho * g " + I.getAir_density() * G);
//            System.out.println("this elevation " + this.elevation);
//            System.out.println("h0 " + I.getElevation());
//            System.out.println("1/2 * rho " + I.getAir_density() * 0.5);
            if (oneZone) { //One zone in cross ventilation
//                System.out.println("Cw " + prevcw);
//                            System.out.println("V^2 " + Math.pow(Math.abs(wind_v), 2.0));
//            System.out.println("Positive part " + (I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) + prevcw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0));
//            
//            System.out.println("Pi,0 " + J.getPressure());
//            System.out.println("rho * g " + J.getAir_density() * G);
//            System.out.println("this elevation " + this.elevation);
//            System.out.println("hi " + J.getElevation());
//            System.out.println("Negative part " + (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())));
                this.delta_p = I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) + prevcw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0 - (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())); //ambientZones
            } else {
                this.delta_p = I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) + Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0 - (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())); //ambientZones
//                System.out.println("Cw " + Cw);
//                            System.out.println("V^2 " + Math.pow(Math.abs(wind_v), 2.0));
//            System.out.println("Positive part " + (I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) + Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0));
//            
//            System.out.println("Pi,0 " + J.getPressure());
//            System.out.println("rho * g " + J.getAir_density() * G);
//            System.out.println("this elevation " + this.elevation);
//            System.out.println("hi " + J.getElevation());
//            System.out.println("Negative part " + (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())));
            }
        } else {
//            System.out.println("//////////////////////////////////////////////////////////////////////DeltaP10");
//            System.out.println("Pi,0 " + I.getPressure());
//            System.out.println("rho * g " + I.getAir_density() * G);
//            System.out.println("this elevation " + this.elevation);
//            System.out.println("hi " + I.getElevation());
//            System.out.println("Cw " + Cw);
//            System.out.println("1/2 * rho " + I.getAir_density() * 0.5);
//            System.out.println("V^2 " + Math.pow(Math.abs(wind_v), 2.0));
//            System.out.println("Positive part " + (I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) - Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0));
//            
//            System.out.println("P0,0 " + J.getPressure());
//            System.out.println("rho * g " + J.getAir_density() * G);
//            System.out.println("this elevation " + this.elevation);
//            System.out.println("h0 " + J.getElevation());
//            System.out.println("Negative part " + (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())));
            this.delta_p = I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) - Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0 - (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())); //Zones
        }
    }

    /*FADE: This might be useless
    public void calDelta_p(Zone I, Zone J, boolean isIntzone, int isFan, double flowrate) { //FADE: Flowrate is from I to J
    I.calAir_density();
    J.calAir_density();
    
    if (isIntzone == false) { //I is ambient zone
    if (isFan == 1) { //This never happens
    this.delta_p = I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) + Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0 - (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())) + dpFan;
    } else if (isFan == -1) {//Air is flowing from J (internal zone) to I (ambient zone)
    this.delta_p = I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) + Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0 - (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())) - dpFan;
    } else {
    this.delta_p = I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) + Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0 - (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation()));
    }
    } else { //I is internal zone
    if (isFan == 1) { //Flow is from I (internal zone) to J (ambient zone). This is the one that should be hapening
    this.delta_p = -(this.a2 * Math.pow(flowrate,2) + this.a1 * Math.abs(flowrate) + this.a0); //FADE: P_I < P_J, thus delta_p_{i,j} has to be negative               
    //                this.delta_p = I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) - Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0 - (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())) + dpFan;
    } else if (isFan == -1) {//This never happens
    this.delta_p = I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) - Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0 - (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation())) - dpFan;
    } else {
    this.delta_p = I.getPressure() - I.getAir_density() * G * (this.elevation - I.getElevation()) - Cw * I.getAir_density() * Math.pow(Math.abs(wind_v), 2.0) / 2.0 - (J.getPressure() - J.getAir_density() * G * (this.elevation - J.getElevation()));
    }
    }
    }
     */
    public double getCd() {
        return this.Cd;
    }

    public void setCd(double Cd) {
        this.Cd = Cd;
    }

    public double getWind_v() {
        return this.wind_v;
    }

    public void setWind_v(double wind_v) {
        this.wind_v = wind_v;
    }

    public double getCw() {
        return this.Cw;
    }

    public void setCw(double Cw) {
        this.Cw = Cw;
    }

    public void setConnectionZoneNums(int zoneNum1, int zoneNum2) {
        connectionZoneNum1 = zoneNum1;
        connectionZoneNum2 = zoneNum2;
    }

    public int getConnectionZoneNum1() {
        return this.connectionZoneNum1;
    }

    public int getConnectionZoneNum2() {
        return this.connectionZoneNum2;
    }

    public double getUpperOpeningHeight() { //MAMB Dec 2012
        return this.upperopeningheight;
    }

    public void setUpperOpeningHeight(double upperopeningheight) { //MAMB Dec 2012
        this.upperopeningheight = upperopeningheight;
    }

    public double getUpperOpeningArea() { //MAMB Dec 2012
        return this.upperopeningarea;
    }

    public void setUpperOpeningArea(double upperopeningarea) { //MAMB Dec 2012
        this.upperopeningarea = upperopeningarea;
    }

    public double getWindowHeight() { //MAMB Dec 2012
        return this.windowheight;
    }

    public void setWindowHeight(double windowheight) { //MAMB Dec 2012
        this.windowheight = windowheight;
    }

    public int getDoubleOpeningCheck() { //MAMB Dec 2012
        return this.doubleopeningcheck;
    }
    
    //sdr_hulic
    public String getOpeningType()
    {
    	return this.openingType;
    }
    
    public void setDoubleOpeningCheck(int doubleopeningcheck) { //MAMB Dec 2012
        this.doubleopeningcheck = doubleopeningcheck;
    }
    
    public void setOpeningFraction(double opfrac){ //FADENEW
        this.openingFraction = opfrac;
    }
    
    public double getOpeningFraction(){ //FADENEW
        return this.openingFraction;
    }

    public void printFlowpath() {
        System.out.println("Height:" + height + " Width: " + width + "  Elevation:" + elevation);
        System.out.println("Is Large Opening=" + isLargeOpen + "  Connection Zone1 Num=" + (connectionZoneNum1 + 1) + "  Connection Zone2 Num=" + (connectionZoneNum2 + 1) + "   Cd=" + Cd + "  PowerN=" + powerN + "     Wind Velocity=" + wind_v + "   Cw=" + Cw);
    }
    
    public void setOpeningLongitude(double openingLe) {
        this.openingLongitude = openingLe;
    }
    
    public double getOpeningLongitude() {
        return this.openingLongitude;
    }
    
    public void setSurfaceLength(double openingLe) {
        this.surfaceLength = openingLe;
    }
    
    public double getSurfaceLength() {
        return this.surfaceLength;
    }
    
    public void setSurfaceHeight(double openingLe) {
        this.surfaceHeight = openingLe;
    }
    
    public double getSurfaceHeight() {
        return this.surfaceHeight;
    }
    
    public void setSurfaceSideRatio(double openingLe) {
        this.surfaceSideRatio = openingLe;
    }
    
    public double getSurfaceSideRatio() {
        return this.surfaceSideRatio;
    }
    
    public void setOpeningAzimuth(double openingLe) {
        this.openingAzimuth = openingLe;
    }
    
    public double getOpeningAzimuth() {
        return this.openingAzimuth;
    }
    
    public void setOpeningx(double opx)
    {
        this.openingx = opx;
    }
    
    public double getOpeningx() {
        return this.openingx;
    }
    
    public void setOpeningy(double opy)
    {
        this.openingy = opy;
    }
    
    public double getOpeningy() {
        return this.openingy;
    }
}