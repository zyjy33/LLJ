package com.ctrip.openapi.java.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GetImgUtil {
	// ��ȡ����ͼƬ������
	public static Bitmap getImage(String picturepath)  {
		 URL myFileURL;  
	        Bitmap bitmap=null;  
	        try{  
	            myFileURL = new URL(picturepath);  
	            //�������  
	            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();  
	            //���ó�ʱʱ��Ϊ5�룬conn.setConnectionTiem(0);��ʾû��ʱ������  
	            conn.setConnectTimeout(5*1000);  
	            //�������û��������  
	            conn.setDoInput(true);  
	            //��ʹ�û���  
	            conn.setUseCaches(false);  
	            //�����п��ޣ�û��Ӱ��  
	            //conn.connect();  
	            //�õ�������  
	            InputStream is = conn.getInputStream();  
	            //�����õ�ͼƬ  
	            bitmap = BitmapFactory.decodeStream(is);  
	            //�ر�������  
	            is.close();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  
	          
	        return bitmap; 
	}
}
