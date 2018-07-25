package com.lelinju.www;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.hengyu.pub.MyShopCartAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.ShopCarts;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingCart1Activity extends BaseActivity {
	private ListView list_shop_cart;
	private Button btn_sittle_account;
	private TextView tv_endnumber, tv_endmarketprice, tv_preferential,
			tv_endmoney, jf;
	private LinearLayout list_shops, list_none;
	private WareDao wareDao;
	private ArrayList<ShopCarts> list;
	private MyShopCartAdapter adapter;
	private ShopCartData data;
	private DialogProgress progress;
	private String strUrl;
	private String yth;
	private MyPopupWindowMenu popupWindowMenu;
	private EditText tv_amount_jf;
	private UserRegisterData registerData;
	private CheckBox in_jf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart);
		new Thread() {
			public void run() {
				wareDao = new WareDao(ShoppingCart1Activity.this);
				registerData = wareDao.findIsLoginHengyuCode();
				yth = registerData.getHengyuCode();
				handler.sendEmptyMessage(0);
				// ------------
				wareDao = new WareDao(ShoppingCart1Activity.this);
				/*
				 * List<ShopCartData> list = wareDao.findShopCart(); Message
				 * message = new Message(); message.what = 100; message.obj =
				 * list; handler.sendMessage(message);
				 */
				// --------------
				/*
				 * data = wareDao.findResult(); Message message2 = new
				 * Message(); message2.what = 200; message2.obj = data;
				 * handler.sendMessage(message2);
				 */

			};
		}.start();
		progress = new DialogProgress(ShoppingCart1Activity.this);

		ininate();
//		loadWeather();

	}
	/**
	 * �������
	 * @param carts
	 * @return
	 */
	private int countJf(ArrayList<ShopCarts> carts) {
		int count = 0;

		for (int i = 0; i < carts.size(); i++) {
			ArrayList<ShopCartData> data = carts.get(i).getList();
			int len = data.size();
			for (int j = 0; j < len; j++) {
				if(data.get(j).isCheck){
				count+=data.get(j).getJf()*data.get(j).getNumber();
				}
			}
		}
		return count;
	}
	/**
	 * ��������
	 * @param carts
	 * @return
	 */
	private int countNum(ArrayList<ShopCarts> carts) {
		int count = 0;

		for (int i = 0; i < carts.size(); i++) {
			ArrayList<ShopCartData> data = carts.get(i).getList();
			int len = data.size();
			for (int j = 0; j < len; j++) {
				if(data.get(j).isCheck){
					count+=1;
				}
			}
		}
		return  count;
	}
	/**
	 * �����ܼ�
	 * @param carts
	 * @return
	 */
	private String countTotal(ArrayList<ShopCarts> carts) {
		double count = 0;

		for (int i = 0; i < carts.size(); i++) {
			ArrayList<ShopCartData> data = carts.get(i).getList();
			int len = data.size();
			for (int j = 0; j < len; j++) {
				if(data.get(j).isCheck){
					count+= Double.parseDouble(data.get(j).getRetailprice())*data.get(j).getNumber();
				}
			}
		}
		BigDecimal b = new BigDecimal(count);
		// ����2λС��
		double targetDouble = b.setScale(2, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
		return String.valueOf(targetDouble);
	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case -1:
				final ArrayList<ShopCarts> carts0 = (ArrayList<ShopCarts>) msg.obj;
				adapter.putData(carts0);
				if (carts0.size() == 0) {
					list_none.setVisibility(View.VISIBLE);
					list_shop_cart.setVisibility(View.INVISIBLE);
					btn_sittle_account.setEnabled(false);
					in_jf.setEnabled(false);
				} else {
					list_none.setVisibility(View.INVISIBLE);
					list_shop_cart.setVisibility(View.VISIBLE);
					btn_sittle_account.setEnabled(true);
					in_jf.setEnabled(true);
				}

				break;
			case -2:
				final ArrayList<ShopCarts> carts = (ArrayList<ShopCarts>) msg.obj;
				if (carts.size() != 0) {

				/*	tv_endnumber.setText(data.getEndnumber() + "");
					tv_endmarketprice.setText("��"
							+ data.getEndmarketprice().toString());
					tv_preferential.setText("��"
							+ data.getPreferential().toString());
					tv_endmoney.setText("��" + data.getEndmoney().toString());
*/
					tv_endmoney.setText("��" + countTotal(carts));
					if (in_jf.isChecked()) {
						in_jf.setChecked(false);
					}
					int jf = (int) Double
							.parseDouble(registerData.getCredits());// �ܾۺ��

					int xf = countJf(carts);
					if (jf > xf) {
						tv_amount_jf.setHint("����:" + xf);
					} else {
						tv_amount_jf.setHint("����:" + jf);
					}
					btn_sittle_account
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub

									// TODO Auto-generated method stub
									if (countNum(carts)>0) {
										int jf = (int) Double
												.parseDouble(registerData
														.getCredits());// �ܾۺ��
										if (countJf(carts) > jf) {
											Toast.makeText(ShoppingCart1Activity.this,
													"�ۺ������", 200).show();
										} else {
											Intent intent = new Intent(
													ShoppingCart1Activity.this,
													OrderConfrimActivity.class);
											String rsu = tv_amount_jf.getText()
													.toString();
											intent.putExtra("jf",
													rsu.length() == 0 ? "0"
															: rsu);
											intent.putExtra("obj", carts);
											startActivity(intent);
										}
										
									} else {
										Toast.makeText(ShoppingCart1Activity.this,
												"�빴ѡҪ�µ�����Ʒ", 200).show();
									}

								}
							});


					in_jf.setOnCheckedChangeListener(new OnCheckedChangeListener() {

						@Override
						public void onCheckedChanged(CompoundButton arg0,
								boolean arg1) {
							int jf = (int) Double.parseDouble(registerData
									.getCredits());// �ܾۺ��
							int xf = countJf(carts);// �����Ѿۺ��
							double price = Double.parseDouble(countTotal(carts));
							if (arg1) {
								tv_amount_jf.setEnabled(true);
								// ����ǵ������״̬
								if (jf - xf > 0) {
									tv_amount_jf.setText(String.valueOf(xf));
								} else {
									tv_amount_jf.setText(String.valueOf(jf));
								}
							} else {
								tv_amount_jf.setEnabled(false);
								// ����ǵ��δ����״̬
								tv_endmoney.setText(String.valueOf(price));
								tv_amount_jf.setHint("����:" + countJf(carts));
								tv_amount_jf.setText("");
							}
						}
					});
					tv_amount_jf.addTextChangedListener(new TextWatcher() {
						@Override
						public void onTextChanged(CharSequence arg0, int arg1,
								int arg2, int arg3) {

						}

						@Override
						public void beforeTextChanged(CharSequence arg0,
								int arg1, int arg2, int arg3) {

						}

						/**
						 * �����������ֵĸ����¼���Ӧ
						 */
						@Override
						public void afterTextChanged(Editable arg0) {
							// TODO Auto-generated method stub
							// tv_amount_jf.setText(arg0);
							String result = arg0.toString();
							double price = Double.parseDouble(countTotal(carts));
							if (result.length() != 0) {
								System.out.println(result);
								int jf = (int) Double.parseDouble(registerData
										.getCredits());// �ܾۺ��
								int rt = Integer.parseInt(result);// ����ۺ��
								int xf = countJf(carts);// �����Ѿۺ��
								if (jf - xf > 0) {
									// �ܾۺ�����ڿ����ѣ�ֻ�����벻���ڿ����Ѽ���
									if (rt <= xf) {
										tv_endmoney.setText(String
												.valueOf((price - rt < 0) ? 0
														: (price - rt)));
									} else {
										Toast.makeText(ShoppingCart1Activity.this,
												"����ۺ�������޶�" + xf + "��" + jf,
												200).show();
									}
								} else if (0 >= (jf - xf)) {
									if (rt <= jf) {
										tv_endmoney.setText(String
												.valueOf((price - rt < 0) ? 0
														: (price - rt)));
									} else {
										Toast.makeText(
												ShoppingCart1Activity.this,
												"����ۺ�����ڸ����ܾۺ��" + xf + "��" + jf,
												200).show();
									}
								}
							} else {

								tv_endmoney.setText(String.valueOf(price));
							}
						}
					});
					btn_sittle_account.setText("ȥ����(" + countNum(carts)+")");
				} else {
					tv_endnumber.setText("");
					tv_endmarketprice.setText("");
					tv_preferential.setText("");
					tv_endmoney.setText("");
					tv_amount_jf.setHint("");
					btn_sittle_account.setText("ȥ����");
				}
				break;
			case 0:
				try {
					loadCart();
					load();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				jf.setText("��ǰ�ۺ��:" + registerData.getDaijifen() == null ? "0"
						: registerData.getDaijifen());
				break;
			 

			case 400:
				int position = (Integer) msg.obj;
				int orderid = msg.arg1;
				dialog(position, orderid);
				break;
			case 1:
				registerData = (UserRegisterData) msg.obj;
				UserRegisterData is = wareDao.findLoginCheck(yth);
				if (is.getIsLogin() != 0) {
					// δ��¼
					registerData.setIsLogin(1);// �޸�
					wareDao.updateIsLogin(yth, registerData);
				}
				break;
			default:
				break;
			}
			super.dispatchMessage(msg);
		}
	};

	private void loadCart() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("yth", yth);
		params.put("key", registerData.getUserkey());
		params.put("act", "UserCartInfo");
		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							/*
							 * 0��ͨ��Ʒ 1Ϊ���ѳ�ֵ����Ʒ��Ϣ�� 2ΪQQ��ֵ����Ʒ��Ϣ�� 3Ϊ��Ʊ����Ʒ��Ϣ��
							 * 4Ϊ����̳ǣ������̾�ϵͳ�еľ۱Ҷһ����������Ʒ�� 5Ϊ�齱������õ���Ʒ��
							 * 6��ʾһԪ�۹���ҵ����Ʒ��
							 * 7��ʾ�û�����VIP��ҵ����Ʒ���û�����Ϊvip֮�󣬻����û�����Ʒ����
							 * 8��ʾ�Ź���Ϣ���͵�ҵ����Ʒ��
							 */
							ArrayList<ShopCarts> lists = new ArrayList<ShopCarts>();

							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							if (status.equals("1")) {
								// ����0����ͨ��Ʒ
								JSONArray array0 = jsonObject
										.getJSONArray("productItemType_0_items");
								int len0 = array0.length();
								if (len0 != 0) {

									ArrayList<ShopCartData> cart_item0 = new ArrayList<ShopCartData>();
									for (int i = 0; i < len0; i++) {
										cart_item0.add(getparse(array0, i));
									}
									ShopCarts carts = new ShopCarts();
									carts.setList(cart_item0);
									carts.setName("���ֹ�");
									lists.add(carts);
								}
								// ����4�������Ʒ
								JSONArray array4 = jsonObject
										.getJSONArray("productItemType_4_items");
								int len4 = array4.length();
								if (len4 != 0) {

									ArrayList<ShopCartData> cart_item0 = new ArrayList<ShopCartData>();
									for (int i = 0; i < len4; i++) {
										cart_item0.add(getparse(array4, i));
									}
									ShopCarts carts = new ShopCarts();
									carts.setList(cart_item0);
									carts.setName("���");
									lists.add(carts);
								}
								// ����5����ͨ��Ʒ
								JSONArray array5 = jsonObject
										.getJSONArray("productItemType_5_items");
								int len5 = array5.length();
								if (len5 != 0) {

									ArrayList<ShopCartData> cart_item0 = new ArrayList<ShopCartData>();
									for (int i = 0; i < len5; i++) {
										cart_item0.add(getparse(array5, i));
									}
									ShopCarts carts = new ShopCarts();
									carts.setList(cart_item0);
									carts.setName("�齱");
									lists.add(carts);
								}
								// ����6��һԪ��Ʒ
								JSONArray array6 = jsonObject
										.getJSONArray("productItemType_6_items");
								int len6 = array6.length();
								if (len6 != 0) {

									ArrayList<ShopCartData> cart_item0 = new ArrayList<ShopCartData>();
									for (int i = 0; i < len6; i++) {
										cart_item0.add(getparse(array6, i));
									}
									ShopCarts carts = new ShopCarts();
									carts.setList(cart_item0);
									carts.setName("һԪ��");
									lists.add(carts);
								}
							}
							// ����7��VIP��Ʒ
							JSONArray array7 = jsonObject
									.getJSONArray("productItemType_7_items");
							int len7 = array7.length();
							if (len7 != 0) {

								ArrayList<ShopCartData> cart_item0 = new ArrayList<ShopCartData>();
								for (int i = 0; i < len7; i++) {
									cart_item0.add(getparse(array7, i));
								}
								ShopCarts carts = new ShopCarts();
								carts.setList(cart_item0);
								carts.setName("VIP��Ʒ");
								lists.add(carts);
							}
							Message msg = new Message();
							msg.what = -1;
							msg.obj = lists;
							handler.sendMessage(msg);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				});
	}

	private ShopCartData getparse(JSONArray array, int i) {

		ShopCartData data = new ShopCartData();
		try {
			JSONObject object = array.getJSONObject(i);

			data.orderid = object.getInt("ProductOrderItemId");
			data.wareid = object.getInt("productItemId");
			data.warename = object.getString("proName");
			data.number = object.getInt("productCount");
			data.retailprice = object.getString("oneProductPrice");
			data.marketprice = object.getString("marketPrice");
			data.stylenameone = object.getString("sellPropertyName1");
			data.stylenatureone = object.getString("sellPropertyValue1");
			data.stylenametwo = object.getString("sellPropertyName2");
			data.stylenaturetwo = object.getString("sellPropertyValue2");
			data.totalProductPrice = object.getString("totalProductPrice");
			String abe = object.getString("AvailableIntegral");
			if (abe.length() == 0) {
				data.jf = 0;
			} else {
				data.jf = (int) Double.parseDouble(object
						.getString("AvailableIntegral"));
			}
			// data.jf = 2;
			data.imgurl = object.getString("proFaceImg");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	private void load() throws JSONException, Exception {
		String str2 = RealmName.REALM_NAME + "/mi/getdata.ashx";

		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "myInfo");
		params.put("key", registerData.getUserkey());
		params.put("yth", yth);
		AsyncHttp.post_1(str2, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				UserRegisterData data2 = null;
				try {
					JSONObject object2 = new JSONObject(arg1);
					data2 = new UserRegisterData();
					data2.hengyuCode = object2.getString("HengYuCode");
					data2.userName = object2.getString("username");
					data2.PassTicketBalance = object2
							.getString("PassTicketBalance");
					data2.shopPassTicket = object2.getString("shopPassTicket");
					data2.avatarimageURL = object2.getString("avatarimageURL");
					data2.credits = object2.getString("credits");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message msg = new Message();
				msg.what = 1;
				msg.obj = data2;
				handler.sendMessage(msg);
			}
		});

	}
	 
	protected void dialog(final int index, final int orderid) {
		AlertDialog.Builder builder = new Builder(ShoppingCart1Activity.this);
		builder.setMessage("ȷ��ɾ�������Ʒ��");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (adapter != null) {
					adapter.deleteData(null, index);
				}
				strUrl = RealmName.REALM_NAME + "/mi/receiveOrderInfo.ashx?"
						+ "act=DeleteOneCartInfo&yth=" + yth
						+ "&ProductOrderItemId=" + orderid;
				AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(),
						ShoppingCart1Activity.this);

				// ��Ʒ�����������͸ı� ˢ�¼���ҳ��
				data = wareDao.findResult();
				Message message = new Message();
				message.what = 200;
				message.obj = data;
				handler.sendMessage(message);
				dialog.dismiss();
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

	/**
	 * ��ʼ���ؼ����
	 */
	private void ininate( ) {
		list_none = (LinearLayout)findViewById(R.id.list_none);
		list_shops = (LinearLayout)findViewById(R.id.list_shops);
		in_jf = (CheckBox)findViewById(R.id.in_jf);
		btn_sittle_account = (Button) findViewById(R.id.btn_settle_accounts);
		list_shop_cart = (ListView)findViewById(R.id.list_shop_cart);
		tv_endnumber = (TextView)findViewById(R.id.tv_number);
		tv_endmarketprice = (TextView)  findViewById(R.id.tv_original_price);
		tv_preferential = (TextView)findViewById(R.id.tv_preferential);
		tv_endmoney = (TextView)findViewById(R.id.tv_amount_payable);
		tv_amount_jf = (EditText)findViewById(R.id.tv_amount_jf);
		jf = (TextView)findViewById(R.id.jf);
		ArrayList<ShopCarts> carts = new ArrayList<ShopCarts>();
		adapter = new MyShopCartAdapter(carts, ShoppingCart1Activity.this, handler,
				imageLoader);
		list_shop_cart.setAdapter(adapter);
		// jf.setText("��ǰ�ۺ��:" + registerData.getCredits());
	}
}
