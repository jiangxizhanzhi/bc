package com.cyberpeace.cr.evaluation.controller;


import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTestListReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTestTemplateListRsp;
import com.cyberpeace.cr.evaluation.service.EvalCloudTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalTestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "靶场测评-试验任务")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/test")
public class EvalTestController {

	@Autowired
	private EvalTestService evalTestService;

	@Autowired
	private EvalCloudTemplateService evalCloudTemplateService;

	@ApiOperation("获取模板列表")
	@GetMapping("/template/list")
	public BaseRsp<ListRsp<EvalTestTemplateListRsp>> templateList(@Validated EvalTestListReq evalTestListReq) {
		return new BaseRsp<>(evalCloudTemplateService.templateList(evalTestListReq));
	}

	@ApiOperation("根据模板创建试验任务")
	@PostMapping("")
	public BaseRsp<String> addEvalTemplate(@RequestBody @Validated EvalTestAddReq evalTestAddReq) {
		return new BaseRsp<>(evalTestService.createByTemplate(evalTestAddReq));
	}

	@ApiOperation("我的训练列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalTestListRsp>> testList(@Validated EvalTestListReq evalTestListReq) {
		return new BaseRsp<>(evalTestService.testList(evalTestListReq));
	}


}
