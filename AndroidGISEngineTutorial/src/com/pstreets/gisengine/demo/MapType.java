package com.pstreets.gisengine.demo;

import com.mapdigit.gis.geometry.GeoLatLng;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapType extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onStart() {
		super.onStart();
		GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
		SharedMapInstance.map.setCenter(center, 13,
				com.mapdigit.gis.raster.MapType.MICROSOFTCHINA);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.maptype_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.googlemaptype:
			SharedMapInstance.map
			  .setMapType(com.mapdigit.gis.raster.MapType.GOOGLECHINA);
			return true;
		case R.id.bingmaptype:
			SharedMapInstance.map
			   .setMapType(com.mapdigit.gis.raster.MapType.MICROSOFTCHINA);
			return true;
		case R.id.mapabcmaptype:
			SharedMapInstance.map
			   .setMapType(com.mapdigit.gis.raster.MapType.MAPABCCHINA);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
