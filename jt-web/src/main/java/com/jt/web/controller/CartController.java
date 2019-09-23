package com.jt.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.po.Cart;
import com.jt.common.vo.SysResult;
import com.jt.web.service.CartService;
import com.jt.web.thread.UserThreadLocal;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	private CartService cartService;
	
	@RequestMapping("/show")
	public String show(Model model){
		Long userId = UserThreadLocal.get().getId();
		List<Cart> cartList = 
				cartService.findCartByUserId(userId);
		model.addAttribute("cartList",cartList);
		return "cart";
	}
	
	@RequestMapping("/add/{itemId}")
	public String saveCart(@PathVariable Long itemId,Cart cart){
		Long userId = UserThreadLocal.get().getId();
		cart.setItemId(itemId);
		cart.setUserId(userId);
		cartService.saveCart(cart);
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId,Cart cart){
		Long userId = UserThreadLocal.get().getId();
		cart.setItemId(itemId);
		cart.setUserId(userId);
		cartService.deleteCart(cart);
		return "redirect:/cart/show.html";
	}
	
	@RequestMapping("/update/num/{itemId}/{num}")
	@ResponseBody
	public SysResult updateCart(
			@PathVariable Long itemId,
			@PathVariable Integer num,
			Cart cart){
		try {
			Long userId = UserThreadLocal.get().getId();
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setNum(num);
			cartService.updateCart(cart);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品更新失败");
	}
}
