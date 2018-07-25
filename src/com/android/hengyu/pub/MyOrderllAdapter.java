package com.android.hengyu.pub;


import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.android.hengyu.web.Constants;
import com.android.hengyu.web.RealmName;
import com.android.pliay.PayResult;
import com.android.pliay.SignUtils;
import com.androidquery.AQuery;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.Common;
import com.hengyushop.demo.home.DianPingActivity;
import com.hengyushop.demo.my.MyOrderXqActivity;
import com.hengyushop.demo.my.MyOrderZFActivity;
import com.hengyushop.demo.my.TishiCarArchivesActivity;
import com.hengyushop.entity.MyAssetsBean;
import com.hengyushop.entity.MyOrderData;
import com.lglottery.www.widget.CommomConfrim;
import com.lglottery.www.widget.CommomConfrim.onDeleteSelect;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.lelinju.www.MyOrderConfrimActivity;
import com.lelinju.www.R;
import com.lelinju.www.R.id;
import com.lelinju.www.R.layout;

/**
 * �ҵĶ���
 * 
 * @author Administrator
 * 
 */
public class MyOrderllAdapter extends BaseAdapter {
	private Context context;
	private Intent intent;
	private List<MyOrderData> list;
	private LayoutInflater inflater;
	private Activity act;
	private Handler handler;
	private String payment_status,express_status,status;
	int zhuangtai,yunfei1;
	public static String total_cll,heji_zongjia;
	double dzongjia,yunfei;
	String user_name, user_id,login_sign,order_no_fk,total_c;
	TextView tv_heji;
	String type;
	int p;
	public static AQuery mAq;
	public static String recharge_no,notify_url;
//	public static List<Double> list_monney = new ArrayList<Double>();
	public MyOrderllAdapter(List<MyOrderData> list,Context context,Handler handler) {
		this.list = list;
		this.context = context;
		this.handler = handler;
//		this.payment_status = payment_status;
		mAq = new AQuery(context);
		this.inflater = LayoutInflater.from(context);
	}

//	@Override
//	public int getCount() {
//		if (list.size() < 1) {
//
//			return 0;
//		} else {
//
//			return list.size();
//		}
//	}
	
	public void putData(ArrayList<MyOrderData> list){
		this.list = list;
		this.notifyDataSetChanged();
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	
//	@Override
//	public Object getItem(int position) {
//		// TODO Auto-generated method stub
//		return list.get(position);
//	}
	
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}


	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	private final class ViewHolder {
		TextView tv_goods_title;
		TextView tv_market_price;
		TextView sell_price;
		TextView quantity;
		ImageView tupian;//
		TextView lv_jijian;
		TextView tv_kukuang;
		TextView tv_quxiao;//
		TextView tv_zhuangtai;//
		TextView jijian;//
		TextView tv_heji,tv_yunfei;//
		TextView shanchu;//ɾ��
		TextView tv_zongjia;
		TextView tv_queren_fukuan;
		TextView tv_pingjia,tv_company_name,tv_hongbao;
		LinearLayout lv_dingdanxq;
		LinearLayout ll_anliu,ll_hongbao;
		private long timeGetTime;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup patent) {
		// TODO Auto-generated method stub
//		ViewHolder holder = null;
//		holder = new ViewHolder();
		System.out.println("position==========================================================="+position);
//		if (convertView == null) {
//		holder = new ViewHolder();
			
//		convertView = inflater.inflate(R.layout.itme_my_order, null);
		if (convertView == null) {
			convertView = LinearLayout.inflate(context,R.layout.itme_my_order, null);
		}
		
		LinearLayout addview = (LinearLayout) convertView.findViewById(id.gwc_addview);
		try {
			LinearLayout ll_hongbao = (LinearLayout) convertView.findViewById(R.id.ll_hongbao);// 	
			LinearLayout ll_anliu = (LinearLayout) convertView.findViewById(R.id.ll_anliu);// 
			TextView lv_jijian = (TextView) convertView.findViewById(R.id.lv_jijian);//
			TextView tv_kukuang = (TextView) convertView.findViewById(R.id.tv_kukuang);
			TextView tv_quxiao = (TextView) convertView.findViewById(R.id.tv_quxiao);//
			TextView tv_zhuangtai = (TextView) convertView.findViewById(R.id.tv_zhuangtai);//
			TextView tv_tuikuan = (TextView) convertView.findViewById(R.id.tv_tuikuan);//
		TextView tv_yunfei = (TextView) convertView.findViewById(R.id.tv_yunfei);//
		TextView shanchu = (TextView) convertView.findViewById(R.id.tv_shanche);// 
		TextView tv_queren_shouhuo = (TextView) convertView.findViewById(R.id.tv_queren_fukuan);//
		TextView tv_pingjia = (TextView) convertView.findViewById(R.id.tv_pingjia);//
		TextView tv_hongbao = (TextView) convertView.findViewById(R.id.tv_hongbao);//
		TextView tv_company_name = (TextView) convertView.findViewById(R.id.tv_company_name);//
		TextView tv_heji = (TextView) convertView.findViewById(R.id.tv_heji);//
		TextView tv_yanhuoma = (TextView) convertView.findViewById(R.id.tv_yanhuoma);//
//		BigDecimal c = new BigDecimal(Double.parseDouble(list.get(position).getPayable_amount())+Double.parseDouble(list.get(position).getExpress_fee()));
//		String total_cll = Double.toString(c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tv_heji.setText("��"+list.get(position).getPayable_amount()); 
		
        tv_company_name.setText(list.get(position).getCompany_name());
		payment_status = list.get(position).getPayment_status();
//		System.out.println("payment_status============="+payment_status);
		express_status = list.get(position).getExpress_status();
//		System.out.println("express_status============="+express_status);
		status = list.get(position).getStatus();
//		System.out.println("status============="+status);
		
//		yunfei = Double.parseDouble(list.get(position).getExpress_fee());
//		int yunfei = Integer.parseInt(list.get(position).getExpress_fee());
		String yunfei = list.get(position).getExpress_fee();
//		System.out.println("yunfei1============="+yunfei);
		if (yunfei.equals("0.0")) {
			tv_yunfei.setVisibility(View.GONE);
		}else {
		tv_yunfei.setText("(���˷ѣ�"+list.get(position).getExpress_fee()+")");
		}
		
//		String kedi_honbao = list.get(position).getCashing_packet();
////		System.out.println("kedi_honbao============="+kedi_honbao);
//		if (kedi_honbao.equals("0.0")) {
//			ll_hongbao.setVisibility(View.GONE);
//		}else {
//		tv_hongbao.setText("�ɵֺ��:-��"+kedi_honbao);
//		}
		
		
		if (payment_status.equals("1")){
			System.out.println("������=============");
			tv_zhuangtai.setText("�ȴ���Ҹ���");
			ll_anliu.setVisibility(View.VISIBLE);
			tv_kukuang.setVisibility(View.VISIBLE);//ȷ�ϸ���
			tv_pingjia.setVisibility(View.GONE);//����
			tv_queren_shouhuo.setVisibility(View.GONE);//ȷ���ջ�
			shanchu.setVisibility(View.VISIBLE);//ɾ��
			tv_tuikuan.setVisibility(View.GONE);//�˿�
			tv_kukuang.setText("ȷ�ϸ���");
			zhuangtai = 2;
		}else if (status.equals("4")){
			tv_zhuangtai.setText("���˿�");
			ll_anliu.setVisibility(View.VISIBLE);
			tv_kukuang.setVisibility(View.GONE);//ȷ�ϸ���
			tv_pingjia.setVisibility(View.GONE);//����
			tv_queren_shouhuo.setVisibility(View.GONE);//ȷ���ջ�
			shanchu.setVisibility(View.GONE);//ɾ��
			tv_tuikuan.setVisibility(View.GONE);//�˿�
		}else if (payment_status.equals("2") && express_status.equals("1") && status.equals("2")){
			System.out.println("������=============");
			tv_zhuangtai.setText("����Ѹ���");
			ll_anliu.setVisibility(View.VISIBLE);
			tv_queren_shouhuo.setVisibility(View.GONE);
			shanchu.setVisibility(View.GONE);//ɾ��
			tv_kukuang.setVisibility(View.GONE);//ȷ�ϸ���
			tv_pingjia.setVisibility(View.GONE);//����
			tv_queren_shouhuo.setVisibility(View.GONE);//ȷ���ջ�
			tv_tuikuan.setVisibility(View.VISIBLE);//�˿�
			
			zhuangtai = 3;
		}else 
			if (payment_status.equals("2") && express_status.equals("2") && status.equals("2")){
				System.out.println("���ջ�=============");
			tv_zhuangtai.setText("�����ѷ���");
			ll_anliu.setVisibility(View.VISIBLE);
			shanchu.setVisibility(View.GONE);//ɾ��
			tv_kukuang.setVisibility(View.GONE);//ȷ�ϸ���
			tv_pingjia.setVisibility(View.GONE);//����
			tv_queren_shouhuo.setVisibility(View.VISIBLE);//ȷ���ջ�
			tv_tuikuan.setVisibility(View.GONE);//�˿�
			tv_queren_shouhuo.setText("ȷ���ջ�");
			zhuangtai = 4;
			if (!list.get(position).getAccept_no().equals("")) {
				tv_yanhuoma.setText("ȡ����:"+list.get(position).getAccept_no());
			}else {
				System.out.println("������ȡ����Ϊ��=============");
			}
		}else if (payment_status.equals("2") && express_status.equals("2") && status.equals("3")){
			System.out.println("�����=============");
			tv_zhuangtai.setText("�������");
			ll_anliu.setVisibility(View.VISIBLE);
			tv_queren_shouhuo.setVisibility(View.GONE);//ȷ���ջ�
			tv_kukuang.setVisibility(View.GONE);//ȷ�ϸ���
			shanchu.setVisibility(View.VISIBLE);//ɾ��
			tv_pingjia.setVisibility(View.VISIBLE);//����
			tv_tuikuan.setVisibility(View.GONE);//�˿�
			tv_pingjia.setText("����");
			zhuangtai = 5;
		}
		
		
		/**
		 * ȷ�ϸ���
		 */
		tv_kukuang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {	
					double a= 0;
					
//					String yunfei = list.get(position).getExpress_fee();
////			    System.out.println("yunfei================"+yunfei);
//					BigDecimal c = new BigDecimal(Double.parseDouble(list.get(position).getPayable_amount())+Double.parseDouble(list.get(position).getExpress_fee()));
//					String total_cll = Double.toString(c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
////				System.out.println("total_cll================"+total_cll);
					
//					  String order_no = list.get(position).getOrder_no();
					  order_no_fk = list.get(position).getTrade_no();
//					  total_c = list.get(position).getPayable_amount();
//					  get_game_groupon(order_no_fk);
					  
					  Intent intent = new Intent(context, MyOrderZFActivity.class);
					  intent.putExtra("order_no",order_no_fk);
					  intent.putExtra("total_c",list.get(position).getPayable_amount());
					  context.startActivity(intent);
					  
						Message msg = new Message();
						msg.what = 4;
						msg.obj = order_no_fk;
						handler.sendMessage(msg);
					  
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
		/**
		 * ȷ���ջ�
		 */
		tv_queren_shouhuo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {	
					    
						Intent intent = new Intent(context, TishiCarArchivesActivity.class);
						intent.putExtra("order_no",list.get(position).getOrder_no());
						intent.putExtra("shouhuo","shouhuo");
						context.startActivity(intent);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
		/**
		 * ȡ������
		 */
		tv_quxiao.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {	
						String order_no = list.get(position).getOrder_no();
						Message msg = new Message();
						msg.what = 2;
						msg.obj = order_no;
						handler.sendMessage(msg);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				
			}
		});
		
		/**
		 * �����˿�
		 */
		tv_tuikuan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {	
						String order_no = list.get(position).getOrder_no();
						Message msg = new Message();
						msg.what = 1;
						msg.obj = order_no;
						handler.sendMessage(msg);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				
			}
		});
		
		
		/**
		 * ɾ��
		 */
		shanchu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {	
					String trade_no = list.get(position).getOrder_no();
					Message msg = new Message();
					msg.what = 3;
					msg.obj = trade_no;
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		addview.removeAllViews();
		
		for (int i = 0; i < list.get(position).getList().size(); i++){
			// ViewHolder holder = null;
			p = i;
//			holder = new ViewHolder();
			View vi = LayoutInflater.from(context).inflate(layout.itme_my_order_zhuti, null);
			
			TextView tv_goods_title = (TextView) vi.findViewById(R.id.tv_goods_title);//
			ImageView tupian = (ImageView) vi.findViewById(R.id.iv_tupian);
			TextView tv_market_price = (TextView) vi.findViewById(R.id.tv_market_price);
			TextView sell_price = (TextView) vi.findViewById(R.id.tv_real_price);
			TextView quantity = (TextView) vi.findViewById(R.id.tv_quantity);
			LinearLayout lv_dingdanxq = (LinearLayout) vi.findViewById(R.id.lv_dingdanxq);
			
//			tv_goods_title.setText(list.get(position).getList().get(i).getGoods_title());
			tv_market_price.setText("��"+list.get(position).getList().get(i).getMarket_price());
//			holder.sell_price.setText("��"+list.get(position).getList().get(i).getSell_price());
			quantity.setText("x"+list.get(position).getList().get(i).getQuantity());
			tv_market_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // �����г������ֵ��л���
//			ImageLoader imageLoader=ImageLoader.getInstance();
//			imageLoader.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).getList().get(i).getImg_url(), holder.tupian);
			 mAq.id(tupian).image(RealmName.REALM_NAME_HTTP+list.get(position).getList().get(i).getImg_url());
			int number = list.get(position).getList().get(i).getQuantity();
			BigDecimal   c   =   new   BigDecimal(Double.parseDouble(list.get(position).getList().get(i).getSell_price())/number);
//			//����2λС��
			double   sell_price_zhi   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
			
			sell_price.setText("��"+sell_price_zhi);//�۸�
			
			
			
			System.out.println("getGoods_title============="+list.get(position).getList().get(i).getGoods_title());
			
			addview.addView(vi);
//			convertView.setTag(holder);  
			
			
			/**
			 * ��������
			 */
			lv_dingdanxq.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
					try {
						
				    Intent intent = new Intent(context,MyOrderXqActivity.class);
				    MyOrderData  bean=  list.get(position);
				    Bundle bundle = new Bundle();
				    bundle.putSerializable("bean", (Serializable) bean);
				    intent.putExtras(bundle);
				    intent.putExtra("payment_status", payment_status);
				    intent.putExtra("express_status", express_status);
				    intent.putExtra("status", status);
					context.startActivity(intent);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			});
		}
		
		
		/**
		 * ����
		 */
//		TextView tv_pingjia = (TextView) convertView.findViewById(R.id.tv_pingjia);//
		tv_pingjia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {	
					Intent intent = new Intent(context, DianPingActivity.class);
					intent.putExtra("article_id",list.get(position).getList().get(p).getArticle_id());
					context.startActivity(intent);	
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		});
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
//		}else {
//			holder = (ViewHolder) convertView.getTag();
//		}
		return convertView;
		
	}
	
	/**
	 * ��֤ƴ���Ƿ���Ա
	 * @param groupon_no 
	 */
	private void get_game_groupon(String order_no) {
		try{
			String strUrlone = RealmName.REALM_NAME_LL + "/get_order_verify?trade_no="+order_no+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						System.out.println("======arg1============="+arg1);
						JSONObject object = new JSONObject(arg1);
						String code = object.getString("code");
						String info = object.getString("info");
						if (code.equals("yes")) {
							 Intent intent = new Intent(context, MyOrderZFActivity.class);
							  intent.putExtra("order_no",order_no_fk);
							  intent.putExtra("total_c",total_c);
							  context.startActivity(intent);
							  
								Message msg = new Message();
								msg.what = 4;
								msg.obj = order_no_fk;
								handler.sendMessage(msg);
						}else{
							Toast.makeText(context, info, 200).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, context);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}