package com.cyberpeace.oj.course.controller.web;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import com.cyberpeace.oj.ojbase.constant.CommentType;
import com.cyberpeace.oj.ojbase.constant.NoteResourceType;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteAddReq;
import com.cyberpeace.oj.ojbase.controller.res.CommentRes;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.dao.entity.Note;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "oj-前台课时相关api")
@RestController
@RequiresAuthentication
@RequestMapping("/oj/lesson")
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
			throw new BusinessException(OjBusinessErrorCode.LESSON_NOT_FIND);
		}

		Course course = courseMapper.selectById(lesson.getCourseId());
		if (null == course) {
			throw new BusinessException(OjBusinessErrorCode.COURSE_NOT_FIND);
		}

		Comment comment = BeanUtils.copy(commentReq, Comment.class);
		comment.setBusinessType(CommentType.LESSON.getCode());
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
			throw new ResourceNotFoundException("未检索到对应课时信息！");
		}

		String userId = UserInfoContext.getUserContext().getUserId();
		NoteAddContext addContext = BeanUtils.copy(noteReq, NoteAddContext.class);
		addContext.setUserId(userId);
		addContext.setBusinessType(NoteResourceType.LESSON.getCode());

		Note noteAddContext = noteService.updateNote(addContext);
		return new BaseRsp<>(noteAddContext);
	}

}
