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
 *  map pan demo for Guidebee Map API on MIDP platform.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 28/01/11
 * @author      Guidebee Pty Ltd.
 */
public class MapPanMIDP extends MapDemoMIDP implements CommandListener {

    private Command mapUpCommand = new Command("Up", Command.OK, 1);
    private Command mapDownCommand = new Command("Down", Command.ITEM, 1);
    private Command mapLeftCommand = new Command("Left", Command.ITEM, 1);
    private Command mapRightCommand = new Command("Right", Command.ITEM, 1);

    public void startApp() {

        init();
        canvas.addCommand(mapUpCommand);
        canvas.addCommand(mapDownCommand);
        canvas.addCommand(mapLeftCommand);
        canvas.addCommand(mapRightCommand);
        canvas.setCommandListener(this);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
        Display.getDisplay(this).setCurrent(canvas);
    }

    public void commandAction(Command c, Displayable d) {
        if (c == mapUpCommand) {
            map.panDirection(0, -32);

        } else if (c == mapDownCommand) {
            map.panDirection(0, 32);
        } else if (c == mapLeftCommand) {
            map.panDirection(-32, 0);
        } else if (c == mapRightCommand) {
            map.panDirection(32, 0);
        }

    }
}
