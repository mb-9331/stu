package com.jt.manage.service;

import java.util.List;

import com.jt.manage.pojo.EasyUITree;

public interface ItemCatService {

	List<EasyUITree> findItemCatById(Long parentId);
	List<EasyUITree> findCacheItemCatById(Long parentId);

}
