package com.lelinju.www;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.example.downloadandnotificationbar.UpdateApkThread;
import com.heathsafy.www.base.AppManager;
import com.heathsafy.www.base.BaseFragmentActivity;
import com.hengyushop.demo.activity.ZhongAnMinShenActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.MyShopPingCarActivity;
import com.hengyushop.demo.service.YunshangServiceActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * 主页
 * @author Administrator
 *
 */
public class MainFragmentActivity extends BaseFragmentActivity {
	private FragmentManager manager;
	private RadioGroup main_bottom_rtns;
	private RadioButton main_bottom_rtn0, main_bottom_rtn1, main_bottom_rtn11,main_bottom_rtn2,
			main_bottom_rtn3;
	private long exitTime = 0;
	public static boolean zhuangtai = false;
	private String URL;
	private String packgeName;
	private MyUncaughtExceptionHandler uncaughtExceptionHandler;
    private String strUr2 = RealmName.REALM_NAME_LL + "/get_apk_version?browser=android";
	private void exit(){

		if ((System.currentTimeMillis() - exitTime) > 2000) // System.currentTimeMillis()无论何时调用，肯定大于2000
		{
			Toast.makeText(getApplicationContext(), "再按一次退出程序",Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
			
		} else {
			AppManager.getAppManager().finishAllActivity();
			Log.i("aa","2");

		}
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
		}
		return true;
	}

//	private void initfragment() {
//        BlankFragment fragment=new BlankFragment();
//        getSupportFragmentManager().beginTransaction().add(R.id.main_titny,fragment,"").commit();
//    }
	
	private void init() {
		main_bottom_rtns = (RadioGroup) findViewById(R.id.main_bottom_rtns);
		main_bottom_rtn0 = (RadioButton) findViewById(R.id.main_bottom_rtn0);
		main_bottom_rtn1 = (RadioButton) findViewById(R.id.main_bottom_rtn1);
		main_bottom_rtn11 = (RadioButton) findViewById(R.id.main_bottom_rtn11);
		main_bottom_rtn2 = (RadioButton) findViewById(R.id.main_bottom_rtn2);
		main_bottom_rtn3 = (RadioButton) findViewById(R.id.main_bottom_rtn3);
		main_bottom_rtns.setOnCheckedChangeListener(listener);
		main_bottom_rtn0.setChecked(true);
		
		if (zhuangtai == false) {
			handler.sendEmptyMessage(-2);//版本更新
		}
	}



	
	private Drawable mActionBarBackgroundDrawable;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		/**
		 * requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		 * setContentView(R.layout.combo_listview);
		 * getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		 * R.layout.title_image);
		 */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fragment_lelinju_activity);
//		((Location) getApplicationContext()).reloadClient();//停止客户端
//		((Location) getApplicationContext()).startLocation();//开始位置
		manager = getSupportFragmentManager();//支持碎片管理
		init();
		
		
		
		
		packgeName = getPackageName();
		cauchException();
		initImageLoader(getApplicationContext());
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private OnCheckedChangeListener listener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				FragmentTransaction transaction = manager.beginTransaction();
				Fragment fragment = getFragment(arg1);
				transaction.replace(R.id.main_content, fragment);
				transaction.commitAllowingStateLoss();
		}
	};
	
	public Fragment getFragment(int what) {
		Fragment fragment = null;
		DisplayMetrics dm = new DisplayMetrics();//显示度量
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		switch (what) {
		case R.id.main_bottom_rtn0:
		case 0:
			//精彩养护
			fragment = new MainFragmentContent1();
			break;
		case R.id.main_bottom_rtn1:
		case 1:
			//健康困扰
			fragment = new YunshangServiceActivity();
			//fragment = new MainFragmentContent0();
			break;
		case R.id.main_bottom_rtn11:
		case 2:
			//活动
			fragment = new ZhongAnMinShenActivity();
			//fragment = new MainFragmentContent0();
			break;
		case R.id.main_bottom_rtn2:
		case 3:
			//我与家人
			fragment = new MyShopPingCarActivity();
			//fragment = new MainFragmentContent0();
			break;
		case R.id.main_bottom_rtn3:
		case 4:
			//身体调理
			fragment = new MainFragmentContent4();
//			fragment = new IndividualCenterActivity();
			break;

		default:
			break;
		}
		return fragment;
	}
	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case -2:
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
//							URL = "http://183.62.138.31:1010/tools/downapk.ashx?id="+id+"";
							//http://www.ju918.com/apkdown/YSJ_apk/YunSJ.apk
							System.out.println("首页版本URL=============="+URL);
							String c_version = getAppVersionName(getApplicationContext()).trim().replaceAll("\\.", "");
							float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//服务器
							float client_version = Float.parseFloat(c_version);//当前
							
							System.out.println("1当前:" + client_version + "1服务器:"+ server_version);
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
			case 0:
				dialog();
				break;
			default:
				break;
			}
		};
	};
	
	// 程序版本更新
		private void dialog() {
			System.out.println("首页版本==============");
			AlertDialog.Builder builder = new Builder(this);
//			builder.setMessage(updatainfo);
			builder.setMessage("是否更新最新版本！");
			builder.setTitle("提示:新版本");
			builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					final String filePath = Environment.getExternalStorageDirectory() + "/ss";
					new UpdateApkThread("http://mobile.lelinju.shop/upload/201709/23/201709231119249507.APK", filePath, "LeLinJu.apk",MainFragmentActivity.this).start();
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
	
	/*
	 * 从服务器中下载APK
	 */
	protected void downLoadApk() {
		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
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
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	// 安装apk
	protected void installApk(File file) {
		MainFragmentActivity.zhuangtai = false;
		UserLoginActivity.zhuangtai = false;
		PersonCenterActivity.zhuangtai = false;
		Intent intent = new Intent();
		// 执行动作
		intent.setAction(Intent.ACTION_VIEW);
		// 执行的数据类型
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");// 此处Android应为android，否则造成安装不了
		startActivity(intent);
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
	
	private void cauchException() {
		System.out.println("-----------------------------------------------------");
		 
		// 程序崩溃时触发线程
		uncaughtExceptionHandler = new MyUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
	}

	// 创建服务用于捕获崩溃异常
	private class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			// 保存错误日志
			saveCatchInfo2File(ex);
 
			// 关闭当前应用
			AppManager.getAppManager().finishAllActivity();
		}
	};

	/**
	 * 保存错误信息到文件中
	 * 
	 * @return 返回文件名称
	 */
	private String saveCatchInfo2File(Throwable ex) {
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String sb = writer.toString();
		try {
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String time = formatter.format(new Date());
			String fileName = time + ".txt";
			System.out.println("fileName:" + fileName);
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				String filePath = Environment.getExternalStorageDirectory() + "/ysj/" + packgeName
						+ "/crash/";
				File dir = new File(filePath);
				if (!dir.exists()) {
					if (!dir.mkdirs()) {
						// 创建目录失败: 一般是因为SD卡被拔出了
						return "";
					}
				}
				System.out.println("filePath + fileName:" + filePath + fileName);
				FileOutputStream fos = new FileOutputStream(filePath + fileName);
				fos.write(sb.getBytes());
				fos.close();
				//文件保存完了之后,在应用下次启动的时候去检查错误日志,发现新的错误日志,就发送给开发者
			}
			return fileName;
		} catch (Exception e) {
			System.out.println("an error occured while writing file..." + e.getMessage());
		}
		return null;
	}
	/**
	 * 装载照片加载驱动
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory().cacheOnDisc().build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(defaultOptions)
				.threadPriority(Thread.MAX_PRIORITY).threadPoolSize(8).memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				 .discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO) // Not
																// necessary
																// in
																// common
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
