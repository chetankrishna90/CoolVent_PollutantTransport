namespace CoolVent
{
    partial class Stratification
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Stratification));
            this.trackBarStrat = new System.Windows.Forms.TrackBar();
            this.snapLabel = new System.Windows.Forms.Label();
            this.snapLabel10 = new System.Windows.Forms.Label();
            this.snapLabel17 = new System.Windows.Forms.Label();
            this.snapLabel24 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.occHeightUnits = new System.Windows.Forms.Label();
            this.occHeightLabel = new System.Windows.Forms.Label();
            this.occHeight = new System.Windows.Forms.TextBox();
            this.graphControlStrat = new CoolVent.graphControl();
            ((System.ComponentModel.ISupportInitialize)(this.trackBarStrat)).BeginInit();
            this.SuspendLayout();
            // 
            // trackBarStrat
            // 
            this.trackBarStrat.LargeChange = 2;
            this.trackBarStrat.Location = new System.Drawing.Point(12, 394);
            this.trackBarStrat.Maximum = 24;
            this.trackBarStrat.Name = "trackBarStrat";
            this.trackBarStrat.Size = new System.Drawing.Size(567, 45);
            this.trackBarStrat.TabIndex = 113;
            this.trackBarStrat.TickStyle = System.Windows.Forms.TickStyle.Both;
            this.trackBarStrat.Scroll += new System.EventHandler(this.trackBar1_Scroll);
            // 
            // snapLabel
            // 
            this.snapLabel.Location = new System.Drawing.Point(216, 450);
            this.snapLabel.Name = "snapLabel";
            this.snapLabel.Size = new System.Drawing.Size(200, 20);
            this.snapLabel.TabIndex = 116;
            this.snapLabel.Text = "**Move trackbar to view specific hour**";
            // 
            // snapLabel10
            // 
            this.snapLabel10.Location = new System.Drawing.Point(218, 433);
            this.snapLabel10.Name = "snapLabel10";
            this.snapLabel10.Size = new System.Drawing.Size(40, 20);
            this.snapLabel10.TabIndex = 117;
            this.snapLabel10.Text = "9:00";
            // 
            // snapLabel17
            // 
            this.snapLabel17.Location = new System.Drawing.Point(415, 432);
            this.snapLabel17.Name = "snapLabel17";
            this.snapLabel17.Size = new System.Drawing.Size(40, 20);
            this.snapLabel17.TabIndex = 114;
            this.snapLabel17.Text = "18:00";
            // 
            // snapLabel24
            // 
            this.snapLabel24.Location = new System.Drawing.Point(553, 433);
            this.snapLabel24.Name = "snapLabel24";
            this.snapLabel24.Size = new System.Drawing.Size(40, 20);
            this.snapLabel24.TabIndex = 115;
            this.snapLabel24.Text = "24:00";
            // 
            // label1
            // 
            this.label1.Location = new System.Drawing.Point(15, 433);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(40, 20);
            this.label1.TabIndex = 118;
            this.label1.Text = "0:00";
            // 
            // label2
            // 
            this.label2.BackColor = System.Drawing.Color.Transparent;
            this.label2.Location = new System.Drawing.Point(12, 370);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(303, 20);
            this.label2.TabIndex = 119;
            this.label2.Text = "Red line indicates the location of the occupied zone height.";
            // 
            // occHeightUnits
            // 
            this.occHeightUnits.Location = new System.Drawing.Point(176, 486);
            this.occHeightUnits.Name = "occHeightUnits";
            this.occHeightUnits.Size = new System.Drawing.Size(31, 21);
            this.occHeightUnits.TabIndex = 122;
            this.occHeightUnits.Text = "m";
            // 
            // occHeightLabel
            // 
            this.occHeightLabel.Location = new System.Drawing.Point(21, 486);
            this.occHeightLabel.Name = "occHeightLabel";
            this.occHeightLabel.Size = new System.Drawing.Size(113, 17);
            this.occHeightLabel.TabIndex = 121;
            this.occHeightLabel.Text = "Ocupant head height:";
            // 
            // occHeight
            // 
            this.occHeight.Location = new System.Drawing.Point(137, 483);
            this.occHeight.Name = "occHeight";
            this.occHeight.Size = new System.Drawing.Size(33, 20);
            this.occHeight.TabIndex = 120;
            this.occHeight.Text = "1.5";
            this.occHeight.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.occHeight.TextChanged += new System.EventHandler(this.occHeight_TextChanged);
            // 
            // graphControlStrat
            // 
            this.graphControlStrat.Location = new System.Drawing.Point(12, 12);
            this.graphControlStrat.Name = "graphControlStrat";
            this.graphControlStrat.ScrollMaxX = 0D;
            this.graphControlStrat.ScrollMaxY = 0D;
            this.graphControlStrat.ScrollMaxY2 = 0D;
            this.graphControlStrat.ScrollMinX = 0D;
            this.graphControlStrat.ScrollMinY = 0D;
            this.graphControlStrat.ScrollMinY2 = 0D;
            this.graphControlStrat.Size = new System.Drawing.Size(567, 355);
            this.graphControlStrat.TabIndex = 0;
            // 
            // Stratification
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(596, 518);
            this.Controls.Add(this.occHeightUnits);
            this.Controls.Add(this.occHeightLabel);
            this.Controls.Add(this.occHeight);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.snapLabel);
            this.Controls.Add(this.snapLabel10);
            this.Controls.Add(this.snapLabel17);
            this.Controls.Add(this.snapLabel24);
            this.Controls.Add(this.trackBarStrat);
            this.Controls.Add(this.graphControlStrat);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "Stratification";
            this.Text = "Stratification";
            this.Load += new System.EventHandler(this.Stratification_Load);
            ((System.ComponentModel.ISupportInitialize)(this.trackBarStrat)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private graphControl graphControlStrat;
        private System.Windows.Forms.TrackBar trackBarStrat;
        private System.Windows.Forms.Label snapLabel;
        private System.Windows.Forms.Label snapLabel10;
        private System.Windows.Forms.Label snapLabel17;
        private System.Windows.Forms.Label snapLabel24;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label occHeightUnits;
        private System.Windows.Forms.Label occHeightLabel;
        private System.Windows.Forms.TextBox occHeight;
    }
}