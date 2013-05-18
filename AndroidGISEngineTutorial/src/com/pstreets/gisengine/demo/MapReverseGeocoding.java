package com.pstreets.gisengine.demo;

import com.mapdigit.gis.DigitalMap;
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.service.IReverseGeocodingListener;
import com.mapdigit.gis.service.IpAddressLocation;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapReverseGeocoding extends Activity implements
    IReverseGeocodingListener {

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
		SharedMapInstance.map.setReverseGeocodingListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mapgeocoding_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.findaddress:
			SharedMapInstance.map.getReverseLocations("32.061699,118.777802");
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void done(String arg0, MapPoint[] result) {
        if (result != null) {
        	SharedMapInstance.map.panTo(result[0].getPoint());
        }
    }

	@Override
	public void readProgress(int bytes, int total) {

	}

}
