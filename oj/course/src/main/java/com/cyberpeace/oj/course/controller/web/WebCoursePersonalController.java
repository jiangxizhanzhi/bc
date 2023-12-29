package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.LearnStatsService;
import com.cyberpeace.oj.course.service.bo.PersonalStatsDetail4CourseBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "oj-前台课程个人相关api")
@RestController
@RequestMapping("/oj/course/person")
public class WebCoursePersonalController {

	@Autowired
	LearnStatsService learnStatsService;


	@ApiOperation("最近在学")
	@GetMapping("/course")
	public BaseRsp<ListRsp<PersonalStatsDetail4CourseBo>> personalStatsDetail4Course(
			PageReqBase pageReq) {
		return new BaseRsp<>(
				learnStatsService.getPersonalStatsDetail4Course(pageReq.toPageParam(), UserInfoContext.getUserContext().getUserId()));
	}

}
