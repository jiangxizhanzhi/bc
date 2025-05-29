package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 课程类别Controller
 *
 * @author SN
 * @version 1.0.0
 * @since 1.0.0
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `CourseCategoryController` 的 Spring MVC 控制器类，用于处理与课程分类相关的 HTTP 请求。以下是该代码的详细分析： 
 * ### 类和注解 
 * - **`@RestController`**: 标识这是一个 RESTful 风格的控制器。 
 * - **`@RequestMapping("/oj/course/direction")`**: 定义了该控制器所有方法的基本路径前缀为 `/oj/course/direction`。 
 * - **`@RequiresAuthentication`**: 表示需要用户认证才能访问这些接口。 
 * ### 成员变量 
 * - **`@Autowired private CourseCategoryService courseCategoryService;`**: 自动注入了一个 `CourseCategoryService` 实例，用于处理业务逻辑。 
 * ### 方法 
 * - **`listDirection`**: 
 * - **`@GetMapping("")`**: 处理 GET 请求，路径为 `/oj/course/direction`（因为类级别的路径前缀已定义）。 
 * - **`@ApiOperation("课程分类")`**: 描述该 API 的操作名称。 
 * - **`@RequiresPermissions`**: 需要特定权限才能访问此方法，允许的权限为 `WEB_OJ_COURSE` 或 `WEB_OJ_ALL_COURSE`。 
 * - **参数**: 
 * - `@RequestParam(value = "children", required = false) Boolean children`: 一个可选的请求参数 `children`，默认值为 `false`。 
 * - **返回值**: 
 * - 返回一个 `BaseRsp<List<CourseCategoryBo>>` 对象，表示操作结果及数据列表。 
 * - **逻辑**: 
 * - 调用 `courseCategoryService.selectAll` 方法获取课程分类信息，并根据 `children` 参数决定是否包含子分类。 
 * - 将结果封装到 `BaseRsp` 对象中并返回。 
 * ### 总结 
 * - **功能**: 该控制器提供了一个接口来获取课程分类信息，支持查询包含子分类的数据。 
 * - **安全性**: 需要用户认证和特定权限才能访问该接口。 
 * - **输入**: 可选的 `children` 参数，用于控制是否包含子分类。 
 * - **输出**: 包含课程分类信息的 JSON 响应。 
 * 这个控制器的主要目的是为前端提供课程分类的查询服务，并确保只有经过认证且具有相应权限的用户可以访问这些数据。 
**/
@Api(tags = "oj-前台课程分类相关api")
@RestController
@RequestMapping("/oj/course/direction")
@RequiresAuthentication
public class CourseCategoryController {

	/**
	 * 课程类别Service
	 *
	 * @see CourseCategoryService
	 */
	@Autowired
	private CourseCategoryService courseCategoryService;

	/**
	 * 列表方向
	 *
	 * @param children 子级
	 * @return {@code BaseRsp<List<CourseCategoryBo>> }
	 * @throws
	 * @see Boolean
	 * @see BaseRsp<List<CourseCategoryBo>>
	 */
	@GetMapping("")
	@ApiOperation("课程分类")
	@RequiresPermissions(value = {MenuConstant.WEB_OJ_COURSE, MenuConstant.WEB_OJ_ALL_COURSE}, logical = Logical.OR)
	public BaseRsp<List<CourseCategoryBo>>
			listDirection(@RequestParam(value = "children", required = false) Boolean children) {
		List<CourseCategoryBo> categories = courseCategoryService
				.selectAll(null != children ? children : CommonConstant.FALSE);

		return new BaseRsp(categories);
	}

}

