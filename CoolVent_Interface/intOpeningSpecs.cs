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
    public partial class intOpeningSpecsWindow : Form
    {
        private double length_conv;

        public intOpeningSpecsWindow()
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
                this.units1LB.Text = "ft";
                this.units2LB.Text = "ft";
            }
            else
            {
                this.units1LB.Text = "m";
                this.units2LB.Text = "m";
            }
            //Load data from globalcontrol
            this.intAreaHeight.Text = (globalcontrol.intAreaHeight * length_conv).ToString();
            this.difHeight.Checked = (globalcontrol.intOpDifHeight);
            this.shaft2InletHeightTB.Text = (globalcontrol.intAreaShaft2Height * length_conv).ToString();
            

            //sdr_hulic - if ventilation shaft is used, clear and disable all internal opening fields
            //if (datapackage.bldgtype == 4)
            //{
            //    //clear text boxes
            //    this.internalOpeningArea.Text = "";
            //    this.intAreaHeight.Text = "";
            //    this.intOpeningCd.Text = "";
            //    this.intGndOpeningArea.Text = "";
            //    this.intGndAreaHeight.Text = "";
            //    this.intGndOpeningCd.Text = "";

            //    //disable text boxes
            //    this.internalOpeningArea.Enabled = false;
            //    this.intAreaHeight.Enabled = false;
            //    this.intOpeningCd.Enabled = false;
            //    this.intGndOpeningArea.Enabled = false;
            //    this.intGndAreaHeight.Enabled = false;
            //    this.intGndOpeningCd.Enabled = false;

            //    //disable ground check box
            //    this.difHeight.Enabled = false;

            //}

            //Enable according to globalcontrol loaded data
            if (globalcontrol.bldgtype == 4)
            {
                this.shaft2P.Visible = true;
                if (globalcontrol.shaft2Check)
                {
                    this.shaft2P.Enabled = true;
                }
                else
                {
                    this.shaft2P.Enabled = false;
                }
            }
            else
            {
                this.shaft2P.Visible = false;
            }
            if (this.difHeight.Checked)
            {
                this.optionP.Enabled = true;
            }
            else
            {
                this.optionP.Enabled = false;
            }
        }

        private void difHeight_CheckedChanged(object sender, EventArgs e) //Diferent height for internal opening
        {
            if (this.difHeight.Checked) //Activate options
            {
                this.optionP.Enabled = true;
            }
            else //Deactivate options
            {
                this.intAreaHeight.Text = (globalcontrol.intAreaHeight * length_conv).ToString();
                this.shaft2InletHeightTB.Text = (globalcontrol.intAreaShaft2Height * length_conv).ToString();
                this.optionP.Enabled = false;
            }
        }

        private void intOpeningCancelBut_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void HelpButton_Click(object sender, EventArgs e)
        {
            BuildHelp buildingdimesionHelpWindow = new BuildHelp();
            buildingdimesionHelpWindow.ShowDialog();
        }

        private void intOpeningOKBut_Click(object sender, EventArgs e)
        {
            bool inputsOkay = true; //FADE: This one checks that all the numbers make sense
            double intopheight = 0;
            double shaft2intophight = 0;

            //FADE: Check inputs
            try
            {
                intopheight = Double.Parse(this.intAreaHeight.Text);
                shaft2intophight = Double.Parse(this.shaft2InletHeightTB.Text);
            }
            catch (SystemException)
            {
                inputsOkay = false;
            }
            if (intopheight < 0 || shaft2intophight < 0 || Double.Parse(intAreaHeight.Text) > globalcontrol.floorheightGC || Double.Parse(shaft2InletHeightTB.Text) > globalcontrol.floorheightGC)
            {
                inputsOkay = false;
            }

            if (!inputsOkay)
            {
                DialogResult res = MessageBox.Show("Some of your inputs are invalid! Look for red boxes. Lenghts must be non-negative numbers.", "Error!", MessageBoxButtons.OK, MessageBoxIcon.Question);
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
                globalcontrol.intOpDifHeight = this.difHeight.Checked;
                globalcontrol.intAreaHeight = intopheight / length_conv;
                globalcontrol.intAreaShaft2Height = shaft2intophight / length_conv;

                //Datapackage
                datapackage.internalOpeningHeight = globalcontrol.intAreaHeight;
                datapackage.shaft2Inlet = globalcontrol.intAreaShaft2Height;

                this.Close();
            }
        }
        //sdr_hulic - added new button to account for different opening types
        //private void button1_Click(object sender, EventArgs e)
        //{
        //    detailedOpeningSpecs detailedOpeningSpecsWindow = new detailedOpeningSpecs();
        //   detailedOpeningSpecsWindow.ShowDialog();
        //}

        private void shaft2InletHeightTB_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (Double.Parse(shaft2InletHeightTB.Text) > 0 && Double.Parse(shaft2InletHeightTB.Text) <= globalcontrol.floorheightGC)
                {
                    shaft2InletHeightTB.BackColor = Color.White;
                    //allinputs[31] = true;
                }
                else
                {
                    throw new FormatException();
                }
            }
            catch (FormatException)
            {
                shaft2InletHeightTB.BackColor = Color.PaleVioletRed;
                //allinputs[31] = false;
                Console.WriteLine("Exception generated in shaft2InletHeight");
            }
        }

        private void intAreaHeight_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (Double.Parse(intAreaHeight.Text) > 0 && Double.Parse(intAreaHeight.Text) <= globalcontrol.floorheightGC)
                {
                    intAreaHeight.BackColor = Color.White;
                    //allinputs[31] = true;
                }
                else
                {
                    throw new FormatException();
                }
            }
            catch (FormatException)
            {
                intAreaHeight.BackColor = Color.PaleVioletRed;
                //allinputs[31] = false;
                Console.WriteLine("Exception generated in intAreaHeight");
            }
        }
    }
}