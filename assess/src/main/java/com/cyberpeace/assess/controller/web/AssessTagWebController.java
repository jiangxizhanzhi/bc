package com.cyberpeace.assess.controller.web;

import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.assess.dao.entity.AssessTag;
import com.cyberpeace.assess.service.AssessTagService;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "评估标签web")
@RestController
@RequestMapping("/web/assess")
@RequiresAuthentication
@RequiresPermissions(value = { MenuConstant.WEB_ASSESS_RESULT_DATA_MANAGE,
		MenuConstant.WEB_ASSESS_EVALUATE_DATA_MANAGE,
		MenuConstant.WEB_ASSESS_EVALUATE_MODEL_DATA_MANAGE,
		MenuConstant.WEB_ASSESS_EVALUATE_CASE_DATA_MANAGE,
		MenuConstant.WEB_ASSESS_INPUT_DATA_MANAGE }, logical = Logical.OR)
public class AssessTagWebController {

	@Autowired
	private AssessTagService assessTagService;

	@ApiOperation("查询单条评估模型")
	@GetMapping("/assess_tag")
	public BaseRsp<AssessTag> getAssessTag(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		AssessTag assessTag = assessTagService.getById(resource_id);

		return new BaseRsp(assessTag);
	}

	@ApiOperation("分页查询")
	@GetMapping("/assess_tag/list")
	public BaseRsp<List<AssessTag>> list(@Validated PageReqBase pageReqBase) {
		ListRsp<AssessTag> data = assessTagService.page(pageReqBase);

		return new BaseRsp(data);
	}

}
