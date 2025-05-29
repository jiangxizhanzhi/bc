package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceAlgorithmTeam;
import com.cyberpeace.space.service.SpaceAlgorithmTeamService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmTeamBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间算法团队Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceAlgorithmTeamController` 的 Spring MVC 控制器类，用于处理与空间算法团队相关的请求。以下是对其的简要分析： 
 * 1. **继承关系**： 
 * - `SpaceAlgorithmTeamController` 继承自 `SpaceControllerBase`，表明它可能复用了基类的一些通用功能。 
 * 2. **注解说明**： 
 * - `@RestController`：标明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping`：指定该控制器处理的 URL 路径为 `/space/algorithm/algorithm_team`。 
 * - `@RequiresAuthentication`：表示访问此接口需要用户认证。 
 * - `@Slf4j`：引入日志功能，方便调试和记录运行时信息。 
 * 3. **功能描述**： 
 * - 类注释提到这是“空间算法团队”的 Controller，并且与“知识库-算法库-团队能力评估算法”相关联。 
 * - 提供了 `checkValue` 方法来验证某些值的有效性（如百分比之和是否合理）。 
 * 4. **依赖的服务和实体**： 
 * - 使用了 `SpaceAlgorithmTeamService` 来执行业务逻辑。 
 * - 数据模型为 `SpaceAlgorithmTeam` 和 `SpaceAlgorithmTeamBo`。 
 * - 请求参数基于 `SpacePageReqBase`。 
 * 5. **日志记录**： 
 * - 使用 Lombok 的 `@Slf4j` 注解简化了日志记录的实现。 
 * 总体来说，这个类主要负责管理空间算法团队的相关数据，确保操作的安全性和正确性，并通过继承和注解增强了代码的可维护性和扩展性。 
**/
@Api(tags = "知识库-算法库-团队能力评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_team")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmTeamController extends SpaceControllerBase<SpaceAlgorithmTeamService, SpaceAlgorithmTeam, SpaceAlgorithmTeamBo, SpacePageReqBase> {
    /**
     * 检查值
     *
     * @param entity 实体
     * @throws
     * @see SpaceAlgorithmTeam
     */
    @Override
    public void checkValue(SpaceAlgorithmTeam entity){
        this.checkPercentageValue(entity.getRolePercent() + entity.getExpertisePercent());
    }
}
    
