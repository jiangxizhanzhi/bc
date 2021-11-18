package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResourceCollectionMapper extends BaseMapper<ResourceCollection> {

	/**
	 * 根据唯一索引删除资源收藏数据
	 * @param collectionContext
	 * @return
	 */
	Integer deleteByUnique(CollectionContext collectionContext);

	/**
	 * 根据唯一索引查询资源数据是否被收藏
	 * @param collectionContext
	 * @return
	 */
	ResourceCollection selectByUnique(CollectionContext collectionContext);
}
