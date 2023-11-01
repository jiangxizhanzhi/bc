package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.concurrent.Executor;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.FlowAutoRunReq;
import com.cyberpeace.situation.controller.req.SceneConfigReq;
import com.cyberpeace.situation.controller.req.SceneIdListReq;
import com.cyberpeace.situation.controller.res.ElementStateRes;
import com.cyberpeace.situation.controller.res.RefreshDataRes;
import com.cyberpeace.situation.controller.res.SceneConfigRes;
import com.cyberpeace.situation.dao.entity.SceneConfig;
import com.cyberpeace.situation.dao.mapper.SceneConfigMapper;
import com.cyberpeace.situation.model.MpSceneData;
import com.cyberpeace.situation.model.SceneData;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsNodeStatus;
import com.cyberpeace.situation.req.FlowLogReq;
import com.cyberpeace.situation.service.DynamicSchemeService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.TopoHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "态势拓扑")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/situation")
public class SituationController {

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private SceneConfigMapper sceneConfigMapper;

	@Autowired
	private DynamicSchemeService dynamicSchemeService;

	@Autowired
	@Qualifier("taskExecutor")
	private Executor executor;

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

	@ApiOperation("获取流量攻击事件")
	@GetMapping("/dynamic/attack")
	public BaseRsp<List<WsMessage>> attackDynamic(FlowLogReq flowLogReq) {

		List<WsMessage> wsMultiNodeDynamics = situationHandlerService.pushFlowEvents(flowLogReq);

		return new BaseRsp<>(wsMultiNodeDynamics);
	}

	@ApiOperation("获取节点监控信息")
	@GetMapping("element")
	public BaseRsp<ElementStateRes>
			element(@ApiParam(name = "scene_id",
					value = "场景编号") @RequestParam(value = "scene_id") String sceneId,
					@ApiParam(name = "element_id",
							value = "节点编号") @RequestParam(value = "element_id") String elementId) {

		ElementStateRes elementStateRes = topoHandlerService.getElementState(sceneId, elementId);

		return new BaseRsp<>(elementStateRes);
	}

	@ApiOperation("获取节点链路状态")
	@GetMapping("link_status")
	public BaseRsp<WsMessage> linkStatus(@ApiParam(name = "scene_id",
			value = "场景编号") @RequestParam(value = "scene_id") String sceneId) {

		WsMessage<WsNodeStatus> wsMessage = situationHandlerService.pushLinkStatus(sceneId);
		return new BaseRsp<>(wsMessage);
	}

	@ApiOperation("随机运行流量")
	@PostMapping("flow/auto_run")
	public BaseRsp<Boolean> flowAutoRun(@RequestBody @Validated FlowAutoRunReq flowAutoRunReq) {

		MpSceneData mpSceneData = topoHandlerService.getMpSceneData(flowAutoRunReq.getSceneId());

		// 场景未启动时返回false
		if (Boolean.FALSE.equals(topoHandlerService.checkSceneStatus(mpSceneData))) {
			log.error("scene is not running, scene_id is:{}", flowAutoRunReq.getSceneId());
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		// 随机运行场景中的流量
		Boolean result = topoHandlerService.flowAutoRun(flowAutoRunReq.getSceneId(), mpSceneData,
				flowAutoRunReq.getInteractiveType());
		return new BaseRsp<>(result);
	}

	@ApiOperation("获取态势场景配置")
	@GetMapping("scene_config")
	public BaseRsp<SceneConfigRes> getSceneConfig(
			@ApiParam(name = "scene_id",
					value = "场景编号") @RequestParam(value = "scene_id") String sceneId,
			@ApiParam(name = "is_instance", value = "是否为实例场景") @RequestParam(value = "is_instance",
					required = false) Boolean isInstance) {

		// 未传入sceneId时返回空值
		if (StringUtils.isEmpty(sceneId) || "demo".equals(sceneId)
				|| (isInstance != null && !isInstance)) {
			return new BaseRsp<>(new SceneConfigRes(false, false, false));
		}

		MpSceneData mpSceneData = topoHandlerService.getMpSceneData(sceneId);

		// 校验场景状态
		if (Boolean.FALSE.equals(topoHandlerService.checkSceneStatus(mpSceneData))) {
			return new BaseRsp<>(new SceneConfigRes(false, false, false));
		}

		// 获取数据库中的态势场景配置
		SceneConfig sceneConfig = sceneConfigMapper.selectBySceneId(sceneId);

		Boolean enableFlowCollect = topoHandlerService.enableFlowCollect(mpSceneData);

		// 未查到记录时新增
		if (sceneConfig == null) {
			sceneConfig = new SceneConfig(sceneId, enableFlowCollect, false);
			sceneConfigMapper.insert(sceneConfig);
		}

		// 未记录配置时修改
		if (sceneConfig.getFlowAnalysis() == null || sceneConfig.getFlowAutoRun() == null) {
			sceneConfig.setFlowAutoRun(false);
			sceneConfig.setFlowAnalysis(enableFlowCollect);
			sceneConfigMapper.updateById(sceneConfig);
		}

		SceneConfigRes sceneConfigRes = BeanUtils.copy(sceneConfig, SceneConfigRes.class);
		return new BaseRsp<>(sceneConfigRes);
	}

	@ApiOperation("修改态势场景配置")
	@PatchMapping("scene_config")
	public BaseRsp<SceneConfigRes>
			setSceneConfig(@RequestBody @Validated SceneConfigReq sceneConfigReq) {

		if (sceneConfigReq.getIsInstance() != null && !sceneConfigReq.getIsInstance()) {
			log.error("scene is not instance, scene_id is {}", sceneConfigReq.getSceneId());
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		String sceneId = sceneConfigReq.getSceneId();
		MpSceneData mpSceneData = topoHandlerService.getMpSceneData(sceneId);

		// 校验场景状态
		if (Boolean.FALSE.equals(topoHandlerService.checkSceneStatus(mpSceneData))) {
			log.error("scene is not running, scene_id is {}", sceneId);
			throw new BusinessException(ErrorCode.SCENE_NOT_RUNNING);
		}

		// 场景配置不在时报错
		SceneConfig sceneConfig = sceneConfigMapper.selectBySceneId(sceneId);
		if (sceneConfig == null) {
			log.error("scene_config is not exist, scene_id is {}", sceneId);
			throw new BusinessException(SituationErrorCode.NO_VALID_SCENE_CONFIG);
		}

		if (sceneConfig.getFlowAnalysis().equals(sceneConfigReq.getFlowAnalysis())
				&& sceneConfig.getFlowAutoRun().equals(sceneConfigReq.getFlowAutoRun())) {
			return new BaseRsp<>(null);
		}

		// 校验场景是否允许流量事件推送
		if (Boolean.TRUE.equals(sceneConfigReq.getFlowAnalysis())
				&& Boolean.TRUE.equals(!topoHandlerService.enableFlowCollect(mpSceneData))) {
			log.error("scene is not configured to collect flow, scene_id is {}", sceneId);
			throw new BusinessException(SituationErrorCode.FLOW_COLLECTION_IS_NOT_CONFIGURED);
		}

		// 校验场景是否允许流量自动运行
		if (Boolean.TRUE.equals(sceneConfigReq.getFlowAutoRun()) && Boolean.TRUE.equals(!topoHandlerService.enableFlowAutoRun(mpSceneData))) {
			log.error("scene is not configured to run flow, scene_id is {}", sceneId);
			throw new BusinessException(SituationErrorCode.FLOW_SIMULATION_IS_NOT_CONFIGURED);
		}

		// 更新数据库配置
		sceneConfig.setFlowAutoRun(sceneConfigReq.getFlowAutoRun());
		sceneConfig.setFlowAnalysis(sceneConfigReq.getFlowAnalysis());
		sceneConfigMapper.updateById(sceneConfig);

		// 推送场景配置
		SceneConfigRes sceneConfigRes = BeanUtils.copy(sceneConfig, SceneConfigRes.class);
		WsMessage<SceneConfigRes> wsMessage = new WsMessage<>(MessageType.SCENE_CONFIG.getDesc(),
				sceneConfigRes);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);

		return new BaseRsp<>(sceneConfigRes);
	}

	@ApiOperation("刷新态势场景数据")
	@GetMapping("refresh_data")
	public BaseRsp<RefreshDataRes> refreshSceneData(
			@ApiParam(name = "scene_id", value = "场景编号",
					required = true) @RequestParam(value = "scene_id") String sceneId,
			@ApiParam(name = "flow_analysis", value = "是否获取流量攻击事件",
					required = true) @RequestParam(value = "flow_analysis") Boolean flowAnalysis,
			@ApiParam(name = "flow_auto_run", value = "是否自动运行流量",
					required = true) @RequestParam(value = "flow_auto_run") Boolean flowAutoRun,
			@ApiParam(name = "link_status", value = "是否获取节点链路状态",
					required = true) @RequestParam(value = "link_status") Boolean linkStatus) {

		MpSceneData mpSceneData = topoHandlerService.getMpSceneData(sceneId);

		RefreshDataRes refreshDataRes = new RefreshDataRes();

		// 场景未启动时返回false
		if (Boolean.FALSE.equals(topoHandlerService.checkSceneStatus(mpSceneData))) {
			return new BaseRsp<>(refreshDataRes);
		}

		// 自动运行流量
		if (Boolean.TRUE.equals(flowAutoRun)) {
			refreshDataRes.setFlowAutoRunResult(
					topoHandlerService.flowAutoRun(sceneId, mpSceneData, null));
		}

		// 推送流量事件
		if (Boolean.TRUE.equals(flowAnalysis)) {
			FlowLogReq flowLogReq = new FlowLogReq(sceneId);
			refreshDataRes.setFlowEvents(situationHandlerService.pushFlowEvents(flowLogReq));
		}
		// 推送主机采集事件
		refreshDataRes.setHostEvents(situationHandlerService.pushHostEvents(sceneId));

		// 推送节点链路状态
		if (Boolean.TRUE.equals(linkStatus)) {
			refreshDataRes.setLinkStatus(situationHandlerService.pushLinkStatus(sceneId));
		}

		return new BaseRsp<>(refreshDataRes);
	}

	@ApiOperation("清除态势场景数据")
	@DeleteMapping("scene_data")
	public BaseRsp<Boolean>
			clearSceneConfig(@RequestBody @Validated SceneIdListReq sceneIdListReq) {

		for (String sceneId : sceneIdListReq.getSceneIds()) {

			executor.execute(() -> {
				MpSceneData mpSceneData;
				try {
					mpSceneData = topoHandlerService.getMpSceneData(sceneId);
				} catch (Exception e) {
					mpSceneData = null;
				}
				if (Boolean.TRUE.equals(topoHandlerService.checkSceneStatus(mpSceneData))) {
					return;
				}
				dynamicSchemeService.clearSceneData(sceneId);
			});
		}
		return new BaseRsp<>(true);
	}
}
