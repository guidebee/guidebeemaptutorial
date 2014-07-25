//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import com.mapdigit.gis.raster.IMapDrawingListener;
import com.mapdigit.gis.raster.IReaderListener;
import com.mapdigit.gis.raster.MapTileDownloadManager;
import com.mapdigit.gis.raster.RasterMap;
import com.mapdigit.licence.LicenceManager;
import com.pstreets.gisengine.demo.rim.drawing.RIMGraphicsFactory;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.TouchEvent;

//[------------------------------ MAIN CLASS ----------------------------------]
/**
 * Base class for all Map Demos.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/02/11
 * @author      Guidebee Pty Ltd.
 */
public abstract class MapDemoRIM extends UiApplication implements IReaderListener,
        IMapDrawingListener {

    protected RasterMap map;
    protected MapTileDownloadManager mapTileDownloadManager;
    protected IImage mapImage;
    protected IGraphics mapGraphics;
    protected MapCanvas canvas;

    public MapDemoRIM() {
        try {
            LicenceManager licenceManager = LicenceManager.getInstance();
            //expires on Tue Mar 15 20:54:42 CST 2011
          long keys[]= {-0x8b9d5bd05f46354L,-0x4e41fd81b7d38cc3L,
            0x5c7addc8022e908aL,-0x532dae1d56b062b2L,
            0x3e18d8181cb217ecL,-0x39a3d8deb40bd92fL,};
            licenceManager.addLicence("GuidebeeMap_JavaME", keys);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void init() {
        canvas = new MapCanvas();
        //set the graphics factory
        MapLayer.setAbstractGraphicsFactory(RIMGraphicsFactory.getInstance());
        mapImage = MapLayer.getAbstractGraphicsFactory().createImage(Display.getWidth(),
                Display.getHeight());
        mapGraphics = mapImage.getGraphics();
        //Create the Digital Map objects.
        mapTileDownloadManager = new MapTileDownloadManager(this);
        try{
        map = new RasterMap(1024, 1024, mapTileDownloadManager);
    }catch(Exception e){}
        map.setScreenSize(Display.getWidth(),
                Display.getHeight());
        mapTileDownloadManager.start();
        map.setMapDrawingListener(this);

    }

    protected void createMenu(Menu menu, int instance){
    }
    public void closeApp() {
        mapTileDownloadManager.stop();
    }

    public void readProgress(int arg0, int arg1) {
        System.out.println(arg0 + "/" + arg1);
    }

    public void done() {
        if (canvas != null) {
            canvas.invalidate();
        }
    }

    protected class MapCanvas extends MainScreen {

        private void panMap(float x, float y) {
        float dx = x - oldX;
           float dy = y - oldY;
           if(!(dx==0 && dy==0))
        map.panDirection((int)dx, (int)dy);

    }
        boolean isPan=false;
        private float oldX = -1;
        private float oldY = -1;
    
        protected void paint(Graphics g) {
            map.paint(mapGraphics);
            Bitmap bitmap=(Bitmap) mapImage.getNativeImage();
            g.drawBitmap(0,0,bitmap.getWidth(),bitmap.getHeight(),bitmap,0,0);
        }
        
        public void close(){
            closeApp();
        }
        
        protected boolean touchEvent(TouchEvent event){
            int action=event.getEvent();
          switch(action){
        case TouchEvent.DOWN:
                  oldX = event.getGlobalX(1);
            oldY = event.getGlobalY(1);
                    isPan=true;
                    break;
         case TouchEvent.UP:
                    oldX = event.getGlobalX(1);
            oldY = event.getGlobalY(1);
                    isPan=false;
                   break;
         case TouchEvent.MOVE :
                 if(isPan)
            {
            panMap(event.getGlobalX(1), event.getGlobalY(1));
            oldX = event.getGlobalX(1);
            oldY = event.getGlobalY(1);
            }
                    break;
         }
      return true;
        }
        protected void makeMenu(Menu menu, int instance){
            createMenu(menu,instance);
        }
    }
}
