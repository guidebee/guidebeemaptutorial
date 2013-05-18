package com.pstreets.gisengine.demo;

import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.service.DigitalMapService;
import com.mapdigit.gis.service.IRoutingListener;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapServiceType extends Activity implements IRoutingListener {

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
		//SharedMapInstance.map.setCurrentMapService(DigitalMapService.GOOGLE_MAP_SERVICE);
		// SharedMapInstance.map.setCurrentMapService(DigitalMapService.MAPABC_MAP_SERVICE);
		SharedMapInstance.map.setCurrentMapService(DigitalMapService.CLOUDMADE_MAP_SERVICE);
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
			GeoLatLng latLng1 = new GeoLatLng(32.0418381, 118.7788905);
			GeoLatLng latLng2 = new GeoLatLng(39.11643, 117.180908);
			SharedMapInstance.map.getDirections(new GeoLatLng[] { latLng1,
					latLng2 });

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
			//SharedMapInstance.map.setZoom(5);
			SharedMapInstance.map.setZoom(15);
			SharedMapInstance.map.panTo(result.routes[0].startGeocode.point);

		}

	}

	@Override
	public void readProgress(int bytes, int total) {

	}

}
