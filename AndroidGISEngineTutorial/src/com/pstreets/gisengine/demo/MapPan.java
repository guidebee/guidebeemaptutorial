package com.pstreets.gisengine.demo;

import com.mapdigit.gis.geometry.GeoLatLng;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;
import com.mapdigit.gis.raster.MapType;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapPan extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onStart() {
		super.onStart();
		GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
		SharedMapInstance.map.setCenter(center, 13, MapType.MICROSOFTCHINA);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mappan_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.mapup:
			SharedMapInstance.map.panDirection(0, -32);
			return true;
		case R.id.mapdown:
			SharedMapInstance.map.panDirection(0, 32);
			return true;
		case R.id.mapleft:
			SharedMapInstance.map.panDirection(-32, 0);
			return true;
		case R.id.mapright:
			SharedMapInstance.map.panDirection(32, 0);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

}
