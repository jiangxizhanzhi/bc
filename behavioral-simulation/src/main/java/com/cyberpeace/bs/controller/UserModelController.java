package com.cyberpeace.bs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.controller.req.IdListReq;
import com.cyberpeace.bs.controller.req.PageReqWithType;
import com.cyberpeace.bs.service.UserModelService;
import com.cyberpeace.bs.service.bo.behavior.BehaviorWithVariable;
import com.cyberpeace.bs.service.bo.model.*;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "行为模拟-网络用户行为模型")
@RestController
@RequestMapping("/bs/user_model")
// @RequiresAuthentication
public class UserModelController {

	@Autowired
	private UserModelService userModelService;

	@ApiOperation("获取用户行为模型分页")
	@GetMapping("/list")
	public BaseRsp<ListRsp<UserModelBo>> getUserModelPage(PageReqWithType pageReq) {
		Page page = pageReq.toPageParam();
		ListRsp<UserModelBo> res = userModelService.listUserModel(page, pageReq.getType(),
				pageReq.getPattern(), pageReq.getAccess_os());
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询详情")
	@GetMapping("/")
	public BaseRsp<UserModelDetail> get(
			@ApiParam(name = "resource_id", value = "查询编号", required = true) String resource_id) {
		UserModelDetail detail = userModelService.getDetail(resource_id);
		return new BaseRsp<>(detail);
	}

	@ApiOperation("新增")
	@PostMapping("/")
	public BaseRsp create(@RequestBody @Validated UserModelAddBo addBo) {
		addBo.validate();
		userModelService.create(addBo);
		return new BaseRsp();
	}

	@ApiOperation("修改")
	@PutMapping("/")
	public BaseRsp update(@RequestBody @Validated UserModelModifyBo modifyBo) {
		modifyBo.validate();
		userModelService.update(modifyBo);
		return new BaseRsp();
	}

	@ApiOperation("删除")
	@DeleteMapping("/")
	public BaseRsp batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		userModelService.delete(deleteReq.getIds());
		return new BaseRsp();
	}

	@ApiOperation("获取活跃度曲线图和访问习惯饼图数据")
	@PostMapping("/chart")
	public BaseRsp<ChartResult> getChartData(@Validated @RequestBody ChartInput chartInput) {
		ChartResult res = userModelService.createChart(chartInput);
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取单个用户模型包含的行为及行为变量")
	@GetMapping("/behavior")
	public BaseRsp<List<BehaviorWithVariable>> getBehaviorsWithVariables(
			@ApiParam(value = "用户模型id", name = "resource_id") String resource_id) {
		List<BehaviorWithVariable> res = userModelService.getBehaviorsWithVariables(resource_id);
		return new BaseRsp(res);
	}

	@ApiOperation("批量获取多个用户模型包含的行为及行为变量")
	@PostMapping("/behavior")
	public BaseRsp<List<UserModelWithVariable>>
			getBehaviorsBatchWithVariable(@Validated @RequestBody IdListReq req) {
		List<UserModelWithVariable> res = userModelService
				.getBehaviorsBatchWithVariables(req.getIds());
		return new BaseRsp<>(res);
	}
}
