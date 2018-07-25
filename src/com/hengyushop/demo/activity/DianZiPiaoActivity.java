package com.hengyushop.demo.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.hengyu.pub.MySpListAdapter;
import com.android.hengyu.pub.MyjuTouTiaoAdapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.CollectWareData;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

public class DianZiPiaoActivity extends BaseActivity {
	private String yth, key, strUrl;
	private DialogProgress progress;
	Button btn_holdr;
	ImageView img_ware;
	private SharedPreferences spPreferences;
//	TextView 
	public static AQuery mAq;
	String user_name,user_id,real_name;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dianzipiao);
		progress = new DialogProgress(DianZiPiaoActivity.this);
		mAq = new AQuery(DianZiPiaoActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		real_name = spPreferences.getString("real_name", "");
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
		TextView tv_feiyong = (TextView) findViewById(R.id.tv_feiyong);
		TextView tv_name = (TextView) findViewById(R.id.tv_name);
		if (ZhongAnMinShenXqActivity.retailPrice.equals("0.0")) {
			tv_feiyong.setText("费用：￥"+"免费");
		}else {
			tv_feiyong.setText("费用：￥"+ZhongAnMinShenXqActivity.retailPrice);
		}
//		tv_name.setText(BaoMinTiShiActivity.real_name+"("+BaoMinTiShiActivity.user_name+")");
		tv_name.setText(real_name+"("+user_name+")");
		
		TextView tv_ware_name = (TextView) findViewById(R.id.tv_ware_name);
		TextView tv_dizhi = (TextView) findViewById(R.id.tv_dizhi);
		TextView tv_time = (TextView) findViewById(R.id.tv_time);
		
		tv_ware_name.setText(getIntent().getStringExtra("hd_title"));
		tv_time.setText("时间："+getIntent().getStringExtra("start_time")+"--"+getIntent().getStringExtra("end_time"));
		tv_dizhi.setText("地点："+getIntent().getStringExtra("address"));
		
		ImageView img_ware = (ImageView) findViewById(R.id.img_ware);
		img_ware.setBackgroundResource(R.drawable.hd_reweiam);
//		mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP + getIntent().getStringExtra("img_url"));
		
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	




}
