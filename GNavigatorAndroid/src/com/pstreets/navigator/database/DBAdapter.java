//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 SWIFTNETWORKS
//                           ALL RIGHTS RESERVED.
//                     EITS CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 20SEP2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.navigator.database;

//--------------------------------- IMPORTS ------------------------------------
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//20SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* This class stands for a sqlite database adapter.
* <hr>
* <b>&copy; Copyright 2011 Swiftnetworks, Inc. All Rights Reserved.</b>
* 
* @version 1.00, 20/09/11
* @author Swiftnetworks Pty Ltd.
*/
class DBAdapter extends SQLiteOpenHelper {
	
	/**
	 * database name
	 */
	public static String DATABASE_NAME = "gnavigator.db";
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructor.
	 * @param context
	 */
	public DBAdapter(Context context,String dbName) {
		super(context, dbName, null, DATABASE_VERSION);
		mDatabaseName=dbName;
		mContext=context;

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
	public boolean deleteDatabase(){
		return mContext.deleteDatabase(getDatabaseFileName());
	}

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * check to see if database exists
	 * @return true exist
	 */
	public boolean checkIfDatabaseExists(){
		File file=new File(getDatabaseFileName());
		return file.exists();
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Get the sqlite database object.
	 * @return
	 */
	public SQLiteDatabase getDatabase() {
		return mDb;
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
		if (mDb != null) {
			mDb.close();
		}
	}

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
		try {
			mDb = getWritableDatabase();
		} catch (SQLiteException ex) {
			mDb = getReadableDatabase();
		}
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
		
		open();
		DatabaseAssistant assistent=new DatabaseAssistant(mDb,filename);
		assistent.exportData();
		close();
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			createLocalTables(db);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// leave it empty, because the database will always be 
		//created from downloading
		
	}
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 22SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * create local tables
	 * @throws IOException
	 */
	private void createLocalTables(SQLiteDatabase db) throws IOException {
		String path="database/create.sql";
		InputStream is = mContext.getAssets().open(path);
		InputStreamReader isr=new InputStreamReader(is);
		LineNumberReader lnr=new LineNumberReader(isr);
		String sql=lnr.readLine();
		while(sql!=null && sql.length()>0){
			db.execSQL(sql);
			sql=lnr.readLine();
		}
		is.close();
	}

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 20SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * get the database name.
	 * @return
	 */
	private String getDatabaseFileName(){

		String appName=mContext.getApplicationInfo().dataDir;
		return appName+File.separator+"databases"+File.separator+mDatabaseName;
	}
	
	protected String mDatabaseName=DATABASE_NAME;
	
	
	/**
	 * database version.e
	 */
	private static final int DATABASE_VERSION = 1;

	/**
	 * SQL database object.
	 */
	private SQLiteDatabase mDb = null;
	
	/**
	 * context object.
	 */
	private final Context mContext;

	
}
