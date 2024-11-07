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
 * @date 2024/11/07
 */
@Mapper
public interface RtksSituationMapper {

	/**
	 * 获取由用户id和角色id和类型
	 *
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @param code   代号
	 * @return {@code SituationLog }
	 */
	SituationLog getByUserIdAndRoleIdAndType(@Param("userId") String userId,
			@Param("roleId") String roleId, @Param("code") Integer code);

	/**
	 * 计数由用户id和角色id和类型
	 *
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @param type   类型
	 * @return {@code Boolean }
	 */
	Boolean countByUserIdAndRoleIdAndType(@Param("userId") String userId,
			@Param("roleId") String roleId, @Param("type") Integer type);

	/**
	 * 创建日志
	 *
	 * @param userId   用户id
	 * @param userName 用户名称
	 * @param roleId   角色id
	 * @param roleName 角色名称
	 * @param type     类型
	 */
	void createLog(@Param("userId") String userId, @Param("userName") String userName,
			@Param("roleId") String roleId, @Param("roleName") String roleName,
			@Param("type") Integer type);

	/**
	 * 计数由用户id和类型
	 *
	 * @return {@code List<MultiRoleContext> }
	 */
	List<MultiRoleContext> countByUserIdAndType();

	/**
	 * 获取时刻描述
	 *
	 * @return {@code List<SituationLog> }
	 */
	List<SituationLog> getMomentsDesc();

	/**
	 * 获取水平
	 *
	 * @param masterId 主人id
	 * @return {@code List<LevelRoleContext> }
	 */
	List<LevelRoleContext> getLevels(@Param("masterId") String masterId);

	/**
	 * 获取都日志由类型
	 *
	 * @param type 类型
	 * @return {@code List<SituationLog> }
	 */
	List<SituationLog> getAllLogByType(@Param("type") Integer type);
}
