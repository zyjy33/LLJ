package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.CollectWareData;
import com.hengyushop.entity.WareData;
import com.lelinju.www.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyCollectWareAdapter extends BaseAdapter {

	private ArrayList<CollectWareData> list;
	private Context context;
	public static AQuery query;
	private ImageLoader loader;

	public MyCollectWareAdapter(ArrayList<CollectWareData> list, Context context,ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.loader = loader;
		query = new AQuery(context);
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
			convertView = LinearLayout.inflate(context,
					R.layout.listitem_collect_ware, null);
		}
		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
		TextView tv_name = (TextView) convertView
				.findViewById(R.id.tv_ware_name);
		TextView tv_price = (TextView) convertView
				.findViewById(R.id.tv_ware_price);
		TextView tv_total = (TextView) convertView
				.findViewById(R.id.tv_ware_total);

//		tv_name.setText(list.get(position).proName);
//		tv_price.setText("￥" + list.get(position).price);
//		tv_total.setText("收藏人气      " + list.get(position).collectTotal);
//		loader.displayImage(
//				RealmName.REALM_NAME + "/admin/"
//						+ list.get(position).proFaceImg, image);
		
		if (!list.get(position).summary.equals("")) {
			tv_price.setText(list.get(position).add_time);
//			tv_price.setTextColor(Color.BLACK);
		}else {
		if (list.get(position).price.equals("null")) {
			tv_price.setText("价格：￥0.0");
		}else {
			tv_price.setText("价格：￥" + list.get(position).price);
		}
		}
		
		tv_name.setText(list.get(position).title);
		
//		loader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).img_url, image);
		query.id(image).image(RealmName.REALM_NAME_HTTP + list.get(position).img_url);


		return convertView;
	}
}
