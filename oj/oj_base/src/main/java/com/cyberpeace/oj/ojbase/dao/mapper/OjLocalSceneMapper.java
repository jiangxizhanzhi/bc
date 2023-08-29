package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OjLocalSceneMapper extends BaseMapper<OjLocalScene> {

	/**
	 * 查询私有场景
	 * @param userId
	 * @param businessId
	 * @param isWeb
	 * @return
	 */
	OjLocalScene selectPrivateScene(@Param("userId") String userId,
			@Param("businessId") String businessId, @Param("isWeb") Boolean isWeb);

	/**
	 * 查询共有场景
	 * @param businessId
	 * @param shared
	 * @param isWeb
	 * @return
	 */
	OjLocalScene selectPublicScene(@Param("practiceId") String businessId,
			@Param("shared") Boolean shared, @Param("isWeb") Boolean isWeb);

	/**
	 * 查询课堂组场景
	 * @param groupId
	 * @return
	 */
	OjLocalScene selectGroupScene(@Param("groupId") String groupId);

	/**
	 * 根据用户查询前台场景启动数量
	 * @param userId
	 * @return
	 */
	Integer selectByUser(@Param("userId") String userId);

	/**
	 * 查询资源启动的场景
	 * @param businessId
	 * @param fromWeb
	 * @return
	 */
	List<OjLocalScene> selectByBusiness(@Param("businessId") String businessId,
			@Param("fromWeb") Boolean fromWeb);

	/**
	 * 查询课堂组场景
	 * @param groupIds
	 * @return
	 */
	List<OjLocalScene> selectByGroupIds(@Param("groupIds") List<String> groupIds);
}
