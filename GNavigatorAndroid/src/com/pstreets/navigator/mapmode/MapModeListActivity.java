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
package com.pstreets.navigator.mapmode;



import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectResource;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.service.DigitalMapService;
import com.pstreets.navigator.GNavigatorActivity;
import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SharedMapInstance;

public class MapModeListActivity extends RoboListActivity {
	
	@InjectResource(R.array.map_type_array) String [] mapTypes;
	
	private int []mapTypeArray=new int[]{MapType.GOOGLEMAP,MapType.GOOGLEHYBRID,MapType.GOOGLECHINA,
			MapType.MICROSOFTMAP,MapType.MICROSOFTHYBRID,MapType.MICROSOFTCHINA,
			MapType.MAPABCCHINA,MapType.OPENSTREETMAP,MapType.YAHOOMAP};
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
        
        getListView().setAdapter(new ArrayAdapter<String>(this,
                R.layout.simple_list_item, mapTypes));
        getListView().setOnItemClickListener(onChildClickListener);
    }

    
    OnItemClickListener onChildClickListener=new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View view, 
				int position,	long id) {
			SharedMapInstance.map.setMapType(mapTypeArray[position]);
			switch(position){
			case 2:
			case 5:
			case 6:
				DigitalMapService.getSearchOptions().LanguageID="zh-CN";
				break;
			default:
				DigitalMapService.getSearchOptions().LanguageID="en-US";
				break;
			}
			Intent intent = new Intent(MapModeListActivity.this, GNavigatorActivity.class); 
			intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
	        startActivity(intent); 
			
		}
    	
    };

    
}
