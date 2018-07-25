package com.lelinju.www;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.Util;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.my.QQLoginActivity;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.NewDataToast;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class PhoneLoginActivity extends BaseActivity implements OnClickListener {
	private Button btn_login;
	private EditText et_username, et_pwd;
	private Button img_title_register;
	private TextView imgbtn_findpwd;
	private MessageDigest md;
	private String status, rnd, name, password, mm, mi, key, hengyucode,bossUid;
	public static String id,login_sign;
	private String inStr;
	private WareDao wareDao;
	private UserRegisterData data;
//	private UserRegisterllData datall;
	private int isLogin = 1;
	private DialogProgress progress;
	private String strUrlone, strUrltwo, strUrlthree, strUrlfour;
	private String phone, actualname, joindate, activeTime, PassTicketBalance,
			shopPassTicket, credits, username;
	private String user_namell,paypassword,passwordll;
	private PhoneLoginActivity md5;
	private int ischecked = 1;
	private int login, wareid;
	public static String kahao;
	private String user_name,user_id,nickname,headimgurl,access_token,sex,unionid,province,city,country;
	private MyPopupWindowMenu popupWindowMenu;
    private SharedPreferences spPreferences;
    private String strUr2 = RealmName.REALM_NAME_LL + "/get_apk_version?browser=android";
	private String URL;
	private LinearLayout ll_buju;
    boolean zhuangtai = false;
    private ImageView img_weixin_login,img_qq_login;
	public static boolean isWXLogin = false;
	public static IWXAPI mWxApi;
	public static String WX_CODE = "";
	private static final String TAG = QQLoginActivity.class.getName();
	public static String mAppid;
	public static QQAuth mQQAuth;
	private UserInfo mInfo;
	private Tencent mTencent;
//	private final String APP_ID = "1105810330";// 测试时使用，真正发布的时候要换成自己的APP_ID
//	private final String APP_ID = "222222";// 测试时使用，真正发布的时候要换成自己的APP_ID
	public static Bitmap bitmap;
	public static String oauth_name = "";
	public static boolean panduan = false;
	Handler handler1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.title_login);
//	      ScrollView ll_buju = (ScrollView) findViewById(R.id.ScrollView01);
//	       ll_buju.getBackground().setAlpha(0);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		
		try {
			progress = new DialogProgress(PhoneLoginActivity.this);
			popupWindowMenu = new MyPopupWindowMenu(this);
			md5 = new PhoneLoginActivity();
			
//			Intent intent = this.getIntent();
//			Bundle bundle = intent.getExtras();
//			login = (Integer) bundle.get("login");
//			if (login == 2) {
//				wareid = (Integer) bundle.get("wareid");
//			}

			initdata();
			
			handler1 = new Handler() {
				public void dispatchMessage(Message msg) {
					switch (msg.what) {
					case 0:
		            break;
					case 1:
						finish();
			            break;

					default:
						break;
					}
				}
			};
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	 @Override
		protected void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			user_name = spPreferences.getString("user_name", "");
			if (user_name != null) {
				et_username = (EditText) findViewById(R.id.et_user_name);
				et_pwd = (EditText) findViewById(R.id.et_user_pwd);
				et_username.setText(user_name);
//				et_pwd.setText(pwd);
			}
//			if (zhuangtai == false) {
//				updata();
//			}
			
		}
	
	    
	Handler handler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {

			switch (msg.what) {
			case 0:
				dialog();
				break;
			case -1:
				progress.CloseProgress();
				break;
			case 6:
//				Userdata();
				try{
				strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+name+"";
				System.out.println("======11============="+strUrlone);
				AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
					public void onSuccess(int arg0, String arg1) {
						try {
							System.out.println("======66输出用户资料============="+arg1);
							JSONObject object = new JSONObject(arg1);
							status = object.getString("status");
							JSONObject obj = object.getJSONObject("data");
							if (status.equals("y")) {
								UserRegisterllData data = new UserRegisterllData();
								data.user_name = obj.getString("user_name");
								data.user_code = obj.getString("user_code");
								data.agency_id = obj.getInt("agency_id");
								data.amount = obj.getString("amount");
								data.pension = obj.getString("pension");
								data.packet = obj.getString("packet");
								data.point = obj.getString("point");
								data.group_id = obj.getString("group_id");
								data.login_sign = obj.getString("login_sign");
								data.avatar = obj.getString("avatar");
								
								login_sign = data.login_sign;
								SharedPreferences spPreferences = getSharedPreferences("longuserset",MODE_PRIVATE);
								Editor editor = spPreferences.edit();
								editor.putString("login_sign", data.login_sign);
								editor.putString("user_code", data.user_code);
								editor.putString("avatar", data.avatar);
								editor.putString("mobile",data.mobile);
								editor.putString("group_id",data.group_id);
								editor.commit();
								
//								user_name = obj.getString("user_name");
//								paypassword = obj.getString("paypassword");
//								password =  obj.getString("password");
								
//								handler.sendEmptyMessage(0);
//								insert();
							}else{
								
								
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						NewDataToast.makeText(PhoneLoginActivity.this, "连接超时", false, 0).show();
					}
				}, PhoneLoginActivity.this);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				break;
			case 10:
				
				break;
			case 1:
				String str = (String) msg.obj;
				Toast.makeText(PhoneLoginActivity.this, str, 200).show();
				break;
			case 2:
				NewDataToast.makeText(PhoneLoginActivity.this, "用户名错误", false,0).show();
				break;
			case 3:
				dialog();
				break;
			case 4:
//				Toast.makeText(WeiXinLoginActivity.this, "当前版本是最新版本", 200).show();
				break;
			case 5:
				try {
					System.out.println("=================2222222==" +mi);
					strUrltwo = RealmName.REALM_NAME + "/mi/login.ashx?yth="
							+ processParam(name) + "&pwd=" + mi + "&msisdn=";
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("strUrltwo   " + strUrltwo);

				AsyncHttp.get(strUrltwo, new AsyncHttpResponseHandler() {
					public void onSuccess(int arg0, String arg1) {
						try {
							System.out.println("=================4==" +arg1);
							JSONObject object = new JSONObject(arg1);
							int s = object.getInt("status");
							System.out.println("s:  " + s);
							if (s == 0) {
								String str = object.getString("msg");
								progress.CloseProgress();
								Message message2 = new Message();
								message2.what = 1;
								message2.obj = str;
								handler.sendMessage(message2);
							} else {
								key = object.getString("key");
								hengyucode = object.getString("yth");
								bossUid = object.getString("bossUid");
								handler.sendEmptyMessage(0);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						NewDataToast.makeText(PhoneLoginActivity.this, "连接超时", false, 0).show();
					}
				}, PhoneLoginActivity.this);

				break;
			case 7:
				
				break;
			default:
				break;
			}

		};

	};
	

	// 获取当前程序的版本信息
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;
			if (versionName == null || versionName.length() <= 0) {
				return "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}

	private String strUrl = RealmName.REALM_NAME + "/apkdown/ysj_apk/version.xml";

//	private String strUrl = RealmName.REALM_NAME_LL;
	private String url, version, updatainfo;

	// 解析服务器端的版本信息
	public void xmlparse(String st) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new ByteArrayInputStream(st
					.getBytes()));
			NodeList nodeList1 = document.getElementsByTagName("version");
			NodeList nodeList2 = document.getElementsByTagName("url");
			NodeList nodeList3 = document.getElementsByTagName("updateInfo");
			version = nodeList1.item(0).getTextContent();
			url = nodeList2.item(0).getTextContent();
			updatainfo = nodeList3.item(0).getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updata() {
		try {
			/**
			 * 版本2
			 */
			AsyncHttp.get(strUr2, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					
					super.onSuccess(arg0, arg1);
					System.out.println("首页版本=============="+arg1);
					try{
					JSONObject jsonObject = new JSONObject(arg1);
					JSONObject jsob = jsonObject.getJSONObject("data");
					String file_version = jsob.getString("file_version");
					String link_url = jsob.getString("link_url");
					String file_path = jsob.getString("file_path");
					String id = jsob.getString("id");
					URL = RealmName.REALM_NAME_HTTP + file_path;
					System.out.println("首页版本URL=============="+URL);
					String c_version = getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
					float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//服务器
					float client_version = Float.parseFloat(c_version);//当前
					
					System.out.println("服务器:"+ server_version+"/当前:" + client_version);
					if (server_version > client_version) {
//						Toast.makeText(MainFragment.this, "提示更新", 200).show();
						Message message = new Message();
						message.what = 0;
						handler.sendMessage(message);
					}
//					Toast.makeText(MainFragment.this, "没有提示更新", 200).show();
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			}, getApplicationContext());
					
			
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public static File getFileFromServer(String path, ProgressDialog pd)
			throws Exception {
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// 获取到文件的大小
			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(),
					"updata.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(PhoneLoginActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		pd.setCanceledOnTouchOutside(false);
		zhuangtai = true;
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = getFileFromServer(URL, pd);
					sleep(3000);
					installApk(file);
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
/*	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		this.setResult(0);
		AppManager.getAppManager().finishActivity();
		return true;
	
	}*/
	// 安装apk
	protected void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
		PhoneLoginActivity.this.startActivity(intent);
	}

	// 程序版本更新
	private void dialog() {

		AlertDialog.Builder builder = new Builder(PhoneLoginActivity.this);
//		builder.setMessage(updatainfo);
		builder.setMessage("检查到最新版本，是否要更新！");
		builder.setTitle("提示:新版本");
		builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				downLoadApk();
			}
		});
		builder.setNegativeButton("以后再说",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}


	private void initdata() {
		ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
		ll_buju.setBackgroundResource(R.drawable.denglu_beijing);
		img_title_register = (Button) findViewById(R.id.img_title_registre);
		btn_login = (Button) findViewById(R.id.btn_login);
		et_username = (EditText) findViewById(R.id.et_user_name);
		et_pwd = (EditText) findViewById(R.id.et_user_pwd);
		imgbtn_findpwd = (TextView) findViewById(R.id.wenhao);
		img_weixin_login = (ImageView) findViewById(R.id.img_weixin_login);
		img_qq_login = (ImageView) findViewById(R.id.img_qq_login);
		img_weixin_login.setOnClickListener(this);
		img_qq_login.setOnClickListener(this);
		img_title_register.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		imgbtn_findpwd.setOnClickListener(this);
		
		ImageView img_menu = (ImageView) findViewById(R.id.img_menu);
		img_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	// 字符串上传服务器 乱码 问题的解决方法
	private String processParam(String temp)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(temp, "UTF-8");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_weixin_login://微信登录
//			Intent intent = new Intent(WeiXinLoginActivity.this,MainWeiXinLoginActivity.class);
//			startActivity(intent);
			isWXLogin = true;
			SendAuth.Req req = new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			req.state = "wechat_sdk_demo";
			mWxApi.sendReq(req);
			break;
		case R.id.img_qq_login://QQ登录
			try {
//		    onClickLogin();	
//			Intent intent = new Intent(WeiXinLoginActivity.this,QQLoginActivity.class);
//			startActivity(intent);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.wenhao://找回密码
//			Intent intent2 = new Intent(WeiXinLoginActivity.this,FindPasswordOneActivity.class);
			Intent intent2 = new Intent(PhoneLoginActivity.this,UserForgotPasswordActivity.class);
			intent2.putExtra("type", "1");
			startActivity(intent2);
			break;
		case R.id.img_title_registre://注册账户
			Intent intent3 = new Intent(PhoneLoginActivity.this,UserRegisterActivity.class);
			startActivity(intent3);
			break;
		case R.id.btn_login:
			name = et_username.getText().toString().trim();
			password = et_pwd.getText().toString().trim();
			System.out.println("=================01==" );
			if (name.equals("")) {
				Toast.makeText(PhoneLoginActivity.this, "手机号码不能为空", 100).show();
			} else if (password.equals("")) {
				Toast.makeText(PhoneLoginActivity.this, "密码不能为空", 100).show();
			}else {
				
				
			progress.CreateProgress();
			try {
//				RequestParams params = new RequestParams();
//				params.put("site", "mobile");
//				params.put("username", name);
//				params.put("password", password);
//				params.put("terminal", "true");
//				strUrlone = RealmName.REALM_NAME + "/mi/getRnd.ashx?yth="+ processParam(name) + "&key=jes800";
				
				strUrlone = RealmName.REALM_NAME_LL + "/user_login?site=mobile&username="+name+"&password="+password+"&terminal=true";
				System.out.println("=================001=="+strUrlone);
				AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
//				System.out.println("=================001=="+params);
//				AsyncHttp.post(RealmName.REALM_NAME_LL+"/user_login", params, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							System.out.println("=================1==" + arg1);
							
							
							status = jsonObject.getString("status");
							System.out.println("status: " + status);
//							System.out.println("strUrlone: " + strUrlone);
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								JSONObject bjot = jsonObject.getJSONObject("data");
								id = bjot.getString("id");
								user_id = bjot.getString("id");
								user_name = bjot.getString("user_name");
//								rnd = bjot.getString("password");
//								System.out.println(status + "\n" + rnd);
								md5.setInStr(password);
								md5.init();
								mm = md5.compute();
								mm = mm.toUpperCase();
//								System.out.println("=================2==" +mm);
								String myrnd = rnd;
								md5.setInStr(mm + myrnd);
								md5.init();
								mi = md5.compute();
								System.out.println("=================3==" +mi);
								
//								Intent intent = new Intent(WeiXinLoginActivity.this,MainFragment.class);
//					    		startActivity(intent);
								System.out.println("===========user_id========" +user_id);
								Context context = null;
								context = getApplicationContext();
								
								Editor editor = spPreferences.edit();
								editor.putBoolean("save", true);
								editor.putString("user", et_username.getText().toString());
								editor.putString("pwd", et_pwd.getText().toString());
								editor.putString("user_id", user_id);
								editor.commit();
								
								NewDataToast.makeText(getApplicationContext(), info,false, 0).show();
								progress.CloseProgress();
								handler.sendEmptyMessage(6);
								UserLoginActivity.handler1.sendEmptyMessage(1);
								finish();
//					    		AppManager.getAppManager().finishActivity();
								
							} else if (status.equals("n")){
								NewDataToast.makeText(getApplicationContext(), info,false, 0).show();
								progress.CloseProgress();
//								Message message3 = new Message();
//								message3.what = 2;
//								handler.sendMessage(message3);
							}
							
//							status = jsonObject.getString("status");
//							System.out.println("status: " + status);
//							System.out.println("strUrlone: " + strUrlone);
//
//							if (status.equals("1")) {
//								rnd = jsonObject.getString("rnd");
//								System.out.println(status + "\n" + rnd);
//								md5.setInStr(password);
//								md5.init();
//								mm = md5.compute();
//								mm = mm.toUpperCase();
//								System.out.println("=================2==" +mm);
//								String myrnd = rnd;
//								md5.setInStr(mm + myrnd);
//								md5.init();
//								mi = md5.compute();
//								System.out.println("=================3==" +mi);
////								handler.sendEmptyMessage(5);
//								finish();
//							} else {
//								Message message3 = new Message();
//								message3.what = 2;
//								handler.sendMessage(message3);
//							}
							
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						
					}
				}, PhoneLoginActivity.this);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			}

			break;
		default:
			break;
		}
	}
    
//	private void onClickLogin() {
//		try {
//			
//		oauth_name = "qq";
//		if (!mQQAuth.isSessionValid()) {
//			try {
//				
//			IUiListener listener = new BaseUiListener() {
//				@Override
//				protected void doComplete(JSONObject values) {
//					updateUserInfo();
//				}
//			};
//			mQQAuth.login(this, "all", listener);
////			 mTencent.loginWithOEM(this, "all",listener,"10000144","10000144","xxxx");
//			mTencent.login(this, "all", listener);
////			finish();
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		} else {
//			mQQAuth.logout(this);
//			updateUserInfo();
////			finish();
//		}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	
//	private class BaseUiListener implements IUiListener {
//
//		@Override
//		public void onComplete(Object response) {
//			System.out.println("response==============="+response);
//			try {
//				String access_token = ((JSONObject) response).getString("access_token");
//				String openid = ((JSONObject) response).getString("openid");
//				String ret = ((JSONObject) response).getString("ret");
////				System.out.println("access_token==============="+access_token);
//				Editor editor = spPreferences.edit();
//				editor.putString("access_token", access_token);
//				editor.putString("unionid", openid);
//				editor.putString("sex", ret);
//				editor.commit();
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////			Util.showResultDialog(WeiXinLoginActivity.this, response.toString(),"登录成功");
////			
//			doComplete((JSONObject) response);
//		}
//
//		protected void doComplete(JSONObject values) {
//
//		}
//
//		@Override
//		public void onError(UiError e) {
//			Util.toastMessage(PhoneLoginActivity.this, "onError: " + e.errorDetail);
//			Util.dismissDialog();
//		}
//
//		@Override
//		public void onCancel() {
////			Util.toastMessage(WeiXinLoginActivity.this, "用户取消");//onCancel: 
//			Util.dismissDialog();
//		}
//	}
	
	private void updateUserInfo() {
		try {
			
		if (mQQAuth != null && mQQAuth.isSessionValid()) {
			try {
			IUiListener listener = new IUiListener() {
				@Override
				public void onError(UiError e) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onComplete(final Object response) {
					try {
//					Message msg = new Message();
//					msg.obj = response;
//					msg.what = 0;
//					mHandler.sendMessage(msg);
					new Thread() {

						@Override
						public void run() {
							JSONObject json = (JSONObject) response;
							if (json.has("figureurl")) {
								bitmap = null;
								try {
									nickname = json.getString("nickname");
									sex = json.getString("gender");
									province = json.getString("province");
									city = json.getString("city");
									System.out.println("nickname==========1====="+nickname);
									bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
									String headimgurl2 = BitUtil.bitmaptoString(bitmap);
									Editor editor = spPreferences.edit();
									editor.putString("nickname", nickname);
									editor.putString("headimgurl2", headimgurl2);
									editor.putString("sex", sex);
									editor.putString("province", province);
									editor.putString("city", city);
									editor.putString("country", country);
									editor.commit();
									System.out.println("bitmap==============="+bitmap);
								} catch (JSONException e) {
									e.printStackTrace();
								}
//								Message msg = new Message();
//								msg.obj = bitmap;
//								msg.what = 1;
//								mHandler.sendMessage(msg);
//								finish();
								if (nickname != null && bitmap != null) {
									panduan = true;
									finish();
									UserLoginActivity.handler1.sendEmptyMessage(1);
								}
							}
						}

					}.start();
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
					
				}

				@Override
				public void onCancel() {
				}
			};
			System.out.println("2===============");
			mInfo = new UserInfo(this, mQQAuth.getQQToken());
			mInfo.getUserInfo(listener);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			
		} else {
//			mUserInfo.setText("");
//			mUserInfo.setVisibility(android.view.View.GONE);
//			mUserLogo.setVisibility(android.view.View.GONE);
		}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
//	Handler mHandler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			if (msg.what == 0) {
//				JSONObject response = (JSONObject) msg.obj;
//				if (response.has("nickname")) {
//					try {
//						
//						System.out.println("nickname==============="+response.getString("nickname"));
//						Editor editor = spPreferences.edit();
//						editor.putString("nickname", response.getString("nickname"));
//						editor.commit();
//						nickname = response.getString("nickname");
////						Toast.makeText(WeiXinLoginActivity.this, touxiang, 200).show();
////						mUserInfo.setVisibility(android.view.View.VISIBLE);
////						mUserInfo.setText(response.getString("nickname"));
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			} else if (msg.what == 1) {
////				bitmap = (Bitmap) msg.obj;
////				mUserLogo.setImageBitmap(bitmap);
////				mUserLogo.setVisibility(android.view.View.VISIBLE);
//			}
//			System.out.println("3===============");
//			if (nickname != null && bitmap != null) {
//				finish();
//			}
//			
////			finish();
//		}
//
//	};
	
	public void MD5() {
		inStr = null;
		md = null;
	}

	public void setInStr(String str) {
		this.inStr = str;
	}

	public void init() {
		try {
			this.md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public String compute() {

		char[] charArray = this.inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] mdBytes = this.md.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < mdBytes.length; i++) {
			int val = (mdBytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}

		return hexValue.toString();
	}


}
