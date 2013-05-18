package com.pstreets.navigator.device;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.inject.Inject;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.pstreets.navigator.GNavigatorActivity;
import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SessionInfo;
import com.pstreets.navigator.database.PersistentFacade;
import com.pstreets.navigator.dataobject.Device;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class AddDeviceActivity extends RoboActivity{
	

	@InjectView (R.id.btnCancel) ImageButton btnCancel;
	@InjectView (R.id.btnOK) ImageButton btnOK;
	@InjectView (R.id.txtDeviceNo) TextView txtDeviceNo;
	@InjectView (R.id.txtDeviceName) TextView txtDeviceName;
	@Inject PersistentFacade persistentFacade;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		setContentView(R.layout.adddevice);
		
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(AddDeviceActivity.this, GNavigatorActivity.class); 
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        startActivity(intent); 
			}
		});
		
		btnOK.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(txtDeviceNo.getText().length()!=0){
					Device device=new Device();
					device.deviceId=txtDeviceNo.getText().toString();
					device.deviceName=txtDeviceName.getText().toString();
					device.lastKnownLocation=new GeoLatLng(0,0);
					updateDevice(device);
					
				}
				Intent intent = new Intent(AddDeviceActivity.this, ManageDeviceListActivity.class); 
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        startActivity(intent); 
			}
		});
	}
	
	
	private void updateDevice(Device device){
		boolean found=false;
		persistentFacade.open();
		for(int i=0;i<SessionInfo.devices.size();i++){
			Device deviceInArray=SessionInfo.devices.get(i);
			if(deviceInArray.deviceId.equalsIgnoreCase(device.deviceId)){
				found=true;
				deviceInArray.deviceName=device.deviceName;
				persistentFacade.updateDevice(deviceInArray);
				break;
			}
		}
		if(!found){
			SessionInfo.devices.add(device);
			persistentFacade.addDevice(device);
		}
		persistentFacade.close();
	}

}
