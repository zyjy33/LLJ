package com.lelinju.www;

import java.util.ArrayList;

import org.kobjects.util.Util;

import com.hengyushop.airplane.adapter.ViewPagerAdapter;
import com.lelinju.www.R;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * 
 * @author weiping
 * @Package com.zhangshang
 * @Description: 引导??
 * @date 2014-9-25 下午03:26:37
 * @version V1.0
 */
public class Guide2Activity extends Activity implements OnClickListener,
		OnPageChangeListener {
	LayoutInflater layoutInflater;
	private Button bv_experience;
	// 定义ViewPager对象
	boolean bool = false;
	private ViewPager viewPager;

	// 定义ViewPager适配??
	private ViewPagerAdapter vpAdapter;

	// 定义??个ArrayList来存放View
	private ArrayList<View> views;

	// 引导图片资源
	private static final int[] pics = { R.drawable.llj_ydy1, R.drawable.llj_ydy2,R.drawable.llj_ydy3,R.drawable.llj_ydy4};

	// 底部小点的图??
	private ImageView[] points;

	// 记录当前选中位置
	private int currentIndex;
	public SharedPreferences mSp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_guide);

		initView();

		initData();
	}

	/**
	 * 初始化组??
	 */
	private void initView() {
		// 实例化ArrayList对象
		views = new ArrayList<View>();

		// 实例化ViewPager
		viewPager = (ViewPager) findViewById(R.id.viewpager);

		// 实例化ViewPager适配??
		vpAdapter = new ViewPagerAdapter(views);
	}

	/**
	 * 初始化数??
	 */
	private void initData() {
		layoutInflater = LayoutInflater.from(this);
		// 初始化引导图片列??
		for (int i = 0; i < pics.length; i++) {
			View view = layoutInflater.inflate(R.layout.item_guide_image, null);
			ImageView iv = (ImageView) view.findViewById(R.id.iv_image);
//			TextView tv = (TextView) view.findViewById(R.id.tv_title);
			bv_experience = (Button) view.findViewById(R.id.bv_experience);
			iv.setImageResource(pics[i]);
			// tv.setText(text[i]);
			views.add(view);
		}

		bv_experience.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getClasss();
			}
		});
		// 设置数据
		viewPager.setAdapter(vpAdapter);
		// 设置监听
		viewPager.setOnPageChangeListener(this);

		// 初始化底部小??
		initPoint();
	}

	public void getClasss() {
		Intent intent = new Intent(Guide2Activity.this,MainFragmentActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 初始化底部小??
	 */
	private void initPoint() {
		LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);

		points = new ImageView[pics.length];

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			// 得到??个LinearLayout下面的每??个子元素
			points[i] = (ImageView) linearLayout.getChildAt(i);
			// 默认都设为灰??
			points[i].setEnabled(true);
			// 给每个小点设置监??
			points[i].setOnClickListener(this);
			// 设置位置tag，方便取出与当前位置对应
			points[i].setTag(i);
		}

		// 设置当面默认的位??
		currentIndex = 0;
		// 设置为白色，即?中状??
		points[currentIndex].setEnabled(false);
	}

	/**
	 * 当滑动状态改变时调用
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	/**
	 * 当当前页面被滑动时调??
	 */

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/**
	 * 当新的页面被选中时调??
	 */

	@Override
	public void onPageSelected(int position) {
		// 设置底部小点选中状??

		if (3 == position) {
			bv_experience.setVisibility(View.VISIBLE);
		} else {
			bv_experience.setVisibility(View.GONE);
		}
		setCurDot(position);
	}

	/**
	 * 通过点击事件来切换当前的页面
	 */
	@Override
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		setCurView(position);
		setCurDot(position);
	}

	/**
	 * 设置当前页面的位??
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}
		viewPager.setCurrentItem(position);
	}

	/**
	 * 设置当前的小点的位置
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}
		points[positon].setEnabled(false);
		points[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

}
