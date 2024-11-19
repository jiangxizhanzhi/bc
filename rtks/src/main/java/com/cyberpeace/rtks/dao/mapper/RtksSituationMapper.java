package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.rtks.dao.entity.SituationLog;
import com.cyberpeace.rtks.service.bo.situation.LevelRoleContext;
import com.cyberpeace.rtks.service.bo.situation.MultiRoleContext;

/**
 * RTKS态势Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Mapper
public interface RtksSituationMapper {

	/**
	 * 获取由用户id和岗位id和类型
	 *
	 * @param userId 用户id
	 * @param roleId 岗位id
	 * @param code   代号
	 * @return {@code SituationLog }
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see SituationLog
	 */
	SituationLog getByUserIdAndRoleIdAndType(@Param("userId") String userId,
			@Param("roleId") String roleId, @Param("code") Integer code);

	/**
	 * 计数由用户id和岗位id和类型
	 *
	 * @param userId 用户id
	 * @param roleId 岗位id
	 * @param type   类型
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see Boolean
	 */
	Boolean countByUserIdAndRoleIdAndType(@Param("userId") String userId,
			@Param("roleId") String roleId, @Param("type") Integer type);

	/**
	 * 创建日志
	 *
	 * @param userId   用户id
	 * @param userName 用户名称
	 * @param roleId   岗位id
	 * @param roleName 岗位名称
	 * @param type     类型
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see Integer
	 */
	void createLog(@Param("userId") String userId, @Param("userName") String userName,
			@Param("roleId") String roleId, @Param("roleName") String roleName,
			@Param("type") Integer type);

	/**
	 * 计数由用户id和类型
	 *
	 * @return {@code List<MultiRoleContext> }
	 * @throws
	 * @see List<MultiRoleContext>
	 */
	List<MultiRoleContext> countByUserIdAndType();

	/**
	 * 获取时刻描述
	 *
	 * @return {@code List<SituationLog> }
	 * @throws
	 * @see List<SituationLog>
	 */
	List<SituationLog> getMomentsDesc();

	/**
	 * 获取水平
	 *
	 * @param masterId 主人id
	 * @return {@code List<LevelRoleContext> }
	 * @throws
	 * @see String
	 * @see List<LevelRoleContext>
	 */
	List<LevelRoleContext> getLevels(@Param("masterId") String masterId);

	/**
	 * 获取全部日志由类型
	 *
	 * @param type 类型
	 * @return {@code List<SituationLog> }
	 * @throws
	 * @see Integer
	 * @see List<SituationLog>
	 */
	List<SituationLog> getAllLogByType(@Param("type") Integer type);
}
