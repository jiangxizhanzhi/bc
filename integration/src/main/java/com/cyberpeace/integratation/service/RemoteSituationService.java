package com.cyberpeace.integratation.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.flow.FlowStatisticsContext;
import com.cyberpeace.integratation.model.situation.flow.FlowTrendContext;
import com.cyberpeace.integratation.model.situation.flow.NodeFlowRankContext;
import com.cyberpeace.integratation.model.situation.layer.LayerEventContext;
import com.cyberpeace.integratation.model.situation.node.*;
import com.cyberpeace.integratation.model.situation.req.*;
import com.cyberpeace.integratation.model.situation.res.EventAxisRsp;
import com.cyberpeace.integratation.model.situation.user.UserActivityContext;
import com.cyberpeace.integratation.service.bo.situation.*;

public interface RemoteSituationService {

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
	List<PlatformContext> getPlatformList(String taskId, String subjectId, String subRangeId,
			String schemeId);

	List<PlatformLinkContext> getPlatformLinkList(String taskId, String subjectId, String subRangeId,
												  String schemeId);

	/**
	 * 获取网络信息
	 * @return NetworkInfoContext
	 * @param platformReq
	 */
	NetworkInfoContext getNetworkInfo(PlatformReq platformReq);

	List<NodeTypeStatisticsContext> getNodeTypeStatistics(ThirdPartyTopoBo topo);

	List<TypeStatisticsContext> getLinkTypeStatistics(ThirdPartyTopoBo topo);

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
	TopoContext getTopo(TopoReq topoReq, Boolean adjust);

	ThirdPartyTopoBo getXdTopo(TopoReq topoReq);

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

	Boolean releaseScreen(StatusOrderContext statusOrderContext);

	String getRunId(String subjectId);

	List<NodeBasicInfoContext> getSimilarNodes(NodeIdApiReq nodeIdApiReq);

	TopoContext getCrossLayerTopo(TaskBaseReq taskBaseReq);

	List<LayerEventContext> getCrossLayerEvent(TaskBaseReq taskBaseReq);

	EventAxisRsp getEventAxis(TaskBaseReq taskBaseReq);

	List<NodeThreatenedDistributeContext> getNodeThreatenedDistribute(TaskBaseReq taskBaseReq);

	List<NodeAttackStatisticsContext> getNodeRankAttack(TaskSizeReq taskSizeReq);

	List<UserActivityContext> getUserActivity(TaskSizeReq taskSizeReq);

	FlowStatisticsContext getFlowStatistics(TaskBaseReq taskBaseReq);

	List<NodeFlowRankContext> getNodeFlowRank(TaskSizeReq taskSizeReq);

	FlowTrendContext getFlowTrend(TaskBaseReq taskBaseReq);

	List<StatusStatisticsContext> getNodeStatusStatistics(TaskSizeReq taskSizeReq);

	List<NodeCriticalDegreeContext> getNodeCriticalDegree(TaskSizeReq taskSizeReq);

	List<StatusStatisticsContext> getLinkStatusStatistics(TaskBaseReq taskBaseReq);

	List<NodeMigrationContext> getNodeMigrations(EventReq eventReq, String featureId);

	List<NodeProtocolContext> getNodeProtocols(TaskBaseReq taskBaseReq);

	RangeData getRangeData (String bcId);
}
