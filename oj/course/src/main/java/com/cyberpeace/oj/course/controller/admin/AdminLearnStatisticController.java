package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.controller.req.CourseStatisticsListReq;
import com.cyberpeace.oj.course.controller.req.LessonStatisticsListReq;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseLearnStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseQueryBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Api(tags = "oj-后台学习统计相关api")
@RestController
@RequestMapping("/oj/admin/statistic")
@RequiresAuthentication
@Slf4j
public class AdminLearnStatisticController {

	@Autowired
	private CourseLearnStatisticsService courseLearnStatisticsService;

	@Autowired
	private CourseCategoryService courseCategoryService;

	@ApiOperation("课程统计柱状图")
	@GetMapping("/course_histogram")
	public BaseRsp<HistogramRes> courseHistogram() {
		HistogramRes res = courseLearnStatisticsService.courseHistogram();
		return new BaseRsp<>(res);
	}

	@ApiOperation("课程统计柱状图")
	@GetMapping("/direction_histogram")
	public BaseRsp<HistogramRes> directionHistogram() {
		HistogramRes res = courseLearnStatisticsService.directionHistogram();
		return new BaseRsp<>(res);
	}

  @ApiOperation("课程学习统计list")
  @GetMapping("/course_statistics_list")
  public BaseRsp<IPage<AdminCourseStatisticsRes>> courseStatisticsList(
      @ApiParam(name = "listReq", value = "查询条件") @Valid
          CourseStatisticsListReq courseStatisticsListReq,
      @ApiParam @Validated PageReqBase pageReq) {
	  CourseContext context = BeanUtils.copy(courseStatisticsListReq, CourseContext.class);
    context.setPattern(courseStatisticsListReq.getPattern());

    if (StringUtils.isNotBlank(courseStatisticsListReq.getDirection())) {
      List<String> childrenIds =
          courseCategoryService.getChildrenIds(
              courseStatisticsListReq.getDirection(), CommonConstant.TRUE);
      context.setDirections(childrenIds);
    }
    Page page = pageReq.toPageParam();
    AuthContext authContext =
        AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), false);
    IPage<AdminCourseStatisticsRes> res =
        courseLearnStatisticsService.courseStatisticsList(context, page, authContext);
    return new BaseRsp<>(res);
  }

  @ApiOperation("课程学习统计detail")
  @GetMapping("/lesson_statistics_list")
  public BaseRsp<IPage<AdminLessonStatisticsRes>> lessonStatisticsList(
      @ApiParam(name = "listReq", value = "查询条件") @Valid
          LessonStatisticsListReq lessonStatisticsListReq,
      @ApiParam @Validated PageReqBase pageReq) {
    Page page = pageReq.toPageParam();
    IPage<AdminLessonStatisticsRes> res =
        courseLearnStatisticsService.LessonStatisticsList(lessonStatisticsListReq, page);
    return new BaseRsp<>(res);
  }

  @ApiOperation("课时报告统计detail")
  @GetMapping("/lesson_statistics_detail")
  public BaseRsp<IPage<AdminLessonStatisticsDetailRes>> lessonStatisticsDetail(
          @RequestParam("resource_id") @NotBlank String resourceId, @ApiParam @Validated PageReqBase pageReq) {
    Page page = pageReq.toPageParam();
    IPage<AdminLessonStatisticsDetailRes> res =
        courseLearnStatisticsService.LessonStatisticsDetail(resourceId, page, pageReq);
    return new BaseRsp<>(res);
  }
}
