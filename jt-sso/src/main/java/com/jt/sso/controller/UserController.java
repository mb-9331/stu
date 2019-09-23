package com.jt.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.sso.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	@RequestMapping("/check/{param}/{type}")
	@ResponseBody
	public MappingJacksonValue findCheckUser(
			@PathVariable String param,
			@PathVariable Integer type,
			String callback){
		boolean flag = userService.findCheckUser(param,type);
		SysResult sysResult = SysResult.oK(flag);
		MappingJacksonValue jacksonValue = 
				new MappingJacksonValue(sysResult);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
	
	@RequestMapping("/register")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			userService.saveUser(user);
			return SysResult.oK(user.getUsername());
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.build(201,"用户新增失败");
		}
	}
	@RequestMapping("/login")
	@ResponseBody
	public SysResult findUserByUP(
			User user){
		try {
			String token = userService.findUserByUP(user);
			
			if(!StringUtils.isEmpty(token))
				return SysResult.oK(token);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "用户名或密码错误！");
	}
	
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public MappingJacksonValue findUserByToken(
			@PathVariable String ticket,
			String callback){
		String userJSON = jedisCluster.get(ticket);
		MappingJacksonValue jacksonValue = null;
		if(StringUtils.isEmpty(userJSON)){
			jacksonValue = 
					new MappingJacksonValue(SysResult.build(201, "查询用户失败！"));
		}else{
			jacksonValue = 
					new MappingJacksonValue(SysResult.oK(userJSON));
		}
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
}
