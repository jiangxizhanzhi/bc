package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.service.SceneService;
import com.cyberpeace.scene.service.TopologyService;
import com.cyberpeace.scene.service.bo.SceneData;
import com.cyberpeace.scene.service.bo.ad.AdDeleteSceneReq;
import com.cyberpeace.scene.service.bo.ad.AdSceneStartReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "外部接口-topo相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/topo")
public class ExternalSceneController {

	@Autowired
	private SceneService sceneService;

	@Autowired
	private TopologyService topologyService;

	@GetMapping("")
	@ApiOperation(value = "获取拓扑结构")
	public BaseRsp<JSONObject>
			getTopology(@ApiParam(value = "拓扑结构id", name = "id") @RequestParam("id") String id) {
		if (id.startsWith(CommonConstant.AD_PREFIX)) {
			TopologyEntity entity = topologyService.getById(id);
			return new BaseRsp<>(JSON.parseObject(entity.getTopology()));
		} else {
			JSONObject topology = topologyService.getTopology(id);
			return new BaseRsp<>(topology);
		}
	}

	@ApiOperation("复制topo")
	@PostMapping("/sync")
	public BaseRsp<JSONObject> copyTopo(@RequestBody JSONObject req) {
		log.info("ad copy topo request: {}", req);
		TopologyEntity entity = new TopologyEntity();
		entity.setTopologyId(CommonConstant.AD_PREFIX + IDGeneratorUtil.getID());
		req.put("id", entity.getTopologyId());
		entity.setTopology(JsonUtil.toJsonString(req));
		topologyService.save(entity);
		return this.getTopology(entity.getTopologyId());
	}

	@ApiOperation("删除场景")
	@DeleteMapping("/delete/scene")
	public BaseRsp<Void> deleteScene(@RequestBody AdDeleteSceneReq req) {
		sceneService.destroyScene(req.getId());
		return new BaseRsp<>();
	}

	@ApiOperation("拓扑启动场景")
	@PostMapping("/start/scene")
	public BaseRsp<JSONObject> startSceneByTopo(@RequestBody AdSceneStartReq req) {
		sceneService.verify(req.getContext().getAccessUserId());
		SceneEntity scene = sceneService.startSceneByTopo(req);

		return this.getSceneBySceneId(req.getContext().getAccessUserId(), scene.getResourceId());
	}

	@ApiOperation("获取指定id场景实例")
	@GetMapping("/scene")
	public BaseRsp<JSONObject>
			getSceneBySceneId(@ApiParam(value = "用户id",
					name = "accessUserId") @RequestParam("accessUserId") String accessUserId,
					@ApiParam(value = "场景实例id", name = "id") @RequestParam("id") String id) {

		SceneData sceneData = sceneService.getSceneDataBySceneId(id);
		JSONObject res = new JSONObject();
		res.put(JsonKeyConstant.SCENE_DATA, sceneData);
		res.put(JsonKeyConstant.ID, id);
		return new BaseRsp<>(res);
	}

}
