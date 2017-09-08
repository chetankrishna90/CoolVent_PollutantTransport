namespace CoolVent
{
    partial class HybridSpecs
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(HybridSpecs));
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.fanOptimumSpeedCheck = new System.Windows.Forms.CheckBox();
            this.label20 = new System.Windows.Forms.Label();
            this.label19 = new System.Windows.Forms.Label();
            this.label18 = new System.Windows.Forms.Label();
            this.label17 = new System.Windows.Forms.Label();
            this.label16 = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.trackBar1 = new System.Windows.Forms.TrackBar();
            this.label13 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.fanOperSpeedTextBox = new System.Windows.Forms.TextBox();
            this.predefinedMediumRB = new System.Windows.Forms.RadioButton();
            this.predefinedLightRB = new System.Windows.Forms.RadioButton();
            this.label8 = new System.Windows.Forms.Label();
            this.fanCurveButton = new System.Windows.Forms.Button();
            this.predefinedHeavyRB = new System.Windows.Forms.RadioButton();
            this.fanPersonalizedRadio = new System.Windows.Forms.RadioButton();
            this.peronalizedButton = new System.Windows.Forms.Button();
            this.fanPredefinedRadio = new System.Windows.Forms.RadioButton();
            this.label12 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.label3 = new System.Windows.Forms.Label();
            this.COPTextBox = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.okButton = new System.Windows.Forms.Button();
            this.cancelButton = new System.Windows.Forms.Button();
            this.hrControlCB = new System.Windows.Forms.CheckBox();
            this.groupBox1.SuspendLayout();
            this.panel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.trackBar1)).BeginInit();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.panel1);
            this.groupBox1.Controls.Add(this.fanPersonalizedRadio);
            this.groupBox1.Controls.Add(this.peronalizedButton);
            this.groupBox1.Controls.Add(this.fanPredefinedRadio);
            this.groupBox1.Controls.Add(this.label12);
            this.groupBox1.Controls.Add(this.label11);
            this.groupBox1.Controls.Add(this.label10);
            this.groupBox1.Location = new System.Drawing.Point(20, 21);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(678, 494);
            this.groupBox1.TabIndex = 106;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Fan Specification";
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.fanOptimumSpeedCheck);
            this.panel1.Controls.Add(this.label20);
            this.panel1.Controls.Add(this.label19);
            this.panel1.Controls.Add(this.label18);
            this.panel1.Controls.Add(this.label17);
            this.panel1.Controls.Add(this.label16);
            this.panel1.Controls.Add(this.label15);
            this.panel1.Controls.Add(this.trackBar1);
            this.panel1.Controls.Add(this.label13);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.fanOperSpeedTextBox);
            this.panel1.Controls.Add(this.predefinedMediumRB);
            this.panel1.Controls.Add(this.predefinedLightRB);
            this.panel1.Controls.Add(this.label8);
            this.panel1.Controls.Add(this.fanCurveButton);
            this.panel1.Controls.Add(this.predefinedHeavyRB);
            this.panel1.Location = new System.Drawing.Point(53, 189);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(608, 232);
            this.panel1.TabIndex = 131;
            // 
            // fanOptimumSpeedCheck
            // 
            this.fanOptimumSpeedCheck.Location = new System.Drawing.Point(32, 189);
            this.fanOptimumSpeedCheck.Name = "fanOptimumSpeedCheck";
            this.fanOptimumSpeedCheck.Size = new System.Drawing.Size(559, 41);
            this.fanOptimumSpeedCheck.TabIndex = 142;
            this.fanOptimumSpeedCheck.Text = "Let CoolVent change the speed of the fan every hour. Fan operates at lowest possi" +
    "ble speed that keeps the temperature of any internal zone below the temperature " +
    "setpoint.";
            this.fanOptimumSpeedCheck.UseVisualStyleBackColor = true;
            this.fanOptimumSpeedCheck.CheckedChanged += new System.EventHandler(this.fanOperatingSpeedCheck_CheckedChanged);
            // 
            // label20
            // 
            this.label20.Location = new System.Drawing.Point(264, 135);
            this.label20.Name = "label20";
            this.label20.Size = new System.Drawing.Size(32, 23);
            this.label20.TabIndex = 141;
            this.label20.Text = "30%";
            // 
            // label19
            // 
            this.label19.Location = new System.Drawing.Point(394, 135);
            this.label19.Name = "label19";
            this.label19.Size = new System.Drawing.Size(32, 23);
            this.label19.TabIndex = 140;
            this.label19.Text = "70%";
            // 
            // label18
            // 
            this.label18.Location = new System.Drawing.Point(552, 135);
            this.label18.Name = "label18";
            this.label18.Size = new System.Drawing.Size(37, 23);
            this.label18.TabIndex = 138;
            this.label18.Text = "120%";
            // 
            // label17
            // 
            this.label17.Location = new System.Drawing.Point(487, 135);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(41, 23);
            this.label17.TabIndex = 139;
            this.label17.Text = "100%";
            // 
            // label16
            // 
            this.label16.Location = new System.Drawing.Point(327, 135);
            this.label16.Name = "label16";
            this.label16.Size = new System.Drawing.Size(32, 23);
            this.label16.TabIndex = 138;
            this.label16.Text = "50%";
            // 
            // label15
            // 
            this.label15.Location = new System.Drawing.Point(198, 135);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(32, 23);
            this.label15.TabIndex = 137;
            this.label15.Text = "10%";
            // 
            // trackBar1
            // 
            this.trackBar1.Location = new System.Drawing.Point(196, 105);
            this.trackBar1.Maximum = 120;
            this.trackBar1.Minimum = 10;
            this.trackBar1.Name = "trackBar1";
            this.trackBar1.Size = new System.Drawing.Size(384, 45);
            this.trackBar1.TabIndex = 136;
            this.trackBar1.TickFrequency = 10;
            this.trackBar1.Value = 100;
            this.trackBar1.Scroll += new System.EventHandler(this.trackBar1_Scroll);
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(81, 157);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(86, 13);
            this.label13.TabIndex = 135;
            this.label13.Text = "% of rated speed";
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(29, 96);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(158, 56);
            this.label1.TabIndex = 134;
            this.label1.Text = "Fan operating speed,\r\nas percentage of rated speed\r\n(between 10% and 120%):";
            // 
            // fanOperSpeedTextBox
            // 
            this.fanOperSpeedTextBox.Location = new System.Drawing.Point(32, 154);
            this.fanOperSpeedTextBox.Name = "fanOperSpeedTextBox";
            this.fanOperSpeedTextBox.Size = new System.Drawing.Size(43, 20);
            this.fanOperSpeedTextBox.TabIndex = 133;
            this.fanOperSpeedTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.fanOperSpeedTextBox.TextChanged += new System.EventHandler(this.fanOperSpeedTextBox_TextChanged);
            // 
            // predefinedMediumRB
            // 
            this.predefinedMediumRB.AutoSize = true;
            this.predefinedMediumRB.Location = new System.Drawing.Point(10, 34);
            this.predefinedMediumRB.Name = "predefinedMediumRB";
            this.predefinedMediumRB.Size = new System.Drawing.Size(331, 17);
            this.predefinedMediumRB.TabIndex = 131;
            this.predefinedMediumRB.Text = "Use a predefined fan curve for a typical medium-duty exhaust fan";
            this.predefinedMediumRB.UseVisualStyleBackColor = true;
            this.predefinedMediumRB.CheckedChanged += new System.EventHandler(this.predefinedMediumRB_CheckedChanged);
            // 
            // predefinedLightRB
            // 
            this.predefinedLightRB.AutoSize = true;
            this.predefinedLightRB.Location = new System.Drawing.Point(10, 4);
            this.predefinedLightRB.Name = "predefinedLightRB";
            this.predefinedLightRB.Size = new System.Drawing.Size(314, 17);
            this.predefinedLightRB.TabIndex = 130;
            this.predefinedLightRB.Text = "Use a predefined fan curve for a typical light-duty exhaust fan";
            this.predefinedLightRB.UseVisualStyleBackColor = true;
            this.predefinedLightRB.CheckedChanged += new System.EventHandler(this.predefinedLightRB_CheckedChanged);
            // 
            // label8
            // 
            this.label8.Location = new System.Drawing.Point(415, 15);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(133, 36);
            this.label8.TabIndex = 129;
            this.label8.Text = "Predefined fan curve and additional information:";
            // 
            // fanCurveButton
            // 
            this.fanCurveButton.Enabled = false;
            this.fanCurveButton.Location = new System.Drawing.Point(433, 52);
            this.fanCurveButton.Name = "fanCurveButton";
            this.fanCurveButton.Size = new System.Drawing.Size(94, 23);
            this.fanCurveButton.TabIndex = 128;
            this.fanCurveButton.Text = "See fan curve";
            this.fanCurveButton.UseVisualStyleBackColor = true;
            this.fanCurveButton.Click += new System.EventHandler(this.fanCurveButton_Click);
            // 
            // predefinedHeavyRB
            // 
            this.predefinedHeavyRB.AutoSize = true;
            this.predefinedHeavyRB.Enabled = false;
            this.predefinedHeavyRB.Location = new System.Drawing.Point(10, 67);
            this.predefinedHeavyRB.Name = "predefinedHeavyRB";
            this.predefinedHeavyRB.Size = new System.Drawing.Size(324, 17);
            this.predefinedHeavyRB.TabIndex = 127;
            this.predefinedHeavyRB.Text = "Use a predefined fan curve for a typical heavy-duty exhaust fan";
            this.predefinedHeavyRB.UseVisualStyleBackColor = true;
            this.predefinedHeavyRB.CheckedChanged += new System.EventHandler(this.predefinedHeavyRB_CheckedChanged);
            // 
            // fanPersonalizedRadio
            // 
            this.fanPersonalizedRadio.AutoSize = true;
            this.fanPersonalizedRadio.Location = new System.Drawing.Point(33, 437);
            this.fanPersonalizedRadio.Name = "fanPersonalizedRadio";
            this.fanPersonalizedRadio.Size = new System.Drawing.Size(315, 17);
            this.fanPersonalizedRadio.TabIndex = 130;
            this.fanPersonalizedRadio.Text = "Use a personalized fan. Click in the \"Personalized fan\" button";
            this.fanPersonalizedRadio.UseVisualStyleBackColor = true;
            this.fanPersonalizedRadio.CheckedChanged += new System.EventHandler(this.fanPersonalizedRadio_CheckedChanged);
            // 
            // peronalizedButton
            // 
            this.peronalizedButton.Location = new System.Drawing.Point(81, 460);
            this.peronalizedButton.Name = "peronalizedButton";
            this.peronalizedButton.Size = new System.Drawing.Size(116, 23);
            this.peronalizedButton.TabIndex = 129;
            this.peronalizedButton.Text = "Personalized fan";
            this.peronalizedButton.UseVisualStyleBackColor = true;
            this.peronalizedButton.Click += new System.EventHandler(this.peronalizedButton_Click);
            // 
            // fanPredefinedRadio
            // 
            this.fanPredefinedRadio.Location = new System.Drawing.Point(33, 159);
            this.fanPredefinedRadio.Name = "fanPredefinedRadio";
            this.fanPredefinedRadio.Size = new System.Drawing.Size(271, 24);
            this.fanPredefinedRadio.TabIndex = 122;
            this.fanPredefinedRadio.Text = "Select one of the three predefined fans";
            this.fanPredefinedRadio.UseVisualStyleBackColor = true;
            this.fanPredefinedRadio.CheckedChanged += new System.EventHandler(this.fanPredefinedRadio_CheckedChanged);
            // 
            // label12
            // 
            this.label12.Location = new System.Drawing.Point(30, 132);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(382, 16);
            this.label12.TabIndex = 3;
            this.label12.Text = "Select a predefined fan, let CoolVent choose a fan or specify your own fan";
            // 
            // label11
            // 
            this.label11.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label11.Location = new System.Drawing.Point(30, 54);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(614, 78);
            this.label11.TabIndex = 2;
            this.label11.Text = resources.GetString("label11.Text");
            // 
            // label10
            // 
            this.label10.Location = new System.Drawing.Point(10, 28);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(651, 26);
            this.label10.TabIndex = 0;
            this.label10.Text = "This section allows you to include an exhaust fan at the atrium / chimney exhaust" +
    "";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.hrControlCB);
            this.groupBox2.Controls.Add(this.label3);
            this.groupBox2.Controls.Add(this.COPTextBox);
            this.groupBox2.Controls.Add(this.label2);
            this.groupBox2.Location = new System.Drawing.Point(20, 529);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(678, 86);
            this.groupBox2.TabIndex = 107;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Air Conditioning Specification";
            // 
            // label3
            // 
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(205, 31);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(113, 23);
            this.label3.TabIndex = 2;
            this.label3.Text = "Default COP is 3";
            // 
            // COPTextBox
            // 
            this.COPTextBox.Location = new System.Drawing.Point(134, 28);
            this.COPTextBox.Name = "COPTextBox";
            this.COPTextBox.Size = new System.Drawing.Size(43, 20);
            this.COPTextBox.TabIndex = 1;
            this.COPTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.COPTextBox.TextChanged += new System.EventHandler(this.COPTextBox_TextChanged);
            // 
            // label2
            // 
            this.label2.Location = new System.Drawing.Point(10, 31);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(118, 23);
            this.label2.TabIndex = 0;
            this.label2.Text = "Air conditioning COP:";
            // 
            // okButton
            // 
            this.okButton.Location = new System.Drawing.Point(531, 621);
            this.okButton.Name = "okButton";
            this.okButton.Size = new System.Drawing.Size(75, 23);
            this.okButton.TabIndex = 108;
            this.okButton.Text = "OK";
            this.okButton.UseVisualStyleBackColor = true;
            this.okButton.Click += new System.EventHandler(this.hybridOKBut_Click);
            // 
            // cancelButton
            // 
            this.cancelButton.Location = new System.Drawing.Point(623, 621);
            this.cancelButton.Name = "cancelButton";
            this.cancelButton.Size = new System.Drawing.Size(75, 23);
            this.cancelButton.TabIndex = 109;
            this.cancelButton.Text = "Cancel";
            this.cancelButton.UseVisualStyleBackColor = true;
            this.cancelButton.Click += new System.EventHandler(this.hybridCancelBut_Click);
            // 
            // hrControlCB
            // 
            this.hrControlCB.AutoSize = true;
            this.hrControlCB.Checked = true;
            this.hrControlCB.CheckState = System.Windows.Forms.CheckState.Checked;
            this.hrControlCB.Location = new System.Drawing.Point(13, 58);
            this.hrControlCB.Name = "hrControlCB";
            this.hrControlCB.Size = new System.Drawing.Size(254, 17);
            this.hrControlCB.TabIndex = 3;
            this.hrControlCB.Text = "Include humidity control in AC energy calculation";
            this.hrControlCB.UseVisualStyleBackColor = true;
            // 
            // HybridSpecs
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(713, 656);
            this.Controls.Add(this.cancelButton);
            this.Controls.Add(this.okButton);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.groupBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "HybridSpecs";
            this.Text = "Hybrid Ventilation Specifications";
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.trackBar1)).EndInit();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label20;
        private System.Windows.Forms.Label label19;
        private System.Windows.Forms.Label label18;
        private System.Windows.Forms.Label label17;
        private System.Windows.Forms.Label label16;
        private System.Windows.Forms.Label label15;
        private System.Windows.Forms.TrackBar trackBar1;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox fanOperSpeedTextBox;
        private System.Windows.Forms.RadioButton predefinedMediumRB;
        private System.Windows.Forms.RadioButton predefinedLightRB;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Button fanCurveButton;
        private System.Windows.Forms.RadioButton predefinedHeavyRB;
        private System.Windows.Forms.RadioButton fanPersonalizedRadio;
        private System.Windows.Forms.Button peronalizedButton;
        private System.Windows.Forms.RadioButton fanPredefinedRadio;
        private System.Windows.Forms.Label label12;
        private System.Windows.Forms.Label label11;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.TextBox COPTextBox;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.CheckBox fanOptimumSpeedCheck;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.CheckBox hrControlCB;
    }
}