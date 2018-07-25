package com.hengyushop.demo.home;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.pub.MyListViewAdapter;
import com.android.hengyu.pub.MyListViewAdapter.ViewHolder;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.domain.TuiGuangBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

public class JuFaFaXunaZheActivityll extends BaseActivity implements OnClickListener,OnItemClickListener {

	private ArrayList<TuiGuangBean> list;
	private ArrayList<TuiGuangBean> list_ll;
	private DialogProgress progress;
	private ListView listView;
	MyListViewAdapter adapter;
	int len = 1;
	String id,exam_id,title,title_id,datatype;
	String id2 = "1726";
	AQuery aQuery;
	private TextView tv_zhuti,tv_ware_name,tv_timu;
	private ImageView iv_tupian;
	private TextView tv_shangyiti,tv_shangyitill,tv_xiayiti,tv_xiayiti_ll;
	private ArrayList<String> list_name = new ArrayList<String>();
	private ArrayList<String> list_id;
	ArrayList<Group> groups;  
	String str1="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acticity_ceshi_xuanzhe);
		progress = new DialogProgress(JuFaFaXunaZheActivityll.this);
//		progress.CreateProgress();
		aQuery = new AQuery(this);
		tv_zhuti = (TextView) findViewById(R.id.tv_zhuti);
		tv_ware_name = (TextView) findViewById(R.id.tv_ware_name);
		iv_tupian = (ImageView) findViewById(R.id.iv_tupian);
		exam_id = getIntent().getStringExtra("exam_id");
		initdata();
		loadCate(exam_id);
	}
	private void initdata() {
		tv_timu = (TextView) findViewById(R.id.textView1);
		tv_timu.setText("推广传播");
		listView = (ListView) findViewById(R.id.new_list);
		tv_shangyiti = (TextView) findViewById(R.id.tv_shangyiti);
		tv_shangyitill = (TextView) findViewById(R.id.tv_shangyitill);
		tv_xiayiti = (TextView) findViewById(R.id.tv_xiayiti);
		tv_xiayiti_ll = (TextView) findViewById(R.id.tv_xiayiti_ll);
		tv_xiayiti.setOnClickListener(this);
		tv_xiayiti_ll.setOnClickListener(this);
		tv_shangyiti.setOnClickListener(this);
//		tv_shangyitill.setOnClickListener(this);
		
		listView.setOnItemClickListener(this);
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	
	//商品列表
		private void loadCate(String exam_id2){
			
			AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_test_exam_model?" +
	                "exam_id="+exam_id2+"", new AsyncHttpResponseHandler(){
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
						String summary = obj.getString("summary");
						String img_url = obj.getString("img_url");
						tv_zhuti.setText(summary);
						aQuery.id(iv_tupian).image(RealmName.REALM_NAME + img_url);
	                    } else {
	                    	
						}
						
						load_list(exam_id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, null);
	    }
		
	Handler handler = new Handler() {
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				System.out.println("=================5="+list.size());
				
				if (len == 1) {
					System.out.println("=====================1");
					tv_shangyiti.setVisibility(View.GONE);
					tv_shangyitill.setVisibility(View.VISIBLE);
				}
				
//				if (list_id.size() == len) {
//					System.out.println("=====================2");
//					tv_xiayiti.setVisibility(View.VISIBLE);
//					tv_xiayiti_ll.setVisibility(View.GONE);
//				}
				
				adapter = new MyListViewAdapter(list, getApplicationContext());
				listView.setAdapter(adapter);
				progress.CloseProgress();
				break;
				
			case 1:
				break;

			default:
				break;
			}
		};
	};
	
	
	/**
	 * 第1个列表数据解析
	 */
	private void load_list(String exam_id) {
		System.out.println("==len==================="+len);
		AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_test_question_list?" +
                "exam_id="+exam_id+"", new AsyncHttpResponseHandler(){
				
							@Override
							public void onSuccess(int arg0, String arg1) {
								// TODO Auto-generated method stub
								super.onSuccess(arg0, arg1);
//								System.out.println("=====================二级值1"+arg1);
								try {
									JSONObject jsonObject = new JSONObject(arg1);
									String status = jsonObject.getString("status");
									if (status.equals("y")) {
									JSONArray jsonArray = jsonObject.getJSONArray("data");
									for (int i = 0; i < jsonArray.length(); i++) {
										JSONObject object = jsonArray.getJSONObject(i);
//										data.id = object.getString("id");
//										title = object.getString("title");
										String child = object.getString("child");
										JSONArray ja = new JSONArray(child);
//										list_title.add(title);
										list_id = new ArrayList<String>();
										System.out.println("=====================list_id.size()=="+list_id.size());
										for (int j = 0; j < ja.length(); j++) {
											JSONObject jo = ja.getJSONObject(j);
											title_id = jo.getString("title");
											list_id.add(title_id);
										}
										for (int j = 0; j < len; j++) { //ja.length(); j++) {
											JSONObject jo = ja.getJSONObject(j);
											title = jo.getString("title");
											datatype = jo.getString("datatype");
											String item = jo.getString("item");
											JSONArray jaot = new JSONArray(item);
//											list_id.add(title_id);
											list = new ArrayList<TuiGuangBean>();
											for (int k = 0; k < jaot.length(); k++) {
												JSONObject jot = jaot.getJSONObject(k);
												TuiGuangBean data = new TuiGuangBean();
												data.question_id = jot.getString("question_id");
												data.title = jot.getString("title");
												data.name = jot.getString("name");
												list.add(data);
											}
										}
									}
				                    }else {
									}
									
									tv_ware_name.setText(title);
									if (datatype.contains("多选题")) {
										handler.sendEmptyMessage(0);
									}else {
										
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}, null);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_shangyiti:
			System.out.println("=====================二级值=="+len);
			System.out.println("=====================list_id.size()=="+list_id.size());
			HashMap<Integer, Boolean> map1 = MyListViewAdapter.getIsSelected();
			System.out.println("=====map.size()================"+map1.size());
			str1="";
			for (int i = 0; i < map1.size(); i++) {
				if (map1.get(i)) {
					String fegefu = str1.length()>0?"":"";
					str1 += fegefu+list.get(i).question_id+"_"+list.get(i).name;
					System.out.println("=====str1================"+str1);
				}
			}
			MyListViewAdapter.getIsSelected().get("");
//			Toast.makeText(getApplicationContext(),"已选中了" + str1 + "", Toast.LENGTH_SHORT).show();
//			list_name.remove(str1);
//			StringBuffer str2 = new StringBuffer();
//	        for(String s:list_name){
//	        	str2.append(s+",");
//	        }
//	        str2.delete(str2.lastIndexOf(","),str2.length()); 
//	        System.out.println("id拼接之后---------------"+str2);
//			Toast.makeText(getApplicationContext(),"已选中了" + str2 + "", Toast.LENGTH_SHORT).show();
			
			len -= 1;
			if (list_id.size() > len) {
				load_list(exam_id);
				tv_xiayiti.setVisibility(View.GONE);
				tv_xiayiti_ll.setVisibility(View.VISIBLE);
			}
			
			break;
		case R.id.tv_xiayiti_ll:
			if (datatype.contains("多选题")) {
				
			
			groups = new ArrayList<Group>();  
			HashMap<Integer, Boolean> map = MyListViewAdapter.getIsSelected();
			System.out.println("=====map.size()================"+map.size());
			str1="";
			for (int i = 0; i < map.size(); i++) {
				if (map.get(i)) {
					String fegefu = str1.length()>0?"":"";
					str1 += fegefu+list.get(i).question_id+"_"+list.get(i).name;
//					System.out.println("=====str1================"+str1);
				}
			}
			if (str1.equals("")) {
				Toast.makeText(getApplicationContext(),"请选择答案", Toast.LENGTH_SHORT).show();
			}else {
			MyListViewAdapter.getIsSelected().get("");
//			Toast.makeText(getApplicationContext(),"已选中了" + str1 + "", Toast.LENGTH_SHORT).show();
			list_name.add(str1);
			
//			StringBuffer str = new StringBuffer();
//	        for(String s:list_name){
//	        	str.append(s+",");
//	        }
//	        str.delete(str.lastIndexOf(","),str.length()); 
//	        System.out.println("id拼接之后---------------"+str);
//	        Toast.makeText(getApplicationContext(),"已选中了" + str + "", Toast.LENGTH_SHORT).show();
	        
			if (list_id.size() > len) {
				len += 1;
				load_list(exam_id);
				tv_shangyiti.setVisibility(View.VISIBLE);
				tv_shangyitill.setVisibility(View.GONE);
			}
			}
			
            }else {
				
			}
			
			break;
		case R.id.tv_xiayiti:
			myPrice();
			break;

		default:
			break;
		}
	}
	
	/**
	 * 计算总价格的方法
	 * */
	public void myPrice() {
		HashMap<Integer, Boolean> map = MyListViewAdapter.getIsSelected();
//		System.out.println("=====map.size()================"+map.size());
		str1="";
		for (int i = 0; i < map.size(); i++) {
			if (map.get(i)) {
				String fegefu = str1.length()>0?",":"";
//				str1 += fegefu+(i);
				str1 += fegefu+list.get(i).question_id+"_"+list.get(i).name;
			}
		}
		
		if (str1.equals("")) {
			Toast.makeText(getApplicationContext(),"您还未选择答案", Toast.LENGTH_SHORT).show();
		}else {
		MyListViewAdapter.getIsSelected().get("");
		Toast.makeText(getApplicationContext(),"已选中了" + str1 + "", Toast.LENGTH_SHORT).show();
		list_name.add(str1);
		System.out.println("=====list_name.size()================"+list_name.size());
		StringBuffer str = new StringBuffer();
        for(String s:list_name){
        	str.append(s+",");
        }
        str.delete(str.lastIndexOf(","),str.length()); 
        System.out.println("id拼接之后---------------"+str);
		}
		
	}
	
	/**
	 * listview的item的选择的方法
	 * */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
		ViewHolder holder = (ViewHolder) view.getTag();
		// 改变CheckBox的状态
		holder.cb.toggle();
		// 将CheckBox的选中状况记录下来
		MyListViewAdapter.getIsSelected().put(position, holder.cb.isChecked());

	}
	

}
