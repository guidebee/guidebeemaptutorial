//------------------------------------------------------------------------------
//                         COPYRIGHT 2011 SWIFTNETWORKS
//                           ALL RIGHTS RESERVED.
//                     EITS CONFIDENTIAL PROPRIETARY
///////////////////////////////////// REVISIONS ////////////////////////////////
// Date       Name                 Tracking #         Description
// ---------  -------------------  ----------         --------------------------
// 29SEP2011  James Shen                 	          Initial Creation
////////////////////////////////////////////////////////////////////////////////
//--------------------------------- PACKAGE ------------------------------------
package com.pstreets.navigator.location;

//--------------------------------- IMPORTS ------------------------------------
import java.util.logging.Logger;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.location.ChinaMapOffset;
import com.pstreets.navigator.app.SessionInfo;
import com.pstreets.navigator.app.SharedMapInstance;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//29SEP2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
*  location service.
* <hr>
* <b>&copy; Copyright 2011 Swiftnetworks, Inc. All Rights Reserved.</b>
* 
* @version 1.00, 29/09/11
* @author Swiftnetworks Pty Ltd.
*/
@Singleton
public class LocationService {

	private ChinaMapOffset mapOffset=new ChinaMapOffset();
	
	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 28SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Constructor.
	 * @param context
	 */
	@Inject
	public LocationService(Context context) {
		mContext = context;
		mLocationManager = (LocationManager) mContext
				.getSystemService(Context.LOCATION_SERVICE);
	}

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 28SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* start the service.
	*/
	public void start() {
		mLocationManager.requestLocationUpdates(provider, mReportPeriod, distance,
				locationListener);
		mLocationManager.requestLocationUpdates(providerNetwork, mReportPeriod, distance,
				locationListenerNetwork);
	}

	////////////////////////////////////////////////////////////////////////////
	//--------------------------------- REVISIONS ------------------------------
	// Date       Name                 Tracking #         Description
	// ---------  -------------------  -------------      ----------------------
	// 28SEP2011  James Shen                 	          Initial Creation
	////////////////////////////////////////////////////////////////////////////
	/**
	* stop service.
	*/
	public void stop() {
		mLocationManager.removeUpdates(locationListener);
		mLocationManager.removeUpdates(locationListenerNetwork);
	}
	
	
	private String provider = LocationManager.GPS_PROVIDER;
	private String providerNetwork = LocationManager.NETWORK_PROVIDER;

	//5 s
	private int mReportPeriod =1* 1000;

	//5 m
	private int distance = 5;
	
	private Context mContext;

	private LocationManager mLocationManager;
	

	private LocationListener locationListener = new LocationListener() {

		public void onLocationChanged(Location location) {
			
			GeoLatLng earth=new GeoLatLng(location.getLatitude(),location.getLongitude());
			if(SessionInfo.ajuestOffset){
				GeoLatLng mars=mapOffset.fromEarthToMars(earth);
				SharedMapInstance.currentLocation.setLocation(mars.lng(),mars.lat());
			}else{

				SharedMapInstance.currentLocation
				  .setLocation(location.getLongitude(),location.getLatitude());
			}
			
		
			
			if(SharedMapInstance.centerMyLocation){
				
				SharedMapInstance.map.panTo(SharedMapInstance.currentLocation);
				
			}
			

		}

		public void onProviderDisabled(String provider) {
			mLoger.info("onProviderDisabled");

		}

		public void onProviderEnabled(String provider) {
			mLoger.info("onProviderEnabled");

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			mLoger.info("onStatusChanged");

		}
	};

	private LocationListener locationListenerNetwork = new LocationListener() {

		public void onLocationChanged(Location location) {
			mLoger.info(location.toString());
	
			GeoLatLng earth=new GeoLatLng(location.getLatitude(),location.getLongitude());
			if(SessionInfo.ajuestOffset){
				GeoLatLng mars=mapOffset.fromEarthToMars(earth);
				SharedMapInstance.currentLocation.setLocation(mars.lng(),mars.lat());
			}else{

				SharedMapInstance.currentLocation
				  .setLocation(location.getLongitude(),location.getLatitude());
			}
			if(SharedMapInstance.centerMyLocation){
				
				
				SharedMapInstance.map.panTo(SharedMapInstance.currentLocation);
				
			}
		

	

		}

		public void onProviderDisabled(String provider) {
			mLoger.info("onProviderDisabled");

		}

		public void onProviderEnabled(String provider) {
			mLoger.info("onProviderEnabled");

		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			mLoger.info("onStatusChanged");

		}
	};

	

	private static Logger mLoger = Logger.getLogger(LocationService.class
			.getName());

}
