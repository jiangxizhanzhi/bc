package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.service.AttackMessageHistoryService;
import com.cyberpeace.situation.service.SituationStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@ApiOperation("攻击趋势")
	@GetMapping("/scene_trend")
	public BaseRsp<List<SceneTrend>> getSceneTrend(){
		return new BaseRsp<>((List)situationStatisticsService.getSceneTrendList());
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

}
