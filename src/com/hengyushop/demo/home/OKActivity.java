package com.hengyushop.demo.home;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.ctrip.openapi.java.utils.EncodingHandler;
import com.google.zxing.WriterException;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;

/**
 * 面对面推广
 * 
 * @author Administrator
 * 
 */
public class OKActivity extends BaseActivity implements OnClickListener{

	private ImageView iv_fanhui,mImageView,mImageView1,mImageView2;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private EditText mEditText;
	private Button btn_data;
	private LinearLayout ll_erweima;
	private List<String> list;
	private ListView lv;
	private TextView tv_geshu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mianduimianll);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(OKActivity.this);
		intren();
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		TextView tv_geshu = (TextView) findViewById(R.id.tv_geshu);
//		if (list.size() > 0) {
//			tv_geshu.setText(list.size());
//		}
//	}
	
	public void intren() {
		try {
		mImageView = (ImageView) findViewById(R.id.iv_qr_image);
		mImageView1 = (ImageView) findViewById(R.id.iv_qr_image1);
		mImageView2 = (ImageView) findViewById(R.id.iv_qr_image2);
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		mEditText = (EditText) findViewById(R.id.et_haoma);
		btn_data = (Button) findViewById(R.id.btn_data);
//		tv_geshu = (TextView) findViewById(R.id.tv_geshu);
//		mImageView2.setBackgroundResource(R.drawable.rwm);
//		ll_erweima = (LinearLayout) findViewById(R.id.ll_erweima);
		
		iv_fanhui.setOnClickListener(this);
		btn_data.setOnClickListener(this);
		
		
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
			finish();
			break;

		default:
			break;
		}
	}
	
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {   
        // 获取ListView对应的Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()返回数据项的数目   
            View listItem = listAdapter.getView(i, null, listView);   
            // 计算子项View 的宽高   
            listItem.measure(0, 0);    
            // 统计所有子项的总高度   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()获取子项间分隔符占用的高度   
        // params.height最后得到整个ListView完整显示需要的高度   
        listView.setLayoutParams(params);   
    } 
}
