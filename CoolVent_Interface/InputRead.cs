using System;
using System.Collections.Generic;
//using System.Linq;
using System.Text;
using System.IO;
namespace CoolVent
{
    class InputRead
    {
        public InputRead() { }

        public static WindDataReader generateOutdoortxt(int monthini, int monthend, String cityfilename) //FADE: Modified this to allow for multiple month simulations
        {
            /*FADE: Generate an "Output.txt" file. Output.txt file contains:
             * 1) Dry bulb temperature
             * 2) Wind velocity
             * 3) Wind direction
             * 4) Solar direct radiation
             * 5) Solar diffuse radiation
             * 6) Delta temperature
             * 7) Delta wind velocity
             * 8) Delta wind direction
             * 9) Delta solar direct radiation
             * 10) Delta solar diffuse radiation
             * 11) Humidity ratio
             * 12) Delta humidity ratio
             * 13) Declination
             */

            double deltaTemp = 0;
            double deltaWindDir = 0;
            double deltaWindVel = 0;
            double deltaSolarDir = 0;
            double deltaSolarDiff = 0;
            double deltaomega = 0;
            int noWind = 1; //FADENEW: this is used to set the wind speed to zero
            int noSun = 1; //FADENEW: this is used to set the sun heat gains to zero

            if (globalcontrol.ignoreWindCB) //FADENEW:
            {
                noWind = 0;
            }
            if (globalcontrol.ignoreSunCB) //FADENEW:
            {
                noSun = 0;
            }

            string[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };  //FADENEW: added this
            int[] daysinmonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };  //FADENEW: added this

            WindDataReader w = new WindDataReader();
            w.WindRead(cityfilename);

            String outdoorfilename = "Outdoor.txt";
            StreamWriter bw = new StreamWriter(outdoorfilename);

            bw.WriteLine(w.city + " " + w.state + " " + w.country);

            string simtype = months[monthini]; //FADENEW:

            if (datapackage.dailySim == 1) //FADENEW:
            {
                simtype +=" to " + months[monthend] + " - daily simulation";
            }
            bw.WriteLine(simtype);
            //bw.WriteLine(w.DeclinationAve[month]);
            bw.WriteLine(w.latitude);
            bw.WriteLine(w.elevation);
            bw.WriteLine("Temp\tWindVel\tWindDir\tSDirR\tSDiffR\tDTemp\tDWVel\tDWDir\tDSDirR\tDSDiffR\tomega\tDomega\tDeclination");

            //print out a bunch of info into Outdoor.txt
            //FADENEW:***********************************************************************************************************************
            for (int month = monthini; month <= monthend; month++)
            {
                if (globalcontrol.dailyCB) //FADE: Daily simulation. Print every day to Outdoor.txt
                {
                    for (int day = 0; day < daysinmonth[month]; day++)
                    {
                        for (int hour = 0; hour < 24; hour++)
                        {
                            if (month == monthini && day == 0 && hour == 0) //First hour of the first month, take the last hour of previous day. This adds an extra line in Outdoor.txt
                            {
                                if (month == 0) //No month left to go back, so go to the previous year
                                {
                                    deltaTemp = w.DryBulbDump[month, day, hour] - w.DryBulbDump[11, 30, 23];
                                    deltaWindDir = w.WindDirDump[month, day, hour] - w.WindDirDump[11, 30, 23];
                                    deltaWindVel = w.WindVelDump[month, day, hour] - w.WindVelDump[11, 30, 23];
                                    deltaSolarDir = w.SolarRadDump[month, day, hour] - w.SolarRadDump[11, 30, 23];
                                    deltaSolarDiff = w.SolarDiffRadDump[month, day, hour] - w.SolarDiffRadDump[11, 30, 23];
                                    deltaomega = Math.Round(w.omegaDump[month, day, hour], 4) - Math.Round(w.omegaDump[11, 30, 23], 4); //FADE added this

                                    bw.Write(Math.Round(w.DryBulbDump[11, 30, 23], 2) + "\t" +
                                        Math.Round(noWind * w.WindVelDump[11, 30, 23], 2) + "\t" +
                                        Math.Round(w.WindDirDump[11, 30, 23], 2) + "\t" +
                                        Math.Round(noSun * w.SolarRadDump[11, 30, 23], 2) + "\t" +
                                        Math.Round(noSun * w.SolarDiffRadDump[11, 30, 23], 2) + "\t" +
                                        Math.Round(deltaTemp, 2) + "\t" +
                                        Math.Round(noWind * deltaWindVel, 2) + "\t" +
                                        Math.Round(deltaWindDir, 2) + "\t" +
                                        Math.Round(noSun * deltaSolarDir, 2) + "\t" +
                                        Math.Round(noSun * deltaSolarDiff, 2) + "\t" +
                                        Math.Round(w.omegaDump[11, 30, 23], 4) + "\t" +
                                        Math.Round(deltaomega, 4) + "\t" +
                                        Math.Round(w.DeclinationAve[11], 2));
                                    bw.WriteLine();
                                }
                                else
                                {
                                    deltaTemp = w.DryBulbDump[month, day, hour] - w.DryBulbDump[month - 1, daysinmonth[month - 1] - 1, 23];
                                    deltaWindDir = w.WindDirDump[month, day, hour] - w.WindDirDump[month - 1, daysinmonth[month - 1] - 1, 23];
                                    deltaWindVel = w.WindVelDump[month, day, hour] - w.WindVelDump[month - 1, daysinmonth[month - 1] - 1, 23];
                                    deltaSolarDir = w.SolarRadDump[month, day, hour] - w.SolarRadDump[month - 1, daysinmonth[month - 1] - 1, 23];
                                    deltaSolarDiff = w.SolarDiffRadDump[month, day, hour] - w.SolarDiffRadDump[month - 1, daysinmonth[month - 1] - 1, 23];
                                    deltaomega = Math.Round(w.omegaDump[month, day, hour], 4) - Math.Round(w.omegaDump[month - 1, daysinmonth[month - 1] - 1, 23], 4); //FADE added this

                                    bw.Write(Math.Round(w.DryBulbDump[month - 1, daysinmonth[month - 1] - 1, 23], 2) + "\t" +
                                        Math.Round(noWind * w.WindVelDump[month - 1, daysinmonth[month - 1] - 1, 23], 2) + "\t" +
                                        Math.Round(w.WindDirDump[month - 1, daysinmonth[month - 1] - 1, 23], 2) + "\t" +
                                        Math.Round(noSun * w.SolarRadDump[month - 1, daysinmonth[month - 1] - 1, 23], 2) + "\t" +
                                        Math.Round(noSun * w.SolarDiffRadDump[month - 1, daysinmonth[month - 1] - 1, 23], 2) + "\t" +
                                        Math.Round(deltaTemp, 2) + "\t" +
                                        Math.Round(noWind * deltaWindVel, 2) + "\t" +
                                        Math.Round(deltaWindDir, 2) + "\t" +
                                        Math.Round(noSun * deltaSolarDir, 2) + "\t" +
                                        Math.Round(noSun * deltaSolarDiff, 2) + "\t" +
                                        Math.Round(w.omegaDump[month - 1, daysinmonth[month - 1] - 1, 23], 4) + "\t" +
                                        Math.Round(deltaomega, 4) + "\t" +
                                        Math.Round(w.DeclinationAve[month - 1], 2));
                                    bw.WriteLine();
                                }
                            }
                            if (hour == 23) //Go to the next day
                            {
                                if (day + 1 == daysinmonth[month]) //Go to next month
                                {
                                    if (month == 11) //No month left in the year go to next year (i.e. go back to January)
                                    {
                                        deltaTemp = w.DryBulbDump[0, 0, 0] - w.DryBulbDump[month, day, hour];
                                        deltaWindDir = w.WindDirDump[0, 0, 0] - w.WindDirDump[month, day, hour];
                                        deltaWindVel = w.WindVelDump[0, 0, 0] - w.WindVelDump[month, day, hour];
                                        deltaSolarDir = w.SolarRadDump[0, 0, 0] - w.SolarRadDump[month, day, hour];
                                        deltaSolarDiff = w.SolarDiffRadDump[0, 0, 0] - w.SolarDiffRadDump[month, day, hour];
                                        deltaomega = Math.Round(w.omegaDump[0, 0, 0], 4) - Math.Round(w.omegaDump[month, day, hour], 4); //FADE added this
                                    }
                                    else //same year
                                    {
                                        deltaTemp = w.DryBulbDump[month + 1, 0, 0] - w.DryBulbDump[month, day, hour];
                                        deltaWindDir = w.WindDirDump[month + 1, 0, 0] - w.WindDirDump[month, day, hour];
                                        deltaWindVel = w.WindVelDump[month + 1, 0, 0] - w.WindVelDump[month, day, hour];
                                        deltaSolarDir = w.SolarRadDump[month + 1, 0, 0] - w.SolarRadDump[month, day, hour];
                                        deltaSolarDiff = w.SolarDiffRadDump[month + 1, 0, 0] - w.SolarDiffRadDump[month, day, hour];
                                        deltaomega = Math.Round(w.omegaDump[month + 1, 0, 0], 4) - Math.Round(w.omegaDump[month, day, hour], 4); //FADE added this
                                    }
                                }
                                else //Same month
                                {
                                    deltaTemp = w.DryBulbDump[month, day + 1, 0] - w.DryBulbDump[month, day, hour];
                                    deltaWindDir = w.WindDirDump[month, day + 1, 0] - w.WindDirDump[month, day, hour];
                                    deltaWindVel = w.WindVelDump[month, day + 1, 0] - w.WindVelDump[month, day, hour];
                                    deltaSolarDir = w.SolarRadDump[month, day + 1, 0] - w.SolarRadDump[month, day, hour];
                                    deltaSolarDiff = w.SolarDiffRadDump[month, day + 1, 0] - w.SolarDiffRadDump[month, day, hour];
                                    deltaomega = Math.Round(w.omegaDump[month, day + 1, 0], 4) - Math.Round(w.omegaDump[month, day, hour], 4); //FADE added this
                                }
                            }
                            else //Same day
                            {
                                deltaTemp = w.DryBulbDump[month, day, hour + 1] - w.DryBulbDump[month, day, hour];
                                deltaWindDir = w.WindDirDump[month, day, hour + 1] - w.WindDirDump[month, day, hour];
                                deltaWindVel = w.WindVelDump[month, day, hour + 1] - w.WindVelDump[month, day, hour];
                                deltaSolarDir = w.SolarRadDump[month, day, hour + 1] - w.SolarRadDump[month, day, hour];
                                deltaSolarDiff = w.SolarDiffRadDump[month, day, hour + 1] - w.SolarDiffRadDump[month, day, hour];
                                deltaomega = Math.Round(w.omegaDump[month, day, hour + 1], 4) - Math.Round(w.omegaDump[month, day, hour], 4); //FADE added this
                            }
                            bw.Write(Math.Round(w.DryBulbDump[month, day, hour], 2) + "\t" +
                                Math.Round(noWind * w.WindVelDump[month, day, hour], 2) + "\t" +
                                Math.Round(w.WindDirDump[month, day, hour], 2) + "\t" +
                                Math.Round(noSun * w.SolarRadDump[month, day, hour], 2) + "\t" +
                                Math.Round(noSun * w.SolarDiffRadDump[month, day, hour], 2) + "\t" +
                                Math.Round(deltaTemp, 2) + "\t" +
                                Math.Round(noWind * deltaWindVel, 2) + "\t" +
                                Math.Round(deltaWindDir, 2) + "\t" +
                                Math.Round(noSun * deltaSolarDir, 2) + "\t" +
                                Math.Round(noSun * deltaSolarDiff, 2) + "\t" +
                                Math.Round(w.omegaDump[month, day, hour], 4) + "\t" +
                                Math.Round(deltaomega, 4) + "\t" +
                                Math.Round(w.DeclinationAve[month], 2));
                            bw.WriteLine();
                        }
                    }
                }
                else //FADENEW: Simulate an "average" day in a single month
                {
                    for (int j = 0; j < 24; j++)
                    {
                        #region FADENEW: Multimonth no longer available
                        //if (month == monthini && j == 0) //First hour of the first month, take the last hour of previous month. This adds an extra line in Outdoor.txt
                        //{
                        //    if (month == 0) //No month left to go back, so go to the previous year
                        //    {
                        //        deltaTemp = w.DryBulbAve[month, j] - w.DryBulbAve[11, 23];
                        //        deltaWindDir = w.WindDirAve[month, j] - w.WindDirAve[11, 23];
                        //        deltaWindVel = w.WindVelAve[month, j] - w.WindVelAve[11, 23];
                        //        deltaSolarDir = w.SolarRadAve[month, j] - w.SolarRadAve[11, 23];
                        //        deltaSolarDiff = w.SolarDiffRadAve[month, j] - w.SolarDiffRadAve[11, 23];
                        //        deltaomega = Math.Round(w.omega[month, j], 4) - Math.Round(w.omega[11, 23], 4); //FADE added this

                        //        bw.Write(Math.Round(w.DryBulbAve[11, 23], 2) + "\t" +
                        //            Math.Round(noWind * w.WindVelAve[11, 23], 2) + "\t" +
                        //            Math.Round(w.WindDirAve[11, 23], 2) + "\t" +
                        //            Math.Round(noSun * w.SolarRadAve[11, 23], 2) + "\t" +
                        //            Math.Round(noSun * w.SolarDiffRadAve[11, 23], 2) + "\t" +
                        //            Math.Round(deltaTemp, 2) + "\t" +
                        //            Math.Round(noWind * deltaWindVel, 2) + "\t" +
                        //            Math.Round(deltaWindDir, 2) + "\t" +
                        //            Math.Round(noSun * deltaSolarDir, 2) + "\t" +
                        //            Math.Round(noSun * deltaSolarDiff, 2) + "\t" +
                        //            Math.Round(w.omega[11, 23], 4) + "\t" +
                        //            Math.Round(deltaomega, 4) + "\t" +
                        //            Math.Round(w.DeclinationAve[11], 2));
                        //        bw.WriteLine();
                        //    }
                        //    else
                        //    {
                        //        deltaTemp = w.DryBulbAve[month, j] - w.DryBulbAve[month - 1, 23];
                        //        deltaWindDir = w.WindDirAve[month, j] - w.WindDirAve[month - 1, 23];
                        //        deltaWindVel = w.WindVelAve[month, j] - w.WindVelAve[month - 1, 23];
                        //        deltaSolarDir = w.SolarRadAve[month, j] - w.SolarRadAve[month - 1, 23];
                        //        deltaSolarDiff = w.SolarDiffRadAve[month, j] - w.SolarDiffRadAve[month - 1, 23];
                        //        deltaomega = Math.Round(w.omega[month, j], 4) - Math.Round(w.omega[month - 1, 23], 4); //FADE added this

                        //        bw.Write(Math.Round(w.DryBulbAve[month - 1, 23], 2) + "\t" +
                        //            Math.Round(noWind * w.WindVelAve[month - 1, 23], 2) + "\t" +
                        //            Math.Round(w.WindDirAve[month - 1, 23], 2) + "\t" +
                        //            Math.Round(noSun * w.SolarRadAve[month - 1, 23], 2) + "\t" +
                        //            Math.Round(noSun * w.SolarDiffRadAve[month - 1, 23], 2) + "\t" +
                        //            Math.Round(deltaTemp, 2) + "\t" +
                        //            Math.Round(noWind * deltaWindVel, 2) + "\t" +
                        //            Math.Round(deltaWindDir, 2) + "\t" +
                        //            Math.Round(noSun * deltaSolarDir, 2) + "\t" +
                        //            Math.Round(noSun * deltaSolarDiff, 2) + "\t" +
                        //            Math.Round(w.omega[month - 1, 23], 4) + "\t" +
                        //            Math.Round(deltaomega, 4) + "\t" +
                        //            Math.Round(w.DeclinationAve[month - 1], 2));
                        //        bw.WriteLine();
                        //    }
                        //}
                        //if (datapackage.multimonths == 1) //FADENEW: This is a multimonth simulation, so deltas can be calculated with next month information
                        //{ 
                        #endregion
                        if (j == 0 || j == 23)
                        {
                            deltaTemp = w.DryBulbAve[month, 0] - w.DryBulbAve[month, 23];
                            deltaWindDir = w.WindDirAve[month, 0] - w.WindDirAve[month, 23];
                            deltaWindVel = w.WindVelAve[month, 0] - w.WindVelAve[month, 23];
                            deltaSolarDir = w.SolarRadAve[month, 0] - w.SolarRadAve[month, 23];
                            deltaSolarDiff = w.SolarDiffRadAve[month, 0] - w.SolarDiffRadAve[month, 23];
                            deltaomega = Math.Round(w.omega[month, 0], 4) - Math.Round(w.omega[month, 23], 4); //FADE added this
                            if (j == 0) //FADENEW: This is the first hour, add midnight here. I want to begin days at 12:00 am, not at 1:00 am
                            {
                                bw.Write(Math.Round(w.DryBulbAve[month, 23], 2) + "\t" +
                                    Math.Round(noWind * w.WindVelAve[month, 23], 2) + "\t" + //FADENEW
                                    Math.Round(w.WindDirAve[month, 23], 2) + "\t" +
                                    Math.Round(noSun * w.SolarRadAve[month, 23], 2) + "\t" + //FADENEW
                                    Math.Round(noSun * w.SolarDiffRadAve[month, 23], 2) + "\t" + //FADENEW
                                    Math.Round(deltaTemp, 2) + "\t" +
                                    Math.Round(noWind * deltaWindVel, 2) + "\t" +
                                    Math.Round(deltaWindDir, 2) + "\t" +
                                    Math.Round(noSun * deltaSolarDir, 2) + "\t" + //FADENEW
                                    Math.Round(noSun * deltaSolarDiff, 2) + "\t" + //FADENEW
                                    Math.Round(w.omega[month, 23], 4) + "\t" +
                                    Math.Round(deltaomega, 4) + "\t" +
                                    Math.Round(w.DeclinationAve[month], 2));
                                bw.WriteLine();
                                deltaTemp = w.DryBulbAve[month, j + 1] - w.DryBulbAve[month, j];
                                deltaWindDir = w.WindDirAve[month, j + 1] - w.WindDirAve[month, j];
                                deltaWindVel = w.WindVelAve[month, j + 1] - w.WindVelAve[month, j];
                                deltaSolarDir = w.SolarRadAve[month, j + 1] - w.SolarRadAve[month, j];
                                deltaSolarDiff = w.SolarDiffRadAve[month, j + 1] - w.SolarDiffRadAve[month, j];
                                deltaomega = Math.Round(w.omega[month, j + 1], 4) - Math.Round(w.omega[month, j], 4); //FADE added this
                            }
                        }
                        else
                        {
                            deltaTemp = w.DryBulbAve[month, j + 1] - w.DryBulbAve[month, j];
                            deltaWindDir = w.WindDirAve[month, j + 1] - w.WindDirAve[month, j];
                            deltaWindVel = w.WindVelAve[month, j + 1] - w.WindVelAve[month, j];
                            deltaSolarDir = w.SolarRadAve[month, j + 1] - w.SolarRadAve[month, j];
                            deltaSolarDiff = w.SolarDiffRadAve[month, j + 1] - w.SolarDiffRadAve[month, j];
                            deltaomega = Math.Round(w.omega[month, j + 1], 4) - Math.Round(w.omega[month, j], 4); //FADE added this
                        }
                        //FADENEW:***********************************************************************************************************************

                        //System.out.println(deltaTemp + "   " + deltaWindDir + "   " + deltaWindVel + "   " + deltaSolarDir + "   " + deltaSolarDiff);
                        //bw.write(w.DryBulbAve[month,j]+" "+ w.WindDirAve[month,j]+" " + w.WindVelAve[month,j]);
                        //bw.write(w.DryBulbAve[month,j]+" "+ w.WindDirAve[month,j]+" " + w.WindVelAve[month,j] + " "+ w.SolarRadAve[month,j]); *****20070915 MAMB added heat loads
                        //bw.write(w.DryBulbAve[month,j]+" "+ w.WindDirAve[month,j]+" " + w.WindVelAve[month,j] + " "+ w.SolarRadAve[month,j]+" "+w.SolarDiffRadAve[month,j]+" "+w.DeclinationAve[month]+" "+w.getLatitude()); ///180.0*3.141592); // //*****20070924 MAMB added angles

                        bw.Write(Math.Round(w.DryBulbAve[month, j], 2) + "\t" +
                            Math.Round(noWind * w.WindVelAve[month, j], 2) + "\t" + //FADENEW
                            Math.Round(w.WindDirAve[month, j], 2) + "\t" +
                            Math.Round(noSun * w.SolarRadAve[month, j], 2) + "\t" + //FADENEW
                            Math.Round(noSun * w.SolarDiffRadAve[month, j], 2) + "\t" + //FADENEW
                            Math.Round(deltaTemp, 2) + "\t" +
                            Math.Round(noWind * deltaWindVel, 2) + "\t" +
                            Math.Round(deltaWindDir, 2) + "\t" +
                            Math.Round(noSun * deltaSolarDir, 2) + "\t" + //FADENEW
                            Math.Round(noSun * deltaSolarDiff, 2) + "\t" + //FADENEW
                            Math.Round(w.omega[month, j], 4) + "\t" +
                            Math.Round(deltaomega, 4) + "\t" +
                            Math.Round(w.DeclinationAve[month], 2));
                        bw.WriteLine();
                    }
                }
            }
            bw.Close();
            return w;
        }//end method
        #region FADE: Old code
        //public static WindDataReader generateOutdoortxt(int month, String cityfilename)
        //{
        //    double deltaTemp = 0;
        //    double deltaWindDir = 0;
        //    double deltaWindVel = 0;
        //    double deltaSolarDir = 0;
        //    double deltaSolarDiff = 0;
        //    double deltaomega = 0;

        //    string[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

        //    WindDataReader w = new WindDataReader();

        //    w.WindRead(cityfilename);

        //    String outdoorfilename = "Outdoor.txt";
        //    StreamWriter bw = new StreamWriter(outdoorfilename);

        //    //System.out.println(" w.DryBulbAve[month,0]   " +  w.DryBulbAve[month,0] );

        //    //Use first few lines for variables that do not differ from month to month
        //    bw.WriteLine(w.city + " " + w.state + " " + w.country);
        //    bw.WriteLine(months[month]);
        //    //bw.WriteLine(w.DeclinationAve[month]);
        //    bw.WriteLine(w.latitude);
        //    bw.WriteLine(w.elevation);
        //    bw.WriteLine("Temp\tWindVel\tWindDir\tSDirR\tSDiffR\tDTemp\tDWVel\tDWDir\tDSDirR\tDSDiffR\tomega\tDomega\tDeclination");

        //    //print out a bunch of info into Outdoor.txt
        //    for (int j = 0; j < 24; j++)
        //    {
        //        if (j == 23)
        //        {
        //            deltaTemp = w.DryBulbAve[month, 0] - w.DryBulbAve[month, j];
        //            deltaWindDir = w.WindDirAve[month, 0] - w.WindDirAve[month, j];
        //            deltaWindVel = w.WindVelAve[month, 0] - w.WindVelAve[month, j];
        //            deltaSolarDir = w.SolarRadAve[month, 0] - w.SolarRadAve[month, j];
        //            deltaSolarDiff = w.SolarDiffRadAve[month, 0] - w.SolarDiffRadAve[month, j];
        //            deltaomega = Math.Round(w.omega[month, 0], 4) - Math.Round(w.omega[month, j], 4); //FADE added this
        //        }
        //        else
        //        {
        //            //System.out.println(j);
        //            deltaTemp = w.DryBulbAve[month, j + 1] - w.DryBulbAve[month, j];
        //            deltaWindDir = w.WindDirAve[month, j + 1] - w.WindDirAve[month, j];
        //            deltaWindVel = w.WindVelAve[month, j + 1] - w.WindVelAve[month, j];
        //            deltaSolarDir = w.SolarRadAve[month, j + 1] - w.SolarRadAve[month, j];
        //            deltaSolarDiff = w.SolarDiffRadAve[month, j + 1] - w.SolarDiffRadAve[month, j];
        //            deltaomega = Math.Round(w.omega[month, j + 1], 4) - Math.Round(w.omega[month, j], 4);
        //        }
        //        //System.out.println(deltaTemp + "   " + deltaWindDir + "   " + deltaWindVel + "   " + deltaSolarDir + "   " + deltaSolarDiff);
        //        //bw.write(w.DryBulbAve[month,j]+" "+ w.WindDirAve[month,j]+" " + w.WindVelAve[month,j]);
        //        //bw.write(w.DryBulbAve[month,j]+" "+ w.WindDirAve[month,j]+" " + w.WindVelAve[month,j] + " "+ w.SolarRadAve[month,j]); *****20070915 MAMB added heat loads
        //        //bw.write(w.DryBulbAve[month,j]+" "+ w.WindDirAve[month,j]+" " + w.WindVelAve[month,j] + " "+ w.SolarRadAve[month,j]+" "+w.SolarDiffRadAve[month,j]+" "+w.DeclinationAve[month]+" "+w.getLatitude()); ///180.0*3.141592); // //*****20070924 MAMB added angles

        //        bw.Write(Math.Round(w.DryBulbAve[month, j], 2) + "\t" +
        //                Math.Round(w.WindVelAve[month, j], 2) + "\t" +
        //                Math.Round(w.WindDirAve[month, j], 2) + "\t" +
        //                Math.Round(w.SolarRadAve[month, j], 2) + "\t" +
        //                Math.Round(w.SolarDiffRadAve[month, j], 2) + "\t" +
        //                Math.Round(deltaTemp, 2) + "\t" +
        //                Math.Round(deltaWindVel, 2) + "\t" +
        //                Math.Round(deltaWindDir, 2) + "\t" +
        //                Math.Round(deltaSolarDir, 2) + "\t" +
        //                Math.Round(deltaSolarDiff, 2) + "\t" +
        //                Math.Round(w.omega[month, j], 4) + "\t" +
        //                Math.Round(deltaomega, 4) + "\t" +
        //                Math.Round(w.DeclinationAve[month], 2));
        //        bw.WriteLine();
        //    }
        //    bw.Close();
        //    return w;
        //}//end method 
        #endregion
    }//end class
}//end namespace
