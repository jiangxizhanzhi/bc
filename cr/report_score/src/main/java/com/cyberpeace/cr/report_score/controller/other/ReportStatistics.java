package com.cyberpeace.cr.report_score.controller.other;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.report_score.controller.res.RankingRep;
import com.cyberpeace.cr.report_score.service.ReportScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "大屏统计")
@RestController
@RequestMapping("/cr/report_statistics")
@RequiresAuthentication
public class ReportStatistics {

	@Autowired
	private ReportScoreService reportScoreService;

	@ApiOperation("排行榜")
	@GetMapping("/ranking")
	public BaseRsp<List<RankingRep>> ranking() {
		List<RankingRep> rankingReps = reportScoreService.rankingStatistics();

		return new BaseRsp<>(rankingReps);
	}
}
