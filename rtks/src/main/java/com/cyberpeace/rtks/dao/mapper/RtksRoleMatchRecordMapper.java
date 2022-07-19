package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.rtks.service.bo.situation.RoleMatchRecordContext;

@Mapper
public interface RtksRoleMatchRecordMapper {

	Boolean existBy(@Param("userId") String userId, @Param("roleId") String roleId);

	void updateRecord(@Param("userId") String userId, @Param("roleId") String roleId,
			@Param("match") Integer match);

	void insertRecord(@Param("userId") String userId, @Param("roleId") String roleId,
			@Param("match") Integer match);

	List<RoleMatchRecordContext> selectAllByExistingRoles();

}
