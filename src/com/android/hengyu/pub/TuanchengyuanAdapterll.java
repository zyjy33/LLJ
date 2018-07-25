package com.android.hengyu.pub;

import java.io.File;
import java.util.ArrayList;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.example.taobaohead.headview.RoundImageView;
import com.example.uploadpicdemo.Utils;
import com.hengyushop.entity.JuTuanGouData;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.lelinju.www.R;

public class TuanchengyuanAdapterll extends BaseAdapter {

	private ArrayList<JuTuanGouData> list;
	private ArrayList<Integer> list_num;
//	private ArrayList datadz;
	private ArrayList datadz2;
	private Context context;
	public AQuery mAq;
	String user_id,avatar;
	private ImageLoader mImageLoader;
	ImageView image;
	String jutuan_tx;
	int num;
	String img_url;
	Bitmap bitMap_tx;
	String data_tx;
//	private XCRoundImageViewByXfermode circleImageView;//Բ��ͼƬ
//	public TuanchengyuanAdapter(ArrayList<JuTuanGouData> list, ArrayList datadz,Context context) {// List<Integer> list_num, ArrayList datadz,
		public TuanchengyuanAdapterll(ArrayList datadz2,Context context) {
		this.context = context;
//		this.list = list;
//		this.datadz = datadz;
		this.datadz2 = datadz2;
//		this.list_num = (ArrayList<Integer>) list_num;
		mAq = new AQuery(context);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return datadz2.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datadz2.get(position);
//		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LinearLayout.inflate(context,R.layout.tuan_chengyuan_itemll, null);
		}
//		RoundImageView networkImage = (RoundImageView) convertView.findViewById(R.id.roundImage_network);
//		circleImageView = (XCRoundImageViewByXfermode)convertView.findViewById(R.id.cicleImageView);
		image = (ImageView) convertView.findViewById(R.id.img_ware);
		LinearLayout ll_touxiang = (LinearLayout) convertView.findViewById(R.id.ll_touxiang);
		System.out.println("position================="+position);
		try {
			num = position;
		System.out.println("datadz2================="+(String) datadz2.get(position));
		
		if (!datadz2.get(position).equals("")) {
//			circleImageView.setVisibility(View.VISIBLE);
//			image.setVisibility(View.GONE);
			data_tx = (String) datadz2.get(position);
			img_url = "http://mobile.lelinju.shop/templates/mobile/images/leader-user.png";//Ĭ��ͼƬ
			if (data_tx.contains("http")) {
				img_url = (String) datadz2.get(position);
	       } else if(data_tx.contains("upload")){
	    	   img_url = "http://mobile.lelinju.shop"+(String) datadz2.get(position);
		   }
			mAq.id(image).image(img_url);
		}else {
//			circleImageView.setVisibility(View.GONE);
//			image.setVisibility(View.VISIBLE);
			String img_url = "http://mobile.lelinju.shop/images/ysj_tx.png";
			mAq.id(image).image(img_url);
			System.out.println("1=================");
		}
		
		
		
//		mImageLoader = initImageLoader(context, mImageLoader, "test");
//		mImageLoader.displayImage(RealmName.REALM_NAME_FTP +datadz2.get(position),networkImage);
		
//		String data_tx = "http://wx.qlogo.cn/mmopen/Zw5SzXToEzuCtHFRb2IVVZemJzJx4cLibMpDIE2y4kA1lgPfbhe2rO851s5G72B2U1Wz6cGe8Eb7B4AbtibiaUaSRBeH1XqqMiam/0";
//		String data_tx = (String) datadz2.get(position);
//		Bitmap bitmap = BitUtil.stringtoBitmap(data_tx);
//		bitmap = Utils.toRoundBitmap(bitmap, null); // ���ʱ���ͼƬ�Ѿ��������Բ�ε���
//		image.setImageBitmap(bitmap);
		
//		System.out.println("================="+RealmName.REALM_NAME_HTTP+datadz2.get(position));
		
////		if (position == 1) {
////			System.out.println("position=========22========"+position);
//////			if (!avatar.equals("")) {
//////			mImageLoader = initImageLoader(context, mImageLoader, "test");
//////			mImageLoader.displayImage(RealmName.REALM_NAME_FTP +avatar,networkImage);
//////			}
////				if (!datadz2.get(position).equals("")) {
////					String data_tx = (String) datadz2.get(position);
////					if (data_tx.contains("http")) {
//////				ll_touxiang.setVisibility(View.VISIBLE);
//////			    networkImage.setVisibility(View.GONE);
//////				Bitmap bitmap = BitUtil.stringtoBitmap(data_tx);
//////				bitmap = Utils.toRoundBitmap(bitmap, null); // ���ʱ���ͼƬ�Ѿ��������Բ�ε���
//////				image.setImageBitmap(bitmap);
////				
////				mImageLoader = initImageLoader(context, mImageLoader, "test");
////				mImageLoader.displayImage(data_tx,networkImage);
////			}else if (!datadz2.get(position).equals("")){
////					mImageLoader = initImageLoader(context, mImageLoader, "test");
////					mImageLoader.displayImage(RealmName.REALM_NAME_FTP +datadz2.get(position),networkImage);
////			}
////				}else {
////					
////				}
////		}
//		
//		else {
//			if (!datadz2.get(position).equals("")) {
//			String data_tx = (String) datadz2.get(position);
//			if (data_tx.contains("http")) {
////				ll_touxiang.setVisibility(View.VISIBLE);
////			    networkImage.setVisibility(View.GONE);
////				Bitmap bitmap = BitUtil.stringtoBitmap(data_tx);
////				bitmap = Utils.toRoundBitmap(bitmap, null); // ���ʱ���ͼƬ�Ѿ��������Բ�ε���
////				image.setImageBitmap(bitmap);
//				
//				mImageLoader = initImageLoader(context, mImageLoader, "test");
//				mImageLoader.displayImage(data_tx,networkImage);
//			}else if (datadz2.get(position).equals("")){
//				ll_touxiang.setVisibility(View.VISIBLE);
//			    networkImage.setVisibility(View.GONE);
//			    image.setBackgroundResource(R.drawable.ysj_tx);
//			}else {
//				mImageLoader = initImageLoader(context, mImageLoader, "test");
//				mImageLoader.displayImage(RealmName.REALM_NAME_FTP +datadz2.get(position),networkImage);
//			}
//			}else {
//				
//			}
//		}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		System.out.println("==============================================================");
		return convertView;
	}
	
	  Runnable getPicByUrl = new Runnable() {
			@Override
			public void run() {
				try {
//					String img_url2 = "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
					String img_url2 = RealmName.REALM_NAME_HTTP +(String) datadz2.get(num);
					System.out.println("img_url2=====1========="+img_url2);
					Bitmap bmp = GetImgUtil.getImage(img_url);// BitmapFactory��ͼƬ������
					bitMap_tx = Utils.toRoundBitmap(bmp,null);// ���ʱ���ͼƬ�Ѿ��������Բ�ε���
					System.out.println("bitMap1=============="+bitMap_tx);
//					image.setImageBitmap(bitMap_tx);
				} catch (Exception e) {
					Log.i("ggggg", e.getMessage());
				}
			}
		};
		
		
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
			dirName = "";
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
		
		/**
	    *
	    * @param x
	    *            ͼ��Ŀ��
	    * @param y
	    *            ͼ��ĸ߶�
	    * @param image
	    *            ԴͼƬ
	    * @param outerRadiusRat
	    *            Բ�ǵĴ�С
	    * @return Բ��ͼƬ
	    */
	   Bitmap createFramedPhoto(int x, int y, Bitmap image, float outerRadiusRat) {
	       // ����Դ�ļ��½�һ��darwable����
	       Drawable imageDrawable = new BitmapDrawable(image);

	       // �½�һ���µ����ͼƬ
	       Bitmap output = Bitmap.createBitmap(x, y, Bitmap.Config.ARGB_8888);
	       Canvas canvas = new Canvas(output);

	       // �½�һ������
	       RectF outerRect = new RectF(50, 50, x, y);

	       // ����һ����ɫ��Բ�Ǿ���
	       Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	       paint.setColor(Color.RED);
	       paint.setStyle(Paint.Style.STROKE);
	       paint.setStrokeWidth(3);
	       canvas.drawRoundRect(outerRect, outerRadiusRat, outerRadiusRat, paint);

	       // ��ԴͼƬ���Ƶ����Բ�Ǿ�����
//	       paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	       imageDrawable.setBounds(0, 0, x, y);
	       canvas.saveLayer(outerRect, paint, Canvas.ALL_SAVE_FLAG);
	       imageDrawable.draw(canvas);
	       canvas.restore();

	       return output;
	   }
}
