package com.cyberpeace.gateway.controller;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.AttackType;
import com.cyberpeace.situation.constant.ws.DefenseType;
import com.cyberpeace.situation.controller.res.SituationTimeRes;
import com.cyberpeace.situation.model.*;
import com.cyberpeace.situation.service.AttackMessageHistoryService;
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

@Api(tags = "外部接口-态势统计相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/situation/statistics")
public class ExternalStatisticsController {

	@Autowired
	private SituationTimeService situationTimeService;

	@Autowired
	private AttackMessageHistoryService attackMessageHistoryService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@ApiOperation("攻击手法Top5")
	@GetMapping("/attack_type_count_top")
	public BaseRsp<List<AttackTypeCount>> getAttackTypeTop5(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		List<AttackTypeCount> attackTypeCountList = attackMessageHistoryService.getAttackTop5(convertId(resourceId));
		for (AttackTypeCount attackTypeCount : attackTypeCountList) {
			attackTypeCount.setAttackType(AttackType.getEnumByKeyWord(attackTypeCount.getAttackType(), AttackType.class).getMsg());
		}
		return new BaseRsp<>(attackTypeCountList);
	}

	@ApiOperation("防御手法Top5")
	@GetMapping("/defense_type_count_top")
	public BaseRsp<List<DefenseTypeCount>> getDefenseTypeTop5(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		List<DefenseTypeCount> defenseTypeCountList = attackMessageHistoryService.getDefenseTop5(convertId(resourceId));
		for (DefenseTypeCount defenseTypeCount : defenseTypeCountList) {
			defenseTypeCount.setDefenseType(DefenseType.getEnumByKeyWord(defenseTypeCount.getDefenseType(), DefenseType.class).getMsg());
		}
		return new BaseRsp<>(defenseTypeCountList);
	}

	@ApiOperation("漏洞利用主机Top5")
	@GetMapping("/exploit_host_count_top")
	public BaseRsp<List<HostCount>> getExploit(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		return new BaseRsp<>(attackMessageHistoryService.getHostTop5(AttackType::getIsExploit, convertId(resourceId)));
	}

	@ApiOperation("病毒植入主机Top5")
	@GetMapping("/virus_host_count_top")
	public BaseRsp<List<HostCount>> getVirus(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		return new BaseRsp<>(attackMessageHistoryService.getHostTop5(AttackType::getIsVirus, convertId(resourceId)));
	}

	@ApiOperation("主机状态数量")
	@GetMapping("/host_status_count")
	public BaseRsp<List<HostStatusCount>> getHostStatusCount(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		return new BaseRsp<>(attackMessageHistoryService.getHostStatusCount(convertId(resourceId)));
	}

	@ApiOperation("攻击日志")
	@GetMapping("/attack_log")
	public BaseRsp<List<AttackLog>> getAttackLog(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		return new BaseRsp<>(attackMessageHistoryService.getAttackLog(convertId(resourceId)));
	}

	@ApiOperation("防御日志")
	@GetMapping("/defense_log")
	public BaseRsp<List<DefenseLog>> getDefenseType(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		return new BaseRsp<>(attackMessageHistoryService.getDefenseLog(convertId(resourceId)));
	}

	@ApiOperation("防御趋势")
	@GetMapping("/defense_trend")
	public BaseRsp<List<SceneTrend>> getDefenseTrend(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		return new BaseRsp<>(attackMessageHistoryService.getDefenseTrend(convertId(resourceId)));
	}

	@ApiOperation("获取态势时间和状态")
	@GetMapping("/get_time")
	public BaseRsp<SituationTimeRes> getTime(
			@ApiParam(name = "resource_id", value = "场景编号", required = true) @RequestParam(value = "resource_id") String resourceId){
		return new BaseRsp<>(situationTimeService.getTimeManagement(convertId(resourceId)));
	}

	private String convertId(String resourceId) {
		BusinessTemplate businessTemplate = businessTemplateService.getById(resourceId);
		if (businessTemplate == null) {
			throw new BusinessException(SituationErrorCode.CANNOT_FIND_TOPO);
		}
		return businessTemplate.getTopoId();
	}
}
