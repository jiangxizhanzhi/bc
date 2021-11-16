package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.LessonPracticeSubmitReq;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import com.cyberpeace.oj.course.service.bo.LessonPracticeBo;
import com.cyberpeace.oj.course.service.bo.PracticeAnswerContext;
import com.cyberpeace.oj.course.service.bo.SubmitAnswerContext;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteAddReq;
import com.cyberpeace.oj.ojbase.controller.res.CommentRes;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "oj-前台课时相关api")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/lesson")
@Slf4j
public class WebLessonController {

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	private CourseMapper courseMapper;

	@Autowired
	private CommentService commentService;

	@Autowired
	private NoteService noteService;

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

	@ApiOperation("课时评论")
	@PostMapping("/comment")
	public BaseRsp<CommentRes> lessonComment(@RequestBody @Valid CommentReq commentReq) {

		CourseLesson lesson = courseLessonMapper.selectById(commentReq.getBusinessId());
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", commentReq.getBusinessId());
			throw new BusinessException(OjBusinessErrorCode.LESSON_NOT_FIND);
		}

		Course course = courseMapper.selectById(lesson.getCourseId());
		if (null == course) {
			log.error("Lesson not found! course_id=[{}]", lesson.getCourseId());
			throw new BusinessException(OjBusinessErrorCode.COURSE_NOT_FIND);
		}

		Comment comment = BeanUtils.copy(commentReq, Comment.class);
		comment.setBusinessType(Comment.CommentType.LESSON.getCode());
		comment.setUserId(UserInfoContext.getUserContext().getUserId());
		comment.setThemeName(
				course.getName() + CommonConstant.LEFT_OBLIQUE_LINE + lesson.getName());

		Comment newComment = commentService.addNewComment(comment);
		CommentRes commentRes = BeanUtils.copy(newComment, CommentRes.class);
		return new BaseRsp<>(commentRes);
	}

	@ApiOperation("课时笔记新增")
	@PostMapping("/note")
	public BaseRsp<Note> saveNote(@RequestBody @Valid NoteAddReq noteReq) {

		CourseLesson lesson = courseLessonMapper.selectById(noteReq.getBusinessId());
		if (null == lesson) {

			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		String userId = UserInfoContext.getUserContext().getUserId();
		NoteAddContext addContext = BeanUtils.copy(noteReq, NoteAddContext.class);
		addContext.setUserId(userId);
		addContext.setBusinessType(Note.NoteResourceType.LESSON.getCode());

		Note noteAddContext = noteService.updateNote(addContext);
		return new BaseRsp<>(noteAddContext);
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
