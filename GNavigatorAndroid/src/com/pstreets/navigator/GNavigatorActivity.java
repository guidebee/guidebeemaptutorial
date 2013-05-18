//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 20AUG2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.navigator;

//--------------------------------- IMPORTS ------------------------------------
import com.pstreets.navigator.app.SessionInfo;
import com.pstreets.navigator.app.SharedMapInstance;
import com.pstreets.navigator.app.SharedSearchResults;
import com.pstreets.navigator.database.PersistentFacade;
import com.pstreets.navigator.dataobject.Device;
import com.pstreets.navigator.device.ManageDeviceListActivity;
import com.pstreets.navigator.location.LocationService;
import com.pstreets.navigator.mapmode.MapModeListActivity;
import com.pstreets.navigator.routing.RoutingActivity;
import com.pstreets.navigator.settings.SettingsActivity;
import com.pstreets.navigator.sms.DeviceMessageProcessor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.MapConfiguration;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.raster.RasterMap;
import com.mapdigit.gis.service.IReverseGeocodingListener;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import java.util.ArrayList;
import java.util.List;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//20AUG2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* This is the main activity .
* <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* @version     1.00, 20/08/11
* @author      Guidebee Pty Ltd.
*/
public class GNavigatorActivity extends RoboActivity  
    implements IReverseGeocodingListener{
	
	@InjectView (R.id.guidebeemap_view) GuidebeeMapView guidebeeMapView;
	@Inject Resources res;
	@Inject LocationService locationService;
	@Inject PersistentFacade persistentFacade;
	
   /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
        setContentView(R.layout.main);
        deviceMessageProcessor=new DeviceMessageProcessor(persistentFacade);
        imageRedNumbers=BitmapFactory.decodeResource(res, R.drawable.numberred);
        imageGreenNumbers=BitmapFactory.decodeResource(res, R.drawable.numbergreen);
        imageBlueNumbers=BitmapFactory.decodeResource(res, R.drawable.numberblue);
        imageBlueLocation=BitmapFactory.decodeResource(res, R.drawable.blue_location);
        imageGreyLocation=BitmapFactory.decodeResource(res, R.drawable.grey_location);
        imageDeviceStatus=BitmapFactory.decodeResource(res, R.drawable.device_status);
        initGuidebeeMap();
        registerForContextMenu(guidebeeMapView);
        (new Thread(deviceMessageProcessor)).start();
        persistentFacade.open();
        ArrayList<Device> devices=persistentFacade.getAllDevices();
        if(devices!=null){
        	SessionInfo.devices.clear();
        	SessionInfo.devices.addAll(devices);
        }
        persistentFacade.close();
   }
    

    public void onResume(){
    	super.onResume();
    	if(SharedSearchResults.mapDirection!=null || 
    			(SharedSearchResults.searchResults!=null 
    			&& SharedSearchResults.searchResults.length>1)){
    		for(int i=0;i<3;i++){
    			guidebeeMapView.setIconVisible(i,true);
    		}
    	}else{
    		for(int i=0;i<3;i++){
    			guidebeeMapView.setIconVisible(i,false);
    		}
    	}
    	locationService.start();
    }
    
    @Override
    protected void onPause(){
    	super.onPause();
    	locationService.stop();
    }
    
    @Override
    protected void onDestroy(){
    	super.onDestroy();
    	locationService.stop();
    	deviceMessageProcessor.stop();
    	writePreferences();
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);
		return true;
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	
    	int menuOption = item.getItemId(); 
    	handleMenuClick(menuOption);
    	return true;
    	
    }
    
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		int menuOption = item.getItemId();
		handleMenuClick(menuOption);
		return true;

	}
    
    
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.conextmenu, menu);
	}
    
	@Override
	public void onBackPressed() {
		exitApplication();
	}
    
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_SEARCH:
			return new AlertDialog.Builder(GNavigatorActivity.this)
					.setTitle(R.string.search)
					.setItems(R.array.search_option,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) { /*
													 * User clicked so do some
													 * stuff
													 */
									handleSearchOption(which);
								}
							}).create();
			
		}
		return null;
	}
	
	private void initGuidebeeMap() {
		new InitMapTask().execute(null, null, null);
		
	}
	
	private static void closeAllBelowActivities(Activity current) { 
        boolean flag = true; 
        Activity below = current.getParent(); 
        if (below == null) 
            return; 
        System.out.println("Below Parent: " + below.getClass()); 
        while (flag) { 
            Activity temp = below; 
            try { 
                below = temp.getParent(); 
                temp.finish(); 
            } catch (Exception e) { 
                flag = false; 
            } 
        } 
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
	private void exitApplication(){
		
		try {

			PackageManager localPackageManager = getPackageManager();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);

			List<ResolveInfo> resolveInfos = localPackageManager
					.queryIntentActivities(intent,
							PackageManager.MATCH_DEFAULT_ONLY);
			for (int i = 0; i < resolveInfos.size(); i++) {
				ResolveInfo resolveInfo = resolveInfos.get(i);
				ActivityInfo activityInfo = resolveInfo.activityInfo;
				if (!activityInfo.name.endsWith("DummyActivity")) {
					ComponentName componentName = new ComponentName(
							activityInfo.packageName, activityInfo.name);

					Intent intent1 = new Intent();
					intent1.setComponent(componentName);

					startActivity(intent1);
					System.exit(0);
					break;
				}

			}

		} catch (Exception e) {
			
		}
		closeAllBelowActivities(GNavigatorActivity.this);

	
	}
	
	Handler handler=new Handler();
	Bitmap imageRedNumbers;
	Bitmap imageGreenNumbers;
	Bitmap imageBlueNumbers;
	Bitmap imageBlueLocation;
	Bitmap imageGreyLocation;
	Bitmap imageDeviceStatus;
	
	static final int DIALOG_SEARCH=1;
	private int reverseSearchMode=0;
	
	
	private DeviceMessageProcessor deviceMessageProcessor;
	
	private void initMapSize() {
		if (SharedMapInstance.mapImage == null) {
			Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();
			int width = display.getWidth();
			int height = display.getHeight();

			int maxWidth = Math.max(width, height);
			int mapSize = (int) (maxWidth / 256) * 256;
			if (mapSize < maxWidth)
				mapSize += 256; 
			SharedMapInstance.mapImage = MapLayer.getAbstractGraphicsFactory()
					.createImage(mapSize, mapSize);
			SharedMapInstance.mapGraphics = SharedMapInstance.mapImage
					.getGraphics();
			SharedMapInstance.map = new RasterMap(mapSize * 2, mapSize * 2,
					SharedMapInstance.mapTileDownloadManager);
			SharedMapInstance.map.setViewSize(mapSize, mapSize);
			SharedMapInstance.map.setMaxZoomLevel(21);
			SharedMapInstance.map.setMapType(MapType.GOOGLEMAP);
			MapConfiguration.setParameter(MapConfiguration
					.IGNORE_MAP_TYPE_FOR_STORED_MAP, false);
			readPreferences();
			SharedMapInstance.mapTileDownloadManager.start();
			guidebeeMapView.initGuidebeeMap();
		}
    }
	
	private void writePreferences() {
		SharedPreferences sharedPref = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPref.edit();
		editor.putString("deivceno_preference", SessionInfo.localDeviceNo);
		editor.putString("mastdeviceno_preference", SessionInfo.masterDeviceNo);
		editor.putString("mapzoom_preference",
				String.valueOf(SharedMapInstance.map.getZoom()));
		String centerLocation = SharedMapInstance.map.getCenter().lng() + ","
				+ SharedMapInstance.map.getCenter().lat();
		editor.putString("mapcenter_preference", centerLocation);
		int mapType = 0;
		switch (SharedMapInstance.map.getMapType()) {
		case MapType.GOOGLEMAP:
			mapType = 0;
			break;
		case MapType.GOOGLEHYBRID:
			mapType = 1;
			break;
		case MapType.GOOGLECHINA:
			mapType = 2;
			break;
		case MapType.MICROSOFTMAP:
			mapType = 3;
			break;
		case MapType.MICROSOFTHYBRID:
			mapType = 4;
			break;
		case MapType.MICROSOFTCHINA:
			mapType = 5;
			break;
		case MapType.MAPABCCHINA:
			mapType = 6;
			break;
		case MapType.OPENSTREETMAP:
			mapType = 7;
			break;

		}
		editor.putString("mapmode_preference", String.valueOf(mapType));
		editor.commit();

	}
	
	private void readPreferences(){
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String values = sharedPref.getString("deivceno_preference", SessionInfo.localDeviceNo);
        SessionInfo.localDeviceNo=values;
        values = sharedPref.getString("mastdeviceno_preference", SessionInfo.masterDeviceNo);
        SessionInfo.masterDeviceNo=values;
        values = sharedPref.getString("mapzoom_preference", "1");
        int mapZoom=1;
        mapZoom=Integer.parseInt(values);
        
        values = sharedPref.getString("mapcenter_preference", "0,0");
        String location = "[" + values + ",0]";
    	GeoLatLng latLng=MapLayer.fromStringToLatLng(location);
    	SharedMapInstance.map.setCenter(latLng, mapZoom);
        
        int mapType=MapType.GOOGLEMAP;
        values = sharedPref.getString("mapmode_preference","0");
        mapType=Integer.parseInt(values);
        switch(mapType){
        	case 0:	mapType=MapType.GOOGLEMAP;break;
        	case 1:	mapType=MapType.GOOGLEHYBRID;break;
        	case 2:	mapType=MapType.GOOGLECHINA;break;
        	case 3:	mapType=MapType.MICROSOFTMAP;break;
        	case 4:	mapType=MapType.MICROSOFTHYBRID;break;
        	case 5:	mapType=MapType.MICROSOFTCHINA;break;
        	case 6:	mapType=MapType.MAPABCCHINA;break;
        	case 7:	mapType=MapType.OPENSTREETMAP;break;
        	
        }
        
        SharedMapInstance.map.setMapType(mapType);
	}
	
	
	private void handleMenuClick(int menuId) {
		switch (menuId) {
		case R.id.mnu_search:
			showDialog(GNavigatorActivity.DIALOG_SEARCH);
			break;
		case R.id.mnu_mapmode: {
			Intent intent = new Intent(GNavigatorActivity.this,
					MapModeListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		}
			break;
		case R.id.mnu_direction: {
			Intent intent = new Intent(GNavigatorActivity.this,
					RoutingActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			break;
		}
		case R.id.mnu_mylocation: {
			SharedMapInstance.centerMyLocation = !SharedMapInstance.centerMyLocation;
			if (SharedMapInstance.centerMyLocation) {
				if (SharedMapInstance.currentLocation.x != 0) {
					SharedMapInstance.map
							.panTo(SharedMapInstance.currentLocation);
				}
			}
		}
			break;
			
		case R.id.mnuclearmap:
			SharedMapInstance.map.setMapDirection(null);
			SharedSearchResults.mapDirection=null;
			SharedSearchResults.searchResults=null;
			SharedSearchResults.directionStart=null;
			SharedSearchResults.directionEnd=null;
			for(int i=0;i<3;i++){
    			guidebeeMapView.setIconVisible(i,false);
    		}
			System.gc();
			guidebeeMapView.invalidate();
			break;
		case R.id.mnureportlocation:
		{
			if(SessionInfo.masterDeviceNo!=null && SessionInfo.masterDeviceNo.length()!=0){
				GeoLatLng reportLocation=SharedMapInstance.currentLocation;
				if(reportLocation==null || Math.abs(reportLocation.x*
						reportLocation.y)<0.0001){
					reportLocation=SharedMapInstance.map.getCenter();
				}
				String cmd="@cmd:903:"+reportLocation.lat()+","+
						reportLocation.lng();
				sendSMS(SessionInfo.masterDeviceNo,cmd);
				
			}
		}
			break;
		case R.id.menuwhatshere:
		{
			reverseSearchMode=0;
			GeoLatLng latLng=SharedMapInstance.map
					.fromScreenPixelToLatLng(SharedSearchResults.currentPressedPoint);
			String latLngAddress=latLng.y+","+latLng.x;
			SharedMapInstance.map.setReverseGeocodingListener(this);
			SharedMapInstance.map.getReverseLocations(latLngAddress);
			
		}
			break;
		case R.id.mnudirectionfromhere:
		{
			reverseSearchMode=1;
			GeoLatLng latLng=SharedMapInstance.map
					.fromScreenPixelToLatLng(SharedSearchResults.currentPressedPoint);
			String latLngAddress=latLng.y+","+latLng.x;
			SharedMapInstance.map.setReverseGeocodingListener(this);
			SharedMapInstance.map.getReverseLocations(latLngAddress);
			
		}
			break;
		case R.id.mnudirectiontohere:
		{
			reverseSearchMode=2;
			GeoLatLng latLng=SharedMapInstance
					.map.fromScreenPixelToLatLng(SharedSearchResults.currentPressedPoint);
			String latLngAddress=latLng.y+","+latLng.x;
			SharedMapInstance.map.setReverseGeocodingListener(this);
			SharedMapInstance.map.getReverseLocations(latLngAddress);
			
		}
			break;
			
		case R.id.mnu_device:
		{
			Intent intent = new Intent(GNavigatorActivity.this,
					ManageDeviceListActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		}
			break;
		case R.id.mnu_about:
		{
			Intent intent = new Intent(GNavigatorActivity.this,
					AboutActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		}
			break;
		case R.id.mnu_settings:
		{
			Intent intent = new Intent(GNavigatorActivity.this,
					SettingsActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
		}
		break;

		}
	}
	
	private void handleSearchOption(int select){
		SharedSearchResults.currentSearchType=select;
		onSearchRequested();
		
	}
    
    private class InitMapTask extends AsyncTask<Void,Void,Void>{

		@Override
		protected Void doInBackground(Void... params) {
			initMapSize();
			return null;
		}
		
	}

	public void done(String arg0, final MapPoint[] result) {
		if(result!=null){
			switch(reverseSearchMode){
			case 0:
				handler.post(new Runnable(){

					public void run() {
						Toast.makeText(GNavigatorActivity.this, result[0].name, 
								Toast.LENGTH_LONG).show(); 
						
					}});
				break;
			case 1:
				SharedSearchResults.directionStart=result[0];
				break;
			case 2:
				SharedSearchResults.directionEnd=result[0];
				Intent intent = new Intent(GNavigatorActivity.this,
						RoutingActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				break;
			}
			
		}else{
			handler.post(new Runnable(){

				public void run() {
					Toast.makeText(GNavigatorActivity.this, R.string.searchnoresult, 
							Toast.LENGTH_SHORT).show(); 
					
				}});
		}
		
	}

	public void readProgress(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
    
}