package com.cyberpeace.oj.ojbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;

import java.util.List;
import java.util.Map;

/**
 * 资源收集Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface ResourceCollectionService extends IService<ResourceCollection> {

    /**
     * 资源收藏和取消收藏
     *
     * @param collectionContext
     * @param isCollection
     * @return
     * @throws
     * @see CollectionContext
     * @see Boolean
     * @see Boolean
     */
    Boolean collectionResource(CollectionContext collectionContext, Boolean isCollection);

    /**
     * 查询资源是否被收藏
     *
     * @param userId
     * @param businessType
     * @param businessId
     * @return
     * @throws
     * @see String
     * @see Integer
     * @see String
     * @see Boolean
     */
    Boolean selectByUnique(String userId, Integer businessType, String businessId);

    /**
     * 查询
     *
     * @param userId
     * @param businessType
     * @return
     * @throws
     * @see String
     * @see Integer
     * @see List<ResourceCollection>
     */
    List<ResourceCollection> selectByType(String userId, Integer businessType);

    /**
     * 批量查询资源有没有被收藏
     *
     * @param userId
     * @param businessType
     * @param businessIds
     * @return
     * @throws
     * @see String
     * @see Integer
     * @see List<String>
     * @see Map<String, Boolean>
     */
    Map<String, Boolean> selectCollectionBatchIds(String userId, Integer businessType, List<String> businessIds);

    /**
     * 查看有多少资源被收藏
     *
     * @param userId
     * @param businessType
     * @param businessIds
     * @return
     * @throws
     * @see String
     * @see Integer
     * @see List<String>
     * @see Integer
     */
    Integer countCollectionByUserIdAndBusinessId(String userId, Integer businessType, List<String> businessIds);
}
