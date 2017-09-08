namespace CoolVent
{
    partial class Progress
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Progress));
            this.simulatingPB = new System.Windows.Forms.ProgressBar();
            this.simulatingL = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // simulatingPB
            // 
            this.simulatingPB.Location = new System.Drawing.Point(32, 52);
            this.simulatingPB.Name = "simulatingPB";
            this.simulatingPB.Size = new System.Drawing.Size(191, 23);
            this.simulatingPB.Style = System.Windows.Forms.ProgressBarStyle.Continuous;
            this.simulatingPB.TabIndex = 0;
            this.simulatingPB.UseWaitCursor = true;
            // 
            // simulatingL
            // 
            this.simulatingL.Cursor = System.Windows.Forms.Cursors.WaitCursor;
            this.simulatingL.Location = new System.Drawing.Point(30, 26);
            this.simulatingL.Name = "simulatingL";
            this.simulatingL.Size = new System.Drawing.Size(193, 23);
            this.simulatingL.TabIndex = 1;
            this.simulatingL.Text = "Running simulation. Please wait...";
            this.simulatingL.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            this.simulatingL.UseWaitCursor = true;
            // 
            // Progress
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(258, 109);
            this.ControlBox = false;
            this.Controls.Add(this.simulatingL);
            this.Controls.Add(this.simulatingPB);
            this.Cursor = System.Windows.Forms.Cursors.WaitCursor;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "Progress";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Simulating";
            this.UseWaitCursor = true;
            this.ResumeLayout(false);

        }

        #endregion

        public System.Windows.Forms.ProgressBar simulatingPB;
        private System.Windows.Forms.Label simulatingL;

    }
}