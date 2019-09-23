package com.jt.manage.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.manage.pojo.EasyUITree;
import com.jt.manage.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
	
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 实现商品分类目录展现
	 * @RequestParam
	 * 	defaultValue= 如果没有传递参数 默认值为...
	 *  required=true/false 默认为false  如果true 那么前台必须传递该参数.
	 *  value 需要转化变量的名称
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITree> findItemCatById(
			@RequestParam(value="id",
			defaultValue="0") 
			Long parentId){
		//查询一级商品分类目录
		//Long parentId = 0L;
		//return itemCatService.findItemCatById(parentId);
		return itemCatService.findCacheItemCatById(parentId);
	}
}
