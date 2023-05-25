package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.rtks.controller.req.TalentStatistuicReq;
import com.cyberpeace.rtks.controller.res.*;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.RtksSituationService;
import com.cyberpeace.rtks.service.RtksStatisticService;
import com.cyberpeace.rtks.service.bo.situation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "RTKS-人才培养态势")
@RestController
@RequestMapping("/rtks/situation")
@RequiresAuthentication
public class RtksSituationController {

	@Autowired
	private RtksSituationService rtksSituationService;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RtksStatisticService rtksStatisticService;

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
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<TalentReleaseRes>> getTalentReleaseRanking() {
		return new BaseRsp<>(rtksSituationService.getTalentReleaseRanking());
	}

	@ApiOperation("网安个人排名Top5")
	@GetMapping("/person/ranking")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<TalentReleasePersonRankingRes>> getTalentPersonRanking() {
		return new BaseRsp<>(rtksSituationService.getTalentPersonRanking());
	}

	@ApiOperation("网安组织排名Top5")
	@GetMapping("/org/ranking")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<TalentReleaseRankingRes>> getTalentOrgRanking() {
		return new BaseRsp<>(rtksSituationService.getTalentOrgRanking());
	}

	@ApiOperation("网安统计数据")
	@GetMapping("/user/ranking")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<ListRsp<TalentUserRoleInfoRes>> getTalentUserRoleInfos(TalentStatistuicReq req) {
		return new BaseRsp<>(rtksSituationService.getTalentUserRanking(req));
	}

	@ApiOperation("网安个人岗位数据")
	@GetMapping("/user/roleInfos")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<TalentUserReleaseRes>
			getTalentUserRanking(@RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(rtksSituationService.getTalentUserRoleInfos(resourceId));
	}

	@ApiOperation("网安全岗位")
	@GetMapping("/roles")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<Role>> getAllRoles() {
		return new BaseRsp<>(roleMapper.selectList(null));
	}

	@ApiOperation("岗位能力完成进度")
	@GetMapping("/user/progress")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<SituationProgressBo> getProgress(
			@ApiParam(name = "user_id", value = "用户id", required = true) String user_id) {
		if (StringUtils.isBlank(user_id)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		SituationProgressBo res = rtksSituationService.getProgress(user_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("岗位满足度趋势")
	@GetMapping("/user/match/trend")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<UserTrend> getUserTrend(
			@ApiParam(name = "user_id", value = "用户id", required = true) String user_id) {
		if (StringUtils.isBlank(user_id)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserTrend res = rtksSituationService.getUserTrend(user_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("个人岗位统计数据")
	@GetMapping("/user/statistics")
	@RequiresPermissions(MenuConstant.CMS_TALENT_STATISTICS)
	public BaseRsp<List<UserStatisticsBo>> getUserStatistics(
			@ApiParam(name = "user_id", value = "用户id", required = true) String user_id,
			@ApiParam(name = "sort", value = "排序字段") String sort,
			@ApiParam(name = "asc", value = "是否正序， 默认倒序") Boolean asc) {
		if (StringUtils.isBlank(user_id)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		List<UserStatisticsBo> res = rtksStatisticService.getUserStatistics(user_id, sort, asc);
		return new BaseRsp<>(res);
	}

}
