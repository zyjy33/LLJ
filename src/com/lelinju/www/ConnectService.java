/* Copyright (C) 2010-2011, Mamadou Diop.
*  Copyright (C) 2011, Doubango Telecom.
*
* Contact: Mamadou Diop <diopmamadou(at)doubango(dot)org>
*	
* This file is part of imsdroid Project (http://code.google.com/p/imsdroid)
*
* imsdroid is free software: you can redistribute it and/or modify it under the terms of 
* the GNU General Public License as published by the Free Software Foundation, either version 3 
* of the License, or (at your option) any later version.
*	
* imsdroid is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
* See the GNU General Public License for more details.
*	
* You should have received a copy of the GNU General Public License along 
* with this program; if not, write to the Free Software Foundation, Inc., 
* 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
package com.lelinju.www;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.yzx.api.ClientType;
import com.yzx.api.UCSCall;
import com.yzx.api.UCSMessage;
import com.yzx.api.UCSService;
import com.yzx.listenerInterface.CallStateListener;
import com.yzx.listenerInterface.ConnectionListener;
import com.yzx.listenerInterface.MessageListener;
import com.yzx.listenerInterface.UcsReason;
import com.yzx.tcp.packet.UcsMessage;
import com.yzx.tcp.packet.UcsStatus;


public class ConnectService extends Service {
	private final static String TAG = ConnectService.class.getCanonicalName();
	
	public ConnectService(){
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate()");
		UCSService.init(getApplicationContext(),true);
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
			System.out.println("连接通话服务");
		UCSService.addConnectionListener(new ConnectionListener() {
			@Override
			public void onConnectionSuccessful() {
						
				Log.i("mark", "UCSService 连接成功");
			}
					
			@Override
			public void onConnectionFailed(UcsReason arg0) {
					
				Log.i("mark", "UCSService 连接失败"+arg0.getReason());
			}
		});
		 
		
	
		UCSCall.addCallStateListener(new CallStateListener() {
			
			//来电，开始响??
			@Override
			public void onIncomingCall(String arg0, String arg1, String arg2) {
				
				//true 还震??
				UCSCall.setSpeakerphone(true);
				UCSCall.startRinging(true);
			
			}
					
			//只需处理挂机UI
			@Override
			public void onHangUp(String arg0, UcsReason arg1) {
				
				UCSCall.stopRinging();
			}
				
			
			//呼叫失败 直拨与VOIP
			@Override
			public void onDialFailed(String arg0, UcsReason arg1) {
						
				Log.i("mark", "呼叫失败");
			}
				
			//两端回拨
			@Override
			public void onCallBackSuccess() {
				
				
			}
					
			//对端接听
			@Override
			public void onAnswer(String arg0) {
						
				Log.i("mark", "对端接听回调"+arg0);
				UCSCall.setSpeakerphone(false);
				UCSCall.stopRinging();
				//回调响应
			}
			
			//播放回铃 直播回铃
			@Override
			public void onAlerting(String arg0) {
						
			}
			
		});
		
		UCSMessage.addMessageListener(new MessageListener() {
			
			@Override
			public void onUserState(ArrayList arg0) {
				
				for(int i=0;i<arg0.size();i++){
					UcsStatus status = (UcsStatus) arg0.get(i);
				}
				
				
				
			}
			
			@Override
			public void onSendUcsMessage(UcsReason arg0, UcsMessage arg1) {
				
			}
			
			@Override
			public void onSendFileProgress(int arg0) {
				
			}
			
			@Override
			public void onReceiveUcsMessage(UcsReason arg0, UcsMessage arg1) {
				
			}
			
			@Override
			public void onDownloadAttachedProgress(String arg0, String arg1, int arg2,
					int arg3) {
				
			}
		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				UCSMessage.queryUserState(ClientType.CLIENT, "79318000878750");
				
			}
		}).start();
		
	}
	
	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy()");
		super.onDestroy();
	
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}
