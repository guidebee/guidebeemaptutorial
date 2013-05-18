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
package com.pstreets.navigator.database;

//--------------------------------- IMPORTS ------------------------------------
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//23SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* Database assistant to dump data to another location.
* <hr>
* <b>&copy; Copyright 2011 Swiftnetworks, Inc. All Rights Reserved.</b>
* 
* @version 1.00, 23/09/11
* @author Swiftnetworks Pty Ltd.
*/
class DatabaseAssistant {
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 23SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructor
	 * @param context
	 * @param db
	 * @param destXml
	 */
	public DatabaseAssistant(SQLiteDatabase db,String destXml) {
		mDb = db;
		mDestXmlFilename=destXml;

		try {
			// create a file on the sdcard to export the
			// database contents to
			File myFile = new File(mDestXmlFilename);
			myFile.createNewFile();

			FileOutputStream fOut = new FileOutputStream(myFile);
			BufferedOutputStream bos = new BufferedOutputStream(fOut);

			mExporter = new Exporter(bos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 23SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Export all data to xml.
	 */
	public void exportData() {
		mLoger.info("Exporting Data");

		try {
			mExporter.startDbExport(mDb.getPath());

			// get the tables out of the given sqlite database
			String sql = "SELECT * FROM sqlite_master";

			Cursor cur = mDb.rawQuery(sql, new String[0]);
			//mLoger.info("show tables, cur size " + cur.getCount());
			cur.moveToFirst();

			String tableName;
			while (cur.getPosition() < cur.getCount()) {
				tableName = cur.getString(cur.getColumnIndex("name"));
				//mLoger.info("table name " + tableName);

				// don't process these two tables since they are used
				// for metadata
				if (!tableName.equals("android_metadata")
						&& !tableName.equals("sqlite_sequence")) {
					exportTable(tableName);
				}

				cur.moveToNext();
			}
			mExporter.endDbExport();
			mExporter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportTable(String tableName) throws IOException {
		mExporter.startTable(tableName);

		// get everything from the table
		String sql = "select * from " + tableName;
		Cursor cur = mDb.rawQuery(sql, new String[0]);
		int numcols = cur.getColumnCount();

		mLoger.info("Start exporting table " + tableName);

		// logging
		//for( int idx = 0; idx < numcols; idx++ )
		// {
		//	mLoger.info( "column " + cur.getColumnName(idx) );
		// }

		cur.moveToFirst();

		// move through the table, creating rows
		// and adding each column with name and value
		// to the row
		while (cur.getPosition() < cur.getCount()) {
			mExporter.startRow();
			String name;
			String val;
			for (int idx = 0; idx < numcols; idx++) {
				name = cur.getColumnName(idx);
				val = cur.getString(idx);
				//mLoger.info("col '" + name + "' -- val '" + val + "'");

				mExporter.addColumn(name, val);
			}

			mExporter.endRow();
			cur.moveToNext();
		}

		cur.close();

		mExporter.endTable();
	}

	private static Logger mLoger
      =Logger.getLogger(DatabaseAssistant.class.getName());
	
	private String mDestXmlFilename = "/sdcard/export.xml";

	private SQLiteDatabase mDb;
	private Exporter mExporter;

	class Exporter {
		private static final String CLOSING_WITH_TICK = "'>";
		private static final String START_DB = "<export-database name='";
		private static final String END_DB = "</export-database>";
		private static final String START_TABLE = "<table name='";
		private static final String END_TABLE = "</table>";
		private static final String START_ROW = "<row>";
		private static final String END_ROW = "</row>";
		private static final String START_COL = "<col name='";
		private static final String END_COL = "</col>";

		private BufferedOutputStream mbufferos;

		public Exporter() throws FileNotFoundException {
			this(new BufferedOutputStream(new FileOutputStream(mDestXmlFilename)));

		}

		public Exporter(BufferedOutputStream bos) {
			mbufferos = bos;
		}

		public void close() throws IOException {
			if (mbufferos != null) {
				mbufferos.close();
			}
		}

		public void startDbExport(String dbName) throws IOException {
			String stg = START_DB + dbName + CLOSING_WITH_TICK;
			mbufferos.write(stg.getBytes());
		}

		public void endDbExport() throws IOException {
			mbufferos.write(END_DB.getBytes());
		}

		public void startTable(String tableName) throws IOException {
			String stg = START_TABLE + tableName + CLOSING_WITH_TICK;
			mbufferos.write(stg.getBytes());
		}

		public void endTable() throws IOException {
			mbufferos.write(END_TABLE.getBytes());
		}

		public void startRow() throws IOException {
			mbufferos.write(START_ROW.getBytes());
		}

		public void endRow() throws IOException {
			mbufferos.write(END_ROW.getBytes());
		}

		public void addColumn(String name, String val) throws IOException {
			String stg = START_COL + name + CLOSING_WITH_TICK + val + END_COL;
			mbufferos.write(stg.getBytes());
		}
	}

}
