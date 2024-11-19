package com.cyberpeace.rtks.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.rtks.service.bo.situation.RoleMatchRecordContext;

/**
 * RTKS岗位火柴记录Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Mapper
public interface RtksRoleMatchRecordMapper {

	/**
	 * 存在方式
	 *
	 * @param userId 用户id
	 * @param roleId 岗位id
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	Boolean existBy(@Param("userId") String userId, @Param("roleId") String roleId);

	/**
	 * 更新记录
	 *
	 * @param userId 用户id
	 * @param roleId 岗位id
	 * @param match  火柴
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	void updateRecord(@Param("userId") String userId, @Param("roleId") String roleId,
			@Param("match") Integer match);

	/**
	 * 插入记录
	 *
	 * @param userId 用户id
	 * @param roleId 岗位id
	 * @param match  火柴
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	void insertRecord(@Param("userId") String userId, @Param("roleId") String roleId,
			@Param("match") Integer match);

	/**
	 * 查询全部由现存角色
	 *
	 * @return {@code List<RoleMatchRecordContext> }
	 * @throws
	 * @see List<RoleMatchRecordContext>
	 */
	List<RoleMatchRecordContext> selectAllByExistingRoles();

	/**
	 * 获取第一记录时间
	 *
	 * @return {@code Date }
	 * @throws
	 * @see Date
	 */
	Date getFirstRecordTime();
}
