package com.cyberpeace.cr.crbase.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.MultiSelectReq;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.controller.req.ShareReq;
import com.cyberpeace.cr.crbase.controller.req.TemplateReq;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.Share;
import com.cyberpeace.cr.crbase.service.context.ShareContext;

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
	public BaseRsp<ListRsp<BusinessTemplateData>> getTemplate(TemplateReq listReq) {

		BusinessTemplateQuery businessTemplateQuery = BeanUtils.copy(listReq,
				BusinessTemplateQuery.class);

		ListRsp<BusinessTemplateData> data = businessTemplateService.page(businessTemplateQuery);

		return new BaseRsp<ListRsp<BusinessTemplateData>>(data);
	}

	/**
	 * 获取靶场业务模板的操作权限
	 * @return
	 */
	@ApiOperation("查询操作权限")
	@GetMapping("/getShares")
	public BaseRsp<Share> getShares(
			@ApiParam(name = "resource_id", value = "模板id", required = true) String resource_id) {
		Share data = businessTemplateService.getShares(resource_id);

		return new BaseRsp<Share>(data);
	}

	/**
	 * 获取靶场业务模板的操作权限
	 * @return
	 */
	@ApiOperation("批量修改操作权限")
	@PostMapping("/batchSetShares")
	public BaseRsp<String> batchSetShares(
			@ApiParam(name = "shareReq", value = "修改权限内容") @RequestBody @Valid ShareReq shareReq) {
		ShareContext shareContext = BeanUtils.copy(shareReq, ShareContext.class);

		businessTemplateService.batchSetShares(shareContext);

		return new BaseRsp<String>();
	}

	/**
	 * 导入业务模板
	 * @return
	 */
	@ApiOperation("导入业务模板")
	@PostMapping("/batch_loads")
	public BaseRsp<String> batchLoads(@RequestBody MultipartFile file) {
		// todo

		return null;
	}

	/**
	 * 导出业务模板
	 * @return
	 */
	@ApiOperation("导出业务模板")
	@PostMapping("/batch_dumps")
	public BaseRsp<String> batchDumps(@ApiParam(name = "multiSelectReq",
			value = "模板id") @RequestBody MultiSelectReq multiSelectReq) {
		// todo

		return null;
	}

	@DeleteMapping()
	@ApiOperation("批量删除")
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		// todo
		return new BaseRsp<>();
	}

}
