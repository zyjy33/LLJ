package com.hengyushop.airplane.adapter;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.android.hengyu.web.RealmName;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.ZhongAnYlBean;
import com.hengyushop.entity.ZhongAnYlData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class zaylAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<ZhongAnYlBean> items;
	private ImageLoader imageLoader;
	public zaylAdapter(ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.imageLoader = imageLoader;
	} 

	public zaylAdapter(ArrayList<ZhongAnYlBean> items2, Context mContext) {
		super();
		this.items = items2;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			
			// TODO: handle exception
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.zayl_item, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_zhuti);
		TextView tv_market_price = BaseViewHolder.get(convertView, R.id.tv_market_price);
		TextView tv_sell_price = BaseViewHolder.get(convertView, R.id.tv_sell_price);
		ImageView img_ware = BaseViewHolder.get(convertView, R.id.img_ware);;
		tv.setText(items.get(position).getTitle()); 
		tv_market_price.setText("￥"+items.get(position).getMarket_price()); 
		tv_sell_price.setText("￥"+items.get(position).getSell_price());
		tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
		 ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ items.get(position).getImg_url(),img_ware);
		
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		return convertView;
	}

}

