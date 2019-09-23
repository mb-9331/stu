package com.jt.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;

public interface ItemMapper extends SysMapper<Item>{
	/**
	 * @Select  @Update  @Delete @Insert
	 */
	@Select("select count(*) from tb_item")
	int findItemCount();
	
	/**
	 * 规定:mybatis中要求,只能单值传参.
	 * 思路:将多值转化为单值
	 * 		1.封装pojo对象
	 * 		2.封装集合数据 array  List Map
	 * 
	 		@Param("start")int start
	 		表示将参数封装为Map集合
	 * @param start
	 * @param rows
	 * @return
	 */
	List<Item> findItemByPage(@Param("start")int start, 
			@Param("rows")int rows);

	@Select("select name from tb_item_cat where id = #{itemId}")
	String findItemCatById(Long itemId);
	
	
	void updateStatus(@Param("ids")Long[] ids,@Param("status")int status);
	
	
	
	
	
	
	
}
