package com.android.hengyu.pub;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.entity.JuTuanGouData;
import com.lelinju.www.R;

public class JuJingCaiAdapter extends BaseAdapter {

	private Context mContext;
	private List<JuTuanGouData> List;
	private LayoutInflater mInflater;
	public static AQuery mAq;
	java.util.Date now_1;
	java.util.Date date_1;
	public static boolean type = false;
	public JuJingCaiAdapter(Context context, List<JuTuanGouData> list) {
		this.List = list;
		this.mContext = context;
		mInflater = LayoutInflater.from(context);
		mAq = new AQuery(context);
	}

	public void putData(ArrayList<JuTuanGouData> List) {
		this.List = List;
		this.notifyDataSetChanged();
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return List.size();
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
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.jujingcai_item, null);
			holder.img = (ImageView) convertView.findViewById(R.id.img);
			holder.iv_zhuti = (ImageView) convertView.findViewById(R.id.iv_zhuti);
//			holder.tv_zhuti_2 = (TextView) convertView.findViewById(R.id.tv_zhuti_2);
//			holder.tv_zhuti_3 = (TextView) convertView.findViewById(R.id.tv_zhuti_3);
			holder.tv_titel = (TextView) convertView.findViewById(R.id.tv_titel);

			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_groupon_price = (TextView) convertView.findViewById(R.id.tv_groupon_price);
			holder.tv_tuan = (TextView) convertView.findViewById(R.id.tv_tuan);
			holder.tv_anniu = (TextView) convertView.findViewById(R.id.tv_anniu);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		/**
		 * 类别选项
		 */
		try {
			// System.out.println("List======================"+List.size());
			System.out.println("列表标题-----------------------------------title-----------"+List.get(position).getTitle());
			
			 System.out.println("List======================"+List.get(position).getCategory_id());
			if (List.get(position).getCategory_id().equals("1703")) {//聚精彩
				holder.iv_zhuti.setBackgroundResource(R.drawable.jujingcai);
			}else if (List.get(position).getCategory_id().equals("1702")) {
				holder.iv_zhuti.setBackgroundResource(R.drawable.jutuan);
			}else if (List.get(position).getCategory_id().equals("1704")) {//聚团
				holder.iv_zhuti.setBackgroundResource(R.drawable.just);
			}
			System.out.println("个数1======================" + position);
			holder.tv_titel.setText(List.get(position).getTitle());
			holder.tv_price.setText("￥" + List.get(position).getPrice());
			holder.tv_groupon_price.setText("￥"+ List.get(position).getSell_price());
			holder.tv_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置市场价文字的中划线
			holder.tv_tuan.setText(List.get(position).getPeople() + "人团");
			mAq.id(holder.img).image(RealmName.REALM_NAME_HTTP+ List.get(position).getImg_url());
			
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			try {
////				timer_time = "2017-05-02 18:04:50";
//				now_1 = df.parse(List.get(position).getEnd_time());
//			} catch (java.text.ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			try {
////				datetime = "2017-05-02 18:04:10";
//				date_1 = df.parse(HomeActivity.datetime);
//			} catch (java.text.ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			try {
//				   long end_time = now_1.getTime();
//				   long time = date_1.getTime();
//				   System.out.println("end_time-------------"+end_time);
//					System.out.println("time-------------"+time);
//					if (end_time > time) {
//						holder.tv_anniu.setText("去开团");
////						holder.tv_anniu.setOnClickListener(new OnClickListener() {
////
////							@Override
////							public void onClick(View arg0) {
////								// TODO Auto-generated method stub
////								try {
////									
//////									Intent intent = new Intent(mContext,JuJingCaiXqActivity.class);
//////									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//////									intent.putExtra("id", List.get(position).getId());
//////									intent.putExtra("choujiang", "110");
//////									intent.putExtra("type","1");
//////									mContext.startActivity(intent);
////									
////									HomeActivity.handlerll.sendEmptyMessage(1);
////								} catch (Exception e) {
////									// TODO: handle exception
////									e.printStackTrace();
////								}
////							}
////						});
//						System.out.println("1-------立即参与------");
//					}else {
//						System.out.println("2-----已结束--------");
//						holder.tv_anniu.setText("已结束");
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
			type = true;


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return convertView;
	}

	class ViewHolder {
		ImageView img,iv_zhuti,ll_tupian;
		TextView tv_titel,tv_zhuti_1, tv_zhuti_2,tv_zhuti_3,tv_zhuti_4,tv_price, tv_tuan, tv_groupon_price, tv_anniu;
		TextView tv_price2, tv_tuan2, tv_groupon_price2, tv_anniu2, tv_time;
		TextView tv_price3, tv_groupon_price3, tv_anniu3;
		LinearLayout ll_jutuan, ll_yushoutuan, ll_yiyuanjutou;
	}
}
