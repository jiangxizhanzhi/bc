package com.cyberpeace.cr.evaluation.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;
import com.cyberpeace.cr.evaluation.service.TrainEvalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "训练评估")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/train_eval")
@Slf4j
public class TrainEvalController {

	@Autowired
	private TrainEvalService trainEvalService;

	@ApiOperation("获取规则列表（分页）")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalRule>> list(EvalRuleListReq req) {
		return new BaseRsp<>(trainEvalService.pageList(req));
	}

	@ApiOperation("创建规则")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("")
	public BaseRsp<Boolean> create(@RequestBody EvalRuleReq req) {
		return new BaseRsp<>(trainEvalService.create(req));
	}

	@ApiOperation("更新规则")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PatchMapping("")
	public BaseRsp<Boolean> update(@RequestBody EvalRuleReq req) {
		return new BaseRsp<>(trainEvalService.update(req));
	}

	@ApiOperation("获取规则")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("")
	public BaseRsp<EvalRule> get(@ApiParam(name = "resource_id",
			value = "规则ID") @RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(trainEvalService.get(resourceId));
	}

	@ApiOperation("删除规则")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@DeleteMapping("")
	public BaseRsp<Boolean> delete(@RequestBody List<String> ids) {
		return new BaseRsp<>(trainEvalService.delete(ids));
	}

	// @ApiOperation("获取学员的评估提交记录")
	// @GetMapping("/mission_evaluation")
	//// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	// public BaseRsp<List<MissionEvaluationRsp>>
	// getMissionEvaluations(@Validated RbMissionEvaluationListReq
	// missionEvaluationListReq) {
	//
	// MissionEvaluationQuery missionEvaluationQuery =
	// BeanUtils.copy(missionEvaluationListReq,
	// MissionEvaluationQuery.class);
	// missionEvaluationQuery.setPer_page(missionEvaluationListReq.getPerPage());
	// missionEvaluationQuery.setStudentHash(missionEvaluationListReq.getSubmitUser());
	//
	//// rbRoomService.checkRefereePermission(missionEvaluationListReq.getRoomId(),
	//// UserInfoContext.getUserContext().getUserId());
	//
	// IPage<MissionEvaluationContext> data = trainEvalService
	// .getRbMissionEvaluationList(missionEvaluationQuery);
	//
	// List<String> userIds = data.getRecords().stream()
	// .map(MissionEvaluationContext::getSubmitUser).distinct()
	// .collect(Collectors.toList());
	//
	// userIds.addAll(data.getRecords().stream().map(MissionEvaluationContext::getEvaluatorId)
	// .distinct().collect(Collectors.toList()));
	// Map<String, String> userInfoMap = remoteUserService.getIdNameMap(userIds);
	//
	// data.convert(missionEvaluationContext -> {
	// MissionEvaluationRsp missionEvaluationRsp =
	// BeanUtils.copy(missionEvaluationContext,
	// MissionEvaluationRsp.class);
	//
	// MissionEvaluationRsp.SubmitInfo submitInfo = missionEvaluationRsp.new
	// SubmitInfo();
	//
	// submitInfo.setCreateTime(missionEvaluationContext.getCreateTime());
	// submitInfo.setUserAnswer(missionEvaluationContext.getAnswer());
	//
	// missionEvaluationRsp
	// .setEvaluatorName(userInfoMap.get(missionEvaluationContext.getEvaluatorId()));
	// missionEvaluationRsp.setEvaluator(missionEvaluationContext.getEvaluatorId());
	// missionEvaluationRsp.setSubmitUserRole(missionEvaluationContext.getSubmitUserRole());
	// missionEvaluationRsp
	// .setSubmitUserRoleResourceId(missionEvaluationContext.getSubmitUserRoleId());
	// missionEvaluationRsp
	// .setSubmitUserName(userInfoMap.get(missionEvaluationContext.getSubmitUser()));
	// missionEvaluationRsp.setSubmitLogHash(missionEvaluationContext.getMissionHash());
	// missionEvaluationRsp.setTotalScore(missionEvaluationContext.getMission().getScore());
	// missionEvaluationRsp.setEvaluateTime(missionEvaluationContext.getEvaluateTime());
	// missionEvaluationRsp.setSubmitInfo(submitInfo);
	// missionEvaluationRsp.setMissionData(missionEvaluationContext.getMission());
	//
	// return missionEvaluationRsp;
	// });
	//
	// return new BaseRsp(PageUtil.getListResult(data));
	// }

	/**
	 * 获取统计数据
	 * @return
	 */
	@ApiOperation("获取统计数据")
	@GetMapping("/get_statistical_info")
	// @RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getStatisticalInfo(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		MissionEvaluationQuery missionEvaluationQuery = new MissionEvaluationQuery();
		missionEvaluationQuery.setRoomId(resource_id);
		missionEvaluationQuery.setPage(1);

		IPage<MissionEvaluationContext> data = trainEvalService
				.getRbMissionEvaluationList(missionEvaluationQuery);

		Integer successMissionsCount = 0;
		Integer failMissionsCount = 0;
		Integer rejectMissionsCount = 0;
		Integer submitMissionCount = 0;
		Integer waitMissionsCount = 0;

		List<MissionEvaluationContext> taskSubmits = data.getRecords();
		for (MissionEvaluationContext taskSubmit : taskSubmits) {
			submitMissionCount++;

			if (CrTaskSubmit.EvaluateStatus.SUCCESS.getCode()
					.equals(taskSubmit.getEvaluateStatus())) {
				successMissionsCount++;
			} else if (CrTaskSubmit.EvaluateStatus.FAIL.getCode()
					.equals(taskSubmit.getEvaluateStatus())) {
				failMissionsCount++;
			} else if (CrTaskSubmit.EvaluateStatus.REJECT.getCode()
					.equals(taskSubmit.getEvaluateStatus())) {
				rejectMissionsCount++;
			} else {
				waitMissionsCount++;
			}
		}

		JSONObject result = new JSONObject();
		result.put("success_missions_count", successMissionsCount);
		result.put("fail_missions_count", failMissionsCount);
		result.put("reject_missions_count", rejectMissionsCount);
		result.put("submit_mission_count", submitMissionCount);
		result.put("wait_missions_count", waitMissionsCount);

		return new BaseRsp(result);
	}

}
