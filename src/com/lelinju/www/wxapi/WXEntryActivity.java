package com.lelinju.www.wxapi;

import com.android.hengyu.web.Constants;
import com.hengyushop.demo.home.MianDuiMianFxhbActivity;
import com.lelinju.www.UserLoginActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler{
	private IWXAPI api;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UserLoginActivity.mWxApi.handleIntent(getIntent(), this);
	    api = WXAPIFactory.createWXAPI(WXEntryActivity.this, Constants.APP_ID,false);
		api.registerApp(Constants.APP_ID);
	}

	@Override
	public void onReq(BaseReq arg0) {
		
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	    super.onNewIntent(intent);
	 
	    setIntent(intent);
	    api.handleIntent(intent, this);
	}
	
	@Override
	public void onResp(BaseResp resp) {
//		Toast.makeText(this, "调用onResp", 1).show();
		System.out.println("------------------"+UserLoginActivity.isWXLogin);
		switch(resp.errCode) {
		
			case BaseResp.ErrCode.ERR_OK:
				System.out.println("0------------------"+UserLoginActivity.isWXLogin);
				if(UserLoginActivity.isWXLogin){
					SendAuth.Resp sendResp = (SendAuth.Resp) resp;
					System.out.println("1------------------"+sendResp.code);
					UserLoginActivity.WX_CODE = sendResp.code;
//					Toast.makeText(this, "用户同意", Toast.LENGTH_LONG).show();
					finish();
				}else{
//					Toast.makeText(this, "用户同意", Toast.LENGTH_LONG).show();
				}
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
//				Toast.makeText(this, "用户取消", Toast.LENGTH_LONG).show();
				UserLoginActivity.isWXLogin=false;
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				Toast.makeText(this, "用户拒绝授权", Toast.LENGTH_LONG).show();
				UserLoginActivity.isWXLogin=false;
				break;
			default:
//				Toast.makeText(this, "4", Toast.LENGTH_LONG).show();
				break;
		}
		
		finish();
	}
	
}
