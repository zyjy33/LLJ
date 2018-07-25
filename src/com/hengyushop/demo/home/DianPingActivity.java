package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.airplane.AirPlaneBargainActivity;
import com.hengyushop.demo.airplane.AirPlaneOnLineActivity;
import com.hengyushop.demo.airplane.AirPlaneSelectActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.XiangqingData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;
import com.lelinju.www.UserLoginActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * ����
 * 
 * @author Administrator
 * 
 */
public class DianPingActivity extends BaseActivity implements OnClickListener{

	private ImageView iv_fanhui;
	private TextView tv_xiabu;
	EditText ra4;
	String check = "0";
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_dianping);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(DianPingActivity.this);
		intren();
		

	}
	public void intren() {
		try {
			
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
		iv_fanhui.setOnClickListener(this);
		RadioGroup ra0 = (RadioGroup) findViewById(R.id.ra0);
		RadioButton ra1 = (RadioButton) findViewById(R.id.ra1);
		RadioButton ra2 = (RadioButton) findViewById(R.id.ra2);
		RadioButton ra3 = (RadioButton) findViewById(R.id.ra3);
		ra4 = (EditText) findViewById(R.id.ra4);
		Button ra5 = (Button) findViewById(R.id.ra5);
		ra5.setOnClickListener(this);
		
//		ra1.setChecked(true);
		
		
		ra0.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case R.id.ra1:
					check = "0";
//					ra4.setText("���ǵ���Ʒһ����������������");
					break;
				case R.id.ra2:
					check = "1";
//					ra4.setText("һ�����Ҳ�������������������ԥ������һ�������ø��ã�");
					break;
				case R.id.ra3:
					check = "2";
//					ra4.setText("������Ľ���������кܴ�İ�����");
					break;
				default:
					break;
				}
			}
		});
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	private void loadWeather() {
		String article_id = getIntent().getStringExtra("article_id");
		String user_name = spPreferences.getString("user", "");
		String id = spPreferences.getString("user_id", "");
		String content = ra4.getText().toString();
		AsyncHttp.get(RealmName.REALM_NAME_LL + "/comment_add?user_id="+id+"&user_name="+user_name+"" +
				"&article_id="+article_id+"&content="+content+"&score="+check+"", new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				System.out.println("=======��������=="+arg1);
				try {
					progress.CloseProgress();
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
					if (status.equals("y")) {
						Toast.makeText(DianPingActivity.this, info, 200).show();
						finish();
					}else {
						Toast.makeText(DianPingActivity.this, info, 200).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}, null);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.iv_fanhui:
			finish();
			break;
		case R.id.ra5:
			progress.CreateProgress();
			loadWeather();
			break;

		default:
			break;
		}
	}
}
