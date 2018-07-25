package com.android.cricle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.android.hengyu.web.RealmName;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.JifenPop;
import com.hengyushop.entity.UserRegisterData;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

public class JifenMainActivity extends BaseActivity {
	private TextView msg1, msg2, jifen_t;
	private String msg1Value, msg2Value;
	private ImageView start_click, jifen_main_bg, jifen_c,iv_tupian1;
	private LinearLayout layout0, layout2;
	private LinearLayout layout;
	private int currentStatus = -1;
	
	private ArrayList<JifenPop> pops ;
	/**
	 * ��ʼ��
	 */
	private WareDao wareDao;
	private String yth;

	private void start() {
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		String str = RealmName.REALM_NAME
				+ "/mi/getdata.ashx";
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "DailyRegistrationStatus");
		params.put("yth", yth);
		AsyncHttp.post_1(str, params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					msg1Value = jsonObject.getString("msg001");
					// msg2Value = jsonObject.getString("msg002");
					currentStatus = jsonObject.getInt("currentStatus");
					handler.sendEmptyMessage(0);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				jifen_t.setText(msg1Value);
				imageLoader.displayImage("drawable://"
						+ R.drawable.qiandao_y, jifen_c);
				initPopupWindow();
				showPopupWindow(layout);
				break;
			case 0:
				jifen_t.setText(msg1Value);
				System.out.println(currentStatus);
				if (currentStatus == 0) {
					imageLoader.displayImage("drawable://"
							+ R.drawable.qiandao_n, jifen_c);
					jifen_c.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							click();
						}
					});

				} else {
					imageLoader.displayImage("drawable://"
							+ R.drawable.qiandao_y, jifen_c);
				}
				break;

			default:
				break;
			}
		};
	};
	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;

	private void click() {
		wareDao = new WareDao(getApplicationContext());
		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
		yth = registerData.getHengyuCode();
		String str = RealmName.REALM_NAME
				+ "/mi/getdata.ashx";
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "DailyRegistration");
		params.put("yth", yth);
				
		AsyncHttp.post_1(str, params,new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					msg1Value = jsonObject.getString("msg001");
					msg2Value = jsonObject.getString("msg002");
					JSONArray jsonArray = jsonObject.getJSONArray("items");
					int len = jsonArray.length();
					  pops = new ArrayList<JifenPop>();
					
					for(int i=0;i<len;i++){
						JSONObject object  = jsonArray.getJSONObject(i);
						JifenPop jp = new JifenPop();
						jp.setCanGetNum(object.getString("CanGetNum"));
						jp.setCurrentStatus(object.getString("CurrentStatus"));
						jp.setDayOfWeek(object.getString("DayOfWeek"));
						jp.setMonthAndDay(object.getString("MonthAndDay"));
						pops.add(jp);
					}
					handler.sendEmptyMessage(1);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jifen_main_layout);
		jifen_t = (TextView) findViewById(R.id.jifen_t);
		jifen_c = (ImageView) findViewById(R.id.jifen_c);
		iv_tupian1 = (ImageView) findViewById(R.id.iv_tupian1);
		layout = (LinearLayout) findViewById(R.id.layout);
		iv_tupian1.setBackgroundResource(R.drawable.qiandao_bg);
		
		// init();
		start();
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	private String dp(String temp){
		  if (temp.equals("Monday")) {
			  temp = "����һ";
		}else if (temp.equals("Tuesday")) {
			  temp = "���ڶ�";
		}else if (temp.equals("Wednesday")) {
			  temp = "������";
		}else if (temp.equals("Thursday")) {
			  temp = "������";
		}else if (temp.equals("Friday")) {
			  temp = "������";
		}else if (temp.equals("Saturday")) {
			  temp = "������";
		}else if (temp.equals("Sunday")) {
			  temp = "������";
		}
		return temp;
	}
	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.jifen_pop, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//��������background������ʧ
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.ban_louming));
		mPopupWindow.setOutsideTouchable(true);
		// �Զ��嶯��
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// ʹ��ϵͳ����
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		LinearLayout content = (LinearLayout) popView
				.findViewById(R.id.content);
		ImageView close = (ImageView) popView.findViewById(R.id.pop_close);
		
		TextView pop_text = (TextView) popView.findViewById(R.id.pop_text);
		pop_text.setText(msg1Value);
		close.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mPopupWindow.dismiss();
			}
		});
		for (int i = 0; i < pops.size(); i++) {
			LinearLayout itemLayout = (LinearLayout) LinearLayout.inflate(
					getApplicationContext(), R.layout.jifen_pop_item, null);
			TextView item0 = (TextView) itemLayout.findViewById(R.id.item0);
			TextView item1 = (TextView) itemLayout.findViewById(R.id.item1);
			ImageView item2 = (ImageView) itemLayout.findViewById(R.id.item2);
			TextView item3 = (TextView) itemLayout.findViewById(R.id.item3);
			TextView item4 = (TextView) itemLayout.findViewById(R.id.item4);
			item0.setText(dp(pops.get(i).getDayOfWeek()));
			item1.setText(pops.get(i).getMonthAndDay());
			if(pops.get(i).getCurrentStatus().equals("1")){
				item4.setVisibility(View.GONE);
				item2.setVisibility(View.VISIBLE);
				item2.setBackgroundResource(R.drawable.lglottery_main_selected);
			}else {
				item4.setVisibility(View.VISIBLE);
				item2.setVisibility(View.GONE);
				item4.setText("δ��ȡ");
			}
			item3.setText(pops.get(i).getCanGetNum());
			View v = new View(getApplicationContext());
			v.setLayoutParams(new LayoutParams(1, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
			v.setBackgroundResource(R.drawable.list_item_vertical_sep);
			content.addView(itemLayout);
			if(i!=pops.size()-1){
				content.addView(v);
			}else {
				content.addView(v);
			}
		
		}

	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// ��һ������ָ��PopupWindow��ê��view�����������ĸ�view�ϡ�
			// �ڶ�������ָ����ʼ��Ϊparent�����½ǣ�����������������parent�����½�Ϊԭ�㣬�����ϸ�ƫ��10���ء�
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}
}
