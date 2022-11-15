package com.cyberpeace.bs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.bo.behavior.BehaviorPageReq;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.behavior.VariableListBo;
import com.cyberpeace.bs.service.bo.behavior.VariableModifyReq;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "行为模拟-行为管理")
@RestController
@RequestMapping("/bs/behavior")
// @RequiresAuthentication
public class BehaviorController {

	@Autowired
	LcService lcService;

	@ApiOperation("获取行为列表")
	@GetMapping("/page")
	public BaseRsp<ListRsp<LcBehavior>> pageBehavior(BehaviorPageReq pageReq) {
		Page page = pageReq.toPageParam();
		ListRsp<LcBehavior> behaviorListRsp = lcService.getLcBehaviorList(pageReq);
		return new BaseRsp<>(behaviorListRsp);
	}

	@ApiOperation("获取变量列表")
	@GetMapping("/variable")
	public BaseRsp<VariableListBo> getVariables(@ApiParam(value = "行为id", name = "id") String id) {
		VariableListBo res = lcService.getVariableList(id);
		return new BaseRsp(res);
	}

	@ApiOperation("编辑行为变量")
	@PutMapping("/variable")
	public BaseRsp updateVariables(@Validated @RequestBody VariableModifyReq req) {
		lcService.updateVariable(req);
		return new BaseRsp();
	}
}
