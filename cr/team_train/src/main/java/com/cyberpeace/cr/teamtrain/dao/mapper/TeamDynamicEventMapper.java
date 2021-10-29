package com.cyberpeace.cr.teamtrain.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamDynamicEvent;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.TeamDynamicEvent
 */
@Mapper
@Repository
public interface TeamDynamicEventMapper extends BaseMapper<TeamDynamicEvent> {

}
