package com.cyberpeace.situation.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.situation.controller.req.AddDynamicSchemeTemplateReq;
import com.cyberpeace.situation.controller.req.SchemeIdListReq;
import com.cyberpeace.situation.controller.req.UpdateDynamicSchemeTemplateReq;
import com.cyberpeace.situation.controller.res.OptionTemplateRes;
import com.cyberpeace.situation.model.SimpleDynamicSchemeTemplate;
import com.cyberpeace.situation.service.DynamicSchemeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "攻防方案模板管理")
@RestController
@RequestMapping("/situation/dynamic_scheme_template")
public class SituationDynamicSchemeTemplateController {

	@Autowired
	private DynamicSchemeTemplateService dynamicSchemeTemplateService;

	@ApiOperation("新增攻防方案模板")
	@PostMapping("/")
	public BaseRsp<String> addDynamicSchemeTemplate(
			@RequestBody @Validated AddDynamicSchemeTemplateReq addDynamicSchemeTemplateReq) {
		return new BaseRsp<>(dynamicSchemeTemplateService.addDynamicSchemeTemplate(addDynamicSchemeTemplateReq));
	}

	@ApiOperation("删除攻防方案模板")
	@DeleteMapping("/")
	public BaseRsp deleteDynamicSchemeTemplate(@RequestBody @Validated SchemeIdListReq schemeIdListReq) {
		dynamicSchemeTemplateService.bashDeleteDynamicSchemeTemplate(schemeIdListReq.getSchemeIds());
		return new BaseRsp();
	}

	@ApiOperation("更新攻防方案模板")
	@PatchMapping("/")
	public BaseRsp updateDynamicSchemeTemplate(@RequestBody @Validated UpdateDynamicSchemeTemplateReq updateDynamicSchemeTemplateReq) {
		dynamicSchemeTemplateService.updateDynamicSchemeTemplate(updateDynamicSchemeTemplateReq);
		return new BaseRsp();
	}

	@ApiOperation("查询单个攻防方案模板")
	@GetMapping("/")
	public BaseRsp<UpdateDynamicSchemeTemplateReq> getDynamicSchemeTemplate(@ApiParam(name = "resource_id", value = "动效方案编号",
			required = true) @RequestParam(value = "resource_id") String resourceId) {
	 	return new BaseRsp<>(dynamicSchemeTemplateService.getDynamicSchemeTemplate(resourceId));
	}

	@ApiOperation("列表查询攻防方案模板")
	@GetMapping("/list")
	public BaseRsp<ListRsp<SimpleDynamicSchemeTemplate>> getListDynamicSchemeTemplate(@Validated PageReqBase pageReqBase) {
		if (StringUtils.isBlank(pageReqBase.getSort())) {
			pageReqBase.setSort("-update_time");
		}
		return new BaseRsp<>(dynamicSchemeTemplateService.getListDynamicSchemeTemplate(pageReqBase));
	}

	@ApiOperation("获取选项")
	@GetMapping("/option")
	public BaseRsp<OptionTemplateRes> getOptionTemplate() {
		return new BaseRsp<>(dynamicSchemeTemplateService.getOptions());
	}
}
