package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.controller.res.SituationTimeRes;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.model.ws.WsRealisticSceneDynamic;
import com.cyberpeace.situation.service.AttackMessageHistoryService;
import com.cyberpeace.situation.service.SituationStatisticsService;
import com.cyberpeace.situation.service.SituationTimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "态势统计")
@RestController
@Slf4j
@RequestMapping("/situation/statistics")
public class SituationStatisticsController {

	@Autowired
	private SituationStatisticsService situationStatisticsService;

	@Autowired
	private AttackMessageHistoryService attackMessageHistoryService;

	@Autowired
	private SituationTimeService situationTimeService;

	@ApiOperation("攻击趋势")
	@GetMapping("/scene_trend")
	public BaseRsp<List<SceneTrend>> getSceneTrend(){
		return new BaseRsp<>((List)situationStatisticsService.getSceneTrendList());
	}

	@ApiOperation("攻击流量")
	@GetMapping("/flow")
	public BaseRsp<List<SceneTrend>> getFlow(){
		return new BaseRsp<>((List)situationStatisticsService.getFlowList());
	}

	@ApiOperation("攻击手法Top5")
	@GetMapping("/attack_type_count_top")
	public BaseRsp<List<AttackTypeCount>> getAttackTypeTop5(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		return new BaseRsp<>(attackMessageHistoryService.getAttackTop5(sceneId));
	}

	@ApiOperation("防御手法Top5")
	@GetMapping("/defense_type_count_top")
	public BaseRsp<List<DefenseTypeCount>> getDefenseTypeTop5(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		return new BaseRsp<>(attackMessageHistoryService.getDefenseTop5(sceneId));
	}

	@ApiOperation("漏洞利用主机Top5")
	@GetMapping("/exploit_host_count_top")
	public BaseRsp<List<HostCount>> getExploit(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		return new BaseRsp<>(attackMessageHistoryService.getHostTop5(AttackType::getIsExploit, sceneId));
	}

	@ApiOperation("病毒植入主机Top5")
	@GetMapping("/virus_host_count_top")
	public BaseRsp<List<HostCount>> getVirus(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		return new BaseRsp<>(attackMessageHistoryService.getHostTop5(AttackType::getIsVirus, sceneId));
	}

	@ApiOperation("主机状态数量")
	@GetMapping("/host_status_count")
	public BaseRsp<List<HostStatusCount>> getHostStatusCount(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		return new BaseRsp<>(attackMessageHistoryService.getHostStatusCount(sceneId));
	}

	@ApiOperation("攻击日志")
	@GetMapping("/attack_log")
	public BaseRsp<List<AttackLog>> getAttackLog(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		return new BaseRsp<>(attackMessageHistoryService.getAttackLog(sceneId));
	}

	@ApiOperation("运行时间")
	@GetMapping("/run_time")
	public BaseRsp<String> getRunTime(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		return new BaseRsp<>(situationTimeService.getRunTime(sceneId));
	}

	@ApiOperation("防御日志")
	@GetMapping("/defense_log")
	public BaseRsp<List<DefenseLog>> getDefenseType(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		return new BaseRsp<>(attackMessageHistoryService.getDefenseLog(sceneId));
	}

	@ApiOperation("防御趋势")
	@GetMapping("/defense_trend")
	public BaseRsp<List<SceneTrend>> getDefenseTrend(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		return new BaseRsp<>(attackMessageHistoryService.getDefenseTrend(sceneId));
	}

	@ApiOperation("刷新节点状态")
	@GetMapping("/refresh_node_status")
	public void refreshNodeStatus(
			@ApiParam(name = "scene_id", value = "场景id", required = true) @RequestParam(name = "scene_id") String sceneId){
		attackMessageHistoryService.pushParalysisNodeStatus(sceneId);
	}

	@ApiOperation("获取态势时间和状态")
	@GetMapping("/get_time")
	public BaseRsp<SituationTimeRes> getTime(@ApiParam(name = "topo_id", value = "场景编号",
			required = true) @RequestParam(value = "topo_id") String topoId) {
		return new BaseRsp<>(situationTimeService.getTimeManagement(topoId));
	}

	@ApiOperation("28所demo")
	@GetMapping("/28s_demo")
	public void outerDemo(@RequestParam("topo_id") String topoId, @RequestParam("step") Integer step) {
		WsRealisticSceneDynamic wsRealisticSceneDynamic = new WsRealisticSceneDynamic();
		wsRealisticSceneDynamic.setStep(step);
		attackMessageHistoryService.pushDataTo28S(topoId, wsRealisticSceneDynamic);
	}

}
