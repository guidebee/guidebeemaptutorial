package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------

import com.mapdigit.gis.MapPoint;
import com.pstreets.gisengine.demo.*;


import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;

import com.mapdigit.gis.service.IGeocodingListener;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;

public class MapGeocodingLWUIT extends MapDemoLWUIT implements IGeocodingListener{
    
    private Command mapFindAddressCommand;
  
    public void startApp() {
        init();
        canvas.setTitle("Map Geocoding");
     
        mapFindAddressCommand=new Command("Find Address"){
            public void actionPerformed(ActionEvent evt) {
                String name = "??????";
                map.getLocations(name);

            }
        };
        canvas.addCommand(mapFindAddressCommand);
        GeoLatLng center =  new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
        map.setGeocodingListener(this);

        canvas.show();
    }



    public void done(String query, MapPoint[] result) {
        if(result!=null){
            map.panTo(result[0].getPoint());

        }
    }
}
