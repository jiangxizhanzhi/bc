package com.cyberpeace.rtks.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.rtks.service.bo.situation.RoleMatchRecordContext;

/**
 * RTKS角色火柴记录Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
public interface RtksRoleMatchRecordMapper {

	/**
	 * 存在方式
	 *
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @return {@code Boolean }
	 */
	Boolean existBy(@Param("userId") String userId, @Param("roleId") String roleId);

	/**
	 * 更新记录
	 *
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @param match  火柴
	 */
	void updateRecord(@Param("userId") String userId, @Param("roleId") String roleId,
			@Param("match") Integer match);

	/**
	 * 插入记录
	 *
	 * @param userId 用户id
	 * @param roleId 角色id
	 * @param match  火柴
	 */
	void insertRecord(@Param("userId") String userId, @Param("roleId") String roleId,
			@Param("match") Integer match);

	/**
	 * 查询都由现存角色
	 *
	 * @return {@code List<RoleMatchRecordContext> }
	 */
	List<RoleMatchRecordContext> selectAllByExistingRoles();

	/**
	 * 获取第一记录时间
	 *
	 * @return {@code Date }
	 */
	Date getFirstRecordTime();
}
