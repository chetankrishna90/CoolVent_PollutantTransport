using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using ZedGraph;

namespace CoolVent
{
	/// <summary>
	/// Summary description for PlotT.
	/// </summary>
	public class PlotT : System.Windows.Forms.Form
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;
        private System.Windows.Forms.Label occHeightUnits;
        private System.Windows.Forms.Label occHeightLabel;
        private TextBox occHeight;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label snapLabel;
        private graphControl graphControlStrat;
        private GroupBox groupBox1;
        private GroupBox stratPlotGB;
        private System.Windows.Forms.Label snapLabel24;
        private System.Windows.Forms.Label snapLabel17;
        private System.Windows.Forms.Label snapLabel10;
        private System.Windows.Forms.Label label1;
        private TrackBar trackBarStrat;
        private CoolVent.graphControl graphControl1;

		//private WebTel.Drawing.Chart.ucChart ucChart1;

		public PlotT()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();

			//
			// TODO: Add any constructor code after InitializeComponent call
			//
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(PlotT));
            this.occHeightUnits = new System.Windows.Forms.Label();
            this.occHeightLabel = new System.Windows.Forms.Label();
            this.occHeight = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.snapLabel = new System.Windows.Forms.Label();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.stratPlotGB = new System.Windows.Forms.GroupBox();
            this.snapLabel24 = new System.Windows.Forms.Label();
            this.snapLabel17 = new System.Windows.Forms.Label();
            this.snapLabel10 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.trackBarStrat = new System.Windows.Forms.TrackBar();
            this.graphControlStrat = new CoolVent.graphControl();
            this.graphControl1 = new CoolVent.graphControl();
            this.groupBox1.SuspendLayout();
            this.stratPlotGB.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.trackBarStrat)).BeginInit();
            this.SuspendLayout();
            // 
            // occHeightUnits
            // 
            this.occHeightUnits.Location = new System.Drawing.Point(434, 506);
            this.occHeightUnits.Name = "occHeightUnits";
            this.occHeightUnits.Size = new System.Drawing.Size(31, 21);
            this.occHeightUnits.TabIndex = 133;
            this.occHeightUnits.Text = "m";
            // 
            // occHeightLabel
            // 
            this.occHeightLabel.Location = new System.Drawing.Point(279, 506);
            this.occHeightLabel.Name = "occHeightLabel";
            this.occHeightLabel.Size = new System.Drawing.Size(113, 17);
            this.occHeightLabel.TabIndex = 132;
            this.occHeightLabel.Text = "Ocupant head height:";
            // 
            // occHeight
            // 
            this.occHeight.Location = new System.Drawing.Point(395, 503);
            this.occHeight.Name = "occHeight";
            this.occHeight.Size = new System.Drawing.Size(33, 20);
            this.occHeight.TabIndex = 131;
            this.occHeight.Text = "1.5";
            this.occHeight.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.occHeight.TextChanged += new System.EventHandler(this.occHeight_TextChanged);
            // 
            // label2
            // 
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.label2.Location = new System.Drawing.Point(35, 394);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(303, 20);
            this.label2.TabIndex = 130;
            this.label2.Text = "Red line indicates the location of the occupied zone height.";
            // 
            // snapLabel
            // 
            this.snapLabel.Location = new System.Drawing.Point(69, 477);
            this.snapLabel.Name = "snapLabel";
            this.snapLabel.Size = new System.Drawing.Size(200, 20);
            this.snapLabel.TabIndex = 127;
            this.snapLabel.Text = "**Move trackbar to view specific hour**";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.graphControl1);
            this.groupBox1.Location = new System.Drawing.Point(12, 13);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(616, 540);
            this.groupBox1.TabIndex = 134;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Temperature-time plot";
            // 
            // stratPlotGB
            // 
            this.stratPlotGB.Controls.Add(this.snapLabel24);
            this.stratPlotGB.Controls.Add(this.snapLabel17);
            this.stratPlotGB.Controls.Add(this.snapLabel10);
            this.stratPlotGB.Controls.Add(this.label1);
            this.stratPlotGB.Controls.Add(this.graphControlStrat);
            this.stratPlotGB.Controls.Add(this.trackBarStrat);
            this.stratPlotGB.Controls.Add(this.occHeightUnits);
            this.stratPlotGB.Controls.Add(this.occHeightLabel);
            this.stratPlotGB.Controls.Add(this.occHeight);
            this.stratPlotGB.Controls.Add(this.label2);
            this.stratPlotGB.Controls.Add(this.snapLabel);
            this.stratPlotGB.Enabled = false;
            this.stratPlotGB.Location = new System.Drawing.Point(654, 13);
            this.stratPlotGB.Name = "stratPlotGB";
            this.stratPlotGB.Size = new System.Drawing.Size(636, 540);
            this.stratPlotGB.TabIndex = 135;
            this.stratPlotGB.TabStop = false;
            this.stratPlotGB.Text = "Temperature stratification in zone";
            this.stratPlotGB.Visible = false;
            // 
            // snapLabel24
            // 
            this.snapLabel24.Location = new System.Drawing.Point(574, 456);
            this.snapLabel24.Name = "snapLabel24";
            this.snapLabel24.Size = new System.Drawing.Size(40, 20);
            this.snapLabel24.TabIndex = 137;
            this.snapLabel24.Text = "24:00";
            // 
            // snapLabel17
            // 
            this.snapLabel17.Location = new System.Drawing.Point(440, 457);
            this.snapLabel17.Name = "snapLabel17";
            this.snapLabel17.Size = new System.Drawing.Size(40, 20);
            this.snapLabel17.TabIndex = 136;
            this.snapLabel17.Text = "18:00";
            // 
            // snapLabel10
            // 
            this.snapLabel10.Location = new System.Drawing.Point(239, 456);
            this.snapLabel10.Name = "snapLabel10";
            this.snapLabel10.Size = new System.Drawing.Size(40, 20);
            this.snapLabel10.TabIndex = 135;
            this.snapLabel10.Text = "9:00";
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(35, 456);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(40, 20);
            this.label1.TabIndex = 134;
            this.label1.Text = "0:00";
            // 
            // trackBarStrat
            // 
            this.trackBarStrat.LargeChange = 1;
            this.trackBarStrat.Location = new System.Drawing.Point(35, 418);
            this.trackBarStrat.Maximum = 24;
            this.trackBarStrat.Name = "trackBarStrat";
            this.trackBarStrat.Size = new System.Drawing.Size(567, 45);
            this.trackBarStrat.TabIndex = 124;
            this.trackBarStrat.TickStyle = System.Windows.Forms.TickStyle.Both;
            this.trackBarStrat.Scroll += new System.EventHandler(this.trackBar1_Scroll);
            // 
            // graphControlStrat
            // 
            this.graphControlStrat.Location = new System.Drawing.Point(35, 36);
            this.graphControlStrat.Name = "graphControlStrat";
            this.graphControlStrat.ScrollMaxX = 0D;
            this.graphControlStrat.ScrollMaxY = 0D;
            this.graphControlStrat.ScrollMaxY2 = 0D;
            this.graphControlStrat.ScrollMinX = 0D;
            this.graphControlStrat.ScrollMinY = 0D;
            this.graphControlStrat.ScrollMinY2 = 0D;
            this.graphControlStrat.Size = new System.Drawing.Size(567, 355);
            this.graphControlStrat.TabIndex = 123;
            // 
            // graphControl1
            // 
            this.graphControl1.Location = new System.Drawing.Point(21, 75);
            this.graphControl1.Name = "graphControl1";
            this.graphControl1.ScrollMaxX = 0D;
            this.graphControl1.ScrollMaxY = 0D;
            this.graphControl1.ScrollMaxY2 = 0D;
            this.graphControl1.ScrollMinX = 0D;
            this.graphControl1.ScrollMinY = 0D;
            this.graphControl1.ScrollMinY2 = 0D;
            this.graphControl1.Size = new System.Drawing.Size(567, 388);
            this.graphControl1.TabIndex = 0;
            // 
            // PlotT
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.AutoScroll = true;
            this.AutoSize = true;
            this.ClientSize = new System.Drawing.Size(654, 569);
            this.Controls.Add(this.stratPlotGB);
            this.Controls.Add(this.groupBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "PlotT";
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Zone Temperature";
            this.Load += new System.EventHandler(this.tempGraph_Load);
            this.groupBox1.ResumeLayout(false);
            this.stratPlotGB.ResumeLayout(false);
            this.stratPlotGB.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.trackBarStrat)).EndInit();
            this.ResumeLayout(false);

		}
		#endregion

        public GraphPane mySPane;
        public LineItem curveS;
        public double threshHoldY = stratdata.H0;

		private void tempGraph_Load(object sender, System.EventArgs e)
		{
            ///prepare data// 01/16/2007

            //generate the plot
			GraphPane myTPane = graphControl1.GraphPane;

			// Set the title and axis labels
			myTPane.Title.Text = "Zone temperature vs. Time";
			myTPane.XAxis.Title.Text = "Time (hr) of a day, starting from midnight";
            myTPane.XAxis.Scale.Min = -1;
            myTPane.XAxis.Scale.Max = 25;
            if (globalcontrol.units == 0)
            {
                myTPane.YAxis.Title.Text = "Temperature (°C)"; //\n(C)";
            }
            else
            {
                myTPane.YAxis.Title.Text = "Temperature (°F)";
            }

			//enter some arbitrary data points
			double[] xT = plotdata.timeX24h ;
            double[] yT0 = plotdata.TY0; //FADE: Outdoor temperature (25 temp points)
            double[] yT24h = plotdata.TY24h; //FADE: Zone temperature (25 temp points)
            double[] yT = plotdata.TY;

            if (globalcontrol.units == 1)
            {
                for (int i = 0; i < yT0.Length; i++)
                {
                    yT0[i] = yT0[i] * 9 / 5 + 32;
                    yT24h[i] = yT24h[i] * 9 / 5 + 32;
                }
                for (int i = 0; i < yT.Length; i++)
                {
                    yT[i] = yT[i] * 9 / 5 + 32;
                }
            }

			// Add a green curve
			LineItem curveT = myTPane.AddCurve( "Outdoor", xT, yT0, Color.Green, SymbolType.Circle );
			curveT.Line.Width = 3.0F;
			// Make the curve smooth with cardinal splines
			curveT.Line.IsSmooth = true;
			curveT.Line.SmoothTension = 0.6F;
			// Fill the symbols with white to make them opaque
			curveT.Symbol.Fill = new Fill( Color.White );
			curveT.Symbol.Size = 10;
			
			// Add a second curve
            curveT = myTPane.AddCurve("Zone " + plotdata.selZone.ToString(), xT, yT24h, Color.FromArgb(200, 55, 135), SymbolType.Triangle); //FADENEW
            // Fill the symbols with white to make them opaque
            curveT.Symbol.Fill = new Fill(Color.White);
            curveT.Symbol.Size = 10;
            curveT.Line.IsVisible = false;

            // Add a third curve
            curveT = myTPane.AddCurve("Zone " + plotdata.selZone.ToString(), plotdata.timeX, yT, Color.FromArgb(200, 55, 135), SymbolType.None); //FADENEW
            curveT.Line.Width = 3.0F;
            curveT.Line.IsSmooth = true;
            curveT.Line.SmoothTension = 0.6F;
            curveT.Label.IsVisible = false;
			
			// Fill the pane background with a color gradient
			myTPane.Fill = new Fill( Color.WhiteSmoke, Color.Lavender, 0F );
			
			// Fill the axis background with a color gradient
			myTPane.Chart.Fill = new Fill( Color.FromArgb( 255, 255, 245),
				Color.FromArgb( 255, 255, 190), 90F );
			
			// Show the x and y axis gridlines
			myTPane.XAxis.MajorGrid.IsVisible = true;
			myTPane.YAxis.MajorGrid.IsVisible = true;
			
			//JInchao Yuan 01/17/2007: we do not use this type
			// Use the stacked curve type so the curve values sum up
			// this also causes only the area between the curves to be filled, rather than
			// the area between each curve and the x axis
			//myTPane.LineType = LineType.Stack;

			graphControl1.AxisChange();

            // FADENEW: Moved thermal stratification here to be able to open more than one window for scenario comparison (once dif scenarios are enabled)
            if (stratdata.plotStrat)
            {
                stratPlotGB.Enabled = true;
                stratPlotGB.Visible = true;

                mySPane = graphControlStrat.GraphPane;

                if (globalcontrol.units == 0)
                {
                    occHeightUnits.Text = "m";
                }
                else
                {
                    occHeightUnits.Text = "ft";
                }

                if (datapackage.transimu == 0)	//steady state
                {
                    this.trackBarStrat.Visible = false;
                    this.snapLabel.Visible = false;
                    this.snapLabel10.Visible = false;
                    this.snapLabel17.Visible = false;
                    this.snapLabel24.Visible = false;
                    this.label1.Visible = false;
                }
                else //transient
                {
                    this.trackBarStrat.Visible = true;
                    //if (globalcontrol.trackbartime == 24) //FADENNEW
                    //    globalcontrol.trackbartime=0;
                    this.trackBarStrat.Value = globalcontrol.trackbartime;
                }

                // Set the title and axis labels
                this.mySPane.Title.Text = "Zone " + (stratdata.selZone + 1).ToString() + " temperature vs. Room height";
                if (globalcontrol.units == 0)
                {
                    this.mySPane.XAxis.Title.Text = "Temperature (°C)";
                    this.mySPane.YAxis.Title.Text = "Room height (m)"; //\n(C)";
                }
                else
                {
                    this.mySPane.XAxis.Title.Text = "Temperature (°F)";
                    this.mySPane.YAxis.Title.Text = "Room height (ft)";
                }

                // Fill the pane background with a color gradient
                this.mySPane.Fill = new Fill(Color.WhiteSmoke, Color.Lavender, 0F);

                // Fill the axis background with a color gradient
                this.mySPane.Chart.Fill = new Fill(Color.FromArgb(255, 255, 245),
                    Color.FromArgb(255, 255, 190), 90F);

                // Show the x and y axis gridlines
                this.mySPane.XAxis.MajorGrid.IsVisible = true;
                this.mySPane.YAxis.MajorGrid.IsVisible = true;

                if (datapackage.transimu == 0)
                {
                    if (globalcontrol.units == 0)
                    {
                        this.mySPane.XAxis.Scale.Max = stratdata.temperatureX[0][2] + 1;
                        this.mySPane.XAxis.Scale.Min = stratdata.temperatureX[0][0] - 1;
                    }
                    else
                    {
                        this.mySPane.XAxis.Scale.Max = stratdata.temperatureX[0][2] * 9 / 5 + 32 + 1;
                        this.mySPane.XAxis.Scale.Min = stratdata.temperatureX[0][0] * 9 / 5 + 32 - 1;
                    }
                }
                else
                {
                    if (globalcontrol.units == 0)
                    {
                        this.mySPane.XAxis.Scale.Max = Math.Ceiling(FindMax(stratdata.temperatureX, (LutonVisual.daytosimulateindex - 1) * 24, (LutonVisual.daytosimulateindex - 1) * 24 + 24, 3)); //FADENEW //COMPLETELY unrelated to CoolVent's Ceiling and Floor.
                        this.mySPane.XAxis.Scale.Min = Math.Floor(FindMin(stratdata.temperatureX, (LutonVisual.daytosimulateindex - 1) * 24, (LutonVisual.daytosimulateindex - 1) * 24 + 24, 3)); //FADENEW
                    }
                    else
                    {
                        this.mySPane.XAxis.Scale.Max = Math.Ceiling((FindMax(stratdata.temperatureX, (LutonVisual.daytosimulateindex - 1) * 24, (LutonVisual.daytosimulateindex - 1) * 24 + 24, 3)) * 9 / 5 + 32+1); //FADENEW //COMPLETELY unrelated to CoolVent's Ceiling and Floor.
                        this.mySPane.XAxis.Scale.Min = Math.Floor((FindMin(stratdata.temperatureX, (LutonVisual.daytosimulateindex - 1) * 24, (LutonVisual.daytosimulateindex - 1) * 24 + 24, 3)) * 9 / 5 + 32-1); //FADENEW
                    }
                }

                this.mySPane.YAxis.Scale.Min = 0;
                if (globalcontrol.units == 0)
                {
                    this.mySPane.YAxis.Scale.Max = datapackage.floortoceilingheight;
                }
                else
                {
                    this.mySPane.YAxis.Scale.Max = datapackage.floortoceilingheight * 3.28084;
                }

                this.mySPane.YAxis.Scale.MinAuto = false;
                this.mySPane.YAxis.Scale.MaxAuto = false;
                this.mySPane.XAxis.Scale.MinAuto = false;
                this.mySPane.XAxis.Scale.MaxAuto = false;

                //Add occupant height line
                LineObj threshHoldLine = new LineObj(Color.Red, mySPane.XAxis.Scale.Min, threshHoldY, mySPane.XAxis.Scale.Max, threshHoldY);
                this.mySPane.GraphObjList.Add(threshHoldLine);
                this.occHeight.Text = (globalcontrol.occheight).ToString("0.0");
                this.refreshPlot();
            }
            else
            {
                this.stratPlotGB.Enabled = false;
                this.stratPlotGB.Visible = false;
            }
		}

    private void trackBar1_Scroll(object sender, EventArgs e)
        {
            //globalcontrol.trackbartime = trackBarStrat.Value;
            this.mySPane.CurveList.Clear();
            //graphControlStrat.Refresh();
            this.refreshPlot();
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
            double[] xStemp = stratdata.temperatureX[snapposition]; //FADENEW: I hate this address thing!
            double[] ySTemp = stratdata.heightY[snapposition]; // FADENEW

            double[] xS = new double[3];
            double[] yS = new double[3];

            for (int i = 0; i < xS.Length; i++)
            {
                xS[i] = xStemp[i];
                yS[i] = ySTemp[i];
            }

            if (globalcontrol.units == 1)
            {
                for (int i = 0; i < xS.Length; i++)
                {
                    xS[i] = xS[i] * 9 / 5 + 32;
                    yS[i] = yS[i] * 3.28084;
                }
            }

            // Add a green curve
            this.curveS = mySPane.AddCurve("Time: " + trackBarStrat.Value.ToString() + ":00", xS, yS, Color.Green, SymbolType.Circle);
            this.curveS.Line.Width = 3.0F;
            // Fill the symbols with white to make them opaque
            this.curveS.Symbol.Fill = new Fill(Color.White);
            this.curveS.Symbol.Size = 10;

            if (datapackage.transimu == 0)
                this.mySPane.Legend.IsVisible = false; //hides the legend from steady state simulations


            this.graphControlStrat.AxisChange();
            //graphControlStrat.RestoreScale(mySPane);
            //graphControlStrat.ZoomOut(mySPane);
            this.graphControlStrat.Refresh();
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
            double convlength = 1;
            try
            {
                double heighttemp = Double.Parse(occHeight.Text);
                if (heighttemp < 0)
                {
                    System.ApplicationException ex = new System.ApplicationException("Height of occupied zone must be positive!");
                    throw ex;
                }
                else
                {
                    if (globalcontrol.units == 1)
                    {
                        convlength = 3.28084;
                    }
                    globalcontrol.occheight = Double.Parse(occHeight.Text) / convlength;
                    this.occHeight.BackColor = Color.White;
                }
            }
            catch (Exception)
            {
                this.occHeight.BackColor = Color.PaleVioletRed;
            }
            this.mySPane.CurveList.Clear();
            this.mySPane.GraphObjList.Clear();
            this.threshHoldY = globalcontrol.occheight * convlength;
            LineObj threshHoldLine = new LineObj(Color.Red, mySPane.XAxis.Scale.Min, threshHoldY, mySPane.XAxis.Scale.Max, threshHoldY);
            this.mySPane.GraphObjList.Add(threshHoldLine);
            this.refreshPlot();
        }
    }
}
