package com.android.hengyu.ui;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.hengyu.web.Constants;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.json.HttpClientUtil;
import com.hengyushop.json.HttpUtils;
import com.lglottery.www.http.Util;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.lelinju.www.CollectWareActivity;
import com.lelinju.www.HelpActivity;
import com.lelinju.www.MainFragment;
import com.lelinju.www.R;
import com.lelinju.www.UserLoginActivity;
import com.lelinju.www.UserSettingActivity;
/**
 *
 * 设置界面
 * @author Administrator
 *
 */
public class MyPopupWindowMenu extends PopupWindow implements OnClickListener,
		IWXAPIEventHandler {
	private Activity context; // 上下文
	private int titleIndex; // 菜单序号
	public int currentState; // 对话框状态：0--显示中、1--已消失、2--失去焦点
	public View sub_view;
	private IWXAPI api;
	private LinearLayout ll_backhome, ll_account, ll_collect, ll_setting,
			ll_update, ll_softshare, ll_help, ll_exit;
	private String url, version, updatainfo;
	//	private String URL = "http://www.wxpalm.com.cn/apkdown/WXZ/wxz.apk";
	private String strUrl = RealmName.REALM_NAME + "/apkdown/ysj_apk/version.xml";
	//	private String strUrl = RealmName.REALM_NAME_LL;
	// private DialogProgress progress;
	private WareDao wareDao;
	private ImageButton btn_wechat, btn_wx_friend, btn_sms;
	private static LayoutInflater inflater;
	private static View view;
	private static PopupWindow pop;
	private ImageButton img_btn_tencent;
	private SharedPreferences spPreferences;
	public static String user_name, user_id;
	private String strUr2 = RealmName.REALM_NAME_LL + "/get_apk_version?browser=android";
	private String URL;
	private String content;
	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
				case 0:
					dialog();
					break;
				case 1:
					Toast.makeText(context, "当前版本是最新版本", 200).show();
					break;
				case 2:
					String text1 = (String) msg.obj;
					softshareWxChat(text1);
					break;
				case 3:
					String text2 = (String) msg.obj;
//				softshareWxFriend(text2);
					break;
				case 4:
					String text = (String) msg.obj;
					Uri uri = Uri.parse("smsto:");
					Intent it = new Intent(Intent.ACTION_SENDTO, uri);
					it.putExtra("sms_body", text);
					context.startActivity(it);
					break;
				default:
					break;
			}
		};
	};

	public MyPopupWindowMenu(final Activity context) {

		super(context);
		this.context = context;
		// progress = new DialogProgress(context);

		wareDao = new WareDao(context);
		currentState = 1;

		LayoutInflater inflater = LayoutInflater.from(context);
		// // 引入窗口配置文件
		sub_view = inflater.inflate(R.layout.menu_popupwindow, null);
		// 添加菜单视图
		this.setContentView(sub_view);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);// menu菜单获得焦点 如果没有获得焦点menu菜单中的控件事件无法响应\
		this.setOutsideTouchable(true);
		this.setBackgroundDrawable(new BitmapDrawable());

		example();
	}

	private void example() {
		ll_backhome = (LinearLayout) sub_view.findViewById(R.id.ll_backhome);
		ll_account = (LinearLayout) sub_view.findViewById(R.id.ll_account);
		ll_collect = (LinearLayout) sub_view.findViewById(R.id.ll_collect);
		ll_setting = (LinearLayout) sub_view.findViewById(R.id.ll_setting);
		ll_update = (LinearLayout) sub_view.findViewById(R.id.ll_update);
		ll_softshare = (LinearLayout) sub_view.findViewById(R.id.ll_softshare);
		ll_help = (LinearLayout) sub_view.findViewById(R.id.ll_help);
		ll_exit = (LinearLayout) sub_view.findViewById(R.id.ll_exit);

		ll_backhome.setOnClickListener(this);
		ll_account.setOnClickListener(this);
		ll_collect.setOnClickListener(this);
		ll_setting.setOnClickListener(this);
		ll_update.setOnClickListener(this);
		ll_softshare.setOnClickListener(this);
		ll_help.setOnClickListener(this);
		ll_exit.setOnClickListener(this);
	}

	/**
	 * 获取当前选中菜单项
	 *
	 * @return 菜单项序号
	 */
	public int getTitleIndex() {

		return titleIndex;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			case R.id.ll_backhome:
				dismiss();
				Intent intent1 = new Intent(context, MainFragment.class);
				context.startActivity(intent1);
				break;
			case R.id.ll_account:
				dismiss();
//			int isLogin = 0;
//			int index = 0;
//			UserRegisterData data = new UserRegisterData();
//			data.setIsLogin(isLogin);
//			wareDao.updateQuitIsLogin(data);
//			wareDao.deleteAllShopCart();
//			wareDao.deleteAllUserInformation();
				Intent intent2 = new Intent(context, UserLoginActivity.class);
//			intent2.putExtra("login", index);
				context.startActivity(intent2);
				break;
			case R.id.ll_collect:
				dismiss();
				Intent intent3 = new Intent(context, CollectWareActivity.class);
				context.startActivity(intent3);
				break;
			case R.id.ll_setting:
				dismiss();
				Intent intent4 = new Intent(context, UserSettingActivity.class);
				context.startActivity(intent4);
				break;
			case R.id.ll_update:
				dismiss();
				// progress.CreateProgress();
				new Thread() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						super.run();
//					try {
//						InputStream ip = HttpClientUtil.getRequest(strUrl);
//						String st = new HttpUtils().convertStreamToString(ip,"utf-8").trim();
//						xmlparse(st);
//						System.out.println(st);
//						// progress.CloseProgress();
//						String c_version = getAppVersionName(context).trim().replaceAll("\\.", "");
//						System.out.println("c_version========================="+c_version);
//						System.out.println(version+"---"+c_version+"---"+version.trim().replaceAll("\\.", ""));
//						float server_version = Float.parseFloat(version.replaceAll("\\.", ""));
//						float client_version = Float.parseFloat(c_version);
//						Log.v("data1", c_version + "  " + server_version);
//						System.out.println("当前:" + client_version + "服务器:"+ server_version);
//						if (server_version > client_version) {
//							Message message = new Message();
//							message.what = 0;
//							handler.sendMessage(message);
//						} else if (server_version == client_version) {
//							Message message = new Message();
//							message.what = 1;
//							handler.sendMessage(message);
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//					}

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
//								URL = "http://183.62.138.31:1010/tools/downapk.ashx?id="+id+"";
										//http://www.ju918.com/apkdown/YSJ_apk/YunSJ.apk
										System.out.println("首页版本URL=============="+context);
										String version = getAppVersionName(context);
										String c_version = getAppVersionName(context).trim().replaceAll("\\.", "");
										float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//服务器
										float client_version = Float.parseFloat(c_version);//当前
										content = "有最新版本了，服务器"+file_version+"是否替换当前版本"+version;
										System.out.println("content=============="+content);
										System.out.println("服务器:"+ server_version+"/当前:" + client_version);
										if (server_version > client_version) {
//									Toast.makeText(MainFragment.this, "提示更新", 200).show();
											Message message = new Message();
											message.what = 0;
											handler.sendMessage(message);
										} else if (server_version == client_version) {
											Message message = new Message();
											message.what = 1;
											handler.sendMessage(message);
										}
//								Toast.makeText(MainFragment.this, "没有提示更新", 200).show();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}, context);



						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
				break;
			case R.id.ll_softshare:
				dismiss();
				SoftWarePopuWindow(arg0, context);
				break;
			case R.id.ll_help:
				dismiss();
				Intent intent5 = new Intent(context, HelpActivity.class);
				context.startActivity(intent5);
				break;
			case R.id.ll_exit:
				wareDao.deleteAllShopCart();
				AppManager.getAppManager().finishAllActivity();
				break;
			default:
				break;
		}
	}

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

	// 程序版本更新
	private void dialog() {

		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage(content);
		System.out.println("content=============="+content);
//		builder.setMessage("检查到最新版本，是否要更新！");
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

	/*
	 * 从服务器中下载APK
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
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

	// 安装apk
	protected void installApk(File file) {
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");// 编者按：此处Android应为android，否则造成安装不了
		context.startActivity(intent);
	}

	@Override
	public void onReq(BaseReq arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResp(BaseResp arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 分享
	 *
	 * @param view2
	 * @param context
	 */
	private void SoftWarePopuWindow(View view2, final Context context) {
		try {
//			mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//			// inflater = LayoutInflater.from(context);
//			view = mLayoutInflater.inflate(R.layout.ware_infromation_share,null);
//			pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT, false);
//			final Dialog dlg = new Dialog(context, R.style.delete_pop_style);
//			pop.setBackgroundDrawable(new BitmapDrawable());
//			pop.setOutsideTouchable(true);

			spPreferences = context.getSharedPreferences("longuserset", context.MODE_PRIVATE);
			user_name = spPreferences.getString("user", "");
			user_id = spPreferences.getString("user_id", "");

			inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.ware_infromation_share, null);
			pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, false);
			// pop.setFocusable(true);
			// pop.setTouchable(true); // 设置PopupWindow可触摸
			//
			if (!pop.isShowing()) {
				pop.showAtLocation(view2, Gravity.BOTTOM, 0, 0);
			}
			btn_wechat = (ImageButton) view.findViewById(R.id.img_btn_wechat);
			btn_wx_friend = (ImageButton) view
					.findViewById(R.id.img_btn_wx_friend);
			btn_sms = (ImageButton) view.findViewById(R.id.img_btn_sms);
			img_btn_tencent = (ImageButton) view.findViewById(R.id.img_btn_tencent);
//			Button btn_holdr = (Button) view.findViewById(R.id.btn_holdr);
//			btn_holdr.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					pop.dismiss();
//				}
//			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// 新浪
		img_btn_tencent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				con(19, 1);
			}
		});

		// 微信
		btn_wechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				// progress.CreateProgress();
				con(16, 1);
			}
		});
		// 朋友圈
		btn_wx_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				con(17, 1);
			}
		});
		// 短信
		btn_sms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				con(18, 0);
			}
		});
	}

	private void con(final int index, int type) {
		try {

			String user_name = spPreferences.getString("user", "");
			String user_id = spPreferences.getString("user_id", "");
			String data = "http://183.62.138.31:1010/appshare/" + user_id + ".html";
			System.out.println("分享11======================" + data);
			String zhou = "云商聚下载地址,下载后可帮分享的好友获得积分" + data;

			System.out.println("==========" + zhou);
			softshareWxChat(zhou);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 微信分享
	 * @param text
	 */
	private void softshareWxChat(String text) {
		String temp[] = text.split("http");

		api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
//		webpage.webpageUrl = temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		//msg.title = "我发你一个软件,看看呗!";
		msg.title = "云商聚APP分享";
		msg.description = temp[0];
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),R.drawable.ysj_logn);
		msg.thumbData = Util.bmpToByteArray(thumb, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);

		System.out.println("微信注册" + flag);

	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

}
