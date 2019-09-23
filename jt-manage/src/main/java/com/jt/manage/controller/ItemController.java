package com.jt.manage.controller;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;
@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	//根据分页实现商品列表查询  /item/query?page=1&rows=20
	@RequestMapping("/query")
	@ResponseBody
	public EasyUIResult findItemByPage(int page,int rows){
		
		return itemService.findItemByPage(page,rows);
	}
	
	/**
	 * 根据商品分类id查询商品分类名称  iso-8859-1
	 * 
	 * 使用@ResponseBody注解时,
	 * 	  如果回传数据是String 则默认的解析规则ISO-8859-1
	 * 	  如果回传的数据是对象 则采用UTF-8格式编码
	 * 
	 * 原因:
	 * 	public class StringHttpMessageConverter extends AbstractHttpMessageConverter<String> {

		public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
		
		public abstract class AbstractJackson2HttpMessageConverter extends AbstractHttpMessageConverter<Object>
		implements GenericHttpMessageConverter<Object> {
		public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/cat/queryItemName",produces="text/html;charset=UTF-8")
	@ResponseBody
	public String findItemCatById(Long itemId){
		
		return itemService.findItemCatById(itemId);
	}
	
	//实现商品新增
	@RequestMapping("/save")
	@ResponseBody
	public SysResult saveItem(Item item,String desc){
		try {
			itemService.saveItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品新增失败");
	}
	
	//实现商品修改
	@RequestMapping("/update")
	@ResponseBody
	public SysResult updateItem(Item item,String desc){
		try {
			itemService.updateItem(item,desc);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品修改失败");
	}
	
	
	//商品下架  /item/instock      12,3,4,5,6
	@RequestMapping("/instock")
	@ResponseBody
	public SysResult instock(Long[] ids){
		try {
			int status = 2;  //下架       1是上架
			itemService.updateStatus(ids,status);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201,"商品下架失败");
	}
	
	//根据ItemId查询商品详情信息
	@RequestMapping("/query/item/desc/{itemId}")
	@ResponseBody
	public SysResult findItemDescById(@PathVariable Long itemId){
		try {
			ItemDesc itemDesc = 
					itemService.findItemDescById(itemId);
			return SysResult.oK(itemDesc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.build(201, "商品详情查询失败");
	}
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public SysResult deleteItems(Long[] ids){
		try {
			itemService.deleteItems(ids);
			return SysResult.oK();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SysResult.build(201,"商品删除失败");
	}
	
}
