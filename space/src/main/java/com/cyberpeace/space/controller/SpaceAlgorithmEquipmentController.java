/**
 * 这段代码定义了一个名为 `SpaceAlgorithmEquipmentController` 的 Spring MVC 控制器类。以下是对其的简要分析： 
 * 1. **包与导入**： 
 * - 该类位于 `com.cyberpeace.space.controller` 包中。 
 * - 导入了多个类和注解，包括自定义实体类、业务逻辑服务、请求模型以及 Swagger 注解等。 
 * 2. **继承关系**： 
 * - `SpaceAlgorithmEquipmentController` 继承自 `SpaceControllerBase`，表明它可能使用了泛型来处理特定的业务逻辑和服务。 
 * 3. **注解说明**： 
 * - `@RestController`：声明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping`：定义了该控制器的基本 URL 路径为 `/space/algorithm/algorithm_equipment`。 
 * - `@RequiresAuthentication`：表示访问此接口需要用户认证。 
 * - `@Slf4j`：引入日志功能，方便调试和记录操作。 
 * 4. **类描述**： 
 * - 文档注释清晰地描述了该类的功能是关于“空间算法设备”的管理，并指定了作者、版本信息及相关的继承类。 
 * 5. **功能定位**： 
 * - 根据命名和上下文推测，此控制器主要用于处理与“知识库-算法库-装备效能评估算法”相关联的操作请求。 
 * 6. **安全性**： 
 * - 使用了 Apache Shiro 的权限控制注解 `@RequiresAuthentication`，确保只有经过身份验证的用户才能调用这些接口。 
 * 综上所述，这段代码实现了一个基于 Spring Boot 的 REST API 控制器，用于管理和操作与特定算法设备相关的数据，同时强调了安全性和可维护性。 
**/
package com.cyberpeace.space.controller;

import com.cyberpeace.space.dao.entity.SpaceAlgorithmEquipment;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.SpaceAlgorithmEquipmentService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmEquipmentBo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 空间算法设备Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-算法库-装备效能评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_equipment")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmEquipmentController extends SpaceControllerBase<SpaceAlgorithmEquipmentService, SpaceAlgorithmEquipment, SpaceAlgorithmEquipmentBo, SpacePageReqBase> {
}
    