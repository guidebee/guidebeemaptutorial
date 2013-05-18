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
import com.pstreets.navigator.dataobject.Device;
import com.pstreets.navigator.drawing.AndroidGraphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.drawing.IGraphics;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.mapdigit.gis.geometry.GeoPoint;
import com.mapdigit.gis.raster.IMapDrawingListener;
import com.mapdigit.gis.raster.IReaderListener;

//[------------------------------ MAIN CLASS ----------------------------------]
//--------------------------------- REVISIONS ----------------------------------
//Date       Name                 Tracking #         Description
//--------   -------------------  -------------      --------------------------
//20AUG2011  James Shen                 	         Initial Creation
////////////////////////////////////////////////////////////////////////////////
/**
 * Guidebee Map View.
 * <hr>
 * <b>&copy; Copyright 2011 Guidebee, Inc. All Rights Reserved.</b>
 * 
 * @version 1.00, 20/08/11
 * @author Guidebee Pty Ltd.
 */
public class GuidebeeMapView extends View implements IReaderListener,
		IMapDrawingListener {
	
	public GuidebeeMapView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mapActivity = (GNavigatorActivity) context;
		toolbar = new Toolbar(this);
		imageWidth = 8;
		downloadImage = ((BitmapDrawable) context.getResources().getDrawable(
				R.drawable.downloading)).getBitmap();
		initGuidebeeMap();
		setFocusable(true);
		postDelayed(updateMapCanvasInSecond, 2000);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			SharedMapInstance.map.panDirection(32, 0);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			SharedMapInstance.map.panDirection(-32, 0);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			SharedMapInstance.map.panDirection(0, 32);
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			SharedMapInstance.map.panDirection(0, -32);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setIconVisible(int imageIndex, boolean visible) {
		toolbar.toolbarButtons[imageIndex].isVisiable = visible;
	}

	public boolean onTouchEvent(MotionEvent event) {
		boolean pressed = toolbar.onTouchEvent(event);
		int screenWidth = getWidth();
		int screenHeight = getHeight();
		int mapWidth = SharedMapInstance.mapImage.getWidth();
		int mapHeight = SharedMapInstance.mapImage.getHeight();
		offsetX = (screenWidth - mapWidth) / 2;
		offsetY = (screenHeight - mapHeight) / 2;
		SharedSearchResults.currentPressedPoint.x = event.getX() - offsetX;
		SharedSearchResults.currentPressedPoint.y = event.getY() - offsetY;
		if (!pressed) {
			SharedMapInstance.centerMyLocation = false;
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

		}
		return super.onTouchEvent(event);
	}

	private int offsetX;
	private int offsetY;

	GNavigatorActivity mapActivity;
	Paint p = new Paint();
	private boolean isPan = false;
	private float oldX = -1;
	private float oldY = -1;
	private Toolbar toolbar;
	private OverLayMapLayer mapLayer;
	private int imageWidth;

	private Bitmap downloadImage;

	private void panMap(float x, float y) {
		float dx = x - oldX;
		float dy = y - oldY;
		if (!(dx == 0 && dy == 0))
			if (SharedMapInstance.map != null) {
				SharedMapInstance.map.panDirection((int) dx, (int) dy);
			}

	}

	final Runnable updateMapCanvas = new Runnable() {
		public void run() {
			invalidate();
		}
	};

	final Runnable updateMapCanvasInSecond = new Runnable() {
		public void run() {
			GuidebeeMapView.this.invalidate();
			SharedMapInstance.myLocationImage = !SharedMapInstance.myLocationImage;
			mapActivity.handler.postDelayed(this, 2000);
		}
	};

	public GuidebeeMapView(Context context) {
		this(context, null);

	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (SharedMapInstance.map != null) {
			SharedMapInstance.map.paint(SharedMapInstance.mapGraphics);
			Bitmap bitmap = (Bitmap) SharedMapInstance.mapImage
					.getNativeImage();
			int mapWidth = SharedMapInstance.mapImage.getWidth();
			int mapHeight = SharedMapInstance.mapImage.getHeight();
			int screenWidth = getWidth();
			int screenHeight = getHeight();
			offsetX = (screenWidth - mapWidth) / 2;
			offsetY = (screenHeight - mapHeight) / 2;
			canvas.drawBitmap(bitmap, offsetX, offsetY, p);
			drawCursor(canvas);

		} else {

			drawEmptyCanvas(canvas);

		}
		toolbar.drawToolbar(canvas);

	}

	Paint mPaint = new Paint();

	private void drawCursor(Canvas canvas) {

		int mapWidth = getWidth();
		int mapHeight = getHeight();
		int x = mapWidth / 2;
		int y = mapHeight / 2;
		mPaint.setColor(0xff205020);
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(x - 4, y - 4, x + 4, y + 4, mPaint);
		canvas.drawLine(x, y - 6, x, y - 2, mPaint);
		canvas.drawLine(x, y + 6, x, y + 2, mPaint);
		canvas.drawLine(x - 6, y, x - 2, y, mPaint);
		canvas.drawLine(x + 6, y, x + 2, y, mPaint);

	}

	private void drawEmptyCanvas(Canvas canvas) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		int mapWidth = downloadImage.getHeight();
		int rows = height / mapWidth + 2;
		int cols = width / mapWidth + 2;
		for (int i = 0; i <= rows; i++) {
			for (int j = 0; j <= cols; j++) {
				canvas.drawBitmap(downloadImage, i * mapWidth, j * mapWidth, p);
			}
		}
	}

	public void initGuidebeeMap() {
		if (SharedMapInstance.map != null) {
			SharedMapInstance.map.setMapDrawingListener(this);
			SharedMapInstance.mapTileDownloadManager.setReaderListener(this);
			mapLayer = new OverLayMapLayer(
					SharedMapInstance.map.getScreenWidth(),
					SharedMapInstance.map.getScreenHeight());
			SharedMapInstance.map.addMapLayer(mapLayer);
			done();
		}
	}

	public void destroy() {
		toolbar.distroy();
		downloadImage.recycle();

	}

	public void readProgress(int bytes, int total) {
	}

	public void done() {
		post(updateMapCanvas);
	}

	class OverLayMapLayer extends MapLayer {

		public OverLayMapLayer(int width, int height) {
			super(width, height);
		}


		public void paint(IGraphics graphics, int offsetX, int offsetY) {
			Canvas canvas = ((AndroidGraphics) graphics).getNativeCanvas();
			drawSearchResults(canvas);
			drawMyLocation(canvas);
			drawDirectionFlag(canvas);
			drawDeviceList(canvas);

		}

		public void drawSearchResults(Canvas graphics) {
			if (SharedSearchResults.searchResults != null) {
				for (int i = 0; i < SharedSearchResults.searchResults.length; i++) {
					drawRedNumber(i, graphics,
							SharedSearchResults.searchResults[i].point);
				}
			}
		}

		public void drawDeviceList(Canvas graphics) {
			if (SessionInfo.devices != null) {
				for (int i = 0; i < SessionInfo.devices.size(); i++) {
					Device device = SessionInfo.devices.get(i);
					drawDevice(graphics, i, device);
				}
			}
		}

		public void drawDirectionFlag(Canvas graphics) {
			if (SharedSearchResults.directionStart != null) {
				drawBlueNumber(0, graphics,
						SharedSearchResults.directionStart.point);
			}
			if (SharedSearchResults.directionEnd != null) {
				drawBlueNumber(1, graphics,
						SharedSearchResults.directionEnd.point);
			}
		}


		public void drawRedNumber(int num, Canvas graphics, GeoLatLng pt) {
			GeoPoint ptOnScreen = SharedMapInstance.map
					.fromLatLngToScreenPixel(pt);
			int x = (int) ptOnScreen.x;
			int y = (int) ptOnScreen.y;
			imageWidth = mapActivity.imageRedNumbers.getScaledHeight(graphics) / 2;
			Rect src = new Rect(num * imageWidth * 2, 0, (num + 1) * imageWidth
					* 2, imageWidth * 2);
			Rect dst = new Rect(x - imageWidth, y - imageWidth, x + imageWidth,
					y + imageWidth);
			graphics.drawBitmap(mapActivity.imageRedNumbers, src, dst, null);
		}

		public void drawBlueNumber(int num, Canvas graphics, GeoLatLng pt) {
			GeoPoint ptOnScreen = SharedMapInstance.map
					.fromLatLngToScreenPixel(pt);
			int x = (int) ptOnScreen.x;
			int y = (int) ptOnScreen.y;
			imageWidth = mapActivity.imageRedNumbers.getScaledHeight(graphics) / 2;
			Rect src = new Rect(num * imageWidth * 2, 0, (num + 1) * imageWidth
					* 2, imageWidth * 2);
			Rect dst = new Rect(x - imageWidth, y - imageWidth, x + imageWidth,
					y + imageWidth);
			graphics.drawBitmap(mapActivity.imageBlueNumbers, src, dst, null);
		}

		public void drawDevice(Canvas graphics, int num, Device device) {
			if (device.lastKnownLocation != null) {
				GeoPoint ptOnScreen = SharedMapInstance.map
						.fromLatLngToScreenPixel(device.lastKnownLocation);
				int x = (int) ptOnScreen.x;
				int y = (int) ptOnScreen.y;
				imageWidth = mapActivity.imageGreenNumbers
						.getScaledHeight(graphics) / 2;
				Rect src = new Rect(num * imageWidth * 2, 0, (num + 1)
						* imageWidth * 2, imageWidth * 2);
				Rect dst = new Rect(x - imageWidth, y - imageWidth, x
						+ imageWidth, y + imageWidth);
				graphics.drawBitmap(mapActivity.imageGreenNumbers, src, dst,
						null);
				graphics.drawText(device.deviceName, x + imageWidth * 2, y,
						mPaint);
			}
		}

		public void drawGreenNumber(int num, Canvas graphics, GeoLatLng pt) {
			GeoPoint ptOnScreen = SharedMapInstance.map
					.fromLatLngToScreenPixel(pt);
			int x = (int) ptOnScreen.x;
			int y = (int) ptOnScreen.y;
			imageWidth = mapActivity.imageRedNumbers.getScaledHeight(graphics) / 2;
			Rect src = new Rect(num * imageWidth * 2, 0, (num + 1) * imageWidth
					* 2, imageWidth * 2);
			Rect dst = new Rect(x - imageWidth, y - imageWidth, x + imageWidth,
					y + imageWidth);
			graphics.drawBitmap(mapActivity.imageGreenNumbers, src, dst, null);
		}

		private void drawMyLocation(Canvas graphics) {
			if (SharedMapInstance.currentLocation.x != 0) {
				GeoPoint ptOnScreen = SharedMapInstance.map
						.fromLatLngToScreenPixel(SharedMapInstance.currentLocation);
				int x = (int) ptOnScreen.x;
				int y = (int) ptOnScreen.y;
				if (SharedMapInstance.myLocationImage) {
					imageWidth = mapActivity.imageBlueLocation
							.getScaledHeight(graphics) / 2;
					graphics.drawBitmap(mapActivity.imageBlueLocation, x
							- imageWidth, y - imageWidth, null);

				} else {
					imageWidth = mapActivity.imageGreyLocation
							.getScaledHeight(graphics) / 2;
					graphics.drawBitmap(mapActivity.imageGreyLocation, x
							- imageWidth, y - imageWidth, null);
				}
			}

		}

	}

}
