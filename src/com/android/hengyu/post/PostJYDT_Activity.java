package com.android.hengyu.post;

import java.util.ArrayList;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;

public class PostJYDT_Activity extends BaseActivity {
	/**
	 * �����������
	 */
	private ListView post_list;
	private Button post_jydt_back;
	private PostStandAdapter adapter;
	private OnClickListener clickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.post_jydt_back:
				AppManager.getAppManager().finishActivity();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * �������
	 */
	@SuppressWarnings("unchecked")
	private void init() {
		post_jydt_back = (Button) findViewById(R.id.post_jydt_back);
		post_jydt_back.setOnClickListener(clickListener);
		post_list = (ListView) findViewById(R.id.post_list);
		final ArrayList<PostStandDo> list = (ArrayList<PostStandDo>) getIntent()
				.getExtras().get("list");
		adapter = new PostStandAdapter(list, getApplicationContext());
		post_list.setAdapter(adapter);
		post_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PostJYDT_Activity.this,
						PostStandDetail.class);
				intent.putExtra("ob", list.get(arg2));
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_jydt_layout);
		init();
	}
}
