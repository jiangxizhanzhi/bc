package com.cyberpeace.cr.redvsblue.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RbCombatLogMapper extends BaseMapper<RbCombatLog> {

	RbCombatLog selectOneByOriginalLog(String originalLog);

	List<RbCombatLog> selectOneByRoomIdAndAgentIpAndIsLostHost(@Param("roomId") String roomId, @Param("agentIp") String agentIp, @Param("isLostHost") Boolean isLostHost);

	List<RbCombatLog> selectByMainLogId(@Param("mainLogId") String mainLogId);
}
