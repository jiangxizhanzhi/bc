package com.cyberpeace.cr.teamtrain.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamScore;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.TeamScore
 */
@Mapper
@Repository
public interface TeamScoreMapper extends BaseMapper<TeamScore> {

	TeamScore getByRoomIdAndUser(String roomId, String userId);
}
