package com.pstreets.navigator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

import com.mapdigit.gis.MapStep;
import com.mapdigit.gis.MapPoint;
import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SharedMapInstance;
import com.pstreets.navigator.app.SharedSearchResults;
import com.pstreets.navigator.routing.RouteDetailActivity;
import com.pstreets.navigator.search.SearchResultListActivity;

class ToolbarButton {

	public Bitmap imageNormal;
	public Bitmap imageDisabled;
	public Rect rectArea;
	public boolean isDisabled;
	public boolean isVisiable;

}

class Toolbar {

	private Bitmap zoomIconGlow;

	ToolbarButton btnZoomIn;
	ToolbarButton btnZoomOut;
	ToolbarButton btnRouteList;
	ToolbarButton btnPrev;
	ToolbarButton btnNext;
	ToolbarButton[] toolbarButtons;

	GuidebeeMapView mapView;
	Rect toolbarArea;
	int imageWidth;
	boolean pressed = false;
	boolean handled=false;
	int pressButtonIndex = -1;
	Rect touchArea=new Rect();
	Toast mToast;

	public Toolbar(GuidebeeMapView mapView) {
		this.mapView = mapView;
		Bitmap toolbarImage = BitmapFactory.decodeResource(
				mapView.getResources(), R.drawable.touchscreen_icons);
		zoomIconGlow = BitmapFactory.decodeResource(mapView.getResources(),
				R.drawable.touchscreen_icon_glow);
		imageWidth = toolbarImage.getHeight();
		btnZoomIn = new ToolbarButton();
		btnZoomOut = new ToolbarButton();
		btnRouteList = new ToolbarButton();
		btnPrev = new ToolbarButton();
		btnNext = new ToolbarButton();
		btnZoomIn.imageNormal = Bitmap.createBitmap(toolbarImage, 0, 0,
				imageWidth, imageWidth);
		btnZoomOut.imageNormal = Bitmap.createBitmap(toolbarImage, imageWidth,
				0, imageWidth, imageWidth);
		btnRouteList.imageNormal = Bitmap.createBitmap(toolbarImage,
				imageWidth * 2, 0, imageWidth, imageWidth);
		btnPrev.imageNormal = Bitmap.createBitmap(toolbarImage, imageWidth * 3,
				0, imageWidth, imageWidth);
		btnNext.imageNormal = Bitmap.createBitmap(toolbarImage, imageWidth * 4,
				0, imageWidth, imageWidth);

		btnZoomIn.imageDisabled = Bitmap.createBitmap(toolbarImage,
				imageWidth * 6, 0, imageWidth, imageWidth);
		btnZoomOut.imageDisabled = Bitmap.createBitmap(toolbarImage,
				imageWidth * 7, 0, imageWidth, imageWidth);
		btnRouteList.imageDisabled = Bitmap.createBitmap(toolbarImage,
				imageWidth * 8, 0, imageWidth, imageWidth);
		btnPrev.imageDisabled = Bitmap.createBitmap(toolbarImage,
				imageWidth * 9, 0, imageWidth, imageWidth);
		btnNext.imageDisabled = Bitmap.createBitmap(toolbarImage,
				imageWidth * 10, 0, imageWidth, imageWidth);
		toolbarButtons = new ToolbarButton[5];
		toolbarButtons[0] = btnRouteList;
		toolbarButtons[1] = btnPrev;
		toolbarButtons[2] = btnNext;
		toolbarButtons[3] = btnZoomIn;
		toolbarButtons[4] = btnZoomOut;
		Display display = ((WindowManager) mapView.mapActivity
				.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();

		int minWidth = Math.min(width, height);
		int offsetY = 24;
		int RectWidth =  minWidth / 5;

		for (int i = 0; i < 3; i++) {
			toolbarButtons[i].rectArea = new Rect(i * RectWidth, height
					- RectWidth - offsetY, i * RectWidth + RectWidth, height
					- offsetY);
			toolbarButtons[i].isVisiable = false;
			toolbarButtons[i].isDisabled = false;
		}

		for (int i = 3; i < 5; i++) {
			toolbarButtons[i].rectArea = new Rect(width - (5 - i) * RectWidth,
					height - RectWidth - offsetY, width - (5 - i) * RectWidth
							+ RectWidth, height - offsetY);
			toolbarButtons[i].isVisiable = true;
			toolbarButtons[i].isDisabled = false;
		}
		toolbarArea = new Rect(0, height - RectWidth - offsetY, width, height
				- offsetY);

		toolbarImage.recycle();
		mToast=Toast.makeText(mapView.mapActivity, "", Toast.LENGTH_LONG);
	}

	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int x = (int) event.getRawX();
		int y = (int) event.getRawY();
		pressButtonIndex = -1;
		for (int i = 0; i < toolbarButtons.length; i++) {
			if (toolbarButtons[i].isVisiable && !toolbarButtons[i].isDisabled) {
				touchArea.top=toolbarButtons[i].rectArea.top-4;
				touchArea.bottom=toolbarButtons[i].rectArea.bottom+4;
				touchArea.left=toolbarButtons[i].rectArea.left;
				touchArea.right=toolbarButtons[i].rectArea.right;
				if (touchArea.contains(x, y)) {
					pressButtonIndex = i;
					break;
				}
			}
		}
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			pressed = true;
			break;
		case MotionEvent.ACTION_UP:
			pressed = false;
			handled=false;
			
			break;
		}
		if (pressButtonIndex != -1) {
			mapView.invalidate();
			if (pressed && !handled) {
				handled=true;
				switch (pressButtonIndex) {
				case 0:
				{
					if (SharedSearchResults.isRouteMode){
						Intent intent = new Intent(mapView.mapActivity, RouteDetailActivity.class); 
						intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						mapView.mapActivity.startActivity(intent);
					}else{
						Intent intent = new Intent(mapView.mapActivity, SearchResultListActivity.class); 
						intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						mapView.mapActivity.startActivity(intent);
					}
				}
					break;
				case 1:
 {
					if (SharedSearchResults.isRouteMode) {
						if (SharedSearchResults.currentMapStepIndex > 0) {
							SharedSearchResults.currentMapStepIndex -= 1;
							final MapStep mapStep = SharedSearchResults.mapStepList
									.get(SharedSearchResults.currentMapStepIndex);

							mapView.mapActivity.handler.post(new Runnable() {

								public void run() {
									mToast.cancel();
									mToast.setText(mapStep.description);
									mToast.show();

								}
							});
							SharedMapInstance.map.panTo(mapStep.firstLatLng);
						}
					} else {
						if (SharedSearchResults.currentSeachResultIndex > 0) {
							SharedSearchResults.currentSeachResultIndex -= 1;
							final MapPoint mapPoint 
							  = SharedSearchResults
							  .searchResults[SharedSearchResults.currentSeachResultIndex];
							mapView.mapActivity.handler.post(new Runnable() {

								public void run() {
									mToast.cancel();
									mToast.setText(mapPoint.name);
									mToast.show();

								}
							});
							SharedMapInstance.map.panTo(mapPoint.point);
							

						}
					}
				}
					break;
				case 2:
				{
					if (SharedSearchResults.isRouteMode) {
						if (SharedSearchResults.currentMapStepIndex < SharedSearchResults.mapStepList
								.size() - 1) {
							SharedSearchResults.currentMapStepIndex += 1;
							final MapStep mapStep = SharedSearchResults.mapStepList
									.get(SharedSearchResults.currentMapStepIndex);

							mapView.mapActivity.handler.post(new Runnable() {

								public void run() {
									mToast.cancel();
									mToast.setText(mapStep.description);
									mToast.show();

								}
							});
							SharedMapInstance.map.panTo(mapStep.firstLatLng);
						}
					}else{
						if (SharedSearchResults.currentSeachResultIndex 
								< SharedSearchResults.searchResults.length-1) {
							SharedSearchResults.currentSeachResultIndex += 1;
							final MapPoint mapPoint 
							  = SharedSearchResults
							  .searchResults[SharedSearchResults.currentSeachResultIndex];
							mapView.mapActivity.handler.post(new Runnable() {

								public void run() {
									mToast.cancel();
									mToast.setText(mapPoint.name);
									mToast.show();

								}
							});
							SharedMapInstance.map.panTo(mapPoint.point);
							

						}
					}
				}
					break;
				case 3:
					SharedMapInstance.map.zoomIn();
					break;
				case 4:
					SharedMapInstance.map.zoomOut();
					break;
				}
			}
		}
		return (pressButtonIndex != -1);
	}

	public void drawToolbar(Canvas canvas) {
		// draw glow
		if (pressButtonIndex != -1 && pressed) {
			int x = (toolbarButtons[pressButtonIndex].rectArea.left + toolbarButtons[pressButtonIndex].rectArea.right) / 2;
			int y = (toolbarButtons[pressButtonIndex].rectArea.top + toolbarButtons[pressButtonIndex].rectArea.bottom) / 2;
			canvas.drawBitmap(zoomIconGlow, x - zoomIconGlow.getWidth() / 2, y
					- zoomIconGlow.getHeight() / 2, mapView.p);

		}
		for (int i = 0; i < toolbarButtons.length; i++) {
			if (toolbarButtons[i].isVisiable) {
				int x = (toolbarButtons[i].rectArea.left + toolbarButtons[i].rectArea.right) / 2;
				int y = (toolbarButtons[i].rectArea.top + toolbarButtons[i].rectArea.bottom) / 2;
				
				if (!toolbarButtons[i].isDisabled) {
					canvas.drawBitmap(toolbarButtons[i].imageNormal, x - imageWidth / 2, y
							- imageWidth / 2, mapView.p);
				} else {
					canvas.drawBitmap(toolbarButtons[i].imageDisabled, x - imageWidth / 2, y
							- imageWidth / 2, mapView.p);
				}
			}
		}

	}

	public void distroy() {
		for (int i = 0; i < toolbarButtons.length; i++) {
			toolbarButtons[i].imageDisabled.recycle();
			toolbarButtons[i].imageNormal.recycle();
		}
		zoomIconGlow.recycle();
	}
}