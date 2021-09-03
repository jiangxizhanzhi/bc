package com.cyberpeace.oj.course.controller;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "oj-课程目录相关")
@RestController
@RequestMapping("/lesson/config")
@RequiresAuthentication
public class LessonConfigController {

	@Autowired
	private CourseLessonService courseLessonService;

	@GetMapping("")
	@ApiOperation("课程目录查询")
	public BaseRsp<LessonConfigBo> getLessonConfig(@ApiParam(name = "course_id",
			value = "课程编号") @RequestParam("course_id") String courseId) {

		String userId = UserInfoContext.getUserContext().getUserId();
		LessonConfigBo config = courseLessonService.getLessonConfig(courseId, userId);
		return new BaseRsp<>(config);
	}

}
