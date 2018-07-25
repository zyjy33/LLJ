package com.android.hengyu.pub;


import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.MyAssetsBean;
import com.lelinju.www.R;

/**
 * @Description:gridview��Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class MyGridllAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GuigeBean> list;
    private AQuery aQuery;
	public MyGridllAdapter(ArrayList<GuigeBean> list,Context context) {
		super();
		this.list = list; 
		this.context = context;
		aQuery = new AQuery(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.grid_item_ll, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		
		tv.setText(list.get(position).title); 
		aQuery.id(iv).image(RealmName.REALM_NAME_HTTP + list.get(position).icon_url);
		
		return convertView;
	}

}

