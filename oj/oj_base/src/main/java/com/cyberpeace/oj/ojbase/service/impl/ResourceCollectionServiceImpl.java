package com.cyberpeace.oj.ojbase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.ResourceCollectionMapper;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源收集Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ServiceImpl
 * @see ResourceCollectionService
 * @since 1.0.0
 */
@Service
public class ResourceCollectionServiceImpl extends ServiceImpl<ResourceCollectionMapper, ResourceCollection> implements ResourceCollectionService {

	/**
	 * 资源收集Mapper
	 *
	 * @see ResourceCollectionMapper
	 */
	@Autowired
	private ResourceCollectionMapper resourceCollectionMapper;

	/**
	 * 集合资源
	 *
	 * @param collectionContext 集合上下文
	 * @param isCollection      is 集合
	 * @return {@code Boolean }
	 * @throws
	 * @see CollectionContext
	 * @see Boolean
	 * @see Boolean
	 */
	@Override
	public Boolean collectionResource(CollectionContext collectionContext, Boolean isCollection) {

		Integer row = 0;

		if (isCollection) {
			ResourceCollection collection = BeanUtils.copy(collectionContext,
					ResourceCollection.class);
			row = resourceCollectionMapper.insert(collection);
		} else {
			row = resourceCollectionMapper.deleteByUnique(collectionContext);
		}

		return row > 0;
	}

	/**
	 * 查询由独特
	 *
	 * @param userId       用户id
	 * @param businessType 业务类型
	 * @param businessId   业务id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see Boolean
	 */
	@Override
	public Boolean selectByUnique(String userId, Integer businessType, String businessId) {
		CollectionContext context = CollectionContext.builder().businessType(businessType)
				.businessId(businessId).userId(userId).build();

		ResourceCollection resourceCollection = resourceCollectionMapper.selectByUnique(context);
		return null != resourceCollection;
	}

	/**
	 * 查询由类型
	 *
	 * @param userId       用户id
	 * @param businessType 业务类型
	 * @return {@code List<ResourceCollection> }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<ResourceCollection>
	 */
	@Override
	public List<ResourceCollection> selectByType(String userId, Integer businessType) {

		return resourceCollectionMapper.selectByType(userId, businessType);
	}

	/**
	 * 查询收集批量id列表
	 *
	 * @param userId       用户id
	 * @param businessType 业务类型
	 * @param businessIds  业务id列表
	 * @return {@code Map<String, Boolean> }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<String>
	 * @see Map<String, Boolean>
	 */
	@Override
	public Map<String, Boolean> selectCollectionBatchIds(String userId, Integer businessType, List<String> businessIds) {
		List<ResourceCollection> resourceCollections = resourceCollectionMapper.selectByBusinessIds(userId, businessType, businessIds);
		List<String> collectionBusinessIds = StreamApiUtils.map(resourceCollections, ResourceCollection::getBusinessId);
		Map<String, Boolean> collectionMap = new HashMap<>();
		for (String businessId : businessIds) {
			if (collectionBusinessIds.contains(businessId)) {
				collectionMap.put(businessId, true);
			} else {
				collectionMap.put(businessId, false);
			}
		}
		return collectionMap;
	}

	/**
	 * 计数收集由用户id和业务id
	 *
	 * @param userId       用户id
	 * @param businessType 业务类型
	 * @param businessIds  业务id列表
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<String>
	 * @see Integer
	 */
	@Override
	public Integer countCollectionByUserIdAndBusinessId(String userId, Integer businessType,
														List<String> businessIds) {
		return resourceCollectionMapper.selectByBusinessIds(userId, businessType, businessIds).size();
	}
}
