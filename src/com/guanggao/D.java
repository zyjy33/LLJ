package com.guanggao;

import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;
import android.os.CountDownTimer;

public class D extends BaseActivity{
	 
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guanggao);
		//�����������ȡϵͳ����TELEPHONY_SERVICE
	    /*TelephonyManager telM = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);  
		telM.listen(new TelListener(this), PhoneStateListener.LISTEN_CALL_STATE);*/
		
		 /*CountDownTimer timer = new CountDownTimer(3000,100) {
			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				AppManager.getAppManager().finishAllActivity();
			}
		};
		timer.start();*/
	};
}