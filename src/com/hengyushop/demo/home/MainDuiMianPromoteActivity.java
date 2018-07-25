package com.hengyushop.demo.home;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.ctrip.openapi.java.utils.LogoConfig;
import com.ctrip.openapi.java.utils.Util;
import com.example.taobaohead.headview.RoundImageView;
import com.example.uploadpicdemo.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hengyushop.demo.at.BaseActivity;
import com.hengyushop.demo.wec.MyGridView;
import com.hengyushop.entity.GuigeBean;
import com.hengyushop.entity.GuigeData;
import com.hengyushop.entity.GuigellBean;
import com.hengyushop.entity.MyAssetsBean;
import com.lelinju.www.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 
 * ��������
 * 
 * @author Administrator
 * 
 */
public class MainDuiMianPromoteActivity extends BaseActivity implements OnClickListener {
	private ImageView iv_fanhui, cursor1, cursor2, cursor3, cursor4;
	private ImageView iv_qr_image1,iv_qr_image2,iv_touxiang;
	private LinearLayout index_item0, index_item1, ll_buju1, index_item3;
	private SharedPreferences spPreferences;
	private TextView tv_ticket, tv_shop_ticket, tv_jifen_ticket;
	private ArrayList<GuigeBean> list;
	private ArrayList<MyAssetsBean> list_lb;
	String user_name, user_id;
	int len;
	String fund_id = "0";
	private MyGridView gridView2;
	private ListView new_list;
	GuigeData md; 
	GuigeBean mb;
	GuigellBean mbll;
	GuigeBean data_ll;
	MyAssetsBean data;
	public static Handler handler;
	ArrayList<GuigeBean> list_l;
	private static final int THUMB_SIZE = 100;
	private DialogProgress progress;
	String erweima = "";
	RoundImageView networkImage,imv_user_photo;
	Bitmap bitmap;// ��ά���м�ͼƬ
	private int iv_halfWidth = 20;// ��ʾ�м�ͼƬ�Ŀ�ȵ�һ��
	Bitmap mBitmap;// ��ά��ͼƬ;
	Bitmap bitmap_tx,bitmap_touxiang;
	String avatar = "";
	String touxiang = "";
	String mdm_sys = "";
	private ImageLoader mImageLoader;
	private ImageView iv_touxiang2;
	private TextView tv_xiabu;
	private Button btn_fenxiang,btn_zhuti;
	Bitmap alterBitmap;
	private IWXAPI api;
	Bitmap bitmap_fx;
	ImageView iv;
	LinearLayout ll_zhuti,ll_user_buju;
	Bitmap bitMap_tx,bitMap_ewm_tx;
	Bitmap bitMap1;
	Bitmap bitMap2;
	ArrayList<GuigeData> list_ll = new ArrayList<GuigeData>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mdm_promote);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(this);
		user_name = spPreferences.getString("user", "");
		user_id = spPreferences.getString("user_id", "");
		Initialize();
//		String fx_erweimw = getIntent().getStringExtra("fx_erweimw");
//		System.out.println("fx_erweimw========"+fx_erweimw);
//		if (fx_erweimw != null) {
//		getmianduimian();
//		}
		erweima = getIntent().getStringExtra("erweima");
		System.out.println("erweima========"+erweima);
		
		
		String zhuti_tp = getIntent().getStringExtra("num");
		System.out.println("zhuti_tp========"+zhuti_tp);
		if (zhuti_tp != null) {
			intren();
			cursor1.setVisibility(View.INVISIBLE);
			cursor2.setVisibility(View.VISIBLE);
			ll_buju1.setVisibility(View.GONE);
			ll_user_buju.setVisibility(View.VISIBLE);
		}else {
			if (!erweima.equals("")) {
				Bitmap bitmap_erweima = BitUtil.stringtoBitmap(erweima);
				System.out.println("1========"+bitmap_erweima);
				iv_qr_image1.setImageBitmap(bitmap_erweima);
			}else {
				System.out.println("2========"+mBitmap);
//				iv_qr_image1.setImageBitmap(mBitmap);
			}
			cursor1.setVisibility(View.VISIBLE);
			cursor2.setVisibility(View.INVISIBLE);
			ll_buju1.setVisibility(View.VISIBLE);
			ll_user_buju.setVisibility(View.GONE);
		}
		
		
		handler = new Handler() {
			public void dispatchMessage(Message msg) {
				switch (msg.what) {
				case 1:
//					bitmap_fx.recycle();  //����ͼƬ��ռ���ڴ� 
					finish();
		            break;

				default:
					break;
				}
			}
		};
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	/**
	 * �ؼ���ʼ��
	 */
	private void Initialize() {
		
		avatar = spPreferences.getString("avatar", "");
		System.out.println("avatar============="+avatar);
//		if (!avatar.equals("")) {
//			new Thread(getPicByUrl).start();
//		}
		try {
//			tv_djjifen_ticket = (TextView) findViewById(R.id.tv_djjifen_ticket);
			index_item0 = (LinearLayout) findViewById(R.id.index_item0);
			index_item1 = (LinearLayout) findViewById(R.id.index_item1);
			ll_buju1 = (LinearLayout) findViewById(R.id.ll_buju1);
			cursor1 = (ImageView) findViewById(R.id.cursor1);
			cursor2 = (ImageView) findViewById(R.id.cursor2);
			index_item0.setOnClickListener(this);
			index_item1.setOnClickListener(this);
			iv_qr_image1 = (ImageView) findViewById(R.id.iv_qr_image1);
			
			btn_fenxiang = (Button) findViewById(R.id.btn_fenxiang);
			btn_zhuti = (Button) findViewById(R.id.btn_zhuti);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(this);
			ll_user_buju = (LinearLayout) findViewById(R.id.ll_user_buju);
			ll_user_buju.setVisibility(View.VISIBLE);
			iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
			networkImage = (RoundImageView)findViewById(R.id.roundImage_network);
//			iv_touxiang2 = (ImageView) findViewById(R.id.iv_touxiang2);
			iv_qr_image2 = (ImageView) findViewById(R.id.iv_qr_image2);
			btn_fenxiang.setOnClickListener(this);
			btn_zhuti.setOnClickListener(this);
			ll_zhuti = (LinearLayout) findViewById(R.id.ll_zhuti);
			
			
			ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			iv_fanhui.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});

			ll_buju1.setVisibility(View.VISIBLE);
			ll_user_buju.setVisibility(View.GONE);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.index_item0:
			cursor1.setVisibility(View.VISIBLE);
			cursor2.setVisibility(View.INVISIBLE);
			ll_buju1.setVisibility(View.VISIBLE);
			ll_user_buju.setVisibility(View.GONE);
			erweima = getIntent().getStringExtra("erweima");
			System.out.println("erweima========"+erweima);
			if (!erweima.equals("")) {
				Bitmap bitmap_erweima = BitUtil.stringtoBitmap(erweima);
				System.out.println("1========"+bitmap_erweima);
				iv_qr_image1.setImageBitmap(bitmap_erweima);
			}else {
				System.out.println("2========"+mBitmap);
//				iv_qr_image1.setImageBitmap(mBitmap);
			}
			break;
		case R.id.index_item1:
			cursor1.setVisibility(View.INVISIBLE);
			cursor2.setVisibility(View.VISIBLE);
			ll_buju1.setVisibility(View.GONE);
			ll_user_buju.setVisibility(View.VISIBLE);
//			Toast.makeText(MainDuiMianPromoteActivity.this, "�����ճ�����", 200).show();
			intren();
			break;
		case R.id.btn_fenxiang:
			softshareWxChat();
			break;
		case R.id.btn_zhuti:
			try {
			Intent intent = new Intent(MainDuiMianPromoteActivity.this, MianDuiMianGhztActivity.class);
			intent.putExtra("erweima", erweima);
			intent.putExtra("mdm_sys", mdm_sys);
			startActivity(intent);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public void intren() {
		try {
//			btn_fenxiang = (Button) findViewById(R.id.btn_fenxiang);
//			btn_zhuti = (Button) findViewById(R.id.btn_zhuti);
//			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
//			iv_fanhui.setOnClickListener(this);
//			ll_user_buju = (LinearLayout) findViewById(R.id.ll_user_buju);
//			ll_user_buju.setVisibility(View.VISIBLE);
//			iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
//			networkImage = (RoundImageView)findViewById(R.id.roundImage_network);
////			iv_touxiang2 = (ImageView) findViewById(R.id.iv_touxiang2);
//			iv_qr_image2 = (ImageView) findViewById(R.id.iv_qr_image2);
//			btn_fenxiang.setOnClickListener(this);
//			btn_zhuti.setOnClickListener(this);
//			ll_zhuti = (LinearLayout) findViewById(R.id.ll_zhuti);
			
			String zhuti_tp = getIntent().getStringExtra("num");
			System.out.println("zhuti_tp========"+zhuti_tp);
			if (zhuti_tp != null) {
				if (zhuti_tp.equals("1")) {
					
					ll_zhuti.setBackgroundResource(R.drawable.ysj_hb1);
//					Toast.makeText(MianDuiMianFxhbActivity.this, "1", 200).show();
				}else if (zhuti_tp.equals("2")){
					try {
					ll_zhuti.setBackgroundResource(R.drawable.ysj_hb2);
//					Toast.makeText(MianDuiMianFxhbActivity.this, "2", 200).show();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}else {
				ll_zhuti.setBackgroundResource(R.drawable.ysj_hb1);
			}
			
			erweima = getIntent().getStringExtra("erweima");
			System.out.println("erweima========"+erweima);
			if (!erweima.equals("")) {
				Bitmap bitmap_erweima = BitUtil.stringtoBitmap(erweima);
				System.out.println("1========"+bitmap_erweima);
				iv_qr_image2.setImageBitmap(bitmap_erweima);
			}else {
				System.out.println("2========"+mBitmap);
				iv_qr_image2.setImageBitmap(mBitmap);
			}
			
			avatar = spPreferences.getString("avatar", "");
			System.out.println("avatar========"+avatar);
			if (!avatar.equals("")) {
				new Thread(getPicByUrl).start();
				mImageLoader = initImageLoader(MainDuiMianPromoteActivity.this, mImageLoader, "test");
				if (!avatar.equals("")){
					mImageLoader.displayImage(RealmName.REALM_NAME_FTP +avatar,networkImage);
				}
			}else {
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	private void getmianduimian() {
		// TODO Auto-generated method stub
		try {
			
			mdm_sys = getIntent().getStringExtra("mdm_sys");
			System.out.println("mdm_sys================="+mdm_sys);
			bitmap_touxiang = BitUtil.stringtoBitmap(mdm_sys);
//			bitmap_touxiang = GetImgUtil.getImage(mdm_sys);
			System.out.println("avatar============="+avatar);
//			System.out.println("mdm_sys============="+mdm_sys);
			System.out.println("bitmap_touxiang============="+bitmap_touxiang);
			if (avatar.contains("http")) {
				bitmap_tx = BitmapFactory.decodeResource(getResources(),R.drawable.llj_app);
			}else 
			if (!avatar.equals("")) {
					bitmap_tx = bitmap_touxiang;
			}else {
				bitmap_tx = BitmapFactory.decodeResource(getResources(),R.drawable.llj_app);
			}
			
			System.out.println("bitmap_tx============="+bitmap_tx);
			LogoConfig logoConfig = new LogoConfig();
			bitmap = logoConfig.modifyLogo(BitmapFactory.decodeResource(getResources(),R.drawable.white_bg),bitmap_tx);
			// ����ͼƬ���õ�����ȥ��
			Matrix matrix = new Matrix();
			float sx = (float) 2 * iv_halfWidth / bitmap.getWidth();
			float sy = (float) 2 * iv_halfWidth / bitmap.getHeight();
			matrix.setScale(sx, sy);
			System.out.println("bitmap============="+bitmap);
			// �������ź��ͼƬ
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, false);

			String content = getIntent().getStringExtra("erweima");
			System.out.println("content============="+content);
			try {
				mBitmap = createBitmap(new String(content.getBytes(), "ISO-8859-1"));
				erweima = BitUtil.bitmaptoString(mBitmap);
				System.out.println("mBitmap============="+mBitmap);
				iv_qr_image1.setImageBitmap(mBitmap);
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
//			btn_fenxiang = (Button) findViewById(R.id.btn_fenxiang);
//			btn_zhuti = (Button) findViewById(R.id.btn_zhuti);
//			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
//			iv_fanhui.setOnClickListener(this);
//			ll_user_buju = (LinearLayout) findViewById(R.id.ll_user_buju);
//			ll_user_buju.setVisibility(View.VISIBLE);
//			iv_touxiang = (ImageView) findViewById(R.id.iv_touxiang);
//			networkImage = (RoundImageView)findViewById(R.id.roundImage_network);
////			iv_touxiang2 = (ImageView) findViewById(R.id.iv_touxiang2);
//			iv_qr_image2 = (ImageView) findViewById(R.id.iv_qr_image2);
//			btn_fenxiang.setOnClickListener(this);
//			btn_zhuti.setOnClickListener(this);
//			ll_zhuti = (LinearLayout) findViewById(R.id.ll_zhuti);
			
//			String zhuti_tp = getIntent().getStringExtra("num");
//			System.out.println("zhuti_tp========"+zhuti_tp);
//			if (zhuti_tp != null) {
//				if (zhuti_tp.equals("1")) {
//					
//					ll_zhuti.setBackgroundResource(R.drawable.ysj_hb1);
////					Toast.makeText(MianDuiMianFxhbActivity.this, "1", 200).show();
//				}else if (zhuti_tp.equals("2")){
//					try {
//					ll_zhuti.setBackgroundResource(R.drawable.ysj_hb2);
////					Toast.makeText(MianDuiMianFxhbActivity.this, "2", 200).show();
//					} catch (Exception e) {
//						// TODO: handle exception
//						e.printStackTrace();
//					}
//				}
//			}else {
//				ll_zhuti.setBackgroundResource(R.drawable.ysj_hb1);
//			}
			
			System.out.println("erweima========"+erweima);
			if (erweima.equals("")) {
				Bitmap bitmap_erweima = BitUtil.stringtoBitmap(erweima);
				System.out.println("1========"+bitmap_erweima);
				iv_qr_image2.setImageBitmap(bitmap_erweima);
			}else {
				System.out.println("2========"+mBitmap);
				iv_qr_image2.setImageBitmap(mBitmap);
			}
			
			avatar = spPreferences.getString("avatar", "");
			System.out.println("avatar========"+avatar);
			if (!avatar.equals("")) {
				new Thread(getPicByUrl).start();
				mImageLoader = initImageLoader(MainDuiMianPromoteActivity.this, mImageLoader, "test");
				if (!avatar.equals("")){
					mImageLoader.displayImage(RealmName.REALM_NAME_FTP +avatar,networkImage);
				}
			}else {
			}
			
				
				
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	

	  Runnable getPicByUrl = new Runnable() {
			@Override
			public void run() {
				try {
//					String img_url2 = "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
					String img_url2 = RealmName.REALM_NAME_HTTP +avatar;
					System.out.println("img_url2=============="+img_url2);
					Bitmap bmp = GetImgUtil.getImage(img_url2);// BitmapFactory��ͼƬ������
					bitMap_ewm_tx = bmp;
					bitMap_tx = Utils.toRoundBitmap(bmp,null);// ���ʱ���ͼƬ�Ѿ��������Բ�ε���
					System.out.println("bitMap1=============="+bitMap_tx);
				} catch (Exception e) {
					Log.i("ggggg", e.getMessage());
				}
			}
		};
		
//	  Runnable getPicByUrl = new Runnable() {
//			@Override
//			public void run() {
//				try {
//					String img_url2 = RealmName.REALM_NAME_HTTP +avatar;
//					System.out.println("img_url2=============="+img_url2);
//					bitmap_touxiang = GetImgUtil.getImage(img_url2);// BitmapFactory��ͼƬ������
//					Bitmap	bitMap_tx = Utils.toRoundBitmap(bitmap_touxiang,null);// ���ʱ���ͼƬ�Ѿ��������Բ�ε���
//					touxiang = BitUtil.bitmaptoString(bitMap_tx);
//					System.out.println("touxiang=============="+touxiang);
//					System.out.println("bitmap_touxiang=============="+bitmap_touxiang);
////					bitmap_tx = bitmap_touxiang;
//				} catch (Exception e) {
//					Log.i("ggggg", e.getMessage());
//				}
//			}
//		};
		
		/**
		 * ΢�ŷ���ͼƬ
		 * 
		 * @param text
		 */
		private void softshareWxChat() {
	         try {
//	        	 if (bitMap_tx != null) {
	        	 
	        	  // ͼƬ�ϳ�-���� ��ȥ��A ��ȥ��B
	 			String zhuti_tp = getIntent().getStringExtra("num");
	 			System.out.println("zhuti_tp========"+zhuti_tp);
	 			System.out.println("bitmap_fx0=============="+bitmap_fx);
	 			if (zhuti_tp != null) {
//	 				bitmap_fx.recycle();  //����ͼƬ��ռ���ڴ�  	
	 				if (zhuti_tp.equals("1")) {
//	 					String fx_tp2 = spPreferences.getString("fx_tp2", "");
//	 					bitmap_fx = BitUtil.stringtoBitmap(fx_tp2);
//	 					bitmap_fx = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao1); // bitmapΪֻ����
	 				   InputStream is = this.getResources().openRawResource(R.drawable.ysj_haibao1); 
					     BitmapFactory.Options options=new BitmapFactory.Options(); 
					     options.inJustDecodeBounds = false; 
					     options.inSampleSize = 1;   //width��hight��Ϊԭ����ʮ��һ
					     bitmap_fx =BitmapFactory.decodeStream(is,null,options); 
					     zhou();
	 				}else if (zhuti_tp.equals("2")){
	 					try {
//	 					bitmap_fx = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao2); // bitmapΪֻ����
	 						 InputStream is = this.getResources().openRawResource(R.drawable.ysj_haibao2); 
	 					     BitmapFactory.Options options=new BitmapFactory.Options(); 
	 					     options.inJustDecodeBounds = false; 
	 					     options.inSampleSize = 2;   //width��hight��Ϊԭ����ʮ��һ
	 					     bitmap_fx =BitmapFactory.decodeStream(is,null,options); 
	 					     zhoull();	
	 					} catch (Exception e) {
							// TODO: handle exception
	 						e.printStackTrace();
						}
	 				}
	 			} else {
//	 				bitmap_fx = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_haibao1); // bitmapΪֻ����
//	 				yuanban();
	 				     InputStream is = this.getResources().openRawResource(R.drawable.ysj_haibao1); 
					     BitmapFactory.Options options=new BitmapFactory.Options(); 
					     options.inJustDecodeBounds = false; 
					     options.inSampleSize = 1;   //width��hight��Ϊԭ����ʮ��һ
					     bitmap_fx =BitmapFactory.decodeStream(is,null,options); 
					     zhou();
	 			}
	 			
	 	        System.out.println("bitmap_fx1=============="+bitmap_fx);
	 	        
	         } catch (Exception e) {
	 			// TODO: handle exception
	        	 e.printStackTrace();
	 		}
		}
		
		
		
		private String buildTransaction(final String type) {
			return (type == null) ? String.valueOf(System.currentTimeMillis())
					: type + System.currentTimeMillis();
		}
		
		private void zhou() {
			// TODO Auto-generated method stub
			 System.out.println("bitmap_fx1=============="+bitmap_fx);
		        
		        alterBitmap = Bitmap.createBitmap(bitmap_fx.getWidth(),bitmap_fx.getHeight(), bitmap_fx.getConfig());
		        Canvas canvas = new Canvas(alterBitmap);
		        Paint paint = new Paint();
		        paint.setColor(Color.BLACK);
		        canvas.drawBitmap(bitmap_fx, new Matrix(), paint);
		        System.out.println("bitmap_fx2=============="+bitmap_fx);
		        bitmap_fx.recycle();  //����ͼƬ��ռ���ڴ�  
		       
		       //ͷ��
//		       String touxiang = getIntent().getStringExtra("touxiang");
//		       System.out.println("touxiang=============="+touxiang);
		       if (bitMap_tx == null) {
		    	     bitMap2 = BitmapFactory.decodeResource(getResources(),R.drawable.llj_app);
		       }else {
//		    	   String touxiang1 = getIntent().getStringExtra("touxiang");
//		    	   System.out.println("touxiang=============="+touxiang1);
//		    	    Bitmap bitmap_tx = BitUtil.stringtoBitmap(touxiang1);
//		    	    System.out.println("bitmap_tx=============="+bitmap_tx);
		    	    bitMap2 = bitMap_tx;
		       }
		       System.out.println("bitMap2=============="+bitMap2);
		        if (bitMap2 != null) {
		    	   try {
//		        bitMap2 = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_tx);
		        int width2 = bitMap2.getWidth();
		        int height2 = bitMap2.getHeight();
		        // ������Ҫ�Ĵ�С
		        int newWidth2= 180;
		        int newHeight2 = 180;
		        // �������ű���
		        float scaleWidth2 = ((float) newWidth2) / width2;
		        float scaleHeight2 = ((float) newHeight2) / height2;
		        // ȡ����Ҫ���ŵ�matrix����
		        Matrix matrix2 = new Matrix();
		        matrix2.postScale(scaleWidth2, scaleHeight2);
		        // �õ��µ�ͼƬ
		        bitMap2 = Bitmap.createBitmap(bitMap2, 0, 0, width2, height2, matrix2, true);
		        canvas.drawBitmap(bitMap2, 290, 180, null);
		    	  } catch (Exception e) {
						// TODO: handle exception
		    		  e.printStackTrace();
				  }
		       }else {
				
			   }
		        
		        //��ά��
//		        erweima = getIntent().getStringExtra("erweima");
		        System.out.println("erweima=================================="+erweima);
				Bitmap bitMap = BitUtil.stringtoBitmap(erweima);
		        int width = bitMap.getWidth();
		        int height = bitMap.getHeight();
//	 	        // ������Ҫ�Ĵ�С
	 	        int newWidth = 270;
	 	        int newHeight = 270;
		        // �������ű���
		        float scaleWidth = ((float) newWidth) / width;
		        float scaleHeight = ((float) newHeight) / height;
		        // ȡ����Ҫ���ŵ�matrix����
		        Matrix matrix = new Matrix();
		        matrix.postScale(scaleWidth, scaleHeight);
		        // �õ��µ�ͼƬ
		        bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
		        canvas.drawBitmap(bitMap, 240, 935, null);
		        
		       System.out.println("bitMap_tx=============="+bitMap_tx);
		       
		        
//		        iv.setImageBitmap(alterBitmap);
		        System.out.println("alterBitmap=============="+alterBitmap);
//			   ll_user_buju.setVisibility(View.GONE);
		        api = WXAPIFactory.createWXAPI(MainDuiMianPromoteActivity.this, Constants.APP_ID,false);
				api.registerApp(Constants.APP_ID);
				WXImageObject imgObj1 = new WXImageObject(alterBitmap);
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = imgObj1;
				
				Bitmap thumbBmp = Bitmap.createScaledBitmap(alterBitmap, THUMB_SIZE, THUMB_SIZE, true);
				alterBitmap.recycle();
				msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // ��������ͼ
				
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				
				
				req.transaction = buildTransaction("webpage");
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneSession;
				boolean flag = api.sendReq(req);

				System.out.println("΢��ע��" + flag);
				
		}
		
		private void zhoull() {
			// TODO Auto-generated method stub
			 System.out.println("bitmap_fx1=============="+bitmap_fx);
		        
		        alterBitmap = Bitmap.createBitmap(bitmap_fx.getWidth(),bitmap_fx.getHeight(), bitmap_fx.getConfig());
		        Canvas canvas = new Canvas(alterBitmap);
		        Paint paint = new Paint();
		        paint.setColor(Color.BLACK);
		        canvas.drawBitmap(bitmap_fx, new Matrix(), paint);
		        System.out.println("bitmap_fx2=============="+bitmap_fx);
		        bitmap_fx.recycle();  //����ͼƬ��ռ���ڴ�  
		       
		       //ͷ��
		       if (bitMap_tx == null) {
		    	     bitMap2 = BitmapFactory.decodeResource(getResources(),R.drawable.llj_app);
		       }else {
		    	    bitMap2 = bitMap_tx;
		       }
		       System.out.println("bitMap2=============="+bitMap2);
		        if (bitMap2 != null) {
		    	   try {
		        int width2 = bitMap2.getWidth();
		        int height2 = bitMap2.getHeight();
		        // ������Ҫ�Ĵ�С
		        int newWidth2= 140;
		        int newHeight2 = 140;
		        // �������ű���
		        float scaleWidth2 = ((float) newWidth2) / width2;
		        float scaleHeight2 = ((float) newHeight2) / height2;
		        // ȡ����Ҫ���ŵ�matrix����
		        Matrix matrix2 = new Matrix();
		        matrix2.postScale(scaleWidth2, scaleHeight2);
		        // �õ��µ�ͼƬ
		        bitMap2 = Bitmap.createBitmap(bitMap2, 0, 0, width2, height2, matrix2, true);
		        canvas.drawBitmap(bitMap2, 200, 120, null);
		    	  } catch (Exception e) {
						// TODO: handle exception
		    		  e.printStackTrace();
				  }
		       }else {
				
			   }
		        
		        //��ά��
//		        erweima = getIntent().getStringExtra("erweima");
				Bitmap bitMap = BitUtil.stringtoBitmap(erweima);
		        int width = bitMap.getWidth();
		        int height = bitMap.getHeight();
//	 	        // ������Ҫ�Ĵ�С
	 	        int newWidth = 200;
	 	        int newHeight = 200;
		        // �������ű���
		        float scaleWidth = ((float) newWidth) / width;
		        float scaleHeight = ((float) newHeight) / height;
		        // ȡ����Ҫ���ŵ�matrix����
		        Matrix matrix = new Matrix();
		        matrix.postScale(scaleWidth, scaleHeight);
		        // �õ��µ�ͼƬ
		        bitMap = Bitmap.createBitmap(bitMap, 0, 0, width, height, matrix, true);
		        canvas.drawBitmap(bitMap, 170, 670, null);
		        
		       System.out.println("bitMap_tx=============="+bitMap_tx);
		       
		        
		        System.out.println("alterBitmap=============="+alterBitmap);
//			    ll_user_buju.setVisibility(View.GONE);
		        api = WXAPIFactory.createWXAPI(MainDuiMianPromoteActivity.this, Constants.APP_ID,false);
				api.registerApp(Constants.APP_ID);
				WXImageObject imgObj1 = new WXImageObject(alterBitmap);
				WXMediaMessage msg = new WXMediaMessage();
				msg.mediaObject = imgObj1;
				//import com.lglottery.www.http.Util;
				Bitmap thumbBmp = Bitmap.createScaledBitmap(alterBitmap, THUMB_SIZE, THUMB_SIZE, true);
				alterBitmap.recycle();
				msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // ��������ͼ
				
				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("webpage");
				req.message = msg;
				req.scene = SendMessageToWX.Req.WXSceneSession;
				boolean flag = api.sendReq(req);

				System.out.println("΢��ע��" + flag);
		}
	/**
	 * �����ַ������ɶ�ά��
	 * 
	 * @param s
	 * @return
	 * @throws WriterException
	 */
	private Bitmap createBitmap(String str) throws WriterException {
		// ���ɶ�Ϊ���󣬱�����ָ����С����Ҫ������ͼƬ�ڽ������ţ������ᵼ��ģ��ʶ��ʧ�ܣ�����ɨ��ʧ���ˡ�
		BitMatrix mBitMatrix = new MultiFormatWriter().encode(str,
				BarcodeFormat.QR_CODE, 300, 300);// BarcodeFormat.QR_CODE-�����ʽ
		// ��ά����Ŀ��
		int w = mBitMatrix.getWidth();
		int h = mBitMatrix.getHeight();

		// ͷ��Ŀ��
		int halfw = w / 2;
		int halfh = h / 2;
		// ׼������ά�룬�Ѷ�ά����ת��Ϊһά���飬һֱ���Ż�
		int[] pixels = new int[w * h];// ���鳤�Ⱦ��Ǿ�������ֵ
		for (int y = 0; y < h; y++) {
			int outputOffset = y * w;
			for (int x = 0; x < w; x++) {
				// ��һ����ͨ�Ķ�ά��
				// if (mBitMatrix.get(x, y)) {// ��ʾ��ά������ֵ����Ӧ��һ���ڵ�
				// pixels[outputOffset + x] = 0xff000000;
				// } else {
				// pixels[outputOffset + x] = 0xffffffff;
				// }

				// ��һ����ͼƬ�Ķ�ά��ͼƬ
				if (x > (halfw - iv_halfWidth) && x < (halfw + iv_halfWidth)
						&& y > (halfh - iv_halfWidth)
						&& y < (halfh + iv_halfWidth)) {// �м�ͼƬ������
					pixels[outputOffset + x] = bitmap.getPixel(x - halfw
							+ iv_halfWidth, y - halfh + iv_halfWidth);// ���ﻭͼ֮�������Ե���ʾ����
				} else {
					if (mBitMatrix.get(x, y)) {// ��ʾ��ά������ֵ����Ӧ��һ���ڵ�
						pixels[outputOffset + x] = 0xff000000;
					} else {
						pixels[outputOffset + x] = 0xffffffff;
					}
				}
			}
		}
		Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ��300*300bitmap
		bitmap.setPixels(pixels, 0, w, 0, 0, w, h);// ���ص㡢��ʼ�㡢��ȡ�����ʼ���ء�����
		return bitmap;

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

}
