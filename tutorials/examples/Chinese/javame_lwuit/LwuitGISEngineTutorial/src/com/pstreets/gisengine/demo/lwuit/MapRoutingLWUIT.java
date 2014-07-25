//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.lwuit;

//--------------------------------- IMPORTS ------------------------------------

import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;


import com.mapdigit.gis.service.IRoutingListener;
import com.pstreets.gisengine.demo.MapDemoLWUIT;
import com.sun.lwuit.Command;
import com.sun.lwuit.events.ActionEvent;

public class MapRoutingLWUIT extends MapDemoLWUIT implements IRoutingListener{

    private Command mapGetDirectionCommand;

    public void startApp() {
        init();
        canvas.setTitle("Map Routing");

        mapGetDirectionCommand=new Command("Get Direction"){
            public void actionPerformed(ActionEvent evt) {
               String name1 = "Nanjing";
               String name2 = "Beijing";
               map.getDirections("from: " + name1 + " to: " + name2);

            }
        };
        canvas.addCommand(mapGetDirectionCommand);
        GeoLatLng center =  new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
        map.setRoutingListener(this);
        canvas.show();
    }

    

    public void done(String arg0, MapDirection result) {
        if(result!=null){
            map.setMapDirection(result);
            //map.resize(result.getBound());
            map.setZoom(5);
        }
    }
}
