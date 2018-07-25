package com.hengyushop.json;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HttpClientUtil {
	// 创建HttpClient对象
	private static int TIME_OUT = 6000;
	private static HttpClient httpClient;

	private HttpClientUtil() {
	}

	public static synchronized HttpClient getHttpClient() {
		if (null == httpClient) {
			HttpParams params = new BasicHttpParams();
			// 设置一些基本参数
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setUseExpectContinue(params, true);
			/*
			 * HttpProtocolParams.setUserAgent(params,
			 * "Mozilla/5.0(Linux;U;Android 2.2.2;en-us;Nexus One Build.FRG83) "
			 * +
			 * "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1"
			 * );
			 */
			// 超时设置
			/* 从连接池中取连接的超时时间 */
			ConnManagerParams.setTimeout(params, TIME_OUT);
			/* 连接超时 */
			HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
			/* 请求超时 */
			HttpConnectionParams.setSoTimeout(params, 60000);
			// 设置我们的HttpClient支持HTTP和HTTPS两种模式
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
			// 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			httpClient = new DefaultHttpClient(conMgr, params);
		}
		return httpClient;
	}

	// 获得HttpClient对象
	// public String BASE_URL =
	// "http://192.168.2.107:8080/CSdingzuo/android/restaurantList.htm";//访问的地址
	public String TAG;// 访问标示符

	/**
	 * @param url
	 *            发送请求的URL
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static InputStream getRequest(String BASE_URL) throws Exception {
		// 创建HttpGet对象。
		HttpGet get = new HttpGet(BASE_URL);
		// 发送GET请求
		HttpResponse httpResponse = getHttpClient().execute(get);
		InputStream in = null;
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			in = httpResponse.getEntity().getContent();
		}
		return in;
	}

	/**
	 * @param url
	 *            发送请求的URL
	 * @param params
	 *            请求参数
	 * @return 服务器响应字符串
	 * @throws Exception
	 */
	public static InputStream postRequest(String url,
			Map<String, String> rawParams) throws Exception {
		// 创建HttpPost对象。
		HttpPost post = new HttpPost(url);
		// 如果传递参数个数比较多的话可以对传递的参数进行封装
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		InputStream in = null;
		for (String key : rawParams.keySet()) {
			// 封装请求参数
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// 设置请求参数
		post.setEntity(new UrlEncodedFormEntity(params, "gb2312"));
		// 发送POST请求
		HttpResponse httpResponse = getHttpClient().execute(post);
		// 如果服务器成功地返回响应
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// 获取服务器响应字符串
			in = httpResponse.getEntity().getContent();
		}
		return in;
	}

}
