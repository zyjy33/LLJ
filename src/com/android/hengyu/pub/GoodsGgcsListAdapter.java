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
import com.hengyushop.entity.XiangqingData;
import com.lelinju.www.R;

/**
 * 规格参数
 * 
 */
public class GoodsGgcsListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<XiangqingData> list;
    private AQuery aQuery;
	public GoodsGgcsListAdapter(ArrayList<XiangqingData> list,Context context) {
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
					R.layout.goods_ggcs_item, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		TextView tv_ggcs_1 = BaseViewHolder.get(convertView, R.id.tv_ggcs_1);
//		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		
		tv.setText(list.get(position).getTitle()); 
		tv_ggcs_1.setText(list.get(position).getContent()); 
//		aQuery.id(iv).image(RealmName.REALM_NAME_HTTP + list.get(position).icon_url);
		
		return convertView;
	}

}

