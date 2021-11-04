package com.cyberpeace.cr.crbase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.model.Share;
import com.cyberpeace.cr.crbase.model.ShareReq;
import com.cyberpeace.cr.crbase.model.TemplateReq;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "基础模板")
@RestController
@RequestMapping("/admin/ceBase/crEvents")
public class BusinessTemplateController {

	@Autowired
	BusinessTemplateService businessTemplateService;

	/**
	 * 获取所有靶场的业务模板
	 * @return
	 */
	@ApiOperation("查询模板列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<BusinessTemplateContext>> getTemplate(TemplateReq listReq) {
		ListRsp<BusinessTemplateContext> data = businessTemplateService.page(listReq);

		return new BaseRsp(data);
	}

	/**
	 * 获取靶场业务模板的操作权限
	 * @return
	 */
	@ApiOperation("查询操作权限")
	@GetMapping("/getShares")
	public BaseRsp<Share> getShares(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		Share data = businessTemplateService.getShares(resource_id);

		return new BaseRsp<Share>(data);
	}

	/**
	 * 获取靶场业务模板的操作权限
	 * @return
	 */
	@ApiOperation("批量修改操作权限")
	@PostMapping("/batchSetShares")
	public BaseRsp<String> batchSetShares(ShareReq shareReq) {
		businessTemplateService.batchSetShares(shareReq);

		return new BaseRsp<String>();
	}

}
