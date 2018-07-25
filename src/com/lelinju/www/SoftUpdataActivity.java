package com.lelinju.www;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.hengyu.web.FtpImage;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.common.SharedUtils;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.lelinju.www.R;

public class SoftUpdataActivity extends BaseActivity {
	private TextView v1, v2, v3,price;
	private LinearLayout l1, l2, l3;
	private Button btn, updata_ico;
	private EditText text1, text2, pass1, pass2;
	private int INDEX = 0;
	private final String IMAGE_TYPE = "image/*";
	private final int IMAGE_CODE = 0;
	private String path;
	private WareDao wareDao;
	private String yth;
	private String key;
	private String image_url;

	private void setImage() {
		// TODO Auto-generated method stub
		// ʹ��intent����ϵͳ�ṩ����Ṧ�ܣ�ʹ��startActivityForResult��Ϊ�˻�ȡ�û�ѡ���ͼƬ
		Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
		getAlbum.setType(IMAGE_TYPE);
		startActivityForResult(getAlbum, IMAGE_CODE);
	}

	@SuppressWarnings("deprecation")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode != RESULT_OK) { // �˴��� RESULT_OK ��ϵͳ�Զ����һ������
			Log.e("TAG->onresult", "ActivityResult resultCode error");
			return;
		}
		Bitmap bm = null;
		// ���ĳ������ContentProvider���ṩ���� ����ͨ��ContentResolver�ӿ�
		ContentResolver resolver = getContentResolver();
		// �˴��������жϽ��յ�Activity�ǲ�������Ҫ���Ǹ�
		if (requestCode == IMAGE_CODE) {
			try {
				Uri originalUri = data.getData(); // ���ͼƬ��uri
				bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);
				// �Եõ�bitmapͼƬ
				// img_head.setImageBitmap(bm);
				// ���￪ʼ�ĵڶ����֣���ȡͼƬ��·����
				String[] proj = { MediaStore.Images.Media.DATA };
				// ������android��ý�����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�
				Cursor cursor = managedQuery(originalUri, proj, null, null,
						null);
				// ���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ
				int column_index = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				// �����������ͷ ���������Ҫ����С�ĺ���������Խ��
				cursor.moveToFirst();
				// ����������ֵ��ȡͼƬ·��
				path = cursor.getString(column_index);

				new Thread() {
					public void run() {

						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyyMMddHHmmssSSS");
						Date curDate = new Date(System.currentTimeMillis());// ��ȡ��ǰʱ��
						// fileName = formatter.format(curDate);

						try {
							FtpImage.ftpUpload(path, yth, yth + "_card");
						} catch (IllegalStateException e) {
							e.printStackTrace();
						} catch (FTPIllegalReplyException e) {
							e.printStackTrace();
						} catch (FTPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FTPDataTransferException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FTPAbortedException e) {
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String imgUrl = "/ftpFiles/PhoneImages/" + yth + "/"
								+ yth + "_card.jpg";
						String strUrl = RealmName.REALM_NAME
								+ "/mi/receiveOrderInfo.ashx?act=UpdateUserAvatarimage&yth="
								+ yth + "&AvatarimageURL=" + imgUrl + "&key="
								+ key;
						image_url = imgUrl;
						AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
							public void onSuccess(int arg0, String arg1) {
								updata_ico.setText("�ϴ����");

							};
						}, getApplicationContext());

					};
				}.start();

			} catch (IOException e) {
				Log.e("TAG-->Error", e.toString());
			}
		}
	}

	private String trade_no;
	private ArrayList<CardItem> banks;
	private String[] bankNames;

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			if (banks != null && banks.size() != 0) {
				// ��ʾ�ǵڶ���֧��
				System.out.println("д�ڶ���֧��");
				// initPopupWindow1();
				// showPopupWindow1(btn_OK);
				Intent intent = new Intent(SoftUpdataActivity.this,
						PayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("tag", 1);
				bundle.putSerializable("trade_no", trade_no);
				bundle.putStringArray("bank_names", bankNames);
				bundle.putSerializable("bank_objs", banks);
				intent.putExtras(bundle);
				startActivity(intent);
				AppManager.getAppManager().finishActivity();
			} else {
				// ��ʾ�״�֧��
				Intent intent = new Intent(SoftUpdataActivity.this,
						PayActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("tag", 0);
				bundle.putSerializable("trade_no", trade_no);
				intent.putExtras(bundle);
				startActivity(intent);
				AppManager.getAppManager().finishActivity();
				// initPopupWindow();
				// showPopupWindow(btn_OK);
			}
		};
	};

	private void init() {
		pass1 = (EditText) findViewById(R.id.pass1);
		pass2 = (EditText) findViewById(R.id.pass2);
		text1 = (EditText) findViewById(R.id.text11);
		text2 = (EditText) findViewById(R.id.text22);
		price = (TextView) findViewById(R.id.price);
		price.setText(getIntent().getStringExtra("price")+"Ԫ");
		v1 = (TextView) findViewById(R.id.text1);
		v2 = (TextView) findViewById(R.id.text2);
		v3 = (TextView) findViewById(R.id.text3);
		l1 = (LinearLayout) findViewById(R.id.layout1);
		l2 = (LinearLayout) findViewById(R.id.layout2);
		l3 = (LinearLayout) findViewById(R.id.layout3);
		btn = (Button) findViewById(R.id.btn);
		l1.setVisibility(View.VISIBLE);
		l2.setVisibility(View.INVISIBLE);
		l3.setVisibility(View.INVISIBLE);
		v1.setTextColor(getResources().getColor(R.color.green));
		v2.setTextColor(getResources().getColor(R.color.black));
		v3.setTextColor(getResources().getColor(R.color.black));
		updata_ico = (Button) findViewById(R.id.updata_ico);
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();
		if (yth == null) {
			NewDataToast.makeText(getApplicationContext(), "�û�δ��¼", false, 0).show();
			AppManager.getAppManager().finishActivity();
		}else {
			SharedUtils utils = new SharedUtils(getApplicationContext(), "shouyi");
			if(utils.getStringValue("IsVipPrivilege").equals("1")){
				NewDataToast.makeText(getApplicationContext(), "�����ٴ�����", false, 0).show();
				AppManager.getAppManager().finishActivity();
			}
		}
		updata_ico.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setImage();
			}
		});

		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (INDEX == 2) {
					// ������������ݵ��ύ
					String name = text1.getText().toString();
					String card_id = text2.getText().toString();
					String pass_1 = pass1.getText().toString();
					String pass_2 = pass2.getText().toString();
						/*
						 * http://www.ehaoyy.com//mi/getdata.ashx?act=
						 * RealNameAuthentication_Phone
						 * &identityCard=55&identityimageURL=55
						 * &payPassword=1&actualname=1&yth=admin
						 * &PayType=1&PayPassTickets=1
						 */
						Map<String, String> params = new HashMap<String, String>();
						params.put("identityCard", card_id);
						params.put("identityimageURL", image_url);
						params.put("payPassword", pass_1);
						params.put("yth", yth);
						params.put("actualname", name);
						params.put("payType", "1");
						params.put("productBenchmarkPriceID", getIntent().getStringExtra("vip"));
						params.put("payPassTickets", getIntent().getStringExtra("price"));
						params.put("act", "RealNameAuthentication_Phone");
						System.out.println(card_id + "--" + name + "--"
								+ pass_1 + "---" + image_url + "--" + yth);
						AsyncHttp
								.post_1(RealmName.REALM_NAME
										+ "/mi/getdata.ashx",
										params, new AsyncHttpResponseHandler() {
											public void onSuccess(int arg0,
													String arg1) {
												// TODO Auto-generated method
												super.onSuccess(arg0, arg1);
												System.out.println(arg1);
												try {
													JSONObject object = new JSONObject(
															arg1);
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
													handler.sendEmptyMessage(0);
												} catch (JSONException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												}

											};
										} );
					 
				} else if (INDEX == 1) {
					 if (!(pass1.getText().toString().length()<20&&pass1.getText().toString().length()>=8)) {
						 NewDataToast.makeText(getApplicationContext(), "������8-20λ֮��", false, 0).show();
					}else if (!(pass2.getText().toString().length()<20&&pass2.getText().toString().length()>=8)) {
						NewDataToast.makeText(getApplicationContext(), "������8-20λ֮��", false, 0).show();
					}else if (!pass1.getText().toString().equals(pass2.getText().toString())) {
						Toast.makeText(getApplicationContext(), "���ν������벻һ��",
								200).show();
					}else {
						INDEX = INDEX + 1;
					}
				}else {
					INDEX = INDEX + 1;
				}
				switch (INDEX) {
				case 0:
					v1.setTextColor(getResources().getColor(R.color.green));
					v2.setTextColor(getResources().getColor(R.color.black));
					v3.setTextColor(getResources().getColor(R.color.black));
					l1.setVisibility(View.VISIBLE);
					l2.setVisibility(View.INVISIBLE);
					l3.setVisibility(View.INVISIBLE);
					break;
				case 1:
					v2.setTextColor(getResources().getColor(R.color.green));
					v1.setTextColor(getResources().getColor(R.color.black));
					v3.setTextColor(getResources().getColor(R.color.black));
					l1.setVisibility(View.INVISIBLE);
					l2.setVisibility(View.VISIBLE);
					l3.setVisibility(View.INVISIBLE);
					break;
				case 2:
					v3.setTextColor(getResources().getColor(R.color.green));
					v2.setTextColor(getResources().getColor(R.color.black));
					v1.setTextColor(getResources().getColor(R.color.black));
					l1.setVisibility(View.INVISIBLE);
					l2.setVisibility(View.INVISIBLE);
					l3.setVisibility(View.VISIBLE);
					break;
				default:
					break;
				}
			}
		});

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.soft_updata_layout);
		init();
	}
}
