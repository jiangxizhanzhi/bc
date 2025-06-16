package com.cyberpeace.space.controller;

import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.SpaceCallCountService;
import com.cyberpeace.space.service.context.rule.SpaceViolationJudgeRuleBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceViolationJudgeRule;
import com.cyberpeace.space.service.SpaceViolationJudgeRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间违反法官统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * ### 分析与总结
 * #### 代码概述
 * 该代码定义了一个名为 `SpaceViolationJudgeRuleController` 的 Spring MVC 控制器类。它继承自通用的 `SpaceControllerBase` 类，并用于处理与“空间违反法官规则”相关的请求。
 * ---
 * #### 主要功能模块
 * 1. **注解与元信息**：
 * - 使用了 `@RestController` 注解，表明这是一个 RESTful 风格的控制器。
 * - 使用了 `@RequestMapping` 注解，将此控制器映射到路径 `/space/rule/space_violation_judge_rule`。
 * - 使用了 `@Api` 注解，为 Swagger 提供接口文档描述，标签为“知识库-规则库-违规判定规则”。
 * 2. **权限控制**：
 * - 使用了 `@RequiresAuthentication` 注解，确保只有经过身份验证的用户才能访问这些接口。
 * 3. **日志记录**：
 * - 使用了 `@Slf4j` 注解，引入 Lombok 的日志功能，方便在代码中使用 `log` 对象进行日志记录。
 * 4. **继承关系**：
 * - 继承自 `SpaceControllerBase`，并通过泛型指定以下类型：
 * - `SpaceViolationJudgeRuleService`：服务层实现类。
 * - `SpaceViolationJudgeRule`：实体类。
 * - `SpaceViolationJudgeRuleBo`：业务对象（BO）。
 * - `SpacePageReqBase`：分页请求基类。
 * 5. **依赖注入**：
 * - 使用了 `SpaceViolationJudgeRuleService`，这是典型的 Spring 框架中的服务层依赖注入模式。
 * 6. **注释与文档**：
 * - 类上有详细的 Javadoc 注释，描述了类的功能、作者、版本及继承关系。
 * ---
 * #### 总结
 * 该代码是一个基于 Spring Boot 和 Shiro 权限框架的 RESTful 接口控制器，主要用于管理“空间违反法官规则”的相关操作。通过继承通用的 `SpaceControllerBase` 类，实现了代码复用，并通过注解简化了开发过程。同时，Swagger 文档支持使得接口易于被其他开发者理解与使用。
**/

@Api(tags = "知识库-规则库-违规判定规则")
@RestController
@RequestMapping("/space/rule/space_violation_judge_rule")
@RequiresAuthentication
@Slf4j
public class SpaceViolationJudgeRuleController extends SpaceControllerBase<SpaceViolationJudgeRuleService, SpaceViolationJudgeRule, SpaceViolationJudgeRuleBo, SpacePageReqBase>

{

    @Autowired
    private SpaceCallCountService spaceCallCountService;
    @Override
    public void detailAddCallTypeNum(){
        spaceCallCountService.addCount(CallType.RULE);
    }
}
