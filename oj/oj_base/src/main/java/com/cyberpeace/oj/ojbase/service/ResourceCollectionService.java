package com.cyberpeace.oj.ojbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;

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
}
