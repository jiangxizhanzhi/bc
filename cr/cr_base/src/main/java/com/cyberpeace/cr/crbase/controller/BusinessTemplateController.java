package com.cyberpeace.cr.crbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.model.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.model.TemplateReq;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "基础模板")
@RestController
@RequestMapping
public class BusinessTemplateController {

	@Autowired
	BusinessTemplateService businessTemplateService;

	/**
	 * 获取所有靶场的业务模板
	 * @return
	 */
	@ApiOperation("查询列表")
	@GetMapping()
	public BaseRsp<ListRsp<BusinessTemplateRsp>> getTemplate(TemplateReq listReq) {
		ListRsp<BusinessTemplateRsp> data = businessTemplateService.page(listReq);

		return new BaseRsp<ListRsp<BusinessTemplateRsp>>(data);
	}
}
