package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.context.rule.SpaceSourceAnalyzeRuleBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceSourceAnalyzeRule;
import com.cyberpeace.space.service.SpaceSourceAnalyzeRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间源分析统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceSourceAnalyzeRuleController` 的 Spring MVC 控制器类，继承自 `SpaceControllerBase`。以下是代码的主要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceSourceAnalyzeRuleController` 
 * - 包名：`com.cyberpeace.space.controller` 
 * - 功能描述：用于处理与“空间源分析规则”相关的请求。 
 * 2. **继承关系**： 
 * - 该类继承自 `SpaceControllerBase`，并且传入了四个泛型参数： 
 * - `SpaceSourceAnalyzeRuleService`：服务层接口。 
 * - `SpaceSourceAnalyzeRule`：实体类。 
 * - `SpaceSourceAnalyzeRuleBo`：业务对象。 
 * - `SpacePageReqBase`：分页请求基类。 
 * 3. **注解说明**： 
 * - `@RestController`：表明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping`：定义了该控制器的基础路径为 `/space/rule/space_source_analyze_rule`。 
 * - `@RequiresAuthentication`：表示需要用户认证才能访问这些接口。 
 * - `@Slf4j`：引入日志功能，方便记录日志。 
 * - `@Api`：通过 Swagger 注解标记了该 API 的标签为“知识库-规则库-攻击源分析规则”。 
 * 4. **实现的功能**： 
 * - 该控制器主要负责处理与“空间源分析规则”相关的 CRUD 操作。 
 * - 具体实现细节依赖于父类 `SpaceControllerBase` 和其泛型参数所指定的服务、实体、业务对象等。 
 * 5. **日志记录**： 
 * - 使用 Lombok 的 `@Slf4j` 注解自动注入日志功能，便于调试和监控。 
 * 总结来说，这段代码是一个典型的基于 Spring Boot 和 Shiro 安全框架的 RESTful 控制器类，用于管理“空间源分析规则”的相关操作，并且通过 Swagger 提供了清晰的 API 文档支持。 
**/

@Api(tags = "知识库-规则库-攻击源分析规则")
@RestController
@RequestMapping("/space/rule/space_source_analyze_rule")
@RequiresAuthentication
@Slf4j
public class SpaceSourceAnalyzeRuleController extends SpaceControllerBase<SpaceSourceAnalyzeRuleService, SpaceSourceAnalyzeRule, SpaceSourceAnalyzeRuleBo, SpacePageReqBase>
    
{}
