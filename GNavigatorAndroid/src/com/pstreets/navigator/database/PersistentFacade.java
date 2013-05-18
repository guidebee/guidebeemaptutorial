//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 SWIFTNETWORKS
//                           ALL RIGHTS RESERVED.
//                     EITS CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 22SEP2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.navigator.database;

import com.pstreets.navigator.dataobject.Device;
import com.pstreets.navigator.dataobject.DeviceLocationHistory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//22SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * This class is a single point to access the database.
 * <hr>
 * <b>&copy; Copyright 2011 Swiftnetworks, Inc. All Rights Reserved.</b>
 * 
 * @version 1.00, 22/09/11
 * @author Swiftnetworks Pty Ltd.
 */
@Singleton
public class PersistentFacade {

    ////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Open the database.
	 */
	public void open() {
		mAdapter.open();
	}

    ////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * close the database.
	 */
	public void close() {
		mAdapter.close();
	}

    ////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * reset the db adapter.
	 */
	public void resetDBAdapter(DBAdapter dbAdapter) {
		mAdapter = dbAdapter;
	}

	
    ////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructor.
	 */
	@Inject
	public PersistentFacade(Context context) {
		mContext = context;
		mAdapter = new DBAdapter(mContext, DBAdapter.DATABASE_NAME);
	}

    ////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * delete database.
	 */
	public boolean deleteDatabase() {
		return mAdapter.deleteDatabase();
	}

	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * get database.
	 */
	public SQLiteDatabase getDatabase() {
		return mAdapter.getDatabase();
	}


    ////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * check to see if database exists
	 * 
	 * @return true exist
	 */
	public boolean checkIfDatabaseExists() {
		return mAdapter.checkIfDatabaseExists();
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
		DeviceDBMapper dbMapper=new DeviceDBMapper(mAdapter);
		return dbMapper.addDevice(device);
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
		DeviceDBMapper dbMapper=new DeviceDBMapper(mAdapter);
		return dbMapper.deleteDevice(deviceNo);
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
		DeviceDBMapper dbMapper=new DeviceDBMapper(mAdapter);
		return dbMapper.getAllDevices();
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
		DeviceDBMapper dbMapper=new DeviceDBMapper(mAdapter);
		return dbMapper.getDeviceByImei(imei);
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
		DeviceLocationHistoryDBMapper dbMapper=new DeviceLocationHistoryDBMapper(mAdapter);
		return dbMapper.getDeviceLocationHistory(deviceNo,limit);
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
		DeviceLocationHistoryDBMapper dbMapper=new DeviceLocationHistoryDBMapper(mAdapter);
		return dbMapper.addDeviceLocationHistory(deviceLocationHistory);
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
		DeviceDBMapper dbMapper=new DeviceDBMapper(mAdapter);
		return dbMapper.updateDevice(device);
	}

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* export data in the database to an xml file.
	* @param filename
	*/
	public void exportData(String filename){
		mAdapter.exportData(filename);
	}

	private Context mContext;

	private DBAdapter mAdapter;
}
