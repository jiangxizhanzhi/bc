package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalStepTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalStepTemplateModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalStepTemplateListRsp;
import com.cyberpeace.cr.evaluation.service.EvalStepTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "后台-云场景模板-步骤")
@RestController
@RequestMapping("/cr/eval/cloud/template")
@RequiresAuthentication
public class EvalStepTemplateController {
	
	@Autowired
	private EvalStepTemplateService evalStepTemplateService;

	@ApiOperation("新增步骤")
	@PostMapping("/step")
	public BaseRsp<String> addStep(@RequestBody @Validated EvalStepTemplateAddReq evalStepTemplateAddReq) {
		return new BaseRsp<>(evalStepTemplateService.addStep(evalStepTemplateAddReq));
	}

	@ApiOperation("删除步骤")
	@DeleteMapping("/step")
	public BaseRsp<Boolean> deleteStep(@RequestBody DeleteReq deleteReq) {
		evalStepTemplateService.deleteStep(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("修改步骤")
	@PutMapping("/step")
	public BaseRsp<Boolean> modifyStep(@RequestBody @Validated EvalStepTemplateModifyReq evalStepTemplateModifyReq) {
		evalStepTemplateService.modifyStep(evalStepTemplateModifyReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询步骤列表")
	@GetMapping("/step")
	public BaseRsp<List<EvalStepTemplateListRsp>> getStepList(@ApiParam(name = "cloud_template_id", value = "云模板id") @RequestParam("cloud_template_id") String cloudTemplateId) {
		return new BaseRsp<>(evalStepTemplateService.getStepList(cloudTemplateId));
	}

}
