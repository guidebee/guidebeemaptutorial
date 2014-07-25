//------------------------------------------------------------------------------
//                         COPYRIGHT 2010 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY 
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 20SEP2010  James Shen                 	          Code review
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- IMPORTS ------------------------------------
using System;
using System.Drawing;
using System.Globalization;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using GuidebeeMapDemo.Drawing;
using Mapdigit.Gis;
using Mapdigit.Gis.Drawing;
using Mapdigit.Gis.Geometry;
using Mapdigit.Gis.Raster;
using Mapdigit.Gis.Service;
using Mapdigit.Licence;
using Mapdigit.Util;

//--------------------------------- PACKAGE ------------------------------------

namespace GuidebeeMapDemo
{
    /// <summary>
    /// Main Form.
    /// </summary>
    public partial class MainWindow : Form,  IMapDrawingListener,
                                      IReaderListener, IGeocodingListener, IRoutingListener
    {
        /// <summary>
        /// map download manager.
        /// </summary>
        private readonly MapTileDownloadManager _mapTileDownloadManager;
        /// <summary>
        /// raster map interface
        /// </summary>
        private readonly RasterMap _rasterMap;
        /// <summary>
        /// map image.
        /// </summary>
        private readonly IImage _mapImage;
        /// <summary>
        /// graphics for map image.
        /// </summary>
        private readonly IGraphics _mapGraphics;

        /// <summary>
        /// previous x
        /// </summary>
        private int _oldX;

        /// <summary>
        /// previous y
        /// </summary>
        private int _oldY;
        private delegate void UpdateInfo(string message);

        /// <summary>
        /// map type.
        /// </summary>
        private int _mapType = MapType.MicrosoftChina;

        /// <summary>
        /// Updates the status.
        /// </summary>
        /// <param name="messsage">The messsage.</param>
        private void UpdateStatus(string messsage)
        {
            if (InvokeRequired)
            {
                BeginInvoke(new UpdateInfo(UpdateStatus), messsage);

            }
            else
            {
                lblStatus.Text = messsage;
                picMapCanvas.Invalidate();
            }
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="MainWindow"/> class.
        /// </summary>
        public MainWindow()
        {
            InitializeComponent();
            

            //set the map licence info.
            LicenceManager licenceManager = LicenceManager.GetInstance();
            long []keys= {-0x8b9d5bd05f46354L,-0x4e41fd81b7d38cc3L,
                             0x1b071d669ee9b833L,0x5aa83843b0672fc8L,
                             -0x109dc3c9f0722846L,-0x48da5fe25dd815dfL,};
            licenceManager.AddLicence("GuidebeeMap_DotNet", keys);

            //optional, get the tile url from server.
            MapType.UpdateMapTileUrl();
            //MapType.SetCustomMapTileUrl(new CustomMapType()); 
            

            MapLayer.SetAbstractGraphicsFactory(NETGraphicsFactory.GetInstance());
            //set how many download threads
            MapConfiguration.SetParameter(MapConfiguration.WorkerThreadNumber, 32);
            _mapImage = MapLayer.GetAbstractGraphicsFactory().CreateImage(768, 768);
            _mapGraphics = _mapImage.GetGraphics();

            _mapTileDownloadManager = new MapTileDownloadManager(this);
            _rasterMap = new RasterMap(2048, 2048, _mapType, _mapTileDownloadManager);
            
            _rasterMap.SetScreenSize(768, 768);
            _mapTileDownloadManager.Start();
            _rasterMap.SetMapDrawingListener(this);
            _rasterMap.SetGeocodingListener(this);
            _rasterMap.SetRoutingListener(this);

        }



        /// <summary>
        /// Read progress notification.
        /// </summary>
        /// <param name="context">message context, can be any object.</param>
        /// <param name="bytes">the number of bytes has been read.</param>
        /// <param name="total">total bytes to be read.Total will be zero if not available</param>
        public void ReadProgress(object context, int bytes, int total)
        {
            if (total != 0)
            {
                UpdateStatus("Reading ..." + (int)((bytes / (double)total) * 100.0) + "%");
            }
        }

        /// <summary>
        /// Write progress notification.
        /// </summary>
        /// <param name="context">message context, can be any object.</param>
        /// <param name="bytes">the number of bytes has been written.</param>
        /// <param name="total">total bytes to be written.Total will be zero if not available .</param>
        public void WriteProgress(object context, int bytes, int total)
        {

        }

       

        /// <summary>
        /// Dones the specified context.
        /// </summary>
        /// <param name="context">The context.</param>
        /// <param name="rawResult">The raw result.</param>
        public void Done(object context, string rawResult)
        {

        }


        /// <summary>
        /// Dones the specified query.
        /// </summary>
        /// <param name="query">message query context(string)</param>
        /// <param name="result">the result object.</param>
        public void Done(string query, MapPoint[] result)
        {
            if (result != null)
            {
                _rasterMap.SetCenter(result[0].Point, 15, _rasterMap.GetMapType());
            }
            else
            {
                UpdateStatus("Address not found!");
            }
        }

        /// <summary>
        /// Dones the specified query.
        /// </summary>
        /// <param name="query">message query context(string).</param>
        /// <param name="result">The result.</param>
        public void Done(string query, MapDirection result)
        {
            if (result != null)
            {
                _rasterMap.SetMapDirection(result);
                _rasterMap.Resize(result.Bound);
            }
        }

        /// <summary>
        /// Read progress notification.
        /// </summary>
        /// <param name="bytes">the number of bytes has been read.</param>
        /// <param name="total">total bytes to be read.Total will be zero if not available
        /// (content-length header not set)</param>
        public void ReadProgress(int bytes, int total)
        {
            if (total != 0)
            {
                UpdateStatus("Reading ...");
            }
        }


        /// <summary>
        /// the drawing is done.
        /// </summary>
        public void Done()
        {

            UpdateStatus("");


        }

        private void MainWindow_Load(object sender, EventArgs e)
        {
            var center = new GeoLatLng(32.0616667, 118.7777778);
            _rasterMap.SetCenter(center, 15, _rasterMap.GetMapType());
        }

        private void MainWindow_FormClosed(object sender, FormClosedEventArgs e)
        {
            _mapTileDownloadManager.Stop();

        }

        private void btnUp_Click(object sender, EventArgs e)
        {
            _rasterMap.PanDirection(0, 64);
        }

        private void btnDown_Click(object sender, EventArgs e)
        {

            _rasterMap.PanDirection(0, -64);
        }

        private void btnLeft_Click(object sender, EventArgs e)
        {
            _rasterMap.PanDirection(64, 0);
        }

        private void btnRight_Click(object sender, EventArgs e)
        {
            _rasterMap.PanDirection(-64, 0);
        }


        private void btnZoomIn_Click(object sender, EventArgs e)
        {
            _rasterMap.ZoomIn();

        }

        private void btnZoomOut_Click(object sender, EventArgs e)
        {
            _rasterMap.ZoomOut();

        }

        private void btnExit_Click(object sender, EventArgs e)
        {
            _mapTileDownloadManager.Stop();
            Application.Exit();
        }

        private readonly UTF8Encoding _encoding = new UTF8Encoding();

        private void btnGo_Click(object sender, EventArgs e)
        {
            if (!string.IsNullOrEmpty(txtAddress.Text))
            {
                string name = txtAddress.Text;
               _rasterMap.GetLocations(name);

            }
        }




        private void picMapCanvas_MouseMove(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Left)
            {
                _rasterMap.PanDirection(e.X - _oldX, e.Y - _oldY);

            }

            _oldX = e.X;
            _oldY = e.Y;

        }

        private void picMapCanvas_MouseDown(object sender, MouseEventArgs e)
        {

            _oldX = e.X;
            _oldY = e.Y;

        }






        private void cboMapType_SelectedIndexChanged(object sender, EventArgs e)
        {
            int mapType = MapType.MicrosoftMap;
            _rasterMap.SetMaxZoomLevel(17);
            switch (cboMapType.SelectedIndex)
            {
                case 0:
                    mapType = MapType.GoogleMap;
                    break;
                case 1:
                    mapType = MapType.GoogleChina;
                    break;
                case 2:
                    mapType = MapType.MicrosoftMap;
                    break;
                case 3:
                    mapType = MapType.MicrosoftChina;
                    break;
                case 4:
                    mapType = MapType.MicrosoftSatellite;
                    break;
                case 5:
                    mapType = MapType.MicrosoftHybrid;
                    break;
                case 6:
                    mapType = MapType.MapabcChina;
                    //_rasterMap.SetMaxZoomLevel(21);
                    break;
            }
            _rasterMap.SetMapType(mapType);
        }




        private void picMapCanvas_Paint(object sender, PaintEventArgs e)
        {
            _rasterMap.Paint(_mapGraphics);
            e.Graphics.DrawImage((Bitmap)_mapImage.GetNativeImage(), 0, 0);
        }

        private void btnGetDirection_Click(object sender, EventArgs e)
        {
            if (txtAddress1.Text.Length != 0 && txtAddress2.Text.Length != 0)
            {
                String name1 = txtAddress1.Text;
                String name2 = txtAddress2.Text;
                
                _rasterMap.GetDirections("from: " + name1 + " to: " + name2);
            }
        }
    }

    class CustomMapType : ICustomMapType
    {
        public string GetTileUrl(int mtype, int x, int y, int zoomLevel)
        {
            string returnURL = string.Empty;
            switch (mtype)
            {
                case MapType.GenericMaptype7:
                    returnURL = string.Format("http://www.nearmap.com/maps/hl=en&x={0}&y={1}&z={2}&nml=MapT", x, y,
                                              zoomLevel);
                    break;
                case MapType.GenericMaptype6:
                    returnURL = string.Format("http://www.nearmap.com/maps/hl=en&x={0}&y={1}&z={2}&nml=vert", x, y,
                                              zoomLevel);
                    break;
            }
            return returnURL;
        }
    }
}