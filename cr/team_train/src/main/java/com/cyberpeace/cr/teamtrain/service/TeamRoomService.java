package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;

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
	TeamTemplate create(TeamTrainRoomContext input);

	/** 更新实例
	 * @param input
	 * @param businessTemplate
	 * @return
	 */
	TeamTemplate updateTeamRoom(TeamTrainTemplateContext input, BusinessTemplate businessTemplate);

	/** 列表查询团队训练
	 * @param listQuery
	 * @return
	 */
	ListRsp<TeamRoomRsp> list(ListQuery listQuery);

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
