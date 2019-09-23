package com.jt.order.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jt.common.po.Order;
import com.jt.common.po.OrderItem;
import com.jt.common.po.OrderShipping;
import com.jt.order.mapper.OrderItemMapper;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.mapper.OrderShippingMapper;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private OrderItemMapper orderItemMapper;
	@Autowired
	private OrderShippingMapper orderShippingMapper;
	@Override
	public String saveOrder(Order order) {
		Date date = new Date();
		String orderId = ""+order.getUserId()+System.currentTimeMillis();
		order.setOrderId(orderId);
		order.setStatus(1);//表示未付款
		order.setCreated(date);
		order.setUpdated(date);
		orderMapper.insert(order);
		System.out.println("订单入库成功");
		
		OrderShipping orderShipping = order.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(date);
		orderShipping.setUpdated(date);
		orderShippingMapper.insert(orderShipping);
		System.out.println("订单物流入库成功");
		
		List<OrderItem> orderItemList = order.getOrderItems();
		/*for (OrderItem orderItem : orderItemList) {
			orderItem.setOrderId(orderId);
			orderItem.setCreated(date);
			orderItem.setUpdated(date);
			orderItemMapper.insert(orderItem);
		}
		System.out.println("订单商品入库成功");*/
		orderItemMapper.insertOrderItems(orderItemList,orderId,date);
		System.out.println(orderId);
		return orderId;
	}
	@Override
	public Order findOrderById(String id) {
		Order order = 
				orderMapper.selectByPrimaryKey(id);
		OrderShipping orderShipping = 
				orderShippingMapper.selectByPrimaryKey(id);
		OrderItem orderItem = new OrderItem();
		orderItem.setOrderId(id);
		List<OrderItem> orderItems = 
				orderItemMapper.select(orderItem);
		order.setOrderShipping(orderShipping);
		order.setOrderItems(orderItems);
		return order;
	}
}
