package com.jt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private ItemDescMapper itemDescMapper;

	
	@Override
	public EasyUIResult findItemByPage(int page, int rows) {
		//1.获取商品记录总数
		//int total = itemMapper.findItemCount();
		/**
		 * 通用Mapper查询中,如果添加了对象,则对象中不为
		 * null属性充当where条件.
		 * item item = new Item();
		 * item.setId(1)
		 * select count(*) from 表名 where id = 1
		 */
		int total = itemMapper.selectCount(null);
		
		//System.out.println("利用通用Mapper查询数据"+total);
		/**
		 * 2.实现分页查询
		 * sql:SELECT * FROM tb_item LIMIT 起始记录数,每页记录数
		 * 第一页  SELECT * FROM tb_item LIMIT 0,20
		 * 第二页  SELECT * FROM tb_item LIMIT 20,20
		 * 第三页  SELECT * FROM tb_item LIMIT 40,20
		 * 第N页  SELECT * FROM tb_item LIMIT (n-1)*rows,20
		 */
		int start = (page - 1) * rows;
		List<Item> itemList = 
				itemMapper.findItemByPage(start,rows);
		
		return new EasyUIResult(total, itemList);
	}


	@Override
	public String findItemCatById(Long itemId) {
		
		return itemMapper.findItemCatById(itemId);
	}
	
	@Override
	public void saveItem(Item item,String desc) {
		//利用通用mapper实现数据入库
		item.setStatus(1);
		item.setCreated(new Date());
		item.setUpdated(item.getCreated());
		itemMapper.insert(item);
		
		//100,101,103,105,106,A107 B110 C120
		//查找当前事务中的最大Id 
		//Executing: SELECT LAST_INSERT_ID() 
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemId(item.getId()); //mybatis底层自动回填数据
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(item.getCreated());
		itemDesc.setUpdated(item.getCreated());
		itemDescMapper.insert(itemDesc);
	}


	@Override
	public void updateItem(Item item,String desc) {
		item.setUpdated(new Date());
		//将对象中不为null的数据修改.
		itemMapper.updateByPrimaryKeySelective(item);
		
		ItemDesc itemDesc = new ItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(item.getId());
		itemDesc.setUpdated(item.getUpdated());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
	}

	/**
	 * 能否使用通用Mapper   单表操作
	 * sql:
	 * 	update tb_item set status = #{status},updated = #{updated}/now()
	 *  where id  in (1,2,3,4,5)
	 */
	@Override
	public void updateStatus(Long[] ids, int status) {
		
		/*for (Long id : ids) {
			Item item = new Item();
			item.setId(id);
			item.setStatus(status);
			item.setUpdated(new Date());
			itemMapper.updateByPrimaryKeySelective(item);
		}*/
		itemMapper.updateStatus(ids,status);
	}


	@Override
	public ItemDesc findItemDescById(Long itemId) {
		
		return itemDescMapper.selectByPrimaryKey(itemId);
	}


	@Override
	public void deleteItems(Long[] ids) {
		itemDescMapper.deleteByIDS(ids);
		itemMapper.deleteByIDS(ids);
	}

	
	/**
	 * 1.手写sql 手动返回po.item
	 * 2.编辑vo.Mapper 操作item表
	 * 3.直接返回manage.中的pojo对象 json返回
	 * 4.不用common中po  在web层添加pojo对象
	 */
	@Override
	public Item finditemById(Long itemId) {
		
		return itemMapper.selectByPrimaryKey(itemId);
	}
	
	
	
	
	
	
	
	
}
