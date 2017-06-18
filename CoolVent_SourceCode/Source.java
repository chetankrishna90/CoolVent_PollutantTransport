/*20090616 This code suffered a major cleanup by MAMB. 
 source.java
 * Created on 2003��4��28��, ����6:07
 * This class is to hold the sources's information:
 * heat, airflow, contaminant
 */
//package mmpn1;

public class Source implements java.io.Serializable{
    private double airflow_source=0.0;
    private double airflow_temp=0.0;
    private double Occupancyheat_source=0.0;
    private double contaminant=0.0;
    private double totalheatload = 0.0;
    public double TotalHeatLoad = 0;
    public double windowTransmissivity = 0.5; //Double glazed, low-e (for clear glass ~ 0.8)

    /** Creates a new instance of source */
    public Source() {
    }
    public double getAirflow_source(){ return this.airflow_source;}
    public void setAirflow_source(double airflow_source){ this.airflow_source=airflow_source;}
    public double getAirflow_temp(){ return this.airflow_temp;}
    public void setAirflow_temp(double airflow_temp){ this.airflow_temp=airflow_temp;}
    public double getOccupancyHeat_source(){ return this.Occupancyheat_source;}
    public void setOccupancyHeat_source(double Occupancyheat_source){ this.Occupancyheat_source=Occupancyheat_source;}
    public double getContaminant(){ return this.contaminant;}
    public void setContaminant(double contaminant){ this.contaminant=contaminant;}
    
    public double getTotalHeatLoad(){return this.totalheatload;}
    public void setTotalHeatLoad(double THLoads){THLoads = totalheatload;}


 
}
