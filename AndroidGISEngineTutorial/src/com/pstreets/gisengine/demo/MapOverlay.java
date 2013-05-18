package com.pstreets.gisengine.demo;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.geometry.GeoPoint;
import com.mapdigit.gis.raster.MapType;
import com.pstreets.gisengine.GuidebeeMapView;
import com.pstreets.gisengine.R;
import com.pstreets.gisengine.SharedMapInstance;
import com.pstreets.gisengine.drawing.AndroidGraphics;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

public class MapOverlay extends Activity {

	 OverLayMapLayer mapLayer;
	 
	 GuidebeeMapView guidebeeMapView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		guidebeeMapView=(GuidebeeMapView)findViewById(R.id.guidebeemap_view);
	}

	public void onStart() {
		super.onStart();
		GeoLatLng center = new GeoLatLng(32.0616667, 118.7777778);
		SharedMapInstance.map.setCenter(center, 9, MapType.MICROSOFTCHINA);
		mapLayer = new OverLayMapLayer(SharedMapInstance.map.getScreenWidth(),
				SharedMapInstance.map.getScreenHeight());
		SharedMapInstance.map.addMapLayer(mapLayer);

	}
	
	class OverLayMapLayer extends MapLayer {

        GeoLatLng pt1 = new GeoLatLng(32.345281, 118.84261);
        GeoLatLng pt2 = new GeoLatLng(32.05899, 118.62789);
        GeoLatLng pt3 = new GeoLatLng(32.011811, 118.798656);

        public OverLayMapLayer(int width, int height) {
            super(width, height);
        }

        
        public void drawLocationInMouseLocation(IGraphics graphics,int x, int y){
        	x-=guidebeeMapView.offsetX;
        	y-=guidebeeMapView.offsetY;
        	GeoPoint ptOnScreen=new GeoPoint(x,y);
        	
        	GeoLatLng pt=SharedMapInstance.map.fromScreenPixelToLatLng(ptOnScreen);
        	
        	drawPoint(graphics,pt);
        	
        	
        	
        }
        
        public void paint(IGraphics graphics, int offsetX, int offsetY) {
        	
	            drawCursor(graphics);
	            drawTriangle(graphics);
	            drawPoint(graphics, pt1);
	            drawPoint(graphics, pt2);
	            drawPoint(graphics, pt3);
	            drawText(graphics,"Anything",pt1);
	            drawLocationInMouseLocation(graphics,10,10);
        	

        }
        
        
        public void drawText(IGraphics graphics,String message,GeoLatLng pt){
        	Canvas canvas;
        	if(graphics instanceof AndroidGraphics){
        		canvas=((AndroidGraphics)graphics).getNativeCanvas();
        		GeoPoint ptOnScreen = SharedMapInstance.map.fromLatLngToScreenPixel(pt);
                int x = (int) ptOnScreen.x;
                int y = (int) ptOnScreen.y;
                Paint p=new Paint();
                p.setColor(0xFF0000FF);
                canvas.drawText(message, x, y, p);
        	}
        }

        public void drawTriangle(IGraphics g) {
            GeoPoint ptOnScreen1 = SharedMapInstance.map.fromLatLngToScreenPixel(pt1);
            GeoPoint ptOnScreen2 = SharedMapInstance.map.fromLatLngToScreenPixel(pt2);
            GeoPoint ptOnScreen3 = SharedMapInstance.map.fromLatLngToScreenPixel(pt3);
            g.setColor(0xFF0000FF);
            
            g.drawLine((int) ptOnScreen1.x, (int) ptOnScreen1.y,
                    (int) ptOnScreen2.x, (int) ptOnScreen2.y);
            g.drawLine((int) ptOnScreen2.x, (int) ptOnScreen2.y,
                    (int) ptOnScreen3.x, (int) ptOnScreen3.y);
            g.drawLine((int) ptOnScreen1.x, (int) ptOnScreen1.y,
                    (int) ptOnScreen3.x, (int) ptOnScreen3.y);
        }

        public void drawPoint(IGraphics g, GeoLatLng pt) {
            GeoPoint ptOnScreen = SharedMapInstance.map.fromLatLngToScreenPixel(pt);
            int x = (int) ptOnScreen.x;
            int y = (int) ptOnScreen.y;
            g.setColor(0xFF00FF00);
            g.fillRect(x - 4, y - 4, 8, 8);
            
            
           

        }

        private void drawCursor(IGraphics g) {
            
            int mapWidth=getScreenWidth();
    		int mapHeight=getScreenHeight();
    		int x = mapWidth;
    		int y = mapHeight;
            g.setColor(0xFF205020);
            g.drawRect(x - 4, y - 4, 8, 8);
            g.drawLine(x, y - 6, x, y - 2);
            g.drawLine(x, y + 6, x, y + 2);
            g.drawLine(x - 6, y, x - 2, y);
            g.drawLine(x + 6, y, x + 2, y);
        }
    }

}
