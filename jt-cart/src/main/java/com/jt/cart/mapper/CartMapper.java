package com.jt.cart.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.Cart;

public interface CartMapper extends SysMapper<Cart>{
	@Select("select * from tb_cart where user_id = #{userId} and item_id = #{itemId}")
	Cart findCartByUI(Cart cart);
	@Update("update tb_cart set num=#{num},updated=#{updated} where user_id = #{userId} and item_id = #{itemId}")
	void updateByUIN(Cart cart);
	
	void deleteByUI(
			@Param("userId")Long userId, 
			@Param("itemIds") Long[] itemIds);

}
