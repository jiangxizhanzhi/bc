package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceFingerPrint;
import com.cyberpeace.space.service.SpaceFingerPrintService;
import com.cyberpeace.space.service.context.rule.SpaceFingerPrintBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间手指打印Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 * @author zhangxuejun
 * @date 2021/11/18
 */
/**
 * ### 代码分析： 
 * 1. **类定义与继承**： 
 * - `SpaceFingerPrintController` 是一个基于 Spring 的 REST 控制器类，继承自 `SpaceControllerBase`。 
 * - 它扩展了通用的控制器基类，并通过泛型参数指定了相关的实体类 (`SpaceFingerPrint`)、服务类 (`SpaceFingerPrintService`)、业务对象类 (`SpaceFingerPrintBo`) 和请求参数类 (`SpacePageReqBase`)。 
 * 2. **注解说明**： 
 * - `@RestController`：表明这是一个 RESTful 风格的控制器，用于处理 HTTP 请求并返回 JSON 或 XML 数据。 
 * - `@RequestMapping("/space/rule/space_finger_print")`：定义了该控制器处理请求的基本路径为 `/space/rule/space_finger_print`。 
 * - `@RequiresAuthentication`：表示只有经过身份验证的用户才能访问此接口。 
 * - `@Slf4j`：引入日志功能，方便记录日志信息。 
 * 3. **Swagger 注解**： 
 * - `@Api(tags = "知识库-规则库-指纹库")`：用于生成 API 文档，将此控制器归类到 "知识库-规则库-指纹库" 标签下。 
 * 4. **功能描述**： 
 * - 此控制器主要用于管理与“空间手指打印”相关的操作，如查询、添加、更新或删除等。具体实现依赖于继承的基类方法和相关服务层逻辑。 
 * 5. **设计模式**： 
 * - 使用了模板方法模式（通过继承 `SpaceControllerBase`），避免重复编写常见的 CRUD 操作代码，提高开发效率。 
 * 6. **日志支持**： 
 * - 通过 `@Slf4j` 注解引入日志功能，便于后续调试和监控。 
 * ### 总结： 
 * 该代码是一个典型的基于 Spring Boot 的 RESTful 控制器类，专注于处理“空间手指打印”的相关业务逻辑。它利用了泛型、注解和日志等功能，实现了模块化设计，提高了代码的可维护性和复用性。 
**/

@Api(tags = "知识库-规则库-指纹库")
@RestController
@RequestMapping("/space/rule/space_finger_print")
@RequiresAuthentication
@Slf4j
public class SpaceFingerPrintController extends SpaceControllerBase<SpaceFingerPrintService, SpaceFingerPrint, SpaceFingerPrintBo, SpacePageReqBase>
    
{}
