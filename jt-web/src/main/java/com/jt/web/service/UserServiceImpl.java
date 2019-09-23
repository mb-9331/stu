package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private HttpClientService httpClient;
	private static ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void saveUser(User user) {
		//1.定义远程的Url
		String url = "http://sso.jt.com/user/register";
		Map<String,String> params = new HashMap<>();
		params.put("username", user.getUsername());
		params.put("password", user.getPassword());
		params.put("phone", user.getPhone());
		params.put("email", user.getEmail());
		
		
		//如何保证后台程序执行正确???? 200/201
		try {
			String sysResultJSON = 
					httpClient.doPost(url, params);
			SysResult sysResult = 
					objectMapper.readValue(sysResultJSON,SysResult.class);
			
			if(sysResult.getStatus() != 200){
				throw new RuntimeException();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	public String findUserByUP(User user) {
		String url = "http://sso.jt.com/user/login";
		Map<String, String> params = new HashMap<>();
		params.put("username",user.getUsername());
		params.put("password",DigestUtils.md5Hex(user.getPassword()));		
		String token = null;
		try {
			String resultJSON = httpClient.doPost(url, params);
			SysResult readValue = 
					objectMapper.readValue(resultJSON, SysResult.class);
			
			if(readValue.getStatus()==200){
				token =(String) readValue.getData();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return token;
	}
	
	
	
	
	
	
	
}
