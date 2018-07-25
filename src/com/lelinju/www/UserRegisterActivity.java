package com.lelinju.www;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.ctrip.openapi.java.utils.Validator;
import com.hengyushop.dao.CityDao;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.CityData;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

public class UserRegisterActivity extends BaseActivity implements
		OnClickListener {

	private Button img_title_login;
	private EditText userpwd, userphone, et_user_yz;
//	userpostbox, userpwd, userpwdagain,
	private Button btn_register, get_yz;
	private String name, phone, postbox, pwd, pwdagain, insertdata, yz,shoujihao;
	private UserRegisterData data;
	private WareDao wareDao;
	private int isEnable = 1;
	private int isLogin = 0;
	private String str, hengyuName;
	private DialogProgress progress;
	private String strUrl;
	private MyPopupWindowMenu popupWindowMenu;
	private TextView regise_tip;
	private RelativeLayout lay;
	private ArrayAdapter aa_sheng, aa_shi, aa_area;
	private String yanzhengma;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_zhuce);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		// getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
		// R.layout.title_register);
		initdata();

		
		ImageView img_menu = (ImageView) findViewById(R.id.img_menu);
		img_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
	}
	
	Handler handler = new Handler() {

		public void dispatchMessage(android.os.Message msg) {

			switch (msg.what) {
			case 0:
//				et_user_yz.setText("");
//				username.setText("");
//				userphone.setText("");
//				userpostbox.setText("");
//				userpwd.setText("");
//				userpwdagain.setText("");
				String strhengyuname = (String) msg.obj;
				NewDataToast.makeText(getApplicationContext(), strhengyuname,false, 0).show();
				progress.CloseProgress();
//				Intent intent = new Intent(UserRegisterActivity.this, UserLoginActivity.class);
//				startActivity(intent);
				finish();
				break;
			case 1:
				String strmsg = (String) msg.obj;
				NewDataToast.makeText(getApplicationContext(), strmsg,false, 0).show();
				break;
			case 2:
				NewDataToast.makeText(getApplicationContext(), "验证码已发送",false, 0).show();
				new Thread() {
					public void run() {
						for (int i = 120; i >= 0; i--) {
							if (i == 0) {
								handler.sendEmptyMessage(4);
							} else {
								Message msg = new Message();
								msg.arg1 = i;
								msg.what=5;
								handler.sendMessage(msg);

								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
						}
					};
				}.start();
				break;
			case 3:
				
				NewDataToast.makeText(getApplicationContext(), "验证码已下发",false, 0).show();
				break;
			case 4:
				get_yz.setEnabled(true);
				get_yz.setText("获取验证码");
				break;
			case 5:
				get_yz.setEnabled(false);
				get_yz.setText(msg.arg1 + "s");
				break;
			default:
				break;
			}
		};

	};
	
	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
//	private Spinner sp_sheng;
//	private Spinner sp_shi;
//	private Spinner sp_xian;
	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.register_tip, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//必须设置background才能消失
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.ban_louming));
		mPopupWindow.setOutsideTouchable(true);
		// 自定义动画
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// 使用系统动画
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		final TextView lglottery_pop_closed = (TextView) popView
				.findViewById(R.id.register_tip);
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "GetwxzRegForIOS");
		params.put("yth", "test");
		
		AsyncHttp.post_1(RealmName.REALM_NAME+"/mi/getdata.ashx", params,new AsyncHttpResponseHandler(){
//		AsyncHttp.post_1(RealmName.REALM_NAME_LL+"/user_invite_code", params,new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					JSONObject object = new JSONObject(arg1);
					lglottery_pop_closed.setText(object.getString("msg"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAsDropDown(lay) ;
		}
	}
	
	public  float[] bubbleSort(float[] args){//冒泡排序算法
        for(int i=0;i<args.length-1;i++){
                for(int j=i+1;j<args.length;j++){
                        if (args[i]<args[j]){
                        	float temp=args[i];
                                args[i]=args[j];
                                args[j]=temp;
                        }
                }
        }
        return args;
}
	
	private void initdata() {
		try {
			LinearLayout ll_buju = (LinearLayout) findViewById(R.id.ll_buju);
			ll_buju.setBackgroundResource(R.drawable.denglu_beijing);	
		lay = (RelativeLayout) findViewById(R.id.lay);
		regise_tip = (TextView) findViewById(R.id.regise_tip);
		et_user_yz = (EditText) findViewById(R.id.et_user_yz);
		get_yz = (Button) findViewById(R.id.get_yz);
//		img_title_login = (Button) findViewById(R.id.img_title_login);
//		username = (EditText) findViewById(R.id.et_user_name);
		userphone = (EditText) findViewById(R.id.et_user_phone);
//		userpostbox = (EditText) findViewById(R.id.et_user_postbox);
		userpwd = (EditText) findViewById(R.id.et_user_pwd);
//		userpwdagain = (EditText) findViewById(R.id.et_user_pwd_again);
		btn_register = (Button) findViewById(R.id.btn_register);
//		img_title_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		get_yz.setOnClickListener(this);
//		regise_tip.setText(Html.fromHtml(" <u>用户协议</u> "));
		regise_tip.setOnClickListener(this);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		try {
			
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.regise_tip:
//			initPopupWindow();
//			showPopupWindow(regise_tip);
			Intent intent4 = new Intent(UserRegisterActivity.this,Webview1.class);
			intent4.putExtra("userxy", "5997");
			startActivity(intent4);
			
			break;
		case R.id.get_yz:
			phone = userphone.getText().toString().trim();
			if (phone.equals("")) {
				Toast.makeText(UserRegisterActivity.this, "手机号码不能为空", 200).show();
//			}else if (phone.length() < 11 ) {
//				Toast.makeText(UserRegisterActivity.this, "手机号码少于11位", 200).show();
			}else {
			if(Validator.isMobile(phone)){
//				showToast("验证手机号成功!");
				phone = userphone.getText().toString().trim();//user_verify_smscode
//				if (phone != null && phone.length() == 11) {
					strUrl = RealmName.REALM_NAME_LL+"/user_verify_smscode?mobile="+phone+"";
//					AsyncHttp.get( "http://www.hengyushop.com/mi/SmsAndMms.ashx?companyName=云商聚&mobile=" + phone+"&RequesDomain=ehaoyy",
							
					AsyncHttp.get(strUrl,
							new AsyncHttpResponseHandler() {
								@Override
								public void onSuccess(int arg0, String arg1) {
									super.onSuccess(arg0, arg1);
									System.out.println("============="+arg1);
									try {
										JSONObject object = new JSONObject(arg1);
										String result = object.getString("status");//
										String info = object.getString("info");//info
										if (result.equals("y")) {
											yanzhengma = object.getString("data");
											
//											Toast.makeText(UserRegisterActivity.this, info, 200).show();
											handler.sendEmptyMessage(2);
										} else {
										    Toast.makeText(UserRegisterActivity.this, info, 200).show();
//											handler.sendEmptyMessage(3);
										}
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
							}, getApplicationContext());
//				} else {
////					NewDataToast.makeText(getApplicationContext(), "请输入手机号码",false,0).show();
//					Toast.makeText(UserRegisterActivity.this, "手机号码不能为空", 200).show();
//				}
			}else{
				showToast("验证手机号失败!");
			}
		    }
			break;
		case R.id.img_title_login:
			int index = 0;
			Intent intent = new Intent(UserRegisterActivity.this,
					UserLoginActivity.class);
			intent.putExtra("login", index);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_register:
			yz = et_user_yz.getText().toString().trim();
//			name = username.getText().toString().trim();
			phone = userphone.getText().toString().trim();
//			postbox = userpostbox.getText().toString().trim();
			pwd = userpwd.getText().toString().trim();
//			pwdagain = userpwdagain.getText().toString().trim();
			// SimpleDateFormat formatter = new SimpleDateFormat(
			// "yyyy年MM月dd日   HH:mm:ss");
			// Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
			// insertdata = formatter.format(curDate);

			System.out.println("1=================="+yz);
			System.out.println("2=================="+yanzhengma);
			if (phone.equals("")) {
				Toast.makeText(UserRegisterActivity.this, "手机号码不能为空", 200).show();
			}else if (phone.length() < 11 ) {
				Toast.makeText(UserRegisterActivity.this, "手机号码少于11位", 200).show();
			}
			else if (yz.equals("")) {
				Toast.makeText(UserRegisterActivity.this, "验证码不能为空", 200).show();
			}
//			else if (!yz.equals(yanzhengma)) {
//				Toast.makeText(UserRegisterActivity.this, "验证码不正确", 200).show();
//			}
//			else if (yz.length() < 6 ) {
//				Toast.makeText(UserRegisterActivity.this, "验证码少于六位", 200).show();
//			}
			else if (pwd.equals("")) {
				Toast.makeText(UserRegisterActivity.this, "密码不能为空", 200).show();
			} else if (pwd.length()<8) {
				Toast.makeText(UserRegisterActivity.this, "密码不得小于8位", 200).show();
			} else if (!(userpwd.getText().toString().length()<20&&userpwd.getText().toString().length()>=8)) {
				Toast.makeText(UserRegisterActivity.this, "密码在8-20位之间", 200).show();
			}else {
				try {
					progress = new DialogProgress(UserRegisterActivity.this);
					progress.CreateProgress();
					new Thread() {
						public void run() {
							try {
//								System.out.println("=================11==" + phone);
//								System.out.println("=================12==" + pwd);
//								System.out.println("=================13==" + postbox);
//								System.out.println("=================14==" + shoujihao);//"+123488+"
								
								strUrl = RealmName.REALM_NAME_LL+"/user_register?site=mobile&code="+yz+"&username="+phone+
										 "&password="+pwd+"&mobile="+phone+"";
								System.out.println("注册" + strUrl);
								
								AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(){
									@Override
									public void onSuccess(int arg0, String arg1) {
										// TODO Auto-generated method stub
										super.onSuccess(arg0, arg1);
										try {
											JSONObject jsonObject = new JSONObject(arg1);
											System.out.println("=================1==" + arg1);
											String status = jsonObject.getString("status");
											String info = jsonObject.getString("info");
											if (status.equals("n")) {
												System.out.println("=================2==");
												str = jsonObject.getString("info");
												String no = jsonObject.getString("info");
////											str = jsonObject.getString("info");
//											    NewDataToast.makeText(getApplicationContext(), no,false, 0).show();
												progress.CloseProgress();
												Message message = new Message();
												message.what = 1;
												message.obj = str;
												handler.sendMessage(message);
											} else if (status.equals("y")){
												try {
												System.out.println("=================3=="+info);
												hengyuName = jsonObject.getString("info");
//												NewDataToast.makeText(getApplicationContext(), info,false, 0).show();
												System.out.println("=================4==");
												Log.v("data1", hengyuName + "");
												
												SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
												Editor editor = spPreferences.edit();
												editor.putBoolean("save", true);
												editor.putString("user_name", userphone.getText().toString());
												editor.putString("pwd", userpwd.getText().toString());
												editor.commit();
												
												progress.CloseProgress();
												Message message = new Message();
												message.what = 0;
												message.obj = hengyuName;
												handler.sendMessage(message);
//												Intent intent = new Intent(UserRegisterActivity.this, HomeActivity.class);
//												startActivity(intent);
//												finish();
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									
									@Override
									public void onFailure(Throwable arg0,String arg1) {
										// TODO Auto-generated method stub
										super.onFailure(arg0, arg1);
										System.out.println("=================arg0=="+arg0);
										System.out.println("=================arg1=="+arg1);
									}
								}, getApplicationContext());
								
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						};
					}.start();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			break;

		default:
			break;
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void showToast(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

}
