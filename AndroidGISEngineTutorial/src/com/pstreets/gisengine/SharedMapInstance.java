package com.pstreets.gisengine;

import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.drawing.IImage;
import com.mapdigit.gis.raster.IReaderListener;
import com.mapdigit.gis.raster.MapTileDownloadManager;
import com.mapdigit.gis.raster.RasterMap;

public abstract class SharedMapInstance {

	public static RasterMap map;
	public static IImage mapImage;
	public static IGraphics mapGraphics;
	public static MapTileDownloadManager mapTileDownloadManager;

}
