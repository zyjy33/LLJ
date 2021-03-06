package com.lelinju.www;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.json.HttpClientUtil;
import com.hengyushop.json.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.widget.TextView;

public class WareInformationServerActivity extends BaseActivity {

	private int wareid;
	private TextView tv_server;
	private String strUrl;
	private String ware_server;
	private MyPopupWindowMenu popupWindowMenu;

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				String details = (String) msg.obj;
				tv_server.setText(details);
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ware_infromation_server);
		popupWindowMenu = new MyPopupWindowMenu(this);
		tv_server = (TextView) findViewById(R.id.tv_server);

		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras();
		wareid = (Integer) bundle.get("wareid");

		strUrl = RealmName.REALM_NAME
				+ "/mi/getdata.ashx"
				+ wareid;
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "OneProductItemInfo");
		params.put("yth", "test");
		params.put("key", "test");
		params.put("ProductItemId", wareid+"");
		AsyncHttp.post_1(strUrl, params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		});
	}

	private void parse(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				ware_server = object.getString("afterSaleService");

				Message message = new Message();
				message.what = 0;
				message.obj = ware_server;
				handler.sendMessage(message);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

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
