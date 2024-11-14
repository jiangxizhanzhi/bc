package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 外部态势Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "外部接口-态势相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/situation")
public class ExternalSituationController {

	/**
	 * 态势处理器Service
	 */
	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 拓扑处理器Service
	 */
	@Autowired
	private TopoHandlerService topoHandlerService;

	/**
	 * 拓扑
	 *
	 * @param sceneId 场景id
	 * @param topoId  拓扑id
	 * @return {@code BaseRsp<SituationTopo> }
	 */
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

}
