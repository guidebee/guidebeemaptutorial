//------------------------------------------------------------------------------
//                         COPYRIGHT 2010 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 18SEP2010  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo;

//--------------------------------- IMPORTS ------------------------------------
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import com.mapdigit.gis.raster.IMapDrawingListener;
import com.mapdigit.gis.raster.IReaderListener;
import com.mapdigit.gis.raster.MapTileDownloadManager;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.raster.RasterMap;
import com.mapdigit.licence.LicenceManager;

import com.pstreets.gisengine.demo.midp.drawing.MIDPGraphicsFactory;


//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 18SEP2010  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * Base class for all Map Demos.
 * <hr><b>&copy; Copyright 2010 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 18/09/10
 * @author      Guidebee Pty Ltd.
 */
public abstract class MapDemoMIDP extends MIDlet implements IReaderListener,
        IMapDrawingListener {

    protected RasterMap map;
    protected MapTileDownloadManager mapTileDownloadManager;
    protected IImage mapImage;
    protected IGraphics mapGraphics;
    protected MapCanvas canvas;

    public MapDemoMIDP() {
        try {
            LicenceManager licenceManager = LicenceManager.getInstance();
            long keys[]= {-0x8b9d5bd05f46354L,-0x4e41fd81b7d38cc3L,
            0x5c7addc8022e908aL,-0x532dae1d56b062b2L,
            0x3e18d8181cb217ecL,-0x39a3d8deb40bd92fL,};
            licenceManager.addLicence("GuidebeeMap_JavaME", keys);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * this only used for the vector map demo.
     */
    public void initCanvas() {
        canvas = new MapCanvas();
    }

    public void init() {
        canvas = new MapCanvas();
        //set the graphics factory
        MapLayer.setAbstractGraphicsFactory(MIDPGraphicsFactory.getInstance());
        mapImage = MapLayer.getAbstractGraphicsFactory().createImage(canvas.getWidth(),
                canvas.getHeight());
        mapGraphics = mapImage.getGraphics();
        //Create the Digital Map objects.
        mapTileDownloadManager = new MapTileDownloadManager(this);
        MapType.updateMapTileUrl();
        map = new RasterMap(1024, 1024, mapTileDownloadManager);
        map.setScreenSize(canvas.getWidth(),
                canvas.getHeight());
        mapTileDownloadManager.start();
        map.setMapDrawingListener(this);

    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        mapTileDownloadManager.stop();
    }

    public void readProgress(int arg0, int arg1) {
        System.out.println(arg0 + "/" + arg1);
    }

    public void done() {
        if (canvas != null) {
            canvas.repaint();
        }
    }

    /**
     * Map canvas class, a subclass of Canvas.
     */
    protected class MapCanvas extends Canvas {

        private void panMap(float x, float y) {
            float dx = x - oldX;
            float dy = y - oldY;
            if (!(dx == 0 && dy == 0)) {
                map.panDirection((int) dx, (int) dy);
            }

        }
        boolean isPan = false;
        private float oldX = -1;
        private float oldY = -1;

        protected void paint(Graphics g) {
            map.paint(mapGraphics);
            g.drawImage((Image) mapImage.getNativeImage(), 0, 0, 0);
        }

        public void pointerDragged(int x, int y) {
            if (isPan) {
                panMap(x, y);
                oldX = x;
                oldY = y;
            }
        }

        public void pointerPressed(int x, int y) {
            oldX = x;
            oldY = y;
            isPan = true;
        }

        public void pointerReleased(int x, int y) {
            oldX = x;
            oldY = y;
            isPan = false;
        }
    }
}
