namespace CoolVent
{
    partial class Troubleshooting
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Troubleshooting));
            this.TroubleshootingText = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // TroubleshootingText
            // 
            this.TroubleshootingText.AutoSize = true;
            this.TroubleshootingText.Location = new System.Drawing.Point(12, 9);
            this.TroubleshootingText.Name = "TroubleshootingText";
            this.TroubleshootingText.Size = new System.Drawing.Size(607, 247);
            this.TroubleshootingText.TabIndex = 0;
            this.TroubleshootingText.Text = resources.GetString("TroubleshootingText.Text");
            // 
            // Troubleshooting
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(672, 275);
            this.Controls.Add(this.TroubleshootingText);
            this.Name = "Troubleshooting";
            this.Text = "Is CoolVent not running correctly?";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label TroubleshootingText;
    }
}