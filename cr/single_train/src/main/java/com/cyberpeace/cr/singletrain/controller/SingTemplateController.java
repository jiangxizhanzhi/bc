package com.cyberpeace.cr.singletrain.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "单兵训练模板相关接口")
@RestController
@RequestMapping("/cr/admin/training_single/training_single_templates")
public class SingTemplateController {

	@PostMapping
	@ApiOperation("单兵训练模板创建")
	public BaseRsp createTemplate() {
		// TODO
		return new BaseRsp();
	}
}
