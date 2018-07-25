package com.lelinju.www;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.android.hengyu.web.RealmName;
import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;
public class GuideActivity extends BaseActivity {
	/**
	 * ��������ҳ�Ľ���
	 */
    private ViewPager viewPager;  
    private ArrayList<View> pageViews;  
    SharedPreferences preferences ;
	private ImageView i0;
	private ViewPager i1;
    /** Called when the activity is first created. */
	private Handler handler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				preferences = getSharedPreferences("guide", Activity.MODE_PRIVATE);
			      //��������Ѿ�����
			    	  if(preferences.getString("flow", "").equals("yes")){
			    		  getgaoguan();
//			    		  Intent intent = new Intent(GuideActivity.this,SecondActivity.class);
//			    		  startActivity(intent);
//			    		 AppManager.getAppManager().finishActivity();
			      }else {
//			    	  i1.setVisibility(View.VISIBLE);
			          i0.setVisibility(View.GONE);
			          Intent intent = new Intent(GuideActivity.this,Guide2Activity.class);
						Editor editor = preferences.edit();
						editor.putString("flow", "yes");
						editor.commit();
		    		  startActivity(intent);
		    		  finish();
				}
				break;

			default:
				break;
			}
		};
	};

	/**
	 * �ж��Ƿ��й��
	 */
	private void getgaoguan() {
		// TODO Auto-generated method stub
		
		AsyncHttp.get(RealmName.REALM_NAME_LL+ "/get_adbanner_list?advert_id=15",new AsyncHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, String arg1) {
						super.onSuccess(arg0, arg1);
						try {
							System.out.println("-----------------" + arg1);
							JSONObject object = new JSONObject(arg1);
							String status = object.getString("status");
							if (status.equals("y")) {
								Intent intent = new Intent(GuideActivity.this,SecondActivity.class);
								startActivity(intent);
								AppManager.getAppManager().finishActivity();
							}else {
								Intent intent = new Intent(GuideActivity.this,MainFragmentActivity.class);
								startActivity(intent);
								AppManager.getAppManager().finishActivity();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					@Override
					public void onFailure(Throwable arg0, String arg1) {
					// TODO Auto-generated method stub
					super.onFailure(arg0, arg1);
					System.out.println("�쳣-----------------" + arg1);
					Intent intent = new Intent(GuideActivity.this,MainFragmentActivity.class);
					startActivity(intent);
					AppManager.getAppManager().finishActivity();
					}
				}, GuideActivity.this);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        try {
			
        setContentView(R.layout.item01);
        i0 = (ImageView) findViewById(R.id.i0);
        i0.setBackgroundResource(R.drawable.llj_qd);
        i1 = (ViewPager) findViewById(R.id.i1);
        i0.setVisibility(View.VISIBLE);
        i1.setVisibility(View.GONE);
        i0.postDelayed(new Runnable() {

			@Override
			public void run() { 
				handler.sendEmptyMessage(0);
				 
			}
		}, 3000);
        
        } catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
      
    }
    
//    @Override
//    protected void onStart() {
//    	// TODO Auto-generated method stub
//    	super.onStart();
//    	//��õõ�ҳ������ö���
//        LayoutInflater inflater = getLayoutInflater();
//        pageViews = new ArrayList<View>();  
//        pageViews.add(inflater.inflate(R.layout.item2, null));  
//        pageViews.add(inflater.inflate(R.layout.item3, null));  
//        pageViews.add(inflater.inflate(R.layout.item4, null));  
//        pageViews.add(inflater.inflate(R.layout.item5, null));  
////        pageViews.add(inflater.inflate(R.layout.item6, null));  
//        // group��R.layou.main�еĸ������СԲ���LinearLayout.  
//        viewPager = (ViewPager)findViewById(R.id.i1);  
//        viewPager.setAdapter(new GuidePageAdapter());  
//        viewPager.setOnPageChangeListener(new GuidePageChangeListener());  
//    }
//    
//    /** ָ��ҳ��Adapter */
//    class GuidePageAdapter extends PagerAdapter {  
//        @Override  
//        public int getCount() {  
//            return pageViews.size();  
//        }  
//        @Override  
//        public boolean isViewFromObject(View arg0, Object arg1) {  
//            return arg0 == arg1;  
//        }  
//        @Override  
//        public int getItemPosition(Object object) {  
//            // TODO Auto-generated method stub  
//            return super.getItemPosition(object);  
//        }  
//        @Override  
//        public void destroyItem(View arg0, int arg1, Object arg2) {  
//            // TODO Auto-generated method stub  
//            ((ViewPager) arg0).removeView(pageViews.get(arg1));  
//        }  
//        @Override  
//        public Object instantiateItem(View arg0, int arg1) {  
//            // TODO Auto-generated method stub  
//            ((ViewPager) arg0).addView(pageViews.get(arg1)); 
//            pageViews.get(3).setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					 
//						Intent intent = new Intent(GuideActivity.this,MainFragment.class);
//						startActivity(intent);
//						finish();
//						Editor editor = preferences.edit();
//						editor.putString("flow", "yes");
//						editor.commit();
//					 
//				
//				}
//			});
//            return pageViews.get(arg1);  
//        }  
//        @Override  
//        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
//            // TODO Auto-generated method stub  
//        }  
//        @Override  
//        public Parcelable saveState() {  
//            // TODO Auto-generated method stub  
//            return null;  
//        }  
//        @Override  
//        public void startUpdate(View arg0) {  
//            // TODO Auto-generated method stub  
//        }  
//        @Override  
//        public void finishUpdate(View arg0) {  
//            // TODO Auto-generated method stub  
//        }  
//    } 
//    /** ָ��ҳ��ļ����� */
//    class GuidePageChangeListener implements OnPageChangeListener {  
//        public void onPageScrollStateChanged(int arg0) {  
//            // TODO Auto-generated method stub  
//  
//        }  
//        public void onPageScrolled(int arg0, float arg1, int arg2) {  
//            // TODO Auto-generated method stub  
//        }  
//        public void onPageSelected(int arg0) { 
//        }  
//    }  
}