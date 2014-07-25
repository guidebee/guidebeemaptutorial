//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.rim;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.IRoutingListener;
import com.mapdigit.gis.service.DigitalMapService;
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
public class MapServiceTypeRIM extends MapDemoRIM implements    
   IRoutingListener {

    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MapServiceTypeRIM theApp = new MapServiceTypeRIM();       
        theApp.enterEventDispatcher();
    }
    
    private MenuItem mapGetDirectionMenuItem = new MenuItem("Get Direction", 0, 0){
        public void run(){
            GeoLatLng latLng1=new GeoLatLng(32.0418381,118.7788905);
            GeoLatLng latLng2=new GeoLatLng(39.11643,117.180908);
            map.getDirections(new GeoLatLng[]{latLng1,latLng2});
        }
    };

    public MapServiceTypeRIM() {

        init();
        pushScreen(canvas);
        //map.setCurrentMapService(DigitalMapService.GOOGLE_MAP_SERVICE);
        //map.setCurrentMapService(DigitalMapService.MAPABC_MAP_SERVICE);
         map.setCurrentMapService(DigitalMapService.CLOUDMADE_MAP_SERVICE);
        map.setRoutingListener(this);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);        
        map.setCenter(center, 13, MapType.MICROSOFTCHINA); 
        
    }

    

    public void done(String arg0, MapDirection result) {
        if (result != null) {
            map.setMapDirection(result);
            //map.resize(result.getBound());
           // map.setZoom(6);
           map.setZoom(15);
           map.panTo(result.routes[0].startGeocode.point);
        }
    }
    
    protected void createMenu(Menu menu, int instance){
         menu.add(mapGetDirectionMenuItem);

   }
}
