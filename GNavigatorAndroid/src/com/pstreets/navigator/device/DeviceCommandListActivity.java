package com.pstreets.navigator.device;

import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SessionInfo;
import com.pstreets.navigator.dataobject.DeviceCommand;
import com.pstreets.navigator.settings.SettingsActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;

import java.util.ArrayList;

public class DeviceCommandListActivity extends RoboListActivity {

	
	int commandType;
	
	@InjectView(R.id.spinner1)	Spinner spinner1;
	@InjectView(R.id.btnSend)	Button btnSend;
	@InjectView(R.id.txtParameters)	EditText txtParameters;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.devicecommandlist);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.command, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				commandType=position;
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		btnSend.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(SessionInfo.currentDevice!=null){
					String commandString="";
					switch(commandType){
					case 0:
						if(SessionInfo.localDeviceNo==null || SessionInfo.localDeviceNo.length()==0){
							Intent intent = new Intent(DeviceCommandListActivity.this,
									SettingsActivity.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
							startActivity(intent);
							return;
						}else{
							commandString=DeviceCommand.CMD_BIND+"+"+SessionInfo.localDeviceNo;
						}
						break;
					case 1:
						commandString=DeviceCommand.CMD_GPS_ON+"+";
						break;
					case 2:
						commandString=DeviceCommand.CMD_GPS_OFF+"+";
						break;
					case 3:
						commandString=DeviceCommand.CMD_GET_POWER_LEVEL+"+";
						break;
					case 4:
						commandString=DeviceCommand.CMD_GET_IMEI+"+";
						break;
					case 5:
						commandString=DeviceCommand.CMD_GEOFENCE1+"+"+txtParameters.getText().toString();
						break;
					case 6:
						commandString=DeviceCommand.CMD_GEOFENCE2+"+"+txtParameters.getText().toString();
						break;
					case 7:
						commandString=DeviceCommand.CMD_GEOFENCE_ON+"+";
						break;
					case 8:
						commandString=DeviceCommand.CMD_GEOFENCE_OFF+"+";
						break;
					case 9:
						commandString=DeviceCommand.CMD_GPRS_IP+"+"+txtParameters.getText().toString();
						break;
					case 10:
						commandString=DeviceCommand.CMD_GPRS_ON+"+";
						break;
					case 11:
						commandString=DeviceCommand.CMD_REPORT_PERIOD+"+"+txtParameters.getText().toString();
						break;
					case 12:
						commandString=DeviceCommand.CMD_REPORT_ONE+"+";
						break;
						
					}
					
					sendSMS(SessionInfo.currentDevice.deviceId,commandString);
					SessionInfo.currentDevice.commandList.add(commandString);
					resetLocationList();
				}
				
			}
		});
		mHandler.postDelayed(updateList, 5000);

	}

	public void onResume(){
    	super.onResume();
    	resetLocationList();
    }
    
	
	private Handler mHandler=new Handler();
	
	private volatile int commandCount;
	
	private Runnable updateList=new Runnable(){

		public void run() {
			if(SessionInfo.currentDevice!=null){
				ArrayList<String> commandList=SessionInfo.currentDevice.commandList;
				if(commandList.size()!=commandCount){
					resetLocationList();
				}
				
				mHandler.postDelayed(updateList, 3000);
			}
			
		}};
	
    
    private void resetLocationList(){
    	if(SessionInfo.currentDevice!=null){
    		ArrayList<String> commandList=SessionInfo.currentDevice.commandList;
    		if(commandList!=null){
    			commandCount=commandList.size();
    			String [] strings=new String[commandCount];
    			for(int i=0;i<commandCount;i++){
    				String commandString=commandList.get(i);
    				strings[i]=commandString;
    			}
    			
    			setListAdapter(new ArrayAdapter<String>(this,
    	                android.R.layout.simple_list_item_1, strings));
    	        getListView().setTextFilterEnabled(true);
    	       
    		}
    	
    	}
    }
    
	void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	
	 //---sends an SMS message to another device---
    private void sendSMS(String phoneNumber, String message)
    {        
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
            new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
            new Intent(DELIVERED), 0);
 
        //---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", 
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));
 
        //---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered", 
                                Toast.LENGTH_SHORT).show();
                        break;                        
                }
            }
        }, new IntentFilter(DELIVERED));        
 
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);        
    }
}
