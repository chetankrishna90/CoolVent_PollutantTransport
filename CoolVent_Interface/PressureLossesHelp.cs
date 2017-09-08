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
    public class PressureLossesHelp : System.Windows.Forms.Form
    {
        private Label label6;
        private PictureBox fixedCustom;
        private Label label5;
        private PictureBox pictureBox1;
        private Label label4;
        private PictureBox fixedDamper;
        private Label label3;
        private Label label1;
        private PictureBox fixedElbow;
        private PictureBox fixedGrate;
        private TableLayoutPanel tableLayoutPanel1;
        private Label label2;
        private Label label7;
        private Label label8;
        private Label label9;
        private CheckBox checkBox1;        
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.Container components = null;

        public PressureLossesHelp()
        {
            //
            // Required for Windows Form Designer support
            //
            InitializeComponent();

            //
            // TODO: Add any constructor code after InitializeComponent call
            //
        }

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                if (components != null)
                {
                    components.Dispose();
                }
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
            this.label6 = new System.Windows.Forms.Label();
            this.fixedCustom = new System.Windows.Forms.PictureBox();
            this.label5 = new System.Windows.Forms.Label();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.label4 = new System.Windows.Forms.Label();
            this.fixedDamper = new System.Windows.Forms.PictureBox();
            this.label3 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.fixedElbow = new System.Windows.Forms.PictureBox();
            this.fixedGrate = new System.Windows.Forms.PictureBox();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.label2 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.checkBox1 = new System.Windows.Forms.CheckBox();
            ((System.ComponentModel.ISupportInitialize)(this.fixedCustom)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.fixedDamper)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.fixedElbow)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.fixedGrate)).BeginInit();
            this.tableLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(532, 322);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(41, 13);
            this.label6.TabIndex = 184;
            this.label6.Text = "custom";
            // 
            // fixedCustom
            // 
            this.fixedCustom.BackColor = System.Drawing.SystemColors.Window;
            this.fixedCustom.Image = global::CoolVent.Properties.Resources.Custom_lowres;
            this.fixedCustom.Location = new System.Drawing.Point(521, 265);
            this.fixedCustom.Name = "fixedCustom";
            this.fixedCustom.Size = new System.Drawing.Size(66, 54);
            this.fixedCustom.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.fixedCustom.TabIndex = 183;
            this.fixedCustom.TabStop = false;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(460, 322);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(51, 13);
            this.label5.TabIndex = 182;
            this.label5.Text = "reduction";
            // 
            // pictureBox1
            // 
            this.pictureBox1.BackColor = System.Drawing.SystemColors.Window;
            this.pictureBox1.Image = global::CoolVent.Properties.Resources.Reduction_lowres;
            this.pictureBox1.Location = new System.Drawing.Point(449, 265);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(66, 54);
            this.pictureBox1.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pictureBox1.TabIndex = 181;
            this.pictureBox1.TabStop = false;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(388, 322);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(42, 13);
            this.label4.TabIndex = 180;
            this.label4.Text = "damper";
            // 
            // fixedDamper
            // 
            this.fixedDamper.BackColor = System.Drawing.SystemColors.Window;
            this.fixedDamper.Image = global::CoolVent.Properties.Resources.Damper_lowres;
            this.fixedDamper.Location = new System.Drawing.Point(377, 265);
            this.fixedDamper.Name = "fixedDamper";
            this.fixedDamper.Size = new System.Drawing.Size(66, 54);
            this.fixedDamper.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.fixedDamper.TabIndex = 179;
            this.fixedDamper.TabStop = false;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(324, 322);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(31, 13);
            this.label3.TabIndex = 178;
            this.label3.Text = "bend";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(255, 322);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(31, 13);
            this.label1.TabIndex = 177;
            this.label1.Text = "grate";
            // 
            // fixedElbow
            // 
            this.fixedElbow.BackColor = System.Drawing.SystemColors.Window;
            this.fixedElbow.Image = global::CoolVent.Properties.Resources.Ubend_lowres;
            this.fixedElbow.Location = new System.Drawing.Point(305, 265);
            this.fixedElbow.Name = "fixedElbow";
            this.fixedElbow.Size = new System.Drawing.Size(66, 54);
            this.fixedElbow.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.fixedElbow.TabIndex = 176;
            this.fixedElbow.TabStop = false;
            // 
            // fixedGrate
            // 
            this.fixedGrate.BackColor = System.Drawing.SystemColors.Control;
            this.fixedGrate.Image = global::CoolVent.Properties.Resources.Grate_lowres;
            this.fixedGrate.Location = new System.Drawing.Point(233, 265);
            this.fixedGrate.Name = "fixedGrate";
            this.fixedGrate.Size = new System.Drawing.Size(66, 54);
            this.fixedGrate.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.fixedGrate.TabIndex = 175;
            this.fixedGrate.TabStop = false;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 2;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Controls.Add(this.label2, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.label7, 1, 0);
            this.tableLayoutPanel1.Controls.Add(this.label8, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.label9, 1, 1);
            this.tableLayoutPanel1.Location = new System.Drawing.Point(85, 430);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 2;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(200, 100);
            this.tableLayoutPanel1.TabIndex = 185;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(3, 0);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(35, 13);
            this.label2.TabIndex = 0;
            this.label2.Text = "label2";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(103, 0);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(35, 13);
            this.label7.TabIndex = 1;
            this.label7.Text = "label7";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(3, 50);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(35, 13);
            this.label8.TabIndex = 2;
            this.label8.Text = "label8";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(103, 50);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(35, 13);
            this.label9.TabIndex = 3;
            this.label9.Text = "label9";
            // 
            // checkBox1
            // 
            this.checkBox1.AutoSize = true;
            this.checkBox1.Location = new System.Drawing.Point(814, 265);
            this.checkBox1.Name = "checkBox1";
            this.checkBox1.Size = new System.Drawing.Size(80, 17);
            this.checkBox1.TabIndex = 186;
            this.checkBox1.Text = "checkBox1";
            this.checkBox1.UseVisualStyleBackColor = true;
            // 
            // PressureLossesHelp
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(820, 600);
            this.Controls.Add(this.checkBox1);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.fixedCustom);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.pictureBox1);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.fixedDamper);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.fixedElbow);
            this.Controls.Add(this.fixedGrate);
            this.Name = "PressureLossesHelp";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Class2";
            this.Load += new System.EventHandler(this.Class2_Load);
            ((System.ComponentModel.ISupportInitialize)(this.fixedCustom)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.fixedDamper)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.fixedElbow)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.fixedGrate)).EndInit();
            this.tableLayoutPanel1.ResumeLayout(false);
            this.tableLayoutPanel1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        #endregion


        private void Class2_Load(object sender, System.EventArgs e)
        {
            //Figure out what to put here.
            

        }
        
        private void SpaceCancelBut_Click(object sender, System.EventArgs e)
        {
            this.Close();
        }
    }
}
