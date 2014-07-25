//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 28JAN2011  James Shen                              Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.midp;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.DigitalMap;
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.IIpAddressGeocodingListener;
import com.pstreets.gisengine.demo.MapDemoMIDP;
import com.mapdigit.gis.service.IpAddressLocation;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 28JAN2011  James Shen                              Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 *  map pan demo for Guidebee Map API on MIDP platform.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 28/01/11
 * @author      Guidebee Pty Ltd.
 */
public class MapIpSearchMIDP extends MapDemoMIDP implements CommandListener,
        IIpAddressGeocodingListener {

    private Command mapFindAddressCommand = new Command("Find Address",
            Command.OK, 1);

    public void startApp() {

        init();
        canvas.addCommand(mapFindAddressCommand);
        map.setIpAddressGeocodingListener(this);
        canvas.setCommandListener(this);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 15, MapType.MICROSOFTCHINA);
        Display.getDisplay(this).setCurrent(canvas);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == mapFindAddressCommand) {
            map.getIpLocations("58.192.32.1");
        }
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
}
