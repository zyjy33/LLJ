package com.hengyushop.demo.my;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.ZhiFuFangShiActivity;
import com.hengyushop.entity.JuTuanGouData;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;
/**
 * 创客
 * @author Administrator
 *
 */
public class JuFaFaUserActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhui,img_menu,im_je1,im_je2,im_je3,im_je4,im_je5,im_je6,im_je7,im_je8,im_je9,im_je10
	,im_weizhi1,im_weizhi2,im_weizhi3,im_weizhi4,
	im_weizhi5,im_weizhi6,im_weizhi7,im_weizhi8,im_weizhi9,im_weizhi10;
	private Button fanhui,btn_chongzhi;
	private LinearLayout adv_pager,adv_pager1,index_item0,index_item1,index_item2,index_item3,ll_dengji2;
	private EditText et_chongzhi;
	private SharedPreferences spPreferences;
	String user_name, user_id;
	private TextView tv_monney1,tv_monney2,tv_monney3,tv_monney4,tv_monney5,tv_monney6,tv_monney7,
	tv_monney8,tv_monney9,tv_monney10,tv_dengji,tv_dengji2,tv_shouyi1,tv_shouyi2,tv_shouyi3;
	int reserves,reserveb;
	String reserves1;
	String expenses_id = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chuangke_title_ok);
		try {
		Button enter_shop = (Button) findViewById(R.id.enter_shop);
//		enter_shop.getBackground().setAlpha(50);
		enter_shop.setOnClickListener(this);
		System.out.println("值是1================");
		 getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			user_name = spPreferences.getString("user", "");
			user_id = spPreferences.getString("user_id", "");
			
		Initialize();
		userxinxi();
		getmonney1();
		loadguanggao();
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//	}
	
	/**
	 * 备货金余额
	 */
	public void userxinxi(){
		try{
			
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======66输出用户资料============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							UserRegisterllData data = new UserRegisterllData();
							data.reserves = obj.getInt("reserves");
							data.reserveb = obj.getInt("reserveb");
							reserves = data.reserves;
							reserveb = data.reserveb;
							TextView tv_beihuojin = (TextView) findViewById(R.id.tv_beihuojin);
//							DecimalFormat decimalFormat = new DecimalFormat();
//							reserves1 = decimalFormat.format(reserves);
							System.out.println("reserves----------"+reserves);
							String monney = String.valueOf(reserveb);
//							System.out.println("reserveb----------"+reserveb);
							tv_beihuojin.setText(monney+"元");
							
							panduan();
						}else{
							
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, JuFaFaUserActivity.this);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * @param expenses_id 
	 * 
	 */
	public void getmonney1(){
			
			String strUrlone = RealmName.REALM_NAME_LL + "/get_payrecord_expenses_sum?" +
					"to_user_id="+user_id+"&fund_id=1&expenses_id=6";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======11============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							String pensions = obj.getString("pensions");
							tv_shouyi1.setText(pensions+"元");
							getmonney2();
						}else{
							
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, JuFaFaUserActivity.this);
			
	}
	
	/**
	 * @param expenses_id 
	 * 
	 */
	public void getmonney2(){
			
			String strUrlone = RealmName.REALM_NAME_LL + "/get_payrecord_expenses_sum?" +
					"to_user_id="+user_id+"&fund_id=1&expenses_id=10";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======22============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							String pensions = obj.getString("pensions");
							tv_shouyi2.setText(pensions+"元");
							getmonney3();
						}else{
							
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, JuFaFaUserActivity.this);
			
	}
	
	/**
	 * @param expenses_id 
	 * 
	 */
	public void getmonney3(){
			
			String strUrlone = RealmName.REALM_NAME_LL + "/get_payrecord_expenses_sum?" +
					"to_user_id="+user_id+"&fund_id=1&expenses_id=5";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======33============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							String pensions = obj.getString("pensions");
							tv_shouyi3.setText(pensions+"元");
						}else{
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, JuFaFaUserActivity.this);
			
	}
	
	/**
	 * 控件初始化
	 */
	private void Initialize() {
		
		try {
		index_item0 = (LinearLayout) findViewById(R.id.index_item0);
//		index_item1 = (LinearLayout) findViewById(R.id.index_item1);
//		index_item2 = (LinearLayout) findViewById(R.id.index_item2);
//		index_item3 = (LinearLayout) findViewById(R.id.index_item3);
//		ll_dengji2 = (LinearLayout) findViewById(R.id.ll_dengji2);
		img_menu = (ImageView) findViewById(R.id.img_menu);
		et_chongzhi = (EditText) findViewById(R.id.et_chongzhi);
		fanhui = (Button) findViewById(R.id.fanhui);
		btn_chongzhi = (Button) findViewById(R.id.btn_chongzhi);
		fanhui.setOnClickListener(this);
		btn_chongzhi.setOnClickListener(this);
		index_item0.setOnClickListener(this);
//		index_item1.setOnClickListener(this);
//		index_item2.setOnClickListener(this);
//		index_item3.setOnClickListener(this);
		tv_shouyi1 = (TextView) findViewById(R.id.tv_shouyi1);
		tv_shouyi2 = (TextView) findViewById(R.id.tv_shouyi2);
		tv_shouyi3 = (TextView) findViewById(R.id.tv_shouyi3);
		
		btn_chongzhi = (Button) findViewById(R.id.btn_chongzhi);
		et_chongzhi = (EditText) findViewById(R.id.et_chongzhi);
		tv_monney1 = (TextView) findViewById(R.id.tv_monney1);
		tv_monney2 = (TextView) findViewById(R.id.tv_monney2);
		tv_monney3 = (TextView) findViewById(R.id.tv_monney3);
		tv_monney4 = (TextView) findViewById(R.id.tv_monney4);
		tv_monney5 = (TextView) findViewById(R.id.tv_monney5);
		tv_monney6 = (TextView) findViewById(R.id.tv_monney6);
		tv_monney7 = (TextView) findViewById(R.id.tv_monney7);
		tv_monney8 = (TextView) findViewById(R.id.tv_monney8);
		tv_monney9 = (TextView) findViewById(R.id.tv_monney9);
		tv_monney10 = (TextView) findViewById(R.id.tv_monney10);
		im_weizhi1 = (ImageView) findViewById(R.id.im_weizhi1);
		im_weizhi2 = (ImageView) findViewById(R.id.im_weizhi2);
		im_weizhi3 = (ImageView) findViewById(R.id.im_weizhi3);
		im_weizhi4 = (ImageView) findViewById(R.id.im_weizhi4);
		im_weizhi5 = (ImageView) findViewById(R.id.im_weizhi5);
		im_weizhi6 = (ImageView) findViewById(R.id.im_weizhi6);
		im_weizhi7 = (ImageView) findViewById(R.id.im_weizhi7);
		im_weizhi8 = (ImageView) findViewById(R.id.im_weizhi8);
		im_weizhi9 = (ImageView) findViewById(R.id.im_weizhi9);
		im_weizhi10 = (ImageView) findViewById(R.id.im_weizhi10);
		im_je1 = (ImageView) findViewById(R.id.im_je1);
		im_je2 = (ImageView) findViewById(R.id.im_je2);
		im_je3 = (ImageView) findViewById(R.id.im_je3);
		im_je4 = (ImageView) findViewById(R.id.im_je4);
		im_je5 = (ImageView) findViewById(R.id.im_je5);
		im_je6 = (ImageView) findViewById(R.id.im_je6);
		im_je7 = (ImageView) findViewById(R.id.im_je7);
		im_je8 = (ImageView) findViewById(R.id.im_je8);
		im_je9 = (ImageView) findViewById(R.id.im_je9);
		im_je10 = (ImageView) findViewById(R.id.im_je10);
		tv_dengji = (TextView) findViewById(R.id.tv_dengji);
		tv_dengji2 = (TextView) findViewById(R.id.tv_dengji2);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void panduan() {
		// TODO Auto-generated method stub
		try {
//		int yue = Integer.parseInt(reserves1);
 		System.out.println("----------"+reserves);
		if (reserves < 100) {
			int zdj_monney = 100;
			int monney = zdj_monney - reserves;
			im_je1.setVisibility(View.VISIBLE);
			tv_monney1.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi1.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserveb+"元");
			tv_dengji2.setText(monney+"元");
			
		}else 
		if (reserves < 500){
			int zdj_monney = 500;
			int monney = zdj_monney - reserves;
			im_je2.setVisibility(View.VISIBLE);
			tv_monney2.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi2.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
//			tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserves_ll+"元");
			tv_dengji2.setText(monney+"元");
		}else 
		if (reserves < 3000){
			int zdj_monney = 3000;
			int monney = zdj_monney - reserves;
			im_je3.setVisibility(View.VISIBLE);
			tv_monney3.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi3.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
//			tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserves_ll+"元");
			tv_dengji2.setText(monney+"元");
		}else 
		if (reserves < 10000){
			int zdj_monney = 10000;
			int monney = zdj_monney - reserves;
			im_je4.setVisibility(View.VISIBLE);
			tv_monney4.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi4.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
//			tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserves_ll+"元");
			tv_dengji2.setText(monney+"元");
		}else 
		if (reserves < 20000){
			int zdj_monney = 20000;
			int monney = zdj_monney - reserves;
			im_je5.setVisibility(View.VISIBLE);
			tv_monney5.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi5.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
//			tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserves_ll+"元");
			tv_dengji2.setText(monney+"元");
		}else 
		if (reserves < 30000){
			int zdj_monney = 30000;
			int monney = zdj_monney - reserves;
			im_je6.setVisibility(View.VISIBLE);
			tv_monney6.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi6.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
//			tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserves_ll+"元");
			tv_dengji2.setText(monney+"元");
		}else 
		if (reserves < 40000){
			int zdj_monney = 40000;
			int monney = zdj_monney - reserves;
			im_je7.setVisibility(View.VISIBLE);
			tv_monney7.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi7.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
//			tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserves_ll+"元");
			tv_dengji2.setText(monney+"元");
		}else 
		if (reserves < 50000){
			int zdj_monney = 50000;
			int monney = zdj_monney - reserves;
			im_je8.setVisibility(View.VISIBLE);
			tv_monney8.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi8.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
//			tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserves_ll+"元");
			tv_dengji2.setText(monney+"元");
		}else 
		if (reserves < 60000){
			int zdj_monney = 60000;
			int monney = zdj_monney - reserves;
			im_je9.setVisibility(View.VISIBLE);
			tv_monney9.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi9.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
//			tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserves_ll+"元");
			tv_dengji2.setText(monney+"元");
		}else 
		if (reserves < 100000){
			int zdj_monney = 100000;
			int monney = zdj_monney - reserves;
			im_je10.setVisibility(View.VISIBLE);
			tv_monney10.setTextColor(Color.RED);
//			tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
			im_weizhi10.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
//			tv_dengji.setText("距离下一等级商，还需"+monney+"元，请输入充值金额");
			String reserves_ll = String.valueOf(reserves);
			tv_dengji.setText(reserves_ll+"元");
			tv_dengji2.setText(monney+"元");
//			Toast.makeText(JuFaFaUserActivity.this, "", 200).show();
		}else 
			if (reserves > 100000){
				im_je10.setVisibility(View.VISIBLE);
				tv_monney10.setTextColor(Color.RED);
//				tv_monney1.setBackgroundColor(getResources().getColor(R.color.hongse));
				im_weizhi10.setBackgroundResource(R.drawable.bg_red_3_5_jiajian_hs);
				String reserves_ll = String.valueOf(reserves);
				tv_dengji.setText(reserves_ll+"元");
				ll_dengji2.setVisibility(View.GONE);
			}
//		else {
//			im_je10.setVisibility(View.INVISIBLE);
//			tv_monney10.setBackgroundColor(getResources().getColor(R.color.ziti_huise));
//			im_weizhi10.setBackgroundResource(R.drawable.bg_red_3_5_jiajian);
//		}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fanhui:
			finish();
			break;
//		case R.id.btn_login:
//			Intent intent = new Intent(ChuangKeUserActivity.this,ShengJiCkActivity.class);
////			Intent intent = new Intent(ChuangKeUserActivity.this,ChongZhiActivity.class);
//			startActivity(intent);
//			break;
		case R.id.btn_chongzhi:
			try {
				
//			Intent intent = new Intent(ChuangKeActivity.this,ShengJiCkActivity.class);
			String chongzhi = et_chongzhi.getText().toString().trim();
			if (chongzhi.equals("")) {
				Toast.makeText(JuFaFaUserActivity.this, "充值不能为空", 200).show();
			} else {
				int cz = Integer.parseInt(chongzhi);
//				int cz_monney = reserves+cz;
				if (reserves > 100000) {
					Toast.makeText(JuFaFaUserActivity.this, "备货金大于10万以上,不能再充值了", 200).show();
				} else if (cz < 100) {
					Toast.makeText(JuFaFaUserActivity.this, "充值不能小于100", 200).show();
				} else {
				Intent intent1 = new Intent(JuFaFaUserActivity.this,UserChongZhiActivity.class);
				intent1.putExtra("chongzhi", chongzhi);
				startActivity(intent1);
				}
			}
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.enter_shop:
			Intent intent1 = new Intent(JuFaFaUserActivity.this,Webview1.class);
			intent1.putExtra("jjf_id", "6057");
			startActivity(intent1);
			
			break;
		case R.id.index_item0:
			Intent intent2 = new Intent(JuFaFaUserActivity.this,BeiHuoJinMxActivity.class);
			intent2.putExtra("5", "5");
			startActivity(intent2);
			
			break;
		case R.id.index_item1:
			expenses_id = "6";
//			getmonney(expenses_id);
			break;
		case R.id.index_item2:
			expenses_id = "10";
//			getmonney(expenses_id);
			break;
		case R.id.index_item3:
			expenses_id = "5";
//			getmonney(expenses_id);
			break;

		default:
			break;
		}
	}
	
	/**
	 * 获取广告图片
	 */
	private void loadguanggao() {
		try {
			
		//广告滚动	
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_adbanner_list?advert_id=13",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							JSONArray array = object.getJSONArray("data");
							int len = array.length();
							ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
							for (int i = 0; i < len; i++) {
								AdvertDao1 ada = new AdvertDao1();
								JSONObject json = array.getJSONObject(i);
								ada.setId(json.getString("id"));
								ada.setAd_url(json.getString("ad_url"));
								String ad_url = ada.getAd_url();
								System.out.println("图片值是================"+ad_url);
								
							    ImageLoader imageLoader=ImageLoader.getInstance();
							    imageLoader.displayImage(RealmName.REALM_NAME_HTTP + ad_url, img_menu);
								images.add(ada);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, null);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
