/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pstreets.navigator.sms;

import com.pstreets.navigator.dataobject.DeviceCommand;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;



public class SmsMessageReceiver extends BroadcastReceiver {
	

    @Override
    public void onReceive(Context context, Intent intent) {
        
        Bundle extras = intent.getExtras();
        if (extras == null)
            return;

        Object[] pdus = (Object[]) extras.get("pdus");

        for (int i = 0; i < pdus.length; i++) {
            SmsMessage message = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String fromAddress = message.getOriginatingAddress();
            String msg=message.getMessageBody();
            if(msg.startsWith(DeviceCommand.CMD_PREFIX)){
            	abortBroadcast();
               DeviceMessage deviceMessage=new DeviceMessage();
               deviceMessage.deviceNo=fromAddress;
               deviceMessage.messageBody=msg;
               DeviceMessageCenter.AddMessage(deviceMessage);
            }


        }
    }
}
