package com.test;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;

public class TestSysLogDao01 extends TestBase {
	@Test
	public void testFindPageObjects(){
		//1.获取dao对象
		SysLogDao dao=
		ctx.getBean("sysLogDao",SysLogDao.class);
		//2.执行dao对象方法
		List<SysLog> list=dao.findPageObjects(
				"admin",0,10);
		//3.测试结果
		Assert.assertNotEquals(null, list);
		System.out.println("list.size="+list.size());
	}
}





