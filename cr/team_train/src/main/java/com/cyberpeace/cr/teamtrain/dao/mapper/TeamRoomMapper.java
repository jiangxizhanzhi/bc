package com.cyberpeace.cr.teamtrain.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.teamtrain.controller.TeamRoomReq;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomData;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom
 */
@Mapper
@Repository
public interface TeamRoomMapper extends BaseMapper<TeamRoom> {

	TeamRoomData getTeamRoomOutputById(String resourceId);

	IPage<TeamRoomData> listTeamRoom(Page page, TeamRoomReq listReq);
}
