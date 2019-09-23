package com.jt.order.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.OrderItem;

public interface OrderItemMapper extends SysMapper<OrderItem>{

	void insertOrderItems(
			@Param("orderItemList") List<OrderItem> orderItemList, 
			@Param("orderId")String orderId,
			@Param("date")Date date);
    
}