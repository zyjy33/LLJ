package com.android.hengyu.pub;

import java.io.File;
import java.util.ArrayList;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.hengyushop.entity.JuTuanGouData;
import com.lelinju.www.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class TuanchengyuanAdapter extends BaseAdapter {

	private ArrayList<JuTuanGouData> list;
	private Context context;
	public AQuery mAq;
	public TuanchengyuanAdapter(ArrayList<JuTuanGouData> list, Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.list = list;
		mAq = new AQuery(context);
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

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LinearLayout.inflate(context,R.layout.tuan_chengyuan_item, null);
		}
		
//		ImageView image = (ImageView) convertView.findViewById(R.id.img_ware);
		
//		RoundImageView networkImage = (RoundImageView) convertView.findViewById(R.id.roundImage_network);
//		mImageLoader = initImageLoader(context, mImageLoader, "test");
//		mImageLoader.displayImage(headimgurl,networkImage);
		
//		TextView tv_name = (TextView) convertView.findViewById(R.id.tv_ware_name);
		
//		mAq.id(image).image(RealmName.REALM_NAME_HTTP+list.get(position).icon_url);

		return convertView;
	}
	
	/**
	 * 初始化图片下载器，图片缓存地址<i>("/Android/data/[app_package_name]/cache/dirName")</i>
	 */
	public ImageLoader initImageLoader(Context context,
			ImageLoader imageLoader, String dirName) {
		imageLoader = ImageLoader.getInstance();
		if (imageLoader.isInited()) {
			// 重新初始化ImageLoader时,需要释放资源.
			imageLoader.destroy();
		}
		imageLoader.init(initImageLoaderConfig(context, dirName));
		return imageLoader;
	}
	/**
	 * 配置图片下载器
	 * 
	 * @param dirName
	 *            文件名
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
