/*
 * This class models air filters in the pathways of airflows (i.e. through windows, any ducts or other openings
 */

/**
 *
 * @author ckrishna 2016
 */
public class WindowFilters {
    private double efficiency;
    private double pressuredrop;
    public double zone_i; //Zone I and Zone J indicate the interface in which the filter operates
    public double zone_j;
    
    public WindowFilters(){}
    public WindowFilters(double e, double pd)
    {
        this.efficiency = e;
        this.pressuredrop = pd;
    }
    
    public double get_pd()
    {
        return this.pressuredrop;
    }
    public double getE()
    {
        return this.efficiency;
    }
}
