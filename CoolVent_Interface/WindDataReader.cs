using System;
using System.Collections.Generic;
//using System.Linq;
using System.Text;
using System.IO;
using System.Windows.Forms;

namespace CoolVent
{
    class WindDataReader
    {
        /*20090301 by MAMB
        New class file that reads epw files, instead of tmy2 data.
        */

        //import java.io.*;
        //import java.util.StringTokenizer; 

        private const double C1 = -5.6745359e3;
        private const double C2 = 6.3925247;
        private const double C3 = -9.677843e-3;
        private const double C4 = 6.2215701e-7;
        private const double C5 = 2.0747825e-9;
        private const double C6 = -9.484024e-13;
        private const double C7 = 4.1635019;
        private const double C8 = -5.8002206e3;
        private const double C9 = 1.3914993;
        private const double C10 = -4.8640239e-2;
        private const double C11 = 4.1764768e-5;
        private const double C12 = -1.4452093e-8;
        private const double C13 = 6.5459673;
        private const double CTOK = 273.15; //From Celsius to Kelvin
        private const double PERCENT = 100; //100%
        private const double ASHRAECONST = 0.621945; //Constant in equation 22 in ASHRAE Fundamentals 2009

        public double latitude, elevation;
        public String city, state, country;
        public double[,] DryBulbAve = new double[12, 24];
        public double[,] RelHumAve = new double[12, 24];
        public double[,] PressureAve = new double[12, 24];
        public double[,] PSatWAve = new double[12, 24];
        public double[,] PPWAve = new double[12, 24];
        public double[,] omega = new double[12, 24];

        public double[,] WindDirAve = new double[12, 24];//Need to make this a mode
        public double[,] WindVelAve = new double[12, 24];
        public double[,] SolarRadAve = new double[12, 24];
        public double[,] SolarDiffRadAve = new double[12, 24];
        public double[] DeclinationAve = new double[12];

        //FADE: changed format so it is easy to print all the information for daily simulations
        public double[, ,] DryBulbDump = new double[12, 31, 24];
        public double[, ,] RelHumDump = new double[12, 31, 24];
        public double[, ,] PressureDump = new double[12, 31, 24];
        public double PSatDump;
        public double PPWDump;
        public double[, ,] omegaDump = new double[12, 31, 24];
        public double[, ,] WindDirDump = new double[12, 31, 24];
        public double[, ,] WindVelDump = new double[12, 31, 24];
        public double[, ,] SolarRadDump = new double[12, 31, 24];
        public double[, ,] SolarDiffRadDump = new double[12, 31, 24];

        #region FADE: Old code
        //public double[,] DryBulbDump = new double[365, 24];
        //public double[,] RelHumDump = new double[365, 24];
        //public double[,] PressureDump = new double[365, 24];
        //public double[,] WindDirDump = new double[365, 24];
        //public double[,] WindVelDump = new double[365, 24];
        //public double[,] SolarRadDump = new double[365, 24];
        //public double[,] SolarDiffRadDump = new double[365, 24]; 
        #endregion

        public void WindRead(String filename) //throws IOException 
        {
            int month, hour, day;
            double[] dblb = new double[24];
            double[] rhum = new double[24]; //FADE added
            double[] press = new double[24]; //FADE added
            double[] hr = new double[24]; //FADE added humidity ratio
            //double []wdir = new double [24];
            double[] wvel = new double[24];
            double[] srad = new double[24];
            double[] sdrad = new double[24];
            int[] daysinmonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

            String item = null;
            try
            {
                StreamReader br = new StreamReader(filename);

                // parse first line
                String data = br.ReadLine();

                int stcounter = 0;
                char[] delims = { char.Parse(",") };
                string[] st = data.Split(delims);
                item = st[stcounter];//changed by Marcel
                city = st[++stcounter];
                state = st[++stcounter];
                country = st[++stcounter];
                item = st[stcounter += 2];//I hope this updates properly
                latitude = double.Parse(st[++stcounter]);
                elevation = double.Parse(st[stcounter += 3]);
                //System.out.println("Latt:  " + latitude+ "  " + city + "  " + state + "  " + country);

                // skip next 7 lines
                for (int i = 0; i < 7; i++)
                    item = br.ReadLine();

                //Console.WriteLine("B");

                // start calculations
                int dayinyear = 0; //FADE
                for (month = 0; month < 12; month++)
                {
                    DeclinationAve[month] = 23.45 * Math.Sin(360.0 / 365.0 * (284.0 + ((double)daysinmonth[month]) / 2.0 + (double)dayinyear) * 3.141592 / 180.0); //degrees
                    double[,] windHisto = new double[daysinmonth[month], 24];

                    // beginning of a new month, initialise monthly values to 0
                    for (hour = 0; hour < 24; hour++)
                    {
                        dblb[hour] = 0.0;
                        rhum[hour] = 0.0;
                        press[hour] = 0.0;
                        hr[hour] = 0.0;
                        //wdir[hour] = 0.0;
                        wvel[hour] = 0.0;
                        srad[hour] = 0.0;
                        sdrad[hour] = 0.0;
                    }

                    for (day = 0; day < daysinmonth[month]; day++)
                    {
                        for (hour = 0; hour < 24; hour++)
                        {
                            data = br.ReadLine();
                            stcounter = 0;
                            string[] st2 = data.Split(delims);//changed by Marcel

                            stcounter = 6;//temperature is correct
                            DryBulbDump[month, day, hour] = double.Parse(st2[stcounter]);
                            //DryBulbDump[dayinyear, hour] = double.Parse(st2[stcounter]);      //C

                            //FADE added relative humidity and humidity ratio
                            stcounter = 8;
                            RelHumDump[month, day, hour] = double.Parse(st2[stcounter]);
                            stcounter = 9;
                            PressureDump[month, day, hour] = double.Parse(st2[stcounter]);
                            if (DryBulbDump[month, day, hour] < 0)
                            {
                                PSatDump = Math.Exp(C1 / (DryBulbDump[month, day, hour] + CTOK) + C2 + C3 * (DryBulbDump[month, day, hour] + CTOK) + C4 * Math.Pow((DryBulbDump[month, day, hour] + CTOK), 2) + C5 * Math.Pow((DryBulbDump[month, day, hour] + CTOK), 3) + C6 * Math.Pow((DryBulbDump[month, day, hour] + CTOK), 4) + C7 * Math.Log((DryBulbDump[month, day, hour] + CTOK), Math.E));
                            }
                            else
                            {
                                PSatDump = Math.Exp(C8 / (DryBulbDump[month, day, hour] + CTOK) + C9 + C10 * (DryBulbDump[month, day, hour] + CTOK) + C11 * Math.Pow((DryBulbDump[month, day, hour] + CTOK), 2) + C12 * Math.Pow((DryBulbDump[month, day, hour] + CTOK), 3) + C13 * Math.Log((DryBulbDump[month, day, hour] + CTOK), Math.E));
                            }
                            PPWDump = RelHumDump[month, day, hour] / PERCENT * PSatDump;
                            omegaDump[month, day, hour] = ASHRAECONST * PPWDump / (PressureDump[month, day, hour] - PPWDump); //FADE: omega is the humidity ratio [kgw / kgda]
                            //RelHumDump[dayinyear, hour] = double.Parse(st2[stcounter]);
                            //PressureDump[dayinyear, hour] = double.Parse(st2[stcounter]); //Pa
                            //Console.WriteLine(PressureDump[dayinyear, hour]);

                            stcounter = 13;//make sure this is +7 from temperature
                            SolarRadDump[month, day, hour] = double.Parse(st2[++stcounter]);     //Wh/m^2
                            SolarDiffRadDump[month, day, hour] = double.Parse(st2[++stcounter]); //Wh/m^2
                            //SolarRadDump[dayinyear, hour] = double.Parse(st2[++stcounter]);     //Wh/m^2
                            //SolarDiffRadDump[dayinyear, hour] = double.Parse(st2[++stcounter]); //Wh/m^2

                            stcounter += 4;
                            WindDirDump[month, day, hour] = double.Parse(st2[++stcounter]);      //Degrees
                            WindVelDump[month, day, hour] = double.Parse(st2[++stcounter]);      //m/s
                            //WindDirDump[dayinyear, hour] = double.Parse(st2[++stcounter]);      //Degrees
                            //WindVelDump[dayinyear, hour] = double.Parse(st2[++stcounter]);      //m/s

                            dblb[hour] += DryBulbDump[month, day, hour];
                            rhum[hour] += RelHumDump[month, day, hour];
                            press[hour] += PressureDump[month, day, hour];

                            if (WindVelDump[month, day, hour] > 0)
                                windHisto[day, hour] = WindDirDump[month, day, hour];
                            else
                                windHisto[day, hour] = 700; // random number > 360
                            wvel[hour] += WindVelDump[month, day, hour];
                            srad[hour] += SolarRadDump[month, day, hour];  //  Wh/m2 integrated over the last hour
                            sdrad[hour] += SolarDiffRadDump[month, day, hour];  //  Wh/m2 integrated over the last hour

                            //dblb[hour] += DryBulbDump[dayinyear, hour];
                            //rhum[hour] += RelHumDump[dayinyear, hour];
                            //press[hour] += PressureDump[dayinyear, hour];

                            //if (WindVelDump[dayinyear, hour] > 0)
                            //    windHisto[day, hour] = WindDirDump[dayinyear, hour];
                            //else
                            //    windHisto[day, hour] = 700; // random number > 360
                            //wvel[hour] += WindVelDump[dayinyear, hour];
                            //srad[hour] += SolarRadDump[dayinyear, hour];  //  Wh/m2 integrated over the last hour
                            //sdrad[hour] += SolarDiffRadDump[dayinyear, hour];  //  Wh/m2 integrated over the last hour
                        }
                        dayinyear++;
                    }

                    //get the mode of wind for each hour
                    double[] windModes = getHisto(windHisto, daysinmonth[month]);
                    for (int i = 0; i < 24; i++)
                        WindDirAve[month, i] = windModes[i];

                    // end of a month, calculate and store values
                    for (hour = 0; hour < 24; hour++)
                    {
                        DryBulbAve[month, hour] += dblb[hour] / daysinmonth[month]; //FADE: We might not need the += here. I need to check that - Checked: not needed
                        //Console.WriteLine("Month " + month + " Hour " + hour + " Temp " + DryBulbAve[month, hour]);
                        RelHumAve[month, hour] += rhum[hour] / daysinmonth[month];
                        PressureAve[month, hour] += press[hour] / daysinmonth[month];
                        //Console.WriteLine("Month " + month + " Hour " + hour + " Pressure " + PressureAve[month, hour]);
                        //FADE added calculation of humidity ratio for hybrid mode:
                        if (DryBulbAve[month, hour] < 0) //FADE: This assumes that the += is not necessary to calculate the average values - need to check
                        {
                            PSatWAve[month, hour] = Math.Exp(C1 / (DryBulbAve[month, hour] + CTOK) + C2 + C3 * (DryBulbAve[month, hour] + CTOK) + C4 * Math.Pow((DryBulbAve[month, hour] + CTOK), 2) + C5 * Math.Pow((DryBulbAve[month, hour] + CTOK), 3) + C6 * Math.Pow((DryBulbAve[month, hour] + CTOK), 4) + C7 * Math.Log((DryBulbAve[month, hour] + CTOK), Math.E));
                        }
                        else
                        {
                            PSatWAve[month, hour] = Math.Exp(C8 / (DryBulbAve[month, hour] + CTOK) + C9 + C10 * (DryBulbAve[month, hour] + CTOK) + C11 * Math.Pow((DryBulbAve[month, hour] + CTOK), 2) + C12 * Math.Pow((DryBulbAve[month, hour] + CTOK), 3) + C13 * Math.Log((DryBulbAve[month, hour] + CTOK), Math.E));
                        }
                        //Console.WriteLine("Month " + month + " Hour " + hour + " P_Sat " + PSatWAve[month, hour]);
                        PPWAve[month, hour] = RelHumAve[month, hour] / PERCENT * PSatWAve[month, hour];
                        omega[month, hour] = ASHRAECONST * PPWAve[month, hour] / (PressureAve[month, hour] - PPWAve[month, hour]); //FADE: omega is the humidity ratio [kgw / kgda]
                        //Console.WriteLine("Month " + month + " Hour " + hour + " PPW " + PPWAve[month, hour]);
                        //Console.WriteLine("Month " + month + " Hour " + hour + " omega " + omega[month, hour]);
                        WindVelAve[month, hour] += wvel[hour] / daysinmonth[month];//I suppose its ok to average wind velocity 
                        SolarRadAve[month, hour] += srad[hour] / daysinmonth[month];
                        SolarDiffRadAve[month, hour] += 0.5 * sdrad[hour] / daysinmonth[month]; // 1/2 is for half hemisphere.
                        //System.out.println(SolarDiffRadAve[month][hour]);
                        //System.out.println("///////////////////////////////////////" + SolarDiffRadAve[month][hour]); // TEST
                        //TEST: NO WIND NO SOLAR
                        //WindVelAve[month][hour] += 0*wvel[hour]/daysinmonth[month]; //test                
                        //SolarRadAve[month][hour] += 0*srad[hour]/daysinmonth[month];//**** 20070915 MAMB Reading heat loads
                        //SolarDiffRadAve[month][hour] += 0*sdrad[hour]/daysinmonth[month]; //**** 20071021 MAMB Reading diffuse heat loads
                    }
                }
                br.Close();
            }
            catch (FileNotFoundException)
            {
                MessageBox.Show("Cannot find weather file", "Weather file error", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        public double[] getHisto(double[,] data, int daysinmonth)
        {
            double[] windModes = new double[24];

            for (int hour = 0; hour < 24; hour++)
            {
                //Create an array to keep track of where the wind is at each day
                int[] occurrences = { 0, 0, 0, 0, 0, 0, 0, 0 };//N, NE...NW

                //Cycle through each day and count up the occurrences
                for (int day = 0; day < daysinmonth; day++)
                {
                    double deg = data[day, hour];
                    if (deg < 700) // random number >360 used to filter occurrences of velocity = 0
                    {
                        if (deg >= 337.5 || deg < 22.5)
                            occurrences[0]++;
                        else if (deg >= 22.5 && deg < 67.5)
                            occurrences[1]++;
                        else if (deg >= 67.5 && deg < 112.5)
                            occurrences[2]++;
                        else if (deg >= 112.5 && deg < 157.5)
                            occurrences[3]++;
                        else if (deg >= 157.5 && deg < 202.5)
                            occurrences[4]++;
                        else if (deg >= 202.5 && deg < 247.5)
                            occurrences[5]++;
                        else if (deg >= 247.5 && deg < 292.5)
                            occurrences[6]++;
                        else    //(deg >= 292.5 && deg < 337.5)
                            occurrences[7]++;
                    }
                }

                int max = 0;
                for (int i = 0; i < 7; i++)
                {
                    if (occurrences[i + 1] > occurrences[max])
                        max = i + 1;
                }

                if (max == 0)
                    windModes[hour] = 0;
                else if (max == 1)
                    windModes[hour] = 45;
                else if (max == 2)
                    windModes[hour] = 90;
                else if (max == 3)
                    windModes[hour] = 135;
                else if (max == 4)
                    windModes[hour] = 180;
                else if (max == 5)
                    windModes[hour] = 225;
                else if (max == 6)
                    windModes[hour] = 270;
                else    //max == 7
                    windModes[hour] = 315;
            }

            return windModes;
        }

    }//end class
}//end namespace
