package com.jt.order.mapper;

import java.util.Date;

import com.jt.common.mapper.SysMapper;
import com.jt.common.po.Order;

public interface OrderMapper extends SysMapper<Order>{

	void updateStatus(Date dateAge);
   
}