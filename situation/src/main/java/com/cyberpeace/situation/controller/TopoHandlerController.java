package com.cyberpeace.situation.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.controller.req.FlowLogReq;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.TopoHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "态势拓扑")
@RestController
@RequiresAuthentication
@RequestMapping("/situation")
public class TopoHandlerController {

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
		SituationTopo situationTopo = topoHandlerService.getSituationTopo(sceneData);

		return new BaseRsp<>(situationTopo);
	}

	@ApiOperation("攻击动态")
	@GetMapping("/dynamic/attack")
	public List<JSONObject> attackDynamic(FlowLogReq flowLogReq) {

		// 获取流量日志
		List<JSONObject> flowLogs = topoHandlerService.getFlowLogs(flowLogReq);

		// 处理流量日志
		List<JSONObject> wsMultiNodeDynamics = topoHandlerService
				.getAttackEventList(flowLogs, flowLogReq.getSceneId());

		return wsMultiNodeDynamics;
	}
}
