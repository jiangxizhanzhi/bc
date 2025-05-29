package com.cyberpeace.cr.tool.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.controller.req.DeleteReq;
import com.cyberpeace.cr.tool.controller.req.ToolAddReq;
import com.cyberpeace.cr.tool.controller.req.ToolUpdateReq;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
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
 * 工具Controller
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个Spring Boot控制器类，用于管理工具库中的工具。以下是对其主要部分的简要分析： 
 * ### 类定义和注解 
 * - **类名**：`ToolController` 
 * - **包名**：`com.cyberpeace.cr.tool.controller.admin` 
 * - **注解**： 
 * - `@RestController`：表明这是一个RESTful风格的控制器。 
 * - `@RequestMapping("/cr/admin/tools")`：设置该控制器的默认路径为`/cr/admin/tools`。 
 * - `@RequiresAuthentication`：表示需要用户认证才能访问这些接口。 
 * ### 成员变量 
 * - **toolService**：用于处理与工具相关的业务逻辑。 
 * - **utilService**：提供一些通用的工具方法，如文件URL替换。 
 * - **remoteLogService**：记录操作日志。 
 * ### 方法 
 * 1. **getList** 
 * - **描述**：获取工具列表。 
 * - **参数**：分页请求体、类型、标签、类别。 
 * - **返回值**：包含工具列表的响应对象。 
 * - **功能**：根据查询条件分页获取工具列表，并记录日志。 
 * 2. **getDetail** 
 * - **描述**：获取单个工具的详细信息。 
 * - **参数**：工具ID。 
 * - **返回值**：包含工具详细信息的响应对象。 
 * - **功能**：通过工具ID获取工具详情，并返回。 
 * 3. **create** 
 * - **描述**：创建新的工具。 
 * - **参数**：工具请求体。 
 * - **返回值**：包含新创建工具详细信息的响应对象。 
 * - **功能**：将请求体中的数据转换为工具对象，并保存到数据库中，同时记录创建日志。 
 * 4. **update** 
 * - **描述**：更新现有工具的信息。 
 * - **参数**：工具更新请求体。 
 * - **返回值**：空响应对象。 
 * - **功能**：验证工具名称是否存在，更新工具信息并记录更新日志。 
 * 5. **batchRemove** 
 * - **描述**：批量删除工具。 
 * - **参数**：删除请求体（包含要删除的工具ID列表）。 
 * - **返回值**：空响应对象。 
 * - **功能**：验证工具是否为内置工具，删除工具并记录删除日志。 
 * ### 总结 
 * - 该控制器提供了对工具库的基本CRUD（创建、读取、更新、删除）操作。 
 * - 每个操作都进行了权限检查（使用`@RequiresPermissions`注解）。 
 * - 数据操作通过`ToolService`完成，并且每个操作都会记录相应的日志。 
 * - 使用了Swagger注解来生成API文档。 
 * 这个控制器设计合理，功能完整，涵盖了工具库的基本管理需求。 
**/
@Api(tags = "后台-资源中心-工具库")
@RestController
@RequestMapping("/cr/admin/tools")
@RequiresAuthentication
public class ToolController {

	/**
	 * 工具Service
	 */
	@Autowired
	private ToolService toolService;

	/**
	 * utilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 远程日志Service
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 获取列表
	 *
	 * @param pageReqBase 分页请求体基础
	 * @param type        类型
	 * @param tag         标记
	 * @param category    类别
	 * @return {@code BaseRsp<ListRsp<ToolRsp>> }
	 */
	@ApiOperation("工具库列表")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ListRsp<ToolRsp>> getList(
			@ApiParam(name = "listReq", value = "查询条件") @Validated PageReqBase pageReqBase,
			@ApiParam(name = "type", value = "工具分类")String type,
			@ApiParam(name = "tag", value = "工具标签")String tag,
			@ApiParam(name = "category", value = "工具分类")String category) {
		Boolean isAdmin = true;
		Page page = pageReqBase.toPageParam();
		ToolListQuery listQuery = BeanUtils.copy(pageReqBase, ToolListQuery.class);
		listQuery.setType(type);
		listQuery.setTag(tag);
		listQuery.setCategory(category);
		IPage<ToolRsp> toolBoListRsp = toolService.selectPage(page, listQuery, isAdmin);
		return new BaseRsp(PageUtil.getListResult(toolBoListRsp));
	}

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<ToolRsp> }
	 */
	@ApiOperation("工具详情")
	@GetMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ToolRsp> getDetail(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		ToolRsp toolRsp = toolService.selectById(resourceId);
		return new BaseRsp<>(toolRsp);
	}

	/**
	 * 创造
	 *
	 * @param toolReq 工具请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("工具创建")
	@PostMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<ToolRsp> create(@RequestBody @Validated ToolAddReq toolReq) {
		Tool tool = BeanUtils.copy(toolReq, Tool.class);

		tool.setBuiltin(false);
		tool.setCover(utilService.replaceFileUrl(tool.getCover()));
		tool.setTag(JsonUtil.arrayToString(toolReq.getTag()));
		toolService.validateToolNameExist(tool);
		toolService.save(tool);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
				OperationType.CREATE, tool.getName());

		return this.getDetail(tool.getResourceId());
	}

	/**
	 * 更新
	 *
	 * @param toolupdateReq 工具更新请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("工具更新")
	@PatchMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<String> update(@RequestBody @Validated ToolUpdateReq toolupdateReq) {
		toolService.checkBuiltin(toolupdateReq.getResourceId());
		Tool tool = BeanUtils.copy(toolupdateReq, Tool.class);
		tool.setTag(JsonUtil.arrayToString(toolupdateReq.getTag()));
		toolService.validateEditToolNameExist(tool);
		toolService.updateById(tool);

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
				OperationType.UPDATE, tool.getName());

		return new BaseRsp<>();
	}

	/**
	 * 批量删除
	 *
	 * @param deleteReq 删除请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("工具删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.CMS_TOOL)
	public BaseRsp<String> batchRemove(@RequestBody @Validated DeleteReq deleteReq) {

		List<Tool> toolList = toolService.listByIds(deleteReq.getIds());
		for (Tool tool : toolList) {
			if (tool.getBuiltin()) {
				throw new BusinessException(ErrorCode.BUILT_IN);
			}
		}

		toolService.removeByIds(deleteReq.getIds());

		toolList.forEach(tool -> {
			if (tool != null) {
				remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TOOL,
						OperationType.DELETE, tool.getName());
			}
		});

		return new BaseRsp<>();
	}


}

