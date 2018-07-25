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
import com.hengyushop.entity.CollectWareData;
import com.hengyushop.entity.UserAddressData;
import com.hengyushop.entity.WareData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

public class XiuGaiDizhiAdapter extends BaseAdapter {

	private ArrayList<UserAddressData> list;
	private Context context;

	private ImageLoader loader;

	public XiuGaiDizhiAdapter(ArrayList<UserAddressData> list, Context context,ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.loader = loader;
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
//		tv_price.setText("£§" + list.get(position).retailPrice);
//		tv_total.setText(" ’≤ÿ»À∆¯      " + list.get(position).collectTotal);
//		loader.displayImage(
//				RealmName.REALM_NAME + "/admin/"
//						+ list.get(position).proFaceImg, image);
		
//		tv_name.setText(list.get(position).title);
//		loader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).img_url, image);


		return convertView;
	}
}
