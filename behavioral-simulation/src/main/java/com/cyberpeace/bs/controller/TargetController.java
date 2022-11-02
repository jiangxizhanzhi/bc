package com.cyberpeace.bs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.bs.service.TargetService;
import com.cyberpeace.bs.service.bo.TargetBo;
import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "行为模拟-行为设备管理")
@RestController
@RequestMapping("/bs/target")
// @RequiresAuthentication
public class TargetController {

	@Autowired
	TargetService targetService;

	@ApiOperation("行为设备列表")
	@GetMapping("/list")
	public BaseRsp<List<TargetBo>> listTarget(@ApiParam(value = "类型", name = "type") Integer type,
			@ApiParam(value = "搜索", name = "search") String search) {
		List<TargetBo> res = targetService.getTargets(type, search);
		return new BaseRsp<>(res);
	}

}
