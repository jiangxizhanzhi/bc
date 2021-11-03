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

	/** 通过id获取团队训练
	 * @param resourceId
	 * @return
	 */
	TeamRoomRsp getTeamRoomRspByRoomId(String resourceId);

	/** 创建实例
	 * @param input
	 * @return
	 */
	TeamTemplate create(TeamTrainRoomReq input);

	/** 更新实例
	 * @param input
	 * @param businessTemplate
	 * @return
	 */
	TeamTemplate updateTeamRoom(TeamTrainTemplateModifyReq input,
			BusinessTemplate businessTemplate);

	/** 列表查询团队训练
	 * @param listReq
	 * @return
	 */
	ListRsp<TeamRoomRsp> list(TeamRoomReq listReq);

	/** 修改团队训练状态
	 * @param resourceId
	 * @return
	 */
	Integer changeTrainingStatus(String resourceId);

	/** 获取团队训练状态
	 * @param resourceId
	 * @return
	 */
	Integer getTrainingStatus(String resourceId);
}
