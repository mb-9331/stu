package com.jt.web.service;

import java.util.List;

import com.jt.common.po.Cart;
import com.jt.common.po.Order;

public interface CartService {

	List<Cart> findCartByUserId(Long userId);

	void saveCart(Cart cart);

	void deleteCart(Cart cart);

	void updateCart(Cart cart);

	void deleteCartOrder(Order order);

}
