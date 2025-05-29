package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.context.rule.SpaceBaseAlgorithmBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceBaseAlgorithm;
import com.cyberpeace.space.service.SpaceBaseAlgorithmService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间基础算法Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceBaseAlgorithmController` 的 Spring MVC 控制器类，用于处理与“空间基础算法”相关的 HTTP 请求。以下是对其功能和结构的简要分析： 
 * ### 功能分析： 
 * 1. **继承关系**： 
 * - `SpaceBaseAlgorithmController` 继承自 `SpaceControllerBase`，这是一个泛型基类，通常负责通用的 CRUD 操作。 
 * - 泛型参数包括： 
 * - `SpaceBaseAlgorithmService`：负责业务逻辑的服务层。 
 * - `SpaceBaseAlgorithm`：数据库实体类。 
 * - `SpaceBaseAlgorithmBo`：业务对象（BO）。 
 * - `SpacePageReqBase`：分页请求参数。 
 * 2. **权限控制**： 
 * - 使用了 `@RequiresAuthentication` 注解，表示该控制器下的所有方法都需要用户认证后才能访问。 
 * 3. **Swagger 文档支持**： 
 * - 使用了 `@Api` 注解，将该控制器标记为“知识库-算法库-基础算法库”，便于生成 Swagger API 文档。 
 * 4. **日志记录**： 
 * - 使用了 `@Slf4j` 注解，引入 Lombok 提供的日志功能，方便记录运行时信息。 
 * 5. **请求映射**： 
 * - 使用了 `@RequestMapping` 注解，将该控制器的所有请求路径前缀设置为 `/space/algorithm/base_algorithm`。 
 * ### 总结： 
 * 此代码实现了一个基于 Shiro 认证的安全性保护、支持 Swagger 文档生成的基础算法管理控制器，通过继承基类实现了通用的 CRUD 功能，并结合泛型设计增强了代码的可复用性和扩展性。 
**/

@Api(tags = "知识库-算法库-基础算法库")
@RestController
@RequestMapping("/space/algorithm/base_algorithm")
@RequiresAuthentication
@Slf4j
public class SpaceBaseAlgorithmController extends SpaceControllerBase<SpaceBaseAlgorithmService, SpaceBaseAlgorithm, SpaceBaseAlgorithmBo, SpacePageReqBase> {

}
