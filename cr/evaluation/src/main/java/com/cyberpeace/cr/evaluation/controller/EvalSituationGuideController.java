package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalGuideAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalGuideModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalGuideListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepGuideDetailRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepGuideListRsp;
import com.cyberpeace.cr.evaluation.service.EvalGuideService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "靶场测评-态势导调")
@RestController
@RequestMapping("/cr/eval/situation/guide")
@Slf4j
public class EvalSituationGuideController {

	@Autowired
	private EvalGuideService evalGuideService;

	@ApiOperation("新增导调方案")
	@PostMapping("")
	public BaseRsp<String> addGuideScheme(@RequestBody @Validated EvalGuideAddReq req) {
		return new BaseRsp<>(evalGuideService.addSituationGuide(req));
	}

	@ApiOperation("修改导调方案")
	@PutMapping("")
	public BaseRsp<Boolean> modifyGuideScheme(@RequestBody @Validated EvalGuideModifyReq req) {
		evalGuideService.modifySituationGuide(req);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询手动导调方案列表")
	@GetMapping("/list")
	public BaseRsp<List<EvalGuideListRsp>> guideSchemeList(@ApiParam(name = "test_id", value = "试验id") @RequestParam("test_id") String testId) {
		return new BaseRsp<>(evalGuideService.getGuideList(testId));
	}

	@ApiOperation("执行手动导调")
	@GetMapping("")
	public BaseRsp<Boolean> runGuide(@ApiParam(name = "guide_id", value = "导调id") @RequestParam("guide_id") String guideId) {
		evalGuideService.runGuideScheme(guideId);
		return new BaseRsp<>(true);
	}

	@ApiOperation("删除手动导调")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteGuide(@ApiParam(name = "guide_id", value = "导调id") @RequestParam("guide_id") String guideId) {
		evalGuideService.deleteGuideScheme(guideId);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询阶段任务列表")
	@GetMapping("/step/list")
	public BaseRsp<List<EvalStepGuideListRsp>> getStepGuideList(@ApiParam(name = "test_id", value = "试验id") @RequestParam("test_id") String testId) {
		return new BaseRsp<>(evalGuideService.getStepGuideList(testId));
	}

	@ApiOperation("查询任务导调详情")
	@GetMapping("/step")
	public BaseRsp<EvalStepGuideDetailRsp> getStepDetailGuide(@ApiParam(name = "step_id", value = "步骤id") @RequestParam("step_id") String stepId) {
		return new BaseRsp<>(evalGuideService.getGuideDetail(stepId));
	}

	@ApiOperation("删除任务对应的导调")
	@DeleteMapping("/step")
	public BaseRsp<Boolean> deleteStepGuide(@ApiParam(name = "step_id", value = "步骤id") @RequestParam("step_id") String stepId) {
		evalGuideService.deleteByStepId(stepId);
		return new BaseRsp<>(true);
	}

}
