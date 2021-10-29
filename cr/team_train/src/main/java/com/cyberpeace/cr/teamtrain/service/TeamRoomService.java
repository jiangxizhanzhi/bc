package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomOutput;

/**
 *
 */
public interface TeamRoomService extends IService<TeamRoom> {

    TeamRoomOutput getTeamRoomOutputById(String resourceId);
}
