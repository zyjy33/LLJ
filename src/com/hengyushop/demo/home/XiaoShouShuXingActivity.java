package com.hengyushop.demo.home;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.GouwucheAdapter;
import com.hengyushop.airplane.adapter.GuigeListlAdapter;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.lelinju.www.MyOrderConfrimActivity;
import com.lelinju.www.WareInformationActivity;
import com.lelinju.www.R;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class XiaoShouShuXingActivity extends BaseActivity{
	public static ListView listview_01;
	public static GuiGeListviewAdapter adapter;
//	private GuigeListAdapter adapter;
	public GuigeListlAdapter adapter1;
	public static TextView market_information_sep_price,market_information_pop_buy,market_information_pop_shopcart,tv_guige;
	public static String retailPrice,tuanshu,tuangoujia;
	public static String id,id1,id2,id3,id4,id5,id6;
	public static String title,title1,title2,title3,title4,title5,title6;
	public static int gk_id = 0;
	public static int gk_id1 = 0;
	public static int gk_id2 = 0;
	public static int gk_id3 = 0;
	public static int gk_id4 = 0;
	public static int gk_id5 = 0;
	public static int gk_id6 = 0;
	public static String xz_pyte = "";
	public static String xz_pyte1 = "";
	public static String xz_pyte2 = "";
	public static String xz_pyte3 = "";
	public static String xz_pyte4 = "";
	public static String xz_pyte5 = "";
	public static String xz_pyte6 = "";
//	,xz_pyte1,xz_pyte2,xz_pyte3,xz_pyte4,xz_pyte5,xz_pyte6;
	LinearLayout no_data_no;
	static GuigeData md; 
	GuigeBean mb;
	public static DialogProgress progress;
	public static TextView market_information_seps_num;
	public static SharedPreferences spPreferences;
	public static String user_name,user_id,login_sign;
	public static Context context;
	public static Activity activity;
	static String proName;
	static TextView market_information_seps_del;
	public static String status_list = "0";
	public static String monney_sx_list = ""; 
//	public static String market_price = ""; 
	public static String goods_id = ""; 
	public static String people = "";
	public static String price = ""; 
	public static String spec_text = ""; 
	static String article_id;
	public static boolean ptye_xs = false;
//	public interface onDeleteSelect {
//		void onClick(String resID);
//	}
	
	/**
	 * ������
	 */
//	private CommomConfrim() {
//		final Dialog dlg; 
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiaoshou_guige);
		try {
		article_id = getIntent().getStringExtra("id");//�����article_id	
		listview_01 = (ListView) findViewById(R.id.listview_01);
		progress = new DialogProgress(XiaoShouShuXingActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		login_sign = spPreferences.getString("login_sign", "");
		market_information_seps_del = (TextView)findViewById(R.id.market_information_seps_del);
		loadWeatherll();
		inter();
		
			//��Ʒ����
			market_information_seps_del.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int num = Integer.parseInt(market_information_seps_num.getText().toString());
					if (num != 1) {
						market_information_seps_num.setText(String.valueOf(num - 1));
					} else {
						Toast.makeText(context, "�����ټ���", 200).show();
					}
				}
			});

		//ȡ��
		market_information_pop_buy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				try {
//				activity.finish();
				finish();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		
		//���빺�ﳵ
				market_information_pop_shopcart
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								
									try {
										System.out.println("xz_pyte=============="+xz_pyte);
										System.out.println("xz_pyte1=============="+xz_pyte1);
										System.out.println("xz_pyte2=============="+xz_pyte2);
										System.out.println("xz_pyte3=============="+xz_pyte3);
										System.out.println("gk_id=============="+gk_id);
										System.out.println("gk_id1=============="+gk_id1);
										System.out.println("gk_id2=============="+gk_id2);
										System.out.println("gk_id3=============="+gk_id3);
									if (xz_pyte.equals("0")) {
									if (gk_id == 0) {
										Toast.makeText(context, "��ѡ��"+list.get(0).getTitle()+"������", 200).show();
									}
								}else if (xz_pyte1.equals("1")){
									if (gk_id1 == 0) {
										Toast.makeText(context, "��ѡ��"+list.get(1).getTitle()+"������", 200).show();
									}
								}else if (xz_pyte2.equals("2")){
									if (gk_id2 == 0) {
										Toast.makeText(context, "��ѡ"+list.get(2).getTitle()+"������", 200).show();
									}
								}else if (xz_pyte3.equals("3")){
									if (gk_id3 == 0) {
										Toast.makeText(context, "��ѡ"+list.get(3).getTitle()+"������", 200).show();
									}
								}else if (xz_pyte4.equals("4")){
									if (gk_id4 == 0) {
										Toast.makeText(context, "��ѡ"+list.get(4).getTitle()+"������", 200).show();
									}
								}else if (xz_pyte5.equals("5")){
									if (gk_id5 == 0) {
										Toast.makeText(context, "��ѡ"+list.get(5).getTitle()+"������", 200).show();
									}
								}else if (xz_pyte6.equals("6")){
									if (gk_id6 == 0) {
										Toast.makeText(context, "��ѡ"+list.get(6).getTitle()+"������", 200).show();
									}
								}else{
									    
//									   System.out.println("JuJingCaiXqActivity.type_spec_item===============3================="+JuJingCaiXqActivity.type_spec_item);
//									   System.out.println("JuTuanGouXqActivity.type_spec_item===============3================="+JuTuanGouXqActivity.type_spec_item);
									 System.out.println("ptye=============="+ptye_xs);
									   if (ptye_xs == true ) {
										   System.out.println("type_wx==============");
											progress.CreateProgress();
											String geshu = market_information_seps_num.getText().toString().trim();
											System.out.println("���goods_id===============1================="+goods_id);
											if (JuJingCaiXqActivity.type_spec_item == true) {
											if (goods_id.equals("")) {//û����������
												goods_id = JuJingCaiXqActivity.goods_id;
											}
											}else {
												if (goods_id.equals("")) {//û����������
													goods_id = JuTuanGouXqActivity.goods_id;
												}
											}
											/**
											 * ������Ʒ���빺���嵥
											 */
											System.out.println("������Ʒ���빺���嵥=============="+geshu);
											
										    AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_activity_buy?user_id="+user_id+"&user_name="+user_name+"&user_sign="+login_sign+"&" +
										    		"article_id="+article_id+"&goods_id="+goods_id+"&quantity="+geshu+"&order_id="+0+"&people="+tuanshu+"",
//												   AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_buy?user_id="+user_id+"&user_name="+user_name+
//															"&article_id="+article_id+"&goods_id="+goods_id+"&quantity="+geshu+"",
												new AsyncHttpResponseHandler() {
													@Override
													public void onSuccess(int arg0,String arg1) {
														// TODO Auto-generated method stub
														super.onSuccess(arg0, arg1);
														try {
															JSONObject jsonObject = new JSONObject(arg1);
															String status = jsonObject.getString("status");
															System.out.println("�����嵥================"+arg1);
															String info = jsonObject.getString("info");
															if (status.equals("y")) {
																progress.CloseProgress();
																JSONObject obj = jsonObject.getJSONObject("data");
																String buy_no = obj.getString("buy_no");
																String count = obj.getString("count");
																Toast.makeText(XiaoShouShuXingActivity.this, info, 200).show();
//																if (JuJingCaiXqActivity.fangshi == 4){
																System.out.println("type_wx==============");
																	Intent intent=new Intent(XiaoShouShuXingActivity.this, JuTuanConfrimActivity.class);
																	intent.putExtra("price",price);
																	intent.putExtra("spec_text",spec_text);
																	intent.putExtra("buy_no",buy_no);
																	intent.putExtra("people",tuanshu);
																	intent.putExtra("type_wx", "type_wx");
																	intent.putExtra("type_title", "1");
																	intent.putExtra("jiekou","1");//�۾��ʽӿ�״̬
																	intent.putExtra("stare", "2");
																	intent.putExtra("type_jutuan","1");//�۾���״̬
																	startActivity(intent);
//																	JuJingCaiXqActivity.fangshi = 0;
																	finish();
																	goods_id = "";//�����嵥֮��Ϊ��
																	ptye_xs = false;//���ز���ѡ��
//																}
//																else {
//																	Intent intent=new Intent(context, JuTuanConfrimActivity.class);
//																	intent.putExtra("title", JuJingCaiXqActivity.title);
//																	intent.putExtra("price", JuJingCaiXqActivity.sell_price);
//																	intent.putExtra("img_url", JuJingCaiXqActivity.img_url);
//																	intent.putExtra("groupon_price", JuJingCaiXqActivity.price);
////																	intent.putExtra("item_id", item_id);
////																	intent.putExtra("ct_id", ct_id);
//																	intent.putExtra("foreman_id",user_id);
//																	intent.putExtra("foreman_name",user_name);
////																intent.putExtra("groupon_no", groupon_no);
//																	intent.putExtra("stare", "2");
//																	intent.putExtra("type","1");//�۾���״̬
//																	intent.putExtra("jiekou","1");//�۾��ʽӿ�״̬
//																	intent.putExtra("fx_shuzi","groupon");
//																 	intent.putExtra("type_wx","type_wx");//֧����ʽ
//																	context.startActivity(intent);
////																	JuJingCaiXqActivity.fangshi = 0;
//																	dlg.dismiss();
//																	goods_id = "";//�����嵥֮��Ϊ��
//																}
															}else {
																progress.CloseProgress();
																Toast.makeText(XiaoShouShuXingActivity.this, info, 200).show();
																ptye_xs = false;//���ز���ѡ��
															}
														} catch (JSONException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}

													}
													@Override
													public void onFailure(Throwable arg0, String arg1) {
														// TODO Auto-generated method stub
														progress.CloseProgress();
														System.out.println("==========================���ʽӿ�ʧ�ܣ�");
														System.out.println("========================="+arg0);
														System.out.println("=========================="+arg1);
														Toast.makeText(XiaoShouShuXingActivity.this, "�쳣", 200).show();
//														ptye = false;//���ز���ѡ��
														super.onFailure(arg0, arg1);
													}
													

												}, null);
										}else {
											System.out.println("==goods_id================================"+goods_id);
											if (goods_id.equals("")) {//û����������
												if (JuJingCaiXqActivity.type_xq == true) {
													goods_id = JuJingCaiXqActivity.goods_id;
												}else if (JuTuanGouXqActivity.type_xq == true) {
													goods_id = JuTuanGouXqActivity.goods_id;
												}else{
													goods_id = WareInformationActivity.goods_id;
												}
											}
											if (WareInformationActivity.fangshi == 2) {
												//���빺�ﳵ
												progress.CreateProgress();
												System.out.println("1================"+article_id);
												System.out.println("2================"+goods_id);
												String geshu = market_information_seps_num.getText().toString().trim();
												System.out.println("���빺�ﳵ=============="+geshu);
												AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_cart?user_id="+user_id+"&user_name="+user_name+
														"&article_id="+article_id+"&goods_id="+goods_id+"&quantity="+geshu+"",new AsyncHttpResponseHandler() {
															@Override
															public void onSuccess(int arg0,String arg1) {
																// TODO Auto-generated method stub
																super.onSuccess(arg0, arg1);
																try {
																	JSONObject jsonObject = new JSONObject(arg1);
																	System.out.println("1================"+arg1);
																	String status = jsonObject.getString("status");
																	String info = jsonObject.getString("info");
																	if (status.equals("y")) {
																	progress.CloseProgress();
																	Toast.makeText(XiaoShouShuXingActivity.this, info, 200).show();
																	WareInformationActivity.fangshi = 0;
																	finish();
																	goods_id = "";//�����嵥֮��Ϊ��
																	}else {
																		progress.CloseProgress();
																		Toast.makeText(XiaoShouShuXingActivity.this, info, 200).show();
																	}
																} catch (JSONException e) {
																	// TODO Auto-generated catch block
																	e.printStackTrace();
																}

															}
															@Override
															public void onFailure(Throwable arg0, String arg1) {
																// TODO Auto-generated method stub
																System.out.println("==========================���ʽӿ�ʧ�ܣ�");
																System.out.println("========================="+arg0);
																System.out.println("=========================="+arg1);
																Toast.makeText(XiaoShouShuXingActivity.this, "�쳣", 200).show();
																super.onFailure(arg0, arg1);
															}

														}, null);
											}else {
											    progress.CreateProgress();
											    System.out.println("WareInformationActivity.fangshi================================"+WareInformationActivity.fangshi);
											    System.out.println("JuJingCaiXqActivity.type_xq===============3================="+JuJingCaiXqActivity.type_xq);
//												System.out.println("���goods_id=============2==================="+goods_id);
//												if (goods_id.equals("")) {//û����������
//													if (JuJingCaiXqActivity.type_xq == true) {
//														goods_id = JuJingCaiXqActivity.goods_id;
//													}else{
//														goods_id = WareInformationActivity.goods_id;
//													}
//												}
												try {
													String geshu = market_information_seps_num.getText().toString().trim();
													System.out.println("��Ʒ���빺���嵥=============="+geshu);
													/**
													 * ��Ʒ���빺���嵥
													 */
												AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_buy?user_id="+user_id+"&user_name="+user_name+"" +
														"&user_sign="+login_sign+"&article_id="+article_id+"&goods_id="+goods_id+"&quantity="+geshu+"",
														new AsyncHttpResponseHandler() {
															@Override
															public void onSuccess(int arg0,String arg1) {
																// TODO Auto-generated method stub
																super.onSuccess(arg0, arg1);
																try {
																	JSONObject jsonObject = new JSONObject(arg1);
																	String status = jsonObject.getString("status");
																	System.out.println("�����嵥================"+arg1);
																	String info = jsonObject.getString("info");
																	if (status.equals("y")) {
																		progress.CloseProgress();
																		JSONObject obj = jsonObject.getJSONObject("data");
																		String buy_no = obj.getString("buy_no");
																		String count = obj.getString("count");
																		Toast.makeText(XiaoShouShuXingActivity.this, info, 200).show();
																		if (WareInformationActivity.fangshi == 1) {
																			//��Ʒ��������
																		    WareInformationActivity.fangshi = 0;
																			Intent intent=new Intent(context, MyOrderConfrimActivity.class);
																			intent.putExtra("buy_no",buy_no);
//																			intent.putExtra("price",price);
//																			intent.putExtra("spec_text",spec_text);
																			startActivity(intent);
																			finish();
																			goods_id = "";//�����嵥֮��Ϊ��
																		}else if (JuJingCaiXqActivity.type_xq == true){
																			//������������
//																			JuJingCaiXqActivity.type_xq = false;
																			Intent intent=new Intent(XiaoShouShuXingActivity.this, JuTuanConfrimActivity.class);
//																			intent.putExtra("buy_no",buy_no);
//																			intent.putExtra("type_title", "1");
//																			intent.putExtra("price",tuanshu);
//																			intent.putExtra("spec_text",spec_text);
																			
																			intent.putExtra("price",price);
																			intent.putExtra("spec_text",spec_text);
																			intent.putExtra("buy_no",buy_no);
																			intent.putExtra("people",tuanshu);
																			intent.putExtra("type_wx", "type_wx");
																			intent.putExtra("type_title", "1");
																			intent.putExtra("jiekou","1");//�۾��ʽӿ�״̬
																			intent.putExtra("stare", "2");
																			intent.putExtra("type_jutuan","1");//״̬
																			startActivity(intent);
																			finish();
																			
																			
																			goods_id = "";//�����嵥֮��Ϊ��
																		}else if (JuTuanGouXqActivity.type_xq == true){
																			try {
																				
																			//������������
																			Intent intent=new Intent(XiaoShouShuXingActivity.this, JuTuanConfrimActivity.class);
//																			intent.putExtra("buy_no",buy_no);
//																			intent.putExtra("type_title", "1");
//																			intent.putExtra("price",price);
//																			intent.putExtra("spec_text",spec_text);
																			System.out.println("type_xq================"+getIntent().getStringExtra("type_xq"));
																			if (getIntent().getStringExtra("type_xq").equals("1")) {
																				intent.putExtra("jiekou","2");//�۾��ʽӿ�״̬
																			}else {
																				intent.putExtra("jiekou","3");//�۾��ʽӿ�״̬
																			}
																			intent.putExtra("price",price);
																			intent.putExtra("spec_text",spec_text);
																			intent.putExtra("buy_no",buy_no);
																			intent.putExtra("people",tuanshu);
																			intent.putExtra("type_wx", "type_wx");
																			intent.putExtra("type_title", "1");
//																			intent.putExtra("jiekou","2");//�۾��ʽӿ�״̬
																			intent.putExtra("stare", "2");
																			intent.putExtra("type_jutuan","1");//״̬
																			startActivity(intent);
																			finish();
																			goods_id = "";//�����嵥֮��Ϊ��
																			} catch (Exception e) {
																				// TODO: handle exception
																				e.printStackTrace();
																			}
																		}
//																		else {
//																			//���빺�ﳵ
//																			WareInformationActivity.fangshi = 0;
//																			dlg.dismiss();
//																			goods_id = "";//�����嵥֮��Ϊ��
//																		}
																	}else {
																		progress.CloseProgress();
																		Toast.makeText(XiaoShouShuXingActivity.this, info, 200).show();
																	}
																} catch (JSONException e) {
																	// TODO Auto-generated catch block
																	e.printStackTrace();
																}

															}
															@Override
															public void onFailure(Throwable arg0, String arg1) {
																// TODO Auto-generated method stub
																progress.CloseProgress();
																System.out.println("==========================���ʽӿ�ʧ�ܣ�");
																System.out.println("========================="+arg0);
																System.out.println("=========================="+arg1);
																Toast.makeText(XiaoShouShuXingActivity.this, "�쳣", 200).show();
																super.onFailure(arg0, arg1);
															}
															

														}, null);
												} catch (Exception e) {
													// TODO: handle exception
													e.printStackTrace();
												}
											}
												
										}
								}
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
	}
	
	public void inter() {
		no_data_no = (LinearLayout)findViewById(R.id.no_data_no);
		ImageView market_information_sep_ico = (ImageView)findViewById(R.id.market_information_sep_ico);
		market_information_sep_price = (TextView)findViewById(R.id.market_information_sep_price);
		tv_guige = (TextView)findViewById(R.id.tv_guige);
//		TextView market_information_sep_name = (TextView) layout.findViewById(R.id.market_information_sep_name);
		market_information_pop_shopcart = (Button)findViewById(R.id.market_information_pop_shopcart);
		market_information_pop_buy = (Button)findViewById(R.id.market_information_pop_buy);
//		if (WareInformationActivity.fangshi == 1) {
//			market_information_pop_shopcart.setText("��������");
//		}else if (JuJingCaiXqActivity.type_xq == true) {
//			market_information_pop_shopcart.setText("��������");
//		}
		
//		else if (JuJingCaiXqActivity.fangshi == 4) {
//			market_information_pop_shopcart.setText("ȷ��");
//		}
		// ��������
		TextView market_information_seps_add = (TextView) findViewById(R.id.market_information_seps_add);
//		TextView market_information_seps_del = (TextView) layout.findViewById(R.id.market_information_seps_del);
		market_information_seps_num = (TextView) findViewById(R.id.market_information_seps_num);
		
		market_information_seps_num.setText("1");

		
//		Button market_information_pop_sure = (Button)layout.findViewById(R.id.market_information_pop_sure);
		System.out.println("JuJingCaiXqActivity.type_xq==================================="+JuJingCaiXqActivity.type_xq);
		//��������
		if (JuJingCaiXqActivity.type_xq == true) {
			ImageLoader imageLoader=ImageLoader.getInstance();
			imageLoader.displayImage(RealmName.REALM_NAME_HTTP + JuJingCaiXqActivity.img_url,market_information_sep_ico);
			imageLoader.clearMemoryCache();//����ڴ滺��
			if (JuJingCaiXqActivity.type_spec_item == true) {
				tv_guige.setVisibility(View.VISIBLE);
//				ptye = true;
				market_information_sep_price.setText("��" + JuJingCaiXqActivity.tuangoujia);
			}else {
				market_information_sep_price.setText("��" + JuJingCaiXqActivity.sell_price);
			}
			tuanshu = JuJingCaiXqActivity.tuanshu;
			price = JuJingCaiXqActivity.tuangoujia;
//			market_information_sep_name.setText(JuJingCaiXqActivity.title);
		}else if (JuTuanGouXqActivity.type_xq == true) {
			ImageLoader imageLoader=ImageLoader.getInstance();
			imageLoader.displayImage(RealmName.REALM_NAME_HTTP + JuTuanGouXqActivity.img_url,market_information_sep_ico);
			imageLoader.clearMemoryCache();//����ڴ滺��
			if (JuTuanGouXqActivity.type_spec_item == true) {
				tv_guige.setVisibility(View.VISIBLE);
//				ptye = true;
				market_information_sep_price.setText("��" + JuTuanGouXqActivity.price);
			}else {
				market_information_sep_price.setText("��" + JuTuanGouXqActivity.sell_price);
			}
			tuanshu = JuTuanGouXqActivity.tuanshu;
			price = JuTuanGouXqActivity.tuangoujia;
//			market_information_sep_name.setText(JuJingCaiXqActivity.title);
		}else{
		ImageLoader imageLoader=ImageLoader.getInstance();
		imageLoader.displayImage(RealmName.REALM_NAME_HTTP + WareInformationActivity.proFaceImg,market_information_sep_ico);
		imageLoader.clearMemoryCache();//����ڴ滺��
		
//		market_information_sep_name.setText(WareInformationActivity.proName);
		market_information_sep_price.setText("��" + WareInformationActivity.retailPrice);
		}
		
		try {
		market_information_seps_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int num = Integer.parseInt(market_information_seps_num.getText().toString());
				market_information_seps_num.setText(String.valueOf(num + 1));
			}
		});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * ��������б�����
	 */
	private static void loadWeatherll() {
//		progress.CreateProgress();
//		String article_id = getIntent().getStringExtra("article_id");
//		if (JuJingCaiXqActivity.article_id != null) {
//			 article_id = JuJingCaiXqActivity.article_id;
//		}else {
//			 article_id = WareInformationActivity.article_id;
//		}
		System.out.println("article_id=========================="+article_id);
//		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_article_spec_list?" +"channel_name=goods",
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_spec_list?" +"article_id="+article_id+"",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
//						System.out.println("=====�������====================="+arg1);
						Message msg = new Message();
						msg.what = 0;
						msg.obj = arg1;
						handler.sendMessage(msg);
					}
				}, null);
	}
	private static Handler handler = new Handler(){
		public void dispatchMessage(Message msg) {
			switch (msg.what) {
			case 0:
				formatWeatherll((String) msg.obj);
				break;
			case 1:
//				Intent intent = new Intent(SYBActivity.this,UserLoginActivity.class);
//				startActivity(intent);
				break;
			case 5:
//				try {
//				ArrayList data_shuzu = WareInformationActivity.data_shuzu;
//				ArrayList data_mrz = WareInformationActivity.data_mrz;
//				ArrayList data_monney = WareInformationActivity.data_monney;
//				System.out.println("ֵ��1============="+data_mrz.size());
//				adapter = new GuiGeListviewAdapter(context,list,data,data1,data2,data_id,data_id1,data_id2,data_shuzu,data_mrz,data_monney, data_monney, data_monney, data_monney, data_monney, data_monney, data_monney, data_monney, data_monney);
////				adapter = new GuigeListAdapter(SYBActivity.this,list,data,data1,data2);
//				listview_01.setAdapter(adapter);
//				setListViewHeightBasedOnChildren(listview_01);   
////				adapter.notifyDataSetChanged();
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
				break;
			default:
				break;
			}
		};
	};
	static ArrayList data_tv;
	static ArrayList data;
	static ArrayList data1;
	static ArrayList data2;
	static ArrayList data3;
	static ArrayList data4;
	static ArrayList data5;
	static ArrayList data6;
	static ArrayList data_id;
	static ArrayList data_id1;
	static ArrayList data_id2;
	static ArrayList data_id3;
	static ArrayList data_id4;
	static ArrayList data_id5;
	static ArrayList data_id6;
	static List<GuigeData> list;
//	static int num = 0;
//	static int zhi = 0;
    static String title_num;
    static int len = 0;
	public static ArrayList data_shuzu,data_monney,data_goods_id,data_market_price,
	data_people,data_goods_id_1,data_people_1,data_price,data_spec_text;
	private static void formatWeatherll(String result) {
		title = null;
		title1 = null;
		title2 = null;
		title3 = null;
		title4 = null;
		title5 = null;
		title = null;
		xz_pyte = "";
		xz_pyte1 = "";
		xz_pyte2 = "";
		xz_pyte3 = "";
		xz_pyte4 = "";
		xz_pyte5 = "";
		xz_pyte6 = "";
		int num = 0;
		int zhi = 0;
		data_tv = new ArrayList();
		data = new ArrayList();
		data1 = new ArrayList();
		data2 = new ArrayList();
		data3 = new ArrayList();
		data4 = new ArrayList();
		data5 = new ArrayList();
		data6 = new ArrayList();
		data_id = new ArrayList();
		data_id1 = new ArrayList();
		data_id2 = new ArrayList();
		data_id3 = new ArrayList();
		data_id4 = new ArrayList();
		data_id5 = new ArrayList();
		data_id6 = new ArrayList();
		list = new ArrayList<GuigeData>();
		try {
			System.out.println("=====�������11====================="+result);
			JSONObject object = new JSONObject(result);
//			JSONArray jobt = object.getJSONArray("data");
//			int lenth = jobt.length();
//			String info = object.getString("info");
			String status = object.getString("status");
			if (status.equals("y")) {
				JSONArray jobt = object.getJSONArray("data");
//				if (JuJingCaiXqActivity.type_spec_item == true) {
//					ptye_xs = true;
//				}else if (JuTuanGouXqActivity.type_xq == true) {
//					ptye_xs = true;
//				}
			for (int i = 0; i < jobt.length(); i++) {
			JSONObject obj= jobt.getJSONObject(i);
			md = new GuigeData();
			md.setTitle(obj.getString("title"));
//			String title_1 = obj.getString("title");
//			data_tv.add(title_1);
			
//			list.add(md);
			JSONArray jaArray = obj.getJSONArray("child");
			int len = jaArray.length();
			System.out.println("len===============================+"+len);
    		if (len > 0) {
    			if (zhi == 0) {
    				num += 0;
    				zhi = 100;
				}else {
					num += 1;
				}
    		System.out.println("num===============================+"+num);
    		System.out.println("zhi===============================+"+zhi);
			switch (num) {
			case 0:
				status_list = "1";
	    		for (int j = 0; j < jaArray.length(); j++) {
	    		JSONObject objc= jaArray.getJSONObject(j);
	    	    title = objc.getString("title");
	    		id = objc.getString("id");
	    		data_id.add(id);
	    		System.out.println("=====ֵ====================="+title);
	    		data.add(title);
	    		xz_pyte = "0";
//	    		adapter1 = new GuigeListlAdapter(SYBActivity.this,data,data_tv);
//				listview_01.setAdapter(adapter1);
	    		}
//	    		String title = list.get(0).getTitle();
	    		System.out.println("=====title=========0============"+title);
//	    		data_tv.add(title);
	    		if (title != null) {
	    			list.add(md);
				}
	    		System.out.println("list==ֵ��========0====="+list.size());
				if (JuJingCaiXqActivity.type_spec_item == true) {
					ptye_xs = true;
				}else if (JuTuanGouXqActivity.type_xq == true) {
					ptye_xs = true;
				}
				break;
            case 1:
            	status_list = "1";
//            	if (len > 0) {
					
            	for (int j = 0; j < jaArray.length(); j++) {
    	    		JSONObject objc= jaArray.getJSONObject(j);
    	    		title1 = objc.getString("title");
    	    		id1 = objc.getString("id");
    	    		data_id1.add(id1);
    	    		System.out.println("=====ֵ1====================="+title1);
    	    		data1.add(title1);
    	    		xz_pyte1 = "1";
    	    		}
//            	String title1 = list.get(1).getTitle();
            	System.out.println("=====title1==========1==========="+title1);
//	    		data_tv.add(title1);
            	if (title1 != null) {
	    		list.add(md);
            	}
            	System.out.println("list==ֵ��========1====="+list.size());
//            	}
    			if (JuJingCaiXqActivity.type_spec_item == true) {
					ptye_xs = true;
				}else if (JuTuanGouXqActivity.type_xq == true) {
					ptye_xs = true;
				}
				break;
            case 2:
            	status_list = "1";
            	for (int j = 0; j < jaArray.length(); j++) {
            		try {
						
    	    		JSONObject objc= jaArray.getJSONObject(j);
    	    		title2 = objc.getString("title");
    	    		id2 = objc.getString("id");
    	    		data_id2.add(id2);
    	    		System.out.println("=====ֵ2====================="+title2);
    	    		data2.add(title2);
    	    		xz_pyte2 = "2";
            		} catch (Exception e) {
						// TODO: handle exception
            			e.printStackTrace();
					}
    	    		}
//            	String title2 = list.get(2).getTitle();
            	System.out.println("=====title2====================="+title2);
//	    		data_tv.add(title2);
            	if (title2 != null) {
	    		list.add(md);
            	}
            	System.out.println("list==ֵ��========2====="+list.size());
    			if (JuJingCaiXqActivity.type_spec_item == true) {
					ptye_xs = true;
				}else if (JuTuanGouXqActivity.type_xq == true) {
					ptye_xs = true;
				}
				break;
            case 3:
            	status_list = "1";
            	for (int j = 0; j < jaArray.length(); j++) {
            		try {
						
    	    		JSONObject objc= jaArray.getJSONObject(j);
    	    		title3 = objc.getString("title");
    	    		id3 = objc.getString("id");
    	    		data_id3.add(id3);
    	    		System.out.println("=====ֵ3====================="+title3);
    	    		data3.add(title3);
    	    		xz_pyte3 = "3";
    	    		list.add(md);
            		} catch (Exception e) {
						// TODO: handle exception
            			e.printStackTrace();
					}
    	    		}
//            	String title3 = list.get(3).getTitle();
//	    		data_tv.add(title3);
            	System.out.println("=====title3====================="+title3);
            	if (title3 != null) {
            	list.add(md);
            	}
    			if (JuJingCaiXqActivity.type_spec_item == true) {
					ptye_xs = true;
				}else if (JuTuanGouXqActivity.type_xq == true) {
					ptye_xs = true;
				}
				break;
            case 4:
            	status_list = "1";
            	for (int j = 0; j < jaArray.length(); j++) {
            		try {
						
    	    		JSONObject objc= jaArray.getJSONObject(j);
    	    		title4 = objc.getString("title");
    	    		id4 = objc.getString("id");
    	    		data_id4.add(id4);
    	    		System.out.println("=====ֵ4====================="+title4);
    	    		data4.add(title4);
    	    		xz_pyte4 = "4";
            		} catch (Exception e) {
						// TODO: handle exception
            			e.printStackTrace();
					}
    	    		}
//            	String title4 = list.get(4).getTitle();
//	    		data_tv.add(title4);
            	if (title4 != null) {
	    		list.add(md);
            	}
    			if (JuJingCaiXqActivity.type_spec_item == true) {
					ptye_xs = true;
				}else if (JuTuanGouXqActivity.type_xq == true) {
					ptye_xs = true;
				}
				break;
            case 5:
            	status_list = "1";
            	for (int j = 0; j < jaArray.length(); j++) {
            		try {
						
    	    		JSONObject objc= jaArray.getJSONObject(j);
    	    		title5 = objc.getString("title");
    	    		id5 = objc.getString("id");
    	    		data_id5.add(id5);
    	    		System.out.println("=====ֵ5====================="+title5);
    	    		data5.add(title5);
    	    		xz_pyte5 = "5";
            		} catch (Exception e) {
						// TODO: handle exception
            			e.printStackTrace();
					}
    	    		}
//            	String title5 = list.get(5).getTitle();
//	    		data_tv.add(title5);
            	if (title5 != null) {
	    		list.add(md);
            	}
    			if (JuJingCaiXqActivity.type_spec_item == true) {
					ptye_xs = true;
				}else if (JuTuanGouXqActivity.type_xq == true) {
					ptye_xs = true;
				}
				break;
            case 6:
            	status_list = "1";
            	for (int j = 0; j < jaArray.length(); j++) {
            		try {
						
    	    		JSONObject objc= jaArray.getJSONObject(j);
    	    		title6 = objc.getString("title");
    	    		id6 = objc.getString("id");
    	    		data_id6.add(id6);
    	    		System.out.println("=====ֵ6====================="+title6);
    	    		data6.add(title6);
    	    		xz_pyte6 = "6";
            		} catch (Exception e) {
						// TODO: handle exception
            			e.printStackTrace();
					}
    	    		}
//            	String title6 = list.get(6).getTitle();
//	    		data_tv.add(title6);
            	if (title6 != null) {
	    		list.add(md);
            	}
    			if (JuJingCaiXqActivity.type_spec_item == true) {
					ptye_xs = true;
				}else if (JuTuanGouXqActivity.type_xq == true) {
					ptye_xs = true;
				}
				break;
			default:
				break;
			}
			
    		}else {
//    			status_list = "0";
			}
    		
    		System.out.println("=====status_list====================="+status_list);
			}
			gk_id = 0;
			gk_id1 = 0;
		    gk_id2 = 0;
			gk_id3 = 0;
			gk_id4 = 0;
			gk_id5 = 0;
			gk_id6 = 0;
			progress.CloseProgress();
			listview_01.setVisibility(View.VISIBLE);
//			handler.sendEmptyMessage(5);
			try {
			System.out.println("list==ֵ��==================================="+list.size());
			System.out.println("JuJingCaiXqActivity.data_shuzu==================================="+JuJingCaiXqActivity.data_shuzu);
			System.out.println("JuJingCaiXqActivity.data_monney==================================="+JuJingCaiXqActivity.data_monney);
			System.out.println("JuJingCaiXqActivity.data_market_price==================================="+JuJingCaiXqActivity.data_market_price);
			System.out.println("JuJingCaiXqActivity.data_goods_id==================================="+JuJingCaiXqActivity.data_goods_id);
			System.out.println("JuJingCaiXqActivity.type_xq==================================="+JuJingCaiXqActivity.type_xq);
			data_people = new ArrayList();
			data_goods_id_1 = new ArrayList();
			data_people_1 = new ArrayList();
			data_price = new ArrayList();
			data_spec_text = new ArrayList();
			//�жϾ�������
			if (JuJingCaiXqActivity.type_xq == true) {
				try {
				data_shuzu = JuJingCaiXqActivity.data_shuzu;//��������idƴ����
				data_monney = JuJingCaiXqActivity.data_monney;//�������Զ�Ӧ�ļ۸�
				data_market_price = JuJingCaiXqActivity.data_market_price;
				data_goods_id = JuJingCaiXqActivity.data_goods_id;
				data_people = JuJingCaiXqActivity.data_people;
				data_goods_id_1 = JuJingCaiXqActivity.data_goods_id_1;
				data_people_1 = JuJingCaiXqActivity.data_people_1;
				data_price = JuJingCaiXqActivity.data_price;
				data_spec_text = JuJingCaiXqActivity.data_spec_text;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else if (JuTuanGouXqActivity.type_xq == true) {
				try {
				data_shuzu = JuTuanGouXqActivity.data_shuzu;//��������idƴ����
				data_monney = JuTuanGouXqActivity.data_monney;//�������Զ�Ӧ�ļ۸�
				data_market_price = JuTuanGouXqActivity.data_market_price;
				data_goods_id = JuTuanGouXqActivity.data_goods_id;
				data_people = JuTuanGouXqActivity.data_people;
				data_goods_id_1 = JuTuanGouXqActivity.data_goods_id_1;
				data_people_1 = JuTuanGouXqActivity.data_people_1;
				data_price = JuTuanGouXqActivity.data_price;
				data_spec_text = JuTuanGouXqActivity.data_spec_text;
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}else{
			data_shuzu = WareInformationActivity.data_shuzu;//��������idƴ����
//			ArrayList data_mrz = WareInformationActivity.data_mrz;//
			data_monney = WareInformationActivity.data_monney;//�������Զ�Ӧ�ļ۸�
			data_market_price = WareInformationActivity.data_market_price;
			data_goods_id = WareInformationActivity.data_goods_id;
			
			}
			
//			System.out.println("ֵ��1============="+data_shuzu.size());
//			System.out.println("data_tv==ֵ��============="+data_tv.size());
			adapter = new GuiGeListviewAdapter(context,list,data,data1,data2,data3,data4,data5,data6,data_id,data_id1,data_id2,
					data_id3,data_id4,data_id5,data_id6,data_tv,data_shuzu,data_monney,data_market_price,data_goods_id,data_people,
					data_goods_id_1,data_people_1,data_price,data_spec_text);
			listview_01.setAdapter(adapter);
			setListViewHeightBasedOnChildren(listview_01);   
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			}else {
//				ptye = true;
			}
			
//			if (title == null) {
//			progress.CloseProgress();
//			no_data_no.setVisibility(View.VISIBLE);
//			listview_01.setVisibility(View.GONE);
//		}else if (title1 == null) {
//			progress.CloseProgress();
//			no_data_no.setVisibility(View.VISIBLE);
//			listview_01.setVisibility(View.GONE);
//		}else if (title2 == null) {
//			progress.CloseProgress();
//			no_data_no.setVisibility(View.VISIBLE);
//			listview_01.setVisibility(View.GONE);
//		}else{
//			progress.CloseProgress();
//			listview_01.setVisibility(View.VISIBLE);
////			handler.sendEmptyMessage(5);
//			System.out.println("list==ֵ��============="+list.size());
//			ArrayList data_shuzu = WareInformationActivity.data_shuzu;
//			ArrayList data_mrz = WareInformationActivity.data_mrz;
//			ArrayList data_monney = WareInformationActivity.data_monney;
//			System.out.println("ֵ��1============="+data_mrz.size());
//			adapter = new GuiGeListviewAdapter(context,list,data,data1,data2,data3,data4,data5,data6,data_id,data_id1,data_id2,
//					data_id3,data_id4,data_id5,data_id6,data_shuzu,data_mrz,data_monney);
//			listview_01.setAdapter(adapter);
//			setListViewHeightBasedOnChildren(listview_01);   
//		}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {   
        // ��ȡListView��Ӧ��Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()�������������Ŀ   
            View listItem = listAdapter.getView(i, null, listView);   
            // ��������View �Ŀ��   
            listItem.measure(0, 0);    
            // ͳ������������ܸ߶�   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�   
        // params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�   
        listView.setLayoutParams(params);   
    } 
	
	public static class GuiGeListviewAdapter  extends BaseAdapter {
		private List<GuigeData> list;
		private ArrayList data,data1,data2,data3,data4,data5,data6,data_tv;
		private ArrayList data_id,data_id1,data_id2,data_id3,data_id4,data_id5,data_id6,data_shuzu,data_mrz,data_monney,data_market_price,data_goods_id,
		data_people,data_goods_id_1,data_people_1,data_price,data_spec_text;
		private Context context;
		LayoutInflater inflater;  
		GouwucheAdapter MyAdapter;
		GouwucheAdapter MyAdapter1;
		GouwucheAdapter MyAdapter2;
		GouwucheAdapter MyAdapter3;
		GouwucheAdapter MyAdapter4;
		GouwucheAdapter MyAdapter5;
		GouwucheAdapter MyAdapter6;
		String spec_ids;
	    final int TYPE_1 = 0;  
	    final int TYPE_2 = 1;
	    final int TYPE_3 = 2;
	    final int TYPE_4 = 3;
	    final int TYPE_5 = 4;
	    final int TYPE_6 = 5;
	    final int TYPE_7 = 6;
		private ImageLoader imageLoader;
		
		public GuiGeListviewAdapter (ImageLoader imageLoader) {
			// TODO Auto-generated constructor stub
			this.imageLoader = imageLoader;
		} 
		
		public GuiGeListviewAdapter (Context context,List<GuigeData> list,ArrayList data,ArrayList data1,ArrayList data2,ArrayList data3,ArrayList data4,
				ArrayList data5,ArrayList data6,ArrayList data_id,ArrayList data_id1,ArrayList data_id2,ArrayList data_id3,
				ArrayList data_id4,ArrayList data_id5,ArrayList data_id6,ArrayList data_tv,ArrayList data_shuzu//,ArrayList data_mrz
				,ArrayList data_monney,ArrayList data_market_price,ArrayList data_goods_id,ArrayList data_people,ArrayList data_goods_id_1,
				ArrayList data_people_1,ArrayList data_price,ArrayList data_spec_text) {
			try {
				
			this.list = list;
			this.data = data; 
			this.data1 = data1; 
			this.data2 = data2; 
			this.data3 = data3; 
			this.data4 = data4; 
			this.data5 = data5; 
			this.data6 = data6; 
			this.data_tv = data_tv; 
			this.data_id = data_id;
			this.data_id1 = data_id1;
			this.data_id2 = data_id2;
			this.data_id3 = data_id3;
			this.data_id4 = data_id4;
			this.data_id5 = data_id5;
			this.data_id6 = data_id6;
			this.data_shuzu = data_shuzu;
			this.data_monney = data_monney;
			this.data_market_price = data_market_price;
			this.data_goods_id = data_goods_id;
			this.data_people = data_people;
			this.data_goods_id_1 = data_goods_id_1;
			this.data_people_1 = data_people_1;
			this.data_price = data_price;
			this.data_spec_text = data_spec_text;
//			this.data_mrz = data_mrz;
			this.context = context;
			Log.i("data1", data1+"");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		@Override
		public int getCount() {
//			Log.i("data", "=======1======");
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			Log.i("data", "=======2======");
			return list.get(position);
		}
		

		@Override
		public long getItemId(int position) {
			Log.i("data", "=======3======");
			return 0;
		}
		
		// ÿ��convert view������ô˷�������õ�ǰ����Ҫ��view��ʽ  
	    @Override  
	    public int getItemViewType(int position) {
	        int p = position;  
	        if (p == 0)  
	            return TYPE_1;  
	         else if(p == 1) 
	            return TYPE_2;
	         else if(p == 2) 
	            return TYPE_3;
	         else if(p == 3) 
	        	 return TYPE_4;
	         else if(p == 4) 
	        	 return TYPE_5;
	         else if(p == 5) 
	        	 return TYPE_6;
	         else if(p == 6) 
		         return TYPE_7;
	        Log.i("data", "=======4======"+p);
			return p;
			
	    }  
	  
	    @Override  
	    public int getViewTypeCount() {  
	        return 7;  
	    } 
		    public class ViewHolder1 {  
		    	MyGridView gridView;
			    TextView tv_yhwenzi;  
		    }  
		  
		    public class ViewHolder2 {
		    	MyGridView gridView;
		    	TextView tv_yhwenzi;  
		    }
		    public class ViewHolder3 {
		    	MyGridView gridView;
		    	TextView tv_yhwenzi;  
		    }
		    public class ViewHolder4 {
		    	MyGridView gridView;
		    	TextView tv_yhwenzi;  
		    }
		    public class ViewHolder5 {
		    	MyGridView gridView;
		    	TextView tv_yhwenzi;  
		    }
		    public class ViewHolder6 {
		    	MyGridView gridView;
		    	TextView tv_yhwenzi;  
		    }
		    public class ViewHolder7 {
		    	MyGridView gridView;
		    	TextView tv_yhwenzi;  
		    }
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			try {
				
			    ViewHolder1 holder1 = null;  
		        ViewHolder2 holder2 = null;  
		        ViewHolder3 holder3 = null;  
		        ViewHolder4 holder4 = null;  
		        ViewHolder5 holder5 = null;  
		        ViewHolder6 holder6 = null;  
		        ViewHolder7 holder7 = null;  
		        int type = getItemViewType(position);  
		        
	        if (convertView == null) {  
	        	try {
					
	        	 inflater = LayoutInflater.from(context);  
	            // ����ǰ�������ʽ��ȷ��new�Ĳ���  
	        	 System.out.println("position==================11==============================="+position);
	        	 System.out.println("list.get(position).getTitle()==11======="+list.get(position).getTitle());
	            switch (type) {  
	            case TYPE_1:  
	                convertView = inflater.inflate(R.layout.guige_item,   parent, false);  
//	                View vi = LayoutInflater.from(context).inflate(R.layout.guige_item,null);
	                holder1 = new ViewHolder1();  
	                holder1.tv_yhwenzi = (TextView) convertView .findViewById(R.id.tv_zhuti);  
	                holder1.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
	                convertView.setTag(holder1);  
	                
	                break;  
	            case TYPE_2:  
	            	 convertView = inflater.inflate(R.layout.guige_item,   parent, false);  
	                 holder2 = new ViewHolder2();  
	                 holder2.tv_yhwenzi = (TextView) convertView  .findViewById(R.id.tv_zhuti);  
	                 holder2.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
	                 convertView.setTag(holder2);  
	                break;  
	            case TYPE_3:  
	           	    convertView = inflater.inflate(R.layout.guige_item,   parent, false);  
	                holder3 = new ViewHolder3();  
	                holder3.tv_yhwenzi = (TextView) convertView  .findViewById(R.id.tv_zhuti);  
	                holder3.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
	                convertView.setTag(holder3);  
	               
	               break;  
	            case TYPE_4:  
		           	 convertView = inflater.inflate(R.layout.guige_item,   parent, false);  
		                holder4 = new ViewHolder4();  
		                holder4.tv_yhwenzi = (TextView) convertView  .findViewById(R.id.tv_zhuti);  
		                holder4.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
		                convertView.setTag(holder4);  
		               break; 
	            case TYPE_5:  
		           	    convertView = inflater.inflate(R.layout.guige_item,   parent, false);  
		                holder5 = new ViewHolder5();  
		                holder5.tv_yhwenzi = (TextView) convertView  .findViewById(R.id.tv_zhuti);  
		                holder5.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
		                convertView.setTag(holder5);  
		               
		               break; 
	            case TYPE_6:  
		           	    convertView = inflater.inflate(R.layout.guige_item,   parent, false);  
		                holder6 = new ViewHolder6();  
		                holder6.tv_yhwenzi = (TextView) convertView  .findViewById(R.id.tv_zhuti);  
		                holder6.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
		                convertView.setTag(holder6);  
		               
		               break; 
	            case TYPE_7:  
		           	    convertView = inflater.inflate(R.layout.guige_item,   parent, false);  
		                holder7 = new ViewHolder7();  
		                holder7.tv_yhwenzi = (TextView) convertView  .findViewById(R.id.tv_zhuti);  
		                holder7.gridView=(MyGridView) convertView.findViewById(R.id.gridView);
		                convertView.setTag(holder7);  
		               
		               break; 
	            case 7:  
	            	System.out.println("position==7======="+position);
	               break; 
	     
	            default:  
	                break;  
	            }  
	            
	    	} catch (Exception e) {
				// TODO: handle exception
	    		e.printStackTrace();
			}
	  
	        } else {  
	            switch (type) {  
	            case TYPE_1:  
	                holder1 = (ViewHolder1) convertView.getTag();  
	                break;  
	            case TYPE_2:  
	                holder2 = (ViewHolder2) convertView.getTag();  
	                break;  
	            case TYPE_3:  
	                holder3 = (ViewHolder3) convertView.getTag();  
	                break;  
	            case TYPE_4:  
	                holder4 = (ViewHolder4) convertView.getTag();  
	                break;  
	            case TYPE_5:  
	                holder5 = (ViewHolder5) convertView.getTag();  
	                break;  
	            case TYPE_6:  
	                holder6 = (ViewHolder6) convertView.getTag();  
	                break;  
	            case TYPE_7:  
	                holder7 = (ViewHolder7) convertView.getTag();  
	                break;  
	            
	            }  
	        }
	        
	        // ������Դ  
	        switch (type) {
	        
	        case TYPE_1:  
	        	try {
					
	        	System.out.println("position==0======="+position);
	        	System.out.println("list.get(position).getTitle()==0======="+list.get(position).getTitle());
	        	holder1.tv_yhwenzi.setText(list.get(position).getTitle());
//	        	holder1.tv_yhwenzi.setText((String)data_tv.get(position));
	           	if (data.size() > 0) {
	        	MyAdapter=new GouwucheAdapter(data,context);
	        	holder1.gridView.setAdapter(MyAdapter);
	        	
	        	holder1.gridView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		            	try {
		            	MyAdapter.setSeclection(arg2);
		            	MyAdapter.notifyDataSetChanged();
		            	id = (String)data_id.get(arg2);
		            	gk_id = Integer.parseInt(id);
		            	xz_pyte = "0_xz";//�ж��Ƿ�ѡ��
//		            	String zhi = (String)data_mrz.get(arg2);
//		            	System.out.println("Ĭ��ֵ====="+zhi);
		            	System.out.println("idֵ��====="+id);
//		            	System.out.println("ֵ========="+data_shuzu.get(arg2));
		            	String monney1 = (String)data_monney.get(arg2);
						System.out.println("�۸�========="+monney1);
//		            	if (data.size() > 0) {
//		            		spec_ids  = ","+id+",";
//						}else if (data1.size() > 0){
//							spec_ids  = ","+id+","+id1+",";
//						}else if (data2.size() > 0){
//							spec_ids  = ","+id+","+id1+","+id2+",";
//						}else if (data3.size() > 0){
//							spec_ids  = ","+id+","+id1+","+id2+","+id3+",";
//						}else if (data4.size() > 0){
//							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+",";
//						}else if (data5.size() > 0){
//							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+",";
						
						if (data6.size() > 0 && data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+","+id6+",";
						}else if (data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+",";
						}else if (data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+",";
						}else if (data3.size() > 0 && data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+",";
						}else if (data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+",";
						}else if (data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+",";
						}else if (data.size() > 0){
							spec_ids  = ","+id+",";
						}
//		            	spec_ids  = ","+id+","+id1+","+id2+",";
		            	 System.out.println("ƴ�ӵ�ֵ========="+spec_ids);
		            	 System.out.println("data_shuzu.get(arg2)========="+data_shuzu.get(arg2));
		            	 if (spec_ids.equals(data_shuzu.get(arg2))) {
		            			System.out.println("JuJingCaiXqActivity.type_spec_item=================1=================="+JuJingCaiXqActivity.type_spec_item);
		            			//�жϾ�������
				     			if (JuJingCaiXqActivity.type_spec_item == true) {
				     				System.out.println("arg2====================="+arg2);
				     				System.out.println("tuanshu====================="+tuanshu);
				     				 System.out.println("data_goods_id.get(arg2)========="+data_goods_id.get(arg2));
//				     				 System.out.println("data_goods_id_1.get(arg2)========="+data_goods_id_1.get(arg2));
//				     				 System.out.println("data_people.get(arg2)========="+data_people.get(arg2));
//				     				 System.out.println("data_people_1.get(arg2)========="+data_people_1.get(arg2));
				     				 
				     				 for (int i = 0; i < data_goods_id_1.size(); i++) {
				     					 if (data_goods_id.get(arg2).equals(data_goods_id_1.get(i)) && tuanshu.equals(data_people_1.get(i))) {
						     				 System.out.println("data_goods_id_1.get(i)========="+data_goods_id_1.get(i));
						     				 System.out.println("tuanshu=================="+tuanshu);
						     				 System.out.println("data_people_1.get(i)========="+data_people_1.get(i));
						     				goods_id = (String)data_goods_id_1.get(i);
				     						price = (String)data_price.get(i);
				     						System.out.println("�۸�price====1====="+price);
					     					market_information_sep_price.setText("��" + price);
					     					spec_text = (String)data_spec_text.get(arg2);
					     					System.out.println("spec_text=====1====="+spec_text);
					     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
										}
									}
//					     				if (data_people.get(arg2).equals(data_people_1.get(arg2))) {
//						     			if (data_goods_id.get(arg2).equals(data_goods_id_1.get(arg2)) && data_people.get(arg2).equals(data_people_1.get(arg2))) {
//				     					String price = (String)data_price.get(arg2);
//				     					System.out.println("�۸�price====1====="+price);
//				     					goods_id = (String)data_goods_id_1.get(arg2);
//				     					market_information_sep_price.setText("��" + price);
//				     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
//				     					people = (String)data_people_1.get(arg2);
//				     					System.out.println("people========="+people);
//									}else {
//										String price = (String)data_price.get(arg2);
//				     					System.out.println("�۸�price====12====="+price);
//				     					goods_id = (String)data_goods_id.get(arg2);
//				     					market_information_sep_price.setText("��" + price);
//				     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
//				     					people = (String)data_people_1.get(arg2);
//				     					System.out.println("people===2======"+people);
//									}
				     			}else {
		            		 monney_sx_list = (String)data_monney.get(arg2);
//		            		 market_price = (String)data_market_price.get(arg2);
		            		 goods_id = (String)data_goods_id.get(arg2);
								System.out.println("�۸�0========="+monney_sx_list);
//								System.out.println("market_price========="+market_price);
								System.out.println("goods_id========="+goods_id);
			            		market_information_sep_price.setText("��" + (String)data_monney.get(arg2));
			            		people = (String)data_people_1.get(arg2);
		     					System.out.println("people===3======"+people);
							}
		     			}
		            	 System.out.println("11/////////////////////////////////////////////////"); 
		            	} catch (Exception e) {
		    				// TODO: handle exception
		            		e.printStackTrace();
		    			}
		            }
		        });
	           	}
	        	} catch (Exception e) {
					// TODO: handle exception
	        		e.printStackTrace();
				}
	            break;  
	        case TYPE_2:  
//	        	 convertView.setVisibility(View.VISIBLE);  
	        	try {
	        		System.out.println("position==1======="+position);
//	        		
	        		System.out.println("list.get(position).getTitle()==1======="+list.get(position).getTitle());
//	        	holder2.tv_yhwenzi.setText(list.get(position).getTitle());	
//	        		holder1.tv_yhwenzi.setText((String)data_tv.get(position));
	        	System.out.println("data1.size()==1======="+data1.size());
	        	if (data1.size() > 0) {
	        	holder2.tv_yhwenzi.setText(list.get(position).getTitle());	
	        	MyAdapter1=new GouwucheAdapter(data1,context);
	        	holder2.gridView.setAdapter(MyAdapter1);
	        	
	        	holder2.gridView.setOnItemClickListener(new OnItemClickListener() {
		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		              	try {
		            	MyAdapter1.setSeclection(arg2);
		            	MyAdapter1.notifyDataSetChanged();
		            	id1 = (String)data_id1.get(arg2);
		            	gk_id1 = Integer.parseInt(id1);
		            	xz_pyte1 = "1_xz";//�ж��Ƿ�ѡ��
//		            	String zhi = (String)data_mrz.get(arg2);
//		            	System.out.println("Ĭ��ֵ====="+zhi);
		            	System.out.println("idֵ��1====="+id1);
//		            	System.out.println("ֵ1========="+data_shuzu.get(arg2));
		            	String monney1 = (String)data_monney.get(arg2);
						System.out.println("�۸�========="+monney1);
						if (data6.size() > 0 && data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+","+id6+",";
						}else if (data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+",";
						}else if (data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+",";
						}else if (data3.size() > 0 && data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+",";
						}else if (data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+",";
						}else if (data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+",";
						}else if (data.size() > 0){
							spec_ids  = ","+id+",";
						}
//		            	spec_ids  = ","+id+","+id1+","+id2+",";
		            	 System.out.println("ƴ�ӵ�ֵ========="+spec_ids);
		            	 System.out.println("data_shuzu.get(arg2)========="+data_shuzu.get(arg2));
		            	 if (spec_ids.equals(data_shuzu.get(arg2))) {
		            		 
		          			//�жϾ�������
				     			if (JuJingCaiXqActivity.type_spec_item == true) {
//				     				System.out.println("arg2====================="+arg2);
				     				System.out.println("JuJingCaiXqActivity.type_spec_item==================2================="+JuJingCaiXqActivity.type_spec_item);
				     				System.out.println("tuanshu====================="+tuanshu);
				     				System.out.println("data_goods_id.get(arg2)========="+data_goods_id.get(arg2));
//				     				 System.out.println("data_goods_id_1.get(arg2)========="+data_goods_id_1.get(arg2));
//				     				 System.out.println("data_people.get(arg2)========="+data_people.get(arg2));
//				     				 System.out.println("data_people_1.get(arg2)========="+data_people_1.get(arg2));
				     				 
				     				 for (int i = 0; i < data_goods_id_1.size(); i++) {
				     					 if (data_goods_id.get(arg2).equals(data_goods_id_1.get(i)) && tuanshu.equals(data_people_1.get(i))) {
						     				 System.out.println("data_goods_id_1.get(i)========="+data_goods_id_1.get(i));
						     				 System.out.println("tuanshu=================="+tuanshu);
						     				 System.out.println("data_people_1.get(i)========="+data_people_1.get(i));
						     				goods_id = (String)data_goods_id_1.get(i);
				     						price = (String)data_price.get(i);
				     						System.out.println("�۸�price====1====="+price);
					     					market_information_sep_price.setText("��" + price);
					     					spec_text = (String)data_spec_text.get(arg2);
					     					System.out.println("spec_text=====1====="+spec_text);
					     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
										}
									}
				     				 
//				     				if (data_people.get(arg2).equals(data_people_1.get(arg2))) {
//				     				if (data_goods_id.get(arg2).equals(data_goods_id_1.get(arg2)) && data_people.get(arg2).equals(data_people_1.get(arg2))) {
//				     					String price = (String)data_price.get(arg2);
//				     					System.out.println("�۸�price=====2===="+price);
//				     					goods_id = (String)data_goods_id_1.get(arg2);
//				     					market_information_sep_price.setText("��" + price);
//				     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
//				     					people = (String)data_people_1.get(arg2);
//				     					System.out.println("people========="+people);
//									}else {
//										String price = (String)data_price.get(arg2);
//				     					System.out.println("�۸�price====12====="+price);
//				     					goods_id = (String)data_goods_id.get(arg2);
//				     					market_information_sep_price.setText("��" + price);
//				     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
//				    					people = (String)data_people_1.get(arg2);
//				     					System.out.println("people===2======"+people);
//									}
				     			}else {
		            		     monney_sx_list = (String)data_monney.get(arg2);
//		            			 market_price = (String)data_market_price.get(arg2);
			            		 goods_id = (String)data_goods_id.get(arg2);
			            		 System.out.println("goods_id========="+goods_id);
								System.out.println("�۸�1========="+monney_sx_list);
			            		market_information_sep_price.setText("��" + monney_sx_list);
			            		people = (String)data_people_1.get(arg2);
		     					System.out.println("people===3======"+people);
				     			}
							}
		            	 System.out.println("22/////////////////////////////////////////////////");
		              	} catch (Exception e) {
		    				// TODO: handle exception
		            		e.printStackTrace();
		    			}
		            }
		        });
	        	}else {
	        		holder2.tv_yhwenzi.setText(View.GONE);	
				}
	        	} catch (Exception e) {
					// TODO: handle exception
	        		e.printStackTrace();
				}
	        	
	            break;
	            
	        case TYPE_3:  
	        	try {
	        		convertView.setVisibility(View.VISIBLE);  
	        	System.out.println("position==2======="+position);
	        	System.out.println("list.get(position).getTitle()==2======="+list.get(position).getTitle());
	        	holder3.tv_yhwenzi.setText(list.get(position).getTitle());	
//	        	holder1.tv_yhwenzi.setText((String)data_tv.get(position));
	        	if (data2.size() > 0) {
	        	MyAdapter2=new GouwucheAdapter(data2,context);
	        	holder3.gridView.setAdapter(MyAdapter2);
	        	
	        	holder3.gridView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		              	try {
		            	MyAdapter2.setSeclection(arg2);
		            	MyAdapter2.notifyDataSetChanged();
		            	id2 = (String)data_id2.get(arg2);
//		            	String zhi = (String)data_mrz.get(arg2);
		            	gk_id2 = Integer.parseInt(id2);
		            	xz_pyte2 = "2_xz";//�ж��Ƿ�ѡ��
//		            	System.out.println("Ĭ��ֵ====="+zhi);
		            	System.out.println("idֵ��2====="+id2);
		            	if (data6.size() > 0 && data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+","+id6+",";
						}else if (data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+",";
						}else if (data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+",";
						}else if (data3.size() > 0 && data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+",";
						}else if (data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+",";
						}else if (data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+",";
						}else if (data.size() > 0){
							spec_ids  = ","+id+",";
						}
//		            	spec_ids  = ","+id+","+id1+","+id2+",";
		            	
		                System.out.println("ƴ�ӵ�ֵ========="+spec_ids);
		                System.out.println("ֵ2========="+data_shuzu.get(arg2));
		                String monney1 = (String)data_monney.get(arg2);
						System.out.println("�۸�========="+monney1);
						
		                if (spec_ids.equals(data_shuzu.get(arg2))) {
		          			//�жϾ�������
			     			if (JuJingCaiXqActivity.type_spec_item == true) {
			     				System.out.println("JuJingCaiXqActivity.type_spec_item==================2================="+JuJingCaiXqActivity.type_spec_item);
			     				System.out.println("tuanshu====================="+tuanshu);
			     				System.out.println("data_goods_id.get(arg2)========="+data_goods_id.get(arg2));
			     				 
			     				 for (int i = 0; i < data_goods_id_1.size(); i++) {
			     					 if (data_goods_id.get(arg2).equals(data_goods_id_1.get(i)) && tuanshu.equals(data_people_1.get(i))) {
					     				goods_id = (String)data_goods_id_1.get(i);
			     						price = (String)data_price.get(i);
				     					market_information_sep_price.setText("��" + price);
				     					spec_text = (String)data_spec_text.get(arg2);
				     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
									}
								}
			     			}else {
		                	monney_sx_list = (String)data_monney.get(arg2);
//		                 	market_price = (String)data_market_price.get(arg2);
	            		    goods_id = (String)data_goods_id.get(arg2);
							System.out.println("�۸�2========="+monney_sx_list);
		            		market_information_sep_price.setText("��:" + (String)data_monney.get(arg2));
		            		people = (String)data_people_1.get(arg2);
	     					System.out.println("people===3======"+people);
			     			}
						}
		                
//						}
		              	} catch (Exception e) {
		    				// TODO: handle exception
		    				e.printStackTrace();
		    			}
		            }
		              	
		        });
	        	}
	        } catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
			}
	            break;  
	        case TYPE_4:  
	        	try {
	        	System.out.println("position==3======="+position);
	        	System.out.println("list.get(position).getTitle()==3======="+list.get(position).getTitle());
	        	holder4.tv_yhwenzi.setText(list.get(position).getTitle());	
//	        	holder1.tv_yhwenzi.setText((String)data_tv.get(position));
	        	if (data3.size() > 0) {
//	        		holder4.tv_yhwenzi.setText(list.get(position).getTitle());	
	        	MyAdapter4=new GouwucheAdapter(data4,context);
	        	holder4.gridView.setAdapter(MyAdapter4);
	        	
	        	holder4.gridView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		              	try {
		            	MyAdapter3.setSeclection(arg2);
		            	MyAdapter3.notifyDataSetChanged();
		            	id2 = (String)data_id2.get(arg2);
//		            	String zhi = (String)data_mrz.get(arg2);
		            	gk_id2 = Integer.parseInt(id2);
		            	xz_pyte3 = "3_xz";//�ж��Ƿ�ѡ��
//		            	System.out.println("Ĭ��ֵ====="+zhi);
		            	System.out.println("idֵ��2====="+id2);
		            	if (data6.size() > 0 && data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+","+id6+",";
						}else if (data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+",";
						}else if (data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+",";
						}else if (data3.size() > 0 && data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+",";
						}else if (data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+",";
						}else if (data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+",";
						}else if (data.size() > 0){
							spec_ids  = ","+id+",";
						}
//		            	spec_ids  = ","+id+","+id1+","+id2+",";
		                System.out.println("ƴ�ӵ�ֵ========="+spec_ids);
		                System.out.println("ֵ2========="+data_shuzu.get(arg2));
		                String monney1 = (String)data_monney.get(arg2);
						System.out.println("�۸�========="+monney1);
						
//						market_information_sep_price.setText("��" + monney1);
		                if (spec_ids.equals(data_shuzu.get(arg2))) {
		          			//�жϾ�������
			     			if (JuJingCaiXqActivity.type_spec_item == true) {
			     				System.out.println("tuanshu====================="+tuanshu);
			     				System.out.println("data_goods_id.get(arg2)========="+data_goods_id.get(arg2));
			     				 
			     				 for (int i = 0; i < data_goods_id_1.size(); i++) {
			     					 if (data_goods_id.get(arg2).equals(data_goods_id_1.get(i)) && tuanshu.equals(data_people_1.get(i))) {
					     				goods_id = (String)data_goods_id_1.get(i);
			     						price = (String)data_price.get(i);
				     					market_information_sep_price.setText("��" + price);
				     					spec_text = (String)data_spec_text.get(arg2);
				     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
									}
								}
			     			}else {
		                	monney_sx_list = (String)data_monney.get(arg2);
//		               	 market_price = (String)data_market_price.get(arg2);
	            		 goods_id = (String)data_goods_id.get(arg2);
							System.out.println("�۸�2========="+monney_sx_list);
		            		market_information_sep_price.setText("��:" + (String)data_monney.get(arg2));
		            		people = (String)data_people_1.get(arg2);
	     					System.out.println("people===3======"+people);
			     			}
						}
		                
//						}
		              	} catch (Exception e) {
		    				// TODO: handle exception
		    				e.printStackTrace();
		    			}
		            }
		              	
		        });
	        	}
			} catch (Exception e) {
				// TODO: handle exception
        		e.printStackTrace();
			}
	            break; 
	        case TYPE_5:  
	        	try {
	        	System.out.println("position==4======="+position);
	        	System.out.println("list.get(position).getTitle()==4======="+list.get(position).getTitle());
	        	holder5.tv_yhwenzi.setText(list.get(position).getTitle());
//	        	holder1.tv_yhwenzi.setText((String)data_tv.get(position));
	        	if (data4.size() > 0) {
	        	MyAdapter2=new GouwucheAdapter(data2,context);
	        	holder5.gridView.setAdapter(MyAdapter2);
	        	
	        	holder3.gridView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		              	try {
		            	MyAdapter4.setSeclection(arg2);
		            	MyAdapter4.notifyDataSetChanged();
		            	id2 = (String)data_id2.get(arg2);
//		            	String zhi = (String)data_mrz.get(arg2);
		            	gk_id2 = Integer.parseInt(id2);
		            	xz_pyte4 = "4_xz";//�ж��Ƿ�ѡ��
//		            	System.out.println("Ĭ��ֵ====="+zhi);
		            	System.out.println("idֵ��2====="+id2);
		            	if (data6.size() > 0 && data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+","+id6+",";
						}else if (data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+",";
						}else if (data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+",";
						}else if (data3.size() > 0 && data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+",";
						}else if (data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+",";
						}else if (data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+",";
						}else if (data.size() > 0){
							spec_ids  = ","+id+",";
						}
//		            	spec_ids  = ","+id+","+id1+","+id2+",";
		                System.out.println("ƴ�ӵ�ֵ========="+spec_ids);
		                System.out.println("ֵ2========="+data_shuzu.get(arg2));
		                String monney1 = (String)data_monney.get(arg2);
						System.out.println("�۸�========="+monney1);
						
//						market_information_sep_price.setText("��" + monney1);
						
		                if (spec_ids.equals(data_shuzu.get(arg2))) {
		          			//�жϾ�������
			     			if (JuJingCaiXqActivity.type_spec_item == true) {
			     				System.out.println("tuanshu====================="+tuanshu);
			     				System.out.println("data_goods_id.get(arg2)========="+data_goods_id.get(arg2));
			     				 
			     				 for (int i = 0; i < data_goods_id_1.size(); i++) {
			     					 if (data_goods_id.get(arg2).equals(data_goods_id_1.get(i)) && tuanshu.equals(data_people_1.get(i))) {
					     				goods_id = (String)data_goods_id_1.get(i);
			     						price = (String)data_price.get(i);
				     					market_information_sep_price.setText("��" + price);
				     					spec_text = (String)data_spec_text.get(arg2);
				     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
									}
								}
			     			}else {
		                	monney_sx_list = (String)data_monney.get(arg2);
//		               	 market_price = (String)data_market_price.get(arg2);
	            		 goods_id = (String)data_goods_id.get(arg2);
							System.out.println("�۸�2========="+monney_sx_list);
		            		market_information_sep_price.setText("��:" + (String)data_monney.get(arg2));
		            		people = (String)data_people_1.get(arg2);
	     					System.out.println("people===3======"+people);
			     			}
						}
		                
//						}
		              	} catch (Exception e) {
		    				// TODO: handle exception
		    				e.printStackTrace();
		    			}
		            }
		              	
		        });
	        	}
		} catch (Exception e) {
			// TODO: handle exception
    		e.printStackTrace();
		}
	            break; 
	        case TYPE_6:  
	        	try {
	        	System.out.println("position==5======="+position);
	        	System.out.println("list.get(position).getTitle()==5======="+list.get(position).getTitle());
	        	holder6.tv_yhwenzi.setText(list.get(position).getTitle());	
//	        	holder1.tv_yhwenzi.setText((String)data_tv.get(position));
	        	if (data5.size() > 0) {
	        	MyAdapter2=new GouwucheAdapter(data2,context);
	        	holder5.gridView.setAdapter(MyAdapter2);
	        	
	        	holder3.gridView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		              	try {
		            	MyAdapter5.setSeclection(arg2);
		            	MyAdapter5.notifyDataSetChanged();
		            	id2 = (String)data_id2.get(arg2);
//		            	String zhi = (String)data_mrz.get(arg2);
		            	gk_id2 = Integer.parseInt(id2);
		            	xz_pyte5 = "5_xz";//�ж��Ƿ�ѡ��
//		            	System.out.println("Ĭ��ֵ====="+zhi);
		            	System.out.println("idֵ��2====="+id2);
		            	if (data6.size() > 0 && data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+","+id6+",";
						}else if (data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+",";
						}else if (data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+",";
						}else if (data3.size() > 0 && data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+",";
						}else if (data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+",";
						}else if (data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+",";
						}else if (data.size() > 0){
							spec_ids  = ","+id+",";
						}
//		            	spec_ids  = ","+id+","+id1+","+id2+",";
		                System.out.println("ƴ�ӵ�ֵ========="+spec_ids);
		                System.out.println("ֵ2========="+data_shuzu.get(arg2));
		                String monney1 = (String)data_monney.get(arg2);
						System.out.println("�۸�========="+monney1);
						
//						market_information_sep_price.setText("��" + monney1);
		                if (spec_ids.equals(data_shuzu.get(arg2))) {
		          			//�жϾ�������
			     			if (JuJingCaiXqActivity.type_spec_item == true) {
			     				System.out.println("tuanshu====================="+tuanshu);
			     				System.out.println("data_goods_id.get(arg2)========="+data_goods_id.get(arg2));
			     				 
			     				 for (int i = 0; i < data_goods_id_1.size(); i++) {
			     					 if (data_goods_id.get(arg2).equals(data_goods_id_1.get(i)) && tuanshu.equals(data_people_1.get(i))) {
					     				goods_id = (String)data_goods_id_1.get(i);
			     						price = (String)data_price.get(i);
				     					market_information_sep_price.setText("��" + price);
				     					spec_text = (String)data_spec_text.get(arg2);
				     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
									}
								}
			     			}else {
		                	monney_sx_list = (String)data_monney.get(arg2);
//		               	    market_price = (String)data_market_price.get(arg2);
	            		    goods_id = (String)data_goods_id.get(arg2);
							System.out.println("�۸�2========="+monney_sx_list);
		            		market_information_sep_price.setText("��:" + (String)data_monney.get(arg2));
		            		people = (String)data_people_1.get(arg2);
	     					System.out.println("people===3======"+people);
			     			}
						}
		                
//						}
		              	} catch (Exception e) {
		    				// TODO: handle exception
		    				e.printStackTrace();
		    			}
		            }
		              	
		        });
	        	}
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
	            break; 
	        case TYPE_7:  
	        	try {
	        	System.out.println("position==6======="+position);
	        	System.out.println("list.get(position).getTitle()==6======="+list.get(position).getTitle());
	        	holder7.tv_yhwenzi.setText(list.get(position).getTitle());	
//	        	holder1.tv_yhwenzi.setText((String)data_tv.get(position));
	        	if (data6.size() > 0) {
	        	MyAdapter2=new GouwucheAdapter(data2,context);
	        	holder5.gridView.setAdapter(MyAdapter2);
	        	
	        	holder3.gridView.setOnItemClickListener(new OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		              	try {
		            	MyAdapter6.setSeclection(arg2);
		            	MyAdapter6.notifyDataSetChanged();
		            	id2 = (String)data_id2.get(arg2);
//		            	String zhi = (String)data_mrz.get(arg2);
		            	gk_id2 = Integer.parseInt(id2);
		            	xz_pyte6 = "6_xz";//�ж��Ƿ�ѡ��
//		            	System.out.println("Ĭ��ֵ====="+zhi);
		            	System.out.println("idֵ��2====="+id2);
		            	if (data6.size() > 0 && data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+","+id6+",";
						}else if (data5.size() > 0 && data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+","+id5+",";
						}else if (data4.size() > 0 && data3.size() > 0 && data2.size() > 0
								&& data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+","+id4+",";
						}else if (data3.size() > 0 && data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+","+id3+",";
						}else if (data2.size() > 0 && data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+","+id2+",";
						}else if (data1.size() > 0 && data.size() > 0){
							spec_ids  = ","+id+","+id1+",";
						}else if (data.size() > 0){
							spec_ids  = ","+id+",";
						}
//		            	spec_ids  = ","+id+","+id1+","+id2+",";
		                System.out.println("ƴ�ӵ�ֵ========="+spec_ids);
		                System.out.println("ֵ2========="+data_shuzu.get(arg2));
		                String monney1 = (String)data_monney.get(arg2);
						System.out.println("�۸�========="+monney1);
						
//						market_information_sep_price.setText("��" + monney1);
		                if (spec_ids.equals(data_shuzu.get(arg2))) {
		          			//�жϾ�������
			     			if (JuJingCaiXqActivity.type_spec_item == true) {
			     				System.out.println("tuanshu====================="+tuanshu);
			     				System.out.println("data_goods_id.get(arg2)========="+data_goods_id.get(arg2));
			     				 
			     				 for (int i = 0; i < data_goods_id_1.size(); i++) {
			     					 if (data_goods_id.get(arg2).equals(data_goods_id_1.get(i)) && tuanshu.equals(data_people_1.get(i))) {
					     				goods_id = (String)data_goods_id_1.get(i);
			     						price = (String)data_price.get(i);
				     					market_information_sep_price.setText("��" + price);
				     					spec_text = (String)data_spec_text.get(arg2);
				     					tv_guige.setText("��" + (String)data_spec_text.get(arg2));
									}
								}
			     			}else {
		                	monney_sx_list = (String)data_monney.get(arg2);
//		               	 market_price = (String)data_market_price.get(arg2);
	            		 goods_id = (String)data_goods_id.get(arg2);
							System.out.println("�۸�2========="+monney_sx_list);
		            		market_information_sep_price.setText("��:" + (String)data_monney.get(arg2));
		            		people = (String)data_people_1.get(arg2);
	     					System.out.println("people===3======"+people);
			     			}
						}
		                
//						}
		              	} catch (Exception e) {
		    				// TODO: handle exception
		    				e.printStackTrace();
		    			}
		            }
		              	
		        });
	        	}
} catch (Exception e) {
	// TODO: handle exception
	e.printStackTrace();
}
	            break; 
	       
	        }
	        
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return convertView;
		}
		
	}
}
