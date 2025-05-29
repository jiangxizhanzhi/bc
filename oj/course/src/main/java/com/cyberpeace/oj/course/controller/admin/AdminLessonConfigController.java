package com.cyberpeace.oj.course.controller.admin;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 管理课配置Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个用于管理课程配置的控制器 `AdminLessonConfigController`。以下是对其主要部分的简要分析： 
 * ### 包和导入 
 * - 导入了多个包，包括自定义的业务逻辑、服务接口以及一些常用的工具类。 
 * - 使用了 `@RestController` 注解，表明这是一个 RESTful 控制器。 
 * ### 类和注解 
 * - `AdminLessonConfigController` 类使用了 `@RestController` 和 `@RequestMapping` 注解，指定了请求路径为 `/oj/admin/lessonconfig`。 
 * - 使用了 `@Api` 注解来描述这个控制器的用途，即“后台课程目录相关api”。 
 * - `@RequiresAuthentication` 注解确保只有经过身份验证的用户才能访问这些API。 
 * ### 成员变量 
 * - `lessonConfigService` 是一个通过 `@Autowired` 自动注入的服务对象，用于处理与课程配置相关的业务逻辑。 
 * ### 方法 
 * 1. **获取课程配置** 
 * - 方法 `getLessonConfig` 使用 `@GetMapping` 注解，处理 GET 请求以获取特定课程的配置。 
 * - 参数 `courseId` 通过 `@RequestParam` 注解从请求中获取。 
 * - 调用 `lessonConfigService.getLessonConfig` 方法获取课程配置，并返回一个包含配置信息的 `BaseRsp` 对象。 
 * - 使用了 `@ApiOperation` 注解来描述此方法的功能。 
 * 2. **设置课程配置** 
 * - 方法 `setLessonConfig` 使用 `@PutMapping` 注解，处理 PUT 请求以更新课程配置。 
 * - 参数 `configBo` 通过 `@RequestBody` 注解从请求体中获取。 
 * - 调用 `lessonConfigService.updateLessonConfig` 方法更新课程配置，并返回一个包含更新后配置信息的 `BaseRsp` 对象。 
 * - 使用了 `@ApiOperation` 注解来描述此方法的功能。 
 * ### 总结 
 * 该控制器提供了两个主要功能：获取课程配置和设置课程配置。这两个操作都需要经过身份验证和权限检查（通过 `@RequiresPermissions` 注解）。代码结构清晰，使用了 Spring Boot 的注解来简化开发过程。 
**/
@RestController
@RequestMapping("/oj/admin/lessonconfig")
@Api(tags = "oj-后台课程目录相关api")
@RequiresAuthentication
public class AdminLessonConfigController {

	/**
	 * 课配置Service
	 *
	 * @see LessonConfigService
	 */
	@Autowired
	private LessonConfigService lessonConfigService;

	/**
	 * 获取课配置
	 *
	 * @param courseId 课程id
	 * @return {@code BaseRsp<LessonConfigBo> }
	 * @throws
	 * @see String
	 * @see BaseRsp<LessonConfigBo>
	 */
	@ApiOperation("后台课程目录查询")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<LessonConfigBo> getLessonConfig(@ApiParam(name = "course_id",
			value = "课程编号") @RequestParam("course_id") String courseId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		LessonConfigBo lessonConfig = lessonConfigService.getLessonConfig(courseId, authContext);
		return new BaseRsp(lessonConfig);
	}

	/**
	 * 设置课配置
	 *
	 * @param configBo 配置博
	 * @return {@code BaseRsp<LessonConfigBo> }
	 * @throws
	 * @see LessonConfigBo
	 * @see BaseRsp<LessonConfigBo>
	 */
	@ApiOperation("编排课时")
	@PutMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<LessonConfigBo> setLessonConfig(
			@ApiParam(name = "configBo", value = "课程编排信息") @RequestBody LessonConfigBo configBo) {

		LessonConfigBo lessonConfigBo = lessonConfigService.updateLessonConfig(configBo);
		return new BaseRsp(lessonConfigBo);
	}

}

