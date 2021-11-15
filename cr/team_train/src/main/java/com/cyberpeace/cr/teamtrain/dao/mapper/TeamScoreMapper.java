package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;

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

	List<TeamScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

}
