//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 SWIFTNETWORKS
//                           ALL RIGHTS RESERVED.
//                     EITS CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 24OCT2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.navigator.database;

//--------------------------------- IMPORTS ------------------------------------
import java.util.ArrayList;
import java.util.logging.Logger;

import com.mapdigit.gis.MapLayer;
import com.pstreets.navigator.dataobject.Device;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//24OCT2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* This class is the device DB mapper.
* <hr>
* <b>&copy; Copyright 2011 Swiftnetworks, Inc. All Rights Reserved.</b>
* 
* @version 1.00, 24/10/11
* @author Swiftnetworks Pty Ltd.
*/
class DeviceDBMapper extends DBMapper{

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 24OCT2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* Constructor.
	* @param adapter
	*/
	public DeviceDBMapper(DBAdapter adapter) {
		super(adapter);
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* Add a device record to table.
	*/
	public long addDevice(Device device) {
		SQLiteDatabase db = mAdapter.getDatabase();
		ContentValues newTaskValues = new ContentValues();
		newTaskValues.put(KEY_DEVICE_NO, device.deviceId);
		newTaskValues.put(KEY_DEVICE_NAME, device.deviceName);
		newTaskValues.put(KEY_DEVICE_IMEI, device.deviceIMEI);
		newTaskValues.put(KEY_DEVICE_STATUS, device.deviceStatus);
		if(device.lastKnownLocation!=null){
			newTaskValues.put(KEY_DEVICE_LATITUDE, device.lastKnownLocation.lat());
			newTaskValues.put(KEY_DEVICE_LONGITUDE, device.lastKnownLocation.lng());
		}
		newTaskValues.put(KEY_DEVICE_POWER_LEVEL, device.devicePowerLevel);
		return db.insert(TABLE_NAME, null, newTaskValues);
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* Add a device record to table.
	*/
	public long updateDevice(Device device) {
		SQLiteDatabase db = mAdapter.getDatabase();
		ContentValues newTaskValues = new ContentValues();
		newTaskValues.put(KEY_DEVICE_NO, device.deviceId);
		newTaskValues.put(KEY_DEVICE_NAME, device.deviceName);
		newTaskValues.put(KEY_DEVICE_IMEI, device.deviceIMEI);
		newTaskValues.put(KEY_DEVICE_STATUS, device.deviceStatus);
		if(device.lastKnownLocation!=null){
			newTaskValues.put(KEY_DEVICE_LATITUDE, device.lastKnownLocation.lat());
			newTaskValues.put(KEY_DEVICE_LONGITUDE, device.lastKnownLocation.lng());
		}
		newTaskValues.put(KEY_DEVICE_POWER_LEVEL, device.devicePowerLevel);
		return db.update(TABLE_NAME, newTaskValues, 
				KEY_DEVICE_NO+"='"+device.deviceId
				+"'", null);
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* Add a device record to table.
	*/
	public long deleteDevice(String deviceNo) {
		SQLiteDatabase db = mAdapter.getDatabase();
		return db.delete(TABLE_NAME, KEY_DEVICE_NO + "='" + deviceNo + "'",
				null);
	}

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 24OCT2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* get Device with given id
	* @param imei 
	* @return device ,null if not found.
	*/
	public ArrayList<Device> getAllDevices() {
		ArrayList<Device> devices = null;
		try {
			SQLiteDatabase db = mAdapter.getDatabase();
			Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ID,
					KEY_DEVICE_NO, KEY_DEVICE_IMEI, KEY_DEVICE_NAME,
					KEY_DEVICE_STATUS, KEY_DEVICE_LATITUDE,
					KEY_DEVICE_LONGITUDE, KEY_DEVICE_POWER_LEVEL }, null, null,
					null, null, null, null);
			int recordCount = cursor.getCount();
			if (recordCount > 0 && cursor.moveToFirst()) {
				devices = new ArrayList<Device>();
				for (int i = 0; i < recordCount; i++) {

					Device device = new Device();
					device.deviceId = cursor.getString(ID_DEVICE_NO);
					device.deviceIMEI = cursor.getString(ID_DEVICE_IMEI);
					device.deviceName = cursor.getString(ID_KEY_DEVICE_NAME);
					device.deviceStatus = cursor.getString(ID_DEVICE_STATUS);
					try{
					String latitude = cursor.getString(ID_DEVICE_LATITUDE);
					String logitude = cursor.getString(ID_DEVICE_LONGITUDE);
					
					String location = "[" + logitude + "," + latitude + ",0]";
					device.lastKnownLocation = MapLayer
							.fromStringToLatLng(location);
					}catch(Exception e){
						//ingore latitude/longigude
					}
					device.devicePowerLevel = cursor
							.getString(ID_DEVICE_POWER_LEVEL);
					devices.add(device);
					cursor.moveToNext();
				}

			}
		} catch (Exception e) {
			mLoger.info("Unable to get given device:" + e.getMessage());
		}
		return devices;
	}
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 24OCT2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* get Device with given id
	* @param imei 
	* @return device ,null if not found.
	*/
	public Device getDeviceByImei(String imei) {
		Device device = null;
		try {
			SQLiteDatabase db = mAdapter.getDatabase();
			Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ID,
					KEY_DEVICE_NO, KEY_DEVICE_IMEI, KEY_DEVICE_NAME,
					KEY_DEVICE_STATUS,KEY_DEVICE_LATITUDE,
					KEY_DEVICE_LONGITUDE,KEY_DEVICE_POWER_LEVEL}, KEY_DEVICE_IMEI + "='" + imei
					+ "'", null, null, null, null, null);
			int recordCount = cursor.getCount();
			if (recordCount == 1 && cursor.moveToFirst()) {
				device = new Device();
				device.deviceId = cursor.getString(ID_DEVICE_NO);
				device.deviceIMEI = cursor.getString(ID_DEVICE_IMEI);
				device.deviceName= cursor.getString(ID_KEY_DEVICE_NAME);
				device.deviceStatus = cursor.getString(ID_DEVICE_STATUS);
				String latitude=cursor.getString(ID_DEVICE_LATITUDE);
				String logitude=cursor.getString(ID_DEVICE_LONGITUDE);
				String location="["+logitude+","+latitude+",0]";
				device.lastKnownLocation=MapLayer.fromStringToLatLng(location);
				device.devicePowerLevel=cursor.getString(ID_DEVICE_POWER_LEVEL);
	
			}
		} catch (Exception e) {
			mLoger.info("Unable to get given device:" + e.getMessage());
		}
		return device;
	}
	
	private final static String KEY_ID ="_id";
	private final static String KEY_DEVICE_NO ="deviceNo";
	private final static String KEY_DEVICE_IMEI ="deviceImei";
	private final static String KEY_DEVICE_NAME ="deviceName";
	private final static String KEY_DEVICE_STATUS ="deviceStatus";
	private final static String KEY_DEVICE_LATITUDE ="latitude";
	private final static String KEY_DEVICE_LONGITUDE ="longitude";
	private final static String KEY_DEVICE_POWER_LEVEL ="devicePowerLevel";

	
	//private final static int ID_ID=0;
	private final static int ID_DEVICE_NO=1;
	private final static int ID_DEVICE_IMEI=2;
	private final static int ID_KEY_DEVICE_NAME=3;
	private final static int ID_DEVICE_STATUS=4;
	private final static int ID_DEVICE_LATITUDE=5;
	private final static int ID_DEVICE_LONGITUDE=6;
	private final static int ID_DEVICE_POWER_LEVEL=7;

	private final static String TABLE_NAME="Device";
	
	private static Logger mLoger
       =Logger.getLogger(DeviceDBMapper.class.getName());
}
