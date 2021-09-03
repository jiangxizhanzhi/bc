package com.cyberpeace.oj.course.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/course/lesson")
@Api(tags = "oj-课时相关")
@RequiresAuthentication
public class CourseLessonController {

	@Autowired
	private CourseLessonService courseLessonService;

	@ApiOperation("课时详情查询")
	@GetMapping("")
	@ApiImplicitParams({ @ApiImplicitParam(name = "lesson_id", value = "课时编号"),
			@ApiImplicitParam(name = "course_id", value = "课程编号")
	})
	public BaseRsp<LessonInfoBo> lessonDetail(@RequestParam("course_id") String courseId,
			@RequestParam("lesson_id") String lessonId) {
		LessonInfoBo infoBo = courseLessonService.getLessonDetail(courseId, lessonId);
		return new BaseRsp<>(infoBo);
	}

}
