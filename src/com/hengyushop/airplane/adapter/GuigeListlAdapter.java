package com.hengyushop.airplane.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hengyushop.demo.wec.MyGridView;
import com.lelinju.www.R;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class GuigeListlAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	private ArrayList data;
	private ArrayList data1;

	public GuigeListlAdapter(Context context, 
			ArrayList data, ArrayList data1) {
		// TODO Auto-generated constructor stub
		this.data = data;
		this.data1 = data1;
		this.mContext = context;
		this.inflater = LayoutInflater.from(context);
		// System.out.println("=====42====================="+list.size());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		Log.i("data", "=============" + list.size());
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);

	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup patent) {
		try {
			System.out.println("=====51=====================");
			// TODO Auto-generated method stub
			convertView = inflater.inflate(R.layout.guige_item, null);
			LinearLayout addview = (LinearLayout) convertView
					.findViewById(R.id.addview);
			TextView tv_zhuti = (TextView) convertView
					.findViewById(R.id.tv_zhuti);
			tv_zhuti.setText((String)data.get(position));
			
			// System.out.println("=====6====================="+zhou);
			
			
			MyGridView gridView = (MyGridView) convertView.findViewById(R.id.gridView);
					GouwucheAdapter MyAdapter = new GouwucheAdapter(data1,mContext);
					gridView.setAdapter(MyAdapter);
			addview.removeAllViews();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}


}
