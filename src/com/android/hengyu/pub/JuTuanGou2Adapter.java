package com.android.hengyu.pub;

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
import com.hengyushop.entity.EnterpriseData;
import com.hengyushop.entity.JuTuanGouData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

public class JuTuanGou2Adapter extends BaseAdapter {

	private Context mContext;
	private List<JuTuanGouData> List;
	private LayoutInflater mInflater;
	
	public JuTuanGou2Adapter(Context context, List<JuTuanGouData> list){
		this.List = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
//		System.out.println("============="+list.size()+1);
	}
	
	@Override
	public int getCount() {
		if (List.size()<1) {
			return 0;
		}else{
			return List.size();
			
		}
	}
	
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return List.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder =  new ViewHolder();
			convertView = mInflater.inflate(R.layout.jutuangou_item2, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.tv_titel = (TextView) convertView.findViewById(R.id.tv_titel);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_groupon_price = (TextView) convertView.findViewById(R.id.tv_groupon_price);
			holder.tv_tuan = (TextView) convertView.findViewById(R.id.tv_tuan);
			holder.tv_anniu = (TextView) convertView.findViewById(R.id.tv_anniu);
			
//			tv_titel,tv_price,tv_tuan,tv_groupon_price,tv_anniu;
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		 try {
//		  System.out.println("List======================"+List.size());
			 
		 System.out.println("个数1======================"+position);
		
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage((String) RealmName.REALM_NAME_HTTP+List.get(position).getImg_url(),holder.img);
		
		  holder.tv_titel.setText(List.get(position).getTitle());
		  holder.tv_price.setText("￥"+List.get(position).getPrice());
		  holder.tv_groupon_price.setText("单买价￥"+List.get(position).getGroupon_price());
		  holder.tv_groupon_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
		  holder.tv_tuan.setText(List.get(position).getPeople()+"人团");
//		  holder.tv_anniu.setText(List.get(position).title);
		  System.out.println("List.get(position).getTitle()======================"+List.get(position).getTitle());
		 } catch (Exception e) {
				// TODO: handle exception
			 e.printStackTrace();
			}
		return convertView;
	}


	class ViewHolder{
		ImageView img;
		TextView tv_titel,tv_price,tv_tuan,tv_groupon_price,tv_anniu;
		RadioButton radioButton;
	}
}
