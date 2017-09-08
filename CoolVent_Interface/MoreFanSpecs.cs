using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace CoolVent
{
    public partial class MoreFanSpecs : Form
    {
        private double length_conv;

        public MoreFanSpecs()
        {
            InitializeComponent();
            InitialFormLoad();
        }

        private void intOpeningSpecsWindow_Load(object sender, EventArgs e)
        {
            //InitialFormLoad();
        }

        private void InitialFormLoad()
        {
            //Load information and change units
            length_conv = 1;
            if (globalcontrol.units == 1) //
            {
                length_conv = 0.3048;
                this.label23.Text = "ft";
            }
            else
            {
                this.label23.Text = "m";
            }

            //Load data from globalcontrol
            this.fana2.Text = (globalcontrol.fana2).ToString();
            this.fana1.Text = (globalcontrol.fana1).ToString();
            this.fana0.Text = (globalcontrol.fana0).ToString();
            this.fanb2.Text = (globalcontrol.fanb2).ToString();
            this.fanb1.Text = (globalcontrol.fanb1).ToString();
            this.fanD.Text = (globalcontrol.fanD).ToString();
            this.fanrs.Text = (globalcontrol.fanrs).ToString();
            this.fanos.Text = (globalcontrol.fanOperSpeedTB).ToString();

        }

        private void intOpeningCancelBut_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void intOpeningOKBut_Click(object sender, EventArgs e)
        {
            bool inputsOkay = true; //FADE: This one checks that all the numbers make sense
            double fana2 = 0;
            double fana1 = 0;
            double fana0 = 0;
            double fanb2 = 0;
            double fanb1 = 0;
            double fand = 0;
            double fanrs = 0;
            double fanos = 0;

            //FADE: Check inputs
            try
            {
                fana2 = Double.Parse(this.fana2.Text);
                fana1 = Double.Parse(this.fana1.Text);
                fana0 = Double.Parse(this.fana0.Text);
                fanb2 = Double.Parse(this.fanb2.Text);
                fanb1 = Double.Parse(this.fanb1.Text);
                fand = Double.Parse(this.fanD.Text);
                fanrs = Double.Parse(this.fanrs.Text);
                fanos = Double.Parse(this.fanos.Text);
            }
            catch (SystemException)
            {
                inputsOkay = false;
            }

            if (fana2 > 0 || fanb2 > 0 || fand <= 0 || fanrs <= 0 || fanos < 0)
            {
                inputsOkay = false;
            }
            if (!inputsOkay)
            {
                DialogResult res = MessageBox.Show("Some of your inputs are invalid! Parameters a2 and b2 cannot be larger than zero. Lenghts and fan speed must be non-negative numbers.", "Error!", MessageBoxButtons.OK, MessageBoxIcon.Question);
                if (res == DialogResult.OK)
                {
                    return;
                }
            }
            else
            {
                //Units conversion
                length_conv = 1;
                if (globalcontrol.units == 1)
                {
                    length_conv = 0.3048;
                }
                //Globalcontrol
                globalcontrol.fana2 = fana2;
                globalcontrol.fana1 = fana1;
                globalcontrol.fana0 = fana0;
                globalcontrol.fanb2 = fanb2;
                globalcontrol.fanb1 = fanb1;
                globalcontrol.fanD = fand;
                globalcontrol.fanrs = fanrs;
                globalcontrol.fanOperSpeedTB = fanos;
                //Datapackage
                //datapackage.fana2 = globalcontrol.fana2;
                //datapackage.fana1 = globalcontrol.fana1;
                //datapackage.fana0 = globalcontrol.fana0;
                //datapackage.fanb2 = globalcontrol.fanb2;
                //datapackage.fanb1 = globalcontrol.fanb1;
                //datapackage.fanD = globalcontrol.fanD;
                //datapackage.Tuser = globalcontrol.fanTempSetPointTB;
                this.Close();
            }
        }
    }
}
