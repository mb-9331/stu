package com.jt.sso.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.User;
import com.jt.sso.mapper.UserMapper;

import redis.clients.jedis.JedisCluster;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	private static ObjectMapper ObjectMapper = new ObjectMapper();
	@Autowired
	private JedisCluster jedisCluster;
	@Override
	public boolean findCheckUser(String param, Integer type) {
		String cloumn = null;
		switch (type) {
		case 1:
			cloumn="username";
			break;
		case 2:
			cloumn="phone";
			break;
		case 3:
			cloumn="email";
			break;
		}
		int count =userMapper.findCheckUser(cloumn,param);
		return count==0?false:true;
	}

	@Override
	public void saveUser(User user) {
		String md5pass = DigestUtils.md5Hex(user.getPassword());
		user.setPassword(md5pass);
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		userMapper.insert(user);
	}

	@Override
	public String findUserByUP(User user) {
		User userDB = userMapper.findUserByUP(user);
		if(userDB==null)throw new RuntimeException();
		String token=null;
		try {
			token = DigestUtils.md5Hex("JT_TICKET_ " + System.currentTimeMillis() + user.getUsername());
			String userJSON = 
					ObjectMapper.writeValueAsString(userDB);
			jedisCluster.setex(token,3600*24*7,userJSON);
			
		} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new RuntimeException();
		}
	
		return token;
	}

}
