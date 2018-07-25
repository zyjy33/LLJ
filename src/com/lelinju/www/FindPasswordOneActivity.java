package com.lelinju.www;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 找回密码
 * @author Administrator
 *
 */
public class FindPasswordOneActivity extends BaseActivity {
	private EditText et_user_phone,et_user_yz,et_user_pwd,et_useryanshi_pwd;
	private Button get_yz,btn_cz;
	private String yanzhengma;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
		setContentView(R.layout.find_password);
		init();
	}
	private void init(){
		
		et_user_phone = (EditText) findViewById(R.id.et_user_phone);
		et_user_yz = (EditText) findViewById(R.id.et_user_yz);
		et_user_pwd = (EditText) findViewById(R.id.et_user_pwd);
		et_useryanshi_pwd = (EditText) findViewById(R.id.et_useryanshi_pwd);
		//et_user_pwd_again = (EditText) findViewById(R.id.et_user_pwd_again);
		get_yz = (Button) findViewById(R.id.get_yz);
		btn_cz = (Button) findViewById(R.id.btn_cz);
		get_yz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String phone = et_user_phone.getText().toString();
					AsyncHttp.get("http://www.hengyushop.com/mi/SmsAndMms.ashx?mobile="+phone+"&companyName=云商聚&frontContents=您正在重置密码，请在15分钟内按页面提示提交验证码，切勿将验证码泄露于他人。&RequestType=", new AsyncHttpResponseHandler(){
				
				@Override
						public void onSuccess(int arg0, String arg1) {
							super.onSuccess(arg0, arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								Toast.makeText(getApplicationContext(), jsonObject.getString("msg"), 200).show();
								 
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					}, getApplicationContext());
//				if (phone != null && phone.length() == 11) {
//					String strUrl = RealmName.REALM_NAME_LL+"/user_verify_smscode?mobile="+phone+"";
//							
//					AsyncHttp.get(strUrl,
//							new AsyncHttpResponseHandler() {
//								@Override
//								public void onSuccess(int arg0, String arg1) {
//									super.onSuccess(arg0, arg1);
//									System.out.println("============="+arg1);
//									try {
//										JSONObject object = new JSONObject(arg1);
//										String result = object.getString("status");//
//										String info = object.getString("info");//info
//										if (result.equals("y")) {
//											yanzhengma = object.getString("data");
//											
//											Toast.makeText(FindPasswordOneActivity.this, info, 200).show();
//										} else {
//										    Toast.makeText(FindPasswordOneActivity.this, info, 200).show();
////											handler.sendEmptyMessage(3);
//										}
//									} catch (JSONException e) {
//										e.printStackTrace();
//									}
//								}
//							}, getApplicationContext());
//				} else {
////					NewDataToast.makeText(getApplicationContext(), "请输入手机号码",false,0).show();
//					Toast.makeText(FindPasswordOneActivity.this, "手机号码不能为空", 200).show();
//				}
			}
		});
		
		btn_cz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String phone = et_user_phone.getText().toString();
				String yz = et_user_yz.getText().toString();
				// TODO Auto-generated method stub
				String pass1 = et_user_pwd.getText().toString();//输入新密码
				String ys_pass = et_useryanshi_pwd.getText().toString();//输入原始密码
				if(pass1.equals("")){
					Toast.makeText(getApplicationContext(), "密码为空", 200).show();
				}else if(phone.equals("")){
					Toast.makeText(getApplicationContext(), "手机号码为空", 200).show();
				}else if(ys_pass.equals("")){
					Toast.makeText(getApplicationContext(), "原始密码为空", 200).show();
				}else{
					Map<String, String> params= new HashMap<String, String>();
					params.put("username", phone);
					params.put("password", pass1);
					params.put("type", "oldpassword");
					params.put("code", pass1);
//					AsyncHttp.get(RealmName.REALM_NAME_LL+"/mobile_update_password?username="+phone+"&password="+pass1+"&type=oldpassword&code="+ys_pass+"",
					AsyncHttp.get(RealmName.REALM_NAME_LL+"/mobile_update_password?user_name="+phone+"&mobile="+phone+"&code="+ys_pass+"&newpassword="+pass1+"&type=paypassword",
							new AsyncHttpResponseHandler() {
					public void onSuccess(int arg0, String arg1) {
							System.out.println("================="+arg1);
							try {
								JSONObject jsonObject  = new JSONObject(arg1);
								String info = jsonObject.getString("info");
//								Toast.makeText(getApplicationContext(), "修改成功", 200).show();
								Toast.makeText(getApplicationContext(), info, 200).show();
//								if(jsonObject.getInt("status")==1){
//									AppManager.getAppManager().finishActivity();
//								}
								finish();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						};
					}, null );
				}
			}
		});
		
//		mi/SmsAndMms.ashx?mobile=15889698754&companyName=微行掌&frontContents=前缀内容&RequestType=1
	
	
	}
	 

}
