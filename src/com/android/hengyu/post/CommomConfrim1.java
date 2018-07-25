package com.android.hengyu.post;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.OrderListAdapter;
import com.android.hengyu.pub.WideMarketAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.GuigeListAdapter;
import com.hengyushop.airplane.adapter.GuigeListlAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;
import com.lelinju.www.WareInformationActivity;

public class CommomConfrim1 extends Activity{
	public static ListView listview_01;
	public GuigeListlAdapter adapter1;
	public static TextView market_information_sep_price,market_information_pop_buy,market_information_pop_shopcart;
	public static String retailPrice;
	public static String id;
	public static String id1;
	public static String id2;
	public static String title;
	public static String title1;
	public static String title2;
	public static int gk_id = 0;
	public static int gk_id1 = 0;
	public static int gk_id2 = 0;
	public static LinearLayout no_data_no;
	public static GuigeData md; 
	public static GuigeBean mb;
	public static DialogProgress progress;
	public static TextView market_information_seps_num;
	public static SharedPreferences spPreferences;
	public static String user_name,user_id;
	public static Context context;
	public static Activity activity;
	static String proName;
	
	public static ArrayList<GuigeData> list = new ArrayList<GuigeData>();
	public static ListView new_list;
	public static  GuigeListAdapter adapter;
	
	public interface onDeleteSelect {
		void onClick(String resID);
	}
	
	/**
	 * 弹出框
	 */
	private CommomConfrim1() {
		final Dialog dlg; 
	}

	public static Dialog showSheet(final Context context,
			final com.android.hengyu.post.CommomConfrim.onDeleteSelect oonDeleteSelectnDeleteSelect,final String id3) {
			
		 final Dialog dlg = new Dialog(context, R.style.delete_pop_style);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = (LinearLayout) inflater.inflate(R.layout.market_information_sep_pop_ll, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);
		listview_01 = (ListView) layout.findViewById(R.id.listview_01);
		progress = new DialogProgress(context);
		spPreferences = context.getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		
//		loadWeatherll();
		inter(layout);
//		loadguigecanshu();
		try {
		System.out.println("111111==============="+id3);
		
//		if (list.size() > 0) {
//			
//		}else {
		String article_id = WareInformationActivity.article_id;
		System.out.println("article_id=========================="+article_id);
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
							GuigeData md = new GuigeData();
							md.setTitle(obj.getString("title"));
							JSONArray jaArray = obj.getJSONArray("child");
							md.setList(new ArrayList<GuigeBean>());
				    		for (int j = 0; j < jaArray.length(); j++) {
				    		JSONObject objc= jaArray.getJSONObject(j);
				    		GuigeBean mb = new GuigeBean();
				    		mb.setTitle(objc.getString("title"));
				    		mb.setId(objc.getString("id"));
				    		String zhou = mb.getTitle();
				    		System.out.println("=====值====================="+zhou);
				    		md.getList().add(mb);
				    		}
				    		list.add(md);
							}
//							shangpingcsAdapter jysadapter = new shangpingcsAdapter(list,data,getApplicationContext());
//							new_list.setAdapter(jysadapter);
							System.out.println("=====值1=====================");
							OrderListAdapter adapter = new OrderListAdapter(list,context, handler);
							listview_01.setAdapter(adapter);
							setListViewHeightBasedOnChildren(listview_01);  
							adapter.notifyDataSetChanged();
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
//		}
		
		//取消
		market_information_pop_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
//				activity.finish();
				dlg.dismiss();
				
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		
		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		
		dlg.setContentView(layout);
		dlg.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dlg;
		
	}
	
	public static void inter(View layout) {
		  no_data_no = (LinearLayout) layout.findViewById(R.id.no_data_no);
		ImageView market_information_sep_ico = (ImageView) layout.findViewById(R.id.market_information_sep_ico);
		market_information_sep_price = (TextView) layout.findViewById(R.id.market_information_sep_price);
		TextView market_information_sep_name = (TextView) layout.findViewById(R.id.market_information_sep_name);
		market_information_pop_shopcart = (Button) layout.findViewById(R.id.market_information_pop_shopcart);
		market_information_pop_buy = (Button) layout.findViewById(R.id.market_information_pop_buy);
		// 关于数量
		TextView market_information_seps_add = (TextView) layout.findViewById(R.id.market_information_seps_add);
		TextView market_information_seps_del = (TextView) layout.findViewById(R.id.market_information_seps_del);
		market_information_seps_num = (TextView) layout.findViewById(R.id.market_information_seps_num);
		
		market_information_seps_num.setText("1");

		layout.findViewById(R.id.market_information_pop_sure);
		
		ImageLoader imageLoader=ImageLoader.getInstance();
		imageLoader.displayImage(RealmName.REALM_NAME_HTTP + WareInformationActivity.proFaceImg,market_information_sep_ico);
		
		market_information_sep_name.setText(WareInformationActivity.proName);
		market_information_sep_price.setText("￥" + WareInformationActivity.retailPrice);
		
		try {
		market_information_seps_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int num = Integer.parseInt(market_information_seps_num.getText().toString());
				market_information_seps_num.setText(String.valueOf(num + 1));
			}
		});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		try {
		//产品减少
		market_information_seps_del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int num = Integer.parseInt(market_information_seps_num
						.getText().toString());
				if (num != 1) {
					market_information_seps_num.setText(String.valueOf(num - 1));
				} else {
					Toast.makeText(context, "不能再减了", 200).show();
				}
			}
		});

	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		
		//加入购物车
		market_information_pop_shopcart.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						try {
						
//						if (id != null) {
//							if (gk_id == 0) {
//								Toast.makeText(context, "请选择商品的属性", 200).show();
//							}
//						}else if (id1 != null){
//							if (gk_id1 == 0) {
//								Toast.makeText(context, "请选择商品的属性", 200).show();
//							}
//						}else if (id2 != null){
//							if (gk_id2 == 0) {
//								Toast.makeText(context, "请选择商品的属性", 200).show();
//							}
//						}else {
//						
//								oadWeather_gouwuche();
//						}
//						oadWeather_gouwuche();
						} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					    }
					}
				});
		
	}
	
	/**
	 * 加入购物车数据
	 */
	private static void oadWeather_gouwuche() {
//		progress.CreateProgress();
//		String id = UserLoginActivity.id;
//		String user_name = UserLoginActivity.user_name;
		System.out.println("1================"+user_id);
		System.out.println("2================"+user_name);
		String article_id = WareInformationActivity.article_id;
		String goods_id = WareInformationActivity.goods_id;
		System.out.println("1================"+article_id);
		System.out.println("2================"+goods_id);
//		String goods_id = getIntent().getStringExtra("goods_id");
//		String article_id = getIntent().getStringExtra("article_id");
		
		String geshu = market_information_seps_num.getText().toString().trim();
		System.out.println("结果呢1=============="+geshu);
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_cart?user_id="+user_id+"&user_name="+user_name+
				"&article_id="+article_id+"&goods_id="+goods_id+"&quantity="+geshu+"",new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							System.out.println("1================"+arg1);
//							progress.CloseProgress();
							String info = jsonObject.getString("info");
							Toast.makeText(context, info, 200).show();
							activity.finish();
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

				}, null);
		        
	}

	private static Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				break;
			case 1:
				break;
			case 5:
				try {
					
				ArrayList data_shuzu = WareInformationActivity.data_shuzu;
				ArrayList data_mrz = WareInformationActivity.data_mrz;
				ArrayList data_monney = WareInformationActivity.data_monney;
				System.out.println("值是1============="+data_mrz.size());
//				adapter = new GuiGeListviewAdapter(context,list,data,data1,data2,data_id,data_id1,data_id2,data_shuzu,data_mrz,data_monney);
//				adapter = new GuigeListAdapter(SYBActivity.this,list,data,data1,data2);
				listview_01.setAdapter(adapter);
				setListViewHeightBasedOnChildren(listview_01);   
//				adapter.notifyDataSetChanged();
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
	
	/**
	 * 解析规格列表数据
	 */
	private static void loadguigecanshu() {
//		progress.CreateProgress();
		String article_id = WareInformationActivity.article_id;
		System.out.println("article_id=========================="+article_id);
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
							JSONArray jaArray = obj.getJSONArray("child");
							md.setList(new ArrayList<GuigeBean>());
				    		for (int j = 0; j < jaArray.length(); j++) {
				    		JSONObject objc= jaArray.getJSONObject(j);
				    		mb = new GuigeBean();
				    		mb.setTitle(objc.getString("title"));
				    		String zhou = mb.getTitle();
				    		System.out.println("=====值====================="+zhou);
				    		md.getList().add(mb);
				    		}
				    		list.add(md);
							}
//							CanshuGuiGeAdapter jysadapter = new CanshuGuiGeAdapter(list,lists_ll,getApplicationContext());
//							new_list.setAdapter(jysadapter);
//							shangpingcsAdapter jysadapter = new shangpingcsAdapter(list,data,getApplicationContext());
//							new_list.setAdapter(jysadapter);
							System.out.println("=====值1=====================");
							WideMarketAdapter adapter = new WideMarketAdapter(list,activity, handler);
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
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {   
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
}
