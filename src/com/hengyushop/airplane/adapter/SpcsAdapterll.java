package com.hengyushop.airplane.adapter;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.hengyushop.entity.GuigeData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class SpcsAdapterll extends BaseAdapter {
	private Context mContext;
	private ArrayList datatb1;
	private ArrayList<GuigeData> list;
	
	public SpcsAdapterll(ArrayList<GuigeData> list, Context context) {
		this.list = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		if (list.size()<1) {

			return 0;
		}else{

			return list.size();
		}
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
		try {
			
			// TODO: handle exception
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.gridview_item0, parent, false);
		}
		
		TextView tv = BaseViewHolder.get(convertView, R.id.btn_aaa1);
		
		
		tv.setText(list.get(position).getTitle()); 
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		return convertView;
	}

}

