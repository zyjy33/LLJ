package com.lelinju.www;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.PhoneAndEmailProving;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.CityDao;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.DBManager;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.CityData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class XiuGairessActivity extends BaseActivity {

	private EditText et_username, et_userphone, et_address;
	private Button btn_hold;
	private String name, phone, address;
	private Spinner sp_sheng, sp_shi, sp_xian;
	private ArrayList<String> al_sheng, al_shi, al_xian;
	private String sheng, shi, xian, yth, key;
	private int sheng_code, shi_code, area_code, index;
	private WareDao wareDao;
	private UserRegisterData registerData;
	private DialogProgress progress;
	private DBManager dbManager;
	private CityDao cityDao;
	@SuppressWarnings("rawtypes")
	private ArrayAdapter aa_sheng, aa_shi, aa_area;
	private String strUrl;
	private MyPopupWindowMenu popupWindowMenu;
	private SharedPreferences spPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {

			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.edit_user_address);
			popupWindowMenu = new MyPopupWindowMenu(this);
			wareDao = new WareDao(getApplicationContext());
			progress = new DialogProgress(XiuGairessActivity.this);
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			innidade();

			// Intent intent = this.getIntent();
			// Bundle bundle = intent.getExtras();
			// index = (Integer) bundle.get("index");
			// registerData = wareDao.findIsLoginHengyuCode();
			// yth = registerData.getHengyuCode().toString();
			// key = registerData.getUserkey().toString();
			// dbManager = new DBManager(this);
			// dbManager.openDatabase();
			// dbManager.closeDatabase();

			cityDao = new CityDao(XiuGairessActivity.this);
			ArrayList<CityData> shengs = cityDao.findSheng();
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < shengs.size(); i++) {
				list.add(shengs.get(i).name);
			}
			Log.v("data2", shengs.get(0).name + "     " + shengs.size());

			Message message = new Message();
			message.what = 2;
			message.obj = list;
			handler.sendMessage(message);

			spinnerData();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				et_username.setText("");
				et_userphone.setText("");
				et_address.setText("");
				String strmsg = (String) msg.obj;
				progress.CloseProgress();
				Toast.makeText(getApplicationContext(), strmsg, 200).show();
				AppManager.getAppManager().finishActivity();
				/*
				 * if (index == 1) { Intent intent = new
				 * Intent(AddUserAddressActivity.this,
				 * AddressManagerActivity.class); startActivity(intent); } else
				 * if (index == 0) { Intent intent = new
				 * Intent(AddUserAddressActivity.this,
				 * OrderConfrimActivity.class); startActivity(intent); }
				 */
				finish();
				break;
			case 1:
				et_username.setText("");
				et_userphone.setText("");
				et_address.setText("");
				String strmsgll = (String) msg.obj;
				progress.CloseProgress();
				// Toast.makeText(getApplicationContext(), "添加失败,请重新添加。",
				// 200).show();
				Toast.makeText(getApplicationContext(), strmsgll, 200).show();
				break;
			case 2:
				al_sheng = (ArrayList<String>) msg.obj;
				aa_sheng = new ArrayAdapter(XiuGairessActivity.this,
						android.R.layout.simple_spinner_item, al_sheng);
				aa_sheng.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp_sheng.setAdapter(aa_sheng);
				break;
			case 3:
				al_shi = (ArrayList<String>) msg.obj;
				aa_shi = new ArrayAdapter(XiuGairessActivity.this,
						android.R.layout.simple_spinner_item, al_shi);
				aa_shi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp_shi.setAdapter(aa_shi);
				break;
			case 4:
				al_xian = (ArrayList<String>) msg.obj;
				aa_area = new ArrayAdapter(XiuGairessActivity.this,
						android.R.layout.simple_spinner_item, al_xian);
				aa_area.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				sp_xian.setAdapter(aa_area);
				break;
			default:
				break;
			}

		};
	};

	
	

	private void innidade() {
		et_username = (EditText) findViewById(R.id.et_user_name);
		et_userphone = (EditText) findViewById(R.id.et_user_phone);
		et_address = (EditText) findViewById(R.id.et_user_address);
		sp_sheng = (Spinner) findViewById(R.id.sp_sheng);
		sp_shi = (Spinner) findViewById(R.id.sp_shi);
		sp_xian = (Spinner) findViewById(R.id.sp_xian);
		btn_hold = (Button) findViewById(R.id.btn_holdr);

		btn_hold.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = et_username.getText().toString();
				phone = et_userphone.getText().toString();
				address = et_address.getText().toString();

				if (name.equals("")) {
					et_username.setError("请填写用户名");
				} else if (phone.equals("")) {
					et_userphone.setError("请填写联系电话");
				} /*
				 * else if (!PhoneAndEmailProving.isMobileNO(phone)) {
				 * et_userphone.setError("号码输入有误"); }
				 */else if (address.equals("")) {
					et_address.setError("请填写详细地址");
				} else {
					progress.CreateProgress();
					String id = spPreferences.getString("user_id", "");
					String user_name = spPreferences.getString("user", "");

					String pingjiedizhi = sheng + "、" + shi + "、" + xian;
					strUrl = RealmName.REALM_NAME_LL
							+ "/add_user_shopping_address?user_id=" + id
							+ "&user_name=" + user_name + ""
							+ "&user_accept_name=" + name + "&user_province=&user_city=&user_area="
							+ pingjiedizhi + "&user_street=&user_address=" + address
							+ "&user_mobile=" + phone + "&user_telphone="
							+ "&user_email=&user_post_code=&is_default=0";
					
					System.out.println("11================" + sheng);
					System.out.println("11================" + shi);
					System.out.println("11================" + xian);
					System.out.println("strUrl================" + strUrl);
					AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
						public void onSuccess(int arg0, String arg1) {
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("0================" + arg1);
								String status = object.getString("status");
								// String info = object.getString("info");
								if (status.equals("y")) {
									System.out.println("1================");
									String msg = object.getString("info");
									Log.v("data1", msg);
									Message message = new Message();
									message.what = 0;
									message.obj = msg;
									handler.sendMessage(message);
								} else {
									System.out.println("2================");
									String msg = object.getString("info");
									Message message2 = new Message();
									message2.what = 1;
									message2.obj = msg;
									handler.sendMessage(message2);
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};

						public void onFailure(Throwable arg0, String arg1) {
							System.out.println("3================" + arg0);
							System.out.println("3================" + arg1);

						};

					}, null);

				}
			}
		});
	}

	// 字符串上传服务器 乱码 问题的解决方法
	private String processParam(String temp)
			throws UnsupportedEncodingException {
		// return URLEncoder.encode(temp, "UTF-8");
		return temp;
	}

	private void spinnerData() {
		sp_sheng.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				sheng = al_sheng.get(arg2);
				cityDao = new CityDao(XiuGairessActivity.this);
				CityData cityData = cityDao.findShengCode(sheng);
				sheng_code = cityData.getCode();
				Log.v("data2", cityData.getCode() + "");
				cityDao = new CityDao(XiuGairessActivity.this);
				ArrayList<CityData> shis = cityDao.findCity(sheng_code);
				ArrayList<String> list2 = new ArrayList<String>();
				for (int i = 0; i < shis.size(); i++) {
					list2.add(shis.get(i).name);
				}
				Message message = new Message();
				message.what = 3;
				message.obj = list2;
				handler.sendMessage(message);

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

		sp_shi.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				shi = al_shi.get(arg2);
				cityDao = new CityDao(XiuGairessActivity.this);
				CityData cityData = cityDao.findCityCode(shi);
				shi_code = cityData.getCode();
				cityDao = new CityDao(XiuGairessActivity.this);
				ArrayList<CityData> areas = cityDao.findArea(shi_code);
				ArrayList<String> list3 = new ArrayList<String>();
				for (int i = 0; i < areas.size(); i++) {
					list3.add(areas.get(i).name);
				}
				Message message = new Message();
				message.what = 4;
				message.obj = list3;
				handler.sendMessage(message);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		sp_xian.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				xian = al_xian.get(arg2);
				cityDao = new CityDao(XiuGairessActivity.this);
				CityData cityData = cityDao.findAreaCode(xian);
				area_code = cityData.getCode();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppManager.getAppManager().finishActivity();
		}
		return super.onKeyDown(keyCode, event);

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
	// new Thread() {
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// super.run();
	// try {
	// // strUrl = RealmName.REALM_NAME+
	// "/mi/getdata.ashx";//add_user_shopping_address
	// String pingjiedizhi = sheng_code+"、"+shi_code+"、"+area_code;
	// strUrl = RealmName.REALM_NAME_LL+
	// "/add_user_shopping_address?user_id=19&user_name=13714758507" +
	// "&user_accept_name="+name+"&user_area="+pingjiedizhi+"&user_address="+address+"&user_mobile="+phone+"&user_telphone="
	// +
	// "&user_email=&user_post_code=&is_default=0";
	//
	// Map<String, String> params = new HashMap<String, String>();
	// // params.put("act", "addreceiver");
	// // params.put("yth", yth);
	// // params.put("key", key);
	// // params.put("name", processParam(name));
	// // params.put("mobile", phone);
	// // params.put("provinceCode", sheng_code+"");
	// // params.put("cityCode", shi_code+"");
	// // params.put("areaCode", area_code+"");
	// // params.put("addressDetail", processParam(address));
	// // params.put("postcode", "");
	// // params.put("email", "");
	// // params.put("default", "1");
	// params.put("user_id", "19");
	// params.put("user_name", "13714758507");
	// params.put("user_accept_name", name);
	// params.put("user_area", pingjiedizhi);
	// params.put("user_address", address);
	// params.put("user_mobile", phone);
	// params.put("user_telphone", "");
	// params.put("user_email", "");
	// params.put("user_post_code", "");
	// params.put("is_default", "0");
	// System.out.println("01================"+name);
	// System.out.println("02================"+pingjiedizhi);//"广东深圳南山"
	// System.out.println("03================"+address);
	// System.out.println("04================"+phone);
	//
	// System.out.println("0================"+params);
	// // AsyncHttp.post_1(strUrl, params,new AsyncHttpResponseHandler(){
	// AsyncHttp.get(strUrl,new AsyncHttpResponseHandler(){
	// public void onSuccess(int arg0, String arg1) {
	// try {
	// JSONObject object = new JSONObject(arg1 );
	// System.out.println("0================"+arg1);
	// int status = object.getInt("status");
	// if (status == 1) {
	// System.out.println("1================");
	// String msg = object.getString("msg");
	// Log.v("data1", msg);
	// Message message = new Message();
	// message.what = 0;
	// message.obj = msg;
	// handler.sendMessage(message);
	// } else {
	// System.out.println("2================");
	// Message message2 = new Message();
	// message2.what = 1;
	// handler.sendMessage(message2);
	// }
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// };
	// public void onFailure(Throwable arg0, String arg1) {
	// System.out.println("3================"+arg0);
	// System.out.println("3================"+arg1);
	//
	// };
	//
	// }, null );
	//
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }.start();

}
