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
/**
 * 这段代码定义了一个名为 `ToolCategoryController` 的 Spring MVC 控制器类，用于处理与工具类别相关的 RESTful API 请求。以下是代码的主要功能和结构分析： 
 * ### 类和注解 
 * - **类名**：`ToolCategoryController` 
 * - **注解**： 
 * - `@RestController`：声明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("cr/admin/tool/category")`：设置该控制器所有方法的默认路径前缀为 `/cr/admin/tool/category`。 
 * - `@RequiresAuthentication`：表示需要用户认证才能访问这些接口。 
 * ### 属性 
 * - **属性**：`private ToolCategoryService toolCategoryService;` 
 * - 通过 `@Autowired` 自动注入了 `ToolCategoryService` 服务类，用于处理业务逻辑。 
 * ### 方法 
 * 1. **获取工具分类列表** 
 * - **方法签名**：`@GetMapping("/list")` 
 * - **描述**：根据分页参数和工具类型获取工具分类列表。 
 * - **参数**： 
 * - `pageReqBase`：分页请求体。 
 * - `type`：工具类型。 
 * - **返回值**：包含工具分类列表的 `BaseRsp<ListRsp<ToolCategory>>` 对象。 
 * 2. **获取工具分类详情** 
 * - **方法签名**：`@GetMapping` 
 * - **描述**：根据资源 ID 获取工具分类详情。 
 * - **参数**： 
 * - `resource_id`：资源 ID。 
 * - **返回值**：包含工具分类详情的 `BaseRsp<ToolCategoryRsp>` 对象。 
 * 3. **创建工具分类** 
 * - **方法签名**：`@PostMapping` 
 * - **描述**：创建新的工具分类。 
 * - **参数**： 
 * - `categoryAddReq`：包含新工具分类信息的请求体。 
 * - **返回值**：成功创建后的响应对象 `BaseRsp<String>`。 
 * 4. **更新工具分类** 
 * - **方法签名**：`@PatchMapping` 
 * - **描述**：更新已存在的工具分类。 
 * - **参数**： 
 * - `categoryUpdateReq`：包含更新信息的请求体。 
 * - **返回值**：成功更新后的响应对象 `BaseRsp<String>`。 
 * 5. **批量删除工具分类** 
 * - **方法签名**：`@DeleteMapping` 
 * - **描述**：根据 ID 列表批量删除工具分类。 
 * - **参数**： 
 * - `deleteReq`：包含要删除的 ID 列表的请求体。 
 * - **返回值**：成功删除后的响应对象 `BaseRsp<String>`。 
 * 6. **获取所有工具分类** 
 * - **方法签名**：`@GetMapping("/all")` 
 * - **描述**：获取所有工具分类。 
 * - **返回值**：包含所有工具分类的 `BaseRsp<List<ToolTypeCategory>>` 对象。 
 * ### 总结 
 * 该控制器主要处理与工具分类相关的 CRUD 操作，并且每个方法都有相应的权限控制（通过 `@RequiresPermissions` 注解）。整体设计符合 RESTful API 规范，使用了 Swagger 注解来描述接口，便于文档生成和接口测试。 
**/
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

