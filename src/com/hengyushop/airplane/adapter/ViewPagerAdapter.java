package com.hengyushop.airplane.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @author yangyu
 *	鍔熻兘鎻忚堪锛歏iewPager閫傞厤鍣紝鐢ㄦ潵缁戝畾鏁版嵁鍜寁iew
 */
public class ViewPagerAdapter extends PagerAdapter {
	
	//鐣岄潰鍒楄〃
    private ArrayList<View> views;
    
    public ViewPagerAdapter (ArrayList<View> views){
        this.views = views;
    }
       
	/**
	 * 鑾峰緱褰撳墠鐣岄潰鏁?
	 */
	@Override
	public int getCount() {
		 if (views != null) {
             return views.size();
         }      
         return 0;
	}

	/**
	 * 鍒濆鍖杙osition浣嶇疆鐨勭晫闈?
	 */
    @Override
    public Object instantiateItem(View view, int position) {
       
        ((ViewPager) view).addView(views.get(position), 0);
       
        return views.get(position);
    }
    
    /**
	 * 鍒ゆ柇鏄惁鐢卞璞＄敓鎴愮晫闈?
	 */
	@Override
	public boolean isViewFromObject(View view, Object arg1) {
		return (view == arg1);
	}

	/**
	 * 閿?姣乸osition浣嶇疆鐨勭晫闈?
	 */
    @Override
    public void destroyItem(View view, int position, Object arg2) {
        ((ViewPager) view).removeView(views.get(position));       
    }
}
