package com.jt.manage.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisServer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.RedisService;
import com.jt.manage.mapper.ItemCatMapper;
import com.jt.manage.pojo.EasyUITree;
import com.jt.manage.pojo.ItemCat;

import redis.clients.jedis.JedisCluster;


@Service
public class ItemCatServiceImpl implements ItemCatService {
	
	@Autowired
	private ItemCatMapper itemCatMapper;
	
	@Autowired
	private JedisCluster jedisCluster;
	//private RedisService redisService;
	//private Jedis jedis; //connection
	
	private static ObjectMapper objectMapper 
	= new ObjectMapper();
	/**
	 * 1.根据parentId查询列表信息
	 * 2.将列表数据转化为返回voList集合
	 */
	@Override
	public List<EasyUITree> findItemCatById(Long parentId) {
		ItemCat itemCat = new ItemCat();
		itemCat.setParentId(parentId);
		List<ItemCat> itemCatList = 
				itemCatMapper.select(itemCat);
		List<EasyUITree> treeList = new ArrayList<>();
		for (ItemCat itemCatTemp : itemCatList) {
			EasyUITree easyUITree = new EasyUITree();
			easyUITree.setId(itemCatTemp.getId());
			easyUITree.setText(itemCatTemp.getName());
			//如果是父级则应该closed,如果不是父级 open
			String state = 
			itemCatTemp.getIsParent() ? "closed" : "open";		
			easyUITree.setState(state);
			treeList.add(easyUITree);
		}
		return treeList;
	}
	
	//通过redis缓存实现信息查询
	@Override
	public List<EasyUITree> findCacheItemCatById(Long parentId) {
		List<EasyUITree> treeList = null;
		
		//1.先查询缓存数据
		String key = "ITEM_CAT_"+parentId;
		String resultJSON = jedisCluster.get(key);
		
		try {
			//2.判断缓存中是否有数据
			if(StringUtils.isEmpty(resultJSON)){
				//2.1表示缓存中没有数据,需要查询后台数据库
				treeList = findItemCatById(parentId);
				
				//2.2将java对象转化为JSON串
				String jsonData = objectMapper.writeValueAsString(treeList);
				
				//2.3将数据写入缓存
				jedisCluster.set(key, jsonData);
				System.out.println("第一次查询数据库");
			}else {
				//3.缓存中有数据
				//3.1将JSON串转化为java对象(List)
				EasyUITree[] trees = 
				objectMapper.readValue(resultJSON,EasyUITree[].class);
				treeList = Arrays.asList(trees);
				System.out.println("查询缓存数据");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return treeList;
	}
}
