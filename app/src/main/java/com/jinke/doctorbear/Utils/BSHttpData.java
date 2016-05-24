package com.jinke.doctorbear.Utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;*/

import android.os.AsyncTask;

import com.jinke.doctorbear.Listener.HttpGetDataListener;

public class BSHttpData extends AsyncTask<String, Void, String>{

	/**
	 * 熊大夫智能界面请求服务器数据方法
	 */
	
	
	private String url;
	private HttpGetDataListener listener;
	/*private DefaultHttpClient mHttpClient;
	private Httpget mHttpGet;
	private HttpResponse mHttpResponse;
	private HttpEntity mHttpEntity;*/
	private InputStream in;
	public BSHttpData(String url,HttpGetDataListener listener) {
		this.url = url;
		this.listener = listener;
	}
	@Override
	protected String doInBackground(String... params) {
		try {

			/*mHttpClient = new DefaultHttpClient();
			mHttpGet = new HttpGet(url);
			mHttpResponse = mHttpClient.execute(mHttpGet);
			mHttpEntity = mHttpResponse.getEntity();
			in = mHttpEntity.getContent();*/
			URL urlname = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)urlname.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			StringBuffer sb = null;
			if (conn.getResponseCode() == 200){
				InputStream is = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line = null;
				sb = new StringBuffer();
				while ((line = br.readLine())!=null) {
					sb.append(line);
				}
				return sb.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		listener.getDataUrl(result);
		super.onPostExecute(result);
	}

}
