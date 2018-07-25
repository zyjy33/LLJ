package com.hengyushop.demo.home;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;
/**
 * 签到
 * @author Administrator
 *
 */
public class QianDaoActivity extends BaseActivity{
	private TextView msg1, msg2, jifen_t;
	private ImageView start_click, jifen_main_bg, jifen_c,iv_tupian1;
	private LinearLayout layout;
	ImageView iv_qiandao;
	String login_sign,amount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jifen_main_layout);
		
		userloginqm();
		
		jifen_t = (TextView) findViewById(R.id.jifen_t);
		jifen_c = (ImageView) findViewById(R.id.jifen_c);
		iv_tupian1 = (ImageView) findViewById(R.id.iv_tupian1);
		iv_qiandao = (ImageView) findViewById(R.id.iv_qiandao);
		layout = (LinearLayout) findViewById(R.id.layout);
		iv_tupian1.setBackgroundResource(R.drawable.qiandao_bg);//qiandao_ysj qiandao_bg
		
		jifen_c.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userqiandao();
			}
		});
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	/**
	 * 输出站点配置
	 */
	private void userqiandao() {
		try{
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_id = spPreferences.getString("user_id", "");
			String user_name = spPreferences.getString("user", "");
//			String login_sign = spPreferences.getString("login_sign", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/comment_sign_in?user_id="+user_id+"&user_name="+user_name+"&login_sign="+login_sign+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======arg1============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
//							String webtel = obj.getString("webtel");
							Toast.makeText(QianDaoActivity.this, info, 200).show();
							jifen_c.setBackgroundResource(R.drawable.yi_qiandao);
							
						}else{
							Toast.makeText(QianDaoActivity.this, info, 200).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
				
				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					Toast.makeText(QianDaoActivity.this, "访问接口失败", 200).show();
				}
			}, QianDaoActivity.this);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取登录签名
	 * @param order_no 
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
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
							login_sign = data.login_sign;
							System.out.println("======login_sign============="+login_sign);
						}else{
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, null);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化
	 */

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
//			case 1:
//				jifen_t.setText(msg1Value);
//				imageLoader.displayImage("drawable://"+ R.drawable.qiandao_y, jifen_c);
//				initPopupWindow();
//				showPopupWindow(layout);
//				break;
//			case 0:
//				jifen_t.setText(msg1Value);
//				System.out.println(currentStatus);
//				if (currentStatus == 0) {
//					imageLoader.displayImage("drawable://"
//							+ R.drawable.qiandao_n, jifen_c);
//					jifen_c.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View arg0) {
//							// TODO Auto-generated method stub
//							click();
//						}
//					});
//
//				} else {
//					imageLoader.displayImage("drawable://"
//							+ R.drawable.qiandao_y, jifen_c);
//				}
//				break;

			default:
				break;
			}
		};
	};
//	private String dp(String temp){
//		  if (temp.equals("Monday")) {
//			  temp = "星期一";
//		}else if (temp.equals("Tuesday")) {
//			  temp = "星期二";
//		}else if (temp.equals("Wednesday")) {
//			  temp = "星期三";
//		}else if (temp.equals("Thursday")) {
//			  temp = "星期四";
//		}else if (temp.equals("Friday")) {
//			  temp = "星期五";
//		}else if (temp.equals("Saturday")) {
//			  temp = "星期六";
//		}else if (temp.equals("Sunday")) {
//			  temp = "星期日";
//		}
//		return temp;
//	}
}