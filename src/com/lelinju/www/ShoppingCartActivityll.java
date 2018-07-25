package com.lelinju.www;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.MyAdapter;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.entity.ShopCartBean;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.hengyushop.airplane.adapter.MyShopingCartllAdapter.ViewHolder;
import com.lelinju.www.R;

public class ShoppingCartActivityll extends Fragment {
	private ListView list_shop_cart;
	private Button btn_sittle_account;
	private TextView tv_endnumber, tv_endmarketprice, tv_preferential,
			tv_endmoney, jf,tv_shanchu;
	private LinearLayout list_shops, list_none;
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
	ArrayList<ShopCartData> list_ll;
	ArrayList<ShopCartBean> list;
	private static List<String> list_id = new ArrayList<String>();  
	int shopping_id;
	String id;
	private int ID;
	private int checkNum; // 记录选中的条目数量
//    public static Handler handler;
	private SharedPreferences spPreferences;
	private static String user_name,user_id;
	public static StringBuffer str,str1,str2,str3;
	boolean status1 = false;
//	public static  String total_c,total_c_jian,total_c_jia;
	public  StringBuffer sb;
	public static String total_cll;
	private static List<String> list_cart_id = new ArrayList<String>();  
	private static List<String> list_goods_id = new ArrayList<String>(); 
	private static List<Integer> list_quantity = new ArrayList<Integer>(); 
//	private static List<String> list_monney1= new ArrayList<String>(); 
	private static List<Double> list_monney = new ArrayList<Double>(); 
	private  HashMap<Integer, Boolean> isSelected;
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
		loadWeather();
//		setTotalCost();
		
		return layout;

	}
	
	// 刷新listview和TextView的显示
//		private void dataChanged() {
//			// 通知listView刷新
//			adapter.notifyDataSetChanged();
//			// TextView显示最新的选中数目
//			btn_sittle_account.setText("已选中" + checkNum + "项");
//		};
//	public void setTotalCost(){
//		tv_shanchu.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				ID = Integer.parseInt(list_ll.get(arg2).getId());
//				dialog(ID);
//			}
//		});
//	}
	
	/**
	 * 初始化控件类别
	 */
	private void ininate(View layout) {
		try {
			
		list_none = (LinearLayout)layout.findViewById(R.id.list_none);
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
		
//		 list_shop_cart.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				Toast.makeText(getActivity(),"请勾选要下单的商品", 200).show();
////				ID = Integer.parseInt(list_ll.get(arg2).getId());
////				dialog(ID);
//				return false;
//			}
//			 
//		});
		/**
		 * 去结算
		 */
		btn_sittle_account.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				System.out.println("1================"+MyShopingCartllAdapter.str1);
//				System.out.println("2================"+MyShopingCartllAdapter.str2);
//				System.out.println("3================"+MyShopingCartllAdapter.str3);
				
//				Intent intent=new Intent(getActivity(), MainActivity.class);
//				startActivity(intent);
//				if (list_cart_id.size() > 0) {
//				loadgouwuche();
//				}else {
//					Toast.makeText(getActivity(),"请勾选要下单的商品", 200).show();
//				}
			}
		});
		
		/**
		 * 去结算
		 */
		btn_sittle_account.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("1================"+MyShopingCartllAdapter.str1);
				System.out.println("2================"+MyShopingCartllAdapter.str2);
				System.out.println("3================"+MyShopingCartllAdapter.str3);
				if (list_cart_id.size() > 0) {
				loadgouwuche();
				}else {
					Toast.makeText(getActivity(),"请勾选要下单的商品", 200).show();
				}
			}
		});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void loadgouwuche() {
		try {
			progress.CreateProgress();
			list = new ArrayList<ShopCartBean>();
			str1 = MyShopingCartllAdapter.str1;
			str2 = MyShopingCartllAdapter.str2;
			str3 = MyShopingCartllAdapter.str3;
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/add_shopping_buys?user_id="+user_id+"&user_name="+user_name+
					"&article_id="+str1+"&goods_id="+str2+"&quantity="+str3+"",new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0,String arg1) {
							// TODO Auto-generated method stub
							super.onSuccess(arg0, arg1);
							try {
								JSONObject jsonObject = new JSONObject(arg1);
								String status = jsonObject.getString("status");
								System.out.println("购物清单================"+arg1);
								String info = jsonObject.getString("info");
								 status1 = true;
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
								        System.out.println("id拼接之后---------------"+str);
								        
									Toast.makeText(getActivity(), info, 200).show();
//									Intent intent=new Intent(getActivity(), MainActivity.class);
////									Intent intent=new Intent(getActivity(), MyOrderConfrimActivity.class);
//									intent.putExtra("total_cll",total_cll);
//									startActivity(intent);
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
							System.out.println("==========================访问接口失败！");
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
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		list_id.clear();
		if (list_id.size() == 0) {
//			list_cart_id.clear();
//			list_goods_id.clear();
//			list_quantity.clear();
//			MyShopingCartllAdapter.str1 = null;
//			MyShopingCartllAdapter.str2 = null;
//			MyShopingCartllAdapter.str3 = null;
		}
		System.out.println("11购物清单================"+list_id.size());
		System.out.println("22购物清单================"+list_cart_id.size());
		System.out.println("33购物清单================"+list_goods_id.size());
		System.out.println("44购物清单的个数================"+list_quantity.size());
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
//				 if (list_ll.size() > 0) {
//						btn_sittle_account.setText("去结算(" + list_ll.size() + ")");
//					}else {
//						btn_sittle_account.setText("去结算");
//					}
				 
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 200:
				try {
				String total_c = MyShopingCartllAdapter.total_c;
				System.out.println("total_c================"+MyShopingCartllAdapter.total_c);	
				tv_endmoney.setText("￥" + total_c);
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
			case 22:
				try {
//					List<String> list_cart_id =  (ArrayList<String>) msg.obj;  
					System.out.println("list_cart_id================"+list_cart_id);	
					if (list_cart_id.size() > 0) {
						btn_sittle_account.setText("去结算(" + list_cart_id.size() + ")");
					}else {
						btn_sittle_account.setText("去结算");
					}
					
					 System.out.println("几个列表的个数============="+list_monney.size());
					 
//					String total_c = MyShopingCartllAdapter.total_c;
					String total_c = (String) msg.obj;
					System.out.println("最后的总计total_c========3========"+total_c);	
					tv_endmoney.setText("￥" + total_c);
					
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				break;
			case 1:
				try {
//				total_cll = MyShopingCartllAdapter.total_c;
				String total_c = (String) msg.obj;
				tv_endmoney.setText("￥" + total_c);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 3:
				break;
			default:
				break;
			}
			super.dispatchMessage(msg);
		}
	};
	
	/**
	 * 获取购物车列表数据
	 */
	private void loadWeather() {
		
		list_ll = new ArrayList<ShopCartData>();
		progress.CreateProgress();
		String user_id = spPreferences.getString("user_id", "");
		System.out.println("1==================" + user_id);
		if (!user_id.equals("")) {
	
		System.out.println("结果呢1=============="+id);
			
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
//							if (!jsot.equals("")) {
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
							System.out.println("2================"+list_ll.size());
							handler.sendEmptyMessage(0);
							}else {
								progress.CloseProgress();
//								adapter = new MyShopingCartllAdapter(list_ll, getActivity(), handler);
//								list_shop_cart.setAdapter(adapter);
								adapter.notifyDataSetChanged();
								Toast.makeText(getActivity(), "购物车暂无数据", 200).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, null);
		
		}else {
			progress.CloseProgress();
			Toast.makeText(getActivity(), "请先登录", 200).show();
		}
	}


	protected void dialog(final int index, final int ID) {
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("确认删除这个商品吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
//				if (adapter != null) {
////					adapter.deleteData(null, index);
//				}
//				if (UserLoginActivity.id != null) {
					strUrl = RealmName.REALM_NAME_LL + "/cart_goods_delete?"
							+ "clear=0&user_id=" + user_id
							+ "&cart_id=" + ID;
					AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(),getActivity());

					// 商品数量或者类型改变 刷新计数页面
//					data = wareDao.findResult();
//					Message message = new Message();
//					message.what = 200;
//					message.obj = data;
//					handler.sendMessage(message);
					Toast.makeText(getActivity(), "删除成功", 200).show();
					dialog.dismiss();
					adapter.notifyDataSetChanged();
					loadWeather();
					
//				}else {
//					Intent intent = new Intent(MyShoppingCartActivity.this, UserLoginActivity.class);
//					startActivity(intent);
//				}
			}
		});
		
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}


	public static class MyShopingCartllAdapter extends BaseAdapter {
		private static HashMap<Integer, Boolean> isSelected;
		private ArrayList<ShopCartData> list;
		public static StringBuffer str,str1,str2,str3,str4;
		public static String total_c,total_c_jian,total_c_jia;
		// 用来导入布局
		private LayoutInflater inflater = null;
		private Context context;
		private ImageLoader loader;
		private WareDao wareDao;
		private ShopCartData cartData;
		private Handler handler;
		String quantity;
		boolean statuo = false;
		private double monney;
//		EditText et_number;
		int number;
		String quantity_ll;
		TextView btn_add,btn_reduce,et_number;
		// 用来控制CheckBox的选中状况
		private ArrayList<ShopCartData> list2;
		
		// 构造器
		public MyShopingCartllAdapter(ArrayList<ShopCartData> list, Context context
				,Handler handler) {
			this.context = context;
			this.handler = handler;
			this.list = list;
			inflater = LayoutInflater.from(context);
			isSelected = new HashMap<Integer, Boolean>();
			// 初始化数据
			initDate();
		}

		// 初始化isSelected的数据
		private void initDate() {
			for (int i = 0; i < list.size(); i++) {
				getIsSelected().put(i, false);
			}
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		public  class ViewHolder {
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

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			try {
			ViewHolder holder = null;
			if (convertView == null) {
					
				System.out.println("6================");
				holder = new ViewHolder();
				convertView = RelativeLayout.inflate(context,R.layout.listitem_shopping_cart, null);
//				convertView = inflater.inflate(R.layout.listviewitem, null);
				holder.img_ware = (ImageView) convertView.findViewById(R.id.img_ware);
				holder.tv_warename = (TextView) convertView.findViewById(R.id.tv_ware_name);
				holder.tv_color = (TextView) convertView.findViewById(R.id.tv_color);
				holder.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
				holder.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
				holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
				holder.btn_reduce = (ImageButton) convertView.findViewById(R.id.img_btn_reduce);
				holder.et_number = (EditText) convertView.findViewById(R.id.et_number);
				holder.btn_add = (ImageButton) convertView.findViewById(R.id.img_btn_add);
				holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
				holder.btn_order_cancle = (ImageButton) convertView.findViewById(R.id.cb_style);
				holder.cb = (CheckBox) convertView.findViewById(R.id.item_cb);
				// 关于数量
				holder.market_information_seps_add = (TextView) convertView.findViewById(R.id.market_information_seps_add);//增加
				holder.market_information_seps_del = (TextView) convertView.findViewById(R.id.market_information_seps_del);//减少
				holder.market_information_seps_num = (TextView) convertView.findViewById(R.id.market_information_seps_num);//个数
//				holder.market_information_seps_num.setText("1");
				convertView.setTag(holder);
				
			} else {
				// 取出holder
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tv_warename.setText(list.get(position).getTitle());
			holder.tv_color.setText("￥" + list.get(position).getSell_price());
			holder.tv_size.setText("￥" + list.get(position).getMarket_price());
			holder.et_number.setText(list.get(position).getQuantity()+ "");
			System.out.println("个数是============="+list.get(position).getQuantity());
			ImageLoader imageLoaderll=ImageLoader.getInstance();
			imageLoaderll.displayImage(RealmName.REALM_NAME_HTTP + list.get(position).getImg_url(),holder.img_ware);
			holder.tv_size.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
			
			
			/**
			 * 删除
			 */
//			holder.btn_order_cancle.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					int orderid = Integer.parseInt(list.get(position).getId());
//					Message message = new Message();
//					message.what = 400;
//					message.obj = position;
//					message.arg1 = orderid;
//					handler.sendMessage(message);
//
//				}
//			});
			/**
			 * 增加数量	
			 */
			holder.btn_add.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
//						EditText et_number = (EditText) findViewById(R.id.et_number);
//						int num = Integer.parseInt(et_number.getText().toString());
//						et_number.setText(String.valueOf(num + 1));
//						if (list_monney.size() == list.size()) {
//							notifyDataSetChanged();
//						}else if (list_monney.size() == 0) {
//							notifyDataSetChanged();
//						}else {
//				    monney = Double.parseDouble(list.get(position).getSell_price());
//						list_quantity.remove(quantity_ll);
						
					changeAdd(position);
					statuo = true;
					// 商品数量或者类型改变 刷新计数页面
//					cartData = wareDao.findResult();
					Message message2 = new Message();
					message2.what = 200;
					message2.obj = total_c;
					handler.sendMessage(message2);
//						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			});
			
			/**
			 * 减少数量	
			 */
			holder.btn_reduce.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					try {
						
//						int num = Integer.parseInt(et_number
//								.getText().toString());
//						if (num != 1) {
//							et_number.setText(String.valueOf(num - 1));
//						} else {
//							Toast.makeText(context, "不能再减了", 200).show();
//						}
						
					changeReduce(position);
					statuo = true;
					// 商品数量或者类型改变 刷新计数页面
//					cartData = wareDao.findResult();
					Message message2 = new Message();
					message2.what = 200;
					message2.obj = total_c;
					handler.sendMessage(message2);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				}
			});
			try {
			/**
			 * 第一次加载列表
			 */
			if (statuo == false) {
			     String cart_id = list.get(position).getArticle_id();
            	 String goods_id = list.get(position).getGoods_id();
            	 int quantity = list.get(position).getQuantity();
//				 quantity_ll = String.valueOf(quantity);
					list_cart_id.add(cart_id);
					list_goods_id.add(goods_id);
					list_quantity.add(quantity);
					
//					System.out.println("2222================"+list_cart_id.size());
					double monney = Double.parseDouble(list.get(position).getSell_price());
					list_monney.add((double) monney);
					System.out.println("金额值============="+list_monney.size());
					
	            	//article_id 拼接
	            	str1 = new StringBuffer();
			        for(String s:list_cart_id){
			        	str1.append(s+",");
			        }
			        str1.delete(str1.lastIndexOf(","),str1.length()); 
			        System.out.println("1拼接之后---------------"+str1);
			        
			        //goods_id 拼接
			        str2 = new StringBuffer();
			        for(String s:list_goods_id){
			        	str2.append(s+",");
			        }
			        str2.delete(str2.lastIndexOf(","),str2.length()); 
			        
			        System.out.println("2拼接之后---------------"+str2);
			        
			      //quantity 拼接
			        str3 = new StringBuffer();
			        for(int s:list_quantity){
			        	str3.append(s+",");
			        }
			        str3.delete(str3.lastIndexOf(","),str3.length()); 
			        
			        System.out.println("3拼接之后---------------"+str3);
			        
			        
			        str4 = new StringBuffer();
			        for(double s:list_monney){
			        	str4.append(s+",");
			        }
			        str4.delete(str4.lastIndexOf(","),str4.length()); 
			        
			        System.out.println("4拼接之后---------------"+str4);
			        
					double a= 0;
					for (int i = 0; i < list.size(); i++) {
						String price = list.get(i).sell_price;
						int number = list.get(i).getQuantity();
						BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*number);
						//保留2位小数
						double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						a += total_c_ll;
					}
					total_c =  Double.toString(a);
					System.out.println("合计============="+total_c);
				
				Message message = new Message();
				message.what = 1;
				message.obj = total_c;
				handler.sendMessage(message);
				
				}
//			else {
//				
//					
////				if (list_cart_id.size() ) {
//					
//				 int quantity = list.get(position).getQuantity();
//				String quantity_ll = String.valueOf(quantity);
//				list_quantity.add(quantity_ll);
//				
//				  //quantity 拼接
//		        str3 = new StringBuffer();
//		        for(String s:list_quantity){
//		        	str3.append(s+",");
//		        }
//		        str3.delete(str3.lastIndexOf(","),str3.length()); 
//		        
//		        System.out.println("3拼接之后---------------"+str3);
//		        System.out.println("数量个数============="+list_monney.size());
//				
//		        double monney = Double.parseDouble(list.get(position).getSell_price());
//				list_monney.add((double) monney);
//		        StringBuffer  str4 = new StringBuffer();
//		        for(double s:list_monney){
//		        	str4.append(s+",");
//		        }
//		        str4.delete(str4.lastIndexOf(","),str4.length()); 
//		        
//		        System.out.println("4拼接之后---------------"+str4);
//		        
//				 System.out.println("金额列表个数============="+list_monney.size());
//				 
//				 
//		        
				Double sum1 = 0d;
				int number = list.get(position).getQuantity();
				 System.out.println("数量个数============="+number);
				 
				 
//				zhou = list_monney.get(position) * list_quantity.get(position);
				for(Double d:list_monney)//价格数组
				{
					 sum1 += d;
					 System.out.println("list_monney============="+d);
					 
//					 for(int a:list_quantity)
//						{
//							BigDecimal   c   =   new   BigDecimal(d*a);
//							//保留2位小数
//							double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//						    sum1 += total_c_ll;
//						}
				}
				for(int a:list_quantity)//个数数组
				{
				    sum1 += a;
				    System.out.println("list_quantity============="+a);
				}
//				
				
//				total_c =  Double.toString(sum1);
//				System.out.println("价格是多少2+++++++++++++"+total_c);
//				
//				
//				Message message = new Message();
//				message.what = 1;
//				message.obj = total_c;
//				handler.sendMessage(message);
//				
////				total_c = Double.toString(a);
////				System.out.println("===总计======="+a);
//				
//				}
			
			
			 int quantity = list.get(position).getQuantity();
			 quantity_ll = String.valueOf(quantity);
			 
			 
			 
		        StringBuffer  str4 = new StringBuffer();
		        for(double s:list_monney){
		        	str4.append(s+",");
		        }
		        str4.delete(str4.lastIndexOf(","),str4.length()); 
		        
		        System.out.println("4拼接之后---------------"+str4);
		        
				System.out.println("金额列表个数============="+list_monney.size());
				
//				for (int i = 0; i < list_monney.size(); i++) {
//					Double sum1 = 0d;
//					for(Double d:list_monney)
//					{
//						
//					      sum1 += d;
//					}
//					total_c =  Double.toString(sum1);
//					
//				}
				 
			 
			
				 
//			double a= 0;
//			
//			for (int i = 0; i < list.size(); i++) {
//				String price = list.get(i).sell_price;
//				int number1 = list.get(i).getQuantity();
//				BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*number1);
//				//保留2位小数
//				double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//				a += total_c_ll;
//			}
//			total_c =  Double.toString(a);
//			System.out.println("合计============="+total_c);
//		
//		Message message = new Message();
//		message.what = 1;
//		message.obj = total_c;
//		handler.sendMessage(message);
		
		
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
//			}
//			System.out.println("状态==============="+isSelected.get(position));
		    // 监听checkBox并根据原来的状态来设置新的状态  
			holder.cb.setOnClickListener(new View.OnClickListener() {  
	  
	            public void onClick(View v) {  
//	            	mCheckAll.isChecked()
	            	System.out.println("状态==============="+isSelected.get(position));
	                if (isSelected.get(position)) {  
	                	
	                	try {
	                		System.out.println("状态返回true++++++++++++++++++++++");
		                    isSelected.put(position, false);  
		                    setIsSelected(isSelected);  
		                    String cart_id = list.get(position).getArticle_id();
		                	String goods_id = list.get(position).getGoods_id();
		                	int quantity = list.get(position).getQuantity();
//							String quantity_ll = String.valueOf(quantity);
							list_cart_id.add(cart_id);
							list_goods_id.add(goods_id);
							list_quantity.add(quantity);
//							System.out.println("2222================"+list_cart_id.size());
							double monney = Double.parseDouble(list.get(position).getSell_price());
							String monney1 = list.get(position).getSell_price();
							
							list_monney.add((double) monney);
							 System.out.println("金额列表个数============="+list_monney.size());
							Double sum1 = 0d;
							for(Double d:list_monney)
							{
							      sum1 += d;
							}
							total_c =  Double.toString(sum1);
//							System.out.println("价格是多少2+++++++++++++"+total_c);
							
//							for (int i = 0; i < list_monney.size(); i++) {
//							String price = list.get(i).sell_price;
//							number = list.get(i).getQuantity();
//							BigDecimal   c   =   new   BigDecimal(Double.parseDouble(total_c1)*number);
//							//保留2位小数
//							double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//							total_c = Double.toString(total_c_ll);
							System.out.println("增加之后的-----价格是多少============="+total_c);
//						}
							
							    Message message = new Message();
					    		message.what = 22;
					    		message.obj = total_c;
					    		handler.sendMessage(message);
//		                    id = list.size();
					    		
					    		statuo = true;
		                	} catch (Exception e) {
								// TODO: handle exception
		                		e.printStackTrace();
							}
	                } else {  
	                	try {
	                	System.out.println("状态返回fasle---------------------------");
	                    isSelected.put(position, true);  
	                    setIsSelected(isSelected);  
						String cart_id = list.get(position).getArticle_id();
						String goods_id = list.get(position).getGoods_id();
						int quantity = list.get(position).getQuantity();
						int quantity2 = list.get(position).getQuantity();
						String quantity_ll = String.valueOf(quantity);
						list_cart_id.remove(cart_id);
						list_goods_id.remove(goods_id);
						list_quantity.remove(quantity_ll);
						
//						list2.add(list.get(position).getQuantity());
//						System.out.println("1111================"+list_cart_id.size());
						double monney = Double.parseDouble(list.get(position).getSell_price());
						list_monney.remove((double) monney);
						 System.out.println("00============="+list_monney.size());
//	                    id = list_cart_id.size();
						
							Double sum1 = 0d;
							for(Double d:list_monney)
							{
							      sum1 += d;
							}
							total_c =  Double.toString(sum1);
							System.out.println("价格是多少-----------------//"+total_c);
//							
						    Message message = new Message();
				    		message.what = 22;
				    		message.obj = total_c;
				    		handler.sendMessage(message);
				    		statuo = true;
	                	} catch (Exception e) {
							// TODO: handle exception
	                		e.printStackTrace();
						}
	                }  
	                
	            	try {
	            	if (list_cart_id.size() > 0) {
					
	            	//article_id 拼接
	            	str1 = new StringBuffer();
			        for(String s:list_cart_id){
			        	str1.append(s+",");
			        }
			        str1.delete(str1.lastIndexOf(","),str1.length()); 
			        System.out.println("1拼接之后---------------"+str1);
			        
			        //goods_id 拼接
			        str2 = new StringBuffer();
			        for(String s:list_goods_id){
			        	str2.append(s+",");
			        }
			        str2.delete(str2.lastIndexOf(","),str2.length()); 
			        
			        System.out.println("2拼接之后---------------"+str2);
			        
			      //quantity 拼接
			        str3 = new StringBuffer();
			        for(int s:list_quantity){
			        	str3.append(s+",");
			        }
			        str3.delete(str3.lastIndexOf(","),str3.length()); 
			        
			        System.out.println("3拼接之后---------------"+str3);
			        
			        str4 = new StringBuffer();
			        for(double s:list_monney){
			        	str4.append(s+",");
			        }
			        str4.delete(str4.lastIndexOf(","),str4.length()); 
			        
			        System.out.println("4拼接之后---------------"+str4);
			        
	            	}
	            	
	            	
					
	            	} catch (Exception e) {
						// TODO: handle exception
	            		e.printStackTrace();
					}
	            	
	            	
	            }  
	        });
	            // 根据isSelected来设置checkbox的选中状况
//	     		holder.cb.setChecked(getIsSelected().get(position));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			return convertView;
		}

		public  HashMap<Integer, Boolean> getIsSelected() {
			return isSelected;
		}

		public  void setIsSelected(HashMap<Integer, Boolean> isSelected) {
			MyShopingCartllAdapter.isSelected = isSelected;
		}
		
//		public void deleteData(int index) {
//			String orderid = list.get(index).orderid + "";
//			wareDao.deleteByOrderid(orderid);
//			wareDao.findShopCart();
//			list.remove(index);
//			notifyDataSetChanged();
//		}

		private void changeAdd(int index) {
			try {
				
			list.get(index).setQuantity((list.get(index).getQuantity()) + 1);
			
			list_quantity.remove(quantity_ll);
			
//			ImageButton market_information_seps_num = (TextView) layout.findViewById(R.id.market_information_seps_num);
//			int num = Integer.parseInt(market_information_seps_num.getText().toString());
//			market_information_seps_num.setText(String.valueOf(num + 1));
			
//			for (int i = 0; i < list_monney.size(); i++) {
//				String price = list.get(i).sell_price;
//				number = list.get(i).getQuantity();
//				BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*number);
//				//保留2位小数
//				double   total_c_ll   =   c.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
//				total_c = Double.toString(total_c_ll);
//				System.out.println("增加之后的-----价格是多少============="+total_c);
//			}
			
//			double monney = Double.parseDouble(list.get(position).getSell_price());
//			list_monney.add((double) monney);
//			 System.out.println("00============="+list_monney.size());
			
//			Double sum1 = 0d;
//			for(Double d:list_monney)
//			{
//			      sum1 += d;
//			}
//			total_c =  Double.toString(sum1);
//			System.out.println("价格是多少2+++++++++++++"+total_c);
			
			String price = list.get(index).sell_price;
			int number = list.get(index).getQuantity();
			BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*number);
			//保留2位小数
			double   total_c_ll   =   c.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
			total_c = Double.toString(total_c_ll);
			System.out.println("减少之后的-----价格是多少1============="+total_c);
			
			
			String cart_id = list.get(index).getId();
			System.out.println("cart_id=============="+cart_id);
			AsyncHttp.get(RealmName.REALM_NAME_LL+ "/cart_goods_update?cart_id="+cart_id+"&user_id="+user_id+"&quantity="+number+"",new AsyncHttpResponseHandler() {
				@Override
				public void onSuccess(int arg0, String arg1) {
					// TODO Auto-generated method stub
					System.out.println("==========================增加数量成功！"+arg1);
					super.onSuccess(arg0, arg1);
				}
				
			}, context);
			statuo = true;
			notifyDataSetChanged();
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

		private void changeReduce(int index) {
			try {
			if (list.get(index).getQuantity() != 1) {
				list.get(index).setQuantity((list.get(index).getQuantity()) - 1);
				
				list_quantity.remove(quantity_ll);

				String price = list.get(index).sell_price;
				int number = list.get(index).getQuantity();
				BigDecimal   c   =   new   BigDecimal(Double.parseDouble(price)*number);
				//保留2位小数
				double   total_c_ll   =   c.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();
				total_c = Double.toString(total_c_ll);
				System.out.println("减少之后的-----价格是多少1============="+total_c);
				
				
				String cart_id = list.get(index).getId();
				AsyncHttp.get(RealmName.REALM_NAME_LL+ "/cart_goods_update?cart_id="+cart_id+"&user_id="+user_id+"&quantity="+number+"",new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						// TODO Auto-generated method stub
//						System.out.println("==========================2访问接口成功！"+arg1);
						super.onSuccess(arg0, arg1);
					}
					
				}, context);
				
				notifyDataSetChanged();
			} else {
				Toast.makeText(context, "不能再减了", 200).show();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		}


	}
}
