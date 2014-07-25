//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 28JAN2011  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.midp;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.pstreets.gisengine.demo.MapDemoMIDP;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
// Date       Name                 Tracking #         Description
// --------   -------------------  -------------      --------------------------
// 28JAN2011  James Shen                 	      Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 *  map type demo for Guidebee Map API on MIDP platform.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 28/01/11
 * @author      Guidebee Pty Ltd.
 */
public class MapTypeMIDP extends MapDemoMIDP implements CommandListener {

    private int mapType = 0;
    private static final int[] mapTypes = {MapType.GOOGLECHINA,
        MapType.MAPABCCHINA, MapType.MICROSOFTCHINA};

    private Command mapTypeCommand = new Command("MapType", Command.OK, 1);

    public void startApp() {

        init();
        canvas.addCommand(mapTypeCommand);
        canvas.setCommandListener(this);
        map.setMapDrawingListener(this);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.GOOGLECHINA);
        Display.getDisplay(this).setCurrent(canvas);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == mapTypeCommand) {
            map.setMapType(mapTypes[mapType]);
            mapType++;
            mapType %= mapTypes.length;
        }
    }
}
