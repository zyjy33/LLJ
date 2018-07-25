package com.hengyushop.demo.home;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.Constants;
import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.hengyushop.demo.at.AsyncHttp;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;
import com.lglottery.www.http.Util;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * 分享
 * 
 * @author Administrator
 * 
 */
public class DBFengXiangActivity extends BaseActivity implements OnClickListener{

	private ImageView iv_fanhui;
	private TextView tv_xiabu;
	EditText ra4;
	String check = "0";
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private ImageButton btn_wechat;
	String title,img_url,data;
	protected static Uri tempUri;
	private IWXAPI api;
//	private View btn_sms;
//	private View btn_wx_friend;
	public static boolean fanhui_type = false;
	private ImageButton img_btn_tencent,btn_wx_friend,btn_sms;
	String user_id; 
	String unionid;
	Bitmap thumb;
	String subtitle = "";
	String fx_shuzi;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fenxiang_time);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(DBFengXiangActivity.this);
		fanhui_type = true;
//		if (JuTuanGouXq2Activity.fanhui_type == true) {
//			fanhui_type = true;
//		}
		new Thread(getPicByUrl).start();
		intren();
	}
	
	public void intren() {
		try {
			btn_wechat = (ImageButton) findViewById(R.id.img_btn_wechat);
			btn_wx_friend = (ImageButton) findViewById(R.id.img_btn_wx_friend);
			btn_sms = (ImageButton) findViewById(R.id.img_btn_sms);
			img_btn_tencent = (ImageButton) findViewById(R.id.img_btn_tencent);
			Button btn_holdr = (Button) findViewById(R.id.btn_holdr);
			btn_holdr.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					finish();
				}
			});	
			
			// 新浪
			img_btn_tencent.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();
//					con(19, 1);
				}
			});

			// 微信
			btn_wechat.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// progress.CreateProgress();
					finish();
					con(16, 1);
				}
			});
			// 朋友圈
			btn_wx_friend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
					con(17, 1);
				}
			});
			
			// 短信
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
			String user_name = spPreferences.getString("user", "");
			String user_id = spPreferences.getString("user_id", "");
			System.out.println("user_id==========" + user_id);
			SharedPreferences spPreferences_login = getSharedPreferences("longuserset_login", MODE_PRIVATE);
//			SharedPreferences spPreferences_tishi = getSharedPreferences("longuserset_tishi", MODE_PRIVATE);
			SharedPreferences spPreferences = getSharedPreferences("longuserset_ptye", MODE_PRIVATE);
			String ptye = spPreferences.getString("ptye", "");
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
			
			String sp_id = getIntent().getStringExtra("sp_id");//商品分享
			String pt_id = getIntent().getStringExtra("pt_id");//拼团分享
			String ct_id = getIntent().getStringExtra("ct_id");//参团分享
			String list_id = getIntent().getStringExtra("list_id");//新闻分享
//			String img_url = getIntent().getStringExtra("img_url");
			System.out.println("unionid==========" + unionid);
			System.out.println("sp_id==========" + sp_id);
			System.out.println("pt_id==========" + pt_id);
			System.out.println("ct_id==========" + ct_id);
			System.out.println("list_id==========" + list_id);
//			System.out.println("img_url==========" + img_url);
			
			fx_shuzi = getIntent().getStringExtra("fx_shuzi");//
			System.out.println("fx_shuzi============================" + fx_shuzi);
			
			String company_id = getIntent().getStringExtra("company_id");
			System.out.println("company_id==========" + company_id);
			title = getIntent().getStringExtra("title");
			subtitle = getIntent().getStringExtra("subtitle");
			System.out.println("title==========" + title);
			System.out.println("subtitle==========" + subtitle);
			if (sp_id != null) {//商品分享
//				title = getIntent().getStringExtra("title");
//				String subtitle = getIntent().getStringExtra("subtitle");
				String data_sp = subtitle + RealmName.REALM_NAME_HTTP+"/goods/show-" + sp_id + ".html?cid="+company_id+"&unionid="+unionid+"&shareid="+user_id+"&from=android";
				
				System.out.println("分享11======================" + data_sp);
//				softshareWxChat(data_sp);
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_sp);
				}else {
					System.out.println("==========" + 17);
					softshareWxFriend(data_sp);
				}
			}else if (pt_id != null) {//拼团分享
				String data_pt = subtitle + RealmName.REALM_NAME_HTTP+"/"+fx_shuzi+"/show-"+pt_id+".html?cid="+company_id+"&unionid="+unionid+"&shareid="+user_id+"&from=android";
				System.out.println("分享22======================" + data_pt);
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_pt);
				}else {
					System.out.println("==========" + 17);
					softshareWxFriend(data_pt);
				}
			}else if (ct_id != null) {//参团分享
				String data_ct = subtitle + RealmName.REALM_NAME_HTTP+"/"+fx_shuzi+"/join-"+ct_id+".html?cid="+company_id+"&unionid="+unionid+"&shareid="+user_id+"&from=android";
				System.out.println("分享33======================" + data_ct);
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_ct);
				}else {
					System.out.println("==========" + 17);
					softshareWxFriend(data_ct);
				}
			}else if (list_id != null) {//新闻分享
				String data_ct = subtitle + RealmName.REALM_NAME_HTTP + "/mobile/news/conent-"+list_id+".html?unionid="+unionid+"&shareid="+user_id+"&from=android";
				System.out.println("分享44======================" + data_ct);
				if (index == 16) {
					System.out.println("==========" + 16);
					softshareWxChat(data_ct);
				}else {
					System.out.println("==========" + 17);
					softshareWxFriend(data_ct);
				}
			}
			//http://183.62.138.31:1010/mobile/news/conent-103.html?cid=0&unionid=&openid=&shareid=7260&from=android
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	
	/**
	 * 微信分享
	 * 
	 * @param text
	 */
	private void softshareWxChat(String text) {
         
//		    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ysj_haibao1);//用这个bmp就可以跳出分享界面
//		    WXImageObject imgObj = new WXImageObject(bmp);
//
//		    WXMediaMessage msg = new WXMediaMessage();
//		    msg.mediaObject = imgObj;
//
//		    Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
//		    bmp.recycle();
//		    msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置缩略图
		    
		String temp[] = text.split("http");
		api = WXAPIFactory.createWXAPI(DBFengXiangActivity.this, Constants.APP_ID,false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		Log.e("zyjy","appId="+Constants.APP_ID+" webpage.webpageUrl="+webpage.webpageUrl);
		
		
		WXMediaMessage msg = new WXMediaMessage(webpage);
//		msg.title = "我发你一个软件,看看呗!";
		msg.title = title;
		msg.description = temp[0];//Bitmap
		System.out.println("thumb=============="+thumb);
		String img_url = getIntent().getStringExtra("img_url");
		System.out.println("img_url==========" + img_url);
		if (img_url.equals("")) {
		Bitmap thumb = BitmapFactory.decodeResource(DBFengXiangActivity.this.getResources(),R.drawable.llj_fx);
		msg.thumbData = Util.bmpToByteArray(thumb, true);// 设置缩略图
//		msg.thumbData = bitmap2Bytes(thumb,32);
		Log.e("zyjy","true");
		}else {
		msg.thumbData = bitmap2Bytes(thumb,32);
		Log.e("zyjy","false");	}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		boolean flag = api.sendReq(req);

		System.out.println("微信注册" + flag);
		
	}
	
		
	/**
	 * 微信分享朋友圈
	 * 
	 * @param text
	 */
	private void softshareWxFriend(String text) {
		String temp[] = text.split("http");
		api = WXAPIFactory.createWXAPI(DBFengXiangActivity.this, Constants.APP_ID, false);
		api.registerApp(Constants.APP_ID);
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = "http" + temp[1];
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = title;
//		msg.title = "我发你一个软件,看看呗!";
		msg.description = temp[0];
		System.out.println("thumb=============="+thumb);
		String img_url = getIntent().getStringExtra("img_url");
		System.out.println("img_url==========" + img_url);
		if (img_url.equals("")) {
		Bitmap thumb = BitmapFactory.decodeResource(DBFengXiangActivity.this.getResources(),R.drawable.llj_fx);
		msg.thumbData = Util.bmpToByteArray(thumb, true);// 设置缩略图
//		msg.thumbData = bitmap2Bytes(thumb,32);
		}else {
		msg.thumbData = bitmap2Bytes(thumb,32);
		}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneTimeline;
		boolean flag = api.sendReq(req);
		System.out.println(flag + "-->" + msg.thumbData);
		System.out.println("微信" + flag);
	}
	
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
	
	
	 /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     * @param bitmap
     * @param IMAGE_SIZE
     * @return
     */
 public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb&& options != 10) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
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
					thumb = GetImgUtil.getImage(img_url2);// BitmapFactory：图片工厂！
//					Bitmap bitMap_tx = Utils.toRoundBitmap(bmp,null);// 这个时候的图片已经被处理成圆形的了
//					System.out.println("bitMap_tx=============="+bitMap_tx);
					System.out.println("bmp=============="+thumb);
					
				} catch (Exception e) {
					Log.i("ggggg", e.getMessage());
				}
			}
		};
	
	public static Bitmap GetLocalOrNetBitmap(String url)
	{
	    Bitmap bitmap = null;
	    InputStream in = null;
	    BufferedOutputStream out = null;
	    try
	    {
	        in = new BufferedInputStream(new URL(url).openStream(), 1024);
	        final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	        out = new BufferedOutputStream(dataStream, 1024);
	        copy(in, out);
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
	private static void copy(InputStream in, OutputStream out)
	        throws IOException {
	    byte[] b = new byte[1024];
	    int read;
	    while ((read = in.read(b)) != -1) {
	        out.write(b, 0, read);
	    }
	}
	
	/** 
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如: 
     *  
     * A.网络路径: url=&quot;http://blog.foreverlove.us/girl2.png&quot; ; 
     *  
     * B.本地路径:url=&quot;file://mnt/sdcard/photo/image.png&quot;; 
     *  
     * C.支持的图片格式 ,png, jpg,bmp,gif等等 
     * 
     *  
     * @param url 
     * @return 
     */  
//    public static Bitmap GetLocalOrNetBitmap(String url)  {  
//        Bitmap bitmap = null;  
//        InputStream in = null;  
//        BufferedOutputStream out = null;  
//        try  
//        {  
//        	System.out.println("url==========" + url);
//            in = new BufferedInputStream(new URL(url).openStream());  
//            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();  
//            out = new BufferedOutputStream(dataStream);  
////            copy(in, out);  
//            out.flush();  
//            byte[] data = dataStream.toByteArray();  
//            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);  
//            data = null;  
//            return bitmap;  
//        }  
//        catch (IOException e)  
//        {  
//            e.printStackTrace();  
//            return null;  
//        }  
//    } 
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
}
