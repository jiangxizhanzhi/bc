/**
 * 这段代码是一个基于Spring Boot框架实现的控制器类 `SpaceActionTargetController`，用于管理空间行动目标（Space Action Target）。以下是对代码的简要分析： 
 * 1. **类的基本信息**： 
 * - 类名：`SpaceActionTargetController` 
 * - 包名：`com.cyberpeace.space.controller` 
 * - 注解：`@RestController` 表明这是一个RESTful风格的控制器，`@RequestMapping` 定义了该控制器的请求路径为 `/space/action_frame/action_target`，`@RequiresAuthentication` 表示需要用户认证才能访问。 
 * 2. **继承与泛型**： 
 * - 该类继承自 `SpaceActionFrameBaseController`，并使用了泛型参数，分别指定了相关的服务、实体类、业务对象和请求参数类型。 
 * - 泛型的具体类型包括：`SpaceActionTargetService`、`SpaceActionTarget`、`SpaceActionTargetBo` 和 `SpaceActionTargetPageReq`。 
 * 3. **依赖注入**： 
 * - 使用了 `@Autowired` 注解将 `SpaceActionFrameService` 注入到类中，用于处理与框架相关的业务逻辑。 
 * 4. **方法重写**： 
 * - 覆写了父类中的两个抽象方法： 
 * - `getUpperRelated(List<String> upperIds)`：根据上级ID列表获取相关的实体对象列表。 
 * - `getUpperIds(SpaceActionTargetBo req)`：从请求对象中提取上级ID列表。 
 * 5. **功能描述**： 
 * - 该控制器的主要功能是管理和操作空间行动目标，通过调用服务层的方法来完成具体业务逻辑。 
 * - 提供了基于分页请求的处理能力，并且利用了Stream API进行数据转换。 
 * 6. **Swagger注解**： 
 * - 使用了 `@Api` 注解来描述API的功能标签为“知识库-防御行动框架-作战目标管理”。 
 * 总体来说，这个类实现了对空间行动目标的CRUD操作，并且通过继承和泛型机制减少了重复代码，提高了代码的复用性和可维护性。 
**/
package com.cyberpeace.space.controller;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.space.dao.entity.SpaceActionTarget;
import com.cyberpeace.space.model.IdAndName;
import com.cyberpeace.space.model.SpaceFrameBaseTable;
import com.cyberpeace.space.model.controller.SpaceActionFrameBaseController;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceActionTargetService;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetBo;
import com.cyberpeace.space.service.context.frame.SpaceActionTargetPageReq;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 空间行动目标Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceActionFrameBaseController
 * @since 1.0.0
 */
@Api(tags = "知识库-防御行动框架-作战目标管理")
@RestController
@RequestMapping("/space/action_frame/action_target")
@RequiresAuthentication
public class SpaceActionTargetController extends SpaceActionFrameBaseController<SpaceActionTargetService, SpaceActionTarget, SpaceActionTargetBo, SpaceActionTargetPageReq> {

    /**
     * 框架Service
     *
     * @see SpaceActionFrameService
     */
    @Autowired
    private SpaceActionFrameService frameService;


    @Override
    protected List<? extends SpaceFrameBaseTable> getUpperRelated(List<String> upperIds) {
        return frameService.listByIds(upperIds);
    }

    @Override
    protected List<String> getUpperIds(SpaceActionTargetBo req) {
        return StreamApiUtils.map(req.getFrame(), IdAndName::getResourceId);
    }
}
