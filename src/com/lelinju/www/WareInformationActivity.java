package com.lelinju.www;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.post.CommomConfrim;
import com.android.hengyu.post.CommomConfrim.onDeleteSelect;
import com.android.hengyu.pub.GoodsGgcsListAdapter;
import com.android.hengyu.pub.WideMarketAdapter;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.airplane.adapter.GuigeListAdapter;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.DBFengXiangActivity;
import com.hengyushop.demo.home.DianPingActivity;
import com.hengyushop.demo.home.JuJingCaiXqActivity;
import com.hengyushop.demo.home.JuTuanGouXqActivity;
import com.hengyushop.demo.my.TishiWxBangDingActivity;
import com.hengyushop.demo.shopcart.MyShopCarActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.JuTuanGouData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.entity.XiangqingData;
import com.lglottery.www.http.Util;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

@SuppressWarnings({ "deprecation", "unused" })
public class WareInformationActivity extends BaseActivity implements
		OnClickListener{
	private List<Map<String, String>> allGriddatas = null;
	private TextView tv_market_money, tv_ware_name, tv_hengyu_money,tv_wenzi3,
			tv_title_name, tv_ware_market_jifen,tv_integral,tv_spec_text,tv_wenzi1,tv_wenzi2;
	private Button btn_add_shop_cart;
	private LinearLayout btn_collect, btn_dianping;
	private ImageButton btn_ware_infromation_share, btn_wechat, btn_wx_friend,sms;
	private ImageButton img_btn_tencent;
	private View btn_sms;
	private LinearLayout market_information_param,market_information_sep;
	// private TabHost tabHost;
	private LayoutInflater inflater;
	private View view;
	private PopupWindow pop;
	private String strUrl, str1, str2, str3, str4, str5, str6;
	private int productItemId, AvailableIntegral;
	public static  String title,sp_id,proFaceImg, proInverseImg, proDoDetailImg, proDesignImg,title_jduihuan,
			proName, proTip, retailPrice, marketPrice, proSupplementImg,goods_price,price,exchange_point,
			proComputerInfo, yth, key, releaseBossUid, AvailableJuHongBao,Atv_integral,company_id,exchange_price,
			productCount,title_ll,spec_ids,article_id,goods_id,subtitle,spec_text,point_id,point_ll;
	XiangqingData xqdata,data_tp;
	private String sub_title,sell_price,market_price,cost_price,rebate_price;
	private LinearLayout order_shop_now;
	private WareDao wareDao;
	public static String title_jdh = "";
	public static String jdh_type = "";
	private int index = 1;
	private int Orderid;
	private int ischecked = 1;
	private int wareId;
	private DialogProgress progress;
	private IWXAPI api;
	private ArrayList<XiangqingData> lists;
	private MyPosterView market_information_images;
	
	private UserRegisterData registerData;
	private int productItemType;
	private int iSLING = -1;
	private int CID;
	private LinearLayout images_layout;
	private LinearLayout market_information_describe;
	private TextView market_information_title, market_information_tip;
	private Button enter_shop,fanhui;
	private LinearLayout ll_money_ju,market_information_juduihuan,market_information_bottom;
	GuigeData md; 
	GuigeBean mb;
	private ImageView img_shared;
	private Context context;
	private SharedPreferences spPreferences;
	public static String user_name,user_id,nickname;
	private LinearLayout ll_shiyishicai1,ll_shiyishicai2,ll_buju1,ll_buju2;
	private TextView bt_cart_all, bt_cart_low, bt_cart_stock;
	private TextView show_cart_all, show_cart_low, show_cart_stock;
	private ArrayList<GuigeData> list_ll = null;
	private ArrayList<GuigeData> list_lb = null;
	private WebView webview;
	private ListView new_list;
	int spjs = 1;
	boolean zhuantai = false;
	private ImageView ling_tip;
	String point;
	public static int fangshi = 0;
	public static int spec_text_list = 0;//销售套餐判断为0
	public static boolean taocan_type = false;//判断商品套餐价格
	public AQuery mAq;
	ArrayList<XiangqingData> list_ggcs;
	int len;
	public static boolean type_spec_item = false;
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JuJingCaiXqActivity.type_xq = false;//聚团详情销售属性不显示
		JuJingCaiXqActivity.type_spec_item = false;//聚团详情销售属性不显示
		JuTuanGouXqActivity.type_xq = false;//聚团详情销售属性不显示
		JuTuanGouXqActivity.type_spec_item = false;//聚团详情销售属性不显示
		JuJingCaiXqActivity.spec_text_list = 0;//销售套餐判断为0
		JuTuanGouXqActivity.spec_text_list = 0;//销售套餐判断为0
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shenghuoguan_home);//new_ware_infromation  shenghuoguan_home activity_fdxq
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		nickname = spPreferences.getString("nickname", "");
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		id = spPreferences.getString("user_id", "");
		WareInformationActivity.title_jduihuan = null;//判断为聚兑换显示数据
		mAq = new AQuery(this);
		try {
//			RelativeLayout rl_title = (RelativeLayout)findViewById(R.id.rl_title);
//			rl_title.getBackground().setAlpha(50);
//		imageLoader.clearMemoryCache();
		progress = new DialogProgress(WareInformationActivity.this);
		progress.CreateProgress();
		
		innidata();
		String jdh_id = getIntent().getStringExtra("jdh_id");
		System.out.println("jdh_id=============="+jdh_id);
		if (jdh_id != null) {
			market_information_juduihuan.setVisibility(view.VISIBLE);
			market_information_bottom.setVisibility(view.GONE);
//			loadJuDuiHuan(jdh_id);//获取聚兑换商品详情
			getjutuanxq(jdh_id);//获取聚兑换商品详情
		}else {
			market_information_juduihuan.setVisibility(view.GONE);
			market_information_bottom.setVisibility(view.VISIBLE);
			loadWeather();//获取商品详情
		}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	/**
	 * 获取聚兑换商品详情
	 * @param groupon_id 
	 * @param category_id 
	 */
	JuTuanGouData data_xq;
	public static ArrayList data_shuzu,data_monney,data_goods_id,data_market_price
	,data_people,data_goods_id_1,data_people_1,data_price,data_spec_text,data_mrz,data_exchange_price,data_exchange_point;
	private void getjutuanxq(String jdh_id) {
		 data_shuzu = new ArrayList();
		 data_monney = new ArrayList();
		 data_goods_id = new ArrayList();
		 data_market_price = new ArrayList();
		 data_people = new ArrayList();
		 data_goods_id_1 = new ArrayList();
		 data_people_1 = new ArrayList();
		 data_price = new ArrayList();
		 data_spec_text = new ArrayList();
		 data_exchange_price = new ArrayList();
		 data_exchange_point = new ArrayList();
//		list_ll = new ArrayList<JuTuanGouData>();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_article_foreman_list?article_id="+jdh_id+"&datatype=2&top=1"
				, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				System.out.println("输出内容详情========="+arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
//					datetime = object.getString("datetime");
					if (status.equals("y")) {
						JSONObject jsonobt = object.getJSONObject("data");
						JSONObject obj= jsonobt.getJSONObject("article_model");
				        
						data_xq = new JuTuanGouData();
						data_xq.setId(obj.getString("id"));
						data_xq.setTitle(obj.getString("title"));
						data_xq.setImg_url(obj.getString("img_url"));
						data_xq.setAdd_time(obj.getString("add_time"));
						data_xq.setStart_time(obj.getString("start_time"));
						data_xq.setUpdate_time(obj.getString("update_time"));
						data_xq.setCategory_id(obj.getString("category_id"));
						data_xq.setEnd_time(obj.getString("end_time"));
						data_xq.setCompany_id(obj.getString("company_id"));
						sp_id = data_xq.getId();
						point_id = obj.getString("id");
						title = obj.getString("title");
						title_jduihuan = obj.getString("title");
						subtitle = obj.getString("subtitle");
				        proInverseImg = obj.getString("img_url");
				        company_id = obj.getString("company_id");
				        proFaceImg = obj.getString("img_url");
						
							JSONObject jsot = obj.getJSONObject("default_spec_item");
							data_xq.setGoods_id(jsot.getString("goods_id"));
							data_xq.setSell_price(jsot.getString("sell_price"));
							data_xq.setArticle_id(jsot.getString("article_id"));
							data_xq.setMarket_price(jsot.getString("market_price"));
							data_xq.setCashing_packet(jsot.getString("cashing_packet"));
							data_xq.setCashing_point(jsot.getString("cashing_point"));
							data_xq.setSpec_text(jsot.getString("spec_text"));
							data_xq.setExchange_point(jsot.getString("exchange_point"));
							data_xq.setExchange_price(jsot.getString("exchange_price"));
					        point_ll =  jsot.getString("cashing_point");
					    	goods_price = jsot.getString("cashing_packet");
					    	retailPrice = jsot.getString("sell_price");
					        price = jsot.getString("market_price");
					        spec_text = jsot.getString("spec_text");
					        goods_id = jsot.getString("goods_id");
					        article_id =  jsot.getString("article_id");
					        exchange_point =  jsot.getString("exchange_point");
					        exchange_price =  jsot.getString("exchange_price");
					    	
							JSONArray jsonay = obj.getJSONArray("spec_item");
				    		for (int i = 0; i < jsonay.length(); i++) {
				    		JSONObject objt= jsonay.getJSONObject(i);
				    		JuTuanGouData data = new JuTuanGouData();
//				    		data.setSpec_text(objt.getString("spec_text"));
				    		data.setSell_price(objt.getString("sell_price"));
				    		data.setMarket_price(objt.getString("market_price"));
				    		data.setSpec_ids(objt.getString("spec_ids"));
				    		data.setGoods_id(objt.getString("goods_id"));
				    		data.setArticle_id(objt.getString("article_id"));
				    		data.setSpec_text(objt.getString("spec_text"));
				    		data.setExchange_point(objt.getString("exchange_point"));
				    		data.setExchange_price(objt.getString("exchange_price"));
							data_shuzu.add(data.spec_ids);
							data_monney.add(data.sell_price);
							data_market_price.add(data.market_price);
							data_goods_id.add(data.goods_id);
							data_spec_text.add(data.spec_text);
							data_exchange_point.add(data.exchange_point);
						    data_exchange_price.add(data.exchange_price);
						    
//							JSONArray jsonArray2 = objt.getJSONArray("activity_price");
//							for (int k = 0; k < jsonArray2.length(); k++) {
//							JSONObject objet2 = jsonArray2.getJSONObject(k);
//							JuTuanGouData data1 = new JuTuanGouData();
//							data1.setGoods_id(objet2.getString("goods_id"));
//							data1.setPeople(objet2.getString("people"));
//							data1.setPrice(objet2.getString("price"));
//							data_goods_id_1.add(data1.goods_id);
//							data_people_1.add(data1.people);
//							data_price.add(data1.price);
//				            }
						    
						    }
//							JSONObject jsoct = jsot.getJSONObject("default_activity_price");
//							data.setPeople(jsoct.getString("people"));
//							data.setPrice(jsoct.getString("price"));
//							JSONArray jsonArray = jsot.getJSONArray("activity_price");
//							for (int k = 0; k < jsonArray.length(); k++) {
//								JSONObject objet2 = jsonArray.getJSONObject(k);
//								JuTuanGouData data = new JuTuanGouData();
//								data.setGoods_id(objet2.getString("goods_id"));
//								data.setPeople(objet2.getString("people"));
//								data.setPrice(objet2.getString("price"));
////							list.add(data);
//					        }
							
//						JSONObject jsonobt = object.getJSONObject("data");
//						JSONArray jsot_ll = jsonobt.getJSONArray("foreman_list");
//						for (int k = 0; k < jsot_ll.length(); k++) {
//						JSONObject obj1 = jsot_ll.getJSONObject(k);
//						data.setOrder_no(obj1.getString("order_no"));
//						data.setCompany_id(obj1.getString("company_id"));
//						JSONArray jsot1 = obj1.getJSONArray("order_goods");
//						for (int q = 0; q < jsot1.length(); q++) {
//							JSONObject jsont = jsot1.getJSONObject(q);
////							data = new JuTuanGouData();
//							data.setArticle_id(jsont.getString("article_id"));
//							data.setOrder_id(jsont.getString("order_id"));
//							data.setGoods_id(jsont.getString("goods_id"));
//							data.setQuantity(jsont.getString("quantity"));
//////						data.setShare_img_url(jsont.getString("share_img_url"));
//							data.setArticle_title(jsont.getString("article_title"));
//							data.setImg_url(jsont.getString("img_url"));
//							data.setForeman_id(jsont.getString("foreman_id"));
//							data.setForeman_name(jsont.getString("foreman_name"));
//							data.setTimer_time(jsont.getString("timer_time"));
//							data.setEnd_time(jsont.getString("end_time"));
//							data.setStart_time(jsont.getString("start_time"));
//							data.setActivity_people(jsont.getInt("activity_people"));
//							data.setActivity_member(jsont.getInt("activity_member"));
//							data.setActivity_price(jsont.getString("activity_price"));
//							data.setSell_price(jsont.getString("sell_price"));
//				    		data.setMarket_price(jsont.getString("market_price"));
//							foreman_id = data.getForeman_id();
//							foreman_name = data.getForeman_name();
////							share_img_url = data.getShare_img_url();
//							timer_time = data.getTimer_time();
//							ct_id = data.getOrder_id();
//							ct_tuanshu = String.valueOf(data.getActivity_people()- data.getActivity_member());
////							list_ll.add(data);
//							}
//						}
					}else {
						Toast.makeText(WareInformationActivity.this, info, 200).show();
					}
					webview.loadUrl(RealmName.REALM_NAME_HTTP + "/mobile/goods/conent-"+data_xq.getArticle_id()+".html");//商品介绍
					userjubi();//获取聚币
					handler.sendEmptyMessage(4);
					progress.CloseProgress();
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}, null);
	}
	
//	/**
//	 * 获取聚兑换商品详情
//	 * @param jdh_id 
//	 */
//	private void loadJuDuiHuan(String jdh_id) {
//		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_game_point_content?id="+jdh_id+"",new AsyncHttpResponseHandler() {
//							@Override
//							public void onSuccess(int arg0, String arg1) {
//								// TODO Auto-generated method stub
//								super.onSuccess(arg0, arg1);
//								System.out.println("=========解析数据============"+arg1);
//								formaJuDuiHuan(arg1);
//							}
//							
//						}, null);
//	}
//	
//	private void formaJuDuiHuan(String result) {
//		 lists = new ArrayList<XiangqingData>();
//		try {
//			System.out.println("=======详情数据=="+result);
//			JSONObject object = new JSONObject(result);
//			String status = object.getString("status");
//			String info = object.getString("info");
//			if (status.equals("y")) {
//			JSONObject jobt = object.getJSONObject("data");
//			xqdata = new XiangqingData();
//			point_id = jobt.getString("id");
//			title = jobt.getString("title");
//			goods_price = jobt.getString("goods_price");
//	        xqdata.setId(jobt.getString("id"));
//	        proInverseImg = jobt.getString("img_url");
//	        point_ll =  jobt.getString("point");
//	        goods_id = jobt.getString("goods_id");
//	        article_id =  jobt.getString("article_id");
//	        price = jobt.getString("price");
//	        spec_text = jobt.getString("spec_text");
//	        company_id = jobt.getString("company_id");
//	        
//			System.out.println("=====article_id====================="+article_id);
//			System.out.println("=====goods_id====================="+goods_id);
//			
//			lists.add(xqdata);
//			handler.sendEmptyMessage(4);
//			userjubi();//获取聚币
//    		progress.CloseProgress();
//    		
//			}else {
//				progress.CloseProgress();
//				Toast.makeText(WareInformationActivity.this, info, 200).show();
//			}
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 获取商品详情
	 */
	private void loadWeather() {
		String id = getIntent().getStringExtra("id");
//		int  id = Integer.parseInt(getIntent().getStringExtra("id"));
//		int  id = Integer.parseInt(id_ll);
		System.out.println("=========1============"+id);//5897
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_id_content?id="+id+"",new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("=========解析数据============"+arg1);
//								Message msg = new Message();
//								msg.what = 007;
//								msg.obj = arg1;
//								handler.sendMessage(msg);
								formatWeather(arg1);
							}
							
							
						}, null);
	}
	
	private ArrayList data_id,data1;
	private String id;
	private void formatWeather(String result) {
		 data_id = new ArrayList();
		 data_shuzu = new ArrayList();
		 data_mrz = new ArrayList();
		 data_monney = new ArrayList();
		 data_goods_id = new ArrayList();
		 data_spec_text = new ArrayList();
		 data_market_price = new ArrayList();
		 lists = new ArrayList<XiangqingData>();
		try {
			System.out.println("=======详情数据=="+result);
			JSONObject object = new JSONObject(result);
			String status = object.getString("status");
			String info = object.getString("info");
			if (status.equals("y")) {
				
			JSONObject jobt = object.getJSONObject("data");
			xqdata = new XiangqingData();
	        xqdata.setTitle(jobt.getString("title"));
	        xqdata.setSubtitle(jobt.getString("subtitle"));
	        xqdata.setId(jobt.getString("id"));
	        xqdata.img_url = jobt.getString("img_url");
	        company_id = jobt.getString("company_id");
	        proInverseImg = xqdata.img_url;
			proName = xqdata.getTitle();
			title = xqdata.getTitle();
			subtitle = xqdata.getSubtitle();
			sp_id = xqdata.getId();
			
//			JSONObject job = jobt.getJSONObject("spec_item");
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
			
			spec_ids = xqdata.getSpec_ids();
			proTip = xqdata.getSub_title();
			retailPrice = xqdata.getSell_price();
			marketPrice = xqdata.getMarket_price();
			AvailableJuHongBao = xqdata.getCost_price();
			Atv_integral = xqdata.getRebate_price();
			goods_id = xqdata.getGoods_id();
			article_id = xqdata.getArticle_id();
			spec_text =xqdata.getSpec_text();
			String is_default = objt.getString("is_default");
			
			data_mrz.add(is_default);
			data_shuzu.add(spec_ids);
			data_monney.add(retailPrice);
			data_spec_text.add(spec_text);
			data_market_price.add(marketPrice);
			data_goods_id.add(xqdata.goods_id);
			System.out.println("=========数据============"+spec_ids);
		    }
			JSONArray jsonArray = jobt.getJSONArray("albums");
			ArrayList<XiangqingData> list_tp = new ArrayList<XiangqingData>();
    		for (int i = 0; i < jsonArray.length(); i++) {
    		XiangqingData data_tp = new XiangqingData();
    		JSONObject obj= jsonArray.getJSONObject(i);
    		data_tp.setThumb_path(obj.getString("thumb_path"));
    		data_tp.setOriginal_path(obj.getString("original_path"));
    		proFaceImg = obj.getString("thumb_path");
			proInverseImg = obj.getString("original_path");
//			data_tp.original_path = obj.getString("original_path");
			System.out.println("=========original_path============"+proInverseImg);
			list_tp.add(data_tp);
    		}
    		
    		try {
				
        		JSONArray jsonArray1 = jobt.getJSONArray("param");
        		len = jsonArray1.length();
        		System.out.println("=========数据len============"+len);
        		System.out.println("=========数据len============"+jsonArray1);
        		if (len > 0) {
        			list_ggcs = new ArrayList<XiangqingData>();
            		for (int i = 0; i < jsonArray1.length(); i++) {
            		XiangqingData data_ggcs = new XiangqingData();
            		JSONObject obj= jsonArray1.getJSONObject(i);
            		data_ggcs.setTitle(obj.getString("title"));
            		data_ggcs.setContent(obj.getString("content"));
            		list_ggcs.add(data_ggcs);
            		}
            		
            		System.out.println("=========list_ggcs============"+list_ggcs.size());
    			}
        		} catch (Exception e) {
    				// TODO: handle exception
        			e.printStackTrace();
    			}
    		
			lists.add(xqdata);
			Message msg = new Message();
			msg.obj = list_tp;
			msg.what = 0;
			childHandler.sendMessage(msg);
			
			handler.sendEmptyMessage(2);
    		progress.CloseProgress();
    		
			}else {
				progress.CloseProgress();
				Toast.makeText(WareInformationActivity.this, info, 200).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	ArrayList<XiangqingData> tempss;
	private Handler childHandler = new Handler() {
		@SuppressWarnings("unchecked")
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				tempss = (ArrayList<XiangqingData>) msg.obj;
				ArrayList<String> urls = new ArrayList<String>();
				for(int i=0;i<tempss.size();i++){
					urls.add(RealmName.REALM_NAME_HTTP + tempss.get(i).getOriginal_path());
					System.out.println("tempss================="+tempss.get(i).getOriginal_path());
				}
				
				if (urls.size() == 1) {
					 mAq.id(ling_tip).image(RealmName.REALM_NAME_HTTP+proInverseImg);
				}else {
				market_information_images.setData(urls, new MyPosterOnClick() {
					@Override
					public void onMyclick(int position) {
						// TODO Auto-generated method stub
//						Message msg = new Message();
//						msg.what = 13;
//						msg.obj = tempss.get(position).getId();
//						handler.sendMessage(msg);
					}
				}, true, imageLoader, true);
				}
				break;
			default:
				break;
			}
		};
	};
	private void softshareWxChat(String text) {
		try {
			
		String temp[] = text.split("http");
		api = WXAPIFactory.createWXAPI(WareInformationActivity.this, Constants.APP_ID, false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http"+temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
//		msg.title = "我发你一个软件,看看呗!";
		msg.title = "云商聚的分享";
		msg.description = temp[0];
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon);
		msg.thumbData = Util.bmpToByteArray(thumb, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);
		
		System.out.println("微信註冊" + flag);
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	}
	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		market_information_images.puseExecutorService();
//	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case  007:
				formatWeather((String) msg.obj);
				break;
			case 16:
				System.out.println("到这里了16");
				String text1 = (String) msg.obj;
				softshareWxChat(text1);
				break;
			case 4:
				
//				market_information_tip.setVisibility(view.VISIBLE);
				market_information_title.setText(title);
				market_information_tip.setText(subtitle);
//				tv_hengyu_money.setText(point_ll);
				tv_hengyu_money.setText(data_xq.exchange_point+"乐豆"+"+"+data_xq.exchange_price+"元");//兑换价
				tv_ware_market_jifen.setText( "￥" + goods_price);
				tv_ware_market_jifen.setTextColor(Color.BLACK);
				tv_market_money.setText( "￥" + price);
				tv_ware_market_jifen.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//				price = "100";
//				if(!price.equals("0")){
//					tv_market_money.setText( "￥" + price);
//					 tv_wenzi3.setText("价格:");
//				}else {
//					ll_money_ju.setVisibility(View.GONE);
//				}
				
				tv_spec_text.setText(spec_text);
				ling_tip.setVisibility(view.VISIBLE);
			    mAq.id(ling_tip).image(RealmName.REALM_NAME_HTTP+proInverseImg);
			    
			    tv_wenzi1.setText("乐豆兑换:");
//			    tv_wenzi2.setText("市场价:");
			    
				//立刻购买
			    market_information_juduihuan.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						System.out.println("point_ll========1=========="+point_ll);
						if (!user_name.equals("")) {
							try {
							int jubi = Integer.parseInt(point_ll);
							int dq_jubi =  Integer.parseInt(point);
//							int dq_jubi =  10000;
							System.out.println("jubi=================="+jubi);
							System.out.println("dq_jubi==================="+dq_jubi);
							if (point.equals("0")) {
								Toast.makeText(WareInformationActivity.this, "您的乐豆为0", 200).show();
							}else if (dq_jubi >= jubi){
//								type_spec_item = true;
								fangshi = 3;//销售属性判断
								title_jdh = "2";
								taocan_type = false;//积分兑换判断套餐价格显示
								spec_text_list = 1;//销售套餐判断为1
								jdh_type = getIntent().getStringExtra("jdh_type");//乐豆兑换调用接口
								System.out.println("jdh_type==================="+jdh_type);
								CommomConfrim.showSheet(WareInformationActivity.this, new onDeleteSelect() {
									@Override
									public void onClick(String resID) {
										// TODO Auto-generated method stub
				  					  }
								     }, article_id);
								progress.CloseProgress();
								
//								Intent intent=new Intent(WareInformationActivity.this, MyOrderConfrimActivity.class);
//								intent.putExtra("point_id",point_id);
//								intent.putExtra("img_url",proInverseImg);
//								intent.putExtra("point",point_ll);
//								intent.putExtra("goods_price",goods_price);
//								intent.putExtra("jdh_title",title);
//								intent.putExtra("price",price);
//								startActivity(intent);
							} else {
								Toast.makeText(WareInformationActivity.this, "您当前的乐豆还不够兑换", 200).show();
							}
							
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						} else {
							Intent intent = new Intent(WareInformationActivity.this,UserLoginActivity.class);
							startActivity(intent);
							progress.CloseProgress();
						}
//						}

					}
				});
				
				break;
			case 2:
				try {
//					market_information_juduihuan.setVisibility(view.GONE);
//					market_information_bottom.setVisibility(view.VISIBLE);
					
				System.out.println("值==================="+retailPrice);
				String id = getIntent().getStringExtra("id");
				System.out.println("=========================值id==================="+id);
				if(!id.equals("")){
					webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/goods/conent-"+id+".html");//商品介绍
				}else{
					if (spjs == 1) {
						webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/goods/conent-"+article_id+".html");//商品介绍
					}
				}
				tv_hengyu_money.setText( "￥" + retailPrice);
				tv_market_money.setText( "￥" + marketPrice);
				market_information_title.setText(proName + "");
				market_information_tip.setText(subtitle);
				tv_spec_text.setText(spec_text);
				if(xqdata.cashing_packet != null){
					tv_ware_market_jifen.setText( "￥" + xqdata.cashing_packet);//红包
				}else{
					ll_buju1.setVisibility(view.GONE);
				}
				
//				if(xqdata.give_pension != null){
//					tv_integral.setText( "￥" + xqdata.give_pension);//养老金
//				}else{
//					ll_buju2.setVisibility(view.GONE);
//				}
			 
//				ArrayList<String> images_ll = getDatall();
//				imageLoader.clearMemoryCache();
//				market_information_images.clearMemory();
//				System.out.println("=========图片值============"+images_ll.size());
// 
				
//				ArrayList<String> urls = new ArrayList<String>();
//				for(int i=0;i<list_tp.size();i++){
//					System.out.println("=========getOriginal_path============"+list_tp.get(i).getOriginal_path());
//					urls.add(RealmName.REALM_NAME_HTTP +list_tp.get(i).getOriginal_path());
//				}
//				System.out.println("=========lists============"+list_tp.size());
//				System.out.println("=========urls============"+urls.size());
//				//动态广告
//				if(images_ll.size()==1){
//					market_information_images.setData(images_ll,new MyPosterOnClick() {
//
//								@Override
//								public void onMyclick(int position) {
//									// TODO Auto-generated method stub
//
//								}
//							}, true, imageLoader, false);
//				}else{
//					market_information_images.setData(images_ll,new MyPosterOnClick() {
//
//								@Override
//								public void onMyclick(int position) {
//									// TODO Auto-generated method stub
//
//								}
//							}, true, imageLoader, true);
//				}
				
				progress.CloseProgress();
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case -1:
				Toast.makeText(getApplicationContext(), "添加失败", 200).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "成功加入购物车", 200).show();
				progress.CloseProgress();
				try {

					AppManager.getAppManager().finishActivity(
							WareClassifyFourActivity.class);
					AppManager.getAppManager().finishActivity(
							WareClassifyThreeActivity.class);
					AppManager.getAppManager().finishActivity(
							WareClassifyTwoActivity.class);
					AppManager.getAppManager().finishActivity(NewWare.class);
				} catch (ConcurrentModificationException e) {
					// TODO: handle exception

				}
				setResult(3, new Intent(WareInformationActivity.this,
						MainFragment.class));
				AppManager.getAppManager().finishActivity();
				break;
			case 0:
//				formatWeatherll((String) msg.obj);
				break;
			case 3:
				String str = (String) msg.obj;
				progress.CloseProgress();
				Toast.makeText(getApplicationContext(), str, 200).show();
				break;
			case 20:
				String mess = (String) msg.obj;
				Toast.makeText(getApplicationContext(), mess, 100).show();
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
				break;
			case 5:
//				adapter = new GuigeListAdapter(WareInformationActivity.this,list,data);
//				listview_01.setAdapter(adapter);
				adapter.notifyDataSetChanged();
		        System.out.println("=====5=====================");
				break;
			default:
				break;
			}
		};
	};
	
//	private ArrayList<String> getDatall() {
//		ArrayList<String> list = new ArrayList<String>();
//		System.out.println("=========list_tp============"+list_tp.size());
//		for(int i=0;i<list_tp.size();i++){
//			System.out.println("=========getOriginal_path============"+list_tp.get(i).getOriginal_path());
//			list.add(RealmName.REALM_NAME_HTTP + list_tp.get(i).getOriginal_path());
//		}
//		return list;
//	}
	
	/**
	 * 获取当前聚币
	 * @param order_no 
	 */
	private void userjubi() {
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							point = obj.getString("point");
							System.out.println("point-------------"+point);
						}else{
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, null);
			
	}
//	private void load() throws JSONException, Exception {
////		String str2 = RealmName.REALM_NAME + "/mi/getdata.ashx";
////
////		Map<String, String> params = new HashMap<String, String>();
////		params.put("act", "myInfo");
////		params.put("key", registerData.getUserkey());
////		params.put("yth", yth);
////		AsyncHttp.post_1(str2, params, new AsyncHttpResponseHandler() {
////			@Override
////			public void onSuccess(int arg0, String arg1) {
////				// TODO Auto-generated method stub
////				super.onSuccess(arg0, arg1);
////				UserRegisterData data2 = null;
////				try {
////					JSONObject object2 = new JSONObject(arg1);
////					data2 = new UserRegisterData();
////					data2.hengyuCode = object2.getString("HengYuCode");
////					data2.userName = object2.getString("username");
////					data2.PassTicketBalance = object2
////							.getString("PassTicketBalance");
////					data2.shopPassTicket = object2.getString("shopPassTicket");
////					data2.avatarimageURL = object2.getString("avatarimageURL");
////					data2.credits = object2.getString("credits");
////				} catch (JSONException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////				Message msg = new Message();
////				msg.what = 1;
////				msg.obj = data2;
////				handler.sendMessage(msg);
////			}
////		});
//
//		
//	}
	
	private void loadgouwuche() {
		try {
			progress.CreateProgress();
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String user_id = spPreferences.getString("user_id", "");
			
			  System.out.println("=====user_id====================="+article_id);
			  System.out.println("=====user_name====================="+goods_id);
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_buy?user_id="+user_id+"&user_name="+user_name+
					"&article_id="+article_id+"&goods_id="+goods_id+"&quantity="+1+"",new AsyncHttpResponseHandler() {
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
									String id = obj.getString("id");
									String count = obj.getString("count");
//									Toast.makeText(WareInformationActivity.this, info, 200).show();
										Intent intent=new Intent(WareInformationActivity.this, MyOrderConfrimActivity.class);
										intent.putExtra("shopping_ids",id);
										startActivity(intent);
								}else {
									progress.CloseProgress();
									Toast.makeText(WareInformationActivity.this, info, 200).show();
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
						@Override
						public void onFailure(Throwable arg0, String arg1) {
							// TODO Auto-generated method stub
							System.out.println("==========================访问接口失败！");
							System.out.println("========================="+arg0);
							System.out.println("=========================="+arg1);
							super.onFailure(arg0, arg1);
						}
						

					}, getApplicationContext());
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	private void loadWeatherll() {
		
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_spec_list?" +
				"channel_name=goods",new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						Message msg = new Message();
						msg.what = 0;
						msg.obj = arg1;
						handler.sendMessage(msg);
					}
				}, null);
	}
	
	ArrayList<GuigeData> list = new ArrayList<GuigeData>();
	
	private GuigeListAdapter adapter;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppManager.getAppManager().finishActivity();
		}
		return true;
	}

	private void innidata() {
		ling_tip = (ImageView) findViewById(R.id.ling_tip);
		bt_cart_all = (TextView) findViewById(R.id.bt_cart_all);
		bt_cart_low = (TextView) findViewById(R.id.bt_cart_low);
		show_cart_all = (TextView) findViewById(R.id.show_cart_all);
		show_cart_low = (TextView) findViewById(R.id.show_cart_low);
		 ll_shiyishicai1=(LinearLayout) findViewById(R.id.ll_shiyishicai1);
		 ll_shiyishicai2=(LinearLayout) findViewById(R.id.ll_shiyishicai2);
		 ll_buju1=(LinearLayout) findViewById(R.id.ll_buju1);
		 ll_buju2=(LinearLayout) findViewById(R.id.ll_buju2);
			webview = (WebView) findViewById(R.id.webview);
			webview.getSettings().setJavaScriptEnabled(true);
			webview.addJavascriptInterface(new JavascriptHandler(), "handler");
			webview.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageFinished(WebView view, String url) {
				}
			});
			
		 bt_cart_all.setOnClickListener(this);
		 bt_cart_low.setOnClickListener(this);
		 new_list = (ListView) findViewById(R.id.new_list);
		img_shared = (ImageView) findViewById(R.id.img_shared);
		img_shared.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				try {
					if (!nickname.equals("")) {
						if (!user_name.equals("")) {
							Intent intentll = new Intent(WareInformationActivity.this,DBFengXiangActivity.class);
							intentll.putExtra("sp_id",sp_id);
							intentll.putExtra("title",title);
							intentll.putExtra("subtitle",subtitle);
							intentll.putExtra("img_url", proInverseImg);
							intentll.putExtra("company_id", company_id);
							startActivity(intentll);
						} else {
						Intent intent = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
						startActivity(intent);
						progress.CloseProgress();
						}
					}else {
					
					if (user_name.equals("")) {
						Intent intent = new Intent(WareInformationActivity.this,UserLoginActivity.class);
						startActivity(intent);
						progress.CloseProgress();
					}else {
//					SoftWarePopuWindow(img_shared, context);
						Intent intentll = new Intent(WareInformationActivity.this,DBFengXiangActivity.class);
						intentll.putExtra("sp_id",sp_id);
						intentll.putExtra("title",title);
						intentll.putExtra("subtitle",subtitle);
						intentll.putExtra("company_id", company_id);
						intentll.putExtra("img_url", proInverseImg);
						startActivity(intentll);
					}
				}
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
		order_shop_now = (LinearLayout) findViewById(R.id.order_shop_now);
		btn_add_shop_cart = (Button) findViewById(R.id.btn_add_shop_cart);
		// btn_ware_infromation_share = (ImageButton)
		// findViewById(R.id.img_btn_ware_infromation_share);
		// btn_ware_infromation_share.setOnClickListener(this);
		btn_collect = (LinearLayout) findViewById(R.id.btn_collect);
		btn_collect.setOnClickListener(this);
		btn_dianping = (LinearLayout) findViewById(R.id.btn_dianping);
		market_information_param = (LinearLayout) findViewById(R.id.market_information_param);
		market_information_sep = (LinearLayout) findViewById(R.id.market_information_sep);
		btn_dianping.setOnClickListener(this);
		
//		if (getIntent().hasExtra("vip")) {
//			btn_dianping.setVisibility(View.INVISIBLE);
//			btn_collect.setVisibility(View.INVISIBLE);
//			btn_add_shop_cart.setText("升级VIP套餐");
//		}
		
		market_information_describe = (LinearLayout) findViewById(R.id.market_information_describe);
		images_layout = (LinearLayout) findViewById(R.id.images_layout);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;// 宽度height = dm.heightPixels ;
		images_layout.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, widthPixels));
		tv_ware_market_jifen = (TextView) findViewById(R.id.tv_ware_market_jifen);
		tv_integral = (TextView) findViewById(R.id.tv_integral);
		tv_market_money = (TextView) findViewById(R.id.tv_ware_market_money);
		market_information_title = (TextView) findViewById(R.id.market_information_title);
		market_information_tip = (TextView) findViewById(R.id.market_information_tip);
		tv_hengyu_money = (TextView) findViewById(R.id.tv_ware_hengyu_money);
		tv_wenzi1 = (TextView) findViewById(R.id.tv_wenzi1);
		tv_wenzi2 = (TextView) findViewById(R.id.tv_wenzi2);
		tv_wenzi3 = (TextView) findViewById(R.id.tv_wenzi3);
		tv_spec_text = (TextView) findViewById(R.id.tv_spec_text);
//		tv_market_money.getPaint().setFlags(
//				Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置文字的中划线
		market_information_images = (MyPosterView) findViewById(R.id.market_information_images);
		enter_shop = (Button) findViewById(R.id.enter_shop);
		fanhui = (Button) findViewById(R.id.fanhui);
		ll_money_ju = (LinearLayout) findViewById(R.id.ll_money_ju);
		market_information_juduihuan = (LinearLayout) findViewById(R.id.market_information_juduihuan);
		market_information_bottom = (LinearLayout) findViewById(R.id.market_information_bottom);
		
		//返回
		fanhui.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				
		//跳转到购物车
		enter_shop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("点击");
				if (!nickname.equals("")) {
					if (!user_name.equals("")) {
						Intent Intent2 = new Intent(WareInformationActivity.this,MyShopCarActivity.class);
						startActivity(Intent2);
					} else {
					Intent intent = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
					startActivity(intent);
					progress.CloseProgress();
					}
				}else {
				if (user_name.equals("")) {
					Intent intent = new Intent(WareInformationActivity.this,UserLoginActivity.class);
					startActivity(intent);
					progress.CloseProgress();
				}else {
				Intent intent = new Intent(WareInformationActivity.this,MyShopCarActivity.class);
				startActivity(intent);
				
				}
				}
			}
		});
		
		
		//商品介绍
		market_information_describe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(WareInformationActivity.this,WareInformationDetailsActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putInt("wareid", getIntent().getIntE-------------xtra("id", -1));
//				intent.putExtras(bundle);
//				startActivity(intent);/-
//				lists.get(index).getGoods_id();
				
//				System.out.println("================id="+lists.get(0).id);
//				Intent intent= new Intent(WareInformationActivity.this,Webview1.class);
//				intent.putExtra("spjs", lists.get(0).id);
//				startActivity(intent);
			}
		});
		
		//加入购物车
		btn_add_shop_cart.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try{
//					if (!nickname.equals("")) {
//						if (!user_name.equals("")) {
//							fangshi = 2;
//							CommomConfrim.showSheet(WareInformationActivity.this, new onDeleteSelect() {
//								@Override
//								public void onClick(String resID) {
//									// TODO Auto-generated method stub
//			  					  }
//							     }, article_id);
//							progress.CloseProgress();
//						} else {
//						Intent intent = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
//						startActivity(intent);
//						progress.CloseProgress();
//						}
//					}else {
					if (!user_name.equals("")) {
						fangshi = 2;
						taocan_type = true;//判断商品套餐价格
						spec_text_list = 1;//销售套餐判断为1
						jdh_type = "";
						CommomConfrim.showSheet(WareInformationActivity.this, new onDeleteSelect() {
							@Override
							public void onClick(String resID) {
								// TODO Auto-generated method stub
		  					  }
						     }, lists.get(0).id);
						progress.CloseProgress();
					} else {
						Intent intent = new Intent(WareInformationActivity.this,UserLoginActivity.class);
						startActivity(intent);
						progress.CloseProgress();
					}
//					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		//选择:颜色/分类/套餐
		market_information_sep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//				initPopupWindow(rads, 0);
//				showPopupWindow(btn_add_shop_cart);
				// TODO Auto-generated method stub
				Intent intent=new Intent(WareInformationActivity.this, SYBActivity.class);
				intent.putExtra("proName", proName);
				intent.putExtra("proFaceImg", proFaceImg);
				intent.putExtra("retailPrice", retailPrice);
				intent.putExtra("spec_ids", spec_ids);
				intent.putExtra("goods_id",goods_id);
				intent.putExtra("article_id",article_id);
				startActivity(intent);
			}
		});
		
		//属性
//		market_information_param.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//			Intent intent=new Intent(WareInformationActivity.this, SYBActivity.class);
//			intent.putExtra("proName", proName);
//			intent.putExtra("proFaceImg", proFaceImg);
//			intent.putExtra("retailPrice", retailPrice);
//			intent.putExtra("spec_ids", spec_ids);//data_shuzu
//			intent.putExtra("goods_id",goods_id);
//			intent.putExtra("article_id",article_id);
//			startActivity(intent);
//			}
//		});
		
		//立刻购买
		order_shop_now.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
//	if (!nickname.equals("")) {
//		if (!user_name.equals("")) {
//			fangshi = 1;//销售套餐购买判断为1
//			spec_text_list = 1;//销售套餐判断为1
//			taocan_type = true;//判断商品套餐价格
//			jdh_type = "";
//			CommomConfrim.showSheet(WareInformationActivity.this, new onDeleteSelect() {
//				@Override
//				public void onClick(String resID) {
//					// TODO Auto-generated method stub
//					  }
//			}, lists.get(0).id);
//			progress.CloseProgress();
//		} else {
//		Intent intent = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
//		startActivity(intent);
//		progress.CloseProgress();
//		}
//	}else {
				if (!user_name.equals("")) {
//				loadgouwuche();//立即购买加入购物车
					fangshi = 1;//销售套餐购买判断为1
					spec_text_list = 1;//销售套餐判断为1
					taocan_type = true;//判断商品套餐价格
					jdh_type = "";
					CommomConfrim.showSheet(WareInformationActivity.this, new onDeleteSelect() {
						@Override
						public void onClick(String resID) {
							// TODO Auto-generated method stub
	  					  }
					}, lists.get(0).id);
					progress.CloseProgress();
				} else {
					Intent intent = new Intent(WareInformationActivity.this,UserLoginActivity.class);
					startActivity(intent);
					progress.CloseProgress();
				}
//	}
			}
		});
		

	}


	


	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	private String processParam(String temp)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(temp, "UTF-8");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		//商品详情
		case R.id.bt_cart_all:
			System.out.println("1================");
			ll_shiyishicai1.setVisibility(View.VISIBLE);
			ll_shiyishicai2.setVisibility(View.GONE);
			bt_cart_all.setTextColor(Color.RED);
			bt_cart_low.setTextColor(Color.GRAY);
//			show_cart_all.setTextColor(Color.RED);
//			show_cart_low.setTextColor(Color.GRAY);
			show_cart_all.setBackgroundColor(getResources().getColor(R.color.hongse));
			show_cart_low.setBackgroundColor(getResources().getColor(R.color.all_c1c1c1));
			spjs = 0;
//			shangpingjieshan();
			break;
			//规格参数
        case R.id.bt_cart_low:
        	try {
        	System.out.println("len================"+len);
        	ll_shiyishicai1.setVisibility(View.GONE);
			ll_shiyishicai2.setVisibility(View.VISIBLE);
			bt_cart_all.setTextColor(Color.GRAY);
			bt_cart_low.setTextColor(Color.RED);
			show_cart_all.setTextColor(Color.GRAY);
			show_cart_low.setTextColor(Color.RED);
			show_cart_all.setBackgroundColor(getResources().getColor(R.color.all_c1c1c1));
			show_cart_low.setBackgroundColor(getResources().getColor(R.color.hongse));
			if (len > 0) {
				GoodsGgcsListAdapter ggcsadapter = new GoodsGgcsListAdapter(list_ggcs, getApplicationContext());
			    new_list.setAdapter(ggcsadapter);
			    setListViewHeightBasedOnChildren(new_list);  
			}else {
//				loadguigecanshu(); 
//				Toast.makeText(WareInformationActivity.this, "无规格参数", 200).show();
			}
			
        	} catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
			}
			break;
		case R.id.btn_collect://收藏
			
			progress.CreateProgress();
//			String  id = UserLoginActivity.id;
//			String  user_name = UserLoginActivity.user_name;
			if (!nickname.equals("")) {
				if (!user_name.equals("")) {
					String article_id = getIntent().getStringExtra("id");
					System.out.println("1================"+id);
					System.out.println("2================"+user_name);
					AsyncHttp.get(RealmName.REALM_NAME_LL+ "/user_favorite?article_id="+article_id+"&user_name="+user_name+"" +
							"&user_id="+id+"&tags=", new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								System.out.println("收藏================"+arg1);
								progress.CloseProgress();
								String info = jsonObject.getString("info");
								Toast.makeText(getApplicationContext(), info, 200).show();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					 }, getApplicationContext());
				} else {
				Intent intent = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
				startActivity(intent);
				progress.CloseProgress();
				}
			}else {
            if (user_name.equals("")) {
				Intent intent = new Intent(WareInformationActivity.this,UserLoginActivity.class);
				startActivity(intent);
				progress.CloseProgress();
			}else {
            
			String article_id = getIntent().getStringExtra("id");
			System.out.println("1================"+id);
			System.out.println("2================"+user_name);
				
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/user_favorite?article_id="+article_id+"&user_name="+user_name+"" +
					"&user_id="+id+"&tags=", new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					try {
						JSONObject jsonObject = new JSONObject(arg1);
						System.out.println("收藏================"+arg1);
						progress.CloseProgress();
						String info = jsonObject.getString("info");
						Toast.makeText(getApplicationContext(), info, 200).show();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			 }, getApplicationContext());
            
			}
			}
			break;
		case R.id.btn_dianping://购物车
//			progress.CreateProgress();
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			user_name = spPreferences.getString("user", "");
//			id = spPreferences.getString("user_id", "");
//			if (!nickname.equals("")) {
//				if (!user_name.equals("")) {
//					Intent intent = new Intent(WareInformationActivity.this,DianPingActivity.class);
//					intent.putExtra("article_id", sp_id);
//					startActivity(intent);
//					progress.CloseProgress();
//				} else {
//				Intent intent = new Intent(WareInformationActivity.this, TishiWxBangDingActivity.class);
//				startActivity(intent);
//				progress.CloseProgress();
//				}
//			}else {
			if (!user_name.equals("")) {
				Intent intent = new Intent(WareInformationActivity.this,MyShopCarActivity.class);
				intent.putExtra("article_id", sp_id);
				startActivity(intent);
				progress.CloseProgress();
			} else {
				Intent intent = new Intent(WareInformationActivity.this,UserLoginActivity.class);
				startActivity(intent);
				progress.CloseProgress();
			}
//			}
			break;
		default:
			break;
		}
	}
	
	//商品介绍网页端
	private void shangpingjieshan(){
		webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/goods/conent-"+article_id+".html");//商品介绍
	}
	
	/**
	 * 解析规格列表数据
	 */
	ArrayList data;
	private void loadguigecanshu() {
//		progress.CreateProgress();
		data = new ArrayList();
		list_ll = new ArrayList<GuigeData>();
		list_lb = new ArrayList<GuigeData>();
		String article_id = WareInformationActivity.article_id;
		System.out.println("article_id=========================="+article_id);
//		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_spec_list?" +"channel_name=goods",
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_spec_list?" +"article_id="+article_id+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====规格数据====================="+arg1);
						
						try {
							JSONObject object = new JSONObject(arg1);
							JSONArray jobt = object.getJSONArray("data");
							for (int i = 0; i < jobt.length(); i++) {
							JSONObject obj= jobt.getJSONObject(i);
							md = new GuigeData();
							md.setTitle(obj.getString("title"));
							String title = md.getTitle();
							String cars = obj.getString("child");
							System.out.println("=====1值====================="+title);
							JSONArray jaArray = obj.getJSONArray("child");
							md.setList(new ArrayList<GuigeBean>());
				    		for (int j = 0; j < jaArray.length(); j++) {
				    		JSONObject objc= jaArray.getJSONObject(j);
				    		mb = new GuigeBean();
				    		mb.setTitle(objc.getString("title"));
				    		String zhou = mb.getTitle();
				    		System.out.println("=====2值====================="+zhou);
				    		data.add(zhou);
				    		
				    		md.getList().add(mb);
				    		}
				    		list.add(md);
							}
//							CanshuGuiGeAdapter jysadapter = new CanshuGuiGeAdapter(list,lists_ll,getApplicationContext());
//							new_list.setAdapter(jysadapter);
//							shangpingcsAdapter jysadapter = new shangpingcsAdapter(list,data,getApplicationContext());
//							new_list.setAdapter(jysadapter);
							System.out.println("=====值1=====================");
							WideMarketAdapter adapter = new WideMarketAdapter(list,getApplicationContext(), handler);
							new_list.setAdapter(adapter);
							setListViewHeightBasedOnChildren(new_list);  
							adapter.notifyDataSetChanged();
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
	}
	

	class JavascriptHandler {
		@JavascriptInterface
		public void getContent(String htmlContent) {
		}
	}

	public void onPageFinished(WebView view, String url) {
		view.loadUrl("javascript:window.handler.getContent(document.body.innerHTML);");
	}
	
	LayoutInflater mLayoutInflater;
	PopupWindow mPopupWindow;


	public void setListViewHeightBasedOnChildren(ListView listView) {   
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);   
            // 计算子项View 的宽高   
            listItem.measure(0, 0);    
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);   
    } 
	
	
	/**
	 * 立即购买
	 */
//	private void addNow() {
//		List<UserRegisterData> username = wareDao.findisLogin();
//		Log.v("number", username.size() + "");
//		if (username.size() != 0) {
//			if (style_name.size() >= 2) { // 当商品只有两条属性时
//				if (propits[0] == null || propits[1] == null) {
//					Toast.makeText(getApplicationContext(), "请选择商品的属性", 200)
//							.show();
//				} else {
//					stylename1 = style_name.get(0).toString();
//					stylename2 = style_name.get(1).toString();
//					stylenature1 = propits[0].toString();
//					stylenature2 = propits[1].toString();
//					go();
//				}
//			} else if (style_name.size() == 0) { // 当商品没有属性时
//				stylename1 = "";
//				stylename2 = "";
//				stylenature1 = "";
//				stylenature2 = "";
//				go();
//			} else if (style_name.size() == 1) { // 当商品没有属性时
//				if (propits[0] == null  ) {
//					Toast.makeText(getApplicationContext(), "请选择商品的属性", 200)
//							.show();}
//				else {
//					stylename1 = style_name.get(0).toString();
//					stylename2 = "";
//					stylenature1 = propits[0].toString();
//					stylenature2 = "";
//					go();
//				}
//				
//
//			}
}
