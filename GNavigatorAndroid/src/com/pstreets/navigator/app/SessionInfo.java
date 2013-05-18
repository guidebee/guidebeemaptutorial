package com.pstreets.navigator.app;

import com.pstreets.navigator.dataobject.Device;

import java.util.ArrayList;

public abstract class SessionInfo {
	public static ArrayList<Device> devices = new ArrayList<Device>();

	public static Device currentDevice;

	public static String localDeviceNo;
	
	public static String masterDeviceNo;

	/**
	 * select device list
	 */
	public static ArrayList<Device> selectedDeviceList = new ArrayList<Device>();
	
	public static boolean ajuestOffset=false;

}
