package com.hengyushop.demo.at;

import com.nostra13.universalimageloader.core.ImageLoader;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.res.Configuration;
import android.os.Bundle;

/**
 * ��װActivity
 * */
public abstract class BaseActivity extends ActivityGroup {
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	/**
	 * ��д���෽����������ת�������¿�ʼactivity��������
	 * */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * 4.0��ʱ�򻻻���
		 */
		// ���Activity����ջ
		AppManager.getAppManager().addActivity(this);
		init();

	}

	/**
	 * 
	 */
	private void init() {

	}
	/**
	 * ����ȫ��
	 */

	/**
	 * ����
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		// getWindow().getDecorView().setSystemUiVisibility(View.GONE);
		/**
		 * ����Ϊ����
		 */

		super.onResume();
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ����Activity&�Ӷ�ջ���Ƴ�
	}

}
