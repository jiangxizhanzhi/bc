/**
 * 这段代码定义了一个名为 `AdminLearnStatisticController` 的 Spring MVC 控制器类，用于处理后台管理的学习统计相关的 API 请求。以下是对该代码的简要分析： 
 * ### 类结构与注解 
 * 1. **类声明**： 
 * - `AdminLearnStatisticController` 类位于 `com.cyberpeace.oj.course.controller.admin` 包下。 
 * - 使用了 `@RestController` 注解，表明这是一个 RESTful 风格的控制器。 
 * - 使用了 `@RequestMapping` 注解，指定该控制器的所有请求路径前缀为 `/oj/admin/statistic`。 
 * 2. **权限控制**： 
 * - 使用了 `@RequiresAuthentication` 注解，表示所有方法都需要用户进行认证。 
 * - 使用了 `@RequiresPermissions` 注解，限制只有具有特定权限的用户才能访问某些方法（例如 `MenuConstant.CMS_COURSE_STATISTICS`）。 
 * 3. **日志记录**： 
 * - 使用了 `@Slf4j` 注解，自动引入了 SLF4J 日志记录功能。 
 * ### 成员变量 
 * 1. **依赖注入**： 
 * - `courseLearnStatisticsService`：通过 `@Autowired` 注入，用于处理课程学习统计相关的业务逻辑。 
 * - `courseCategoryService`：通过 `@Autowired` 注入，用于处理课程类别相关的业务逻辑。 
 * ### 方法 
 * 1. **课程统计柱状图 (`courseHistogram`)** 
 * - 返回一个 `BaseRsp<HistogramRes>` 对象。 
 * - 调用 `courseLearnStatisticsService.courseHistogram` 方法获取数据，并封装到响应对象中返回。 
 * 2. **方向统计柱状图 (`directionHistogram`)** 
 * - 返回一个 `BaseRsp<HistogramRes>` 对象。 
 * - 调用 `courseLearnStatisticsService.directionHistogram` 方法获取数据，并封装到响应对象中返回。 
 * 3. **课程统计列表 (`courseStatisticsList`)** 
 * - 接受一个 `CourseStatisticsListReq` 请求体参数。 
 * - 将请求体转换为 `CourseContext` 对象，并设置相关参数。 
 * - 调用 `courseLearnStatisticsService.courseStatisticsList` 方法获取数据，并封装到响应对象中返回。 
 * 4. **课时统计列表 (`lessonStatisticsList`)** 
 * - 接受一个 `LessonStatisticsListReq` 请求体参数。 
 * - 调用 `courseLearnStatisticsService.LessonStatisticsList` 方法获取数据，并封装到响应对象中返回。 
 * 5. **课时统计详情 (`lessonStatisticsDetail`)** 
 * - 接受一个 `LessonStatisticsDetailReq` 请求体参数。 
 * - 调用 `courseLearnStatisticsService.LessonStatisticsDetail` 方法获取数据，并封装到响应对象中返回。 
 * ### 总结 
 * - `AdminLearnStatisticController` 类主要负责处理后台管理的学习统计相关的 API 请求。 
 * - 通过依赖注入的 `CourseLearnStatisticsService` 和 `CourseCategoryService` 来处理具体的业务逻辑。 
 * - 方法返回的数据都封装在 `BaseRsp` 或其子类 `ListRsp` 中，便于统一处理响应格式。 
 * - 所有方法均需用户认证并通过权限检查后才能访问。 
**/
package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.CourseStatisticsListReq;
import com.cyberpeace.oj.course.controller.req.LessonStatisticsDetailReq;
import com.cyberpeace.oj.course.controller.req.LessonStatisticsListReq;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseLearnStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理学习统计Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Api(tags = "oj-后台学习统计相关api")
@RestController
@RequestMapping("/oj/admin/statistic")
@RequiresAuthentication
@Slf4j
public class AdminLearnStatisticController {

	/**
	 * 课程学习统计学Service
	 *
	 * @see CourseLearnStatisticsService
	 */
	@Autowired
	private CourseLearnStatisticsService courseLearnStatisticsService;

	/**
	 * 课程类别Service
	 *
	 * @see CourseCategoryService
	 */
	@Autowired
	private CourseCategoryService courseCategoryService;

	/**
	 * 课程直方图
	 *
	 * @return {@code BaseRsp<HistogramRes> }
	 * @throws
	 * @see BaseRsp<HistogramRes>
	 */
	@ApiOperation("课程统计柱状图")
	@GetMapping("/course_histogram")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<HistogramRes> courseHistogram() {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		HistogramRes res = courseLearnStatisticsService.courseHistogram(authContext);
		return new BaseRsp<>(res);
	}

	/**
	 * 方向直方图
	 *
	 * @return {@code BaseRsp<HistogramRes> }
	 * @throws
	 * @see BaseRsp<HistogramRes>
	 */
	@ApiOperation("课程方向统计柱状图")
	@GetMapping("/direction_histogram")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<HistogramRes> directionHistogram() {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		HistogramRes res = courseLearnStatisticsService.directionHistogram(authContext);
		return new BaseRsp<>(res);
	}

	/**
	 * 课程统计列表
	 *
	 * @param courseStatisticsListReq 课程统计学列表请求体
	 * @return {@code BaseRsp<List<AdminCourseStatisticsRes>> }
	 * @throws
	 * @see CourseStatisticsListReq
	 * @see BaseRsp<List<AdminCourseStatisticsRes>>
	 */
	@ApiOperation("课程学习统计list")
	@GetMapping("/course_statistics_list")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<List<AdminCourseStatisticsRes>> courseStatisticsList(@ApiParam(name = "listReq",
			value = "查询条件") @Valid CourseStatisticsListReq courseStatisticsListReq) {
		CourseContext context = BeanUtils.copy(courseStatisticsListReq, CourseContext.class);
		context.setPattern(courseStatisticsListReq.getPattern());

		if (StringUtils.isNotBlank(courseStatisticsListReq.getDirection())) {
			List<String> childrenIds = courseCategoryService
					.getChildrenIds(courseStatisticsListReq.getDirection(), CommonConstant.TRUE);
			context.setDirections(childrenIds);
		}
		Page page = courseStatisticsListReq.toPageParam();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		ListRsp<AdminCourseStatisticsRes> res = courseLearnStatisticsService
				.courseStatisticsList(context, page, authContext);
		return new BaseRsp(res);
	}

	/**
	 * 课程统计列表
	 *
	 * @param pageReq 分页请求体
	 * @return {@code BaseRsp<ListRsp<AdminLessonStatisticsRes>> }
	 * @throws
	 * @see LessonStatisticsListReq
	 * @see BaseRsp<ListRsp<AdminLessonStatisticsRes>>
	 */
	@ApiOperation("课时学习统计list")
	@GetMapping("/lesson_statistics_list")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<ListRsp<AdminLessonStatisticsRes>>
			lessonStatisticsList(@ApiParam @Validated LessonStatisticsListReq pageReq) {
		Page page = pageReq.toPageParam();
		ListRsp<AdminLessonStatisticsRes> res = courseLearnStatisticsService
				.LessonStatisticsList(pageReq.getCourseId(), page, pageReq);
		return new BaseRsp(res);
	}

	/**
	 * 课统计学详情
	 *
	 * @param pageReq 分页请求体
	 * @return {@code BaseRsp<ListRsp<AdminLessonStatisticsDetailRes>> }
	 * @throws
	 * @see LessonStatisticsDetailReq
	 * @see BaseRsp<ListRsp<AdminLessonStatisticsDetailRes>>
	 */
	@ApiOperation("课时报告统计detail")
	@GetMapping("/lesson_statistics_detail")
	@RequiresPermissions(MenuConstant.CMS_COURSE_STATISTICS)
	public BaseRsp<ListRsp<AdminLessonStatisticsDetailRes>>
			lessonStatisticsDetail(@ApiParam @Validated LessonStatisticsDetailReq pageReq) {
		Page page = pageReq.toPageParam();
		ListRsp<AdminLessonStatisticsDetailRes> res = courseLearnStatisticsService
				.LessonStatisticsDetail(pageReq.getOrgId(), pageReq.getLessonId(),
						pageReq.getSearch(), page, pageReq.getSort());
		return new BaseRsp(res);
	}
}
