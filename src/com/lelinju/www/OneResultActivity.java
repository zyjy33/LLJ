package com.lelinju.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.movie.adapter.OneResultAdapter;
import com.hengyushop.movie.adapter.OneResultBean;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

public class OneResultActivity extends BaseActivity{
	private ListView listview;
	private OneResultAdapter resultAdapter;
	private TextView jiexiaoj;
	private ArrayList<OneResultBean> lists;
	private Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 1:
				//�������ݷ��ʳɹ�֮�󷵻ص���Ϣ
				  ArrayList<OneResultBean> lists = (ArrayList<OneResultBean>) msg.obj;
				resultAdapter.putData(lists);
				//AnnouncedTime
				jiexiao.setText("��ֹ����ʱ�䡾"+AnnouncedTime+"��");
				//
				jiexiaoj.setText("���"+lists.size()+"��ȫվ����ʱ���¼");
				break;
			default:
				break;
			}
		};
	};
	String AnnouncedTime ;
	private TextView jiexiao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.one_result_activity);
		listview = (ListView) findViewById(R.id.listview);
		lists = new ArrayList<OneResultBean>();
		resultAdapter = new OneResultAdapter(OneResultActivity.this, lists, imageLoader);
		listview.setAdapter(resultAdapter);
		jiexiaoj = (TextView) findViewById(R.id.jiexiaoj);
 
		jiexiao = (TextView) findViewById(R.id.jiexiao);
		Map<String, String> params = new HashMap<String, String>();
		params.put("act", "GetLuckReleaseRecords");
		params.put("yth", "");
		params.put("productItemId", getIntent().getStringExtra("id"));
		params.put("luckDrawBatchOrderNumber", getIntent().getStringExtra("idex"));
		/*
		params.put("act", "GetLuckReleaseRecords");
		params.put("yth", "");
		params.put("productItemId", getIntent().getStringExtra("id"));
		params.put("luckDrawBatchOrderNumber", "1");
		*/
		//mi/getdata.ashx?act=GetLuckReleaseRecords&yth=test��Ϊ��&ProductItemId=1&LuckDrawBatchOrderNumber=�ѽ����ĳ齱
		//mi/getdata.ashx?act=GetLuckYiYuanJuGouAnnounceRecords&yth=test��Ϊ��&ProductItemId=1
		AsyncHttp.post_1(RealmName.REALM_NAME+"/mi/getdata.ashx", params, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int arg0, String arg1) {
				super.onSuccess(arg0, arg1);
				try {
					JSONObject jsonObject = new JSONObject(arg1);
					String status = jsonObject.getString("status");
					  AnnouncedTime = jsonObject.getString("AnnouncedTime");
					if(status.equals("1")){
						JSONArray array = jsonObject.getJSONArray("items");
						ArrayList<OneResultBean> lists = new ArrayList<OneResultBean>();
						int len = array.length();
						for(int i=0;i<len;i++){
							if(i!=0){
								JSONObject object = array.getJSONObject(i);
								OneResultBean bean = new OneResultBean();
								bean.setCode(object.getString("HengYuCode"));
								bean.setComplete(object.getString("proName"));
								bean.setEnd_time(object.getString("LuckDrawTime"));
								bean.setLuck(object.getString("LuckDrawTimeFormat"));
								bean.setName(object.getString("username"));
								lists.add(bean);
							}
						}
						Message msg = new Message();
						msg.what = 1;
						msg.obj = lists;
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		//mi/getdata.ashx?act=GetLuckReleaseRecords&yth=test��Ϊ��&ProductItemId=1&LuckDrawBatchOrderNumber=�ѽ����ĳ齱
	}
}
