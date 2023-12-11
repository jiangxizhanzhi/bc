package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTemTemplateReq;
import com.cyberpeace.cr.evaluation.controller.req.UpdateEvalMissionTemplateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalMissionTemplateRsp;
import com.cyberpeace.cr.evaluation.service.EvalMissionTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalTemTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "靶场测评-后台模板")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/template/template/")
public class EvalTemTemplateController {

	@Autowired
	private EvalMissionTemplateService evalMissionTemplateService;

	@Autowired
	private EvalTemTemplateService evalTemTemplateService;

	@ApiOperation("新增任务")
	@PostMapping("/mission")
	public BaseRsp<String> createMission(@RequestBody @Validated EvalMissionTemplateReq evalMissionTemplateReq) {
		return new BaseRsp<>(evalMissionTemplateService.addMissionTemplate(evalMissionTemplateReq));
	}

	@ApiOperation("修改任务")
	@PatchMapping("/mission")
	public BaseRsp<Boolean> updateMission(@RequestBody @Validated UpdateEvalMissionTemplateReq updateEvalMissionReq) {
		evalMissionTemplateService.updateMissionTemplate(updateEvalMissionReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询任务信息")
	@GetMapping("/mission")
	public BaseRsp<EvalMissionTemplateRsp> getMission(@ApiParam(value = "resource_id", required = true) @RequestParam(value = "resource_id", required = true, name = "资源id") String resourceId) {
		return new BaseRsp<>(evalMissionTemplateService.getMissionTemplate(resourceId));
	}

	@PostMapping("")
	@ApiOperation("靶场测评后台模板创建")
	public BaseRsp<String> createTemplate(@RequestBody EvalTemTemplateReq evalTemTemplateReq) {
		return new BaseRsp<>(evalTemTemplateService.createTemplate(evalTemTemplateReq));
	}
}
