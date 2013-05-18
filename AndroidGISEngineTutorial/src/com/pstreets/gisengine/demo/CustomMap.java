package com.pstreets.gisengine.demo;
import com.mapdigit.gis.raster.ICustomMapType;
import com.mapdigit.gis.raster.MapType;

import com.mapdigit.gis.geometry.GeoLatLng;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class CustomMap extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onStart() {
		super.onStart();
		MapType.setCustomMapTileUrl(new TiandiMapType());
		GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
		SharedMapInstance.map.setCenter(center, 13, MapType.GENERIC_MAPTYPE_5);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mapzoom_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.zoomin:
			SharedMapInstance.map.zoomIn();

			return true;
		case R.id.zoomout:
			SharedMapInstance.map.zoomOut();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}

	}

}

class TiandiMapType implements ICustomMapType {

    private static int serverIndex=1;

    public String getTileURL(int mtype, int x, int y, int zoomLevel) {
        String returnURL="";
        serverIndex+=1;
        serverIndex%=3;
        int maxTiles=(int)Math.pow(2, zoomLevel);
        switch(mtype){
            case MapType.GENERIC_MAPTYPE_5:
            	 returnURL= "http://p" 
            		 + serverIndex+".map.qq.com/maptiles/" ;
                 y=maxTiles-y-1;
                 returnURL+=+zoomLevel+"/"+(int)(x/16)+"/"+(int)(y/16)+"/"+x+"_"+y+".gif";
                break;
            case MapType.GENERIC_MAPTYPE_6:

                if(zoomLevel<11){
                   returnURL= "http://tile" 
                	   + serverIndex+".tianditu.com/DataServer?T=A0512_EMap";
                   returnURL+="&X="+x+"&Y="+y+"&L="+zoomLevel;

                }else if(zoomLevel<13){
                   returnURL= "http://tile" 
                	   + serverIndex+".tianditu.com/DataServer?T=B0627_EMap1112";
                   returnURL+="&X="+x+"&Y="+y+"&L="+zoomLevel;
                }else{
                   returnURL= "http://tile" 
                	   + serverIndex+".tianditu.com/DataServer?T=siwei0608";
                   returnURL+="&X="+x+"&Y="+y+"&L="+zoomLevel;
                }
                 
                break;
            case MapType.GENERIC_MAPTYPE_7:
                if(zoomLevel<11){
                   returnURL= "http://tile" 
                	   + serverIndex+".tianditu.com/DataServer?T=AB0512_Anno";
                   returnURL+="&X="+x+"&Y="+y+"&L="+zoomLevel;
                }else{
                   returnURL=MapType.EMPTY_TILE_URL;
                }
                 
                break;

        }
        return returnURL;
    }

}
