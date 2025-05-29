package com.cyberpeace.cr.crbase.controller;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.rsp.CurrentTimeRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.CodeNameRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 靶场基础Controller
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 1. **类的功能与结构** 
 * - 类名为 `CrBaseController`，位于包 `com.cyberpeace.cr.crbase.controller` 下。 
 * - 注解 `@RestController` 表明这是一个 RESTful 风格的控制器类。 
 * - 注解 `@Slf4j` 提供日志功能。 
 * - 注解 `@RequestMapping("/cr/common")` 定义了该控制器的基础路径为 `/cr/common`。 
 * #### 2. **依赖注入** 
 * - 使用 `@Autowired` 注入了 `CrConstantsService`，用于处理常量相关的业务逻辑。 
 * #### 3. **主要方法** 
 * ##### 方法 1: `getConstantsConfig` 
 * - **功能**: 根据传入的类型 (`type`) 获取对应的常量配置。 
 * - **参数**: 
 * - `type`: 整型值，表示常量的类型。 
 * - **逻辑**: 
 * - 调用 `crConstantsService.getByType(type)` 获取指定类型的常量列表。 
 * - 如果类型是特定的三种（如任务类别、工具类型等），则将结果转换为 `MissionCategoryRsp` 列表并返回。 
 * - 否则，将结果转换为 `CodeNameRsp` 列表并返回。 
 * - **返回值**: 包装在 `BaseRsp` 中的响应数据。 
 * ##### 方法 2: `getCurrentTime` 
 * - **功能**: 返回当前服务器的时间。 
 * - **注解**: 
 * - `@ApiOperation("获取当前服务器时间")`: 描述 API 的作用。 
 * - `@GetMapping("/current_time")`: 映射到 `/cr/common/current_time`。 
 * - `@RequiresPermissions`: 需要用户具备某些权限才能访问此接口。 
 * - 权限包括：课程管理、在线课程、红蓝对抗等。 
 * - **逻辑**: 
 * - 创建 `CurrentTimeRsp` 对象，并设置当前时间。 
 * - 将其包装在 `BaseRsp` 中返回。 
 * - **返回值**: 包含当前时间的 `BaseRsp<CurrentTimeRsp>`。 
 * #### 4. **安全性** 
 * - 方法 `getCurrentTime` 使用了 `@RequiresPermissions` 注解，确保只有拥有指定权限的用户可以访问该接口。 
 * #### 5. **工具类与常量** 
 * - 使用了 `StreamApiUtils` 工具类进行集合的映射操作。 
 * - 引用了多个常量类（如 `CodeNameRsp`, `MissionCategoryRsp`, `CrConstantsConfig` 等）和枚举（如 `CrConstantsConfig.ConstantsType`）。 
 * #### 总结 
 * 该类主要用于提供靶场的基础服务接口，包括常量配置查询和当前时间获取。代码结构清晰，注释详尽，使用了现代 Java 技术（如 Lombok 和 Spring Boot 注解）来简化开发。 
**/
@Api(tags = "靶场公用接口")
@RestController
@Slf4j
@RequestMapping("/cr/common")
//@RequiresAuthentication
public class CrBaseController {

	/**
	 * 靶场常数Service
	 */
	@Autowired
	private CrConstantsService crConstantsService;

	/**
	 * 获取常数配置
	 *
	 * @param type 类型
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("获取常量配置")
	@GetMapping("/constants_config")
	public BaseRsp getConstantsConfig(@ApiParam(name = "type", value = "类型") Integer type) {
		List<CrConstantsConfig> constants = crConstantsService.getByType(type);
		// 任务类型
		if (type.equals(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode()) ||
				type.equals(CrConstantsConfig.ConstantsType.TOOL_TYPE.getCode())
				|| type.equals(CrConstantsConfig.ConstantsType.ALGORITHM_APPLY_TYPE.getCode())) {
			List<MissionCategoryRsp> rsp = StreamApiUtils.map(constants,
					constant -> new MissionCategoryRsp(constant.getResourceId(),
							constant.selectI18nName()));
			return new BaseRsp(rsp);
		} else {
			List<CodeNameRsp> rsp = StreamApiUtils.map(constants,
					constant -> new CodeNameRsp(constant.getCode(), constant.selectI18nName()));
			return new BaseRsp(rsp);
		}
	}

	/**
	 * 获取当前时间
	 *
	 * @return {@code BaseRsp<CurrentTimeRsp> }
	 */
	@ApiOperation("获取当前服务器时间")
	@GetMapping("/current_time")
	@RequiresPermissions(
			value = { MenuConstant.CMS_CLASS_ROOM, MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE,
					MenuConstant.WEB_CR_RED_VS_BLUE, MenuConstant.WEB_CR_TEAM_TRAIN },
			logical = Logical.OR)
	public BaseRsp<CurrentTimeRsp> getCurrentTime() {
		CurrentTimeRsp rsp = new CurrentTimeRsp();
		rsp.setCurrentTime(new Date());
		return new BaseRsp<>(rsp);
	}

}

