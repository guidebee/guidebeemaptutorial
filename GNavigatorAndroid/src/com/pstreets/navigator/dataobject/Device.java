package com.pstreets.navigator.dataobject;

import java.util.ArrayList;

import com.mapdigit.gis.geometry.GeoLatLng;

public class Device {
	
	public String deviceId;
	
	public String deviceIMEI;
	
	public String deviceName;
	
	public String deviceStatus;
	
	public String lastIssuedCommand;
	
	public String devicePowerLevel;
	
	public GeoLatLng lastKnownLocation;
	
	public ArrayList<GeoLatLng> geofence;
	
	public ArrayList<GeoLatLng> positionHistroy;
	
	public ArrayList<String> commandList=new ArrayList<String>();

}
