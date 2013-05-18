package com.pstreets.gisengine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.mapdigit.gis.raster.IMapDrawingListener;
import com.mapdigit.gis.raster.IReaderListener;

public class GuidebeeMapView extends View implements IReaderListener,
		IMapDrawingListener {

	
	public int offsetX;
	public int offsetY;
	
	Paint p = new Paint();
	boolean isPan = false;
	private float oldX = -1;
	private float oldY = -1;

	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			oldX = event.getRawX();
			oldY = event.getRawY();
			isPan = true;
			break;
		case MotionEvent.ACTION_UP:
			oldX = event.getRawX();
			oldY = event.getRawY();
			isPan = false;
			break;
		case MotionEvent.ACTION_MOVE:
			if (isPan) {
				panMap(event.getRawX(), event.getRawY());
				oldX = event.getRawX();
				oldY = event.getRawY();
			}
			break;
		}
		return true;
	}

	private void panMap(float x, float y) {
		float dx = x - oldX;
		float dy = y - oldY;
		if (!(dx == 0 && dy == 0))
			SharedMapInstance.map.panDirection((int) dx, (int) dy);

	}

	final Runnable updateMapCanvas = new Runnable() {
		public void run() {
			postInvalidate();
		}
	};

	public GuidebeeMapView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initGuidebeeMap();

	}

	public GuidebeeMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initGuidebeeMap();

	}

	public GuidebeeMapView(Context context) {
		super(context);
		initGuidebeeMap();

	}

	
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Bitmap bitmap = (Bitmap) SharedMapInstance.mapImage.getNativeImage();
		int mapWidth=SharedMapInstance.mapImage.getWidth();
		int mapHeight=SharedMapInstance.mapImage.getHeight();
		int screenWidth=getWidth();
		int screenHeight=getHeight();
		offsetX=(screenWidth-mapWidth)/2;
		offsetY=(screenHeight-mapHeight)/2;
		canvas.drawBitmap(bitmap, offsetX,  offsetY, p);
	}

	private void initGuidebeeMap() {


		SharedMapInstance.map.setMapDrawingListener(this);
		SharedMapInstance.mapTileDownloadManager.setReaderListener(this);
		
	}


	 

	@Override
	public void readProgress(int bytes, int total) {
		//System.out.println(bytes);

	}

	@Override
	public void done() {
		SharedMapInstance.map.paint(SharedMapInstance.mapGraphics);

		post(updateMapCanvas);

	}


}
