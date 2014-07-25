//------------------------------------------------------------------------------
//                         COPYRIGHT 2010 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 16SEP2010  James Shen                           Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.rim;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.pstreets.gisengine.demo.MapDemoRIM;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.MenuItem;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 16SEP2010  James Shen                               Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 *  map type demo for Guidebee Map API on RIM platform.
 * <hr><b>&copy; Copyright 2010 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 16/09/10
 * @author      Guidebee Pty Ltd.
 */
public class MapTypeRIM extends MapDemoRIM  {

    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MapTypeRIM theApp = new MapTypeRIM();       
        theApp.enterEventDispatcher();
    }
    
    private int mapType = 0;
    private static final int[] mapTypes = {MapType.MICROSOFTSATELLITE,
        MapType.MICROSOFTMAP, MapType.MICROSOFTHYBRID};
    private MenuItem mapTypeMenuItem = new MenuItem("MapType",0,1){
        public void run(){
            map.setMapType(mapTypes[mapType]);
            mapType++;
            mapType %= mapTypes.length;
        }
        };

    public MapTypeRIM() {

        init();
        pushScreen(canvas);
        GeoLatLng center = new GeoLatLng(-31.948275, 115.857562);
        map.setCenter(center, 13, MapType.MICROSOFTMAP);

    }

     protected void createMenu(Menu menu, int instance){
         menu.add(mapTypeMenuItem);

   }
}

