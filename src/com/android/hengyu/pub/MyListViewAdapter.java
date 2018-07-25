package com.android.hengyu.pub;

import java.util.ArrayList;
import java.util.HashMap;

import com.lglottery.www.domain.TuiGuangBean;
import com.lelinju.www.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListViewAdapter extends BaseAdapter {
	// ������ݵ�list
	private ArrayList<TuiGuangBean> foodlist;
	// ��������CheckBox��ѡ��״��
	private static HashMap<Integer, Boolean> isSelected;
	// ������
	private Context context;
	// �������벼��
	private LayoutInflater inflater = null;

	// ������
	public MyListViewAdapter(ArrayList<TuiGuangBean> list, Context context) {
		this.context = context;
		this.foodlist = list;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		// ��ʼ������
		initDate();
	}

	// ��ʼ��isSelected������
	private void initDate() {
		for (int i = 0; i < foodlist.size(); i++) {
			getIsSelected().put(i, false);
		}
	}

	@Override
	public int getCount() {
		return foodlist.size();
	}

	@Override
	public Object getItem(int position) {
		return foodlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
		ViewHolder holder = null;
		if (convertView == null) {
			try {
				
			// ���ViewHolder����
			holder = new ViewHolder();
			// ���벼�ֲ���ֵ��convertview
			convertView = inflater.inflate(R.layout.ceshi_item, null);
			holder.txt1 = (TextView) convertView.findViewById(R.id.food_name);
			holder.cb = (CheckBox) convertView.findViewById(R.id.check_box);
			// Ϊview���ñ�ǩ
			convertView.setTag(holder);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else {
			// ȡ��holder
			holder = (ViewHolder) convertView.getTag();
		}
		// ��ȡ����
		TuiGuangBean food = foodlist.get(position);

		// ��������䵽��ǰconvertView�Ķ�Ӧ�ؼ���
//		holder.imageView.setImageResource(food.food_img);
		holder.txt1.setText(food.title);
		// ����list��TextView����ʾ
		// ����isSelected������checkbox��ѡ��״��
		holder.cb.setChecked(getIsSelected().get(position));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		MyListViewAdapter.isSelected = isSelected;
	}

	public static class ViewHolder {
		public TextView txt1;
		public TextView txt2;
		public ImageView imageView;
		public CheckBox cb;
	}
}