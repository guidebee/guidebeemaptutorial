package com.pstreets.navigator.sms;

import java.util.ArrayList;


public class DeviceMessageCenter {
	
	static ArrayList<DeviceMessage> deviceMessages=new ArrayList<DeviceMessage>();
	
	public static void AddMessage(DeviceMessage message){
		synchronized(deviceMessages){
			deviceMessages.add(message);
		}
	}
	
	public static DeviceMessage getMessage() {
		synchronized (deviceMessages) {
			if (deviceMessages.size() > 0) {
				return deviceMessages.remove(deviceMessages.size()-1);
			}
			return null;
		}
	}

}
