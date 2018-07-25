package com.lelinju.www;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.MyAdapter;
import com.hengyushop.airplane.adapter.MyShopingCartAdapter;
import com.hengyushop.airplane.adapter.MyShopingCartllAdapter;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.my.MyShopPingCarActivity;
import com.hengyushop.demo.wec.NewWare;
import com.hengyushop.entity.ShopCartBean;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;
//import com.hengyushop.airplane.adapter.MyShopingCartllAdapter.ViewHolder;
/**
 * ���̾۹��ﳵ
 * @author Administrator
 *
 */
public class ShoppingCartActivity extends Fragment{
	private ListView list_shop_cart;
	private Button btn_sittle_account;
	private TextView tv_endnumber, tv_endmarketprice, tv_preferential,
			tv_endmoney, jf,tv_shanchu;
	private LinearLayout list_shops, list_none,ll_xianshi;
	private WareDao wareDao;
	private MyShopingCartllAdapter adapter;
	private MyAdapter madapter;
	private ShopCartData dm;
	private ShopCartData data;
	private ShopCartBean bean;
	private DialogProgress progress;
	private String strUrl;
	private String yth;
	private MyPopupWindowMenu popupWindowMenu;
	private EditText tv_amount_jf;
	private UserRegisterData registerData;
	private CheckBox in_jf,shopcart_item_check;
	ArrayList<ShopCartData> list_ll = new ArrayList<ShopCartData>();
	static StringBuffer sb;
	public static StringBuffer str,str1,str2,str3;
	int shopping_id;
	String id;
	private int ID;
	private int checkNum; // ��¼ѡ�е���Ŀ����
	ArrayList<ShopCartBean> list;
	private SharedPreferences spPreferences;
	public static String user_name,user_id;
	private static List<String> list_id = new ArrayList<String>(); 
	public static String total_cll;
//	List<String> list_cart_id;
//	List<String> list_goods_id;
//	List<Integer> list_quantity;
	public static List<String> list_cart_id = new ArrayList<String>();  
	public static List<String> list_goods_id = new ArrayList<String>(); 
	public static List<Integer> list_quantity = new ArrayList<Integer>(); 
	private LinearLayout adv_pager;
	private FrameLayout adv_pager2;
	private Button btn_register;
	double money_heji;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layout = inflater.inflate(R.layout.shopping_cart, null);
		progress = new DialogProgress(getActivity());
		spPreferences = getActivity().getSharedPreferences("longuserset", Context.MODE_PRIVATE);
		user_name = spPreferences.getString("user_name", "");
		user_id = spPreferences.getString("user_id", "");
		ininate(layout);
		 //�ڴ˵������淽�������ܲ����߳��е��쳣
//        Thread.setDefaultUncaughtExceptionHandler(this);
//		loadWeather();
		return layout;
	}
	
//	@Override
//	public void uncaughtException(Thread arg0, Throwable arg1) {
//		// TODO Auto-generated method stub
//		 //�ڴ˴����쳣�� arg1��Ϊ���񵽵��쳣
//        Log.i("AAA", "uncaughtException   " + arg1);
//	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("2================");
		MyShoppingCartllActivity.str = null;
//		List<String> list_cart_id = MyShopingCartllAdapter.list_cart_id;
//		 List<String> list_goods_id = MyShopingCartllAdapter.list_goods_id;
//		 List<Integer> list_quantity = MyShopingCartllAdapter.list_quantity;
		 System.out.println("list_cart_id================"+list_cart_id.size());
		if (list_cart_id.size() > 0) {
			list_cart_id.clear();
			list_goods_id.clear();
			list_quantity.clear();
			list_id.clear();
			System.out.println("list_id=====1==========="+list_id);
//			list_ll.clear();
			loadWeather();
			System.out.println("list_cart_id2================"+list_cart_id.size());
		}else {
			list_id.clear();
			System.out.println("list_id======2=========="+list_id);
//			list_ll.clear();
			loadWeather();
		}
		
	}
	
	/**
	 * ��ʼ���ؼ����
	 */
	private void ininate(View layout) {
		adv_pager = (LinearLayout)layout.findViewById(R.id.adv_pager);
		adv_pager2 = (FrameLayout)layout.findViewById(R.id.adv_pager2);
		list_none = (LinearLayout)layout.findViewById(R.id.list_none);
		ll_xianshi = (LinearLayout)layout.findViewById(R.id.ll_xianshi);
		list_shops = (LinearLayout)layout.findViewById(R.id.list_shops);
		in_jf = (CheckBox)layout.findViewById(R.id.in_jf);
		shopcart_item_check = (CheckBox)layout.findViewById(R.id.shopcart_item_check);
		btn_sittle_account = (Button) layout.findViewById(R.id.btn_settle_accounts);
		list_shop_cart = (ListView)layout.findViewById(R.id.list_shop_cart);
		tv_endnumber = (TextView)layout.findViewById(R.id.tv_number);
		tv_shanchu = (TextView) layout.findViewById(R.id.tv_shanchu);
		tv_endmarketprice = (TextView)layout.findViewById(R.id.tv_original_price);
		tv_preferential = (TextView)layout.findViewById(R.id.tv_preferential);
		tv_endmoney = (TextView)layout.findViewById(R.id.tv_amount_payable);
		tv_amount_jf = (EditText)layout.findViewById(R.id.tv_amount_jf);
		jf = (TextView)layout.findViewById(R.id.jf);
		list_shop_cart.setCacheColorHint(0);
		
		btn_register = (Button) layout.findViewById(R.id.btn_register);
		
//		list_shop_cart.setOnItemClickListener(new OnItemClickListener() {
//            
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				try {
//					
//				// TODO Auto-generated method stub
//				String id = lists.get(arg2).getId();
//				System.out.println("====================="+id);
//				
//				
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//			}
//		});
		//���ﳵ����Ʒȥ���
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				user_id = spPreferences.getString("user_id", "");
				if (user_id.equals("")) {
					Intent intentll = new Intent(getActivity(),UserLoginActivity.class);
					startActivity(intentll);
				} else {
						Intent intentll = new Intent(getActivity(),NewWare.class);
						startActivity(intentll);
				}
			}
		});
		
		btn_sittle_account.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				String total_cll = MyShopingCartllAdapter.total_c;
//				Intent intent = new Intent(getActivity(),MyOrderConfrimActivity.class);
//				intent.putExtra("total_cll", total_cll);
//				startActivity(intent);
//				if (list_cart_id.size() > 0) {
				
				loadgouwuche();
				
//				}else {
//					Toast.makeText(getActivity(),"�빴ѡҪ�µ�����Ʒ", 200).show();
//				}
				
//				Intent intent = new Intent(getActivity(),MyShopPingCarActivity.class);
//				startActivity(intent);
			}
		});
		
	}
	
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case 0:
				try {
					
				System.out.println("3================"+list_ll.size());
				
				
				
				adapter = new MyShopingCartllAdapter(list_ll, getActivity(), handler);
				list_shop_cart.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				list_shop_cart.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
				 if (list_ll.size() > 0) {
						btn_sittle_account.setText("ȥ����(" + list_ll.size() + ")");
					}else {
						btn_sittle_account.setText("ȥ����");
					}
				 
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 200:
				try {
				String total_c = MyShopingCartllAdapter.total_c;
				tv_endmoney.setText("��" + total_c);
//					double money_heji = MyShopingCartllAdapter.total_monney;
//					tv_endmoney.setText("��" + money_heji);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					final ArrayList<ShopCartData> carts = (ArrayList<ShopCartData>) msg.obj;
					shopping_id = MyShopingCartllAdapter.id;
					System.out.println("2222================"+shopping_id);
					if (shopping_id>0) {
						btn_sittle_account.setText("ȥ����(" + shopping_id + ")");
					}else {
						Toast.makeText(getActivity(),"�빴ѡҪ�µ�����Ʒ", 200).show();
					}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				
				break;
			case 1:
				try {
				total_cll = MyShopingCartllAdapter.total_c;
				tv_endmoney.setText("��" + total_cll);
//				money_heji = MyShopingCartllAdapter.total_monney;
//				tv_endmoney.setText("��" + money_heji);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 3:
				try {
//				String[] orderid = (String[]) msg.obj;
//				System.out.println("����ֵ11=================="+orderid);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 400:
				int position = (Integer) msg.obj;
				int orderid = msg.arg1;
				dialog(position, orderid);
				break;
			default:
				break;
			}
			super.dispatchMessage(msg);
		}
	};
	
	
	/**
	 * ��ȡ���ﳵ�б�����
	 */
	private void loadWeather() {
		list_ll = new ArrayList<ShopCartData>();
//		progress.CreateProgress();
		String user_id = spPreferences.getString("user_id", "");
		System.out.println("1==================" + user_id);
		if (!user_id.equals("")) {
		System.out.println("�����1=============="+id);
			
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_shopping_cart?pageSize=10&pageIndex=1&user_id="+user_id+""
				,new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0,String arg1) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0, arg1);
						try {
							JSONObject jsonObject = new JSONObject(arg1);
							System.out.println("1================"+arg1);
							JSONArray jsot = jsonObject.getJSONArray("data");
							if (jsot.length() > 0) {
							data = new ShopCartData();
							for (int i = 0; i < jsot.length(); i++) {
								JSONObject object = jsot.getJSONObject(i);
								dm = new ShopCartData();
								data.title = object.getString("title");
								data.market_price = object.getString("market_price");
								data.sell_price = object.getString("sell_price");
								data.id = object.getString("id");
								data.quantity = object.getInt("quantity");
								data.img_url = object.getString("img_url");
								
								dm.setTitle(object.getString("title"));
								dm.setMarket_price(object.getString("market_price"));
								dm.setSell_price(object.getString("sell_price"));
								dm.setId(object.getString("id"));
								dm.setImg_url(object.getString("img_url"));
								dm.setQuantity(object.getInt("quantity"));
								dm.setArticle_id(object.getString("article_id"));
								dm.setGoods_id(object.getString("goods_id"));
								
								String zhou = dm.getSell_price();
								System.out.println("21================"+zhou);
								
								list_ll.add(dm);
//								list_ll.add(data);
							}
							progress.CloseProgress();
							handler.sendEmptyMessage(0);
							adv_pager.setVisibility(View.GONE);
							adv_pager2.setVisibility(View.VISIBLE);
							ll_xianshi.setVisibility(View.VISIBLE);
//							Toast.makeText(getActivity(), "���ﳵ��Ʒ", 200).show();
							}else {
								progress.CloseProgress();
								adapter = new MyShopingCartllAdapter(list_ll, getActivity(), handler);
								list_shop_cart.setAdapter(adapter);
								adapter.notifyDataSetChanged();
								adv_pager.setVisibility(View.VISIBLE);
								adv_pager2.setVisibility(View.GONE);
								ll_xianshi.setVisibility(View.GONE);
//								Toast.makeText(getActivity(), "���ﳵ������Ʒ", 200).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, null);
		
		}else {
			progress.CloseProgress();
			adv_pager.setVisibility(View.VISIBLE);
//			Toast.makeText(getActivity(), "���ȵ�¼", 200).show();
		}
	}

	
	private void loadgouwuche() {
		try {
			progress.CreateProgress();
			list = new ArrayList<ShopCartBean>();
//			str1 = MyShopingCartllAdapter.str1;
//			str2 = MyShopingCartllAdapter.str2;
//			str3 = MyShopingCartllAdapter.str3;
			 for (int i = 0; i < list_ll.size(); i++) {
	    		 String cart_id = list_ll.get(i).getArticle_id();
	        	 String goods_id = list_ll.get(i).getGoods_id();
	        	 int quantity = list_ll.get(i).getQuantity();
	        	 
	        	 list_cart_id.add(cart_id);
	  			list_goods_id.add(goods_id);
	  			list_quantity.add(quantity);
			}
			
			//article_id ƴ��
	        	str1 = new StringBuffer();
		        for(String s:list_cart_id){
		        	str1.append(s+",");
		        }
		        str1.delete(str1.lastIndexOf(","),str1.length()); 
		        System.out.println("1ƴ��֮��---------------"+str1);
		        
		        //goods_id ƴ��
		        str2 = new StringBuffer();
		        for(String s:list_goods_id){
		        	str2.append(s+",");
		        }
		        str2.delete(str2.lastIndexOf(","),str2.length()); 
		        
		        System.out.println("2ƴ��֮��---------------"+str2);
		        
		      //quantity ƴ��
		        str3 = new StringBuffer();
		        for(int s:list_quantity){
		        	str3.append(s+",");
		        }
		        str3.delete(str3.lastIndexOf(","),str3.length()); 
		        
		        System.out.println("3ƴ��֮��---------------"+str3);
		        
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_buys?user_id="+user_id+"&user_name="+user_name+
					"&article_id="+str1+"&goods_id="+str2+"&quantity="+str3+"",new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0,String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								System.out.println("�����嵥================"+arg1);
								String info = jsonObject.getString("info");
//								 status1 = true;
								if (status.equals("y")) {
									progress.CloseProgress();
									JSONArray jsot = jsonObject.getJSONArray("data");
									bean = new ShopCartBean();
									for (int i = 0; i < jsot.length(); i++) {
									JSONObject obj = jsot.getJSONObject(i);
									bean.setId(obj.getString("id"));
									bean.id = obj.getString("id");
									String id = obj.getString("id");
									list.add(bean);
									list_id.add(id);
									}
									    str = new StringBuffer();
								        for(String s:list_id){
								        	str.append(s+",");
								        }
								        str.delete(str.lastIndexOf(","),str.length()); 
								        System.out.println("idƴ��֮��---------------"+str);
								        
								        
//									Toast.makeText(getActivity(), info, 200).show();
									Intent intent=new Intent(getActivity(), MyOrderConfrimActivity.class);
									intent.putExtra("total_cll",total_cll);
									startActivity(intent);
//									finish();
								}else {
									progress.CloseProgress();
									Toast.makeText(getActivity(), info, 200).show();
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
							super.onFailure(arg0, arg1);
						}
						

					}, getActivity());
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	

	protected void dialog(final int index, final int ID) {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("ȷ��ɾ�������Ʒ��");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				if (adapter != null) {
////					adapter.deleteData(null, index);
//				}
//				if (UserLoginActivity.id != null) {
					strUrl = RealmName.REALM_NAME_LL + "/cart_goods_delete?"
							+ "clear=0&user_id=" + 19
							+ "&cart_id=" + ID;
					AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(),getActivity());

					// ��Ʒ�����������͸ı� ˢ�¼���ҳ��
//					data = wareDao.findResult();
//					Message message = new Message();
//					message.what = 200;
//					message.obj = data;
//					handler.sendMessage(message);
					Toast.makeText(getActivity(), "ɾ���ɹ�", 200).show();
//					List<String> list_cart_id = MyShopingCartllAdapter.list_cart_id;
//					 List<String> list_goods_id = MyShopingCartllAdapter.list_goods_id;
//					 List<Integer> list_quantity = MyShopingCartllAdapter.list_quantity;
					 System.out.println("list_cart_id================"+list_cart_id.size());
					if (list_cart_id.size() > 0) {
						list_cart_id.clear();
						list_goods_id.clear();
						list_quantity.clear();
					}
					
					dialog.dismiss();
//					adapter.notifyDataSetChanged();
					loadWeather();
					
//				}else {
//					Intent intent = new Intent(MyShoppingCartActivity.this, UserLoginActivity.class);
//					startActivity(intent);
//				}
			}
		});
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}


}