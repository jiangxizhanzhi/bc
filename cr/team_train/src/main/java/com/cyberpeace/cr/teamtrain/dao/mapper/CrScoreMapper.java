package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;

import com.cyberpeace.cr.teamtrain.dao.entity.CrScore;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.CrScore
 */
@Mapper
@Repository
public interface CrScoreMapper extends BaseMapper<CrScore> {

	CrScore getByRoomIdAndUser(String roomId, String userId);

	List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

}
