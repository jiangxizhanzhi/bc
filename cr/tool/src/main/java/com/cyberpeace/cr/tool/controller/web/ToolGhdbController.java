/**
 * 这段代码定义了一个名为 `ToolGhdbController` 的控制器类，用于处理与工具数据库（ToolGhdb）相关的HTTP请求。以下是代码的详细分析： 
 * ### 包和导入 
 * - 导入了多个包，包括Spring框架、Apache Shiro安全框架以及自定义的常量、模型和服务等。 
 * ### 类声明 
 * - `ToolGhdbController` 类被注解为 `@RestController`，表示这是一个RESTful风格的控制器。 
 * - `@RequestMapping("/cr/tool/ghdb")` 指定了该控制器处理的所有请求的基本路径。 
 * - `@RequiresAuthentication` 注解表明访问该控制器的方法需要用户认证。 
 * ### 成员变量 
 * - `private ToolGhdbService toolGhdbService;`：自动注入了 `ToolGhdbService`，用于处理业务逻辑。 
 * ### 方法 
 * 1. **`list` 方法** 
 * - `@GetMapping("/list")`：映射到 `/cr/tool/ghdb/list` 的GET请求。 
 * - `@RequiresPermissions(MenuConstant.WEB_CR_TOOL_GHDB)`：要求具有特定权限才能访问。 
 * - 参数 `ToolGhdbListReq toolGhdbListReq`：接收请求体中的参数。 
 * - 返回类型是 `BaseRsp<ListRsp<ToolGhdb>>`：包含分页信息和数据列表。 
 * - 使用 `BeanUtils.copy` 方法将请求体转换为目标对象，并调用 `toolGhdbService.listGhdb` 获取数据列表。 
 * 2. **`detail` 方法** 
 * - `@GetMapping()`：映射到 `/cr/tool/ghdb/{resource_id}` 的GET请求。 
 * - `@RequiresPermissions(MenuConstant.WEB_CR_TOOL_GHDB)`：要求具有特定权限才能访问。 
 * - 参数 `String resource_id`：通过路径参数传递资源ID。 
 * - 返回类型是 `BaseRsp<ToolGhdb>`：包含单个资源的详细信息。 
 * - 调用 `toolGhdbService.getById(resource_id)` 获取指定ID的资源详情。 
 * ### 总结 
 * - 这个控制器处理了两个主要操作：获取工具数据库的列表和获取单个工具的详情。 
 * - 安全性方面，这两个方法都要求用户经过身份验证且具有相应的权限。 
 * - 使用了分层架构，将业务逻辑和数据访问逻辑分离在服务层实现。 
 * 这段代码展示了如何使用Spring Boot和Shiro来构建一个简单的RESTful API，处理基本的CRUD操作，并添加了必要的安全控制。 
**/
package com.cyberpeace.cr.tool.controller.web;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.tool.controller.req.ToolGhdbListReq;
import com.cyberpeace.cr.tool.dao.entity.ToolGhdb;
import com.cyberpeace.cr.tool.service.ToolGhdbService;
import com.cyberpeace.cr.tool.controller.res.ToolGhdbRsp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工具GHDB 数据库Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "前台-资源中心-ghdb")
@RestController
@RequestMapping("/cr/tool/ghdb")
@RequiresAuthentication
public class ToolGhdbController {

	/**
	 * 工具GHDB 数据库Service
	 */
	@Autowired
	private ToolGhdbService toolGhdbService;

	/**
	 * 列表
	 *
	 * @param toolGhdbListReq 工具GHDB 数据库列表请求体
	 * @return {@code BaseRsp<ListRsp<ToolGhdb>> }
	 */
	@ApiOperation("ghdb列表")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_GHDB)
	public BaseRsp<ListRsp<ToolGhdb>> list(@Validated ToolGhdbListReq toolGhdbListReq) {
		ToolGhdbRsp toolGhdbRsp = BeanUtils.copy(toolGhdbListReq, ToolGhdbRsp.class);
		ListRsp<ToolGhdb> data = toolGhdbService.listGhdb(toolGhdbListReq, toolGhdbRsp);
		return new BaseRsp<>(data);
	}

	/**
	 * 详情
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<ToolGhdb> }
	 */
	@ApiOperation("ghdb详情")
	@GetMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TOOL_GHDB)
	public BaseRsp<ToolGhdb> detail(
			@ApiParam(name = "resource_id", value = "查询id", required = true) String resource_id) {
		ToolGhdb toolGhdb = toolGhdbService.getById(resource_id);

		return new BaseRsp<>(toolGhdb);
	}
}
