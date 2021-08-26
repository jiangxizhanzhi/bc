package com.cyberpeace.cr.singletrain.controller;

import javax.validation.constraints.NotBlank;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "靶场-单兵训练的demo")
@RestController
@RequestMapping("/cr/single_train/demo")
public class SingleTrainDemoController {

	@GetMapping()
	@ApiOperation("查询demo")
	public BaseRsp<String> getCourse(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		BaseRsp rsp = new BaseRsp("this is a test!");

		return rsp;
	}

}
