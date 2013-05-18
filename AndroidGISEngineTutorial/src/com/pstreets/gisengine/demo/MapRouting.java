package com.pstreets.gisengine.demo;

import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.service.IRoutingListener;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapRouting extends Activity implements IRoutingListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onStart() {
		super.onStart();
		GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
		SharedMapInstance.map.setCenter(center, 15,
				com.mapdigit.gis.raster.MapType.MICROSOFTCHINA);
		SharedMapInstance.map.setRoutingListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.maprouting_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.getdirection:
			String name1 = "南京";
			String name2 = "北京";
			SharedMapInstance.map.getDirections("from: " + name1 + " to: "
					+ name2);

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void done(String query, MapDirection result) {
		if (result != null) {
			SharedMapInstance.map.setMapDirection(result);
			SharedMapInstance.map.resize(result.getBound());
			SharedMapInstance.map.zoomOut();
			SharedMapInstance.map.zoomOut();

		}

	}

	@Override
	public void readProgress(int bytes, int total) {

	}

}
