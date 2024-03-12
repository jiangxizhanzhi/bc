package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.evaluation.controller.req.*;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "后台-云场景模板")
@RestController
@RequestMapping("/cr/eval/cloud/template")
@RequiresAuthentication
public class EvalCloudTemplateController {

	@Autowired
	private EvalRuleTemplateService evalRuleTemplateService;

	@Autowired
	private EvalHostTemplateService evalHostTemplateService;

	@Autowired
	private EvalStepTemplateService evalStepTemplateService;

	@Autowired
	private EvalActionTemplateService evalActionTemplateService;

	@Autowired
	private EvalCloudTemplateService evalCloudTemplateService;

	@Autowired
	private EvalDimensionTemplateService evalDimensionTemplateService;

	@ApiOperation("规则新增")
	@PostMapping("/rule")
	public BaseRsp<String> addRule(@RequestBody EvalRuleTemplateAddReq evalRuleTemplateAddReq) {
		return new BaseRsp<>(evalRuleTemplateService.addRuleTemplate(evalRuleTemplateAddReq));
	}

	@ApiOperation("规则分页查询")
	@GetMapping("/rule/page")
	public BaseRsp<ListRsp<EvalRuleTemplateListRsp>> getRulePage(EvalRuleTemplateListReq evalRuleTemplateListReq) {
		List<EvalRuleTemplateListRsp> evalRuleTemplateListRsqList = evalRuleTemplateService.getRuleTemplateList(evalRuleTemplateListReq);
		return new BaseRsp<>(PageUtil.getListResult(evalRuleTemplateListRsqList, evalRuleTemplateListReq.toPageParam(), evalRuleTemplateListRsqList.size()));
	}

	@ApiOperation("规则删除")
	@DeleteMapping("/rule")
	public BaseRsp<Boolean> deleteRule(@RequestBody DeleteReq deleteReq) {
		evalRuleTemplateService.deleteRuleTemplate(deleteReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("规则详情查询")
	@GetMapping("/rule")
	public BaseRsp<EvalRuleTemplateDetailRsp> getRule(@ApiParam(name = "rule_id", value = "规则id") @RequestParam("rule_id") String ruleId) {
		return new BaseRsp<>(evalRuleTemplateService.getRuleTemplate(ruleId));
	}

	@ApiOperation("修改规则")
	@PutMapping("/rule")
	public BaseRsp<Boolean> modifyRule(@RequestBody EvalRuleTemplateModifyReq evalRuleTemplateModifyReq) {
		evalRuleTemplateService.modifyRuleTemplate(evalRuleTemplateModifyReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("获取规则基础信息相关枚举")
	@GetMapping("/rule/enum")
	public BaseRsp<EvalRuleTemplateEnumRsp> getRuleEnum() {
		return new BaseRsp<>(evalRuleTemplateService.getRuleEnum());
	}

	@ApiOperation("新增标注")
	@PostMapping("/dimension")
	public BaseRsp<String> addDimension(@RequestBody EvalDimensionAddReq evalDimensionAddReq) {
		return new BaseRsp<>(evalDimensionTemplateService.addDimensionTemplate(evalDimensionAddReq));
	}

	@ApiOperation("删除标注")
	@DeleteMapping("/dimension")
	public BaseRsp<Boolean> deleteDimension(@RequestBody DeleteReq deleteReq) {
		evalDimensionTemplateService.removeByIds(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("获取标注相关枚举")
	@GetMapping("/dimension/enum")
	public BaseRsp<EvalDimensionEnumRsp> getDimensionEnum() {
		return new BaseRsp<>(evalDimensionTemplateService.getDimensionEnum());
	}

	@ApiOperation("新增主机")
	@PostMapping("/host")
	public BaseRsp<String> addHost(@RequestBody EvalHostAddReq evalHostAddReq) {
		return new BaseRsp<>(evalHostTemplateService.addHost(evalHostAddReq));
	}

	@ApiOperation("删除主机")
	@DeleteMapping("/host")
	public BaseRsp<Boolean> deleteHost(@RequestBody DeleteReq deleteReq) {
		evalHostTemplateService.deleteHost(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("新增任务")
	@PostMapping("/task")
	public BaseRsp<Boolean> addTask(@RequestBody EvalTaskTemplateAddReq evalTaskTemplateAddReq) {
		evalRuleTemplateService.addTaskTemplate(evalTaskTemplateAddReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("删除任务")
	@DeleteMapping("/task")
	public BaseRsp<Boolean> deleteTask(@RequestBody EvalTaskTemplateDelReq evalTaskTemplateDelReq) {
		evalRuleTemplateService.deleteTaskTemplate(evalTaskTemplateDelReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("新增动作")
	@PostMapping("/action")
	public BaseRsp<String> addAction(@RequestBody EvalActionTemplateAddReq evalActionTemplateAddReq) {
		return new BaseRsp<>(evalActionTemplateService.addAction(evalActionTemplateAddReq));
	}

	@ApiOperation("删除动作")
	@DeleteMapping("/action")
	public BaseRsp<Boolean> deleteAction(@RequestBody DeleteReq deleteReq) {
		evalActionTemplateService.deleteAction(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("修改动作")
	@PutMapping("/action")
	public BaseRsp<Boolean> modifyAction(@RequestBody EvalActionTemplateModifyReq evalActionTemplateModifyReq) {
		evalActionTemplateService.modifyAction(evalActionTemplateModifyReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("获取动作相关枚举")
	@GetMapping("/action/enum")
	public BaseRsp<EvalActionTemplateEnumRsp> getActionEnum() {
		return new BaseRsp<>(evalActionTemplateService.getActionEnum());
	}

	@ApiOperation("新增步骤")
	@PostMapping("/step")
	public BaseRsp<String> addStep(@RequestBody EvalStepTemplateAddReq evalStepTemplateAddReq) {
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
	public BaseRsp<Boolean> modifyStep(@RequestBody EvalStepTemplateModifyReq evalStepTemplateModifyReq) {
		evalStepTemplateService.modifyStep(evalStepTemplateModifyReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("查询步骤列表")
	@GetMapping("/step")
	public BaseRsp<List<EvalStepTemplateListRsp>> getStepList(@ApiParam(name = "cloud_template_id", value = "云模板id") @RequestParam("cloud_template_id") String cloudTemplateId) {
		return new BaseRsp<>(evalStepTemplateService.getStepList(cloudTemplateId));
	}

	@ApiOperation("获取步骤相关枚举")
	@GetMapping("/step/enum")
	public BaseRsp<EvalStepTemplateEnumRsp> getStepEnum() {
		return new BaseRsp<>(evalStepTemplateService.getStepEnum());
	}

	@ApiOperation("新增云场景模板")
	@PostMapping("/")
	public BaseRsp<Boolean> addCloudTemplate(@RequestBody EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		evalCloudTemplateService.addCloudTemplate(evalCloudTemplateAddReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("修改云场景模板")
	@PutMapping("/")
	public BaseRsp<Boolean> modifyCloudTemplate(@RequestBody EvalCloudTemplateAddReq evalCloudTemplateAddReq) {
		evalCloudTemplateService.modifyCloudTemplate(evalCloudTemplateAddReq);
		return new BaseRsp<>(true);
	}

	@ApiOperation("删除云场景模板")
	@DeleteMapping("/")
	public BaseRsp<Boolean> deleteCloudTemplate(@RequestBody DeleteReq deleteReq) {
		evalCloudTemplateService.deleteCloudTemplate(deleteReq.getIds());
		return new BaseRsp<>(true);
	}

	@ApiOperation("分页查询云场景模板")
	@GetMapping("/page")
	public BaseRsp<ListRsp<EvalCloudTemplateListRsp>> getCloudTemplateList(EvalCloudTemplateListReq evalCloudTemplateListReq) {
		List<EvalCloudTemplateListRsp> evalCloudTemplateListRspList = evalCloudTemplateService.getCloudTemplateList(evalCloudTemplateListReq);
		return new BaseRsp<>(PageUtil.getListResult(evalCloudTemplateListRspList, evalCloudTemplateListReq.toPageParam(), evalCloudTemplateListRspList.size()));
	}

	@ApiOperation("获取云模板基础信息相关枚举")
	@GetMapping("/enum")
	public BaseRsp<EvalCloudTemplateEnumRsp> getCloudTemplateEnum() {
		return new BaseRsp<>(evalCloudTemplateService.getCloudTemplateEnum());
	}

}
