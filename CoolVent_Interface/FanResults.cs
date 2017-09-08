using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using ZedGraph;

namespace CoolVent
{
    public partial class FanResults : Form
    {
        public GraphPane gPFanResults;
        public LineItem lIFanSpeed;
        public LineItem lIFanPower;
        public LineItem lIFanEfficiency;

        public FanResults(List<double> eta, List<double> power, List<double> gamma, int type)
        {
            InitializeComponent();
            printresults(eta, power, gamma, type);
        }

        private void FanResults_Load(object sender, EventArgs e)
        {


        }

        private void printresults(List<double> eta, List<double> power, List<double> gamma, int fanType)
        {
            double fand = 0;
            double fanrs = 0;
            if (fanType == 1) //Light duty
            {
                this.fanTypeL.Text = "Light duty";
                fand = 0.508;
                fanrs = 1241;
            }
            else if (fanType == 2)
            {
                this.fanTypeL.Text = "Medium duty";
                fand = 1.0668;
                fanrs = 524;
            }
            else if (fanType == 3)
            {
                this.fanTypeL.Text = "Heavy duty";
                fand = 1.524;
                fanrs = 496;
            }
            else if (fanType == 0)
            {
                this.fanTypeL.Text = "No fan";
            }

            if (fanType != 0)
            {
                this.fanDiameterL.Text = fand.ToString();
                if (datapackage.bldgtype == 0 || datapackage.bldgtype == 1)
                {
                    this.noOfFansL.Text = (Math.Floor(datapackage.topwindowsize / (Math.PI / 4 * Math.Pow(fand, 2)))).ToString();
                }
                else if (datapackage.bldgtype == 2)
                {
                    this.noOfFansL.Text = (Math.Floor(datapackage.sidewindowsize / (Math.PI / 4 * Math.Pow(fand, 2)))).ToString();
                }

                if (datapackage.transimu == 0) //SS
                {

                }
                else
                {
                    double[] eff = new double[24];
                    double[] pow = new double[24];
                    double[] sp = new double[24];
                    double[] time = new double[24];
                    int i = 0;
                    double maxeff = 0;
                    double maxpow = 0;
                    double maxsp = 0;
                    double avgeff = 0;
                    double avgsp = 0;
                    double totpow = 0;

                    for (int linea = 39; linea <= 40 * 24 - 1; linea += 40)
                    {
                        eff[i] = eta[linea] * 100;
                        avgeff += eff[i] / 24;
                        pow[i] = power[linea] / 1000;
                        totpow += pow[i];
                        sp[i] = (1 / gamma[linea]) * fanrs;
                        avgsp += sp[i] / 24;
                        if (eff[i] > maxeff)
                        {
                            maxeff = eff[i];
                        }
                        if (pow[i] > maxpow)
                        {
                            maxpow = pow[i];
                        }
                        if (sp[i] > maxsp)
                        {
                            maxsp = sp[i];
                        }
                        time[i] = ++i;
                    }

                    this.fanOSL.Text = (Math.Round(avgsp)).ToString();
                    this.fanPowerL.Text = (Math.Round(totpow)).ToString();
                    this.fanEtaL.Text = (Math.Round(avgeff)).ToString();

                    gPFanResults = FanCurvePane.GraphPane;

                    // Set the title and axis labels
                    gPFanResults.Title.IsVisible = false;
                    gPFanResults.XAxis.Title.Text = "Time of a day, starting from midnight [hr]";
                    gPFanResults.YAxis.Title.Text = "Electric consumption [kW]";
                    gPFanResults.Y2Axis.Title.Text = "Operational speed [rpm]";
                    Y2Axis yAxis2 = new Y2Axis("Efficiency [%]");
                    gPFanResults.Y2AxisList.Add(yAxis2);
                    gPFanResults.Y2Axis.IsVisible = true;
                    yAxis2.IsVisible = true;
                    yAxis2.Scale.Align = AlignP.Inside;

                    //Chart format
                    gPFanResults.Fill = new Fill(Color.WhiteSmoke, Color.Lavender, 0F);
                    gPFanResults.Chart.Fill = new Fill(Color.FromArgb(255, 255, 245),
                    Color.FromArgb(255, 255, 190), 90F);
                    gPFanResults.XAxis.MajorGrid.IsVisible = true;

                    //Plot curves
                    lIFanPower = gPFanResults.AddCurve("Electric consumption", time, pow, Color.Green, SymbolType.None);
                    lIFanPower.Line.Width = 2.0F;
                    //lIFanPower.Line.IsSmooth = true;
                    lIFanSpeed = gPFanResults.AddCurve("Speed", time, sp, Color.Red, SymbolType.None);
                    lIFanSpeed.IsY2Axis = true;
                    lIFanSpeed.Line.Width = 2.0F;
                    //lIFanSpeed.Line.IsSmooth = true;
                    lIFanEfficiency = gPFanResults.AddCurve("Efficiency", time, eff, Color.Blue, SymbolType.None);
                    lIFanEfficiency.Line.Width = 2.0F;
                    //lIFanEfficiency.Line.IsSmooth = true;
                    lIFanEfficiency.IsY2Axis = true;
                    lIFanEfficiency.YAxisIndex = 1;

                    //Axis format
                    gPFanResults.YAxis.MajorTic.IsOpposite = false;
                    gPFanResults.Y2Axis.MajorTic.IsOpposite = false;
                    gPFanResults.YAxis.MinorTic.IsOpposite = false;
                    gPFanResults.Y2Axis.MinorTic.IsOpposite = false;
                    yAxis2.MinorTic.IsOpposite = false;
                    yAxis2.MajorTic.IsOpposite = false;

                    gPFanResults.YAxis.Scale.FontSpec.FontColor = Color.Green;
                    gPFanResults.YAxis.Title.FontSpec.FontColor = Color.Green;

                    gPFanResults.Y2Axis.Title.FontSpec.FontColor = Color.Red;
                    gPFanResults.Y2Axis.Scale.FontSpec.FontColor = Color.Red;

                    yAxis2.Title.FontSpec.FontColor = Color.Blue;
                    yAxis2.Scale.FontSpec.FontColor = Color.Blue;

                    gPFanResults.YAxis.Scale.MajorStepAuto = false;
                    gPFanResults.YAxis.Scale.MinorStepAuto = false;
                    gPFanResults.YAxis.Scale.Max = maxpow * 1.1;
                    gPFanResults.YAxis.Scale.Min = 0;
                    gPFanResults.YAxis.Scale.MajorStep = maxpow * 1.1 / 4;
                    gPFanResults.YAxis.Scale.MinorStep = maxpow * 1.1 / 8;

                    gPFanResults.Y2Axis.Scale.MajorStepAuto = false;
                    gPFanResults.Y2Axis.Scale.MinorStepAuto = false;
                    gPFanResults.Y2Axis.Scale.Max = maxsp * 2;
                    gPFanResults.Y2Axis.Scale.Min = 0;
                    gPFanResults.Y2Axis.Scale.MajorStep = maxsp * 2 / 4;
                    gPFanResults.Y2Axis.Scale.MinorStep = maxsp * 2 / 8;

                    yAxis2.Scale.MajorStepAuto = false;
                    yAxis2.Scale.MajorStepAuto = false;
                    yAxis2.Scale.Max = 100;
                    yAxis2.Scale.Min = 0;
                    yAxis2.Scale.MajorStep = 20;
                    yAxis2.Scale.MinorStep = 10;

                    gPFanResults.XAxis.Scale.MajorStepAuto = false;
                    gPFanResults.XAxis.Scale.Format = "0#";
                    gPFanResults.XAxis.Scale.Max = 25;
                    gPFanResults.XAxis.Scale.Min = 0;
                    gPFanResults.XAxis.Scale.MajorStep = 5;
                    gPFanResults.XAxis.Scale.MinorStep = 1;
                }
            }
            else
            {
                this.noOfFansL.Text = "0";
                this.fanOSL.Text = "0";
                this.fanPowerL.Text = "0";
                this.fanEtaL.Text = "0";
                this.fanDiameterL.Text = "0";
                this.FanCurvePane.Visible = false;
            }
        }

    }





    }
