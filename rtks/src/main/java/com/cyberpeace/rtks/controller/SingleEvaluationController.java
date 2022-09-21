package com.cyberpeace.rtks.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.service.SingleEvaluationService;
import com.cyberpeace.rtks.service.bo.xiamen2c.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 厦门2C项目单兵接口
 * @author SN
 */
@Api(tags = "厦门2C项目单兵评估页面接口")
@RestController
@RequestMapping("/single/evaluation")
@RequiresAuthentication
public class SingleEvaluationController {

	@Autowired
	private SingleEvaluationService singleEvaluationService;

	@ApiOperation("人员评估-全部技能")
	@GetMapping("/skill/list")
	public BaseRsp<ListRsp<EvaluationSkillBo>> listSkills(@Validated PageReqWithRole pageReq) {
		Page page = pageReq.toPageParam();
		ListRsp<EvaluationSkillBo> res = singleEvaluationService.listSkill(page,
				pageReq.getRole_id());
		return new BaseRsp<>(res);
	}

	@ApiOperation("人员评估-技能详情")
	@GetMapping("/skill/detail")
	public BaseRsp<EvaluationSkillDetail> getSkillDetail(
			@Validated @ApiParam(name = "skill_id", value = "技能id") String skill_id) {
		EvaluationSkillDetail detail = singleEvaluationService.getSkillDetail(skill_id);
		return new BaseRsp<>(detail);
	}

	@ApiOperation("人员评估-参训人员信息")
	@GetMapping("/trainee/list")
	public BaseRsp<ListRsp<EvaluationTraineeBo>> listTrainees(@Validated PageReqWithRole pageReq) {
		Page page = pageReq.toPageParam();
		ListRsp<EvaluationTraineeBo> res = singleEvaluationService.listTrainee(page,
				pageReq.getRole_id());
		return new BaseRsp<>(res);
	}

	@ApiOperation("人员评估-岗位列表")
	@GetMapping("/role/list")
	public BaseRsp<List<RoleSimple>> listRoles() {
		List<RoleSimple> roles = singleEvaluationService.listRoles();
		return new BaseRsp<>(roles);
	}
}
