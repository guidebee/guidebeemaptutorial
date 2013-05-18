package com.pstreets.navigator.routing;


import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.inject.Inject;
import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.service.DigitalMapService;
import com.mapdigit.gis.service.IRoutingListener;
import com.mapdigit.gis.service.SearchOptions;
import com.pstreets.navigator.GNavigatorActivity;
import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SharedMapInstance;
import com.pstreets.navigator.app.SharedSearchResults;

public class RoutingActivity extends RoboActivity implements IRoutingListener
{
	
	@InjectView (R.id.txtStartAddress) EditText txtStartAddress;
	@InjectView (R.id.txtEndAddress) EditText txtEndAddress;
	@InjectView (R.id.radioDrive) RadioButton radioDrive;
	@InjectView (R.id.radioWalk) RadioButton radioWalk;
	@InjectView (R.id.btnGetDirection) ImageButton btnGetDirection;
	@InjectView (R.id.btnViewRoute) ImageButton btnViewRoute;
	@InjectView (R.id.btnClear) Button btnClear;
	SearchOptions searchOptions;
	private int mScale=1;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
        setContentView(R.layout.routing);
        searchOptions=DigitalMapService.getSearchOptions();
        mScale=SharedMapInstance.map.getMapHeight()/SharedMapInstance.map.getScreenHeight();
        btnGetDirection.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				searchDirection();
			}
		});
        
        btnClear.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				txtStartAddress.setText("");
				txtEndAddress.setText("");
			}
		});
        btnViewRoute.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				if(SharedSearchResults.mapDirection!=null){
					Intent intent = new Intent(RoutingActivity.this, RouteDetailActivity.class); 
					intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			        startActivity(intent); 
				}
			}
		});
    }

    @Override
    public void onResume(){
    	super.onResume();
    	if(SharedSearchResults.directionStart!=null){
    		txtStartAddress.setText(SharedSearchResults.directionStart.name);
    	}else{
    		txtStartAddress.setText("");
    	}
    	if(SharedSearchResults.directionEnd!=null){
    		txtEndAddress.setText(SharedSearchResults.directionEnd.name);
    	}else{
    		txtEndAddress.setText("");
    	}

    }
    
    private void searchDirection(){
    	
		String name1 = txtStartAddress.getText().toString();
		String name2 = txtEndAddress.getText().toString();
		if(name1.length()==0){
			name1="@"+SharedMapInstance.currentLocation.lat()+","+
					SharedMapInstance.currentLocation.lng();
		}
		if(SharedSearchResults.directionStart!=null &&
				SharedSearchResults.directionStart.name.equalsIgnoreCase(name1)){
			name1="@"+SharedSearchResults.directionStart.point.lat()+","+
					SharedSearchResults.directionStart.point.lng();
		}
		
		if(SharedSearchResults.directionEnd!=null &&
				SharedSearchResults.directionEnd.name.equalsIgnoreCase(name2)){
			name2="@"+SharedSearchResults.directionEnd.point.lat()+","+
					SharedSearchResults.directionEnd.point.lng();
		}
		if (name2.length() != 0) {
			showDialog(DIALOG1_KEY);
			if (radioDrive.isChecked()) {

				searchOptions.RoutingType = SearchOptions.ROUTE_TYPE_DRIVING;
			} else {
				searchOptions.RoutingType = SearchOptions.ROUTE_TYPE_WALKING;
			}
			DigitalMapService.setSearchOptions(searchOptions);
			SharedMapInstance.map
					.setRoutingListener(RoutingActivity.this);
			SharedMapInstance.map.getDirections("from: " + name1
					+ " to: " + name2);
		}
    }
    
    @Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG1_KEY: {
			progressBar = new ProgressDialog(this);
			progressBar.setMessage(res.getString(R.string.searchinprogress));
			progressBar.setIndeterminate(true);
			progressBar.setCancelable(true);
			return progressBar;
		}

		}
		return null;
	}
	public void done(String query, MapDirection result) {
		SharedSearchResults.mapDirection=result;
		if(result!=null){
			SharedMapInstance.map.setMapDirection(result);
			SharedMapInstance.map.resize(result.getBound());
			for(int i=0;i<mScale;i++){
				SharedMapInstance.map.zoomOut();
			}
		}
		displayResult();
	}

	public void readProgress(int arg0, int arg1) {
		
		
	}
	
	private void displayResult(){
		runOnUiThread(new Runnable() {

			public void run() {
				if(SharedSearchResults.mapDirection==null){
					Toast.makeText(RoutingActivity.this, R.string.searchnoresult, 
							Toast.LENGTH_SHORT).show(); 
					Intent intent = new Intent(RoutingActivity.this, GNavigatorActivity.class); 
					intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			        startActivity(intent); 
				}else{
					SharedSearchResults.isRouteMode=true;
					Intent intent = new Intent(RoutingActivity.this, RouteDetailActivity.class); 
					intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			        startActivity(intent); 
				}
				progressBar.dismiss();
				

			}
		});
	}

	private ProgressDialog progressBar;
	private @Inject	Resources res;
	private static final int DIALOG1_KEY = 0;
}
