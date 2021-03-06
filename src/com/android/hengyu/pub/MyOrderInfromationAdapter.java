package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.OrderInfromationData;
import com.hengyushop.entity.TicketData;
import com.hengyushop.entity.WareInformationData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

public class MyOrderInfromationAdapter extends BaseAdapter {

	private ArrayList<OrderInfromationData> list;
	private Context context;
	private ImageLoader imageLoader;

	public MyOrderInfromationAdapter(ArrayList<OrderInfromationData> list,
			Context context, ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.imageLoader = imageLoader;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		// return list.size();
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
					R.layout.listitem_orderinfromation, null);
		}
		TextView co = (TextView) convertView.findViewById(R.id.im_co);
		TextView pr = (TextView) convertView.findViewById(R.id.im_pr);
		TextView na = (TextView) convertView.findViewById(R.id.im_na);
		ImageView im = (ImageView) convertView.findViewById(R.id.im_im);
		imageLoader.displayImage(
				RealmName.REALM_NAME + "/admin/" + list.get(position).image, im);
		co.setText("共" + list.get(position).count + "件商品");
		pr.setText("￥" + list.get(position).Price);
		na.setText(list.get(position).proName);
		return convertView;
	}
}
