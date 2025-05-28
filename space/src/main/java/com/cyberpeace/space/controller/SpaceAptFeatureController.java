/**
 * 这段代码定义了一个名为 `SpaceAptFeatureController` 的 Spring MVC 控制器类，用于处理与空间容易特征（space_apt_feature）相关的请求。以下是对代码的简要分析： 
 * 1. **包名与导入**： 
 * - 类位于 `com.cyberpeace.space.controller` 包中。 
 * - 导入了多个常用的类和注解，包括 Shiro 的权限注解 `@RequiresAuthentication`、Spring 的 `@RestController` 和 `@RequestMapping` 注解，以及 Lombok 的日志注解 `@Slf4j`。 
 * 2. **类注解**： 
 * - `@Api(tags = "知识库-规则库-APT组织库")`：通过 Swagger 注解标记该控制器属于“知识库-规则库-APT组织库”模块。 
 * - `@RestController`：表明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("/space/rule/space_apt_feature")`：为控制器定义了基础路径 `/space/rule/space_apt_feature`。 
 * - `@RequiresAuthentication`：表示访问该接口需要用户已认证。 
 * 3. **继承关系**： 
 * - 该类继承自 `SpaceControllerBase`，并传入了四个泛型参数： 
 * - `SpaceAptFeatureService`：服务层接口。 
 * - `SpaceAptFeature`：实体类。 
 * - `SpaceAptFeatureBo`：业务对象。 
 * - `SpaceAptFeaturePageReq`：分页请求对象。 
 * 4. **功能定位**： 
 * - 这是一个专门用于管理空间容易特征（APT 组织相关数据）的控制器。 
 * - 它可能提供分页查询、增删改查等操作，具体实现逻辑依赖于父类 `SpaceControllerBase` 的方法。 
 * 5. **日志记录**： 
 * - 使用 Lombok 的 `@Slf4j` 注解，简化了日志记录的实现。 
 * 总结： 
 * `SpaceAptFeatureController` 是一个基于 Spring MVC 的 RESTful 控制器，用于处理与空间容易特征相关的 HTTP 请求。它通过继承通用基类实现了大部分业务逻辑，并结合 Shiro 权限控制和 Swagger 文档支持，方便前后端交互和权限管理。 
**/

package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.controller.req.SpaceAptFeaturePageReq;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceAptFeatureService;
import com.cyberpeace.space.service.context.rule.SpaceAptFeatureBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间容易特征Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-规则库-APT组织库")
@RestController
@RequestMapping("/space/rule/space_apt_feature")
@RequiresAuthentication
@Slf4j
public class SpaceAptFeatureController extends SpaceControllerBase<SpaceAptFeatureService, SpaceAptFeature, SpaceAptFeatureBo, SpaceAptFeaturePageReq> {
    
}