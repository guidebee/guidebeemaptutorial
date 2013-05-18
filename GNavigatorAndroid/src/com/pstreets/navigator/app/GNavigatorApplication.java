//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 20AUG2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.navigator.app;

//--------------------------------- IMPORTS ------------------------------------
import com.pstreets.navigator.GNavigatorActivity;
import com.pstreets.navigator.R;
import com.pstreets.navigator.drawing.AndroidGraphicsFactory;
import com.pstreets.navigator.gis.FileMapTiledZone;

import android.content.Intent;
import android.os.Environment;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.raster.MapConfiguration;
import com.mapdigit.gis.raster.MapTileDownloadManager;
import com.mapdigit.gis.raster.MapTileStreamReader;
import com.mapdigit.gis.raster.MapTiledZone;

import com.mapdigit.util.Log;

import roboguice.application.RoboApplication;

import java.io.DataInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//20AUG2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* This is the application class.
* <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* @version     1.00, 20/08/11
* @author      Guidebee Pty Ltd.
*/
public class GNavigatorApplication extends RoboApplication implements
		Thread.UncaughtExceptionHandler {

	@Override
	public void onCreate() {

		try {
			InputStream inputStream = getResources().openRawResource(
					R.raw.guidebee);
			
			MapConfiguration.setParameter(MapConfiguration.IS_CACHE_ON, true);
			MapConfiguration.setParameter(
					MapConfiguration.WORKER_THREAD_NUMBER, 16);
			MapConfiguration.setParameter(
					MapConfiguration.IGNORE_MAP_TYPE_FOR_STORED_MAP, true);
			MapLayer.setAbstractGraphicsFactory(AndroidGraphicsFactory
					.getInstance());
			inputStream.close();
			SharedMapInstance.mapTileDownloadManager = new MapTileDownloadManager();

			MapTileStreamReader streamReader = SharedMapInstance.mapTileDownloadManager
					.getInteralMapTileStreamReader();

			SharedMapInstance.worldMapFileStream = getResources()
					.openRawResource(R.raw.world);
			MapTiledZone worldmapZone = new MapTiledZone(new DataInputStream(
					SharedMapInstance.worldMapFileStream));
			streamReader.addZone(worldmapZone);
			try {
				File sdcardDir = Environment.getExternalStorageDirectory();
				File guidebeeDir = new File(sdcardDir, "guidebee");
				if (guidebeeDir.exists()) {
					FilenameFilter filter = new FilenameFilter() {
						public boolean accept(File dir, String filename) {
							if (filename.endsWith(".map")) {
								return true;
							}
							return false;

						}
					};

					String[] allMapfiles = guidebeeDir.list(filter);
					String path = guidebeeDir.getAbsolutePath();
					if (allMapfiles != null) {
						for (int i = 0; i < allMapfiles.length; i++) {

							FileMapTiledZone mapTileZone = new FileMapTiledZone(
									path + File.separator + allMapfiles[i],
									false);
							Log.p(allMapfiles[i]);
							streamReader.addZone(mapTileZone);
						}
						
					}
				}

			} catch (Exception e) {

			}finally{
				streamReader.open();
			}

		} catch (Exception e) {

		}

		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void onTerminate() {
		if (SharedMapInstance.mapTileDownloadManager != null) {
			SharedMapInstance.mapTileDownloadManager.stop();
		}
		MapTileStreamReader streamReader = SharedMapInstance.mapTileDownloadManager
				.getInteralMapTileStreamReader();
		if (streamReader != null) {
			try {
				streamReader.close();
				if (SharedMapInstance.worldMapFileStream != null) {
					SharedMapInstance.worldMapFileStream.close();
				}
			} catch (Exception e) {
			}
		}
	}


	public void uncaughtException(Thread thread, Throwable ex) {

		Intent intent = new Intent(this, GNavigatorActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(intent);
	}

}
