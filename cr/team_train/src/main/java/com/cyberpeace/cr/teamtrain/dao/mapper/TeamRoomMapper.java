package com.cyberpeace.cr.teamtrain.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom
 */
@Mapper
@Repository
public interface TeamRoomMapper extends BaseMapper<TeamRoom> {

	TeamRoomData getTeamRoomRspByRoomId(String resourceId);

	IPage<TeamRoomData> listTeamRoom(Page page, ListQuery listQuery);
}
