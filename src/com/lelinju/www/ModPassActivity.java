package com.lelinju.www;

import java.security.MessageDigest;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
/**
 * �޸�����
 * @author cloor
 */
public class ModPassActivity extends BaseActivity implements OnClickListener{
	EditText v1, v2, v3;
	Button denglu;
	private TextView tv1,wenhao;
	private WareDao wareDao;
	private String yth;
	String oldP, newP1, newP2;
	ModPassActivity md5;
	private String inStr, mm, mi;
	private MessageDigest md;
	private TextView imgbtn_findpwd;
	private int tag = -1;
	private SharedPreferences spPreferences;
	String user_name;
	String type,type_num;
	String value;
//	private Handler handler = new Handler() {
//		public void dispatchMessage(android.os.Message msg) {
//			switch (msg.what) {
//			case 0:
//				oldP = mi;
//				// /mi/resetpwd.ashx?yth=1&pwd=1&newpwd=1
//				RequestParams params = new RequestParams();
//				params.put("yth", yth);
//				params.put("pwd", oldP);
//				params.put("newpwd", newP1);
//				String url = "";
//				if(tag==0){
//					url = "/mi/resetpwd.ashx";
//				}else if(tag==1){
//					url = "/mi/resetpaypwd.ashx";
//				}
//				AsyncHttp.post(RealmName.REALM_NAME + url,
//						params, new AsyncHttpResponseHandler() {
//							public void onSuccess(int arg0, String arg1) {
//								System.out.println("~" + arg1);
//								try {
//									JSONObject object = new JSONObject(arg1);
//
//									Toast.makeText(getApplicationContext(),
//											object.getString("msg"), 200).show();
//								} catch (JSONException e) {
//									e.printStackTrace();
//								}
//							};
//						}, getApplicationContext());
//				break;
//
//			default:
//				break;
//			}
//		};
//	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modpass_layout);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		tv1 = (TextView) findViewById(R.id.tv1);
		try {
		type_num = getIntent().getStringExtra("value");
		System.out.println("===type_num=========="+type_num);
		if (type_num != null) {
			if (type_num.equals("1")) {
				type = "password";
				tv1.setText("�޸��û�����");
			}else if (type_num.equals("2")){
				type = "paypassword";
				tv1.setText("�޸�֧������");
			}
			}else {
				
			}
			
			
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		imgbtn_findpwd = (TextView) findViewById(R.id.wenhao);
		imgbtn_findpwd.setOnClickListener(this);
		v1 = (EditText) findViewById(R.id.v1);
		v2 = (EditText) findViewById(R.id.v2);
		v3 = (EditText) findViewById(R.id.v3);
		denglu = (Button) findViewById(R.id.denglu);
		denglu.setOnClickListener(this);
		
		} catch (Exception e) {
			// TODO: handle exception
			
			
			
			
			e.printStackTrace();
		}
		
//		tag =  getIntent().getIntExtra("tag",-1);
		
//		switch (tag) {
//		case 0:
//			type = "password";
//			type_num = "1";
//			tv1.setText("�޸ĵ�¼����");
//			break;
//		case 1:
//			type = "paypassword";
//			type_num = "2";
//			tv1.setText("�޸�֧������");
//			break;
//
//		default:
//			break;
//		}
		
//		md5 = new ModPassActivity();
//
//		wareDao = new WareDao(getApplicationContext());
//		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
//		yth = registerData.getHengyuCode();
//		
//		if (yth != null) {
//			v4.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					oldP = v1.getText().toString();
//					newP1 = v2.getText().toString();
//					newP2 = v3.getText().toString();
//					 if (!(v2.getText().toString().length()<20&&v2.getText().toString().length()>=8)) {
//						 v2.setError("������8-20λ֮��");
//						 v2.requestFocus();
//					}else if (!(v3.getText().toString().length()<20&&v3.getText().toString().length()>=8)) {
//						v3.setError("������8-20λ֮��");
//						v3.requestFocus();
//					}else
//					if (newP1.equals(newP2)) {
//						// ��ʼ��������
//						/*
//						 * RealmName.REALM_NAME + "/mi/getRnd.ashx?yth=" +
//						 * processParam(name) + "&key=jes800";
//						 */
//						RequestParams params = new RequestParams();
//						params.put("yth", yth);
//
//						AsyncHttp.post(RealmName.REALM_NAME
//								+ "/mi/getRnd.ashx?key=jes800", params,
//								new AsyncHttpResponseHandler() {
//									@Override
//									public void onSuccess(int arg0, String arg1) {
//										// TODO Auto-generated method stub
//										super.onSuccess(arg0, arg1);
//										try {
//											JSONObject jsonObject = new JSONObject(
//													arg1);
//											String status = jsonObject
//													.getString("status");
//											if (status.equals("1")) {
//												String rnd = jsonObject
//														.getString("rnd");
//
//												md5.setInStr(oldP);
//												md5.init();
//												mm = md5.compute();
//												mm = mm.toUpperCase();
//												String myrnd = rnd;
//												md5.setInStr(mm + myrnd);
//												md5.init();
//												mi = md5.compute();
////												handler.sendEmptyMessage(0);
//											}
//										} catch (JSONException e) {
//											// TODO Auto-generated catch block
//											e.printStackTrace();
//										}
//
//									}
//								}, getApplicationContext());
//					} else {
//						Toast.makeText(getApplicationContext(), "�������벻��ȷ!", 200)
//								.show();
//					}
//				}
//			});
//
//		}

	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.wenhao://�һ�����
			System.out.println("=================type_num==" + type_num);
			Intent intent2 = new Intent(ModPassActivity.this,UserForgotPasswordActivity.class);
			intent2.putExtra("type", type_num);
			startActivity(intent2);
			break;
		case R.id.denglu:
			oldP = v1.getText().toString();
			newP1 = v2.getText().toString();
			newP2 = v3.getText().toString();
			if (oldP.equals("")) {
				Toast.makeText(ModPassActivity.this, "�����벻��Ϊ��", 200).show();
			}else if (!(v2.getText().toString().length()<20&&v2.getText().toString().length()>=8)) {
				 Toast.makeText(ModPassActivity.this, "������8-20λ֮��", 200).show();
			}else if (!(v3.getText().toString().length()<20&&v3.getText().toString().length()>=8)) {
				Toast.makeText(ModPassActivity.this, "������8-20λ֮��", 200).show();
			}else if (!newP1.equals(newP2)) {
				Toast.makeText(ModPassActivity.this, "�����벻��ͬ", 200).show();
			}else
				{
				System.out.println("===type=========="+type);
				String strUrl = RealmName.REALM_NAME_LL+"/user_update_password?user_name="+user_name+"&oldpassord="+oldP+"&newpassword="+newP1+"&type="+type+"";
						
				AsyncHttp.get(strUrl,
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								super.onSuccess(arg0, arg1);
								System.out.println("============="+arg1);
								try {
									JSONObject object = new JSONObject(arg1);
									String result = object.getString("status");
									String info = object.getString("info");
									if (result.equals("y")) {
//										handler.sendEmptyMessage(2);
										 Toast.makeText(ModPassActivity.this, info, 200).show();
										 finish();
									} else {
									    Toast.makeText(ModPassActivity.this, info, 200).show();
									    finish();
//										handler.sendEmptyMessage(3);
									}
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}, getApplicationContext());
			}

			break;
		default:
			break;
		}
	}
//	public void MD5() {
//		inStr = null;
//		md = null;
//	}
//
//	public void setInStr(String str) {
//		this.inStr = str;
//	}
//
//	public void init() {
//		try {
//			this.md = MessageDigest.getInstance("MD5");
//		} catch (Exception e) {
//		}
//	}
//
//	public String compute() {
//
//		char[] charArray = this.inStr.toCharArray();
//		byte[] byteArray = new byte[charArray.length];
//		for (int i = 0; i < charArray.length; i++) {
//			byteArray[i] = (byte) charArray[i];
//		}
//		byte[] mdBytes = this.md.digest(byteArray);
//		StringBuffer hexValue = new StringBuffer();
//		for (int i = 0; i < mdBytes.length; i++) {
//			int val = (mdBytes[i]) & 0xff;
//			if (val < 16) {
//				hexValue.append("0");
//			}
//			hexValue.append(Integer.toHexString(val));
//		}
//
//		return hexValue.toString();
//	}


}