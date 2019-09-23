package com.jt.cart.service;

import java.util.List;

import com.jt.common.po.Cart;

public interface CartService {

	List<Cart> findCartByUserId(Long userId);

	void saveCart(Cart cart);

	void deleteCart(Long userId, Long... itemId);
	
	void updateCart(Cart cart);

}
