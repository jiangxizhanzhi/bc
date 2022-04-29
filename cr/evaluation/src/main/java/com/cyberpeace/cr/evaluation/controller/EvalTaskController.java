package com.cyberpeace.cr.evaluation.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStartReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStepReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStopReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskTimeLineRsp;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "测试靶场-任务")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task")
public class EvalTaskController {

	@Autowired
	private EvalTaskService taskService;

	@ApiOperation("启动步骤")
	@PostMapping("/step/start")
	public BaseRsp<Boolean> startStep(@RequestBody EvalTaskStepReq evalTaskStepReq) {

		return new BaseRsp<>(true);
	}

	@ApiOperation("完成步骤")
	@PostMapping("/step/finish")
	public BaseRsp<Boolean> finishStep(@RequestBody EvalTaskStepReq evalTaskStepReq) {
		return new BaseRsp<>(true);
	}

	@ApiOperation("启动测试任务")
	@PostMapping("/start")
	public BaseRsp<Boolean> startEvalTask(@RequestBody EvalTaskStartReq evalTaskStartReq) {
		return new BaseRsp<>(true);
	}

	@ApiOperation("关闭测试任务")
	@PostMapping("/stop")
	public BaseRsp<Boolean> stopEvalTask(@RequestBody EvalTaskStopReq evalTaskStopReq) {
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询测试任务")
	@GetMapping("")
	public BaseRsp<EvalTaskRsp> getEvalTask(
			@ApiParam(name = "template_id", value = "测试靶场模板id", required = true) String template_id,
			@ApiParam(name = "task_id", value = "测试任务id", required = false) String task_id) {

		// task_id未赋值时。默认返回当前实例
		return new BaseRsp<>(new EvalTaskRsp());
	}

	@ApiOperation("删除测试任务")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteEvalTask(@RequestBody EvalTaskDeleteReq evalTaskDeleteReq) {
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询测试任务时间线")
	@GetMapping("/time_line")
	public BaseRsp<EvalTaskTimeLineRsp> getTimeLine(
			@ApiParam(name = "template_id", value = "测试靶场模板id", required = true) String template_id,
			@ApiParam(name = "task_id", value = "测试任务id", required = false) String task_id) {
		return new BaseRsp<>(new EvalTaskTimeLineRsp());
	}

}