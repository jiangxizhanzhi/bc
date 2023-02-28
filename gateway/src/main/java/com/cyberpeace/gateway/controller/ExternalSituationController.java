package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.model.DynamicSchemeProcess;
import com.cyberpeace.situation.model.Instruction;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	private SituationTimeService situationTimeService;

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
		if(dynamicSchemeProcess == null){
			throw new BusinessException(SituationErrorCode.CANNOT_FIND_STOP_DYNAMIC);
		}
		dynamicSchemeService.pushDynamicSchemeToRoom(dynamicSchemeProcess.getDynamicSchemeReq(),
				dynamicSchemeProcess.getStageName(),dynamicSchemeProcess.getAttackMessageDescription());
		return new BaseRsp<>(true);
	}

	@ApiOperation("接收指令")
	@PostMapping("/receiveInstruction")
	public BaseRsp	receiveInstruction(@RequestBody @Validated Instruction instruction) {
		BusinessTemplate businessTemplate = businessTemplateMapper.selectById(instruction.getResourceId());
		if (businessTemplate == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		situationTimeService.executeInstruction(instruction, businessTemplate.getTopoId());
		return new BaseRsp();
	}

}
