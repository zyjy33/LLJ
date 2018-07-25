package com.lelinju.www;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
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
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.CollectWareData;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.json.HttpClientUtil;
import com.hengyushop.json.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

public class CollectWareActivity extends BaseActivity {

	private ArrayList<CollectWareData> list;
	private WareDao wareDao;
	private String yth, key, strUrl;
	private DialogProgress progress;
	private ListView listView;
	private int ID;
	private MyPopupWindowMenu popupWindowMenu;
	private SharedPreferences spPreferences;
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				list = (ArrayList<CollectWareData>) msg.obj;
				MyCollectWareAdapter adapter = new MyCollectWareAdapter(list,CollectWareActivity.this, imageLoader);
				listView.setAdapter(adapter);
				progress.CloseProgress();
				break;

			default:
				break;
			}
		};
	};
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		adapter.notifyDataSetChanged();
//		loadWeather();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collect_ware);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		progress = new DialogProgress(CollectWareActivity.this);

		listView = (ListView) findViewById(R.id.list_ware_collect);
		listView.setCacheColorHint(0);

		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();

//		strUrl = RealmName.REALM_NAME+ "/mi/getdata.ashx";
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("act", "GetUserCollectInfo");
//		params.put("key", key);
//		params.put("yth", yth);
		
		progress.CreateProgress();
		
		loadWeather();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String wareId = list.get(arg2).article_id;
				Intent intent = new Intent(CollectWareActivity.this,WareInformationActivity.class);
				intent.putExtra("id", wareId);
				startActivity(intent);
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
	private void loadWeather() {
//		String id = UserLoginActivity.id;
		String id = spPreferences.getString("user_id", "");
		strUrl = RealmName.REALM_NAME_LL+ "/get_user_favorite_list?user_id="+id+"&page_size=10&page_index=1" +
				"&strwhere=&orderby=";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("user_id", id);
		params.put("page_size", "10");
		params.put("page_index", "1");
		params.put("strwhere", "");
		params.put("orderby", "");
		
		System.out.println("�ղ�"+strUrl);
		System.out.println("�ղ�1"+params);
//		AsyncHttp.post_1(strUrl, params, new AsyncHttpResponseHandler(){
		AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("===================="+arg1);
				super.onSuccess(arg0, arg1);
				parse(arg1);
				
			}
		}, null);
	}

	private void parse(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			ArrayList<CollectWareData> list = new ArrayList<CollectWareData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				CollectWareData data = new CollectWareData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.id = object.getInt("id");
				data.article_id = object.getString("article_id");
				data.title = object.getString("title");
				data.img_url = object.getString("img_url");
				data.article_id = object.getString("article_id");
				data.price = object.getString("price");
				data.summary = object.getString("summary");
				data.add_time = object.getString("add_time");
//				data.retailPrice = object.getString("retailPrice");
//				data.proFaceImg = object.getString("proFaceImg");
//				data.collectTotal = object.getString("collectTotal");
				list.add(data);
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = list;
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}


	protected void dialog(final int ID) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("ȷ��ɾ�������Ʒ��");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

//				final String str = RealmName.REALM_NAME
//						+ "/mi/receiveOrderInfo.ashx?act=DeleteOneUserCollectInfo&yth="
//						+ yth + "&ProductItemId=" + ID + "&key=" + key;
				String user_id = spPreferences.getString("user_id", "");
				System.out.println("1111===================="+user_id);
				final String str = RealmName.REALM_NAME_LL
						+ "/user_favorite_delete?user_id="+user_id+"&id=" + ID + "";
				Log.v("data1", "ɾ��:" + strUrl);
				progress.CreateProgress();
//				AsyncHttp.get(str, new AsyncHttpResponseHandler(){
//				}, getApplicationContext());
				System.out.println("1111===================="+str);
				AsyncHttp.get(str, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						System.out.println("===================="+arg1);
						progress.CloseProgress();
						super.onSuccess(arg0, arg1);
						loadWeather();
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

		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

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
			popupWindowMenu.dismiss(); // �Ի�����ʧ
			popupWindowMenu.currentState = 1; // ���״̬������ʧ
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // ���״̬����ʾ��
		}
		return false; // true--��ʾϵͳ�Դ��˵���false--����ʾ��
	}

}
