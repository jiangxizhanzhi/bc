package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.ResourceCollection;
import com.cyberpeace.oj.ojbase.service.context.CollectionContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资源收集Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
public interface ResourceCollectionMapper extends BaseMapper<ResourceCollection> {

	/**
	 * 根据唯一索引删除资源收藏数据
	 *
	 * @param collectionContext
	 * @return
	 */
	Integer deleteByUnique(CollectionContext collectionContext);

	/**
	 * 根据唯一索引查询资源数据是否被收藏
	 *
	 * @param collectionContext
	 * @return
	 */
	ResourceCollection selectByUnique(CollectionContext collectionContext);

	/**
	 * 查询对应类型的收藏资源
	 *
	 * @param userId
	 * @param businessType
	 * @return
	 */
	List<ResourceCollection> selectByType(@Param("userId") String userId,
			@Param("businessType") Integer businessType);

	/**
	 * 根据业务数据查询收藏
	 *
	 * @param userId       用户id
	 * @param businessId
	 * @param businessType
	 * @return
	 */
	List<String> selectByBusiness(@Param("userId") String userId,
								  @Param("businessId") String businessId, @Param("businessType") Integer businessType);

	/**
	 * 查询由业务id列表
	 *
	 * @param userId       用户id
	 * @param businessType 业务类型
	 * @param businessIds  业务id列表
	 * @return {@code List<ResourceCollection> }
	 */
	List<ResourceCollection> selectByBusinessIds(@Param("userId") String userId, @Param("businessType")Integer businessType,
														@Param("businessIds") List<String> businessIds);
}
