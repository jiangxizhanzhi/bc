package com.cyberpeace.cr.report_score.controller.other;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
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
	public BaseRsp<List<SubjectRankingRes>> subjectRanking() {
		List<SubjectRankingRes> subjectRankingResList = reportScoreService.subjectRanking();
		return new BaseRsp<>(subjectRankingResList);
	}

	@ApiOperation("系统信息")
	@GetMapping("/system")
	public BaseRsp<SystemConfigRes> system() {
		Map<String, String> systemConfig = reportScoreConfigService.getSystemConfig();
		SystemConfigRes systemConfigRes = JsonUtil
				.toJsonObject(JsonUtil.toJsonString(systemConfig), SystemConfigRes.class);
		return new BaseRsp<>(systemConfigRes);
	}

	@ApiOperation("攻击次数统计")
	@GetMapping("/sttack/st")
	public BaseRsp<List<AttackStRes>> attackSt() {
		List<AttackStRes> attackStResList = reportScoreService.attackSt();
		return new BaseRsp<>(attackStResList);
	}

	@ApiOperation("攻击日志")
	@GetMapping("/attack/log")
	public BaseRsp<List<AttackLogRes>> attackLog() {
		List<AttackLogRes> attackLogRes = reportScoreConfigService.getAttackLogRes();
		return new BaseRsp<>(attackLogRes);
	}

	@ApiOperation("攻击动态")
	@GetMapping("/attack/dy")
	public BaseRsp<String> attackDynamics() {
		// todo 产品显示字段暂未确定，且统计内容和攻击日志一致，确认是否改成任务动态会更好
		return null;
	}
}
