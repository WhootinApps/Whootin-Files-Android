package com.whootin.whootinfiles_db;

import java.io.*;


import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.graphics.*;
import android.os.*;


public class HttpConnection implements Runnable 
{
	public static final int DID_START = 0;
	public static final int DID_ERROR = 1;
	public static final int DID_SUCCEED = 2;

	private static final int GET = 0;
	private static final int POST = 1;
	private static final int PUT = 2;
	private static final int DELETE = 3;
	private static final int BITMAP = 4;

	private String url;
	private int method;
	private Handler handler;
	private String data;

	private HttpClient httpClient;

	public HttpConnection() 
	{
		this(new Handler());
	}

	public HttpConnection(final Handler _handler) 
	{
		handler = _handler;
	}

	public void create(final int method, final String url, final String data) 
	{
		this.method = method;
		this.url = url;
		this.data = data;
		ConnectionManager.getInstance().push(this);
	}

	public void get(final String url) 
	{
		create(GET, url, null);
	}

	public void post(final String url, final String data) 
	{
		create(POST, url, data);
	}

	public void put(final String url, final String data) {
		create(PUT, url, data);
	}

	public void delete(final String url) 
	{
		create(DELETE, url, null);
	}

	public void bitmap(final String url) 
	{
		create(BITMAP, url, null);
	}

	public void run() 
	{
		handler.sendMessage(Message.obtain(handler, HttpConnection.DID_START));
		httpClient = new DefaultHttpClient();
		
		HttpConnectionParams.setSoTimeout(httpClient.getParams(),3000);
		
		try 
		{
			HttpResponse response = null;
		
			switch (method) 
			{
				case GET:
					HttpGet httpGet = new HttpGet(url);
					httpGet.setHeader("User-Agent", "Mozilla");
					response = httpClient.execute(httpGet);
					break;
				case POST:
					HttpPost httpPost = new HttpPost(url);
					httpPost.setHeader("User-Agent", "Mozilla");
					//httpPost.setHeader("Content-Type", "multipart/form-data");
					httpPost.setEntity(new StringEntity(data));
					response = httpClient.execute(httpPost);
					break;
				case PUT:
					HttpPut httpPut = new HttpPut(url);
					httpPut.setHeader("User-Agent", "Mozilla");
					httpPut.setEntity(new StringEntity(data));
					response = httpClient.execute(httpPut);
					break;
				case DELETE:
					HttpDelete httpDelete = new HttpDelete(url);
					httpDelete.setHeader("User-Agent", "Mozilla");
					response = httpClient.execute(new HttpDelete(url));
					break;
				case BITMAP:
					HttpGet httpGetBitMap = new HttpGet(url);
					httpGetBitMap.setHeader("User-Agent", "Mozilla");
					response = httpClient.execute(httpGetBitMap);
					processBitmapEntity(response.getEntity());
					break;
			}
			if (method < BITMAP)
			{
				processEntity(response.getEntity());
			}
		} 
		catch (Exception e) 
		{
			handler.sendMessage(Message.obtain(handler, HttpConnection.DID_ERROR, e));
		}
		
		ConnectionManager.getInstance().didComplete(this);
	}
	
	private void processEntity(final HttpEntity entity) throws IllegalStateException,IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
		String line, result = "";
		
		while ((line = br.readLine()) != null)
		{
			result += line;
		}
		
		Message message = Message.obtain(handler, DID_SUCCEED, result);
		handler.sendMessage(message);
	}

	private void processBitmapEntity(final HttpEntity entity) throws IOException 
	{
		BufferedHttpEntity bufHttpEntity = new BufferedHttpEntity(entity);
		Bitmap bm = BitmapFactory.decodeStream(bufHttpEntity.getContent());
		handler.sendMessage(Message.obtain(handler, DID_SUCCEED, bm));
	}
}
