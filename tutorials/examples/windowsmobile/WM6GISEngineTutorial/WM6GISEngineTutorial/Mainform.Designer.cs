namespace WM6GISEngineTutorial
{
    partial class Mainform
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;
        private System.Windows.Forms.MainMenu mainMenu1;

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
            this.mainMenu1 = new System.Windows.Forms.MainMenu();
            this.menuItem1 = new System.Windows.Forms.MenuItem();
            this.mnuMapType = new System.Windows.Forms.MenuItem();
            this.mnuMapType_GoogleMap = new System.Windows.Forms.MenuItem();
            this.mnuMapType_BingMap = new System.Windows.Forms.MenuItem();
            this.mnuMapType_MapAbc = new System.Windows.Forms.MenuItem();
            this.mnuZoomIn = new System.Windows.Forms.MenuItem();
            this.mnuZoomOut = new System.Windows.Forms.MenuItem();
            this.mnuFindAddress = new System.Windows.Forms.MenuItem();
            this.mnuGetDirection = new System.Windows.Forms.MenuItem();
            this.mnuLocalSearch = new System.Windows.Forms.MenuItem();
            this.mnuIPSearch = new System.Windows.Forms.MenuItem();
            this.mnuReverseGeocoding = new System.Windows.Forms.MenuItem();
            this.mnuServiceType = new System.Windows.Forms.MenuItem();
            this.mnuServiceType_Google = new System.Windows.Forms.MenuItem();
            this.mnuServiceType_MapAbc = new System.Windows.Forms.MenuItem();
            this.mnuServiceType_CloudMade = new System.Windows.Forms.MenuItem();
            this.SuspendLayout();
            // 
            // mainMenu1
            // 
            this.mainMenu1.MenuItems.Add(this.menuItem1);
            // 
            // menuItem1
            // 
            this.menuItem1.MenuItems.Add(this.mnuMapType);
            this.menuItem1.MenuItems.Add(this.mnuZoomIn);
            this.menuItem1.MenuItems.Add(this.mnuZoomOut);
            this.menuItem1.MenuItems.Add(this.mnuFindAddress);
            this.menuItem1.MenuItems.Add(this.mnuGetDirection);
            this.menuItem1.MenuItems.Add(this.mnuLocalSearch);
            this.menuItem1.MenuItems.Add(this.mnuIPSearch);
            this.menuItem1.MenuItems.Add(this.mnuReverseGeocoding);
            this.menuItem1.MenuItems.Add(this.mnuServiceType);
            this.menuItem1.Text = "Examples";
            // 
            // mnuMapType
            // 
            this.mnuMapType.MenuItems.Add(this.mnuMapType_GoogleMap);
            this.mnuMapType.MenuItems.Add(this.mnuMapType_BingMap);
            this.mnuMapType.MenuItems.Add(this.mnuMapType_MapAbc);
            this.mnuMapType.Text = "MapType";
            // 
            // mnuMapType_GoogleMap
            // 
            this.mnuMapType_GoogleMap.Text = "GoogleMap";
            this.mnuMapType_GoogleMap.Click += new System.EventHandler(this.mnuMapType_GoogleMap_Click);
            // 
            // mnuMapType_BingMap
            // 
            this.mnuMapType_BingMap.Text = "BingMap";
            this.mnuMapType_BingMap.Click += new System.EventHandler(this.mnuMapType_BingMap_Click);
            // 
            // mnuMapType_MapAbc
            // 
            this.mnuMapType_MapAbc.Text = "MapAbc";
            this.mnuMapType_MapAbc.Click += new System.EventHandler(this.mnuMapType_MapAbc_Click);
            // 
            // mnuZoomIn
            // 
            this.mnuZoomIn.Text = "Zoom In";
            this.mnuZoomIn.Click += new System.EventHandler(this.mnuZoomIn_Click);
            // 
            // mnuZoomOut
            // 
            this.mnuZoomOut.Text = "Zoom Out";
            this.mnuZoomOut.Click += new System.EventHandler(this.mnuZoomOut_Click);
            // 
            // mnuFindAddress
            // 
            this.mnuFindAddress.Text = "Find Address";
            this.mnuFindAddress.Click += new System.EventHandler(this.mnuFindAddress_Click);
            // 
            // mnuGetDirection
            // 
            this.mnuGetDirection.Text = "Get Direction";
            this.mnuGetDirection.Click += new System.EventHandler(this.mnuGetDirection_Click);
            // 
            // mnuLocalSearch
            // 
            this.mnuLocalSearch.Text = "Local Search";
            this.mnuLocalSearch.Click += new System.EventHandler(this.mnuLocalSearch_Click);
            // 
            // mnuIPSearch
            // 
            this.mnuIPSearch.Text = "IP Search";
            this.mnuIPSearch.Click += new System.EventHandler(this.mnuIPSearch_Click);
            // 
            // mnuReverseGeocoding
            // 
            this.mnuReverseGeocoding.Text = "Reverse Geocoding";
            this.mnuReverseGeocoding.Click += new System.EventHandler(this.mnuReverseGeocoding_Click);
            // 
            // mnuServiceType
            // 
            this.mnuServiceType.MenuItems.Add(this.mnuServiceType_Google);
            this.mnuServiceType.MenuItems.Add(this.mnuServiceType_MapAbc);
            this.mnuServiceType.MenuItems.Add(this.mnuServiceType_CloudMade);
            this.mnuServiceType.Text = "Map Service Type";
            // 
            // mnuServiceType_Google
            // 
            this.mnuServiceType_Google.Text = "Google Map Service";
            this.mnuServiceType_Google.Click += new System.EventHandler(this.mnuServiceType_Google_Click);
            // 
            // mnuServiceType_MapAbc
            // 
            this.mnuServiceType_MapAbc.Text = "MapAbc Map Service";
            this.mnuServiceType_MapAbc.Click += new System.EventHandler(this.mnuServiceType_MapAbc_Click);
            // 
            // mnuServiceType_CloudMade
            // 
            this.mnuServiceType_CloudMade.Text = "CloudMade Map Service";
            this.mnuServiceType_CloudMade.Click += new System.EventHandler(this.mnuServiceType_CloudMade_Click);
            // 
            // Mainform
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(192F, 192F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Dpi;
            this.AutoScroll = true;
            this.ClientSize = new System.Drawing.Size(480, 536);
            this.Location = new System.Drawing.Point(0, 52);
            this.Menu = this.mainMenu1;
            this.Name = "Mainform";
            this.Text = "Guidebee Map Demo";
            this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.Mainform_MouseUp);
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.Mainform_Paint);
            this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Mainform_MouseDown);
            this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.Mainform_MouseMove);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.MenuItem menuItem1;
        private System.Windows.Forms.MenuItem mnuMapType;
        private System.Windows.Forms.MenuItem mnuMapType_GoogleMap;
        private System.Windows.Forms.MenuItem mnuMapType_BingMap;
        private System.Windows.Forms.MenuItem mnuMapType_MapAbc;
        private System.Windows.Forms.MenuItem mnuZoomIn;
        private System.Windows.Forms.MenuItem mnuZoomOut;
        private System.Windows.Forms.MenuItem mnuFindAddress;
        private System.Windows.Forms.MenuItem mnuGetDirection;
        private System.Windows.Forms.MenuItem mnuLocalSearch;
        private System.Windows.Forms.MenuItem mnuIPSearch;
        private System.Windows.Forms.MenuItem mnuReverseGeocoding;
        private System.Windows.Forms.MenuItem mnuServiceType;
        private System.Windows.Forms.MenuItem mnuServiceType_Google;
        private System.Windows.Forms.MenuItem mnuServiceType_MapAbc;
        private System.Windows.Forms.MenuItem mnuServiceType_CloudMade;
    }
}

