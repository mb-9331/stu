package com.test;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Assert;
import org.junit.Test;
public class TestMyBatis01 extends TestBase {
	
	@Test
	public void testSqlSessionFactory(){
		//1.获取SqlSessionFactory对象
		SqlSessionFactory ssf=//DefaultSqlSessionFactory
		ctx.getBean("sqlSessionFactory",SqlSessionFactory.class);
		System.out.println(ssf.getClass().getName());
		//2.测试对象的值是否为空
	    Assert.assertNotEquals(null,ssf);
	}

}
