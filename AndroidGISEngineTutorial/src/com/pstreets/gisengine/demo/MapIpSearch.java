package com.pstreets.gisengine.demo;

import com.mapdigit.gis.DigitalMap;
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.service.IIpAddressGeocodingListener;
import com.mapdigit.gis.service.IpAddressLocation;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapIpSearch extends Activity implements
		IIpAddressGeocodingListener {

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
		SharedMapInstance.map.setIpAddressGeocodingListener(this);

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
			SharedMapInstance.map.getIpLocations("58.192.32.1");
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public void done(String query, IpAddressLocation result) {
		if (result != null && result.error.length() == 0
				&& result.longitude.length() > 0
				&& result.longitude.length() > 0) {
			try {

				MapPoint mapPoint = new MapPoint();
				String latLng = "[" + result.longitude + "," + result.latitude
						+ ",0]";
				mapPoint.point = DigitalMap.fromStringToLatLng(latLng);
				mapPoint.setName(result.organization);
				mapPoint.setNote(result.city + " " + result.country);
				SharedMapInstance.map.panTo(mapPoint.point);
			} catch (Exception e) {

				result.error = "IP_NOT_FOUND";
			}
		}

	}

	@Override
	public void readProgress(int bytes, int total) {

	}

}
