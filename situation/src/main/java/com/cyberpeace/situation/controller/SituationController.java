package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.FlowLogReq;
import com.cyberpeace.situation.controller.res.ElementStateRes;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsNodeStatus;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "态势拓扑")
@RestController
@RequiresAuthentication
@RequestMapping("/situation")
public class SituationController {

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@CrossOrigin("*")
	@ApiOperation("拓扑解析")
	@GetMapping("topology")
	public BaseRsp<SituationTopo> topology(
			@ApiParam(name = "scene_id", value = "场景编号") @RequestParam(value = "scene_id",
					required = false) String sceneId,
			@ApiParam(name = "topo_id", value = "拓扑编号") @RequestParam(value = "topo_id",
					required = false) String topoId) {

		// 获取场景/拓扑数据
		SceneData sceneData = topoHandlerService.getSceneData(sceneId, topoId);

		// 处理态势数据
		SituationTopo situationTopo = situationHandlerService.getSituationTopo(sceneData);

		return new BaseRsp<>(situationTopo);
	}

	@ApiOperation("攻击动态")
	@GetMapping("/dynamic/attack")
	public List<WsMessage> attackDynamic(FlowLogReq flowLogReq) {

		// 处理起始时间和结束时间
		// if (flowLogReq.getInterval() != null ) {
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Long endTime = (new Date()).getTime();
		// Long startTime = endTime - flowLogReq.getInterval()*1000;
		// flowLogReq.setEndTime(dateFormat.format(endTime));
		// flowLogReq.setStartTime(dateFormat.format(startTime));
		// }

		// 获取流量日志
		List<JSONObject> flowLogs = topoHandlerService.getFlowLogs(flowLogReq);

		// 处理流量日志
		List<WsMessage> wsMultiNodeDynamics = situationHandlerService.getAttackEventList(flowLogs,
				flowLogReq.getSceneId());

		// 分批推送消息
		situationHandlerService.batchPushDataToRoom(flowLogReq.getSceneId(), wsMultiNodeDynamics, 5,
				5);

		return wsMultiNodeDynamics;
	}

	@ApiOperation("节点信息")
	@GetMapping("element")
	public BaseRsp<ElementStateRes>
			element(@ApiParam(name = "scene_id",
					value = "场景编号") @RequestParam(value = "scene_id") String sceneId,
					@ApiParam(name = "element_id",
							value = "节点编号") @RequestParam(value = "element_id") String elementId) {

		ElementStateRes elementStateRes = topoHandlerService.getElementState(sceneId, elementId);

		return new BaseRsp<>(elementStateRes);
	}

	@ApiOperation("节点状态")
	@GetMapping("node_status")
	public BaseRsp<WsMessage> nodeStatus(@ApiParam(name = "scene_id",
			value = "场景编号") @RequestParam(value = "scene_id") String sceneId) {

		WsNodeStatus wsNodeStatus = topoHandlerService.getNodeStatusMessage(sceneId);

		// 推送ws消息
		WsMessage<WsNodeStatus> wsMessage = new WsMessage<>(MessageType.NodeStatus.getDesc(),
				wsNodeStatus);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
		return new BaseRsp<>(wsMessage);
	}
}
