//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine.demo.rim;

//--------------------------------- IMPORTS ------------------------------------
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.pstreets.gisengine.demo.MapDemoRIM;

//[------------------------------ MAIN CLASS ----------------------------------]
/**
 *  Hello world demo for Guidebee Map API on RIM platform.
 * <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * @version     1.00, 06/02/11
 * @author      Guidebee Pty Ltd.
 */
public class HelloWorldRIM extends MapDemoRIM {

    /**
     * Entry point for application
     * @param args Command line arguments (not used)
     */ 
    public static void main(String[] args)
    {
        // Create a new instance of the application and make the currently
        // running thread the application's event dispatch thread.
        HelloWorldRIM theApp = new HelloWorldRIM();       
        theApp.enterEventDispatcher();
    }
    
    public HelloWorldRIM() {
        init();
        pushScreen(canvas);
        GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
        map.setCenter(center, 13, MapType.MICROSOFTCHINA);
    }
    
}
