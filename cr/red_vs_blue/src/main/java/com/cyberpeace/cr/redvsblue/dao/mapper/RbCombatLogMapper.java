package com.cyberpeace.cr.redvsblue.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RbCombatLogMapper extends BaseMapper<RbCombatLog> {

	RbCombatLog selectOneByOriginalLog(String originalLog);

	RbCombatLog selectOneByRoomIdAndAgentIpAndIsLostHost(@Param("roomId") String roomId, @Param("agentIp") String agentIp, @Param("isLostHost") Boolean isLostHost);
}
