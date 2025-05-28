/**
 * 这段代码定义了一个名为 `SystemPropertyController` 的 Spring MVC 控制器类，用于处理与系统配置相关的 HTTP 请求。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **系统配置管理**：该控制器负责获取系统配置的相关信息。 
 * 2. **权限控制**：通过 `@RequiresAuthentication` 注解确保只有经过身份验证的用户才能访问相关接口。 
 * ### 关键点分析 
 * - **注解说明**： 
 * - `@RestController`：声明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("/system_property")`：定义了该控制器的基本路径为 `/system_property`。 
 * - `@RequiresAuthentication`：确保接口需要用户进行身份认证后才能访问。 
 * - `@Slf4j`：引入日志功能，方便调试和记录操作。 
 * - **方法分析**： 
 * - 方法 `appRunningInfo`： 
 * - 使用 `@GetMapping("/list")` 映射到 `/system_property/list` 路径。 
 * - 返回一个包含系统配置列表的 `BaseRsp` 对象。 
 * - 调用了 `SystemPropertyService` 的 `list()` 方法来获取系统配置数据。 
 * - **依赖注入**： 
 * - `SystemPropertyService` 通过 `@Autowired` 注入，用于处理具体的业务逻辑。 
 * ### 总结 
 * 此代码实现了一个简单的 REST 接口，用于获取系统配置信息，并结合了权限控制和日志功能，适合在企业级应用中使用。 
**/
package com.cyberpeace.cs.controller;


import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.controller.res.SystemUseStatusRes;
import com.cyberpeace.cs.dao.entity.SystemProperty;
import com.cyberpeace.cs.service.SystemPropertyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统配置Controller
 *
 * @author SN
 * @date 2024/11/08
 */
@Api(tags = "获取系统变量")
@RestController
@RequestMapping("/system_property")
@RequiresAuthentication
@Slf4j
public class SystemPropertyController {

    /**
     * 系统配置Service
     */
    @Autowired
    private SystemPropertyService systemPropertyService;

    /**
     * 应用运行信息
     *
     * @return {@code BaseRsp<List<SystemProperty>> }
     */
    @ApiOperation("全量获取")
    @GetMapping("/list")
    public BaseRsp<List<SystemProperty>> appRunningInfo() {
        List<SystemProperty> list = systemPropertyService.list();
        return new BaseRsp<>(list);
    }
}
