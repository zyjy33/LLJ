package com.lelinju.www;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.RealmName;
import com.guanggao.S;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.DBManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.fragment.YunCommunityActivity;
import com.hengyushop.demo.my.MyShopPingCarActivity;
import com.hengyushop.demo.service.YunshangServiceActivity;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.lelinju.www.R;


public class MainFragment extends BaseActivity {
	private RadioButton main_bottom_rtn0, main_bottom_rtn1, main_bottom_rtn2,
			main_bottom_rtn3, main_bottom_rtn4;
	private IWXAPI api;
	private WareDao wareDao;
	private RadioGroup main_bottom_rtns;
	private List<UserRegisterData> list_isLogin;
	private String yth, key;
	private String strUrl2 = RealmName.REALM_NAME + "/apkdown/ysj_apk/version.xml";
	private String strUr2 = RealmName.REALM_NAME_LL + "/get_apk_version?browser=android";
	private String url, version,version2, updatainfo, c_version;
//	private String URL = "http://www.ju918.com/apkdown/YSJ_apk/YunSJ.apk";
	private String URL;
	private SharedPreferences spPreferences;
	public static String user_name,user_id,nickname;
    boolean zhuangtai = false;
	public static boolean panduan = false;
	
	private FragmentManager manager;
	private MyPopupWindowMenu popupWindowMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fragment_activity);
//		Thread.setDefaultUncaughtExceptionHandler(this);
		//�����������ȡϵͳ����TELEPHONY_SERVICE
	    /*TelephonyManager telM = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);  
		telM.listen(new TelListener(this), PhoneStateListener.LISTEN_CALL_STATE);*/
		{
			//�������
//			Intent intent = new Intent(this,S.class);
//			intent.putExtra("start", 0);
//			startService(intent);
		}
		 
		
		popupWindowMenu = new MyPopupWindowMenu(MainFragment.this);
		manager = getFragmentManager();
//		((Location) getApplicationContext()).reloadClient();
//		((Location) getApplicationContext()).startLocation();
		main_bottom_rtns = (RadioGroup) findViewById(R.id.main_bottom_rtns);
		main_bottom_rtn0 = (RadioButton) findViewById(R.id.main_bottom_rtn0);
		main_bottom_rtn1 = (RadioButton) findViewById(R.id.main_bottom_rtn1);
//		main_bottom_rtn2 = (RadioButton) findViewById(R.id.main_bottom_rtn2);
		main_bottom_rtn3 = (RadioButton) findViewById(R.id.main_bottom_rtn3);
		main_bottom_rtn4 = (RadioButton) findViewById(R.id.main_bottom_rtn4);
	
		main_bottom_rtns.setOnCheckedChangeListener(listener);
		main_bottom_rtns.check(0);
		
		
		new Thread() {
			public void run() {
				api = WXAPIFactory.createWXAPI(MainFragment.this,
						Constants.APP_ID);
				wareDao = new WareDao(getApplicationContext());
				list_isLogin = wareDao.findisLogin();
				reView();
				
				final String dbFile1 = "/data/data/" + DBManager.PACKAGE_NAME
						+ "/databases/work.db";
				final String dbFile2 = "/data/data/" + DBManager.PACKAGE_NAME
						+ "/databases/hcz.db";
				final String dbFile3 = "/data/data/" + DBManager.PACKAGE_NAME
						+ "/databases/tuangou.db";
				final String dbFile4 = "/data/data/" + DBManager.PACKAGE_NAME
						+ "/databases/jdpiao.db";
				final String dbFile0 = "/data/data/" + DBManager.PACKAGE_NAME
						+ "/databases/city.db";
				copyCityDB(dbFile0);
				copyWorkDB(dbFile1);
				copyHczDB(dbFile2);
				copyTuanDB(dbFile3);
				copyJDB(dbFile4);

			};
		}.start();
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
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

	private int ischecked = 1;


	private void reView() {
		try {
			
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		nickname = spPreferences.getString("nickname", "");
//		String pwdString = spPreferences.getString("pwd", "");
		
		System.out.println("=================="+user_name);
		System.out.println("===========user_id========" +user_id);
		
//		if (!nickname.equals("")) {
			try {
//			Toast.makeText(MainFragment.this, "����Ҫ��¼", 200).show();
			wareDao = new WareDao(getApplicationContext());
			UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
			yth = registerData.getHengyuCode();
			key = registerData.getUserkey();
			wareDao = new WareDao(getApplicationContext());
			wareDao.deleteAllShopCart(); // ��չ��ﳵ�������Ϣ
			c_version = getAppVersionName(this).trim();
			
			if (zhuangtai == false) {
				handler.sendEmptyMessage(-2);//�汾����
			}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
//		} else {
//			try {
//				
////				if (!user_name.equals("")) {
//					try {
//						wareDao = new WareDao(getApplicationContext());
//						UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
//						yth = registerData.getHengyuCode();
//						key = registerData.getUserkey();
//						wareDao = new WareDao(getApplicationContext());
//						wareDao.deleteAllShopCart(); // ��չ��ﳵ�������Ϣ
//						c_version = getAppVersionName(this).trim();
//						if (zhuangtai == false) {
//							handler.sendEmptyMessage(-2);//�汾����
//						}
//						} catch (Exception e) {
//							// TODO: handle exception
//							e.printStackTrace();
//						}
////				}else {
////			Intent intent = new Intent(MainFragment.this,UserLoginActivity.class);
////			intent.putExtra("login", 0);
////			startActivityForResult(intent, 0);
////				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
		
		} catch (Exception e) {
			// TODO: handle exception
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

	/*
	 * �ӷ�����������APK
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // �������Ի���
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("�������ظ���");
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
					pd.dismiss(); // �������������Ի���
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	// ��װapk
	protected void installApk(File file) {
		Intent intent = new Intent();
		// ִ�ж���
		intent.setAction(Intent.ACTION_VIEW);
		// ִ�е���������
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");// �˴�AndroidӦΪandroid��������ɰ�װ����
		startActivity(intent);
	}

	// ����汾����
	private void dialog() {
		System.out.println("��ҳ�汾==============");
		AlertDialog.Builder builder = new Builder(this);
//		builder.setMessage(updatainfo);
		builder.setMessage("�Ƿ�������°汾��");
		builder.setTitle("��ʾ:�°汾");
		builder.setPositiveButton("����", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
//				spPreferences.edit().clear().commit(); 
//				String user_id = spPreferences.getString("user_id", "");
//				System.out.println("user_id�Ƿ�Ϊ��======"+user_id);
//				 Toast.makeText(MainFragment.this, user_id, 200).show();
				downLoadApk();
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

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case -3:
				int id = msg.arg1;
				Intent intent = new Intent(MainFragment.this,
						WareInformationActivity.class);
				intent.putExtra("id", id);
				startActivity(intent);
				break;
			case -2:
			/*	Map<String, String> params = new HashMap<String, String>();
				params.put("act", "UserCartInfo");
				params.put("yth", yth);
				params.put("key", key);
				strUrl = RealmName.REALM_NAME + "/mi/getdata.ashx";
				AsyncHttp.post_1(strUrl, params,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						parseShopCart(arg1);
					}
				});*/
//				/**
//				 * �汾
//				 */
				try {
//				AsyncHttp.get(strUrl2, new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int arg0, String arg1) {
//						
//						super.onSuccess(arg0, arg1);
//						System.out.println("��ҳ�汾1=============="+arg1);
//						xmlparse(arg1);
//						
//						String c_version = getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
//						float server_version = Float.parseFloat(version.replaceAll("\\.", ""));
//						float client_version = Float.parseFloat(c_version);
//						System.out.println("��ǰ:" + client_version + "������:"+ server_version);
////						if (server_version > client_version) {
//////						if (client_version > server_version) {
////							Message message = new Message();
////							message.what = 0;
////							handler.sendMessage(message);
////						}
//					}
//				}, getApplicationContext());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
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
//							URL = "http://183.62.138.31:1010/tools/downapk.ashx?id="+id+"";
							//http://www.ju918.com/apkdown/YSJ_apk/YunSJ.apk
							System.out.println("��ҳ�汾URL=============="+URL);
							String c_version = getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
							float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//������
							float client_version = Float.parseFloat(c_version);//��ǰ
							
							System.out.println("1��ǰ:" + client_version + "1������:"+ server_version);
							if (server_version > client_version) {
								Message message = new Message();
								message.what = 0;
								handler.sendMessage(message);
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						}
					}, getApplicationContext());
							
					
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				break;
//			case -1:
//				SoftWarePopuWindow();
//				break;
			case 0:
				dialog();
				break;
			case 2:

				break;
			case 3:
				main_bottom_rtns.check(3);
				break;
//			case 5:
//				String text1 = (String) msg.obj;
//				softshareWxChat(text1);
//				break;
//			case 6:
//				String text2 = (String) msg.obj;
//				softshareWxFriend(text2);
//				break;
			case 7:
				String text = (String) msg.obj;
				Uri uri = Uri.parse("smsto:");
				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
				it.putExtra("sms_body", text);
				startActivity(it);
				break;
			default:
				break;
			}
		};
	};
	
	// �����������˵İ汾��Ϣ
		public void xmlparse(String st) {
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.parse(new ByteArrayInputStream(st.getBytes()));
//				NodeList nodeList0 = document.getElementsByTagName("version2");
				NodeList nodeList1 = document.getElementsByTagName("version");
				NodeList nodeList2 = document.getElementsByTagName("url");
				NodeList nodeList3 = document.getElementsByTagName("updateInfo");
				System.out.println("nodeList1=================="+nodeList1);
				System.out.println("nodeList2=================="+nodeList2);
				System.out.println("nodeList3=================="+nodeList3);
//				version2 = nodeList0.item(0).getTextContent();
				version = nodeList1.item(0).getTextContent();
				url = nodeList2.item(0).getTextContent();
//				System.out.println("version2=================="+version2);
				System.out.println("version=================="+version);
				System.out.println("url=================="+url);
				//http://www.ju918.com/apkdown/YSJ_apk/YunSJ.apk
				updatainfo = nodeList3.item(0).getTextContent();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			FragmentTransaction transaction = manager.beginTransaction();
			Fragment fragment = getFragment(arg1);
			transaction.replace(R.id.main_content, fragment);
			transaction.commit();

		}
	};
	
	private boolean isEdit = true;

	public Fragment getFragment(int what) {
		Fragment fragment = null;
		switch (what) {
		case R.id.main_bottom_rtn0:
		case 0:
			try {
				
			isEdit = true;
//			fragment = new HomeActivity(imageLoader, MainFragment.this,key,yth);
			main_bottom_rtns.setVisibility(View.VISIBLE);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.main_bottom_rtn1:
		case 1:
			try {
			isEdit = false;
			main_bottom_rtns.setVisibility(View.VISIBLE);
//			fragment = new YiHuaActivity(imageLoader,MainFragment.this);YunshangServiceActivity
//			fragment = new YunshangServiceActivity();
		    } catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		    }
			break;
//		case R.id.main_bottom_rtn2:
//		case 2:
//			//Cocos2d
//			isEdit = true;
//			main_bottom_rtns.setVisibility(View.VISIBLE);
////			fragment = new MenuActivity();
//			fragment = new YunCommunityActivity();
//			break;
		case R.id.main_bottom_rtn3:
			
		case 3://���ﳵ
			try {
			isEdit = true;
			main_bottom_rtns.setVisibility(View.VISIBLE);
//			fragment = new MyShopPingCarActivity();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.main_bottom_rtn4:
		case 4:
			try {
			isEdit = true;
			main_bottom_rtns.setVisibility(View.VISIBLE);
//			fragment = new IndividualCenterActivity(imageLoader, getApplicationContext());
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		return fragment;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("����ֵ:"+requestCode);
		switch (requestCode) {
		case 0:
			main_bottom_rtns.check(0);
			break;
		case 3:
			main_bottom_rtns.check(3);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isEdit) {
				AlertDialog isExit = new AlertDialog.Builder(this).create();
				isExit.setTitle("ϵͳ��ʾ");
				isExit.setMessage("ȷ���˳����ۣ̾�");
				isExit.setButton("ȷ��", listener2);
				isExit.setButton2("ȡ��", listener2);
				isExit.show();
			}else {
				main_bottom_rtns.check(0);
				
			}
		}
		return true;
	}

	/** �����Ի��������button����¼� */
	DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case AlertDialog.BUTTON_POSITIVE:// "ȷ��"��ť�˳�����
				wareDao.deleteAllShopCart();
//				AppManager.getAppManager().finishAllActivity();
				android.os.Process.killProcess(android.os.Process.myPid());
				break;
			case AlertDialog.BUTTON_NEGATIVE:// "ȡ��"�ڶ�����ťȡ���Ի���
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // �Ի�����ʧ
			popupWindowMenu.currentState = 1; // ���״̬������ʧ
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // ���״̬����ʾ��
		}
		return false; // true--��ʾϵͳ�Դ��˵���false--����ʾ��
	}

	
//	 public void uncaughtException(Thread arg0, Throwable arg1) {
//			// TODO Auto-generated method stub
//			 //�ڴ˴����쳣�� arg1��Ϊ���񵽵��쳣
//	        Log.i("AAA", "uncaughtException   " + arg1);
//		}

	/**
	 * �������ݿ��ļ�
	 * 
	 * @param dbFile
	 */
	public void copyHczDB(String dbFile) {
		// new File(dbFile).delete();
		System.out.println(dbFile);
		if (!new File(dbFile).exists()) {// �ж����ݿ��ļ��Ƿ���ڣ�����������ִ�е��룬����ֱ�Ӵ����ݿ�
			InputStream is = getApplicationContext().getResources()
					.openRawResource(R.raw.hcz); // ����������ݿ�
			try {
				FileOutputStream fos = new FileOutputStream(dbFile);
				byte[] buffer = new byte[40000];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public void copyCityDB(String dbFile) {
		// new File(dbFile).delete();
		System.out.println(dbFile);
		if (!new File(dbFile).exists()) {// �ж����ݿ��ļ��Ƿ���ڣ�����������ִ�е��룬����ֱ�Ӵ����ݿ�
			InputStream is = getApplicationContext().getResources()
					.openRawResource(R.raw.city); // ����������ݿ�
			try {
				FileOutputStream fos = new FileOutputStream(dbFile);
				byte[] buffer = new byte[40000];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * �������ݿ��ļ�
	 * 
	 * @param dbFile
	 */
	public void copyTuanDB(String dbFile) {
		// new File(dbFile).delete();
		System.out.println(dbFile);
		if (!new File(dbFile).exists()) {// �ж����ݿ��ļ��Ƿ���ڣ�����������ִ�е��룬����ֱ�Ӵ����ݿ�
			InputStream is = getApplicationContext().getResources()
					.openRawResource(R.raw.tuangou); // ����������ݿ�
			try {
				FileOutputStream fos = new FileOutputStream(dbFile);
				byte[] buffer = new byte[40000];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * �������ݿ��ļ�
	 * 
	 * @param dbFile
	 */
	public void copyJDB(String dbFile) {
		// new File(dbFile).delete();
		System.out.println(dbFile);
		if (!new File(dbFile).exists()) {// �ж����ݿ��ļ��Ƿ���ڣ�����������ִ�е��룬����ֱ�Ӵ����ݿ�
			InputStream is = getApplicationContext().getResources()
					.openRawResource(R.raw.jdpiao); // ����������ݿ�
			try {
				FileOutputStream fos = new FileOutputStream(dbFile);
				byte[] buffer = new byte[40000];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void copyWorkDB(String dbFile) {
		// new File(dbFile).delete();
		if (!new File(dbFile).exists()) {// �ж����ݿ��ļ��Ƿ���ڣ�����������ִ�е��룬����ֱ�Ӵ����ݿ�
			InputStream is = getApplicationContext().getResources()
					.openRawResource(R.raw.work); // ����������ݿ�
			try {
				FileOutputStream fos = new FileOutputStream(dbFile);
				byte[] buffer = new byte[40000];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	private void con(final int index,int type) {
		WareDao wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		String yth = registerData.getHengyuCode();
		if(yth.length()==0){
			NewDataToast.makeText(getApplicationContext(), "���ȵ�¼",false, 0).show();
		}else {
			Map<String, String> param0 = new HashMap<String, String>();
			param0.put("act", "GetDownloadAPK_URL");
			param0.put("yth", "");
			param0.put("reqType", ""+type);
		AsyncHttp.post_1(
				RealmName.REALM_NAME+"/mi/getdata.ashx"
						,param0, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							Message msg = new Message();
							msg.what = index;
							msg.obj = jsonObject.getString("msg");
							handler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				});
		}
	}
}
