package com.android.hengyu.pub;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctrip.openapi.java.utils.MyCountdownTimer;
import com.hengyushop.entity.JuTuanGouData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.lelinju.www.R;

public class QuCantuanAdapter extends BaseAdapter {

	private ArrayList<JuTuanGouData> list;
	private ArrayList<JuTuanGouData> listll;
	private Context context;
	private Handler handler;
	private ImageLoader loader;
    public static String foreman_id,foreman_name,tuan_id;
	java.util.Date now;
	java.util.Date date;
	private MyCount count;//��ʱ����� 
	private long current_time = 0;
	private long time = 0;//����Ϊ��λ
	TextView tv_time;
	LinearLayout ll_time;
	ViewHolder holder;
	
	public QuCantuanAdapter(ArrayList<JuTuanGouData> list, ArrayList<JuTuanGouData> listll,Context context,Handler handler) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		this.listll = listll; 
		this.handler = handler;
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

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		holder = null;
		if (convertView == null) {
//			convertView = LinearLayout.inflate(context,R.layout.cantuanjia_item, null);
			convertView = View.inflate(context, R.layout.cantuanjia_item, null);
			holder = new ViewHolder();
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
	   }else{
		holder = (ViewHolder) convertView.getTag();
	   }
//		System.out.println("position===================="+position);
//		ll_time = (LinearLayout) convertView.findViewById(R.id.ll_time);
		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		TextView tv_renshu = (TextView) convertView.findViewById(R.id.tv_renshu);
		
//		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
//		TextView tv_city = (TextView) convertView.findViewById(R.id.tv_city);
		
//		TextView tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		TextView tv_qucantuan = (TextView) convertView.findViewById(R.id.tv_qucantuan);
		tuan_id = list.get(position).getGroupon_item_id();
		
		for (int i = 0; i < listll.size(); i++) {
			String id = listll.get(i).getId();
			if (id.equals(tuan_id)) {
				int zongshu = Integer.parseInt(listll.get(i).getPeople());
				String tuanshu = String.valueOf(zongshu - list.get(position).getGroupon_item_member());
				tv_renshu.setText("����"+tuanshu+"�˳���"); 
			}
		}
		String user_name = list.get(position).getForeman_name();
		tv_name.setText(user_name); 
		
//		if (list_ll.size() > 0) {
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			String end_time = "2004-03-26 13:31:40";
			now = df.parse(end_time);
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String datetime = "2004-03-24 11:30:24";
			date = df.parse(datetime);
		} catch (java.text.ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		   time=now.getTime()-date.getTime();
//		   String zhou = String.valueOf(time);
//		   tv_time.setText(zhou); 
//		   time=5000;
			System.out.println("1-------------"+time);
			count = new MyCount(time, 1000);
			System.out.println("2-------------"+count);
			count.start();//��ʼ��ʱ 
			
			
//			Message msg = new Message();
//			msg.what = 5;
//			msg.obj = time;
//			handler.sendMessage(msg);
			
//			long day = JuTuanGouXqActivity.day;
//			long hour= JuTuanGouXqActivity.hour;
//			long min= JuTuanGouXqActivity.min;
//			long s= JuTuanGouXqActivity.s;
//			tv_time.setText("ʣ��ʱ��: "+day+"��" + hour + "Сʱ" + min + "��" + s+"��"); 
//		}
		
	    tv_qucantuan.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					foreman_id = list.get(position).getForeman_id();
					foreman_name = list.get(position).getForeman_name();
				Message msg = new Message();
				msg.what = 3;
				msg.obj = list.get(position).getGroupon_item_id();
				handler.sendMessage(msg);
			}
		});
	    
		return convertView;
	}
	
	//��ȡ��ǰʱ��
//		private long getCurrentTime() { 
//			return current_time; 
//		}
		
	//ʵ�ּ�ʱ���ܵ��� 
		class MyCount extends MyCountdownTimer { 

			public MyCount(long millisInFuture, long countDownInterval) { 
				super(millisInFuture, countDownInterval); 
//				holder.tv_time.setText("ʣ��ʱ��:"); 
				current_time = millisInFuture;
				System.out.println("current_time-------------"+current_time);
				   long day=current_time/(24*60*60*1000);
				   long hour=(current_time/(60*60*1000)-day*24);
				   long min=((current_time/(60*1000))-day*24*60-hour*60);
				   long s=(current_time/1000-day*24*60*60-hour*60*60-min*60);
//				   System.out.println(""+day+"��"+hour+"Сʱ"+min+"��"+s+"��");
//				   tv_time.setText("ʣ��ʱ��: "+day+":" + hour + ":" + min + ":" + s); 
				   holder.tv_time.setText("ʣ��ʱ��: "+day+"��" + hour + "Сʱ" + min + "��" + s+"��"); 
			} 
			@Override 
			public void onFinish() {
				//ý����� 
//				final MediaPlayer media = MediaPlayer.create(MainActivity.this, R.raw.alarm); 
				//�Ի������ 
//				final AlertDialog mydialog = new AlertDialog.Builder(MainActivity.this).setMessage( 
//						"ʱ�䵽��").setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){ 
	//
//							@Override 
//							public void onClick(DialogInterface dialog, int which) { 
//								media.stop(); 
//								media.release(); 
//								btn_pause.setEnabled(false); 
//								btn_resume.setEnabled(false); 
//								btn_start.setEnabled(true); 
//							} 
//						}).create(); 
//				mydialog.show();//���Ž�������ʾ�Ի��� 
//				tv_time.setText(""); 
//				ll_time.setVisibility(View.GONE);
				
				Message msg = new Message();
				msg.what = 5;
//				msg.obj = list;
				handler.sendMessage(msg);
//				try { 
//					media.prepare(); //׼���������� 
//				} catch (IllegalStateException e) { 
//					e.printStackTrace(); 
//				} catch (Exception e) { 
//					e.printStackTrace(); 
//				} 
//				media.start();//�������� 
//				media.setOnCompletionListener(new OnCompletionListener() {//���Ž�����Ự����ʧ�����ð�ť״̬ 
//					@Override 
//					public void onCompletion(MediaPlayer mp) { 
//						mydialog.hide(); 
//						btn_pause.setEnabled(false); 
//						btn_con.setEnabled(false); 
//						btn_start.setEnabled(true); 
//					} 
//				}); 
			} 
			
			//����ʣ��ʱ�� 
			@Override 
			public void onTick(long millisUntilFinished, int percent) {
				current_time = millisUntilFinished;
//				System.out.println("current_time-------------"+current_time);
				
				   long day=current_time/(24*60*60*1000);
				   long hour=(current_time/(60*60*1000)-day*24);
				   long min=((current_time/(60*1000))-day*24*60-hour*60);
				   long s=(current_time/1000-day*24*60*60-hour*60*60-min*60);
				   System.out.println(""+day+"��"+hour+"Сʱ"+min+"��"+s+"��");
//				   tv_time.setText("ʣ��ʱ��: "+day+":" + hour + ":" + min + ":" + s); 
//				   for (int i = 0; i < list.size(); i++) {
					   holder.tv_time.setText("ʣ��ʱ��: "+day+"��" + hour + "Сʱ" + min + "��" + s+"��"); 
//				  }
			} 
		} 
		private class ViewHolder{
			private TextView tv_time;
			/** Сʱ **/
			private TextView tv_hour;
			/** ���� **/
			private TextView tv_minute;
			/** �� **/
			private TextView tv_second;		
		}
	
}
