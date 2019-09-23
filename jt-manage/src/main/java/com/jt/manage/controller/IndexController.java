package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping("/index")
	public String index(){
		
		return "index";
	}
	
	/**
	 * url:
	 * 	/page/item-add
	 * 	/page/item-update
	 * 
	 * 要求:
	 * 	 1.参数的位置必须固定
	 *   2.如果有多个参数时使用"/"分割
	 *   3.需要接收的参数使用{}包装,
	 *     使用@PathVariable注解接收参数
	 * 用法:
	 * 	 @RequestMapping("/page/{aaa}")
	 * 	 @PathVariable(value="aaa")String moduleName
	 * 	 如果参数接收名称不一致.可以使用
	 *   @PathVariable(value="aaa")方法获取数据
	 *   
	 *   localhost:8091/addUser?id=1&name=tom
	 *   localhost:8091/addUser/1/tom
	 * @return
	 */					
					///page/item-add
	@RequestMapping("/page/{moduleName}")
	public String item_add(@PathVariable String moduleName){
		
		return moduleName;
	}
	
	
	
	
	
	
	
}
