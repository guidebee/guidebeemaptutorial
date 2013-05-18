package com.pstreets.navigator.device;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Logger;

import roboguice.activity.RoboListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.google.inject.Inject;
import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.pstreets.navigator.GNavigatorActivity;
import com.pstreets.navigator.app.SessionInfo;
import com.pstreets.navigator.app.SharedMapInstance;
import com.pstreets.navigator.database.PersistentFacade;
import com.pstreets.navigator.dataobject.DeviceLocationHistory;

public class DeviceLocationHistoryListActivity extends RoboListActivity {

	@Inject	PersistentFacade persistentFacade;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
			Locale.US);

	ArrayList<DeviceLocationHistory> locationHistory;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoger.info("onCreate");
	}

	public void onResume() {
		super.onResume();
		resetLocationList();
	}

	private void resetLocationList() {
		if (SessionInfo.currentDevice != null) {
			persistentFacade.open();
			locationHistory = persistentFacade.getDeviceLocationHistory(
					SessionInfo.currentDevice.deviceId, 50);
			String[] strings=null;
			if (locationHistory != null) {
				strings = new String[locationHistory.size()];
				for (int i = 0; i < locationHistory.size(); i++) {
					DeviceLocationHistory deviceLocationHistory = locationHistory
							.get(i);
					String location = "[" + deviceLocationHistory.longitude
							+ "," + deviceLocationHistory.latitude + ",0]";
					GeoLatLng latLng = MapLayer.fromStringToLatLng(location);
					Date date = new Date(deviceLocationHistory.timeStamp);
					strings[i] = sdf.format(date) + "\r\n  "
							+ latLng.toUrlValue(6);
				}

				setListAdapter(new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1, strings));
				getListView().setTextFilterEnabled(true);
				getListView().setOnItemClickListener(onChildClickListener);
				
			}else{
				setListAdapter(null);
			}
			
			
			persistentFacade.close();
		}
	}

	OnItemClickListener onChildClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			DeviceLocationHistory deviceLocationHistory = locationHistory
					.get(position);
			String location = "[" + deviceLocationHistory.longitude + ","
					+ deviceLocationHistory.latitude + ",0]";
			GeoLatLng latLng = MapLayer.fromStringToLatLng(location);
			SharedMapInstance.map.setCenter(latLng, 15);

			Intent intent = new Intent(DeviceLocationHistoryListActivity.this,
					GNavigatorActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);

		}
	};

	private static Logger mLoger = Logger
			.getLogger(DeviceLocationHistoryListActivity.class.getName());

}
