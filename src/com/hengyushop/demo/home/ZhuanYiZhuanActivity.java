package com.hengyushop.demo.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cricle.Circleview;
import com.android.hengyu.web.RealmName;
import com.example.taobaohead.BeanVo;
import com.example.taobaohead.headview.ScrollTopView_xyz;
import com.example.zhuanpan.LuckyPan;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.domain.CircleBean;
import com.lglottery.www.widget.NewDataToast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.JulsActivity;
import com.lelinju.www.R;
/**
 * 转一转抽奖
 * @author Administrator
 *
 */
public class ZhuanYiZhuanActivity extends BaseActivity {
	/** Called when the activity is first created. */
	private LinearLayout layout;
	private ImageView lottery,ico_image;
	private Circleview claert;
	private int screnWidth;
	private Button circle_tip, start;
	private LayoutInflater mLayoutInflater;
	private PopupWindow mPopupWindow;
	private View popView, point;
	private int layoutW;
	private int layoutH;
	private float x,x1;
	private float y,y1;
	private Thread thread;
	private int FINAL_START = -1;
	private CircleBean circleBean;
	private WebView web_c;
	ScrollTopView_xyz mytaobao;
	private String yth="";
	private LinearLayout main;
	private ArrayList<BeanVo> list_ju;
	private LuckyPan mLuckyPan;
	private ImageView mStartBtn;
	private ArrayList<XsgyListData> list;
	String id,drawn,login_sign,ysj_point;
	int num;
	public static Handler handlerll;
	public static boolean type = false;
	private String activity_rule;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zhuanyizhuan);
		mytaobao = (ScrollTopView_xyz) findViewById(R.id.mytaobao);
		
	       handlerll = new Handler() {
		   		public void handleMessage(Message msg) {
		   			switch (msg.what) {
		   			case 2:
//		   				initPopupWindowTip(drawn);
//		   				showPopupWindowTip(drawn);
		   				if (type == true) {
		   					try {
		   					type = false;
//		   					Toast.makeText(ZhuanYiZhuanActivity.this, drawn, 200).show();
		   					System.out.println("drawn-------------------------------"+drawn);
		   					Intent intent = new Intent(ZhuanYiZhuanActivity.this,ZyZTiShiActivity.class);
		   					intent.putExtra("drawn", drawn);//
		   					intent.putExtra("id", id);//
		   					startActivity(intent);
		   				} catch (Exception e) {
							// TODO: handle exception
	   						e.printStackTrace();
						}
						}
		   				break;
		   			}
		   		}
		   	};
		mLuckyPan = (LuckyPan) findViewById(R.id.id_luckyPan);
		mStartBtn = (ImageView) findViewById(R.id.id_start_btn);

		mStartBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
//				System.out.println("id-----------------------------------"+id);
//				for (int i = 0; i < list.size(); i++) {
//					if (list.get(i).id.equals(id)) {
//						num = i;
//						drawn = list.get(i).drawn;
//					}
//				}
//				System.out.println("num-----------------------------------"+num);
//				System.out.println("drawn-----------------------------------"+drawn);
				if (!mLuckyPan.isStart()) {
//					int jifen = Integer.valueOf(ysj_point);
//					System.out.println("jifen=================="+jifen);
//					if (jifen >= 10) {
//						mLuckyPan.luckyStart(num);
//						mStartBtn.setImageResource(R.drawable.stop);
//						System.out.println("开始-----------------------------------");
						getjiangxiang(login_sign);
					
//						Intent intent = new Intent(ZhuanYiZhuanActivity.this,ZyZTiShiActivity.class);
//	   					intent.putExtra("drawn", drawn);
//	   					intent.putExtra("id", "8");
//	   					startActivity(intent);
						
//					}else {
//						Toast.makeText(ZhuanYiZhuanActivity.this, "您还不够10个聚币", 200).show();
//					}
				} else {
					if (!mLuckyPan.isShouldEnd()) {
						mLuckyPan.luckyEnd();
						mStartBtn.setImageResource(R.drawable.start);
						type = true;
//						userloginqm();
//						getjiangxiang(login_sign);
						System.out.println("停止-----------------------------------");
					}
				}
			}
		});
		userloginqm();
//		getjiangxiang();
		getjiangxiangxq();
		loadjutoutiao();
		getguizhe();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		web_c = (WebView) findViewById(R.id.web_c);
		web_c.getSettings().setJavaScriptEnabled(true);
		web_c.setWebChromeClient(new WebChromeClient());
		web_c.loadUrl(RealmName.REALM_NAME+"/wapSite/LotteryResultsJu918");
		
		main = (LinearLayout) findViewById(R.id.main);
//		ico_image = (ImageView) findViewById(R.id.ico_image);
//		imageLoader.displayImage("drawable://"+R.drawable.circle_bg1, ico_image);
		
		circle_tip = (Button) findViewById(R.id.circle_tip);
		circle_tip.setOnClickListener(clickListener);
//		
//		layout = (LinearLayout) findViewById(R.id.lottery1);
//		lottery = (ImageView) findViewById(R.id.lottery);
//		imageLoader.displayImage("drawable://"+R.drawable.circle_layout1, lottery);
//		point = findViewById(R.id.point);
//		start = (Button) findViewById(R.id.begin_btn);
////		start.setOnClickListener(clickListener);
//		screnWidth = getWindowManager().getDefaultDisplay().getWidth();
//		/**
//		 * 添加随机数，制定奖项数量为上限，一般抽奖中什么都是服务器返回的，可以在请求服务器接口成功在制定转盘转到那个奖项
//		 */
//		layout.post(new Runnable() {
//
//			@Override
//			public void run() {
//				layoutW = layout.getMeasuredWidth();
//				layoutH = layout.getMeasuredHeight();
//				// claertW = claert.getMeasuredWidth();
//				// claertH = claert.getMeasuredHeight();
//			
//				/*point.getLocationOnScreen(location);
//				x = location[0];
//				y = location[1];*/
//				x = point.getX();
//				y = point.getY();
//				System.out.println(x+"--"+y+"---"+x1+"---"+y1);
//				handler.sendEmptyMessage(0);
//			}
//		});
		
//		getLuckydraw();
	}
	
	//输出幸运奖幸奖项
		private void loadjutoutiao(){
//			progress.CreateProgress();
//				String strUrl = RealmName.REALM_NAME_LL+ "/get_lottery_lucky?lottery_id=0";
			String strUrl = RealmName.REALM_NAME_LL+ "/get_order_lucky_list?datatype=8&top=5";
//				System.out.println("中奖"+strUrl);
				AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(){
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						parse1(arg1);
					}
				}, null);
			}
		
		//输出幸运奖幸奖项
		private void parse1(String st) {
			try {
				JSONObject jsonObject = new JSONObject(st);
				String status = jsonObject.getString("status");
				if (status.equals("y")) {
				System.out.println("中奖===================="+st);
				list_ju = new ArrayList<BeanVo>();
				org.json.JSONArray jsonArray = jsonObject.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					BeanVo data = new BeanVo();
					JSONObject object = jsonArray.getJSONObject(i);
					data.user_name = object.getString("user_name");
					data.mobile = object.getString("mobile");
					data.lottery_title = object.getString("lottery_title");
//						System.out.println("data.groupon_title===================="+data.user_name);
					list_ju.add(data);
				}
				mytaobao.setData(list_ju);
				System.out.println("list_ju===================="+list_ju.size());
				}else{
					
				}
//				progress.CloseProgress();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			 
			AppManager.getAppManager().finishActivity();
		}
		return true;
		
	}
	
	private Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case -2:
				String mss = (String) msg.obj;
				NewDataToast.makeText(getApplicationContext(), mss, false, 0).show();
				break;
			case 0:
//				Toast.makeText(getApplicationContext(), "准备", 200).show();
				claert = new Circleview(ZhuanYiZhuanActivity.this, screnWidth, x, y,handler);
				layout.addView(claert, layoutW, layoutH);
				System.out.println(layoutW + "~" + layoutH);
				start.setOnClickListener(clickListener);
				break;
			case 1:
				claert.setStopRoter(true);
				thread = null;
//				Toast.makeText(getApplicationContext(), "开奖", 200).show();
//				initPopupWindowTip(circleBean);
				showPopupWindowTip(circle_tip);
				start.setEnabled(true);
				break;
			case 3:
				thread = (Thread) msg.obj;
				break;
			case 4:
				FINAL_START = msg.arg1;
				thread.start();
				int place = msg.arg1;
				claert.setReset(0);
				claert.setStopPlace(place);
				claert.setStopRoter(false);
				break;
			case 5:
				//抽奖结果
				FINAL_START = (int)(Math.random()*10);
				thread.start();
				int place1 =FINAL_START;
				claert.setReset(0);
				claert.setStopPlace(place1);
				claert.setStopRoter(false);
				
				circleBean = (CircleBean) msg.obj;
				if(circleBean.getPrizeTypeID().equals("1")){
					//商品
					
				}else {
					//积分
					
				}
				break;
			default:
				break;
			}
		};
	};
	Thread thread_1;
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.circle_tip:

				initPopupWindow();
				showPopupWindow(circle_tip);
				break;
			case R.id.begin_btn:
				try {
				Toast.makeText(ZhuanYiZhuanActivity.this, "转一转", 200).show();
//				System.out.println();
				start.setEnabled(false);
				FINAL_START = -1;//重置
				
				FINAL_START = (int)(Math.random()*10);
				thread.start();
				int place1 =FINAL_START;
				claert.setReset(2);
				claert.setStopPlace(place1);
				claert.setStopRoter(false);
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				if(yth.length()!=0){
				Map<String, String> params = new HashMap<String, String>();
				params.put("yth", yth);
				params.put("luckDrawSetName", "转啊转");
				params.put("act", "GetLuckDrawResult");
				AsyncHttp.post_1(RealmName.REALM_NAME+"/mi/getData.ashx", params, new AsyncHttpResponseHandler(){
					public void onSuccess(int arg0, String arg1) {
						try {
							System.out.println(arg1);
							JSONObject jsonObject = new JSONObject(arg1);
							String status = jsonObject.getString("status");
							if(status.equals("1")){
								//正确，开始处理
							String PrizeTypeID = 	jsonObject.getString("PrizeTypeID");//1.商品，2.表示积分
							CircleBean bean = new CircleBean();
							bean.setLuckDrawSerialNumber(jsonObject.getString("LuckDrawSerialNumber"));
							bean.setMsg(jsonObject.getString("msg"));
							bean.setMsg1(jsonObject.getString("msg2"));
							bean.setPrizeTypeID(PrizeTypeID);
							Message msg = new Message();
							msg.what = 5;
							msg.obj = bean;
							handler.sendMessage(msg);
							}else{
								Message msg = new Message();
								msg.what = -2;
								msg.obj = jsonObject.getString("msg");
								handler.sendMessage(msg);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					};
				});
				}

				break;
			default:
				break;
			}
		}
	};

	/**
	 * 抽奖规则
	 */
	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.circle_tip, null);
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
		Button lglottery_pop_closed = (Button) popView
				.findViewById(R.id.lglottery_pop_closed);
		lglottery_pop_closed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
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
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}
	/**
	 * 中奖内容
	 */
	private void initPopupWindowTip(String drawn) {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.circle_tip_text, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
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
		Button lglottery_pop_closed = (Button) popView.findViewById(R.id.lglottery_pop_closed);
		TextView start_f_name = (TextView) popView.findViewById(R.id.start_f_name);
//		start_f_name.setText(circleBean.getMsg1());
		TextView start_f_name0 = (TextView) popView.findViewById(R.id.start_f_name0);
//		start_f_name0.setText(circleBean.getMsg());
		Button ji_xu = (Button) popView.findViewById(R.id.ji_xu);
		Button ji_ls = (Button) popView.findViewById(R.id.ji_ls);
		lglottery_pop_closed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});
		ji_ls.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ZhuanYiZhuanActivity.this,JulsActivity.class);
				startActivity(intent);
			}
		});
		ji_xu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mPopupWindow.isShowing()) {
					mPopupWindow.dismiss();
				}
			}
		});
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				layout.removeAllViews();
				claert = new Circleview(ZhuanYiZhuanActivity.this, screnWidth, x, y,handler);
				layout.addView(claert, layoutW, layoutH);
				System.out.println(layoutW + "~" + layoutH);
				start.setOnClickListener(clickListener);
			}
		});

	}

	private void showPopupWindowTip(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// 第一个参数指定PopupWindow的锚点view，即依附在哪个view上。
			// 第二个参数指定起始点为parent的右下角，第三个参数设置以parent的右下角为原点，向左、上各偏移10像素。
			int[] location = new int[2];
			view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		}
	}
	
	/**
	 * 获取登录签名
	 * @param order_no 
	 */
	private void userloginqm() {
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
//						System.out.println("登录签名==================="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							login_sign = obj.getString("login_sign");
							ysj_point = obj.getString("point");
						}else{
						}
//						SharedPreferences spPreferences = getSharedPreferences("longuserset",MODE_PRIVATE);
//						Editor editor = spPreferences.edit();
//						editor.putString("login_sign", login_sign);
//						editor.commit();
//						getjiangxiang(login_sign);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, null);
	}
	
	/**
	 * 输出抽奖幸奖项-转一转
	 */
	private void getjiangxiang(String login_sign) {
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_id = spPreferences.getString("user_id", "");
			String user_name = spPreferences.getString("user", "");
//			String login_sign = spPreferences.getString("login_sign", "");
//			String login_sign = getIntent().getStringExtra("login_sign");
//			String strUrlone = RealmName.REALM_NAME_LL + 
//					"/get_lottery_award?user_id="+user_id+"&user_name="+user_name+"&lottery_id=15&sign="+login_sign+"";
			String strUrlone = RealmName.REALM_NAME_LL + 
					"/get_article_activity_award?user_id="+user_id+"&user_name="+user_name+"&article_id=10496&sign="+login_sign+"";
			System.out.println("======输出抽奖幸奖项============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======输出抽奖幸奖项============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
//							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
							JSONObject obct = object.getJSONObject("data");
							    id = obct.getString("id");
//								String title = obct.getString("title");
//								String drawn = obct.getString("drawn");
							    
								System.out.println("id-----------------------------------"+id);
								for (int i = 0; i < list.size(); i++) {
									if (list.get(i).id.equals(id)) {
										num = i;
										drawn = list.get(i).drawn;
									}
								}
								System.out.println("num-----------------------------------"+num);
								System.out.println("drawn-----------------------------------"+drawn);
								
								mLuckyPan.luckyStart(num);
								mStartBtn.setImageResource(R.drawable.stop);
								System.out.println("开始-----------------------------------");
						}else{
							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
						}
						
						
						System.out.println("======输出抽奖幸奖项=======id======"+id);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
				
				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					System.out.println("======访问接口失败============="+arg1);
//					Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
				}
			}, ZhuanYiZhuanActivity.this);
			
	}
	
	/**
	 * 输出抽奖列表
	 */
	private void getjiangxiangxq() {
		list = new ArrayList<XsgyListData>();
//			String strUrlone = RealmName.REALM_NAME_LL + "/get_lottery_model?lottery_id=15";
		String strUrlone = RealmName.REALM_NAME_LL + "/get_article_model?id=10496";
//			System.out.println("======输出抽奖详情============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======输出抽奖列表============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
//							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
							JSONObject obct = object.getJSONObject("data");
							org.json.JSONArray jsonArray = obct.getJSONArray("activity_award");
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jobject = jsonArray.getJSONObject(i);
								XsgyListData spList = new XsgyListData();
								spList.id = jobject.getString("id");
								spList.title = jobject.getString("title");
								spList.drawn = jobject.getString("drawn");
								list.add(spList);
							 }
						}else{
							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
						}
						System.out.println("======list.size()============="+list.size());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
				
				@Override
				public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					System.out.println("======访问接口失败============="+arg1);
//					Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
				}
			}, ZhuanYiZhuanActivity.this);
			
	}
	/**
	 * 输出内容
	 */
	private void getguizhe() {
//		list = new ArrayList<XsgyListData>();
		String strUrlone = RealmName.REALM_NAME_LL + "/get_article_model?id=10496";
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======输出抽奖列表============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
//							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
							JSONObject obct = object.getJSONObject("data");
//							org.json.JSONArray jsonArray = obct.getJSONArray("activity");
							JSONObject obct1 = obct.getJSONObject("activity");						
//							for(int i=0;i<jsonArray.length();i++){
//								JSONObject jobject = jsonArray.getJSONObject(i);
//								XsgyListData spList = new XsgyListData();
//								spList.id = jobject.getString("id");
								activity_rule = obct1.getString("activity_rule");
//								list.add(spList);
//							 }
								System.out.println("activity_rule==================="+activity_rule);
						}else{
//							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
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
					System.out.println("======访问接口失败============="+arg1);
//					Toast.makeText(ZhuanYiZhuanActivity.this, "访问接口失败", 200).show();
				}
			},getApplicationContext());
			
	}
	
}