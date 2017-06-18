//20090616 This code suffered a major cleanup by MAMB.  
//Tmass.java
// Created on 2005 March 16th, 4:33pm by Jinchao Yuan
// This class is to hold the information of each zone
//
//package mmpn1;

public class TMass implements java.io.Serializable {

    private double volume = 0.0;
    private double area = 0.0;
    private double temperature = 0.0;
    private double density = 2500;
    private double h = 3; //W/m2.K // 
    private double mass = 0.0;	// 
    private double Cp = 880;   // Unit: W/kg.K  
    private double hA = 10; //W/K 
    private boolean isAdiabatic = false;
    private double k = 0.1; //W/m.K //
    private int absorbssolar = 1;
    private double perimeter = 0; //FADENEW
    private String type = "floor";
    private double SAA;
    private static double absorptivity = 0.57; //Avg for brick (common,red), concrete
    private double solarHeatLoad = 0;

    public TMass(double volume, double area, double density) {
        this.volume = volume;
        this.area = area;
        this.density = density;
    }

    public TMass(double volume, double area, double perimeter, double density, double k) { //FADENEW
        this.volume = volume;
        this.area = area;
        this.perimeter = perimeter;
        this.density = density;
        this.k = k;
    }

    public boolean getIsAdiabatic() {
        return this.isAdiabatic;
    }

    public void setIsAdiabatic(boolean isAdiabatic) {
        this.isAdiabatic = isAdiabatic;
    }

    public double getArea() {
        return this.area;
    }

    public void setArea(double floor_area) {
        this.area = floor_area;
    }

    public double getVolume() {
        return this.volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getDensity() {
        return this.density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMass() {
        return this.mass;
    }

    public void calMass() {
        this.mass = density * volume;
    }

    public double getH(double airTemp) {
        this.calH(airTemp); //FADENEW
        return this.h;
    }

//    public void setH(double h) {
//        this.h = h;
//    }

    public void calHA(double airTemp) {
        this.hA = this.getH(airTemp) * area; //FADENEW
    }

    public double getHA(double airTemp) {
        //this.getH(airTemp); //FADENEW: This is horrible!
        this.calHA(airTemp);
        return this.hA;
    }

    public void setk(double k) {
        this.k = k;
    }

    public double getk() {
        return k;
    }

    public double getkAoX() {
        return k * area / volume * area;
    }

    public double getCp() {
        return this.Cp;
    }

    public void setCp(double Cp) {
        this.Cp = Cp;
    }

    public int getAbsorbsSolar() {
        return this.absorbssolar;
    }

    public void setAbsorbsSolar(int absorbssolar) {
        this.absorbssolar = absorbssolar;
    }

    public void printTMass() {
        System.out.println("TMass temperature:" + temperature + "  TMass density: " + density + "  h:" + h);
        System.out.println("Is Adiabatic=" + isAdiabatic + "  Volume=" + volume + "   area=" + area + "  temperature=" + temperature + "     temperature=" + temperature);
    }
    
    //FADENEW::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public void calH(double airTemp){
       if (this.type.equals("floor")) {
//           this.h = 2.175/Math.pow(4*this.area/this.perimeter,0.076) * Math.pow(Math.abs(this.temperature - airTemp),0.308); //FADENEW: Need better correlation
           this.h = 10;
//           System.out.println("Floorh "+this.h+ " delta temp "+(this.temperature - airTemp));
       } else {
//           this.h = 0.704/Math.pow(4*this.area/this.perimeter,0.601) * Math.pow(Math.abs(this.temperature - airTemp),0.133); //FADENEW: Need better correlation
           this.h = 10;
//           System.out.println("Ceilingh "+this.h+ " delta temp "+(this.temperature - airTemp));
        }
    }

    public double getPerimeter() {
        return this.perimeter;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public double getSurfaceAzimuthAngle() {
        return this.SAA;
    }

    public void setSurfaceAzimuthAngle(double SurfaceAzimuthAngle) {
        this.SAA = SurfaceAzimuthAngle;
    }

    public double calSolarHeatLoad(double latitude, double declination, double solarheatload_xhour, double solardiffuseheatload_hourx, int timeofday) { //FADE thinks we should change solar heat gains from an attribute of zones to an attribute of surfaces (openings and TMasses)
        double costheta;
        double frac = 0.45;
        if (this.getType().equals("roof")) {
            frac = 1;
            costheta = 1;
        } else {
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
        }
        double SolarDirectHeatLoad = solarheatload_xhour * absorptivity * this.getArea() * costheta;
        double SolarDiffuseHeatLoad = solardiffuseheatload_hourx * absorptivity * this.getArea() * frac;//vertical diffuse=0.45 horizontal diffuse
        return (SolarDirectHeatLoad + SolarDiffuseHeatLoad) * this.getAbsorbsSolar();
    }

    public void setSolarHeatLoad(double sunload) {
        this.solarHeatLoad = sunload;
    }
    
    public double getSolarHeatLoad() {
        return this.solarHeatLoad;
    }
    //FADENEW::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
}