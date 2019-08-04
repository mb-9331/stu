package com.test;

import java.util.List;

import org.junit.Test;

import com.jt.sys.controller.SysLogController;
import com.jt.sys.entity.SysLog;

public class TestSysLogController01 extends TestBase {

	@Test
	public void testDoFindPageObjects(){
		//1.获取controller对象
		SysLogController controller=
		ctx.getBean("sysLogController",
				     SysLogController.class);
		//2.调用分页查询相关方法
		List<SysLog> list=
		controller.doFindPageObjects("admin",
				1);
		System.out.println(list.size());
		
	}
}
