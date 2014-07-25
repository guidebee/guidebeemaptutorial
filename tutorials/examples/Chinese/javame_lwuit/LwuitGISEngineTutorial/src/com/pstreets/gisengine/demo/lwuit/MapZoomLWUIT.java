package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;

import com.pstreets.gisengine.demo.MapDemoLWUIT;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;

public class MapZoomLWUIT extends MapDemoLWUIT {

    private Command mapZoomInCommand;
    private Command mapZoomOutCommand;

    public void startApp() {
        init();
        canvas.setTitle("Map Zoom");
        canvas.setScrollable(false);

        mapZoomInCommand = new Command("Zoom In") {
            public void actionPerformed(ActionEvent evt) {
                map.zoomIn();
            }
        };

        mapZoomOutCommand = new Command("Zoom Out") {
            public void actionPerformed(ActionEvent evt) {
                map.zoomOut();
            }
        };

        canvas.addCommand(mapZoomInCommand);
        canvas.addCommand(mapZoomOutCommand);
        GeoLatLng center =  new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
        canvas.show();
    }
}
