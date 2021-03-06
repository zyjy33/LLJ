package com.hengyushop.demo.home;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.android.pliay.SignUtils;
import com.androidquery.AQuery;
import com.hengyushop.airplane.adapter.ShopingCartOrderAdapter;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.demo.at.SlipButton;
import com.hengyushop.demo.my.TishiCarArchivesActivity;
import com.hengyushop.entity.JuTuanGouData;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.ShopCarts;
import com.hengyushop.entity.UserAddressData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.UserRegisterllData;
import com.lelinju.www.AddUserAddressActivity;
import com.lelinju.www.AddressManagerActivity;
import com.lglottery.www.widget.InScrollListView;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
/**
 * 订单确认
 * @author Administrator
 *
 */
public class JuTuanConfrimActivity extends BaseActivity {
	private ListView list_address_a;
	private String yth, key, phone, imei, tel, endmoney, pwd, username,
			PassTicket, shopPassTicket, orderSerialNumber;
	private int addressid;
	private WareDao wareDao;
	private UserRegisterData registerData;
	private String strUrl;
	private ArrayList<ShopCartData> list;
	private DialogProgress progress;
	private int checkedAddressId;
	private StringBuilder orderid;
	private MyPopupWindowMenu popupWindowMenu;
	private String trade_no;
	private ArrayList<CardItem> banks = null;
	private String bankNames[] = null;
	private static final int REQUESTCODE = 10000;
	private int jf,express_fee;
	private ArrayList<ShopCarts> carts;
	private TextView tv_user_name, tv_user_address, tv_user_phone,tv_hongbao,tv_num;
	private SharedPreferences spPreferences;
	public static String user_name, user_id;
	private ImageButton btn_add_address;
	private ShopingCartOrderAdapter adapter;
	private InScrollListView list_shop_cart;
	ArrayList<ShopCartData> list_ll = new ArrayList<ShopCartData>();
	private ShopCartData data;
	private ShopCartData dm;
	private LinearLayout layout0, ll_ljgm, layout2,ll_zhifufs;
	private LinearLayout layout1;
	private TextView heji,tv_1,tv_2;
	private Button confrim_btn;
	ImageView img_ware;
	TextView tv_warename,tv_guige;
	TextView tv_color;
	TextView tv_size,tv_zhifu,shi_fukuang;
	public static boolean fanhui_type = false;
//	String name = "";
	private String ZhiFuFangShi,express_id;
	private String retailPrice,jubi;
	String type_zhifu;
	String jubi_type = "";
	String order_str,notify_url;
	public static String province,city,area,user_address,user_accept_name,user_mobile,shopping_address_id;
	public static String province1,city1,area1,user_address1,accept_name1,user_mobile1,recharge_no1,article_id1;
	public static String recharge_no,order_no,datetime1,sell_price1,give_pension1;
	int zhifu,kou_hongbao;
	private IWXAPI api;
	private String partner_id,prepayid,noncestr,timestamp,package_,sign;
	private String user_dizhiname;
	boolean zhuangtai = false;
	public static boolean teby = false;
	private int len;
	String url;
	StringBuffer shopping_ids1;
	String login_sign,dandu_goumai;
	boolean flag;
	boolean hongbao_tety = true;
	double dzongjia = 0;
	double cashing_packet = 0;
	double packet,heji_zongjia;
	String total_fee,hongbao,kedi_hongbao;
	ArrayList<JuTuanGouData> list_zf = new ArrayList<JuTuanGouData>();
	SlipButton sb;
	double jiaguo = 0;
	private LinearLayout ll_money_ju,market_information_juduihuan,market_information_bottom;
	public AQuery mAq;
//	Handler handler;
	String stare; 
	String type_wx,type_title;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			System.out.println("flag=======微信支付成功后关闭此界面=======" + flag);
			//微信支付成功后关闭此界面
			if (flag == true) {
				userloginqm();
//				finish();
			}
			
//		fanhui_type = true;
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		login_sign = spPreferences.getString("login_sign", "");
		dzongjia = 0;
//		System.out.println("dzongjiade值为零================"+dzongjia);
		
		String type_wx = getIntent().getStringExtra("type_wx");
		System.out.println("type_wx===1====" + type_wx);
		
		
		//获取地址
		user_accept_name = getIntent().getStringExtra("user_accept_name");
		System.out.println("name==============" + user_accept_name);
		if (user_accept_name != null) {
			  province = getIntent().getStringExtra("province");
			  city = getIntent().getStringExtra("city");
			  area = getIntent().getStringExtra("user_area");
			  user_address = getIntent().getStringExtra("user_address");
			  user_mobile = getIntent().getStringExtra("user_mobile");
			  
			tv_user_name.setText("收货人：" + user_accept_name);
			tv_user_address.setText("地址：" +province + "、"+city+ "、"+area+ "、" + user_address);
			tv_user_phone.setText(user_mobile);
		}
		else {
			getuseraddress2();
		}
		
//		System.out.println("teby==============" + teby);
		//余额支付成功后更新订单
//		if (teby == true) {
////			userloginqm();
//			teby = false;
//			finish();
//		}
		
		
		//余额支付取消关闭此界面
//		if (TishiCarArchivesActivity.yue_zhuangtai != null) {
//			TishiCarArchivesActivity.yue_zhuangtai = null;
//			finish();
//		}
		
		

			// 立即购买
			img_ware = (ImageView) findViewById(R.id.img_ware);
			tv_warename = (TextView) findViewById(R.id.tv_ware_name);
			tv_guige  = (TextView) findViewById(R.id.tv_guige);
			tv_color = (TextView) findViewById(R.id.tv_color);
			tv_size = (TextView) findViewById(R.id.tv_size);
			shi_fukuang = (TextView) findViewById(R.id.shi_fukuang);
			tv_num = (TextView) findViewById(R.id.tv_num);
			ll_ljgm = (LinearLayout) findViewById(R.id.ll_ljgm);
			
//			JuTuanGouData bean = (JuTuanGouData) getIntent().getSerializableExtra("bean");
//			String title = bean.getTitle();
			
//			String title = getIntent().getStringExtra("title");
//			if (title != null) {
//				    ll_ljgm.setVisibility(View.VISIBLE);
////					dzongjia = Double.parseDouble(getIntent().getStringExtra("groupon_price"));
////					tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//					tv_1.setText("团购价：");
//					tv_2.setText("团数：");
//					String activity_price = getIntent().getStringExtra("activity_price");
//					tv_color.setText( "￥" + activity_price);//团购价
//					String ct_tuanshu = getIntent().getStringExtra("ct_tuanshu");
//					tv_size.setText( "￥" +ct_tuanshu);//团数
//					heji.setText("合计:" + "￥" +  activity_price);
//					shi_fukuang.setText("￥" +  activity_price);
//					tv_warename.setText(title);
//					System.out.println("2======================");
//				    String img_url = getIntent().getStringExtra("img_url");
//				    mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP + img_url);
//			} else {
				System.out.println("3================");
				load_list(true);
//			}
			

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public static Handler handlerll;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(JuTuanConfrimActivity.this);
		api = WXAPIFactory.createWXAPI(JuTuanConfrimActivity.this,null);
		api.registerApp(Constants.APP_ID);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jutuan_confrim);
		mAq = new AQuery(this);
//		progress.CreateProgress();
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		
	       handlerll = new Handler() {
	   		public void handleMessage(Message msg) {
	   			switch (msg.what) {
	   			case 2:
	   				finish();
	   				break;
	   			}
	   		}
	   	};
		initdata();
		
	}
	
	
	private void initdata() {
		try {
			market_information_juduihuan = (LinearLayout) findViewById(R.id.market_information_juduihuan);
			confrim_btn = (Button) findViewById(R.id.confrim_btn);
			list_shop_cart = (InScrollListView) findViewById(R.id.list_shop_cart);
			btn_add_address = (ImageButton) findViewById(R.id.img_btn_add_address);
			layout0 = (LinearLayout) findViewById(R.id.layout0);
			layout1 = (LinearLayout) findViewById(R.id.layout1);
			layout2 = (LinearLayout) findViewById(R.id.layout2);
//			ll_zhifufs = (LinearLayout) findViewById(R.id.ll_zhifufs);
			tv_user_name = (TextView) findViewById(R.id.tv_user_name);
			tv_user_address = (TextView) findViewById(R.id.tv_user_address);
			tv_user_phone = (TextView) findViewById(R.id.tv_user_phone);
//			tv_hongbao = (TextView) findViewById(R.id.tv_hongbao);
//			tv_zhifu = (TextView) findViewById(R.id.tv_zhifu);
			heji = (TextView) findViewById(R.id.heji);
			tv_1 = (TextView) findViewById(R.id.tv_1);
			tv_2 = (TextView) findViewById(R.id.tv_2);
			
//			retailPrice = getIntent().getStringExtra("total_cll");
//			System.out.println("合计----------------"+retailPrice);
//			if (retailPrice != null) {
//				heji.setText("合计:" + retailPrice);
//			}

			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
					
				}
			});
			
			/**
			 * 支付方式
			 */
//			ll_zhifufs.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
////					BigDecimal   a   =   new   BigDecimal(Double.parseDouble(retailPrice)-express_fee);
////					retailPrice = Double.toString(a.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
////					System.out.println("支付方式retailPrice====================="+retailPrice);
//					Intent intent = new Intent(JuTuanConfrimActivity.this,ZhiFuFangShiActivity.class);
//					intent.putExtra("order_confrim", "order_confrim");// 
//					startActivity(intent);
//				}
//			});

			/**
			 * 收货地址列表
			 */
			layout0.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					String title = getIntent().getStringExtra("title");
					String stare = getIntent().getStringExtra("stare");
					String groupon_price = getIntent().getStringExtra("groupon_price");
					String img_url = getIntent().getStringExtra("img_url");
					String price = getIntent().getStringExtra("price");
					String goods_price = getIntent().getStringExtra("goods_price");
					String point = getIntent().getStringExtra("point");
					String shopping_ids = getIntent().getStringExtra("shopping_ids");
					String tuangou_id = getIntent().getStringExtra("tuangou_id");
					String type_wx = getIntent().getStringExtra("type_wx");
					String jdh_title = getIntent().getStringExtra("jdh_title");
					String point_id = getIntent().getStringExtra("point_id");
					String weixin = getIntent().getStringExtra("100");
					String zhifubao_weixin = getIntent().getStringExtra("200");
					String item_id = getIntent().getStringExtra("item_id");
				 	String foreman_id = getIntent().getStringExtra("foreman_id");
				 	String foreman_name = getIntent().getStringExtra("foreman_name");
				 	String jiekou = getIntent().getStringExtra("jiekou");
				 	
				 	String buy_no = getIntent().getStringExtra("buy_no");
					System.out.println("buy_no=====================" + buy_no);
					Intent intent = new Intent(JuTuanConfrimActivity.this,AddressManagerActivity.class);
					intent.putExtra("buy_no", getIntent().getStringExtra("buy_no"));
					intent.putExtra("jutuan_type", "1");
					intent.putExtra("type_title", type_title);
					intent.putExtra("type_wx", getIntent().getStringExtra("type_wx"));
					intent.putExtra("jiekou", getIntent().getStringExtra("jiekou"));
					intent.putExtra("spec_text", getIntent().getStringExtra("spec_text"));
					intent.putExtra("price", getIntent().getStringExtra("price"));
					intent.putExtra("people", getIntent().getStringExtra("people"));
//					intent.putExtra("title", title);// 标示
//					intent.putExtra("stare", stare);
//					intent.putExtra("groupon_price", groupon_price);
//					intent.putExtra("img_url", img_url);
//					intent.putExtra("price", price);
//					intent.putExtra("goods_price", goods_price);
//					intent.putExtra("point", point);
//					intent.putExtra("shopping_ids", shopping_ids);
//					intent.putExtra("tuangou_id", tuangou_id);
//					intent.putExtra("type_wx", type_wx);
//					intent.putExtra("jdh_title", jdh_title);
//					intent.putExtra("point_id", point_id);
//					intent.putExtra("order_confrim", "order_confrim");
//					intent.putExtra("100", weixin);
//					intent.putExtra("200", zhifubao_weixin);
//					intent.putExtra("item_id", item_id);
//					intent.putExtra("foreman_id", foreman_id);
//					intent.putExtra("foreman_name", foreman_name);
//					intent.putExtra("jutuan_type", "jutuan_type");
//					intent.putExtra("jiekou", jiekou);
					startActivity(intent);
//					startActivityForResult(intent, 100);
//					startActivityForResult(intent, 0);
				}
			});

			/**
			 * 添加收货地址
			 */
			layout1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
//					int index = 0;
					Intent intent = new Intent(JuTuanConfrimActivity.this,AddUserAddressActivity.class);
//					intent.putExtra("index", index);
//					startActivityForResult(intent, 0);
					startActivity(intent);
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		

		/**
		 * 结算方式
		 */
		confrim_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				System.out.println("name======11========" + user_accept_name);
				if (user_accept_name == null) {
					Toast.makeText(JuTuanConfrimActivity.this, "您还未添加收货地址",Toast.LENGTH_SHORT).show();
				}else {
					type_zhifu = "5";
					String type_wx = getIntent().getStringExtra("type_wx");
					System.out.println("type_wx===========" + type_wx);
					if (type_wx != null) {
							loadtuangou(type_zhifu,kou_hongbao);//提交聚团订单
					}else {
						loadusertijiao(type_zhifu,kou_hongbao);//提交订单
					}
					
					try {
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
			}
			}
		});
	}
	
	/**
	 * 获取登录签名
	 */
	private void userloginqm() {
		try{
			System.out.println("======11====登录签名=========");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			System.out.println("======22====登录签名========="+strUrlone);
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
							String type_jutuan = getIntent().getStringExtra("type_jutuan");//聚团状态
							System.out.println("type_jutuan-------聚团------"+type_jutuan);
							if (type_jutuan != null) {
								loadguanggao_jutuan(recharge_no,login_sign);
							}else {
								loadguanggaoll(recharge_no,login_sign);
							}
						}else{
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, JuTuanConfrimActivity.this);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 输出用户默认收货地址
	 */
//	private void getuseraddress2() {
//		try {
//			progress.CreateProgress();
//		list_ll = new ArrayList<ShopCartData>();
//		user_name = spPreferences.getString("user", "");
//		 user_id = spPreferences.getString("user_id", "");
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
//					System.out.println("输出用户默认收货地址========1========" + arg1);
//					String status = jsonObject.getString("status");
//					if (status.equals("y")) {
//						JSONObject jsot = jsonObject.getJSONObject("data");
//						UserAddressData data = new UserAddressData();
//						String user_dizhiname = jsot.getString("user_accept_name");
//						shopping_address_id = jsot.getString("id");
//						province = jsot.getString("province");
//						city = jsot.getString("city");
//						area = jsot.getString("area");
//						user_mobile = jsot.getString("user_mobile");
//						user_address = jsot.getString("user_address");
//						user_accept_name = user_dizhiname;
//						tv_user_name.setText("收货人：" + user_accept_name);
////						tv_user_address.setText(area + "、" + user_address);
//						tv_user_address.setText("地址：" +province + "、"+city+ "、"+area+ "、" + user_address);
//						tv_user_phone.setText(user_mobile);
//
//						SharedPreferences spPreferences = getSharedPreferences("user_dizhixinxi", MODE_PRIVATE);
//						Editor editor = spPreferences.edit();
//						editor.putString("province", province);
//						editor.putString("city", city);
//						editor.putString("area", area);
//						editor.putString("user_address", user_address);
//						editor.putString("user_mobile", user_mobile);
//						editor.commit();
//						
//						layout1.setVisibility(View.GONE);
//						progress.CloseProgress();
//						layout0.setVisibility(View.VISIBLE);
//					} else {
//						progress.CloseProgress();
//						layout1.setVisibility(View.VISIBLE);
//						layout0.setVisibility(View.GONE);
//					}
//
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					progress.CloseProgress();
//					e.printStackTrace();
//				}
//			}
//			
//			
//
//		}, getApplicationContext());
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//	}
	
	/**
	 * 输出用户默认收货地址
	 */
	private void getuseraddress2() {
		try {
			
		list_ll = new ArrayList<ShopCartData>();
		user_name = spPreferences.getString("user", "");
		 user_id = spPreferences.getString("user_id", "");
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_user_shopping_address?user_name=" + user_name//get_user_shopping_address_default
				+ "", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					System.out.println("输出用户默认收货地址================" + arg1);
					String status = jsonObject.getString("status");
					if (status.equals("y")) {
//						JSONObject jsot = jsonObject.getJSONObject("data");
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						for(int i=0;i<jsonArray.length();i++){
						JSONObject jsot = jsonArray.getJSONObject(i);
//						UserAddressData data = new UserAddressData();
						String user_dizhiname = jsot.getString("user_accept_name");
						shopping_address_id = jsot.getString("id");
						province = jsot.getString("province");
						city = jsot.getString("city");
						area = jsot.getString("area");
						user_mobile = jsot.getString("user_mobile");
						user_address = jsot.getString("user_address");
						user_accept_name = user_dizhiname;
						}
						tv_user_name.setText("收货人：" + user_accept_name);
						tv_user_address.setText("地址：" + province + "、"+city+ "、"+area+ "、" + user_address);
						tv_user_phone.setText(user_mobile);
						
						SharedPreferences spPreferences = getSharedPreferences("user_dizhixinxi", MODE_PRIVATE);
						Editor editor = spPreferences.edit();
						editor.putString("province", province);
						editor.putString("city", city);
						editor.putString("area", area);
						editor.putString("user_address", user_address);
						editor.putString("user_mobile", user_mobile);
						editor.commit();
						
						layout1.setVisibility(View.GONE);
						progress.CloseProgress();
						layout0.setVisibility(View.VISIBLE);
					} else {
						progress.CloseProgress();
						layout1.setVisibility(View.VISIBLE);
						layout0.setVisibility(View.GONE);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					progress.CloseProgress();
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
	 * 获取购单物信息  第1个列表数据解析
	 */
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	private void load_list(boolean flag) {
		list = new ArrayList<ShopCartData>();
		if (flag) {
			// 计数和容器清零
			CURRENT_NUM = 0;
			list = new ArrayList<ShopCartData>();
		}
		
//		String shopping_ids = getIntent().getStringExtra("shopping_ids");
//		System.out.println("shopping_ids=====================" + shopping_ids);
//		if (shopping_ids != null) {
//			System.out.println("shopping_ids=====================" + shopping_ids);
//			url = RealmName.REALM_NAME_LL + "/get_shopping_buy?pageSize=" + VIEW_NUM + "&pageIndex=" + CURRENT_NUM + "&user_id="
//					+ user_id + "&user_name="+user_name+"&shopping_ids="+shopping_ids+"";
//		}
		String buy_no = getIntent().getStringExtra("buy_no");
		System.out.println("buy_no=====================" + buy_no);
		url = RealmName.REALM_NAME_LL + "/get_shopping_buy?buy_no="+buy_no+"";
		AsyncHttp.get(url,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1" + arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								len = jsonArray.length();
								double a= 0;
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject json = jsonArray.getJSONObject(i);
									data = new ShopCartData();
									data.title = json.getString("title");
									data.market_price = json.getString("market_price");
									data.sell_price = json.getString("sell_price");
									data.cashing_packet = json.getDouble("cashing_packet");
									data.id = json.getString("id");
									data.quantity = json.getInt("quantity");
									data.img_url = json.getString("img_url");
									data.spec_text = json.getString("spec_text");
									data.activity_people = json.getString("activity_people");
									data.activity_price = json.getString("activity_price");
									data.buy_no = json.getString("buy_no");
									data.goods_no = json.getString("goods_no");
									
//									//商品价格
//									BigDecimal   c   =   new   BigDecimal(Double.parseDouble(data.sell_price)*data.quantity);
//									//保留2位小数
//									double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//									a += total_c_ll;
//									System.out.println("a---------------"+a)
//									BigDecimal   w   =   new   BigDecimal(a);  
//									dzongjia   =  w.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
//									System.out.println("dzongjia---------------"+dzongjia);
//									retailPrice = Double.toString(dzongjia);
									
									try {
									type_title = getIntent().getStringExtra("type_title");
									System.out.println("type_title------------------------------"+type_title);
									//团购价购买
									if (type_title.equals("1")) {
										BigDecimal c = new BigDecimal(Double.parseDouble(data.activity_price)*data.quantity);
										//保留2位小数
										dzongjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
										System.out.println("dzongjia------------------------------"+dzongjia);
										
//										if (getIntent().getStringExtra("spec_text").equals("")) {
											tv_guige.setText(data.spec_text);
//										}else {
//											tv_guige.setText(getIntent().getStringExtra("spec_text"));
//										}
									tv_1.setText("团购价：");
									tv_2.setText("团数：");
//									tv_color.setText( "￥" + getIntent().getStringExtra("price"));//团购价
									tv_color.setText( "￥" + dzongjia);//团购价
									tv_size.setText(data.activity_people+"人");
									heji.setText("合计:" + "￥" + dzongjia);
									shi_fukuang.setText("￥" +  dzongjia);
									}else {
										//单独购买
										BigDecimal c = new BigDecimal(Double.parseDouble(data.sell_price)*data.quantity);
										//保留2位小数
										dzongjia = c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
										System.out.println("dzongjia------------------------------"+dzongjia);
										
										tv_guige.setText(data.spec_text);
										tv_1.setText("价格：");
										tv_2.setText("市场价：");
//										tv_color.setText( "￥" + data.sell_price);//价格
										tv_color.setText( "￥" + dzongjia);//价格
										tv_size.setText("￥" + data.market_price);//市场价
										tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
										heji.setText("合计:" + "￥" +  dzongjia);
										shi_fukuang.setText("￥" +  dzongjia);
									}
									tv_warename.setText(data.title);
									tv_num.setText(String.valueOf(data.quantity));
								    mAq.id(img_ware).image(RealmName.REALM_NAME_HTTP + data.img_url);
								    
									list.add(data);
									} catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
								}
								
								
								 zhuangtai = false;
								 progress.CloseProgress();
//								 handler.sendEmptyMessage(0);
							} else {
								progress.CloseProgress();
								Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
							}							
							if (len != 0) {
//								CURRENT_NUM = CURRENT_NUM + VIEW_NUM;
								CURRENT_NUM = 1;
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
	}
	

	/**
	 * 提交用户订单  
	 * @param payment_id 
	 * @param kou_hongbao 
	 */
	private void loadusertijiao(String payment_id, int kou_hongbao) {
		try {
//			progress.CreateProgress();	
		 	String buy_no = getIntent().getStringExtra("buy_no");
			System.out.println("buy_no=====================" + buy_no);
//			if (shopping_ids != null) {
//				url = RealmName.REALM_NAME_LL+ "/order_save?user_id="+user_id+"&user_name="+user_name+"&is_cashing_packet="+0+"&is_cashing_point=0&" +
//						"shopping_ids="+shopping_ids+"&payment_id="+payment_id+"&shopping_address_id="+shopping_address_id+"&express_id="+7+"&" +
//						"is_invoice=0&accept_name="+user_accept_name+"&province="+province+"&city="+city+"&area="+area+"&address="+user_address+"" +
//								"&telphone=&mobile="+user_mobile+"&email=&post_code=&message=&invoice_title=";
//			}
			url = RealmName.REALM_NAME_LL+ "/order_save?user_id="+user_id+"&user_name="+user_name+"&user_sign="+login_sign+"&is_cashing_packet=0" +
					"&is_cashing_point=0&buy_no="+buy_no+"&payment_id="+payment_id+"&express_id=7&is_invoice=0&invoice_title=&address_id="+shopping_address_id+"" +
					"&accept_name="+user_accept_name+"&province="+province+"&city="+city+"&area="+area+"&address="+user_address+"&telphone=" +
					"&mobile="+user_mobile+"&email=&post_code=&message=";
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
							    	JSONObject jsonObject = object.getJSONObject("data");
							    	recharge_no = jsonObject.getString("trade_no");
//							    	datetime = jsonObject.getString("datetime");
//							    	order_no = jsonObject.getString("order_no");
							    	String total_amount = jsonObject.getString("total_amount");
									data = new ShopCartData();
									 if (type_zhifu.equals("3")) {
										 loadzhidu(recharge_no,total_amount);
									}else if (type_zhifu.equals("5")){
										 loadweixinzf2(recharge_no,total_amount);
									}else if (type_zhifu.equals("2")){
//										loadYue(recharge_no);
//										teby = true;
										Intent intent = new Intent(JuTuanConfrimActivity.this, TishiCarArchivesActivity.class);
										intent.putExtra("order_no",recharge_no);
										intent.putExtra("yue","yue");
										startActivity(intent);
//										finish();
									}
//									handler.sendEmptyMessage(0);
							    }else {
//							    	progress.CloseProgress();
									Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
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
	 * 提交团购订单  
	 * @param payment_id 
	 * @param kou_hongbao 
	 */
	String apl_jiekou,jiekou;
	private void loadtuangou(String payment_id, int kou_hongbao) {
//			progress.CreateProgress();	
//		 	String item_id = getIntent().getStringExtra("item_id");
//		 	String foreman_id = getIntent().getStringExtra("foreman_id");
//		 	String foreman_name = getIntent().getStringExtra("foreman_name");
//		 	System.out.println("foreman_id================================="+foreman_id);
//		 	System.out.println("foreman_name================================="+foreman_name);
//		 	if (foreman_name == null) {
//		 		foreman_id = user_id;
//		 		foreman_name = user_name;
//			}else {
//				 foreman_id = getIntent().getStringExtra("foreman_id");
//				 foreman_name = getIntent().getStringExtra("foreman_name");
//			}
//		 	System.out.println("foreman_id======================2==========="+foreman_id);
//		 	System.out.println("foreman_name====================2============="+foreman_name);
		 	
		 	jiekou = getIntent().getStringExtra("jiekou");
		 	System.out.println("jiekou==================1====================================="+jiekou);
		 	if (jiekou.equals("1")) {
		 		apl_jiekou = "add_order_groupon";//聚精彩提交订单
			}else if (jiekou.equals("2")) {
				apl_jiekou = "add_order_group";//提交聚团订单
			}else if (jiekou.equals("3")) {
				apl_jiekou = "add_order_ladder";//提交阶梯团订单
			}
		 	String buy_no = getIntent().getStringExtra("buy_no");
			System.out.println("buy_no=====================" + buy_no);
			url = RealmName.REALM_NAME_LL+ "/"+apl_jiekou+"?user_id="+user_id+"&user_name="+user_name+"&user_sign="+login_sign+"&is_cashing_packet=0" +
			"&is_cashing_point=0&buy_no="+buy_no+"&payment_id="+payment_id+"&express_id=11&is_invoice=0&invoice_title=&address_id="+shopping_address_id+"" +
			"&accept_name="+user_accept_name+"&province="+province+"&city="+city+"&area="+area+"&address="+user_address+"&telphone="+user_mobile+"" +
			"&mobile="+user_mobile+"&email=&post_code=&message=";
			
//			url = RealmName.REALM_NAME_LL+ "/"+apl_jiekou+"?user_id="+user_id+"&user_name="+user_name+"&foreman_id="+foreman_id+"&foreman_name="+foreman_name+"" +
//			"&quantity=1&item_id="+item_id+"&payment_id="+payment_id+"&shopping_address_id="+shopping_address_id+"&express_id="+7+"&is_invoice=0&accept_name="+user_accept_name+"&province="+province+"" +
//			"&city="+city+"&area="+area+"" +"&address="+user_address+"" +"&telphone=&mobile="+user_mobile+"&email=&post_code=&message=&invoice_title=";
			
		AsyncHttp.get(url,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("提交团购订单================================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
							    	Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
							    	JSONObject jsonObject = object.getJSONObject("data");
							    	recharge_no = jsonObject.getString("trade_no");
//							    	String order_no = jsonObject.getString("order_no");
//							    	System.out.println("order_no================================="+order_no);
									SharedPreferences spPreferences = getSharedPreferences("longuserset_id", Context.MODE_PRIVATE);
									Editor editor = spPreferences.edit();
									editor.putString("ct_order_no",recharge_no);
									editor.commit();
									
							    	String total_amount = jsonObject.getString("payable_amount");
//									data = new ShopCartData();
									 progress.CloseProgress();
//									 if (type_zhifu.equals("3")) {
//										 loadzhidu(recharge_no,total_amount);
//									}else
									 
									if (type_zhifu.equals("5")){
										loadweixinzf2(recharge_no,total_amount);
									}
									 
//										String fx_shuzi = getIntent().getStringExtra("fx_shuzi");//
//										System.out.println("fx_shuzi============================" + fx_shuzi);
//							    	    String stare = getIntent().getStringExtra("stare");//拼团支付跳转到分享界面
//							    	    System.out.println("stare----------------------------------------"+stare);
//							    	    String type = getIntent().getStringExtra("type");//聚精彩状态
//							    	    System.out.println("type-------------"+type);
//									 	String people = getIntent().getStringExtra("people");
//										System.out.println("buy_no=====================" + people);
//										String buy_no = getIntent().getStringExtra("buy_no");
//										System.out.println("buy_no=====================" + buy_no);
//							    		   Intent intent = new Intent(JuTuanConfrimActivity.this,JuTuanGouXq2Activity.class);
//							    		   intent.putExtra("order_no",buy_no);
//							    		   intent.putExtra("ct_tuanshu",people);
//							    		   intent.putExtra("stare",stare);//判断参团或邀请提示分享
//							    		   intent.putExtra("type",type);//聚精彩状态
//							    		   intent.putExtra("jiekou",jiekou);
//							    		   intent.putExtra("fx_shuzi",fx_shuzi);
//										   startActivity(intent);
										   
//									else if (type.equals("2")){
////										loadYue(recharge_no);
////										teby = true;
//										
//										String groupon_id = getIntent().getStringExtra("id");
//								    	String ct_tuanshu = getIntent().getStringExtra("ct_tuanshu");
//								    	String fx_key = getIntent().getStringExtra("fx_key");
//								    	String ct_id = getIntent().getStringExtra("ct_id");
//								    	String type = getIntent().getStringExtra("type");//聚精彩状态
//								    	System.out.println("type-------------"+type);
////								    	String orders_no = getIntent().getStringExtra("orders_no");
//								    	System.out.println("ct_id-------------"+ct_id);
//								    	System.out.println("stare-------------"+stare);
//										System.out.println("ct_tuanshu-------------"+ct_tuanshu);
//										Intent intent = new Intent(JuTuanConfrimActivity.this, TishiCarArchivesActivity.class);
////										intent.putExtra("orders_no",orders_no);
//										intent.putExtra("order_no",recharge_no);
//										intent.putExtra("fx_key",fx_key);
//										intent.putExtra("ct_id",ct_id);
//										intent.putExtra("yue","yue");
//										intent.putExtra("stare",stare);
//										intent.putExtra("ct_tuanshu",ct_tuanshu);
//										intent.putExtra("id",groupon_id);
//										intent.putExtra("type",type);//聚精彩状态
//										startActivity(intent);
//									}
//									handler.sendEmptyMessage(0);
							    }else {
							    	progress.CloseProgress();
							    	try {
										
									System.out.println("ct_tuanshu-------------"+TishiCarArchivesActivity.order_no);
									Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
									
							    	} catch (Exception e) {
										// TODO: handle exception
							    		e.printStackTrace();
									}
								}
							    
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
					}
					
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						System.out.println("11================================="+arg0);
						System.out.println("22================================="+arg1);
						Toast.makeText(JuTuanConfrimActivity.this, "异常", 200).show();
					}

				}, getApplicationContext());
		
	}
	

//	OnCancelListener cancelListener = new OnCancelListener() {
//
//		@Override
//		public void onCancel(DialogInterface dialog) {
//			/*
//			 * http://www.ju918.com/mi/getdata.ashx?act=UserCartInfo&appkey=
//			 * 0762222540
//			 * &key=QUPgWi93j719&sign=AAE3474591B6B22950AD09A11082D4D751DDABC9
//			 * &yth=112967999
//			 */
//		}
//	};
	
	
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case 0:
				try {
					
				System.out.println("3================"+list.size());
				adapter = new ShopingCartOrderAdapter(list, getApplicationContext(), handler);
				list_shop_cart.setAdapter(adapter);
//				ShopingCartOrderAdapter.query.clear();
//				list_shop_cart.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 1:
				ali_pay();
				break;
			case 2://微信支付
				try {
				boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
//				Toast.makeText(MyOrderConfrimActivity.this, "获取订单中...", Toast.LENGTH_SHORT).show();
				String zhou = String.valueOf(isPaySupported);
//				Toast.makeText(MyOrderConfrimActivity.this, zhou, Toast.LENGTH_SHORT).show();
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
						
						// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
						api.registerApp(Constants.APP_ID);
//						api.sendReq(req);
						flag = api.sendReq(req);
						System.out.println("支付"+flag);
						
//						finish();
//						Toast.makeText(MyOrderConfrimActivity.this, "支付true", Toast.LENGTH_SHORT).show();
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}else {
					  Toast.makeText(JuTuanConfrimActivity.this,"支付失败",Toast.LENGTH_SHORT).show();
					  finish();
				}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				break;
			case 5://支付宝
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();
				System.out.println(resultInfo + "---" + resultStatus);
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(JuTuanConfrimActivity.this, "支付成功",Toast.LENGTH_SHORT).show();
					userloginqm();
//					finish();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(JuTuanConfrimActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();
						finish();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(JuTuanConfrimActivity.this, "支付失败",Toast.LENGTH_SHORT).show();
						finish();
					}
				}
				break;
			default:
				break;
			}
		}
	};


	
	/**
	 * 聚团购更新支付 
	 * @param login_sign 
	 * @param payment_id 
	 */
	private void loadguanggao_jutuan(String recharge_noll, String login_sign) {
		try {
//			recharge_no = recharge_noll;
		   AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/update_order_payment?user_id="+user_id+"&user_name="+user_name+"" +
						"&trade_no="+recharge_noll+"&sign="+login_sign+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("聚团购更新订单================================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
							    	   progress.CloseProgress();
							    	   list_shop_cart.setVisibility(View.GONE);
							    	   teby = false;
							    	   fanhui_type = true;
									   Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
										  
//							    		String ct_tuanshu = JuTuanGouXqActivity.people;
//								        System.out.println("ct_tuanshu------1-------"+ct_tuanshu);
//								        String orders_no = getIntent().getStringExtra("orders_no");
//								    	System.out.println("orders_no----------------------------------------"+orders_no);
//								    	String ct_id = getIntent().getStringExtra("ct_id");
//								    	System.out.println("ct_id---------------------------------------------"+ct_id);
								    	String fx_shuzi = getIntent().getStringExtra("fx_shuzi");//
										System.out.println("fx_shuzi============================" + fx_shuzi);
									    String stare = getIntent().getStringExtra("stare");//拼团支付跳转到分享界面
							    	    System.out.println("stare----------------------------------------"+stare);
							    	    String type_jutuan = getIntent().getStringExtra("type_jutuan");//聚精彩状态
							    	    System.out.println("type_jutuan-------------"+type_jutuan);
									 	String people = getIntent().getStringExtra("people");
										System.out.println("people=====================" + people);
										String buy_no = getIntent().getStringExtra("buy_no");
										System.out.println("buy_no=====================" + buy_no);
							    		   Intent intent = new Intent(JuTuanConfrimActivity.this,JuTuanGouXq2Activity.class);
							    		   intent.putExtra("order_no",buy_no);
							    		   intent.putExtra("ct_tuanshu",people);
							    		   intent.putExtra("stare",stare);//判断参团或邀请提示分享
//							    		   intent.putExtra("ct_id",ct_id);
							    		   intent.putExtra("type",type_jutuan);//聚精彩状态
							    		   intent.putExtra("jiekou",jiekou);
							    		   intent.putExtra("fx_shuzi",fx_shuzi);
										   startActivity(intent);
										   
										   System.out.println("stare-------------"+stare);
										   if (stare.equals("3")) {
											  JuTuanGouXq2Activity.handlerll.sendEmptyMessage(2);
										   }
							    	   finish();
							    }else {
							    	progress.CloseProgress();
							    	teby = false;
									Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
							    	finish();
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
						Toast.makeText(JuTuanConfrimActivity.this, "网络超时异常", 200).show();
					}

				}, null);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 订单更新支付 
	 * @param login_sign 
	 * @param payment_id 
	 */
	private void loadguanggaoll(String recharge_noll, String login_sign) {
		try {
//			recharge_no = recharge_noll;
		   AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/update_order_payment?user_id="+user_id+"&user_name="+user_name+"" +
						"&trade_no="+recharge_noll+"&sign="+login_sign+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							System.out.println("订单更新订单==============1==================="+arg1);
							  String status = object.getString("status");
							    String info = object.getString("info");
							    if (status.equals("y")) {
							    	   progress.CloseProgress();
							    	   list_shop_cart.setVisibility(View.GONE);
							    	   teby = false;
								    	JSONObject jsonObject = object.getJSONObject("data");
										JSONArray jay = jsonObject.getJSONArray("orders");
										for (int j = 0; j < jay.length(); j++){
										   JSONObject objc= jay.getJSONObject(j);
										    accept_name1 = objc.getString("accept_name");
										    province1 = objc.getString("province");
											city1 = objc.getString("city");
											area1 = objc.getString("area");
											user_mobile1 = objc.getString("mobile");
											user_address1 = objc.getString("address");
											recharge_no1 = objc.getString("order_no");
											datetime1 = objc.getString("add_time");
											sell_price1 = objc.getString("payable_amount");
											
											JSONArray jsonArray = objc.getJSONArray("order_goods");
											for (int i = 0; i < jsonArray.length(); i++) {
												JSONObject json = jsonArray.getJSONObject(i);
												article_id1 = json.getString("article_id");
//												sell_price1 = json.getString("sell_price");
												give_pension1 = json.getString("give_pension");
											}
										}
							    	   Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
							    	   finish();
							    	   Intent intent = new Intent(JuTuanConfrimActivity.this,ZhiFuOKActivity.class);
									   startActivity(intent);
							    }else {
							    	progress.CloseProgress();
							    	teby = false;
									Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
							    	finish();
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
						Toast.makeText(JuTuanConfrimActivity.this, "网络超时异常", 200).show();
					}

				}, null);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 支付宝
	 * @param total_amount 
	 * @param payment_id 
	 */
	private void loadzhidu(String recharge_no3, String total_amount) {
		try {
		recharge_no = recharge_no3;
//		total_fee = String.valueOf(Double.parseDouble(retailPrice) + Double.parseDouble(String.valueOf(express_fee)));
		total_fee = total_amount;
		System.out.println("22retailPrice================================="+total_fee);
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/payment_sign?user_id="+user_id+"&user_name="+user_name+"" +
					"&total_fee="+total_amount+"&out_trade_no="+recharge_no+"&payment_type=alipay",
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
							    }else {
							    	progress.CloseProgress();
									Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
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

	
	
	/**
	 *  微信支付
	 * @param total_amount 
	 * @param payment_id 
	 */
	private void loadweixinzf2(String recharge_no2, String total_amount) {
		try {
			recharge_no	= recharge_no2;
//		System.out.println("总金额==================="+retailPrice);	
		System.out.println("total_amount=================="+total_amount);
		 String monney_ll = String.valueOf(Double.parseDouble(total_amount) *100);
		 System.out.println("monney_ll=================="+monney_ll);
		 BigDecimal   b   =   new   BigDecimal(monney_ll);  
		 String monney = String.valueOf(b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue()); 
//		 String monney = "0.01";
		 System.out.println("monney=================="+monney);
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
							}else {
						    	progress.CloseProgress();
								Toast.makeText(JuTuanConfrimActivity.this, info, 200).show();
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
	
	
	private void ali_pay() {

	}
	
	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, Common.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}
	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String dingdan) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + Common.PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + Common.SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + dingdan + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + total_fee + "\"";
//		orderInfo += "&total_fee=" + "\"" + 0.01 + "\"";
		
		// 服务器异步通知页面路径
		orderInfo += "&notify_url=" + "\"" +  notify_url + "\"";
		System.out.println("======notify_url============="+notify_url);
		
		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		System.out.println(orderInfo);
		return orderInfo;
	}
}
