package com.hengyushop.airplane.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.SpListData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

public class GoodsMyGridViewAdaper extends BaseAdapter {

	private Context mContext;
	private ArrayList<SpListData> list;
	private LayoutInflater mInflater;
	public static AQuery mAq;
	public GoodsMyGridViewAdaper(ArrayList<SpListData> list,Context context){
		this.list = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mAq = new AQuery(context);
	}

	public void putData(ArrayList<SpListData> lists){
		this.list = lists;
		this.notifyDataSetChanged();
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
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		try {
			
		if (convertView == null) {
			holder =  new ViewHolder();
			convertView = mInflater.inflate(R.layout.gridview_goods_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.tv_biaoti = (TextView) convertView.findViewById(R.id.tv_biaoti);
			holder.tv_hengyu_money = (TextView) convertView.findViewById(R.id.tv_hengyu_money);
			holder.tv_shichangjia = (TextView) convertView.findViewById(R.id.tv_shichangjia);
			holder.tv_hongbao = (TextView) convertView.findViewById(R.id.tv_hongbao);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		System.out.println("list.get(position).getTitle()--------------------------"+list.get(position).getTitle());
		holder.tv_biaoti.setText(list.get(position).getTitle());
		holder.tv_hengyu_money.setText("��"+list.get(position).getSell_price());
		holder.tv_shichangjia.setText("��"+list.get(position).getMarket_price());
		holder.tv_hongbao.setText(list.get(position).getCashing_packet());
		holder.tv_shichangjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // �����г������ֵ��л���
        mAq.id(holder.img).image(RealmName.REALM_NAME_HTTP+list.get(position).getImg_url());
		
//        mAq.clear();
		} catch (Exception e) { 
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}
	

	class ViewHolder{
		ImageView img;
		TextView tv_biaoti;
		TextView tv_hengyu_money;
		TextView tv_shichangjia,tv_hongbao;
		RadioButton radioButton;
	}
}