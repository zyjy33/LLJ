package com.hengyushop.demo.my;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.android.pliay.SignUtils;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.db.SharedUtils;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.at.Common;
import com.hengyushop.demo.home.YangLaoChongZhiActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.lelinju.www.R;
/**
 * 
 * ����ֵ
 * @author Administrator
 *
 */
public class MyQianBaoActivity extends BaseActivity implements OnClickListener{
	private Button chongzhi_submit;
	private TextView tv_ticket;
	private SharedPreferences spPreferences;
	String user_name,user_id,pwd;
	public static String recharge_no,notify_url,return_url;
	private ImageView iv_fanhui;
	private DialogProgress progress;
	LinearLayout yu_pay0;
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		userloginqm();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qianbao_chongzhi);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		progress = new DialogProgress(this);
//		yth = registerData.getHengyuCode();
//		key = registerData.getUserkey();
		
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		pwd = spPreferences.getString("pwd", "");
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(this);
		

		tv_ticket = (TextView) findViewById(R.id.tv_monney);
//		yfje_edit = (TextView) findViewById(R.id.yfje_edit);
		chongzhi_submit = (Button) findViewById(R.id.chongzhi_submit);
		yu_pay0 =  (LinearLayout) findViewById(R.id.yu_pay0);
		yu_pay0.setBackgroundResource(R.drawable.my_qianbao);
		chongzhi_submit.setOnClickListener(this);
		
//		userloginqm();
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.iv_fanhui:
			finish();
			break;
		case R.id.chongzhi_submit:
			Intent intent = new Intent(MyQianBaoActivity.this,MonneyChongZhiActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
	
	/**
	 * ��ȡ��¼ǩ��
	 */
	private void userloginqm() {
		try{
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						JSONObject obj = object.getJSONObject("data");
						if (status.equals("y")) {
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							String amount = obj.getString("amount");
							tv_ticket.setText(amount);
						}else{
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, MyQianBaoActivity.this);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
