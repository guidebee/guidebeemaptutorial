//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 28JAN2011  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.midp;

//--------------------------------- IMPORTS ------------------------------------
import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.Display;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.IMapDrawingListener;
import com.mapdigit.gis.raster.IReaderListener;
import com.mapdigit.gis.raster.MapTileDownloadManager;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.raster.RasterMap;

import com.mapdigit.licence.LicenceManager;
import com.pstreets.gisengine.demo.midp.drawing.MIDPGraphicsFactory;

//[------------------------------ MAIN CLASS ----------------------------------]
/**
 * Hello China demo.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 28/01/11
 * @author      Guidebee Pty Ltd.
 */
public class HelloChina extends MIDlet implements IReaderListener,
        IMapDrawingListener {

    protected RasterMap map;
    protected MapTileDownloadManager mapTileDownloadManager;
    protected IImage mapImage;
    protected IGraphics mapGraphics;
    protected MapCanvas canvas;

    public HelloChina() {
        try {
            LicenceManager licenceManager = LicenceManager.getInstance();
            long keys[]= {-0x8b9d5bd05f46354L,-0x4e41fd81b7d38cc3L,
            0x5c7addc8022e908aL,-0x532dae1d56b062b2L,
            0x3e18d8181cb217ecL,-0x39a3d8deb40bd92fL,};
            licenceManager.addLicence("GuidebeeMap_JavaME", keys);
        } catch (Exception ex) {
        }

    }

    public void startApp() {
        canvas = new MapCanvas();
        //set the graphics factory
        MapLayer.setAbstractGraphicsFactory(MIDPGraphicsFactory.getInstance());
        mapImage = MapLayer.getAbstractGraphicsFactory()
                .createImage(canvas.getWidth(),
                canvas.getHeight());
        mapGraphics = mapImage.getGraphics();
        //Create the Digital Map objects.
        mapTileDownloadManager = new MapTileDownloadManager(this);
        try {
            map = new RasterMap(1024, 1024, mapTileDownloadManager);
        } catch (Exception ex) {
        }
        map.setScreenSize(canvas.getWidth(),
                canvas.getHeight());
        mapTileDownloadManager.start();
        map.setMapDrawingListener(this);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.GOOGLECHINA);
        Display.getDisplay(this).setCurrent(canvas);
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        mapTileDownloadManager.stop();
    }

    public void readProgress(int downloaded, int total) {
        System.out.println(downloaded + "/" + total);
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
