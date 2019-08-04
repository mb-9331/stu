package com.jt.sys.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.jt.sys.dao.SysLogDao;
import com.jt.sys.entity.SysLog;
/**
 * 思考:如何将此对象交给Spring管理
 */
public class SysLogDaoImpl implements SysLogDao {
    
	private SqlSessionFactory sqlSessionFactory;
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	/**
     * 思考:如何在如下方法中通过mybatis对象
     * 访问数据库获取日志信息
     */
	public List<SysLog> findPageObjects(String username,Integer startIndex,Integer pageSize) {
		//1.获取SqlSession对象
		SqlSession session=
		sqlSessionFactory.openSession();
		//2.执行查询操作
		String statement=
		"com.jt.sys.dao.SysLogDao.findPageObjects";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("username", username);
		map.put("startIndex",startIndex);
		map.put("pageSize", pageSize);
		List<SysLog> list=
		session.selectList(statement,map);
		//3.释放资源
		session.close();
		//4.返回结果
		return list;
	}

}
