package com.hengyushop.demo.my;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyAssetsAdapter;
import com.android.hengyu.pub.XinShouGongyeLieAdapter;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.XinshouGyActivity;
import com.hengyushop.entity.MyAssetsBean;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.widget.NewDataToast;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;
import com.lelinju.www.UserLoginActivity;

/**
 * 
 * 我的资产
 * 
 * @author Administrator
 * 
 */
public class MyAssetsActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4;
	private Button fanhui, btn_chongzhi;
	private LinearLayout index_item0, index_item1, index_item2, index_item3;
	private EditText et_chongzhi;
	private SharedPreferences spPreferences;
	private TextView tv_ticket, tv_shop_ticket, tv_jifen_ticket,
			tv_djjifen_ticket;
	private ArrayList<MyAssetsBean> list;
	private ListView listView;
	private PullToRefreshView refresh;
	MyAssetsAdapter adapter;
	String user_name, user_id;
	int len;
	String fund_id = "0";
	private int RUN_METHOD = -1;
	public static String type = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_assets);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		Initialize();
		
		list = new ArrayList<MyAssetsBean>();
		adapter = new MyAssetsAdapter(list, MyAssetsActivity.this,imageLoader);
		listView.setAdapter(adapter);
//		loadguanggao();

		fund_id = "1";
		load_list(true, fund_id);

	}

	/**
	 * 控件初始化
	 */
	private void Initialize() {

		try {
			refresh = (PullToRefreshView) findViewById(R.id.refresh);
			refresh.setOnHeaderRefreshListener(listHeadListener);
			refresh.setOnFooterRefreshListener(listFootListener);
			listView = (ListView) findViewById(R.id.new_list);
			tv_ticket = (TextView) findViewById(R.id.tv_ticket);
			tv_shop_ticket = (TextView) findViewById(R.id.tv_shop_ticket);
			tv_jifen_ticket = (TextView) findViewById(R.id.tv_jifen_ticket);
			tv_djjifen_ticket = (TextView) findViewById(R.id.tv_djjifen_ticket);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			index_item2 = (LinearLayout) findViewById(R.id.index_item2);
			index_item3 = (LinearLayout) findViewById(R.id.index_item3);
			cursor1 = (ImageView) findViewById(R.id.cursor1);
			cursor2 = (ImageView) findViewById(R.id.cursor2);
			cursor3 = (ImageView) findViewById(R.id.cursor3);
			cursor4 = (ImageView) findViewById(R.id.cursor4);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			index_item2.setOnClickListener(this);
			index_item3.setOnClickListener(this);
			
			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.index_item0:
			// index_item0.setVisibility(View.VISIBLE);
			// index_item1.setVisibility(View.INVISIBLE);
			// index_item2.setVisibility(View.INVISIBLE);
			// index_item3.setVisibility(View.INVISIBLE);
			cursor1.setVisibility(View.VISIBLE);
			cursor2.setVisibility(View.INVISIBLE);
			cursor3.setVisibility(View.INVISIBLE);
			cursor4.setVisibility(View.INVISIBLE);
			fund_id = "1";
			load_list(true, fund_id);
			break;
		case R.id.index_item1:
			// index_item0.setVisibility(View.INVISIBLE);
			// index_item1.setVisibility(View.VISIBLE);
			// index_item2.setVisibility(View.INVISIBLE);
			// index_item3.setVisibility(View.INVISIBLE);
			cursor1.setVisibility(View.INVISIBLE);
			cursor2.setVisibility(View.VISIBLE);
			cursor3.setVisibility(View.INVISIBLE);
			cursor4.setVisibility(View.INVISIBLE);
			fund_id = "4";
			load_list(true, fund_id);
			break;
		case R.id.index_item2:
			// index_item0.setVisibility(View.INVISIBLE);
			// index_item1.setVisibility(View.INVISIBLE);
			// index_item2.setVisibility(View.VISIBLE);
			// index_item3.setVisibility(View.INVISIBLE);
			cursor1.setVisibility(View.INVISIBLE);
			cursor2.setVisibility(View.INVISIBLE);
			cursor3.setVisibility(View.VISIBLE);
			cursor4.setVisibility(View.INVISIBLE);
			fund_id = "2";
			load_list(true, fund_id);
			break;
		case R.id.index_item3:
			// index_item0.setVisibility(View.INVISIBLE);
			// index_item1.setVisibility(View.INVISIBLE);
			// index_item2.setVisibility(View.INVISIBLE);
			// index_item3.setVisibility(View.VISIBLE);
			cursor1.setVisibility(View.INVISIBLE);
			cursor2.setVisibility(View.INVISIBLE);
			cursor3.setVisibility(View.INVISIBLE);
			cursor4.setVisibility(View.VISIBLE);
			fund_id = "3";
			load_list(true, fund_id);
			break;

		default:
			break;
		}
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
//						if (RUN_METHOD == 0) {
//							System.out.println("RUN_METHOD1========="+ RUN_METHOD);
//							load_list2(false);
//						} else {
							System.out.println("RUN_METHOD2========="+ RUN_METHOD);
							load_list(false, fund_id);
//						}
						refresh.onFooterRefreshComplete();

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}, 1000);
		}
	};
	
//	private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {
//
//		@Override
//		public void onFooterRefresh(PullToRefreshView view) {
//			// TODO Auto-generated method stub
//			refresh.postDelayed(new Runnable() {
//
//				@Override
//				public void run() {
//					if(RUN_METHOD==0){
//						load_P(false);
//					}else {
////						if(INDX!=-1)
//						load(INDX, false);
//					}
//					refresh.onFooterRefreshComplete();
//				}
//			}, 1000);
//		}
//	};

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				adapter.putData(list);
//				list = (ArrayList<MyAssetsBean>) msg.obj;
//				adapter = new MyAssetsAdapter(list, MyAssetsActivity.this,imageLoader);
//				listView.setAdapter(adapter);
//				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 第1个列表数据解析
	 */
//	private int RUN_METHOD = -1;
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;

	private void load_list(boolean flag, String fund_id) {
		type = fund_id;
		RUN_METHOD = 1;
		if (flag) {
			// 计数和容器清零
			System.out.println("=====================flag==" + flag);
			CURRENT_NUM = 1;
			list = new ArrayList<MyAssetsBean>();
		}
		String user_name = spPreferences.getString("user", "");
		String user_id = spPreferences.getString("user_id", "");
//		+ user_id + "&user_name="+125+"&fund_id=13502883181
		System.out.println("=====================fund_id--" + fund_id);
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_payrecord_list?user_id="+ user_id + "&user_name="+user_name+"&fund_id=" + fund_id
				+ "&page_size=" + VIEW_NUM + "&page_index=" + CURRENT_NUM + "",
				new AsyncHttpResponseHandler() {
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
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject json = jsonArray.getJSONObject(i);
									MyAssetsBean data = new MyAssetsBean();
									data.fund = json.getString("fund");
									data.income = json.getString("income");
									data.user_name = json.getString("user_name");
									data.add_time = json.getString("add_time");
									data.expense = json.getString("expense");
									data.remark = json.getString("remark");
									data.balance = json.getString("balance");
									list.add(data);
								}
							} else {
								Toast.makeText(MyAssetsActivity.this, info, 200).show();
							}
							System.out.println("=====================二级值12");
							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);
							
							if (len != 0) {
								CURRENT_NUM = CURRENT_NUM + 1;
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
	}


	/**
	 * 值
	 */
	private void loadguanggao() {
		try {
			String user_name = spPreferences.getString("user", "");
			String strUrl = RealmName.REALM_NAME_LL
					+ "/get_user_model?username=" + user_name + "";
			System.out.println("======11=============" + strUrl);
			AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======输出用户资料=============" + arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.user_name = obj.getString("user_name");
							data.user_code = obj.getString("user_code");
							data.agency_id = obj.getInt("agency_id");
							data.amount = obj.getString("amount");
							data.pension = obj.getString("pension");
							data.packet = obj.getString("packet");
							data.point = obj.getString("point");// point
							data.group_id = obj.getString("group_id");
							data.login_sign = obj.getString("login_sign");
							data.agency_name = obj.getString("agency_name");
							data.group_name = obj.getString("group_name");

							tv_ticket.setText(data.amount + "元");
							tv_shop_ticket.setText(data.pension + "元");
							tv_jifen_ticket.setText(data.packet  + "元");
							tv_djjifen_ticket.setText(data.point + "分");//packet point

						} else {

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
					NewDataToast.makeText(MyAssetsActivity.this, "连接超时", false,0).show();
				}
			}, getApplicationContext());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
