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
    public class BuildHelp : System.Windows.Forms.Form
    {
        
        
        private System.Windows.Forms.Button SpaceCancelBut;

        private PictureBox BldgHelp3;
        private PictureBox BldgHelp2;
        private PictureBox BldgHelp1;
        private PictureBox BldgHelp0;        
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.Container components = null;

        public BuildHelp()
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
            this.SpaceCancelBut = new System.Windows.Forms.Button();
            this.BldgHelp0 = new System.Windows.Forms.PictureBox();
            this.BldgHelp1 = new System.Windows.Forms.PictureBox();
            this.BldgHelp2 = new System.Windows.Forms.PictureBox();
            this.BldgHelp3 = new System.Windows.Forms.PictureBox();
            ((System.ComponentModel.ISupportInitialize)(this.BldgHelp0)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.BldgHelp1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.BldgHelp2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.BldgHelp3)).BeginInit();
            this.SuspendLayout();
            // 
            // SpaceCancelBut
            // 
            this.SpaceCancelBut.Location = new System.Drawing.Point(10, 600);
            this.SpaceCancelBut.Name = "SpaceCancelBut";
            this.SpaceCancelBut.Size = new System.Drawing.Size(800, 20);
            this.SpaceCancelBut.TabIndex = 19;
            this.SpaceCancelBut.Text = "Close";
            this.SpaceCancelBut.Click += new System.EventHandler(this.SpaceCancelBut_Click);
            // 
            // BldgHelp0
            // 
            this.BldgHelp0.Image = global::CoolVent.Properties.Resources.build0;
            this.BldgHelp0.Location = new System.Drawing.Point(8, 7);
            this.BldgHelp0.Name = "BldgHelp0";
            this.BldgHelp0.Size = new System.Drawing.Size(800, 440);
            this.BldgHelp0.TabIndex = 23;
            this.BldgHelp0.TabStop = false;
            // 
            // BldgHelp1
            // 
            this.BldgHelp1.Image = global::CoolVent.Properties.Resources.build1;
            this.BldgHelp1.Location = new System.Drawing.Point(8, 7);
            this.BldgHelp1.Name = "BldgHelp1";
            this.BldgHelp1.Size = new System.Drawing.Size(800, 495);
            this.BldgHelp1.TabIndex = 22;
            this.BldgHelp1.TabStop = false;
            // 
            // BldgHelp2
            // 
            this.BldgHelp2.Image = global::CoolVent.Properties.Resources.build2;
            this.BldgHelp2.Location = new System.Drawing.Point(8, 7);
            this.BldgHelp2.Name = "BldgHelp2";
            this.BldgHelp2.Size = new System.Drawing.Size(800, 510);
            this.BldgHelp2.TabIndex = 21;
            this.BldgHelp2.TabStop = false;
            // 
            // BldgHelp3
            // 
            this.BldgHelp3.Image = global::CoolVent.Properties.Resources.build3;
            this.BldgHelp3.Location = new System.Drawing.Point(8, 7);
            this.BldgHelp3.Name = "BldgHelp3";
            this.BldgHelp3.Size = new System.Drawing.Size(800, 501);
            this.BldgHelp3.TabIndex = 20;
            this.BldgHelp3.TabStop = false;
            // 
            // BuildHelp
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.ClientSize = new System.Drawing.Size(820, 600);
            this.Controls.Add(this.BldgHelp2);
            this.Controls.Add(this.SpaceCancelBut);
            this.Controls.Add(this.BldgHelp3);
            this.Controls.Add(this.BldgHelp0);
            this.Controls.Add(this.BldgHelp1);
            this.Name = "BuildHelp";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Class2";
            this.Load += new System.EventHandler(this.Class2_Load);
            ((System.ComponentModel.ISupportInitialize)(this.BldgHelp0)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.BldgHelp1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.BldgHelp2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.BldgHelp3)).EndInit();
            this.ResumeLayout(false);

        }
        #endregion


        private void Class2_Load(object sender, System.EventArgs e)
        {
            //Figure out what to put here.
            if (globalcontrol.bldgtype == 0) //Luton
            {
                BldgHelp0.Show();
                BldgHelp1.Hide();
                BldgHelp2.Hide();
                BldgHelp3.Hide();
                this.SpaceCancelBut.Location = new System.Drawing.Point(10, this.BldgHelp0.Height + 20);
                this.ClientSize = new System.Drawing.Size(820, this.SpaceCancelBut.Location.Y + this.SpaceCancelBut.Height + 10);
            }
            else if (globalcontrol.bldgtype == 1)
            {
                BldgHelp1.Show();
                BldgHelp0.Hide();
                BldgHelp2.Hide();
                BldgHelp3.Hide();
                this.SpaceCancelBut.Location = new System.Drawing.Point(10, this.BldgHelp1.Height + 20);
                this.ClientSize = new System.Drawing.Size(820, this.SpaceCancelBut.Location.Y + this.SpaceCancelBut.Height + 10);
            }
            else if (globalcontrol.bldgtype == 2)
            {
                BldgHelp2.Show();
                BldgHelp1.Hide();
                BldgHelp0.Hide();
                BldgHelp3.Hide();
                this.SpaceCancelBut.Location = new System.Drawing.Point(10, this.BldgHelp3.Height + 20);
                this.ClientSize = new System.Drawing.Size(820, this.SpaceCancelBut.Location.Y + this.SpaceCancelBut.Height + 10);
            }
            else if (globalcontrol.bldgtype == 4) //Shaft
            {
                BldgHelp1.Show();
                BldgHelp0.Hide();
                BldgHelp2.Hide();
                BldgHelp3.Hide();
                this.SpaceCancelBut.Location = new System.Drawing.Point(10, this.BldgHelp1.Height + 20);
                this.ClientSize = new System.Drawing.Size(820, this.SpaceCancelBut.Location.Y + this.SpaceCancelBut.Height + 10);

            }
            else
            {
                BldgHelp3.Show();
                BldgHelp1.Hide();
                BldgHelp2.Hide();
                BldgHelp0.Hide();
                this.SpaceCancelBut.Location = new System.Drawing.Point(10, this.BldgHelp3.Height + 20);
                this.ClientSize = new System.Drawing.Size(820, this.SpaceCancelBut.Location.Y + this.SpaceCancelBut.Height + 10);
            }

        }
        
        private void SpaceCancelBut_Click(object sender, System.EventArgs e)
        {
            this.Close();
        }
    }
}
