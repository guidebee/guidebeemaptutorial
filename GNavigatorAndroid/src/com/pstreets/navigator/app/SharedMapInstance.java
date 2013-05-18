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
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapTileDownloadManager;
import com.mapdigit.gis.raster.RasterMap;

import java.io.InputStream;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//20AUG2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* Shared map instance.
* <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* @version     1.00, 20/08/11
* @author      Guidebee Pty Ltd.
*/
public abstract class SharedMapInstance {

	/**
	 * Raster map instance
	 */
	public static RasterMap map;
	/**
	 * map image instance
	 */
	public static IImage mapImage;
	/**
	 * Map graphics instance
	 */
	public static IGraphics mapGraphics;
	/**
	 * map tile download instance
	 */
	public static MapTileDownloadManager mapTileDownloadManager;

	/**
	 * world map instance.
	 */
	public static InputStream worldMapFileStream;
	
	
	public static boolean centerMyLocation=false;
	
	public static boolean myLocationImage=false;
	
	public static GeoLatLng currentLocation=new GeoLatLng(0,0);
	
	public static boolean chinaMapOffset=false;
	
	
	
}
