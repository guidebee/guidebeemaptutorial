//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.rim;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.DigitalMap;
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.IIpAddressGeocodingListener;
import com.mapdigit.gis.service.IpAddressLocation;
import com.pstreets.gisengine.demo.MapDemoRIM;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.MenuItem;

//[------------------------------ MAIN CLASS ----------------------------------]
public class MapIpSearchRIM extends MapDemoRIM implements IIpAddressGeocodingListener   {

    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        MapIpSearchRIM theApp = new MapIpSearchRIM();       
        theApp.enterEventDispatcher();
    }
    
    private MenuItem mapFindAddressMenuItem = new MenuItem("Find Address", 0, 0){
        public void run(){
           map.getIpLocations("58.192.32.1");
        }
    };

    public MapIpSearchRIM() {

        init();
        pushScreen(canvas);
        map.setIpAddressGeocodingListener(this);
       GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);        
        map.setCenter(center, 13, MapType.MICROSOFTCHINA); 
        
    }
    
   public void done(String query, IpAddressLocation result) {
        if (result != null && result.error.length() == 0
                && result.longitude.length() > 0
                && result.longitude.length() > 0) {
            try {

                MapPoint mapPoint = new MapPoint();
                String latLng = "[" + result.longitude + ","
                        + result.latitude + ",0]";
                mapPoint.point = DigitalMap.fromStringToLatLng(latLng);
                mapPoint.setName(result.organization);
                mapPoint.setNote(result.city + " " + result.country);
                map.panTo(mapPoint.point);
            } catch (Exception e) {

                result.error = "IP_NOT_FOUND";
            }
        }
    }
    
    protected void createMenu(Menu menu, int instance){
         menu.add(mapFindAddressMenuItem);
   }
}

