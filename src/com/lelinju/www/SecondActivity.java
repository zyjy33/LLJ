package com.lelinju.www;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.hengyu.web.RealmName;
import com.androidquery.AQuery;
import com.hengyushop.dao.AdvertDao1;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lglottery.www.common.Config;
import com.lglottery.www.common.SharedUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.lelinju.www.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter; 
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
public class SecondActivity extends BaseActivity {
	/**
	 * 关于引导页的界面
	 */
    private ViewPager viewPager;  
    private ArrayList<View> pageViews;  
    SharedPreferences preferences ;
	private ImageView i0,gd_ll;
	private ViewPager i1;
	public AQuery mAq;
    /** Called when the activity is first created. */
	private Handler handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
//				 Toast.makeText(SecondActivity.this, "自动登录首页", 200).show();
				 Intent intent = new Intent(SecondActivity.this,MainFragmentActivity.class);
//				 Intent intent = new Intent(SecondActivity.this,MainFragment.class);
	    		 startActivity(intent);
	    		 AppManager.getAppManager().finishActivity();
				break;
			case 1:
				if(u.getStringValue("url").length()!=0){
		        	 imageLoader.displayImage(u.getStringValue("url"), i0);
		        }
				break;
			default:
				break;
			}
		};
	};
	private SharedUtils u;
    @Override
    public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_layout);
        i0 = (ImageView) findViewById(R.id.gd);
        gd_ll = (ImageView) findViewById(R.id.gd_ll);
        gd_ll.setBackgroundResource(R.drawable.llj_ll);
        mAq = new AQuery(this);
        u = new SharedUtils(getApplicationContext(), "url");
        if(u.getStringValue("url").length()!=0){
        	 imageLoader.displayImage(u.getStringValue("url"), i0);
        }
        Log.i("1", "这里出错了！---");
       
        loadguanggao();
//       AsyncHttp.get(RealmName.REALM_NAME+"/mi/getdata.ashx?act=GetPhoneGuidePageList&yth=admin", new AsyncHttpResponseHandler(){
//        	@Override
//        	public void onSuccess(int arg0, String arg1) {
//        		// TODO Auto-generated method stub
//        		super.onSuccess(arg0, arg1);
//        		try {
//        			Log.i("2", "这里出错了！---");
//					JSONObject j = new JSONObject(arg1);
//					u.setStringValue("url", RealmName.REALM_NAME+"/"+j.getString("GuidePageUrl1"));
//					 imageLoader.loadImage(RealmName.REALM_NAME+"/"+j.getString("GuidePageUrl1"), new ImageLoadingListener() {
//						@Override
//						public void onLoadingStarted(String imageUri, View view) {
//							// TODO Auto-generated method stub
//							
//						}
//						
//						@Override
//						public void onLoadingFailed(String imageUri, View view,
//								FailReason failReason) {
//							// TODO Auto-generated method stub
//							
//						}
//						
//						@Override
//						public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//							// TODO Auto-generated method stub
//							handler.sendEmptyMessage(1);
//						}
//						
//						@Override
//						public void onLoadingCancelled(String imageUri, View view) {
//							// TODO Auto-generated method stub
//							
//						}
//					});
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//        		 
//        	}
//        }, getApplicationContext());
       
        
        
        i0.postDelayed(new Runnable() {

			@Override
			public void run() { 
				handler.sendEmptyMessage(0);
				 
			}
		}, 3000);
      
    }
	private void loadguanggao() {
		try {
			
		//广告滚动	
		AsyncHttp.get(RealmName.REALM_NAME_LL
				+ "/get_adbanner_list?advert_id=15",
				new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							JSONObject object = new JSONObject(arg1);
							JSONArray array = object.getJSONArray("data");
							int len = array.length();
							ArrayList<AdvertDao1> images = new ArrayList<AdvertDao1>();
							for (int i = 0; i < len; i++) {
								AdvertDao1 ada = new AdvertDao1();
								JSONObject json = array.getJSONObject(i);
								ada.setId(json.getString("id"));
								ada.setAd_url(json.getString("ad_url"));
								String ad_url = ada.getAd_url();
//							    ImageLoader imageLoader=ImageLoader.getInstance();
//							    imageLoader.displayImage(RealmName.REALM_NAME_HTTP + ad_url, i0);
							    mAq.id(i0).image(RealmName.REALM_NAME_HTTP+ad_url);
							    
								images.add(ada);
								
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
    
    @Override
    protected void onStart() {
    	// TODO Auto-generated method stub
    	super.onStart();
    	 
    }
    
}