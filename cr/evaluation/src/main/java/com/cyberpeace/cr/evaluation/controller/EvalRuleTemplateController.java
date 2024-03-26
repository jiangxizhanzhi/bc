package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleTemplateAddReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleTemplateListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleTemplateModifyReq;
import com.cyberpeace.cr.evaluation.controller.rsp.*;
import com.cyberpeace.cr.evaluation.service.EvalActionTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalDimensionTemplateService;
import com.cyberpeace.cr.evaluation.service.EvalRuleTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(tags = "后台-云场景模板-规则")
@RestController
@RequestMapping("/cr/eval/cloud/template")
@RequiresAuthentication
public class EvalRuleTemplateController {

	@Autowired
	private EvalRuleTemplateService evalRuleTemplateService;

	@Autowired
	private EvalActionTemplateService evalActionTemplateService;

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

	@ApiOperation("获取标注相关枚举")
	@GetMapping("/dimension/enum")
	public BaseRsp<EvalDimensionEnumRsp> getDimensionEnum() {
		return new BaseRsp<>(evalDimensionTemplateService.getDimensionEnum());
	}

	@ApiOperation("获取动作相关枚举")
	@GetMapping("/action/enum")
	public BaseRsp<EvalActionTemplateEnumRsp> getActionEnum() {
		return new BaseRsp<>(evalActionTemplateService.getActionEnum());
	}

}
