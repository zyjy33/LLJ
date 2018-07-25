package com.hengyushop.demo.my;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.android.pliay.SignUtils;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.SharedUtils;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.UserRegisterllData;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
/**
 * 
 * @author Administrator
 *
 */
public class UserChongZhiActivity extends BaseActivity implements OnClickListener{
	private Button chongzhi_submit;
	private EditText chongzhi_edit;
	private TextView yfje_edit;
	private LinearLayout yu_pay0,yu_pay1,yu_pay2;
	private CheckBox yu_pay_c0,yu_pay_c1,yu_pay_c2;
	private IWXAPI api;
	private SharedUtils in ;
	private WareDao wareDao;
	private String yth;
	private String key;
	private SharedPreferences spPreferences;
	String user_name,user_id;
	String payment_id;
//	String action = "5";
	public static String recharge_no,notify_url;
//	String orderSerialNumber;
	private ImageView iv_fanhui;
	private String partner_id,prepayid,noncestr,timestamp,package_,sign;
	private DialogProgress progress;
	String login_sign,pety,pwd;
	int zhifu;
	boolean zhuangtai = false;
	private TextView tv_title1,tv_title2;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("11==============" + zhuangtai);
	}
	
	/**
	 * ��ȡ��¼ǩ��
	 */
	private void userloginqm() {
		try{
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							login_sign = data.login_sign;
							loadguanggaoll(recharge_no,login_sign);
						}else{
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, UserChongZhiActivity.this);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shengji_chongzhi);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		api = WXAPIFactory.createWXAPI(UserChongZhiActivity.this,null);
		api.registerApp(Constants.APP_ID);
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		progress = new DialogProgress(this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		pwd = spPreferences.getString("pwd", "");
		
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(this);
		
//		loadguanggao();
//		tv_title1 = (TextView) findViewById(R.id.tv_title1);
//		tv_title2 = (TextView) findViewById(R.id.tv_title2);
		tv_title1.setVisibility(View.GONE);
		tv_title2.setVisibility(View.VISIBLE);
		in = new SharedUtils(getApplicationContext(), "shouyi");
		chongzhi_edit = (EditText) findViewById(R.id.chongzhi_edit);
		yfje_edit = (TextView) findViewById(R.id.yfje_edit);
		chongzhi_submit = (Button) findViewById(R.id.chongzhi_submit);
		yu_pay0 =  (LinearLayout) findViewById(R.id.yu_pay0);
		yu_pay1 = (LinearLayout) findViewById(R.id.yu_pay1);
		yu_pay2 = (LinearLayout) findViewById(R.id.yu_pay2);
		yu_pay_c0 = (CheckBox) findViewById(R.id.yu_pay_c0);
		yu_pay_c1 = (CheckBox) findViewById(R.id.yu_pay_c1);
		yu_pay_c2 = (CheckBox) findViewById(R.id.yu_pay_c2);
		chongzhi_edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		
		String chongzhi = getIntent().getStringExtra("chongzhi");
		if (chongzhi != null) {
			chongzhi_edit.setText(chongzhi);
//			chongzhi_edit.getText().toString();
			yfje_edit.setText(chongzhi);
			
		}
		
        Resources res = this.getResources();
        chongzhi_edit.setOnKeyListener(new EditText.OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				yfje_edit.setText(chongzhi_edit.getText());
				return false;
			}
   
        });
        
		/**
		 * ΢��֧��
		 */
		yu_pay0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c1.isChecked()){
					yu_pay_c1.setChecked(false);
					yu_pay_c2.setChecked(false);
				}
				yu_pay_c0.setChecked(true);
			}
		});
		yu_pay_c0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c1.isChecked()){
					//��������Ƿ�Ϊ���״̬
					yu_pay_c1.setChecked(false);
					yu_pay_c2.setChecked(false);
				}
				yu_pay_c0.setChecked(true);
			}
		});
		/**
		 * ֧����֧��
		 */
		yu_pay_c1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c0.isChecked()){
					yu_pay_c0.setChecked(false);
					yu_pay_c2.setChecked(false);
				}
				yu_pay_c1.setChecked(true);
			}
		});
		yu_pay1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c0.isChecked()){
					yu_pay_c0.setChecked(false);
					yu_pay_c2.setChecked(false);
				}
				yu_pay_c1.setChecked(true);
			}
		});
		/**
		 * ���֧��
		 */
	    yu_pay_c2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c0.isChecked()){
					yu_pay_c0.setChecked(false);
					yu_pay_c1.setChecked(false);
				}
				yu_pay_c2.setChecked(true);
			}
		});
		yu_pay2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c0.isChecked()){
					yu_pay_c0.setChecked(false);
					yu_pay_c1.setChecked(false);
				}
				yu_pay_c2.setChecked(true);
			}
		});
		
		chongzhi_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String yue = chongzhi_edit.getText().toString();
				try {
					Double.parseDouble(yue);
					double monney = Double.parseDouble(yue);
					if (monney >= 100) {
					
						
					if(yu_pay_c0.isChecked()){//΢��֧��
						
//						processWX(yue);
						payment_id = "5";
						System.out.println("payment_id=============="+payment_id);
						loadweixinzf1(payment_id);
						
					}else if (yu_pay_c1.isChecked()) {//֧����΢��
						
//						process(yue);
						payment_id = "3";
						loadguanggao(payment_id);
					}else if (yu_pay_c2.isChecked()) {
						
//						process(yue);
						payment_id = "2";
						loadguanggao(payment_id);
//						Toast.makeText(getApplicationContext(), "��ʱ�޷�֧��",200).show();
					}else{
						Toast.makeText(getApplicationContext(), "��ѡ��֧����ʽ",100).show();
					}
					
                    }else {
					Toast.makeText(getApplicationContext(), "��������ȷ�Ľ��,����С��100", 200).show();	
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "��������", 200).show();
				}
				
				
			}
		});
	}
	
//	publicvoidonResp(BaseRespresp){
//		if(resp.getType()==ConstantsAPI.COMMAND_PAY_BY_WX){
//		Log.d(TAG,"onPayFinish,errCode="+resp.errCode);
//		AlertDialog.Builderbuilder=newAlertDialog.Builder(this);
//		builder.setTitle(R.string.app_tip);
//		}
//		}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.iv_fanhui:
			finish();
			break;
		case R.id.tv_xiabu:
//			finish();
//			Intent intent = new Intent(ShengJiCkActivity.this,ApplyBusiness2Activity.class);
//			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	/**
	 * ���ɶ���   ֧����1  
	 * @param payment_id 
	 */
	private void loadguanggao(String payment_id) {
		try {
			progress.CreateProgress();	
		String amount = chongzhi_edit.getText().toString();
//		String amount = "200";
        System.out.println("==============="+amount);
        yfje_edit.setText(amount);
        pety = payment_id;
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/add_amount_recharge?user_id="+user_id+"&user_name="+user_name+"" +
						"&amount="+amount+"&fund_id=5&payment_id="+payment_id+"&rebate_item_id=0",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("1================================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
							    	JSONObject obj = object.getJSONObject("data");
									AdvertDao1 data = new AdvertDao1();
									data.recharge_no = obj.getString("recharge_no");
									recharge_no = data.recharge_no;
									System.out.println("11================================="+data.recharge_no );
									  progress.CloseProgress();
//										loadguanggaoll(recharge_no);
									  if (pety.equals("2")) {
//										  loadYue(recharge_no);
											Intent intent = new Intent(UserChongZhiActivity.this, TishiCarArchivesActivity.class);
											intent.putExtra("order_no",recharge_no);
											intent.putExtra("yue","yue");
											startActivity(intent);
									  }else if (pety.equals("3")){
										  loadzhidu(recharge_no);
									  }
							    }else {
							    	progress.CloseProgress();
									Toast.makeText(UserChongZhiActivity.this, info, 200).show();
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
	 * �û����߳�ֵ  ���֧��
	 * @param payment_id 
	 */
//	private void loadYue(String recharge_no) {
//		try {
//			
//      String amount = chongzhi_edit.getText().toString().trim();
////		String amount = "0.01";
//        
//		AsyncHttp.get(RealmName.REALM_NAME_LL
//				+ "/payment_balance?user_id="+user_id+"&user_name="+user_name+"" +
//						"&order_no="+recharge_no+"&paypassword="+pwd+"",
//						
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int arg0, String arg1) {
//						super.onSuccess(arg0, arg1);
//						try {
//							JSONObject object = new JSONObject(arg1);
//							System.out.println("2================================="+arg1);
//							  String status = object.getString("status");
//							    String info = object.getString("info");
//							    if (status.equals("y")) {
//							    	progress.CloseProgress();
//							    	userloginqm();
//							    }else {
//							    	progress.CloseProgress();
//									Toast.makeText(UserChongZhiActivity.this, info, 200).show();
//								}
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//
//				}, null);
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * ���±�����
	 * @param login_sign 
	 * @param payment_id 
	 */
	private void loadguanggaoll(String recharge_noll, String login_sign) {
		try {
			recharge_no = recharge_noll;
			System.out.println("recharge_no================================="+recharge_noll);
//			login_sign = spPreferences.getString("login_sign", "");
			System.out.println("login_sign================================="+login_sign);

		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/update_amount_reserve?user_id="+user_id+"&user_name="+user_name+"" +
						"&recharge_no="+recharge_noll+"&sign="+login_sign+"",
						
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("1================================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
							    	   progress.CloseProgress();
//										loadzhidu(recharge_no);
//							    	   Toast.makeText(ChongZhiActivity.this, info, 200).show();
							    }else {
							    	progress.CloseProgress();
//									Toast.makeText(ChongZhiActivity.this, info, 200).show();
								}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						System.out.println("11================================="+arg0);
						System.out.println("22================================="+arg1);
						Toast.makeText(UserChongZhiActivity.this, "���糬ʱ�쳣", 200).show();
					}

				}, null);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * �û����߳�ֵ  ֧����3
	 * @param payment_id 
	 */
	private void loadzhidu(String recharge_no) {
		try {
			
      String amount = chongzhi_edit.getText().toString().trim();
//		String amount = "0.01";
        
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/payment_sign?user_id="+user_id+"&user_name="+user_name+"" +
						"&total_fee="+amount+"&out_trade_no="+recharge_no+"&payment_type=alipay",
						
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("2================================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
							    	JSONObject obj = object.getJSONObject("data");
							    	notify_url = obj.getString("notify_url"); 
							    	progress.CloseProgress();
									handler.sendEmptyMessage(1);
									zhifu = 3;
									zhuangtai = true;
							    }else {
							    	progress.CloseProgress();
									Toast.makeText(UserChongZhiActivity.this, info, 200).show();
								}
//							String info = object.getString("info");
//							Toast.makeText(getApplicationContext(), info, 200).show();
//							System.out.println("==========================11=="+recharge_no);
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
	 * ���ɶ���    ΢��֧��1  
	 * @param payment_id 
	 */
	private void loadweixinzf1(String payment_id) {
		try {
			progress.CreateProgress();	
		String amount = chongzhi_edit.getText().toString();
        System.out.println("0==============="+amount);
        yfje_edit.setText(amount);
        
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/add_amount_recharge?user_id="+user_id+"&user_name="+user_name+"" +
						"&amount="+amount+"&fund_id=5&payment_id="+payment_id+"&rebate_item_id=0",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("0================================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
							    	JSONObject obj = object.getJSONObject("data");
									AdvertDao1 data = new AdvertDao1();
									data.recharge_no = obj.getString("recharge_no");
									recharge_no = data.recharge_no;
									System.out.println("0================================="+data.recharge_no );
									  progress.CloseProgress();
//										loadweixinzf2(recharge_no);
										loadweixinzf3(recharge_no);
							    }else {
							    	progress.CloseProgress();
									Toast.makeText(UserChongZhiActivity.this, info, 200).show();
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
	 * �û����߳�ֵ    ΢��֧��3
	 * @param payment_id 
	 */
	private void loadweixinzf3(String recharge_no) {
		try {
        String amount = chongzhi_edit.getText().toString().trim();
//        String monney = String.valueOf(Integer.parseInt(amount)*100);
        String monney = String.valueOf(Double.parseDouble(amount) *100);
        
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/payment_sign?user_id="+user_id+"&user_name="+user_name+"" +
						"&total_fee="+monney+"&out_trade_no="+recharge_no+"&payment_type=weixin",
						
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							
							JSONObject object = new JSONObject(arg1);
							System.out.println("weixin================================="+arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if(status.equals("y")){
								  JSONObject jsonObject = object.getJSONObject("data");
								  partner_id = jsonObject.getString("mch_id");
								  prepayid = jsonObject.getString("prepay_id"); 
								  noncestr= jsonObject.getString("nonce_str");
								  timestamp = jsonObject.getString("timestamp");
								  
								  package_="Sign=WXPay";
								  sign= jsonObject.getString("sign");
								  System.out.println("weixin================================="+package_);
									progress.CloseProgress();
									handler.sendEmptyMessage(2);
									zhifu = 5;
									zhuangtai = true;
							}else {
						    	progress.CloseProgress();
								Toast.makeText(UserChongZhiActivity.this, info, 200).show();
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
	
	  
		// ��ʱ���תΪ�ַ��� 
//		String re_StrTime = null; 
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
//		// ���磺cc_time=1291778220 
//		long lcc_time = Long.valueOf(timestamp1); 
//		timestamp = sdf.format(new Date(lcc_time * 1000L)); 
//		System.out.println("===============111--"+timestamp);
	  
	/**
	 * sign the order info. �Զ�����Ϣ����ǩ��
	 * 
	 * @param content
	 *            ��ǩ��������Ϣ
	 */
	public String sign(String content) {
		return SignUtils.sign(content, Common.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. ��ȡǩ����ʽ
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * create the order info. ����������Ϣ
	 * 
	 */
	public String getOrderInfo(String subject, String body, String dingdan) {
		// ǩԼ����������ID
		String orderInfo = "partner=" + "\"" + Common.PARTNER + "\"";

		// ǩԼ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + Common.SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + dingdan + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + chongzhi_edit.getText().toString() + "\"";
		
		// �������첽֪ͨҳ��·��
		orderInfo += "&notify_url=" + "\"" +  notify_url + "\"";
		
		// ����ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
		// orderInfo += "&paymethod=\"expressGateway\"";
		System.out.println(orderInfo);
		return orderInfo;
	}
	private void ali_pay() {
		//
		String orderInfo = getOrderInfo("��ֵ������", "��Ʒ����", recharge_no);

		// �Զ�����RSA ǩ��
		String sign = sign(orderInfo);
		try {
			// �����sign ��URL����
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// �����ķ���֧���������淶�Ķ�����Ϣ
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// ����PayTask ����
				PayTask alipay = new PayTask(UserChongZhiActivity.this);
				// ����֧���ӿڣ���ȡ֧�����
				String result = alipay.pay(payInfo);
				Message msg = new Message();
				msg.what = 5;
				msg.obj = result;
				handler.sendMessage(msg);
			}
		};

		// �����첽����
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 1:
				ali_pay();
				break;
			case 2://΢��֧��
				boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
				String zhou = String.valueOf(isPaySupported);
//				Toast.makeText(ChongZhiActivity.this, zhou, Toast.LENGTH_SHORT).show();
				if(isPaySupported){ 
					try {
						PayReq req = new PayReq();
						req.appId			= Constants.APP_ID;
						req.partnerId		= Constants.MCH_ID;
						req.prepayId		= prepayid;//7
						req.nonceStr		= noncestr;//3
						req.timeStamp		= timestamp;//-1
						req.packageValue	= package_;
						req.sign			= sign;//-3
						// ��֧��֮ǰ�����Ӧ��û��ע�ᵽ΢�ţ�Ӧ���ȵ���IWXMsg.registerApp��Ӧ��ע�ᵽ΢��
						api.registerApp(Constants.APP_ID);
						boolean flag = api.sendReq(req);
						System.out.println("֧��"+flag);
						
//						Toast.makeText(ChongZhiActivity.this, "֧��OK", Toast.LENGTH_SHORT).show();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}else {
//					Toast.makeText(ChongZhiActivity.this, "֧��NO", Toast.LENGTH_SHORT).show();
				}
				
				
				break;
			case 5://֧����
				PayResult payResult = new PayResult((String) msg.obj);

				// ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();
				System.out.println(resultInfo + "---" + resultStatus);
				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬���������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(UserChongZhiActivity.this, "֧���ɹ�",Toast.LENGTH_SHORT).show();
					userloginqm();
				} else {
					// �ж�resultStatus Ϊ�ǡ�9000�����������֧��ʧ��
					// ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(UserChongZhiActivity.this, "֧�����ȷ����",
								Toast.LENGTH_SHORT).show();

					} else {
						// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
						Toast.makeText(UserChongZhiActivity.this, "֧��ʧ��",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;

			default:
				break;
			}
		};
	};
}