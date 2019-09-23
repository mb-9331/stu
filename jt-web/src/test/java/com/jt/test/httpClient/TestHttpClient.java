package com.jt.test.httpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.http.HttpEntity;

public class TestHttpClient {
	@Test
	public void doGet() throws ClientProtocolException, IOException{
		//实例化httpClient对象
		CloseableHttpClient client = 
				HttpClients.createDefault();
		//定义url地址
		String url = "https://www.baidu.com";
		//定义请求类型
		HttpGet get = new HttpGet(url);
		String method = 
				get.getRequestLine().getMethod();
		System.out.println("请求类型："+method);
		String http = 
				get.getRequestLine().getProtocolVersion().toString();
		System.out.println("请求协议："+http);
		//发起http请求
		CloseableHttpResponse response = 
				client.execute(get);
		
		//判断状态信息
		if(response.getStatusLine().getStatusCode()==200){
			//获取页面信息
			String result = 
					EntityUtils.toString(response.getEntity());
			System.out.println(result);
		}
	}
	@Test
	public String doPort(
			String url,
			Map<String, String> params,
			String charset){
		CloseableHttpClient client = 
				HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		List<NameValuePair> parameters = new ArrayList<>();
		for (Map.Entry<String, String> element : params.entrySet()) {
			parameters.add(new BasicNameValuePair(element.getKey(),element.getValue()));
		}
		
		UrlEncodedFormEntity formEntity=null;
		try {
			formEntity = new UrlEncodedFormEntity(parameters, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		post.setEntity(formEntity);
		try {
			CloseableHttpResponse execute = 
					client.execute(post);
			if(execute.getStatusLine().getStatusCode()==200){
				return EntityUtils.toString(execute.getEntity());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
