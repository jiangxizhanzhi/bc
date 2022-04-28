package com.cyberpeace.cr.evaluation.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.req.TaskStepReq;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "测试靶场-任务")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task")
public class EvalTaskController {

	@Autowired
	private EvalTaskService taskService;

	@ApiOperation("启动步骤")
	@PostMapping("/step/start")
	public BaseRsp<Boolean> startStep(@RequestBody TaskStepReq taskStepReq) {

		Boolean result = taskService.getStepsByTaskId("11");

		return new BaseRsp<>(true);
	}

	@ApiOperation("完成步骤")
	@PostMapping("/step/finish")
	public BaseRsp<Boolean> finishStep(@RequestBody TaskStepReq taskStepReq) {
		return new BaseRsp<>(true);
	}

}