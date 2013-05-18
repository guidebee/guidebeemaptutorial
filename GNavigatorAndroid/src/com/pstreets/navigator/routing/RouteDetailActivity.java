package com.pstreets.navigator.routing;
import java.util.Hashtable;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mapdigit.gis.MapDirection;
import com.mapdigit.gis.MapDirectionCommandElement;
import com.mapdigit.gis.MapDirectionCommandType;
import com.mapdigit.gis.MapStep;
import com.pstreets.navigator.GNavigatorActivity;
import com.pstreets.navigator.R;
import com.pstreets.navigator.app.SharedMapInstance;
import com.pstreets.navigator.app.SharedSearchResults;
public class RouteDetailActivity extends RoboListActivity{
	
	@InjectView (R.id.txtSummary) TextView txtSummary;
	@InjectView (R.id.btnMap) ImageButton btnMap;
	@InjectResource (R.string.km) String km;
	
	
	Hashtable<String,Drawable> iconDrawables=new Hashtable<String,Drawable>();
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
        setContentView(R.layout.routedetail);
        
        btnMap.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(RouteDetailActivity.this,
						GNavigatorActivity.class); 
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        startActivity(intent); 
			}
		});
        mLayoutInflater=LayoutInflater.from(this);
        
    }
    
    @Override
	public void onResume(){
    	super.onResume();
    	updateRouteStepList();
    }
    
    
    OnItemClickListener onChildClickListener=new OnItemClickListener(){

		public void onItemClick(AdapterView<?> parent, View view, 
					int position,	long id) {
			   
			   SharedSearchResults.currentMapStepIndex=position;
			   MapStep mapStep=SharedSearchResults.mapStepList.get(position);
			   SharedMapInstance.map.setCenter(mapStep.firstLatLng, 15);
			   Intent intent = new Intent(RouteDetailActivity.this,
						GNavigatorActivity.class); 
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		        startActivity(intent); 
			   
			  	
				
			}};
	
	private void updateRouteStepList() {
		SharedSearchResults.mapStepList.clear();
		SharedSearchResults.currentMapStepIndex=0;
		
		MapDirection mapDirection = SharedSearchResults.mapDirection;
		if (mapDirection != null) {
			txtSummary.setText(mapDirection.summary);
			for (int j = 0; j < mapDirection.routes.length; j++) {
				MapStep[] mapSteps = mapDirection.routes[j].steps;
				for (int i = 0; i < mapSteps.length; i++) {
					SharedSearchResults.mapStepList.add(mapSteps[i]);
				}
			}
		}
		
		setListAdapter(new RouteStepListAdapter(this));
		getListView().setOnItemClickListener(onChildClickListener);
	}
    
   private Drawable getDrawableByResId(int type){
	   try{
	   int ResId=getResIdByType(type);
	   Drawable drawable=iconDrawables.get(String.valueOf(ResId));
	   if(drawable==null){
		   drawable=getResources().getDrawable(ResId);
		   iconDrawables.put(String.valueOf(ResId), drawable);
	   }
	   return drawable;
	   }catch(OutOfMemoryError ome){
		   System.gc();
		   return null;
	   }
   }
	
	
	private int getResIdByType(int type) {
		int image = 0;
		switch (type) {

		case MapDirectionCommandType.COMMAND_HEAD_DIRECTION_E:
		case MapDirectionCommandType.COMMAND_HEAD_DIRECTION_N:
		case MapDirectionCommandType.COMMAND_HEAD_DIRECTION_NE:
		case MapDirectionCommandType.COMMAND_HEAD_DIRECTION_W:
		case MapDirectionCommandType.COMMAND_HEAD_DIRECTION_NW:
		case MapDirectionCommandType.COMMAND_HEAD_DIRECTION_S:
		case MapDirectionCommandType.COMMAND_HEAD_DIRECTION_SE:
		case MapDirectionCommandType.COMMAND_HEAD_DIRECTION_SW:
			image = R.drawable.navigator;
			break;

		case MapDirectionCommandType.COMMAND_BEAR_LEFT:
			image = R.drawable.bearleft;
			break;
		case MapDirectionCommandType.COMMAND_BEAR_RIGHT:
			image = R.drawable.bearright;
			break;
		case MapDirectionCommandType.COMMAND_DESTINATION_ON_THE_LEFT:
			image = R.drawable.destinationonleft;
			break;
		case MapDirectionCommandType.COMMAND_DESTINATION_ON_THE_RIGHT:
			image = R.drawable.destinationonright;
			break;
		case MapDirectionCommandType.COMMAND_ENTER_HIGHWAY:
			image = R.drawable.enterhighway;
			break;
		case MapDirectionCommandType.COMMAND_ENTER_HIGHWAY_LEFT:
			image = R.drawable.enterhighwayonleft;
			break;
		case MapDirectionCommandType.COMMAND_ENTER_HIGHWAY_RIGHT:
			image = R.drawable.enterhighwayonright;
			break;
		case MapDirectionCommandType.COMMAND_KEEP_LEFT:
			image = R.drawable.keepleft;
			break;
		case MapDirectionCommandType.COMMAND_KEEP_RIGHT:
			image = R.drawable.keepright;
			break;
		case MapDirectionCommandType.COMMAND_LEAVE_HIGHWAY:
			image = R.drawable.exithighway;
			break;
		case MapDirectionCommandType.COMMAND_LEAVE_HIGHWAY_LEFT:
			image = R.drawable.exithighwayonleft;
			break;
		case MapDirectionCommandType.COMMAND_LEAVE_HIGHWAY_RIGHT:
			image = R.drawable.exithighwayonright;
			break;
		case MapDirectionCommandType.COMMAND_NO_TURN:
		case MapDirectionCommandType.COMMAND_MERGE:
			image = R.drawable.gostraight;
			break;
		case MapDirectionCommandType.COMMAND_REACH_DESTINATION:
			image = R.drawable.destination;
			break;
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_1_EXIT:
			image = R.drawable.roundabouttake1exit;
			break;
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_2_EXIT:
			image = R.drawable.roundabouttake2exit;
			break;
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_3_EXIT:
			image = R.drawable.roundabouttake3exit;
			break;
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_4_EXIT:
			image = R.drawable.roundabouttake4exit;
			break;
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_5_EXIT:
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_6_EXIT:
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_7_EXIT:
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_8_EXIT:
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_9_EXIT:
		case MapDirectionCommandType.COMMAND_ROUNDABOUT_10_EXIT:
			image = R.drawable.roundabouttakeexit;
			break;
		case MapDirectionCommandType.COMMAND_SHARP_LEFT:
			image = R.drawable.sharpleft;
			break;
		case MapDirectionCommandType.COMMAND_SHARP_RIGHT:
			image = R.drawable.sharpright;
			break;
		case MapDirectionCommandType.COMMAND_TAKE_1_LEFT:
		case MapDirectionCommandType.COMMAND_TAKE_2_LEFT:
		case MapDirectionCommandType.COMMAND_TAKE_3_LEFT:
		case MapDirectionCommandType.COMMAND_TAKE_4_LEFT:
		case MapDirectionCommandType.COMMAND_TAKE_5_LEFT:
		case MapDirectionCommandType.COMMAND_TAKE_6_LEFT:
		case MapDirectionCommandType.COMMAND_TAKE_7_LEFT:
		case MapDirectionCommandType.COMMAND_TAKE_8_LEFT:
		case MapDirectionCommandType.COMMAND_TAKE_9_LEFT:
		case MapDirectionCommandType.COMMAND_TURN_LEFT:
			image = R.drawable.turnleft;
			break;
		case MapDirectionCommandType.COMMAND_TAKE_1_RIGHT:
		case MapDirectionCommandType.COMMAND_TAKE_2_RIGHT:
		case MapDirectionCommandType.COMMAND_TAKE_3_RIGHT:
		case MapDirectionCommandType.COMMAND_TAKE_4_RIGHT:
		case MapDirectionCommandType.COMMAND_TAKE_5_RIGHT:
		case MapDirectionCommandType.COMMAND_TAKE_6_RIGHT:
		case MapDirectionCommandType.COMMAND_TAKE_7_RIGHT:
		case MapDirectionCommandType.COMMAND_TAKE_8_RIGHT:
		case MapDirectionCommandType.COMMAND_TAKE_9_RIGHT:
		case MapDirectionCommandType.COMMAND_TURN_RIGHT:
			image = R.drawable.turnright;
			break;
		case MapDirectionCommandType.COMMAND_U_TURN:
			image = R.drawable.makeuturn;
			break;
		default:
			image = R.drawable.destination;
			break;

		}
		return image;
	}
   
    
	
	
	/**
     * A sample ListAdapter that presents content from arrays of speeches and
     * text.
     * 
     */
    private class RouteStepListAdapter extends BaseAdapter {
        public RouteStepListAdapter(Context context) {
            mContext = context;
        }

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         * 
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return SharedSearchResults.mapStepList.size();
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         * 
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         * 
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        
        
        /**
         * Make a SpeechView to hold each row.
         * 
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         *      android.view.ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
        	MapStep mapStep=SharedSearchResults.mapStepList.get(position);
        	int commandType = mapStep
        			.directionCommandElements
        			[MapDirectionCommandElement
        			 .DIRECTION_COMMAND].directionCommandType.type;
        	double distanceInKm=mapStep.distance / 1000.0;
            Drawable drawable=getDrawableByResId(commandType);
        	if (convertView == null) {
            	convertView = new RouteDetailItemView(mContext,
            			drawable,
            			mapStep.description,String.valueOf(distanceInKm));

            } else {
            	RouteDetailItemView view=(RouteDetailItemView)convertView;
            	view.setImageDRawable(drawable);
            	view.setDesc(mapStep.description);
            	view.setDistance(String.valueOf(distanceInKm));
           }

            return convertView;
        }

        /**
         * Remember our context so we can use it when constructing views.
         */
        private Context mContext;
        
        
    }
    
    private LayoutInflater mLayoutInflater;
    
    private class RouteDetailItemView extends LinearLayout {
        public RouteDetailItemView(Context context, Drawable drawable, 
        		String name,String price) {
            super(context);
            mLayoutInflater
			.inflate(R.layout.route_detail_list_item, this);
            imageDirection=(ImageView)findViewById(R.id.imageDirection);
            txtDesc=(TextView)findViewById(R.id.txtDesc);
            txtDistance=(TextView)this.findViewById(R.id.txtDistance);
            imageDirection.setImageDrawable(drawable);
            txtDesc.setText(name);
            txtDistance.setText(price +" "+km);
            
        }

        
        public void setImageDRawable(Drawable drawable) {
        	imageDirection.setImageDrawable(drawable);
        }

        
        public void setDesc(String words) {
        	txtDesc.setText(words);
        }
        
        public void setDistance(String words) {
        	txtDistance.setText(words +" "+km);
        }

        private ImageView imageDirection;
        private TextView txtDesc;
        private TextView txtDistance;
    }
    

}
