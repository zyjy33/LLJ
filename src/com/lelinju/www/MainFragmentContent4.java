package com.lelinju.www;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.ctrip.openapi.java.utils.BitUtil;
import com.example.taobaohead.headview.RoundImageView;
import com.example.uploadpicdemo.Utils;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.home.FenXiangActivity;
import com.hengyushop.demo.my.ChuangKeActivity;
import com.hengyushop.demo.my.ChuangKeUserActivity;
import com.hengyushop.demo.my.JuJuFaActivity;
import com.hengyushop.demo.my.MyAssetsActivity;
import com.hengyushop.demo.my.MyJuDuiHuanActivity;
import com.hengyushop.demo.my.MyJuTuanActivity;
import com.hengyushop.demo.my.MyOrderActivity;
import com.hengyushop.demo.my.MyQianBaoActivity;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.demo.user.MobilePhoneActivity;
import com.hengyushop.entity.MyOrderData;
import com.hengyushop.entity.OrderTypeData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.UserRegisterllData;
import com.lglottery.www.widget.NewDataToast;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.sdk.openapi.IWXAPI;

public class MainFragmentContent4 extends Fragment implements OnClickListener {
	private LinearLayout img_address, img_financemanage, img_btn_chongzhi,
			img_btn_collect_ware, img_btn_order, caidan, zhuxiao,
			img_btn_shared;
	private TextView tv_unpay, tv_delivered, tv_received, tv_payed, tv_name,tv_weixin_name,
			tv_usernum, tv_usertag, tv_ticket, tv_shop_ticket, tv_jifen_ticket,
			tv_djjifen_ticket, is_vip,agency_name,tv_chuangke,tv_jiazhibi,tv_quanbu;
	private ImageView img_head;
	private LinearLayout ll_quanbu,ll_unpay, ll_delivered, ll_received, ll_payed,
			ll_user,ll_chuangke,ll_user_weixin;
	private RelativeLayout rl_time1,rl_time2,rl_time3,rl_time4,rl_time5,rl_time6,rl_time7,
	rl_time8,rl_time9,rl_time10,rl_time11,rl_time12;
	private WareDao wareDao;
	// private DialogProgress progress;
	private OrderTypeData typeData;
	private UserRegisterData registerData;
	private MyPopupWindowMenu popupWindowMenu;
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的
	private ImageLoader imageLoader;
	private Handler handler2;
	private Context context;
	private Button person_login_un_btn;
	private LinearLayout lau0;
	private RelativeLayout lau1;
	private LayoutInflater inflater;
	private View view;
	private PopupWindow pop;
	private IWXAPI api;
	private SharedPreferences spPreferences;
	String strUrlone;
	private String huiyuan0;
	private String huiyuan1;
	private String group_id; 
	String user_id,headimgurl,access_token,sex,unionid;
	ArrayList<MyOrderData> list;
	private String  key, str, str2;
	private List<UserRegisterllData> lists = new ArrayList<UserRegisterllData>();
	private ImageLoader mImageLoader;
	private DialogProgress progress;
	public static String yth,login_sign,agencygroupid,storegroupid,shopsgroupid,salesgroupid;
//	private LinearLayout index_item0, index_item1, index_item2, index_item3;
	RoundImageView networkImage,imv_user_photo;
	String zhou;
	FrameLayout fl_buju;
	UserRegisterllData data;
	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;
	public static String path,time,area,lng,lat,imagePath,tupian;
	Bitmap photo;
	private SharedPreferences spPreferences_login;
	String nickname = "";
	String user_name_weixin = "";
	String user_name_qq = "";
	String weixin = "";
	String qq = "";
	String headimgurl2 = "";
	String user_name = "";
	String user_name_phone = "";
	String user_name_3_wx = "";
	String user_name_3_qq = "";
	String user_name_3 = "";
	String user_name_key = "";
	String oauth_name;
	String datall;
	String province = "";
	String city = "";
	String country = "";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layout = inflater.inflate(R.layout.user_individual_center_ll, null);
		popupWindowMenu = new MyPopupWindowMenu(getActivity());
		progress = new DialogProgress(getActivity());
		shoujihao_type = false;
		example(layout);
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
	
	public void onResume() {
		super.onResume();
		try {
			lau0.setBackgroundResource(R.drawable.my_bj);
			lau1.setBackgroundResource(R.drawable.my_bj);
			
		spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
		nickname = spPreferences_login.getString("nickname", "");
		headimgurl = spPreferences_login.getString("headimgurl", "");
		headimgurl2 = spPreferences_login.getString("headimgurl2", "");
		
		System.out.println("nickname================="+nickname);
		if (!nickname.equals("")) {
			getjianche();//后台检测是否绑定手机
		}else {
			getuserxinxi();
		}
		
		} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	    }
	};
	boolean shoujihao_type = false;
	private void getjianche() {
		// TODO Auto-generated method stub
			spPreferences_login = getActivity().getSharedPreferences("longuserset_login", Context.MODE_PRIVATE);
			
			nickname = spPreferences_login.getString("nickname", "");
			headimgurl = spPreferences_login.getString("headimgurl", "");
			unionid = spPreferences_login.getString("unionid", "");
			access_token = spPreferences_login.getString("access_token", "");
			sex = spPreferences_login.getString("sex", "");
			String oauth_openid = spPreferences_login.getString("oauth_openid", "");
				System.out.println("UserLoginActivity====================="+UserLoginActivity.oauth_name);
				System.out.println("UserLoginWayActivity====================="+UserLoginWayActivity.oauth_name);
				
			if (UserLoginActivity.oauth_name.equals("weixin")) {
				oauth_name = "weixin";
			} else if (UserLoginWayActivity.oauth_name.equals("weixin")) {
				oauth_name = "qq";
				unionid = "";
			}
			
			System.out.println("nickname-----1-----"+nickname);
			String nick_name = nickname.replaceAll("\\s*", "");//去除空格
			
			
			System.out.println("nick_name-----2-----"+nick_name);
			
//			String strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register_0215?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+"" +
//			"&province="+province+"&city="+city+"&country="+country+"&oauth_name="+oauth_name+"&oauth_access_token="+access_token+"" +
//					"&oauth_unionid="+unionid+"";
			
			String strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register_0217?nick_name="+nick_name+"&sex="+sex+"&avatar="+headimgurl+"" +
					"&province="+province+"&city="+city+"&country="+country+"&oauth_name="+oauth_name+"&oauth_unionid="+unionid+"" +
							"&oauth_openid="+oauth_openid+"";
			System.out.println("我的======11======1======="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					System.out.println("我的======输出=====1========"+arg1);
					try {
						JSONObject object = new JSONObject(arg1);
						object.getString("status");
						object.getString("info");
//						if (status.equals("y")) {
							datall = object.getString("data");
//							JSONObject obj = object.getJSONObject("data");
//							data.id = obj.getString("id");
//							data.user_name = obj.getString("user_name");
//							province = obj.getString("province");
//							city = obj.getString("city");
//							area = obj.getString("area");
							
							
							System.out.println("shoujihao_type=============="+shoujihao_type);
							System.out.println("datall=============="+datall);
							if (datall.equals("null")) {
//								if (shoujihao_type == false) {
//									 Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
//								     startActivity(intent1);
//								     shoujihao_type = true;
//								 }else {
//								    shoujihao_type = false;
//								 }
								
								SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
								weixin = spPreferences_tishi.getString("weixin", "");
								qq = spPreferences_tishi.getString("qq", "");
								System.out.println("=================weixin==" + weixin);
								System.out.println("=================qq==" + qq);
								System.out.println("nickname----------"+nickname);
								System.out.println("UserLoginActivity.panduan====1=="+UserLoginActivity.panduan_tishi);
								System.out.println("UserLoginWayActivity.panduan====2=="+UserLoginWayActivity.panduan_tishi);
//							    if (shoujihao_type == false) {
								if (!nickname.equals("")) {
									
									if (UserLoginActivity.panduan_tishi == true) { 
										if (weixin.equals("weixin")) {
//											 Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
//										     startActivity(intent1);
//											 UserLoginActivity.panduan_tishi = false;
//											SharedPreferences spPreferences_tishi_wx = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
//											spPreferences_tishi_wx.edit().clear().commit();//第三方授权登录提示绑定手机号信息清空
										}else {
											 Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
										     startActivity(intent1);
											 UserLoginActivity.panduan_tishi = false;
										}
										
									}else if (UserLoginWayActivity.panduan_tishi == true) {
										if (qq.equals("qq")) {
//											Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
//									        startActivity(intent2);
//											UserLoginWayActivity.panduan_tishi = false;
//											SharedPreferences spPreferences_tishi_qq = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
//											spPreferences_tishi_qq.edit().clear().commit();//第三方授权登录提示绑定手机号信息清空
										}else {
											Intent intent2 = new Intent(getActivity(), TishiWxBangDingActivity.class);
									        startActivity(intent2);
											UserLoginWayActivity.panduan_tishi = false;
										}
									}
								}
//							    }else {
//							    	shoujihao_type = false;
//								}
								
							}else {
								UserRegisterllData data = new UserRegisterllData();
								JSONObject obj = object.getJSONObject("data");
								data.id = obj.getString("id");
								data.user_name = obj.getString("user_name");
								user_id = data.id;
								user_name = data.user_name;
								
								System.out.println("---data.user_name-------------------"+data.user_name);
								System.out.println("---user_id-------------------"+user_id);
								
								if (data.user_name.equals("匿名")) {
//								if (data.id.equals("0")) {
									System.out.println("---微信还未绑定-------------------");
//								    System.out.println("UserLoginActivity.panduan====1=="+UserLoginActivity.panduan_tishi);
//									System.out.println("UserLoginWayActivity.panduan====2=="+UserLoginWayActivity.panduan_tishi);
//									 SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
//									if (UserLoginActivity.panduan_tishi == true) {
//									    Editor editor = spPreferences_tishi.edit();
//										editor.putString("user","weixin");
//										editor.commit();
//									}else if (UserLoginWayActivity.panduan_tishi == true) {
//									    Editor editor = spPreferences_tishi.edit();
//										editor.putString("qq","qq");
//										editor.commit();
//									}
									Intent intent1 = new Intent(getActivity(), TishiWxBangDingActivity.class);
								    startActivity(intent1);
								}else {
									spPreferences = getActivity().getSharedPreferences("longuserset", context.MODE_PRIVATE);
									String user = spPreferences.getString("user", "");
									System.out.println("---1-------------------"+user);
									
							        data.login_sign = obj.getString("login_sign");
								    Editor editor = spPreferences.edit();
								    editor.putString("login_sign", data.login_sign);
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
			System.out.println("user_name_phone================="+user_name_phone);
			
			
			if (!user_name_phone.equals("")) {
				user_id = spPreferences.getString("user_id", "");
//				System.out.println("user_name============1====="+user_name);
			}
			
			if (!user_name_phone.equals("")) {
				user_name_key = user_name_phone;
			}
//			
			if (!nickname.equals("")){
				
				System.out.println("==00====");
				lau0.setVisibility(View.GONE);
				lau1.setVisibility(View.VISIBLE);
				ll_user_weixin.setVisibility(View.GONE);
				tv_weixin_name.setVisibility(View.VISIBLE);
				System.out.println("==11==user_name_phone=="+user_name_phone);
				if (!user_name_phone.equals("")) {
					System.out.println("==11====");
					ll_user_weixin.setVisibility(View.VISIBLE);
					tv_weixin_name.setVisibility(View.GONE);
					handler.sendEmptyMessage(-11);
					load_list();
				}else {
					try {
						System.out.println("==22====");
						setinten();//数据清空	
						
					lau0.setVisibility(View.GONE);
					lau1.setVisibility(View.VISIBLE);
					if (!headimgurl2.equals("")) {
						img_head.setVisibility(View.VISIBLE);
						networkImage.setVisibility(View.GONE);
						tv_weixin_name.setVisibility(View.VISIBLE);
						Bitmap bitmap = BitUtil.stringtoBitmap(headimgurl2);
//						Bitmap bitmap = UserLoginActivity.bitmap;
						bitmap = Utils.toRoundBitmap(bitmap, null); // 这个时候的图片已经被处理成圆形的了
						img_head.setImageBitmap(bitmap);
						tv_weixin_name.setText(nickname);
					}else {
						img_head.setVisibility(View.GONE);
						networkImage.setVisibility(View.VISIBLE);
						tv_weixin_name.setVisibility(View.VISIBLE);
					mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
					mImageLoader.displayImage(headimgurl,networkImage);
					tv_weixin_name.setText(nickname);
					}
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}else {
				System.out.println("==33====");
				
					if (!user_name_phone.equals("")) {
						ll_user_weixin.setVisibility(View.VISIBLE);
						lau0.setVisibility(View.GONE);
						lau1.setVisibility(View.VISIBLE);
						tv_weixin_name.setVisibility(View.GONE);
						handler.sendEmptyMessage(-11);
						load_list();
					} else {
						lau0.setVisibility(View.VISIBLE);
						lau1.setVisibility(View.GONE);
						ll_user_weixin.setVisibility(View.GONE);
						tv_weixin_name.setVisibility(View.GONE);
						handler.sendEmptyMessage(-22);
					}
			}			
			
//			SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
//			weixin = spPreferences_tishi.getString("weixin", "");
//			qq = spPreferences_tishi.getString("qq", "");
//			System.out.println("=================weixin==" + weixin);
//			System.out.println("=================qq==" + qq);
//			
//			System.out.println("UserLoginActivity.panduan====1=="+UserLoginActivity.panduan_tishi);
//			System.out.println("UserLoginWayActivity.panduan====2=="+UserLoginWayActivity.panduan_tishi);
//			if (!nickname.equals("")) {
//				
//				if (UserLoginActivity.panduan_tishi == true) { 
////				if (!user_name_3_wx.equals("")) {
//					if (weixin.equals("weixin")) {
//					}else {
//						 Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
//					     startActivity(intent);
//						 UserLoginActivity.panduan_tishi = false;
//					}
//					
//				}else if (UserLoginWayActivity.panduan_tishi == true) {
////				}else if (!user_name_3_qq.equals("")) {
//					if (qq.equals("qq")) {
//					}else {
//						Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
//				        startActivity(intent);
//						UserLoginWayActivity.panduan_tishi = false;
//					}
//				}
//			}
			
			
//			};
//		}.start();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 提示是否注销登录
	 */
	protected void dialoglogin() {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("是否注销登录?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//清空SharedPreferences保存数据
				System.out.println("UserLoginActivity.panduan====1=="+UserLoginActivity.panduan);
				System.out.println("UserLoginWayActivity.panduan====2=="+UserLoginWayActivity.panduan);
				System.out.println("PhoneLoginActivity.panduan====3=="+PhoneLoginActivity.panduan);
				spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
				
			if (UserLoginActivity.panduan == true) {
//				if (!user_name_weixin.equals("")) {
				spPreferences_login.edit().clear().commit();
				spPreferences.edit().clear().commit();
					String nickname = spPreferences_login.getString("nickname", "");
					System.out.println("0======"+nickname);
//					Toast.makeText(getActivity(), "微信名/"+nickname, 200).show();
					UserLoginActivity.panduan = false;
					Intent intent4 = new Intent(getActivity(), UserLoginActivity.class);
					startActivity(intent4);
				}else
					if (UserLoginWayActivity.panduan == true) {
//					if (!user_name_qq.equals("")) {
					spPreferences_login.edit().clear().commit();
					spPreferences.edit().clear().commit();
					String nickname = spPreferences_login.getString("nickname", "");
//					Toast.makeText(getActivity(), "QQ名/"+nickname, 200).show();
					System.out.println("1======"+nickname);
					UserLoginWayActivity.panduan = false;
					Intent intent4 = new Intent(getActivity(), UserLoginActivity.class);
					startActivity(intent4);
				}else 
					 if (PhoneLoginActivity.panduan == true){
//					if (!user_name.equals("")) {
				spPreferences.edit().clear().commit();
				spPreferences_login.edit().clear().commit();
				String user_name = spPreferences.getString("user", "");
				System.out.println("2======"+user_name);
//				Toast.makeText(getActivity(), "用户名/"+user_name, 200).show();
				Intent intent4 = new Intent(getActivity(), UserLoginActivity.class);
//				intent4.putExtra("login", index);
				PhoneLoginActivity.panduan = false;
				startActivity(intent4);
				}else {
					spPreferences.edit().clear().commit();
					spPreferences_login.edit().clear().commit();
//					spPreferences_login.edit().clear().commit();
//					spPreferences_login.edit().clear().commit();
//					Toast.makeText(getActivity(), "空值", 200).show();
					Intent intent4 = new Intent(getActivity(), UserLoginActivity.class);
					startActivity(intent4);
				}
			SharedPreferences spPreferences_tishi = getActivity().getSharedPreferences("longuserset_tishi", Context.MODE_PRIVATE);
			spPreferences_tishi.edit().clear().commit();//第三方授权登录提示绑定手机号信息清空
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}
	/**
	 * 数据清空
	 */
	private void setinten() {
		// TODO Auto-generated method stub
		tv_ticket.setText("0");
		tv_shop_ticket.setText("0");
		tv_jifen_ticket.setText("0");
		tv_djjifen_ticket.setText("0");
		
		tv_quanbu.setText("");
		tv_unpay.setText("");
		tv_delivered.setText("");
		tv_received.setText("");
		tv_payed.setText("");
	}
	
	 public void userlogin(){
			try{
				 strUrlone = RealmName.REALM_NAME_LL + "/user_oauth_register?oauth_name=weixin&oauth_access_token="+access_token+"&oauth_unionid="+unionid+"";
				System.out.println("======11============="+strUrlone);
				AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
					public void onSuccess(int arg0, String arg1) {
						System.out.println("======输出============="+arg1);
//						Toast.makeText(getActivity(), "数据为+"+arg1, 400).show();
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
//							tv_weixin_name.setText(arg1);
							if (status.equals("y")) {
								String data = object.getString("data");
								if (data.equals("null")) {
//									Toast.makeText(getActivity(), "1数据为空", 200).show();
									dialogweixinlogin();
								}else {
//									Toast.makeText(getActivity(), "22数据为空", 200).show();
//									String id = object.getString("id");
//									String user_name = object.getString("user_name");
								}
								
							}else {
								
							}
						} catch (JSONException e) {
							
							
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
				}, getActivity());
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		/**
		 * 提示是否注销登录
		 */
		protected void dialogweixinlogin() {
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setMessage("您目前是第三方账号登录，完善手机可获得更多你感兴趣的内容！");
			builder.setTitle("绑定云商聚手机号");
			builder.setPositiveButton("去绑定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					Intent intent = new Intent(getActivity(), MobilePhoneActivity.class);
					intent.putExtra("nickname", nickname);
					intent.putExtra("access_token", access_token);
					intent.putExtra("unionid", unionid);
					intent.putExtra("sex", sex);
					intent.putExtra("headimgurl", headimgurl);
					startActivity(intent);
				}
			});

			builder.setNegativeButton("跳过", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});

			builder.create().show();
		}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

//	public IndividualCenterActivity(ImageLoader imageLoader,
//			Context context) {
//		// TODO Auto-generated constructor stub
//		try {
//			
//		this.imageLoader = imageLoader;
//		this.context = context;
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}

//	public IndividualCenterActivity() {
//	                  	        
//	}

//	private String buildTransaction(final String type) {
//		return (type == null) ? String.valueOf(System.currentTimeMillis())
//				: type + System.currentTimeMillis();
//	}

	private void getuserxinx() {
		// TODO Auto-generated method stub
		lau0.setVisibility(View.GONE);
		lau1.setVisibility(View.VISIBLE);
		try{
//			String name = UserLoginActivity.user_name;
			String user_name = spPreferences.getString("user", "");
//			if (name != null) {
//				 strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+name+"";
//			}else {
				 strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
//			}
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======输出用户资料============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
						JSONObject obj = object.getJSONObject("data");
						data = new UserRegisterllData();
						data.id = obj.getString("id");
						data.user_name = obj.getString("user_name");
						data.user_code = obj.getString("user_code");
						data.agency_id = obj.getInt("agency_id");
						data.amount = obj.getString("amount");
						data.pension = obj.getString("pension");
						data.packet = obj.getString("packet");
						data.point = obj.getString("point");
						data.group_id = obj.getString("group_id");
						data.login_sign = obj.getString("login_sign");
						data.agency_name = obj.getString("agency_name");
						data.group_name = obj.getString("group_name");
						data.avatar = obj.getString("avatar");
						data.mobile = obj.getString("mobile");
                        System.out.println("avatar==============="+data.avatar);
						
                		Editor editor = spPreferences.edit();
						editor.putString("avatar", data.avatar);
						editor.putString("mobile",data.mobile);
						editor.commit();
						
						data.exp = obj.getString("exp");
						login_sign = data.login_sign;
						tv_usernum.setText(data.group_name);
						group_id = data.group_id;
						tv_name.setText(data.user_name);
						tv_usertag.setText("聚卡:"+data.user_code);
						yth = data.user_code;
						tv_ticket.setText(data.amount);
						tv_shop_ticket.setText(data.pension);
						tv_jifen_ticket.setText(data.packet);
						tv_djjifen_ticket.setText(data.point);//data.packet point
						tv_jiazhibi.setText("成长值："+data.exp);
						
						String group_name = data.group_name;
						if (group_name.contains("无")) {
						
					    lists.add(data);
						}else {
							tv_usernum.setText(group_name);
						}
						
						if (photo == null) {
						mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
						if (!data.avatar.equals("")){
//							img_head.setVisibility(view.VISIBLE);
							System.out.println("avatar1==============="+data.avatar);
//							imageLoader.displayImage(RealmName.REALM_NAME_FTP +data.avatar, img_head);
//							mImageLoader.displayImage("http://ju918.com/upload/phone/113990299/20161117140333104.jpg",networkImage);
							mImageLoader.displayImage(RealmName.REALM_NAME_FTP +data.avatar,networkImage);
						} else {
							if (!headimgurl.equals("")) {
								mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
								mImageLoader.displayImage(headimgurl,networkImage);
							} else {
								if (data.avatar.equals("")){
//									img_head.setVisibility(View.VISIBLE);
//									networkImage.setVisibility(View.GONE);
//									img_head.setBackgroundResource(R.drawable.app_zams);
								}else {
								if (!headimgurl.equals("")) {
									img_head.setVisibility(View.GONE);
									networkImage.setVisibility(View.VISIBLE);
									mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
									mImageLoader.displayImage(headimgurl,networkImage);
								} else {
									if (!headimgurl2.equals("")) {
										img_head.setVisibility(View.VISIBLE);
										networkImage.setVisibility(View.GONE);
										Bitmap bitmap = BitUtil.stringtoBitmap(headimgurl2);
										bitmap = Utils.toRoundBitmap(bitmap, null); // 这个时候的图片已经被处理成圆形的了
										img_head.setImageBitmap(bitmap);
									}
								}
							}
						}
//							img_head.setVisibility(view.VISIBLE);
//							networkImage.setVisibility(view.GONE);
//							img_head.setBackgroundResource(R.drawable.ysj_tx);
						}
						
						}
						userpanduan(data.login_sign);
						
						
						}else {
							
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
					NewDataToast.makeText(context, "连接超时", false, 0).show();
				}
			}, context);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			try {
			switch (msg.what) {
			case -22:
				lau0.setVisibility(View.VISIBLE);
				lau1.setVisibility(View.GONE);
				break;
			case -11:
				lau0.setVisibility(View.GONE);
				lau1.setVisibility(View.VISIBLE);
				try{
					String user_name = spPreferences.getString("user", "");
						 strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
					System.out.println("======11============="+strUrlone);
					AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
						public void onSuccess(int arg0, String arg1) {
							try {
								System.out.println("======输出用户资料============="+arg1);
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONObject obj = object.getJSONObject("data");
								data = new UserRegisterllData();
								data.user_name = obj.getString("user_name");
								data.user_code = obj.getString("user_code");
								data.agency_id = obj.getInt("agency_id");
								data.amount = obj.getString("amount");
								data.pension = obj.getString("pension");
								data.packet = obj.getString("packet");
								data.point = obj.getString("point");
								data.group_id = obj.getString("group_id");
								data.login_sign = obj.getString("login_sign");
								data.agency_name = obj.getString("agency_name");
								data.group_name = obj.getString("group_name");
								data.avatar = obj.getString("avatar");
								data.mobile = obj.getString("mobile");
	                            System.out.println("avatar==============="+data.avatar);
								
	                            SharedPreferences spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
		                		Editor editor = spPreferences.edit();
								editor.putString("avatar", data.avatar);
								editor.putString("mobile",data.mobile);
								editor.commit();
								
								data.exp = obj.getString("exp");
								login_sign = data.login_sign;
								tv_usernum.setText(data.group_name);
								group_id = data.group_id;
								tv_name.setText(data.user_name);
								tv_usertag.setText("会员号:"+data.user_code);
								yth = data.user_code;
								tv_ticket.setText(data.amount);
								tv_shop_ticket.setText(data.pension);
								tv_jifen_ticket.setText(data.packet);
								tv_djjifen_ticket.setText(data.point);//data.packet point
								tv_jiazhibi.setText("成长值："+data.exp);
								
								String group_name = data.group_name;
								if (group_name.contains("无")) {
								
							    lists.add(data);
								}else {
									tv_usernum.setText(group_name);
								}
								System.out.println("data.avatar--------------------------"+RealmName.REALM_NAME_FTP +data.avatar);
								if (photo == null) {
								mImageLoader = initImageLoader(getActivity(), mImageLoader, "test");
								if (!data.avatar.equals("")){
//									img_head.setVisibility(view.VISIBLE);
									System.out.println("avatar1==============="+data.avatar);
//									imageLoader.displayImage(RealmName.REALM_NAME_FTP +data.avatar, img_head);
//									mImageLoader.displayImage("http://ju918.com/upload/phone/113990299/20161117140333104.jpg",networkImage);
									mImageLoader.displayImage(RealmName.REALM_NAME_HTTP +data.avatar,networkImage);
								}else {
//									img_head.setVisibility(view.VISIBLE);
//									networkImage.setVisibility(view.GONE);
//									img_head.setBackgroundResource(R.drawable.ysj_tx);
								}
								}
								userpanduan(data.login_sign);
								
								
								}else {
									
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
							NewDataToast.makeText(context, "连接超时", false, 0).show();
						}
					}, context);
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				break;
			case 0:
				break;
			case 1:
				break;
//			case 2:
//				String strobj = (String) msg.obj;
//				Toast.makeText(getActivity(), strobj, 200).show();
//				break;
//			case 5:
//				String text1 = (String) msg.obj;
//				softshareWxChat(text1);
//				break;
//			case 3:
//				String text2 = (String) msg.obj;
//				softshareWxFriend(text2);
//				break;
//			case 4:
//				String text = (String) msg.obj;
//				Uri uri = Uri.parse("smsto:");
//				Intent it = new Intent(Intent.ACTION_SENDTO, uri);
//				it.putExtra("sms_body", text);
//				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(it);
//				break;
			default:
				break;
			}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		};
	};
	
	/**
	 * 初始化图片下载器，图片缓存地址<i>("/Android/data/[app_package_name]/cache/dirName")</i>
	 */
	public ImageLoader initImageLoader(Context context,
			ImageLoader imageLoader, String dirName) {
		imageLoader = ImageLoader.getInstance();
		if (imageLoader.isInited()) {
			// 重新初始化ImageLoader时,需要释放资源.
			imageLoader.destroy();
		}
		imageLoader.init(initImageLoaderConfig(context, dirName));
		return imageLoader;
	}

	/**
	 * 配置图片下载器
	 * 
	 * @param dirName
	 *            文件名
	 */
	private ImageLoaderConfiguration initImageLoaderConfig(
			Context context, String dirName) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCache(new UnlimitedDiscCache(new File(dirName)))
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		return config;
	}

	private int getMemoryCacheSize(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
															// limit
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}
		return memoryCacheSize;
	}
	/**
	 * 判断是否升级
	 * @param login_sign 
	 */
	public void userpanduan(String login_sign){
		try{
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_config?username="+user_name+"&sign="+login_sign+"";
			
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							 agencygroupid = obj.getString("agencygroupid");
							 storegroupid = obj.getString("storegroupid");
							 shopsgroupid = obj.getString("shopsgroupid");
							 salesgroupid = obj.getString("salesgroupid");
							 
							 System.out.println("======agencygroupid============="+agencygroupid);
							 System.out.println("======storegroupid============="+storegroupid);
							 System.out.println("======shopsgroupid============="+shopsgroupid);
							 System.out.println("======salesgroupid============="+salesgroupid);
							 System.out.println("======group_id============="+group_id);
							 if (agencygroupid.contains(group_id)) {
									tv_chuangke.setText("我是创客");
								}else if (storegroupid.contains(group_id)){
									tv_chuangke.setText("我是创客");
								}else if (shopsgroupid.contains(group_id)){
									tv_chuangke.setText("我是创客");
								}else if (salesgroupid.contains(group_id)){
									tv_chuangke.setText("我是创客");
								}else {
									tv_chuangke.setText("我要创客");
								}
//							agencygroupid  代理
//							storegroupid   仓超
//							shopsgroupid   门店
//							salesgroupid   业务员
						}else{
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, context);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void example(View layout) {
		try {
			//ban_louming //半透明颜色
		fl_buju =  (FrameLayout) layout.findViewById(R.id.fl_buju);
//		index_item0 = (LinearLayout) layout.findViewById(R.id.index_item0);
//		index_item1 = (LinearLayout) layout.findViewById(R.id.index_item1);
//		index_item2 = (LinearLayout) layout.findViewById(R.id.index_item2);
//		index_item3 = (LinearLayout) layout.findViewById(R.id.index_item3);
//		index_item0.setOnClickListener(this);
//		index_item1.setOnClickListener(this);
//		index_item2.setOnClickListener(this);
//		index_item3.setOnClickListener(this);
		networkImage = (RoundImageView) layout.findViewById(R.id.roundImage_network);
		
		person_login_un_btn = (Button) layout.findViewById(R.id.person_login_un_btn);
		person_login_un_btn.setOnClickListener(this);
		lau0 = (LinearLayout) layout.findViewById(R.id.lau0);
		lau1 = (RelativeLayout) layout.findViewById(R.id.lau1);
		is_vip = (TextView) layout.findViewById(R.id.is_vip);
//		img_btn_shared = (LinearLayout) layout
//				.findViewById(R.id.img_btn_shared);

		tv_chuangke = (TextView) layout.findViewById(R.id.tv_chuangke);
		tv_jiazhibi = (TextView) layout.findViewById(R.id.tv_jiazhibi);
		tv_usernum = (TextView) layout.findViewById(R.id.tv_usernum);
//		agency_name = (TextView) layout.findViewById(R.id.tv_agency_name);
		tv_usertag = (TextView) layout.findViewById(R.id.tv_usertag);
//		img_address = (LinearLayout) layout.findViewById(R.id.img_btn_address);
//		zhuxiao = (LinearLayout) layout.findViewById(R.id.zhuxiao);
//		zhuxiao.setOnClickListener(this);
//		img_financemanage = (LinearLayout) layout
//				.findViewById(R.id.img_btn_financemanage);
//		img_btn_chongzhi = (LinearLayout) layout
//				.findViewById(R.id.img_btn_chongzhi);

//		img_btn_collect_ware = (LinearLayout) layout
//				.findViewById(R.id.img_btn_collect_ware);
		img_head = (ImageView) layout.findViewById(R.id.ib_user_photo);
		imv_user_photo = (RoundImageView) layout.findViewById(R.id.imv_user_photo);
//		caidan = (LinearLayout) layout.findViewById(R.id.caidan);
//		caidan.setOnClickListener(this);
		tv_quanbu = (TextView) layout.findViewById(R.id.tv_quanbu);
		tv_unpay = (TextView) layout.findViewById(R.id.tv_unpay);
		tv_delivered = (TextView) layout.findViewById(R.id.tv_delivered);
		tv_received = (TextView) layout.findViewById(R.id.tv_received);
		tv_payed = (TextView) layout.findViewById(R.id.tv_payed);

		tv_name = (TextView) layout.findViewById(R.id.tv_username);
		tv_weixin_name = (TextView) layout.findViewById(R.id.tv_weixin_name);
		tv_ticket = (TextView) layout.findViewById(R.id.tv_ticket);
		tv_shop_ticket = (TextView) layout.findViewById(R.id.tv_shop_ticket);
		tv_jifen_ticket = (TextView) layout.findViewById(R.id.tv_jifen_ticket);
		tv_djjifen_ticket = (TextView) layout.findViewById(R.id.tv_djjifen_ticket);
		ll_quanbu = (LinearLayout) layout.findViewById(R.id.ll_quanbu);
		ll_unpay = (LinearLayout) layout.findViewById(R.id.ll_unpay);
		ll_delivered = (LinearLayout) layout.findViewById(R.id.ll_delivered);
		ll_received = (LinearLayout) layout.findViewById(R.id.ll_received);
		ll_payed = (LinearLayout) layout.findViewById(R.id.ll_payed);
		img_btn_order = (LinearLayout) layout.findViewById(R.id.img_btn_order);
		ll_user = (LinearLayout) layout.findViewById(R.id.ll_user);
//		ll_chuangke = (LinearLayout) layout.findViewById(R.id.ll_chuangke);
		ll_user_weixin = (LinearLayout) layout.findViewById(R.id.ll_user_weixin);
//		ll_chuangke.setOnClickListener(this);
//		img_address.setOnClickListener(this);
//		img_financemanage.setOnClickListener(this);
//		img_btn_collect_ware.setOnClickListener(this);
		img_head.setOnClickListener(this);
		networkImage.setOnClickListener(this);
		ll_quanbu.setOnClickListener(this);
		ll_unpay.setOnClickListener(this);
		ll_delivered.setOnClickListener(this);
		ll_received.setOnClickListener(this);
		ll_payed.setOnClickListener(this);
		img_btn_order.setOnClickListener(this);
		ll_user.setOnClickListener(this);
//		img_btn_shared.setOnClickListener(this);
//		img_btn_chongzhi.setOnClickListener(this);
		
		
		rl_time1 = (RelativeLayout) layout.findViewById(R.id.rl_time1);
		rl_time2 = (RelativeLayout) layout.findViewById(R.id.rl_time2);
		rl_time3 = (RelativeLayout) layout.findViewById(R.id.rl_time3);
		rl_time4 = (RelativeLayout) layout.findViewById(R.id.rl_time4);
		rl_time5 = (RelativeLayout) layout.findViewById(R.id.rl_time5);
		rl_time6 = (RelativeLayout) layout.findViewById(R.id.rl_time6);
		rl_time7 = (RelativeLayout) layout.findViewById(R.id.rl_time7);
		rl_time8 = (RelativeLayout) layout.findViewById(R.id.rl_time8);
		rl_time9 = (RelativeLayout) layout.findViewById(R.id.rl_time9);
		rl_time10 = (RelativeLayout) layout.findViewById(R.id.rl_time10);
		rl_time11 = (RelativeLayout) layout.findViewById(R.id.rl_time11);
		rl_time12 = (RelativeLayout) layout.findViewById(R.id.rl_time12);
		rl_time1.setOnClickListener(this);
		rl_time2.setOnClickListener(this);
		rl_time3.setOnClickListener(this);
		rl_time4.setOnClickListener(this);
		rl_time5.setOnClickListener(this);
		rl_time6.setOnClickListener(this);
		rl_time7.setOnClickListener(this);
		rl_time8.setOnClickListener(this);
		rl_time9.setOnClickListener(this);
		rl_time10.setOnClickListener(this);
		rl_time11.setOnClickListener(this);
		rl_time12.setOnClickListener(this);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_btn_order://订单管理
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent personIntent = new Intent(getActivity(),MyOrderActivity.class);
					personIntent.putExtra("num", "1");
					startActivity(personIntent);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
				if (user_name_phone.equals("")) {
					Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
					startActivity(intent9);
				} else {
			Intent personIntent = new Intent(getActivity(),MyOrderActivity.class);
			personIntent.putExtra("num", "1");
			startActivity(personIntent);
			}
			}
			break;
		case R.id.rl_time1://我的优惠劵
			Toast.makeText(getActivity(), "功能还未开发，敬请期待", 200).show();
			break;
		case R.id.rl_time2://我的聚兑换
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent Intent1 = new Intent(getActivity(),MyJuDuiHuanActivity.class);
					Intent1.putExtra("num", "2");
					startActivity(Intent1);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
				if (user_name_phone.equals("")) {
					Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
					startActivity(intent9);
				} else {
			Intent Intent1 = new Intent(getActivity(),MyJuDuiHuanActivity.class);
			Intent1.putExtra("num", "2");
			startActivity(Intent1);
			}
			}
			break;
		case R.id.rl_time3://我的聚团
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent Intent2 = new Intent(getActivity(),MyJuTuanActivity.class);
					Intent2.putExtra("num", "3");
					startActivity(Intent2);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
				if (user_name_phone.equals("")) {
					Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
					startActivity(intent9);
				} else {
			Intent Intent2 = new Intent(getActivity(),MyJuTuanActivity.class);
			Intent2.putExtra("num", "3");
			startActivity(Intent2);
			}
			}
			break;
		case R.id.rl_time4://我的抽奖
			Toast.makeText(getActivity(), "功能还未开发，敬请期待", 200).show();
			break;
		case R.id.rl_time8://我的钱包
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent intent = new Intent(getActivity(), MyQianBaoActivity.class);
					startActivity(intent);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
			if (user_name_phone.equals("")) {
				Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
				startActivity(intent9);
			} else {
			Intent intent = new Intent(getActivity(), MyQianBaoActivity.class);
			startActivity(intent);
			}
			}
			break;
		case R.id.rl_time10:
//			SoftWarePopuWindow(img_btn_shared, context);
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent intentll = new Intent(getActivity(),FenXiangActivity.class);
					startActivity(intentll);
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
					
//				SoftWarePopuWindow(img_shared, context);
					Intent intentll = new Intent(getActivity(),FenXiangActivity.class);
					startActivity(intentll);
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			}
			break;
		case R.id.person_login_un_btn://用户登录
//			if (!nickname.equals("")) {
//				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
//				startActivity(intent);
//			}else {
			Intent intent0 = new Intent(getActivity(), UserLoginActivity.class);
			intent0.putExtra("login", 0);
			startActivity(intent0);
//			}
			break;
		case R.id.rl_time5:// 账户管理
			try {
				if (!nickname.equals("")) {
					if (!user_name_phone.equals("")) {
						Intent intent9 = new Intent(getActivity(),PersonCenterActivity.class);
						startActivity(intent9);
					} else {
					Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
					startActivity(intent);
					}
				}else {
				if (user_name_phone.equals("")) {
					Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
					startActivity(intent9);
				} else {
			        Intent intent3 = new Intent(getActivity(),PersonCenterActivity.class);
			        startActivity(intent3);
				}
				}
			
//			Toast.makeText(getActivity(), "功能正在完善", 200).show();
//			user_name = spPreferences.getString("user", "");
//			if (user_name.equals("")) {
//				Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
//				startActivity(intent9);
//			} else {
//			Intent intent3 = new Intent(getActivity(),PersonCenterActivity.class);
//			startActivity(intent3);
//			}
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		case R.id.rl_time9://财务管理
//			Intent intent2 = new Intent(getActivity(),FinanceManageActivity.class);
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent intent9 = new Intent(getActivity(),MyAssetsActivity.class);
					startActivity(intent9);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
			if (user_name_phone.equals("")) {
				Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
				startActivity(intent9);
			} else {
			Intent intent2 = new Intent(getActivity(),MyAssetsActivity.class);
			startActivity(intent2);
			}
			}
			break;
		case R.id.ll_quanbu://待付款
//			int index0 = 0;
//			Intent intent5 = new Intent(getActivity(),OrderInfromationActivity.class);//MyOrderActivity
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent intent5 = new Intent(getActivity(),MyOrderActivity.class);
					intent5.putExtra("status","0");
					intent5.putExtra("num", "1");
					startActivity(intent5);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
			if (user_name_phone.equals("")) {
				Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
				startActivity(intent9);
			} else {
			Intent intent5 = new Intent(getActivity(),MyOrderActivity.class);
			intent5.putExtra("status","0");
			intent5.putExtra("num", "1");
			startActivity(intent5);
			}
			}
			break;
		case R.id.ll_unpay://待付款
//			int index0 = 0;
//			Intent intent5 = new Intent(getActivity(),OrderInfromationActivity.class);//MyOrderActivity
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent intent5 = new Intent(getActivity(),MyOrderActivity.class);
					intent5.putExtra("status","1");
					intent5.putExtra("num", "1");
					startActivity(intent5);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
			if (user_name_phone.equals("")) {
				Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
				startActivity(intent9);
			} else {
			Intent intent5 = new Intent(getActivity(),MyOrderActivity.class);
			intent5.putExtra("status","1");
			intent5.putExtra("num", "1");
			startActivity(intent5);
			}
			}
			break;
		case R.id.ll_delivered://已付款
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent intent6 = new Intent(getActivity(),MyOrderActivity.class);
					intent6.putExtra("status","2");
					intent6.putExtra("num", "1");
					startActivity(intent6);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
			if (user_name_phone.equals("")) {
				Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
				startActivity(intent9);
			} else {
			Intent intent6 = new Intent(getActivity(),MyOrderActivity.class);
			intent6.putExtra("status","2");
			intent6.putExtra("num", "1");
			startActivity(intent6);
			}
			}
			break;
		case R.id.ll_received://待发货
//			int index2 = 2;
//			Intent intent7 = new Intent(getActivity(),OrderInfromationActivity.class);
//			intent7.putExtra("num", index2);
//			startActivity(intent7);
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent intent7 = new Intent(getActivity(),MyOrderActivity.class);
					intent7.putExtra("status","3");
					intent7.putExtra("num", "1");
					startActivity(intent7);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
			if (user_name_phone.equals("")) {
				Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
				startActivity(intent9);
			} else {
			Intent intent7 = new Intent(getActivity(),MyOrderActivity.class);
			intent7.putExtra("status","3");
			intent7.putExtra("num", "1");
			startActivity(intent7);
			}
			}
			break;
		case R.id.ll_payed://待收货
			if (!nickname.equals("")) {
				if (!user_name_phone.equals("")) {
					Intent intent8 = new Intent(getActivity(),MyOrderActivity.class);
					intent8.putExtra("status","4");
					intent8.putExtra("num", "1");
					startActivity(intent8);
				} else {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}
			}else {
			if (user_name_phone.equals("")) {
				Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
				startActivity(intent9);
			} else {
			Intent intent8 = new Intent(getActivity(),MyOrderActivity.class);
			intent8.putExtra("status","4");
			intent8.putExtra("num", "1");
			startActivity(intent8);
			}
			}
			break;
		case R.id.rl_time6:// 聚聚发
			try{
				if (!nickname.equals("")) {
//					String user = spPreferences.getString("user", "");
					System.out.println("值是================"+group_id);
					if (user_name_phone.equals("")) {
						Intent intent11 = new Intent(getActivity(), TishiWxBangDingActivity.class);
						startActivity(intent11);
					}else {
						if (agencygroupid.contains(group_id)) {
							Intent intent_ck= new Intent(getActivity(),ChuangKeUserActivity.class);
							startActivity(intent_ck);
						}else if (storegroupid.contains(group_id)){
							Intent intent_ck= new Intent(getActivity(),ChuangKeUserActivity.class);
							startActivity(intent_ck);
						}else if (shopsgroupid.contains(group_id)){
							Intent intent_ck= new Intent(getActivity(),ChuangKeUserActivity.class);
							startActivity(intent_ck);
						}else if (salesgroupid.contains(group_id)){
							Intent intent_ck= new Intent(getActivity(),ChuangKeUserActivity.class);
							startActivity(intent_ck);
						}else {
							Intent intent_ck= new Intent(getActivity(),ChuangKeActivity.class);
							startActivity(intent_ck);
					    }
					}
				}else {
			System.out.println("值是================"+group_id);
			if (user_name_phone.equals("")) {
				Intent intent11 = new Intent(getActivity(), UserLoginActivity.class);
				startActivity(intent11);
			}else {
				if (agencygroupid.contains(group_id)) {
					Intent intent_ck= new Intent(getActivity(),ChuangKeUserActivity.class);
					startActivity(intent_ck);
				}else if (storegroupid.contains(group_id)){
					Intent intent_ck= new Intent(getActivity(),ChuangKeUserActivity.class);
					startActivity(intent_ck);
				}else if (shopsgroupid.contains(group_id)){
					Intent intent_ck= new Intent(getActivity(),ChuangKeUserActivity.class);
					startActivity(intent_ck);
				}else if (salesgroupid.contains(group_id)){
					Intent intent_ck= new Intent(getActivity(),ChuangKeUserActivity.class);
					startActivity(intent_ck);
				}
				else {
			Intent intent_ck= new Intent(getActivity(),ChuangKeActivity.class);
			startActivity(intent_ck);
				}
			}
				}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			
			break;
		case R.id.rl_time7:// 我的收藏
			if (!nickname.equals("")) {
				if (user_name_phone.equals("")) {
				Intent intent = new Intent(getActivity(), TishiWxBangDingActivity.class);
				startActivity(intent);
				}else {
					Intent intent9 = new Intent(getActivity(),CollectWareActivity.class);
					startActivity(intent9);
				}
			}else {
			if (user_name_phone.equals("")) {
				Intent intent9 = new Intent(getActivity(),UserLoginActivity.class);
				startActivity(intent9);
			} else {
				Intent intent9 = new Intent(getActivity(),CollectWareActivity.class);
				startActivity(intent9);
			}
			}
			break;
//		case R.id.ib_user_photo:
//			dialog();
//			break;
		case R.id.roundImage_network:
		    showChoosePicDialog();
//			dialog();
			break;
		case R.id.rl_time11:
//			if (!popupWindowMenu.isShowing()) {
//				popupWindowMenu.showAtLocation(caidan, Gravity.BOTTOM, 0, 0);
//
//			}
			Intent intent4 = new Intent(getActivity(),Webview1.class);
			intent4.putExtra("jysbz_id", "10334");
			startActivity(intent4);
			break;
//		case R.id.rl_time12:
//			Intent intentu = new Intent(getActivity(),PersonCenterActivity.class);
//			startActivity(intentu);
//			break;
		case R.id.rl_time12:
		    dialoglogin();
			break;
		default:
			break;
		}
	}

	/**
	 * 显示修改头像的对话框
	 */
	protected void showChoosePicDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("设置头像");
		String[] items = { "选择本地照片", "拍照" };
		builder.setNegativeButton("取消", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case CHOOSE_PICTURE: // 选择本地照片
					Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
					System.out.println("本地照片-----------------"+openAlbumIntent);
					openAlbumIntent.setType("image/*");
					startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
					break;
				case TAKE_PICTURE: // 拍照
					Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
					System.out.println("拍照================"+tempUri);
					// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
					
					break;
				}
			}
			
		});
		builder.create().show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
//		if (resultCode == RESULT_OK) { // 如果返回码是可以用的
			switch (requestCode) {
			//拍照
			case TAKE_PICTURE:
				startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
				break;
			//上传图片	
			case CHOOSE_PICTURE:
				startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
				break;
			case CROP_SMALL_PICTURE:
				if (data != null) {
					setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
				}
				break;
			}
//		}
	}
	
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
//		System.out.println("裁剪图片方法实现================"+tempUri);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}
	
	public void saveBitmapFile(Bitmap bitmap){
        File file=new File("/mnt/sdcard/pic/01.jpg");//将要保存图片的路径
        try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                
                bos.flush();
                bos.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
}
	
	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param
	 * 
	 * @param picdata
	 */
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		photo = extras.getParcelable("data");
		if (extras != null) {
//			photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
			System.out.println("图片的值1================="+photo);
			System.out.println("图片的值2================="+tempUri);
			networkImage.setImageBitmap(photo);
			try {
				imagePath = Utils.savePhoto(photo, Environment.getExternalStorageDirectory().getAbsolutePath(), 
						String.valueOf(System.currentTimeMillis()));
				System.out.println("imagePath======================="+imagePath);
				
				new Thread() {
					public void run() {
						try {
							FTPClient client = new FTPClient();
							client.connect("183.62.138.31", 1021);
							client.login("le", "le1230.");
							SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
			                time = f.format(new Date());  
			                yth = MainFragmentContent4.yth;
			                String remotePathTmp = "phone/" + "" + yth +"";//路径
							System.out.println("========================"+remotePathTmp);
							
							try {
								client.createDirectory(remotePathTmp);//客户端创建目录
							} catch (Exception e) {
					                e.printStackTrace();
							} finally {
								client.changeDirectory(remotePathTmp);
								
								File file = new File(imagePath);
								FileInputStream fis = new FileInputStream(file);
								try {
									client.upload(time + ".jpg", fis, 0, 0, null);
								} catch (FTPDataTransferException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (FTPAbortedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								fis.close();
								client.logout();//exit
							}
							
								tupian = "/upload/phone/" + yth + "/"+ time + ".jpg";
							System.out.println("tupian1--------------------------"+tupian);

						} catch (IllegalStateException e) {
							e.printStackTrace();//非法状态异常
						}
						catch (FTPIllegalReplyException e) {
							e.printStackTrace();//非法回复异常
						} catch (FTPException e) {
							e.printStackTrace();//异常
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						gettouxiang(); 
					};
					
				}.start();
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
	
	
	//创建删除方法： 
	public void clearData() { 
//		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		spPreferences.edit().clear().commit(); 
	   } 
	
	/**
	 * 第0个列表数据解析
	 */
	private void load_list() {
	   list = new ArrayList<MyOrderData>();
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_order_page_size_list?user_id="+user_id+"" +
				"&page_size=100&page_index=1&strwhere=datatype=1&orderby=",
				new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
//								System.out.println("=========数据接口============"+arg1);
								try {
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
							    MyOrderData md = new MyOrderData();
								JSONObject obj= jsonArray.getJSONObject(i);
								md.setId(obj.getString("id"));
								list.add(md); 
//								 tv_unpay,tv_delivered,tv_received,tv_payed
								}
								System.out.println("========list.size()==========="+list.size());
//								int num = list.size();
//								String num1 = String.valueOf(list.size());
//								tv_quanbu.setText(num1);
								}else {
//									progress.CloseProgress();
								}
								load_list1();
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}
							
						}, getActivity());
	}
	
	/**
	 * 第1个列表数据解析
	 */
	private void load_list1() {
	   list = new ArrayList<MyOrderData>();
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_order_page_size_list?user_id="+user_id+"" +
				"&page_size=100&page_index=1&strwhere=payment_status=1%20and%20datatype=1&orderby=",
				new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
//								System.out.println("=========数据接口============"+arg1);
								try {
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
							    MyOrderData md = new MyOrderData();
								JSONObject obj= jsonArray.getJSONObject(i);
								md.setId(obj.getString("id"));
								list.add(md); 
//								 tv_unpay,tv_delivered,tv_received,tv_payed
								}
								System.out.println("========list.size()==========="+list.size());
								if (list.size() > 0) {
								tv_unpay.setVisibility(View.VISIBLE);
								String num1 = String.valueOf(list.size());
								tv_unpay.setText(num1);
                                }else {
                                	tv_unpay.setVisibility(View.GONE);
								}
								}else {
//									progress.CloseProgress();
								}
								load_list2();
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}
							
						}, getActivity());
	}
	//18129864981
	/**
	 * 第2个列表数据解析
	 */
	private void load_list2() {
	   list = new ArrayList<MyOrderData>();
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_order_page_size_list?user_id="+user_id+"" +
				"&page_size=100&page_index=1&strwhere=payment_status=2%20and%20express_status=1%20and%20datatype=1&orderby=",
				new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
//								System.out.println("=========数据接口============"+arg1);
								try {
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
							    MyOrderData md = new MyOrderData();
								JSONObject obj= jsonArray.getJSONObject(i);
								md.setId(obj.getString("id"));
								list.add(md); 
								}
								
								if (list.size() > 0) {
									tv_delivered.setVisibility(View.VISIBLE);
									String num1 = String.valueOf(list.size());
									tv_delivered.setText(num1);
	                                }else {
	                                	tv_delivered.setVisibility(View.GONE);
									}
								}else {
//									progress.CloseProgress();
								}
								load_list3();
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
								System.out.println("========1===========");
							}
							
						}, getActivity());
		
	}
	/**
	 * 第3个列表数据解析
	 */
	private void load_list3() {
	   list = new ArrayList<MyOrderData>();
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_order_page_size_list?user_id="+user_id+"" +
				"&page_size=100&page_index=1&strwhere=payment_status=2%20and%20express_status=2%20and%20status=2%20and%20datatype=1&orderby=",
				new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
//								System.out.println("=========数据接口============"+arg1);
								try {
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
							    MyOrderData md = new MyOrderData();
								JSONObject obj= jsonArray.getJSONObject(i);
								md.setId(obj.getString("id"));
								list.add(md); 
								}
								
								if (list.size() > 0) {
									tv_received.setVisibility(View.VISIBLE);
									String num1 = String.valueOf(list.size());
									tv_received.setText(num1);
	                                }else {
	                                	tv_received.setVisibility(View.GONE);
									}
								
								}else {
//									progress.CloseProgress();
								}
								load_list4();
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}
							
						}, getActivity());
		
	}
	/**
	 * 第4个列表数据解析
	 */
	private void load_list4() {
	   list = new ArrayList<MyOrderData>();
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_order_page_size_list?user_id="+user_id+"" +
				"&page_size=100&page_index=1&strwhere=payment_status=2%20and%20express_status=2%20and%20status=3%20and%20datatype=1&orderby=",
				new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
//								System.out.println("=========数据接口============"+arg1);
								try {
								JSONObject object = new JSONObject(arg1);
								String status = object.getString("status");
								if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								for (int i = 0; i < jsonArray.length(); i++) {
							    MyOrderData md = new MyOrderData();
								JSONObject obj= jsonArray.getJSONObject(i);
								md.setId(obj.getString("id"));
								list.add(md); 
								}
								
								if (list.size() > 0) {
									tv_payed.setVisibility(View.VISIBLE);
									String num1 = String.valueOf(list.size());
									tv_payed.setText(num1);
	                                }else {
	                                	tv_payed.setVisibility(View.GONE);
									}
								}else {
//									progress.CloseProgress();
								}
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}
							
						}, getActivity());
		
	}
	
	
	/**
	 * 提示是否修改头像
	 */
//	protected void dialog() {
//		AlertDialog.Builder builder = new Builder(getActivity());
//		builder.setMessage("确认要修改图片吗?");
//		builder.setTitle("提示");
//		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				setImage();
//			}
//		});
//
//		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.dismiss();
//			}
//		});
//
//		builder.create().show();
//	}
//
//	private void setImage() {
//		// TODO Auto-generated method stub
//		// 使用intent调用系统提供的相册功能，使用startActivityForResult是为了获取用户选择的图片
//		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
//		getAlbum.setType(IMAGE_TYPE);
//		startActivityForResult(getAlbum, IMAGE_CODE);
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		try {
//			
//		if (resultCode != -1) { // 此处的 RESULT_OK 是系统自定义得一个常量
//			Log.e("TAG->onresult", "ActivityResult resultCode error");
//			return;
//		}
//		Bitmap bm = null;
//		// 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
//		ContentResolver resolver = getActivity().getContentResolver();
//		// 此处的用于判断接收的Activity是不是你想要的那个
//		if (requestCode == IMAGE_CODE) {
//			try {
//				Uri originalUri = data.getData(); // 获得图片的uri
//				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
//				// 显得到bitmap图片
//				System.out.println("bm-------------------------"+bm);
////				img_head.setImageBitmap(bm);
//				// 这里开始的第二部分，获取图片的路径：
//				String[] proj = { MediaStore.Images.Media.DATA };
//				System.out.println("proj-------------------------"+proj);
//				// 好像是android多媒体数据库的封装接口，具体的看Android文档
//				Cursor cursor = getActivity().managedQuery(originalUri, proj,null, null, null);
//				// 按我个人理解 这个是获得用户选择的图片的索引值
//				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//				// 将光标移至开头 ，这个很重要，不小心很容易引起越界
//				cursor.moveToFirst();
//				// 最后根据索引值获取图片路径
//				path = cursor.getString(column_index);
////				bm = Utils.toRoundBitmap(bm,originalUri);
//				System.out.println("path-------------------------"+path);
//				///storage/emulated/0/DCIM/Camera/IMG_20161106_200658.jpg
//				new Thread() {
//					public void run() {
//						try {
////						SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
//							/**
////							 * 操作FTP文件
////							 */
////							String directoryname = "/upload/phone/";
////							String fileName = "119265199";
//////							String fileName = "20161110144007";
////							System.out.println("yth-------------------------"+yth);
////							int result=	FtpImage.ftpUpload(path,yth,yth);
//							
//							FTPClient client = new FTPClient();
//							client.connect("183.62.138.31", 2020);
//							client.login("ju918", "yunsen1230.");
//							
//							 SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
//			                 time = f.format(new Date());  
//			                      
//			                    String reTransmitFolderName = time + "_Folder";  
//			                    System.out.println("========================"+reTransmitFolderName);
////							String remotePathTmp = "phone/" + "" + yth + "/" +time+"";//路径
//			                	String remotePathTmp = "phone/" + "" + yth +"";//路径
//							System.out.println("========================"+remotePathTmp);
//
//							try {
//								client.createDirectory(remotePathTmp);//客户端创建目录
//							} catch (Exception e) {
//					                e.printStackTrace();
//							} finally {
//								client.changeDirectory(remotePathTmp);
//								File file = new File(path);
//								FileInputStream fis = new FileInputStream(file);
//								
//								try {
//									client.upload(time + ".jpg", fis, 0, 0, null);
//								} catch (FTPDataTransferException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} catch (FTPAbortedException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								
//								fis.close();
//								client.logout();//exit
//							}
//
//						//String imgUrl, String directoryname, String filename	
//						} catch (IllegalStateException e) {
//							e.printStackTrace();//非法状态异常
//						}
//						catch (FTPIllegalReplyException e) {
//							e.printStackTrace();//非法回复异常
//						} catch (FTPException e) {
//							e.printStackTrace();//异常
////						} catch (FTPDataTransferException e) {
////							e.printStackTrace();//数据转移异常
////						} catch (FTPAbortedException e) {
////							e.printStackTrace();//失败异常
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//						gettouxiang();
//						onResume();
//					};
//					
//				}.start();
//
//			} catch (IOException e) {
//				Log.e("TAG-->Error", e.toString());
//				e.printStackTrace();
//			}
//		}
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
	
	private void gettouxiang() {
		// TODO Auto-generated method stub
		String imgUrl = "/upload/phone/" + yth + "/"+ time + ".jpg";
//		String imgUrl= "/upload/phone/" + yth + ".jpg";
		System.out.println("imgUrl--------------------------"+imgUrl);
		Editor editor = spPreferences.edit();
		editor.putString("avatar", imgUrl);
		editor.commit();
		String strUrl = RealmName.REALM_NAME_LL
				+ "/user_avatar_save?user_name="+user_name+"&user_id="+user_id+"&user_avatar="+imgUrl+"&sign="+login_sign+"";
		AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("arg1--------------------------"+arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
//					onResume();
					if (status.equals("y")) {
//						Toast.makeText(context, info, 200).show();
						Toast.makeText(context, "上传成功", 200).show();
						System.out.println("上传成功--------------------------");
					}else{
//						Toast.makeText(context, info, 200).show();
						Toast.makeText(context, "上传失败", 200).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}, getActivity());
	}

	
	

}
