package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	OjLocalScene selectByGroup(@Param("groupId") String groupId);

}
