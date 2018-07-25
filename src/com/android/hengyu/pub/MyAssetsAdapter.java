package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.my.MyAssetsActivity;
import com.hengyushop.entity.CollectWareData;
import com.hengyushop.entity.MyAssetsBean;
import com.hengyushop.entity.SpListData;
import com.hengyushop.entity.WareData;
import com.hengyushop.entity.XsgyListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

public class MyAssetsAdapter extends BaseAdapter {

	private ArrayList<MyAssetsBean> list;
	private Context context;
	private ImageLoader loader;

	public MyAssetsAdapter(ArrayList<MyAssetsBean> list, Context context,ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.loader = loader;
	}

	public void putData(ArrayList<MyAssetsBean> list){
		this.list = list;
		this.notifyDataSetChanged();
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
//			convertView = LinearLayout.inflate(context,R.layout.listitem_xsgy, null);
			convertView = LinearLayout.inflate(context,R.layout.listitem_myasste, null);
		}
		System.out.println("=====================二级值16");
//		TextView tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
		TextView tv_income = (TextView) convertView.findViewById(R.id.tv_income);
		TextView tv_add_time = (TextView) convertView.findViewById(R.id.tv_add_time);
		TextView tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);

		
		if (MyAssetsActivity.type.equals("1")) {
			if (list.get(position).income.equals("0.00")){
				tv_income.setText("-"+list.get(position).expense+"元");
			}else if (!list.get(position).income.equals("0.00")){
				tv_income.setText("+"+list.get(position).income+"元");
			}
		}else if (MyAssetsActivity.type.equals("4")) {
			if (list.get(position).income.equals("0.00")){
				tv_income.setText("-"+list.get(position).expense+"元");
			}else if (!list.get(position).income.equals("0.00")){
				tv_income.setText("+"+list.get(position).income+"元");
			}
		}else if (MyAssetsActivity.type.equals("2")) {
			if (list.get(position).income.equals("0.00")){
				tv_income.setText("-"+list.get(position).expense+"个");
			}else if (!list.get(position).income.equals("0.00")){
				tv_income.setText("+"+list.get(position).income+"个");
			}
		}else if (MyAssetsActivity.type.equals("3")) {
			if (list.get(position).income.equals("0.00")){
				tv_income.setText("-"+list.get(position).expense+"元");
			}else if (!list.get(position).income.equals("0.00")){
				tv_income.setText("+"+list.get(position).income+"元");
			}
		}
		
		
		tv_remark.setText(list.get(position).remark);
//		tv_user_name.setText(list.get(position).user_name);
//		tv_income.setText("+"+list.get(position).income+"元");
		tv_add_time.setText(list.get(position).add_time);
 

		return convertView;
	}
}
