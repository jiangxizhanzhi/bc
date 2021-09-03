package com.cyberpeace.oj.course.controller;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.oj.course.controller.req.CollectionReq;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;

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
			@ApiImplicitParam(name = "course_id", value = "课程编号") })
	public BaseRsp<LessonInfoBo> lessonDetail(@RequestParam("course_id") String courseId,
			@RequestParam("lesson_id") String lessonId) {
		LessonInfoBo infoBo = courseLessonService.getLessonDetail(courseId, lessonId);
		return new BaseRsp<>(infoBo);
	}

	@ApiOperation("课时收藏")
	@PostMapping("/collection")
	public BaseRsp<String> collectionLesson(@RequestBody CollectionReq req) {

		String userId = UserInfoContext.getUserContext().getUserId();
		String lesson = courseLessonService.collectionLesson(userId, req.getResource(),
				req.getIsCollection());
		return new BaseRsp<>(lesson);
	}

}
