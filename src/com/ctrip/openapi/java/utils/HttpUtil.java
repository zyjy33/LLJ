package com.ctrip.openapi.java.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class HttpUtil {

	private static HttpResponse httpResponse = null; 
	private HttpUtil(){}
	
	public static String httpsGet(String url){
		HttpGet httpGet = new HttpGet(url);
		try {
			httpResponse = new DefaultHttpClient().execute(httpGet); 
			 if (httpResponse.getStatusLine().getStatusCode() == 200){  
			     String result = EntityUtils.toString(httpResponse.getEntity()); 
			     return result;
			 }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }
	
}
