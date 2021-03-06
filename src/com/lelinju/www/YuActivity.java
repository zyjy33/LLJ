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
					//点击设置是否为点击状态
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
						Toast.makeText(getApplicationContext(), "请选择支付方式",200).show();
					}
					
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "请输入正确的金额", 200).show();
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
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, Common.RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String dingdan) {
		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + Common.PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + Common.SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + dingdan + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + chongzhi_edit.getText().toString() + "\"";
		
		// 服务器异步通知页面路径
//		orderInfo += "&notify_url=" + "\"" + RealmName.REALM_NAME
//				+ "/taobao/alipay_notify_url.aspx" + "\"";
			orderInfo += "&notify_url=" + "\"" +  "http://183.62.138.31:1636/taobao/alipay_notify_url.aspx" + "\"";
		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";
		System.out.println(orderInfo);
		return orderInfo;
	}
	private void ali_pay() {
		//
		String orderInfo = getOrderInfo("充值", "商品描述", orderSerialNumber);

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		// 完整的符合支付宝参数规范的订单信息
		final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
				+ getSignType();

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask alipay = new PayTask(YuActivity.this);
				// 调用支付接口，获取支付结果
				String result = alipay.pay(payInfo);
				Message msg = new Message();
				msg.what = 5;
				msg.obj = result;
				handler.sendMessage(msg);
			}
		};

		// 必须异步调用
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
						// 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
						api.registerApp(Constants.APP_ID);
						boolean flag = api.sendReq(req);
						System.out.println("支付"+flag);
				}else {
					    
				}
				
				break;
			case 5:
				PayResult payResult = new PayResult((String) msg.obj);

				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();

				String resultStatus = payResult.getResultStatus();
				System.out.println(resultInfo + "---" + resultStatus);
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					Toast.makeText(YuActivity.this, "支付成功",
							Toast.LENGTH_SHORT).show();
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(YuActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();

					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(YuActivity.this, "支付失败",
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
