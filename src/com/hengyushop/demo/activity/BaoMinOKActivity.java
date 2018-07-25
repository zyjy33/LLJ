package com.hengyushop.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;

public class BaoMinOKActivity extends BaseActivity {
	private String yth, key, strUrl;
	private DialogProgress progress;
	Button btn_holdr;
	ImageView img_ware;
//	TextView 
	public static AQuery mAq;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baomin_ok);
		progress = new DialogProgress(BaoMinOKActivity.this);
		mAq = new AQuery(BaoMinOKActivity.this);
//		progress.CreateProgress();
		initdata();
	}
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				break;

			default:
				break;
			}
		};
	};
	private void initdata() {
		TextView tv_ware_name = (TextView) findViewById(R.id.tv_ware_name);
		TextView tv_dizhi = (TextView) findViewById(R.id.tv_dizhi);
		TextView tv_time = (TextView) findViewById(R.id.tv_time);
		tv_ware_name.setText(getIntent().getStringExtra("hd_title"));
		tv_time.setText(getIntent().getStringExtra("start_time")+"--"+getIntent().getStringExtra("end_time"));
		tv_dizhi.setText(getIntent().getStringExtra("address"));
		
		ImageView img_ware = (ImageView) findViewById(R.id.img_ware);
		mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP + getIntent().getStringExtra("img_url"));
		  
		
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		btn_holdr = (Button) findViewById(R.id.btn_holdr);
		btn_holdr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				  Intent intent = new Intent(BaoMinOKActivity.this, DianZiPiaoActivity.class);
				  intent.putExtra("hd_title",getIntent().getStringExtra("hd_title"));
				  intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
				  intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
				  intent.putExtra("address",getIntent().getStringExtra("address"));
				  startActivity(intent);
				  finish();
			}
		});
	}
	




}
