namespace CoolVent
{
    partial class intOpeningSpecsWindow
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(intOpeningSpecsWindow));
            this.intOpeningGroup = new System.Windows.Forms.GroupBox();
            this.optionP = new System.Windows.Forms.Panel();
            this.shaft2P = new System.Windows.Forms.Panel();
            this.label1 = new System.Windows.Forms.Label();
            this.units2LB = new System.Windows.Forms.Label();
            this.shaft2InletHeightTB = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.intAreaHeight = new System.Windows.Forms.TextBox();
            this.units1LB = new System.Windows.Forms.Label();
            this.HelpButton = new System.Windows.Forms.Button();
            this.difHeight = new System.Windows.Forms.CheckBox();
            this.label6 = new System.Windows.Forms.Label();
            this.intOpeningCancelBut = new System.Windows.Forms.Button();
            this.intOpeningOKBut = new System.Windows.Forms.Button();
            this.intOpeningGroup.SuspendLayout();
            this.optionP.SuspendLayout();
            this.shaft2P.SuspendLayout();
            this.SuspendLayout();
            // 
            // intOpeningGroup
            // 
            this.intOpeningGroup.Controls.Add(this.optionP);
            this.intOpeningGroup.Controls.Add(this.HelpButton);
            this.intOpeningGroup.Controls.Add(this.difHeight);
            this.intOpeningGroup.Controls.Add(this.label6);
            this.intOpeningGroup.Location = new System.Drawing.Point(25, 26);
            this.intOpeningGroup.Name = "intOpeningGroup";
            this.intOpeningGroup.Size = new System.Drawing.Size(597, 169);
            this.intOpeningGroup.TabIndex = 103;
            this.intOpeningGroup.TabStop = false;
            this.intOpeningGroup.Text = "Internal opening specification";
            // 
            // optionP
            // 
            this.optionP.Controls.Add(this.shaft2P);
            this.optionP.Controls.Add(this.label5);
            this.optionP.Controls.Add(this.intAreaHeight);
            this.optionP.Controls.Add(this.units1LB);
            this.optionP.Enabled = false;
            this.optionP.Location = new System.Drawing.Point(39, 81);
            this.optionP.Name = "optionP";
            this.optionP.Size = new System.Drawing.Size(353, 77);
            this.optionP.TabIndex = 111;
            // 
            // shaft2P
            // 
            this.shaft2P.Controls.Add(this.label1);
            this.shaft2P.Controls.Add(this.units2LB);
            this.shaft2P.Controls.Add(this.shaft2InletHeightTB);
            this.shaft2P.Enabled = false;
            this.shaft2P.Location = new System.Drawing.Point(13, 36);
            this.shaft2P.Name = "shaft2P";
            this.shaft2P.Size = new System.Drawing.Size(290, 29);
            this.shaft2P.TabIndex = 114;
            this.shaft2P.Visible = false;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(6, 6);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(179, 13);
            this.label1.TabIndex = 111;
            this.label1.Text = "Second shaft inlet height (from floor):";
            // 
            // units2LB
            // 
            this.units2LB.AutoSize = true;
            this.units2LB.Location = new System.Drawing.Point(266, 6);
            this.units2LB.Name = "units2LB";
            this.units2LB.Size = new System.Drawing.Size(15, 13);
            this.units2LB.TabIndex = 113;
            this.units2LB.Text = "m";
            // 
            // shaft2InletHeightTB
            // 
            this.shaft2InletHeightTB.Location = new System.Drawing.Point(220, 3);
            this.shaft2InletHeightTB.Name = "shaft2InletHeightTB";
            this.shaft2InletHeightTB.Size = new System.Drawing.Size(40, 20);
            this.shaft2InletHeightTB.TabIndex = 112;
            this.shaft2InletHeightTB.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.shaft2InletHeightTB.TextChanged += new System.EventHandler(this.shaft2InletHeightTB_TextChanged);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(19, 13);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(170, 13);
            this.label5.TabIndex = 108;
            this.label5.Text = "Internal opening height (from floor):";
            // 
            // intAreaHeight
            // 
            this.intAreaHeight.Location = new System.Drawing.Point(233, 10);
            this.intAreaHeight.Name = "intAreaHeight";
            this.intAreaHeight.Size = new System.Drawing.Size(40, 20);
            this.intAreaHeight.TabIndex = 109;
            this.intAreaHeight.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.intAreaHeight.TextChanged += new System.EventHandler(this.intAreaHeight_TextChanged);
            // 
            // units1LB
            // 
            this.units1LB.AutoSize = true;
            this.units1LB.Location = new System.Drawing.Point(279, 13);
            this.units1LB.Name = "units1LB";
            this.units1LB.Size = new System.Drawing.Size(15, 13);
            this.units1LB.TabIndex = 110;
            this.units1LB.Text = "m";
            // 
            // HelpButton
            // 
            this.HelpButton.Image = global::CoolVent.Properties.Resources.help;
            this.HelpButton.Location = new System.Drawing.Point(470, 106);
            this.HelpButton.Name = "HelpButton";
            this.HelpButton.Size = new System.Drawing.Size(30, 30);
            this.HelpButton.TabIndex = 106;
            this.HelpButton.Click += new System.EventHandler(this.HelpButton_Click);
            // 
            // difHeight
            // 
            this.difHeight.AutoSize = true;
            this.difHeight.Location = new System.Drawing.Point(13, 58);
            this.difHeight.Name = "difHeight";
            this.difHeight.Size = new System.Drawing.Size(553, 17);
            this.difHeight.TabIndex = 107;
            this.difHeight.Text = "The height of the internal opening is different from the height of the windows. I" +
    "f unsure please, leave unchecked";
            this.difHeight.UseVisualStyleBackColor = true;
            this.difHeight.CheckedChanged += new System.EventHandler(this.difHeight_CheckedChanged);
            // 
            // label6
            // 
            this.label6.Location = new System.Drawing.Point(10, 31);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(556, 25);
            this.label6.TabIndex = 19;
            this.label6.Text = "This section allows you to specify the height of the opening between internal zon" +
    "es of the building.";
            // 
            // intOpeningCancelBut
            // 
            this.intOpeningCancelBut.Location = new System.Drawing.Point(542, 206);
            this.intOpeningCancelBut.Name = "intOpeningCancelBut";
            this.intOpeningCancelBut.Size = new System.Drawing.Size(80, 20);
            this.intOpeningCancelBut.TabIndex = 102;
            this.intOpeningCancelBut.TabStop = false;
            this.intOpeningCancelBut.Text = "Cancel";
            this.intOpeningCancelBut.Click += new System.EventHandler(this.intOpeningCancelBut_Click);
            // 
            // intOpeningOKBut
            // 
            this.intOpeningOKBut.Location = new System.Drawing.Point(445, 206);
            this.intOpeningOKBut.Name = "intOpeningOKBut";
            this.intOpeningOKBut.Size = new System.Drawing.Size(80, 20);
            this.intOpeningOKBut.TabIndex = 101;
            this.intOpeningOKBut.TabStop = false;
            this.intOpeningOKBut.Text = "OK";
            this.intOpeningOKBut.Click += new System.EventHandler(this.intOpeningOKBut_Click);
            // 
            // intOpeningSpecsWindow
            // 
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Inherit;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(647, 238);
            this.Controls.Add(this.intOpeningGroup);
            this.Controls.Add(this.intOpeningCancelBut);
            this.Controls.Add(this.intOpeningOKBut);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "intOpeningSpecsWindow";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Advanced Internal Opening Options";
            this.Load += new System.EventHandler(this.intOpeningSpecsWindow_Load);
            this.intOpeningGroup.ResumeLayout(false);
            this.intOpeningGroup.PerformLayout();
            this.optionP.ResumeLayout(false);
            this.optionP.PerformLayout();
            this.shaft2P.ResumeLayout(false);
            this.shaft2P.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.GroupBox intOpeningGroup;
        private System.Windows.Forms.Button intOpeningCancelBut;
        private System.Windows.Forms.Button intOpeningOKBut;
        private System.Windows.Forms.CheckBox difHeight;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label units1LB;
        private System.Windows.Forms.TextBox intAreaHeight;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Button HelpButton;
        private System.Windows.Forms.Panel optionP;
        private System.Windows.Forms.Panel shaft2P;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label units2LB;
        private System.Windows.Forms.TextBox shaft2InletHeightTB;
    }
}