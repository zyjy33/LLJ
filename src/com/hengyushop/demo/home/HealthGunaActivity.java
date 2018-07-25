package com.hengyushop.demo.home;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.HealthPavilionAdapter;
import com.android.hengyu.pub.MyGridllAdapter;
import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.GuigellBean;
import com.hengyushop.entity.MyAssetsBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

/**
 * 
 * 健康馆
 * 
 * @author Administrator
 * 
 */
public class HealthGunaActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4;
	private LinearLayout index_item0, index_item1, ll_buju1, index_item3;
	private SharedPreferences spPreferences;
	private TextView tv_ticket, tv_shop_ticket, tv_jifen_ticket;
	private ArrayList<GuigeBean> list;
	private ArrayList<MyAssetsBean> list_lb;
	String user_name, user_id;
	int len;
	String fund_id = "0";
	private MyGridView gridView2;
	private ListView new_list;
	GuigeData md; 
	GuigeBean mb;
	GuigellBean mbll;
	GuigeBean data_ll;
	MyAssetsBean data;
	ArrayList<GuigeBean> list_l;
	ArrayList<GuigeData> list_ll = new ArrayList<GuigeData>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_health_pavilion);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		Initialize();
		load_gridview();

	}

	/**
	 * 控件初始化
	 */
	private void Initialize() {

		try {
//			tv_ticket = (TextView) findViewById(R.id.tv_ticket);
//			tv_shop_ticket = (TextView) findViewById(R.id.tv_shop_ticket);
//			tv_jifen_ticket = (TextView) findViewById(R.id.tv_jifen_ticket);
//			tv_djjifen_ticket = (TextView) findViewById(R.id.tv_djjifen_ticket);
			gridView2=(MyGridView)findViewById(R.id.gridview2);
			new_list = (ListView) findViewById(R.id.new_list);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			ll_buju1 = (LinearLayout) findViewById(R.id.ll_buju1);
			cursor1 = (ImageView) findViewById(R.id.cursor1);
			cursor2 = (ImageView) findViewById(R.id.cursor2);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			
			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.index_item0:
			cursor1.setVisibility(View.VISIBLE);
			cursor2.setVisibility(View.INVISIBLE);
			ll_buju1.setVisibility(View.VISIBLE);
			break;
		case R.id.index_item1:
			cursor1.setVisibility(View.INVISIBLE);
			cursor2.setVisibility(View.VISIBLE);
			ll_buju1.setVisibility(View.GONE);
			Toast.makeText(HealthGunaActivity.this, "暂无日常调理", 200).show();
			break;

		default:
			break;
		}
	}

	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				 MyGridllAdapter MyAdapter=new MyGridllAdapter(list,getApplicationContext());
				 gridView2.setAdapter(MyAdapter);
//				 
				 gridView2.setOnItemClickListener(new OnItemClickListener() {
				        @Override
				        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				            
				            Intent intent= new Intent(HealthGunaActivity.this,SymptomsJieShaoActivity.class);
				            intent.putExtra("summary", list.get(arg2).summary);
				            intent.putExtra("proposal", list.get(arg2).proposal);
				            intent.putExtra("cause", list.get(arg2).cause);
				            intent.putExtra("doctor", list.get(arg2).doctor);
				            intent.putExtra("title", list.get(arg2).title);
				            intent.putExtra("num", "2");
//				            GuigeData  bean=  list_ll.get(arg2);
//						    Bundle bundle = new Bundle();
//						    bundle.putSerializable("bean", bean);
//						    intent.putExtras(bundle);
							startActivity(intent);
				        }
				    });
				break;
			case 1:
				try{
				
				ArrayList<MyAssetsBean> list_lb = (ArrayList<MyAssetsBean>) msg.obj;
				System.out.println("======title===============" + list_lb.get(0).title);
//				Intent intent = new Intent(HealthGunaActivity.this,SymptomsJieShaoActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putString("city", CITY_CODE);
//				bundle.putString("name", view.getText().toString());
//				bundle.putInt("id", Integer.parseInt(id));
//				intent.putExtras(bundle);
//				intent.putExtra("shaixuan", "");
//				startActivity(intent);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			default:
				break;
			}
		};
	};

	/**
	 * 列表数据解析
	 */

	private void load_gridview() {
		
			list = new ArrayList<GuigeBean>();
		    AsyncHttp.get(RealmName.REALM_NAME_LL + "/get_test_solution_list?" +
				"top=8&where=is_hot=1",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====================二级值1" + arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							String info = object.getString("info");
							if (status.equals("y")) {
								JSONArray jsonArray = object.getJSONArray("data");
								len = jsonArray.length();
								for (int i = 0; i < jsonArray.length(); i++) {
									JSONObject json = jsonArray.getJSONObject(i);
									data_ll = new GuigeBean();
									data_ll.id = json.getString("id");
									data_ll.title = json.getString("title");
									data_ll.icon_url = json.getString("icon_url");
									data_ll.summary = json.getString("summary");//总结
									data_ll.proposal = json.getString("proposal");//生活建议
									data_ll.cause = json.getString("cause");//形成原因
									data_ll.doctor = json.getString("doctor");//何时就医
									list.add(data_ll);
								}
							} else {
								Toast.makeText(HealthGunaActivity.this, info, 200).show();
							}
							Message msg = new Message();
							msg.what = 0;
							msg.obj = list;
							handler.sendMessage(msg);
							loadzhengzhuang();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}, null);
	}
	
	
	/**
	 * 解析列表数据
	 */
	private void loadzhengzhuang() {
		list_l = new ArrayList<GuigeBean>();
		list_lb  = new ArrayList<MyAssetsBean>();
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_test_lesson_solustion_list?channel_name=physique&parent_id=0",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						System.out.println("=====规格数据====================="+arg1);
						
						try {
							JSONObject object = new JSONObject(arg1);
							JSONArray jobt = object.getJSONArray("data");
							for (int i = 0; i < jobt.length(); i++) {
								JSONObject obj= jobt.getJSONObject(i);
								
								String child = obj.getString("child");
								System.out.println("=====1值====================="+child);
								if (child.length() > 0) {
//								JSONArray jaArray = obj.getJSONArray(child);
								JSONArray jaArray = new JSONArray(child);
					    		for (int j = 0; j < jaArray.length(); j++){
					    		JSONObject objc= jaArray.getJSONObject(j);
					    		md = new GuigeData();
								md.setTitle(objc.getString("title"));
								JSONArray jay = objc.getJSONArray("solution");
								md.setList(new ArrayList<GuigeBean>());
								for (int k = 0; k < jay.length(); k++) {
						    	JSONObject obt= jay.getJSONObject(k);
					    		mb = new GuigeBean();
					    		mb.setTitle(obt.getString("title"));
					    		mb.setIcon_url(obt.getString("icon_url"));
					    		mb.summary = obt.getString("summary");
					    		mb.proposal = obt.getString("proposal");//生活建议
					    		mb.cause = obt.getString("cause");//形成原因
					    		mb.doctor = obt.getString("doctor");//何时就医
//					    		list_l.add(mb);
					    		md.getList().add(mb);
					    		JSONArray ja = obt.getJSONArray("articles");
					    		mb.setList(new ArrayList<GuigellBean>());
								for (int y = 0; y < ja.length(); y++) {
						    	JSONObject joct= ja.getJSONObject(y);
//						    	data = new MyAssetsBean();
//						    	data.id = joct.getString("id");
//						    	data.title = joct.getString("title");
//						    	data.img_url = joct.getString("img_url");
//						    	list_lb.add(data);
						    	mbll = new GuigellBean();
						    	mbll.article_id = joct.getString("article_id");
						    	mbll.title = joct.getString("title");
						    	mbll.img_url = joct.getString("img_url");
						    	mbll.item_id = joct.getString("item_id");
						    	mb.getList().add(mbll);
								}
						    	
								}
								list_ll.add(md);
					    		}
					    		System.out.println("=====11====================="+list_ll.size());
								}else {
									System.out.println("=====22=====================");
								}
								}
						} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
							
							HealthPavilionAdapter adapter = new HealthPavilionAdapter(list_ll,list_l,getApplicationContext(), handler);
							new_list.setAdapter(adapter);
							setListViewHeightBasedOnChildren(new_list);  
//							adapter.notifyDataSetChanged();
							
					}
				}, HealthGunaActivity.this);
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
