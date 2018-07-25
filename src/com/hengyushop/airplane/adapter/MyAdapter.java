package com.hengyushop.airplane.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.MyShopingCartllAdapter.ViewHolder;
import com.hengyushop.entity.ShopCartData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	// ������ݵ�list
	private ArrayList<ShopCartData> list;
	// ��������CheckBox��ѡ��״��
	private static HashMap<Integer, Boolean> isSelected;
	// ������
	private Context context;
	// �������벼��
	private LayoutInflater inflater = null;
	public static List list_id = new ArrayList();
	private Handler handler;
	// ������
	public MyAdapter(ArrayList<ShopCartData> list, Context context) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		// ��ʼ������
		initDate();
	}

	// ��ʼ��isSelected������
	private void initDate() {
		for (int i = 0; i < list.size(); i++) {
			getIsSelected().put(i, false);
		}
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			// ���ViewHolder����
			holder = new ViewHolder();
			// ���벼�ֲ���ֵ��convertview
//			convertView = inflater.inflate(R.layout.listitem_shopping_cart, null);//listviewitem
			convertView = inflater.inflate(R.layout.listviewitem, null);
			holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
			holder.img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
			holder.tv_warename = (TextView) convertView.findViewById(R.id.tv_ware_name);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_money);
			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money2);
			
			// ��������
			holder.market_information_seps_add = (TextView) convertView.findViewById(R.id.market_information_seps_add);//����
			holder.market_information_seps_del = (TextView) convertView.findViewById(R.id.market_information_seps_del);//����
			holder.market_information_seps_num = (TextView) convertView.findViewById(R.id.market_information_seps_num);//����
			// Ϊview���ñ�ǩ
			convertView.setTag(holder);
		} else {
			// ȡ��holder
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tv_warename.setText(list.get(position).getTitle());
		holder.tv_size.setText("��" + list.get(position).getSell_price());
		holder.tv_money.setText("��" + list.get(position).getMarket_price());
		holder.market_information_seps_num.setText(list.get(position).getQuantity()+ "");
		ImageLoader imageLoaderll=ImageLoader.getInstance();
		imageLoaderll.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).getImg_url(),holder.img_ware);
		holder.tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		
		// ����isSelected������checkbox��ѡ��״��
		holder.cb.setChecked(getIsSelected().get(position));
		
		
		// ����list��TextView����ʾ
//		holder.tv.setText(list.get(position).getTitle());
		// ����isSelected������checkbox��ѡ��״��
		
	    // ����checkBox������ԭ����״̬�������µ�״̬  
        holder.cb.setOnClickListener(new View.OnClickListener() {  
  
            public void onClick(View v) {  
  
                if (isSelected.get(position)) {  
                    isSelected.put(position, false);  
                    setIsSelected(isSelected);  
					String cart_id = list.get(position).getId();
					list_id.remove(cart_id);
                    System.out.println("1111================");
                } else {  
                    isSelected.put(position, true);  
                    setIsSelected(isSelected);  
                    String cart_id = list.get(position).getId();
					list_id.add(cart_id);
                    System.out.println("2222================");
                }  
                for(int i=0;i<list_id.size();i++){
					 System.out.println("=====���Դ�1======================="+list_id.get(i));
			     } 
//				Message message2 = new Message();
//				message2.what = 400;
//				message2.obj = list_id;
//				handler.sendMessage(message2);
                
  
            }  
        }); 
        
        holder.cb.setChecked(getIsSelected().get(position));
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		MyAdapter.isSelected = isSelected;
	}

	public static class ViewHolder {
		TextView tv;
		public CheckBox cb;
		 ImageButton btn_order_cancle;
		 ImageView img_ware;
		 TextView tv_warename;
		 TextView tv_color;
		 TextView tv_1;
		 TextView tv_2;
		 TextView tv_size;
		 ImageButton btn_reduce;
		 EditText et_number;
		 ImageButton btn_add;
		 TextView tv_money;
		 TextView market_information_seps_add;
		 TextView market_information_seps_del;
		 TextView market_information_seps_num;
		 CheckBox shopcart_item_check;
	}
}