package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.hengyu.pub.MySpListAdapter;
import com.android.hengyu.pub.MyjuTouTiaoAdapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.CollectWareData;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;
import com.lelinju.www.WareInformationActivity;

public class SouSuoSpActivity extends BaseActivity {

	private ArrayList<SpListData> lists;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private DialogProgress progress;
	private ListView listView;
	private int ID;
	private MyPopupWindowMenu popupWindowMenu;
	private PullToRefreshView refresh;
	MySpListAdapter myadapter;
	int len;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xinshougongye);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(SouSuoSpActivity.this);
//		progress.CreateProgress();
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setText("乐邻生活");
		initdata();
		
		lists = new ArrayList<SpListData>();
		myadapter = new MySpListAdapter(lists,getApplicationContext(), imageLoader);
		listView.setAdapter(myadapter);
		
		load_list(true);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				try {
					
				System.out.println("=================1="+lists.size());
				Intent intent= new Intent(SouSuoSpActivity.this,WareInformationActivity.class);
				intent.putExtra("id", lists.get(arg2).id);
				startActivity(intent);
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});

	}
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				System.out.println("=================5="+lists.size());
				myadapter.putData(lists);
				progress.CloseProgress();
				
//				listView.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//							long arg3) {
//						// TODO Auto-generated method stub
//						try {
//							
//						System.out.println("=================1="+lists.size());
//						Intent intent= new Intent(SouSuoSpActivity.this,Webview1.class);
//						intent.putExtra("list_xsgy", lists.get(arg2).id);
//						startActivity(intent);
//						
//						} catch (Exception e) {
//							// TODO: handle exception
//							e.printStackTrace();
//						}
//					}
//				});
				break;

			default:
				break;
			}
		};
	};
	private void initdata() {
		refresh = (PullToRefreshView) findViewById(R.id.refresh);
		refresh.setOnHeaderRefreshListener(listHeadListener);
		refresh.setOnFooterRefreshListener(listFootListener);
		listView = (ListView) findViewById(R.id.new_list);
		
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
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
						load_list(false);
					refresh.onFooterRefreshComplete();
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};
	
	/**
	 * 第1个列表数据解析
	 */
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	private void load_list(boolean flag) {
		try {
			
		if(flag){
			//计数和容器清零
			CURRENT_NUM = 1;
			lists = new ArrayList<SpListData>();
		}
		String strwhere_zhi = getIntent().getStringExtra("strwhere_zhi");//
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_page_search_list?channel_name=goods&category_id=0&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"" +
				"&keyword="+strwhere_zhi+"&orderby=id%20desc",
				new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("=====================二级值1"+arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									String status = jsonObject.getString("status");
									String info = jsonObject.getString("info");
									if (status.equals("y")) {
									JSONArray jsonArray = jsonObject.getJSONArray("data");
									 len = jsonArray.length();
									for(int i=0;i<jsonArray.length();i++){
										JSONObject object = jsonArray.getJSONObject(i);
										SpListData spList = new SpListData();
										spList.id = object.getString("id");
										spList.img_url = object.getString("img_url");
										spList.title = object.getString("title");
//										spList.market_price = object.getString("market_price");
//										spList.sell_price = object.getString("sell_price");
										JSONObject jsot = object.getJSONObject("default_spec_item");
										spList.market_price = jsot.getString("market_price");
										spList.sell_price = jsot.getString("sell_price");
										lists.add(spList);
									}
									}else {
										progress.CloseProgress();
										Toast.makeText(SouSuoSpActivity.this, info, 200).show();
									}
									progress.CloseProgress();
									handler.sendEmptyMessage(0);
									if(len!=0){
										CURRENT_NUM =CURRENT_NUM+1;
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}




}
