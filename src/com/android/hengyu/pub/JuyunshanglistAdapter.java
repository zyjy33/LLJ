package com.android.hengyu.pub;

import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.SpcsAdapter;
import com.hengyushop.airplane.adapter.zaylAdapter;
import com.hengyushop.demo.home.ZhongAnYlActivity;
import com.hengyushop.demo.home.ZhongAnYlListActivity;
import com.hengyushop.entity.GoodsListData;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.XsgyListData;
import com.hengyushop.entity.ZhongAnYlBean;
import com.hengyushop.entity.ZhongAnYlData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;
import com.lelinju.www.WareInformationActivity;

public class JuyunshanglistAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<GoodsListData> list;
	private LayoutInflater inflater;
	ArrayList<ZhongAnYlBean> items;
	public static List<String> list_zhi = new ArrayList<String>();
	public static List<String> list_zhi1 = new ArrayList<String>();
	public static List<String> list_zhi2 = new ArrayList<String>();
	private ArrayList<GoodsListData> list_ll = new ArrayList<GoodsListData>();;
	private ImageLoader loader;
	GridView gridview;
	public JuyunshanglistAdapter(ArrayList<GoodsListData> list,Context context, ImageLoader imageLoader) {
		this.list = list;
		this.context = context;
		this.loader = loader;
		this.inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		if (list.size()<1) {

			return 0;
		}else{

			return list.size();
		}
	}

	public void putData(ArrayList<GoodsListData> list){
		this.list = list;
		this.notifyDataSetChanged();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup patent) {
		// TODO Auto-generated method stub
		try {
			
		convertView = inflater.inflate(R.layout.tuijian_yunshangju_time, null);
		TextView tv_letter = (TextView) convertView.findViewById(R.id.tv_ware_name);
		LinearLayout addview= (LinearLayout) convertView.findViewById(R.id.addview);
		ImageView img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
//		LinearLayout img_btn_address = (LinearLayout) convertView.findViewById(R.id.img_btn_address);
		ImageView iv_biaoti1 = (ImageView) convertView.findViewById(R.id.iv_biaoti1);
		ImageView iv_biaoti2 = (ImageView) convertView.findViewById(R.id.iv_biaoti2);
		ImageView iv_biaoti3 = (ImageView) convertView.findViewById(R.id.iv_biaoti3);
		
		if (list.get(position).getName() == null) {
			tv_letter.setText("");
		}else {
			tv_letter.setText(list.get(position).getName());
		}
		
		System.out.println("--------------"+list.get(position).getName());
//		loader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).getImg_url(), img_ware);
		ImageLoader imageLoader = ImageLoader.getInstance();
	    imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ list.get(position).getImg_url(),img_ware);
			
		addview.removeAllViews();
		
//		img_btn_address.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				try {
//					
////				String id = list.get(position).getId();
////				String title = list.get(position).getTitle();
////				System.out.println("====================="+id);
////				Intent intent = new Intent(context,ZhongAnYlListActivity.class);
////				intent.putExtra("id", id);
////				intent.putExtra("title", title);
////				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
////				context.startActivity(intent);
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//			}
//		});
		 
		for (int i = 0; i <list.get(position).getList().size() ; i++) {
//				System.out.println("====getList().size()================="+list.get(position).getList().size());
//			View vi = LayoutInflater.from(context).inflate(R.layout.yhzstp_item,null);
//			ImageView iv_biaoti1 = (ImageView) vi.findViewById(R.id.iv_biaoti1);
//			ImageView iv_biaoti2 = (ImageView) vi.findViewById(R.id.iv_biaoti2);
//			ImageView iv_biaoti3 = (ImageView) vi.findViewById(R.id.iv_biaoti3);
			
			System.out.println("--------------"+list.get(position).getList().get(i).getImg_url());
//			loader.displayImage(RealmName.REALM_NAME_HTTP+ list.get(position).getList().get(i).getImg_url(), iv_biaoti1);
//			ImageLoader imageLoader = ImageLoader.getInstance();
			System.out.println("====================="+i);
			String tupian = list.get(position).getList().get(0).getImg_url();
			list_zhi.add(tupian);
			System.out.println("======list_zhi=0=============="+list_zhi.size());
			String tupian1 = list.get(position).getList().get(1).getImg_url();
			list_zhi1.add(tupian1);
			System.out.println("======list_zhi=1=============="+list_zhi1.size());
			String tupian2 = list.get(position).getList().get(2).getImg_url();
			list_zhi2.add(tupian2);
			System.out.println("======list_zhi=2=============="+list_zhi2.size());
			
//			if (i == 0) {
//				System.out.println("====0================="+i);
//				 imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ list.get(position).getList().get(i).getImg_url(),iv_biaoti1);
//		    }
//				if (i == 1){
//		    	System.out.println("====1================="+i);
//		    	 imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ list.get(position).getList().get(i).getImg_url(),iv_biaoti2);
//		    } 
//		    	if (i == 2){
//		    	System.out.println("====2================="+i);
//		    	 imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ list.get(position).getList().get(i).getImg_url(),iv_biaoti3);
//		    } 	
			
//			addview.addView(vi);
		}
		
//		System.out.println("======list_zhi11==============="+list_zhi.size());
		StringBuffer str1 = new StringBuffer();
        for(String s:list_zhi){
        	str1.append(s+",");
        }
        str1.delete(str1.lastIndexOf(","),str1.length()); 
        System.out.println("1Æ´½ÓÖ®ºó---------------"+str1);
       
       for(String u : list_zhi){  
       System.out.println("list_zhi---------------"+u);
       imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ u,iv_biaoti1);
//       imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ u,iv_biaoti2);
//       imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ u,iv_biaoti3);
       } 
       
    
   
       for(String u : list_zhi1){  
           System.out.println("list_zhi1---------------"+u);
//           imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ u,iv_biaoti1);
           imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ u,iv_biaoti2);
//           imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ u,iv_biaoti3);
          } 
       
       for(String u : list_zhi2){  
           System.out.println("list_zhi2---------------"+u);
//           imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ u,iv_biaoti1);
           imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ u,iv_biaoti3);
//           imageLoader.displayImage(RealmName.REALM_NAME_HTTP+ u,iv_biaoti3);
          } 
       
		list_zhi.clear();
		list_zhi1.clear();
		list_zhi2.clear();
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return convertView;
	}
}