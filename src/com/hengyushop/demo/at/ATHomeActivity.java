package com.hengyushop.demo.at;

import com.lelinju.www.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ATHomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.at_home);
	}

}
