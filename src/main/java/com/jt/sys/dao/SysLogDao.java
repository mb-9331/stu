package com.jt.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.sys.entity.SysLog;

/**
 * DAO:数据访问对象
 * 定义访问数据库的相关方法
 */
public interface SysLogDao {
	/**
	 * 按条件分页查询指定日志信息
	 * @param username  查询条件(哪个用户的操作日志)
	 * @param startIndex 当前查询的起始位置
	 * @param pageSize 当前页面大小(每页最多显示多少条记录)
	 * @return
	 * 说明
	 * 方法中的@Param 为mybatis中的一个修饰参数的注解，
	 * 当dao方法中的参数多余一个时，建议使用此注解进行修
	 * 饰，在映射文件中就可以直接使用#{username}等方式
	 * 获取参数的值，假如没有使用这个注解修饰，在映射文件
	 * 获取参数值时，可以使用#{0},#{1}方式或者#{param1}，
	 * #{param2}的方式进行值的获取。
	 */
	List<SysLog> findPageObjects(
		@Param("username")String username,
		@Param("startIndex")Integer startIndex,
		@Param("pageSize")Integer pageSize);
}








