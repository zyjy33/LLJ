package com.hengyushop.airplane.adapter;


import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.hengyushop.entity.JuTuanGouData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class JuTuanAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<JuTuanGouData> list;
	private int clickTemp = 0;
//	private ArrayList datatupian;
	private Handler handler;
	private ImageLoader imageLoader;
	public static ArrayList<String> list_id = new ArrayList<String>();
	public static String tuangoujia,tuanshu,id;
//	public static int tuanshu;
	public JuTuanAdapter(ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.imageLoader = imageLoader;
	} 

	public JuTuanAdapter(ArrayList<JuTuanGouData> list,Context mContext, Handler handler) {
		super();
		this.list = list; 
		this.handler = handler;
//		this.datatupian = datatupian;
		this.mContext = mContext;
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
	
	public void setSeclection(int position) {
		clickTemp = position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.jutuanjia_item, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.btn_aaa1);
		TextView tv2 = BaseViewHolder.get(convertView, R.id.btn_aaa2);
//		LinearLayout ll_buju = BaseViewHolder.get(convertView, R.id.ll_kj_buju);
		
//		String is_default = list.get(position).getIs_default();
//		if (is_default.equals("1")) {
//			clickTemp = 0;
//		}
		
		tv.setText(list.get(position).getPeople()+"人成团"); 
//		
		tv2.setText("聚团价￥"+list.get(position).getPrice()); 
		
		if (clickTemp == position) {
			convertView.setBackgroundResource(R.drawable.bg_red_3_5_tuangoujia);//zangfutiaoli 
//			tv.setTextColor(Color.RED);
//			tv2.setTextColor(Color.RED);
			tv.setTextColor(Color.WHITE);
			tv2.setTextColor(Color.WHITE);
			
			tuangoujia = list.get(position).getPrice();
			tuanshu = list.get(position).getPeople();
			id = list.get(position).getId();
			list_id.add(list.get(position).getId());
			
			Message message = new Message();
			message.what = 0;
			handler.sendMessage(message);
		} else {
//			convertView.setBackgroundColor(Color.TRANSPARENT);
			convertView.setBackgroundResource(R.drawable.bg_red_3_5_tuangoujia_ll);//julegou_xuankuang 
			tv.setTextColor(Color.BLACK);
			tv2.setTextColor(Color.BLACK);
	    }
		
//        ImageLoader imageLoader=ImageLoader.getInstance();
//        imageLoader.displayImage((String) Config.URL_IMG+datatupian.get(position),iv);
		return convertView;
	}

}

