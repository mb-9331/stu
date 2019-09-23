package com.jt.web.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.po.Cart;
import com.jt.common.po.Order;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.thread.UserThreadLocal;

@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private HttpClientService httpClientService;
	
	private static ObjectMapper objectMapper=new ObjectMapper();
	@Autowired
	private CartService cartService;
	@Override
	public String saveOrder(Order order) {
		String orderId=null;
		String url = "http://order.jt.com/order/create";
		try {
			String orderJSON = 
					objectMapper.writeValueAsString(order);
			Map<String, String> params = new HashMap<>();
			params.put("orderJson", orderJSON);
			String resultJSON = 
					httpClientService.doPost(url, params,"utf-8");
			SysResult sysResult	= 
					objectMapper.readValue(resultJSON, SysResult.class);
			if(sysResult.getStatus()==200){
				orderId =(String) sysResult.getData();
				cartService.deleteCartOrder(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return orderId;
	}
	@Override
	public Order findOrderById(String id) {
		String url = "http://order.jt.com/order/query/"+id;
		Order order = null;
		try {
			String orderJSON = httpClientService.doGet(url);
			order = objectMapper.readValue(orderJSON, Order.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return order;
	}
}
