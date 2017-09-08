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
    public partial class FanCurve : Form
    {

        public GraphPane gPFanCurve;
        public LineItem lIFanCurve;


        public FanCurve()
        {
            InitializeComponent();
        }

        private void FanCurve_Load(object sender, EventArgs e)
        {
            gPFanCurve = FanCurvePane.GraphPane;

            // Set the title and axis labels
            gPFanCurve.Title.Text = "Predefined fan performance";
            gPFanCurve.XAxis.Title.Text = "Discharge [m^3/s]";
            gPFanCurve.YAxis.Title.Text = "Pressure rise [Pa]";
            gPFanCurve.Fill = new Fill(Color.WhiteSmoke, Color.Lavender, 0F);
            gPFanCurve.Chart.Fill = new Fill(Color.FromArgb(255, 255, 245),
            Color.FromArgb(255, 255, 190), 90F);
            gPFanCurve.YAxis.Scale.MajorStepAuto = false;
            gPFanCurve.YAxis.Scale.Max = 94;
            gPFanCurve.YAxis.Scale.Min = 0;
            gPFanCurve.YAxis.Scale.MajorStep = 20;
            gPFanCurve.YAxis.Scale.MinorStep = 10;
            gPFanCurve.YAxis.Scale.BaseTic = 10;

            gPFanCurve.XAxis.Scale.MajorStepAuto = false;            
            gPFanCurve.XAxis.Scale.Max = 32;
            gPFanCurve.XAxis.Scale.Min = 0;
            gPFanCurve.XAxis.Scale.MajorStep = 5;
            gPFanCurve.XAxis.Scale.MinorStep = 1;
            //Light-duty
            double[] Ql = new double[83];
            Ql[0] = 2.113852598;
            double[] Pl = new double[83];
            //Medium-duty
            double[] Qm = new double[83];
            Qm[0] = 0;
            double[] Pm = new double[83];
            //Heavy-duty
            double[] Qh = new double[83];
            Qh[0] = 24.69134633;
            double[] Ph = new double[83];

            for (int i = 0; i < 83; i++)
            {
                Pl[i] = -40.186 * Math.Pow(Ql[i], 2) + 97.263 * Ql[i] + 68.076;
                Pm[i] = -1.1342 * Math.Pow(Qm[i], 2) + 150;
                Ph[i] = -0.3372 * Math.Pow(Qh[i], 2) + 4.5357 * Qh[i] + 187.19;
                if (i < 82)
                {
                    Ql[i + 1] = (2.995922369 - 2.113852598) / 81 * i + 2.113852598;
                    Qm[i + 1] = (11.50007858 - 0) / 81 * i;
                    Qh[i + 1] = (31.23631348 - 24.69134633) / 81 * i + 24.69134633;
                }
            }
            lIFanCurve = gPFanCurve.AddCurve("Light-duty fan", Ql, Pl, Color.Green, SymbolType.None);
            lIFanCurve.Line.Width = 3.0F;
            lIFanCurve = gPFanCurve.AddCurve("Medium-duty fan", Qm, Pm, Color.Blue, SymbolType.None);
            lIFanCurve.Line.Width = 3.0F;
            lIFanCurve = gPFanCurve.AddCurve("Heavy-duty fan", Qh, Ph, Color.Red, SymbolType.None);
            lIFanCurve.Line.Width = 3.0F;
        }
    }
}
