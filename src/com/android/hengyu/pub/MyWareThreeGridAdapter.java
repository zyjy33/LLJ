package com.android.hengyu.pub;

import java.util.ArrayList;

import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.WareClassifyThreeData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyWareThreeGridAdapter extends BaseAdapter {

	private ArrayList<WareClassifyThreeData> list;
	private Context context;
	private ImageLoader loader;

	public MyWareThreeGridAdapter(ArrayList<WareClassifyThreeData> list,
			Context context,ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.loader = loader;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LinearLayout.inflate(context,
					R.layout.griditem_menu_classify_three, null);
		}
		ImageView img = (ImageView) convertView
				.findViewById(R.id.img_ware_three);

		TextView tv_name = (TextView) convertView
				.findViewById(R.id.tv_ware_name_three);
		TextView tv_money = (TextView) convertView
				.findViewById(R.id.tv_ware_money_three);
	loader.displayImage(RealmName.REALM_NAME + "/admin/"
						+ list.get(position).proThumbnailImg, img);

		tv_name.setText(list.get(position).proName);
		tv_money.setText(list.get(position).retailPrice);

		return convertView;
	}

}
