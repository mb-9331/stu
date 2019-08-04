package com.jt.sys.service;
import java.util.List;
import com.jt.sys.entity.SysLog;
/**
 * 业务层接口对象(负责业务逻辑处理)
 * 1)核心业务(分页获取数据)
 * 2)非核心业务(权限，监控，。。。)
 */
public interface SysLogService {
	 /**
	  * 根据条件分页查询当前页的记录
	  * @param username 查询条件
	  * @param pageCurrent 当前页的页码
	  * @return
	  */
	 List<SysLog> findPageObjects(
			 String username,
			 Integer pageCurrent);
}




