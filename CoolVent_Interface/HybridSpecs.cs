using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace CoolVent
{
    public partial class HybridSpecs : Form
    {
        public HybridSpecs()
        {
            InitializeComponent();
            InitialFormLoad();
        }

        private void InitialFormLoad()
        {
            //Load data from globalcontrol
            this.fanPredefinedRadio.Checked = (globalcontrol.fanpredefRB);
            this.fanPersonalizedRadio.Checked = (globalcontrol.fanpersonRB);
            this.COPTextBox.Text = (globalcontrol.acCOP).ToString("0.0");

            if (globalcontrol.fanCheck == 1)
            {
                this.predefinedLightRB.Checked = true;
                this.predefinedMediumRB.Checked = false;
                this.predefinedHeavyRB.Checked = false;
            }
            else if (globalcontrol.fanCheck == 2 || globalcontrol.fanCheck == 0 || globalcontrol.fanCheck == 5)
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
            this.fanOperSpeedTextBox.Text = (globalcontrol.fanOperSpeedTB).ToString("00");
            this.trackBar1.Value = Convert.ToInt32(globalcontrol.fanOperSpeedTB);

            /* Fan */
            if (globalcontrol.fanCB) //Activate fan options
            {
                this.fanPredefinedRadio.Enabled = true;
                this.fanPersonalizedRadio.Enabled = true;

                //Predefined fan
                if (globalcontrol.fanpredefRB)
                {
                    //Activate the predefined fan RBs
                    this.predefinedLightRB.Enabled = true;
                    this.predefinedMediumRB.Enabled = true;
                    this.predefinedHeavyRB.Enabled = true;
                    //Activate fan curve button
                    this.fanCurveButton.Enabled = true;
                    //Deactivate personal fans
                    this.fanPersonalizedRadio.Checked = false;
                    this.peronalizedButton.Enabled = false;
                    //Activate fan operating speed control
                    this.fanOptimumSpeedCheck.Enabled = true;
                    if (globalcontrol.fanOpSpeedCB) //If fanOpSeedCB is true then CV can change the speed but user cannot
                    {
                        this.fanOperSpeedTextBox.Enabled = false;
                        this.trackBar1.Enabled = false;
                    }
                    else //Let the user change the fan speed
                    {
                        this.fanOperSpeedTextBox.Enabled = true;
                        this.trackBar1.Enabled = true;
                    }
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
                    this.fanOptimumSpeedCheck.Enabled = false;
                    this.fanOperSpeedTextBox.Enabled = false;
                    this.trackBar1.Enabled = false;
                }
            }
            else //Deactivate fan options
            {
                this.fanPredefinedRadio.Enabled = false;
                this.predefinedLightRB.Enabled = false;
                this.predefinedMediumRB.Enabled = false;
                this.predefinedHeavyRB.Enabled = false;
                this.fanCurveButton.Enabled = false;
                this.fanOptimumSpeedCheck.Enabled = false;
                this.fanOperSpeedTextBox.Enabled = false;
                this.trackBar1.Enabled = false;
                this.fanPersonalizedRadio.Enabled = false;
                this.peronalizedButton.Enabled = false;
            }

            /* AVC */
            if (globalcontrol.acCB) //Activate fan options
            {
                this.COPTextBox.Enabled = true;
            }
            else //Deactivate fan options
            {
                this.COPTextBox.Enabled = false;
            }
        }

        private void fanPredefinedRadio_CheckedChanged(object sender, EventArgs e)
        {
            if (fanPredefinedRadio.Checked) //Activate predefined options
            {
                this.predefinedLightRB.Enabled = true;
                this.predefinedMediumRB.Enabled = true;
                this.predefinedHeavyRB.Enabled = true;
                this.fanCurveButton.Enabled = true;
                this.fanOptimumSpeedCheck.Enabled = true;
                if (fanOptimumSpeedCheck.Checked)
                {
                    this.fanOperSpeedTextBox.Enabled = false;
                    this.trackBar1.Enabled = false;
                }
                else
                {
                    this.fanOperSpeedTextBox.Enabled = true;
                    this.trackBar1.Enabled = true;
                }
                //Deactivate personalized fan options
                this.fanPersonalizedRadio.Checked = false;
            }
            else
            {
                this.predefinedLightRB.Enabled = false;
                this.predefinedMediumRB.Enabled = false;
                this.predefinedHeavyRB.Enabled = false;
                this.fanCurveButton.Enabled = false;
                this.fanOptimumSpeedCheck.Enabled = false;
                this.fanOperSpeedTextBox.Enabled = false;
                this.trackBar1.Enabled = false;
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
            if (predefinedMediumRB.Checked)
            {
                this.predefinedLightRB.Checked = false;
                this.predefinedHeavyRB.Checked = false;
            }
        }

        private void predefinedHeavyRB_CheckedChanged(object sender, EventArgs e) //Heavy-duty
        {
            if (predefinedHeavyRB.Checked)
            {
                this.predefinedLightRB.Checked = false;
                this.predefinedMediumRB.Checked = false;
            }
        }

        private void fanOperatingSpeedCheck_CheckedChanged(object sender, EventArgs e) //Operating speed
        {
            if (fanOptimumSpeedCheck.Checked)
            {
                this.fanOperSpeedTextBox.Enabled = false;
                this.trackBar1.Enabled = false;
            }
            else
            {
                this.fanOperSpeedTextBox.Enabled = true;
                this.trackBar1.Enabled = true;
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

        private void trackBar1_Scroll(object sender, EventArgs e)
        {
            this.fanOperSpeedTextBox.Text = (trackBar1.Value).ToString("00");
        }

        private void fanOperSpeedTextBox_TextChanged(object sender, EventArgs e)
        {
            try
            {
                int temp = int.Parse(this.fanOperSpeedTextBox.Text);
                if (temp >= 10 && temp <= 120)
                {
                    this.trackBar1.Value = temp;
                    this.fanOperSpeedTextBox.BackColor = Color.White;
                }
                else
                {
                    throw new System.SystemException();
                }

            }
            catch (SystemException)
            {
                this.fanOperSpeedTextBox.BackColor = Color.PaleVioletRed;
            }
        }

        private void COPTextBox_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (double.Parse(this.COPTextBox.Text) > 0)
                {
                    this.COPTextBox.BackColor = Color.White;
                }
                else
                {
                    throw new System.SystemException();
                }

            }
            catch (SystemException)
            {
                this.COPTextBox.BackColor = Color.PaleVioletRed;
            }
        }

        private void hybridCancelBut_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void hybridOKBut_Click(object sender, EventArgs e)
        {
            bool inputsOkay = true; //FADE: This one checks that all the numbers make sense
            double fanos = 100; //FADE: the gamma value of the fan (ie the nondimensional fan speed)
            double COP = 3;

            //FADE: Check inputs
            try
            {
                if (fanPredefinedRadio.Checked && !fanOptimumSpeedCheck.Checked) //FADE: User-defined speed
                {
                    fanos = Double.Parse(this.fanOperSpeedTextBox.Text);
                }
                if (fanPersonalizedRadio.Checked)
                {
                    //Call info from globalcontrol
                }
                if (globalcontrol.acCB)
                {
                    COP = Double.Parse(this.COPTextBox.Text);
                }
            }
            catch (SystemException)
            {
                inputsOkay = false;
            }

            if (fanos < 10 || fanos > 120 || COP <= 0)
            {
                inputsOkay = false;
            }
            if (!inputsOkay)
            {
                DialogResult res = MessageBox.Show("Some of your inputs are invalid! Fan speed should be between 10% and 120% of the rated speed./nCOP should be larger than zero", "Error!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                if (res == DialogResult.OK)
                {
                    return;
                }
            }
            else
            {
                if (fanPredefinedRadio.Checked)
                {
                    if (predefinedLightRB.Checked)
                    {
                        globalcontrol.fanCheck = 1;
                        globalcontrol.fanpredefRB = true;
                        globalcontrol.fanpersonRB = false;
                    }
                    else if (predefinedMediumRB.Checked)
                    {
                        globalcontrol.fanCheck = 2;
                        globalcontrol.fanpredefRB = true;
                        globalcontrol.fanpersonRB = false;
                    }
                    else if (predefinedHeavyRB.Checked)
                    {
                        globalcontrol.fanCheck = 3;
                        globalcontrol.fanpredefRB = true;
                        globalcontrol.fanpersonRB = false;
                    }
                    if (fanOptimumSpeedCheck.Checked)
                    {
                        globalcontrol.fanOpSpeedCB = true;
                    }
                    else
                    {
                        globalcontrol.fanOperSpeedTB = fanos;
                        globalcontrol.fanOpSpeedCB = false;
                    }
                }
                else if (fanPersonalizedRadio.Checked)
                {
                    globalcontrol.fanCheck = 5;
                    globalcontrol.fanpredefRB = false;
                    globalcontrol.fanpersonRB = true;
                }
            }

            //Datapackage
            datapackage.fanCheck = globalcontrol.fanCheck;
            datapackage.fana2 = globalcontrol.fana2;
            datapackage.fana1 = globalcontrol.fana1;
            datapackage.fana0 = globalcontrol.fana0;
            datapackage.fanb2 = globalcontrol.fanb2;
            datapackage.fanb1 = globalcontrol.fanb1;
            datapackage.fanD = globalcontrol.fanD;

            if (fanPredefinedRadio.Checked)
            {
                if (fanOptimumSpeedCheck.Checked)
                {
                    datapackage.gamma = 1;
                }
                else
                {
                    datapackage.gamma = 1 / (globalcontrol.fanOperSpeedTB / 100);
                }
            }

            if (globalcontrol.acCB)
            {
                globalcontrol.acCOP = COP;
                datapackage.acCOP = globalcontrol.acCOP;
            }
            this.Close();
        }
    }

}
