package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.entity.GuigeBean;
import com.lelinju.www.R;

public class OrderXqAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GuigeBean> items;
	private Handler handler;
	private int clickTemp = -1;
	public OrderXqAdapter(ArrayList<GuigeBean> items, Context context,
			Handler handler) {
		this.items = items;
		this.context = context;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	public void setContact() {

		notifyDataSetChanged();
	}

	public void setSeclection(int arg0) {
		clickTemp = arg0;
	}
	
	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
//	public View getView(int arg0, View view, ViewGroup arg2) {
//		// System.out.println(users.size()+"î^Ïñ"+users.get(arg0).getImg());
//		final ViewHolder holder;
//		if (view == null || view.getTag(R.drawable.ic_launcher + arg0) == null) {
//			holder = new ViewHolder();
//			view = LinearLayout.inflate(context, R.layout.category_textview,null);
//			holder.category_textview = (TextView) view.findViewById(R.id.category_textview);
//			view.setTag(holder);
//		} else {
//			holder = (ViewHolder) view.getTag(R.drawable.ic_launcher + arg0);
//		}
//		holder.category_textview.setText(items.get(arg0).getTitle());
//		
//		if (clickTemp == arg0) {
////			convertView.setBackgroundResource(R.drawable.julegou_xuankuang);//julegou_xuankuang  zangfutiaoli
////			view = LayoutInflater.from(context).inflate(R.layout.gridview_item1, arg2, false);
////			TextView tv_zhuti = BaseViewHolder.get(view, R.id.btn_aaa1);
////			holder.category_textview.setText(items.get(arg0).getTitle());
////			tv_zhuti.setTextColor(Color.RED);
//			
//			holder.category_textview.setTextColor(Color.RED);
//			
//		} else {
////			view = LayoutInflater.from(context).inflate(R.layout.gridview_item0, arg2, false);
////			TextView tv1 = BaseViewHolder.get(view, R.id.btn_aaa1);
////			holder.category_textview.setText(items.get(arg0).getTitle());
////			tv1.setTextColor(Color.GRAY);
////			convertView.setBackgroundResource(R.drawable.zangfutiaoli);
//			
//			holder.category_textview.setTextColor(Color.GRAY);
//			
//	    }
//		
//		holder.category_textview.setTag(items.get(arg0).getId());
//		holder.category_textview.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
////				Message msg = new Message();
////				msg.what = 1;
////				msg.obj = holder.category_textview;
////				handler.sendMessage(msg);
//			}
//		});
//		return view;
//	}
	
	public View getView(int arg0, View view, ViewGroup arg2) {
		// System.out.println(users.size()+"î^Ïñ"+users.get(arg0).getImg());
		final ViewHolder holder;
		if (view == null || view.getTag(R.drawable.ic_launcher + arg0) == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.category_textview,null);
			holder.category_textview = (TextView) view.findViewById(R.id.category_textview);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.ic_launcher + arg0);
		}
		holder.category_textview.setText(items.get(arg0).getTitle());
		holder.category_textview.setTag(items.get(arg0).getId());
		holder.category_textview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Message msg = new Message();
//				msg.what = 1;
//				msg.obj = holder.category_textview;
//				handler.sendMessage(msg);
				
//				if (clickTemp == arg0) {
//			      holder.category_textview.setTextColor(Color.RED);
//		        } else {
//			      holder.category_textview.setTextColor(Color.GRAY);
//	            }
				
			}
		});
		return view;
	}

	public class ViewHolder {
		private TextView category_textview;
	}
}
