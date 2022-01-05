package com.cyberpeace.cr.singletrain.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.controller.req.SubmitMissionReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.bo.*;

public interface SingleRoomService extends IService<SingleRoom> {

	SingleRoomBo createRoom(String businessTemplateId);

	SingleRoomBo getRoomInfo(String businessTemplateId);

	Boolean deleteRoom(String businessTemplateId);

	ListRsp<SingleRoomListBo> getSingleTrainPageList(SingleTrainListReq queryParams);

	SingleMissionBo getMission(String businessTemplateId, String missionId);

	Boolean submitMission(String businessTemplateId, SubmitMissionReq requestData);

	List<TrainRecordBo> getTrainRecord(String businessTemplateId);

	String getTrainRecordMarkdown(String businessTemplateId);

	String getTrainManual(String businessTemplateId);

	TrainTimeLineBo getTimeLineData(String businessTemplateId);

	/**
	 * 单兵查看场景
	 */
	SceneViewContext viewScene(String businessTemplateId);

	/**
	 * 查找单兵训练房间
	 * @param businessTemplateId 业务模板id
	 * @param userId 用户id
	 * @return
	 */
	SingleRoom getByBusinessTemplateIdAndUserId(String businessTemplateId, String userId);

	/**
	 * @param businessId 用于消息和ws的id， 格式为 businessTemplateId_userId
	 * @return
	 */
	SingleRoom getByBusinessId(String businessId);

	void clearLocal(SingleRoom room);

	/**
	 * 获取某模板关联的所有房间
	 */
	List<SingleRoom> getByBusinessTemplateId(String businessTemplateId);
}
