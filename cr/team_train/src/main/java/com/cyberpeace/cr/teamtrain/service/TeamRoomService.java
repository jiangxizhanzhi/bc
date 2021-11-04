package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;

/**
 *
 */
public interface TeamRoomService extends IService<TeamRoom> {

	/** 通过id获取团队训练
	 * @param resourceId
	 * @return
	 */
	TeamRoomData getTeamRoomRspByRoomId(String resourceId);

	/** 创建实例
	 * @param teamTrainRoomContext
	 * @return
	 */
	TeamTemplate create(TeamTrainRoomContext teamTrainRoomContext);

	/** 更新实例
	 * @param teamTrainRoomContext
	 * @param teamRoom
	 * @return
	 */
	TeamTemplate updateTeamRoom(TeamTrainRoomContext teamTrainRoomContext, TeamRoom teamRoom);

	/** 列表查询团队训练
	 * @param listQuery
	 * @return
	 */
	IPage<TeamRoomData> list(ListQuery listQuery);

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
