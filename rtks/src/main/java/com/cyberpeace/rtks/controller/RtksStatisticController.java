package com.cyberpeace.rtks.controller;

import java.util.List;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.rtks.service.bo.KnowledgeStudyPercentBo;
import com.cyberpeace.rtks.service.bo.RoleStudyBo;
import com.cyberpeace.rtks.service.bo.RtksRadarBo;
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

	@ApiOperation("岗位学习记录统计")
	@GetMapping("/role/study_record")
	public BaseRsp<RoleStudyBo> getRoleStudyRecord(
			@ApiParam(name = "role_id", value = "岗位编号") @RequestParam("role_id") String roleId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		RoleStudyBo studyBo = rtksStatisticService.roleSkillStudyRecord(roleId, userId);
		BaseRsp<RoleStudyBo> rsp = new BaseRsp<>();
		rsp.setData(studyBo);
		return rsp;
	}

	@ApiOperation("岗位六棱图统计")
	@GetMapping("/role/radar")
	public BaseRsp<List<RtksRadarBo>> getRoleMatch(
			@ApiParam(name = "role_id", value = "岗位编号") @RequestParam("role_id") String roleId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		List<RtksRadarBo> radarBos = rtksStatisticService.calculateRadar(roleId, userId);
		BaseRsp<List<RtksRadarBo>> baseRsp = new BaseRsp<>();
		baseRsp.setData(radarBos);
		return baseRsp;
	}

	@ApiOperation("岗位下知识点完成进度")
	@GetMapping("/role/knowledge/percent")
	public BaseRsp<List<KnowledgeStudyPercentBo>> getKnowledgeProcessByRole(
			@ApiParam(name = "role_id", value = "岗位编号") @RequestParam("role_id") String roleId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		List<KnowledgeStudyPercentBo> percentBos = rtksStatisticService
				.calculateKnowledgePercent(roleId, userId);
		BaseRsp<List<KnowledgeStudyPercentBo>> rsp = new BaseRsp<>();
		rsp.setData(percentBos);
		return rsp;
	}

	@ApiOperation("岗位匹配度计算")
	@GetMapping("/role/match")
	public BaseRsp<String> getMatchByRole(
			@ApiParam(name = "role_id", value = "岗位编号") @RequestParam("role_id") String roleId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		String percent = rtksStatisticService.roleMatchPercent(roleId, userId);
		BaseRsp<String> rsp = new BaseRsp<>();
		rsp.setData(percent);
		return rsp;
	}

}
