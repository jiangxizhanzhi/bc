package com.cyberpeace.integratation.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.req.*;

public interface RemoteSituationService {

	/**
	 * 根据任务获取科目list
	 * @param taskIds 任务id
	 * @return
	 */
	List<SituationSubject> getSubjectByTasks(List<String> taskIds);

	/**
	 * 根据任务获取科目list
	 * @param taskId 任务id
	 * @return
	 */
	List<SituationSubject> getSubjectByTask(String taskId);

	/**
	 * 根据任务类型获取任务list
	 * @param type 任务类型
	 * @param resourceId taskId
	 * @return
	 */
	List<SituationTask> getTasks(String type, String resourceId);

	/**
	 * 获取所有想定方案
	 * @return
	 */
	List<SituationPlan> getPlans();

	/**
	 * 获取分靶场详情
	 * @return SubRangeInfoContext
	 */
	SubRangeInfoContext getSubRangeInfo(String subRangeId);

	/**
	 * 获取LG中心详情
	 * @return CenterInfoContext
	 */
	CenterInfoContext getCenterInfo(String centerId);

	/**
	 * 获取平台列表
	 * @return List<PlatformContext>
	 */
	List<PlatformContext> getPlatformList(String taskId, String subjectId, String subRangeId);

	/**
	 * 获取网络信息
	 * @return NetworkInfoContext
	 */
	NetworkInfoContext getNetworkInfo(NodeListApiReq nodeListApiReq);

	/**
	 * 获取任务列表
	 * @return
	 */
	ListRsp<TaskContext> getTaskList(TaskListReq taskListReq);

	/**
	 * 获取节点基础信息
	 * @return
	 */
	NodeInfoContext getNodeInfo(NodeIdApiReq nodeIdApiReq);

	/**
	 * 获取节点行为数据
	 * @return
	 */
	ListRsp<NodeActionContext> getNodeAction(NodeActionReq nodeActionReq);

	/**
	 * 获取节点行为趋势
	 * @return
	 */
	List<NodeTrendContext> getNodeTrend(NodeIdApiReq nodeIdApiReq);

	/**
	 * 获取用户行为数据
	 * @return
	 */
	ListRsp<UserActionContext> getUserAction(NodeIdPageReq nodeIdPageReq);

	/**
	 * 获取用户行为趋势
	 * @return
	 */
	List<UserTrendContext> getUserTrend(NodeIdApiReq nodeIdApiReq);

	/**
	 * 获取任务事件列表
	 * @return
	 */
	List<TaskEventContext> getTaskEventList(TaskEventReq taskEventReq);

	/**
	 * 获取攻防事件列表
	 * @return
	 */
	List<AdEventContext> getAdEventList(EventReq eventReq);

	/**
	 * 获取节点状态列表
	 * @return
	 */
	List<NodeEventContext> getNodeEventList(EventReq eventReq);

	/**
	 * 获取拓扑
	 * @return TopoContext
	 */
	TopoContext getTopo(TopoReq topoReq);
}
