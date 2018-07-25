package com.lelinju.www;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.ui.ArrayWheelAdapterll;
import com.android.hengyu.ui.WheelViewll;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.example.downloadandnotificationbar.UpdateApkThread;
import com.example.taobaohead.headview.RoundImageView;
import com.example.uploadpicdemo.Utils;
import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.hengyushop.dao.WareDao;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.home.GenderFangShiActivity;
import com.hengyushop.demo.my.TishiNicknameActivity;
import com.hengyushop.entity.UserRegisterllData;
import com.hengyushop.entity.UserSenJiBean;
import com.lglottery.www.widget.MyAlertDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class PersonCenterActivity extends BaseActivity implements OnClickListener{
	private WareDao wareDao;
	private String yth, ye, jf,phone,yue,djf,bjf;
	private ImageView v1;
	private TextView v2, v3, v4,v7,v8,v9,v10,tv_nicheng,tv_nick_name;
	private RelativeLayout v6, mm,ll_update,rl_nichen;
	private ImageLoader mImageLoader;
	RoundImageView networkImage;
	UserSenJiBean bean;
	private ImageView iv_personal_icon,iv_personal_icon1;
	private TextView tv_name,tv_shenfenzheng,tv_jxdizhi,tv_xqdizhi,tv_banbenhao,tv_city;
	private String strUr2 = RealmName.REALM_NAME_LL + "/get_apk_version?browser=android";
	private String URL;
	private Activity context; // ������
	private String content;
	private LinearLayout ll_gender,ll_diqu;
	String dizhi = "ѡ�����";
	private String cityTxt,cityTxt1,cityTxt2,cityTxt3;
	String login_sign,amount,sex,nick_name,mobile;
	String user_name, user_id,nichen,order_no;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	UserRegisterllData data; 
	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;
	public static String path,time,province,city,area,lng,lat,imagePath,tupian;
	public AQuery mAq;
	public static boolean zhuangtai = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_center_layout);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		progress = new DialogProgress(PersonCenterActivity.this);
		mAq = new AQuery(this);
		init();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		userloginqm();
	}
	
	private void init() {
		try {
			
		networkImage = (RoundImageView) findViewById(R.id.roundImage_network);
//		v1 = (ImageView) findViewById(R.id.v1);
		v2 = (TextView) findViewById(R.id.v2);
		tv_city = (TextView) findViewById(R.id.tv_city);
		tv_nicheng = (TextView) findViewById(R.id.tv_nicheng);
		tv_nick_name = (TextView) findViewById(R.id.edt_xingbie);
		v7 = (TextView) findViewById(R.id.v7);
//		v8 = (TextView) findViewById(R.id.v8);
//		v9 = (TextView) findViewById(R.id.v9);
//		v10 = (TextView) findViewById(R.id.v10);
		mm = (RelativeLayout) findViewById(R.id.mm);
		iv_personal_icon = (ImageView) findViewById(R.id.iv_personal_icon);
		iv_personal_icon1 = (ImageView) findViewById(R.id.iv_personal_icon1);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_banbenhao = (TextView) findViewById(R.id.tv_banbenhao);
		tv_shenfenzheng = (TextView) findViewById(R.id.tv_shenfenzheng);
		tv_jxdizhi = (TextView) findViewById(R.id.tv_jxdizhi);
		tv_xqdizhi = (TextView) findViewById(R.id.tv_xqdizhi);
		ll_update = (RelativeLayout) findViewById(R.id.ll_update);
		rl_nichen = (RelativeLayout) findViewById(R.id.rl_nichen);
		ll_gender = (LinearLayout) findViewById(R.id.ll_gender);
		ll_diqu = (LinearLayout) findViewById(R.id.ll_diqu);
		ll_update.setOnClickListener(this);
		rl_nichen.setOnClickListener(this);
		ll_gender.setOnClickListener(this);
		ll_diqu.setOnClickListener(this);
		networkImage.setOnClickListener(this);
		mm.setOnClickListener(this);
		String version = getAppVersionName(PersonCenterActivity.this);
		System.out.println("c_version=============="+version);
		tv_banbenhao.setText(version);
		tv_banbenhao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		v6 = (RelativeLayout) findViewById(R.id.v6);
		v6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
//				Intent intent = new Intent(PersonCenterActivity.this,AddressManagerActivity.class);
//				startActivity(intent);
				Intent intent = new Intent(PersonCenterActivity.this,AddressManagerGlActivity.class);
				intent.putExtra("order_confrim", "order_confrim");// ��ʾ
				startActivity(intent);
			}
		});
//		mm.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//			/*	Intent intent = new Intent(PersonCenterActivity.this,
//						ModPassActivity.class);
//				startActivity(intent);*/
//				initPopupWindow();
//				showPopupWindow(mm);
//			}
//		});
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * ��ȡ�û�����
	 */
	private void userloginqm(){
		try {
			
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
			String user_name = spPreferences.getString("user", "");
			
			String strUrlone = RealmName.REALM_NAME_LL + "/get_user_model?username="+user_name+"";
			System.out.println("======11============="+strUrlone);
			AsyncHttp.get(strUrlone, new AsyncHttpResponseHandler() {
				public void onSuccess(int arg0, String arg1) {
					try {
						JSONObject object = new JSONObject(arg1);
						String status = object.getString("status");
						System.out.println("======arg1============="+arg1);
						if (status.equals("y")) {
						JSONObject obj = object.getJSONObject("data");
						data = new UserRegisterllData();
						data.user_name = obj.getString("user_name");
						data.user_code = obj.getString("user_code");
						data.agency_id = obj.getInt("agency_id");
						data.amount = obj.getString("amount");
						data.pension = obj.getString("pension");
						data.packet = obj.getString("packet");
						data.point = obj.getString("point");
						data.group_id = obj.getString("group_id");
						data.login_sign = obj.getString("login_sign");
						login_sign = data.login_sign;
						data.agency_name = obj.getString("agency_name");
						data.group_name = obj.getString("group_name");
						data.mobile = obj.getString("mobile");
						data.exp = obj.getString("exp");
						data.real_name = obj.getString("real_name");
						data.avatar = obj.getString("avatar");
						sex = obj.getString("sex");
						nick_name = obj.getString("nick_name");
						data.province = obj.getString("province");
						data.city = obj.getString("city");
						data.area = obj.getString("area");
						try {
						tv_nicheng.setText(nick_name);
						tv_nick_name.setText(sex);
						v2.setText(data.user_code);//�ۿ�
//						v3.setText(data.amount);//�˻����
//						v4.setText(data.point);//�۱�
						v7.setText(data.mobile);//�ֻ���
//						v8.setText(data.pension);//���Ͻ�
//						v9.setText(data.packet);//���
//						v10.setText(data.exp);
						
						Editor editor = spPreferences.edit();
						editor.putString("avatar", data.avatar);
						editor.commit();
						
						tv_city.setText(data.province+" "+data.city+" "+data.area);
						
						System.out.println("data.avatar--------------------------"+RealmName.REALM_NAME_HTTP +data.avatar);
						mImageLoader = initImageLoader(PersonCenterActivity.this, mImageLoader, "test");
						if (!data.avatar.equals("")){
							mImageLoader.displayImage(RealmName.REALM_NAME_HTTP +data.avatar,networkImage);
						}
						
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						
						String company = obj.getString("company");
						JSONObject objt = new JSONObject(company);
							bean = new UserSenJiBean();
							bean.contact = objt.getString("contact");
							bean.idcard = objt.getString("idcard");
							bean.idcard_a = objt.getString("idcard_a");
							bean.idcard_b = objt.getString("idcard_b");
							bean.address = objt.getString("address");
							bean.province = objt.getString("province");
							bean.city = objt.getString("city");
							bean.area = objt.getString("area");
							bean.recommend_name = objt.getString("recommend_name");
							
						      ImageLoader imageLoader=ImageLoader.getInstance();
						        imageLoader.displayImage(RealmName.REALM_NAME_HTTP+bean.idcard_a,iv_personal_icon);
						        imageLoader.displayImage(RealmName.REALM_NAME_HTTP+bean.idcard_b,iv_personal_icon1);
						        imageLoader.clearMemoryCache();
//						        mAq.id(iv_personal_icon).image(RealmName.REALM_NAME_FTP+bean.idcard_a);
//						        mAq.id(iv_personal_icon1).image(RealmName.REALM_NAME_FTP+bean.idcard_b);
						        
						    	tv_name = (TextView) findViewById(R.id.tv_name);
								tv_shenfenzheng = (TextView) findViewById(R.id.tv_shenfenzheng);
								tv_jxdizhi = (TextView) findViewById(R.id.tv_jxdizhi);
								tv_xqdizhi = (TextView) findViewById(R.id.tv_xqdizhi);
								
								tv_name.setText(bean.contact);
								tv_shenfenzheng.setText(bean.idcard);
								String dizhi = bean.province +" "+ bean.city +" "+ bean.area;
								
								tv_jxdizhi.setText(dizhi);
								tv_xqdizhi.setText(bean.address);
						}else {
							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				};
			}, PersonCenterActivity.this);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	Handler handler = new Handler() {
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				dialog();
				break;
			case 1:
				Toast.makeText(PersonCenterActivity.this, "��ǰ�汾�����°汾", 200).show();
				break;
			case 2:
				break;
			default:
				break;
			}
		};
	};
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ll_update:
//			if (zhuangtai == false) {
			new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
						/**
						 * �汾2
						 */
						AsyncHttp.get(strUr2, new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int arg0, String arg1) {
								
								super.onSuccess(arg0, arg1);
								System.out.println("��ҳ�汾=============="+arg1);
								try{
								JSONObject jsonObject = new JSONObject(arg1);
								JSONObject jsob = jsonObject.getJSONObject("data");
								String file_version = jsob.getString("file_version");
								String file_path = jsob.getString("file_path");
								URL = RealmName.REALM_NAME_HTTP + file_path;
								System.out.println("��ҳ�汾URL=============="+context);
								String version = getAppVersionName(PersonCenterActivity.this);
								String c_version = getAppVersionName(PersonCenterActivity.this).trim().replaceAll("\\.", "");
								float server_version = Float.parseFloat(file_version.replaceAll("\\.", ""));//������
								float client_version = Float.parseFloat(c_version);//��ǰ
								content = "�����°汾�ˣ�������"+file_version+"�Ƿ��滻��ǰ�汾"+version;
								System.out.println("content=============="+content);
								System.out.println("������:"+ server_version+"/��ǰ:" + client_version);
								if (server_version > client_version) {
									Message message = new Message();
									message.what = 0;
									handler.sendMessage(message);
								} else if (server_version == client_version) {
									Message message = new Message();
									message.what = 1;
									handler.sendMessage(message);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
							}
						}, PersonCenterActivity.this);
				}
			}.start();
//			}
			break;
		case R.id.rl_nichen:
			Intent intent = new Intent(PersonCenterActivity.this,TishiNicknameActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_gender:
			Intent intent1 = new Intent(PersonCenterActivity.this,GenderFangShiActivity.class);
			intent1.putExtra("type", "1");
			startActivity(intent1);
			break;
		case R.id.mm:
			Intent intent2 = new Intent(PersonCenterActivity.this,GenderFangShiActivity.class);
			intent2.putExtra("type", "2");
			startActivity(intent2);
			break;
		case R.id.ll_diqu:
			View view = dialogm();
			final MyAlertDialog dialog1 = new MyAlertDialog(
					PersonCenterActivity.this).builder()
					.setTitle(dizhi.toString()).setView(view)
					.setNegativeButton("ȡ��", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
			dialog1.setPositiveButton("ȷ��", new OnClickListener() {
				@Override
				public void onClick(View v) {
					loadusersex();
				}
			});
			dialog1.show();
			break;
		case R.id.roundImage_network:
			showChoosePicDialog();
			break;
		default:
			break;
		}
	}
	
	/**
	 * ��ʾ�޸�ͷ��ĶԻ���
	 */
	protected void showChoosePicDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("����ͷ��");
		String[] items = { "ѡ�񱾵���Ƭ", "����" };
		builder.setNegativeButton("ȡ��", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case CHOOSE_PICTURE: // ѡ�񱾵���Ƭ
					Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
					System.out.println("������Ƭ-----------------"+openAlbumIntent);
					openAlbumIntent.setType("image/*");
					startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
					break;
				case TAKE_PICTURE: // ����
					Intent openCameraIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
					System.out.println("����================"+tempUri);
					// ָ����Ƭ����·����SD������image.jpgΪһ����ʱ�ļ���ÿ�����պ����ͼƬ���ᱻ�滻
					openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
					startActivityForResult(openCameraIntent, TAKE_PICTURE);
					
					break;
				}
			}
			
		});
		builder.create().show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // ����������ǿ����õ�
			switch (requestCode) {
			//����
			case TAKE_PICTURE:
				startPhotoZoom(tempUri); // ��ʼ��ͼƬ���вü�����
				break;
			//�ϴ�ͼƬ	
			case CHOOSE_PICTURE:
				startPhotoZoom(data.getData()); // ��ʼ��ͼƬ���вü�����
				break;
			case CROP_SMALL_PICTURE:
				if (data != null) {
					setImageToView(data); // �øղ�ѡ��ü��õ���ͼƬ��ʾ�ڽ�����
				}
				break;
			}
		}
	}

	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
//		System.out.println("�ü�ͼƬ����ʵ��================"+tempUri);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}
	
	public void saveBitmapFile(Bitmap bitmap){
        File file=new File("/mnt/sdcard/pic/01.jpg");//��Ҫ����ͼƬ��·��
        try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                
                bos.flush();
                bos.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
}
	
	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param
	 * 
	 * @param picdata
	 */
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		Bitmap photo = extras.getParcelable("data");
		if (extras != null) {
//			photo = Utils.toRoundBitmap(photo, tempUri); // ���ʱ���ͼƬ�Ѿ��������Բ�ε���
			System.out.println("ͼƬ��ֵ1================="+photo);
			System.out.println("ͼƬ��ֵ2================="+tempUri);
			networkImage.setImageBitmap(photo);
			try {
				imagePath = Utils.savePhoto(photo, Environment.getExternalStorageDirectory().getAbsolutePath(), 
						String.valueOf(System.currentTimeMillis()));
				System.out.println("imagePath======================="+imagePath);
				
				new Thread() {
					public void run() {
						try {
							FTPClient client = new FTPClient();
							client.connect("183.62.138.31", 1021);
							client.login("le", "le1230.");
							SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");  
			                time = f.format(new Date());  
			                yth = MainFragmentContent4.yth;
			                String remotePathTmp = "phone/" + "" + yth +"";//·��
							System.out.println("========================"+remotePathTmp);
							
							try {
								client.createDirectory(remotePathTmp);//�ͻ��˴���Ŀ¼
							} catch (Exception e) {
					                e.printStackTrace();
							} finally {
								client.changeDirectory(remotePathTmp);
								
								File file = new File(imagePath);
								FileInputStream fis = new FileInputStream(file);
								try {
									client.upload(time + ".jpg", fis, 0, 0, null);
								} catch (FTPDataTransferException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (FTPAbortedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								fis.close();
								client.logout();//exit
							}
							
								tupian = "/upload/phone/" + yth + "/"+ time + ".jpg";
							System.out.println("tupian1--------------------------"+tupian);

						} catch (IllegalStateException e) {
							e.printStackTrace();//�Ƿ�״̬�쳣
						}
						catch (FTPIllegalReplyException e) {
							e.printStackTrace();//�Ƿ��ظ��쳣
						} catch (FTPException e) {
							e.printStackTrace();//�쳣
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						gettouxiang(); 
					};
					
				}.start();
				
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		}
		
	private void gettouxiang() {
		// TODO Auto-generated method stub
		String imgUrl = "/upload/phone/" + yth + "/"+ time + ".jpg";
		System.out.println("imgUrl--------------------------"+imgUrl);
		
		String strUrl = RealmName.REALM_NAME_LL
				+ "/user_avatar_save?user_name="+user_name+"&user_id="+user_id+"&user_avatar="+imgUrl+"&sign="+login_sign+"";
		AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
			public void onSuccess(int arg0, String arg1) {
				try {
					System.out.println("arg1--------------------------"+arg1);
					JSONObject object = new JSONObject(arg1);
					String status = object.getString("status");
					String info = object.getString("info");
//					onResume();
					if (status.equals("y")) {
//						Toast.makeText(context, info, 200).show();
						Toast.makeText(context, "�ϴ��ɹ�", 200).show();
						System.out.println("�ϴ��ɹ�--------------------------");
					}else{
//						Toast.makeText(context, info, 200).show();
						Toast.makeText(context, "�ϴ�ʧ��", 200).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		},PersonCenterActivity.this);
	}
	
	/**
	 * ѡ�����
	 * @return
	 */
	private View dialogm() {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.wheelcity_cities_layout, null);
		final WheelView country = (WheelView) contentView
				.findViewById(R.id.wheelcity_country);
		country.setVisibleItems(3);
		country.setViewAdapter(new CountryAdapter(this));

		final String cities[][] = AddressData.CITIES;
		final String ccities[][][] = AddressData.COUNTIES;
		final WheelView city = (WheelView) contentView
				.findViewById(R.id.wheelcity_city);
		city.setVisibleItems(0);

		// ����ѡ��
		final WheelView ccity = (WheelView) contentView
				.findViewById(R.id.wheelcity_ccity);
		ccity.setVisibleItems(0);// ���޳���

		country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateCities(city, cities, newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ "��"
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ "��"
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
				cityTxt1 = AddressData.PROVINCES[country.getCurrentItem()];
			}
		});

		city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updatecCities(ccity, ccities, country.getCurrentItem(),
						newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ "��"
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ "��"
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
				cityTxt2 = AddressData.CITIES[country.getCurrentItem()][city
				                        								.getCurrentItem()];
			}
		});

		ccity.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ "��"
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ "��"
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
				cityTxt3 = AddressData.COUNTIES[country.getCurrentItem()][city
				                          								.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		country.setCurrentItem(1);// ���ñ���
		city.setCurrentItem(1);
		ccity.setCurrentItem(1);
		return contentView;
	}

	/**
	 * Updates the city wheel
	 */
	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	/**
	 * Updates the ccity wheel
	 */
	private void updatecCities(WheelView city, String ccities[][][], int index,
			int index2) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				ccities[index][index2]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = AddressData.PROVINCES;

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
			setItemTextResource(R.id.wheelcity_country_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}
	
	
	// ��ȡ��ǰ����İ汾��Ϣ
		public static String getAppVersionName(Context context) {
			String versionName = "";
			try {
				PackageManager pm = context.getPackageManager();
				PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
				versionName = pi.versionName;
//				versioncode = pi.versionCode;
				if (versionName == null || versionName.length() <= 0) {
					return "";
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("VersionInfo", "Exception", e);
			}
			return versionName;
		}
	
	/**
	 * ��ʼ��ͼƬ��������ͼƬ�����ַ<i>("/Android/data/[app_package_name]/cache/dirName")</i>
	 */
	public ImageLoader initImageLoader(Context context,
			ImageLoader imageLoader, String dirName) {
		imageLoader = ImageLoader.getInstance();
		if (imageLoader.isInited()) {
			// ���³�ʼ��ImageLoaderʱ,��Ҫ�ͷ���Դ.
			imageLoader.destroy();
		}
		imageLoader.init(initImageLoaderConfig(context, dirName));
		return imageLoader;
	}

	/**
	 * ����ͼƬ������
	 * 
	 * @param dirName
	 *            �ļ���
	 */
	private ImageLoaderConfiguration initImageLoaderConfig(
			Context context, String dirName) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(3).memoryCacheSize(getMemoryCacheSize(context))
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.discCache(new UnlimitedDiscCache(new File(dirName)))
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		return config;
	}

	private int getMemoryCacheSize(Context context) {
		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024; // 1/8 of app memory
															// limit
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}
		return memoryCacheSize;
	}
	
	private LayoutInflater mLayoutInflater;
	private View popView;
	private PopupWindow mPopupWindow;
	private WheelViewll bank_item;
	
	private void initPopupWindow() {
		mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		popView = mLayoutInflater.inflate(R.layout.chose_payment, null);
		mPopupWindow = new PopupWindow(popView, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		// mPopupWindow.setBackgroundDrawable(new
		// BitmapDrawable());//��������background������ʧ
		mPopupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.grey));
		mPopupWindow.setOutsideTouchable(true);
		// �Զ��嶯��
		// mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		// ʹ��ϵͳ����
		mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
		mPopupWindow.update();
		mPopupWindow.setTouchable(true);
		mPopupWindow.setFocusable(true);
		bank_item = (WheelViewll) popView.findViewById(R.id.bank_item);
		Button cancle = (Button) popView.findViewById(R.id.cancle);
		Button sure = (Button) popView.findViewById(R.id.sure);
		String[] name = new String[2];
		name[0] = "��¼����";
		name[1] = "֧������";
		ArrayWheelAdapterll<String> bankAdapter = new ArrayWheelAdapterll<String>(
				name);
		bank_item.setAdapter(bankAdapter);

		cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				dissPop();
			}
		});
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				 
				int index = bank_item.getCurrentItem();
				Intent intent = new Intent(PersonCenterActivity.this,ModPassActivity.class);
				intent.putExtra("tag", index);
				startActivity(intent);
				
				dissPop();
			}
		});
	}

	private void showPopupWindow(View view) {
		if (!mPopupWindow.isShowing()) {
			// mPopupWindow.showAsDropDown(view,0,0);
			// ��һ������ָ��PopupWindow��ê��view�����������ĸ�view�ϡ�
			// �ڶ�������ָ����ʼ��Ϊparent�����½ǣ�����������������parent�����½�Ϊԭ�㣬�����ϸ�ƫ��10���ء�
			// int[] location = new int[2];
			// view.getLocationOnScreen(location);
			mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
		}
	}

	private void dissPop() {
		if (mPopupWindow != null && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
		}
	}
	
	// ����汾����
		private void dialog() {

			AlertDialog.Builder builder = new Builder(PersonCenterActivity.this);
			builder.setMessage(content);
			System.out.println("content=============="+content);
//			builder.setMessage("��鵽���°汾���Ƿ�Ҫ���£�");
			builder.setTitle("��ʾ:�°汾");
			builder.setPositiveButton("����", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					System.out.println("MainFragmentActivity.zhuangtai------------------"+MainFragmentActivity.zhuangtai);
					System.out.println("UserLoginActivity.zhuangtai------------------"+UserLoginActivity.zhuangtai);
					if (MainFragmentActivity.zhuangtai == true) {
						Toast.makeText(PersonCenterActivity.this, "��������...", 200).show();
						dialog.dismiss();
					}else if (UserLoginActivity.zhuangtai == true) {
						Toast.makeText(PersonCenterActivity.this, "��������...", 200).show();
						dialog.dismiss();
					}else if (PersonCenterActivity.zhuangtai == true) {
						Toast.makeText(PersonCenterActivity.this, "��������...", 200).show();
						dialog.dismiss();
					}else {
						final String filePath = Environment.getExternalStorageDirectory() + "/ss";
						new UpdateApkThread("http://mobile.lelinju.shop/upload/201709/23/201709231119249507.APK", filePath, "LeLinJu.apk",PersonCenterActivity.this).start();
						downLoadApk();
					}
				}
			});
			builder.setNegativeButton("�Ժ���˵",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			builder.create().show();
		}

		public static File getFileFromServer(String path, ProgressDialog pd)
				throws Exception {
			// �����ȵĻ���ʾ��ǰ��sdcard�������ֻ��ϲ����ǿ��õ�
			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5000);
				// ��ȡ���ļ��Ĵ�С
				pd.setMax(conn.getContentLength());
				InputStream is = conn.getInputStream();
				File file = new File(Environment.getExternalStorageDirectory(),
						"updata.apk");
				FileOutputStream fos = new FileOutputStream(file);
				BufferedInputStream bis = new BufferedInputStream(is);
				byte[] buffer = new byte[1024];
				int len;
				int total = 0;
				while ((len = bis.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					total += len;
					// ��ȡ��ǰ������
					pd.setProgress(total);
				}
				fos.close();
				bis.close();
				is.close();
				return file;
			} else {
				return null;
			}
		}

		/*
		 * �ӷ�����������APK
		 */
		protected void downLoadApk() {
			final ProgressDialog pd; // �������Ի���
			pd = new ProgressDialog(PersonCenterActivity.this);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setMessage("�������ظ���");
			pd.setCanceledOnTouchOutside(true);
			pd.setProgressNumberFormat(null);
			zhuangtai = true;
			pd.show();
			new Thread() {
				@Override
				public void run() {
					try {
						File file = getFileFromServer(URL, pd);
						sleep(3000);
						installApk(file);
						pd.dismiss(); // �������������Ի���
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}

		// ��װapk
		protected void installApk(File file) {
			MainFragmentActivity.zhuangtai = false;
			UserLoginActivity.zhuangtai = false;
			PersonCenterActivity.zhuangtai = false;
			Intent intent = new Intent();
			// ִ�ж���
			intent.setAction(Intent.ACTION_VIEW);
			// ִ�е���������
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");// ���߰����˴�AndroidӦΪandroid��������ɰ�װ����
			PersonCenterActivity.this.startActivity(intent);
		}

		/**
		 * �޸ĵ���
		 * @param login_sign 
		 * @param payment_id 
		 */
		private void loadusersex() {
			try {
			AsyncHttp.get(RealmName.REALM_NAME_LL
					+ "/user_info_edit?user_id="+user_id+"&user_name="+user_name+"" +
							"&nick_name="+nick_name+"&mobile="+mobile+"&sex="+sex+"&birthday=string&email=string" +
					"&telphone=string&qq=string&msn=string&province="+cityTxt1+"&city="+cityTxt2+"&area="+cityTxt3+"&address=string&sign="+login_sign+"",
					new AsyncHttpResponseHandler() {
						@Override
						public void onSuccess(int arg0, String arg1) {
							try {
								JSONObject object = new JSONObject(arg1);
								System.out.println("2================================="+arg1);
								  String status = object.getString("status");
								    String info = object.getString("info");
								    if (status.equals("y")) {
								    	progress.CloseProgress();
//										Toast.makeText(PersonCenterActivity.this, info, 200).show();
										userloginqm();
								    } else {
								    	progress.CloseProgress();
										Toast.makeText(PersonCenterActivity.this, info, 200).show();
									}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

					}, null);
			
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		
		
}
