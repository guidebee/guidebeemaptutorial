//------------------------------------------------------------------------------
//                         COPYRIGHT 2010 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 16SEP2010  James Shen                              Initial Creation
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
// 16SEP2010  James Shen                              Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 *  map pan demo for Guidebee Map API on MIDP platform.
 * <hr><b>&copy; Copyright 2010 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 16/09/10
 * @author      Guidebee Pty Ltd.
 */
public class MapPanRIM extends MapDemoRIM  {

   /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MapPanRIM theApp = new MapPanRIM();       
        theApp.enterEventDispatcher();
    }
    
    private MenuItem mapUpMenuItem = new MenuItem("Up", 0, 0){
        public void run(){
            map.panDirection(0, -32);

            }};
    private MenuItem mapDownMenuItem = new MenuItem("Down",1, 0){
        public void run(){
            map.panDirection(0, 32);
            }};
    private MenuItem mapLeftMenuItem = new MenuItem("Left",2, 0){
        public void run(){
            map.panDirection(-32, 0);
            }};
    private MenuItem mapRightMenuItem = new MenuItem("Right", 3, 0){
        public void run(){
            map.panDirection(32, 0);
            }};

    public MapPanRIM() {

        init();
        pushScreen(canvas);
        GeoLatLng center = new GeoLatLng(-31.948275, 115.857562);
        map.setCenter(center, 13, MapType.MICROSOFTMAP);

    }

    protected void createMenu(Menu menu, int instance){
         menu.add(mapUpMenuItem);
         menu.add(mapDownMenuItem);
         menu.add(mapLeftMenuItem);
         menu.add(mapRightMenuItem);

    }
}
