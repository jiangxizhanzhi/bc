package com.cyberpeace.cr.evaluation.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTest;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;
import com.cyberpeace.cr.evaluation.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "靶场测评-试验任务")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/test")
public class EvalTestController {

	@Autowired
	private EvalTestService evalTestService;

	@Autowired
	private EvalCloudTemplateService evalCloudTemplateService;

	@Autowired
	private EvalSceneService evalSceneService;

	@Autowired
	private EvalTestInstanceService evalTestInstanceService;

	@Autowired
	private EvalStepService evalStepService;

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

	@ApiOperation("查看训练详情")
	@GetMapping("")
	public BaseRsp<EvalTestRsp> getDetail(@ApiParam(name = "test_id", value = "试验id", required = true) @RequestParam(name = "test_id") String testId,
									  @ApiParam(name = "instance_id", value = "试验实例id") @RequestParam(name = "instance_id", required = false) String instanceId) {

		EvalTest evalTest = evalTestService.getEvalTest(testId);
		String currentInstanceId = evalTest.getCurrentInstanceId();
		if (StringUtils.isEmpty(instanceId) && StringUtils.isNotEmpty(currentInstanceId)) {
			instanceId = currentInstanceId;
		}

		EvalTestRsp evalTestRsp = new EvalTestRsp();
		evalTestRsp.setTestId(evalTest.getResourceId());
		evalTestRsp.setTestName(evalTest.getName());
		SceneViewContext sceneViewContext;
		if (StringUtils.isEmpty(instanceId)) {
			sceneViewContext = evalSceneService.viewScene(null, evalTest.getTopoId());
		} else {
			EvalTestInstance evalTestInstance = evalTestInstanceService.getById(instanceId);
			sceneViewContext = evalSceneService.viewScene(evalTestInstance.getMpSceneId(), evalTestInstance.getCopyedTopoId());
			evalTestRsp.setInstanceId(instanceId);
			evalTestRsp.setInstanceName(evalTestInstance.getInstanceName());
			sceneViewContext.setLocalSceneId(evalTestInstance.getLocalSceneId());
		}
		BeanUtils.copyProperties(sceneViewContext, evalTestRsp);
		return new BaseRsp<>(evalTestRsp);
	}

	@ApiOperation("删除试验任务")
	@DeleteMapping("")
	public BaseRsp<Boolean> delete(@ApiParam(name = "test_id", value = "试验id", required = true) @RequestParam(name = "test_id") String testId) {
		return new BaseRsp<>(evalTestService.deleteTest(testId));
	}

	@ApiOperation("删除试验实例")
	@DeleteMapping("/instance")
	public BaseRsp<Boolean> deleteInstance(@ApiParam(name = "instance_id", value = "试验实例id", required = true) @RequestParam(name = "instance_id") String instanceId) {
		return new BaseRsp<>(evalTestInstanceService.deleteTestInstance(instanceId));
	}

	@ApiOperation("启动场景")
	@PostMapping("/start")
	public BaseRsp<String> start(@RequestBody EvalTestStartReq evalTestStartReq) {

		String testId = evalTestStartReq.getTestId();

		if (Boolean.TRUE.equals(
				evalTestService.validateExistTask(evalTestStartReq.getTestId(), EvalTestInstance.Status.RUNNING))) {
			log.error("The evaluation range has running task! templateId = [{}]", testId);
			throw new BusinessException(CrBusinessErrorCode.RUNNING_EVAL_STEP_EXIST);
		}

		return new BaseRsp<>(evalTestService.startScene(testId));
	}

	@ApiOperation("关闭场景")
	@PostMapping("/stop")
	public BaseRsp<Boolean> stop(@RequestBody @Validated EvalTestStopReq evalTestStopReq) {

		String currentInstanceId = evalTestService.getEvalTest(evalTestStopReq.getTestId()).getCurrentInstanceId();
		if (StringUtils.isNotEmpty(currentInstanceId)) {
			evalTestStopReq.setInstanceId(currentInstanceId);
		} else {
			log.error("no test instance for test: [{}]", evalTestStopReq.getTestId());
			throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_EXIST);
		}

		//没有任务名称时，删除场景及试验实例
		if (evalTestStopReq.getInstanceName() == null) {
			return new BaseRsp<>(evalTestInstanceService.deleteTestInstance(evalTestStopReq.getInstanceId()));
		} else {
			//存在任务名称时，关闭场景
			return new BaseRsp<>(evalTestInstanceService.stopInstance(evalTestStopReq.getTestId(),
					evalTestStopReq.getInstanceId(), evalTestStopReq.getInstanceName()));
		}
	}

	@ApiOperation("获取大屏链接")
	@GetMapping("/dashboard_permalink")
	public BaseRsp<String> getDashboardPermalink(@ApiParam(name = "test_id", value = "试验id", required = true)
													 @RequestParam(name = "test_id") String testId) {
		return new BaseRsp<>(evalTestService.getDashboardPermalink(testId));
	}

	@ApiOperation("查询阶段导调")
	@GetMapping("/step/list")
	public BaseRsp<List<EvalTestStepListRsp>> getStep(@ApiParam(name = "test_id", value = "试验id", required = true)
													@RequestParam(name = "test_id") String testId,
												@ApiParam(name = "instance_id", value = "试验实例id")
													@RequestParam(name = "instance_id", required = false) String instanceId) {
		EvalTest evalTest = evalTestService.getEvalTest(testId);

		if (StringUtils.isEmpty(instanceId)) {
			instanceId = evalTest.getCurrentInstanceId();
		}
		if (StringUtils.isEmpty(instanceId)) {
			//处理试验任务下无实例的情况
			return new BaseRsp<>(evalStepService.getSteps(testId));
		} else {
			return new BaseRsp<>(evalStepService.getInstanceSteps(instanceId));
		}
	}

	@ApiOperation("开始测试")
	@PostMapping("/step/start")
	public BaseRsp<Boolean> startStep(@RequestBody @Validated EvalTaskStepReq evalTaskStepReq) {
		return new BaseRsp<>(evalStepService.startStep(evalTaskStepReq.getStepId()));
	}

	@ApiOperation("完成测试")
	@PostMapping("/step/finish")
	public BaseRsp<Boolean> finishStep(@RequestBody @Validated EvalTaskStepReq evalTaskStepReq) {
		return new BaseRsp<>(evalStepService.finishStep(evalTaskStepReq.getStepId()));
	}

	@ApiOperation("设备统计")
	@GetMapping("/device/statistics")
	public BaseRsp<JSONObject> getDeviceStatistics(
			@ApiParam(name = "test_id", value = "试验id", required = true) @RequestParam(name = "test_id") String testId) {
		return new BaseRsp<>(evalTestService.getDeviceStatistics(testId));
	}

	@ApiOperation("设备列表")
	@GetMapping("/device/list")
	public BaseRsp<JSONArray> getDeviceList(
			@ApiParam(name = "test_id", value = "试验id", required = true) @RequestParam(name = "test_id") String testId,
			@ApiParam(name = "category", value = "设备类型", required = false) Integer category,
			@ApiParam(name = "name", value = "搜索关键字", required = false) String name) {
		return new BaseRsp<>(evalTestService.getDeviceList(testId, category, name));
	}

	@ApiOperation("查询测评任务时间节点")
	@GetMapping("/time_nodes")
	public BaseRsp<EvalTaskTimeNodesRsp> getTimeNodes(@ApiParam(name = "instance_id", value = "实例id",
			required = true) @RequestParam("instance_id") String instanceId) {
		return new BaseRsp<>(evalTestInstanceService.getTimeNodes(instanceId));
	}

	@ApiOperation("我的验证任务")
	@GetMapping("/person/list")
	public BaseRsp<ListRsp<EvalPersonTestRsp>> personTestList(@Validated PageReqBase pageReqBase) {
		return new BaseRsp<>(evalTestService.personTestRspListRsp(pageReqBase));
	}

	@ApiOperation("验证统计")
	@GetMapping("/person/statistics")
	public BaseRsp<EvalTestStatisticsRsp> testStatistics() {
		return new BaseRsp<>(evalTestService.testStatistics());
	}

}
