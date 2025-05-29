package com.cyberpeace.space.controller;

import com.cyberpeace.space.dao.entity.SpaceAlgorithmIndividual;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.SpaceAlgorithmIndividualService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmIndividualBo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 空间算法个人Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceAlgorithmIndividualController` 的 Spring MVC 控制器类，用于处理与“空间算法个人”相关的请求。以下是代码的详细分析： 
 * ### 类的主要功能 
 * 1. **继承关系**： 
 * - `SpaceAlgorithmIndividualController` 继承自 `SpaceControllerBase`，这表明它是一个通用的控制器基类的子类。 
 * - 它使用了泛型参数来指定业务逻辑层、实体类、业务对象（BO）以及分页请求类。 
 * 2. **注解说明**： 
 * - `@RestController`：表示这是一个 RESTful 风格的控制器，返回的数据通常是 JSON 或 XML 格式。 
 * - `@RequestMapping`：指定了该控制器处理的 URL 路径为 `/space/algorithm/algorithm_individual`。 
 * - `@RequiresAuthentication`：表明访问此接口需要用户认证。 
 * - `@Slf4j`：启用日志记录功能。 
 * 3. **API 文档**： 
 * - `@Api` 注解用于生成 Swagger 文档，标签为“知识库-算法库-个人能力评估算法”。 
 * 4. **方法重写**： 
 * - 方法 `checkValue` 被重写以检查某些字段的值是否合法。具体来说，它调用了父类的方法 `checkPercentageValue` 来验证 `rolePercent` 和 `expertisePercent` 字段的总和是否符合预期。 
 * ### 主要依赖的服务和模型 
 * - **服务层**：`SpaceAlgorithmIndividualService` 提供核心业务逻辑。 
 * - **实体类**：`SpaceAlgorithmIndividual` 表示数据库中的数据表结构。 
 * - **业务对象（BO）**：`SpaceAlgorithmIndividualBo` 是业务逻辑处理后的数据传输对象。 
 * - **请求模型**：`SpacePageReqBase` 用于封装分页查询参数。 
 * ### 总结 
 * 这个控制器主要负责管理“空间算法个人”的相关操作，包括但不限于创建、更新、删除等，并且通过继承基类实现了部分通用的功能。同时，它还集成了日志记录和权限控制等功能，确保系统的安全性和可维护性。 
**/
@Api(tags = "知识库-算法库-个人能力评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_individual")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmIndividualController extends SpaceControllerBase<SpaceAlgorithmIndividualService, SpaceAlgorithmIndividual, SpaceAlgorithmIndividualBo, SpacePageReqBase> {

    /**
     * 检查值
     *
     * @param entity 实体
     * @throws
     * @see SpaceAlgorithmIndividual
     */
    @Override
    public void checkValue(SpaceAlgorithmIndividual entity){
        this.checkPercentageValue(entity.getRolePercent() + entity.getExpertisePercent());
    }
}
    
