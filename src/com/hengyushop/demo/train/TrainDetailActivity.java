package com.hengyushop.demo.train;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.ArrayWheelAdapterll;
import com.android.hengyu.ui.WheelViewll;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.hengyushop.json.HttpUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lelinju.www.PayActivity;
import com.lelinju.www.R;
import com.lelinju.www.RechargeMobileActivity;

public class TrainDetailActivity extends BaseActivity {
	private TextView v1, v2, v3, v4, v5, v6;
	private LinearLayout select_seat;
	private ArrayList<TrainPriceDo> priceDos;
	private String seats[] = null;
	private Map<String, String> map, valueMap;
	private Button add_person, submit_person;
	private ListView persons;
	private TrainPriceDo paramTemp = null;
	private ArrayList<TrainPersonItem> list = null;
	private ChePiaoData data;
	private String[] bankNames;
	private ArrayList<CardItem> banks;
	private String trade_no;
	private TraindetailPersonItemAdapter adapter;
	private int k;

	private void initMap() {
		map = new HashMap<String, String>();
		map.put("P", "�ص���");
		map.put("M", "һ����");
		map.put("O", "������");
		map.put("A1", "Ӳ��");
		map.put("A3", "Ӳ��");
		map.put("A4", "����");
		map.put("A9", "������");
		map.put("WZ", "����");

		valueMap = new HashMap<String, String>();
		valueMap.put("�ص���", "P");
		valueMap.put("һ����", "M");
		valueMap.put("������", "O");
		valueMap.put("Ӳ��", "A1");
		valueMap.put("Ӳ��", "A3");
		valueMap.put("����", "A4");
		valueMap.put("������", "A9");
		valueMap.put("����", "WZ");
	}

	private void init() {
		v1 = (TextView) findViewById(R.id.v1);
		v2 = (TextView) findViewById(R.id.v2);
		v3 = (TextView) findViewById(R.id.v3);
		v4 = (TextView) findViewById(R.id.v4);
		v5 = (TextView) findViewById(R.id.v5);
		v6 = (TextView) findViewById(R.id.v6);
		select_seat = (LinearLayout) findViewById(R.id.select_seat);
		add_person = (Button) findViewById(R.id.add_person);
		persons = (ListView) findViewById(R.id.persons);
		submit_person = (Button) findViewById(R.id.submit_person);
		submit_person.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/*
				 * http://wxpalm.com.cn/mi/TrainHandler.ashx?
				 * yth=������&act=InsertTrainTicketOrder
				 * &StationTrainCode=����&StartStationTelecode=ʼ��վ����
				 * &EndStationTelecode=�յ�վ����&FromStationTelecode=����վ����
				 * &ToStationTelecode=����վ����&StartTime=����ʱ��
				 * &ArriveTime=����ʱ��&DayDifference=1&LiShi=�г���ʱʱ��
				 * &TrainUserContactID=1,1,1&SeatCode=��λ���ʹ���,��λ���ʹ���,��λ���ʹ���
				 * &SeatPrice=99,66,59
				 */
				RequestParams params = new RequestParams();
				params.put("StationTrainCode", data.getTrainCode());
				params.put("StartStationTelecode", data.getStartStationCode());
				params.put("EndStationTelecode", data.getEndStationCode());
				params.put("FromStationTelecode", data.getFromStationCode());
				params.put("ToStationTelecode", data.getToStationCode());
				try {
					params.put("StartTime", HttpUtils.getTrainSimpleTime(
							data.getStart_train_date() + data.getFromTime(),
							"yyyy-MM-dd hh:ss"));
					params.put("ArriveTime", HttpUtils.getTrainSimpleTime(
							data.getStart_train_date() + data.getToTime(),
							"yyyy-MM-dd hh:ss"));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				params.put("LiShi", data.getTakeTime());
				params.put("DayDifference", data.getDay_difference());
				if (list != null && paramTemp != null && list.size() != 0) {
					int len = list.size();
					StringBuilder tempID = new StringBuilder();
					StringBuilder tempSeat = new StringBuilder();
					StringBuilder tempPrice = new StringBuilder();
					for (int i = 0; i < len; i++) {
						if (i != len - 1) {
							tempID.append(list.get(i).getTrainUserContactID()
									+ ",");
							tempSeat.append(valueMap.get(list.get(i)
									.getTempPiao()) + ",");
							tempPrice.append(list.get(i).getTempPrice()
									.substring(1)
									+ ",");
						} else {
							tempID.append(list.get(i).getTrainUserContactID());
							tempSeat.append(valueMap.get(list.get(i)
									.getTempPiao()));
							tempPrice.append(list.get(i).getTempPrice()
									.substring(1));
						}

					}

					// TrainUserContactID=1,1,1&SeatCode=��λ���ʹ���,��λ���ʹ���,��λ���ʹ���
					// * &SeatPrice=99,66,59
					params.put("TrainUserContactID", tempID.toString());
					params.put("SeatCode", tempSeat.toString());
					params.put("SeatPrice", tempPrice.toString());
					params.put("yth", yth);

					AsyncHttp
							.post(RealmName.REALM_NAME
									+ "/mi/TrainHandler.ashx?act=InsertTrainTicketOrder",
									params, new AsyncHttpResponseHandler() {

										@Override
										public void onSuccess(int arg0,
												String arg1) {
											// TODO Auto-generated method stub
											super.onSuccess(arg0, arg1);
											System.out.println(arg1);
											try {
												JSONObject object = new JSONObject(
														arg1);
												String status = object
														.getString("status");
												if (status.equals("1")) {

													trade_no = object
															.getString("trade_no");
													JSONArray array = object
															.getJSONArray("items");
													int len = array.length();
													if (len != 0) {
														banks = new ArrayList<CardItem>();
														bankNames = new String[len + 1];
														for (int i = 0; i < len; i++) {
															JSONObject object2 = array
																	.getJSONObject(i);
															CardItem item = new CardItem();
															item.setType(object2
																	.getString("pay_type"));
															item.setBankName(object2
																	.getString("gate_id"));
															item.setLastId(object2
																	.getString("last_four_cardid"));
															item.setId(object2
																	.getString("UserSignedBankID"));
															banks.add(item);
															bankNames[i] = ParseBank.parseBank(
																	item.getBankName(),
																	getApplicationContext())
																	+ "("
																	+ ParseBank
																			.paseName(item
																					.getType())
																	+ ")"
																	+ item.getLastId();
														}
														CardItem item = new CardItem();
														item.setBankName("-1");
														item.setId("-1");
														item.setLastId("-1");
														item.setType("-1");
														banks.add(item);
														bankNames[len] = "��֧����ʽ";
													}

													handler.sendEmptyMessage(2);
												} else {
													handler.sendEmptyMessage(3);
												}
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}
										}
									}, getApplicationContext());
				} else {
					Toast.makeText(getApplicationContext(), "����˳���Ϣ", 200)
							.show();
				}

			}
		});

		add_person.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (paramTemp != null) {
					Intent intent = new Intent(TrainDetailActivity.this,
							TrainPersonActivity.class);
					// 10000����������ӳ�Ա��Ϣ��������
					startActivityForResult(intent, 10000);
				} else {
					Toast.makeText(getApplicationContext(), "����ѡ��Ʊ", 200)
							.show();
				}
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode,
			android.content.Intent data) {
		if (resultCode == 10001) {
			// ������ϵ�˷��صķ�����
			MapSer ser = (MapSer) data.getSerializableExtra("result");
			Map<String, TrainPersonItem> map = ser.getMap();
			Iterator<String> i = map.keySet().iterator();
			list = new ArrayList<TrainPersonItem>();
			while (i.hasNext()) {
				// System.out.println("������map��ֵ" + i.next());
				list.add(map.get(i.next()));
			}
			for (int j = 0; j < list.size(); j++) {
				list.get(j).setTempPiao(paramTemp.getTag());
				list.get(j).setTempPrice(paramTemp.getPrice());
			}
			int size = list.size();
			int height = size * 30;
			// persons.setLayoutParams(new LayoutParams(
			// android.view.ViewGroup.LayoutParams.MATCH_PARENT, height));
			adapter = new TraindetailPersonItemAdapter(getApplicationContext(),
					list, handler);
			persons.setAdapter(adapter);

		}
	};

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "��ѯ����!", 200).show();
				break;
			case 1:
				/*
				 * �ص���:P һ����:M ������:WZ Ӳ��:A1 Ӳ��:A3 ����:A4 ������:A9 ����:O
				 */
				int len = priceDos.size();
				seats = new String[len];
				for (int i = 0; i < len; i++) {
					seats[i] = priceDos.get(i).getTag();
				}
				RadioGroup group = new RadioGroup(getApplicationContext());
				/*
				 * LayoutParams params = new LayoutParams(
				 * LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				 * group.setLayoutParams(params);
				 */
				// group.setGravity(Gravity.CENTER);
				group.setOrientation(LinearLayout.HORIZONTAL);
				for (int i = 0; i < len; i++) {
					RadioButton button = new RadioButton(
							getApplicationContext());
					button.setId(i + 10);
					button.setText(priceDos.get(i).getTag() + "\n"
							+ priceDos.get(i).getPrice());
					button.setGravity(Gravity.CENTER);
					button.setBackgroundResource(R.drawable.chongzhi_select);
					button.setButtonDrawable(android.R.color.transparent);
					button.setTextColor(R.color.black);
					group.addView(button);
				}
				select_seat.addView(group);
				group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup arg0, int arg1) {

						paramTemp = priceDos.get(arg1 - 10);
						if (list != null) {
							for (int i = 0; i < list.size(); i++) {
								list.get(i).setTempPiao(paramTemp.getTag());
								list.get(i).setTempPrice(paramTemp.getPrice());

							}
							adapter.notifyDataSetChanged();
						}

					}
				});
				// 13530689030
				break;
			case 2:
				if (banks != null && banks.size() != 0) {
					// ��ʾ�ǵڶ���֧��
					System.out.println("д�ڶ���֧��");
					// initPopupWindow1();
					// showPopupWindow1(btn_OK);
					Intent intent = new Intent(TrainDetailActivity.this,
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
					Intent intent = new Intent(TrainDetailActivity.this,
							PayActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("tag", 0);
					bundle.putSerializable("trade_no", trade_no);
					intent.putExtras(bundle);
					startActivity(intent);
					// initPopupWindow();
					// showPopupWindow(btn_OK);
				}
				Toast.makeText(getApplicationContext(), "�µ��ɹ�!", 200).show();
				AppManager.getAppManager().finishActivity();
				break;
			case 3:
				Toast.makeText(getApplicationContext(), "�µ�ʧ��!", 200).show();
				break;
			case 4:
				// list.get(0).setTempPiao("������λ");
				k = msg.arg1;
				initPopupWindow();
				showPopupWindow(select_seat);
				// adapter.notifyDataSetChanged();
				// Toast.makeText(getApplicationContext(),"�޸�܇Ʊ",200).show();
				break;
			default:
				break;
			}
		};
	};

	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	private WheelViewll seat_item;

	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.chose_payment, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//��������background������ʧ
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.grey));
		mPopupWindow.setOutsideTouchable(true);
		// �Զ��嶯��
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// ʹ��ϵͳ����
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		seat_item = (WheelViewll) popView.findViewById(R.id.bank_item);
		Button cancle = (Button) popView.findViewById(R.id.cancle);
		Button sure = (Button) popView.findViewById(R.id.sure);

		ArrayWheelAdapterll<String> bankAdapter = new ArrayWheelAdapterll<String>(
				seats);
		seat_item.setAdapter(bankAdapter);

		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dissPop();
			}
		});
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int index = seat_item.getCurrentItem();
				// kΪ��Ҫ�޸ĵ�list���.index��
				list.get(k).setTempPiao(priceDos.get(index).getTag());
				list.get(k).setTempPrice(priceDos.get(index).getPrice());
				adapter.notifyDataSetChanged();
				dissPop();
			}
		});
	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// ��һ������ָ��PopupWindow��ê��view�����������ĸ�view�ϡ�
			// �ڶ�������ָ����ʼ��Ϊparent�����½ǣ�����������������parent�����½�Ϊԭ�㣬�����ϸ�ƫ��10���ء�
			// int[] location = new int[2];
			// view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		}
	}

	private void dissPop() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}

	private WareDao wareDao;
	private String yth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.train_detail_layout);
		init();
		initMap();
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		Bundle bundle = getIntent().getExtras();
		data = (ChePiaoData) bundle.getSerializable("obj");
		v1.setText(data.getTrainCode());
		v2.setText(data.getFromStation());
		v3.setText(data.getFromTime());
		v4.setText(data.getTakeTime());
		v5.setText(data.getToStation());
		v6.setText(data.getToTime());
		// train_no=2400000G6502&from_station_no=01&to_station_no=17&seat_types=OM9&train_date=2014-06-27
		String train_no = data.getTrainNum();
		String from_no = data.getFrom_station_no();
		String to_no = data.getTo_station_no();
		String seat_types = data.getSeat_types();
		String time = bundle.getString("time");
		RequestParams params = new RequestParams();
		params.put("train_no", train_no);
		params.put("from_station_no", from_no);
		params.put("to_station_no", to_no);
		params.put("seat_types", seat_types);
		params.put("train_date", time);
		System.out.println(time + "~" + train_no + "~" + from_no + "~" + to_no
				+ "~" + seat_types);
		AsyncHttp.post(RealmName.REALM_NAME
				+ "/mi/TrainHandler.ashx?yth=test&act=QueryTicketPrice",
				params, new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						System.out.println(arg1);
						priceDos = new ArrayList<TrainPriceDo>();
						parse(arg1);
					}
				}, getApplicationContext());

	}

	private void IsHas(JSONObject object, String param) {
		try {
			// ������������ļ�ֵ
			if (object.has(param)) {
				TrainPriceDo trainPriceDo = new TrainPriceDo();
				trainPriceDo.setTag(map.get(param));
				trainPriceDo.setPrice(object.getString(param));
				priceDos.add(trainPriceDo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parse(String result) {
		Message msg = new Message();

		try {
			JSONObject jsonObject = new JSONObject(result);
			String status = jsonObject.getString("status");
			if (status.equals("1")) {
				// ��ʼ����
				String data = jsonObject.getString("msg");
				JSONObject objectParent = new JSONObject(data);
				JSONObject object = objectParent.getJSONObject("data");
				IsHas(object, "A9");
				IsHas(object, "P");
				IsHas(object, "M");
				IsHas(object, "A4");
				IsHas(object, "A3");
				IsHas(object, "A1");
				IsHas(object, "O");
				IsHas(object, "WZ");
				// if (object.has("WZ") && object.has("A1")) {
				// IsHas(object, "A1");
				// } else {
				// IsHas(object, "WZ");
				// IsHas(object, "A1");
				// }

				// ����������յ��쳵������Oѡ����WZ���վƱ
				// if (!object.has("O")) {
				// TrainPriceDo trainPriceDo = new TrainPriceDo();
				// trainPriceDo.setTag("����");
				// trainPriceDo.setPrice(object.getString("WZ"));
				// priceDos.add(trainPriceDo);
				// }

				msg.what = 1;
			} else {
				msg.what = 0;
			}
			handler.sendMessage(msg);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
