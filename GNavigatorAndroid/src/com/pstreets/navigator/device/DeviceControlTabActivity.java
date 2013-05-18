package com.pstreets.navigator.device;

import com.pstreets.navigator.GNavigatorActivity;
import com.pstreets.navigator.R;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import roboguice.activity.RoboTabActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;

import java.util.logging.Logger;

public class DeviceControlTabActivity extends RoboTabActivity{
	
	@InjectResource (R.string.deviceinfo) String deviceInfoString;
	@InjectResource (R.string.devicelocationlist) String deviceLocationString;
	@InjectResource (R.string.devicecommand) String deviceCommadnString;
	@InjectResource (R.drawable.device) Drawable deviceImage;
	@InjectResource (R.drawable.location) Drawable locationImage;
	@InjectResource (R.drawable.command) Drawable commandImage;
	@InjectView (R.id.btnMap) ImageButton btnMap;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLoger.info("onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.setContentView(R.layout.devicecontroltab);
		TabHost tabHost = getTabHost();
		tabHost.setup();
		Intent intent;
		intent = new Intent().setClass(this, DeviceInfoActivity.class);
		deviceSpec = tabHost.newTabSpec(deviceInfoString)
				.setIndicator(createIndicator(deviceInfoString, deviceImage))
				.setContent(intent);
		tabHost.addTab(deviceSpec);
		intent = new Intent().setClass(this,
				DeviceLocationHistoryListActivity.class);
		locationSpec = tabHost
				.newTabSpec(deviceLocationString)
				.setIndicator(
						createIndicator(deviceLocationString, locationImage))
				.setContent(intent);
		tabHost.addTab(locationSpec);
		intent = new Intent().setClass(this, DeviceCommandListActivity.class);
		commandSpec = tabHost
				.newTabSpec(deviceCommadnString)
				.setIndicator(
						createIndicator(deviceCommadnString, commandImage))
				.setContent(intent);
		tabHost.addTab(commandSpec);
		btnMap.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(DeviceControlTabActivity.this,
						GNavigatorActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
		});

	}

	private TabHost.TabSpec deviceSpec;
	private TabHost.TabSpec locationSpec;
	private TabHost.TabSpec commandSpec;

	private View createIndicator(String labelId, Drawable drawable) {

		View tabIndicator = LayoutInflater.from(this).inflate(
				R.layout.titletab_indicator, getTabWidget(), false);

		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageDrawable(drawable);
		return tabIndicator;

	}

	private static Logger mLoger = Logger
			.getLogger(DeviceControlTabActivity.class.getName());

}
