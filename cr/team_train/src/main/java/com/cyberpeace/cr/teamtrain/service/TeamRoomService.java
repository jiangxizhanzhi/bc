package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomRes;
import com.cyberpeace.cr.teamtrain.model.room.TeamTrainRoomReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyInput;

/**
 *
 */
public interface TeamRoomService extends IService<TeamRoom> {

	TeamRoomRes getTeamRoomOutputById(String resourceId);

	TeamTemplate create(TeamTrainRoomReq input);

	TeamTemplate updateTeamRoom(TeamTrainTemplateModifyInput input,
			BusinessTemplate businessTemplate);
}
