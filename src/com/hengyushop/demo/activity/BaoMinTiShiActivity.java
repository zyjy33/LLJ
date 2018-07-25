package com.hengyushop.demo.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.Validator;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.entity.UserAddressData;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
/**
 * 提示绑定手机号
 * @author 
 *
 */
public class BaoMinTiShiActivity extends Activity implements OnClickListener{
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
	public static  String sp_id,proFaceImg, proInverseImg, proDoDetailImg, proDesignImg,
	proName, proTip, retailPrice, marketPrice, proSupplementImg,goods_price,price,
	proComputerInfo, yth, key, releaseBossUid, AvailableJuHongBao,Atv_integral,company_id,
	productCount,title_ll,spec_ids,article_id,goods_id,subtitle,spec_text,point_id;
	public static String huodong_zf_type = "0";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_baomin_tishi);
		progress = new DialogProgress(BaoMinTiShiActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		real_name = spPreferences.getString("real_name", "");
		initUI();
		getuseraddress();
	}
	
	protected void initUI() {
		et_user_name = (EditText) findViewById(R.id.et_user_name);
		et_user_shoujihao = (EditText) findViewById(R.id.et_user_shoujihao);
		btnConfirm =(TextView) findViewById(R.id.btnConfirm);
		index_item = (LinearLayout) findViewById(R.id.index_item);
		index_item.setOnClickListener(this);
		iv_guanxi =(ImageView) findViewById(R.id.iv_guanxi);
		iv_fanhui =(ImageView) findViewById(R.id.iv_fanhui);
		iv_guanxi.setOnClickListener(this);
		iv_fanhui.setOnClickListener(this);
		if (getIntent().getStringExtra("sell_price").equals("0.0")) {
			btnConfirm.setVisibility(View.GONE);
		}else {
			btnConfirm.setText("合计：￥"+getIntent().getStringExtra("sell_price"));
		}
		et_user_name.setText(real_name);
		et_user_shoujihao.setText(user_name);
		
//		handler = new Handler() {
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//				case 8:
//					
//				}
//			}
//		};
	}
	
	
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
		case R.id.index_item://立即提交
			if (real_name.equals("")) {
				real_name = et_user_name.getText().toString().trim();
			}else {
				real_name = et_user_name.getText().toString().trim();
			}
			
			if (user_name.equals("")) {
				user_name = et_user_shoujihao.getText().toString().trim();
			}else {
				user_name = et_user_shoujihao.getText().toString().trim();
			}
			
			System.out.println("real_name----------------"+real_name);
			System.out.println("user_name----------------"+user_name);
			if (real_name.equals("")) {
				Toast.makeText(BaoMinTiShiActivity.this, "请输入姓名", 200).show();
			}else if (user_name.equals("")) {
				Toast.makeText(BaoMinTiShiActivity.this, "请输入手机号码", 200).show();
			}
		    else {
				if(Validator.isMobile(user_name)){
					try {
//						if (getIntent().getStringExtra("total_c").equals("0.0")) {
//				    		  Intent intent = new Intent(BaoMinTiShiActivity.this,BaoMinOKActivity.class);
//							  intent.putExtra("img_url",getIntent().getStringExtra("img_url"));
//							  intent.putExtra("hd_title",getIntent().getStringExtra("title"));
//							  intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
//							  intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
//							  intent.putExtra("address",getIntent().getStringExtra("address"));
//							  startActivity(intent);
//							  finish();
//						}else {
//						  Intent intent = new Intent(BaoMinTiShiActivity.this, MyOrderZFActivity.class);
//						  intent.putExtra("order_no",getIntent().getStringExtra("order_no"));
//						  intent.putExtra("total_c",getIntent().getStringExtra("total_c"));
//						  intent.putExtra("img_url",getIntent().getStringExtra("img_url"));
//						  intent.putExtra("title",getIntent().getStringExtra("title"));
//						  intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
//						  intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
//						  intent.putExtra("address",getIntent().getStringExtra("address"));
//						  startActivity(intent);
//						  finish();
//						}
//						  if (user_accept_name == null) {
//								 Toast.makeText(BaoMinTiShiActivity.this, "未添加收货地址", 200).show();
//							  }else {
						        loadusertijiao(getIntent().getStringExtra("id"));
//							  }
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
				}else {
					Toast.makeText(BaoMinTiShiActivity.this, "手机号码不正确", 200).show();
				}
			}
			
			break;
	    
		default:
			break;
		}
	}
	
	/**
	 * 提交用户订单  
	 * @param payment_id 
	 * @param kou_hongbao 
	 */
	private void loadusertijiao(String shopping_ids) {
		try {
			progress.CreateProgress();	
			String zhi = "空";
		 	String shopping_address_id = getIntent().getStringExtra("sp_id");
			System.out.println("shopping_ids=====================" + shopping_ids);//order_activity_signup  order_save
			String url = RealmName.REALM_NAME_LL+ "/order_activity_signup?user_id="+user_id+"&user_name="+user_name+"&is_cashing_packet=0" +
			"&is_cashing_point=0&shopping_ids="+shopping_ids+"&payment_id=2&shopping_address_id="+shopping_address_id+"" +
			"&express_id=3&is_invoice=0&accept_name="+zhi+"&province="+zhi+"&city="+zhi+"&area="+zhi+"" +
			"&address="+zhi+"&telphone=&mobile="+user_name+"&email=&post_code=&message=&invoice_title=";
			
		AsyncHttp.get(url,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("提交用户订单 ================================="+arg1);
							try {
								
							    String code = object.getString("code");
							    String info = object.getString("info");
							    if (code.equals("yes")) {
							    	progress.CloseProgress();
							    	JSONObject jsonObject = object.getJSONObject("data");
							    	String trade_no = jsonObject.getString("trade_no");
							    	String total_amount = jsonObject.getString("total_amount");
//							    	order_no = jsonObject.getString("order_no");
							    	
									if (total_amount.equals("0.0")) {
							    		  Intent intent = new Intent(BaoMinTiShiActivity.this,BaoMinOKActivity.class);
							    		  intent.putExtra("order_no",trade_no);
										  intent.putExtra("total_c",total_amount);
										  intent.putExtra("img_url",getIntent().getStringExtra("img_url"));
										  intent.putExtra("hd_title",getIntent().getStringExtra("title"));
										  intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
										  intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
										  intent.putExtra("address",getIntent().getStringExtra("address"));
										  startActivity(intent);
										  finish();
									}else {
										huodong_zf_type = "1";// 活动支付成功不显示详情
									  Intent intent = new Intent(BaoMinTiShiActivity.this, MyOrderZFActivity.class);
									  intent.putExtra("order_no",trade_no);
									  intent.putExtra("total_c",total_amount);
									  intent.putExtra("img_url",getIntent().getStringExtra("img_url"));
									  intent.putExtra("title",getIntent().getStringExtra("title"));
									  intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
									  intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
									  intent.putExtra("address",getIntent().getStringExtra("address"));
									  startActivity(intent);
									  finish();
									}
							    }else {
							    	progress.CloseProgress();
							    	finish();
									Toast.makeText(BaoMinTiShiActivity.this, info, 200).show();
								}
							    
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					

				}, getApplicationContext());
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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