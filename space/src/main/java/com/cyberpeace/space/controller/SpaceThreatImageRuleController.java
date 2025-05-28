/**
 * 该代码定义了一个名为 `SpaceThreatImageRuleController` 的 Spring Boot 控制器类，继承自 `SpaceControllerBase`。以下是代码的主要功能和特点： 
 * 1. **功能描述**： 
 * - 该控制器负责处理与“空间威胁图像规则”相关的业务逻辑。 
 * - 它提供了基于 Shiro 的认证保护（通过 `@RequiresAuthentication` 注解），确保只有经过身份验证的用户可以访问相关接口。 
 * 2. **继承关系**： 
 * - 类 `SpaceThreatImageRuleController` 继承自泛型基类 `SpaceControllerBase`，并指定了四个泛型参数： 
 * - `SpaceThreatImageRuleService`：服务层接口。 
 * - `SpaceThreatImageRule`：数据实体类。 
 * - `SpaceThreatImageRuleBo`：业务对象类。 
 * - `SpacePageReqBase`：分页请求参数类。 
 * 3. **核心方法**： 
 * - 覆写了抽象方法 `checkNameDuplication`，用于检查名称是否重复。当前实现总是返回 `true`，即默认认为名称不会重复。 
 * 4. **注解说明**： 
 * - `@RestController`：声明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping`：定义了该控制器的基础路径为 `/space/rule/space_threat_image_rule`。 
 * - `@Api`：使用 Swagger 注解标记该类属于“知识库-规则库-威胁画像规则”模块。 
 * - `@Slf4j`：引入日志功能，方便记录运行时信息。 
 * 5. **依赖组件**： 
 * - `SpaceThreatImageRuleService`：负责具体的业务逻辑操作。 
 * - `SpaceThreatImageRule` 和 `SpaceThreatImageRuleBo`：分别表示数据库表对应的实体和业务对象。 
 * - `SpacePageReqBase`：用于封装分页查询的请求参数。 
 * 总结：此代码是一个典型的基于 Spring Boot 的 RESTful API 控制器类，主要用来管理“空间威胁图像规则”的相关操作，并结合 Shiro 和 Swagger 提供了安全性和文档化支持。 
**/

package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceThreatImageRule;
import com.cyberpeace.space.service.SpaceThreatImageRuleService;
import com.cyberpeace.space.service.context.rule.SpaceThreatImageRuleBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间威胁图像统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-规则库-威胁画像规则")
@RestController
@RequestMapping("/space/rule/space_threat_image_rule")
@RequiresAuthentication
@Slf4j
public class SpaceThreatImageRuleController extends SpaceControllerBase<SpaceThreatImageRuleService, SpaceThreatImageRule, SpaceThreatImageRuleBo, SpacePageReqBase> {

	/**
	 * 检查名称重复
	 *
	 * @param entity 实体
	 * @return boolean
	 * @throws
	 * @see SpaceThreatImageRule
	 * @see boolean
	 */
	@Override
	public boolean checkNameDuplication(SpaceThreatImageRule entity) {
		return true;
	}
}