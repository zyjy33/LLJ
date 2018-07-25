package com.hengyushop.demo.home;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.example.taobaohead.MyCountdownTimer;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;
import com.lelinju.www.UserLoginActivity;
import com.lglottery.www.http.Util;
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
public class CanTuanFengXiangActivity extends BaseActivity implements OnClickListener{

	private ImageView iv_fanhui;
	private TextView txt_time,tv_tishi;
	EditText ra4;
	String check = "0";
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private ImageButton btn_wechat;
	String title,img_url,data;
	protected static Uri tempUri;
	private IWXAPI api;
	private ImageButton img_btn_tencent,btn_wx_friend,btn_sms;
	String user_id;
	String unionid,fx_cs;
	private long hour = 0;//ʱ����� 
	private long minute = 0; 
	private long second = 0; 
	private long time = 0;//����Ϊ��λ
	private long current_time = 0;
	private long interval = 10*1000;//ÿ�����ӻ���ٵĶ��Ϊ10��
	private MyCount count;//��ʱ����� 
	java.util.Date now;
	java.util.Date date;
	long hourl,min,s,zongxs;
	LinearLayout ll_buju;
	Bitmap thumb;
	public static boolean fanhui_type = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cantuan_fenxiang);
		new Thread(getPicByUrl).start();
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(CanTuanFengXiangActivity.this);
		fanhui_type = true;
//		progress.CreateProgress();
		intren();
		
			String ct_tuanshu = getIntent().getStringExtra("ct_tuanshu");
			System.out.println("ct_tuanshu-------------"+ct_tuanshu);
			tv_tishi.setText(ct_tuanshu);
			
		ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		iv_fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	public void intren() {
		try {
			btn_wechat = (ImageButton) findViewById(R.id.img_btn_wechat);
			btn_wx_friend = (ImageButton) findViewById(R.id.img_btn_wx_friend);
			btn_sms = (ImageButton) findViewById(R.id.img_btn_sms);
			img_btn_tencent = (ImageButton) findViewById(R.id.img_btn_tencent);
//			txt_time = (TextView) findViewById(R.id.tvshowtime); 
			tv_tishi = (TextView) findViewById(R.id.tv_tishi); 
			
			
			// ����
			img_btn_tencent.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();
//					con(19, 1);
				}
			});

			// ΢��
			btn_wechat.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// progress.CreateProgress();
					if (UserLoginActivity.wx_fanhui == false) {
						Intent intent5 = new Intent(CanTuanFengXiangActivity.this,UserLoginActivity.class);
						startActivity(intent5);
					}else {
					finish();
					con(16, 1);
					}
				}
			});
			// ����Ȧ
			btn_wx_friend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (UserLoginActivity.wx_fanhui == false) {
						Intent intent5 = new Intent(CanTuanFengXiangActivity.this,UserLoginActivity.class);
						startActivity(intent5);
					}else {
					finish();
					con(17, 1);
					}
				}
			});
			
			// ����
			btn_sms.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
//					con(18, 0);
				}
			});
			
//		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
//		tv_xiabu = (TextView) findViewById(R.id.tv_xiabu);
//		iv_fanhui.setOnClickListener(this);
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	private void con(final int index, int type) {
		try {
			spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
//			String user_name = spPreferences.getString("user", "");
			String user_id = spPreferences.getString("user_id", "");
			SharedPreferences spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
			SharedPreferences spPreferences = getSharedPreferences("longuserset_ptye", MODE_PRIVATE);
			String ptye = spPreferences.getString("ptye", "");
			String qq = spPreferences.getString("qq", "");
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
			
			String ct_id = getIntent().getStringExtra("ct_id");
			System.out.println("unionid==========" + unionid);
			System.out.println("ct_id==========" + ct_id);
			String company_id = getIntent().getStringExtra("company_id");
			System.out.println("company_id==========" + company_id);
			
			System.out.println("=======JuJingCaiXqActivity.fx_canshu=================="+JuJingCaiXqActivity.fx_canshu);
			System.out.println("=======JuTuanGouXqActivity.fx_canshu================"+JuTuanGouXqActivity.fx_canshu);
			if (!JuJingCaiXqActivity.fx_canshu.equals("")) {
				fx_cs = JuJingCaiXqActivity.fx_canshu;
//				JuJingCaiXqActivity.fx_canshu = "";
			}else {
				fx_cs = JuTuanGouXqActivity.fx_canshu;
//				JuTuanGouXqActivity.fx_canshu = "";
			}
			System.out.println("=======fx_cs================================"+fx_cs);
			
				if (ct_id != null) {
					title = getIntent().getStringExtra("title");
					String subtitle = getIntent().getStringExtra("subtitle");
					
				String data_ct = subtitle + RealmName.REALM_NAME_HTTP+"/"+fx_cs+"/join-"+ct_id+".html?cid="+company_id+"&unionid="+unionid+"&shareid="+user_id+"&from=android";
				System.out.println("����22======================" + data_ct);
//				String zhou = "�������̾۲�Ʒ,�򿪼���������̾۲�Ʒ" + data;
//				title = "���̾���Ʒ����";
//				System.out.println("==========" + zhou);
//				softshareWxChat(data_ct);
				
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_ct);
				}else {
					System.out.println("==========" + 17);
					softshareWxFriend(data_ct);
				}
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
		api = WXAPIFactory.createWXAPI(CanTuanFengXiangActivity.this, Constants.APP_ID,false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		// webpage.webpageUrl = temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
//		msg.title = "�ҷ���һ������,������!";
		msg.title = title;
		msg.description = temp[0];
//		Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.drawable.ysj_logn);
//		msg.thumbData = Util.bmpToByteArray(thumb, true);
		System.out.println("thumb=============="+thumb);
		String img_url = getIntent().getStringExtra("img_url");
		System.out.println("img_url==========" + img_url);
		if (img_url.equals("")) {
		Bitmap thumb = BitmapFactory.decodeResource(CanTuanFengXiangActivity.this.getResources(),R.drawable.llj_fx);
		msg.thumbData = Util.bmpToByteArray(thumb, true);// ��������ͼ
		}else {
		msg.thumbData = bitmap2Bytes(thumb,32);
		}
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);

		System.out.println("΢��ע��" + flag);
		
	}
	
	
	/**
	 * ΢�ŷ�������Ȧ
	 * 
	 * @param text
	 */
	private void softshareWxFriend(String text) {
		String temp[] = text.split("http");
		api = WXAPIFactory.createWXAPI(CanTuanFengXiangActivity.this, Constants.APP_ID, false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
//		msg.title = "�ҷ���һ������,������!";
		msg.title = title;
		msg.description = temp[0];
//		Bitmap thumb = BitmapFactory.decodeResource(CanTuanFengXiangActivity.this.getResources(),
//				R.drawable.ysj_logn);
//		msg.thumbData = Util.bmpToByteArray(thumb, true);
		System.out.println("thumb=============="+thumb);
		String img_url = getIntent().getStringExtra("img_url");
		System.out.println("img_url==========" + img_url);
		if (img_url.equals("")) {
		Bitmap thumb = BitmapFactory.decodeResource(CanTuanFengXiangActivity.this.getResources(),R.drawable.llj_fx);
		msg.thumbData = Util.bmpToByteArray(thumb, true);// ��������ͼ
		}else {
		msg.thumbData = bitmap2Bytes(thumb,32);
		}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		boolean flag = api.sendReq(req);
		System.out.println(flag + "-->" + msg.thumbData);
	}
	
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
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
 


	 Runnable getPicByUrl = new Runnable() {
			@Override
			public void run() {
				try {
//					String img_url2 = "http://183.62.138.31:1010/upload/phone/113875199/20170217164544307.jpg";
					String img_url = getIntent().getStringExtra("img_url");
					System.out.println("img_url==========" + img_url);
					String img_url2 = RealmName.REALM_NAME_HTTP + img_url;
					System.out.println("img_url2=============="+img_url2);
					thumb = GetImgUtil.getImage(img_url2);// BitmapFactory��ͼƬ������
//					Bitmap bitMap_tx = Utils.toRoundBitmap(bmp,null);// ���ʱ���ͼƬ�Ѿ���������Բ�ε���
//					System.out.println("bitMap_tx=============="+bitMap_tx);
					System.out.println("bmp=============="+thumb);
					
				} catch (Exception e) {
					Log.i("ggggg", e.getMessage());
				}
			}
		};
		
	
	/** 
     * �õ����ػ��������ϵ�bitmap url - ������߱���ͼƬ�ľ���·��,����: 
     *  
     * A.����·��: url=&quot;http://blog.foreverlove.us/girl2.png&quot; ; 
     *  
     * B.����·��:url=&quot;file://mnt/sdcard/photo/image.png&quot;; 
     *  
     * C.֧�ֵ�ͼƬ��ʽ ,png, jpg,bmp,gif�ȵ� 
     * 
     *  
     * @param url 
     * @return 
     */  
    public static Bitmap GetLocalOrNetBitmap(String url)  {  
        Bitmap bitmap = null;  
        InputStream in = null;  
        BufferedOutputStream out = null;  
        try  
        {  
        	System.out.println("url==========" + url);
            in = new BufferedInputStream(new URL(url).openStream());  
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();  
            out = new BufferedOutputStream(dataStream);  
//            copy(in, out);  
            out.flush();  
            byte[] data = dataStream.toByteArray();  
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);  
            data = null;  
            return bitmap;  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
            return null;  
        }  
    } 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.iv_fanhui:
			finish();
			break;
		case R.id.ra5:
			break;

		default:
			break;
		}
	}
	
	//ʵ�ּ�ʱ���ܵ��� 
		class MyCount extends MyCountdownTimer { 

			public MyCount(long millisInFuture, long countDownInterval) { 
				super(millisInFuture, countDownInterval); 
			} 
			@Override 
			public void onFinish() { 
				//ý����� 
				txt_time.setText("ʱ�����"); 
			} 
			
			//����ʣ��ʱ�� 
			@Override 
			public void onTick(long millisUntilFinished, int percent) {
				current_time = millisUntilFinished;
				System.out.println("current_time-------------"+current_time);
//				long day= (millisUntilFinished / 1000); 
//				long myhour = (millisUntilFinished / 1000) / 3600; 
//				long myminute = ((millisUntilFinished / 1000) - myhour * 3600) / 60; 
//				long mysecond = millisUntilFinished / 1000 - myhour * 3600 - myminute * 60; 
//				txt_time.setText("ʣ��ʱ��: "+day+":" + myhour + ":" + myminute + ":" + mysecond); 
				
				   long day=current_time/(24*60*60*1000);
				   long hour=(current_time/(60*60*1000)-day*24);
				   long min=((current_time/(60*1000))-day*24*60-hour*60);
				   long s=(current_time/1000-day*24*60*60-hour*60*60-min*60);
				   
				   System.out.println(""+day+"��"+hour+"Сʱ"+min+"��"+s+"��");
//				   txt_time.setText("ʣ��ʱ��: "+day+":" + hour + ":" + min + ":" + s); 
				   txt_time.setText("ʣ��: "+day+"��"+hour+"Сʱ"+min+"��"+s+"��"); 
				   progress.CloseProgress();
			} 
		} 
}