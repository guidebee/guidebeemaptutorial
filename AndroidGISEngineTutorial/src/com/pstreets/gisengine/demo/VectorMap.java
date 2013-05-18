package com.pstreets.gisengine.demo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;

import com.mapdigit.gis.drawing.IFont;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.raster.IReaderListener;
import com.mapdigit.gis.raster.MapTileDownloadManager;
import com.mapdigit.gis.raster.MapType;
import com.mapdigit.gis.raster.RasterMap;
import com.mapdigit.gis.vector.GeoSet;
import com.mapdigit.gis.vector.MapFeatureLayer;
import com.mapdigit.gis.vector.VectorMapRenderer;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;

import android.app.Activity;
import android.os.Bundle;

public class VectorMap extends Activity implements IReaderListener{

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public void onStart() {
		super.onStart();
		
		
		try{
			InputStream geoStream = getResources().openRawResource(R.raw.mawsonlakes);
            InputStream airStream = getResources().openRawResource(R.raw.aus_air);
            InputStream drStream = getResources().openRawResource(R.raw.aus_dr);
            InputStream pkStream = getResources().openRawResource(R.raw.aus_pk);
            InputStream rlStream = getResources().openRawResource(R.raw.aus_rl);
            InputStream rlsStream = getResources().openRawResource(R.raw.aus_rls);
            InputStream stStream = getResources().openRawResource(R.raw.aus_st);
            InputStream pcStream = getResources().openRawResource(R.raw.aust05);

            byte[] bufferGeo = null;
            bufferGeo = new byte[geoStream.available()];
            geoStream.read(bufferGeo);
            geoStream.close();
            ByteArrayInputStream baisGeo = new ByteArrayInputStream(bufferGeo);
            GeoSet geoSet = new GeoSet(new DataInputStream(baisGeo));

            byte[] bufferAIR = null;
            bufferAIR = new byte[airStream.available()];
            airStream.read(bufferAIR);
            airStream.close();
            ByteArrayInputStream baisAIR = new ByteArrayInputStream(bufferAIR);
            MapFeatureLayer layerAIR=new MapFeatureLayer(new DataInputStream(baisAIR));
            //layerAIR.fontColor=0xFF0000;

            byte[] bufferDR = null;
            bufferDR = new byte[drStream.available()];
            drStream.read(bufferDR);
            drStream.close();
            ByteArrayInputStream baisDR = new ByteArrayInputStream(bufferDR);
            MapFeatureLayer layerDR=new MapFeatureLayer(new DataInputStream(baisDR));
            //layerDR.fontColor=0x00FF00;

            byte[] bufferPK = null;
            bufferPK = new byte[pkStream.available()];
            pkStream.read(bufferPK);
            pkStream.close();
            ByteArrayInputStream baisPK = new ByteArrayInputStream(bufferPK);
            MapFeatureLayer layerPK=new MapFeatureLayer(new DataInputStream(baisPK));
            //.fontColor=0x0000FF;

            byte[] bufferRL = null;
            bufferRL = new byte[rlStream.available()];
            rlStream.read(bufferRL);
            rlStream.close();
            ByteArrayInputStream baisRL = new ByteArrayInputStream(bufferRL);
            MapFeatureLayer layerRL=new MapFeatureLayer(new DataInputStream(baisRL));
            //layerRL.fontColor=0xFFFF00;

            byte[] bufferRLS = null;
            bufferRLS = new byte[rlsStream.available()];
            rlsStream.read(bufferRLS);
            rlsStream.close();
            ByteArrayInputStream baisRLS = new ByteArrayInputStream(bufferRLS);
            MapFeatureLayer layerRLS=new MapFeatureLayer(new DataInputStream(baisRLS));
            //layerRLS.fontColor=0xFF00FF;

            byte[] bufferST = null;
            bufferST = new byte[stStream.available()];
            stStream.read(bufferST);
            stStream.close();
            ByteArrayInputStream baisST = new ByteArrayInputStream(bufferST);
            MapFeatureLayer layerST=new MapFeatureLayer(new DataInputStream(baisST));
            //layerST.fontColor=0x00FFFF;

            byte[] bufferPC = null;
            bufferPC = new byte[pcStream.available()];
            pcStream.read(bufferPC);
            pcStream.close();
            ByteArrayInputStream baisPC = new ByteArrayInputStream(bufferPC);
            MapFeatureLayer layerPC=new MapFeatureLayer(new DataInputStream(baisPC));
            //layerPC.fontColor=0x8080FF;

            geoSet.addMapFeatureLayer(layerRLS);
            geoSet.addMapFeatureLayer(layerST);
            geoSet.addMapFeatureLayer(layerRL);
            geoSet.addMapFeatureLayer(layerPK);
            geoSet.addMapFeatureLayer(layerDR);
            geoSet.addMapFeatureLayer(layerAIR);
            geoSet.addMapFeatureLayer(layerPC);
            VectorMapRenderer vectorMapRenderer=new VectorMapRenderer(geoSet);
         geoSet.open();
         SharedMapInstance.mapTileDownloadManager = new MapTileDownloadManager(this, vectorMapRenderer);
         SharedMapInstance.map = new RasterMap(1024, 1024,
 				SharedMapInstance.mapTileDownloadManager);

         SharedMapInstance.mapTileDownloadManager.start();
		 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		 
		
		GeoLatLng center=new GeoLatLng(-34.8176410,138.6163120 );
		SharedMapInstance.map.setCenter(center, 5, MapType.MICROSOFTCHINA);

	}

	@Override
	public void readProgress(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
