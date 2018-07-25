package com.hengyushop.demo.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.android.hengyu.pub.MyJuFenMxAdapter;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.EncodingHandler;
import com.google.zxing.WriterException;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.MyJuFenData;
import com.lglottery.www.http.Util;
import com.lglottery.www.widget.PullToRefreshView;
import com.lglottery.www.widget.PullToRefreshView.OnFooterRefreshListener;
import com.lglottery.www.widget.PullToRefreshView.OnHeaderRefreshListener;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.lelinju.www.R;

/**
 *
 * 我的聚粉
 * @author Administrator
 * 
 */
public class MyJuFenActivity extends BaseActivity implements OnClickListener{
	private ImageView iv_fanhui,mImageView,mImageView1,mImageView2;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private EditText mEditText;
	private LinearLayout img_btn_order;
	private ArrayList<MyJuFenData> list;
	String user_name,user_id;
	private TextView tv_geshu,tv_tjfs,tv_hgfs;
	private Button btn_settle_accounts;
	LayoutInflater mLayoutInflater;
	PopupWindow mPopupWindow;
	protected PopupWindow pop;
	private View view;
	private ImageButton btn_wechat;
	private View btn_sms;
	private View btn_wx_friend;
	private ImageButton img_btn_tencent;
	private IWXAPI api;
	private ListView listView;
	private PullToRefreshView refresh;
	MyJuFenMxAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_jufen);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		progress = new DialogProgress(MyJuFenActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		
		intren();
		load_list(true);
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
//			refresh = (PullToRefreshView) findViewById(R.id.refresh);
//			refresh.setOnHeaderRefreshListener(listHeadListener);
//			refresh.setOnFooterRefreshListener(listFootListener);
			listView = (ListView) findViewById(R.id.new_list);
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		img_btn_order = (LinearLayout) findViewById(R.id.img_btn_order);
		tv_geshu = (TextView) findViewById(R.id.tv_geshu);
//		tv_tjfs = (TextView) findViewById(R.id.tv_tjfs);
//		tv_hgfs = (TextView) findViewById(R.id.tv_hgfs);
		btn_settle_accounts = (Button) findViewById(R.id.btn_settle_accounts);
		iv_fanhui.setOnClickListener(this);
		img_btn_order.setOnClickListener(this);
		btn_settle_accounts.setOnClickListener(this);
		
		
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 上拉列表刷新加载
	 */
//	private OnHeaderRefreshListener listHeadListener = new OnHeaderRefreshListener() {
//
//		@Override
//		public void onHeaderRefresh(PullToRefreshView view) {
//			// TODO Auto-generated method stub
//			refresh.postDelayed(new Runnable() {
//
//				@Override
//				public void run() {
//					refresh.onHeaderRefreshComplete();
//				}
//			}, 1000);
//		}
//	};
	
	/**
	 * 下拉列表刷新加载
	 */
//	private OnFooterRefreshListener listFootListener = new OnFooterRefreshListener() {
//
//		@Override
//		public void onFooterRefresh(PullToRefreshView view) {
//			// TODO Auto-generated method stub
//			refresh.postDelayed(new Runnable() {
//
//				@Override
//				public void run() {
//					try {
//					if(RUN_METHOD==0){
//						System.out.println("RUN_METHOD========="+RUN_METHOD);
//						load_list2(true);
//					}else {
//						load_list(false);
//					}
//					refresh.onFooterRefreshComplete();
//					
//					} catch (Exception e) {
//						// TODO: handle exception
//						e.printStackTrace();
//					}
//				}
//			}, 1000);
//		}
//	};
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				System.out.println("-------------------------"+list.size());
				if (list.size() > 0) {
					String num = String.valueOf(list.size());
					tv_geshu.setText(num+"个");
				}
				
//				list = (ArrayList<MyJuFenData>) msg.obj;
				adapter = new MyJuFenMxAdapter(list,list_avatar1,MyJuFenActivity.this, imageLoader);
				listView.setAdapter(adapter);
				setListViewHeightBasedOnChildren(listView);  
				progress.CloseProgress();
				break;

			default:
				break;
			}
		};
	};
	
	/**
	 * 第1个列表数据解析
	 */
//	private int RUN_METHOD = -1;
//	private int CURRENT_NUM = 1;
//	private final int VIEW_NUM = 100;
	ArrayList list_avatar1;
	private void load_list(boolean flag) {
		list_avatar1 = new ArrayList();
//		progress.CreateProgress();
//		RUN_METHOD = 1;
		list = new ArrayList<MyJuFenData>();
//		if(flag){
//			//计数和容器清零
//			CURRENT_NUM = 0;
//			list = new ArrayList<MyJuFenData>();
//		}
			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_user_child_list?user_id="
				+ user_id + "&user_name="+user_name+""+
							"&page_size="+100+"&page_index="+1+"",
							 new AsyncHttpResponseHandler(){
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
								System.out.println("=====================二级值1"+arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									String status = jsonObject.getString("status");
									String info = jsonObject.getString("info");
									if (status.equals("y")) {
									JSONArray jsonArray = jsonObject.getJSONArray("data");
									 int len = jsonArray.length();
									for(int i=0;i<jsonArray.length();i++){
										JSONObject object = jsonArray.getJSONObject(i);
										MyJuFenData data = new MyJuFenData();
										data.mobile = object.getString("mobile");
										data.login_sign = object.getString("login_sign");
										data.avatar = object.getString("avatar");
										data.real_name = object.getString("real_name");
										data.nick_name = object.getString("nick_name");
										String avatar = data.avatar;//
										System.out.println("二级值2====================="+avatar);
										if (avatar.contains("http")) {
											System.out.println("================================http");
											list_avatar1.add(avatar);
								       } else if(avatar.contains("upload")){
								    	   System.out.println("================================upload");
								    	    String img_url = RealmName.REALM_NAME_HTTP + avatar;
								    	    list_avatar1.add(img_url);
									   }else {
										   System.out.println("================================空值");
										   list_avatar1.add(avatar);
									   }
										list.add(data);
									}
//									Message msg = new Message();
//									msg.what = 0;
//									msg.obj = list;
//									handler.sendMessage(msg);
									
									handler.sendEmptyMessage(0);
									}else {
										progress.CloseProgress();
										Toast.makeText(MyJuFenActivity.this, info, 200).show();
									}
//									if(len!=0){
//										CURRENT_NUM =CURRENT_NUM+VIEW_NUM;
//									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}, null);
	}
	
	/**
	 * 第2个列表数据解析
	 */
//	private void load_list2(boolean flag) {
//		list = new ArrayList<MyJuFenData>();
//		if(flag){
//			//计数和容器清零
//			CURRENT_NUM = 0;
//			list = new ArrayList<MyJuFenData>();
//		}
//			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_page_size_list?channel_name=content&category_id=52" +
//							"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
//							 new AsyncHttpResponseHandler(){
//							@Override
//							public void onSuccess(int arg0, String arg1) {
//								// TODO Auto-generated method stub
//								super.onSuccess(arg0, arg1);
//								System.out.println("=====================二级值1"+arg1);
//								try {
//									JSONObject jsonObject = new JSONObject(arg1);
//									JSONArray jsonArray = jsonObject.getJSONArray("data");
//									 int len = jsonArray.length();
//									for(int i=0;i<jsonArray.length();i++){
//										JSONObject object = jsonArray.getJSONObject(i);
//										MyJuFenData data = new MyJuFenData();
//										data.mobile = object.getString("mobile");
//										data.login_sign = object.getString("login_sign");
//										list.add(data);
//									}
//									Message msg = new Message();
//									msg.what = 0;
//									msg.obj = list;
//									handler.sendMessage(msg);
//								} catch (JSONException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//							}
//						}, null);
//	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.iv_fanhui:
			finish();
			break;
		case R.id.img_btn_order:
//			Intent intent = new Intent(MyJuFenActivity.this, MyJuFenMxActivity.class);
//			startActivity(intent);
			break;
		case R.id.btn_settle_accounts:
//			Intent intent1 = new Intent(MyJuFenActivity.this, FenXiangActivity.class);
//			startActivity(intent1);
			SoftWarePopuWindow(btn_settle_accounts, MyJuFenActivity.this);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 分享
	 * 
	 * @param view2
	 * @param context
	 */
	private void SoftWarePopuWindow(View view2, final Context context) {
		try {
			mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			// inflater = LayoutInflater.from(context);
			view = mLayoutInflater.inflate(R.layout.ware_infromation_share,null);
			pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT, false);
			final Dialog dlg = new Dialog(context, R.style.delete_pop_style);
			pop.setBackgroundDrawable(new BitmapDrawable());
			pop.setOutsideTouchable(true);
			// pop.setFocusable(true);
			// pop.setTouchable(true); // 设置PopupWindow可触摸
			//
			if (!pop.isShowing()) {
				pop.showAtLocation(view2, Gravity.BOTTOM, 0, 0);
			}
			btn_wechat = (ImageButton) view.findViewById(R.id.img_btn_wechat);
			btn_wx_friend = (ImageButton) view
					.findViewById(R.id.img_btn_wx_friend);
			btn_sms = (ImageButton) view.findViewById(R.id.img_btn_sms);
			img_btn_tencent = (ImageButton) view
					.findViewById(R.id.img_btn_tencent);
			Button btn_holdr = (Button) findViewById(R.id.btn_holdr);
			btn_holdr.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					pop.dismiss();
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// 新浪
		img_btn_tencent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				con(19, 1);
			}
		});

		// 微信
		btn_wechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				// progress.CreateProgress();
				con(16, 1);
			}
		});
		// 朋友圈
		btn_wx_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				con(17, 1);
			}
		});
		// 短信
		btn_sms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				con(18, 0);
			}
		});
	}

	private void con(final int index, int type) {
		try {

			// String user_name = spPreferences.getString("user", "");
			String id = spPreferences.getString("user_id", "");
			System.out.println("id==========" + id);
			// http://183.62.138.31:1010/appshare/133.html
			String data = "http://183.62.138.31:1010/appshare/" + id + ".html";
			System.out.println("分享11======================" + data);
			String zhou = "聚云商下载地址,下载后可帮分享的好友获得积分" + data;
			System.out.println("==========" + zhou);
			softshareWxChat(zhou);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 微信分享
	 * 
	 * @param text
	 */
	private void softshareWxChat(String text) {
		String temp[] = text.split("http");

		api = WXAPIFactory.createWXAPI(MyJuFenActivity.this, Constants.APP_ID,
				false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		// webpage.webpageUrl = temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		// msg.title = "我发你一个软件,看看呗!";ysj_logn
		msg.title = "聚云商APP分享";
		msg.description = temp[0];
		Bitmap thumb = BitmapFactory.decodeResource(getResources(),
				R.drawable.icon);
		msg.thumbData = Util.bmpToByteArray(thumb, true);

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);

		System.out.println("微信注册" + flag);

	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
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
	
}
