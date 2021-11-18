package com.cyberpeace.oj.course.controller.web;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.LessonPracticeSubmitReq;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import com.cyberpeace.oj.course.service.bo.LessonPracticeBo;
import com.cyberpeace.oj.course.service.bo.PracticeAnswerContext;
import com.cyberpeace.oj.course.service.bo.SubmitAnswerContext;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "oj-前台课时相关api")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/lesson")
@Slf4j
public class WebLessonController {

	@Autowired
	private CourseLessonService courseLessonService;

	@ApiOperation("课时详情查询")
	@GetMapping("")
	@ApiImplicitParams({ @ApiImplicitParam(name = "lesson_id", value = "课时编号"),
			@ApiImplicitParam(name = "course_id", value = "课程编号") })
	public BaseRsp<LessonInfoBo> lessonDetail(@RequestParam("course_id") String courseId,
			@RequestParam("lesson_id") String lessonId) {

		String userId = UserInfoContext.getUserContext().getUserId();
		LessonInfoBo infoBo = courseLessonService.getLessonDetail(userId, courseId, lessonId);
		return new BaseRsp<>(infoBo);
	}

	@ApiOperation("课后练习数据查询")
	@GetMapping("/practice_after_class")
	@ApiImplicitParams({ @ApiImplicitParam(name = "resource_id", value = "课时编号"),
			@ApiImplicitParam(name = "course_id", value = "课程编号") })
	public BaseRsp<LessonPracticeBo> lessonPractice(@RequestParam("resource_id") String resourceId,
			@RequestParam("course_id") String courseId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);

		LessonPracticeBo practiceBo = courseLessonService.practiceByLesson(courseId, resourceId,
				authContext);
		return new BaseRsp<>(practiceBo);

	}

	@ApiOperation("课后练习解答")
	@PostMapping("/submit_answer")
	public BaseRsp<EvaluateResult>
			submitAnswer(@RequestBody @Validated LessonPracticeSubmitReq submitReq) {

		List<PracticeAnswerContext> answerContexts = BeanUtils.deepCopyList(submitReq.getAnswers(),
				PracticeAnswerContext.class);
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.TRUE);
		SubmitAnswerContext answerContext = BeanUtils.copy(submitReq, SubmitAnswerContext.class);

		EvaluateResult evaluateResult = courseLessonService.submitLessonPractice(answerContext,
				answerContexts, authContext);

		return new BaseRsp<>(evaluateResult);

	}

}
