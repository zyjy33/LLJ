package com.hengyushop.demo.home;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.hengyu.web.DialogProgress;
import com.android.hengyu.web.RealmName;
import com.ctrip.openapi.java.utils.BitUtil;
import com.ctrip.openapi.java.utils.EncodingHandler;
import com.ctrip.openapi.java.utils.GetImgUtil;
import com.ctrip.openapi.java.utils.LogoConfig;
import com.example.uploadpicdemo.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hengyushop.demo.at.BaseActivity;
import com.lelinju.www.R;

/**
 * ������ƹ�
 * 
 * ���̾�
 * @author Administrator
 * 
 */
public class MainDuiMianllActivity extends BaseActivity implements OnClickListener{

	private ImageView iv_fanhui,im_shanchu,mImageView1,mImageView2;
	private DialogProgress progress;
	private SharedPreferences spPreferences;
	private EditText mEditText;
	private Button btn_data;
	private LinearLayout ll_mdm_sys,ll_fx_zshb;
	private List<String> list;
	private ListView lv;
	private TextView tv_geshu,tv_shanchu;
	TestAdapter adapter;
	RelativeLayout rl_tupian;
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
		setContentView(R.layout.activity_mianduimian);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
		progress = new DialogProgress(MainDuiMianllActivity.this);
		intren();
	}
	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		TextView tv_geshu = (TextView) findViewById(R.id.tv_geshu);
//		if (list.size() > 0) {
//			tv_geshu.setText(list.size());
//		}
//	}
	
	public void intren() {
		try {
			
			avatar = spPreferences.getString("avatar", "");
			System.out.println("avatar============="+avatar);
			if (!avatar.equals("")) {
				new Thread(getPicByUrl).start();
			}
			
			
			Bitmap bitmap_dihua = BitmapFactory.decodeResource(getResources(), R.drawable.dihua);
			//bitmap = Bitmap.createBitmap(100, 20, Config.ARGB_8888);
			BitmapDrawable drawable = new BitmapDrawable(bitmap_dihua);
//			drawable.setTileModeXY(TileMode.REPEAT , TileMode.REPEAT );
			drawable.setTileModeX(TileMode.REPEAT);
			drawable.setDither(true);
			View view = findViewById(R.id.iv_dihua);
			view.setBackgroundDrawable(drawable);
			
//		mImageView = (ImageView) findViewById(R.id.iv_qr_image);
//		mImageView1 = (ImageView) findViewById(R.id.iv_qr_image1);
//		mImageView2 = (ImageView) findViewById(R.id.iv_qr_image2);
		iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
		im_shanchu = (ImageView) findViewById(R.id.im_shanchu);
		mEditText = (EditText) findViewById(R.id.et_haoma);
		btn_data = (Button) findViewById(R.id.btn_data);
		tv_geshu = (TextView) findViewById(R.id.tv_geshu);
		tv_shanchu = (TextView) findViewById(R.id.tv_shanchu);
//		
//		mImageView1.setBackgroundResource(R.drawable.rwm);
//		mImageView2.setBackgroundResource(R.drawable.rwmhs);
		ll_mdm_sys = (LinearLayout) findViewById(R.id.ll_mdm_sys);
		ll_fx_zshb = (LinearLayout) findViewById(R.id.ll_fx_zshb);
		ll_mdm_sys.setOnClickListener(this);
		ll_fx_zshb.setOnClickListener(this);
		iv_fanhui.setOnClickListener(this);
		btn_data.setOnClickListener(this);
		im_shanchu.setOnClickListener(this);
		tv_shanchu.setOnClickListener(this);
		list = new ArrayList<String>();
//		list.add("����С��");
//		list.add("����С��");
//		list.add("����С��");
		lv = (ListView) findViewById(R.id.myList);
		adapter = new TestAdapter(list, this);
		lv.setAdapter(adapter);
//		setListViewHeightBasedOnChildren(lv);   
		
		btn_data.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String haoma = mEditText.getText().toString().trim();
				//http://183.62.138.31:1010/appinvite/109/13117711520_13117711521.html
				//https://www.pgyer.com/ZnX8
				
				if (TextUtils.isEmpty(haoma)) {
					Toast.makeText(MainDuiMianllActivity.this, "�������ֻ���", 200).show();
				}else if (haoma.length() < 11 ) {
					Toast.makeText(MainDuiMianllActivity.this, "�ֻ�������11λ", 200).show();
				}else {	
					
					Bitmap _Bitmap;
					try {
						
						String id = spPreferences.getString("user_id", "");
						
						System.out.println("============="+haoma);
						list.add(haoma); //��������� �ı�List����
						adapter.list = list;  //���ı���List���ϸ���Adapter�еļ���
						
				        StringBuffer str = new StringBuffer();
				        for(String s:list){
				        	str.append(s+"_");
				        }
				        
				        str.delete(str.lastIndexOf("_"),str.length()); 
				        
				        System.out.println("22---------------"+str);
				        
						String str1 = "http://183.62.138.31:1010/appinvite/"+id+"/"+str+".html";
						
						System.out.println("str============="+str1);
						
//						mImageView1.setVisibility(View.GONE);
//						mImageView2.setVisibility(View.GONE);
//						mImageView.setVisibility(View.VISIBLE);
						
//						_Bitmap = EncodingHandler.createQRCode(str1, 350);
//						erweima = BitUtil.bitmaptoString(_Bitmap);
//						System.out.println("id111============="+id);
//						mImageView.setImageBitmap(_Bitmap);
						
//						avatar = spPreferences.getString("avatar", "");
//						new Thread(getPicByUrl).start();
						System.out.println("avatar============="+avatar);
						System.out.println("bitmap_tx1============="+bitmap_tx);
						if (!avatar.equals("")) {
//							new Thread(getPicByUrl).start();
							bitmap_tx = bitmap_touxiang;
						}else {
							bitmap_tx =  BitmapFactory.decodeResource(getResources(),R.drawable.ysj_tx);
						}
						
						System.out.println("bitmap_tx============="+bitmap_tx);
						LogoConfig logoConfig = new LogoConfig();
						bitmap = logoConfig.modifyLogo(BitmapFactory.decodeResource(getResources(),R.drawable.white_bg),bitmap_tx);
						// ����ͼƬ���õ�����ȥ��
						Matrix matrix = new Matrix();
						float sx = (float) 2 * iv_halfWidth / bitmap.getWidth();
						float sy = (float) 2 * iv_halfWidth / bitmap.getHeight();
						matrix.setScale(sx, sy);
						// �������ź��ͼƬ
						bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
								bitmap.getHeight(), matrix, false);

//						String content = "http://183.62.138.31:1010/appinvite/"+7028+"/"+00+".html";
						String content = "http://183.62.138.31:1010/appinvite/"+id+"/"+str+".html";
						System.out.println("content============="+content);
						try {
							mBitmap = createBitmap(new String(content.getBytes(), "ISO-8859-1"));
							erweima = BitUtil.bitmaptoString(mBitmap);
							System.out.println("erweima============="+erweima);
						} catch (WriterException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
//						zhou();
							if (list.size() > 0) {
								int gs = list.size();
								String geshu = String.valueOf(gs);
								tv_geshu.setText(geshu);
//								btn_data.setText("+�������");
								im_shanchu.setVisibility(View.VISIBLE);
								tv_shanchu.setVisibility(View.VISIBLE);
							}
							
						mEditText.setText("");
//						setListViewHeightBasedOnChildren(lv);
						adapter.notifyDataSetChanged(); //����notifyDataSetChanged����
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
						
//					} catch (WriterException e) {
////						showToast("�쳣");
//						e.printStackTrace();
//						Toast.makeText(MainDuiMianActivity.this, "�쳣", 200).show();
//					}
						
				}

			}
		});
		
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
					String img_url2 = RealmName.REALM_NAME_HTTP +avatar;
					System.out.println("img_url2=============="+img_url2);
					
					bitmap_touxiang = GetImgUtil.getImage(img_url2);// BitmapFactory��ͼƬ������
					Bitmap	bitMap_tx = Utils.toRoundBitmap(bitmap_touxiang,null);// ���ʱ���ͼƬ�Ѿ��������Բ�ε���
					touxiang = BitUtil.bitmaptoString(bitMap_tx);
					System.out.println("touxiang=============="+touxiang);
					System.out.println("bitmap_touxiang=============="+bitmap_touxiang);
//					bitmap_tx = bitmap_touxiang;
				} catch (Exception e) {
					Log.i("ggggg", e.getMessage());
				}
			}
		};

	private void zhou() {
		// TODO Auto-generated method stub
		try {
			
		TextView tv_geshu = (TextView) findViewById(R.id.tv_geshu);
		if (list.size() > 0) {
			int gs = list.size();
			String geshu = String.valueOf(gs);
			tv_geshu.setText(geshu);
		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.iv_fanhui:
			finish();
			break;
		case R.id.ll_mdm_sys:
			if (erweima.equals("")) {
				Toast.makeText(MainDuiMianllActivity.this, "����Ӻ����ֻ���", 200).show();
			}else {
			Intent intent = new Intent(MainDuiMianllActivity.this, MianDuiMianSySActivity.class);
			intent.putExtra("erweima", erweima);
			startActivity(intent);
			}
			break;
		case R.id.tv_shanchu:
			list.clear();
			adapter = new TestAdapter(list, this);
			lv.setAdapter(adapter);
			adapter.notifyDataSetChanged(); 
			if (list.size() == 0) {
				int gs = list.size();
           	    String geshu = String.valueOf(gs);
					tv_geshu.setText(geshu);
					im_shanchu.setVisibility(View.GONE);
					tv_shanchu.setVisibility(View.GONE);
			} else {
				
			}
			break;
		case R.id.ll_fx_zshb:
			if (erweima.equals("")) {
				Toast.makeText(MainDuiMianllActivity.this, "����Ӻ����ֻ���", 200).show();
			}else {
			Intent intent = new Intent(MainDuiMianllActivity.this, MianDuiMianFxhbActivity.class);
			intent.putExtra("erweima", erweima);
			intent.putExtra("touxiang", touxiang);
			startActivity(intent);
			}
			break;
		default:
			break;
		}
	}
	
	
	public class TestAdapter extends BaseAdapter {
		public List<String> list;
		private Context context;
		private LayoutInflater mInflater;
		public TestAdapter(List<String> list,Context context) {
			super();
			this.list = list;
			this.context = context;
			mInflater = LayoutInflater.from(context);  
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if(convertView == null){
				vh = new ViewHolder();
				convertView = mInflater.inflate(R.layout.listitem, null);
				vh.tv = (TextView) convertView.findViewById(R.id.textView1);
				vh.tv2 = (ImageView) convertView.findViewById(R.id.textView2);
				
				convertView.setTag(vh);
			}else{
				vh = (ViewHolder) convertView.getTag();
				
				vh.tv2.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
//						list.clear();
						list.remove(position);
						System.out.println("============="+position);
						 try {
                        	 int gs = list.size();
                        	 String geshu = String.valueOf(gs);
							if (list.size() > 0) {
								tv_geshu.setText(geshu);
								im_shanchu.setVisibility(View.VISIBLE);
								tv_shanchu.setVisibility(View.VISIBLE);
							}else {
								tv_geshu.setText(geshu);
								im_shanchu.setVisibility(View.GONE);
								tv_shanchu.setVisibility(View.GONE);
//								mImageView1.setVisibility(View.VISIBLE);
//								mImageView2.setVisibility(View.VISIBLE);
//								mImageView.setVisibility(View.GONE);
//								btn_data.setText("�����");
							}
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
						adapter.notifyDataSetChanged();    
					}
				});
				
			}
			vh.tv.setText(list.get(position));
			return convertView;
		}
		
		public class ViewHolder{
			public TextView tv;
			public ImageView tv2;
		}
	}
	
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
	
	public static void setListViewHeightBasedOnChildren(ListView listView) {   
        // ��ȡListView��Ӧ��Adapter   
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {   
            return;   
        }   
   
        int totalHeight = 0;   
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
            // listAdapter.getCount()�������������Ŀ   
            View listItem = listAdapter.getView(i, null, listView);   
            // ��������View �Ŀ��   
            listItem.measure(0, 0);    
            // ͳ������������ܸ߶�   
            totalHeight += listItem.getMeasuredHeight();    
        }   
   
        ViewGroup.LayoutParams params = listView.getLayoutParams();   
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
        // listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�   
        // params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�   
        listView.setLayoutParams(params);   
    } 
}
