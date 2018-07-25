package com.ctrip.openapi.java.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class CustomScrollView extends ScrollView {
	
	 // 滑动距离及坐�?  
    private float xDistance, yDistance, xLast, yLast;  
    
    public CustomScrollView(Context context) {  
        super(context);  
    }  
  
    public CustomScrollView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public CustomScrollView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
    
    /**
     * 公共接口：ScrollView滚动监听
     */
    public interface OnScrollChangedListener {  
        void onScrollChanged(ScrollView who, int x, int y, int oldx, int oldy);  
    }  
  
    private OnScrollChangedListener mOnScrollChangedListener;  
  
    @Override  
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {  
        super.onScrollChanged(x, y, oldx, oldy);  
        if (mOnScrollChangedListener != null) {  
        	//使用公共接口触发滚动信息的onScrollChanged方法，将滚动位置信息暴露给外�?
            mOnScrollChangedListener.onScrollChanged(this, x, y, oldx, oldy);  
        }  
    }  
  
    @Override  
    public boolean onInterceptTouchEvent(MotionEvent ev) {  
    	switch (ev.getAction()) {  
    	case MotionEvent.ACTION_DOWN:  
    		xDistance = yDistance = 0f;  
    		xLast = ev.getX();  
    		yLast = ev.getY();  
    		break;  
    	case MotionEvent.ACTION_MOVE:  
    		final float curX = ev.getX();  
    		final float curY = ev.getY();  

    		xDistance += Math.abs(curX - xLast);  
    		yDistance += Math.abs(curY - yLast);  
    		xLast = curX;  
    		yLast = curY;  

    		if(xDistance > yDistance){  
    			return false;  
    		}    
    	}  

    	return super.onInterceptTouchEvent(ev);  
    }  
    
    
    
    /**
     * 暴露给外部的方法：设置滚动监�?
     * @param listener
     */
    public void setOnScrollChangedListener(OnScrollChangedListener listener) {  
        mOnScrollChangedListener = listener;  
    }  
}
