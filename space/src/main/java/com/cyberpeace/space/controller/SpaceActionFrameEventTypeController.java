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
import com.cyberpeace.space.dao.entity.SpaceActionFrameEventType;
import com.cyberpeace.space.service.SpaceActionFrameEventTypeService;
import com.cyberpeace.space.service.context.frame.SpaceActionFrameEventTypeBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间行动框架事件类型Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 1. **类的基本信息** 
 * - 类名：`SpaceActionFrameEventTypeController` 
 * - 包路径：`com.cyberpeace.space.controller` 
 * - 继承自：`SpaceControllerBase`，这是一个泛型基类，具体参数为： 
 * - `SpaceActionFrameEventTypeService`：服务层接口。 
 * - `SpaceActionFrameEventType`：实体类。 
 * - `SpaceActionFrameEventTypeBo`：业务对象（BO）。 
 * - `SpacePageReqBase`：请求参数基类。 
 * #### 2. **功能描述** 
 * - 该类是一个基于Spring MVC的RESTful控制器，用于管理“空间行动框架事件类型”的相关操作。 
 * - 它通过继承`SpaceControllerBase`实现了通用的CRUD操作逻辑。 
 * #### 3. **注解分析** 
 * - `@RestController`：声明该类是一个RESTful风格的控制器。 
 * - `@RequestMapping("/space/action_frame/event_type")`：定义了该控制器的访问路径为`/space/action_frame/event_type`。 
 * - `@Slf4j`：引入日志功能，方便记录日志。 
 * - `@Api(tags = "防御行动框架-威胁事件类型")`：使用Swagger注解，标明该接口属于“防御行动框架-威胁事件类型”模块。 
 * #### 4. **安全性** 
 * - `@RequiresAuthentication`：表明访问该接口需要用户认证。未认证的用户无法访问。 
 * #### 5. **依赖的服务与数据模型** 
 * - **服务层**：`SpaceActionFrameEventTypeService`，负责具体的业务逻辑处理。 
 * - **实体类**：`SpaceActionFrameEventType`，表示数据库中的表结构。 
 * - **业务对象（BO）**：`SpaceActionFrameEventTypeBo`，用于封装业务逻辑中需要的数据。 
 * - **请求参数基类**：`SpacePageReqBase`，作为分页或其他复杂查询的请求参数。 
 * #### 6. **日志功能** 
 * - 使用`log`记录运行时信息，便于排查问题或监控接口调用情况。 
 * #### 总结 
 * 该代码实现了一个标准的RESTful风格的控制器，封装了对“空间行动框架事件类型”的增删改查操作，并通过继承`SpaceControllerBase`减少了重复代码。同时，它结合了权限控制、日志记录以及Swagger文档支持，是一个功能完善且易于扩展的模块。 
**/
@Api(tags = "防御行动框架-威胁事件类型")
@RestController
@RequestMapping("/space/action_frame/event_type")
@Slf4j
public class SpaceActionFrameEventTypeController extends SpaceControllerBase<SpaceActionFrameEventTypeService, SpaceActionFrameEventType, SpaceActionFrameEventTypeBo, SpacePageReqBase> {

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
    
