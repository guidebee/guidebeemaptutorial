package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.pstreets.gisengine.demo.MapDemoLWUIT;

public class HelloWorldLWUIT extends MapDemoLWUIT {

    public void startApp() {
        init();
        canvas.show();
        GeoLatLng center = new  GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
    }
    
}
