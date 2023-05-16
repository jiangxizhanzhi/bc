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

@Service
public class ResourceCollectionServiceImpl extends ServiceImpl<ResourceCollectionMapper, ResourceCollection> implements ResourceCollectionService {

	@Autowired
	private ResourceCollectionMapper resourceCollectionMapper;

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

	@Override
	public Boolean selectByUnique(String userId, Integer businessType, String businessId) {
		CollectionContext context = CollectionContext.builder().businessType(businessType)
				.businessId(businessId).userId(userId).build();

		ResourceCollection resourceCollection = resourceCollectionMapper.selectByUnique(context);
		return null != resourceCollection;
	}

	@Override
	public List<ResourceCollection> selectByType(String userId, Integer businessType) {

		return resourceCollectionMapper.selectByType(userId, businessType);
	}

	@Override
	public Map<String, Boolean> selectCollectionBatchIds(String userId, Integer businessType, List<String> businessIds) {
		List<ResourceCollection> resourceCollections = resourceCollectionMapper.selectByBusinessIds(userId, businessType, businessIds);
		List<String> collectionBusinessIds = StreamApiUtils.map(resourceCollections, ResourceCollection::getResourceId);
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

	@Override
	public Integer countCollectionByUserIdAndBusinessId(String userId, Integer businessType,
														List<String> businessIds) {
		return resourceCollectionMapper.selectByBusinessIds(userId, businessType, businessIds).size();
	}
}
