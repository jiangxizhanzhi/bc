package com.cyberpeace.situation.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.controller.req.AddDynamicSchemeTemplateReq;
import com.cyberpeace.situation.controller.req.SchemeIdListReq;
import com.cyberpeace.situation.controller.req.UpdateDynamicSchemeTemplateReq;
import com.cyberpeace.situation.model.SimpleDynamicSchemeTemplate;
import com.cyberpeace.situation.service.DynamicSchemeTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public void deleteDynamicSchemeTemplate(@RequestBody @Validated SchemeIdListReq schemeIdListReq) {
		dynamicSchemeTemplateService.bashDeleteDynamicSchemeTemplate(schemeIdListReq.getSchemeIds());
	}

	@ApiOperation("更新攻防方案模板")
	@PatchMapping("/")
	public void updateDynamicSchemeTemplate(@RequestBody @Validated UpdateDynamicSchemeTemplateReq updateDynamicSchemeTemplateReq) {
		dynamicSchemeTemplateService.updateDynamicSchemeTemplate(updateDynamicSchemeTemplateReq);
	}

	@ApiOperation("查询单个攻防方案模板")
	@GetMapping("/")
	public BaseRsp<UpdateDynamicSchemeTemplateReq> getDynamicSchemeTemplate(@ApiParam(name = "resource_id", value = "动效方案编号",
			required = true) @RequestParam(value = "resource_id") String resourceId) {
	 	return new BaseRsp<>(dynamicSchemeTemplateService.getDynamicSchemeTemplate(resourceId));
	}

	@ApiOperation("列表查询攻防方案模板")
	@GetMapping("/list")
	public BaseRsp<List<SimpleDynamicSchemeTemplate>> getListDynamicSchemeTemplate() {
		return new BaseRsp<>(dynamicSchemeTemplateService.getListDynamicSchemeTemplate());
	}
}
