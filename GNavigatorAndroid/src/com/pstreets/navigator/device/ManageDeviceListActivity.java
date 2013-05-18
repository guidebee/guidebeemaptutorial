package com.pstreets.navigator.device;

import java.util.ArrayList;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.inject.Inject;
import com.pstreets.navigator.GNavigatorActivity;
import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SessionInfo;
import com.pstreets.navigator.database.PersistentFacade;
import com.pstreets.navigator.dataobject.Device;

public class ManageDeviceListActivity extends RoboListActivity {

	@InjectView(R.id.btnAdd)	ImageButton btnAdd;
	@InjectView(R.id.btnDelete)	ImageButton btnDelete;
	@InjectView(R.id.btnLocation)	ImageButton btnLocation;
	@InjectView(R.id.btnCommand)	ImageButton btnCommand;
	@Inject PersistentFacade persistentFacade;
	private int selectedPosition=-1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.managedevice);
		registerForContextMenu(getListView());

		btnAdd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ManageDeviceListActivity.this,
						AddDeviceActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
		});
		
		btnDelete.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setSelectedDevices();
				 persistentFacade.open();
				for(int i=0;i<SessionInfo.selectedDeviceList.size();i++){
					Device device=SessionInfo.selectedDeviceList.get(i);
					persistentFacade.deleteDevice(device.deviceId);
				}
				 persistentFacade.close();
				 resetDeviceList();
				
			}
		});
		
		btnLocation.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ManageDeviceListActivity.this,
						GNavigatorActivity.class); 
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        startActivity(intent); 
				
			}
		});
	}

	
	private void setSelectedDevices(){
		ListView listView = getListView();
		SparseBooleanArray checkedArray = listView
				.getCheckedItemPositions();
		SessionInfo.selectedDeviceList.clear();
		for (int i = 0; i < SessionInfo.devices.size(); i++) {
			Device device = SessionInfo.devices.get(i);
			if (checkedArray.get(i)) {
				SessionInfo.selectedDeviceList.add(device);
			} 
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		resetDeviceList();

	}
	
	@Override
    public boolean onContextItemSelected(MenuItem item) {
     
     int menuOption = item.getItemId();
     handleMenuClick(menuOption);
     return true;
     
    }
	private void handleMenuClick(int menuId) {
		switch (menuId) {
		case R.id.mnuDeviceInfo:
		case R.id.mnuDeviceCommand:
		case R.id.mnuLocation:
			SessionInfo.currentDevice = SessionInfo.devices.get(selectedPosition);
			if (SessionInfo.currentDevice != null) {
				Intent intent = new Intent(ManageDeviceListActivity.this,
						DeviceControlTabActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
			
			break;
		}
	}
	@Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
		selectedPosition=info.position;
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.deviceoptionmenu, menu);
    }

	private void resetDeviceList() {
		persistentFacade.open();
		ArrayList<Device> devicesInDB = persistentFacade.getAllDevices();
		ArrayList<Device> oldList=new ArrayList<Device>(SessionInfo.devices);
		SessionInfo.devices.clear();
		if (devicesInDB != null) {

			SessionInfo.devices.addAll(devicesInDB);
		}
		persistentFacade.close();

		//keep last known location.
		for(int i=0;i<oldList.size();i++){
			Device oldDevice=oldList.get(i);
			for(int j=0;j<SessionInfo.devices.size();j++){
				Device device=SessionInfo.devices.get(j);
				if(oldDevice.deviceId.equalsIgnoreCase(device.deviceId)){
					device.lastKnownLocation=oldDevice.lastKnownLocation;
					break;
				}
			}
		}
		
		Device[] devices = new Device[SessionInfo.devices.size()];
		SessionInfo.devices.toArray(devices);
		setListAdapter(new DeviceListAdapter(this, R.layout.device_list_item,
				devices));
		getListView().setItemsCanFocus(false);
		getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		// getListView().setOnItemClickListener(onChildClickListener);
	}

	OnItemClickListener onChildClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			SessionInfo.currentDevice = SessionInfo.devices.get(position);
			if (SessionInfo.currentDevice != null) {
				Intent intent = new Intent(ManageDeviceListActivity.this,
						DeviceControlTabActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}

		}
	};
	
	
	private class DeviceListAdapter extends ArrayAdapter<Device>{

		public DeviceListAdapter(Context context, int textViewResourceId,Device []objects) {
			super(context, textViewResourceId,objects);
			// TODO Auto-generated constructor stub
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			SparseBooleanArray checkedArray = getListView()
					.getCheckedItemPositions();
			Device device=getItem(position);
        	if (convertView == null) {
            	convertView = new DeviceListItemView(getContext(),
            			device);


            } else {
            	DeviceListItemView view=(DeviceListItemView)convertView;
            	view.setName(device.deviceName,checkedArray.get(position));
            	view.setStatus(device.deviceStatus);
            }
            return convertView;
        }
		
	}
	
	
	private class DeviceListItemView extends LinearLayout{
		public DeviceListItemView(Context context,Device device){
			super(context);
			LayoutInflater.from(context)
			.inflate(R.layout.device_list_item, this);
			imgStatus=(ImageView)findViewById(R.id.imgStatus);
			txtDeviceName=(CheckedTextView)findViewById(android.R.id.text1);
			txtDeviceName.setText(device.deviceName);
			txtDeviceName.setChecked(false);
			if(device.deviceStatus!=null && 
					device.deviceStatus.equalsIgnoreCase("offline")){
				imgStatus.setImageResource(R.drawable.offline);
			}else{
				imgStatus.setImageResource(R.drawable.online);
			}
			
		}
		
		public void setName(String title,boolean checked) {
			txtDeviceName.setText(title);
			txtDeviceName.setChecked(checked);
        }

        
        public void setStatus(String words) {
        	if(words!=null &&
        			words.equalsIgnoreCase("offline")){
				imgStatus.setImageResource(R.drawable.offline);
			}else{
				imgStatus.setImageResource(R.drawable.online);
			}
        }
		private CheckedTextView txtDeviceName;
        private ImageView imgStatus;
	}

}
