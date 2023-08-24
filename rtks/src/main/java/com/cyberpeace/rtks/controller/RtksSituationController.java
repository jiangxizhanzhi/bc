package com.cyberpeace.rtks.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.rtks.service.RtksSituationService;
import com.cyberpeace.rtks.service.bo.situation.LevelStatisticBo;
import com.cyberpeace.rtks.service.bo.situation.NewsBo;
import com.cyberpeace.rtks.service.bo.situation.RtksSituationRankings;
import com.cyberpeace.rtks.service.bo.situation.SituationTrendBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "RTKS-人才培养态势")
@RestController
@RequestMapping("/rtks/situation")
@RequiresAuthentication
public class RtksSituationController {

	@Autowired
	private RtksSituationService rtksSituationService;

	@ApiOperation("网安排名：个人-组织-复合")
	@GetMapping("/ranking")
	public BaseRsp<RtksSituationRankings> getUserRanking() {
		return new BaseRsp<>(rtksSituationService.getRankings());
	}

	@ApiOperation("动态")
	@GetMapping("/news")
	public BaseRsp<List<NewsBo>> getNews() {
		List<NewsBo> newsBos = rtksSituationService.getNews();
		return new BaseRsp<>(newsBos);
	}

	@ApiOperation("网安人才岗位态势")
	@GetMapping("/situation")
	public BaseRsp<List<LevelStatisticBo>> getSituation() {
		List<LevelStatisticBo> result = rtksSituationService.getSituation();
		return new BaseRsp<>(result);
	}

	@ApiOperation("网安人才发展趋势")
	@GetMapping("/trend")
	public BaseRsp<SituationTrendBo> getTrend() {
		SituationTrendBo result = rtksSituationService.getTrends();
		return new BaseRsp<>(result);
	}

}
