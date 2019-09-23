package com.jt.sso.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.User;

public interface UserMapper extends SysMapper<User>{
	
	int findCheckUser(@Param("cloumn")String cloumn, 
					@Param("param")String param);
	
	@Select("select * from tb_user where username=#{username} and password=#{password}")
	User findUserByUP(User user);
	
}
