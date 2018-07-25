package com.hengyushop.demo.home;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.Validator;
import com.hengyushop.demo.activity.BaoMinOKActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.entity.UserAddressData;
import com.lelinju.www.WareInformationActivity;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
/**
 * 提示绑定手机号
 * @author 
 *
 */
public class JuTuanXqTiShiActivity extends Activity implements OnClickListener{
	private TextView btnConfirm;//
	private ImageView iv_guanxi,iv_fanhui;
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign,amount;
	public static String yue_zhuangtai,phone,name;
	String user_id,access_token,sex,unionid;
	public static String user_name = "";
	public static String real_name = "";
	public static String hd_user_name = "";
	public static String hd_real_name = "";
	String country = "";
	String nickname = "";
	EditText et_user_name,et_user_shoujihao;
	LinearLayout index_item;
	public static String province,city,area,user_address,user_accept_name,user_mobile,shopping_address_id;
	public static String huodong_zf_type = "0";
	public static ListView listview_01;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jutuan_tishi);
		progress = new DialogProgress(JuTuanXqTiShiActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		real_name = spPreferences.getString("real_name", "");
//		initUI();
	}
	
//	protected void initUI() {
//		listview_01 = (ListView) findViewById(R.id.listview_01);
//		  no_data_no = (LinearLayout) findViewById(R.id.no_data_no);
//		ImageView market_information_sep_ico = (ImageView) findViewById(R.id.market_information_sep_ico);
//		market_information_sep_price = (TextView) findViewById(R.id.market_information_sep_price);
//		TextView market_information_sep_name = (TextView) findViewById(R.id.market_information_sep_name);
//		market_information_pop_shopcart = (Button) findViewById(R.id.market_information_pop_shopcart);
//		market_information_pop_buy = (Button) findViewById(R.id.market_information_pop_buy);
//		if (WareInformationActivity.fangshi == 1) {
//			market_information_pop_shopcart.setText("确认");
//		}
//		// 关于数量
//		TextView market_information_seps_add = (TextView) layout.findViewById(R.id.market_information_seps_add);
//		TextView market_information_seps_del = (TextView) layout.findViewById(R.id.market_information_seps_del);
//		market_information_seps_num = (TextView) layout.findViewById(R.id.market_information_seps_num);
//		
//		market_information_seps_num.setText("1");
//
//		Button market_information_pop_sure = (Button)layout.findViewById(R.id.market_information_pop_sure);
//		
//		ImageLoader imageLoader=ImageLoader.getInstance();
//		imageLoader.displayImage(RealmName.REALM_NAME_HTTP + WareInformationActivity.proFaceImg,market_information_sep_ico);
//		
//		market_information_sep_name.setText(WareInformationActivity.proName);
//		market_information_sep_price.setText("￥" + WareInformationActivity.retailPrice);
//		
//		try {
//		market_information_seps_add.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				int num = Integer.parseInt(market_information_seps_num
//						.getText().toString());
//				market_information_seps_num.setText(String.valueOf(num + 1));
//			}
//		});
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//		//产品减少
//		market_information_seps_del.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				int num = Integer.parseInt(market_information_seps_num
//						.getText().toString());
//				if (num != 1) {
//					market_information_seps_num.setText(String.valueOf(num - 1));
//				} else {
//					Toast.makeText(context, "不能再减了", 200).show();
//				}
//			}
//		});
//
////		handler = new Handler() {
////			public void handleMessage(Message msg) {
////				switch (msg.what) {
////				case 8:
////				}
////			}
////		};
//	}
	
	
	/**
	 * 点击触发事件
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		intent = new Intent();
		switch (v.getId()) {
		case R.id.iv_guanxi://取消
			finish();
			break;
		case R.id.iv_fanhui://返回
			finish();
			break;
	    
		default:
			break;
		}
	}
	
	
	/**
	 * 输出用户默认收货地址
	 */
	private void getuseraddress() {
		user_name = spPreferences.getString("user", "");
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_user_shopping_address_default?user_name=" + user_name
				+ "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					System.out.println("输出用户默认收货地址================" + arg1);
					String status = jsonObject.getString("status");
					if (status.equals("y")) {
						try {
						JSONObject jsot = jsonObject.getJSONObject("data");
						UserAddressData data = new UserAddressData();
						shopping_address_id = jsot.getString("id");
						user_accept_name = jsot.getString("user_accept_name");
						province = jsot.getString("province");
						city = jsot.getString("city");
						area = jsot.getString("area");
						user_mobile = jsot.getString("user_mobile");
						user_address = jsot.getString("user_address");
//						progress.CloseProgress();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
//						progress.CloseProgress();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					progress.CloseProgress();
					e.printStackTrace();
				}
			}

		}, getApplicationContext());
		

	}
	
}