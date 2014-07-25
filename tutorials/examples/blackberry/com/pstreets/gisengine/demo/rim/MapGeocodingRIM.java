//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.rim;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.IGeocodingListener;
import com.pstreets.gisengine.demo.MapDemoRIM;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.MenuItem;


//[------------------------------ MAIN CLASS ----------------------------------]
/**
 *  map pan demo for Guidebee Map API on MIDP platform.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 09/02/11
 * @author      Guidebee Pty Ltd.
 */
public class MapGeocodingRIM extends MapDemoRIM implements  IGeocodingListener {

    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MapGeocodingRIM theApp = new MapGeocodingRIM();       
        theApp.enterEventDispatcher();
    }
    
    private MenuItem mapFindAddressMenuItem = new MenuItem("Find Address", 0, 0){
        public void run(){
            map.getLocations("南京林业大学");
        }
    };

    public MapGeocodingRIM() {

        init();
        pushScreen(canvas);
        map.setGeocodingListener(this);
       GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);        
        map.setCenter(center, 13, MapType.MICROSOFTCHINA); 
        
    }

   
    public void done(String arg0, MapPoint[] result) {
        if (result != null) {
            map.panTo(result[0].getPoint());
        }
    }
    
    protected void createMenu(Menu menu, int instance){
         menu.add(mapFindAddressMenuItem);

                        }
}
