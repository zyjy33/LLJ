package com.hengyushop.demo.movie;

import com.lelinju.www.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MovieDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_detail);
	}

}
