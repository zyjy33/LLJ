package com.android.hengyu.post;

import java.text.ParseException;
import java.util.ArrayList;
import com.hengyushop.json.HttpUtils;
import com.lelinju.www.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PostStandAdapter extends BaseAdapter {
	private ArrayList<PostStandDo> list;
	private Context context;

	public PostStandAdapter(ArrayList<PostStandDo> list, Context context) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if (view == null) {
			holder = new ViewHolder();
			view = LinearLayout.inflate(context, R.layout.poststand_item, null);
			holder.v1 = (TextView) view.findViewById(R.id.item_time);
			holder.v2 = (TextView) view.findViewById(R.id.item_title);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		try {

			holder.v1.setText(HttpUtils.getSimpleTime(list.get(position)
					.getTime(), "MM-dd"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		holder.v2.setText(list.get(position).getTitle());
		return view;
	}

	public class ViewHolder {
		private TextView v1, v2;
	}
}
