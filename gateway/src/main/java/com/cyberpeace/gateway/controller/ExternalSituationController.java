package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.model.DynamicSchemeProcess;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.DynamicSchemeService;
import com.cyberpeace.situation.service.SceneConfigService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "外部接口-态势相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/situation")
public class ExternalSituationController {

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private SceneConfigService sceneConfigService;

	@Autowired
	private DynamicSchemeService dynamicSchemeService;

	@CrossOrigin("*")
	@ApiOperation("获取场景拓扑")
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

	@ApiOperation("结束/暂停动效方案")
	@GetMapping("/stopDynamicScheme")
	public BaseRsp<Boolean> stopDynamicScheme(@ApiParam(name = "scene_id", value = "场景编号")
												  @RequestParam(value = "scene_id") String sceneId){
		sceneConfigService.clearSceneCode(sceneId);
		log.info("sceneId:[" + sceneId + "] has paused");
		return new BaseRsp<>(true);
	}

	@ApiOperation("继续动效方案")
	@GetMapping("/continueDynamicScheme")
	public BaseRsp<Boolean> continueDynamicScheme(@ApiParam(name = "scene_id", value = "场景编号")
												  @RequestParam(value = "scene_id") String sceneId){
		log.info("topoId:[" + sceneId + "] continue");
		DynamicSchemeProcess dynamicSchemeProcess = sceneConfigService.getDynamicSchemeCache(sceneId);
		dynamicSchemeService.pushDynamicSchemeToRoom(dynamicSchemeProcess.getDynamicSchemeReq(),
				dynamicSchemeProcess.getStageName(),dynamicSchemeProcess.getAttackMessageDescription());
		return new BaseRsp<>(true);
	}

}
