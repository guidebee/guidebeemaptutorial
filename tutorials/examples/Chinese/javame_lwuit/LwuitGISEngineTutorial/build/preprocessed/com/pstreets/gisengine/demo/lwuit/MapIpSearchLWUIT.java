package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------

import com.mapdigit.gis.DigitalMap;
import com.mapdigit.gis.MapPoint;

import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.IIpAddressGeocodingListener;

import com.mapdigit.gis.service.IpAddressLocation;
import com.pstreets.gisengine.demo.MapDemoLWUIT;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;

public class MapIpSearchLWUIT extends MapDemoLWUIT
        implements IIpAddressGeocodingListener{

    private Command mapFindAddressCommand;

    public void startApp() {
        init();
        canvas.setTitle("Map Geocoding");

        mapFindAddressCommand=new Command("Find Address"){
            public void actionPerformed(ActionEvent evt) {
                 map.getIpLocations("58.192.32.1");

            }
        };
        canvas.addCommand(mapFindAddressCommand);
        GeoLatLng center =  new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
        map.setIpAddressGeocodingListener(this);

        canvas.show();
    }

    public void done(String query, IpAddressLocation result) {
        if (result != null && result.error.length() == 0
                && result.longitude.length() > 0
                && result.latitude.length() > 0) {
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
}
