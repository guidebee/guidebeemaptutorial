//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 GUIDEBEE
//                           ALL RIGHTS RESERVED.
//                     GUIDEBEE CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 04SEP2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.navigator.search;

//--------------------------------- IMPORTS ------------------------------------
import roboguice.activity.RoboListActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.MapPoint;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.service.IGeocodingListener;
import com.mapdigit.gis.service.IIpAddressGeocodingListener;
import com.mapdigit.gis.service.IReverseGeocodingListener;
import com.mapdigit.gis.service.IpAddressLocation;
import com.mapdigit.util.Log;
import com.mapdigit.util.Utils;
import com.pstreets.navigator.GNavigatorActivity;
import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SharedMapInstance;
import com.pstreets.navigator.app.SharedSearchResults;


//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//04SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
* Search activity and display search result.
* <hr><b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
* @version     1.00, 04/09/11
* @author      Guidebee Pty Ltd.
*/
public class SearchResultListActivity extends RoboListActivity implements
		IGeocodingListener, IIpAddressGeocodingListener,
		IReverseGeocodingListener {


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			search(query);
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
					SearchSuggestionProvider.AUTHORITY, SearchSuggestionProvider.MODE);
			suggestions.saveRecentQuery(query, null);
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

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		if (SharedSearchResults.searchResults != null) {
			MapPoint mapPoint = SharedSearchResults.searchResults[position];
			SharedMapInstance.map.setCenter(mapPoint.point, 15);
			SharedSearchResults.currentSeachResultIndex=position;
			Intent intent = new Intent(SearchResultListActivity.this,
					GNavigatorActivity.class); 
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	        startActivity(intent); 

		}

	}

	private void search(String query) {

		SharedSearchResults.searchResults=null;
		switch (SharedSearchResults.currentSearchType) {
		case SharedSearchResults.AddressSearch: {
			showDialog(DIALOG1_KEY);
			SharedMapInstance.map.setGeocodingListener(this);
			SharedMapInstance.map.getLocations(query);
		}
			break;
		case SharedSearchResults.LocalSearch: {
			showDialog(DIALOG1_KEY);
			localSearchAddress = query;
			localSearchResults = new MapPoint[LOCAL_SEARCH_NUMBER];
			localSearchCount = 0;
			SharedMapInstance.map.setGeocodingListener(this);
			GeoLatLng screenCenter = SharedMapInstance.map.getScreenCenter();
			SharedMapInstance.map.getLocations(query, 0, screenCenter,
					SharedMapInstance.map.getScreenBounds(screenCenter));
		}
			break;
		case SharedSearchResults.IPSearch: {
			showDialog(DIALOG1_KEY);
			SharedMapInstance.map.setIpAddressGeocodingListener(this);
			SharedMapInstance.map.getIpLocations(query);
		}
			break;
		case SharedSearchResults.LatitudeSearch: {
			showDialog(DIALOG1_KEY);
			SharedMapInstance.map.setReverseGeocodingListener(this);
			SharedMapInstance.map.getReverseLocations(query);
		}
			break;
		}
	}


	public void done(String query, MapPoint[] result) {
		try {
			if (SharedSearchResults.currentSearchType == SharedSearchResults.LocalSearch) {
				if (result != null) {
					SharedSearchResults.isRouteMode=false;
					for (int i = 0; i < result.length; i++) {
						if (localSearchCount < LOCAL_SEARCH_NUMBER) {
							localSearchResults[localSearchCount++] = result[i];
						} else {
							break;
						}
					}
					if (localSearchCount < LOCAL_SEARCH_NUMBER
							&& result.length == LOCAL_SEARCH_EACH_ROUND) {

						GeoLatLng screenCenter = SharedMapInstance.map
								.getScreenCenter();
						SharedMapInstance.map.getLocations(localSearchAddress,
								localSearchCount, screenCenter,
								SharedMapInstance.map
										.getScreenBounds(screenCenter));
						if (localSearchCount > 0) {

							SharedSearchResults.searchResults = localSearchResults;
							for (int i = 0; i < localSearchCount; i++) {
								String name = SharedSearchResults.searchResults[i]
										.getName();
								if (name.indexOf("&#39;") >= 0) {
									name = Utils.replace("&#39;", "'", name);
									SharedSearchResults.searchResults[i]
											.setName(name);
								}

							}

						}
					}
				}

			} else {
				if(result!=null && !(result[0].point.x==0 || result[0].point.y==0))
				SharedSearchResults.searchResults = result;
			}
		} catch (Exception e) {
			Log.p(e.getMessage());
		}
		displayResult();

	}

	private void displayResult(){
		runOnUiThread(new Runnable() {

			public void run() {
				if(SharedSearchResults.searchResults==null){
					Toast.makeText(SearchResultListActivity.this, R.string.searchnoresult, 
							Toast.LENGTH_SHORT).show(); 
					Intent intent = new Intent(SearchResultListActivity.this, GNavigatorActivity.class); 
					intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			        startActivity(intent); 
				}else{
					progressBar.dismiss();
					setListAdapter(new SearchResultAdapter(
							SearchResultListActivity.this));
					if(SharedSearchResults.searchResults.length==1){
						MapPoint mapPoint = SharedSearchResults.searchResults[0];
						SharedMapInstance.map.setCenter(mapPoint.point, 15);
						SharedSearchResults.currentSeachResultIndex=0;
						Intent intent = new Intent(SearchResultListActivity.this,
								GNavigatorActivity.class); 
						intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				        startActivity(intent); 
					}
				}

			}
		});
	}
	
	public void readProgress(int bytes, int total) {
		// TODO Auto-generated method stub

	}

	public void done(String query, IpAddressLocation result) {
		try {
			SharedSearchResults.searchResults = null;
			if (result != null) {
				
				SharedSearchResults.searchResults = new MapPoint[1];
				SharedSearchResults.searchResults[0] = new MapPoint();
				String latLng = "[" + result.longitude + "," + result.latitude
						+ ",0]";
				SharedSearchResults.searchResults[0].point = MapLayer
						.fromStringToLatLng(latLng);
				SharedSearchResults.searchResults[0]
						.setName(result.organization);
				SharedSearchResults.searchResults[0].setNote(result.city + " "
						+ result.country);
			}
		} catch (Exception e) {
			Log.p(e.getMessage());
		}

		displayResult();

	}
	
	private ProgressDialog progressBar;
	private MapPoint[] localSearchResults;
	private @Inject	Resources res;

	private static final int DIALOG1_KEY = 0;
	private static int LOCAL_SEARCH_NUMBER = 8;
	private final static int LOCAL_SEARCH_EACH_ROUND = 4;
	private int localSearchCount = 0;
	private String localSearchAddress;

	private class SearchResultAdapter extends BaseAdapter {
		public SearchResultAdapter(Context context) {
			mContext = context;
		}

		public int getCount() {
			if (SharedSearchResults.searchResults != null) {
				return SharedSearchResults.searchResults.length;
			}
			return 0;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			TextView tv;
			if (convertView == null) {
				tv = (TextView) LayoutInflater.from(mContext).inflate(
						R.layout.simple_list_item, parent,
						false);
			} else {
				tv = (TextView) convertView;
			}
			if (SharedSearchResults.searchResults != null) {
				try {
					tv.setText((position+1) +":"+ SharedSearchResults.searchResults[position]
							.getName());
				} catch (Exception e) {
					Log.p("pos:" + position);
				}
			}

			return tv;
		}

		private Context mContext;
	}

}
