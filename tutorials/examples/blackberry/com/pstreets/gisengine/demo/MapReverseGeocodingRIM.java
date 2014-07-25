//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.rim;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.IReverseGeocodingListener;
import com.pstreets.gisengine.demo.MapDemoRIM;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.MenuItem;

//[------------------------------ MAIN CLASS ----------------------------------]
public class MapReverseGeocodingRIM extends MapDemoRIM implements
 IReverseGeocodingListener   {

    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MapReverseGeocodingRIM theApp = new MapReverseGeocodingRIM();       
        theApp.enterEventDispatcher();
    }
    
    private MenuItem mapFindAddressMenuItem = new MenuItem("Find Address", 0, 0){
        public void run(){
            map.getReverseLocations("32.061699,118.777802"); 
        }
    };

    public MapReverseGeocodingRIM() {

        init();
        pushScreen(canvas);
        map.setReverseGeocodingListener(this);
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
