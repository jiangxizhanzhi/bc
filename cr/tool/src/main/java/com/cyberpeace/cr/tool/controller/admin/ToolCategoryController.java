package com.cyberpeace.cr.tool.controller.admin;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.tool.controller.req.CategoryAddReq;
import com.cyberpeace.cr.tool.controller.req.CategoryUpdateReq;
import com.cyberpeace.cr.tool.controller.req.DeleteReq;
import com.cyberpeace.cr.tool.controller.res.ToolCategoryRsp;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Api(tags = "后台-资源管理-工具库-分类管理")
@RestController
@RequestMapping("cr/admin/tool/category")
@RequiresAuthentication
public class ToolCategoryController {

	@Autowired
	private ToolCategoryService toolCategoryService;

	@GetMapping("/list")
	@ApiOperation("工具分类列表")
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS, MenuConstant.WEB_CR_TOOL_TOOLS,
			MenuConstant.WEB_CR_SINGLE_TRAIN,MenuConstant.WEB_CR_RED_TRAIN,MenuConstant.WEB_CR_BLUE_TRAIN,MenuConstant.WEB_CR_RED_VS_BLUE,
			MenuConstant.WEB_CR_DAILY_TRAIN}, logical = Logical.OR)
	public BaseRsp<ListRsp<ToolCategory>> getList(
			@ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase) {
		ListRsp<ToolCategory> courseInfos = toolCategoryService.selectPage(pageReqBase);
		return new BaseRsp<>(courseInfos);
	}

	@GetMapping()
	@ApiOperation("工具分类详情")
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
	public BaseRsp<ToolCategoryRsp> getDetail(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resource_id) {
		ToolCategory toolCategory = toolCategoryService.getById(resource_id);
		ToolCategoryRsp toolCategoryRsp = BeanUtils.copy(toolCategory, ToolCategoryRsp.class);
		return new BaseRsp<>(toolCategoryRsp);
	}

	@PostMapping()
	@ApiOperation("工具分类创建")
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
	public BaseRsp<String> create(@RequestBody @Validated CategoryAddReq categoryAddReq) {
		ToolCategory toolCategory = BeanUtils.copy(categoryAddReq, ToolCategory.class);
		toolCategoryService.validateToolCategoryNameExist(toolCategory);
		toolCategoryService.saveToolCategory(toolCategory);
		return new BaseRsp<>();
	}

	@PatchMapping()
	@ApiOperation("工具分类更新")
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
	public BaseRsp<String> update(@RequestBody @Validated CategoryUpdateReq categoryUpdateReq) {
		ToolCategory toolCategory = BeanUtils.copy(categoryUpdateReq, ToolCategory.class);
		toolCategoryService.updateToolCategoryById(toolCategory);
		return new BaseRsp<>();
	}

	@DeleteMapping()
	@ApiOperation("工具分类批量删除")
	@RequiresPermissions(value = {MenuConstant.CMS_TOOL, MenuConstant.CMS_TOOLS}, logical = Logical.OR)
	public BaseRsp<String> batchRemove(@RequestBody @Validated DeleteReq deleteReq) {
		toolCategoryService.batchDelete(deleteReq.getIds());
		return new BaseRsp<>();
	}
}
