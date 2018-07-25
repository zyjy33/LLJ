package com.hengyushop.demo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;
import com.lelinju.www.UserLoginActivity;
import com.lelinju.www.UserLoginWayActivity;
/**
 * 提示绑定手机号
 * @author 
 *
 */
public class TishiBaoMinOkActivity extends Activity implements OnClickListener{
	private TextView btnConfirm;//
	private TextView btnCancle,tv_yue;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign,amount;
	String user_name,user_id,headimgurl,access_token,sex,unionid,area,real_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_baomin_ok);
//		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
//		user_name = spPreferences.getString("user", "");
//		user_id = spPreferences.getString("user_id", "");
//		progress = new DialogProgress(TishiWxBangDingActivity.this);
		initUI();
	}


	
	protected void initUI() {
		btnConfirm =(TextView) findViewById(R.id.btnConfirm);//
		btnConfirm.setOnClickListener(this);//
		btnCancle =(TextView) findViewById(R.id.btnCancle);// 
		btnCancle.setOnClickListener(this);//
		
		handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 8:
					
				}
			}
		};
	}
	
	
	/**
	 * 点击触发事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		intent = new Intent();
		switch (v.getId()) {
		case R.id.btnConfirm://取消
			finish();
			break;
		case R.id.btnCancle://
			Intent intent = new Intent(TishiBaoMinOkActivity.this, DianZiPiaoActivity.class);
			  intent.putExtra("hd_title",getIntent().getStringExtra("title"));
			  intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
			  intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
			  intent.putExtra("address",getIntent().getStringExtra("address"));
			startActivity(intent);
			 finish();
			break;
	    
		default:
			break;
		}
	}
	
	    
	
}