package com.cyberpeace.cr.teamtrain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom
 */
@Mapper
@Repository
public interface TeamRoomMapper extends BaseMapper<TeamRoom> {

	TeamRoomData getTeamRoomRspByRoomId(String resourceId);

	IPage<TeamRoomData> listTeamRoom(Page<TeamRoomData> page, ListQuery listQuery);

	TeamRoom selectByLocalSceneId(String localSceneId);

	TrainingCount getTrainingCount(@Param("roomIds") List<String> roomIds,
			@Param("completedStatus") Integer completedStatus);

	List<TeamRoom> selectUnfinishedByIds(@Param("roomIds") List<String> roomIds,
			@Param("endCode") Integer endCode);

	/**
	 * 用于重名校验
	 * @param name 
	 * @return
	 */
	Boolean existByName(@Param("name") String name);
}
