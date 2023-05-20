package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.CrScore
 */
@Mapper
@Repository
public interface CrScoreMapper extends BaseMapper<CrScore> {

	CrScore getByRoomIdAndUser(String roomId, String userId);

	List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds);

    List<CrScore> selectByRoomId(@Param("roomId") String roomId);
}
