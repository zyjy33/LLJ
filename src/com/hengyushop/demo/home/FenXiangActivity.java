package com.hengyushop.demo.home;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.android.hengyu.web.Webview1;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.ctrip.openapi.java.utils.LogoConfig;
import com.example.uploadpicdemo.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;
import com.lelinju.www.UserLoginActivity;
import com.lglottery.www.http.Util;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * ����
 * 
 * @author Administrator
 * 
 */
public class FenXiangActivity extends BaseActivity implements OnClickListener {

	private ImageView iv_fanhui;
	private TextView tv_xiabu;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private LinearLayout ll_jufen,ll_mdmtg,ll_fzjf,ll_fenxiang;
	String user_name;
	LayoutInflater mLayoutInflater;
	PopupWindow mPopupWindow;
	protected PopupWindow pop;
	private View view;
	private ImageButton btn_wechat;
	private View btn_sms;
	private View btn_wx_friend;
	private ImageButton img_btn_tencent;
	private IWXAPI api;
	String title;
	String unionid;
	Bitmap thumb;
	String data_fx;
	String link_url;
	String img_url = "";
	String mdm_sys = "";
	String erweima = "";
	Bitmap bitmap;// ��ά���м�ͼƬ
	private int iv_halfWidth = 20;// ��ʾ�м�ͼƬ�Ŀ�ȵ�һ��
	Bitmap mBitmap;// ��ά��ͼƬ;
	Bitmap bitmap_tx,bitmap_touxiang;
	String avatar = "";
	String touxiang = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenxiang);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		user_name = spPreferences.getString("user", "");
		progress = new DialogProgress(FenXiangActivity.this);
		avatar = spPreferences.getString("avatar", "");
		System.out.println("avatar============="+avatar);
//		System.out.println("img_url============="+img_url);
//		if (!img_url.equals("")) {
//			new Thread(getPicByUrl).start();
//		}
		
		if (!avatar.equals("null")) {
			new Thread(getPicByUrl2).start();
		}else if (!avatar.equals("")){
			new Thread(getPicByUrl2).start();
		}
		
//		getmianduimian();
		
		getzhou();
		intren();

	}

	public void intren() {
		try {
			ImageView iv_zhuti1 = (ImageView) findViewById(R.id.iv_zhuti1);
			ImageView iv_zhuti2 = (ImageView) findViewById(R.id.iv_zhuti2);
			ImageView iv_zhuti3 = (ImageView) findViewById(R.id.iv_zhuti3);
			ImageView iv_zhuti4 = (ImageView) findViewById(R.id.iv_zhuti4);
			iv_zhuti1.setBackgroundResource(R.drawable.juyou);
			iv_zhuti2.setBackgroundResource(R.drawable.mianduim);
			iv_zhuti3.setBackgroundResource(R.drawable.shejiao);
			iv_zhuti4.setBackgroundResource(R.drawable.jiqiao);
			ll_jufen = (LinearLayout) findViewById(R.id.ll_jufen);
			ll_mdmtg = (LinearLayout) findViewById(R.id.ll_mdmtg);
			ll_fzjf = (LinearLayout) findViewById(R.id.ll_fzjf);
			ll_fenxiang = (LinearLayout) findViewById(R.id.ll_fenxiang);
			iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
			tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
			iv_fanhui.setOnClickListener(this);
			ll_mdmtg.setOnClickListener(this);
			ll_fenxiang.setOnClickListener(this);
			ll_jufen.setOnClickListener(this);
			ll_fzjf.setOnClickListener(this);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private void getzhou() {
		// TODO Auto-generated method stub
		
		SharedPreferences spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
		SharedPreferences longuserset_ptye = getSharedPreferences("longuserset_ptye", MODE_PRIVATE);
		
		String ptye = longuserset_ptye.getString("ptye", "");
		System.out.println("ptye==========" + ptye);
		if (ptye != null) {
			if (ptye.equals("weixin")) {
				unionid = spPreferences_login.getString("unionid", "");
			}else {
				unionid = spPreferences_login.getString("unionid", "");
			}
		}else {
			    unionid = "";
		}
		System.out.println("unionid==========" + unionid);
		
		

	AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_app_version?browser=android", new AsyncHttpResponseHandler(){
		@Override
		public void onSuccess(int arg0, String arg1) {
			// TODO Auto-generated method stub
			super.onSuccess(arg0, arg1);
			try {
				System.out.println("arg1==========" + arg1);
			JSONObject jsonObject = new JSONObject(arg1);
			String status = jsonObject.getString("status");
			String info = jsonObject.getString("info");
			if (status.equals("y")) {
			JSONObject jobt = jsonObject.getJSONObject("data");
			title = jobt.getString("title");
			img_url = jobt.getString("img_url");
//			link_url = jobt.getString("link_url");
			String content = jobt.getString("content");
			String user_id = spPreferences.getString("user_id", "");
			link_url = RealmName.REALM_NAME_HTTP+"/appshare/" + user_id + ".html?unionid="+unionid+"&shareid="+user_id+"&from=android";
			
			String data = RealmName.REALM_NAME_HTTP+"/appshare/" + user_id + ".html?unionid="+unionid+"&shareid="+user_id+"&from=android";
			data_fx = content + data;
			System.out.println("zhou==========" + data_fx);
			new Thread(getPicByUrl).start();
			}else {
				
			}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}, null);
	
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.iv_fanhui:
			finish();
			break;
		case R.id.ll_jufen:
			Intent intent = new Intent(FenXiangActivity.this,MyJuFenActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_mdmtg:
//			Intent intent1 = new Intent(FenXiangActivity.this,MainDuiMianPromoteActivity.class);
////			Intent intent1 = new Intent(FenXiangActivity.this,MainDuiMianllActivity.class);
//			intent1.putExtra("erweima", link_url);
//			intent1.putExtra("mdm_sys", mdm_sys);
//			intent1.putExtra("erweima", "erweima");
//			intent1.putExtra("fx_erweimw", "fx_erweimw");
//			startActivity(intent1);
			getmianduimian(); 
			break;
		case R.id.ll_fenxiang:
			user_name = spPreferences.getString("user", "");
			if (user_name.equals("")) {
				Intent intentll = new Intent(FenXiangActivity.this,UserLoginActivity.class);
				startActivity(intentll);
			} else {
				try {
//					if (UserLoginActivity.wx_fanhui == false) {
//						Intent intent5 = new Intent(FenXiangActivity.this,UserLoginActivity.class);
//						startActivity(intent5);
//					}else {
					SoftWarePopuWindow(ll_mdmtg, FenXiangActivity.this);
//					}

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			break;
		case R.id.ll_fzjf:
			Intent intent4 = new Intent(FenXiangActivity.this,Webview1.class);
			intent4.putExtra("fzjf_id", "10332");
			startActivity(intent4);
			break;
		default:
			break;
		}
	}

	/**
	 * ����
	 * 
	 * @param view2
	 * @param context
	 */
	private void SoftWarePopuWindow(View view2, final Context context) {
		try {
			mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			// inflater = LayoutInflater.from(context);
			view = mLayoutInflater.inflate(R.layout.ware_infromation_share,null);
			pop = new PopupWindow(view, LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT, false);
			final Dialog dlg = new Dialog(context, R.style.delete_pop_style);
			pop.setBackgroundDrawable(new BitmapDrawable());
			pop.setOutsideTouchable(true);
			// pop.setFocusable(true);
			// pop.setTouchable(true); // ����PopupWindow�ɴ���
			//
			if (!pop.isShowing()) {
				pop.showAtLocation(view2, Gravity.BOTTOM, 0, 0);
			}
			btn_wechat = (ImageButton) view.findViewById(R.id.img_btn_wechat);
			btn_wx_friend = (ImageButton) view
					.findViewById(R.id.img_btn_wx_friend);
			btn_sms = (ImageButton) view.findViewById(R.id.img_btn_sms);
			img_btn_tencent = (ImageButton) view
					.findViewById(R.id.img_btn_tencent);
			Button btn_holdr = (Button) findViewById(R.id.btn_holdr);
			btn_holdr.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					pop.dismiss();
				}
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		// ����
		img_btn_tencent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				pop.dismiss();
//				con(19, 1);
			}
		});

		// ΢��
		btn_wechat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				pop.dismiss();
				// progress.CreateProgress();
				con(16, 1);
			}
		});
		// ����Ȧ
		btn_wx_friend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				con(17, 1);
			}
		});
		
		// ����
		btn_sms.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
//				con(18, 0);
			}
		});
	}

	private void con(final int index, int type) {
		try {
			String user_name = spPreferences.getString("user", "");
			String user_id = spPreferences.getString("user_id", "");
			String sp_id = getIntent().getStringExtra("sp_id");
			System.out.println("sp_id==========" + sp_id);
			
			SharedPreferences spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
			SharedPreferences longuserset_ptye = getSharedPreferences("longuserset_ptye", MODE_PRIVATE);
			
			String ptye = longuserset_ptye.getString("ptye", "");
			System.out.println("ptye==========" + ptye);
			System.out.println("user_id==========" + user_id);
			if (ptye != null) {
				if (ptye.equals("weixin")) {
					unionid = spPreferences_login.getString("unionid", "");
				}else {
					unionid = spPreferences_login.getString("unionid", "");
				}
			}else {
				    unionid = "";
			}
			System.out.println("unionid==========" + unionid);
			
			
//				AsyncHttp.get(RealmName.REALM_NAME_LL+"/get_app_version?browser=android", new AsyncHttpResponseHandler(){
//					@Override
//					public void onSuccess(int arg0, String arg1) {
//						// TODO Auto-generated method stub
//						super.onSuccess(arg0, arg1);
//						try {
//						JSONObject jsonObject = new JSONObject(arg1);
//						String status = jsonObject.getString("status");
//						String info = jsonObject.getString("info");
//						if (status.equals("y")) {
//						JSONObject jobt = jsonObject.getJSONObject("data");
//						title = jobt.getString("title");
//						img_url = jobt.getString("img_url");
//						new Thread(getPicByUrl).start();
//						String link_url = jobt.getString("link_url");
//						String content = jobt.getString("content");
//						String user_id = spPreferences.getString("user_id", "");
//						String data = RealmName.REALM_NAME_FX+"/appshare/" + user_id + ".html";
//						String zhou = content + data;
//						System.out.println("zhou==========" + zhou);
////						Toast.makeText(FenXiangActivity.this, zhou, 200).show();
////						softshareWxChat(zhou);
//						
//						if (index == 16) {
//							System.out.println("==========" + 16);
//							softshareWxChat(zhou);
//						}else if (index == 17){
//							System.out.println("==========" + 17);
//							softshareWxFriend(zhou);
//						}
//						
//						}else {
//							
//						}
//						} catch (Exception e) {
//							// TODO: handle exception
//							e.printStackTrace();
//						}
//					}
//				}, null);
				
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_fx);
				}else if (index == 17){
					System.out.println("==========" + 17);
					softshareWxFriend(data_fx);
				}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * ΢�ŷ���
	 * 
	 * @param text
	 */
	private void softshareWxChat(String text) {
		String temp[] = text.split("http");

		api = WXAPIFactory.createWXAPI(FenXiangActivity.this, Constants.APP_ID,false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		// webpage.webpageUrl = temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		// msg.title = "�ҷ���һ�����,������!";
		msg.title = title;
		msg.description = temp[0];
		
		if (img_url.equals("")) {
			Bitmap thumb = BitmapFactory.decodeResource(FenXiangActivity.this.getResources(),R.drawable.llj_app);
//			msg.thumbData = Util.bmpToByteArray(thumb, true);// ��������ͼ
			msg.thumbData = bitmap2Bytes(thumb,32);
		}else {
			msg.thumbData = bitmap2Bytes(thumb,32);
		}
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_logn);
//		msg.thumbData = Util.bmpToByteArray(thumb, true);
//		msg.thumbData = bitmap2Bytes(thumb,32);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);
//		Toast.makeText(FenXiangActivity.this, "״̬", 200).show();
		String zhuangtai = String.valueOf(flag);
//		Toast.makeText(FenXiangActivity.this, zhuangtai, 200).show();
		System.out.println("΢��ע��" + flag);
	}
	
	/**
	 * ΢�ŷ�������Ȧ
	 * 
	 * @param text
	 */
	private void softshareWxFriend(String text) {
		String temp[] = text.split("http");
		api = WXAPIFactory.createWXAPI(FenXiangActivity.this, Constants.APP_ID, false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
//		msg.title = "�ҷ���һ�����,������!";
		// msg.title = "ni"+"�ҷ���һ�����,������!";
		msg.description = temp[0];
		if (img_url.equals("")) {
//			Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.drawable.llj_fx);
//			msg.thumbData = Util.bmpToByteArray(thumb, true);
			Bitmap thumb = BitmapFactory.decodeResource(FenXiangActivity.this.getResources(),R.drawable.llj_app);
//			msg.thumbData = Util.bmpToByteArray(thumb, true);// ��������ͼ
			msg.thumbData = bitmap2Bytes(thumb,32);
		}else {
			msg.thumbData = bitmap2Bytes(thumb,32);
		}
		
//		Bitmap thumb = BitmapFactory.decodeResource(FenXiangActivity.this.getResources(),R.drawable.ysj_logn);
//		msg.thumbData = Util.bmpToByteArray(thumb, true);
//		msg.thumbData = bitmap2Bytes(thumb,32);
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		boolean flag = api.sendReq(req);
		System.out.println(flag + "-->" + msg.thumbData);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
	
	 /**
     * Bitmapת����byte[]���ҽ���ѹ��,ѹ����������maxkb
     * @param bitmap
     * @param IMAGE_SIZE
     * @return
     */
 public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb&& options != 10) {
            output.reset(); //���output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//����ѹ��options%����ѹ��������ݴ�ŵ�output��
            options -= 10;
        }
        return output.toByteArray();
    }
 


 private void getmianduimian() {
		// TODO Auto-generated method stub
		try {
//			bitmap_touxiang = BitUtil.stringtoBitmap(avatar);
//			bitmap_touxiang = GetImgUtil.getImage(avatar);
			System.out.println("bitmap_touxiang============="+bitmap_touxiang);
//			if (avatar.contains("http")) {
//				bitmap_tx = BitmapFactory.decodeResource(getResources(),R.drawable.llj_app);
//			}else
			if (!avatar.equals("null")) {
				if (avatar.contains("http")) {
					bitmap_tx = BitmapFactory.decodeResource(getResources(),R.drawable.llj_app);
				}else{
				bitmap_tx = bitmap_touxiang;
			    }
			}else {
				bitmap_tx = BitmapFactory.decodeResource(getResources(),R.drawable.llj_app);
			}
			
			System.out.println("bitmap_tx============="+bitmap_tx);
			LogoConfig logoConfig = new LogoConfig();
			bitmap = logoConfig.modifyLogo(BitmapFactory.decodeResource(getResources(),R.drawable.white_bg),bitmap_tx);
			// ����ͼƬ���õ�����ȥ��
			Matrix matrix = new Matrix();
			float sx = (float) 2 * iv_halfWidth / bitmap.getWidth();
			float sy = (float) 2 * iv_halfWidth / bitmap.getHeight();
			matrix.setScale(sx, sy);
			System.out.println("bitmap============="+bitmap);
			// �������ź��ͼƬ
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
					bitmap.getHeight(), matrix, false);

			String content = link_url;
			System.out.println("content======�����ַ======="+content);
			try {
				mBitmap = createBitmap(new String(content.getBytes(), "ISO-8859-1"));
				erweima = BitUtil.bitmaptoString(mBitmap);
				System.out.println("mBitmap============="+mBitmap);
				System.out.println("erweima======����======="+erweima);
				
				Intent intent1 = new Intent(FenXiangActivity.this,MainDuiMianPromoteActivity.class);
				intent1.putExtra("mdm_sys", mdm_sys);
				intent1.putExtra("erweima", erweima);
				startActivity(intent1);
				
//				Intent intent1 = new Intent(FenXiangActivity.this, MianDuiMianFxhbActivity.class);
//				intent1.putExtra("erweima", erweima);
//				intent1.putExtra("touxiang", touxiang);
//				startActivity(intent1);
				
			} catch (WriterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
 
 
	 Runnable getPicByUrl = new Runnable() {
			@Override
			public void run() {
				try {
//					String img_url2 = "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
//					String img_url = getIntent().getStringExtra("img_url");
//					System.out.println("img_url==========" + img_url);
					String img_url2 = RealmName.REALM_NAME_HTTP + img_url;
					System.out.println("img_url2=============="+img_url2);
					thumb = GetImgUtil.getImage(img_url2);// BitmapFactory��ͼƬ������
//					Bitmap bitMap_tx = Utils.toRoundBitmap(bmp,null);// ���ʱ���ͼƬ�Ѿ��������Բ�ε���
//					System.out.println("bitMap_tx=============="+bitMap_tx);
					System.out.println("bmp=============="+thumb);
				} catch (Exception e) {
					Log.i("ggggg", e.getMessage());
				}
			}
		};
		
		  Runnable getPicByUrl2 = new Runnable() {
				@Override
				public void run() {
					try {
						String img_url2 = RealmName.REALM_NAME_HTTP +avatar;
						System.out.println("img_url2=============="+img_url2);
						bitmap_touxiang = GetImgUtil.getImage(img_url2);// BitmapFactory��ͼƬ������
//						Bitmap	bitMap_tx = Utils.toRoundBitmap(bitmap_touxiang,null);// ���ʱ���ͼƬ�Ѿ��������Բ�ε���
//						touxiang = BitUtil.bitmaptoString(bitMap_tx);
						System.out.println("bitmap_touxiang=============="+bitmap_touxiang);
					} catch (Exception e) {
						Log.i("ggggg", e.getMessage());
					}
				}
			};
			
			/**
			 * �����ַ������ɶ�ά��
			 * 
			 * @param s
			 * @return
			 * @throws WriterException
			 */
			private Bitmap createBitmap(String str) throws WriterException {
				// ���ɶ�Ϊ���󣬱�����ָ����С����Ҫ������ͼƬ�ڽ������ţ������ᵼ��ģ��ʶ��ʧ�ܣ�����ɨ��ʧ���ˡ�
				BitMatrix mBitMatrix = new MultiFormatWriter().encode(str,
						BarcodeFormat.QR_CODE, 300, 300);// BarcodeFormat.QR_CODE-�����ʽ
				// ��ά����Ŀ��
				int w = mBitMatrix.getWidth();
				int h = mBitMatrix.getHeight();

				// ͷ��Ŀ��
				int halfw = w / 2;
				int halfh = h / 2;
				// ׼������ά�룬�Ѷ�ά����ת��Ϊһά���飬һֱ���Ż�
				int[] pixels = new int[w * h];// ���鳤�Ⱦ��Ǿ�������ֵ
				for (int y = 0; y < h; y++) {
					int outputOffset = y * w;
					for (int x = 0; x < w; x++) {
						// ��һ����ͨ�Ķ�ά��
						// if (mBitMatrix.get(x, y)) {// ��ʾ��ά������ֵ����Ӧ��һ���ڵ�
						// pixels[outputOffset + x] = 0xff000000;
						// } else {
						// pixels[outputOffset + x] = 0xffffffff;
						// }

						// ��һ����ͼƬ�Ķ�ά��ͼƬ
						if (x > (halfw - iv_halfWidth) && x < (halfw + iv_halfWidth)
								&& y > (halfh - iv_halfWidth)
								&& y < (halfh + iv_halfWidth)) {// �м�ͼƬ������
							pixels[outputOffset + x] = bitmap.getPixel(x - halfw
									+ iv_halfWidth, y - halfh + iv_halfWidth);// ���ﻭͼ֮�������Ե���ʾ����
						} else {
							if (mBitMatrix.get(x, y)) {// ��ʾ��ά������ֵ����Ӧ��һ���ڵ�
								pixels[outputOffset + x] = 0xff000000;
							} else {
								pixels[outputOffset + x] = 0xffffffff;
							}
						}
					}
				}
				Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);// ����һ��300*300bitmap
				bitmap.setPixels(pixels, 0, w, 0, 0, w, h);// ���ص㡢��ʼ�㡢��ȡ�����ʼ���ء�����
				return bitmap;

			}

}
