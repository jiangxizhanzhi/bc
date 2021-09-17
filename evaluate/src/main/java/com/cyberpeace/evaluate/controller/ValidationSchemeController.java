package com.cyberpeace.evaluate.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.evaluate.controller.req.TestSchemeAddReq;
import com.cyberpeace.evaluate.controller.req.ValidationSchemePageReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.evaluate.service.ValidationSchemeService;
import com.cyberpeace.evaluate.service.bo.TestSchemeBO;
import com.cyberpeace.evaluate.service.context.TestSchemeContext;

@Api(tags = "es-系统评估方案相关api")
@RestController
@RequestMapping("/validation/scheme")
@RequiresAuthentication
public class ValidationSchemeController {

	@Autowired
	private ValidationSchemeService validationSchemeService;

	/**
	 * 新增测试方案
	 * @param testSchemeAddReq
	 * @return
	 */
	@PostMapping
	@ApiOperation("新增系统测评方案")
	public BaseRsp<String> addValidationScheme(@RequestBody TestSchemeAddReq testSchemeAddReq) {
		testSchemeAddReq.validate();

		String schemeId = validationSchemeService.addScheme(testSchemeAddReq);

		BaseRsp rsp = new BaseRsp();
		rsp.setData(schemeId);

		return rsp;
	}

	@GetMapping("/{scheme_id}")
	@ApiOperation("查询测评方案")
	public BaseRsp<TestSchemeContext>
			queryBySchemeId(@ApiParam("测评方案编号") @PathVariable("scheme_id") String schemeId) {
		TestSchemeContext data = validationSchemeService.getBySchemeId(schemeId);

		BaseRsp rsp = new BaseRsp();
		rsp.setData(data);

		return rsp;
	}

	@DeleteMapping("/{scheme_id}")
	@ApiOperation("删除系统评估方案")
	public BaseRsp
			deleteBySchemeId(@ApiParam("评估方案编号") @PathVariable("scheme_id") String schemeId) {
		validationSchemeService.deleteScheme(schemeId);

		return new BaseRsp();
	}

	@PatchMapping("/{scheme_id}")
	@ApiOperation("修改测评方案")
	public BaseRsp modifyScheme(@ApiParam("测评方案编号") @PathVariable("scheme_id") String schemeId,
			@RequestBody TestSchemeAddReq testSchemeAddReq) {
		testSchemeAddReq.validate();

		validationSchemeService.modifyScheme(testSchemeAddReq);

		return new BaseRsp();
	}

	/**
	 * 分页查询系统测评方案
	 * @return
	 */
	@GetMapping("/list")
	@ApiOperation("分页查询系统测评方案")
	public BaseRsp<ListRsp<TestSchemeBO>> list(ValidationSchemePageReq validationSchemePageReq) {
		Page page = validationSchemePageReq.toPageParam();

		ListRsp<TestSchemeBO> data = validationSchemeService.schemeList(page);
		return new BaseRsp<>(data);
	}
}
