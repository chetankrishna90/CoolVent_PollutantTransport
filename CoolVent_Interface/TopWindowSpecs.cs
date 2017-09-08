using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.Drawing.Imaging;

namespace CoolVent
{
    /// <summary>
    /// Summary description for TopWindowSpace.
    /// </summary>
    public class TopWindowSpecs : System.Windows.Forms.Form
    {
        private System.Windows.Forms.GroupBox roofInputsGB; //Standard across all building types. //picture for the roof plan diagram.
        //private Bitmap roofDrawing = new Bitmap("roofFigure.jpg");

        private System.Windows.Forms.Button SpaceCancelBut; //keep the buttons for cancel options.
        private System.Windows.Forms.Button SpaceOKBut;

        //Change these.
        private System.Windows.Forms.Label numbOfWindowsLabel;
        private System.Windows.Forms.Label label8; //no longer need.
        private System.Windows.Forms.Label openingAreaUnits;
        private System.Windows.Forms.TextBox SpacingLength; //no longer need.
        private System.Windows.Forms.Label label5; //no longer need.
        private System.Windows.Forms.Label openingAreaLabel;
        private System.Windows.Forms.TextBox roofOpeningArea;
        private System.Windows.Forms.TextBox numbofRoofOpenings;
        private Label label1;
        private Label label2;
        private GroupBox inputsSingle;
        private Label label3;
        private CheckBox doubleOpeningsCheck;
        private Label singleNumbOfWindowsLabel;
        private TextBox singleNumbofWindows;
        private Label lowerOpeningAreaLabel;
        private TextBox singleLowerOpeningArea;
        private Label lowerOpeningAreaUnits;
        private Label singleUpperOpeningAreaLabel;
        private TextBox singleUpperOpeningArea;
        private Label singleUpperOpeningAreaUnits;
        private Label upperOpeningHeightLabel;
        private TextBox upperOpeningHeight;
        private Label upperOpeningHeightUnits;
        private Label deltaHLabel;
        private TextBox deltaH;
        private Label deltaHUnits;
        private Label singleGlazingAreaLabel;
        private TextBox singleGlazingArea;
        private Label singleGlazingAreaUnits;
        private GroupBox groupBox1;
        private Label label7;
        private Label label6;
        private Label label4;
        private Label tOASWUnitsLB;
        private Label label9;
        private TextBox sWtoWallRatioTB;
        private TextBox totOpAreaSWTB;
        private TextBox totGlazAreaSWTB;
        private ErrorProvider errorProvider1;
        private GroupBox groupBox2;
        private Label label17;
        private Label rwtrrLB;
        private Label label15;
        private TextBox rWtoWallRatioTB;
        private TextBox rWtoRoofRatioTB;
        private TextBox totOpAreaRWTB;
        private Label label13;
        private Label label12;
        private Label label11;
        private Label swtwrLB;
        private ErrorProvider errorProvider2;
        private ErrorProvider errorProvider3;
        private IContainer components;
        Boolean inputsOkay1 = true;
        Boolean inputsOkay2 = true;
        Boolean inputsOkay3 = true;
        Boolean inputsOkay4 = true;
        Boolean inputsOkay5 = true;
        Boolean inputsOkay6 = true;
        Boolean inputsOkay7 = true;
        Boolean inputsOkay8 = true;
        Boolean inputsOkay9 = true;
        Boolean inputsOkay10 = true;
        Boolean inputsOkay11 = true;

        public TopWindowSpecs()
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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TopWindowSpecs));
            this.roofInputsGB = new System.Windows.Forms.GroupBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.label17 = new System.Windows.Forms.Label();
            this.rwtrrLB = new System.Windows.Forms.Label();
            this.label15 = new System.Windows.Forms.Label();
            this.rWtoWallRatioTB = new System.Windows.Forms.TextBox();
            this.rWtoRoofRatioTB = new System.Windows.Forms.TextBox();
            this.totOpAreaRWTB = new System.Windows.Forms.TextBox();
            this.label13 = new System.Windows.Forms.Label();
            this.label12 = new System.Windows.Forms.Label();
            this.label11 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.numbOfWindowsLabel = new System.Windows.Forms.Label();
            this.numbofRoofOpenings = new System.Windows.Forms.TextBox();
            this.openingAreaLabel = new System.Windows.Forms.Label();
            this.roofOpeningArea = new System.Windows.Forms.TextBox();
            this.openingAreaUnits = new System.Windows.Forms.Label();
            this.SpaceCancelBut = new System.Windows.Forms.Button();
            this.SpaceOKBut = new System.Windows.Forms.Button();
            this.label8 = new System.Windows.Forms.Label();
            this.SpacingLength = new System.Windows.Forms.TextBox();
            this.label5 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.inputsSingle = new System.Windows.Forms.GroupBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.swtwrLB = new System.Windows.Forms.Label();
            this.tOASWUnitsLB = new System.Windows.Forms.Label();
            this.label9 = new System.Windows.Forms.Label();
            this.sWtoWallRatioTB = new System.Windows.Forms.TextBox();
            this.totOpAreaSWTB = new System.Windows.Forms.TextBox();
            this.totGlazAreaSWTB = new System.Windows.Forms.TextBox();
            this.label7 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
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
            this.errorProvider1 = new System.Windows.Forms.ErrorProvider(this.components);
            this.errorProvider2 = new System.Windows.Forms.ErrorProvider(this.components);
            this.errorProvider3 = new System.Windows.Forms.ErrorProvider(this.components);
            this.roofInputsGB.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.inputsSingle.SuspendLayout();
            this.groupBox1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider2)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider3)).BeginInit();
            this.SuspendLayout();
            // 
            // roofInputsGB
            // 
            this.roofInputsGB.Controls.Add(this.groupBox2);
            this.roofInputsGB.Controls.Add(this.label1);
            this.roofInputsGB.Controls.Add(this.numbOfWindowsLabel);
            this.roofInputsGB.Controls.Add(this.numbofRoofOpenings);
            this.roofInputsGB.Controls.Add(this.openingAreaLabel);
            this.roofInputsGB.Controls.Add(this.roofOpeningArea);
            this.roofInputsGB.Controls.Add(this.openingAreaUnits);
            this.roofInputsGB.Location = new System.Drawing.Point(32, 890);
            this.roofInputsGB.Name = "roofInputsGB";
            this.roofInputsGB.Size = new System.Drawing.Size(672, 369);
            this.roofInputsGB.TabIndex = 99;
            this.roofInputsGB.TabStop = false;
            this.roofInputsGB.Text = "Roof opening";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.label17);
            this.groupBox2.Controls.Add(this.rwtrrLB);
            this.groupBox2.Controls.Add(this.label15);
            this.groupBox2.Controls.Add(this.rWtoWallRatioTB);
            this.groupBox2.Controls.Add(this.rWtoRoofRatioTB);
            this.groupBox2.Controls.Add(this.totOpAreaRWTB);
            this.groupBox2.Controls.Add(this.label13);
            this.groupBox2.Controls.Add(this.label12);
            this.groupBox2.Controls.Add(this.label11);
            this.groupBox2.Location = new System.Drawing.Point(374, 255);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(272, 100);
            this.groupBox2.TabIndex = 37;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Roof opening summary";
            // 
            // label17
            // 
            this.label17.AutoSize = true;
            this.label17.Location = new System.Drawing.Point(217, 23);
            this.label17.Name = "label17";
            this.label17.Size = new System.Drawing.Size(27, 13);
            this.label17.TabIndex = 11;
            this.label17.Text = "m^2";
            // 
            // rwtrrLB
            // 
            this.rwtrrLB.AutoSize = true;
            this.rwtrrLB.Location = new System.Drawing.Point(217, 49);
            this.rwtrrLB.Name = "rwtrrLB";
            this.rwtrrLB.Size = new System.Drawing.Size(15, 13);
            this.rwtrrLB.TabIndex = 10;
            this.rwtrrLB.Text = "%";
            // 
            // label15
            // 
            this.label15.AutoSize = true;
            this.label15.Location = new System.Drawing.Point(217, 75);
            this.label15.Name = "label15";
            this.label15.Size = new System.Drawing.Size(15, 13);
            this.label15.TabIndex = 9;
            this.label15.Text = "%";
            // 
            // rWtoWallRatioTB
            // 
            this.rWtoWallRatioTB.Location = new System.Drawing.Point(159, 72);
            this.rWtoWallRatioTB.Name = "rWtoWallRatioTB";
            this.rWtoWallRatioTB.ReadOnly = true;
            this.rWtoWallRatioTB.Size = new System.Drawing.Size(52, 20);
            this.rWtoWallRatioTB.TabIndex = 5;
            this.rWtoWallRatioTB.TabStop = false;
            this.rWtoWallRatioTB.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // rWtoRoofRatioTB
            // 
            this.rWtoRoofRatioTB.Location = new System.Drawing.Point(159, 46);
            this.rWtoRoofRatioTB.Name = "rWtoRoofRatioTB";
            this.rWtoRoofRatioTB.ReadOnly = true;
            this.rWtoRoofRatioTB.Size = new System.Drawing.Size(52, 20);
            this.rWtoRoofRatioTB.TabIndex = 4;
            this.rWtoRoofRatioTB.TabStop = false;
            this.rWtoRoofRatioTB.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // totOpAreaRWTB
            // 
            this.totOpAreaRWTB.Location = new System.Drawing.Point(159, 20);
            this.totOpAreaRWTB.Name = "totOpAreaRWTB";
            this.totOpAreaRWTB.ReadOnly = true;
            this.totOpAreaRWTB.Size = new System.Drawing.Size(52, 20);
            this.totOpAreaRWTB.TabIndex = 3;
            this.totOpAreaRWTB.TabStop = false;
            this.totOpAreaRWTB.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label13
            // 
            this.label13.AutoSize = true;
            this.label13.Location = new System.Drawing.Point(13, 75);
            this.label13.Name = "label13";
            this.label13.Size = new System.Drawing.Size(125, 13);
            this.label13.TabIndex = 2;
            this.label13.Text = "Roof-window-to-wall ratio";
            // 
            // label12
            // 
            this.label12.AutoSize = true;
            this.label12.Location = new System.Drawing.Point(13, 49);
            this.label12.Name = "label12";
            this.label12.Size = new System.Drawing.Size(128, 13);
            this.label12.TabIndex = 1;
            this.label12.Text = "Roof-window-to-roof ratio:";
            // 
            // label11
            // 
            this.label11.AutoSize = true;
            this.label11.Location = new System.Drawing.Point(13, 23);
            this.label11.Name = "label11";
            this.label11.Size = new System.Drawing.Size(120, 13);
            this.label11.TabIndex = 0;
            this.label11.Text = "Total roof opening area:";
            // 
            // label1
            // 
            this.label1.BackColor = System.Drawing.Color.Transparent;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(15, 330);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(263, 33);
            this.label1.TabIndex = 36;
            this.label1.Text = "Note: Solar heat gains through the roof are not accounted for by CoolVent";
            // 
            // numbOfWindowsLabel
            // 
            this.numbOfWindowsLabel.Location = new System.Drawing.Point(15, 258);
            this.numbOfWindowsLabel.Name = "numbOfWindowsLabel";
            this.numbOfWindowsLabel.Size = new System.Drawing.Size(120, 20);
            this.numbOfWindowsLabel.TabIndex = 35;
            this.numbOfWindowsLabel.Text = "Number of windows: ";
            // 
            // numbofRoofOpenings
            // 
            this.numbofRoofOpenings.Location = new System.Drawing.Point(184, 255);
            this.numbofRoofOpenings.Name = "numbofRoofOpenings";
            this.numbofRoofOpenings.Size = new System.Drawing.Size(40, 20);
            this.numbofRoofOpenings.TabIndex = 2;
            this.numbofRoofOpenings.TabStop = false;
            this.numbofRoofOpenings.Tag = "";
            this.numbofRoofOpenings.Text = "3";
            this.numbofRoofOpenings.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.numbofRoofOpenings.TextChanged += new System.EventHandler(this.numbofRoofOpenings_TextChanged);
            // 
            // openingAreaLabel
            // 
            this.openingAreaLabel.BackColor = System.Drawing.Color.Transparent;
            this.openingAreaLabel.Location = new System.Drawing.Point(15, 290);
            this.openingAreaLabel.Name = "openingAreaLabel";
            this.openingAreaLabel.Size = new System.Drawing.Size(163, 20);
            this.openingAreaLabel.TabIndex = 21;
            this.openingAreaLabel.Text = "Area of each operable window: ";
            // 
            // roofOpeningArea
            // 
            this.roofOpeningArea.Location = new System.Drawing.Point(184, 287);
            this.roofOpeningArea.Name = "roofOpeningArea";
            this.roofOpeningArea.Size = new System.Drawing.Size(40, 20);
            this.roofOpeningArea.TabIndex = 3;
            this.roofOpeningArea.TabStop = false;
            this.roofOpeningArea.Text = "0.5";
            this.roofOpeningArea.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.roofOpeningArea.TextChanged += new System.EventHandler(this.roofOpeningArea_TextChanged);
            // 
            // openingAreaUnits
            // 
            this.openingAreaUnits.Location = new System.Drawing.Point(238, 290);
            this.openingAreaUnits.Name = "openingAreaUnits";
            this.openingAreaUnits.Size = new System.Drawing.Size(40, 20);
            this.openingAreaUnits.TabIndex = 30;
            this.openingAreaUnits.Text = "m^2";
            // 
            // SpaceCancelBut
            // 
            this.SpaceCancelBut.Location = new System.Drawing.Point(622, 1276);
            this.SpaceCancelBut.Name = "SpaceCancelBut";
            this.SpaceCancelBut.Size = new System.Drawing.Size(80, 20);
            this.SpaceCancelBut.TabIndex = 5;
            this.SpaceCancelBut.TabStop = false;
            this.SpaceCancelBut.Text = "Cancel";
            this.SpaceCancelBut.Click += new System.EventHandler(this.SpaceCancelBut_Click);
            // 
            // SpaceOKBut
            // 
            this.SpaceOKBut.Location = new System.Drawing.Point(532, 1276);
            this.SpaceOKBut.Name = "SpaceOKBut";
            this.SpaceOKBut.Size = new System.Drawing.Size(80, 20);
            this.SpaceOKBut.TabIndex = 4;
            this.SpaceOKBut.TabStop = false;
            this.SpaceOKBut.Text = "OK";
            this.SpaceOKBut.Click += new System.EventHandler(this.SpaceOKBut_Click);
            // 
            // label8
            // 
            this.label8.Location = new System.Drawing.Point(256, 304);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(13, 20);
            this.label8.TabIndex = 32;
            this.label8.Text = "m";
            // 
            // SpacingLength
            // 
            this.SpacingLength.Location = new System.Drawing.Point(208, 304);
            this.SpacingLength.Name = "SpacingLength";
            this.SpacingLength.Size = new System.Drawing.Size(40, 20);
            this.SpacingLength.TabIndex = 3;
            this.SpacingLength.Text = "3.0";
            this.SpacingLength.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label5
            // 
            this.label5.Location = new System.Drawing.Point(88, 304);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(93, 20);
            this.label5.TabIndex = 28;
            this.label5.Text = "Spacing Distance";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(29, 223);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(500, 13);
            this.label2.TabIndex = 50;
            this.label2.Text = "This tool will allow you to calculate the total opening and glazing areas per fac" +
    "ade per floor in the building";
            // 
            // inputsSingle
            // 
            this.inputsSingle.Controls.Add(this.groupBox1);
            this.inputsSingle.Controls.Add(this.label3);
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
            this.inputsSingle.Location = new System.Drawing.Point(32, 253);
            this.inputsSingle.Name = "inputsSingle";
            this.inputsSingle.Size = new System.Drawing.Size(672, 622);
            this.inputsSingle.TabIndex = 101;
            this.inputsSingle.TabStop = false;
            this.inputsSingle.Text = "Side openings";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.swtwrLB);
            this.groupBox1.Controls.Add(this.tOASWUnitsLB);
            this.groupBox1.Controls.Add(this.label9);
            this.groupBox1.Controls.Add(this.sWtoWallRatioTB);
            this.groupBox1.Controls.Add(this.totOpAreaSWTB);
            this.groupBox1.Controls.Add(this.totGlazAreaSWTB);
            this.groupBox1.Controls.Add(this.label7);
            this.groupBox1.Controls.Add(this.label6);
            this.groupBox1.Controls.Add(this.label4);
            this.groupBox1.Location = new System.Drawing.Point(374, 457);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(272, 112);
            this.groupBox1.TabIndex = 103;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Side opening summary";
            // 
            // swtwrLB
            // 
            this.swtwrLB.AutoSize = true;
            this.swtwrLB.Location = new System.Drawing.Point(217, 80);
            this.swtwrLB.Name = "swtwrLB";
            this.swtwrLB.Size = new System.Drawing.Size(15, 13);
            this.swtwrLB.TabIndex = 8;
            this.swtwrLB.Text = "%";
            // 
            // tOASWUnitsLB
            // 
            this.tOASWUnitsLB.AutoSize = true;
            this.tOASWUnitsLB.Location = new System.Drawing.Point(217, 55);
            this.tOASWUnitsLB.Name = "tOASWUnitsLB";
            this.tOASWUnitsLB.Size = new System.Drawing.Size(27, 13);
            this.tOASWUnitsLB.TabIndex = 7;
            this.tOASWUnitsLB.Text = "m^2";
            // 
            // label9
            // 
            this.label9.AutoSize = true;
            this.label9.Location = new System.Drawing.Point(217, 28);
            this.label9.Name = "label9";
            this.label9.Size = new System.Drawing.Size(27, 13);
            this.label9.TabIndex = 6;
            this.label9.Text = "m^2";
            // 
            // sWtoWallRatioTB
            // 
            this.sWtoWallRatioTB.Location = new System.Drawing.Point(159, 77);
            this.sWtoWallRatioTB.Name = "sWtoWallRatioTB";
            this.sWtoWallRatioTB.ReadOnly = true;
            this.sWtoWallRatioTB.Size = new System.Drawing.Size(52, 20);
            this.sWtoWallRatioTB.TabIndex = 5;
            this.sWtoWallRatioTB.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // totOpAreaSWTB
            // 
            this.totOpAreaSWTB.Location = new System.Drawing.Point(159, 52);
            this.totOpAreaSWTB.Name = "totOpAreaSWTB";
            this.totOpAreaSWTB.ReadOnly = true;
            this.totOpAreaSWTB.Size = new System.Drawing.Size(52, 20);
            this.totOpAreaSWTB.TabIndex = 4;
            this.totOpAreaSWTB.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // totGlazAreaSWTB
            // 
            this.totGlazAreaSWTB.Location = new System.Drawing.Point(159, 25);
            this.totGlazAreaSWTB.Name = "totGlazAreaSWTB";
            this.totGlazAreaSWTB.ReadOnly = true;
            this.totGlazAreaSWTB.Size = new System.Drawing.Size(52, 20);
            this.totGlazAreaSWTB.TabIndex = 3;
            this.totGlazAreaSWTB.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Location = new System.Drawing.Point(13, 55);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(140, 13);
            this.label7.TabIndex = 2;
            this.label7.Text = "Total opening area per floor:";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Location = new System.Drawing.Point(13, 28);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(135, 13);
            this.label6.TabIndex = 1;
            this.label6.Text = "Total glazing area per floor:";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(13, 80);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(126, 13);
            this.label4.TabIndex = 0;
            this.label4.Text = "Side-window-to-wall ratio:";
            // 
            // label3
            // 
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Italic, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(312, 381);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(349, 33);
            this.label3.TabIndex = 18;
            this.label3.Text = "Area used to calculate solar heat gains (area through which sunlight enters the r" +
    "oom), can be modified to account for blinds or overhangs.";
            // 
            // doubleOpeningsCheck
            // 
            this.doubleOpeningsCheck.AutoSize = true;
            this.doubleOpeningsCheck.Location = new System.Drawing.Point(18, 525);
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
            this.singleNumbOfWindowsLabel.Location = new System.Drawing.Point(15, 348);
            this.singleNumbOfWindowsLabel.Name = "singleNumbOfWindowsLabel";
            this.singleNumbOfWindowsLabel.Size = new System.Drawing.Size(155, 20);
            this.singleNumbOfWindowsLabel.TabIndex = 16;
            this.singleNumbOfWindowsLabel.Text = "Number of windows per floor:";
            // 
            // singleNumbofWindows
            // 
            this.singleNumbofWindows.Location = new System.Drawing.Point(213, 345);
            this.singleNumbofWindows.Name = "singleNumbofWindows";
            this.singleNumbofWindows.Size = new System.Drawing.Size(40, 20);
            this.singleNumbofWindows.TabIndex = 1;
            this.singleNumbofWindows.Tag = "";
            this.singleNumbofWindows.Text = "6";
            this.singleNumbofWindows.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.singleNumbofWindows.TextChanged += new System.EventHandler(this.singleNumbofWindows_TextChanged);
            // 
            // lowerOpeningAreaLabel
            // 
            this.lowerOpeningAreaLabel.Location = new System.Drawing.Point(45, 552);
            this.lowerOpeningAreaLabel.Name = "lowerOpeningAreaLabel";
            this.lowerOpeningAreaLabel.Size = new System.Drawing.Size(188, 22);
            this.lowerOpeningAreaLabel.TabIndex = 16;
            this.lowerOpeningAreaLabel.Text = "Area of each lower opening:";
            // 
            // singleLowerOpeningArea
            // 
            this.singleLowerOpeningArea.Location = new System.Drawing.Point(245, 549);
            this.singleLowerOpeningArea.Name = "singleLowerOpeningArea";
            this.singleLowerOpeningArea.Size = new System.Drawing.Size(40, 20);
            this.singleLowerOpeningArea.TabIndex = 3;
            this.singleLowerOpeningArea.Tag = "";
            this.singleLowerOpeningArea.Text = "0.2";
            this.singleLowerOpeningArea.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.singleLowerOpeningArea.TextChanged += new System.EventHandler(this.singleLowerOpeningArea_TextChanged);
            // 
            // lowerOpeningAreaUnits
            // 
            this.lowerOpeningAreaUnits.Location = new System.Drawing.Point(298, 552);
            this.lowerOpeningAreaUnits.Name = "lowerOpeningAreaUnits";
            this.lowerOpeningAreaUnits.Size = new System.Drawing.Size(34, 20);
            this.lowerOpeningAreaUnits.TabIndex = 16;
            this.lowerOpeningAreaUnits.Text = "m^2";
            // 
            // singleUpperOpeningAreaLabel
            // 
            this.singleUpperOpeningAreaLabel.Location = new System.Drawing.Point(15, 423);
            this.singleUpperOpeningAreaLabel.Name = "singleUpperOpeningAreaLabel";
            this.singleUpperOpeningAreaLabel.Size = new System.Drawing.Size(155, 38);
            this.singleUpperOpeningAreaLabel.TabIndex = 16;
            this.singleUpperOpeningAreaLabel.Text = "Total open area in each window frame:";
            // 
            // singleUpperOpeningArea
            // 
            this.singleUpperOpeningArea.Location = new System.Drawing.Point(213, 427);
            this.singleUpperOpeningArea.Name = "singleUpperOpeningArea";
            this.singleUpperOpeningArea.Size = new System.Drawing.Size(40, 20);
            this.singleUpperOpeningArea.TabIndex = 3;
            this.singleUpperOpeningArea.Tag = "";
            this.singleUpperOpeningArea.Text = "0.1";
            this.singleUpperOpeningArea.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.singleUpperOpeningArea.TextChanged += new System.EventHandler(this.singleUpperOpeningArea_TextChanged);
            // 
            // singleUpperOpeningAreaUnits
            // 
            this.singleUpperOpeningAreaUnits.Location = new System.Drawing.Point(265, 430);
            this.singleUpperOpeningAreaUnits.Name = "singleUpperOpeningAreaUnits";
            this.singleUpperOpeningAreaUnits.Size = new System.Drawing.Size(40, 20);
            this.singleUpperOpeningAreaUnits.TabIndex = 16;
            this.singleUpperOpeningAreaUnits.Text = "m^2";
            // 
            // upperOpeningHeightLabel
            // 
            this.upperOpeningHeightLabel.BackColor = System.Drawing.Color.Transparent;
            this.upperOpeningHeightLabel.Location = new System.Drawing.Point(15, 466);
            this.upperOpeningHeightLabel.Name = "upperOpeningHeightLabel";
            this.upperOpeningHeightLabel.Size = new System.Drawing.Size(192, 44);
            this.upperOpeningHeightLabel.TabIndex = 16;
            this.upperOpeningHeightLabel.Text = "Height from floor to window (h for single-opening window or hupper for double-ope" +
    "ning window):";
            // 
            // upperOpeningHeight
            // 
            this.upperOpeningHeight.Location = new System.Drawing.Point(213, 475);
            this.upperOpeningHeight.Name = "upperOpeningHeight";
            this.upperOpeningHeight.Size = new System.Drawing.Size(40, 20);
            this.upperOpeningHeight.TabIndex = 3;
            this.upperOpeningHeight.TabStop = false;
            this.upperOpeningHeight.Tag = "";
            this.upperOpeningHeight.Text = "0.5";
            this.upperOpeningHeight.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.upperOpeningHeight.TextChanged += new System.EventHandler(this.upperOpeningHeight_TextChanged);
            // 
            // upperOpeningHeightUnits
            // 
            this.upperOpeningHeightUnits.Location = new System.Drawing.Point(272, 478);
            this.upperOpeningHeightUnits.Name = "upperOpeningHeightUnits";
            this.upperOpeningHeightUnits.Size = new System.Drawing.Size(40, 20);
            this.upperOpeningHeightUnits.TabIndex = 16;
            this.upperOpeningHeightUnits.Text = "m";
            // 
            // deltaHLabel
            // 
            this.deltaHLabel.Location = new System.Drawing.Point(45, 583);
            this.deltaHLabel.Name = "deltaHLabel";
            this.deltaHLabel.Size = new System.Drawing.Size(188, 17);
            this.deltaHLabel.TabIndex = 16;
            this.deltaHLabel.Text = "Height between openings (delta-H):";
            // 
            // deltaH
            // 
            this.deltaH.Location = new System.Drawing.Point(245, 580);
            this.deltaH.Name = "deltaH";
            this.deltaH.Size = new System.Drawing.Size(40, 20);
            this.deltaH.TabIndex = 3;
            this.deltaH.Tag = "";
            this.deltaH.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.deltaH.TextChanged += new System.EventHandler(this.deltaH_TextChanged);
            // 
            // deltaHUnits
            // 
            this.deltaHUnits.Location = new System.Drawing.Point(298, 583);
            this.deltaHUnits.Name = "deltaHUnits";
            this.deltaHUnits.Size = new System.Drawing.Size(34, 20);
            this.deltaHUnits.TabIndex = 16;
            this.deltaHUnits.Text = "m";
            // 
            // singleGlazingAreaLabel
            // 
            this.singleGlazingAreaLabel.Location = new System.Drawing.Point(15, 381);
            this.singleGlazingAreaLabel.Name = "singleGlazingAreaLabel";
            this.singleGlazingAreaLabel.Size = new System.Drawing.Size(155, 35);
            this.singleGlazingAreaLabel.TabIndex = 16;
            this.singleGlazingAreaLabel.Text = "Area of each window frame (glazing):";
            // 
            // singleGlazingArea
            // 
            this.singleGlazingArea.Location = new System.Drawing.Point(213, 384);
            this.singleGlazingArea.Name = "singleGlazingArea";
            this.singleGlazingArea.Size = new System.Drawing.Size(40, 20);
            this.singleGlazingArea.TabIndex = 3;
            this.singleGlazingArea.Tag = "";
            this.singleGlazingArea.Text = "10";
            this.singleGlazingArea.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.singleGlazingArea.TextChanged += new System.EventHandler(this.singleGlazingArea_TextChanged);
            // 
            // singleGlazingAreaUnits
            // 
            this.singleGlazingAreaUnits.Location = new System.Drawing.Point(266, 387);
            this.singleGlazingAreaUnits.Name = "singleGlazingAreaUnits";
            this.singleGlazingAreaUnits.Size = new System.Drawing.Size(40, 20);
            this.singleGlazingAreaUnits.TabIndex = 16;
            this.singleGlazingAreaUnits.Text = "m^2";
            // 
            // errorProvider1
            // 
            this.errorProvider1.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.AlwaysBlink;
            this.errorProvider1.ContainerControl = this;
            // 
            // errorProvider2
            // 
            this.errorProvider2.BlinkStyle = System.Windows.Forms.ErrorBlinkStyle.AlwaysBlink;
            this.errorProvider2.ContainerControl = this;
            // 
            // errorProvider3
            // 
            this.errorProvider3.ContainerControl = this;
            // 
            // TopWindowSpecs
            // 
            this.AutoScaleBaseSize = new System.Drawing.Size(5, 13);
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(762, 890);
            this.Controls.Add(this.inputsSingle);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.roofInputsGB);
            this.Controls.Add(this.SpaceCancelBut);
            this.Controls.Add(this.SpaceOKBut);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "TopWindowSpecs";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Window calculator";
            this.Load += new System.EventHandler(this.TopWindowSpace_Load);
            this.roofInputsGB.ResumeLayout(false);
            this.roofInputsGB.PerformLayout();
            this.groupBox2.ResumeLayout(false);
            this.groupBox2.PerformLayout();
            this.inputsSingle.ResumeLayout(false);
            this.inputsSingle.PerformLayout();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider2)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.errorProvider3)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        #endregion

        private void TopWindowSpace_Load(object sender, System.EventArgs e) //FADE: Initialize fields
        {
            singleNumbofWindows.Text = globalcontrol.numofwindows.ToString(); // = 12
            singleGlazingArea.Text = globalcontrol.singleglazingarea.ToString(); // = 7 m^2
            singleUpperOpeningArea.Text = globalcontrol.singleOpeningArea.ToString(); // = 3.5 m^2
            upperOpeningHeight.Text = globalcontrol.UpperHeight.ToString(); //1.75 m
            doubleOpeningsCheck.Checked = globalcontrol.doubleOpeningsCheck; //false
            if (doubleOpeningsCheck.Checked) //FADE: Activate double window options
            {
                lowerOpeningAreaLabel.Enabled = true;
                singleLowerOpeningArea.Enabled = true;
                lowerOpeningAreaUnits.Enabled = true;
                deltaHLabel.Enabled = true;
                deltaH.Enabled = true;
                deltaHUnits.Enabled = true;
            }
            else
            {
                lowerOpeningAreaLabel.Enabled = false;
                singleLowerOpeningArea.Enabled = false;
                lowerOpeningAreaUnits.Enabled = false;
                deltaHLabel.Enabled = false;
                deltaH.Enabled = false;
                deltaHUnits.Enabled = false;
            }
            singleLowerOpeningArea.Text = globalcontrol.singleLowerOpeningArea.ToString(); //1 m^2
            deltaH.Text = globalcontrol.topSpacingLength.ToString(); //1 m
            numbofRoofOpenings.Text = globalcontrol.numberofroofopenings.ToString(); //3
            roofOpeningArea.Text = globalcontrol.singleUpperOpeningArea.ToString(); //15 m^2
            if (datapackage.bldgtype == 0 || datapackage.bldgtype == 1)
            {
                numbofRoofOpenings.Enabled = true;
                roofOpeningArea.Enabled = true;
                roofInputsGB.Enabled = true;
            }
            else
            {
                numbofRoofOpenings.Enabled = false;
                roofOpeningArea.Enabled = false;
                roofInputsGB.Enabled = false;
            }
            updateSummary();
        }

        private void updateSummary()
        {
            double tgasw = Double.Parse(singleNumbofWindows.Text) * Double.Parse(singleGlazingArea.Text); //FADE: Total glazing area per floor
            double toasw = Double.Parse(singleNumbofWindows.Text) * Double.Parse(singleUpperOpeningArea.Text); //FADE: Total opening area per floor
            double swtowr = tgasw / (datapackage.floorlength * datapackage.floorheight) * 100; //FADE: Side window to wall ratio
            totGlazAreaSWTB.Text = tgasw.ToString("#0.0");
            totOpAreaSWTB.Text = toasw.ToString("#0.0");
            sWtoWallRatioTB.Text = swtowr.ToString("#0.0");
            if (swtowr > 100)
            {
                inputsOkay9 = false;
                errorProvider1.SetError(swtwrLB, "Window area is larger than available wall area");
            }
            else
            {
                inputsOkay9 = true;
                errorProvider1.Clear();
            }
            if (toasw > tgasw)
            {
                inputsOkay10 = false;
                errorProvider2.SetError(tOASWUnitsLB, "Opening area is larger than glazing area");
            }
            else
            {
                inputsOkay10 = true;
                errorProvider2.Clear();
            }
            double toarw = Double.Parse(numbofRoofOpenings.Text) * Double.Parse(roofOpeningArea.Text); //FADE: Total roof opening area
            double rwtorr = toarw / (datapackage.floorlength * datapackage.atriumwidth) * 100; //FADE: Roof window to roof ratio
            double rwtowr = toarw / (datapackage.floorlength * datapackage.floorheight) * 100; //FADE: Roof window to wall ratio
            totOpAreaRWTB.Text = toarw.ToString("#0.0");
            rWtoRoofRatioTB.Text = rwtorr.ToString("#0.0");
            rWtoWallRatioTB.Text = rwtowr.ToString("#0.0");
            if (rwtorr > 100)
            {
                inputsOkay11 = false;
                errorProvider3.SetError(rwtrrLB, "Window area is larger than available roof area");
            }
            else
            {
                inputsOkay11 = true;
                errorProvider3.Clear();
            }
        }

        private void SpaceOKBut_Click(object sender, System.EventArgs e)
        {
            if ((!inputsOkay1 || !inputsOkay2 || !inputsOkay3 || !inputsOkay4) || (doubleOpeningsCheck.Checked && (!inputsOkay5 || !inputsOkay6)) || (roofInputsGB.Enabled && (!inputsOkay7 || !inputsOkay8)))
            {
                DialogResult res = MessageBox.Show("Some of your inputs are invalid! Area and number inputs should be non-negative numbers", "Warning!", MessageBoxButtons.OK, MessageBoxIcon.Error);
                if (res == DialogResult.OK)
                {
                    return;
                }
            }
            else
            {
                globalcontrol.numofwindows = int.Parse(singleNumbofWindows.Text);
                globalcontrol.singleglazingarea = Double.Parse(singleGlazingArea.Text);
                globalcontrol.singleOpeningArea = Double.Parse(singleUpperOpeningArea.Text);
                globalcontrol.UpperHeight = Double.Parse(upperOpeningHeight.Text);
                globalcontrol.doubleOpeningsCheck = doubleOpeningsCheck.Checked;
                if (doubleOpeningsCheck.Checked)
                {
                    globalcontrol.singleLowerOpeningArea = Double.Parse(singleLowerOpeningArea.Text);
                    globalcontrol.topSpacingLength = Double.Parse(deltaH.Text);
                }
                if (roofInputsGB.Enabled)
                {
                    globalcontrol.numberofroofopenings = int.Parse(numbofRoofOpenings.Text);
                    globalcontrol.singleUpperOpeningArea = Double.Parse(roofOpeningArea.Text);
                }
                this.Close();
            }
        }



        private void SpaceCancelBut_Click(object sender, System.EventArgs e)
        {
            this.Close();
        }

        private void doubleOpeningsCheck_CheckedChanged(object sender, EventArgs e)
        {
            if (doubleOpeningsCheck.Checked) //FADE: Activate double window options
            {
                lowerOpeningAreaLabel.Enabled = true;
                singleLowerOpeningArea.Enabled = true;
                lowerOpeningAreaUnits.Enabled = true;
                deltaHLabel.Enabled = true;
                deltaH.Enabled = true;
                deltaHUnits.Enabled = true;
            }
            else
            {
                lowerOpeningAreaLabel.Enabled = false;
                singleLowerOpeningArea.Enabled = false;
                lowerOpeningAreaUnits.Enabled = false;
                deltaHLabel.Enabled = false;
                deltaH.Enabled = false;
                deltaHUnits.Enabled = false;
            }
        }

        private void singleNumbofWindows_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (int.Parse(singleNumbofWindows.Text) <= 0)
                {
                    throw new FormatException();
                }
                else
                {
                    inputsOkay1 = true;
                    updateSummary();
                    singleNumbofWindows.BackColor = Color.White;
                }
            }
            catch (FormatException)
            {
                inputsOkay1 = false;
                singleNumbofWindows.BackColor = Color.PaleVioletRed;
            }
        }

        private void singleGlazingArea_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (Double.Parse(singleGlazingArea.Text) <= 0)
                {
                    throw new FormatException();
                }
                else
                {
                    inputsOkay2 = true;
                    updateSummary();
                    singleGlazingArea.BackColor = Color.White;
                }
            }
            catch (FormatException)
            {
                inputsOkay2 = false;
                singleGlazingArea.BackColor = Color.PaleVioletRed;
            }
        }

        private void singleUpperOpeningArea_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (Double.Parse(singleUpperOpeningArea.Text) <= 0)
                {
                    throw new FormatException();
                }
                else
                {
                    inputsOkay3 = true;
                    updateSummary();
                    singleUpperOpeningArea.BackColor = Color.White;
                }
            }
            catch (FormatException)
            {
                inputsOkay3 = false;
                singleUpperOpeningArea.BackColor = Color.PaleVioletRed;
            }
        }

        private void upperOpeningHeight_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (Double.Parse(upperOpeningHeight.Text) >= datapackage.floorheight || Double.Parse(upperOpeningHeight.Text) <= 0)
                {
                    throw new FormatException();
                }
                else
                {
                    inputsOkay4 = true;
                    upperOpeningHeight.BackColor = Color.White;
                }
            }
            catch (FormatException)
            {
                inputsOkay4 = false;
                upperOpeningHeight.BackColor = Color.PaleVioletRed;
            }
        }

        private void singleLowerOpeningArea_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (Double.Parse(singleLowerOpeningArea.Text) > Double.Parse(singleUpperOpeningArea.Text) || Double.Parse(singleLowerOpeningArea.Text) <= 0)
                {
                    throw new FormatException();
                }
                else
                {
                    inputsOkay5 = true;
                    singleLowerOpeningArea.BackColor = Color.White;
                }

            }
            catch (FormatException)
            {
                inputsOkay5 = false;
                singleLowerOpeningArea.BackColor = Color.PaleVioletRed;
            }
        }

        private void deltaH_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (Double.Parse(deltaH.Text) >= Double.Parse(upperOpeningHeight.Text) || Double.Parse(deltaH.Text) <= 0)
                {
                    throw new FormatException();
                }
                else
                {
                    inputsOkay6 = true;
                    deltaH.BackColor = Color.White;
                }

            }
            catch (FormatException)
            {
                inputsOkay6 = false;
                deltaH.BackColor = Color.PaleVioletRed;
            }
        }

        private void numbofRoofOpenings_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (int.Parse(numbofRoofOpenings.Text) <= 0)
                {
                    throw new FormatException();
                }
                else
                {
                    inputsOkay7 = true;
                    updateSummary();
                    numbofRoofOpenings.BackColor = Color.White;
                }
            }
            catch (FormatException)
            {
                inputsOkay7 = false;
                numbofRoofOpenings.BackColor = Color.PaleVioletRed;
            }
        }

        private void roofOpeningArea_TextChanged(object sender, EventArgs e)
        {
            try
            {
                if (Double.Parse(roofOpeningArea.Text) <= 0)
                {
                    throw new FormatException();
                }
                else
                {
                    inputsOkay8 = true;
                    updateSummary();
                    numbofRoofOpenings.BackColor = Color.White;
                }
            }
            catch (FormatException)
            {
                inputsOkay8 = false;
                singleUpperOpeningArea.BackColor = Color.PaleVioletRed;
            }
        }


        //FADE: What is this?
        //ScreenShot method as taken from:
        //http://www.switchonthecode.com/tutorials/taking-some-screenshots-with-csharp
        //public Bitmap ScreenShot()
        //{
        //    Rectangle totalSize = Rectangle.Empty;

        //    foreach (Screen s in Screen.AllScreens)
        //        totalSize = Rectangle.Union(totalSize, s.Bounds);

        //    Bitmap screenShotBMP = new Bitmap(totalSize.Width, totalSize.Height,
        //        PixelFormat.Format32bppArgb);

        //    Graphics screenShotGraphics = Graphics.FromImage(screenShotBMP);

        //    screenShotGraphics.CopyFromScreen(totalSize.X, totalSize.Y,
        //        0, 0, totalSize.Size, CopyPixelOperation.SourceCopy);

        //    screenShotGraphics.Dispose();

        //    return screenShotBMP;
        //}
    }
}
