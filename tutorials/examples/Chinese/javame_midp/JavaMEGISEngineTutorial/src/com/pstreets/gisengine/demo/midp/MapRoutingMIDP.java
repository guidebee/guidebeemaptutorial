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
import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.IRoutingListener;
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
 *  Map routing demo for Guidebee Map API on MIDP platform.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 28/01/11
 * @author      Guidebee Pty Ltd.
 */
public class MapRoutingMIDP extends MapDemoMIDP implements CommandListener,
        IRoutingListener {

    private Command mapGetDirectionCommand = new Command("Get Direction",
            Command.OK, 1);

    public void startApp() {

        init();
        canvas.addCommand(mapGetDirectionCommand);
        map.setRoutingListener(this);
        canvas.setCommandListener(this);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);

        Display.getDisplay(this).setCurrent(canvas);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == mapGetDirectionCommand) {
            String name1 = "??";
            String name2 = "??";
            map.getDirections("from: " + name1 + " to: " + name2);

        }
    }

    public void done(String query, MapDirection result) {
        if (result != null) {
            map.setMapDirection(result);
            map.resize(result.getBound());
            map.setZoom(3);

        }
    }
}
