/**
 * 这段代码定义了一个名为 `ToolShellcodeController` 的 Spring MVC 控制器类，用于处理与 Shell Code 相关的操作。以下是对代码的简要分析： 
 * ### 类和注解 
 * - **类定义**：`ToolShellcodeController` 类使用了 `@RestController` 注解，表示这是一个 RESTful 风格的控制器。 
 * - **路径映射**：`@RequestMapping("/cr/tool/shellcode")` 指定了该控制器所有方法的默认请求路径为 `/cr/tool/shellcode`。 
 * ### 方法 
 * #### 1. `getList` 
 * - **请求类型**：`@GetMapping("/list")` 表示这是一个 GET 请求，路径为 `/cr/tool/shellcode/list`。 
 * - **功能描述**：获取 Shell Code 列表。 
 * - **输入参数**：`ToolShellCodeListReq` 对象，通过 `@Validated` 注解进行验证。 
 * - **返回值**：`BaseRsp<ListRsp<ToolShellCode>>` 对象，封装了查询结果。 
 * #### 2. `getDetail` 
 * - **请求类型**：`@GetMapping()` 表示这是一个 GET 请求，路径为 `/cr/tool/shellcode`（没有子路径）。 
 * - **功能描述**：获取特定 Shell Code 的详细信息。 
 * - **输入参数**：`resource_id` 参数，通过 `@RequestParam` 和 `@NotBlank` 注解确保其不为空。 
 * - **返回值**：`BaseRsp<ToolShellCode>` 对象，封装了查询到的 Shell Code 详情。 
 * ### 权限控制 
 * - **认证**：`@RequiresAuthentication` 确保请求必须经过身份验证。 
 * - **权限**：`@RequiresPermissions(MenuConstant.WEB_CR_TOOL_SHELLCODE)` 确保请求必须具有指定权限才能访问这些方法。 
 * ### 依赖注入 
 * - **ToolShellCodeService**：通过 `@Autowired` 注入，用于处理与 Shell Code 相关的数据操作。 
 * ### 总结 
 * - **用途**：该控制器主要用于提供 Shell Code 的列表和详细信息查询接口。 
 * - **安全控制**：通过 Shiro 进行身份验证和权限检查。 
 * - **数据处理**：使用 `ToolShellCodeService` 进行数据查询和业务逻辑处理。 
 * 这个控制器的设计清晰地展示了如何通过 Spring MVC 构建一个简单的 RESTful API，并结合 Shiro 进行权限管理。 
**/
package com.cyberpeace.cr.tool.controller.web;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.tool.controller.req.ToolShellCodeListReq;
import com.cyberpeace.cr.tool.dao.entity.ToolShellCode;
import com.cyberpeace.cr.tool.service.ToolShellCodeService;
import com.cyberpeace.cr.tool.controller.res.ToolShellCodeRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
 * 工具shell代码Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "前台-资源中心-ShellCode库")
@RestController
@RequestMapping("/cr/tool/shellcode")
@RequiresAuthentication
public class ToolShellcodeController {

	/**
	 * 工具壳代号Service
	 */
	@Autowired
	private ToolShellCodeService toolShellCodeService;

	/**
	 * 获取列表
	 *
	 * @param toolShellCodeListReq 工具壳代号列表请求体
	 * @return {@code BaseRsp<ListRsp<ToolShellCode>> }
	 */
	@GetMapping("/list")
	@ApiOperation("shellcode列表")
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_SHELLCODE)
	public BaseRsp<ListRsp<ToolShellCode>> getList(
			@Validated ToolShellCodeListReq toolShellCodeListReq) {
		ToolShellCodeRsp toolShellCodeRsp = BeanUtils
				.copy(toolShellCodeListReq, ToolShellCodeRsp.class);
		ListRsp<ToolShellCode> toolShellCodeListRsp = toolShellCodeService
				.selectPage(toolShellCodeListReq, toolShellCodeRsp);
		return new BaseRsp<>(toolShellCodeListRsp);
	}

	/**
	 * 获取详情
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<ToolShellCode> }
	 */
	@GetMapping()
	@ApiOperation("shellcode详情")
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_SHELLCODE)
	public BaseRsp<ToolShellCode> getDetail(
			@ApiParam(name = "resource_id", value = "查询id", required = true)
			@RequestParam("resource_id") @NotBlank String resourceId) {
		ToolShellCode toolShellCode = toolShellCodeService.getById(resourceId);
		return new BaseRsp<>(toolShellCode);
	}
}
