package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.BitUtil;
import com.hengyushop.demo.airplane.AirPlaneBargainActivity;
import com.hengyushop.demo.airplane.AirPlaneOnLineActivity;
import com.hengyushop.demo.airplane.AirPlaneSelectActivity;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.XiangqingData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;
import com.lelinju.www.UserLoginActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
 * 面对面扫一扫
 * 
 * @author Administrator
 * 
 */
public class MianDuiMianSySActivity extends BaseActivity implements OnClickListener{

	private ImageView iv_fanhui,iv_qr_image1,iv_touxiang;
	private TextView tv_xiabu;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainduimian_sys);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(MianDuiMianSySActivity.this);
		intren();
	}
	public void intren() {
		try {
			
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_qr_image1 = (ImageView) findViewById(R.id.iv_qr_image1);
//		iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
		String erweima = getIntent().getStringExtra("erweima");
		Bitmap bitmap = BitUtil.stringtoBitmap(erweima);
		iv_qr_image1.setImageBitmap(bitmap);
		
//		String avatar = spPreferences.getString("avatar", "");
//		ImageLoader imageLoader=ImageLoader.getInstance();
//		imageLoader.displayImage((String)RealmName.REALM_NAME_HTTP+avatar,iv_touxiang);
		
		iv_fanhui.setOnClickListener(this);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.iv_fanhui:
			try {
			finish();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			break;

		default:
			break;
		}
	}
}
