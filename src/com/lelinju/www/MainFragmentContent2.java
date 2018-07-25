package com.lelinju.www;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.android.hengyu.web.DialogProgress;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.demo.service.ApplyBusinessActivity;
import com.lelinju.www.R;

public class MainFragmentContent2 extends Fragment{
	private LinearLayout list_shops, list_none;
	private DialogProgress progress;
	static StringBuffer sb;
	int shopping_id;
	private SharedPreferences spPreferences;
	private CheckBox ck_xuanzhe;
	private Button btn_register;
	String user_name,user_code,nickname;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View view = inflater.inflate(R.layout.activity_yun_service, null);
		 //在此调用下面方法，才能捕获到线程中的异常
//		Thread.setDefaultUncaughtExceptionHandler(this);
		progress = new DialogProgress(getActivity());
		spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		nickname = spPreferences.getString("nickname", "");
		ininate(view);
//		loadWeather();
		setTotalCost();
		return view;
		
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 
	}
	 
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	/**
	 * 初始化控件类别
	 */
	private void ininate(View layout) {
		ck_xuanzhe = (CheckBox)layout.findViewById(R.id.ck_xuanzhe);
		btn_register = (Button) layout.findViewById(R.id.btn_register);
	}
public void setTotalCost(){
		
		ck_xuanzhe.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (ck_xuanzhe.isChecked()) {

//					Toast.makeText(getActivity(), "登录成功1", 1000).show();
					getShowButton(); 
				} else {
					getNisabledButton(); 
//					Toast.makeText(getActivity(), "登录成功2", 2000).show();
				}
			}
		});
		
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!nickname.equals("")) {
					if (!user_name.equals("")) {
						Intent Intent2 = new Intent(getActivity(),ApplyBusinessActivity.class);
						startActivity(Intent2);
					} else {
					Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
					startActivity(intent);
					}
				}else {
				if (user_name.equals("")) {
					Intent intentll = new Intent(getActivity(),UserLoginActivity.class);
					startActivity(intentll);
				} else {
				Intent intent = new Intent(getActivity(),ApplyBusinessActivity.class);
				startActivity(intent);
				}
				}
			}
		});
	}
	
	/*
	 * 不可用登录按钮
	 */

	public void getNisabledButton() {
		btn_register.setClickable(false);
		btn_register.setBackgroundResource(R.drawable.bg_ccc_3_5_bg);
	}
	
	/*
	 * 恢复登录按钮
	 */
	public void getShowButton() {
		btn_register.setClickable(true);
		btn_register.setBackgroundResource(R.drawable.btn_red_3_5_bg);
	}
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case 0:
				break;
			case 1:
				break;
			default:
				break;
			}
			super.dispatchMessage(msg);
		}
	};
	 
}
