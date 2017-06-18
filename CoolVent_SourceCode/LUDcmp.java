import Jama.*;

public class LUDcmp {
	
	/*
	Written by CK in 09/2015
	*/

		public int zoneNumber;
		public double[][] flowrates;
		public double[] concentrations;
		public double[][] z; //temporary variable
		public double[][] f_t; //temporary variable for flows*theta*h
		public double[][] f_th; //temporary variable for flows*theta*h
		public double[][] f_dash; //temporary variable for flows*(1-theta)*h
		public double[][] gr_t; //temporary variable for gen/rem constants (net per room)
		public double[][] gr_th; //temporary variable for gen/rem constants at time t+h
		public double[][] z_th; //The new zone concentrations
		double theta = 0.5; //can vary depending on what 'dt' is set to
		
		public LUDcmp()
		{
			this.zoneNumber = 0;				
		}
                
                public void setTheta(int dt)
                {
                    this.theta = dt;
                }
		
		public void populate (int zoneNumber)
		{
			this.zoneNumber = zoneNumber;
			this.flowrates = new double[zoneNumber][zoneNumber];
			this.concentrations = new double[zoneNumber];
		}
		
		public void popFlow (double F, int i, int j)
		{
			this.flowrates[i][j] = F;
		}
		
		public void popConc (double C, int i)
		{
			this.concentrations[i] = C;
		}
		
		public double getFlow (int i, int j)
		{
			return this.flowrates[i][j];		
		}
		
		public double getConc (int i)
		{
			return this.concentrations[i];
		}		
		
		public Matrix LUDcmpCal(double flows_t[][], double flows_th[][], double zoneConcs[], double masses_t[][], double masses_th[][], double genrem_t[], double genrem_th[], double h, double outdoorConc) //note: flowrates are in kg/s or similar mass flow rates; concentrations in mass/mass. Similarly, gen/rem constants must be mass/time
		{
		 	Matrix Flows_t = new Matrix(flows_t);
		 	Matrix Flows_th = new Matrix(flows_th);
		 	z = new double[zoneNumber][1];
                        z_th = new double[zoneNumber][1];
		 	f_t = new double[zoneNumber][zoneNumber];
			f_th = new double[zoneNumber][zoneNumber];
		 	gr_t = new double[zoneNumber][1];
		 	gr_th = new double [zoneNumber][1];
		 	
		 	for (int i = 0;i<zoneNumber;i++)
		 	{       
                                z[i][0] = zoneConcs[i];   
                                if(i == 0) {z[i][0] = outdoorConc; /*z_th[i][0] = outdoorConc;*/}                        
		 		                             
		 		gr_t[i][0] = genrem_t[i];
		 		gr_th[i][0] = genrem_th[i];
		 	}
		 	for (int i = 0; i<zoneNumber; i++)
		 	{
		 		gr_t[i][0] = genrem_t[i];
		 		for(int j = 0; j<zoneNumber; j++)
		 		{
		 			f_th[i][j] = flows_th[i][j];
		 			f_t[i][j] = flows_t[i][j];
		 		}
		 	}
		 	Matrix	fth_matrix = new Matrix(f_th);
		 	Matrix ft_matrix = new Matrix(f_t);
		 	Matrix x_t = new Matrix(z); //zone concentrations at time t
		 	Matrix Masses_t = new Matrix(masses_t);
		 	Matrix Masses_th = new Matrix(masses_th);
		 	Matrix A = Masses_th.minus(fth_matrix.times(theta*h));
		 	
		 	Matrix e_th = new Matrix(gr_th);
		 	Matrix e_t = new Matrix(gr_t);
		 			 	//For the implicit differential equation setup
		 	Matrix b1 = Masses_t.times(x_t);
		 	Matrix b2 = ft_matrix.times(x_t);
		 	Matrix b3 = b2.plus(e_t);
		 	Matrix b4 = b3.times((1-theta)*h);
		 	Matrix b5 = e_th.times(theta*h);
		 	Matrix b = (b1.plus(b4)).plus(b5);
		 	
		 	Matrix x_th = A.solve(b);
                        
		 	return x_th;
		}
                
                //This next method solves our matrices when we have filters between two zones
                public Matrix LUDcmpCal_Filter(double flows_t[][], double flows_th[][], double zoneConcs[], double masses_t[][], double masses_th[][], double genrem_t[], double genrem_th[], double h, double outdoorConc, WindowFilters wf[][]) //note: flowrates are in kg/s or similar mass flow rates; concentrations in mass/mass. Similarly, gen/rem constants must be mass/time
		{
		 	Matrix Flows_t = new Matrix(flows_t);
		 	Matrix Flows_th = new Matrix(flows_th);
		 	z = new double[zoneNumber][1];
                        z_th = new double[zoneNumber][1];
		 	f_t = new double[zoneNumber][zoneNumber];
			f_th = new double[zoneNumber][zoneNumber];
		 	gr_t = new double[zoneNumber][1];
		 	gr_th = new double [zoneNumber][1];
		 	
		 	for (int i = 0;i<zoneNumber;i++)
		 	{       
                                z[i][0] = zoneConcs[i];   
                                if(i == 0) {z[i][0] = outdoorConc; /*z_th[i][0] = outdoorConc;*/}                        
		 		                             
		 		gr_t[i][0] = genrem_t[i];
		 		gr_th[i][0] = genrem_th[i];
		 	}
		 	for (int i = 0; i<zoneNumber; i++)
		 	{
		 		gr_t[i][0] = genrem_t[i];
		 		for(int j = 0; j<zoneNumber; j++)
		 		{
		 			f_th[i][j] = flows_th[i][j]*(1-wf[i][j].getE());//wf[i][j].get_pd()*/; //modified for pressure drop due to filter and also ...
		 			f_t[i][j] = flows_t[i][j]*(1-wf[i][j].getE());//wf[i][j].get_pd()*/; //modifying for filter efficiency (which impacts pollutant transport)
		 		}
		 	}
		 	Matrix	fth_matrix = new Matrix(f_th);
		 	Matrix ft_matrix = new Matrix(f_t);
		 	Matrix x_t = new Matrix(z); //zone concentrations at time t
		 	Matrix Masses_t = new Matrix(masses_t);
		 	Matrix Masses_th = new Matrix(masses_th);
		 	Matrix A = Masses_th.minus(fth_matrix.times(theta*h));
		 	
		 	Matrix e_th = new Matrix(gr_th);
		 	Matrix e_t = new Matrix(gr_t);
		 			 	//For the implicit differential equation setup
		 	Matrix b1 = Masses_t.times(x_t);
		 	Matrix b2 = ft_matrix.times(x_t);
		 	Matrix b3 = b2.plus(e_t);
		 	Matrix b4 = b3.times((1-theta)*h);
		 	Matrix b5 = e_th.times(theta*h);
		 	Matrix b = (b1.plus(b4)).plus(b5);
		 	
		 	Matrix x_th = A.solve(b);
                        
		 	return x_th;
		}
	
	

}
