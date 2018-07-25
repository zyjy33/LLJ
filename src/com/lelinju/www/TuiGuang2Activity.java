package com.lelinju.www;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.cricle.JifenMainActivity;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.GuaYiGuaActivity;
import com.hengyushop.demo.home.JuFaFaXunaZheActivity;
import com.hengyushop.demo.home.QianDaoActivity;
import com.hengyushop.demo.home.QianMingActivity;
import com.hengyushop.demo.home.ZhuanYiZhuanActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.XsgyListData;
import com.lglottery.www.adapter.TuiGuang1Adapter;
import com.lglottery.www.adapter.TuiGuang2Adapter;
import com.lglottery.www.domain.TuiGuangBean;
import com.lglottery.www.widget.InScrollListView;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class TuiGuang2Activity extends BaseActivity {
	private InScrollListView scrool,scroo2;
	private ListView listView;
	private LinearLayout item0, item1, item2,ll_zhuti,ll_zhuti2;
	private int screenWidth;
	private TuiGuang1Adapter adapter1;
	private TuiGuang2Adapter adapter;
	private String yth;
	private ArrayList<TuiGuangBean> lists;
	private WareDao wareDao;
	private TextView tv_zhuti;
	String list_id,id2,login_sign;
	private DialogProgress progress;
	private String id;
	public static String drawn = "";
	private ArrayList<XsgyListData> list;
	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuiguang2_layout);
		// Thread.setDefaultUncaughtExceptionHandler(this);
		tv_zhuti = (TextView) findViewById(R.id.tv_zhuti);
		item0 = (LinearLayout) findViewById(R.id.item0);
		item1 = (LinearLayout) findViewById(R.id.item1);
		item2 = (LinearLayout) findViewById(R.id.item2);
		ll_zhuti = (LinearLayout) findViewById(R.id.ll_zhuti);
		ll_zhuti2 = (LinearLayout) findViewById(R.id.ll_zhuti2);
		scrool = (InScrollListView) findViewById(R.id.scrool);
		scroo2 = (InScrollListView) findViewById(R.id.scroo2);
		listView = (ListView) findViewById(R.id.listview);
		item0.setBackgroundResource(R.drawable.tuiguang2_0);
		item1.setBackgroundResource(R.drawable.tuiguang2_1);
		item2.setBackgroundResource(R.drawable.tuiguang2_2);
//		load();
		userloginqm();
//		getjiangxiangxq();
		loadCate();
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		ll_zhuti.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TuiGuang2Activity.this,QianMingActivity.class);
				intent.putExtra("id", list_id);
				intent.putExtra("tv_name", "1");
				startActivity(intent);
			}
		});
		
		ll_zhuti2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(TuiGuang2Activity.this,QianMingActivity.class);
//				intent.putExtra("id2", id2);
				intent.putExtra("tv_name", "2");
				startActivity(intent);
			}
		});

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;// 宽度height = dm.heightPixels ;
		set(item0, 180, 307);
		set1(item1, 80, 307);
		set1(item2, 80, 307);
		item0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent = new Intent(TuiGuang2Activity.this,JifenMainActivity.class);
				Intent intent = new Intent(TuiGuang2Activity.this,QianDaoActivity.class);
				startActivity(intent);
			}
		});
		item1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent intent42 = new Intent(TuiGuang2Activity.this,CircleActivity.class);
				Intent intent = new Intent(TuiGuang2Activity.this,ZhuanYiZhuanActivity.class);
				intent.putExtra("login_sign", login_sign);
				startActivity(intent);
			}
		});
		item2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try {
					
//				System.out.println("id-----------------------------------"+id);
//				for (int i = 0; i < list.size(); i++) {
//					if (list.get(i).id.equals(id)) {
//						drawn = list.get(i).drawn;
//					}
//				}
//				System.out.println("drawn-----------------------------------"+drawn);
//				Intent intent = new Intent(TuiGuang2Activity.this,GuaGuaActivity.class);
					
				Intent intent = new Intent(TuiGuang2Activity.this,GuaYiGuaActivity.class);
				startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
	};

	private Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
//				TuiGuangBean content = (TuiGuangBean) msg.obj;
				String id = (String) msg.obj;
				Intent intent = new Intent(TuiGuang2Activity.this,JuFaFaXunaZheActivity.class);
				intent.putExtra("exam_id", id);
				startActivity(intent);
				break;
			case 1:
				String web_id = (String) msg.obj;
				Intent intent1 = new Intent(TuiGuang2Activity.this,Webview1.class);
				intent1.putExtra("web_id", web_id);
				startActivity(intent1);
				break;
			case 2:
				adapter1 = new TuiGuang1Adapter(getApplicationContext(), lists,imageLoader, handler);
				scrool.setAdapter(adapter1);
//				setListViewHeightBasedOnChildren(listView);  
				break;
			case 3:
				adapter = new TuiGuang2Adapter(getApplicationContext(), lists,imageLoader, handler);
				scroo2.setAdapter(adapter);
				
//				scroo2.setOnItemClickListener(new OnItemClickListener() {
//
//					@Override
//					public void onItemClick(AdapterView<?> arg0, View arg1,
//							int arg2, long arg3) {
//						// TODO Auto-generated method stub
//						Intent intent= new Intent(TuiGuang2Activity.this,Webview1.class);
//						intent.putExtra("list_xsgy", lists.get(arg2).id);
//						startActivity(intent);
//					}
//					
//				});
				break;
				

			default:
				break;
			}
		};
	};
	
	//商品列表
	private void loadCate(){
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_test_lesson_model?" +
                "lesson_id=23", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					System.out.println("arg1=========="+arg1);
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					if (status.equals("y")) {
					JSONObject obj = jsonObject.getJSONObject("data");
					String title = obj.getString("title");
					list_id = obj.getString("id");
					tv_zhuti.setText(title);
                    }else {
                    	
					}
					loadCatel(list_id);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, null);
		
    }

	
	private void loadCatel(String id){
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_test_exam_list?" +
                "channel_name=question&lesson_id="+id+"&page_size=3&page_index=1&strwhere=&orderby=", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					System.out.println("（商品列表）=========="+arg1);
					lists = new ArrayList<TuiGuangBean>();
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					if (status.equals("y")) {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						TuiGuangBean data = new TuiGuangBean();
						JSONObject object = jsonArray.getJSONObject(i);
						data.id = object.getString("id");
						data.title = object.getString("title");
						data.subtitle = object.getString("subtitle");
						data.img_url = object.getString("img_url");
						data.add_time = object.getString("add_time");
						Log.v("data1", data.id + "");
						lists.add(data);
					}
					
					handler.sendEmptyMessage(2);
                    }else {
//                    	handler.sendEmptyMessage(2);
					}
					
					loadCatell();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, null);
		
    }
	
	
	private void loadCatell(){
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_page_size_list?" +
                "channel_name=content&category_id="+1726+"&page_size=3&page_index=1&strwhere=&orderby=", new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0, arg1);
				try {
					System.out.println("2=========="+arg1);
					lists = new ArrayList<TuiGuangBean>();
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					if (status.equals("y")) {
					JSONArray jsonArray = jsonObject.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						TuiGuangBean data = new TuiGuangBean();
						JSONObject object = jsonArray.getJSONObject(i);
						data.id = object.getString("id");
//						id2 = object.getString("id");
						data.title = object.getString("title");
						data.subtitle = object.getString("subtitle");
						data.img_url = object.getString("img_url");
						data.add_time = object.getString("add_time");
						Log.v("data1", data.id + "");
						lists.add(data);
					}
					
					handler.sendEmptyMessage(3);
                    }else {
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, null);
		
		
    }
	
	public void setListViewHeightBasedOnChildren(ListView listView) {   
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
	
//	 public void uncaughtException(Thread arg0, Throwable arg1) {
//	 // TODO Auto-generated method stub
//	 //在此处理异常， arg1即为捕获到的异常
//	 Log.i("AAA", "uncaughtException   " + arg1);
//	 }
//	private void load() {
//		UserRegisterData registerData = wareDao.findIsLoginHengyuCode();
//		yth = registerData.getHengyuCode();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("act", "GetWeiXinReplyInfoList_ForAPP");
//		params.put("yth", yth);
//		AsyncHttp.post_1(RealmName.REALM_NAME + "/mi/getdata.ashx", params,
//				new AsyncHttpResponseHandler() {
//					@Override
//					public void onSuccess(int arg0, String arg1) {
//						super.onSuccess(arg0, arg1);
//						try {
//							lists = new ArrayList<TuiGuangBean>();
//							JSONObject jsonObject = new JSONObject(arg1);
//							JSONArray jsonArray = jsonObject
//									.getJSONArray("items");
//							int len = jsonArray.length();
//							for (int i = 0; i < len; i++) {
//								TuiGuangBean bean = new TuiGuangBean();
//								JSONObject object = jsonArray.getJSONObject(i);
//								bean.setTitle(object.getString("title"));
//								bean.setImage(object.getString("ImageUrl"));
//								bean.setContent(object.getString("description"));
//								bean.setDetail(object
//										.getString("DetailPageContent"));
//								bean.setHttp(object.getString("LinkUrl"));
//								bean.setTime(object.getString("CreateTime"));
//								bean.setWeiXin_LinkUrl(object
//										.getString("WeiXin_LinkUrl"));
//								lists.add(bean);
//							}
//							Message msg = handler.obtainMessage();
//							msg.what = 1;
//							msg.obj = lists;
//							handler.sendMessage(msg);
//
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}
//					}
//				});
//
//	}

	
	private void set(LinearLayout layout, int x, int y) {
		double scale_b = (double) x / y;
		double layout2_height = screenWidth * scale_b;
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				(int) layout2_height));
	}

	private void set1(LinearLayout layout, int x, int y) {
		double scale_b = (double) x / y;
		double layout2_height = screenWidth * scale_b;
		layout.setLayoutParams(new LayoutParams(screenWidth / 2,
				(int) layout2_height));
	}
	
	/**
	 * 获取登录签名、获取用户积分
	 * @param order_no 
	 */
	private void userloginqm() {
		try{
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			System.out.println("======获取用户积分============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						if (status.equals("y")) {
							JSONObject obj = object.getJSONObject("data");
							UserRegisterllData data = new UserRegisterllData();
							data.login_sign = obj.getString("login_sign");
//							point = obj.getString("point");
							login_sign = data.login_sign;
							System.out.println("======login_sign============="+login_sign);
//							tv_jifen.setText(point);
						}else{
						}
//						getjiangxiang(login_sign);
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
	 * 输出刮一刮奖
	 * @param login_sign 
	 */
	private void getjiangxiang(String login_sign) {
			SharedPreferences spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_id = spPreferences.getString("user_id", "");
			String user_name = spPreferences.getString("user", "");
//			String login_sign = spPreferences.getString("login_sign", "");
//			String login_sign = getIntent().getStringExtra("login_sign");
			String strUrlone = RealmName.REALM_NAME_LL + "/get_lottery_award?user_id="+user_id+"&user_name="+user_name+"&lottery_id=16&sign="+login_sign+"";
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
								String title = obct.getString("title");
								String drawn = obct.getString("drawn");
						}else{
							Toast.makeText(TuiGuang2Activity.this, info, 200).show();
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
			}, TuiGuang2Activity.this);
			
	}
	
	/**
	 * 输出刮一刮奖详情
	 */
	private void getjiangxiangxq() {
		list = new ArrayList<XsgyListData>();
			String strUrlone = RealmName.REALM_NAME_LL + "/get_lottery_model?lottery_id=16";
//			System.out.println("======输出抽奖详情============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======输出抽奖详情============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						String info = object.getString("info");
						if (status.equals("y")) {
//							Toast.makeText(ZhuanYiZhuanActivity.this, info, 200).show();
							JSONObject obct = object.getJSONObject("data");
							org.json.JSONArray jsonArray = obct.getJSONArray("award");
							for(int i=0;i<jsonArray.length();i++){
								JSONObject jobject = jsonArray.getJSONObject(i);
								XsgyListData spList = new XsgyListData();
								spList.id = jobject.getString("id");
								spList.title = jobject.getString("title");
								spList.drawn = jobject.getString("drawn");
								list.add(spList);
							 }
						}else{
							Toast.makeText(TuiGuang2Activity.this, info, 200).show();
						}
						System.out.println("======list.size()============="+list.size());
//						System.out.println("drawn-----------------------------------"+drawn);
						
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
			}, TuiGuang2Activity.this);
			
	}
}
