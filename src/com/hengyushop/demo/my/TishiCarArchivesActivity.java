package com.hengyushop.demo.my;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.activity.BaoMinOKActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.UserRegisterllData;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
/**
 * 余额支付
 * 输入密码
 * @author 
 *
 */
public class TishiCarArchivesActivity extends Activity implements OnClickListener{
	private TextView btnConfirm;//
	private TextView btnCancle,tv_yue;//
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	String user_name, user_id,pwd;
	private EditText zhidupess;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign,amount;
	public static String yue_zhuangtai;
	public static String province,city,area,user_address,accept_name,user_mobile,ct_order_no;
	public static String recharge_no,order_no,datetime,sell_price,give_pension,article_id,update_time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tishi_carxing);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
//		pwd = spPreferences.getString("pwd", "");
		progress = new DialogProgress(TishiCarArchivesActivity.this);
		order_no = getIntent().getStringExtra("order_no");
		System.out.println("order_no-------------"+order_no);
		useryue();
		initUI();
	}


	
	protected void initUI() {
		zhidupess = (EditText) findViewById(R.id.et_user_pwd);
		btnConfirm =(TextView) findViewById(R.id.btnConfirm);//
		btnConfirm.setOnClickListener(this);//
		btnCancle =(TextView) findViewById(R.id.btnCancle);// 
//		tv_yue =(TextView) findViewById(R.id.tv_yue);
//		System.out.println("amount-------------"+amount);
//		tv_yue.setText("你剩余的余额为￥"+amount);
		
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
//			String yue_fanhui = getIntent().getStringExtra("yue");
			finish();
			yue_zhuangtai = "1";
			break;
		case R.id.btnCancle://
			pwd = zhidupess.getText().toString().trim();
			System.out.println("pwd-------------"+pwd);
			if (pwd.equals("")) {
				Toast.makeText(TishiCarArchivesActivity.this, "请输入密码", 200).show();
			}else{
				String yue = getIntent().getStringExtra("yue");
				String jubi = getIntent().getStringExtra("jubi");
				String order_yue = getIntent().getStringExtra("order_yue");
				if (yue != null) {
					userloginqm(); 
				}else if (jubi != null) {
					userloginqm(); 
				}else if (order_yue != null) {
					userloginqm(); 
				}else {
					ShouhuoOK(order_no);
				}
				
			}
			break;
	
		default:
			break;
		}
	}
	
	/**
	 * 判断当前聚币与余额的值
	 * @param order_no 
	 */
	private void useryue() {
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							amount = obj.getString("amount");
							String point = obj.getString("point");
							String jubi = getIntent().getStringExtra("jubi");
							tv_yue =(TextView) findViewById(R.id.tv_yue);
							System.out.println("point-------------"+point);
//							if (!jubi.equals("")) {
							if (jubi != null) {
								if (jubi.equals("1")) {
									tv_yue.setText("你剩余的乐豆为￥"+point);
								}else {
									tv_yue.setText("你剩余的余额为￥"+amount);
								}
							}else {
								String shouhuo = getIntent().getStringExtra("shouhuo");
								System.out.println("shouhuo-------------"+shouhuo);
								if (shouhuo != null) {
									 tv_yue.setText("提示");
								}else {
								tv_yue.setText("你剩余的余额为￥"+amount);
								System.out.println("amount-------------"+amount);
								}
							}
						}else{
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, null);
			
	}
	
	/**
	 * 获取登录签名
	 * @param order_no 
	 */
	private void userloginqm() {
		try{
//			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
//			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							amount = obj.getString("amount");
							login_sign = data.login_sign;
							System.out.println("======login_sign============="+login_sign);
							System.out.println("======order_no============="+order_no);
							loadYue(order_no,login_sign);
						}else{
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, null);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 余额支付
	 * @param login_sign 
	 * @param payment_id 
	 */
	private void loadYue(String order_noll, String login_sign) {
		try {
//			String total_fee = String.valueOf(Double.parseDouble(retailPrice) + Double.parseDouble(String.valueOf(express_fee)));
//		AsyncHttp.get(RealmName.REALM_NAME_LL
//				+ "/payment_balance?user_id="+user_id+"&user_name="+user_name+"" +
//						"&trade_no="+order_noll+"&paypassword="+pwd+"&sign="+login_sign+"",
			AsyncHttp.get("http://mobile.lelinju.shop/api/payment/balance/index.aspx?action=payment&user_id="+user_id+"&user_name="+user_name+"" +
						"&user_sign="+login_sign+"&paypassword="+pwd+"&trade_no="+order_noll+"",			
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("余额支付================================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
							    	progress.CloseProgress();
							    	Toast.makeText(TishiCarArchivesActivity.this, info, 200).show();
//							    	if (condition) {
//										
//									}
//							    	MyOrderConfrimActivity.handlerll.sendEmptyMessage(2);
							    	finish();
//							    	JSONObject jsonObject = object.getJSONObject("data");
//										JSONArray jay = jsonObject.getJSONArray("orders");
//										for (int j = 0; j < jay.length(); j++){
//										   JSONObject objc= jay.getJSONObject(j);
//										   accept_name = objc.getString("accept_name");
//										    province = objc.getString("province");
//											city = objc.getString("city");
//											area = objc.getString("area");
//											user_mobile = objc.getString("mobile");
//											user_address = objc.getString("address");
//											ct_order_no = objc.getString("order_no");
//											datetime = objc.getString("add_time");
//											sell_price = objc.getString("payable_amount");
//											update_time = objc.getString("update_time");
////											ct_order_no = objc.getString("order_no");
////											JSONArray jsonArray = objc.getJSONArray("order_goods");
////											for (int i = 0; i < jsonArray.length(); i++) {
////												JSONObject json = jsonArray.getJSONObject(i);
////												article_id = json.getString("article_id");
//////												sell_price = json.getString("sell_price");
////												give_pension = json.getString("give_pension");
////											}
//										}
							    	
//										System.out.println("user_address================================="+user_address);
//							    	String order_yue = getIntent().getStringExtra("order_yue");
//							    	String yue = getIntent().getStringExtra("yue");
//							    	String jubi = getIntent().getStringExtra("jubi");
//									if (yue != null) {
//										MyOrderConfrimActivity.teby = true;
//										System.out.println("yue-------------"+yue);
//									}
//										else if (order_yue != null) {
//										MyOrderXqActivity.teby = true;
//										System.out.println("order_yue-------------"+order_yue);
//									}
//									else if (jubi != null) {
//										System.out.println("jubi-------------"+jubi);
//									}else {
////										MyOrderActivity.teby = true;
//									}
									
							    	 System.out.println("activity_hd================================="+getIntent().getStringExtra("activity_hd"));
									//活动支付成功不显示详情
//									 if (ZhongAnMinShenXqActivity.huodong_zf_type.equals("1")) {
									  if (getIntent().getStringExtra("activity_hd") != null) {
//										 ZhongAnMinShenXqActivity.huodong_zf_type = "0";
//										 huodong_type = "1";//活动支付成功之后设置不能继续报名
										  Intent intent = new Intent(TishiCarArchivesActivity.this,BaoMinOKActivity.class);
										  intent.putExtra("img_url",getIntent().getStringExtra("img_url"));
										  intent.putExtra("hd_title",getIntent().getStringExtra("hd_title"));
										  intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
										  intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
										  intent.putExtra("address",getIntent().getStringExtra("address"));
										  startActivity(intent);
										  finish();
									}
//									 else {
//									Toast.makeText(TishiCarArchivesActivity.this, info, 200).show();
//									Intent intent = new Intent(TishiCarArchivesActivity.this,ZhiFuOKActivity.class);
//									startActivity(intent);
//							    	finish();
//									}
									
							    }else {
							    	progress.CloseProgress();
									Toast.makeText(TishiCarArchivesActivity.this, info, 200).show();
									finish();
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
	/**
	 * 确认收货
	 * @param order_no 
	 * @param payment_id 
	 */
	public void ShouhuoOK(String order_no) {
			progress.CreateProgress();	
			System.out.println("order_no================================="+order_no);
			System.out.println("login_sign================================="+pwd);
		    AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/update_order_complete?user_id="+user_id+"&user_name="+user_name+"" +
				"&trade_no="+order_no+"&paypassword="+pwd+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("arg1================================"+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
									  progress.CloseProgress();
									  Toast.makeText(TishiCarArchivesActivity.this, info, 200).show();
									  finish();
							    }else {
							    	progress.CloseProgress();
									Toast.makeText(TishiCarArchivesActivity.this, info, 200).show();
								}
							    
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				}, TishiCarArchivesActivity.this);
		
	}
	
}