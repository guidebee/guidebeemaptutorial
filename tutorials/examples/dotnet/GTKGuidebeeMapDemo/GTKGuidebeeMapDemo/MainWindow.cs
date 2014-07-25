using System.Drawing;
using System.Text;
using System;
using Gtk;
using GTKMapDemo.Drawing;
using Mapdigit.Gis;
using Mapdigit.Gis.Drawing;
using Mapdigit.Gis.Geometry;
using Mapdigit.Gis.Raster;
using Mapdigit.Gis.Service;
using Mapdigit.Licence;



namespace GTKMapDemo
{
	public partial class MainWindow : Gtk.Window,IMapDrawingListener,
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

        private int _mapType = MapType.GoogleChina;
        protected virtual void OnPicMapCanvasExposeEvent (object o, Gtk.ExposeEventArgs args)
		{
			DrawingArea area = (DrawingArea) o;

			using(Graphics g=Gtk.DotNet.Graphics.FromDrawable(area.GdkWindow)){
				_rasterMap.Paint(_mapGraphics);
				g.DrawImage((Bitmap)_mapImage.GetNativeImage(),0,0);
			}
		}
		
		
		
		public MainWindow () : base(Gtk.WindowType.Toplevel)
		{
			this.Build ();
			picMapCanvas.ExposeEvent+=OnPicMapCanvasExposeEvent;

			
        
			//set the map licence info.
            LicenceManager licenceManager = LicenceManager.GetInstance();
            long[] keys = {-0x8b9d5bd05f46354L,-0x4e41fd81b7d38cc3L,
                             0x1b071d669ee9b833L,0x5aa83843b0672fc8L,
                             -0x109dc3c9f0722846L,-0x48da5fe25dd815dfL,};
            licenceManager.AddLicence("GuidebeeMap_DotNet", keys);
            //optional, get the tile url from server.
            MapType.UpdateMapTileUrl();

            MapLayer.SetAbstractGraphicsFactory(GTKGraphicsFactory.GetInstance());
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

            var center = new GeoLatLng(32.0616667, 118.7777778);

            _rasterMap.SetCenter(center, 15, _rasterMap.GetMapType());
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
		
		private void UpdateStatus(string messsage)

        {
			Gtk.Application.Invoke(delegate {
           picMapCanvas.QueueDraw();
        });

           

        }
		protected virtual void OnDeleteEvent (object o, Gtk.DeleteEventArgs args)
			
		{
			_mapTileDownloadManager.Stop();
			_mapTileDownloadManager.Stop();
			Application.Quit();
		}
		
		protected virtual void OnBtnUpClicked (object sender, System.EventArgs e)
		{
			 _rasterMap.PanDirection(0, 64);
		}
		
		protected virtual void OnBtnLeftClicked (object sender, System.EventArgs e)
		{
			_rasterMap.PanDirection(64, 0);
		}
		
		protected virtual void OnBtnZoomInClicked (object sender, System.EventArgs e)
		{
			 _rasterMap.ZoomIn();
		}
		
		protected virtual void OnBtnDownClicked (object sender, System.EventArgs e)
		{
			_rasterMap.PanDirection(0, -64);
		}
		
	
		protected virtual void OnBtnZoomOutClicked (object sender, System.EventArgs e)
		{
			_rasterMap.ZoomOut();
		}
		
		

		protected virtual void OnBtnGoClicked (object sender, System.EventArgs e)
		{
			if (!string.IsNullOrEmpty(txtAddress.Text))
            {
                string name = txtAddress.Text;
                
                _rasterMap.GetLocations(name);

            }
		}
		
		protected virtual void OnBtnGetDirectionClicked (object sender, System.EventArgs e)
		{
			if (txtAddress1.Text.Length != 0 && txtAddress2.Text.Length != 0)
            {
                String name1 = txtAddress1.Text;
                String name2 = txtAddress2.Text;
               
                _rasterMap.GetDirections("from: " + name1 + " to: " + name2);
            }
       
		}
		
		protected virtual void OnBtnExitClicked (object sender, System.EventArgs e)
		{
		    //GTK need to stop twice.
			_mapTileDownloadManager.Stop();	
			_mapTileDownloadManager.Stop();
			Application.Quit();
		}
		
		
		
		protected virtual void OnBtnRightClicked (object sender, System.EventArgs e)
		{
			 _rasterMap.PanDirection(-64, 0);
		}
		
		
		
		protected virtual void OnCboMapTypeChanged (object sender, System.EventArgs e)
		{
			int mapType = MapType.MicrosoftMap;
            switch (cboMapType.Active)
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
            }
            _rasterMap.SetMapType(mapType);
		}
		
		
		
		protected virtual void OnPicMapCanvasMotionNotifyEvent (object o, Gtk.MotionNotifyEventArgs args)
		{
			_rasterMap.PanDirection((int)(args.Event.X - _oldX), (int)(args.Event.Y - _oldY));

            _oldX = (int)args.Event.X;
            _oldY = (int)args.Event.Y;
		}
		
			
		protected virtual void OnPicMapCanvasButtonPressEvent (object o, Gtk.ButtonPressEventArgs args)
		{
			_oldX = (int)args.Event.X;
            _oldY = (int)args.Event.Y;
		}
			

		
		
	}
}

