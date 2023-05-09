package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.integratation.model.situation.PlatformContext;
import com.cyberpeace.integratation.model.situation.TopoContext;
import com.cyberpeace.integratation.model.situation.layer.LayerEventContext;
import com.cyberpeace.integratation.model.situation.req.*;
import com.cyberpeace.integratation.model.situation.res.EventAxisRsp;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.bo.situation.ThirdPartyTopoBo;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.res.*;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.SituationGisService;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 外部数据-事件分析
 *
 * @author chenhy
 * @date 2023/05/08
 */
@Api(tags = "外部数据-事件分析")
@RestController
@RequiresAuthentication
@RequestMapping("/third/")
public class ExternalDataAnalysisController {

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private SituationGisService situationGisService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 获取事件轴
	 *
	 * @param taskBaseReq 基本任务信息请求
	 * @return {@code BaseRsp<EventAxisRsp>}
	 */
	@ApiOperation("态势-获取事件轴")
	@PostMapping("/event/axis/")
	public BaseRsp<EventAxisRsp> getEventAxis(@RequestBody @Validated TaskBaseReq taskBaseReq) {
		EventAxisRsp rsp = remoteSituationService.getEventAxis(taskBaseReq);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 获取跨层分析拓扑
	 *
	 * @param taskBaseReq 基本任务信息请求
	 * @return {@code BaseRsp<TopoContext>}
	 */
	@ApiOperation("态势-获取跨层分析拓扑")
	@PostMapping("/topo/cross_layer/")
	public BaseRsp<TopoContext> getCrossLayerTopo(@RequestBody @Validated TaskBaseReq taskBaseReq) {
		TopoContext rsp = remoteSituationService.getCrossLayerTopo(taskBaseReq);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 获取跨层分析事件
	 *
	 * @param taskBaseReq 基本任务信息请求
	 * @return {@code BaseRsp<List<LayerEventContext>>}
	 */
	@ApiOperation("态势-获取跨层分析事件")
	@PostMapping("/event/cross_layer/")
	public BaseRsp<List<LayerEventContext>>
			getCrossLayerEvent(@RequestBody @Validated TaskDashboardReq taskBaseReq) {
		List<LayerEventContext> rsp = remoteSituationService.getCrossLayerEvent(taskBaseReq);
		// 推送WS消息
		WsMessage<List<LayerEventContext>> wsMessage = new WsMessage<>(
				MessageType.LayerEvent.getDesc(), rsp);
		situationHandlerService.pushDataToDashboard(wsMessage, taskBaseReq.getDashboardId(),
				taskBaseReq.getFeatureId());
		return new BaseRsp<>(rsp);
	}

	/**
	 * 获取7层分析应用层数据
	 *
	 * @param taskOsiReq OSI任务信息请求
	 * @return {@code BaseRsp<LayerDataAppRes>}
	 */
	@ApiOperation("态势-获取7层分析应用层数据")
	@PostMapping("/layer/data/app/")
	public BaseRsp<LayerDataAppRes> getLayerDataApp(@RequestBody @Validated TaskOsiReq taskOsiReq) {

		// 构造外部系统OSI信息请求体
		LayerDataAppRes layerDataAppRes = new LayerDataAppRes();
		TaskSizeReq taskSizeReq = BeanUtils.deepCopy(taskOsiReq, TaskSizeReq.class);

		// 设置用户活跃度数据
		taskSizeReq.setSize(10);
		layerDataAppRes.setUserActivity(remoteSituationService.getUserActivity(taskSizeReq));

		// 设置攻击节点排名数据
		taskSizeReq.setSize(5);
		layerDataAppRes
				.setNodeAttackStatistics(remoteSituationService.getNodeRankAttack(taskSizeReq));

		// 设置受威胁节点分布数据
		layerDataAppRes.setThreatenedDistribute(
				remoteSituationService.getNodeThreatenedDistribute(taskOsiReq));
		return new BaseRsp<>(layerDataAppRes);
	}

	/**
	 * 获取7层分析传输层数据
	 *
	 * @param taskOsiReq OSI任务信息请求
	 * @return {@code BaseRsp<LayerDataTransportRes>}
	 */
	@ApiOperation("态势-获取7层分析传输层数据")
	@PostMapping("/layer/data/transport/")
	public BaseRsp<LayerDataTransportRes>
			getLayerDataTransport(@RequestBody @Validated TaskOsiReq taskOsiReq) {

		// 构造外部系统OSI信息请求体
		LayerDataTransportRes layerDataTransportRes = new LayerDataTransportRes();
		TaskSizeReq taskSizeReq = BeanUtils.deepCopy(taskOsiReq, TaskSizeReq.class);

		// 设置传输层流量排名数据
		taskSizeReq.setSize(5);
		layerDataTransportRes.setNodeFlowRank(remoteSituationService.getNodeFlowRank(taskSizeReq));

		// 设置传输层流量统计数据
		layerDataTransportRes
				.setFlowStatistics(remoteSituationService.getFlowStatistics(taskOsiReq));

		// 设置传输层流量趋势数据
		layerDataTransportRes.setFlowTrend(remoteSituationService.getFlowTrend(taskOsiReq));
		return new BaseRsp<>(layerDataTransportRes);
	}

	/**
	 * 获取7层分析网络层数据
	 *
	 * @param taskOsiReq OSI任务信息请求
	 * @return {@code BaseRsp<LayerDataNetRes>}
	 */
	@ApiOperation("态势-获取7层分析网络层数据")
	@PostMapping("/layer/data/net/")
	public BaseRsp<LayerDataNetRes> getLayerDataNet(@RequestBody @Validated TaskOsiReq taskOsiReq) {

		// 构造外部系统OSI信息请求体
		LayerDataNetRes layerDataNetRes = new LayerDataNetRes();
		TaskSizeReq taskSizeReq = BeanUtils.deepCopy(taskOsiReq, TaskSizeReq.class);

		// 获取网络层拓扑数据
		taskSizeReq.setSize(5);
		TopoReq topoReq = BeanUtils.deepCopy(taskOsiReq, TopoReq.class);
		ThirdPartyTopoBo topo = remoteSituationService.getXdTopo(topoReq);

		// 设置网络层拓扑子网数量
		layerDataNetRes.setNetNum(topo.getNetworks().size());

		// 设置网络层节点状态数据
		layerDataNetRes.setNodeStatus(remoteSituationService.getNodeStatusStatistics(taskSizeReq));

		// 设置网络层节点类型数据
		layerDataNetRes
				.setNodeCriticalDegree(remoteSituationService.getNodeCriticalDegree(taskSizeReq));

		// 设置网络层节点类型分布数据
		layerDataNetRes.setNodeTypeDistribute(remoteSituationService.getNodeTypeStatistics(topo));
		return new BaseRsp<>(layerDataNetRes);
	}

	/**
	 * 获取7层分析物理层数据
	 *
	 * @param taskOsiReq OSI任务信息请求
	 * @return {@code BaseRsp<LayerDataPhysicsRes>}
	 */
	@ApiOperation("态势-获取7层分析物理层数据")
	@PostMapping("/layer/data/physics/")
	public BaseRsp<LayerDataPhysicsRes>
			getLayerDataPhysics(@RequestBody @Validated TaskOsiReq taskOsiReq) {

		// 构造外部系统OSI信息请求体
		LayerDataPhysicsRes layerDataPhysicsRes = new LayerDataPhysicsRes();
		TopoReq topoReq = BeanUtils.deepCopy(taskOsiReq, TopoReq.class);

		// 获取物理层拓扑数据
		ThirdPartyTopoBo topo = remoteSituationService.getXdTopo(topoReq);

		// 设置物理层拓扑链路类型分布数据
		layerDataPhysicsRes
				.setConnectionTypeDistribute(remoteSituationService.getLinkTypeStatistics(topo));

		// 设置物理层拓扑链路状态数据
		layerDataPhysicsRes
				.setConnectionStatus(remoteSituationService.getLinkStatusStatistics(taskOsiReq));

		// 设置物理层拓扑节点地理分布数据
		layerDataPhysicsRes.setGeoDistribution(getGeoDistribution(taskOsiReq));
		return new BaseRsp<>(layerDataPhysicsRes);
	}

	/**
	 * 获取7层分析通用数据
	 *
	 * @param taskOsiReq OSI任务信息请求
	 * @return {@code BaseRsp<LayerDataCommonRes>}
	 */
	@ApiOperation("态势-获取7层分析通用数据")
	@PostMapping("/layer/data/common/")
	public BaseRsp<LayerDataCommonRes>
			getLayerDataCommon(@RequestBody @Validated TaskOsiReq taskOsiReq) {
		LayerDataCommonRes layerDataCommonRes = new LayerDataCommonRes();
		layerDataCommonRes.setGeoDistribution(getGeoDistribution(taskOsiReq));
		layerDataCommonRes.setProtocolStatus(remoteSituationService.getNodeProtocols(taskOsiReq));
		return new BaseRsp<>(layerDataCommonRes);
	}

	/**
	 * 获得节点地理分布
	 *
	 * @param taskOsiReq OSI任务信息请求
	 * @return {@code List<String>}
	 */
	private List<String> getGeoDistribution(TaskOsiReq taskOsiReq) {
		// 获取节点地理分布
		List<String> geo = Lists.newArrayList();
		List<PlatformContext> platformList = remoteSituationService.getPlatformList(
				taskOsiReq.getTaskId(), taskOsiReq.getSubjectId(), taskOsiReq.getSubRangeId(),
				taskOsiReq.getSchemeId());
		platformList.forEach(platformContext -> {
			geo.add(situationGisService.getProvince(Float.valueOf(platformContext.getLongitude()),
					Float.valueOf(platformContext.getLatitude())));
		});
		return geo.stream().distinct().collect(Collectors.toList());
	}
}
