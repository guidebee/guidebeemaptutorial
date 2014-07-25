//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------

import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.DigitalMapService;


import com.mapdigit.gis.service.IRoutingListener;
import com.pstreets.gisengine.demo.MapDemoLWUIT;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;

public class MapServiceTypeLWUIT extends MapDemoLWUIT
        implements IRoutingListener{

    private Command mapGetDirectionCommand;

    public void startApp() {
        init();
        canvas.setTitle("Map Service Type");
        
        map.setCurrentMapService(DigitalMapService.GOOGLE_MAP_SERVICE);
        //map.setCurrentMapService(DigitalMapService.MAPABC_MAP_SERVICE);
        //map.setCurrentMapService(DigitalMapService.CLOUDMADE_MAP_SERVICE);
        mapGetDirectionCommand=new Command("Get Direction"){
            public void actionPerformed(ActionEvent evt) {
               GeoLatLng latLng1=new GeoLatLng(32.0418381,118.7788905);
               GeoLatLng latLng2=new GeoLatLng(39.11643,117.180908);
               map.getDirections(new GeoLatLng[]{latLng1,latLng2});

            }
        };
        canvas.addCommand(mapGetDirectionCommand);
        GeoLatLng center =  new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
        map.setRoutingListener(this);
        canvas.show();
    }



    public void done(String query, MapDirection result) {
        if(result!=null){
            map.setMapDirection(result);
            map.resize(result.getBound());
            map.setZoom(3);
        }
    }
}
