package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceIocThreat;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceIocThreatService;
import com.cyberpeace.space.service.context.rule.SpaceIocThreatBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间国际 奥委会威胁Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 概述 
 * 这段代码定义了一个名为 `SpaceIocThreatController` 的 Spring MVC 控制器类。该控制器用于处理与“空间国际奥运会威胁”相关的请求，并且它继承自 `SpaceControllerBase` 类。以下是对其主要功能和特性的分析。 
 * --- 
 * #### 主要特性 
 * 1. **注解说明** 
 * - `@RestController`: 表明这是一个 RESTful 风格的控制器，所有方法默认返回 JSON 或 XML 数据。 
 * - `@Api(tags = "知识库-规则库-IOC威胁情报库")`: 使用 Swagger 注解，描述了该控制器所属的 API 分组。 
 * - `@RequestMapping("/space/rule/space_ioc_threat")`: 定义了该控制器的基本路径为 `/space/rule/space_ioc_threat`。 
 * - `@RequiresAuthentication`: 表示访问此接口需要用户认证。 
 * - `@Slf4j`: 引入日志记录功能，便于调试和监控。 
 * 2. **继承关系** 
 * - 该类继承自 `SpaceControllerBase`，并且传入了四个泛型参数： 
 * - `SpaceIocThreatService`: 提供业务逻辑的服务层实现。 
 * - `SpaceIocThreat`: 数据库实体类。 
 * - `SpaceIocThreatBo`: 业务对象（BO），用于封装业务数据。 
 * - `SpacePageReqBase`: 请求参数基类，可能包含分页信息等。 
 * 3. **功能定位** 
 * - 根据类名和注释推测，该控制器负责管理 IOC (Indicator of Compromise) 威胁情报数据。 
 * - 具体操作由父类 `SpaceControllerBase` 实现，通常包括增删改查等功能。 
 * 4. **日志记录** 
 * - 使用 Lombok 的 `@Slf4j` 注解，可以方便地通过 `log` 对象记录日志，便于排查问题。 
 * 5. **安全性** 
 * - `@RequiresAuthentication` 确保只有经过身份验证的用户才能访问这些接口。 
 * --- 
 * #### 总结 
 * 该代码是一个典型的基于 Spring Boot 和 Shiro 的 RESTful 控制器类，专注于 IOC 威胁情报的管理。通过继承父类 `SpaceControllerBase`，它复用了通用的 CRUD 功能，同时结合 Swagger 文档注解增强了 API 的可读性和可维护性。此外，通过日志记录和权限控制，提升了系统的安全性和可靠性。 
**/

@Api(tags = "知识库-规则库-IOC威胁情报库")
@RestController
@RequestMapping("/space/rule/space_ioc_threat")
@RequiresAuthentication
@Slf4j
public class SpaceIocThreatController extends SpaceControllerBase<SpaceIocThreatService, SpaceIocThreat, SpaceIocThreatBo, SpacePageReqBase> {
}
