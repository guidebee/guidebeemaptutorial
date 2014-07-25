//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;

import com.pstreets.gisengine.demo.MapDemoLWUIT;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;


public class MapTypeLWUIT extends MapDemoLWUIT {

    private Command mapTypeCommand;
    private int mapType = 0;
     private static final int[] mapTypes = {MapType.GOOGLECHINA,
     MapType.MAPABCCHINA, MapType.MICROSOFTCHINA};

    public void startApp() {
        init();
        //Creat the main form.
        canvas.setTitle("Map Type");
        mapTypeCommand = new Command("MapType",2) {

            public void actionPerformed(ActionEvent evt) {
                map.setMapType(mapTypes[mapType]);
                mapType++;
                mapType %= mapTypes.length;

            }
        };
        canvas.addCommand(mapTypeCommand);

        GeoLatLng center =  new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.GOOGLECHINA);
                    canvas.show();
      
    }
}
