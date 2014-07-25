//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.rim;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.IRoutingListener;
import com.pstreets.gisengine.demo.MapDemoRIM;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.MenuItem;

//[------------------------------ MAIN CLASS ----------------------------------]
/**
 *  Map routing demo for Guidebee Map API on RIM platform.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 10/02/11
 * @author      Guidebee Pty Ltd.
 */
public class MapRoutingRIM extends MapDemoRIM implements    IRoutingListener {

    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MapRoutingRIM theApp = new MapRoutingRIM();       
        theApp.enterEventDispatcher();
    }
    
    private MenuItem mapGetDirectionMenuItem = new MenuItem("Get Direction", 0, 0){
        public void run(){
            String name1 = "南京";            
            String name2 = "北京";            
            map.getDirections("from: " + name1 + " to: " + name2); 
        }
    };

    public MapRoutingRIM() {

        init();
        pushScreen(canvas);
        map.setRoutingListener(this);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);        
        map.setCenter(center, 13, MapType.MICROSOFTCHINA); 
        
    }

    

    public void done(String arg0, MapDirection result) {
        if (result != null) {
            map.setMapDirection(result);
            map.resize(result.getBound());
            map.setZoom(5);
        }
    }
    
    protected void createMenu(Menu menu, int instance){
         menu.add(mapGetDirectionMenuItem);

   }
}
