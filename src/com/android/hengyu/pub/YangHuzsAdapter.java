package com.android.hengyu.pub;


import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.hengyu.ui.BaseViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class YangHuzsAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList datatb1;
	private ImageLoader imageLoader;
	public YangHuzsAdapter(ImageLoader imageLoader) {
		// TODO Auto-generated constructor stub
		this.imageLoader = imageLoader;
	} 

	public YangHuzsAdapter(ArrayList datatb1, Context mContext) {
		super();
		this.datatb1 = datatb1;
		this.mContext = mContext;
		Log.i("datatb1", datatb1+"");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datatb1.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datatb1.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.yhzstp_item, parent, false);
		}
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		
		
        ImageLoader imageLoader=ImageLoader.getInstance();
//        imageLoader.displayImage((String) Config.URL_IMG+datatb1.get(position),iv);
		return convertView;
	}

}

