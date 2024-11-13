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
import com.cyberpeace.cr.tool.service.context.ToolTypeCategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 工具类别Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "后台-资源管理-工具库-分类管理")
@RestController
@RequestMapping("cr/admin/tool/category")
@RequiresAuthentication
public class ToolCategoryController {

	/**
	 * 工具类别Service
	 */
	@Autowired
	private ToolCategoryService toolCategoryService;

	/**
	 * 获取列表
	 *
	 * @param pageReqBase 分页请求体基础
	 * @param type        类型
	 * @return {@code BaseRsp<ListRsp<ToolCategory>> }
	 */
	@GetMapping("/list")
	@ApiOperation("工具分类列表")
//	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ListRsp<ToolCategory>> getList(
			@ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase,
			@ApiParam(name = "type", value = "工具类型")String type) {
		ListRsp<ToolCategory> courseInfos = toolCategoryService.selectPage(pageReqBase, type);
		return new BaseRsp<>(courseInfos);
	}

	/**
	 * 获取详情
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<ToolCategoryRsp> }
	 */
	@GetMapping()
	@ApiOperation("工具分类详情")
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ToolCategoryRsp> getDetail(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resource_id) {
		ToolCategory toolCategory = toolCategoryService.getById(resource_id);
		ToolCategoryRsp toolCategoryRsp = BeanUtils.copy(toolCategory, ToolCategoryRsp.class);
		return new BaseRsp<>(toolCategoryRsp);
	}

	/**
	 * 创造
	 *
	 * @param categoryAddReq 类别新增请求体
	 * @return {@code BaseRsp<String> }
	 */
	@PostMapping()
	@ApiOperation("工具分类创建")
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<String> create(@RequestBody @Validated CategoryAddReq categoryAddReq) {
		ToolCategory toolCategory = BeanUtils.copy(categoryAddReq, ToolCategory.class);
		toolCategoryService.validateToolCategoryNameExist(toolCategory);
		toolCategoryService.saveToolCategory(toolCategory);
		return new BaseRsp<>();
	}

	/**
	 * 更新
	 *
	 * @param categoryUpdateReq 类别更新请求体
	 * @return {@code BaseRsp<String> }
	 */
	@PatchMapping()
	@ApiOperation("工具分类更新")
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<String> update(@RequestBody @Validated CategoryUpdateReq categoryUpdateReq) {
		ToolCategory toolCategory = BeanUtils.copy(categoryUpdateReq, ToolCategory.class);
		toolCategoryService.updateToolCategoryById(toolCategory);
		return new BaseRsp<>();
	}

	/**
	 * 批量删除
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<String> }
	 */
	@DeleteMapping()
	@ApiOperation("工具分类批量删除")
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<String> batchRemove(@RequestBody @Validated DeleteReq deleteReq) {
		toolCategoryService.batchDelete(deleteReq.getIds());
		return new BaseRsp<>();
	}

	/**
	 * 获取都
	 *
	 * @return {@code BaseRsp<List<ToolTypeCategory>> }
	 */
	@ApiOperation("全部工具分类")
	@GetMapping("/all")
	public BaseRsp<List<ToolTypeCategory>> getAll() {
		List<ToolTypeCategory> res = toolCategoryService.getAllTypeCategory();
		return new BaseRsp<>(res);
	}
}
