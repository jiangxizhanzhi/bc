package com.cyberpeace.cr.redvsblue.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RbCombatLogMapper extends BaseMapper<RbCombatLog> {

	RbCombatLog selectOneByOriginalLog(String originalLog);
}
