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
import com.pstreets.navigator.dataobject.DeviceLocationHistory;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.logging.Logger;

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
class DeviceLocationHistoryDBMapper extends DBMapper{

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
	public DeviceLocationHistoryDBMapper(DBAdapter adapter) {
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
	public long addDeviceLocationHistory(DeviceLocationHistory deviceLocationHistory) {
		SQLiteDatabase db = mAdapter.getDatabase();
		ContentValues newTaskValues = new ContentValues();
		newTaskValues.put(KEY_DEVICE_NO, deviceLocationHistory.deviceNo);
		newTaskValues.put(KEY_DEVICE_LATITUDE, deviceLocationHistory.latitude);
		newTaskValues.put(KEY_DEVICE_LONGITUDE, deviceLocationHistory.longitude);
		newTaskValues.put(KEY_TIME_STAMP, deviceLocationHistory.timeStamp);
		return db.insert(TABLE_NAME, null, newTaskValues);
	}
	
	

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 24OCT2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* get Device history 
	* @param imei 
	* @return device ,null if not found.
	*/
	public ArrayList<DeviceLocationHistory> getDeviceLocationHistory(String deviceNo,int limit) {
		ArrayList<DeviceLocationHistory> deviceLocationHistories = null;
		try {
			SQLiteDatabase db = mAdapter.getDatabase();
			Cursor cursor = db.query(true, TABLE_NAME, new String[] { KEY_ID,
					KEY_DEVICE_NO, KEY_TIME_STAMP, 
					KEY_DEVICE_LATITUDE,
					KEY_DEVICE_LONGITUDE }, KEY_DEVICE_NO+"='"+deviceNo +"'", null,
					null, null, null, null);
			int recordCount = cursor.getCount();
			if (recordCount > 0 && cursor.moveToFirst()) {
				deviceLocationHistories = new ArrayList<DeviceLocationHistory>();
				for (int i = 0; i < recordCount; i++) {

					DeviceLocationHistory deviceHistroy = new DeviceLocationHistory();
					deviceHistroy.deviceNo = cursor.getString(ID_DEVICE_NO);
					deviceHistroy.timeStamp = cursor.getLong(ID_TIME_STAMP);
					deviceHistroy.latitude= cursor.getString(ID_DEVICE_LATITUDE);
					deviceHistroy.longitude = cursor.getString(ID_DEVICE_LONGITUDE);
						deviceLocationHistories.add(deviceHistroy);
					cursor.moveToNext();
				}

			}
		} catch (Exception e) {
			mLoger.info("Unable to get given device location history:" + e.getMessage());
		}
		return deviceLocationHistories;
	}

	
	private final static String KEY_ID ="_id";
	private final static String KEY_DEVICE_NO ="deviceNo";
	private final static String KEY_TIME_STAMP ="timeStamp";
	private final static String KEY_DEVICE_LATITUDE ="latitude";
	private final static String KEY_DEVICE_LONGITUDE ="longitude";


	
	//private final static int ID_ID=0;
	private final static int ID_DEVICE_NO=1;
	private final static int ID_TIME_STAMP=2;
	private final static int ID_DEVICE_LATITUDE=3;
	private final static int ID_DEVICE_LONGITUDE=4;


	private final static String TABLE_NAME="DeviceLocationHistory";
	
	private static Logger mLoger
       =Logger.getLogger(DeviceLocationHistoryDBMapper.class.getName());
}
