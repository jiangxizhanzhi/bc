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
/**
 * 这段代码定义了一个名为 `ResourceCollectionService` 的接口，该接口扩展了 `IService<ResourceCollection>` 接口。这个服务接口主要用于处理与资源收藏相关的操作。以下是各个方法的简要分析： 
 * 1. **接口继承**： 
 * - `ResourceCollectionService` 继承自 `IService<ResourceCollection>`，这意味着它继承了一些基本的操作，如增删改查等。 
 * 2. **方法列表**： 
 * - `collectionResource(CollectionContext collectionContext, Boolean isCollection)`：此方法用于处理资源的收藏和取消收藏操作。传入一个 `CollectionContext` 对象和一个布尔值来指示是收藏还是取消收藏。 
 * - `selectByUnique(String userId, Integer businessType, String businessId)`：根据用户ID、业务类型和业务ID查询某个资源是否已被收藏。 
 * - `selectByType(String userId, Integer businessType)`：根据用户ID和业务类型查询该用户收藏的所有资源。 
 * - `selectCollectionBatchIds(String userId, Integer businessType, List<String> businessIds)`：批量查询某个用户在特定业务类型下收藏的资源情况，并返回一个映射（Map），键为业务ID，值为布尔值，表示该资源是否被收藏。 
 * - `countCollectionByUserIdAndBusinessId(String userId, Integer businessType, List<String> businessIds)`：统计某个用户在特定业务类型下收藏的资源数量。 
 * 3. **注释**： 
 * - 每个方法都有详细的注释，说明了方法的功能、参数以及可能的返回值。 
 * 总体来说，这个接口提供了丰富的功能来管理用户的资源收藏行为，包括单个资源的收藏、批量查询收藏状态、统计收藏数量等。这些功能使得应用可以灵活地处理用户对各种资源的收藏需求。 
**/
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

