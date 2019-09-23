package com.jt.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.jt.common.po.Cart;
import com.jt.common.po.Order;
import com.jt.common.po.OrderItem;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	private HttpClientService httpClient;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	public List<Cart> findCartByUserId(Long userId) {
		String url = "http://cart.jt.com/cart/query/"+userId;
		List<Cart> cartList =new ArrayList<>();
		try {
			String resultJSON = 
					httpClient.doGet(url);
			SysResult sysResult = 
					objectMapper.readValue(resultJSON, SysResult.class);
			cartList = (List<Cart>) sysResult.getData();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return cartList;
	}
	@Override
	public void saveCart(Cart cart) {
		String url = "http://cart.jt.com/cart/save";
		Map<String,String> map = new HashMap<String,String>();
		map.put("userId", cart.getUserId()+"");
		map.put("itemId", cart.getItemId()+"");
		map.put("itemTitle", cart.getItemTitle());
		map.put("itemImage", cart.getItemImage());
		map.put("itemPrice", cart.getItemPrice()+"");
		map.put("num", cart.getNum()+"");
		try {
			httpClient.doPost(url, map,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void deleteCart(Cart cart) {
		String url= 
				"http://cart.jt.com/cart/delete/"+cart.getUserId()+"/"+cart.getItemId();
		try {
			httpClient.doGet(url,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateCart(Cart cart) {
		String url = 
				//"http://cart.jt.com/cart/update/num/"+cart.getUserId()+"/"+cart.getItemId()+"/"+cart.getNum();
				"http://cart.jt.com/cart/update/num";
		try {
			String cartJSON = 
					objectMapper.writeValueAsString(cart);
			Map<String,String> params =new HashMap<String, String>();
			params.put("cartJSON", cartJSON);
			httpClient.doGet(url,params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void deleteCartOrder(Order order) {
		String url = "http://cart.jt.com/cart/order/delete";
		List<String> itemIds = new ArrayList<>();
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem orderItem : orderItems) {
			itemIds.add(orderItem.getItemId());
		}
		if(itemIds!=null){
			try {
				Map<String, String> params = new HashMap<>();
				params.put("userId", order.getUserId().toString());
				params.put("itemIds",objectMapper.writeValueAsString(itemIds));
				httpClient.doPost(url, params);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
