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
    public partial class IntSpecsCV : Form
    {
        private double length_conv;


        public IntSpecsCV()
        {
            InitializeComponent();
        }

        private void IntSpecsCV_Load(object sender, EventArgs e)
        {
            length_conv = 1;
            if (globalcontrol.units == 1) //IP units
            {
                length_conv = 0.3048;
                this.label3.Text = "ft";
                this.label6.Text = "ft";
                this.label7.Text = "ft";
            }
            else
            {
                this.label3.Text = "m";
                this.label6.Text = "m";
                this.label7.Text = "m";
            }
            ATB.Text = (datapackage.windowheight * length_conv).ToString();
            iOTB.Text = (globalcontrol.intAreaHeight * length_conv).ToString();
            BTB.Text = (globalcontrol.windowheightB * length_conv).ToString();
            cdTB.Text = (globalcontrol.cdCheck).ToString("0.00");
            checkBox1.Checked = globalcontrol.opDifHei;
            //cdCheckCB.Checked = (!globalcontrol.defcdCB); //defcdCB in globalcontrol is true for default value of CD!
            this.fanCheckBox.Checked = (globalcontrol.fanCB);
            this.fanPredefinedRadio.Checked = (globalcontrol.fanpredefRB);
            this.fanOptimRadio.Checked = (globalcontrol.fanoptimRB);
            this.fanPersonalizedRadio.Checked = (globalcontrol.fanpersonRB);
            this.fanOperatingSpeedCheck.Checked = (globalcontrol.fanOpSpeedCB);

            if (globalcontrol.fanCheck == 1)
            {
                this.predefinedLightRB.Checked = true;
                this.predefinedMediumRB.Checked = false;
                this.predefinedHeavyRB.Checked = false;
            }
            else if (globalcontrol.fanCheck == 2 || globalcontrol.fanCheck == 0 || globalcontrol.fanCheck == 4 || globalcontrol.fanCheck == 5)
            {
                this.predefinedLightRB.Checked = false;
                this.predefinedMediumRB.Checked = true;
                this.predefinedHeavyRB.Checked = false;
            }
            else if (globalcontrol.fanCheck == 3)
            {
                this.predefinedLightRB.Checked = false;
                this.predefinedMediumRB.Checked = false;
                this.predefinedHeavyRB.Checked = true;
            }
            if (globalcontrol.fanOperSpeedTB != -1)
            {
                this.fanOperSpeedTextBox.Text = (globalcontrol.fanOperSpeedTB).ToString("0.0");
            }
            if (globalcontrol.fanTempSetPointTB != -300)
            {
                this.tempSetPointTextBox.Text = (globalcontrol.fanTempSetPointTB).ToString("0.0");
            }

            if (checkBox1.Checked) //Openings at different h activated
            {
                ATB.Enabled = true;
                iOTB.Enabled = true;
                BTB.Enabled = true;
            }
            else
            {
                ATB.Enabled = false;
                iOTB.Enabled = false;
                BTB.Enabled = false;
            }
            if (cdCheckCB.Checked) //Activate discharge coefficient specification
            {
                cdTB.Enabled = true;
            }
            else
            {
                cdTB.Enabled = false;
            }

            if (this.fanCheckBox.Checked) //Activate fan options
            {
                this.fanPredefinedRadio.Enabled = true;
                this.fanOptimRadio.Enabled = true;
                this.fanPersonalizedRadio.Enabled = true;

                //Predefined fan
                if (globalcontrol.fanpredefRB)
                {
                    //Activate the predefined fan RBs
                    this.predefinedLightRB.Enabled = true;
                    this.predefinedMediumRB.Enabled = true;
                    this.predefinedHeavyRB.Enabled = true;
                    //Activate fan curve button & operating speed check box
                    this.fanCurveButton.Enabled = true;
                    this.fanOperatingSpeedCheck.Enabled = true;
                    //Deactivate optim and personal fans
                    this.fanOptimRadio.Checked = false;
                    this.tempSetPointTextBox.Enabled = false;
                    this.fanPersonalizedRadio.Checked = false;
                    this.peronalizedButton.Enabled = false;
                    //Activate fan operating speed
                    if (globalcontrol.fanOpSpeedCB)
                    {
                        fanOperSpeedTextBox.Enabled = true;
                    }
                    else
                    {
                        fanOperSpeedTextBox.Enabled = false;
                    }
                }

                //Optim fan
                else if (globalcontrol.fanoptimRB)
                {
                    this.fanOptimRadio.Checked = true;
                    //Activate the fanOptimRadio fan options (i.e. temp setpoint)
                    this.tempSetPointTextBox.Enabled = true;
                    //Deactivate predef & personalized fan
                    this.fanPredefinedRadio.Checked = false;
                    this.predefinedLightRB.Enabled = false;
                    this.predefinedMediumRB.Enabled = false;
                    this.predefinedHeavyRB.Enabled = false;
                    this.fanOperSpeedTextBox.Enabled = false;
                    this.fanPersonalizedRadio.Checked = false;
                    this.peronalizedButton.Enabled = false;
                }

                else if (globalcontrol.fanpersonRB) //Personalized fan
                {
                    //Activate personalized fan options (i.e. "yet more advanced options" button)
                    this.peronalizedButton.Enabled = true;
                    //Deactivate predefined & optim options
                    this.fanPredefinedRadio.Enabled = false;
                    this.predefinedLightRB.Enabled = false;
                    this.predefinedMediumRB.Enabled = false;
                    this.predefinedHeavyRB.Enabled = false;
                    this.fanOptimRadio.Checked = false;
                    this.fanOperSpeedTextBox.Enabled = false;
                }
            }
            else //Deactivate fan options
            {
                this.fanPredefinedRadio.Enabled = false;
                this.predefinedLightRB.Enabled = false;
                this.predefinedMediumRB.Enabled = false;
                this.predefinedHeavyRB.Enabled = false;
                this.fanCurveButton.Enabled = false;
                this.fanOperatingSpeedCheck.Enabled = false;
                this.fanOperSpeedTextBox.Enabled = false;
                this.fanOptimRadio.Enabled = false;
                this.tempSetPointTextBox.Enabled = false;
                this.fanPersonalizedRadio.Enabled = false;
                this.peronalizedButton.Enabled = false;
            }
        }

        private void checkBox1_CheckedChanged(object sender, EventArgs e)
        {
            if (checkBox1.Checked)
            {
                ATB.Enabled = true;
                iOTB.Enabled = true;
                BTB.Enabled = true;
            }
            else
            {
                ATB.Enabled = false;
                iOTB.Enabled = false;
                BTB.Enabled = false;
            }
        }

        private void cdCheckCB_CheckedChanged(object sender, EventArgs e) //You can specify the cd
        {
            if (cdCheckCB.Checked) //You can specify the cd
            {
                cdTB.Enabled = true;
            }
            else //Use defailt value
            {
                cdTB.Enabled = false;
            }
        }

        private void fanCheckBox_CheckedChanged(object sender, EventArgs e)
        {
            if (this.fanCheckBox.Checked) //Activate fan options
            {
                this.fanPredefinedRadio.Enabled = true;
                this.fanOptimRadio.Enabled = true;
                this.fanPersonalizedRadio.Enabled = true;

                //Pre-defined fan is activated
                if (globalcontrol.fanpredefRB)
                {
                    //this.fanPredefinedRadio.Checked = true;
                    //Activate predefined options
                    this.predefinedLightRB.Enabled = true;
                    this.predefinedMediumRB.Enabled = true;
                    this.predefinedHeavyRB.Enabled = true;
                    this.fanCurveButton.Enabled = true;
                    this.fanOperatingSpeedCheck.Enabled = true;
                    //this.fanOperSpeedTextBox.Enabled = false;
                    if (globalcontrol.fanCheck == 1)
                    {
                        this.predefinedLightRB.Checked = true;
                        this.predefinedMediumRB.Checked = false;
                        this.predefinedHeavyRB.Checked = false;
                    }
                    else if (globalcontrol.fanCheck == 2 || globalcontrol.fanCheck == 0 || globalcontrol.fanCheck == 4 || globalcontrol.fanCheck == 5) //Default fan
                    {
                        this.predefinedLightRB.Checked = false;
                        this.predefinedMediumRB.Checked = true;
                        this.predefinedHeavyRB.Checked = false;
                    }
                    else if (globalcontrol.fanCheck == 3)
                    {
                        this.predefinedLightRB.Checked = false;
                        this.predefinedMediumRB.Checked = false;
                        this.predefinedHeavyRB.Checked = true;
                    }
                    if (globalcontrol.fanOpSpeedCB)
                    {
                        this.fanOperatingSpeedCheck.Checked = true;
                        this.fanOperSpeedTextBox.Enabled = true;
                    }
                    else
                    {
                        this.fanOperatingSpeedCheck.Checked = false;
                        this.fanOperSpeedTextBox.Enabled = false;
                    }
                    //Deactivate optim & personalized fan options
                    this.fanOptimRadio.Checked = false;
                    this.tempSetPointTextBox.Enabled = false;
                    this.fanPersonalizedRadio.Checked = false;
                    this.peronalizedButton.Enabled = false;
                }

                //Optim fan is activated
                else if (globalcontrol.fanoptimRB)
                {
                    this.fanOptimRadio.Checked = true;
                    //Activate optm options
                    this.tempSetPointTextBox.Enabled = true;
                    //Deactivate predef & personalized fan options
                    this.fanPredefinedRadio.Checked = false;
                    this.predefinedLightRB.Enabled = false;
                    this.predefinedMediumRB.Enabled = false;
                    this.predefinedHeavyRB.Enabled = false;
                    this.fanCurveButton.Enabled = false;
                    this.fanOperatingSpeedCheck.Enabled = false;
                    this.fanOperSpeedTextBox.Enabled = false;
                    this.fanPersonalizedRadio.Checked = false;
                    this.peronalizedButton.Enabled = false;
                }

                //Personalized fan is activated
                else if (globalcontrol.fanpersonRB)
                {
                    this.fanPersonalizedRadio.Checked = true;
                    //Activate personalized fan options
                    this.peronalizedButton.Enabled = true;
                    //Deactivate predef & optim options
                    this.fanPredefinedRadio.Checked = false;
                    this.predefinedLightRB.Enabled = false;
                    this.predefinedMediumRB.Enabled = false;
                    this.predefinedHeavyRB.Enabled = false;
                    this.fanCurveButton.Enabled = false;
                    this.fanOperatingSpeedCheck.Enabled = false;
                    this.fanOperSpeedTextBox.Enabled = false;
                    this.fanOptimRadio.Checked = false;
                    this.tempSetPointTextBox.Enabled = false;
                }
            }
            else if (!this.fanCheckBox.Checked) //Deactivate fan options
            {
                this.fanPredefinedRadio.Enabled = false;
                this.predefinedLightRB.Enabled = false;
                this.predefinedMediumRB.Enabled = false;
                this.predefinedHeavyRB.Enabled = false;
                this.fanCurveButton.Enabled = false;
                this.fanOperatingSpeedCheck.Enabled = false;
                this.fanOperSpeedTextBox.Enabled = false;
                this.fanOptimRadio.Enabled = false;
                this.tempSetPointTextBox.Enabled = false;
                this.fanPersonalizedRadio.Enabled = false;
                this.peronalizedButton.Enabled = false;
            }
        }

        private void fanPredefinedRadio_CheckedChanged(object sender, EventArgs e)
        {
            if (fanPredefinedRadio.Checked)
            {
                //Activate predefined options
                this.predefinedLightRB.Enabled = true;
                this.predefinedMediumRB.Enabled = true;
                this.predefinedHeavyRB.Enabled = true;
                this.fanCurveButton.Enabled = true;
                this.fanOperatingSpeedCheck.Enabled = true;
                if (fanOperatingSpeedCheck.Checked)
                {
                    this.fanOperSpeedTextBox.Enabled = true;
                }
                else
                {
                    this.fanOperSpeedTextBox.Enabled = false;
                }
                //Deactivate optim & personalized fan options
                this.fanOptimRadio.Checked = false;
                this.fanPersonalizedRadio.Checked = false;
            }
            else
            {
                this.predefinedLightRB.Enabled = false;
                this.predefinedMediumRB.Enabled = false;
                this.predefinedHeavyRB.Enabled = false;
                this.fanCurveButton.Enabled = false;
                this.fanOperatingSpeedCheck.Enabled = false;
                this.fanOperSpeedTextBox.Enabled = false;
            }
        }

        private void predefinedLightRB_CheckedChanged(object sender, EventArgs e) //Light-duty
        {
            if (predefinedLightRB.Checked)
            {
                this.predefinedMediumRB.Checked = false;
                this.predefinedHeavyRB.Checked = false;
            }
        }

        private void predefinedMediumRB_CheckedChanged(object sender, EventArgs e)//Medium-duty
        {
            if (predefinedMediumRB.Checked) //Activate pre-defined fan options
            {
                this.predefinedLightRB.Checked = false;
                this.predefinedHeavyRB.Checked = false;
            }
        }

        private void predefinedHeavyRB_CheckedChanged(object sender, EventArgs e) //Heavy-duty
        {
            if (predefinedHeavyRB.Checked) //Activate pre-defined fan options
            {
                this.predefinedLightRB.Checked = false;
                this.predefinedMediumRB.Checked = false;
            }
        }

        private void fanOperatingSpeedCheck_CheckedChanged(object sender, EventArgs e) //Operating speed
        {
            if (fanOperatingSpeedCheck.Checked)
            {
                this.fanOperSpeedTextBox.Enabled = true;
            }
            else
            {
                this.fanOperSpeedTextBox.Enabled = false;
            }

        }

        private void fanOptimRadio_CheckedChanged(object sender, EventArgs e) //Optim fan
        {
            if (fanOptimRadio.Checked)
            {
                this.fanPredefinedRadio.Checked = false;
                this.fanPersonalizedRadio.Checked = false;
                this.tempSetPointTextBox.Enabled = true;
            }
            else
            {
                this.tempSetPointTextBox.Enabled = false;
            }
        }

        private void fanPersonalizedRadio_CheckedChanged(object sender, EventArgs e)
        {
            if (fanPersonalizedRadio.Checked) //Activate personalized fan options
            {
                this.peronalizedButton.Enabled = true;
            }
            else
            {
                this.peronalizedButton.Enabled = false;
            }
        }

        private void peronalizedButton_Click(object sender, EventArgs e)
        {
            MoreFanSpecs moreFanSepcsWindow = new MoreFanSpecs();
            moreFanSepcsWindow.ShowDialog();
        }

        private void fanCurveButton_Click(object sender, EventArgs e)
        {
            FanCurve fanCurveWindow = new FanCurve();
            fanCurveWindow.ShowDialog();
        }

        private void intOpeningOKBut_Click(object sender, EventArgs e)
        {
            double aheight = 0;
            double bheight = 0;
            double intheight = 0;
            double cd = 0.65;
            double fanos = 0;
            double temp = 22;
            bool inputsOkay = true;

            try
            {
                aheight = Double.Parse(this.ATB.Text);
                bheight = Double.Parse(this.BTB.Text);
                intheight = Double.Parse(this.iOTB.Text);
                if (cdCheckCB.Checked) //cd specified by user
                {
                    cd = Double.Parse(this.cdTB.Text);
                }
                if (fanCheckBox.Checked && fanPredefinedRadio.Checked && fanOperatingSpeedCheck.Checked)
                {
                    fanos = Double.Parse(this.fanOperSpeedTextBox.Text);
                }
                if (fanCheckBox.Checked && fanOptimRadio.Checked)
                {
                    temp = Double.Parse(this.tempSetPointTextBox.Text);
                }
                if (fanCheckBox.Checked && fanPersonalizedRadio.Checked)
                {
                    //Call info from globalcontrol
                }
            }
            catch (SystemException)
            {
                inputsOkay = false;
            }

            if (aheight < 0 || bheight < 0 || intheight < 0 || cd < 0 || cd > 1 || fanos < 0 || temp < -273.15)
            {
                inputsOkay = false;
            }

            if (!inputsOkay)
            {
                DialogResult res = MessageBox.Show("Some of your inputs are invalid! Heights, lenghts and fan speed must be non-negative numbers. Opening A has to be heigher than the internal opening. The internal opening has to be heigher than opening B.", "Error!", MessageBoxButtons.OK, MessageBoxIcon.Question);
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
                if (checkBox1.Checked)
                {
                    globalcontrol.opDifHei = true;
                }
                else
                {
                    globalcontrol.opDifHei = false;
                }

                globalcontrol.UpperHeight = aheight / length_conv;
                globalcontrol.intAreaHeight = intheight / length_conv;
                globalcontrol.windowheightB = bheight / length_conv;
                if (cdCheckCB.Checked) //Use user's cd
                {
                    //globalcontrol.defcdCB = false;
                    globalcontrol.cdCheck = cd;
                }
                else
                {
                    //globalcontrol.defcdCB = true;
                    globalcontrol.cdCheck = 0.65;
                }
                if (fanCheckBox.Checked) //Fan is activated
                {
                    globalcontrol.fanCB = true;
                    if (fanPredefinedRadio.Checked)
                    {
                        if (predefinedLightRB.Checked)
                        {
                            globalcontrol.fanCheck = 1;
                            globalcontrol.fanpredefRB = true;
                            globalcontrol.fanoptimRB = false;
                            globalcontrol.fanpersonRB = false;
                        }
                        else if (predefinedMediumRB.Checked)
                        {
                            globalcontrol.fanCheck = 2;
                            globalcontrol.fanpredefRB = true;
                            globalcontrol.fanoptimRB = false;
                            globalcontrol.fanpersonRB = false;
                        }
                        else if (predefinedHeavyRB.Checked)
                        {
                            globalcontrol.fanCheck = 3;
                            globalcontrol.fanpredefRB = true;
                            globalcontrol.fanoptimRB = false;
                            globalcontrol.fanpersonRB = false;
                        }
                        if (fanOperatingSpeedCheck.Checked)
                        {
                            globalcontrol.fanOperSpeedTB = fanos;
                            globalcontrol.fanOpSpeedCB = true;
                        }
                        else
                        {
                            globalcontrol.fanOpSpeedCB = false;
                        }
                    }
                    else if (fanOptimRadio.Checked)
                    {
                        globalcontrol.fanCheck = 4;
                        globalcontrol.fanTempSetPointTB = temp;
                        globalcontrol.fanpredefRB = false;
                        globalcontrol.fanoptimRB = true;
                        globalcontrol.fanpersonRB = false;
                    }
                    else if (fanPersonalizedRadio.Checked)
                    {
                        globalcontrol.fanCheck = 5;
                        globalcontrol.fanpredefRB = false;
                        globalcontrol.fanoptimRB = false;
                        globalcontrol.fanpersonRB = true;
                    }
                }
                else //Fan is not activated
                {
                    globalcontrol.fanCheck = 0;
                    globalcontrol.fanCB = false;
                    globalcontrol.fanpredefRB = true;
                    globalcontrol.fanoptimRB = false;
                    globalcontrol.fanpersonRB = false;
                }
                //Datapackage
                datapackage.internalOpeningHeight = globalcontrol.intAreaHeight;
                datapackage.windowheight = globalcontrol.UpperHeight;
                datapackage.windowheightW = globalcontrol.windowheightB;
                datapackage.CdCheck = globalcontrol.cdCheck;
                datapackage.fanCheck = globalcontrol.fanCheck;
                datapackage.fana2 = globalcontrol.fana2;
                datapackage.fana1 = globalcontrol.fana1;
                datapackage.fana0 = globalcontrol.fana0;
                datapackage.fanb2 = globalcontrol.fanb2;
                datapackage.fanb1 = globalcontrol.fanb1;
                datapackage.fanD = globalcontrol.fanD;
                if (fanCheckBox.Checked) //Fan is activated
                {
                    if (fanPredefinedRadio.Checked)
                    {
                        if (fanOperatingSpeedCheck.Checked)
                        {
                            datapackage.gamma = (globalcontrol.fanrs) / (globalcontrol.fanOperSpeedTB);
                        }
                        else
                        {
                            datapackage.gamma = 1;
                        }
                    }
                }

                datapackage.Tfan = globalcontrol.fanTempSetPointTB;
                this.Close();
            }
        }
    }
}
