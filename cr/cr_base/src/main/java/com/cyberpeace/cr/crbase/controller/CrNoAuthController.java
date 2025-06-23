package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.service.RemoteUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 无需用户认证的接口
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CrNoAuthController` 的 Spring MVC 控制器类，用于处理无需用户认证的接口。以下是代码的简要分析： 
 * 1. **包与导入**： 
 * - 该类位于 `com.cyberpeace.cr.crbase.controller` 包中。 
 * - 导入了多个外部类和注解，包括 `BaseRsp`、`RemoteUserService`、`TransactionTemplate` 等。 
 * 2. **类与注解**： 
 * - 类使用了 `@RestController` 注解，表明这是一个 RESTful 风格的控制器。 
 * - 使用了 `@Slf4j` 注解，方便日志记录。 
 * - 使用了 `@RequestMapping("/cr/noAuth")` 注解，定义了该控制器的基本请求路径。 
 * 3. **字段**： 
 * - `transactionTemplate`：通过 `@Autowired` 注入，用于管理事务。 
 * - `remoteUserService`：通过 `@Autowired` 注入，提供远程用户服务功能。 
 * 4. **方法**： 
 * - `auth` 方法： 
 * - 使用了 `@GetMapping("/auth")` 注解，处理 GET 请求 `/cr/noAuth/auth`。 
 * - 接收一个名为 `token` 的参数，使用 `@ApiParam` 描述其含义。 
 * - 调用 `remoteUserService.doAuthentication(token)` 进行用户认证。 
 * - 如果认证失败，捕获异常并记录错误日志，然后抛出 `AuthenticationException`。 
 * - 返回一个空的 `BaseRsp` 对象。 
 * 5. **功能描述**： 
 * - 该控制器的主要功能是提供一个无需用户认证的接口，用于辅助鉴权操作。 
 * - 通过调用远程用户服务进行身份验证，并在失败时记录详细日志。 
 * 总结：该代码实现了一个简单的 RESTful 接口，用于辅助用户认证操作，具有日志记录和异常处理功能。 
**/
@Api(tags = "靶场无需用户认证的接口")
@RestController
@Slf4j
@RequestMapping("/cr/noAuth")
public class CrNoAuthController {

	/**
	 * 事务模板
	 */
	@Autowired
	TransactionTemplate transactionTemplate;


	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 认证
	 *
	 * @param token 令 牌
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("辅助鉴权")
	@GetMapping("/auth")
	public BaseRsp auth(@ApiParam(name = "token", value = "jwt token") String token) {
		try {
			UserInfo userInfo = remoteUserService.doAuthentication(token);
		} catch (Exception e) {
			log.error("authentication failed, token={}", token, e);
			throw new AuthenticationException();
		}
		return new BaseRsp();
	}
}

