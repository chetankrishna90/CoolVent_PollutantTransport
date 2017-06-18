/*
 * This class models filters or room air purifiers, as pollutant 'sinks', based on an airflow and existing mass of pollutants in our well mixed zone
 * Note that actual filters follow an efficiency curve of removal efficiency vs. the size of the particles
 * For our calculations however, as we don't have a particle mass distribution for PM2.5 modeled, we need to use simple constant efficiences
 */

/**
 *
 * @author ckrishna
 */
public class Filters {
    private double efficiency;
    private double airflow;
    private double zone;
    public Filters()
    {     
    }
    public Filters(double zone, double efficiency, double airflow)
    {
        this.zone = zone;
        this.efficiency = efficiency;
        this.airflow = airflow;        
    }
    public double getEfficiency()
    {
        return this.efficiency;
    }
    public double getAirflow()
    {
        return this.airflow;
    }
    public double getZoneNumber()
    {
        return this.zone;
    }
    public double removal(double concentration)
    {
        return (-1)*this.efficiency*this.airflow*concentration;
        //CK EDIT: The efficiency is a fraction ~ (0,1) ; concentration is kg/kg ; airflow is kg/s
    }    
    
}
