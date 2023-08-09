package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cr.crbase.controller.req.BatchDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleListReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalRuleReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRule;
import com.cyberpeace.cr.evaluation.service.EvalRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author yaoyao.Mei
 */

@Api(tags = "训练评估-规则")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/rule")
@Slf4j
public class EvalRuleController {

	@Autowired
	private EvalRuleService evalRuleService;

	@ApiOperation("获取规则列表（分页）")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/list")
	public BaseRsp<ListRsp<EvalRule>> list(EvalRuleListReq req) {
		return new BaseRsp<>(evalRuleService.pageList(req));
	}

	@ApiOperation("创建规则")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("")
	public BaseRsp<Boolean> create(@RequestBody EvalRuleReq req) {
		return new BaseRsp<>(evalRuleService.create(req));
	}

	@ApiOperation("更新规则")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PatchMapping("")
	public BaseRsp<Boolean> update(@RequestBody EvalRuleReq req) {
		return new BaseRsp<>(evalRuleService.update(req));
	}

	@ApiOperation("获取规则")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("")
	public BaseRsp<EvalRule> get(@ApiParam(name = "resource_id", value = "规则ID") @RequestParam("resource_id") String resourceId) {
		return new BaseRsp<>(evalRuleService.get(resourceId));
	}

	@ApiOperation("删除规则")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@DeleteMapping("")
	public BaseRsp<Boolean> delete(@RequestBody BatchDeleteReq req) {
		return new BaseRsp<>(evalRuleService.delete(req.getIds()));
	}

	@ApiOperation("规则导出为PDF")
//	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/rule2pdf")
	public void dumpEvalRule2PDF(HttpServletResponse httpServletResponse,
								   @ApiParam(name = "room_id", value = "规则ID") @RequestParam("room_id") String roomId) {
		FileUtil.setResponse(new File(evalRuleService.dumpEvalRule(roomId)), true, httpServletResponse);
	}

}
