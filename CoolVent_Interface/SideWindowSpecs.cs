using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace CoolVent

{
	/// <summary>
	/// Summary description for Form1.
	/// </summary>
	public class WindowSpacingDlg : System.Windows.Forms.Form
    {
        private System.Windows.Forms.GroupBox inputsSingle;

        //inputsSingle fillers:
		private System.Windows.Forms.Label singleNumbOfWindowsLabel;
        private System.Windows.Forms.TextBox singleNumbofWindows;

        private System.Windows.Forms.Label lowerOpeningAreaLabel;
        private System.Windows.Forms.TextBox singleLowerOpeningArea;
        private System.Windows.Forms.Label lowerOpeningAreaUnits;

        private System.Windows.Forms.Label singleUpperOpeningAreaLabel;
        private System.Windows.Forms.TextBox singleUpperOpeningArea;
        private System.Windows.Forms.Label singleUpperOpeningAreaUnits;

        private System.Windows.Forms.Label deltaHLabel;
        private System.Windows.Forms.TextBox deltaH;
        private System.Windows.Forms.Label deltaHUnits;

        private System.Windows.Forms.Label singleGlazingAreaLabel;
        private System.Windows.Forms.TextBox singleGlazingArea;
        private System.Windows.Forms.Label singleGlazingAreaUnits;

        private System.Windows.Forms.Label upperOpeningHeightLabel;
        private System.Windows.Forms.TextBox upperOpeningHeight;
        private System.Windows.Forms.Label upperOpeningHeightUnits;

		private System.Windows.Forms.Button SpaceOKBut;
		private System.Windows.Forms.Button SpaceCancelBut;

        //inputs all fillers:
        private CheckBox doubleOpeningsCheck;
        private Label label1; //not needed.


		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.Container components = null;

		public WindowSpacingDlg()
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();
            InitialFormLoad();

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
            this.inputsSingle = new System.Windows.Forms.GroupBox();
            this.label1 = new System.Windows.Forms.Label();
            this.doubleOpeningsCheck = new System.Windows.Forms.CheckBox();
            this.singleNumbOfWindowsLabel = new System.Windows.Forms.Label();
            this.singleNumbofWindows = new System.Windows.Forms.TextBox();
            this.lowerOpeningAreaLabel = new System.Windows.Forms.Label();
            this.singleLowerOpeningArea = new System.Windows.Forms.TextBox();
            this.lowerOpeningAreaUnits = new System.Windows.Forms.Label();
            this.singleUpperOpeningAreaLabel = new System.Windows.Forms.Label();
            this.singleUpperOpeningArea = new System.Windows.Forms.TextBox();
            this.singleUpperOpeningAreaUnits = new System.Windows.Forms.Label();
            this.upperOpeningHeightLabel = new System.Windows.Forms.Label();
            this.upperOpeningHeight = new System.Windows.Forms.TextBox();
            this.upperOpeningHeightUnits = new System.Windows.Forms.Label();
            this.deltaHLabel = new System.Windows.Forms.Label();
            this.deltaH = new System.Windows.Forms.TextBox();
            this.deltaHUnits = new System.Windows.Forms.Label();
            this.singleGlazingAreaLabel = new System.Windows.Forms.Label();
            this.singleGlazingArea = new System.Windows.Forms.TextBox();
            this.singleGlazingAreaUnits = new System.Windows.Forms.Label();
            this.SpaceOKBut = new System.Windows.Forms.Button();
            this.SpaceCancelBut = new System.Windows.Forms.Button();
            this.inputsSingle.SuspendLayout();
            this.SuspendLayout();
            // 
            // inputsSingle
            // 
            this.inputsSingle.Controls.Add(this.label1);
            this.inputsSingle.Controls.Add(this.doubleOpeningsCheck);
            this.inputsSingle.Controls.Add(this.singleNumbOfWindowsLabel);
            this.inputsSingle.Controls.Add(this.singleNumbofWindows);
            this.inputsSingle.Controls.Add(this.lowerOpeningAreaLabel);
            this.inputsSingle.Controls.Add(this.singleLowerOpeningArea);
            this.inputsSingle.Controls.Add(this.lowerOpeningAreaUnits);
            this.inputsSingle.Controls.Add(this.singleUpperOpeningAreaLabel);
            this.inputsSingle.Controls.Add(this.singleUpperOpeningArea);
            this.inputsSingle.Controls.Add(this.singleUpperOpeningAreaUnits);
            this.inputsSingle.Controls.Add(this.upperOpeningHeightLabel);
            this.inputsSingle.Controls.Add(this.upperOpeningHeight);
            this.inputsSingle.Controls.Add(this.upperOpeningHeightUnits);
            this.inputsSingle.Controls.Add(this.deltaHLabel);
            this.inputsSingle.Controls.Add(this.deltaH);
            this.inputsSingle.Controls.Add(this.deltaHUnits);
            this.inputsSingle.Controls.Add(this.singleGlazingAreaLabel);
            this.inputsSingle.Controls.Add(this.singleGlazingArea);
            this.inputsSingle.Controls.Add(this.singleGlazingAreaUnits);
            this.inputsSingle.Location = new System.Drawing.Point(5, 329);
            this.inputsSingle.Name = "inputsSingle";
            this.inputsSingle.Size = new System.Drawing.Size(672, 171);
            this.inputsSingle.TabIndex = 99;
            this.inputsSingle.TabStop = false;
            // 
            // label1
            // 
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(302, 36);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(359, 30);
            this.label1.TabIndex = 18;
            this.label1.Text = "Area used to calculate solar heat gains (area through which sunlight enters the r" +
    "oom), can be modified to account for blinds or overhangs.";
            // 
            // doubleOpeningsCheck
            // 
            this.doubleOpeningsCheck.AutoSize = true;
            this.doubleOpeningsCheck.Checked = true;
            this.doubleOpeningsCheck.CheckState = System.Windows.Forms.CheckState.Checked;
            this.doubleOpeningsCheck.Location = new System.Drawing.Point(12, 85);
            this.doubleOpeningsCheck.Name = "doubleOpeningsCheck";
            this.doubleOpeningsCheck.Size = new System.Drawing.Size(260, 17);
            this.doubleOpeningsCheck.TabIndex = 17;
            this.doubleOpeningsCheck.Text = "Windows have double openings spaced vertically";
            this.doubleOpeningsCheck.UseVisualStyleBackColor = true;
            this.doubleOpeningsCheck.CheckedChanged += new System.EventHandler(this.doubleOpeningsCheck_CheckedChanged);
            // 
            // singleNumbOfWindowsLabel
            // 
            this.singleNumbOfWindowsLabel.BackColor = System.Drawing.Color.Transparent;
            this.singleNumbOfWindowsLabel.Location = new System.Drawing.Point(10, 15);
            this.singleNumbOfWindowsLabel.Name = "singleNumbOfWindowsLabel";
            this.singleNumbOfWindowsLabel.Size = new System.Drawing.Size(203, 19);
            this.singleNumbOfWindowsLabel.TabIndex = 16;
            this.singleNumbOfWindowsLabel.Text = "# of windows per floor on one elevation: ";
            // 
            // singleNumbofWindows
            // 
            this.singleNumbofWindows.Location = new System.Drawing.Point(224, 12);
            this.singleNumbofWindows.Name = "singleNumbofWindows";
            this.singleNumbofWindows.Size = new System.Drawing.Size(40, 20);
            this.singleNumbofWindows.TabIndex = 3;
            this.singleNumbofWindows.Tag = "";
            this.singleNumbofWindows.Text = "6";
            this.singleNumbofWindows.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // lowerOpeningAreaLabel
            // 
            this.lowerOpeningAreaLabel.Location = new System.Drawing.Point(377, 117);
            this.lowerOpeningAreaLabel.Name = "lowerOpeningAreaLabel";
            this.lowerOpeningAreaLabel.Size = new System.Drawing.Size(208, 22);
            this.lowerOpeningAreaLabel.TabIndex = 16;
            this.lowerOpeningAreaLabel.Text = "Area of each lower opening:";
            // 
            // singleLowerOpeningArea
            // 
            this.singleLowerOpeningArea.Location = new System.Drawing.Point(587, 114);
            this.singleLowerOpeningArea.Name = "singleLowerOpeningArea";
            this.singleLowerOpeningArea.Size = new System.Drawing.Size(40, 20);
            this.singleLowerOpeningArea.TabIndex = 3;
            this.singleLowerOpeningArea.Tag = "";
            this.singleLowerOpeningArea.Text = "0.2";
            this.singleLowerOpeningArea.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // lowerOpeningAreaUnits
            // 
            this.lowerOpeningAreaUnits.Location = new System.Drawing.Point(632, 117);
            this.lowerOpeningAreaUnits.Name = "lowerOpeningAreaUnits";
            this.lowerOpeningAreaUnits.Size = new System.Drawing.Size(34, 20);
            this.lowerOpeningAreaUnits.TabIndex = 16;
            this.lowerOpeningAreaUnits.Text = "m^2";
            // 
            // singleUpperOpeningAreaLabel
            // 
            this.singleUpperOpeningAreaLabel.Location = new System.Drawing.Point(6, 117);
            this.singleUpperOpeningAreaLabel.Name = "singleUpperOpeningAreaLabel";
            this.singleUpperOpeningAreaLabel.Size = new System.Drawing.Size(270, 20);
            this.singleUpperOpeningAreaLabel.TabIndex = 16;
            this.singleUpperOpeningAreaLabel.Text = "Total area of the openings in a single window:";
            // 
            // singleUpperOpeningArea
            // 
            this.singleUpperOpeningArea.Location = new System.Drawing.Point(289, 114);
            this.singleUpperOpeningArea.Name = "singleUpperOpeningArea";
            this.singleUpperOpeningArea.Size = new System.Drawing.Size(40, 20);
            this.singleUpperOpeningArea.TabIndex = 3;
            this.singleUpperOpeningArea.Tag = "";
            this.singleUpperOpeningArea.Text = "0.1";
            this.singleUpperOpeningArea.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // singleUpperOpeningAreaUnits
            // 
            this.singleUpperOpeningAreaUnits.Location = new System.Drawing.Point(332, 117);
            this.singleUpperOpeningAreaUnits.Name = "singleUpperOpeningAreaUnits";
            this.singleUpperOpeningAreaUnits.Size = new System.Drawing.Size(40, 20);
            this.singleUpperOpeningAreaUnits.TabIndex = 16;
            this.singleUpperOpeningAreaUnits.Text = "m^2";
            // 
            // upperOpeningHeightLabel
            // 
            this.upperOpeningHeightLabel.BackColor = System.Drawing.Color.Transparent;
            this.upperOpeningHeightLabel.Location = new System.Drawing.Point(6, 141);
            this.upperOpeningHeightLabel.Name = "upperOpeningHeightLabel";
            this.upperOpeningHeightLabel.Size = new System.Drawing.Size(277, 20);
            this.upperOpeningHeightLabel.TabIndex = 16;
            this.upperOpeningHeightLabel.Text = "Height from floor to center of upper opening (h-upper):";
            // 
            // upperOpeningHeight
            // 
            this.upperOpeningHeight.Location = new System.Drawing.Point(289, 138);
            this.upperOpeningHeight.Name = "upperOpeningHeight";
            this.upperOpeningHeight.Size = new System.Drawing.Size(40, 20);
            this.upperOpeningHeight.TabIndex = 3;
            this.upperOpeningHeight.Tag = "";
            this.upperOpeningHeight.Text = "0.5";
            this.upperOpeningHeight.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // upperOpeningHeightUnits
            // 
            this.upperOpeningHeightUnits.Location = new System.Drawing.Point(332, 141);
            this.upperOpeningHeightUnits.Name = "upperOpeningHeightUnits";
            this.upperOpeningHeightUnits.Size = new System.Drawing.Size(40, 20);
            this.upperOpeningHeightUnits.TabIndex = 16;
            this.upperOpeningHeightUnits.Text = "m";
            // 
            // deltaHLabel
            // 
            this.deltaHLabel.Location = new System.Drawing.Point(377, 141);
            this.deltaHLabel.Name = "deltaHLabel";
            this.deltaHLabel.Size = new System.Drawing.Size(209, 17);
            this.deltaHLabel.TabIndex = 16;
            this.deltaHLabel.Text = "Height between openings (delta-H):";
            // 
            // deltaH
            // 
            this.deltaH.Location = new System.Drawing.Point(586, 138);
            this.deltaH.Name = "deltaH";
            this.deltaH.Size = new System.Drawing.Size(40, 20);
            this.deltaH.TabIndex = 3;
            this.deltaH.Tag = "";
            this.deltaH.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // deltaHUnits
            // 
            this.deltaHUnits.Location = new System.Drawing.Point(632, 141);
            this.deltaHUnits.Name = "deltaHUnits";
            this.deltaHUnits.Size = new System.Drawing.Size(34, 20);
            this.deltaHUnits.TabIndex = 16;
            this.deltaHUnits.Text = "m";
            // 
            // singleGlazingAreaLabel
            // 
            this.singleGlazingAreaLabel.Location = new System.Drawing.Point(10, 40);
            this.singleGlazingAreaLabel.Name = "singleGlazingAreaLabel";
            this.singleGlazingAreaLabel.Size = new System.Drawing.Size(208, 20);
            this.singleGlazingAreaLabel.TabIndex = 16;
            this.singleGlazingAreaLabel.Text = "Area of each window frame (glazing):";
            // 
            // singleGlazingArea
            // 
            this.singleGlazingArea.Location = new System.Drawing.Point(224, 37);
            this.singleGlazingArea.Name = "singleGlazingArea";
            this.singleGlazingArea.Size = new System.Drawing.Size(40, 20);
            this.singleGlazingArea.TabIndex = 3;
            this.singleGlazingArea.Tag = "";
            this.singleGlazingArea.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // singleGlazingAreaUnits
            // 
            this.singleGlazingAreaUnits.Location = new System.Drawing.Point(270, 40);
            this.singleGlazingAreaUnits.Name = "singleGlazingAreaUnits";
            this.singleGlazingAreaUnits.Size = new System.Drawing.Size(40, 20);
            this.singleGlazingAreaUnits.TabIndex = 16;
            this.singleGlazingAreaUnits.Text = "m^2";
            // 
            // SpaceOKBut
            // 
            this.SpaceOKBut.Location = new System.Drawing.Point(511, 506);
            this.SpaceOKBut.Name = "SpaceOKBut";
            this.SpaceOKBut.Size = new System.Drawing.Size(80, 20);
            this.SpaceOKBut.TabIndex = 18;
            this.SpaceOKBut.Text = "OK";
            this.SpaceOKBut.Click += new System.EventHandler(this.SpaceOKBut_Click);
            // 
            // SpaceCancelBut
            // 
            this.SpaceCancelBut.Location = new System.Drawing.Point(597, 506);
            this.SpaceCancelBut.Name = "SpaceCancelBut";
            this.SpaceCancelBut.Size = new System.Drawing.Size(80, 20);
            this.SpaceCancelBut.TabIndex = 19;
            this.SpaceCancelBut.Text = "Cancel";
            this.SpaceCancelBut.Click += new System.EventHandler(this.SpaceCancelBut_Click);
            // 
            // WindowSpacingDlg
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(689, 538);
            this.Controls.Add(this.SpaceCancelBut);
            this.Controls.Add(this.SpaceOKBut);
            this.Controls.Add(this.inputsSingle);
            this.Name = "WindowSpacingDlg";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Side windows area calculator";
            this.Load += new System.EventHandler(this.WindowSpacingDlg_Load);
            this.inputsSingle.ResumeLayout(false);
            this.inputsSingle.PerformLayout();
            this.ResumeLayout(false);

		}
		#endregion

        private void InitialFormLoad()
        {
            this.doubleOpeningsCheck.Checked = false;
            if (datapackage.bldgtype == 3)
            {
                this.doubleOpeningsCheck.Visible = false;
                this.doubleOpeningsCheck.Checked = true;
            }
            else
            {
                this.doubleOpeningsCheck.Visible = true;
            }

            this.deltaH.Text = datapackage.deltaH + "";

            //if (globalcontrol.NumofSideWindows != 0)
            //{
            //    this.singleGlazingArea.Text = globalcontrol.singleGlazingArea.ToString(); // (datapackage.sideglazingarea / globalcontrol.NumofSideWindows) + "";
            //    this.upperOpeningHeight.Text = datapackage.windowheight + "";
            //    this.singleNumbofWindows.Text = String.Format("{0:3}", globalcontrol.NumofSideWindows);

            //    if (datapackage.bldgtype == 3)
            //    {
            //        this.singleLowerOpeningArea.Text = globalcontrol.singleLowerOpeningArea.ToString(); // (datapackage.loweropeningarea / globalcontrol.NumofSideWindows) + "";
            //    }
            //    else
            //    {
            //        this.singleLowerOpeningArea.Text = "0";
            //    }

            //}

        }

		private void WindowSpacingDlg_Load(object sender, System.EventArgs e)
		{
            globalcontrol.sidewindowspaceupdateflag = false;

            this.doubleOpeningsCheck.Checked = globalcontrol.doubleOpeningsCheck;

            if (datapackage.bldgtype == 3)
            {
                this.doubleOpeningsCheck.Visible = false;
                this.doubleOpeningsCheck.Checked = true;
            }
            else
            {
                this.doubleOpeningsCheck.Visible = true;
            }

            //**********************UNITS***************************
            double length_conv = 1; //m to m
            double area_conv = 1; //m to m2

            if (globalcontrol.units == 1)
            {
                length_conv = 0.3048; //ft to m
                area_conv = 0.3048 * 0.3048; //ft2 to m2
                upperOpeningHeightUnits.Text = "ft";
                deltaHUnits.Text = "ft";
                singleUpperOpeningAreaUnits.Text = "ft^2";
                lowerOpeningAreaUnits.Text = "ft^2";
                singleGlazingAreaUnits.Text = "ft^2";
            }
            //******************************************************

            //this.singleNumbofWindows.Text = globalcontrol.NumofSideWindows.ToString();

            //if (globalcontrol.NumofSideWindows != 0)
            //{
            //    globalcontrol.singleGlazingArea = datapackage.sideglazingarea / globalcontrol.NumofSideWindows;

            //    if (datapackage.bldgtype == 3)
            //    {
            //        globalcontrol.singleUpperOpeningArea = datapackage.upperopeningarea / globalcontrol.NumofSideWindows;
            //        globalcontrol.singleLowerOpeningArea = datapackage.loweropeningarea / globalcontrol.NumofSideWindows; //if it's not bldgtype == 3 then singleUpperOpeningArea and singleLowerOpeningArea are simply what is stored.
            //    }
            //    else
            //    {
            //        globalcontrol.singleUpperOpeningArea = datapackage.sidewindowsize / globalcontrol.NumofSideWindows;
            //    }
            //}

            this.deltaH.Text = String.Format("{0:F1}", datapackage.deltaH / length_conv); // = 2.5;
            //this.singleGlazingArea.Text = String.Format("{0:F1}", globalcontrol.singleGlazingArea / area_conv);
            this.upperOpeningHeight.Text = String.Format("{0:F1}", datapackage.windowheight / length_conv);
            this.singleLowerOpeningArea.Text = String.Format("{0:F1}", globalcontrol.singleLowerOpeningArea / area_conv); // = 0.2/conv;
            this.singleUpperOpeningArea.Text = String.Format("{0:F1}", globalcontrol.singleUpperOpeningArea / area_conv); //= 0.1/conv;
		}

		private void SpaceOKBut_Click(object sender, System.EventArgs e)
		{

            Boolean inputsOkay = true;
            double no = 0;
            double oa = 0;
            double sga = 0;
            double singleAL = 0;
            double singleAU = 0;
            double singleNW = 0;
            double singleHL = 0;
            double singleD = 0;
            try
            {
                no = Double.Parse(singleNumbofWindows.Text);
                oa = Double.Parse(singleLowerOpeningArea.Text) + Double.Parse(singleUpperOpeningArea.Text);
                sga = Double.Parse(singleGlazingArea.Text);
                singleAL = Double.Parse(singleLowerOpeningArea.Text);
                singleAU = Double.Parse(singleUpperOpeningArea.Text);
                singleNW = Double.Parse(singleNumbofWindows.Text);
                singleHL = Double.Parse(upperOpeningHeight.Text);
                singleD = Double.Parse(deltaH.Text);
                
            }
            catch (SystemException)
            {
                inputsOkay = false;
            }

            if (oa < 0 || no < 0 || sga<0 || singleAL<0 || singleAU<0 || singleNW<0 || singleHL<0||singleD<0)
            {
                inputsOkay = false;
            }
            if (!inputsOkay)
            {
                DialogResult res = MessageBox.Show("Some of your inputs are invalid!  Area and number inputs should be non-negative numbers. Are you sure you want to continue?", "Warning!", MessageBoxButtons.YesNo, MessageBoxIcon.Question);
                if (res == DialogResult.No)
                {
                    return;
                }
            }

            globalcontrol.sidewindowspaceupdateflag = true;
            globalcontrol.doubleOpeningsCheck = doubleOpeningsCheck.Checked;
            
            //**********************UNITS***************************
            double length_conv = 1; //m to m
            double area_conv = 1; //ft2 to m2            
            if (globalcontrol.units == 1)
            {
                length_conv = 0.3048; //ft to m
                area_conv = 0.3048 * 0.3048; //ft2 to m2
            }
            //******************************************************

            //globalcontrol.NumofSideWindows = Int32.Parse(this.singleNumbofWindows.Text);
            //globalcontrol.singleGlazingArea = Double.Parse(this.singleGlazingArea.Text) * area_conv;

            //globalcontrol.singleUpperOpeningArea = Double.Parse(this.singleUpperOpeningArea.Text) * area_conv;
            //globalcontrol.singleLowerOpeningArea = Double.Parse(this.singleLowerOpeningArea.Text) * area_conv;

            //datapackage.windowheight = Double.Parse(upperOpeningHeight.Text) * length_conv;
            //datapackage.sideglazingarea = globalcontrol.NumofSideWindows * globalcontrol.singleGlazingArea * area_conv;
            //datapackage.deltaH = Double.Parse(deltaH.Text) * length_conv;

            ////if (datapackage.bldgtype == 3)
            ////{
            //    datapackage.upperopeningarea = globalcontrol.NumofSideWindows * globalcontrol.singleUpperOpeningArea * area_conv;
            //    datapackage.loweropeningarea = globalcontrol.NumofSideWindows * globalcontrol.singleLowerOpeningArea * area_conv;
            ////}
            //if (doubleOpeningsCheck.Checked == true) // not bldg 3 but with two windows
            //{
            //    datapackage.sidewindowsize = globalcontrol.NumofSideWindows * (globalcontrol.singleUpperOpeningArea + globalcontrol.singleLowerOpeningArea) * area_conv;
            //}
            //else
            //    datapackage.sidewindowsize = Double.Parse(this.singleNumbofWindows.Text) * (Double.Parse(singleUpperOpeningArea.Text)) * area_conv;

		
            //this.Close();
		}

		private void SpaceCancelBut_Click(object sender, System.EventArgs e)
		{
			this.Close();
		}

        private void doubleOpeningsCheck_CheckedChanged(object sender, EventArgs e)
        {
            if (this.doubleOpeningsCheck.Checked == true)
            {
                this.lowerOpeningAreaLabel.Enabled = true;
                this.lowerOpeningAreaUnits.Enabled = true;
                this.singleLowerOpeningArea.Enabled = true;
                this.deltaHLabel.Enabled = true;
                this.deltaHUnits.Enabled = true;
                this.deltaH.Enabled = true;
                this.singleUpperOpeningAreaLabel.Text = "Area of each individual upper opening:";
                this.upperOpeningHeightLabel.Text = "Height from floor to center of upper opening (h-upper):";
            }
            else
            {
                this.lowerOpeningAreaLabel.Enabled = false;
                this.lowerOpeningAreaUnits.Enabled = false;
                this.singleLowerOpeningArea.Enabled = false;
                this.deltaHLabel.Enabled = false;
                this.deltaHUnits.Enabled = false;
                this.deltaH.Enabled = false;
                this.singleUpperOpeningAreaLabel.Text = "Total area of the openings in a single window:";
                this.upperOpeningHeightLabel.Text = "Height from floor to center of opening (h):";
            }
        }
	}
}
