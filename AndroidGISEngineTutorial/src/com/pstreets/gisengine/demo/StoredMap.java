package com.pstreets.gisengine.demo;

import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapTileStreamReader;
import com.mapdigit.gis.raster.MapType;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;

import android.app.Activity;
import android.os.Bundle;

public class StoredMap extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onStart() {
		super.onStart();
		GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
		SharedMapInstance.map.setCenter(center, 4, MapType.MICROSOFTCHINA);
		MapTileStreamReader streamReader=SharedMapInstance
		   .mapTileDownloadManager.getInteralMapTileStreamReader();

		try{
			FileMapTiledZone mapTileZone=new FileMapTiledZone("/sdcard/guidebee/world.map",false);
			streamReader.addZone(mapTileZone);
			mapTileZone=new FileMapTiledZone("/sdcard/guidebee/china.map",false);
			streamReader.addZone(mapTileZone);
			mapTileZone=new FileMapTiledZone("/sdcard/guidebee/nanjing.map",false);
			streamReader.addZone(mapTileZone);
			streamReader.open();
		}catch(Exception e){
			
		}

	}

}
