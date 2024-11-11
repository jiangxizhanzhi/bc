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
 * @date 2024/11/11
 */
@Api(tags = "oj-后台学习统计相关api")
@RestController
@RequestMapping("/oj/admin/statistic")
@RequiresAuthentication
@Slf4j
public class AdminLearnStatisticController {

	/**
	 * 课程学习统计学Service
	 */
	@Autowired
	private CourseLearnStatisticsService courseLearnStatisticsService;

	/**
	 * 课程类别Service
	 */
	@Autowired
	private CourseCategoryService courseCategoryService;

	/**
	 * 课程直方图
	 *
	 * @return {@code BaseRsp<HistogramRes> }
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
