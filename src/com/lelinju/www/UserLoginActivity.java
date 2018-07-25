package com.lelinju.www;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.example.downloadandnotificationbar.UpdateApkThread;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.my.QQLoginActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;

public class UserLoginActivity extends BaseActivity implements OnClickListener {
	private Button btn_login;
	private DialogProgress progress;
	public static String kahao;
	private String user_name,user_id,nickname,headimgurl,access_token,sex,unionid,province,city,country;
    private SharedPreferences spPreferences;
	public static boolean isWXLogin = false;
	public static IWXAPI mWxApi;
	public static String WX_CODE = "";
	private static final String TAG = QQLoginActivity.class.getName();
	public static String mAppid;
	public static QQAuth mQQAuth;
	private UserInfo mInfo;
	private Tencent mTencent;
	private final String APP_ID = "1105810330";// ����ʱʹ�ã�����������ʱ��Ҫ�����Լ���APP_ID
//	private final String APP_ID = "222222";// ����ʱʹ�ã�����������ʱ��Ҫ�����Լ���APP_ID
	public static Bitmap bitmap;
	public static String oauth_name = "";
	public static boolean panduan = false;
	public static boolean panduan_tishi = false;
	public static Handler handler1;
    public static boolean wx_fanhui = false;
	public static boolean fanhui_type = false;
	public static boolean zhuangtai = false;
    private SharedPreferences spPreferences_login;
    private String strUr2 = RealmName.REALM_NAME_LL + "/get_apk_version?browser=android";
	private String URL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weixin_login);
		  mWxApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID, true);
	      mWxApi.registerApp(Constants.APP_ID);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
		try {
			progress = new DialogProgress(UserLoginActivity.this);
			//�����򷵻�true�ر�
//			if (UserLoginWayActivity.jiemian == true) {
//				UserLoginWayActivity.jiemian = false;
//				finish();
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
//			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
//			user_name = spPreferences.getString("user_name", "");
			fanhui_type = true;
			wx_fanhui = true;//����΢�ŷ���APP
			
			if (zhuangtai == false) {
				updata();
			}
			
			if (isWXLogin) {
				panduan = true;
				panduan_tishi = true;
				oauth_name = "weixin";
				System.out.println("2------------------"+WX_CODE);
//				Toast.makeText(this, "΢��codeΪ"+WX_CODE+"/", 1000).show();
				SharedPreferences spPreferences_tishi = getSharedPreferences("longuserset_tishi", MODE_PRIVATE);
				String qq = spPreferences_tishi.getString("qq", "");
				if (!qq.equals("")) {
					spPreferences_tishi.edit().clear().commit();
					UserLoginWayActivity.panduan_tishi = false;
				}
				System.out.println("=================qq==" + qq);

				
				SharedPreferences spPreferences = getSharedPreferences("longuserset_ptye", MODE_PRIVATE);
				Editor editor = spPreferences.edit();
				editor.putString("ptye", "weixin");
				editor.commit();
				
				userxinxi();
			}else {
//				onClickLogin();
//				finish();
			}
			
		}
	
		@Override
		protected void onStart() {
			Log.d(TAG, "-->onStart");
			final Context context = UserLoginActivity.this;
			final Context ctxContext = context.getApplicationContext();
			mAppid = APP_ID;
			mQQAuth = QQAuth.createInstance(mAppid, ctxContext);
			mTencent = Tencent.createInstance(mAppid, UserLoginActivity.this);
			super.onStart();
		}
		
		
	    public void userxinxi(){
			try{
				String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ Constants.APP_ID + "&secret=" + Constants.APP_SECRET + "&code=" + WX_CODE + 
				"&grant_type=authorization_code";
//				Toast.makeText(MainUserLoginActivity.this, accessTokenUrl, 100000).show();
				System.out.println("======11============="+accessTokenUrl);
				AsyncHttp.get(accessTokenUrl, new AsyncHttpResponseHandler() {
					public void onSuccess(int arg0, String arg1) {
						System.out.println("======���1============="+arg1);
//						Toast.makeText(MainUserLoginActivity.this, "����Ϊ+"+arg1, 400).show();
						try {
							JSONObject object = new JSONObject(arg1);
							String access_token = object.getString("access_token");
							String openid = object.getString("openid");
							userxinxill(access_token,openid);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
				}, UserLoginActivity.this);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	    /**
	     * ΢�ŵ�¼
	     * @param ACCESS_TOKEN
	     * @param openid
	     */
	    public void userxinxill(String ACCESS_TOKEN, String openid){
			try{
				access_token = ACCESS_TOKEN;
				String accessTokenUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+ACCESS_TOKEN+"&openid="+openid+"";
				System.out.println("======22============="+accessTokenUrl);
				AsyncHttp.get(accessTokenUrl, new AsyncHttpResponseHandler() {
					public void onSuccess(int arg0, String arg1) {
						System.out.println("======���2============="+arg1);
//						Toast.makeText(UserLoginActivity.this, "����2Ϊ+"+arg1, 400).show();
						try {
							JSONObject object = new JSONObject(arg1);
							nickname = object.getString("nickname");
							headimgurl = object.getString("headimgurl");
							unionid = object.getString("unionid");
							sex = object.getString("sex");
							province = object.getString("province");
							city = object.getString("city");
							country = object.getString("country");
							
							System.out.println("======headimgurl============="+headimgurl);
							Editor editor = spPreferences_login.edit();
							editor.putString("nickname", nickname);
							editor.putString("headimgurl", headimgurl);
							editor.putString("access_token", access_token);
							editor.putString("unionid", unionid);
							editor.putString("sex", sex);
							editor.putString("province", province);
							editor.putString("city", city);
							editor.putString("country", country);
							editor.commit();
//							mBitmap = HttpUtil.getBitmapFromURL(headimgurl);
//							mTextView.setText(nickname);
//							mImageView.setImageBitmap(mBitmap);
							
							SharedPreferences spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
							String headimgurl = spPreferences_login.getString("headimgurl", "");
							 System.out.println("headimgurl========1========"+headimgurl);
							 
							SharedPreferences spPreferences_qq = getSharedPreferences("longuserset_3_qq", MODE_PRIVATE);
							spPreferences_qq.edit().clear().commit();
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						isWXLogin = false;
						finish();
					};
				}, UserLoginActivity.this);
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
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
			case 10:
				
				break;
			case 1:
				String str = (String) msg.obj;
				Toast.makeText(UserLoginActivity.this, str, 200).show();
				break;
			case 7:
				
				break;
			default:
				break;
			}

		};

	};

	private void initdata() {
		btn_login = (Button) findViewById(R.id.btn_login);
		TextView tv_denglu = (TextView) findViewById(R.id.tv_denglu);
		btn_login.setOnClickListener(this);
		tv_denglu.setOnClickListener(this);
		
		TextView img_menu = (TextView) findViewById(R.id.img_menu);
		img_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_login://΢�ŵ�¼
//			Intent intent = new Intent(UserLoginActivity.this,MainUserLoginActivity.class);
//			startActivity(intent);
			isWXLogin = true;
			SendAuth.Req req = new SendAuth.Req();
			req.scope = "snsapi_userinfo";
			req.state = "wechat_sdk_demo";
			mWxApi.sendReq(req);
			break;
		case R.id.img_qq_login://QQ��¼
			try {
//		    onClickLogin();	
//			Intent intent = new Intent(UserLoginActivity.this,QQLoginActivity.class);
//			startActivity(intent);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.tv_denglu://
			Intent intent3 = new Intent(UserLoginActivity.this,UserLoginWayActivity.class);
			startActivity(intent3);
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
//				Editor editor = spPreferences_login.edit();
//				editor.putString("access_token", access_token);
//				editor.putString("unionid", openid);
//				editor.putString("sex", ret);
//				editor.commit();
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////			Util.showResultDialog(UserLoginActivity.this, response.toString(),"��¼�ɹ�");
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
//			Util.toastMessage(UserLoginActivity.this, "onError: " + e.errorDetail);
//			Util.dismissDialog();
//		}
//
//		@Override
//		public void onCancel() {
////			Util.toastMessage(UserLoginActivity.this, "�û�ȡ��");//onCancel: 
//			Util.dismissDialog();
//		}
//	}
	
//	private void updateUserInfo() {
//		try {
//			
//		if (mQQAuth != null && mQQAuth.isSessionValid()) {
//			try {
//			IUiListener listener = new IUiListener() {
//				@Override
//				public void onError(UiError e) {
//					// TODO Auto-generated method stub
//				}
//
//				@Override
//				public void onComplete(final Object response) {
//					try {
////					Message msg = new Message();
////					msg.obj = response;
////					msg.what = 0;
////					mHandler.sendMessage(msg);
//					new Thread() {
//
//						@Override
//						public void run() {
//							JSONObject json = (JSONObject) response;
//							if (json.has("figureurl")) {
//								bitmap = null;
//								try {
//									nickname = json.getString("nickname");
//									sex = json.getString("gender");
//									province = json.getString("province");
//									city = json.getString("city");
//									System.out.println("nickname==========1====="+nickname);
//									bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
//									String headimgurl2 = BitUtil.bitmaptoString(bitmap);
//									Editor editor = spPreferences_login.edit();
//									editor.putString("nickname", nickname);
//									editor.putString("headimgurl2", headimgurl2);
//									editor.putString("sex", sex);
//									editor.putString("province", province);
//									editor.putString("city", city);
//									editor.putString("country", country);
//									editor.commit();
//									System.out.println("bitmap==============="+bitmap);
//								} catch (JSONException e) {
//									e.printStackTrace();
//								}
////								Message msg = new Message();
////								msg.obj = bitmap;
////								msg.what = 1;
////								mHandler.sendMessage(msg);
////								finish();
//								if (nickname != null && bitmap != null) {
//									panduan = true;
//									finish();
//								}
//							}
//						}
//
//					}.start();
//					
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//					
//				}
//
//				@Override
//				public void onCancel() {
//				}
//			};
//			System.out.println("2===============");
//			mInfo = new UserInfo(this, mQQAuth.getQQToken());
//			mInfo.getUserInfo(listener);
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//			
//		} else {
////			mUserInfo.setText("");
////			mUserInfo.setVisibility(android.view.View.GONE);
////			mUserLogo.setVisibility(android.view.View.GONE);
//		}
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		
//	}
	
	
	
	private void updata() {
		try {
			/**
			 * �汾2
			 */
			AsyncHttp.get(strUr2, new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					
					super.onSuccess(arg0, arg1);
					System.out.println("��ҳ�汾=============="+arg1);
					try{
					JSONObject jsonObject = new JSONObject(arg1);
					JSONObject jsob = jsonObject.getJSONObject("data");
					String file_version = jsob.getString("file_version");
					String link_url = jsob.getString("link_url");
					String file_path = jsob.getString("file_path");
					String id = jsob.getString("id");
					URL = RealmName.REALM_NAME_HTTP + file_path;
					System.out.println("��ҳ�汾URL=============="+URL);
					String c_version = getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
					float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//������
					float client_version = Float.parseFloat(c_version);//��ǰ
					
					System.out.println("������:"+ server_version+"/��ǰ:" + client_version);
					if (server_version > client_version) {
//						Toast.makeText(MainFragment.this, "��ʾ����", 200).show();
						Message message = new Message();
						message.what = 0;
						handler.sendMessage(message);
					}
//					Toast.makeText(MainFragment.this, "û����ʾ����", 200).show();
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
		// �����ȵĻ���ʾ��ǰ��sdcard�������ֻ��ϲ����ǿ��õ�
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// ��ȡ���ļ��Ĵ�С
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
				// ��ȡ��ǰ������
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
		final ProgressDialog pd; // �������Ի���
		pd = new ProgressDialog(UserLoginActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("�������ظ���");
		pd.setCanceledOnTouchOutside(true);
		pd.setProgressNumberFormat(null);
		zhuangtai = true;
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = getFileFromServer(URL, pd);
					sleep(3000);
					installApk(file);
					pd.dismiss(); // �������������Ի���
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	// ��װapk
	protected void installApk(File file) {
		MainFragmentActivity.zhuangtai = false;
		UserLoginActivity.zhuangtai = false;
		PersonCenterActivity.zhuangtai = false;
		Intent intent = new Intent();
		// ִ�ж���
		intent.setAction(Intent.ACTION_VIEW);
		// ִ�е���������
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");// ���߰����˴�AndroidӦΪandroid��������ɰ�װ����
		UserLoginActivity.this.startActivity(intent);
	}

	// ����汾����
	private void dialog() {

		AlertDialog.Builder builder = new Builder(UserLoginActivity.this);
//		builder.setMessage(updatainfo);
		builder.setMessage("��鵽���°汾���Ƿ�Ҫ���£�");
		builder.setTitle("��ʾ:�°汾");
		builder.setPositiveButton("����", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				System.out.println("MainFragmentActivity.zhuangtai------------------"+MainFragmentActivity.zhuangtai);
				System.out.println("UserLoginActivity.zhuangtai------------------"+UserLoginActivity.zhuangtai);
				if (MainFragmentActivity.zhuangtai == true) {
					Toast.makeText(UserLoginActivity.this, "��������...", 200).show();
					dialog.dismiss();
				}else if (UserLoginActivity.zhuangtai == true) {
					Toast.makeText(UserLoginActivity.this, "��������...", 200).show();
					dialog.dismiss();
				}else if (PersonCenterActivity.zhuangtai == true) {
					Toast.makeText(UserLoginActivity.this, "��������...", 200).show();
					dialog.dismiss();
				}else {
					final String filePath = Environment.getExternalStorageDirectory() + "/ss";
					new UpdateApkThread("http://mobile.lelinju.shop/upload/201709/23/201709231119249507.APK", filePath, "LeLinJu.apk",UserLoginActivity.this).start();
					downLoadApk();
				}
			}
		});
		builder.setNegativeButton("�Ժ���˵",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}
	
	// ��ȡ��ǰ����İ汾��Ϣ
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

}
