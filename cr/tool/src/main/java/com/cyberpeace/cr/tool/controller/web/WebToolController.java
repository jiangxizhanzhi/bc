/**
 * 这段代码定义了一个名为 `WebToolController` 的 Spring MVC 控制器类，用于处理与工具库相关的前端请求。以下是该代码的详细分析： 
 * ### 类和注解 
 * - **`@RestController`**: 表示这是一个 RESTful 风格的控制器。 
 * - **`@RequestMapping("/cr/tools")`**: 定义了该控制器的基本路径为 `/cr/tools`。 
 * - **`@RequiresAuthentication`**: 表示该控制器下的所有方法都需要用户认证。 
 * ### 成员变量 
 * - **`@Autowired private ToolService toolService;`**: 自动注入的 `ToolService`，用于调用工具服务层的方法。 
 * ### 方法 
 * #### 1. `getList` 
 * - **路径**: `/cr/tools/list` 
 * - **HTTP 方法**: `GET` 
 * - **功能**: 获取工具列表 
 * - **参数**: 
 * - `@Validated WebToolListReq webToolListReq`: 请求体，包含查询条件。 
 * - **返回值**: 
 * - `BaseRsp<ListRsp<WebToolRsp>>`: 包含分页信息的响应对象。 
 * - **逻辑**: 
 * - 根据请求参数生成分页对象 `page` 和查询条件 `listQuery`。 
 * - 根据查询条件过滤数据。 
 * - 调用 `toolService.selectPage` 方法获取工具列表，并转换成 `WebToolRsp` 对象。 
 * - 返回分页结果。 
 * #### 2. `getDetail` 
 * - **路径**: `/cr/tools` 
 * - **HTTP 方法**: `GET` 
 * - **功能**: 获取工具详情 
 * - **参数**: 
 * - `@RequestParam("resource_id") @NotBlank String resourceId`: 资源 ID，不能为空。 
 * - **返回值**: 
 * - `BaseRsp<ToolRsp>`: 包含工具详情的响应对象。 
 * - **逻辑**: 
 * - 调用 `toolService.selectById` 方法根据 ID 获取工具详情。 
 * - 返回工具详情。 
 * ### 其他注解 
 * - **`@ApiOperation`**: 描述 API 操作。 
 * - **`@ApiParam`**: 描述 API 参数。 
 * - **`@RequiresPermissions`**: 表示该方法需要特定权限才能访问。 
 * ### 总结 
 * 该控制器主要处理工具库的列表查询和详情查询操作。它通过 `ToolService` 来获取数据，并使用分页和转换工具将数据封装成响应对象返回给客户端。整个过程涉及到分页、权限控制和数据转换等操作。 
**/
package com.cyberpeace.cr.tool.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.tool.controller.req.WebToolListReq;
import com.cyberpeace.cr.tool.controller.res.ToolRsp;
import com.cyberpeace.cr.tool.controller.res.WebToolRsp;
import com.cyberpeace.cr.tool.dao.context.ToolListQuery;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.service.ToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * Web工具Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "前台-资源中心-工具库")
@RestController
@RequestMapping("/cr/tools")
@RequiresAuthentication
public class WebToolController {

	/**
	 * 工具Service
	 */
	@Autowired
	private ToolService toolService;

	/**
	 * 获取列表
	 *
	 * @param webToolListReq Web工具列表请求体
	 * @return {@code BaseRsp<ListRsp<WebToolRsp>> }
	 */
	@ApiOperation("工具库列表")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)
	public BaseRsp<ListRsp<WebToolRsp>> getList(
			@ApiParam(name = "listReq", value = "查询条件") @Validated WebToolListReq webToolListReq) {

		Boolean isAdmin = false;
		Page page = webToolListReq.toPageParam();
		ToolListQuery listQuery = BeanUtils.copy(webToolListReq, ToolListQuery.class);
		if (webToolListReq.getCracked() != null
				&& StringUtils.isNotBlank(webToolListReq.getCracked())) {
			if (webToolListReq.getCracked().equals(Tool.Cracked.TRIAL.getCode())) {
				return new BaseRsp<>(PageUtil.getNullResult());
			}
			listQuery.setCracked(webToolListReq.getCracked().equals(Tool.Cracked.FREE.getCode()));
		}

		IPage<ToolRsp> toolBoListRsp = toolService.selectPage(page, listQuery, isAdmin);
		IPage<WebToolRsp> convert = toolBoListRsp
				.convert(webTool -> BeanUtils.copy(webTool, WebToolRsp.class));

		return new BaseRsp<>(PageUtil.getListResult(convert));
	}

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<ToolRsp> }
	 */
	@ApiOperation("工具详情")
	@GetMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_TOOLS)
	public BaseRsp<ToolRsp> getDetail(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		ToolRsp toolRsp = toolService.selectById(resourceId);
		return new BaseRsp<>(toolRsp);
	}

}
