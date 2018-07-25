package com.lelinju.www;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.json.JSONException;
import org.json.JSONObject;

import com.alipay.sdk.app.PayTask;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.android.pliay.SignUtils;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.SharedUtils;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.lelinju.www.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class YuActivity extends BaseActivity{
	private Button chongzhi_submit;
	private EditText chongzhi_edit;
	private LinearLayout yu_pay0,yu_pay1;
	private CheckBox yu_pay_c0,yu_pay_c1;
	private IWXAPI api;
	private SharedUtils in ;
	private WareDao wareDao;
	private String yth;
	private String key;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yu_chongzhi);
		api = WXAPIFactory.createWXAPI(YuActivity.this,null);
		api.registerApp(Constants.APP_ID);
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		key = registerData.getUserkey();
		in = new SharedUtils(getApplicationContext(), "shouyi");
		chongzhi_edit = (EditText) findViewById(R.id.chongzhi_edit);
		chongzhi_submit = (Button) findViewById(R.id.chongzhi_submit);
		yu_pay0 =  (LinearLayout) findViewById(R.id.yu_pay0);
		yu_pay1 = (LinearLayout) findViewById(R.id.yu_pay1);
		yu_pay_c0 = (CheckBox) findViewById(R.id.yu_pay_c0);
		yu_pay_c1 = (CheckBox) findViewById(R.id.yu_pay_c1);
		yu_pay0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c1.isChecked()){
					yu_pay_c1.setChecked(false);
				}
				yu_pay_c0.setChecked(true);
			}
		});
		yu_pay_c0.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c1.isChecked()){
					//��������Ƿ�Ϊ���״̬
					yu_pay_c1.setChecked(false);
				}
				yu_pay_c0.setChecked(true);
			}
		});
		yu_pay_c1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c0.isChecked()){
					yu_pay_c0.setChecked(false);
				}
				yu_pay_c1.setChecked(true);
			}
		});
		yu_pay1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(yu_pay_c0.isChecked()){
					yu_pay_c0.setChecked(false);
				}
				yu_pay_c1.setChecked(true);
			}
		});
		chongzhi_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String yue = chongzhi_edit.getText().toString();
				try {
					Double.parseDouble(yue);
					if(yu_pay_c0.isChecked()){
						
						processWX(yue);
						
						
					}else if (yu_pay_c1.isChecked()) {
						process(yue);
					}else {
						Toast.makeText(getApplicationContext(), "��ѡ��֧����ʽ",200).show();
					}
					
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "��������ȷ�Ľ��", 200).show();
				}
				
				
			}
		});
	}
	String orderSerialNumber;
	private void process(String yu){
		RequestParams params = new RequestParams();
		params.put("act", "BuyPassTicket_Mobile");
		params.put("PassTicketBuy", yu);
		params.put("bossUid", in.getStringValue("ChannelUserID"));
		params.put("BuyType", "1");
		params.put("yth", yth);
		
		AsyncHttp.post(RealmName.REALM_NAME+"/mi/receiveOrderInfo_business.ashx", params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					orderSerialNumber = object.getString("orderSerialNumber");
					handler.sendEmptyMessage(1);
					System.out.println(orderSerialNumber);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		},getApplicationContext());
	}
	private String partner_id,prepayid,noncestr,timestamp,package_,sign;
	private void processWX(String yu){
		
		
		
		RequestParams params = new RequestParams();
		params.put("act", "BuyPassTicket_Mobile");
		params.put("PassTicketBuy", yu);
		params.put("bossUid", in.getStringValue("ChannelUserID"));
		params.put("BuyType", "2");
		params.put("yth", yth);
		
		AsyncHttp.post(RealmName.REALM_NAME+"/mi/receiveOrderInfo_business.ashx", params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub

				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					if(status.equals("1")){
						  partner_id = jsonObject.getString("mch_id");
						  prepayid = jsonObject.getString("prepay_id"); 
						  noncestr= jsonObject.getString("nonce_str");
						  timestamp= jsonObject.getString("timeStamp");
						  package_="Sign=WXPay";
						  sign= jsonObject.getString("sign");
						  handler.sendEmptyMessage(2);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		},getApplicationContext());
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
		orderInfo += "&total_fee=" + "\"" + chongzhi_edit.getText().toString() + "\"";
		
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
		System.out.println(orderInfo);
		return orderInfo;
	}
	private void ali_pay() {
		//
		String orderInfo = getOrderInfo("��ֵ", "��Ʒ����", orderSerialNumber);

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
				PayTask alipay = new PayTask(YuActivity.this);
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
	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 1:
				ali_pay();
				break;
			case 2:
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
				
				break;
			case 5:
				PayResult payResult = new PayResult((String) msg.obj);

				// ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();
				System.out.println(resultInfo + "---" + resultStatus);
				// �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(YuActivity.this, "֧���ɹ�",
							Toast.LENGTH_SHORT).show();
				} else {
					// �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
					// ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(YuActivity.this, "֧�����ȷ����",
								Toast.LENGTH_SHORT).show();

					} else {
						// ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
						Toast.makeText(YuActivity.this, "֧��ʧ��",
								Toast.LENGTH_SHORT).show();

					}
				}
				break;

			default:
				break;
			}
		};
	};
}
