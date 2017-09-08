namespace CoolVent
{
    partial class FanResults
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FanResults));
            this.label1 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.fanOSL = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.fanEtaL = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.fanPowerL = new System.Windows.Forms.Label();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.tableLayoutPanel2 = new System.Windows.Forms.TableLayoutPanel();
            this.label7 = new System.Windows.Forms.Label();
            this.fanTypeL = new System.Windows.Forms.Label();
            this.label10 = new System.Windows.Forms.Label();
            this.label13 = new System.Windows.Forms.Label();
            this.fanDiameterL = new System.Windows.Forms.Label();
            this.noOfFansL = new System.Windows.Forms.Label();
            this.FanCurvePane = new CoolVent.graphControl();
            this.groupBox2.SuspendLayout();
            this.tableLayoutPanel2.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(36, 31);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(205, 13);
            this.label1.TabIndex = 3;
            this.label1.Text = "Fan characteristics and operational results";
            // 
            // label6
            // 
            this.label6.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(4, 132);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(301, 16);
            this.label6.TabIndex = 13;
            this.label6.Text = "Average fan speed [rpm]:";
            this.label6.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // fanOSL
            // 
            this.fanOSL.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.fanOSL.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fanOSL.Location = new System.Drawing.Point(312, 132);
            this.fanOSL.Name = "fanOSL";
            this.fanOSL.Size = new System.Drawing.Size(301, 16);
            this.fanOSL.TabIndex = 6;
            this.fanOSL.Text = "1240";
            this.fanOSL.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label4
            // 
            this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(4, 215);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(301, 16);
            this.label4.TabIndex = 5;
            this.label4.Text = "Average fan+motor efficiency [%]:";
            this.label4.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // fanEtaL
            // 
            this.fanEtaL.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.fanEtaL.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fanEtaL.Location = new System.Drawing.Point(312, 215);
            this.fanEtaL.Name = "fanEtaL";
            this.fanEtaL.Size = new System.Drawing.Size(301, 16);
            this.fanEtaL.TabIndex = 12;
            this.fanEtaL.Text = "1240";
            this.fanEtaL.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label3
            // 
            this.label3.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(4, 172);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(301, 16);
            this.label3.TabIndex = 4;
            this.label3.Text = "Total fan electric consumption [kWh]:";
            this.label3.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // fanPowerL
            // 
            this.fanPowerL.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.fanPowerL.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fanPowerL.Location = new System.Drawing.Point(312, 172);
            this.fanPowerL.Name = "fanPowerL";
            this.fanPowerL.Size = new System.Drawing.Size(301, 16);
            this.fanPowerL.TabIndex = 9;
            this.fanPowerL.Text = "20\" (0.51 m)";
            this.fanPowerL.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.tableLayoutPanel2);
            this.groupBox2.Location = new System.Drawing.Point(39, 61);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(673, 301);
            this.groupBox2.TabIndex = 6;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Fan Characteristics";
            // 
            // tableLayoutPanel2
            // 
            this.tableLayoutPanel2.CellBorderStyle = System.Windows.Forms.TableLayoutPanelCellBorderStyle.Single;
            this.tableLayoutPanel2.ColumnCount = 2;
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel2.Controls.Add(this.fanEtaL, 1, 5);
            this.tableLayoutPanel2.Controls.Add(this.fanOSL, 1, 3);
            this.tableLayoutPanel2.Controls.Add(this.fanPowerL, 1, 4);
            this.tableLayoutPanel2.Controls.Add(this.label6, 0, 3);
            this.tableLayoutPanel2.Controls.Add(this.label4, 0, 5);
            this.tableLayoutPanel2.Controls.Add(this.label7, 0, 0);
            this.tableLayoutPanel2.Controls.Add(this.fanTypeL, 1, 0);
            this.tableLayoutPanel2.Controls.Add(this.label3, 0, 4);
            this.tableLayoutPanel2.Controls.Add(this.label10, 0, 2);
            this.tableLayoutPanel2.Controls.Add(this.label13, 0, 1);
            this.tableLayoutPanel2.Controls.Add(this.fanDiameterL, 1, 1);
            this.tableLayoutPanel2.Controls.Add(this.noOfFansL, 1, 2);
            this.tableLayoutPanel2.Location = new System.Drawing.Point(28, 31);
            this.tableLayoutPanel2.Name = "tableLayoutPanel2";
            this.tableLayoutPanel2.RowCount = 6;
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 16.66667F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 16.66667F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 16.66667F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 16.66667F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 16.66667F));
            this.tableLayoutPanel2.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 16.66667F));
            this.tableLayoutPanel2.Size = new System.Drawing.Size(617, 247);
            this.tableLayoutPanel2.TabIndex = 17;
            // 
            // label7
            // 
            this.label7.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(4, 12);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(301, 16);
            this.label7.TabIndex = 13;
            this.label7.Text = "Fan type:";
            this.label7.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // fanTypeL
            // 
            this.fanTypeL.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.fanTypeL.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fanTypeL.Location = new System.Drawing.Point(312, 12);
            this.fanTypeL.Name = "fanTypeL";
            this.fanTypeL.Size = new System.Drawing.Size(301, 16);
            this.fanTypeL.TabIndex = 6;
            this.fanTypeL.Text = "Light duty";
            this.fanTypeL.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label10
            // 
            this.label10.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.label10.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label10.Location = new System.Drawing.Point(4, 92);
            this.label10.Name = "label10";
            this.label10.Size = new System.Drawing.Size(301, 16);
            this.label10.TabIndex = 5;
            this.label10.Text = "Number of fans in parallel:";
            this.label10.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // label13
            // 
            this.label13.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.label13.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label13.Location = new System.Drawing.Point(4, 52);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(301, 16);
            this.label13.TabIndex = 4;
            this.label13.Text = "Fan diameter [m]:";
            this.label13.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // fanDiameterL
            // 
            this.fanDiameterL.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.fanDiameterL.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fanDiameterL.Location = new System.Drawing.Point(312, 52);
            this.fanDiameterL.Name = "fanDiameterL";
            this.fanDiameterL.Size = new System.Drawing.Size(301, 16);
            this.fanDiameterL.TabIndex = 9;
            this.fanDiameterL.Text = "20\" (0.51 m)";
            this.fanDiameterL.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // noOfFansL
            // 
            this.noOfFansL.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.noOfFansL.Font = new System.Drawing.Font("Microsoft Sans Serif", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.noOfFansL.Location = new System.Drawing.Point(312, 92);
            this.noOfFansL.Name = "noOfFansL";
            this.noOfFansL.Size = new System.Drawing.Size(301, 16);
            this.noOfFansL.TabIndex = 12;
            this.noOfFansL.Text = "1240";
            this.noOfFansL.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // FanCurvePane
            // 
            this.FanCurvePane.IsEnableHPan = false;
            this.FanCurvePane.IsEnableHZoom = false;
            this.FanCurvePane.IsZoomOnMouseCenter = true;
            this.FanCurvePane.Location = new System.Drawing.Point(39, 391);
            this.FanCurvePane.Name = "FanCurvePane";
            this.FanCurvePane.ScrollMaxX = 0D;
            this.FanCurvePane.ScrollMaxY = 0D;
            this.FanCurvePane.ScrollMaxY2 = 0D;
            this.FanCurvePane.ScrollMinX = 0D;
            this.FanCurvePane.ScrollMinY = 0D;
            this.FanCurvePane.ScrollMinY2 = 0D;
            this.FanCurvePane.Size = new System.Drawing.Size(673, 376);
            this.FanCurvePane.TabIndex = 2;
            this.FanCurvePane.ZoomButtons = System.Windows.Forms.MouseButtons.None;
            // 
            // FanResults
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(738, 787);
            this.Controls.Add(this.groupBox2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.FanCurvePane);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "FanResults";
            this.Text = "Fan Operation Results";
            this.groupBox2.ResumeLayout(false);
            this.tableLayoutPanel2.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private graphControl FanCurvePane;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label fanOSL;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label fanEtaL;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label fanPowerL;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel2;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label fanTypeL;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.Label noOfFansL;
        private System.Windows.Forms.Label label13;
        private System.Windows.Forms.Label fanDiameterL;
    }
}