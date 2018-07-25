package com.android.hengyu.pub;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.hengyu.ui.MyGridView;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.lelinju.www.R;

public class OrderListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GuigeData> liDos;
	private Handler handler;
	OrderXqAdapter childAdapter;
	public OrderListAdapter(ArrayList<GuigeData> liDos, Context context,
			Handler handler) {
		this.liDos = liDos;
		this.context = context;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return liDos.size();
	}

	public void setContact() {

		notifyDataSetChanged();
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
	public View getView(int arg0, View view, ViewGroup arg2) {
		// System.out.println(users.size()+"頭像"+users.get(arg0).getImg());
		ViewHolder holder;
		try {
			
		if (view == null || view.getTag(R.drawable.ic_launcher + arg0) == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.wide_market_item,null);
			holder.name = (TextView) view.findViewById(R.id.tagname);
			holder.item_img = (GridView) view.findViewById(R.id.tagvalue);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag(R.drawable.ic_launcher + arg0);
		}

		holder.name.setText(liDos.get(arg0).getTitle());
		
		final ArrayList<GuigeBean> items = liDos.get(arg0).getList();
		childAdapter = new OrderXqAdapter(items, context, handler);
		holder.item_img.setAdapter(childAdapter);
		
//		holder.item_img.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//              	try {
//              		childAdapter.setSeclection(arg2);
//            		childAdapter.notifyDataSetChanged();
//            		
//              	} catch (Exception e) {
//    				// TODO: handle exception
//            		e.printStackTrace();
//    			}
//            }
//        });
     	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return view;
	}

	public class ViewHolder {
		private TextView name;
		private GridView item_img;
	}
}
