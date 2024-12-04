package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * OJ当地场景Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface OjLocalSceneMapper extends BaseMapper<OjLocalScene> {

	/**
	 * 查询私有场景
	 *
	 * @param userId
	 * @param businessId
	 * @param isWeb
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 * @see OjLocalScene
	 */
	OjLocalScene selectPrivateScene(@Param("userId") String userId,
			@Param("businessId") String businessId, @Param("isWeb") Boolean isWeb);

	/**
	 * 查询共有场景
	 *
	 * @param businessId
	 * @param shared
	 * @param isWeb
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see Boolean
	 * @see OjLocalScene
	 */
	OjLocalScene selectPublicScene(@Param("practiceId") String businessId,
			@Param("shared") Boolean shared, @Param("isWeb") Boolean isWeb);

	/**
	 * 查询课堂组场景
	 *
	 * @param groupId
	 * @return
	 * @throws
	 * @see String
	 * @see OjLocalScene
	 */
	OjLocalScene selectGroupScene(@Param("groupId") String groupId);

	/**
	 * 根据用户查询场景启动数量
	 *
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer selectByUser(@Param("userId") String userId);

	/**
	 * 查询资源启动的场景
	 *
	 * @param businessId
	 * @param fromWeb
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see List<OjLocalScene>
	 */
	List<OjLocalScene> selectByBusiness(@Param("businessId") String businessId,
			@Param("fromWeb") Boolean fromWeb);

	/**
	 * 查询课堂组场景
	 *
	 * @param groupIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see List<OjLocalScene>
	 */
	List<OjLocalScene> selectByGroupIds(@Param("groupIds") List<String> groupIds);
}
