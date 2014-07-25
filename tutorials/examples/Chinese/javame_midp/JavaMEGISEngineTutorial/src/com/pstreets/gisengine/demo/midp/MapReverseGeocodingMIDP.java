//------------------------------------------------------------------------------
//                         COPYRIGHT 2010 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 16SEP2010  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.midp;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.IReverseGeocodingListener;
import com.pstreets.gisengine.demo.MapDemoMIDP;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 16SEP2010  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 *  map pan demo for Guidebee Map API on MIDP platform.
 * <hr><b>&copy; Copyright 2010 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 16/09/10
 * @author      Guidebee Pty Ltd.
 */
public class MapReverseGeocodingMIDP extends MapDemoMIDP implements CommandListener,
        IReverseGeocodingListener {

    private Command mapFindAddressCommand = new Command("Find Address",
            Command.OK, 1);

    public void startApp() {

        init();
        canvas.addCommand(mapFindAddressCommand);
        map.setReverseGeocodingListener(this);
        canvas.setCommandListener(this);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
        Display.getDisplay(this).setCurrent(canvas);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == mapFindAddressCommand) {
            map.getReverseLocations("32.061699,118.777802");
        }
    }

    public void done(String arg0, MapPoint[] result) {
        if (result != null) {
            map.panTo(result[0].getPoint());
        }
    }
}
