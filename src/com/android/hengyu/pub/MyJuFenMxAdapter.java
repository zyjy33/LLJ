package com.android.hengyu.pub;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.MyJuFenData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

public class MyJuFenMxAdapter extends BaseAdapter {
    String haoma;
	private ArrayList<MyJuFenData> list;
	private ArrayList datadz1;
	private Context context;
	private ImageLoader loader;
    AQuery aQuery;
	public MyJuFenMxAdapter(ArrayList<MyJuFenData> list,ArrayList datadz1, Context context,ImageLoader loader) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.datadz1 = datadz1;
		this.loader = loader;
		aQuery = new AQuery(context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LinearLayout.inflate(context,R.layout.listitem_my_jufen, null);
		}
		
		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_ware_name);
//		TextView tv_ware_time = (TextView) convertView.findViewById(R.id.tv_ware_time);
//		TextView tv_price = (TextView) convertView.findViewById(R.id.tv_ware_price);
//		TextView tv_total = (TextView) convertView.findViewById(R.id.tv_ware_total);
		
		String haoma_ll = list.get(position).mobile;
		haoma = haoma_ll.substring(0, 3) + "****" + haoma_ll.substring(7, 11);
		System.out.println("haoma====================="+haoma);
		
		
		String real_name = list.get(position).real_name;//
		String nick_name = list.get(position).nick_name;//
		System.out.println("real_name====================="+real_name);
		System.out.println("nick_name====================="+nick_name);
		if (!real_name.equals("")) {
			tv_name.setText(real_name+"（"+haoma+")");
		}else if (!nick_name.equals("")) {
			tv_name.setText(nick_name+"（"+haoma+")");
		}
		else{
			tv_name.setText("匿名用户（"+haoma+")");
		}
		
		
		System.out.println("================================"+position);
		
//		String avatar = list.get(position).avatar;//
//		aQuery.id(image).image(RealmName.REALM_NAME_HTTP + list.get(position).login_sign);
		
		String data_tx = (String) datadz1.get(position);
		System.out.println("data_tx================================"+data_tx);
		aQuery.id(image).image(data_tx);

		return convertView;
	}
}
