package com.pstreets.navigator.sms;

import java.util.logging.Logger;

import com.mapdigit.gis.MapLayer;
import com.mapdigit.gis.geometry.GeoLatLng;
import com.pstreets.navigator.app.SessionInfo;
import com.pstreets.navigator.app.SharedMapInstance;
import com.pstreets.navigator.database.PersistentFacade;
import com.pstreets.navigator.dataobject.Device;
import com.pstreets.navigator.dataobject.DeviceCommand;
import com.pstreets.navigator.dataobject.DeviceLocationHistory;


public class DeviceMessageProcessor implements Runnable{
	
	PersistentFacade persistentFacade;
	
	public DeviceMessageProcessor(PersistentFacade persistentFacade){
		this.persistentFacade=persistentFacade;
	}
	
	
	private volatile boolean stopThread=false;
	
	public void stop(){
		
	}
	
	public void run() {
	    while(!stopThread){
	    	DeviceMessage deviceMessage=DeviceMessageCenter.getMessage();
	    	if(deviceMessage==null){
	    		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}else{
	    		mLoger.info("SMS:"+deviceMessage);
	    		processMessage(deviceMessage);
	    	}
	    	
	    }
		
	}

	private Device getDevice(String deviceId){
		for(int i=0;i<SessionInfo.devices.size();i++){
			Device deviceInArray=SessionInfo.devices.get(i);
			int len1=deviceInArray.deviceId.length();
			int len2=deviceId.length();
			int len=Math.min(len1, len2);
			String deviceId1=deviceInArray.deviceId.substring(len1-len,
					len1);
			String deviceId2=deviceId.substring(len2-len,
					len2);
			if(deviceId1.equalsIgnoreCase(deviceId2)){
				return deviceInArray;
			}
		}
		return null;
	}
	
	private void processMessage(DeviceMessage deviceMessage) {
		try {
			
			Device device = getDevice(deviceMessage.deviceNo);
			if (device != null) {
				String[] commands = deviceMessage.messageBody.split(":");
				
				if (commands[0].equalsIgnoreCase(DeviceCommand.CMD_PREFIX)) {
					
					int commandType=Integer.parseInt(commands[1]);
					String commandString=deviceMessage.messageBody.substring(9);
					device.commandList.add(commandString);
					switch(commandType){
					case 101://CMD_BIND
						processBindResponse(device,commandString);
						break;
					case 201://CMD_GPS_ON
						processGPSOnResponse(device,commandString);
						break;
					case 301://CMD_GPS_OFF
						processGPSOffResponse(device,commandString);
						break;
					case 401: //CMD_GET_POWER_LEVEL
						processGetPowerLevelResponse(device,commandString);
						break;
					case 501: //CMD_GET_IMEI
						processGetImeiResponse(device,commandString);
						break;
					case 601: //CMD_GEOFENCE1
						processGPSGeoFenceResponse(device,commandString);
						break;
					case 602: //CMD_GEOFENCE2
						processGPSGeoFenceResponse(device,commandString);
						break;
					case 603://CMD_GEOFENCE_ON
						processBindResponse(device,commandString);
						break;
					case 604://CMD_GEOFENCE_OFF
						processBindResponse(device,commandString);
						break;
					case 701://CMD_GPRS_IP
						processGPRSIPResponse(device,commandString);
						break;
					case 801://CMD_GPRS_ON
						processBindResponse(device,commandString);
						break;
					case 901://CMD_REPORT_PERIOD
					case 902://CMD_REPORT_ONE
						processProcessLocationResponse(device,commandString);
						break;
					case 903:
						processProcessReportLocationResponse(device,commandString);
						break;
						
					}

				}
			}
		} catch (Exception e) {
			mLoger.info("Error process message:" + e.getMessage());
			mLoger.info(deviceMessage.deviceNo + ":"
					+ deviceMessage.messageBody);
		}

	}
	
	private void processBindResponse(Device device,String message){
		mLoger.info("Bind response" + message);
	}
	
	private void processGPSOnResponse(Device device,String message){
		mLoger.info("GPSOn response" + message);	
	}
	
	private void processGPSOffResponse(Device device,String message){
		mLoger.info("GPSOff response" + message);
	}
	
    private void processGetPowerLevelResponse(Device device,String message){
    	mLoger.info("GetPowerLevel response" + message);
	}
	
	private void processGetImeiResponse(Device device,String message){
		mLoger.info("GetImei response" + message);	
	}
	
	private void processGPSGeoFenceResponse(Device device,String message){
		mLoger.info("GPSGeoFence response" + message);
	}
	
    private void processGPRSIPResponse(Device device,String message){
    	mLoger.info("GPRSIP response" + message);
	}
	
    private void processProcessReportLocationResponse(Device device, String location) {
		String[] values = location.split(",");
		if (values.length == 2) {
			
				
				
				try {
					double latitude = Double.parseDouble(values[0]);
					double longitude = Double.parseDouble(values[1]);
					device.lastKnownLocation = new GeoLatLng(latitude,longitude);
					DeviceLocationHistory deviceLocationHistory=new DeviceLocationHistory();
					deviceLocationHistory.deviceNo=device.deviceId;
					deviceLocationHistory.latitude=String.valueOf(latitude);
					deviceLocationHistory.longitude=String.valueOf(longitude);
					deviceLocationHistory.timeStamp=System.currentTimeMillis();
					SharedMapInstance.map.panTo(device.lastKnownLocation);
					persistentFacade.open();
					persistentFacade.addDeviceLocationHistory(deviceLocationHistory);
					persistentFacade.close();
					device.deviceStatus="online";

				} catch (Exception e) {
					
					mLoger.info("Process location error:"+e.getMessage());
				}
			}
		
	}
    
	private void processProcessLocationResponse(Device device, String location) {
		String[] values = location.split(",");
		if (values.length == 6) {
			if (values[0].startsWith("A")) {
				double latitude = latitudeToDouble(values[1], values[2]);
				double longitude = longitudeToDouble(values[3], values[4]);
				location = "[" + longitude + "," + latitude + ",0]";

				try {
					device.lastKnownLocation = MapLayer.fromStringToLatLng(location);
					SharedMapInstance.map.panTo(device.lastKnownLocation);
					DeviceLocationHistory deviceLocationHistory=new DeviceLocationHistory();
					deviceLocationHistory.deviceNo=device.deviceId;
					deviceLocationHistory.latitude=String.valueOf(latitude);
					deviceLocationHistory.longitude=String.valueOf(longitude);
					deviceLocationHistory.timeStamp=System.currentTimeMillis();
					persistentFacade.open();
					persistentFacade.addDeviceLocationHistory(deviceLocationHistory);
					persistentFacade.close();
					device.deviceStatus="online";

				} catch (Exception e) {
					
					mLoger.info("Process location error:"+e.getMessage());
				}
			}
		}
	}
	
	
	private double latitudeToDouble(String latitude, String direction) {
        double ret = 0.0;
        try {
            String strDegree = latitude.substring(0, 2);
            String strMinutes = latitude.substring(2, latitude.length());
            double dblDegree = Double.parseDouble(strDegree);
            double dblMinute = Double.parseDouble(strMinutes) / 60.0;
            ret = dblDegree + dblMinute;
            if (direction.equalsIgnoreCase("S")) {
                ret = -ret;
            }
        } catch (Exception e) {
        }
        return ret;
    }
	
	private double longitudeToDouble(String longitude, String direction) {
        double ret = 0.0;
        try {
            String strDegree = longitude.substring(0, 3);
            String strMinutes = longitude.substring(3, longitude.length());
            double dblDegree = Double.parseDouble(strDegree);
            double dblMinute = Double.parseDouble(strMinutes) / 60.0;
            ret = dblDegree + dblMinute;
            if (direction.equalsIgnoreCase("W")) {
                ret = -ret;
            }
        } catch (Exception e) {
        
        }
        return ret;
    }
	
	
	private static Logger mLoger = Logger.getLogger(DeviceMessageProcessor.class
			.getName());
}
