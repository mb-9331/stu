package com.jt.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.jt.common.po.User;
import com.jt.common.vo.SysResult;
import com.jt.web.service.UserService;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	@RequestMapping("/{module}")
	public String module(@PathVariable String module){
		
		return module;
	}
	
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user){
		try {
			userService.saveUser(user);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"新增用户失败");
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult findUserByUP(User user,
			HttpServletResponse response){
		try {
			String token = userService.findUserByUP(user);
			if(!StringUtils.isEmpty(token)){
				Cookie cookie = new Cookie("JT_TICKET", token);
				cookie.setMaxAge(3600*24*7);
				cookie.setPath("/");
				
				response.addCookie(cookie);
				
				return SysResult.oK();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"用户名或密码错误！");
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		String  token = null;
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())){
				token = cookie.getValue();
				break;
			}
		}
		if(!StringUtils.isEmpty(token)){
			jedisCluster.del(token);
			Cookie cookie = new Cookie("JT_TICKET","");
			cookie.setPath("/");
			cookie.setMaxAge(0);
			
			response.addCookie(cookie);
		}
		return "redirect:/index.html";
	}
	
	
}
