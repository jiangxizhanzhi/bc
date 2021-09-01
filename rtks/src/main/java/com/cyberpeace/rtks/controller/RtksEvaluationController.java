package com.cyberpeace.rtks.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.rtks.controller.res.PercentRes;
import com.cyberpeace.rtks.service.RtksStatisticService;
import com.cyberpeace.rtks.service.bo.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

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

	@ApiOperation("LGD岗位技能点六棱图统计")
	@GetMapping("/role/skill/radar")
	public BaseRsp<List<RtksRadarBo>> getLgdRoleSkillMatch() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String existRoleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<List<RtksRadarBo>> baseRsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(existRoleId)) {
			List<RtksRadarBo> radarBos = rtksStatisticService.calculateLgdRadar(existRoleId,
					userId);
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
		List<String> xNames = percentBos.stream().map(KnowledgeStudyPercentBo::getKnowledgeId)
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
		BaseRsp<List<CategorySkillBo>> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(roleId)) {
			List<CategorySkillBo> categorySkills = rtksStatisticService.getRoleSkillScores(userId,
					roleId);
			rsp.setData(categorySkills);
		}
		return rsp;
	}

	@ApiOperation("人才能力评估")
	@GetMapping("/role/skill/rardar")
	public BaseRsp<List<RtksRadarBo>> getRoleSkillMatch() {
		String userId = UserInfoContext.getUserContext().getUserId();
		String roleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp<List<RtksRadarBo>> rsp = new BaseRsp<>();
		if (StringUtils.isNotBlank(roleId)) {
			List<RtksRadarBo> skillRadars = rtksStatisticService.calculateSkillRadar(userId,
					roleId);
			rsp.setData(skillRadars);
		}
		return rsp;
	}

	@ApiOperation("岗位课程学习记录")
	@GetMapping("/role/course/study")
	@ApiImplicitParams({ @ApiImplicitParam(name = "start_time", value = "开始时间"),
			@ApiImplicitParam(name = "end_time", value = "结束时间") })
	public BaseRsp<StudyRecordBo> getStudyRecord(@RequestParam("start_time") String startTime,
			@RequestParam("end_time") String endTime) {
		boolean startValidate = TimeUtil.isValidDate(startTime);
		boolean endValidate = TimeUtil.isValidDate(endTime);
		if (!startValidate || !endValidate) {
			throw new InvalidParamException("时间格式不正确！");
		}
		String userId = UserInfoContext.getUserContext().getUserId();
		String roleId = UserInfoContext.getUserContext().getRoleId();
		BaseRsp rsp = new BaseRsp();
		if (StringUtils.isNotBlank(roleId)) {
			StudyRecordBo studyRecordBo = rtksStatisticService.calculateStudyRecord(userId, roleId,
					TimeUtil.toDate1(startTime), TimeUtil.toDate1(endTime));
			rsp.setData(studyRecordBo);
		}
		return rsp;
	}

}
