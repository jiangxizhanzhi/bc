package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomReq;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.model.room.TeamTrainRoomReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyReq;

/**
 *
 */
public interface TeamRoomService extends IService<TeamRoom> {

	TeamRoomRsp getTeamRoomOutputById(String resourceId);

	TeamTemplate create(TeamTrainRoomReq input);

	TeamTemplate updateTeamRoom(TeamTrainTemplateModifyReq input,
			BusinessTemplate businessTemplate);

	ListRsp<TeamRoomRsp> list(TeamRoomReq listReq);

	int changeTrainingStatus(String resource_id);

	int getTrainingStatus(String resource_id);
}
