package com.lelinju.www;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import com.tencent.mm.sdk.constants.Build;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.android.hengyu.pub.MyAddAddressAdapter;
import com.android.hengyu.pub.MyShopCartAdapter;
import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.MD5;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.android.pliay.SignUtils;
import com.hengyushop.airplane.data.ParseBank;
import com.hengyushop.dao.CardItem;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.ShopCarts;
import com.hengyushop.entity.UserAddressData;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.widget.CommomConfrim;
import com.lglottery.www.widget.CommomConfrim.onDeleteSelect;
import com.lglottery.www.widget.InScrollListView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.lelinju.www.R;

public class OrderConfrimActivity extends BaseActivity {
	private ListView list_address_a;
	private ImageButton btn_add_address;
	private String yth, key, phone, imei, tel, endmoney, pwd, username,
			PassTicket, shopPassTicket, orderSerialNumber;
	private int addressid;
	private WareDao wareDao;
	private UserRegisterData registerData;
	private String strUrl;
	private ArrayList<UserAddressData> list;
	private MyAddAddressAdapter adapter;
	private DialogProgress progress;
	private int checkedAddressId;
	private StringBuilder orderid;
	private MyPopupWindowMenu popupWindowMenu;
	private String trade_no;
	private ArrayList<CardItem> banks = null;
	private String bankNames[] = null;
	private static final int REQUESTCODE = 10000;
	private int jf;
	private ArrayList<ShopCarts> carts;
//	private RelativeLayout pay_click0, pay_click1, pay_click2, pay_click3;
	private LinearLayout layout0;
	private RelativeLayout layout1;
	
	
	private TextView tv_user_name,tv_user_address,tv_user_phone;
	private InScrollListView list_shop_cart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		try {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_confrim);
		api = WXAPIFactory.createWXAPI(OrderConfrimActivity.this,null);
		api.registerApp(Constants.APP_ID);
		layout0 = (LinearLayout) findViewById(R.id.layout0);
		layout1 = (RelativeLayout) findViewById(R.id.layout1);
		//-------
		tv_user_name = (TextView) findViewById(R.id.tv_user_name);
		tv_user_address = (TextView) findViewById(R.id.tv_user_address);
		tv_user_phone = (TextView) findViewById(R.id.tv_user_phone);
		list_shop_cart = (InScrollListView) findViewById(R.id.list_shop_cart);
		popupWindowMenu = new MyPopupWindowMenu(this);
		wareDao = new WareDao(getApplicationContext());
		progress = new DialogProgress(OrderConfrimActivity.this);
		System.out.println("============0");
		try {
//			if(getIntent().hasExtra("obj")){
//				
//			carts = (ArrayList<ShopCarts>) getIntent().getSerializableExtra("obj");
//			
//			ArrayList<ShopCarts> newcaCarts = getNew(carts);
//			list_shop_cart.setAdapter(new MyShopCartAdapter(newcaCarts, getApplicationContext(), null, imageLoader));
//			jf = Integer.parseInt(getIntent().getStringExtra("jf"));
//			endmoney = countTotal(carts);
//			orderid = new StringBuilder();
//			for (int i = 0; i < carts.size(); i++) {
//				ArrayList<ShopCartData> datas = carts.get(i).getList();
//				for (int j = 0; j < datas.size(); j++) {
//					if (datas.get(j).isCheck) {
//						if (i!=carts.size()-1&&j!= datas.size()-1) {
//							orderid.append(datas.get(j).getOrderid() + ",");
//						} else {
//							orderid.append(datas.get(j).getOrderid());
//						}
//					}
//				}
//			}	
//			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			AppManager.getAppManager().finishActivity();
		}
		
//		innidada();//��ʱע��
		
		// endmoney = String.valueOf(Double.parseDouble(data.getEndmoney())-jf);
		 
		
		// endmoney = "0.01";
//		registerData = wareDao.findIsLoginHengyuCode();
//		yth = registerData.getHengyuCode().toString();
//		key = registerData.getUserkey().toString();
//		phone = registerData.getPhone().toString();
//		pwd = registerData.getPassword().toString();
		System.out.println("============1");
//		DOparse();//��ȡĬ�ϵ�ַ
		System.out.println("============2");
		TelephonyManager telephonyManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		imei = telephonyManager.getDeviceId();
		tel = telephonyManager.getLine1Number();
	

		/*List<ShopCartData> list = wareDao.findShopCart();
		orderid = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			orderid.append(list.get(i).getOrderid());
			if (i < list.size() - 1) {
				orderid.append(",");
			}
		}*/
		System.out.println("orderid:" + orderid + "  endmoney:" + endmoney);
		System.out.println("============3");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ��������в�ѯ��ַ�Ĳ���
		// Toast.makeText(getApplicationContext(), "��ѯ��ַ��������",200).show();
//		handler.sendEmptyMessage(4);
		 if(resultCode==100){
			 layout0.setVisibility(View.VISIBLE);
			 layout1.setVisibility(View.GONE);
			 
			 UserAddressData dt =  (UserAddressData) data.getSerializableExtra("data");
			 checkedAddressId = dt.consigneeAddressId;
				tv_user_name.setText("�ջ���:"+dt.consigneeUserName);
				tv_user_address.setText(dt.consigneeAddressInfo);
				tv_user_phone.setText(dt.phone);
		 }
	}


	private IWXAPI api;
	Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case -1:
				 
				//���������¶���
				String orderid = (String) msg.obj;
//				endmoney = getIntent().getStringExtra("retailPrice");
				order_str = RealmName.REALM_NAME
						+ "/mi/receiveOrderInfo_business.ashx?" + "imei="
						+ imei
						+ "&act=PhoneBuyProductInfo&bossUid=1&hengyucode="
						+ yth + "&pwd=" + pwd + "&buyPhone=" + phone
						+ "&paymentTypeId=3&consigneeAddressId="
						+ checkedAddressId + "&ProductOrderItemIds=" + orderid
						+ "&PayPassTickets=" + endmoney
						+ "&buyPhoneVerificationCode=&PayType=" + type
						+ "&ConsumptionCredits=" + jf;
				yuePay();
				//���֧��״̬
				break;
			case 0:
				list = (ArrayList<UserAddressData>) msg.obj;
				int size = list.size();
				if(size!=0){
					//������ȡ��һ��
					UserAddressData data = list.get(0);
					layout0.setVisibility(View.VISIBLE);
					layout1.setVisibility(View.GONE);
					checkedAddressId = data.consigneeAddressId;
					tv_user_name.setText("�ջ���:"+data.consigneeUserName);
					tv_user_address.setText(data.consigneeAddressInfo);
					tv_user_phone.setText(data.phone);
					layout0.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View arg0) {
							Intent intent = new Intent(OrderConfrimActivity.this,AddressManagerActivity.class);
							intent.putExtra("order_confrim", "order_confrim");//��ʾ
							startActivityForResult(intent, 100);
						}
					});
				}else {
					//���ý���Ŀ��ֺ���������
					layout0.setVisibility(View.GONE);
					layout1.setVisibility(View.VISIBLE);
				}
				
				adapter = new MyAddAddressAdapter(list,
						OrderConfrimActivity.this, handler);
				list_address_a.setAdapter(adapter);
				break;
			case 1:
				checkedAddressId = msg.arg1;
				break;
			case 2:
				if (TextUtils.equals("1", type)) {
					// �ڴ˵���ѡ���
					// ��ת������ҳ��
					if (banks != null && banks.size() != 0) {
						// ��ʾ�ǵڶ���֧��
						System.out.println("д�ڶ���֧��");
						// initPopupWindow1();
						// showPopupWindow1(btn_OK);
						Intent intent = new Intent(OrderConfrimActivity.this,
								PayActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("tag", 1);
						bundle.putSerializable("trade_no", trade_no);
						bundle.putStringArray("bank_names", bankNames);
						bundle.putSerializable("bank_objs", banks);
						intent.putExtras(bundle);
						startActivityForResult(intent, 1);
						
					} else {
						// ��ʾ�״�֧��
						Intent intent = new Intent(OrderConfrimActivity.this,
								PayActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("tag", 0);
						bundle.putSerializable("trade_no", trade_no);
						intent.putExtras(bundle);
						startActivity(intent);
						// initPopupWindow();
						// showPopupWindow(btn_OK);
					}

				} else if (TextUtils.equals("2", type)) {
					// ֧����
					ali_pay();

				} else if(TextUtils.equals("5", type)){
					System.out.println("΢��֧��");
					//΢��֧��
					
				
					boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
					if(isPaySupported){ 
							PayReq req = new PayReq();
							req.appId			= Constants.APP_ID;
							req.partnerId		= Constants.MCH_ID;
							req.prepayId		= prepayid;
							req.nonceStr		= noncestr;
							req.timeStamp		= timestamp;
							req.packageValue	= package_;
							req.sign			= sign;
							// ��֧��֮ǰ�����Ӧ��û��ע�ᵽ΢�ţ�Ӧ���ȵ���IWXMsg.registerApp��Ӧ��ע�ᵽ΢��
							api.registerApp(Constants.APP_ID);
							boolean flag = api.sendReq(req);
							System.out.println("֧��"+flag);
					}else {
						
					}
					
					
					
				}else {
					System.out.println("?");
					int index = 1;
					Intent intent = new Intent(OrderConfrimActivity.this,
							WareShopCartPayActivity.class);
					intent.putExtra("id", index);
					intent.putExtra("orderSerialNumber", orderSerialNumber);
					intent.putExtra("endmoney", endmoney);
					startActivity(intent);
					AppManager.getAppManager().finishActivity();
				}

				break;
			case 3:
				String str = (String) msg.obj;
				Toast.makeText(getApplicationContext(), str, 200).show();
				break;
			case 4:
				DOparse();
				break;
			case 5: {
				PayResult payResult = new PayResult((String) msg.obj);

				// ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();
				System.out.println(resultInfo + "---" + resultStatus);
				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(OrderConfrimActivity.this, "֧���ɹ�",
							Toast.LENGTH_SHORT).show();
				} else {
					// �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
					// ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(OrderConfrimActivity.this, "֧�����ȷ����",
								Toast.LENGTH_SHORT).show();

					} else {
						// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
						Toast.makeText(OrderConfrimActivity.this, "֧��ʧ��",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;
			}
			case 6: {
				Toast.makeText(OrderConfrimActivity.this, "�����Ϊ��" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			default:
				break;
			}
			super.dispatchMessage(msg);
		}
	};
	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	private long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	private PayReq genPayReq() {
		PayReq req = new PayReq();
		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = "wx20151010122027f46171b4d50841755203";
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());


		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		Log.e("orion", signParams.toString());
		return req;

	}
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);
        sb.append("sign str\n"+sb.toString()+"\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",appSign);
		return appSign;
	}
	/**
	 * sign the order info. �Զ�����Ϣ����ǩ��
	 * 
	 * @param content
	 *            ��ǩ��������Ϣ
	 */
	public String sign(String content) {
		return SignUtils.sign(content, Common.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. ��ȡǩ����ʽ
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * create the order info. ����������Ϣ
	 * 
	 */
	public String getOrderInfo(String subject, String body, String dingdan) {
		// ǩԼ���������ID
		String orderInfo = "partner=" + "\"" + Common.PARTNER + "\"";

		// ǩԼ����֧�����˺�
		orderInfo += "&seller_id=" + "\"" + Common.SELLER + "\"";

		// �̻���վΨһ������
		orderInfo += "&out_trade_no=" + "\"" + dingdan + "\"";

		// ��Ʒ����
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// ��Ʒ����
		orderInfo += "&body=" + "\"" + body + "\"";

		// ��Ʒ���
		orderInfo += "&total_fee=" + "\"" + endmoney + "\"";
		
		// �������첽֪ͨҳ��·��
//		orderInfo += "&notify_url=" + "\"" + RealmName.REALM_NAME
//				+ "/taobao/alipay_notify_url.aspx" + "\"";
			orderInfo += "&notify_url=" + "\"" +  "http://183.62.138.31:1636/taobao/alipay_notify_url.aspx" + "\"";
		// ����ӿ����ƣ� �̶�ֵ
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// ֧�����ͣ� �̶�ֵ
		orderInfo += "&payment_type=\"1\"";

		// �������룬 �̶�ֵ
		orderInfo += "&_input_charset=\"utf-8\"";

		// ����δ����׵ĳ�ʱʱ��
		// Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
		// ȡֵ��Χ��1m��15d��
		// m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
		// �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	private void ali_pay() {
		//
		String orderInfo = getOrderInfo("���̾�", "��Ʒ����", orderSerialNumber);

		// �Զ�����RSA ǩ��
		String sign = sign(orderInfo);
		try {
			// �����sign ��URL����
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// �����ķ���֧���������淶�Ķ�����Ϣ
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// ����PayTask ����
				PayTask alipay = new PayTask(OrderConfrimActivity.this);
				// ����֧���ӿڣ���ȡ֧�����
				String result = alipay.pay(payInfo);
				Message msg = new Message();
				msg.what = 5;
				msg.obj = result;
				handler.sendMessage(msg);
			}
		};

		// �����첽����
		Thread payThread = new Thread(payRunnable);
		payThread.start();
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
	/**
	 * �����µĹ��ﳵ
	 * @param carts
	 * @return
	 */
	private ArrayList<ShopCarts> getNew(ArrayList<ShopCarts> carts) {
		double count = 0;
		ArrayList<ShopCarts> newCarts = new ArrayList<ShopCarts>();
		for (int i = 0; i < carts.size(); i++) {
			ArrayList<ShopCartData> data = carts.get(i).getList();
			ShopCarts temp = new ShopCarts();
			ArrayList<ShopCartData> temp_list = new ArrayList<ShopCartData>();
			int len = data.size();
			for (int j = 0; j < len; j++) {
				if(data.get(j).isCheck){
					temp_list.add(data.get(j));
				}
			}
			if(temp_list.size()!=0){
				temp.setList(temp_list);
				temp.setName(carts.get(i).getName());
				newCarts.add(temp);
			}
			
		}
		 
		return newCarts;
	}
	
	public void setListViewHeightBasedOnChildren(ListView listView) {

		  ListAdapter listAdapter = listView.getAdapter();

		  if (listAdapter == null) {
		   return;
		  }

		  int totalHeight = 0;

		  for (int i = 0; i < listAdapter.getCount(); i++) {
		   View listItem = listAdapter.getView(i, null, listView);
		   listItem.measure(0, 0);
		   totalHeight += listItem.getMeasuredHeight();
		  }

		  ViewGroup.LayoutParams params = listView.getLayoutParams();

		  params.height = totalHeight
		    + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//		  ( (MarginLayoutParams)params).setMargins(10, 10, 10, 10); // ��ɾ��

		  listView.setLayoutParams(params);
		 }
	

	String type = "-1";
	String order_str;

	private void pay() {

		// TODO Auto-generated method stub

		if (checkedAddressId == 0) {
			Toast.makeText(getApplicationContext(), "�����Ʊ�Ҫ��Ϣ!", 200).show();
		} else {
			if (type.equals("-1")) {
				Toast.makeText(getApplicationContext(), "�����Ʊ�Ҫ��Ϣ!", 200).show();
			} else {
				order_str = RealmName.REALM_NAME
						+ "/mi/receiveOrderInfo_business.ashx?" + "imei="
						+ imei
						+ "&act=PhoneBuyProductInfo&bossUid=1&hengyucode="
						+ yth + "&pwd=" + pwd + "&buyPhone=" + phone
						+ "&paymentTypeId=3&consigneeAddressId="
						+ checkedAddressId + "&ProductOrderItemIds=" + orderid
						+ "&PayPassTickets=" + (endmoney==null?"0":endmoney)
						+ "&buyPhoneVerificationCode=&PayType=" + type
						+ "&ConsumptionCredits=" + jf;
				Log.v("data1", "ȥ�����ַ:" + order_str);
				yuePay();

			}

		}

	}
	/**
	 * ���֧��
	 */
	private String partner_id,prepayid,noncestr,timestamp,package_,sign;
	private void yuePay(){
		AsyncHttp.get(order_str, new AsyncHttpResponseHandler() {
			@Override
			public void onStart() {
				super.onStart();
				progress.CreateProgress();
			}

			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				progress.CloseProgress();
				System.out.println(arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					int status = object.getInt("status");
					if (status == 1) {
						if (TextUtils.equals("1", type)) {
							username = object.getString("buyUserName");
							PassTicket = object.getString("PassTicket");
							shopPassTicket = object
									.getString("shopPassTicket");
							orderSerialNumber = object
									.getString("orderSerialNumber");
							trade_no = object.getString("trade_no");

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
											+ ParseBank.paseName(item
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
						} else if (TextUtils.equals("2", type)) {
							// �����Ǵ���֧��������
							orderSerialNumber = object
									.getString("orderSerialNumber");
						}else if (TextUtils.equals("5", type)) {
							//΢�Ŵ���
//							  appid = object.getString("appid");
							  partner_id = object.getString("mch_id");
							  prepayid = object.getString("prepay_id"); 
							  noncestr= object.getString("nonce_str");
							  timestamp= object.getString("timeStamp");
							  package_="Sign=WXPay";
							  sign= object.getString("sign");
						} else {
							username = object.getString("buyUserName");
							PassTicket = object.getString("PassTicket");
							shopPassTicket = object
									.getString("shopPassTicket");
							orderSerialNumber = object
									.getString("orderSerialNumber");
							Log.v("data2", "������ˮ��:" + orderSerialNumber);
						}
						Message message = new Message();
						message.what = 2;
						handler.sendMessage(message);
					} else {
						String msg = object.getString("msg");
						Message message = new Message();
						message.what = 3;
						message.obj = msg;
						handler.sendMessage(message);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, getApplicationContext());
	}
	OnCancelListener cancelListener = new OnCancelListener() {

		@Override
		public void onCancel(DialogInterface dialog) {
			/*
			 * http://www.ju918.com/mi/getdata.ashx?act=UserCartInfo&appkey=0762222540&key=QUPgWi93j719&sign=AAE3474591B6B22950AD09A11082D4D751DDABC9&yth=112967999
			 */
		}
	};
	private Button confrim_btn;
	private TextView heji;
	
	private void innidada() {
		confrim_btn = (Button) findViewById(R.id.confrim_btn);
		heji = (TextView) findViewById(R.id.heji);
		if(carts==null){
			heji.setText("�ϼ�:"+getIntent().getStringExtra("retailPrice"));
		}else {
			 
			
			heji.setText("�ϼ�:"+countTotal(carts));
		}
		if(getIntent().hasExtra("warename")){
			//��ʾ��������
			ArrayList<ShopCarts> newcaCarts = new ArrayList<ShopCarts>();
			ShopCarts c = new ShopCarts();
			c.setName("���ֹ�");
			ArrayList<ShopCartData> da = new ArrayList<ShopCartData>();
			ShopCartData d = new ShopCartData();
			d.imgurl = getIntent().getStringExtra("imgurl");
			d.number = Integer.parseInt(getIntent().getStringExtra("number"));
			d.retailprice = getIntent().getStringExtra("retailPrice");
			d.marketprice = getIntent().getStringExtra("marketPrice");
			d.warename = getIntent().getStringExtra("warename");
			heji.setText("�ϼ�:"+Double.parseDouble(d.retailprice)*d.number);
			endmoney  =String.valueOf(Double.parseDouble(d.retailprice)*d.number);
//					intent.putExtra("imgurl", proFaceImg);
//			intent.putExtra("number", "1");
//			intent.putExtra("retailprice", retailPrice);
//			intent.putExtra("warename", proName);
			da.add(d);
			c.setList(da);
			newcaCarts.add(c);
			list_shop_cart.setAdapter(new MyShopCartAdapter(newcaCarts, getApplicationContext(), null, imageLoader));
		}
		list_address_a = (ListView) findViewById(R.id.list_address_a);
		btn_add_address = (ImageButton) findViewById(R.id.img_btn_add_address);
		btn_add_address.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int index = 0;
				Intent intent = new Intent(OrderConfrimActivity.this,
						AddUserAddressActivity.class);
				intent.putExtra("index", index);
				startActivityForResult(intent, 0);
			}
		});
		confrim_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				CommomConfrim.showSheet(OrderConfrimActivity.this, new onDeleteSelect() {

					@Override
					public void onClick(int resID) {
						// TODO Auto-generated method stub
						switch (resID) {
						case R.id.item0:
							//���֧��
							type = "0";
							if(getIntent().hasExtra("isNow")){
								 jf = getIntent().getIntExtra("AvailableIntegral", 0);
								String url   = RealmName.REALM_NAME
										+ "/mi/receiveOrderInfo.ashx?"
										+ "act=InsertOneProductOrderItem&yth="
										+ yth
										+ "&productItemId="
										+ getIntent().getStringExtra("Id")
										+ "&productCount="+getIntent().getStringExtra("number")
										+ "&oneProductPrice=" + getIntent().getStringExtra("retailPrice")
										+ "&totalProductPrice="
										+ getIntent().getStringExtra("totalPrice")
										+ "&sellPropertyName1="
										+ getIntent().getStringExtra("stylename1")
										+ "&sellPropertyValue1="
										+ getIntent().getStringExtra("stylenature1")
										+ "&sellPropertyName2="
										+ getIntent().getStringExtra("stylename2")
										+ "&sellPropertyValue2="
										+ getIntent().getStringExtra("stylenature2")
										+ "&productItemType="
										+ type;
								AsyncHttp.get(url, new AsyncHttpResponseHandler(){
									@Override
									public void onSuccess(String arg0) {
										// TODO Auto-generated method stub
										super.onSuccess(arg0);
										try {
											JSONObject object = new JSONObject(arg0);
											String status = object.getString("status");
											if(status.equals("1")){
												Message msg  = new Message();
												msg.what = -1;
												msg.obj = object.getString("ProductOrderItemId");
												handler.sendMessage(msg);
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
										
									}
								}, getApplicationContext());
							}else {
							
								pay();
							}
							
							break;
						case R.id.item1:
							type = "1";
							if(getIntent().hasExtra("isNow")){
								 jf = getIntent().getIntExtra("AvailableIntegral", 0);
								String url   = RealmName.REALM_NAME
										+ "/mi/receiveOrderInfo.ashx?"
										+ "act=InsertOneProductOrderItem&yth="
										+ yth
										+ "&productItemId="
										+ getIntent().getStringExtra("Id")
										+ "&productCount="+getIntent().getStringExtra("number")
										+ "&oneProductPrice=" + getIntent().getStringExtra("retailPrice")
										+ "&totalProductPrice="
										+ getIntent().getStringExtra("totalPrice")
										+ "&sellPropertyName1="
										+ getIntent().getStringExtra("stylename1")
										+ "&sellPropertyValue1="
										+ getIntent().getStringExtra("stylenature1")
										+ "&sellPropertyName2="
										+ getIntent().getStringExtra("stylename2")
										+ "&sellPropertyValue2="
										+ getIntent().getStringExtra("stylenature2")
										+ "&productItemType="
										+ type;
								AsyncHttp.get(url, new AsyncHttpResponseHandler(){
									@Override
									public void onSuccess(String arg0) {
										// TODO Auto-generated method stub
										super.onSuccess(arg0);
										try {
											JSONObject object = new JSONObject(arg0);
											String status = object.getString("status");
											if(status.equals("1")){
												Message msg  = new Message();
												msg.what = -1;
												msg.obj = object.getString("ProductOrderItemId");
												handler.sendMessage(msg);
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
										
									}
								}, getApplicationContext());
							}else {
							
								pay();
							}
							break;
						case R.id.item2:
							type = "2";
							if(getIntent().hasExtra("isNow")){
								 jf = getIntent().getIntExtra("AvailableIntegral", 0);
								String url   = RealmName.REALM_NAME
										+ "/mi/receiveOrderInfo.ashx?"
										+ "act=InsertOneProductOrderItem&yth="
										+ yth
										+ "&productItemId="
										+ getIntent().getStringExtra("Id")
										+ "&productCount="+getIntent().getStringExtra("number")
										+ "&oneProductPrice=" + getIntent().getStringExtra("retailPrice")
										+ "&totalProductPrice="
										+ getIntent().getStringExtra("totalPrice")
										+ "&sellPropertyName1="
										+ getIntent().getStringExtra("stylename1")
										+ "&sellPropertyValue1="
										+ getIntent().getStringExtra("stylenature1")
										+ "&sellPropertyName2="
										+ getIntent().getStringExtra("stylename2")
										+ "&sellPropertyValue2="
										+ getIntent().getStringExtra("stylenature2")
										+ "&productItemType="
										+ type;
								AsyncHttp.get(url, new AsyncHttpResponseHandler(){
									@Override
									public void onSuccess(String arg0) {
										// TODO Auto-generated method stub
										super.onSuccess(arg0);
										try {
											JSONObject object = new JSONObject(arg0);
											String status = object.getString("status");
											if(status.equals("1")){
												Message msg  = new Message();
												msg.what = -1;
												msg.obj = object.getString("ProductOrderItemId");
												handler.sendMessage(msg);
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
										
									}
								}, getApplicationContext());
							}else {
							
								pay();
							}
							break;
						case R.id.item3:
							type = "5";
							if(getIntent().hasExtra("isNow")){
								 jf = getIntent().getIntExtra("AvailableIntegral", 0);
								String url   = RealmName.REALM_NAME
										+ "/mi/receiveOrderInfo.ashx?"
										+ "act=InsertOneProductOrderItem&yth="
										+ yth
										+ "&productItemId="
										+ getIntent().getStringExtra("Id")
										+ "&productCount="+getIntent().getStringExtra("number")
										+ "&oneProductPrice=" + getIntent().getStringExtra("retailPrice")
										+ "&totalProductPrice="
										+ getIntent().getStringExtra("totalPrice")
										+ "&sellPropertyName1="
										+ getIntent().getStringExtra("stylename1")
										+ "&sellPropertyValue1="
										+ getIntent().getStringExtra("stylenature1")
										+ "&sellPropertyName2="
										+ getIntent().getStringExtra("stylename2")
										+ "&sellPropertyValue2="
										+ getIntent().getStringExtra("stylenature2")
										+ "&productItemType="
										+ type;
								AsyncHttp.get(url, new AsyncHttpResponseHandler(){
									@Override
									public void onSuccess(String arg0) {
										// TODO Auto-generated method stub
										super.onSuccess(arg0);
										try {
											JSONObject object = new JSONObject(arg0);
											String status = object.getString("status");
											if(status.equals("1")){
												Message msg  = new Message();
												msg.what = -1;
												msg.obj = object.getString("ProductOrderItemId");
												handler.sendMessage(msg);
											}
										} catch (JSONException e) {
											e.printStackTrace();
										}
										
									}
								}, getApplicationContext());
							}else {
							
								pay();
							}
							
							break;
						case R.id.item4:
							
							break;
						default:
							break;
						}
					}
					
					 
				}, cancelListener, null);
			}
		});
		

		list_address_a
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0,
							View arg1, int arg2, long arg3) {
						addressid = list.get(arg2).consigneeAddressId;
						Log.v("data1", "addressid:" + addressid + "  listitem:"
								+ arg2);

						new AlertDialog.Builder(OrderConfrimActivity.this)
								.setTitle("�༭��ַ")
								.setItems(R.array.select_dialog_items,
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												switch (arg1) {
												case 0:
													dialog();
													Toast.makeText(
															getApplicationContext(),
															addressid + "", 200)
															.show();
													break;

												default:
													break;
												}
											}
										}).show();

						return false;
					}
				});
	}

	private void parse(String st) {
		try {
			JSONObject jsonObject = new JSONObject(st);
			JSONArray jsonArray = jsonObject.getJSONArray("items");
			ArrayList<UserAddressData> list = new ArrayList<UserAddressData>();
			for (int i = 0; i < jsonArray.length(); i++) {
				UserAddressData data = new UserAddressData();
				JSONObject object = jsonArray.getJSONObject(i);
				data.consigneeUserName = object.getString("consigneeUserName");
				data.consigneeAddressInfo = object
						.getString("consigneeAddressInfo");
				data.consigneeAddressId = object.getInt("consigneeAddressId");
				data.phone = object.getString("consigneeMobileTel");
				list.add(data);
				Log.v("data1", data.consigneeAddressId + "");
			}
			Message message = new Message();
			message.what = 0;
			message.obj = list;
			handler.sendMessage(message);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * ���ҵ�ַ�б�
	 */
	private void DOparse() {
		try {
			
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "receiver");
		params.put("yth", yth);
		params.put("key", key);
//		String user_name = UserLoginActivity.user_name;
		strUrl = RealmName.REALM_NAME_LL + "/get_user_shopping_address_default?user_name=13714758507";
		Log.v("data1", strUrl);
		/*
		 * new Thread() {
		 * 
		 * @Override public void run() { super.run(); try { Map<String, String>
		 * map = new HashMap<String, String>(); InputStream ip =
		 * HttpClientUtil.postRequest(strUrl, map); String st = new
		 * HttpUtils().convertStreamToString(ip, "utf-8").trim();
		 * 
		 * System.out.println(st); } catch (Exception e) { e.printStackTrace();
		 * } } }.start();
		 */

//		AsyncHttp.post_1(strUrl, params, new AsyncHttpResponseHandler() {
			AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				System.out.println("============11"+arg1);
				super.onSuccess(arg0, arg1);
				parse(arg1);
			}
		}, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("ȷ��ɾ�������ַ��");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try {
					getJsonString(null);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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
	 * ɾ����ַ
	 * 
	 * @param urlPath
	 * @throws Exception
	 */
	private void getJsonString(String urlPath) throws Exception {
		String deleteUrl = RealmName.REALM_NAME + "/mi/getdata.ashx";
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "delreceiver");
		params.put("yth", yth);
		params.put("key", key);
		params.put("id", addressid + "");

		AsyncHttp.post_1(deleteUrl, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				handler.sendEmptyMessage(4);
			}
		});

	}

	 

	 
}
