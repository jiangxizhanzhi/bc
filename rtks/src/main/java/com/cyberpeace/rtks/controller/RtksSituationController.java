package com.cyberpeace.rtks.controller;

import java.util.List;

import com.cyberpeace.rtks.controller.res.TalentPersonReleaseRes;
import com.cyberpeace.rtks.controller.res.TalentReleaseRes;
import com.cyberpeace.rtks.controller.res.TalentUserReleaseRes;
import com.cyberpeace.rtks.controller.res.TalentUserRoleInfoRes;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.rtks.service.RtksSituationService;
import com.cyberpeace.rtks.service.bo.situation.LevelStatisticBo;
import com.cyberpeace.rtks.service.bo.situation.NewsBo;
import com.cyberpeace.rtks.service.bo.situation.RtksSituationRankings;
import com.cyberpeace.rtks.service.bo.situation.SituationTrendBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "RTKS-人才培养态势")
@RestController
@RequestMapping("/rtks/situation")
@RequiresAuthentication
public class RtksSituationController {

	@Autowired
	private RtksSituationService rtksSituationService;

	@ApiOperation("网安排名：个人-组织-复合")
	@GetMapping("/ranking")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<RtksSituationRankings> getUserRanking() {
		return new BaseRsp<>(rtksSituationService.getRankings());
	}

	@ApiOperation("动态")
	@GetMapping("/news")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<NewsBo>> getNews() {
		List<NewsBo> newsBos = rtksSituationService.getNews();
		return new BaseRsp<>(newsBos);
	}

	@ApiOperation("网安人才岗位态势")
	@GetMapping("/situation")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<List<LevelStatisticBo>> getSituation() {
		List<LevelStatisticBo> result = rtksSituationService.getSituation();
		return new BaseRsp<>(result);
	}

	@ApiOperation("网安人才发展趋势")
	@GetMapping("/trend")
	@RequiresPermissions(MenuConstant.CMS_RTKS_NCWF_FRAME)
	public BaseRsp<SituationTrendBo> getTrend() {
		SituationTrendBo result = rtksSituationService.getTrends();
		return new BaseRsp<>(result);
	}

	@ApiOperation("网安人才发布")
	@GetMapping("/talent/ranking")
//	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<TalentReleaseRes>> getTalentReleaseRanking() {
		return new BaseRsp<>(rtksSituationService.getTalentReleaseRanking());
	}

	@ApiOperation("网安个人排名Top5")
	@GetMapping("/person/ranking")
//	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<TalentPersonReleaseRes>> getTalentPersonRanking() {
		return new BaseRsp<>(rtksSituationService.getTalentPersonRanking());
	}

	@ApiOperation("网安组织排名Top5")
	@GetMapping("/org/ranking")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<TalentPersonReleaseRes>> getTalentOrgRanking() {
		return new BaseRsp<>(rtksSituationService.getTalentOrgRanking());
	}

	@ApiOperation("网安统计数据")
	@GetMapping("/user/ranking")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<TalentUserReleaseRes>> getTalentUserRanking() {
		return new BaseRsp<>(rtksSituationService.getTalentUserRanking());
	}


	@ApiOperation("网安个人岗位数据")
	@GetMapping("/user/roleInfos")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<TalentUserRoleInfoRes>> getTalentUserRoleInfos() {
		return new BaseRsp<>(rtksSituationService.getTalentUserRoleInfos());
	}


}
