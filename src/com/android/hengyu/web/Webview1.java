package com.android.hengyu.web;

import com.hengyushop.demo.at.AppManager;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Webview1 extends BaseActivity {
	private WebView webview;
	private TextView tv_title;
	private LinearLayout common_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview1);
		try {
			
		webview = (WebView) findViewById(R.id.webview);
		tv_title = (TextView) findViewById(R.id.tv_title);
		
		common_back = (LinearLayout) findViewById(R.id.common_back);
		common_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AppManager.getAppManager().finishActivity();
			}
		});
		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new JavascriptHandler(), "handler");
		
		try {
			//首页广告链接
			String link_url = getIntent().getStringExtra("link_url");
			if (link_url != null) {
			        webview.loadUrl(link_url);
			}
			
			//统一网页id
			String web_id = getIntent().getStringExtra("web_id");
			if (web_id != null) {
					webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+web_id+".html");
			}
			
			
			//聚云商帮助web网页
			
			String jysbz_id = getIntent().getStringExtra("jysbz_id");
			if (jysbz_id != null) {
					webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+jysbz_id+".html");
			}
			
			
			//发展聚粉技巧web网页
			String fzjf_id = getIntent().getStringExtra("fzjf_id");
			if (fzjf_id != null) {
					webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+fzjf_id+".html");
			}
			
			//注册协议web网页
			String zhuce_id = getIntent().getStringExtra("zhuce_id");
			if (zhuce_id != null) {
					webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+zhuce_id+".html");
			}
			
			//创客协议web网页
			String chuanke_id = getIntent().getStringExtra("chuanke_id");
			if (chuanke_id != null) {
					webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+chuanke_id+".html");
			}
			
			//用户协议web网页
			String userxy_id = getIntent().getStringExtra("userxy");
			if (userxy_id != null) {
					webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+userxy_id+".html");
			}
			
			
			//聚聚发业务说明web网页
			String jjf_id = getIntent().getStringExtra("jjf_id");
			if (jjf_id != null) {
					webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+jjf_id+".html");
			}
			
			//养老银行的魅力web网页
			String ylyh_id = getIntent().getStringExtra("ylyh_id");
			if (ylyh_id != null) {
					webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+ylyh_id+".html");
			}
			
			//首页广告web网页
			String gg_id = getIntent().getStringExtra("gg_id");
			if (gg_id != null) {
//				int sygg_id = Integer.parseInt(gg_id);
//				if (sygg_id > 0) {
//					webview.loadUrl("http://183.62.138.31:1020/mobile/news/show-"+sygg_id+".html");
//				}
			}
			
		//聚头条web网页
		String list_id = getIntent().getStringExtra("list");
		System.out.println("=================list_id="+list_id);
		if (list_id != null) {
				webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+list_id+".html");
		}
		
		//新手攻略web网页
		String xsgl = getIntent().getStringExtra("list_xsgy");
		System.out.println("=================xsgl="+xsgl);
		if (xsgl != null) {
				webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/news/conent-"+xsgl+".html");
		}
		
		//商品详情web网页
		String spjs = getIntent().getStringExtra("spjs");
		System.out.println("=============="+spjs);
		if (spjs != null) {
			webview.loadUrl(RealmName.REALM_NAME_WEB+"/mobile/goods/conent-"+spjs+".html");
		}
				
		
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				tv_title.setText(view.getTitle());
			}
		});
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	class JavascriptHandler {
		@JavascriptInterface
		public void getContent(String htmlContent) {
		}
	}

	public void onPageFinished(WebView view, String url) {
		view.loadUrl("javascript:window.handler.getContent(document.body.innerHTML);");
	}
}
