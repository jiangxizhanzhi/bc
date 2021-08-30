package com.cyberpeace.rtks.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.rtks.controller.res.PercentRes;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.service.UserRelateService;
import com.cyberpeace.rtks.service.bo.CategorySkillBo;
import com.cyberpeace.rtks.service.bo.KnowledgeStudyPercentBo;
import com.cyberpeace.rtks.service.bo.RoleStudyBo;
import com.cyberpeace.rtks.service.bo.RtksRadarBo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.rtks.service.RtksStatisticService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequiresAuthentication
@RestController
@RequestMapping("/rtks/statistic")
@Api(tags = "RTKS-作战能力评估")
public class RtksEvaluationController {

	@Autowired
	private RtksStatisticService rtksStatisticService;

	@ApiOperation("岗位知识点学习进度统计")
	@GetMapping("/role/knowledge/study_record")
	public BaseRsp<RoleStudyBo> getRoleStudyRecord() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<RoleStudyBo> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			RoleStudyBo studyBo = rtksStatisticService.roleKnowledgeStudyRecord(existRoleId,
					userId);
			rsp.setData(studyBo);
		}
		return rsp;
	}

	@ApiOperation("岗位技能点学习记录")
	@GetMapping("/role/skill/study_record")
	public BaseRsp<RoleStudyBo> getRoleSkillRecord() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<RoleStudyBo> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			RoleStudyBo studyBo = rtksStatisticService.roleSkillStudyRecord(existRoleId, userId);
			rsp.setData(studyBo);
		}
		return rsp;
	}

	@ApiOperation("岗位知识点六棱图统计")
	@GetMapping("/role/knowledge/radar")
	public BaseRsp<List<RtksRadarBo>> getRoleKnowledgeMatch() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<List<RtksRadarBo>> baseRsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			List<RtksRadarBo> radarBos = rtksStatisticService.calculateRadar(existRoleId, userId);
			baseRsp.setData(radarBos);
		}
		return baseRsp;
	}

	@ApiOperation("岗位下知识点完成进度")
	@GetMapping("/role/knowledge/percent")
	public BaseRsp<PercentRes> getKnowledgeProcessByRole() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();

		BaseRsp<PercentRes> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			// 数据计算
			List<KnowledgeStudyPercentBo> percentBos = rtksStatisticService
					.calculateKnowledgePercent(existRoleId, userId);
			rsp.setData(percentFormatterTran(percentBos));
		}

		return rsp;
	}

	@ApiOperation("岗位下技能点完成度")
	@GetMapping("/role/skill/percent")
	public BaseRsp<PercentRes> getSkillProcessByRole() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<PercentRes> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			List<KnowledgeStudyPercentBo> percentBos = rtksStatisticService
					.calculateSkillPercent(existRoleId, userId);
			rsp.setData(percentFormatterTran(percentBos));
		}
		return rsp;
	}

	/**
	 *
	 * @param percentBos
	 * @return
	 */
	private PercentRes percentFormatterTran(List<KnowledgeStudyPercentBo> percentBos) {
		List<String> xNames = percentBos.stream().map(KnowledgeStudyPercentBo::getKnowledgeName)
				.collect(Collectors.toList());
		List<Integer> yPercents = percentBos.stream().map(KnowledgeStudyPercentBo::getPercent)
				.collect(Collectors.toList());

		// 结果返回
		PercentRes res = new PercentRes();
		res.setXNames(xNames);
		res.setYPercents(yPercents);
		return res;
	}

	@ApiOperation("岗位匹配度计算")
	@GetMapping("/role/match")
	public BaseRsp<String> getMatchByRole() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<String> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			String percent = rtksStatisticService.roleMatchPercent(existRoleId, userId);
			rsp.setData(percent);
		}
		return rsp;
	}

	@GetMapping("/role/skill/score")
	@ApiOperation("岗位技能评分详情")
	public BaseRsp<List<CategorySkillBo>> getRoleSillScore() {

		String userId = UserInfoContext.getUserContext().getUserId();
		String roleId = UserInfoContext.getUserContext().getRoleId();
		List<CategorySkillBo> categorySkills = rtksStatisticService.getRoleSkillScores(userId, roleId);
		BaseRsp<List<CategorySkillBo>> rsp = new BaseRsp<>();
		rsp.setData(categorySkills);
		return rsp;
	}

	@ApiOperation("人才能力评估")
	@GetMapping("/role/skill/rardar")
	public BaseRsp<List<RtksRadarBo>> getRoleSkillMatch() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String roleId = UserInfoContext.getUserContext().getRoleId();
		List<RtksRadarBo> skillRadars = rtksStatisticService.calculateSkillRadar(userId, roleId);
		BaseRsp<List<RtksRadarBo>> rsp = new BaseRsp<>();
		rsp.setData(skillRadars);
		return rsp;
	}

}
