package com.hengyushop.demo.home;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;

import com.android.hengyu.pub.GoodsListAdapter;
import com.android.hengyu.pub.JegGoodsListAdapter;
import com.android.hengyu.pub.JuyouFanglistAdapter;
import com.android.hengyu.pub.MyAdapter;
import com.android.hengyu.pub.MySpListAdapter;
import com.android.hengyu.pub.SpListDataAdapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.ui.MyGridView;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.GoodsListBean;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.WareData;
import com.hengyushop.entity.XsgyListData;
import com.hengyushop.entity.shangpingListData;
import com.lglottery.www.common.Config;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;
import com.lelinju.www.WareInformationActivity;

public class JuYouFangActivity extends BaseActivity{
	private List<EnterpriseData> list = null;
//	private ArrayList<shangpingListData> list_ll = null;
	private MyGridView myGridView;
	private MyAdapter adapter;
	private int INDX = -1;
	private int quanbu_id = 0;
	private JuyouFanglistAdapter myadapter;
	private ListView new_list;
	private PullToRefreshView refresh;
	private DialogProgress progress;
	int useridString;
	private int id = 0;
	int len;
	GoodsListData data;
	GoodsListBean bean;
	private ArrayList<GoodsListData> lists;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.juyoufang_home);
		 //在此调用下面方法，才能捕获到线程中的异常
//        Thread.setDefaultUncaughtExceptionHandler(this);
		progress = new DialogProgress(this);
		Initialize();
		lists = new ArrayList<GoodsListData>();
		myadapter = new JuyouFanglistAdapter(lists,JuYouFangActivity.this, imageLoader);
		new_list.setAdapter(myadapter);
		loadCate();
	}
	
//	public void uncaughtException(Thread arg0, Throwable arg1) {
//		// TODO Auto-generated method stub
//		 //在此处理异常， arg1即为捕获到的异常
//        Log.i("AAA", "uncaughtException   " + arg1);
//	}
	
	private void Initialize() {
		myGridView = (MyGridView) findViewById(R.id.mGv);
		new_list = (ListView) findViewById(R.id.new_list);
		refresh = (PullToRefreshView) findViewById(R.id.refresh);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		refresh.setOnFooterRefreshListener(listFootListener);
		
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
	}
	//商家列表
		private void loadCate(){
			progress.CreateProgress();
			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_trade_list?" +
	                "channel_name=trade&parent_id=273", new AsyncHttpResponseHandler(){
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onSuccess(arg0, arg1);
					parse(arg1);
				}
			}, null);
	    }
		
		public void parse(String st) {
			try {
				System.out.println("类别列表=========="+st);
				list = new ArrayList<EnterpriseData>();
				JSONObject jsonObject = new JSONObject(st);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				System.out.println("jsonArray"+jsonArray.length());
				int len = jsonArray.length();
				list.add(0, null);
				for (int i = 0; i < len; i++) {
					EnterpriseData data = new EnterpriseData();
					JSONObject object = jsonArray.getJSONObject(i);
//					data.id = object.getInt("id");
					data.setId(object.getInt("id"));
					data.title = object.getString("title");
					data.icon_url = object.getString("icon_url");
					Log.v("data1", data.id + "");
					list.add(data);
				}
				inter();
				handler.sendEmptyMessage(1);
				
				progress.CloseProgress();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				myadapter.putData(lists);
				System.out.println("=====================这里"+lists.size());
				
	            new_list.setOnItemClickListener(new OnItemClickListener() {
	                
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
//						String id = lists.get(arg2).getId();
//						System.out.println("====================="+id);
//						Intent intent = new Intent(JuYouFangActivity.this,WareInformationActivity.class);
//						intent.putExtra("id", id);
//						startActivity(intent);
					}
				});
	            
//				JegGoodsListAdapter myadapter = new JegGoodsListAdapter(lists,list_ll, getApplicationContext(), imageLoader);
//				new_list.setAdapter(myadapter);
//				myadapter = new GoodsListAdapter(lists,list_ll, getApplicationContext(), imageLoader);
//				new_list.setAdapter(myadapter);
//				SpListDataAdapter sAdapter = new SpListDataAdapter(list_ll, getApplicationContext(), imageLoader);
//				listview.setAdapter(sAdapter);
//				myadapter.notifyDataSetChanged();
				break;
			case 1:
				System.out.println("个数是多少===================="+list.size());
				adapter = new MyAdapter(getApplicationContext(),list);
				myGridView.setAdapter(adapter);
				

				if (list.size()>0) {
					try{
//					int id = list.get(1).getId();
//					int id = 273;
					load_list(id, true);
					
					 } catch (Exception e) {
							// TODO: handle exception
						 e.printStackTrace();
						}
				}
				
				myGridView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		            	try {	 	
//		            		 INDX =  list.get(arg2).getId();
		            		 INDX =  arg2;
			            	 System.out.println("=====第二层de数据====================="+INDX);
			            	 load_list(INDX, true);
		            	 adapter.setSeclection(arg2);
		            	 adapter.notifyDataSetChanged();
		            } catch (Exception e) {
						// TODO: handle exception
					 e.printStackTrace();
					}
		            	 
		            }
		        });
				
				break;
			case 2:
			    break;
				

			default:
				break;
			}
		};
	};
	
	
	/**
	 * 第1个列表数据解析
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	private void load_list(final int INDX,boolean flag) {
		RUN_METHOD = 1;
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 1;
			lists = new ArrayList<GoodsListData>();
		}
		System.out.println("=====================001--"+INDX);
			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_user_commpany?trade_id="+INDX+"" +
							"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
							 new AsyncHttpResponseHandler(){
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("商家列表=====================二级值1"+arg1);
								try {
									JSONObject object = new JSONObject(arg1);
									String status = object.getString("status");
									String info = object.getString("info");
									if (status.equals("y")) {
										JSONArray jsonArray = object.getJSONArray("data");
										len = jsonArray.length();
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject object1 = jsonArray.getJSONObject(i);
											data = new GoodsListData();
											data.setId(object1.getString("id"));
											data.setName(object1.getString("name"));
											data.setImg_url(object1.getString("img_url"));
											String article = object1.getString("article");
											data.setList(new ArrayList<GoodsListBean>());
											JSONArray ja = new JSONArray(article);
									for (int k = 0; k < ja.length(); k++) {
										bean = new GoodsListBean();
										JSONObject obt = ja.getJSONObject(k);
										bean.setId(obt.getString("id"));
										bean.setTitle(obt.getString("title"));
										bean.setImg_url(obt.getString("img_url"));
										bean.setSell_price(obt.getString("sell_price"));
										bean.setMarket_price(obt.getString("market_price"));
										data.getList().add(bean);
									}
									lists.add(data);
									}
									}else {
										progress.CloseProgress();
//										Toast.makeText(JuYouFangActivity.this, info, 200).show();
									}
									System.out.println("=====lists2====================="+lists.size());
									if(len!=0){
										CURRENT_NUM =CURRENT_NUM+1;
									}
									progress.CloseProgress();
//									handler.sendEmptyMessage(0);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						}, null);
	}
	
	
	/**
	 * 上拉列表刷新加载
	 */
	private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {

		@Override
		public void onHeaderRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					refresh.onHeaderRefreshComplete();
				}
			}, 1000);
		}
	};
	
	/**
	 * 下拉列表刷新加载
	 */
	private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {

		@Override
		public void onFooterRefresh(PullToRefreshView view) {
			// TODO Auto-generated method stub
			refresh.postDelayed(new Runnable() {

				@Override
				public void run() {
					try {
						load_list(INDX, false);
					refresh.onFooterRefreshComplete();
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};
	
	
	private void inter(){
		
		int size = list.size();//数据总长度

		//获得屏幕宽度
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int windowWidth = metrics.widthPixels;
		int itemWidth = windowWidth/5;

		//获得屏幕宽度也可以这样写
		//int itemWidth = getWindowManager().getDefaultDisplay().getWidth() / 5;//屏幕显示默认数量

		int gridViewWidth = (int)(size * itemWidth);//linearLayout的总宽度
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth,LinearLayout.LayoutParams.MATCH_PARENT);
		myGridView.setLayoutParams(params);//设置GridView布局参数
		myGridView.setNumColumns(size);//动态设置GridView列数
	}
	

}
