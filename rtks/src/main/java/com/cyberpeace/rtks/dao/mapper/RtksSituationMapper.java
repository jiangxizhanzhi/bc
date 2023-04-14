package com.cyberpeace.rtks.dao.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.rtks.dao.entity.SituationLog;
import com.cyberpeace.rtks.service.bo.situation.LevelRoleContext;
import com.cyberpeace.rtks.service.bo.situation.MultiRoleContext;
import com.cyberpeace.rtks.service.bo.situation.SituationLogLocal;

@Mapper
public interface RtksSituationMapper {

	SituationLog getByUserIdAndRoleIdAndType(@Param("userId") String userId,
			@Param("roleId") String roleId, @Param("code") Integer code);

	Boolean countByUserIdAndRoleIdAndType(@Param("userId") String userId,
			@Param("roleId") String roleId, @Param("type") Integer type);

	void createLog(@Param("userId") String userId, @Param("userName") String userName,
			@Param("roleId") String roleId, @Param("roleName") String roleName,
			@Param("type") Integer type, @Param("time") LocalDateTime time);

	List<MultiRoleContext> countByUserIdAndType();

	List<SituationLog> getMomentsDesc();

	List<LevelRoleContext> getLevels(@Param("masterId") String masterId);

	List<SituationLogLocal> getAllLogByType(@Param("type") Integer type);

	void clearLog();

	LocalDateTime getFirstLogTime();
}
