package com.cyberpeace.oj.ojbase.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.dao.mapper.ResourceCollectionMapper;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
