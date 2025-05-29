package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.dao.entity.SpaceTacticDefensePlan;
import com.cyberpeace.space.service.SpaceTacticDefensePlanService;
import com.cyberpeace.space.service.context.frame.SpaceFrameLevel;
import com.cyberpeace.space.service.context.tactic.DefensePlanLevelUpdateReq;
import com.cyberpeace.space.service.context.tactic.SpaceTacticDefensePlanBo;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间战法防御计划Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `SpaceTacticDefensePlanController` 的 Spring MVC 控制器类，用于处理与空间战法防御计划相关的 HTTP 请求。以下是代码的主要功能和结构分析： 
 * ### 类的功能和继承关系 
 * 1. **类名**：`SpaceTacticDefensePlanController` 
 * 2. **功能**：该控制器负责管理空间战法防御计划的相关操作。 
 * 3. **继承**：从 `SpaceControllerBase` 继承，表明它可能复用了一些通用的逻辑。 
 * 4. **注解**： 
 * - `@RestController`：标明这是一个 RESTful 风格的控制器。 
 * - `@RequestMapping("/space/tactic/defense_plan")`：定义了所有请求路径的基地址。 
 * - `@RequiresAuthentication`：确保只有经过身份验证的用户才能访问这些接口。 
 * ### 方法分析 
 * #### 1. `getADetail` 
 * - **功能**：根据资源 ID 查询并返回防御计划的详细信息。 
 * - **参数**：`resource_id`（通过 URL 参数传递）。 
 * - **逻辑**： 
 * - 调用 `baseService.getById(resource_id)` 获取对应的实体对象。 
 * - 如果实体为空，则抛出 `BusinessException`，提示资源未找到。 
 * - 将实体转换为业务对象 (`SpaceTacticDefensePlanBo`) 并设置框架级别。 
 * - 返回包含业务对象的 `BaseRsp` 对象。 
 * - **注解**： 
 * - `@GetMapping("")`：映射 GET 请求到该方法。 
 * - `@ApiOperation("详情")`：描述 API 的用途。 
 * #### 2. `updatePlan` 
 * - **功能**：根据请求体中的数据生成新的框架。 
 * - **参数**：`req` 是一个 JSON 格式的请求体，类型为 `DefensePlanLevelUpdateReq`。 
 * - **逻辑**： 
 * - 调用 `baseService.renewFrame(req)` 生成新的框架。 
 * - 返回包含新框架级别的 `BaseRsp` 对象。 
 * - **注解**： 
 * - `@PostMapping("/renew")`：映射 POST 请求到该方法。 
 * - `@ApiOperation("生成新框架")`：描述 API 的用途。 
 * ### 其他细节 
 * - **日志记录**：使用 `@Slf4j` 注解，便于记录运行时的日志信息。 
 * - **依赖注入**：通过 `@Autowired` 注入了 `SpaceTacticDefensePlanService` 等服务。 
 * - **Swagger 文档支持**：使用 `io.swagger.annotations` 提供 API 文档的支持。 
 * ### 总结 
 * 该控制器主要实现了两个核心功能： 
 * 1. 根据资源 ID 查询并返回防御计划的详细信息。 
 * 2. 根据请求体生成新的框架级别。 
 * 通过继承基类和使用注解，代码保持了良好的可读性和扩展性，同时利用了 Spring 和 Swagger 提供的功能来简化开发过程。 
**/
@Api(tags = "战法库-防御战法方案")
@RestController
@RequestMapping("/space/tactic/defense_plan")
@RequiresAuthentication
@Slf4j
public class SpaceTacticDefensePlanController extends SpaceControllerBase<SpaceTacticDefensePlanService, SpaceTacticDefensePlan, SpaceTacticDefensePlanBo, SpacePageReqBase> {

	/**
	 * 获取adetail
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<SpaceTacticDefensePlanBo> }
	 */
	@ApiOperation("详情")
	@GetMapping("")
	@Override
	public BaseRsp<SpaceTacticDefensePlanBo>
			getADetail(@ApiParam(value = "resource_id", name = "resource_id") String resource_id) {
		SpaceTacticDefensePlan plan = baseService.getById(resource_id);
		if (null == plan) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		SpaceTacticDefensePlanBo bo = plan.entity2Bo(Maps.newHashMap());
		bo.setLevel(baseService.getFrameLevels(plan));
		return new BaseRsp<>(bo);
	}

	/**
	 * @param req 请求体
	 * @return 基础RSP<空间框架级别>
	 * @throws
	 * @see DefensePlanLevelUpdateReq
	 * @see BaseRsp<SpaceFrameLevel>
	 */
	@ApiOperation("生成新框架")
	@PostMapping("/renew")
	public BaseRsp<SpaceFrameLevel> updatePlan(@RequestBody DefensePlanLevelUpdateReq req) {
		SpaceFrameLevel level = baseService.renewFrame(req);
		return new BaseRsp(level);
	}

}

