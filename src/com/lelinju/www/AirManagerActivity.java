package com.lelinju.www;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.data.AirDo;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lelinju.www.R;

public class AirManagerActivity extends BaseActivity {
	// mi/FlightTicket.ashx?yth=������&act=GetFlightOrderInfo
	private ListView air_manager;
	private WareDao wareDao;
	private String yth;
	private String[] bankNames;
	private ArrayList<CardItem> banks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.air_manager_layout);
		air_manager = (ListView) findViewById(R.id.air_manager);
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		if (yth != null) {
			RequestParams params = new RequestParams();
			params.put("yth", yth);
			AsyncHttp.post(RealmName.REALM_NAME
					+ "/mi/FlightTicket.ashx?act=GetFlightOrderInfo", params,
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							System.out.println(arg1);
							parse(arg1);
						}
					}, getApplicationContext());

		}
	}

	private void parse(String result) {
		try {
			ArrayList<AirDo> list = new ArrayList<AirDo>();
			JSONObject jsonObject = new JSONObject(result);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			int len = jsonArray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				AirDo airDo = new AirDo();
				airDo.setAirName(object.getString("AirlineName"));
				airDo.setArrTime(object.getString("arrTime"));
				airDo.setDepTime(object.getString("depTime"));
				airDo.setDstCity(object.getString("dstCity"));
				airDo.setFlightNo(object.getString("flightNo"));
				airDo.setOrgCity(object.getString("orgCity"));
				airDo.setPrice(object.getString("TotalPrice"));
				airDo.setTag(object.getString("orderStatusId"));
				airDo.setTime(object.getString("orderTime"));
				airDo.setTrade_no(object.getString("UMPtrade_no"));
				list.add(airDo);
			}

			JSONArray array = jsonObject.getJSONArray("UserSignedBankList");
			int lens = array.length();
			if (lens != 0) {
				banks = new ArrayList<CardItem>();
				bankNames = new String[lens + 1];
				for (int i = 0; i < lens; i++) {
					JSONObject object2 = array.getJSONObject(i);
					CardItem item = new CardItem();
					item.setType(object2.getString("pay_type"));
					item.setBankName(object2.getString("gate_id"));
					item.setLastId(object2.getString("last_four_cardid"));
					item.setId(object2.getString("UserSignedBankID"));
					banks.add(item);
					bankNames[i] = ParseBank.parseBank(item.getBankName(),
							getApplicationContext())
							+ "("
							+ ParseBank.paseName(item.getType())
							+ ")"
							+ item.getLastId();
				}
				CardItem item = new CardItem();
				item.setBankName("-1");
				item.setId("-1");
				item.setLastId("-1");
				item.setType("-1");
				banks.add(item);
				System.out.println("changdu "+len);
				bankNames[len] = "��֧����ʽ";
			}
			Message msg = new Message();
			msg.what = 0;
			msg.obj = list;
			handler.sendMessage(msg);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				ArrayList<AirDo> list = (ArrayList<AirDo>) msg.obj;
				AirManagerItemAdapter itemAdapter = new AirManagerItemAdapter(
						getApplicationContext(), list, handler);
				air_manager.setAdapter(itemAdapter);

				break;
			case 1:
				String trade_no = (String) msg.obj;
				System.out.println(trade_no);
				if (banks != null && banks.size() != 0) {
					// ��ʾ�ǵڶ���֧��
					System.out.println("д�ڶ���֧��");
					// initPopupWindow1();
					// showPopupWindow1(btn_OK);
					Intent intent = new Intent(AirManagerActivity.this,
							PayActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("tag", 1);
					bundle.putSerializable("trade_no", trade_no);
					bundle.putStringArray("bank_names", bankNames);
					bundle.putSerializable("bank_objs", banks);
					intent.putExtras(bundle);
					startActivity(intent);
				} else {
					// ��ʾ�״�֧��
					Intent intent = new Intent(AirManagerActivity.this,
							PayActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("tag", 0);
					bundle.putSerializable("trade_no", trade_no);
					intent.putExtras(bundle);
					startActivity(intent);
					// initPopupWindow();
					// showPopupWindow(btn_OK);
				}
				break;
			default:
				break;
			}
		};
	};
}