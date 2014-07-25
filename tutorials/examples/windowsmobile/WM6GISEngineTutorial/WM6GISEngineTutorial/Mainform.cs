using System;

using System.Windows.Forms;
using Mapdigit.Gis;
using Mapdigit.Gis.Drawing;
using Mapdigit.Gis.Geometry;
using Mapdigit.Gis.Raster;
using Mapdigit.Gis.Service;
using Mapdigit.Licence;
using WM6GISEngineTutorial.Drawing;
using System.Drawing;

namespace WM6GISEngineTutorial
{
    public partial class Mainform : Form, IMapDrawingListener, IReaderListener,
         IRoutingListener, IGeocodingListener, IIpAddressGeocodingListener, IReverseGeocodingListener 
    {
        /// <summary>
        /// map tile downloader manager
        /// </summary>
        private readonly MapTileDownloadManager _mapTileDownloadManager;
        /// <summary>
        /// raster map.
        /// </summary>
        private readonly RasterMap _rasterMap;
        /// <summary>
        /// map image.
        /// </summary>
        private readonly IImage _mapImage;
        /// <summary>
        /// map graphics object.
        /// </summary>
        private IGraphics _mapGraphics;

        /// <summary>
        /// map type.
        /// </summary>
        private int _mapType = MapType.MicrosoftChina;

        private int _oldX;
        private int _oldY;
        private bool _isPanMode;

        private delegate void UpdateInfo(string message);

        public Mainform()
        {
            InitializeComponent();
            //set the licence info.
            LicenceManager licenceManager = LicenceManager.GetInstance();
            long[] keys = { -0x8b9d5bd05f46354L, -0x4e41fd81b7d38cc3L, 
                              0x1b071d669ee9b833L, 0x5aa83843b0672fc8L,
                              -0x109dc3c9f0722846L, -0x48da5fe25dd815dfL, };
            licenceManager.AddLicence("GuidebeeMap_DotNet", keys);

            //optional, get the tile url from server.
            MapType.UpdateMapTileUrl();

            MapLayer.SetAbstractGraphicsFactory(NETGraphicsFactory.GetInstance());
            MapConfiguration.SetParameter(MapConfiguration.WorkerThreadNumber, 4);
            _mapImage = MapLayer.GetAbstractGraphicsFactory().CreateImage(Width, Height);
            _mapTileDownloadManager = new MapTileDownloadManager(this);
            _rasterMap = new RasterMap(1024, 1024, _mapType, _mapTileDownloadManager);
            _rasterMap.SetScreenSize(Width, Height);
            _mapTileDownloadManager.Start();
            _rasterMap.SetMapDrawingListener(this);
            _rasterMap.SetRoutingListener(this);
            _rasterMap.SetGeocodingListener(this);
            _rasterMap.SetIpAddressGeocodingListener(this);
             GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
            _rasterMap.SetCenter(center, 15, _mapType);
        }


        private void Mainform_Paint(object sender, PaintEventArgs e)
        {
            Graphics graphics = e.Graphics;
            Bitmap image = (Bitmap)_mapImage.GetNativeImage();
            graphics.DrawImage(image, 0, 0);
        }

        private void PanMap(int x, int y)
        {
            int dx = x - _oldX;
            int dy = y - _oldY;
            _rasterMap.PanDirection(dx, dy);

        }

        private void UpdateStatus(string messsage)
        {
            if (InvokeRequired)
            {
                BeginInvoke(new UpdateInfo(UpdateStatus), messsage);

            }
            else
            {
                _mapGraphics = _mapImage.GetGraphics();
                _rasterMap.Paint(_mapGraphics);
                Invalidate();
                Console.WriteLine(messsage);
            }
        }

        public void Done()
        {
            UpdateStatus("");
        }



        public void Done(string query, MapDirection result)
        {
            if (result != null)
            {
                _rasterMap.SetMapDirection(result);
                _rasterMap.Resize(result.Bound);

               // _rasterMap.SetZoom(15);
               // _rasterMap.PanTo(result.Routes[0].StartGeocode.Point);
            }
        }

        public void Done(string query, IpAddressLocation result)
        {
            if (result != null && result.Error.Length == 0
                && result.Longitude.Length > 0
                && result.Latitude.Length > 0)
            {
                try
                {

                    MapPoint mapPoint = new MapPoint();
                    String latLng = "[" + result.Longitude + ","
                            + result.Latitude + ",0]";
                    mapPoint.Point = DigitalMap.FromStringToLatLng(latLng);
                    mapPoint.Name = (result.Organization);
                    mapPoint.ObjectNote = (result.City + " " + result.Country);
                    _rasterMap.PanTo(mapPoint.Point);
                }
                catch (Exception e)
                {

                    result.Error = "IP_NOT_FOUND";
                }
            }
        }

        public void ReadProgress(int bytes, int total)
        {
            if (total != 0)
            {
                UpdateStatus("Reading ...");
            }
        }

        public void Done(string query, MapPoint[] result)
        {
            if (result != null)
            {
                _rasterMap.PanTo(result[0].Point);
                for (int i = 0; i < result.Length; i++)
                {
                    Console.WriteLine(result[i].ObjectNote);
                }

            }
        }

        void IGeocodingListener.ReadProgress(int bytes, int total)
        {
            if (total != 0)
            {
                UpdateStatus("Reading ...");
            }
        }

        void IReaderListener.ReadProgress(int bytes, int total)
        {
            if (total != 0)
            {
                UpdateStatus("Reading ...");
            }
        }

        private void Mainform_MouseDown(object sender, MouseEventArgs e)
        {
            _oldX = e.X;
            _oldY = e.Y;
            _isPanMode = true;
        }

        private void Mainform_MouseMove(object sender, MouseEventArgs e)
        {
            if (_isPanMode)
            {
                PanMap(e.X, e.Y);
                _oldX = e.X;
                _oldY = e.Y;
            }
        }

        private void Mainform_MouseUp(object sender, MouseEventArgs e)
        {
            _oldX = e.X;
            _oldY = e.Y;

            _isPanMode = false;
        }

        private void mnuMapType_GoogleMap_Click(object sender, EventArgs e)
        {
            _rasterMap.SetMapType(MapType.GoogleChina);
        }

        private void mnuMapType_BingMap_Click(object sender, EventArgs e)
        {
            _rasterMap.SetMapType(MapType.MicrosoftChina);
        }

        private void mnuMapType_MapAbc_Click(object sender, EventArgs e)
        {
            _rasterMap.SetMapType(MapType.MapabcChina);
        }

        private void mnuZoomIn_Click(object sender, EventArgs e)
        {
            _rasterMap.ZoomIn();
        }

        private void mnuZoomOut_Click(object sender, EventArgs e)
        {
            _rasterMap.ZoomOut();
        }

        private void mnuFindAddress_Click(object sender, EventArgs e)
        {
            String name = "南京林业大学";
            _rasterMap.GetLocations(name); 
        }

        private void mnuGetDirection_Click(object sender, EventArgs e)
        {
            String name1 = "南京";
            String name2 = "北京";
            _rasterMap.GetDirections("from: " + name1 + " to: " + name2);

        }

        private void mnuLocalSearch_Click(object sender, EventArgs e)
        {
            String name = "宾馆";
            GeoLatLng screenCenter = _rasterMap.GetScreenCenter();
            _rasterMap.GetLocations(name, 0, screenCenter,
                    _rasterMap.GetScreenBounds(screenCenter));
        }

        private void mnuIPSearch_Click(object sender, EventArgs e)
        {
            _rasterMap.GetIpLocations("58.192.32.1");
        }

        private void mnuReverseGeocoding_Click(object sender, EventArgs e)
        {
            _rasterMap.GetReverseLocations("32.061699,118.777802");
        }

        private void GetDirection()
        {
            GeoLatLng latLng1 = new GeoLatLng(32.0418381, 118.7788905);
            GeoLatLng latLng2 = new GeoLatLng(39.11643, 117.180908);
            _rasterMap.GetDirections(new GeoLatLng[] { latLng1, latLng2 });
        }

        private void mnuServiceType_Google_Click(object sender, EventArgs e)
        {
            _rasterMap.SetCurrentMapService(DigitalMapService.GoogleMapService);
            _rasterMap.SetRoutingListener(this);
            GetDirection();
        }

        private void mnuServiceType_MapAbc_Click(object sender, EventArgs e)
        {
            _rasterMap.SetCurrentMapService(DigitalMapService.MapabcMapService);
            _rasterMap.SetRoutingListener(this);
            GetDirection();
        }

        private void mnuServiceType_CloudMade_Click(object sender, EventArgs e)
        {
            _rasterMap.SetCurrentMapService(DigitalMapService.CloudmadeMapService);
            _rasterMap.SetRoutingListener(this);
            GetDirection();
        }

    }
}