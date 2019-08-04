package com.jt.sys.service.impl;

import java.util.List;

import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;
import com.jt.sys.service.SysLogService;

/***
 * 1)将此对象交给spring管理
 * 2)将此对象管理一个Dao对象(SysLogDao)
 */
public class SysLogServiceImpl implements SysLogService {
    //关联DAO
	private SysLogDao sysLogDao;
	public void setSysLogDao(SysLogDao sysLogDao) {
		this.sysLogDao = sysLogDao;
	}
	public List<SysLog> findPageObjects(
			String username, 
			Integer pageCurrent) {
		//1.对参数进行合法性校验
		if(pageCurrent==null||pageCurrent<1)
		throw new IllegalArgumentException("当前页码值不正确");
		//2.分页查询当前页记录
		//定义页面大小
		int pageSize=10;
		//计算起始位置
		int startIndex=(pageCurrent-1)*pageSize;
		//查询记录
		List<SysLog> list=
		sysLogDao.findPageObjects(username,
		startIndex, pageSize);
		//3.返回结果
		return list;
	}

}




