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
 *  map zoom demo for Guidebee Map API on MIDP platform.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 28/01/11
 * @author      Guidebee Pty Ltd.
 */
public class MapZoomMIDP extends MapDemoMIDP implements CommandListener{

    private Command mapZoomInCommand=new Command("Zoom In",Command.OK,1);
    private Command mapZoomOutCommand=new Command("Zoom Out",Command.CANCEL,1);

    public void startApp() {
        init();
        canvas.addCommand(mapZoomInCommand);
        canvas.addCommand(mapZoomOutCommand);
        canvas.setCommandListener(this);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
        Display.getDisplay(this).setCurrent(canvas);
    }

     public void commandAction(Command c, Displayable d) {
        if(c==mapZoomInCommand){
            map.zoomIn();

        }else if(c==mapZoomOutCommand){
            map.zoomOut();
        }
    }
}
