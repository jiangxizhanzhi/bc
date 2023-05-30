package com.cyberpeace.cr.singletrain.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.crbase.service.context.manual.ManualStageBo;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.controller.req.SubmitMissionReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.bo.*;
import com.cyberpeace.cr.singletrain.service.bo.mission.SingleMatrixProgressRsp;
import com.cyberpeace.cr.singletrain.service.bo.record.RecordDetail;
import com.cyberpeace.cr.singletrain.service.bo.record.TrainingRecordListBo;
import com.cyberpeace.cs.service.context.IdNameType;

public interface SingleRoomService extends IService<SingleRoom> {

	/**
	 * 创建单兵训练实例
	 * @param businessTemplateId 业务模板id
	 * @return 单兵训练实例
	 */
	SingleRoomBo createRoom(String businessTemplateId);

	/**
	 * 获取单兵训练实例数据
	 * @param businessTemplateId 业务模板id
	 * @return 单兵训练实例数据
	 */
	SingleRoomBo getRoomInfo(String businessTemplateId);

	/**
	 * 删除单兵训练实例
	 * @param businessTemplateId 业务模板id
	 * @return 删除结果（布尔值）
	 */
	Boolean deleteRoom(String businessTemplateId);

	/**
	 * 删除单兵训练实例
	 * @param singleRoom 单兵训练实例
	 * @return 结果（布尔值）
	 */
	Boolean deleteRoom(SingleRoom singleRoom);

	/**
	 * 分页获取单兵训练实例数据
	 * @param queryParams 分页查询体
	 * @return 单兵训练实例分页数据
	 */
	ListRsp<SingleRoomListBo> getSingleTrainPageList(SingleTrainListReq queryParams);

	/**
	 * 获取单兵训练实例任务
	 * @param businessTemplateId 业务模板id
	 * @param missionId 任务id
	 * @return 单兵训练实例任务信息
	 */
	SingleMissionBo getMission(String businessTemplateId, String missionId);

	/**
	 * 提交单兵训练任务
	 * @param businessTemplateId 业务模板id
	 * @param requestData 用户提交答案
	 * @return 请求结果（布尔值）
	 */
	Boolean submitMission(String businessTemplateId, SubmitMissionReq requestData);

	/**
	 * 获取业务模板的训练记录
	 * @param businessTemplateId 业务模板id
	 * @return 训练记录列表
	 */
	List<TrainRecordBo> getTrainRecord(String businessTemplateId);

	/**
	 * 获取训练记录Markdown 文档
	 * @param businessTemplateId 业务模板id
	 * @return Markdown字符串
	 */
	String getTrainRecordMarkdown(String businessTemplateId);

	/**
	 * 获取训练手册
	 * @param businessTemplateId 业务模板id
	 * @return 训练手册字符串
	 */
	List<ManualStageBo> getTrainManual(String businessTemplateId);

	/**
	 * 获取时间线数据
	 * @param businessTemplateId 业务模板id
	 * @return 时间线数据
	 */
	TrainTimeLineBo getTimeLineData(String businessTemplateId);

	/**
	 * 单兵查看场景
	 * @param businessTemplateId 业务模板id
	 * @return 数据
	 */
	SceneViewContext viewScene(String businessTemplateId);

	/**
	 * 查找单兵训练房间
	 * @param businessTemplateId 业务模板id
	 * @param userId 用户id
	 * @return 单兵训练房间
	 */
	SingleRoom getByBusinessTemplateIdAndUserId(String businessTemplateId, String userId);

	/**
	 * 处理消息时获取单兵房间
	 * @param businessId 用于消息和ws的id， 格式为 businessTemplateId_userId
	 * @return 单兵训练房间
	 */
	SingleRoom getByBusinessId(String businessId);

	/**
	 * 关闭场景时清除本地数据
	 * @param room 单兵训练实例
	 */
	void clearLocal(SingleRoom room);

	/**
	 * 获取某模板关联的所有房间
	 * @param businessTemplateId 业务模板id
	 * @return 关联的所有房间
	 */
	List<SingleRoom> getByBusinessTemplateId(String businessTemplateId);

	/**
	 * 获取用户训练历史记录
	 * @param businessTemplateId 业务模板id
	 * @return 训练历史记录
	 */
	List<TrainingRecordListBo> getTrainHistory(String businessTemplateId);

	/**
	 * 训练提交
	 * @param businessTemplateId 业务模板id
	 * @return 当前房间id
	 */
	String submitTrain(String businessTemplateId);

	/**
	 * 获取记录详情
	 *
	 * @param roomId 房间id
	 * @return {@code RecordDetail}
	 */
	RecordDetail getRecordDetail(String roomId);

	/**
	 * 获取任务矩阵和进度
	 *
	 * @param room 房间
	 * @return {@code SingleMatrixProgressRsp}
	 */
	SingleMatrixProgressRsp getMatrixProgress(SingleRoom room);

	/**
	 * 获取人物矩阵和进度
	 *
	 * @param roomId 房间id
	 * @return {@code SingleMatrixProgressRsp}
	 */
	SingleMatrixProgressRsp getMatrixRsp(String roomId);

	/**
	 * 获取资产清单
	 *
	 * @param req 请求体
	 * @return {@code ListRsp<JSONObject>}
	 */
	ListRsp<JSONObject> getAssetList(AssetPageReq req);

	List<IdNameType> getOsNames(String businessTemplateId);
}
