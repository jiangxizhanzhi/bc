package com.cyberpeace.cr.report_score.controller.other;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.report_score.controller.res.*;
import com.cyberpeace.cr.report_score.service.ReportScoreConfigService;
import com.cyberpeace.cr.report_score.service.ReportScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "大屏统计")
@RestController
@RequestMapping("/cr/report_statistics")
@RequiresAuthentication
public class ReportStatistics {

	@Autowired
	private ReportScoreService reportScoreService;

	@Autowired
	private ReportScoreConfigService reportScoreConfigService;

	@ApiOperation("排行榜")
	@GetMapping("/ranking")
	public BaseRsp<List<RankingRep>> ranking() {
		List<RankingRep> rankingReps = reportScoreService.rankingStatistics();

		return new BaseRsp<>(rankingReps);
	}

	@ApiOperation("任务完成统计")
	@GetMapping("/subject/st")
	public BaseRsp<List<SubjectCompleteRep>> subjectCompleteSt() {
		List<SubjectCompleteRep> subjectCompleteReps = reportScoreService.subjectCompleteSt();
		return new BaseRsp<>(subjectCompleteReps);
	}

	@ApiOperation(("任务完成得分排行"))
	@GetMapping("/subject/ranking")
	public BaseRsp<Map<Integer, List<RankingRep>>> subjectRanking() {
		Map<Integer, List<RankingRep>> integerListMap = reportScoreService.subjectRanking();
		return new BaseRsp<>(integerListMap);
	}

	@ApiOperation("系统信息")
	@GetMapping("/system")
	public BaseRsp<Map<String, String>> system() {
		Map<String, String> systemConfig = reportScoreConfigService.getSystemConfig();
		return new BaseRsp<>(systemConfig);
	}

	@ApiOperation("攻击次数统计")
	@GetMapping("/sttack/st")
	public BaseRsp<List<AttackStRes>> attackSt() {
		// todo 中台提供IP次数接口，统计固定队伍攻击次数
		return null;
	}

	@ApiOperation("攻击日志")
	@GetMapping("/attack/log")
	public BaseRsp<List<AttackLogRes>> attackLog() {
		// todo 中提供攻击日志列表接口，用户初始化展示
		return null;
	}

	@ApiOperation("攻击动态")
	@GetMapping("/attack/dy")
	public BaseRsp<String> attackDynamics() {
		// todo 产品显示字段暂未确定，且统计内容和攻击日志一致，确认是否改成任务动态会更好
		return null;
	}
}
