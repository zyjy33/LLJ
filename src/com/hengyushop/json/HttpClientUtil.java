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
	// ����HttpClient����
	private static int TIME_OUT = 6000;
	private static HttpClient httpClient;

	private HttpClientUtil() {
	}

	public static synchronized HttpClient getHttpClient() {
		if (null == httpClient) {
			HttpParams params = new BasicHttpParams();
			// ����һЩ��������
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setUseExpectContinue(params, true);
			/*
			 * HttpProtocolParams.setUserAgent(params,
			 * "Mozilla/5.0(Linux;U;Android 2.2.2;en-us;Nexus One Build.FRG83) "
			 * +
			 * "AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1"
			 * );
			 */
			// ��ʱ����
			/* �����ӳ���ȡ���ӵĳ�ʱʱ�� */
			ConnManagerParams.setTimeout(params, TIME_OUT);
			/* ���ӳ�ʱ */
			HttpConnectionParams.setConnectionTimeout(params, TIME_OUT);
			/* ����ʱ */
			HttpConnectionParams.setSoTimeout(params, 60000);
			// �������ǵ�HttpClient֧��HTTP��HTTPS����ģʽ
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
			// ʹ���̰߳�ȫ�����ӹ���������HttpClient
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
					params, schReg);
			httpClient = new DefaultHttpClient(conMgr, params);
		}
		return httpClient;
	}

	// ���HttpClient����
	// public String BASE_URL =
	// "http://192.168.2.107:8080/CSdingzuo/android/restaurantList.htm";//���ʵĵ�ַ
	public String TAG;// ���ʱ�ʾ��

	/**
	 * @param url
	 *            ���������URL
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public static InputStream getRequest(String BASE_URL) throws Exception {
		// ����HttpGet����
		HttpGet get = new HttpGet(BASE_URL);
		// ����GET����
		HttpResponse httpResponse = getHttpClient().execute(get);
		InputStream in = null;
		// ����������ɹ��ط�����Ӧ
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// ��ȡ��������Ӧ�ַ���
			in = httpResponse.getEntity().getContent();
		}
		return in;
	}

	/**
	 * @param url
	 *            ���������URL
	 * @param params
	 *            �������
	 * @return ��������Ӧ�ַ���
	 * @throws Exception
	 */
	public static InputStream postRequest(String url,
			Map<String, String> rawParams) throws Exception {
		// ����HttpPost����
		HttpPost post = new HttpPost(url);
		// ������ݲ��������Ƚ϶�Ļ����ԶԴ��ݵĲ������з�װ
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		InputStream in = null;
		for (String key : rawParams.keySet()) {
			// ��װ�������
			params.add(new BasicNameValuePair(key, rawParams.get(key)));
		}
		// �����������
		post.setEntity(new UrlEncodedFormEntity(params, "gb2312"));
		// ����POST����
		HttpResponse httpResponse = getHttpClient().execute(post);
		// ����������ɹ��ط�����Ӧ
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			// ��ȡ��������Ӧ�ַ���
			in = httpResponse.getEntity().getContent();
		}
		return in;
	}

}
