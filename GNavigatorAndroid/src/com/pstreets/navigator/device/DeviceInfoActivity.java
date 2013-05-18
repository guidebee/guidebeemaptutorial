package com.pstreets.navigator.device;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.google.inject.Inject;
import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SessionInfo;
import com.pstreets.navigator.database.PersistentFacade;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class DeviceInfoActivity extends RoboActivity {
	
	@InjectView (R.id.txtDeviceName) TextView txtDeviceName;
	@InjectView (R.id.txtDeviceNo) TextView txtDeviceNo;
	@InjectView (R.id.txtDeviceIMEI) TextView txtDeviceIMEI;
	@InjectView (R.id.txtDeviceLocation) TextView txtDeviceLocation;
	@InjectView (R.id.txtDeviceStatus) TextView txtDeviceStatus;
	@InjectView (R.id.txtDevicePower) TextView txtDevicePower;
	@Inject PersistentFacade persistentFacade;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		setContentView(R.layout.deviceinfo);
	}
	
	public void onResume(){
		super.onResume();
		if(SessionInfo.currentDevice!=null){
			txtDeviceName.setText(SessionInfo.currentDevice.deviceName);
			txtDeviceNo.setText(SessionInfo.currentDevice.deviceId);
			txtDeviceIMEI.setText(SessionInfo.currentDevice.deviceIMEI);
			//txtDeviceLocation.setText(SessionInfo.currentDevice.);
			if(SessionInfo.currentDevice.lastKnownLocation!=null){
				txtDeviceLocation.setText(SessionInfo.currentDevice.lastKnownLocation.toUrlValue(6));
			}else{
				txtDeviceLocation.setText("");
			}
			txtDeviceStatus.setText(SessionInfo.currentDevice.deviceStatus);
			txtDevicePower.setText(SessionInfo.currentDevice.devicePowerLevel);
		}else{
			txtDeviceName.setText("");
			txtDeviceNo.setText("");
			txtDeviceIMEI.setText("");
			txtDeviceLocation.setText("");
			txtDeviceStatus.setText("");
			txtDevicePower.setText("");
		}
	}

}
