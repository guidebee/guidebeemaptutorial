package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------

import com.mapdigit.gis.MapPoint;

import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;


import com.mapdigit.gis.service.IReverseGeocodingListener;
import com.pstreets.gisengine.demo.MapDemoLWUIT;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;

public class MapReverseGeocodingLWUIT extends MapDemoLWUIT
        implements IReverseGeocodingListener{

    private Command mapFindAddressCommand;

    public void startApp() {
        init();
        canvas.setTitle("Map Reverse Geocoding");

        mapFindAddressCommand=new Command("Find Address"){
            public void actionPerformed(ActionEvent evt) {
                 map.getReverseLocations("32.061699,118.777802");

            }
        };
        canvas.addCommand(mapFindAddressCommand);
        GeoLatLng center =  new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
         map.setReverseGeocodingListener(this);

        canvas.show();
    }

    public void done(String arg0, MapPoint[] result) {
        if (result != null) {
            map.panTo(result[0].getPoint());
        }
    }
}
