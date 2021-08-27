package com.cyberpeace.rtks.controller;

import java.util.List;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.service.UserRelateService;
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
@Api(tags = "RTKS-岗位相关统计")
public class RtksStatisticController {

	@Autowired
	private RtksStatisticService rtksStatisticService;

	@Autowired
	private UserRelateService userRelateService;

	@ApiOperation("岗位知识点学习进度统计")
	@GetMapping("/role/knowledge/study_record")
	public BaseRsp<RoleStudyBo> getRoleStudyRecord() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<RoleStudyBo> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			RoleStudyBo studyBo = rtksStatisticService
					.roleKnowledgeStudyRecord(existRoleId, userId);
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

	@ApiOperation("岗位六棱图统计")
	@GetMapping("/role/radar")
	public BaseRsp<List<RtksRadarBo>> getRoleMatch() {
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
	public BaseRsp<List<KnowledgeStudyPercentBo>> getKnowledgeProcessByRole() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<List<KnowledgeStudyPercentBo>> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			List<KnowledgeStudyPercentBo> percentBos = rtksStatisticService
					.calculateKnowledgePercent(existRoleId, userId);
			rsp.setData(percentBos);
		}

		return rsp;
	}

	@ApiOperation("岗位下技能点完成度")
	@GetMapping("/role/skill/percent")
	public BaseRsp<List<KnowledgeStudyPercentBo>> getSkillProcessByRole() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<List<KnowledgeStudyPercentBo>> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			List<KnowledgeStudyPercentBo> percentBos = rtksStatisticService
					.calculateSkillPercent(existRoleId, userId);
			rsp.setData(percentBos);
		}
		return rsp;
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

}
