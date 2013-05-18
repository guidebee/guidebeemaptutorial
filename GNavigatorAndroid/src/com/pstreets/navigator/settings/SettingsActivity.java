//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 SWIFTNETWORKS
//                           ALL RIGHTS RESERVED.
//                     EITS CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 23SEP2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.navigator.settings;

//--------------------------------- IMPORTS ------------------------------------

import roboguice.activity.RoboPreferenceActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapType;
import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SessionInfo;
import com.pstreets.navigator.app.SharedMapInstance;


//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//23SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* This class used for application configuation.
* <hr>
* <b>&copy; Copyright 2011 Swiftnetworks, Inc. All Rights Reserved.</b>
* 
* @version 1.00, 23/09/11
* @author Swiftnetworks Pty Ltd.
*/
public class SettingsActivity extends RoboPreferenceActivity{

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    	
    }
	
	public void onResume(){
		super.onResume();
		writePreferences();
	}
	
	public void onBackPressed(){
		readPreferences();
    	super.onBackPressed();
    }
	
	private void writePreferences(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor=sharedPref.edit();
		editor.putString("deivceno_preference", SessionInfo.localDeviceNo);
		editor.putString("mastdeviceno_preference", SessionInfo.masterDeviceNo);
		editor.putString("mapzoom_preference", String.valueOf(SharedMapInstance.map.getZoom()));
		String centerLocation=SharedMapInstance.map.getCenter().lng()+","+SharedMapInstance.map.getCenter().lat();
		editor.putString("mapcenter_preference", centerLocation);
		int mapType=0;
		switch(SharedMapInstance.map.getMapType()){
		case MapType.GOOGLEMAP: mapType=0;break;
		case MapType.GOOGLEHYBRID: mapType=1;break;
		case MapType.GOOGLECHINA: mapType=2;break;
		case MapType.MICROSOFTMAP: mapType=3;break;
		case MapType.MICROSOFTHYBRID: mapType=4;break;
		case MapType.MICROSOFTCHINA: mapType=5;break;
		case MapType.MAPABCCHINA: mapType=6;break;
		case MapType.OPENSTREETMAP: mapType=7;break;

		}
		editor.putBoolean("chinamapoffset_preference", SessionInfo.ajuestOffset);
		editor.putString("mapmode_preference",String.valueOf(mapType));
		editor.commit();
	
	}
	
	private void readPreferences(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String values = sharedPref.getString("deivceno_preference", SessionInfo.localDeviceNo);
        SessionInfo.localDeviceNo=values;
        values = sharedPref.getString("mastdeviceno_preference", SessionInfo.masterDeviceNo);
        SessionInfo.masterDeviceNo=values;
        values = sharedPref.getString("mapzoom_preference", "1");
        int mapZoom=1;
        mapZoom=Integer.parseInt(values);
        
        values = sharedPref.getString("mapcenter_preference", "0,0");
        String location = "[" + values + ",0]";
    	GeoLatLng latLng=MapLayer.fromStringToLatLng(location);
    	SharedMapInstance.map.setCenter(latLng, mapZoom);
        
        int mapType=MapType.GOOGLEMAP;
        values = sharedPref.getString("mapmode_preference","0");
        mapType=Integer.parseInt(values);
        switch(mapType){
        	case 0:	mapType=MapType.GOOGLEMAP;break;
        	case 1:	mapType=MapType.GOOGLEHYBRID;break;
        	case 2:	mapType=MapType.GOOGLECHINA;break;
        	case 3:	mapType=MapType.MICROSOFTMAP;break;
        	case 4:	mapType=MapType.MICROSOFTHYBRID;break;
        	case 5:	mapType=MapType.MICROSOFTCHINA;break;
        	case 6:	mapType=MapType.MAPABCCHINA;break;
        	case 7:	mapType=MapType.OPENSTREETMAP;break;
        	
        }
        
        SessionInfo.ajuestOffset = sharedPref.getBoolean("chinamapoffset_preference", false);
        
        
        SharedMapInstance.map.setMapType(mapType);
	}
}
