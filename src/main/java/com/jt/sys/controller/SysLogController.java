package com.jt.sys.controller;

import java.util.List;

import com.jt.sys.entity.SysLog;
import com.jt.sys.service.SysLogService;

/**
 * 此对象通过位于软件架构中的控制层，
 * 属于控制层对象，后续讲到Spring MVC时
 * 再对此对象进行加强，在这里只要掌握
 * 控制层与业务的关联
 */
public class SysLogController {

	 private SysLogService sysLogService;
	 public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	 }
	 /**
	  * 通过此方法处理分页请求，并返回响应数据
	  * @param username 用于接收请求中的用户名
	  * @param pageCurrent 用于接收请求中的当前页码值
	  * @return
	  */
	 public  List<SysLog> doFindPageObjects(
			 String  username,
			 Integer pageCurrent){
		 return sysLogService.findPageObjects(
				username, pageCurrent);
	 }
	 
	 
	 
	 
	 
	 
	 
	 
}
