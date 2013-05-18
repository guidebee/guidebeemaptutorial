package com.pstreets.navigator.app;

import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.MapStep;
import com.mapdigit.gis.geometry.GeoPoint;

import java.util.ArrayList;

public class SharedSearchResults {
	
	public final static int AddressSearch=0;
	public final static int LocalSearch=1;
	public final static int IPSearch=2;
	public final static int LatitudeSearch=3;
	
	public static int currentSearchType=AddressSearch;
	public static MapPoint [] searchResults=null;
	public static int currentSeachResultIndex=0;
	
	
	public static MapDirection mapDirection=null;
	
	public static ArrayList<MapStep> mapStepList=new ArrayList<MapStep>();
	public static int currentMapStepIndex=0;
	
	
	public static boolean isRouteMode=false;
	
	public static MapPoint directionStart;
	public static MapPoint directionEnd;
	public static GeoPoint  currentPressedPoint=new GeoPoint();

}
