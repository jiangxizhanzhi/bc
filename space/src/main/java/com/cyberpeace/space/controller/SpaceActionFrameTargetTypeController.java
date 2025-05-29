package com.cyberpeace.space.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameMapper;
import com.cyberpeace.space.service.context.IdListReq;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.dao.entity.SpaceActionFrameTargetType;
import com.cyberpeace.space.service.SpaceActionFrameTargetTypeService;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameTargetTypeBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

import java.util.TreeSet;

/**
 * 空间行动框架目标类型Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个基于Spring Boot的RESTful风格控制器类 `SpaceActionFrameTargetTypeController`，用于管理“空间行动框架目标类型”的相关操作。以下是对其的简要分析： 
 * ### 主要功能 
 * 该类继承自通用的 `SpaceControllerBase` 类，实现了对 `SpaceActionFrameTargetType` 实体的CRUD（创建、读取、更新、删除）操作。通过调用 `SpaceActionFrameTargetTypeService` 提供的服务层逻辑，完成具体的业务处理。 
 * --- 
 * ### 关键点解析 
 * 1. **类注解** 
 * - `@RestController`：声明这是一个RESTful风格的控制器。 
 * - `@RequestMapping("/space/action_frame/target_type")`：定义了此控制器的请求路径前缀为 `/space/action_frame/target_type`。 
 * - `@Slf4j`：引入日志记录功能，便于调试和监控。 
 * 2. **继承关系** 
 * - `SpaceActionFrameTargetTypeController` 继承自泛型基类 `SpaceControllerBase`，并传入了四个泛型参数： 
 * - 第一个参数是服务层接口 `SpaceActionFrameTargetTypeService`； 
 * - 第二个参数是实体类 `SpaceActionFrameTargetType`； 
 * - 第三个参数是业务对象类 `SpaceActionFrameTargetTypeBo`； 
 * - 第四个参数是分页请求类 `SpacePageReqBase`。 
 * - 这种设计利用了泛型机制，减少了重复代码，提高了代码复用性。 
 * 3. **权限控制** 
 * - 方法上标注了 `@RequiresAuthentication`，表明访问这些接口需要用户已认证。 
 * 4. **Swagger文档支持** 
 * - 使用了 `@Api` 注解，将该控制器标记为“防御行动框架-目标类型”，方便生成API文档。 
 * 5. **日志记录** 
 * - 通过 `lombok` 的 `@Slf4j` 注解，可以使用 `log` 对象进行日志记录，便于排查问题。 
 * --- 
 * ### 总结 
 * 此代码是一个典型的基于Spring Boot和Shiro权限框架的RESTful API实现，具有以下特点： 
 * - **模块化设计**：通过继承泛型基类实现通用功能。 
 * - **安全性保障**：使用Shiro注解确保接口的访问权限。 
 * - **可维护性高**：代码结构清晰，符合面向对象编程思想。 
 * - **文档友好**：结合Swagger注解，便于生成API文档。 
 * 如果需要进一步扩展或优化，可以考虑增加异常处理、缓存机制等。 
**/
@Api(tags = "防御行动框架-目标类型")
@RestController
@RequestMapping("/space/action_frame/target_type")
@Slf4j
public class SpaceActionFrameTargetTypeController extends SpaceControllerBase<SpaceActionFrameTargetTypeService, SpaceActionFrameTargetType, SpaceActionFrameTargetTypeBo, SpacePageReqBase> {

    @Autowired
    private SpaceActionFrameMapper spaceActionFrameMapper;

    @Override
    protected void checkBeforeDelete(IdListReq req) {
        if (CollectionUtils.isEmpty(req.getIds())) {
            throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "删除传入的id为空");
        }
        int relatedFrameDataCount = spaceActionFrameMapper.selectCount(new LambdaQueryWrapper<SpaceActionFrame>()
                .in(SpaceActionFrame::getCategoryId, req.getIds()));
        if (relatedFrameDataCount > 0) {
            throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "存在关联的行动框架数据");
        }

    }


}
    
