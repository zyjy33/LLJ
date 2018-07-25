package com.hengyushop.demo.my;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.android.hengyu.pub.MyCollectWareAdapter;
import com.android.hengyu.pub.XiuGaiDizhiAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.CollectWareData;
import com.hengyushop.entity.UserAddressData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.json.HttpClientUtil;
import com.hengyushop.json.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

public class DizhiListActivity extends BaseActivity {

//	private ArrayList<CollectWareData> list;
	private ArrayList<UserAddressData> list;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private DialogProgress progress;
	private ListView listView;
	private int ID;
	private MyPopupWindowMenu popupWindowMenu;
	private SharedPreferences spPreferences;
	private XiuGaiDizhiAdapter adapter;
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				progress.CloseProgress();
				list = (ArrayList<UserAddressData>) msg.obj;
				adapter = new XiuGaiDizhiAdapter(list,DizhiListActivity.this, imageLoader);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		};
	};
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
////		adapter.notifyDataSetChanged();
//		loadWeather();
//	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect_ware);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(DizhiListActivity.this);

		listView = (ListView) findViewById(R.id.list_ware_collect);
		listView.setCacheColorHint(0);

//		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
//		yth = registerData.getHengyuCode();
//		key = registerData.getUserkey();

//		strUrl = RealmName.REALM_NAME+ "/mi/getdata.ashx";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("act", "GetUserCollectInfo");
//		params.put("key", key);
//		params.put("yth", yth);
		
		
		progress.CreateProgress();
		
		
		getuseraddress(); 
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
//				String wareId = Integer.toString(list.get(arg2).id);
//				Intent intent = new Intent(DizhiListActivity.this,WareInformationActivity.class);
//				intent.putExtra("id", wareId);
//				startActivity(intent);
			}
		});
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				ID = list.get(arg2).id;
				dialog(ID);
				return false;
			}
		});
		
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
	 * 输出用户默认收货地址
	 */
	private void getuseraddress() {
		progress.CreateProgress();
		String user_name = spPreferences.getString("user", "");
		System.out.println("结果呢1=============="+user_name);
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_user_shopping_address?user_name="+user_name+""
				,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							System.out.println("1================"+arg1);
							list = new ArrayList<UserAddressData>();
							String status = jsonObject.getString("status");
							if (status.equals("y")) {
								JSONArray jsonArray = jsonObject.getJSONArray("data");
								for(int i=0;i<jsonArray.length();i++){
								JSONObject jsot = jsonArray.getJSONObject(i);		
								UserAddressData data = new UserAddressData();
								data.user_accept_name = jsot.getString("user_accept_name");
								data.user_area = jsot.getString("area");
								data.user_mobile = jsot.getString("user_mobile");
								data.user_address = jsot.getString("user_address");
								data.id = jsot.getInt("id");
								list.add(data);
								}
								progress.CloseProgress();
//								Message message = new Message();
//								message.what = 0;
//								message.obj = list;
//								handler.sendMessage(message);
								handler.sendEmptyMessage(0);
							}else {
								progress.CloseProgress();
								
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							progress.CloseProgress();
							e.printStackTrace();
						}
					}

				}, getApplicationContext());
		        
	}

	protected void dialog(final int ID) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认删除这个商品吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String user_id = spPreferences.getString("user_id", "");
				System.out.println("1111===================="+user_id);
				final String str = RealmName.REALM_NAME_LL
						+ "/delete_user_shopping_address?user_id="+user_id+"&id=" + ID + "";
				Log.v("data1", "删除:" + strUrl);
				progress.CreateProgress();
				AsyncHttp.get(str, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						System.out.println("===================="+arg1);
						progress.CloseProgress();
						super.onSuccess(arg0, arg1);

						adapter.notifyDataSetChanged();
						getuseraddress();
						
					}
					@Override
					public void onFailure(Throwable arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onFailure(arg0, arg1);
						System.out.println("1===================="+arg0);
						System.out.println("2===================="+arg1);
					}
				}, null);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // 对话框消失
			popupWindowMenu.currentState = 1; // 标记状态，已消失
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}

}
