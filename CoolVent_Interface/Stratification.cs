using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;
using ZedGraph;

namespace CoolVent
{
    public partial class Stratification : Form
    {

        //private WebTel.Drawing.Chart.ucChart ucChart2;

        public Stratification()
        {
            InitializeComponent();
        }

        public GraphPane mySPane;
        public LineItem curveS;
        public double threshHoldY = stratdata.H0;

        private void Stratification_Load(object sender, System.EventArgs e)
        {    
            mySPane = graphControlStrat.GraphPane;

            if (datapackage.transimu == 0)	//steady state
            {
                trackBarStrat.Visible = false;
                snapLabel.Visible = false;
                snapLabel10.Visible = false;
                snapLabel17.Visible = false;
                snapLabel24.Visible = false;
                label1.Visible = false;
            }
            else //transient
            {
                trackBarStrat.Visible = true;
                //if (globalcontrol.trackbartime == 24) //FADENNEW
                //    globalcontrol.trackbartime=0;
                trackBarStrat.Value = globalcontrol.trackbartime;
            }

            // Set the title and axis labels
            mySPane.Title.Text = "Zone "  + (stratdata.selZone+1).ToString() + " temperature vs. Room height";
            mySPane.XAxis.Title.Text = "Temperature (°C)";
            mySPane.YAxis.Title.Text = "Room height (m)"; //\n(C)";

            // Fill the pane background with a color gradient
            mySPane.Fill = new Fill(Color.WhiteSmoke, Color.Lavender, 0F);

            // Fill the axis background with a color gradient
            mySPane.Chart.Fill = new Fill(Color.FromArgb(255, 255, 245),
                Color.FromArgb(255, 255, 190), 90F);

            // Show the x and y axis gridlines
            mySPane.XAxis.MajorGrid.IsVisible = true;
            mySPane.YAxis.MajorGrid.IsVisible = true;

            if (datapackage.transimu == 0)
            {
                mySPane.XAxis.Scale.Max = stratdata.temperatureX[0][2] + 1;
                mySPane.XAxis.Scale.Min = stratdata.temperatureX[0][0] - 1;
            }
            else
            {
                mySPane.XAxis.Scale.Max = Math.Ceiling(FindMax(stratdata.temperatureX, (LutonVisual.daytosimulateindex - 1) * 24, (LutonVisual.daytosimulateindex - 1) * 24 + 24, 3)); //FADENEW //COMPLETELY unrelated to CoolVent's Ceiling and Floor.
                mySPane.XAxis.Scale.Min = Math.Floor(FindMin(stratdata.temperatureX, (LutonVisual.daytosimulateindex - 1) * 24, (LutonVisual.daytosimulateindex - 1) * 24 + 24, 3)); //FADENEW
            }

            mySPane.YAxis.Scale.Min = 0;
            mySPane.YAxis.Scale.Max = datapackage.floortoceilingheight;

            mySPane.YAxis.Scale.MinAuto = false;
            mySPane.YAxis.Scale.MaxAuto = false;
            mySPane.XAxis.Scale.MinAuto = false;
            mySPane.XAxis.Scale.MaxAuto = false;

             //Add occupant height line
            LineObj threshHoldLine = new LineObj(Color.Red, mySPane.XAxis.Scale.Min, threshHoldY, mySPane.XAxis.Scale.Max, threshHoldY);
            mySPane.GraphObjList.Add(threshHoldLine);
            this.occHeight.Text = (globalcontrol.occheight).ToString("0.0");
            refreshPlot();
        }

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            globalcontrol.trackbartime = trackBarStrat.Value;
            mySPane.CurveList.Clear();
            //graphControlStrat.Refresh();
            refreshPlot();
        }
        private void refreshPlot()
        {
            //FADENEW*****************************************
            int snapposition;
            snapposition = (LutonVisual.daytosimulateindex - 1) * 24 + trackBarStrat.Value; //FADENEW: With new formatting

            //if (trackBarStrat.Value == 0)
            //{
            //    snapposition = (LutonVisual.monthtosimulate * 24) + 23;
            //}
            //else
            //{
            //    snapposition = (LutonVisual.monthtosimulate * 24) + trackBarStrat.Value - 1;
            //}
            //FADENEW*****************************************

            //enter some arbitrary data points
            double[] xS = stratdata.temperatureX[snapposition]; //0, 1, and 2
            double[] yS = stratdata.heightY[snapposition]; // FADENEW
            //double[] yS = stratdata.heightY[trackBarStrat.Value];//0, 1, and 2 //FADENEW

            // Add a green curve

            curveS = mySPane.AddCurve("Time: " + globalcontrol.trackbartime.ToString() + ":00", xS, yS, Color.Green, SymbolType.Circle);
            curveS.Line.Width = 3.0F;
            // Fill the symbols with white to make them opaque
            curveS.Symbol.Fill = new Fill(Color.White);
            curveS.Symbol.Size = 10;

            if (datapackage.transimu == 0)
                mySPane.Legend.IsVisible = false; //hides the legend from steady state simulations


            graphControlStrat.AxisChange();
            //graphControlStrat.RestoreScale(mySPane);
            //graphControlStrat.ZoomOut(mySPane);
            graphControlStrat.Refresh();
        }

        private static double FindMax(double[][] ia, int limit1, int limit2, int limit3) //FADENEW: limit1 is the first hour in day, limit2 is the last hour in day and limit3 runs along floor, hinge and ceiling temperatures (=3)
        {
            double iMax = ia[limit1][0];
            for (int i = limit1; i < limit2; i++)
                for (int j = 0; j < limit3; j++)
                {
                    if (ia[i][j] > iMax)
                        iMax = ia[i][j];
                }
            return iMax;
        }

        private static double FindMin(double[][] ia, int limit1, int limit2, int limit3) //FADENEW: limit1 is the first hour in day, limit2 is the last hour in day and limit3 runs along floor, hinge and ceiling temperatures (=3)
        {
            double iMin = ia[limit1][0];
            for (int i = limit1; i < limit2; i++)
                for (int j = 0; j < limit3; j++)
                {
                    if (ia[i][j] < iMin)
                        iMin = ia[i][j];
                }
            return iMin;
        }

        //private static double FindMax(double[][] ia, int limit1, int limit2)
        //{
        //    double iMax = ia[0][0];
        //    for (int i = 0; i < limit1; i++)
        //        for (int j = 0; j < limit2; j++)
        //        {
        //            if (ia[i][j] > iMax)
        //                iMax = ia[i][j];
        //        }
        //    return iMax;
        //}

        //private static double FindMin(double[][] ia, int limit1, int limit2)
        //{
        //    double iMin = ia[0][0];
        //    for (int i = 0; i < limit1; i++)
        //        for (int j = 0; j < limit2; j++)
        //        {
        //            if (ia[i][j] < iMin)
        //                iMin = ia[i][j];
        //        }
        //    return iMin;
        //}

        private void occHeight_TextChanged(object sender, EventArgs e)
        {
            try
            {
                globalcontrol.occheight = Double.Parse(occHeight.Text);
                if (globalcontrol.occheight < 0)
                {
                    System.ApplicationException ex = new System.ApplicationException("Height of occupied zone must be positive!");
                    throw ex;
                }
                occHeight.BackColor = Color.White;
            }
            catch (Exception)
            {
                occHeight.BackColor = Color.PaleVioletRed;
            }
            mySPane.CurveList.Clear();
            mySPane.GraphObjList.Clear();
            threshHoldY = globalcontrol.occheight;
            LineObj threshHoldLine = new LineObj(Color.Red, mySPane.XAxis.Scale.Min, threshHoldY, mySPane.XAxis.Scale.Max, threshHoldY);
            mySPane.GraphObjList.Add(threshHoldLine);
            refreshPlot();
        }
    }
}