package com.lelinju.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.movie.adapter.OneBuyAdapter;
import com.hengyushop.movie.adapter.OneBuyBean;
import com.hengyushop.movie.adapter.OneNewBean;
import com.lglottery.www.widget.MyPosterOnClick;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.PagerScrollView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;
import com.zijunlin.Zxing.Demo.view.NoScrollGridView;


public class OneBuyActivity extends BaseActivity{
	private OneBuyAdapter buyAdapter;
	private NoScrollGridView one_views;
	private LinearLayout index_item4,index_item6,index_item7,index_new;
	private PagerScrollView pa_scroll;
	private int widthPixels;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_buy_activity);
		pa_scroll  = (PagerScrollView) findViewById(R.id.pa_scroll);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		widthPixels = dm.widthPixels;// 宽度height = dm.heightPixels ;
		init();
	};
	private LinearLayout one_buy_viewpager;
	private MyPosterView posterView;
	private ArrayList<OneBuyBean> lists;
	private void init(){
		index_item6 = (LinearLayout) findViewById(R.id.index_item6);
		
		index_new = (LinearLayout) findViewById(R.id.index_new);
		index_item4 = (LinearLayout) findViewById(R.id.index_item4);
		index_item7 = (LinearLayout) findViewById(R.id.index_item7);
		one_views = (NoScrollGridView) findViewById(R.id.one_views);
		lists = new ArrayList<OneBuyBean>();
		buyAdapter = new OneBuyAdapter(OneBuyActivity.this, lists, imageLoader);
		one_views.setAdapter(buyAdapter);
		one_buy_viewpager = (LinearLayout)  findViewById(R.id.one_buy_viewpager);
		double viewPager = (double) 182 / 583;
		double viewPager_height = viewPager * widthPixels;
		one_buy_viewpager.setLayoutParams(new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				(int) viewPager_height));
		index_new.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, widthPixels/3));
		
		
		Map<String, String> params = new HashMap<String, String>();
		//mi/getdata.ashx?act=GetLuckYiYuanGameAllNewResult&yth=test或为空&topNum=1
		params.put("act", "GetLuckYiYuanGameAllNewResult");
		params.put("yth", "");
		params.put("topNum", "3");
		
		AsyncHttp.post_1(RealmName.REALM_NAME+"/mi/getdata.ashx", params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					if(status.equals("1")){
						ArrayList<OneNewBean> lists = new ArrayList<OneNewBean>();
						JSONArray jsonArray = object.getJSONArray("items");
						int len = jsonArray.length();
						for(int i=0;i<len;i++){
							JSONObject obj  = jsonArray.getJSONObject(i);
							OneNewBean bean = new OneNewBean();
							bean.setCode(obj.getString("HengYuCode"));
							bean.setCount(obj.getString("AllJuGouCount"));
							bean.setId(obj.getString("ProductItemId"));
							bean.setImg(obj.getString("proFaceImg"));
							bean.setNumber(obj.getString("ActualLuckNumber"));
							bean.setProname(obj.getString("proName"));
							bean.setTime(obj.getString("AnnouncedTime"));
							bean.setUsername(obj.getString("username"));
							bean.setLuckDrawBatchOrderNumber(obj.getString("LuckDrawBatchOrderNumber"));
							lists.add(bean);
						}
						Message msg = new Message();
						msg.what = 1;
						msg.obj = lists;
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
		// TODO Auto-generated method stub
		posterView = new MyPosterView(OneBuyActivity.this, null);
		one_buy_viewpager.addView(posterView);
		onload();
		one_views.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(OneBuyActivity.this,OneBuyInformation.class);
				intent.putExtra("price", lists.get(arg2).getPrice());
				intent.putExtra("id", lists.get(arg2).getId());
				intent.putExtra("tag", "6");
				startActivity(intent);
			}
		});
		index_item6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OneBuyActivity.this,One_PersonActivity.class);
				startActivity(intent);
			}
		});
		index_item4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OneBuyActivity.this,OneNewActivity.class);
				startActivity(intent);
			}
		});
		index_item7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OneBuyActivity.this,OneBuyListActivity.class);
				startActivity(intent);
			}
		});
	}
	/*
	 *获得数据
	 */
	private void onload(){
		///mi/getdata.ashx?act=LuckYiYuanGameItems&yth=admin
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "LuckYiYuanGameItems");
		params.put("yth","admin");
		AsyncHttp.post_1(RealmName.REALM_NAME+"/mi/getdata.ashx", params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					  ArrayList<OneBuyBean> images = new ArrayList<OneBuyBean>();
					JSONObject jsonObject = new JSONObject(arg1);
					JSONArray jsonArray = jsonObject.getJSONArray("items");
					int len = jsonArray.length();
					for(int i=0;i<len;i++){
						OneBuyBean bean = new OneBuyBean();
						JSONObject object = jsonArray.getJSONObject(i);
						bean.setId(	object.getString("ProductItemId"));
						bean.setImg(object.getString("proFaceImg"));
						bean.setJoinNum(object.getString("HasJoinedNum"));
						bean.setMarket(object.getString("marketPrice"));
						bean.setName(object.getString("proName"));
//						bean.setNum(num)
						bean.setNum(object.getString("NeedGameUserNum"));
						bean.setPrice(object.getString("NeedCostPrice"));
						bean.setRecommend(object.getString("IsRecommend"));
						bean.setTime(object.getString("BeginTime"));
						if(object.getString("IsShowIndex").equals("1")){
							bean.setLongImg(object.getString("proLongWidthImg"));
							images.add(bean);
						}
						lists.add(bean);
					}
					Message msg = new Message();
					msg.what = 2;
					msg.obj= images;
					handler.sendMessage(msg);
					handler.sendEmptyMessage(0);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	  int poindex = 0;
	private Handler handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				buyAdapter.putData(lists);
				one_views.post(new Runnable() {
					@Override
					public void run() {
						pa_scroll.fullScroll(ScrollView.FOCUS_UP);
					}
				});
				
				break;
			case 1:
				@SuppressWarnings("unchecked")
				final
				ArrayList<OneNewBean> lists = (ArrayList<OneNewBean>) msg.obj;
				index_new.removeAllViews();
			
				for(int i=0;i<lists.size();i++){
					LinearLayout layout = (LinearLayout) LinearLayout.inflate(getApplicationContext(), R.layout.one_buy_new_item, null);
					layout.setLayoutParams(new LayoutParams(widthPixels/3, LinearLayout.LayoutParams.MATCH_PARENT));
					ImageView new_img = (ImageView) layout.findViewById(R.id.new_img);
					TextView new_text = (TextView) layout.findViewById(R.id.new_text);
					TextView new_text1 = (TextView) layout.findViewById(R.id.new_text1);
					imageLoader.displayImage(RealmName.REALM_NAME+"/admin/"+lists.get(i).getImg(), new_img);
					new_text.setText(lists.get(i).getUsername());
					new_text1.setText(lists.get(i).getProname());
					index_new.addView(layout);
					poindex = i;
					layout.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(OneBuyActivity.this,One_JiexiaoActivity.class);
							intent.putExtra("id",  lists.get(poindex).getId());
							intent.putExtra("LuckDrawBatchOrderNumber", lists.get(poindex).getLuckDrawBatchOrderNumber());
							intent.putExtra("idex", lists.get(poindex).getLuckDrawBatchOrderNumber());
							startActivity(intent);
						}
					});
				}
				break;
			case 2:
				  final ArrayList<OneBuyBean> images  = (ArrayList<OneBuyBean>) msg.obj;
				  ArrayList<String> imgs = new ArrayList<String>();
				  
				  for(int i=0;i<images.size();i++){
					  imgs.add(RealmName.REALM_NAME+"/admin/"+images.get(i).getLongImg());
				  }
//				posterView.setData(imgs, new MyPosterOnClick() {
//					
//					@Override
//					public void onMyclick(int position) {
//						Intent intent = new Intent(OneBuyActivity.this,OneBuyInformation.class);
//						intent.putExtra("price", images.get(position).getPrice());
//						intent.putExtra("id", images.get(position).getId());
//						intent.putExtra("tag", "6");
//						startActivity(intent);
//					}
//				}, false, imageLoader, false);
				break;
			default:
				break;
			}
		};
	};
	public void onPause() {
		super.onPause();
		if (posterView != null) {
			posterView.puseExecutorService();
		}
	};
}
