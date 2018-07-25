package com.hengyushop.demo.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.androidquery.AQuery;
import com.ctrip.openapi.java.utils.BitUtil;
import com.example.uploadpicdemo.Utils;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.DBFengXiangActivity;
import com.hengyushop.demo.home.JuTuanGouXqActivity;
import com.hengyushop.demo.my.ChuangKeActivity;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.demo.my.ShengJiCkActivity;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.entity.UserAddressData;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.XiangqingData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;
import com.lelinju.www.UserLoginActivity;
import com.lglottery.www.widget.NewDataToast;
/**
 * 养老银行
 * @author Administrator
 *
 */
public class ZhongAnMinShenXqActivity extends BaseActivity implements OnClickListener{
	private LinearLayout index_item0,index_item1,index_item2;
	private ImageView ling_tip,ling_tip1;
	private TextView tv_ware_name,tv_jiaguo,tv_time,tv_zhuti;
	String strUrlone,user_name,user_id,login_sign,mobile,real_name;
	private SharedPreferences spPreferences;
	private Button fanhui;
	private ArrayList<XiangqingData> lists;
	String pensions,pensions_yue;
	private DialogProgress progress;
	XiangqingData xqdata;
	public AQuery mAq;
	private WebView webview;
	public static String huodong_zf_type = "0";
	int type = 0;
	String id,buy_no;
	public static String province,city,area,user_address,user_accept_name,user_mobile,shopping_address_id;
	public static  String sp_id,proFaceImg, proInverseImg, proDoDetailImg, proDesignImg,
	proName, proTip, retailPrice, marketPrice, proSupplementImg,goods_price,price,category_title,
	proComputerInfo, yth, key, releaseBossUid, AvailableJuHongBao,Atv_integral,company_id,
	productCount,title_ll,spec_ids,article_id,goods_id,subtitle,spec_text,point_id;
	private String mGroupId = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zams_xq);//activity_zams_xq
		progress = new DialogProgress(ZhongAnMinShenXqActivity.this);
		mAq = new AQuery(this);
		try {
			
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		login_sign = spPreferences.getString("login_sign", "");
		mobile = spPreferences.getString("mobile", "");
		real_name = spPreferences.getString("real_name", "");
		user_mobile = getIntent().getStringExtra("user_mobile");
		getGroupId(false);
		Initialize();
		loadWeather();
//		getuseraddress();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private void Initialize() {
		try {
			
		ling_tip = (ImageView) findViewById(R.id.ling_tip);
		ling_tip1 = (ImageView) findViewById(R.id.ling_tip1);
//		ling_tip.setBackgroundResource(R.drawable.yanglaoyinhang);
		tv_ware_name = (TextView) findViewById(R.id.tv_titel);
		tv_jiaguo = (TextView) findViewById(R.id.tv_jiaguo);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_zhuti = (TextView) findViewById(R.id.tv_zhuti);
//		tv_snyly = (TextView) findViewById(R.id.tv_snyly);
		index_item0 = (LinearLayout) findViewById(R.id.index_item0);
		index_item1 = (LinearLayout) findViewById(R.id.index_item1);
		index_item2 = (LinearLayout) findViewById(R.id.index_item2);
		fanhui = (Button) findViewById(R.id.fanhui);
		fanhui.setOnClickListener(this);
		index_item0.setOnClickListener(this);
		index_item1.setOnClickListener(this);
		index_item2.setOnClickListener(this);
		
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new JavascriptHandler(), "handler");
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
			}
		});
		
		
		ImageView img_shared = (ImageView) findViewById(R.id.img_shared);
		img_shared.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
						Intent intentll = new Intent(ZhongAnMinShenXqActivity.this,DBFengXiangActivity.class);
						intentll.putExtra("sp_id",sp_id);
						intentll.putExtra("img_url",xqdata.imgs_url);
						startActivity(intentll);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	class JavascriptHandler {
		@JavascriptInterface
		public void getContent(String htmlContent) {
		}
	}
	private Handler handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 2:
				try {
					System.out.println("=========数据xqdata.img_ur============"+xqdata.img_url);
					System.out.println("=========数据xqdata.title============"+xqdata.title);
				 mAq.id(ling_tip).image(RealmName.REALM_NAME_HTTP+xqdata.img_url);
				 mAq.id(ling_tip1).image(RealmName.REALM_NAME_HTTP+xqdata.img_url);
				 tv_ware_name.setText(xqdata.title);
				 System.out.println("=========数据xqdata.sell_price============"+xqdata.sell_price);
				 if (xqdata.sell_price.equals("0.0")) {
					 type = 1;
					 tv_jiaguo.setText("免费");
				 }else {
					 type = 2;
					 tv_jiaguo.setText("￥"+ xqdata.sell_price);	
				 }
				 tv_zhuti.setText(category_title);
				 webview.loadUrl(RealmName.REALM_NAME_HTTP+ "/goods/conent-"+article_id+".html");
//				 if (xqdata.start_time != null) {
					 tv_time.setText(xqdata.start_time+"--"+xqdata.end_time);
//				 }else {
//					
//				 }
				 
				} catch (Exception e) {
					// TODO: handle exception
	    			e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};
	private AlertDialog mTiShiDialog;
	/**
	 * 获取商品详情
	 */
	private void loadWeather() {
		String id = getIntent().getStringExtra("id");
		System.out.println("=========1============"+id);//5897
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_id_content?id="+id+"",new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("=========解析数据============"+arg1);
								formatWeather(arg1);
							}
						}, null);
	}
	
	private void formatWeather(String result) {
		 lists = new ArrayList<XiangqingData>();
		try {
			System.out.println("=======详情数据=="+result);
			JSONObject object = new JSONObject(result);
			String status = object.getString("status");
			String info = object.getString("info");
			if (status.equals("y")) {
			JSONObject jobt = object.getJSONObject("data");
			xqdata = new XiangqingData();
	        xqdata.title = jobt.getString("title");
	        xqdata.setSubtitle(jobt.getString("subtitle"));
	        xqdata.setId(jobt.getString("id"));
	        xqdata.img_url = jobt.getString("img_url");
	        xqdata.start_time = jobt.getString("start_time");
	        xqdata.end_time = jobt.getString("end_time");
	        xqdata.address = jobt.getString("address");
	        xqdata.imgs_url = jobt.getString("imgs_url");
//			proName = xqdata.getTitle();
//			subtitle = xqdata.getSubtitle();
			sp_id = xqdata.getId();
			JSONArray jsonay = jobt.getJSONArray("spec_item");
    		for (int i = 0; i < jsonay.length(); i++) {
    		JSONObject objt= jsonay.getJSONObject(i);
//			xqdata.setSub_title(job.getString("sub_title"));
    		xqdata.setSpec_text(objt.getString("spec_text"));
			xqdata.setSell_price(objt.getString("sell_price"));
			xqdata.setMarket_price(objt.getString("market_price"));
			xqdata.setCost_price(objt.getString("cost_price"));
			xqdata.setRebate_price(objt.getString("rebate_price"));
			xqdata.setSpec_ids(objt.getString("spec_ids"));
			xqdata.setGoods_id(objt.getString("goods_id"));
			xqdata.setArticle_id(objt.getString("article_id"));
			xqdata.goods_id = objt.getString("goods_id");
			xqdata.cashing_packet = objt.getString("cashing_packet");
			xqdata.give_pension = objt.getString("give_pension");
//			spec_ids = xqdata.getSpec_ids();
//			proTip = xqdata.getSub_title();
			retailPrice = xqdata.getSell_price();
//			marketPrice = xqdata.getMarket_price();
//			AvailableJuHongBao = xqdata.getCost_price();
//			Atv_integral = xqdata.getRebate_price();
//			goods_id = xqdata.getGoods_id();
			article_id = xqdata.getArticle_id();
//			spec_text =xqdata.getSpec_text();
			System.out.println("=========数据article_id============"+article_id);
		    }
    		try {
				
    		JSONArray jsonArray1 = jobt.getJSONArray("category");
        		for (int i = 0; i < jsonArray1.length(); i++) {
        		JSONObject obj= jsonArray1.getJSONObject(i);
        		category_title = obj.getString("category_title");
        		}
    		} catch (Exception e) {
				// TODO: handle exception
    			e.printStackTrace();
			}
			lists.add(xqdata);
			handler.sendEmptyMessage(2);
    		progress.CloseProgress();
    		
			}else {
				progress.CloseProgress();
				Toast.makeText(ZhongAnMinShenXqActivity.this, info, 200).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getGroupId(boolean flag) {
		final boolean fFlag =flag;
			String 	url = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			AsyncHttp.get(url, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
						JSONObject obj = object.getJSONObject("data");				
						mGroupId = obj.getString("group_id");
						if(fFlag){
							if(!"13".equals(mGroupId)){
							toBaoMing();
							}else{
							showTiShiDialog();
							}
						}
						
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
					Toast.makeText(ZhongAnMinShenXqActivity.this, "连接超时", 0).show();
				}
			}, this);
	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fanhui:
			finish();
			break;
		case R.id.index_item0:
//			Intent intent3 = new Intent(ZhongAnMinShenXqActivity.this, YangLaoChongZhiActivity.class);
//			startActivity(intent3);
			break;
		case R.id.index_item1:
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/user_favorite?article_id="+xqdata.article_id+"&goods_id="+goods_id+"&user_name="+user_name+"" +
			"&user_id="+user_id+"&tags=", new AsyncHttpResponseHandler() {
		@Override
		public void onSuccess(int arg0, String arg1) {
			// TODO Auto-generated method stub
			super.onSuccess(arg0, arg1);
			try {
				JSONObject jsonObject = new JSONObject(arg1);
				System.out.println("收藏================"+arg1);
//				progress.CloseProgress();
				String info = jsonObject.getString("info");
				Toast.makeText(getApplicationContext(), info, 200).show();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	 }, getApplicationContext());
			break;
		case R.id.index_item2:
			if(TextUtils.isEmpty(mGroupId)){
				getGroupId(true);
			}else{
				if(!"13".equals(mGroupId)){//是创客
					toBaoMing();
				}else{ //去升级创客
					showTiShiDialog();
			
				}
			}
			
		
		
			break;
		case R.id.enter_shop:
//			Intent intent4 = new Intent(ZhongAnMinShenXqActivity.this,Webview1.class);
//			intent4.putExtra("ylyh_id", "6239");
//			startActivity(intent4);
			break;


		default:
			break;
		}
		
	}
	/**
	 * 提示用户申请创客
	 */
	private  void  showTiShiDialog(){
		if(mTiShiDialog ==null){
		mTiShiDialog = new AlertDialog.Builder(this).setTitle("提示")
				  .setMessage("您还不是创客，请先申请创客")
				  .setNegativeButton("取消",  new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                  dialog.dismiss();
	                    }
	                })
				  .setPositiveButton("去申请", new DialogInterface.OnClickListener() {
	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                		Intent intent = new Intent(ZhongAnMinShenXqActivity.this,ChuangKeActivity.class);
	    					startActivity(intent);
	                    	 dialog.dismiss();
	                    }
	                }).create();
		}
		if(!mTiShiDialog.isShowing()){
			mTiShiDialog.show();
		}
		 
		
	};
	/**
	 * 去报名
	 */
	private void toBaoMing() {
		
		try {
//			System.out.println("user_accept_name==========================！"+user_accept_name);
//			  if (user_accept_name == null) {
//				 progress.CloseProgress();
//				 Toast.makeText(ZhongAnMinShenXqActivity.this, "未添加收货地址", 200).show();
//			  }else {
			progress.CreateProgress();	
			System.out.println("real_name==========================！"+real_name);
			if (real_name.equals("")) {
				real_name = "空";
			}
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_signup_buy?sale_id="+user_id+"&usale_name="+user_name+"&user_id="+user_id+"" +
					"&user_name="+user_name+"&user_sign="+login_sign+"&signup_mobile="+mobile+"&signup_name="+real_name+"&article_id="+xqdata.article_id+"" +
							"&goods_id="+xqdata.goods_id+"&quantity="+1+""
							
//							add_signup_buy?user_id=string&user_name=string&user_sign=string&signup_mobile=string&signup_name=string&article_id=string&goods_id=string&quantity=string 
//		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_buy?user_id="+user_id+"&user_name="+user_name+
//				"&article_id="+xqdata.article_id+"&goods_id="+xqdata.goods_id+"&quantity="+1+""
				,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							System.out.println("购物清单================"+arg1);
							String info = jsonObject.getString("info");
							if (status.equals("y")) {
								progress.CloseProgress();
								JSONObject obj = jsonObject.getJSONObject("data");
								buy_no = obj.getString("buy_no");
								String count = obj.getString("count");
//								Toast.makeText(ZhongAnMinShenXqActivity.this, info, 200).show();
								try {	
									  loadusertijiao(buy_no);
//									  Intent intent = new Intent(ZhongAnMinShenXqActivity.this, BaoMinTiShiActivity.class);
//									  intent.putExtra("id",id);
//									  intent.putExtra("sell_price",xqdata.sell_price);
//									  intent.putExtra("img_url",xqdata.img_url);
//									  intent.putExtra("title",xqdata.title);
//						              intent.putExtra("start_time",xqdata.start_time);
//									  intent.putExtra("end_time",xqdata.end_time);
//									  intent.putExtra("address",xqdata.address);
//									  intent.putExtra("sp_id",sp_id);
//									  startActivity(intent);
									  
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}else {
								progress.CloseProgress();
								Toast.makeText(ZhongAnMinShenXqActivity.this, info, 200).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						progress.CloseProgress();
						System.out.println("==========================访问接口失败！");
						System.out.println("========================="+arg0);
						System.out.println("=========================="+arg1);
						Toast.makeText(ZhongAnMinShenXqActivity.this, "异常", 200).show();
						super.onFailure(arg0, arg1);
					}
					

				}, ZhongAnMinShenXqActivity.this);
//			  }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 提交用户订单  
	 * @param payment_id 
	 * @param kou_hongbao 
	 */
	private void loadusertijiao(String buy_no) {
		try {
//			progress.CreateProgress();	
//		 	String shopping_ids = getIntent().getStringExtra("shopping_ids");
			System.out.println("buy_no=====================" + buy_no);
//			String zhi = "空";
//			String url = RealmName.REALM_NAME_LL+ "/order_activity_signup?user_id="+user_id+"&user_name="+user_name+"&is_cashing_packet=0" +
//			"&is_cashing_point=0&shopping_ids="+shopping_ids+"&payment_id=2&shopping_address_id="+sp_id+"" +
//			"&express_id=3&is_invoice=0&accept_name="+zhi+"&province="+zhi+"&city="+zhi+"&area="+zhi+"" +
//			"&address="+zhi+"&telphone=&mobile="+user_name+"&email=&post_code=&message=&invoice_title=";
			
			String url = RealmName.REALM_NAME_LL+ "/add_order_signup?user_id="+user_id+"&user_name="+user_name+"&user_sign="+login_sign+"" +
					"&buy_no="+buy_no+"&payment_id=5&is_invoice=0&invoice_title=0";
		AsyncHttp.get(url,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("提交用户订单 ================================="+arg1);
							try {
								
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
							    	progress.CloseProgress();
							    	JSONObject jsonObject = object.getJSONObject("data");
							    	String trade_no = jsonObject.getString("trade_no");
							    	String total_amount = jsonObject.getString("total_amount");
//							    	order_no = jsonObject.getString("order_no");
//							    	Log.e("zyjy","tpta;_amount="+total_amount);
							    	if (total_amount.equals("0.0")) {
							    		  Intent intent = new Intent(ZhongAnMinShenXqActivity.this,BaoMinOKActivity.class);
							    		  intent.putExtra("order_no",trade_no);
										  intent.putExtra("total_c",total_amount);
//										  intent.putExtra("img_url",getIntent().getStringExtra("img_url"));
//										  intent.putExtra("hd_title",getIntent().getStringExtra("title"));
//										  intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
//										  intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
//										  intent.putExtra("address",getIntent().getStringExtra("address"));
										  intent.putExtra("img_url",xqdata.img_url);
										  intent.putExtra("hd_title",xqdata.title);
							              intent.putExtra("start_time",xqdata.start_time);
										  intent.putExtra("end_time",xqdata.end_time);
										  intent.putExtra("address",xqdata.address);
										  startActivity(intent);
//										  finish();
									}else {
//									  huodong_zf_type = "1";// 活动支付成功不显示详情
									  Intent intent = new Intent(ZhongAnMinShenXqActivity.this, MyOrderZFActivity.class);
									  intent.putExtra("activity_hd","activity_hd");
									  intent.putExtra("order_no",trade_no);
									  intent.putExtra("total_c",total_amount);
//									  intent.putExtra("img_url",getIntent().getStringExtra("img_url"));
//									  intent.putExtra("title",getIntent().getStringExtra("title"));
//									  intent.putExtra("start_time",getIntent().getStringExtra("start_time"));
//									  intent.putExtra("end_time",getIntent().getStringExtra("end_time"));
//									  intent.putExtra("address",getIntent().getStringExtra("address"));
									  intent.putExtra("img_url",xqdata.img_url);
									  intent.putExtra("title",xqdata.title);
						              intent.putExtra("start_time",xqdata.start_time);
									  intent.putExtra("end_time",xqdata.end_time);
									  intent.putExtra("address",xqdata.address);
									  startActivity(intent);
//									  finish();
									}
							    	
//									  Intent intent = new Intent(ZhongAnMinShenXqActivity.this, BaoMinTiShiActivity.class);
//									  intent.putExtra("id",id);
//									  intent.putExtra("sell_price",xqdata.sell_price);
//									  intent.putExtra("img_url",xqdata.img_url);
//									  intent.putExtra("title",xqdata.title);
//						              intent.putExtra("start_time",xqdata.start_time);
//									  intent.putExtra("end_time",xqdata.end_time);
//									  intent.putExtra("address",xqdata.address);
//									  intent.putExtra("sp_id",sp_id);
//									  startActivity(intent);
									  
//							    	  if (type == 1) {
////							    		  Toast.makeText(ZhongAnMinShenXqActivity.this, info, 200).show();
////							    		  Intent intent = new Intent(ZhongAnMinShenXqActivity.this,BaoMinOKActivity.class);
////							    		  intent.putExtra("img_url",xqdata.img_url);
////										  intent.putExtra("hd_title",xqdata.title);
////										  intent.putExtra("start_time",xqdata.start_time);
////										  intent.putExtra("end_time",xqdata.end_time);
////										  intent.putExtra("address",xqdata.address);
////										  startActivity(intent);
//										  Intent intent = new Intent(ZhongAnMinShenXqActivity.this, BaoMinTiShiActivity.class);
//										  intent.putExtra("order_no",trade_no);
//										  intent.putExtra("total_c",total_amount);
//										  intent.putExtra("img_url",xqdata.img_url);
//										  intent.putExtra("title",xqdata.title);
//							              intent.putExtra("start_time",xqdata.start_time);
//										  intent.putExtra("end_time",xqdata.end_time);
//										  intent.putExtra("address",xqdata.address);
//										  intent.putExtra("type",type);
//										  startActivity(intent);
//									  }else {
//										  huodong_zf_type = "1";// 活动支付成功不显示详情
////									  Intent intent = new Intent(ZhongAnMinShenXqActivity.this, MyOrderZFActivity.class);
////									  intent.putExtra("order_no",trade_no);
////									  intent.putExtra("total_c",total_amount);
////									  startActivity(intent);
//									  Intent intent = new Intent(ZhongAnMinShenXqActivity.this, BaoMinTiShiActivity.class);
//									  intent.putExtra("order_no",trade_no);
//									  intent.putExtra("total_c",total_amount);
//									  intent.putExtra("img_url",xqdata.img_url);
//									  intent.putExtra("title",xqdata.title);
//						              intent.putExtra("start_time",xqdata.start_time);
//									  intent.putExtra("end_time",xqdata.end_time);
//									  intent.putExtra("address",xqdata.address);
//									  intent.putExtra("type",type);
//									  startActivity(intent);
//									  }
							    	
							    }else {
							    	progress.CloseProgress();
//									Toast.makeText(ZhongAnMinShenXqActivity.this, info, 200).show();
									Intent intent = new Intent(ZhongAnMinShenXqActivity.this, TishiBaoMinOkActivity.class);
									intent.putExtra("title",xqdata.title);
						            intent.putExtra("start_time",xqdata.start_time);
									intent.putExtra("end_time",xqdata.end_time);
									intent.putExtra("address",xqdata.address);
									startActivity(intent);
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
//	private void getuseraddress() {
//		user_name = spPreferences.getString("user", "");
//		AsyncHttp.get(RealmName.REALM_NAME_LL
//				+ "/get_user_shopping_address_default?user_name=" + user_name
//				+ "", new AsyncHttpResponseHandler() {
//			@Override
//			public void onSuccess(int arg0, String arg1) {
//				
//				// TODO Auto-generated method stub
//				super.onSuccess(arg0, arg1);
//				try {
//					JSONObject jsonObject = new JSONObject(arg1);
//					System.out.println("输出用户默认收货地址================" + arg1);
//					String status = jsonObject.getString("status");
//					if (status.equals("y")) {
//						JSONObject jsot = jsonObject.getJSONObject("data");
//						UserAddressData data = new UserAddressData();
//						shopping_address_id = jsot.getString("id");
//						user_accept_name = jsot.getString("user_accept_name");
//						province = jsot.getString("province");
//						city = jsot.getString("city");
//						area = jsot.getString("area");
//						user_mobile = jsot.getString("user_mobile");
//						user_address = jsot.getString("user_address");
//
////						SharedPreferences spPreferences = getSharedPreferences("user_dizhixinxi", MODE_PRIVATE);
////						Editor editor = spPreferences.edit();
////						editor.putString("province", province);
////						editor.putString("city", city);
////						editor.putString("area", area);
////						editor.putString("user_address", user_address);
////						editor.putString("user_mobile", user_mobile);
////						editor.commit();
//						
//						progress.CloseProgress();
//					} else {
//						progress.CloseProgress();
//					}
//
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					progress.CloseProgress();
//					e.printStackTrace();
//				}
//			}
//
//		}, getApplicationContext());
//		
//	}
}
