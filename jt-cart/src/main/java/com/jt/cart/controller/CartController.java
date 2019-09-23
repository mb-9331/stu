package com.jt.cart.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.cart.service.CartService;
import com.jt.common.po.Cart;
import com.jt.common.vo.SysResult;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	@RequestMapping("/query/{userId}")
	@ResponseBody
	public SysResult findCartByUserId(@PathVariable Long userId){
		try {
			List<Cart> cartList = 
					cartService.findCartByUserId(userId);
			return SysResult.oK(cartList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车数据查询失败！");
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveCart(Cart cart){
		try {
			cartService.saveCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "新增购物车失败！");
	}
	@RequestMapping("/delete/{userId}/{itemId}")
	@ResponseBody
	public SysResult deleteCart(
			@PathVariable Long userId,
			@PathVariable Long itemId){
		try {
			cartService.deleteCart(userId,itemId);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "删除购物车失败！");
	}
	
	@RequestMapping("/update/num")
	@ResponseBody
	public SysResult updateCart(
			String cartJSON){
		try {
			Cart cart = objectMapper.readValue(cartJSON, Cart.class);
			cartService.updateCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "更新购物车失败！");
	}
	@RequestMapping("/order/delete")
	@ResponseBody
	public SysResult deleteOrder(String userId,String itemIds){
		try {
			String[] itemIdString = 
					objectMapper.readValue(itemIds, String[].class);
			Long[] itemIdLong = 
					(Long[]) ConvertUtils.convert(itemIdString, Long[].class);
			cartService.deleteCart(Long.valueOf(userId),itemIdLong);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "购物车下单失败");
	}
}
