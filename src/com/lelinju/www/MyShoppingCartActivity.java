package com.lelinju.www;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.MyPopupWindowMenu;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.hengyushop.airplane.adapter.MyAdapter;
import com.hengyushop.airplane.adapter.MyShopingCartllAdapter;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.entity.ShopCartBean;
import com.hengyushop.entity.ShopCartData;
import com.hengyushop.entity.UserRegisterData;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.lelinju.www.R;

public class MyShoppingCartActivity extends BaseActivity {
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
	private DialogProgress progress;
	private String strUrl;
	private String yth;
	private MyPopupWindowMenu popupWindowMenu;
	private EditText tv_amount_jf;
	private UserRegisterData registerData;
	private CheckBox in_jf,shopcart_item_check;
	ArrayList<ShopCartData> list_ll;
	static StringBuffer sb;
	public static String total_cll;
//	private static List list_id = new ArrayList();
	int shopping_id;
	private int ID;
	private int checkNum; // 记录选中的条目数量
	String id;
	private SharedPreferences spPreferences;
	String user_name,user_id;
	ArrayList<ShopCartBean> list;
	public static StringBuffer str,str1,str2,str3;
	private ShopCartBean bean;
	private static List<String> list_id = new ArrayList<String>(); 
//    public static Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_cart);
		popupWindowMenu = new MyPopupWindowMenu(this);
		progress = new DialogProgress(MyShoppingCartActivity.this);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		ininate();
		loadWeather();
		setTotalCost();
		
		// 全选按钮的回调接口
//		shopcart_item_check.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// 遍历list的长度，将MyAdapter中的map值全部设为true
//				Toast.makeText(MyShoppingCartActivity.this,"请勾选要下单的商品", 200).show();
//				System.out.println("值是多少11==============="+list_ll.size());
//				for (int i = 0; i < list_ll.size(); i++) {
//					MyShopingCartllAdapter.getIsSelected().put(i, true);
//				}
//				// 数量设为list的长度
//				checkNum = list_ll.size();
//				// 刷新listview和TextView的显示
//				dataChanged();
//			}
//		});
		
		btn_sittle_account.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

//				String total_cll = MyShopingCartllAdapter.total_c;
//				Intent intent = new Intent(MyShoppingCartActivity.this,MyOrderConfrimActivity.class);
//				intent.putExtra("total_cll", total_cll);
//				startActivity(intent);
//				if (shopping_id>0) {
////					Intent intent = new Intent(MyShoppingCartActivity.this,OrderConfrimActivity.class);
////					String rsu = tv_amount_jf.getText().toString();
////					intent.putExtra("jf",rsu.length() == 0 ? "0": rsu);
////					intent.putExtra("obj", carts);
////					startActivity(intent);
//				}else {
//					Toast.makeText(MyShoppingCartActivity.this,"请勾选要下单的商品", 200).show();
//				}
				
				loadgouwuche();
				
			}
		});
		
		
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
								        System.out.println("id拼接之后---------------"+str);
								        
								        
//									Toast.makeText(getActivity(), info, 200).show();
									Intent intent=new Intent(MyShoppingCartActivity.this, MyOrderConfrimActivity.class);
									intent.putExtra("total_cll",total_cll);
									startActivity(intent);
//									finish();
								}else {
									progress.CloseProgress();
									Toast.makeText(MyShoppingCartActivity.this, info, 200).show();
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
						

					}, MyShoppingCartActivity.this);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	}
	
	// 刷新listview和TextView的显示
		private void dataChanged() {
			// 通知listView刷新
			adapter.notifyDataSetChanged();
			// TextView显示最新的选中数目
			btn_sittle_account.setText("已选中" + checkNum + "项");
		};
	public void setTotalCost(){
//		tv_shanchu.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				ID = Integer.parseInt(list_ll.get(arg2).getId());
//				dialog(ID);
//			}
//		});
	}
	
	/**
	 * 初始化控件类别
	 */
	private void ininate( ) {
		list_none = (LinearLayout)findViewById(R.id.list_none);
		list_shops = (LinearLayout)findViewById(R.id.list_shops);
		in_jf = (CheckBox)findViewById(R.id.in_jf);
		shopcart_item_check = (CheckBox)findViewById(R.id.shopcart_item_check);
		btn_sittle_account = (Button) findViewById(R.id.btn_settle_accounts);
		list_shop_cart = (ListView)findViewById(R.id.list_shop_cart);
		tv_endnumber = (TextView)findViewById(R.id.tv_number);
		tv_shanchu = (TextView) findViewById(R.id.tv_shanchu);
		tv_endmarketprice = (TextView)  findViewById(R.id.tv_original_price);
		tv_preferential = (TextView)findViewById(R.id.tv_preferential);
		tv_endmoney = (TextView)findViewById(R.id.tv_amount_payable);
		tv_amount_jf = (EditText)findViewById(R.id.tv_amount_jf);
		jf = (TextView)findViewById(R.id.jf);
		list_shop_cart.setCacheColorHint(0);
		
//		 list_shop_cart.setOnItemLongClickListener(new OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//					int arg2, long arg3) {
//				// TODO Auto-generated method stub
//				Toast.makeText(MyShoppingCartActivity.this,"请勾选要下单的商品", 200).show();
////				ID = Integer.parseInt(list_ll.get(arg2).getId());
////				dialog(ID);
//				return false;
//			}
//			 
//		});
	}
	
	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void dispatchMessage(Message msg) {

			switch (msg.what) {
			case 0:
				try {
					
				System.out.println("3================"+list_ll.size());
				adapter = new MyShopingCartllAdapter(list_ll, MyShoppingCartActivity.this, handler);
				list_shop_cart.setAdapter(adapter);
//				madapter = new MyAdapter(list_ll, MyShoppingCartActivity.this);
//				list_shop_cart.setAdapter(madapter);
				list_shop_cart.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
				System.out.println("111111================"+list_id.size());
				 if (list_ll.size() != 0) {
						btn_sittle_account.setText("去结算(" + list_ll.size() + ")");
					}else {
						btn_sittle_account.setText("去结算");
					}
				 
				 
				// 绑定listView的监听器
//				list_shop_cart.setOnItemClickListener(new OnItemClickListener() {
//							@Override
//							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//									long arg3) {
//								try {
//									
//								// 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
//								ViewHolder holder = (ViewHolder) arg1.getTag();
//								// 改变CheckBox的状态
//								holder.cb.toggle();
//								// 将CheckBox的选中状况记录下来
//								MyShopingCartllAdapter.getIsSelected().put(arg2, holder.cb.isChecked());
//								// 调整选定条目
//								if (holder.cb.isChecked() == true) {
////									checkNum++;
//									String cart_id = list_ll.get(arg2).getId();
//									
//									System.out.println("1111================"+list_ll.size());
//								} else {
////									checkNum--;
//									System.out.println("2222================"+list_ll.size());
//								}
//								// 用TextView显示
////								tv_show.setText("已选中" + checkNum + "项");
//								} catch (Exception e) {
//									// TODO: handle exception
//									e.printStackTrace();
//								}
//							}
//						});
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 200:
				try {
				String total_c = MyShopingCartllAdapter.total_c;
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
			case 2:
				try {
					shopping_id = MyShopingCartllAdapter.id;
					System.out.println("2222================"+shopping_id);
//					if (id>0) {
						btn_sittle_account.setText("去结算(" + shopping_id + ")");
//					}else {
//						Toast.makeText(MyShoppingCartActivity.this,"请勾选要下单的商品", 200).show();
//					}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				
				break;
			case 1:
				try {
				String total_cll = MyShopingCartllAdapter.total_c;
				tv_endmoney.setText("￥" + total_cll);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			case 3:
				try {
//				String[] orderid = (String[]) msg.obj;
//				System.out.println("数组值11=================="+orderid);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
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
//		if (id!= null) {
			
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
								
								String zhou = dm.getSell_price();
								System.out.println("21================"+zhou);
								int geshu = object.getInt("sell_price");
								System.out.println("22================"+geshu);
								int sum = 0;
								sum +=geshu;
//								for (int j = 0; j < geshu; j++) {
//									sum +=geshu;
//								}
								System.out.println("总额================"+sum);
								list_ll.add(dm);
//								list_ll.add(data);
							}
							
							
							System.out.println("2================"+list_ll.size());
							String zhou = dm.getSell_price();
//							tv_endmoney.setText("￥" + zhou);
							handler.sendEmptyMessage(0);
							progress.CloseProgress();
							}else {
								progress.CloseProgress();
								adapter = new MyShopingCartllAdapter(list_ll, MyShoppingCartActivity.this, handler);
								list_shop_cart.setAdapter(adapter);
								adapter.notifyDataSetChanged();
								Toast.makeText(MyShoppingCartActivity.this, "购物车暂无数据", 200).show();
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}, null);
		}
		        
	}

	protected void dialog(final int ID) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("确认删除这个商品吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

//				final String str = RealmName.REALM_NAME
//						+ "/mi/receiveOrderInfo.ashx?act=DeleteOneUserCollectInfo&yth="
//						+ yth + "&ProductItemId=" + ID + "&key=" + key;
//				Log.v("data1", "删除:" + strUrl);
//				progress.CreateProgress();
//				new Thread() {
//					public void run() {
//						try {
//							httpToServer.getJsonString(str);
//							DOParse();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					};
//				}.start();
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
	protected void dialog(final int index, final int ID) {
		AlertDialog.Builder builder = new Builder(MyShoppingCartActivity.this);
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
					AsyncHttp.get(strUrl, new AsyncHttpResponseHandler(),MyShoppingCartActivity.this);

					// 商品数量或者类型改变 刷新计数页面
//					data = wareDao.findResult();
//					Message message = new Message();
//					message.what = 200;
//					message.obj = data;
//					handler.sendMessage(message);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (0 == popupWindowMenu.currentState && popupWindowMenu.isShowing()) {
			popupWindowMenu.dismiss(); // 对话框消失
			popupWindowMenu.currentState = 1; // 标记状态，已消失
		} else {
			popupWindowMenu.showAtLocation(findViewById(R.id.layout),
					Gravity.BOTTOM, 0, 0);
			popupWindowMenu.currentState = 0; // 标记状态，显示中
		}
		return false; // true--显示系统自带菜单；false--不显示。
	}

}
