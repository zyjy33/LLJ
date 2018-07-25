package com.lelinju.www;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.EditorInfo;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cricle.CircleActivity;
import com.android.cricle.GuaGuaActivity;
import com.android.cricle.JifenMainActivity;
import com.android.hengyu.post.PostMainActivity;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.example.taobaohead.BeanVo;
import com.example.taobaohead.headview.ScrollTopView;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.airplane.AirPlaneHomeActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.home.EndowmentBankActivity;
import com.hengyushop.demo.home.FenXiangActivity;
import com.hengyushop.demo.home.HealthGunaActivity;
import com.hengyushop.demo.home.JuDuiHuanActivity;
import com.hengyushop.demo.home.JuTouTiaoActivity;
import com.hengyushop.demo.home.JuTuanGou2Activity;
import com.hengyushop.demo.home.JuYouFangActivity;
import com.hengyushop.demo.home.JuYunshangActivity;
import com.hengyushop.demo.home.SouSuoSpActivity;
import com.hengyushop.demo.home.XinshouGyActivity;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.demo.train.TrainHomeActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.WareInformationData;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.PagerScrollView;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.zijunlin.Zxing.Demo.CaptureActivity;

public class HomeActivity extends Fragment {//implements UncaughtExceptionHandler {
	private WareDao wareDao;
	private ImageView img_user,iv_sousuo;
	private ImageView img_shared;
	private Thread thread;
	private Handler handler3;
	private Context context;
	private TextView img_demo3_1, img_demo3_0, img_demo2_0, img_demo2_1;
	private PagerScrollView home_main_scrool;
	private RelativeLayout home_title_layout;
	private EditText tv1;
	private LayoutInflater inflater;
	protected PopupWindow pop;
	private View view;
	private ImageButton btn_wechat;
	private View btn_sms;
	private View btn_wx_friend;
	private ImageButton img_btn_tencent;
	private IWXAPI api;
	private LinearLayout ll_jutoutiao,ll_sousuo;
	private ArrayList<BeanVo> list;
	ScrollTopView mytaobao;
	private MyPosterView advPager = null;
	private SharedPreferences spPreferences;
	String id;
	String title = "";
	String guanggao_url = "";
	public AQuery mAq;
	boolean panduan = false;
	View layout;
	public HomeActivity() {
		   
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layout = inflater.inflate(R.layout.hengyu_home, null);
		//在此调用下面方法，才能捕获到线程中的异常
//        Thread.setDefaultUncaughtExceptionHandler(this);
		tv1 = (EditText) layout.findViewById(R.id.tv1);
//		tv1.getBackground().setAlpha(50);
		ll_sousuo = (LinearLayout) layout.findViewById(R.id.ll_sousuo);
		ll_sousuo.getBackground().setAlpha(70);
		mAq = new AQuery(getActivity());
//		ll_sousuo.setBackgroundColor(getResources().getColor(R.color.no_color));
//		tv1.setBackgroundColor(getResources().getColor(R.color.no_color));
		 
		iv_sousuo = (ImageView) layout.findViewById(R.id.iv_sousuo);
		iv_sousuo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// if(detectMobile_sp()){
				Intent intent = new Intent(getActivity(), SouSuoSpActivity.class);
				String 	strwhere_zhi = tv1.getText().toString().trim(); 
				intent.putExtra("strwhere_zhi", strwhere_zhi);
				startActivity(intent);
				// }
			}
		});
		
		
		tv1.setOnEditorActionListener(new TextView.OnEditorActionListener(){  
			  
            @Override  
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {  
                // TODO Auto-generated method stub  
                if(arg1 == EditorInfo.IME_ACTION_SEARCH)  
                {  
//                    Toast.makeText(getActivity(),"呵呵",Toast.LENGTH_SHORT).show();  
                    // search pressed and perform your functionality.  
    				Intent intent = new Intent(getActivity(), SouSuoSpActivity.class);
    				String strwhere_zhi = tv1.getText().toString().trim(); 
    				intent.putExtra("strwhere_zhi", strwhere_zhi);
    				startActivity(intent);
                }  
                return false;  
            }  
  
        }); 
		
		img_user = (ImageView) layout.findViewById(R.id.img_user);
		img_shared = (ImageView) layout.findViewById(R.id.img_shared);
		 img_user.setBackgroundResource(R.drawable.saoyisao);
		 img_shared.setBackgroundResource(R.drawable.home_fx);
		 
		wareDao = new WareDao(getActivity());
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenHeight = dm.widthPixels;// 宽度height = dm.heightPixels ;
		
		initLayout(layout);
		loadWeather();
		
		spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
		return layout;
	}
	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		 //在此调用下面方法，才能捕获到线程中的异常
//        Thread.setDefaultUncaughtExceptionHandler(this);
//	}
//	public void uncaughtException(Thread arg0, Throwable arg1) {
//		// TODO Auto-generated method stub
//		 //在此处理异常， arg1即为捕获到的异常
//        Log.i("AAA", "uncaughtException   " + arg1);
//	}
	
//	 @Override  
//	 public void uncaughtException(Thread thread, Throwable ex) {  
//	       // 收集异常信息 并且发送到服务器  
////	       sendCrashReport(ex);  
//	       // 等待半秒  
//		//在此处理异常， arg1即为捕获到的异常
//         Log.i("AAA", "uncaughtException   " + ex);
//	       try {  
//	  
//	           Thread.sleep(500);  
//	  
//	       } catch (InterruptedException e) {  
//	  
//	           //  
//	  
//	       }  
//	       // 处理异常  
////	       handleException();  
//	    }  
	
	//聚头条
	private void loadWeather() {
		String strUrl = RealmName.REALM_NAME_LL+ "/get_article_category_id_list?channel_name=news&category_id=3" +
				"&top=10&strwhere=";
		System.out.println("聚优惠"+strUrl);
		AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				parse1(arg1);
			}
		}, null);
	}
	
	//聚头条
	private void parse1(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
//			System.out.println("===================="+st);
			list = new ArrayList<BeanVo>();
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				BeanVo data = new BeanVo();
				JSONObject object = jsonArray.getJSONObject(i);
				data.title = object.getString("title");
				data.img_url = object.getString("img_url");
				list.add(data);
			}
			Message msg = new Message();
			msg.what = 6;
			msg.obj = list;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	String user_name_weixin = "";
	String user_name_qq = "";
	String weixin = "";
	String qq = "";
	String nickname = "";
	String user_name = "";
	String user_name_phone = "";
	String user_name_3_wx = "";
	String user_name_3_qq = "";
	String user_name_3 = "";
	String user_name_key = "";
	String user_id;
	String datall;
	String oauth_name;
	String group_id;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
		nickname = spPreferences_login.getString("nickname", "");
		
		System.out.println("nickname================="+nickname);
		if (!nickname.equals("")) {
			getjianche();//后台检测是否绑定手机
		}else {
		    getuserxinxi();
		}
		
//		getTupian();//广告图片
		
//		mAq = new AQuery(getActivity());
//		mAq.clear();//清除首页内存
		
//		loadWeather();//聚头条
//		load_list();//聚精彩
		
		load_P();//聚优惠
	}
	private void getjianche() {
		// TODO Auto-generated method stub
		  SharedPreferences spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
			nickname = spPreferences_login.getString("nickname", "");
			String headimgurl = spPreferences_login.getString("headimgurl", "");
			String unionid = spPreferences_login.getString("unionid", "");
			String access_token = spPreferences_login.getString("access_token", "");
			String sex = spPreferences_login.getString("sex", "");
			
				System.out.println("UserLoginActivity====================="+UserLoginActivity.oauth_name);
				System.out.println("UserLoginWayActivity====================="+UserLoginWayActivity.oauth_name);
				
			if (UserLoginActivity.oauth_name.equals("weixin")) {
				oauth_name = "weixin";
			} else if (UserLoginWayActivity.oauth_name.equals("weixin")) {
				oauth_name = "qq";
			}
			
			System.out.println("nickname-----1-----"+nickname);
			String nick_name = nickname.replaceAll("\\s*", "");
			System.out.println("nick_name-----2-----"+nick_name);
			
			String strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register_0215?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+"" +
			"&province=&city=&country=&oauth_name="+oauth_name+"&oauth_access_token="+access_token+"&oauth_unionid="+unionid+"";
			
			System.out.println("我的======11======1======="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					System.out.println("我的======输出=====1========"+arg1);
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
//						if (status.equals("y")) {
							datall = object.getString("data");
//							JSONObject obj = object.getJSONObject("data");
//							data.id = obj.getString("id");
//							data.user_name = obj.getString("user_name");
//							province = obj.getString("province");
//							city = obj.getString("city");
//							area = obj.getString("area");
							
							System.out.println("datall=============="+datall);
							if (datall.equals("null")) {
								
								SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
								weixin = spPreferences_tishi.getString("weixin", "");
								qq = spPreferences_tishi.getString("qq", "");
								System.out.println("=================weixin==" + weixin);
								System.out.println("=================qq==" + qq);
								
								System.out.println("UserLoginActivity.panduan====1=="+UserLoginActivity.panduan_tishi);
								System.out.println("UserLoginWayActivity.panduan====2=="+UserLoginWayActivity.panduan_tishi);
								if (!nickname.equals("")) {
									
									if (UserLoginActivity.panduan_tishi == true) { 
										if (weixin.equals("weixin")) {
										}else {
											 Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
										     startActivity(intent1);
											 UserLoginActivity.panduan_tishi = false;
										}
										
									}else if (UserLoginWayActivity.panduan_tishi == true) {
										if (qq.equals("qq")) {
										}else {
											Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
									        startActivity(intent2);
											UserLoginWayActivity.panduan_tishi = false;
										}
									}
								}
								
							}else {
								UserRegisterllData data = new UserRegisterllData();
								JSONObject obj = object.getJSONObject("data");
								data.id = obj.getString("id");
								data.user_name = obj.getString("user_name");
								user_id = data.id;
								System.out.println("---data.user_name-------------------"+data.user_name);
								System.out.println("---user_id-------------------"+user_id);
								
								if (data.user_name.equals("微信")) {
//									if (data.id.equals("0")) {
										System.out.println("---微信还未绑定-------------------");
										
									}else {
										SharedPreferences spPreferences = getActivity().getSharedPreferences("longuserset", getActivity().MODE_PRIVATE);
										String user = spPreferences.getString("user", "");
										System.out.println("---1-------------------"+user);
										
										Editor editor = spPreferences.edit();
										editor.putString("user",data.user_name);
										editor.putString("user_id", data.id);
										editor.commit();
										
										String user_name = spPreferences.getString("user", "");
										System.out.println("---2-------------------"+user_name);
									}
							}
							getuserxinxi();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				};
			}, getActivity());
			
	}
	
	private void getuserxinxi() {
		// TODO Auto-generated method stub
		try {
			
		spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
		user_name_phone = spPreferences.getString("user", "");
		System.out.println("user_name_phone================"+user_name_phone);
		
		if (!user_name_phone.equals("")) {
//			user_name = user_name_phone;
			user_id = spPreferences.getString("user_id", "");
		} 
		else {
			user_name = "";
		}
		
		System.out.println("user_name================"+user_name);
		group_id = spPreferences.getString("group_id", "");
		System.out.println("======group_id======1======="+group_id);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 扫一扫
		 img_user.setOnClickListener(new OnClickListener() {
		 @Override
		 public void onClick(View arg0) {
			 System.out.println("======nickname============="+nickname);
			 System.out.println("user_name_phone========扫一扫========"+user_name_phone);
				if (!nickname.equals("")) {
					if (!user_name_phone.equals("")) {
						Intent Intent2 = new Intent(getActivity(),CaptureActivity.class);
						startActivity(Intent2);
					} else {
//						getjianche();//后台检测是否绑定手机
						Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
						startActivity(intent);
					}
				}else {
				if (user_name_phone.equals("")) {
					Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
					startActivity(intent48);
				}else {
//					String group_id = spPreferences.getString("group_id", "");
					System.out.println("group_id======1========="+group_id);
					if (group_id.equals("")) {
						Intent intent48 = new Intent(getActivity(), UserLoginActivity.class);
						startActivity(intent48);
					}else {
						Intent intent48 = new Intent(getActivity(), CaptureActivity.class);
//						intent48.putExtra("sp_sys", "3");
						startActivity(intent48);
					}
				}
				}
				
		 }
		 });
	}
	
	ArrayList<AdvertDao1> tempss;
	private Handler childHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			System.out.println("2=================");
			switch (msg.what) {
			case 0:
				tempss = (ArrayList<AdvertDao1>) msg.obj;
				ArrayList<String> urls = new ArrayList<String>();
				for(int i=0;i<tempss.size();i++){
					urls.add(tempss.get(i).getAd_url());
					System.out.println("tempss================="+tempss.get(i).getAd_url());
				}
//				addvie(context, tempss,urls);
				advPager.setData(urls, new MyPosterOnClick() {
					@Override
					public void onMyclick(int position) {
						// TODO Auto-generated method stub
						System.out.println("3=================");
						Message msg = new Message();
						msg.what = 13;
						msg.obj = tempss.get(position).getLink_url();
						handler.sendMessage(msg);
					}
				}, true, imageLoader, true);
				break;
			default:
				break;
			}
		};
	};
	/**
	 * 广告图片
	 */
	private void getTupian() {
		// TODO Auto-generated method stub
		try {
//			if (guanggao_url.equals("")) {
			//广告滚动	
			advPager = (MyPosterView) layout.findViewById(R.id.adv_pagerll);
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_adbanner_list?advert_id=11",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								System.out.println("加载滚动广告================="+arg1);
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONArray array = object.getJSONArray("data");
								int len = array.length();
								ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
								for (int i = 0; i < len; i++) {
									AdvertDao1 ada = new AdvertDao1();
									JSONObject json = array.getJSONObject(i);
									ada.setId(json.getString("id"));
									ada.setAd_url(json.getString("ad_url"));
									ada.setLink_url(json.getString("link_url"));
									guanggao_url = ada.getAd_url();
									ada.setAd_url(RealmName.REALM_NAME_HTTP + json.getString("ad_url"));
									images.add(ada);
								}
								Message msg = new Message();
								msg.obj = images;
								msg.what = 0;
								childHandler.sendMessage(msg);
								System.out.println("1=================");
								}else{
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, context);
			
//			}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	private ImageView yh0, yh3, yh6,  yh10,yh16,yh14,iv_xsgl;
	private TextView yh1, yh2, yh4, yh5, yh7, yh8,yh11,yh12,yh17, yh18,yh141,yh142;
	private LinearLayout yh_0, yh_1, yh_2, yh_3, yh_4,yh_5;

	private void initLayout(View layout) {
		try {
//		if (guanggao_url.equals("")) {
//			System.out.println("加载滚动广告=================");
		//广告滚动	
		advPager = (MyPosterView) layout.findViewById(R.id.adv_pagerll);
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_adbanner_list?advert_id=11",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							System.out.println("加载滚动广告================="+arg1);
							JSONObject object = new JSONObject(arg1);
							JSONArray array = object.getJSONArray("data");
							int len = array.length();
							ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
							for (int i = 0; i < len; i++) {
								AdvertDao1 ada = new AdvertDao1();
								JSONObject json = array.getJSONObject(i);
								ada.setId(json.getString("id"));
								ada.setAd_url(json.getString("ad_url"));
								ada.setLink_url(json.getString("link_url"));
								guanggao_url = ada.getAd_url();
								ada.setAd_url(RealmName.REALM_NAME_HTTP + json.getString("ad_url"));
								images.add(ada);
							}
							Message msg = new Message();
							msg.obj = images;
							msg.what = 0;
							childHandler.sendMessage(msg);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, context);
		
//		}
		
		//聚头条
		mytaobao = (ScrollTopView) layout.findViewById(R.id.mytaobao);
		ll_jutoutiao = (LinearLayout) layout.findViewById(R.id.ll_jutoutiao);
		
		ll_jutoutiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), JuTouTiaoActivity.class);
				startActivity(intent);
				
			}
		});
		
		mytaobao.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), JuTouTiaoActivity.class);
			startActivity(intent);
			
		}
	});
		//新手专享特惠
		iv_xsgl = (ImageView) layout.findViewById(R.id.iv_xsgl);//get_article_page_size_list
		iv_xsgl.setBackgroundResource(R.drawable.xxzxyh);
		
		iv_xsgl.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String xinshougl = "zhi";
				Intent intent= new Intent(getActivity(),XinshouGyActivity.class);
//				intent.putExtra("xsgl", xinshougl);
				startActivity(intent);
			}
		});
		
		yh_0 = (LinearLayout) layout.findViewById(R.id.yh_0);
		yh_1 = (LinearLayout) layout.findViewById(R.id.yh_1);
		yh_2 = (LinearLayout) layout.findViewById(R.id.yh_2);
		yh_3 = (LinearLayout) layout.findViewById(R.id.yh_3);
		yh_4 = (LinearLayout) layout.findViewById(R.id.yh_4);
		yh_5 = (LinearLayout) layout.findViewById(R.id.yh_5);

		yh0 = (ImageView) layout.findViewById(R.id.yh0);
		yh1 = (TextView) layout.findViewById(R.id.yh1);
		yh2 = (TextView) layout.findViewById(R.id.yh2);
		yh3 = (ImageView) layout.findViewById(R.id.yh3);
		yh4 = (TextView) layout.findViewById(R.id.yh4);
		yh5 = (TextView) layout.findViewById(R.id.yh5);
		yh6 = (ImageView) layout.findViewById(R.id.yh6);
		yh7 = (TextView) layout.findViewById(R.id.yh7);
		yh8 = (TextView) layout.findViewById(R.id.yh8);
		yh16 = (ImageView) layout.findViewById(R.id.yh16);
		yh17 = (TextView) layout.findViewById(R.id.yh17);
		yh18 = (TextView) layout.findViewById(R.id.yh18);
		yh14 = (ImageView) layout.findViewById(R.id.yh14);
		yh141 = (TextView) layout.findViewById(R.id.yh141);
		yh142 = (TextView) layout.findViewById(R.id.yh142);
		
		yh10 = (ImageView) layout.findViewById(R.id.yh10);
		yh11 = (TextView) layout.findViewById(R.id.yh11);
		yh12 = (TextView) layout.findViewById(R.id.yh12);
		
		img_demo2_0 = (TextView) layout.findViewById(R.id.img_demo2_0);
		img_demo2_1 = (TextView) layout.findViewById(R.id.img_demo2_1);
		home_main_scrool = (PagerScrollView) layout
				.findViewById(R.id.home_main_scrool);
		img_demo3_0 = (TextView) layout.findViewById(R.id.img_demo3_0);
		img_demo3_1 = (TextView) layout.findViewById(R.id.img_demo3_1);
		home_title_layout = (RelativeLayout) layout
				.findViewById(R.id.home_title_layout);
		img_shared = (ImageView) layout.findViewById(R.id.img_shared);
		img_user = (ImageView) layout.findViewById(R.id.img_user);
		img_rc5 = (ImageView) layout.findViewById(R.id.img_rc5);
		img_rc0 = (ImageView) layout.findViewById(R.id.img_rc0);
		one_buy = (LinearLayout) layout.findViewById(R.id.one_buy);
		img_rc1 = (ImageView) layout.findViewById(R.id.img_rc1);
		img_rc2 = (ImageView) layout.findViewById(R.id.img_rc2);
		img_rc3 = (ImageView) layout.findViewById(R.id.img_rc3);
		img_rc4 = (ImageView) layout.findViewById(R.id.img_rc4);
		sec_demo2 = (LinearLayout) layout.findViewById(R.id.sec_demo2);
		sec_demo3 = (LinearLayout) layout.findViewById(R.id.sec_demo3);
		img_demo2 = (ImageView) layout.findViewById(R.id.img_demo2);
		img_demo3 = (ImageView) layout.findViewById(R.id.img_demo3);
		layout2 = (LinearLayout) layout.findViewById(R.id.layout2);
//		layout2_1 = (LinearLayout) layout.findViewById(R.id.layout2_2);
		main_fragment_viewpager = (LinearLayout) layout
				.findViewById(R.id.main_fragment_viewpager);
		// TODO Auto-generated method stub
		posterView = new MyPosterView(context, null);
		main_fragment_viewpager.addView(posterView);
		second_main_l3 = (LinearLayout) layout
				.findViewById(R.id.second_main_l3);
		second_main_l2 = (LinearLayout) layout
				.findViewById(R.id.second_main_l2);
		second_main_l4 = (LinearLayout) layout
				.findViewById(R.id.second_main_l4);
		item0 = (LinearLayout) layout.findViewById(R.id.item0);
		item1 = (LinearLayout) layout.findViewById(R.id.item1);
		item2 = (LinearLayout) layout.findViewById(R.id.item2);
		vip0 = (LinearLayout) layout.findViewById(R.id.vip0);
		vip1 = (LinearLayout) layout.findViewById(R.id.vip1);
		item_jipiao = (ImageView) layout.findViewById(R.id.item_jipiao);
		item_huoche = (ImageView) layout.findViewById(R.id.item_huoche);
		item_menpiao = (ImageView) layout.findViewById(R.id.item_menpiao);
		item_chongzhi = (ImageView) layout.findViewById(R.id.item_chongzhi);
		vip0.setOnClickListener(clickListener);
		vip1.setOnClickListener(clickListener);
//		imageLoader.displayImage(
//				"drawable://" + R.drawable.second_main_jintiao, img_rc0);
//		imageLoader.displayImage("drawable://" + R.drawable.second_main_demo2,
//				img_demo2);
//		imageLoader.displayImage("drawable://" + R.drawable.second_main_demo3,
//				img_demo3);
//		imageLoader.displayImage("drawable://" + R.drawable.second_main_zhuan,
//				img_rc1);
//		imageLoader.displayImage("drawable://" + R.drawable.second_main_gua,
//				img_rc2);
//		imageLoader.displayImage(
//				"drawable://" + R.drawable.second_main_qiandao, img_rc3);
//		imageLoader.displayImage("drawable://" + R.drawable.second_main_shared,
//				img_rc4);
//		imageLoader.displayImage("drawable://"
//				+ R.drawable.second_main_chongzhi, item_chongzhi);
//		imageLoader.displayImage(
//				"drawable://" + R.drawable.second_main_menpiao, item_menpiao);
//		imageLoader.displayImage("drawable://" + R.drawable.second_main_demo3,
//				img_rc5);
//		imageLoader.displayImage("drawable://" + R.drawable.second_main_jipiao,
//				item_jipiao);
//		imageLoader.displayImage("drawable://" + R.drawable.second_main_huoche,
//				item_huoche);
//		load();
		item3 = (LinearLayout) layout.findViewById(R.id.item3);
		index_item6 = (LinearLayout) layout.findViewById(R.id.index_item6);
		index_item4 = (LinearLayout) layout.findViewById(R.id.index_item4);
		index_item7 = (LinearLayout) layout.findViewById(R.id.index_item7);
		index_item5 = (LinearLayout) layout.findViewById(R.id.index_item5);

		index_item1 = (LinearLayout) layout.findViewById(R.id.index_item1);
		index_item2 = (LinearLayout) layout.findViewById(R.id.index_item2);
		index_item3 = (LinearLayout) layout.findViewById(R.id.index_item3);
		index_item0 = (LinearLayout) layout.findViewById(R.id.index_item0);
		index_item4.setOnClickListener(clickListener);
		index_item7.setOnClickListener(clickListener);
		index_item6.setOnClickListener(clickListener);
		index_item5.setOnClickListener(clickListener);
		index_item1.setOnClickListener(clickListener);
		index_item2.setOnClickListener(clickListener);
		index_item3.setOnClickListener(clickListener);
		index_item0.setOnClickListener(clickListener);
		one_buy.setOnClickListener(clickListener);
		item0.setOnClickListener(clickListener);
		item1.setOnClickListener(clickListener);
		item2.setOnClickListener(clickListener);
		item3.setOnClickListener(clickListener);
		img_shared.setOnClickListener(clickListener);
		item_jipiao.setOnClickListener(clickListener);
		item_menpiao.setOnClickListener(clickListener);
		item_huoche.setOnClickListener(clickListener);
		item_chongzhi.setOnClickListener(clickListener);
		 
	/*	home_main_scrool.getView();
		home_main_scrool.setFocusable(true);
		home_main_scrool.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onTop() {
				// TODO Auto-generated method stub
				home_title_layout.setBackgroundColor(getResources()
						.getColor(R.color.no_color));
				home_main_scrool.setFocusable(true);
			}
			
			@Override
			public void onScroll() {
				// TODO Auto-generated method stub
				home_title_layout.setBackgroundResource(R.drawable.title);
				home_main_scrool.setFocusable(true);
			}
			
			@Override
			public void onBottom() {
				// TODO Auto-generated method stub
				home_main_scrool.setFocusable(true);
			}
		});*/
		home_main_scrool.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// TODO Auto-generated method stub
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_MOVE:
					System.out.println("滑动" + arg0.getScrollY());
					 if (arg0.getScrollY() <= 1) {
						 img_user.setBackgroundResource(R.drawable.saoyisao);
						 img_shared.setBackgroundResource(R.drawable.home_fx);
					 home_title_layout.setBackgroundColor(getResources().getColor(R.color.no_color));
//					 ll_sousuo.setBackgroundColor(getResources().getColor(R.color.no_color));
					 ll_sousuo.getBackground().setAlpha(70);
					 tv1.setBackgroundColor(getResources().getColor(R.color.no_color));
					 }else {
						 img_user.setBackgroundResource(R.drawable.sys_hs);
						 img_shared.setBackgroundResource(R.drawable.fx_hs);
					 home_title_layout.setBackgroundColor(getResources().getColor(R.color.white));
					 ll_sousuo.setBackgroundColor(getResources().getColor(R.color.baihuise));
					 tv1.setBackgroundColor(getResources().getColor(R.color.baihuise));
					 }
					break;
				}
				return false;
			}
		});
		home_main_scrool.setAlwaysDrawnWithCacheEnabled(true);
		format();
		
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("act", "PhoneIndexScrollImg");
//		params.put("yth", "");
//		params.put("key", "");
//		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getData.ashx", params,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int arg0, String arg1) {
//						super.onSuccess(arg0, arg1);
//						parse(arg1);
//					}
//				});
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 20://聚优坊
				try {
//				Intent intent20 = new Intent(getActivity(),ComboMainActivity.class);
				Intent intent20 = new Intent(getActivity(),JuYouFangActivity.class);
				startActivity(intent20);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 21://聚兑换
//				Intent intent221 = new Intent(getActivity(),LingjiaActivity.class);
				try {
//					Intent intent221 = new Intent(getActivity(),JuDuiHuanActivity.class);
//					startActivity(intent221);
					if (!nickname.equals("")) {
						if (!user_name_phone.equals("")) {
							Intent Intent2 = new Intent(getActivity(),JuDuiHuanActivity.class);
							startActivity(Intent2);
						} else {
						Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
						startActivity(intent);
						}
					}else {
					if (user_name_phone.equals("")) {
						Intent intentll = new Intent(getActivity(),UserLoginActivity.class);
						startActivity(intentll);
					} else {
						try {
							Intent intentll = new Intent(getActivity(),JuDuiHuanActivity.class);
							startActivity(intentll);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				break;
			case 22:
				try {
				Intent intent21 = new Intent(getActivity(),
						WideMarketActivity.class);
				startActivity(intent21);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				break;
			case 23:
				try {
				Intent intent22 = new Intent(getActivity(), NewWare.class);
				startActivity(intent22);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
				break;
			case 111://健康馆
//				Toast.makeText(getActivity(), "功能正在完善", 200).show();
//				Intent intent111 = new Intent(getActivity(), OneBuyActivity.class);
				Intent intent111 = new Intent(getActivity(), HealthGunaActivity.class);
				startActivity(intent111);
				break;
			case 43://赚聚币
				try {
//				Toast.makeText(getActivity(), "功能正在完善", 200).show();
				Intent intent43 = new Intent(getActivity(),TuiGuang2Activity.class);
				startActivity(intent43);
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 11://聚云商
				try {
				Intent intent11 = new Intent(getActivity(),JuYunshangActivity.class);
				startActivity(intent11);
//				Toast.makeText(getActivity(), "功能正在完善", 200).show();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				break;
			case 12:
				try {
					if (!nickname.equals("")) {
						if (!user_name_phone.equals("")) {
							Intent Intent2 = new Intent(getActivity(),JuTuanGou2Activity.class);
							startActivity(Intent2);
						} else {
						Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
						startActivity(intent);
						}
					}else {
					if (user_name_phone.equals("")) {
						Intent intentll = new Intent(getActivity(),UserLoginActivity.class);
						startActivity(intentll);
					} else {
						try {
							Intent intentll = new Intent(getActivity(),JuTuanGou2Activity.class);
							startActivity(intentll);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}
					}
//				Intent intent12 = new Intent(getActivity(),JuTuanGou2Activity.class);
//				startActivity(intent12);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
				break;
			case 13:
				try {
					    String link_url = (String) msg.obj;
						System.out.println("link_url============="+link_url);
						Intent intent13 = new Intent(getActivity(), Webview1.class);
						intent13.putExtra("link_url", link_url);
						startActivity(intent13);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 6:
					mytaobao.setData(list);
				break;
			case 15:
				
				if (!nickname.equals("")) {
					if (!user_name_phone.equals("")) {
						Intent Intent2 = new Intent(getActivity(),FenXiangActivity.class);
						startActivity(Intent2);
					} else {
					Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
					startActivity(intent);
					}
				}else {
				if (user_name_phone.equals("")) {
					Intent intentll = new Intent(getActivity(),UserLoginActivity.class);
					startActivity(intentll);
				} else {
					try {
//					SoftWarePopuWindow(img_shared, context);
						Intent intentll = new Intent(getActivity(),FenXiangActivity.class);
						startActivity(intentll);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				}
				break;
			case 110:
				try {
					
				final ArrayList<WareInformationData> datas = (ArrayList<WareInformationData>) msg.obj;
				int len = datas.size();
				if (len > 0) {
					try {
					System.out.println("点击了=================");
//					imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ datas.get(0).img_url, yh0);
					mAq.id(yh0).image(RealmName.REALM_NAME_HTTP+datas.get(0).img_url);
					yh1.setText(datas.get(0).title);
					yh2.setText("￥" + datas.get(0).sell_price);
					// ---
//					imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ datas.get(1).img_url, yh3);
					mAq.id(yh3).image(RealmName.REALM_NAME_HTTP+datas.get(1).img_url);
					yh4.setText(datas.get(1).title);
					yh5.setText("￥" + datas.get(1).sell_price);
					// -
//					imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ datas.get(2).img_url, yh6);
					mAq.id(yh6).image(RealmName.REALM_NAME_HTTP+datas.get(2).img_url);
					yh7.setText(datas.get(2).title);
					yh8.setText("￥" + datas.get(2).sell_price);
					
//					imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ datas.get(3).img_url, yh16);
					mAq.id(yh16).image(RealmName.REALM_NAME_HTTP+datas.get(3).img_url);
					yh17.setText(datas.get(3).title);
					yh18.setText("￥" + datas.get(3).sell_price);
					
//					imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ datas.get(4).img_url, yh10);
					mAq.id(yh10).image(RealmName.REALM_NAME_HTTP+datas.get(4).img_url);
					yh11.setText(datas.get(4).title);
					yh12.setText("￥" + datas.get(4).sell_price);
					
//					imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ datas.get(5).img_url, yh14);
					mAq.id(yh14).image(RealmName.REALM_NAME_HTTP+datas.get(5).img_url);
					yh141.setText(datas.get(5).title);
					yh142.setText("￥" + datas.get(5).sell_price);
					
					
					yh_0.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
//							Message msg = new Message();
//							msg.what = 30;
//							msg.arg1 = datas.get(0).id;
//							handler.sendMessage(msg);
							String id = Integer.toString(datas.get(0).id);
							System.out.println("====================="+id);
							Intent intent30 = new Intent(getActivity(),WareInformationActivity.class);
							intent30.putExtra("id", id);
							startActivity(intent30);
						}
					});
					//
					
					yh_1.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							String id = Integer.toString(datas.get(1).id);
							System.out.println("====================="+id);
							Intent intent30 = new Intent(getActivity(),WareInformationActivity.class);
							intent30.putExtra("id", id);
							startActivity(intent30);
						}
					});
					//
					yh_2.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							String id = Integer.toString(datas.get(2).id);
							System.out.println("====================="+id);
							Intent intent30 = new Intent(getActivity(),WareInformationActivity.class);
							intent30.putExtra("id", id);
							startActivity(intent30);
						}
					});
					yh_3.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							String id = Integer.toString(datas.get(3).id);
							System.out.println("====================="+id);
							Intent intent30 = new Intent(getActivity(),WareInformationActivity.class);
							intent30.putExtra("id", id);
							startActivity(intent30);
						}
					});
					yh_4.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							String id = Integer.toString(datas.get(4).id);
							System.out.println("====================="+id);
							Intent intent30 = new Intent(getActivity(),WareInformationActivity.class);
							intent30.putExtra("id", id);
							startActivity(intent30);
						}
					});
					yh_5.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {
							String id = Integer.toString(datas.get(5).id);
							System.out.println("====================="+id);
							Intent intent30 = new Intent(getActivity(),WareInformationActivity.class);
							intent30.putExtra("id", id);
							startActivity(intent30);
						}
					});
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 30:
				String id = (String) msg.obj;
				Intent intent30 = new Intent(getActivity(),WareInformationActivity.class);
//				intent30.putExtra("id", Integer.parseInt(id));
				System.out.println("====================="+id);
				intent30.putExtra("id", id);
//				intent30.putExtra("tag", "1");
				startActivity(intent30);
				break;
			case 24:
				Intent intent24 = new Intent(getActivity(),
						RechargeActivity.class);
				startActivity(intent24);
				break;
			case 28:
				Intent intent10 = new Intent(getActivity(),
						CircleActivity.class);
				startActivity(intent10);
				break;
			case 0:
				Intent intent = new Intent(getActivity(), NewWare.class);
				startActivity(intent);
				break;
			case 2:
				Intent intent2 = new Intent(getActivity(),
						PostMainActivity.class);
				startActivity(intent2);
				break;
			case 14:
				Intent intent4 = new Intent(getActivity(),
						MenuAdvertActivity.class);
				intent4.putExtra("name", "热卖榜单");
				intent4.putExtra("index", 1);
				startActivity(intent4);
				break;
			case 9:

				break;
			case 10:
				Intent intent110 = new Intent(getActivity(),
						CircleActivity.class);
				startActivity(intent110);
				break;
			case 3:
				Intent intent3 = new Intent(getActivity(),
						RechargeActivity.class);
				startActivity(intent3);
				break;
			// case 15:
			// SoftWarePopuWindow(imageView, IndexContent0.this);
			// break;
			case 27:
				Intent intent27 = new Intent(getActivity(), MenuActivity.class);
				startActivity(intent27);
				break;
			case 29:
				Intent intent29 = new Intent(getActivity(),
						SoftUpdataActivity.class);
				startActivity(intent29);
				break;
			case 31:
				Intent intent31 = new Intent(getActivity(),
						JifenMainActivity.class);
				startActivity(intent31);
				break;
			// 一下是云商聚的handler
			case 40:
				System.out.println("40");
				Intent intent40 = new Intent(getActivity(),
						CircleActivity.class);
				startActivity(intent40);
				break;
			case 41:
				System.out.println("41");
				Intent intent41 = new Intent(getActivity(),
						GuaGuaActivity.class);
				startActivity(intent41);
				break;
			case 42:
				Intent intent42 = new Intent(getActivity(),
						JifenMainActivity.class);
				startActivity(intent42);
				break;

			case 44:
				Intent intent44 = new Intent(getActivity(),
						RechargeActivity.class);
				startActivity(intent44);
				break;
			case 45:
				break;
			case 46:
				Intent intent46 = new Intent(getActivity(),
						AirPlaneHomeActivity.class);
				startActivity(intent46);
				break;
			case 47:
				Intent intent47 = new Intent(getActivity(),
						TrainHomeActivity.class);
				startActivity(intent47);
				break;
			case 48://养老银行
//				Intent intent48 = new Intent(getActivity(), VIPActivity.class);
				
//				String user = spPreferences.getString("user", "");
				if (!nickname.equals("")) {
					if (!user_name_phone.equals("")) {
						Intent Intent2 = new Intent(getActivity(),EndowmentBankActivity.class);
						startActivity(Intent2);
					} else {
					Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
					startActivity(intent1);
					}
				}else {
				if (user_name_phone.equals("")) {
					Intent intent1 = new Intent(getActivity(), UserLoginActivity.class);
					startActivity(intent1);
				}else {
					Intent intent48 = new Intent(getActivity(), EndowmentBankActivity.class);
					startActivity(intent48);
				}
				}
				break;

			default:
				break;
			}
		};
	};
	
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	public void onPause() {
		super.onPause();
		if (posterView != null) {

			posterView.puseExecutorService();
		}
	};
	

	// 将安装的聊天软件存至SD卡上面
	public boolean copyApkFromAssets(Context context, String fileName,
			String path) {
		boolean copyIsFinish = false;
		try {
			InputStream is = context.getAssets().open(fileName);
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
			copyIsFinish = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return copyIsFinish;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == -1) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				Toast.makeText(getActivity(), contents + "\n" + format, 200)
						.show();
			} else if (resultCode == 0) {

			}
		}
	}

	private ImageLoader imageLoader;
	private String key;
	private String yth;
	public HomeActivity(ImageLoader imageLoader,
			Context context,String key,String yth) {
		this.imageLoader = imageLoader;
		this.context = context;
		this.key = key;
		this.yth = yth;
	}

	@SuppressWarnings("deprecation")
	private ImageView item_jipiao, item_huoche, item_menpiao, item_chongzhi,
			img_demo2, img_demo3;
	private LinearLayout vip0, vip1, second_main_l4, second_main_l3,
			second_main_l2, item0, item1, item2, item3, layout2, layout2_1,
			index_item4, index_item0, index_item1, index_item2, index_item3,
			index_item6, index_item7, index_item5;
	private int screenHeight;
	private LinearLayout sec_demo2, sec_demo3, one_buy;
	private ImageView img_rc0, img_rc1, img_rc2, img_rc3, img_rc4, img_rc5;
	private int scalWidth = 0;

	private void format() {
		double scale_b = (double) 341 / 583;
		double layout2_height = screenHeight * scale_b;
		layout2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				(int) layout2_height));
		// -----------
		double scale_b1 = (double) 444 / 610;
		double layout2_1height = screenHeight * scale_b1;
//		layout2_1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
//				(int) layout2_1height));
		// --------

		double viewPager = (double) 0 / 0;
		double viewPager_height = viewPager * screenHeight;
		main_fragment_viewpager.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				(int) viewPager_height));
		double l2 = (double) 90 / 583;
		double l2_height = l2 * screenHeight;
		System.out.println("l2_height:" + l2_height);

		double l3 = (double) 130 / 583;
		double l3_height = l3 * screenHeight;
		second_main_l2.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, (int) l2_height));
		second_main_l3.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, (int) l3_height));
		second_main_l4.setLayoutParams(new LayoutParams(
				LayoutParams.MATCH_PARENT, (int) l3_height));

		double l4 = (double) 237 / 583;
		double l4_height = l4 * screenHeight;
		sec_demo2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				(int) l4_height));
		sec_demo3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				(int) l4_height));
	}

	private LinearLayout main_fragment_viewpager;
	private MyPosterView posterView;


	private void load_P() {
		System.out.println("加载聚优惠================================");
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_top_list?channel_name=goods&top=6&strwhere=is_top=1"
					,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
//						System.out.println("值是多少11===================="+arg1);
						parse2(arg1);
					}
				}, context);
	}

	private void parse2(String st) {
		try {
//			System.out.println("值是多少===================="+st);
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			ArrayList<WareInformationData> datas = new ArrayList<WareInformationData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				WareInformationData data = new WareInformationData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.id = object.getInt("id");
				data.img_url = object.getString("img_url");
				data.title = object.getString("title");
				title = data.title;
				data.sell_price = object.getString("sell_price");
				datas.add(data);
			}
			Message msg = new Message();
			msg.what = 110;
			msg.obj = datas;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

//	private Handler handler2 = new Handler() {
//		public void dispatchMessage(Message msg) {
//			switch (msg.what) {
//
//			case 1:
//				final ArrayList<String> temp = (ArrayList<String>) msg.obj;
//				posterView.setData(temp, new MyPosterOnClick() {
//					@Override
//					public void onMyclick(int position) {
//						// TODO Auto-generated method stub
//						System.out.println("dianji");
//					}
//				}, true, 5000, imageLoader, true);
//				break;
//
//			default:
//				break;
//			}
//		};
//	};
//	private void parse(String result) {
//		try {
//			JSONObject object = new JSONObject(result);
//			JSONArray array = object.getJSONArray("Data");
//			int len = array.length();
//			ArrayList<String> images = new ArrayList<String>();
//			for (int i = 0; i < len; i++) {
//				AdvertDao1 ada = new AdvertDao1();
//				JSONObject json = array.getJSONObject(i);
//				ada.setId(json.getString("productItemId"));
//				ada.setImage(RealmName.REALM_NAME + "/"
//						+ json.getString("AdvertisingImgShowUrl"));
//				ada.setDetail(RealmName.REALM_NAME + "/"
//						+ json.getString("AdvertisingDetailImgUrl"));
//				ada.setSeri(json.getString("AdvertisingSerialNumber"));
//				// images.add(ada);
//				images.add(RealmName.REALM_NAME + "/"
//						+ json.getString("AdvertisingImgShowUrl"));
//			}
//			Message msg = new Message();
//			msg.obj = images;
//			msg.what = 1;
//			handler2.sendMessage(msg);
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	}

	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.index_item0:
				handler.sendEmptyMessage(111);
				break;
			case R.id.index_item1:
				handler.sendEmptyMessage(48);
				break;
			case R.id.index_item2:
				handler.sendEmptyMessage(12);
				break;
			case R.id.index_item3:
				handler.sendEmptyMessage(43);
				break;
			case R.id.one_buy:
				handler.sendEmptyMessage(111);
				break;
			case R.id.img_shared:
//				handler3.sendEmptyMessage(-1);
				handler.sendEmptyMessage(15);
				break;
//			case R.id.img_shared:
//				handler.sendEmptyMessage(15);
//				break;	
			case R.id.vip1:
				handler.sendEmptyMessage(12);
				break;
			case R.id.vip0:
				handler.sendEmptyMessage(48);
				break;
			case R.id.index_item4:
				handler.sendEmptyMessage(23);
				break;
			case R.id.index_item5:
				handler.sendEmptyMessage(20);
				break;
			case R.id.index_item6:
				handler.sendEmptyMessage(11);
				break;
			case R.id.index_item7:
				handler.sendEmptyMessage(21);
				break;
			case R.id.item0:
				handler.sendEmptyMessage(40);
				// handler.sendEmptyMessage(11);
				break;
			case R.id.item1:
				handler.sendEmptyMessage(41);
				// handler.sendEmptyMessage(2);
				break;
			case R.id.item2:
				handler.sendEmptyMessage(42);
				break;
			case R.id.item3:
				handler.sendEmptyMessage(43);
				break;
			case R.id.item_chongzhi:
				handler.sendEmptyMessage(44);
				break;
			case R.id.item_menpiao:
				handler.sendEmptyMessage(45);
				break;
			case R.id.item_jipiao:
				handler.sendEmptyMessage(46);
				break;
			case R.id.item_huoche:
				handler.sendEmptyMessage(47);
				break;
			default:
				break;
			}
		}
	};

}
