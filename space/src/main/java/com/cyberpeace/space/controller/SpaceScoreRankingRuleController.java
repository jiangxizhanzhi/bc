package com.cyberpeace.space.controller;

import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.space.dao.entity.SpaceScoreRankingRule;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.SpaceCallCountService;
import com.cyberpeace.space.service.SpaceScoreRankingRuleService;
import com.cyberpeace.space.service.context.rule.SpaceScoreRankingRuleBo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 空间得分排名统治Controller
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
 * 该代码定义了一个名为 `SpaceScoreRankingRuleController` 的 Spring Boot 控制器类，用于处理与空间得分排名规则相关的 HTTP 请求。它继承自 `SpaceControllerBase`，并使用了 Shiro 的权限注解 `@RequiresAuthentication` 来确保只有经过身份验证的用户可以访问。 
 * #### 主要组件 
 * 1. **类定义与继承** 
 * - 类名：`SpaceScoreRankingRuleController` 
 * - 继承自 `SpaceControllerBase`，这是一个泛型基类，参数包括： 
 * - `SpaceScoreRankingRuleService`：服务层接口。 
 * - `SpaceScoreRankingRule`：实体类。 
 * - `SpaceScoreRankingRuleBo`：业务对象（BO）。 
 * - `SpacePageReqBase`：分页请求基类。 
 * 2. **注解** 
 * - `@RestController`：表明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping`：定义了该控制器的基本路径为 `/space/rule/space_score_ranking_rule`。 
 * - `@RequiresAuthentication`：由 Apache Shiro 提供，表示该接口需要用户认证才能访问。 
 * - `@Slf4j`：Lombok 注解，用于简化日志记录操作。 
 * 3. **Swagger 文档** 
 * - `@Api` 注解设置了 API 的标签为 `"知识库-规则库-记分排行规则"`，便于 Swagger 自动生成接口文档。 
 * 4. **包结构** 
 * - 包路径为 `com.cyberpeace.space.controller`，表明这是控制器层代码。 
 * - 使用了多个模块化的工具类和服务类，如 `SpaceScoreRankingRuleService` 和 `SpaceScoreRankingRuleBo`。 
 * #### 总结 
 * 此代码实现了一个基于 Spring Boot 和 Shiro 的 RESTful 接口，用于管理空间得分排名规则。通过继承 `SpaceControllerBase`，它复用了通用的 CRUD 功能，并通过注解增强了安全性与可维护性。同时，结合 Swagger 注解提升了接口的可读性和文档化程度。 
**/

@Api(tags = "知识库-规则库-记分排行规则")
@RestController
@RequestMapping("/space/rule/space_score_ranking_rule")
@RequiresAuthentication
@Slf4j
public class SpaceScoreRankingRuleController extends SpaceControllerBase<SpaceScoreRankingRuleService, SpaceScoreRankingRule, SpaceScoreRankingRuleBo, SpacePageReqBase> {

    @Autowired
    private SpaceCallCountService spaceCallCountService;

    /**
     * 检查值
     *
     * @param entity 实体
     */
    @Override
    public void checkValue(SpaceScoreRankingRule entity) {
        if (!StringUtils.isEmpty(entity.getDescription())) {
            CrCommonValidator.validateNameLen(entity.getDescription(), CrBusinessErrorCode.ILLEGAL_NAME_LEN);
        }
    }

    @Override
    public void detailAddCallTypeNum(){
        spaceCallCountService.addCount(CallType.RULE);
    }
}
