package com.pstreets.navigator;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import roboguice.activity.RoboActivity;

public class AboutActivity extends RoboActivity{
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.about);
        mHandler.postDelayed(mFinishActivity, 3000);
    }
    
    
    protected Handler mHandler=new Handler();
    
    @Override
	public boolean dispatchKeyEvent (KeyEvent event){
    	backToParent();
		return super.dispatchKeyEvent(event);
	}
	
	@Override
	public boolean dispatchTouchEvent (MotionEvent ev){
		backToParent();
		return super.dispatchTouchEvent(ev);
	}
	
	public boolean dispatchGenericMotionEvent (MotionEvent ev){
		backToParent();
		return super.dispatchTouchEvent(ev);
	}
    /**
	 * update view.
	 */
    protected Runnable mFinishActivity =new Runnable(){

		@Override
		public void run() {
			backToParent();
		}
		
	};
	
	protected void backToParent() {
		Intent intent = new Intent(AboutActivity.this,
				GNavigatorActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
	}

}
