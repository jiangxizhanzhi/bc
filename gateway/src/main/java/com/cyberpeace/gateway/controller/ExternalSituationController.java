package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.controller.res.SceneAllDataRes;
import com.cyberpeace.situation.model.*;
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

	@Autowired
	private AttackMessageHistoryService attackMessageHistoryService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

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

	@ApiOperation("恢复")
	@PostMapping("/recovery")
	public void recovery(
			@ApiParam(name = "scene_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		//恢复场景下的节点状态
		attackMessageHistoryService.recovery(convertId(resourceId));
	}

	@ApiOperation("情况回放")
	@PostMapping("/playback")
	public void playback(
			@ApiParam(name = "scene_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId,
			@ApiParam(value = "开始时间") @RequestParam(value = "start_time", required = false) String startTime,
			@ApiParam(value = "截止时间") @RequestParam(value = "end_time", required = false) String endTime){
		//回放当前场景动效
		dynamicSchemeService.playbackPushDynamicSchemeToRoom(convertId(resourceId), TimeUtil.toDate1(startTime), TimeUtil.toDate1(endTime));
	}

	@ApiOperation("接收指令")
	@PostMapping("/receive_instruction")
	public BaseRsp<Boolean> receiveInstruction(@RequestBody @Validated Instruction instruction) {
		dynamicSchemeService.executeInstruction(instruction, convertId(instruction.getResourceId()));
		return new BaseRsp<>();
	}

	@ApiOperation("获取场景中节点和链路数据")
	@GetMapping("/scene_data")
	public BaseRsp<SceneAllDataRes> getSceneData(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId) {
		return new BaseRsp<>(situationHandlerService.getSceneAllDate(convertId(resourceId)));
	}

	@ApiOperation("获取拓扑数据")
	@GetMapping("/topo")
	public BaseRsp<SceneData> getTopoData(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId) {
		return new BaseRsp<>(topoHandlerService.getSceneData(null, convertId(resourceId)));
	}

	@ApiOperation("获取节点漏洞信息")
	@GetMapping("/element_leaks")
	public BaseRsp<ElementLeak> getElementLeaks(@ApiParam(name = "element_id", value = "节点id", required = true) @RequestParam(value = "element_id") String elementId) {
		return new BaseRsp<>(topoHandlerService.getElementLeaks(elementId));
	}

	private String convertId(String resourceId) {
		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		if (businessTemplate == null) {
			throw new BusinessException(SituationErrorCode.CANNOT_FIND_TOPO);
		}
		return businessTemplate.getTopoId();
	}

}
