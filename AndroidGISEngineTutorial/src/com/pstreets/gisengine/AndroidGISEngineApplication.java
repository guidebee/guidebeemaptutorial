//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 22JAN2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.gisengine;

//--------------------------------- IMPORTS ------------------------------------
import java.io.InputStream;

import android.app.Application;
import android.preference.PreferenceManager;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.raster.MapConfiguration;
import com.mapdigit.gis.raster.MapTileDownloadManager;
import com.mapdigit.gis.raster.RasterMap;

import com.pstreets.gisengine.drawing.AndroidGraphicsFactory;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//22JAN2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * GIS Engine tutorial application. Just feed the licence info.
 * <hr>
 * <b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * 
 * @version 1.00, 22/01/11
 * @author Guidebee Pty Ltd.
 */
public class AndroidGISEngineApplication extends Application {

	@Override
	public void onCreate() {
		PreferenceManager.setDefaultValues(this, R.xml.default_values, false);
		try {
			
			MapConfiguration.setParameter(MapConfiguration.IS_CACHE_ON, true);
			MapConfiguration.setParameter(
					MapConfiguration.WORKER_THREAD_NUMBER, 1);
			MapConfiguration.setParameter(
					MapConfiguration.IGNORE_MAP_TYPE_FOR_STORED_MAP, false);
			MapLayer.setAbstractGraphicsFactory(AndroidGraphicsFactory
					.getInstance());

			SharedMapInstance.mapImage = MapLayer.getAbstractGraphicsFactory()
					.createImage(512, 512);
			SharedMapInstance.mapGraphics = SharedMapInstance.mapImage
					.getGraphics();
			SharedMapInstance.mapTileDownloadManager 
			= new MapTileDownloadManager();

			try {
				SharedMapInstance.map = new RasterMap(1024, 1024,
						SharedMapInstance.mapTileDownloadManager);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SharedMapInstance.map.setViewSize(512,512);
			SharedMapInstance.mapTileDownloadManager.start();

		} catch (Exception e) {

		}
	}

	@Override
	public void onTerminate() {
		if (SharedMapInstance.mapTileDownloadManager != null) {
			SharedMapInstance.mapTileDownloadManager.stop();
		}
	}
}
