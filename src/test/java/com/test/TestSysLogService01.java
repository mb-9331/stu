package com.test;

import java.util.List;

import org.junit.Test;

import com.jt.sys.entity.SysLog;
import com.jt.sys.service.SysLogService;

public class TestSysLogService01 extends TestBase {

	@Test
	public void testFindPageObjects(){
		SysLogService service=
		ctx.getBean("sysLogService",
				     SysLogService.class);
		List<SysLog> list=
		service.findPageObjects("admin",1);
		System.out.println(list.size());
	}
	
	
	
}
