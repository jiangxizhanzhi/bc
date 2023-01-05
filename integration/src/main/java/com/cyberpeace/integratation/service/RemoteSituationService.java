package com.cyberpeace.integratation.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.req.*;
import com.cyberpeace.integratation.service.bo.situation.*;

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
	 * @param platformReq
	 */
	NetworkInfoContext getNetworkInfo(PlatformReq platformReq);

	/**
	 * 获取任务列表
	 * @return
	 */
	ListRsp<TaskContext> getTaskList(TaskListReq taskListReq);

	/**
	 * 获取科目列表
	 * @return
	 */
	List<SubjectContext> getSubjectList(SubjectListReq subjectListReq);

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
	 * 获取平台节点链路状态信息
	 * @return
	 */
	NodeConnectionContext getNodeConnection(NodeIdApiReq nodeIdApiReq);

	/**
	 * 获取平台节点脆弱性分析信息
	 * @return
	 */
	NodeWeakInfoContext getNodeWeakInfo(NodeIdApiReq nodeIdApiReq);

	/**
	 * 获取平台节点脆弱性列表
	 * @return
	 */
	ListRsp<NodeWeakContext> getNodeWeakList(NodeIdPageReq nodeIdPageReq);

	/**
	 * 获取任务事件列表
	 * @return
	 */
	List<TaskEventContext> getTaskEventList(TaskEventReq taskEventReq, String featureId);

	/**
	 * 获取攻防事件列表
	 * @return
	 */
	List<AdEventContext> getAdEventList(EventReq eventReq, String featureId);

	/**
	 * 获取节点状态列表
	 * @return
	 */
	List<NodeEventContext> getNodeEventList(EventReq eventReq, String featureId);

	/**
	 * 获取拓扑
	 * @return TopoContext
	 */
	TopoContext getTopo(TopoReq topoReq);

	/**
	 * 想定-获取任务下可视化方案列表
	 * @param req
	 * @return
	 */
	List<SchemeRspBo> getSchemeList(SituationTaskReq req);

	/**
	 * 想定-获取任务/科目可视化方案
	 * @param schemeId
	 * @return
	 */
	SchemeVisualizationBo getScheme(String schemeId);

	/**
	 * 配管-获取科目拓扑变更记录
	 * @param req
	 * @return
	 */
	List<TopoHistoryBo> getTopoHistory(SituationTaskReq req);

	/**
	 * 想定-发布大屏
	 * @param schemeId
	 * @param dashboardId
	 */
	Boolean releaseScreen(String schemeId, String dashboardId);

	List<BcContext> getBcList(String type);
}
