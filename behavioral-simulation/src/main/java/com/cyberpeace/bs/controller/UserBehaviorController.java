package com.cyberpeace.bs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.controller.req.PageReqWithType;
import com.cyberpeace.bs.service.UserBehaviorService;
import com.cyberpeace.bs.service.bo.behavior.UserBehaviorAddBo;
import com.cyberpeace.bs.service.bo.behavior.UserBehaviorBo;
import com.cyberpeace.bs.service.bo.behavior.UserBehaviorDetail;
import com.cyberpeace.bs.service.bo.behavior.UserBehaviorModifyBo;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "行为模拟-网络用户行为模拟")
@RestController
@RequestMapping("/bs/user_behavior")
// @RequiresAuthentication
public class UserBehaviorController {

	@Autowired
	private UserBehaviorService userBehaviorService;

	@ApiOperation("获取用户行为分页")
	@GetMapping("/list")
	public BaseRsp<ListRsp<UserBehaviorBo>> getUserBehaviorPage(PageReqWithType pageReq) {
		Page page = pageReq.toPageParam();
		ListRsp<UserBehaviorBo> res = userBehaviorService.listUserBehavior(page, pageReq.getType(),
				pageReq.getPattern());
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询详情")
	@GetMapping("/")
	public BaseRsp<UserBehaviorDetail> get(
			@ApiParam(name = "resource_id", value = "查询编号", required = true) String resource_id) {
		UserBehaviorDetail detail = userBehaviorService.getDetail(resource_id);
		return new BaseRsp<>(detail);
	}

	@ApiOperation("新增")
	@PostMapping("/")
	public BaseRsp create(@RequestBody @Validated UserBehaviorAddBo addBo) {
		addBo.validate();
		userBehaviorService.create(addBo);
		return new BaseRsp();
	}

	@ApiOperation("修改")
	@PutMapping("/")
	public BaseRsp update(@RequestBody @Validated UserBehaviorModifyBo modifyBo) {
		modifyBo.validate();
		userBehaviorService.update(modifyBo);
		return new BaseRsp();
	}

	@ApiOperation("删除")
	@DeleteMapping("/")
	public BaseRsp batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		userBehaviorService.delete(deleteReq.getIds());
		return new BaseRsp();
	}

	@ApiOperation("获取龙测访问行为列表")
	@GetMapping("/list/behavior")
	public BaseRsp<List<Object>> getLcBehaviorList() {
		return new BaseRsp<>();
	}
}
