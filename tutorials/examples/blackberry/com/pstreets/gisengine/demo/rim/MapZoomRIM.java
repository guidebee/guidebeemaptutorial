
package com.pstreets.gisengine.demo.rim;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.pstreets.gisengine.demo.MapDemoRIM;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.MenuItem;

//[------------------------------ MAIN CLASS ----------------------------------]
/**
 *  map zoom demo for Guidebee Map API on MIDP platform.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 09/02/11
 * @author      Guidebee Pty Ltd.
 */
public class MapZoomRIM extends MapDemoRIM {

    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MapZoomRIM theApp = new MapZoomRIM();       
        theApp.enterEventDispatcher();
    }
    
    private MenuItem mapZoomInMenuItem=new MenuItem("Zoom In",0,0){
          public void run(){
              map.zoomIn();
            }
        };
    private MenuItem mapZoomOutMenuItem=new MenuItem("Zoom Out",1,0){
          public void run(){
              map.zoomOut();
            }
        };

    public MapZoomRIM() {
        init();
        pushScreen(canvas);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);

    }

     protected void createMenu(Menu menu, int instance){
         menu.add(mapZoomInMenuItem);
         menu.add(mapZoomOutMenuItem);
                        }
}

