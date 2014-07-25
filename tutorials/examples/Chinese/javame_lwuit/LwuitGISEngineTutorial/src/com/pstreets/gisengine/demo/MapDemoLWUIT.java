//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 11FEB2011  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo;

//--------------------------------- IMPORTS ------------------------------------
import java.io.IOException;
import javax.microedition.midlet.MIDlet;

import com.sun.lwuit.Display;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;
import com.sun.lwuit.Graphics;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import com.mapdigit.gis.raster.IMapDrawingListener;
import com.mapdigit.gis.raster.IReaderListener;
import com.mapdigit.gis.raster.MapClient;
import com.mapdigit.gis.raster.MapTileDownloadManager;
import com.mapdigit.licence.LicenceManager;

import com.pstreets.gisengine.demo.lwuit.drawing.LWUITGraphicsFactory;
import com.sun.lwuit.Painter;
import com.sun.lwuit.geom.Rectangle;


//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 11FEB2011  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * Base class for all Map Demos on LWUIT.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 11/02/11
 * @author      Guidebee Pty Ltd.
 */
public abstract class MapDemoLWUIT extends MIDlet implements IReaderListener,
        IMapDrawingListener {

    protected MapClient map;
    protected MapTileDownloadManager mapTileDownloadManager;
    protected IImage mapImage;
    protected IGraphics mapGraphics;
    protected MapCanvas canvas;

    public MapDemoLWUIT() {
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

    protected void init() {
        Display.init(this);
        try {
            Resources r = Resources.open("/javaTheme.res");
            UIManager.getInstance().setThemeProps(
                    r.getTheme(r.getThemeResourceNames()[0]));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //set the graphics factory
        MapLayer.setAbstractGraphicsFactory(LWUITGraphicsFactory.getInstance());
        mapImage = MapLayer.getAbstractGraphicsFactory()
                .createImage(Display.getInstance().getDisplayWidth(),
                Display.getInstance().getDisplayHeight());
        mapGraphics = mapImage.getGraphics();
        //Create the Digital Map objects.
        mapTileDownloadManager = new MapTileDownloadManager(this);
        map = new MapClient(1024, 1024, mapTileDownloadManager);
        map.setScreenSize(Display.getInstance().getDisplayWidth(),
                Display.getInstance().getDisplayHeight());
        map.start();
        map.setMapDrawingListener(this);
        //Creat the main form.
        canvas = new MapCanvas("Hello world");

    }

    ////////////////////////////////////////////////////////////////////////////
    //--------------------------------- REVISIONS ------------------------------
    // Date       Name                 Tracking #         Description
    // ---------  -------------------  -------------      ----------------------
    // 11FEB2011  James Shen                 	          Initial Creation
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Used instead of using the Resources API to allow us to fetch locally
     * downloaded
     * resources
     *
     * @param name the name of the resource
     * @return a resources object
     */
    public Resources getResource(String name) throws IOException {
        return Resources.open("/" + name + ".res");
    }

    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional) {
        map.stop();
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
     * Map canvas class ,a sub class of Form.
     */
    protected class MapCanvas extends Form {


        MapCanvas(String title) {
            super(title);
           
        }

        public void paintBackground(Graphics g){
             map.paint(mapGraphics);
                g.drawImage((Image) mapImage.getNativeImage(), 0,
                    0);
        }

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
