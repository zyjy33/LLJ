package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.MyGridAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.WareDatall;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;
/**
 * 
 * @author 
 *
 */
public class LieBiaoActivity extends Activity implements OnClickListener{
	private Intent intent;
	public Activity mContext;
	public static Handler handler;
	String user_name, user_id,pwd,order_no;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	String login_sign,amount;
	public static String yue_zhuangtai,title_id;
	public static int INDX = -1;
	private ArrayList<WareDatall> listll;
	WareDatall dm;
	private GridView gridView;
	private ArrayList<SpListData> lists;
	int len; 
	private MyGridAdapter arrayadapter;
	private String quanbu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_pay_pop);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		progress = new DialogProgress(LieBiaoActivity.this);
		gridView = (GridView) findViewById(R.id.gridView);
		String parent_id = getIntent().getStringExtra("id");
		quanbu = getIntent().getStringExtra("quanbu_id");
		 System.out.println("=====1第二层de数据====================="+parent_id);
//		String parent_id = "609";
		getleibie(parent_id);
		
		LinearLayout ll_jiemian = (LinearLayout) findViewById(R.id.ll_jiemian);
		ll_jiemian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private void getleibie(String parent_id) {
		// TODO Auto-generated method stub
		 System.out.println("=====1第二层de数据====================="+parent_id);
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_category_child_list?" +
					"channel_name=goods&parent_id="+parent_id+"",new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0,String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							formatWeatherll(arg1);
						}
					}, null);
	}
	
	/**
	 * 第二级菜单
	 */
	private ArrayList data1,data2;
	private void formatWeatherll(String result) {
		data1= new ArrayList();
		data2= new ArrayList();
		listll = new ArrayList<WareDatall>();
		try {
			System.out.println("=====第二层数据====================="+result);
			JSONObject object = new JSONObject(result);
			String status = object.getString("status");
			if (status.equals("y")) {
			JSONArray jsonArray = object.getJSONArray("data");
			
			listll.add(0, null);
			data1.add("001");
			data2.add("全部");
    		for (int i = 0; i < jsonArray.length(); i++) {
    		JSONObject obj= jsonArray.getJSONObject(i);
    		dm = new WareDatall();
     	    dm.setId(obj.getString("id"));
     	    dm.setTitle(obj.getString("title"));
			String title = obj.getString("title");
			String id = obj.getString("id");
//			INDX =  Integer.parseInt(id);
			listll.add(dm);
			data1.add(id);
			data2.add(title);
    		}
    		
    		System.out.println("=====data2====================="+data2.size());
    		gridView.setVisibility(View.VISIBLE);
//    		MyGridAdapter.clickTemp = 0;
    		arrayadapter = new MyGridAdapter(data1,data2,getApplicationContext());
	        gridView.setAdapter(arrayadapter);
	        
	        System.out.println("=====INDX3====================="+INDX);
	        gridView.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	              try{
	            	 System.out.println("=====arg2===================="+arg2);
	            	 String  quanbu_id = (String) data1.get(arg2);
            	 if (quanbu_id.equals("001")) {
            		 title_id = quanbu;
            		 System.out.println("=====1第二层title_id====================="+title_id);
				 } else {
					 title_id = (String) data1.get(arg2);
					 System.out.println("=====2第二层title_id====================="+title_id);
				 }
            	 
	            	 arrayadapter.setSeclection(arg2);
	            	 arrayadapter.notifyDataSetChanged();
	            	 
	            	 finish();
	            	} catch (Exception e) {
						// TODO: handle exception
					}
	            }
	        });
	        
            }else {
            	gridView.setVisibility(View.GONE);
            	System.out.println("=====第二层数据2====================="+INDX);
            	load_list(INDX, true);
			}
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	
	/**
	 * 1列表数据解析
	 */
	private int CURRENT_NUM = 1;
	private final int VIEW_NUM = 10;
	private void load_list(int INDX,boolean flag) {
		if(flag){
			CURRENT_NUM = 1;
			lists = new ArrayList<SpListData>();
		}
		System.out.println("=====================002--"+INDX);
			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_article_page_size_list?channel_name=goods&category_id="+INDX+"" +
							"&page_size="+VIEW_NUM+"&page_index="+CURRENT_NUM+"&strwhere=&orderby=",
							 new AsyncHttpResponseHandler(){
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
//								System.out.println("=====================三级值"+arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									String status = jsonObject.getString("status");
									String info = jsonObject.getString("info");
									if (status.equals("y")) {
									JSONArray jsonArray = jsonObject.getJSONArray("data");
//									if (jsonArray.length() > 0) {
									 len = jsonArray.length();
									for(int i=0;i<jsonArray.length();i++){
										JSONObject object = jsonArray.getJSONObject(i);
										SpListData spList = new SpListData();
										spList.id = object.getString("id");
										spList.img_url = object.getString("img_url");
										spList.title = object.getString("title");
										spList.market_price = object.getString("market_price");
										spList.sell_price = object.getString("sell_price");
//										JSONArray jaArray = object.getJSONArray("albums");
//										for (int j = 0; j < jaArray.length(); j++) {
//											JSONObject jact = jaArray.getJSONObject(j);
//										}
										lists.add(spList);
									}
									}else {
										progress.CloseProgress();
										Toast.makeText(LieBiaoActivity.this, "没有商品了", 200).show();
									}
									progress.CloseProgress();
									handler.sendEmptyMessage(0);
									if(len!=0){
										CURRENT_NUM =CURRENT_NUM+1;
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}, null);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}


	
	
	
	
	
}