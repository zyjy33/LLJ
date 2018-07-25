package com.hengyushop.demo.home;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.GoodsListAdapter;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.airplane.adapter.JuduihuanAdaper;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.JuTuanGouData;
import com.lelinju.www.WareInformationActivity;
import com.lglottery.www.widget.MyPosterView;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class JuDuiHuanActivity extends BaseActivity{
//	private List<EnterpriseData> list = null;
	private ArrayList<JuTuanGouData> list = null;
	private MyGridView myGridView,myGridView2;
	private MyAdapter adapter;
	private int INDX = 0;
	private GoodsListAdapter myadapter;
	private JuduihuanAdaper jdhadapter;
	private ListView new_list,listview;
	private PullToRefreshView refresh;
	private DialogProgress progress;
	private int id = 0;
	private MyPosterView advPager = null;
	private ImageView ling_tip;
	JuTuanGouData data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_juhuihuan_tetle);//activity_juhuihuan_tetle
		//在此调用下面方法，才能捕获到线程中的异常
//      Thread.setDefaultUncaughtExceptionHandler(this);
		progress = new DialogProgress(this);
		Initialize();
		loadguanggao();
//		loadCate();
		load_list(INDX,true);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
//		imageLoader.clearMemoryCache(); 
//		imageLoader.clearDiscCache();
		super.onDestroy();
	}
//	public void uncaughtException(Thread arg0, Throwable arg1) {
//		// TODO Auto-generated method stub
//		 //在此处理异常， arg1即为捕获到的异常
//        Log.i("AAA", "uncaughtException   " + arg1);
//	}
	private void Initialize() {
		myGridView = (MyGridView) findViewById(R.id.gridView);
//		advPager = (MyPosterView) findViewById(R.id.adv_pagerll);
		ling_tip = (ImageView) findViewById(R.id.ling_tip);
//		refresh = (PullToRefreshView) findViewById(R.id.refresh);
//		refresh.setOnHeaderRefreshListener(listHeadListener);
//		refresh.setOnFooterRefreshListener(listFootListener);
		
//		new_list = (ListView) findViewById(R.id.new_list);
//		listview = (ListView)findViewById(R.id.listview);
		
		Button iv_fanhui = (Button) findViewById(R.id.fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		myGridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//				System.out.println("====================="+lists.get(arg2).id);
				Intent intent = new Intent(JuDuiHuanActivity.this,WareInformationActivity.class);
				intent.putExtra("jdh_type", "1");
				intent.putExtra("jdh_id", list.get(arg2).id);
				startActivity(intent);
            }
        });
	}
	
	/**
	 * 第一个列表数据解析
	 */
	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	private void load_list(final int INDX,boolean flag) {
		RUN_METHOD = 1;
		list = new ArrayList<JuTuanGouData>();
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 0;
			list = new ArrayList<JuTuanGouData>();
		}
		System.out.println("=====================001--"+INDX);
			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_page_size_list_2017?channel_name=point&category_id="+INDX+"" +
							"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
							 new AsyncHttpResponseHandler(){
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("=====================二级值111"+arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									String status = jsonObject.getString("status");
									String info = jsonObject.getString("info");
									if (status.equals("y")) {
										JSONArray jsonArray = jsonObject.getJSONArray("data");
//										JSONObject obj= object.getJSONObject("data");
										for (int i = 0; i < jsonArray.length(); i++) {
											JSONObject obj = jsonArray.getJSONObject(i);
											data = new JuTuanGouData();
											data.setId(obj.getString("id"));
											data.setTitle(obj.getString("title"));
											data.setImg_url(obj.getString("img_url"));
//											data.setAdd_time(obj.getString("add_time"));
//											data.setStart_time(obj.getString("start_time"));
//											data.setUpdate_time(obj.getString("update_time"));
//											data.setCategory_id(obj.getString("category_id"));
//											data.setEnd_time(obj.getString("end_time"));
											
											JSONObject jsot = obj.getJSONObject("default_spec_item");
											data.setGoods_id(jsot.getString("goods_id"));
											data.setArticle_id(jsot.getString("article_id"));
											data.setMarket_price(jsot.getString("market_price"));
											data.setCashing_point(jsot.getString("cashing_point"));
											data.setExchange_point(jsot.getString("exchange_point"));
											data.setExchange_price(jsot.getString("exchange_price"));
//											JSONObject jsoct = jsot.getJSONObject("default_activity_price");
//											data.setPeople(jsoct.getString("people"));
//											data.setPrice(jsoct.getString("price"));
											list.add(data);
									        }
									}else {
										Toast.makeText(JuDuiHuanActivity.this, info, 200).show();
									}
//									if (status.equals("y")) {
//										progress.CreateProgress();
//									JSONArray jsonArray = jsonObject.getJSONArray("data");
//									 int len = jsonArray.length();
//									for(int i=0;i<jsonArray.length();i++){
//										JSONObject object = jsonArray.getJSONObject(i);
//										GoodsListData spList = new GoodsListData();
//										spList.id = object.getString("id");
//										spList.title = object.getString("title");
//										spList.img_url = object.getString("img_url");
//										spList.point = object.getString("point");
//										spList.price = object.getString("price");
//										spList.goods_price = object.getString("goods_price");//goods_price
//										lists.add(spList);
//									}
//									}else {
//									}
									handler.sendEmptyMessage(0);
									progress.CloseProgress();
//									if(len!=0){
//										CURRENT_NUM =CURRENT_NUM+VIEW_NUM;
//									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}, null);
	}
	
	private void loadguanggao() {
		try {
			
		//广告滚动	
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_adbanner_list?advert_id=12",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							JSONArray array = object.getJSONArray("data");
							int len = array.length();
							ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
							for (int i = 0; i < len; i++) {
								AdvertDao1 ada = new AdvertDao1();
								JSONObject json = array.getJSONObject(i);
								ada.setId(json.getString("id"));
								ada.setAd_url(json.getString("ad_url"));
								String ad_url = ada.getAd_url();
//								ada.setAd_url(RealmName.REALM_NAME_HTTP + json.getString("ad_url"));
							    ImageLoader imageLoader=ImageLoader.getInstance();
							    imageLoader.displayImage(RealmName.REALM_NAME_HTTP + ad_url, ling_tip);
								images.add(ada);
							}
//							Message msg = new Message();
//							msg.obj = images;
//							msg.what = 0;
//							childHandler.sendMessage(msg);
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
//	ArrayList<AdvertDao1> tempss;
//	private Handler childHandler = new Handler() {
//		@SuppressWarnings("unchecked")
//		public void dispatchMessage(Message msg) {
//			switch (msg.what) {
//			case 0:
//				tempss = (ArrayList<AdvertDao1>) msg.obj;
//				
//				ArrayList<String> urls = new ArrayList<String>();
//				for(int i=0;i<tempss.size();i++){
//					urls.add(tempss.get(i).getAd_url());
//				}
//				advPager.setData(urls, new MyPosterOnClick() {
//					@Override
//					public void onMyclick(int position) {
//						// TODO Auto-generated method stub
//						Message msg = new Message();
//						msg.what = 13;
//						msg.obj = tempss.get(position).getId();
//						handler.sendMessage(msg);
//					}
//				}, true, imageLoader, true);
//				break;
//			default:
//				break;
//			}
//		};
//	};
	
	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
//				myadapter.putData(lists);
				System.out.println("=====================2这里"+list.size());
				
				jdhadapter = new JuduihuanAdaper(list, JuDuiHuanActivity.this,imageLoader);
				myGridView.setAdapter(jdhadapter);
				
				break;
			case 1:
//				adapter = new MyAdapter(getApplicationContext(), list);
//				myGridView.setAdapter(adapter);
				
//				myGridView.setOnItemClickListener(new OnItemClickListener() {
//
//		            @Override
//		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
////		            	 INDX = list.get(arg2).id;
////		            	 load_list(INDX, true);
////		            	 adapter.setSeclection(arg2);
////		            	 adapter.notifyDataSetChanged();
//		            	 
////						System.out.println("====================="+lists.get(arg2).id);
//						Intent intent = new Intent(JuDuiHuanActivity.this,WareInformationActivity.class);
//						intent.putExtra("jdh_id", lists.get(arg2).id);
//						startActivity(intent);
//		            }
//		        });
				break;
			case 13:
				try {
			    String id = (String) msg.obj;
				System.out.println("1111============="+id);
				Intent intent13 = new Intent(JuDuiHuanActivity.this, Webview1.class);
				intent13.putExtra("gg_id", id);
				startActivity(intent13);
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
						System.out.println("=======4=="+INDX);
						load_list(INDX, false);
					refresh.onFooterRefreshComplete();
				}
			}, 1000);
		}
	};
	
	//商家列表
//	private void loadCate(){
//		progress.CreateProgress();
//		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_category_list?" +
//                "channel_name=activity&parent_id=0", new AsyncHttpResponseHandler(){
//			@Override
//			public void onSuccess(int arg0, String arg1) {
//				// TODO Auto-generated method stub
//				super.onSuccess(arg0, arg1);
//				parse(arg1);
//			}
//		}, null);
//    }
	
//	ArrayList<ArrayList<WareData>> lists = null;
//	public void parse(String st) {
//		try {
//			System.out.println("值=========="+st);
//			list = new ArrayList<EnterpriseData>();
//			JSONObject jsonObject = new JSONObject(st);
//			JSONArray jsonArray = jsonObject.getJSONArray("data");
//			System.out.println("jsonArray"+jsonArray.length());
//			for (int i = 0; i < jsonArray.length(); i++) {
//				EnterpriseData data = new EnterpriseData();
//				JSONObject object = jsonArray.getJSONObject(i);
//				data.id = object.getInt("id");
//				data.title = object.getString("title");
////				data.icon_url = object.getString("icon_url");
//				Log.v("data1", data.id + "");
//				list.add(data);
//			}
////			inter();
//			handler.sendEmptyMessage(1);
//			progress.CloseProgress();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
	
	
	
	
	
	public class MyAdapter extends BaseAdapter {

		private Context mContext;
		private List<EnterpriseData> List;
		private LayoutInflater mInflater;
		private int clickTemp = 0;

		public MyAdapter(Context context, List<EnterpriseData> list){
			try {
			this.List = list;
			this.mContext = context;
			mInflater = LayoutInflater.from(context);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}

		
		@Override
		public int getCount() {
			if (list.size()<1) {

				return 0;
			}else{

				return list.size();
			}
		}
		
		public void setSeclection(int position) {
			clickTemp = position;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			try {
				
			if (convertView == null) {
				holder =  new ViewHolder();
				convertView = mInflater.inflate(R.layout.gridview_item0, null);
				holder.text = (TextView) convertView.findViewById(R.id.btn_aaa1);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.text.setText(list.get(position).title);
			if (clickTemp == position) {
//				convertView.setBackgroundResource(R.drawable.julegou_xuankuang);//julegou_xuankuang 
				holder.text.setTextColor(Color.RED);
			} else {
//				convertView.setBackgroundColor(Color.TRANSPARENT);
//				convertView.setBackgroundResource(R.drawable.zangfutiaoli);//julegou_xuankuang 
				holder.text.setTextColor(Color.GRAY);
		    }
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return convertView;
		}
		

		class ViewHolder{
			ImageView img;
			TextView text;
			RadioButton radioButton;
		}
	}

}
