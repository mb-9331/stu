package com.jt.web.intercept;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.jt.web.service.CartService;

public class InvocationHandle{
	
	public static void main(String[] args) {
		Object proxyObject = 
				getProxyObject(CartService.class);
		CartService proxyObjectCartService = 
				(CartService) proxyObject;
		proxyObjectCartService.findCartByUserId(90L);
	}
	

	static class MyInvocationHandle implements InvocationHandler{
		Object targer;
		
		public MyInvocationHandle(Object targer) {
			super();
			this.targer = targer;
		}

		@Override
		public Object invoke(
				Object proxy, 
				Method method, 
				Object[] args) throws Throwable {
			System.out.println(method.toString());
			Object result = method.invoke(targer, args);
			for (Object object : args) {
				System.out.println(object);
			}
			return result;
		}
		
	}
	//返回代理对象
	static Object getProxyObject(Class interfaceInfo){
		//类加载器
		ClassLoader loader = 
				interfaceInfo.getClassLoader();
		Class[] interfaces = {interfaceInfo};
		MyInvocationHandle myInvocationHandle=
				new MyInvocationHandle(interfaceInfo.getName());
		return Proxy.newProxyInstance(loader, interfaces,myInvocationHandle);
	}
}
