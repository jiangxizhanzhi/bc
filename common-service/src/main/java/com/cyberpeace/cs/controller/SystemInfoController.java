/**
 * 这段代码是一个基于Spring Boot框架的控制器类，用于处理与系统信息相关的请求。以下是代码的简要分析： 
 * 1. **包结构与依赖**： 
 * - 该类位于`com.cyberpeace.cs.controller`包中。 
 * - 使用了`BaseRsp`作为响应封装类，并且引入了`SystemUseStatusRes`作为具体的数据响应类。 
 * - 依赖`SystemUseStatusService`来处理业务逻辑。 
 * 2. **注解功能**： 
 * - `@Api`注解用于描述此控制器的Swagger文档标签为“系统信息服务接口”。 
 * - `@RestController`表明这是一个RESTful风格的控制器。 
 * - `@RequestMapping("/system_info")`定义了该控制器的基础路径。 
 * - `@RequiresAuthentication`确保只有经过认证的用户才能访问这些接口。 
 * - `@Slf4j`提供了日志记录功能。 
 * 3. **方法功能**： 
 * - `appRunningInfo`方法通过GET请求获取应用程序运行状态的信息。 
 * - 调用了`systemUseStatusService.showAll()`来获取数据，并将其封装在`BaseRsp`对象中返回给客户端。 
 * 4. **安全性**： 
 * - 使用了Apache Shiro的`@RequiresAuthentication`注解，保证了只有经过身份验证的用户可以调用此接口。 
 * 5. **日志记录**： 
 * - 使用Lombok的`@Slf4j`注解简化了日志记录的实现。 
 * 总结来说，这个控制器主要负责提供系统的运行状态信息，并且通过Spring Security（Shiro）进行了权限控制，同时使用Swagger进行API文档管理。 
**/
package com.cyberpeace.cs.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.service.SystemUseStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统信息Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "系统信息服务接口")
@RestController
@RequestMapping("/system_info")
@RequiresAuthentication
@Slf4j
public class SystemInfoController {

	/**
	 * 系统用状态Service
	 */
	@Autowired
	private SystemUseStatusService systemUseStatusService;

	/**
	 * 应用运行信息
	 *
	 * @return {@code BaseRsp<SystemUseStatusRes> }
	 */
	@ApiOperation("靶场应用运行图")
	@GetMapping("/app_running_info")
	public BaseRsp<SystemUseStatusRes> appRunningInfo() {
		SystemUseStatusRes res = systemUseStatusService.showAll();
		return new BaseRsp<>(res);
	}

}
