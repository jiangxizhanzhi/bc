package com.cyberpeace.oj.ojbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourceCollectionService extends IService<ResourceCollection> {

	/**
	 * 资源收藏和取消收藏
	 * @param collectionContext
	 * @param isCollection
	 * @return
	 */
	Boolean collectionResource(CollectionContext collectionContext, Boolean isCollection);

	/**
	 * 查询资源是否被收藏
	 * @param userId
	 * @param businessId
	 * @param businessType
	 * @return
	 */
	Boolean selectByUnique(String userId, Integer businessType, String businessId);

	/**
	 * 查询
	 * @param userId
	 * @param businessType
	 * @return
	 */
	List<ResourceCollection> selectByType(String userId, Integer businessType);
}
