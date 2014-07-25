package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;

import com.pstreets.gisengine.demo.MapDemoLWUIT;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;


public class MapPanLWUIT extends MapDemoLWUIT{

    private Command mapUpCommand;
    private Command mapDownCommand;
    private Command mapLeftCommand;
    private Command mapRightCommand;

    public void startApp() {
        init();

        mapUpCommand=new Command("Up"){
            public void actionPerformed(ActionEvent evt) {
                map.panDirection(0, -32);
            }
        };

        mapDownCommand=new Command("Down"){
            public void actionPerformed(ActionEvent evt) {
                map.panDirection(0, 32);
            }
        };

        mapLeftCommand=new Command("Left"){
            public void actionPerformed(ActionEvent evt) {
                map.panDirection(-32, 0);

            }
        };

        mapRightCommand=new Command("Right"){
            public void actionPerformed(ActionEvent evt) {
                map.panDirection(32, 0);
            }
        };

        canvas.addCommand(mapUpCommand);
        canvas.addCommand(mapDownCommand);
        canvas.addCommand(mapLeftCommand);
        canvas.addCommand(mapRightCommand);
        GeoLatLng center =  new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);

        canvas.show();
    }
   
}
