/**
 * ### 代码分析 
 * #### 功能描述 
 * 该代码定义了一个名为 `LogController` 的 Spring MVC 控制器类，用于处理与后台日志相关的 API 请求。主要功能是提供日志文件的下载接口。 
 * #### 主要内容 
 * 1. **包与导入** 
 * - 包名：`com.cyberpeace.cs.controller` 
 * - 导入了多个常用的工具类和注解，包括： 
 * - `MenuConstant`：可能是一个常量类，定义了权限相关的常量。 
 * - `FileUtil`：用于文件操作的工具类。 
 * - `io.swagger.annotations`：用于生成 Swagger 文档。 
 * - `org.apache.shiro.authz.annotation`：Shiro 权限注解，确保只有经过认证和授权的用户可以访问。 
 * - 其他标准的 Spring 和 Java 类。 
 * 2. **类与注解** 
 * - 类名：`LogController` 
 * - 注解： 
 * - `@RestController`：表明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("/cs/admin/log")`：定义了该控制器的基本路径为 `/cs/admin/log`。 
 * - `@RequiresAuthentication`：确保调用者必须通过身份验证。 
 * - 类注释说明了该类的功能是处理后台日志相关的 API。 
 * 3. **方法** 
 * - 方法名：`download` 
 * - 注解： 
 * - `@GetMapping("/downloadLog")`：将该方法映射到 HTTP GET 请求路径 `/cs/admin/log/downloadLog`。 
 * - `@ApiOperation("下载日志")`：用于生成 Swagger 文档，描述该接口的作用。 
 * - `@RequiresPermissions(MenuConstant.CMS_SYSTEM_LOG)`：确保调用者具有特定的权限（CMS_SYSTEM_LOG）才能访问此接口。 
 * - 参数： 
 * - `HttpServletResponse response`：用于设置 HTTP 响应。 
 * - `String name`：通过 `@RequestParam` 获取的文件名参数，表示需要下载的日志文件名。 
 * - 功能： 
 * - 构造日志文件的绝对路径。 
 * - 调用 `FileUtil.setResponse` 方法，将指定的日志文件作为响应返回给客户端。 
 * - 如果文件不存在或无法访问，则不会触发任何异常，但客户端可能收到错误响应。 
 * 4. **工具类 `FileUtil`** 
 * - 该类提供了文件操作的辅助方法，具体逻辑未在代码中展示，但推测其能够正确设置 HTTP 响应头并流式传输文件。 
 * 5. **安全性** 
 * - 使用了 Shiro 的权限控制机制，确保只有经过认证且拥有相应权限的用户才能访问日志下载接口。 
 * #### 总结 
 * - **优点**： 
 * - 结构清晰，功能单一，便于维护。 
 * - 使用了 Swagger 注解，方便生成 API 文档。 
 * - 集成了 Shiro 权限管理，提高了系统的安全性。 
 * - **潜在问题**： 
 * - 文件路径硬编码在代码中（`System.getProperty("catalina.base") + "/logs/"`），可能导致可移植性较差。 
 * - 缺乏对文件存在的校验，可能会导致客户端接收到空响应或错误信息。 
 * - 没有明确的日志记录机制，可能不利于排查问题。 
**/
package com.cyberpeace.cs.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * 日志Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "后台日志相关api")
@RestController
@RequestMapping("/cs/admin/log")
@RequiresAuthentication
public class LogController {

	/**
	 * 下载
	 *
	 * @param response 响应
	 * @param name     名称
	 */
	@ApiOperation("下载日志")
	@GetMapping("/downloadLog")
	@RequiresPermissions(MenuConstant.CMS_SYSTEM_LOG)
	public void download(HttpServletResponse response , @ApiParam(name = "file_name",value = "文件名称",required = true) @RequestParam("file_name") String name){
		String logLootPath = System.getProperty("catalina.base") + "/logs/";
		File file = new File(logLootPath+name);
		FileUtil.setResponse(file,false,response);
	}

}
