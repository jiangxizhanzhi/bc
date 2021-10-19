package com.cyberpeace.oj.course.controller.web;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.req.CommentReq;
import com.cyberpeace.oj.ojbase.controller.req.NoteReq;
import com.cyberpeace.oj.ojbase.controller.res.CommentRes;
import com.cyberpeace.oj.ojbase.dao.entity.Comment;
import com.cyberpeace.oj.ojbase.service.CommentService;
import com.cyberpeace.oj.ojbase.service.NoteService;
import com.cyberpeace.oj.ojbase.service.context.NoteAddContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.controller.req.CollectionReq;
import com.cyberpeace.oj.course.controller.req.CourseListReq;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "oj-前台课程相关api")
@RestController
@RequestMapping("/oj/course")
@RequiresAuthentication
public class WebCourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseCategoryService courseDirectionService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private NoteService noteService;

	@ApiOperation("课程收藏")
	@PostMapping("/collect")
	public BaseRsp<String> courseCollection(@RequestBody CollectionReq req) {

		String userId = UserInfoContext.getUserContext().getUserId();

		String courseId = courseService.collectCourse(userId, req.getResource(),
				req.getIsCollection());
		return new BaseRsp<>(courseId);
	}

	@ApiOperation("前台课程列表查询")
	@GetMapping("/list")
	public BaseRsp<List<CourseInfoBo>> getWebCourse(
			@ApiParam(name = "listReq", value = "查询条件") @Validated CourseListReq listReq) {
		// 数据组装
		CourseContext context = BeanUtils.copy(listReq, CourseContext.class);
		context.setUserId(UserInfoContext.getUserContext().getUserId());
		context.isWeb();

		if (StringUtils.isNotBlank(listReq.getDirection())) {
			List<String> childrenIds = courseDirectionService
					.getAllChildrenIds(listReq.getDirection());
			context.setDirections(childrenIds);
		}

		Page page = listReq.toPageParam();
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), true);

		// 数据查询
		ListRsp<CourseInfoBo> boListRsp = courseService.selectPage(page, context, authContext);
		return new BaseRsp(boListRsp);
	}

	@GetMapping()
	@ApiOperation("单条查询")
	public BaseRsp<CourseInfoBo> getCourse(@ApiParam(name = "resource_id", value = "查询id",
			required = true) @RequestParam("resource_id") @NotBlank String resourceId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		CourseInfoBo course = courseService.selectById(resourceId, userId);
		BaseRsp rsp = new BaseRsp(course);
		return rsp;
	}

	@PostMapping("/comment")
	@ApiOperation("留言新增或者修改")
	public BaseRsp<CommentRes> getCourseComment(@RequestBody @Valid CommentReq commentReq) {

		Course course = courseMapper.selectById(commentReq.getResource());
		if (null == course) {
			throw new BusinessException(OjBusinessErrorCode.COURSE_NOT_FOUND);
		}

		Comment comment = BeanUtils.copy(commentReq, Comment.class);
		comment.setUser(UserInfoContext.getUserContext().getUserId());
		comment.setCommentType(Comment.COURSE);

		Comment newComment = commentService.addNewComment(comment);
		CommentRes commentRes = BeanUtils.copy(newComment, CommentRes.class);
		return new BaseRsp<>(commentRes);
	}

	@PostMapping("/note")
	@ApiOperation("笔记新增或者修改")
	public BaseRsp<NoteAddContext> saveNote(@ApiParam @Valid @RequestBody NoteReq noteReq) {

		// 资源校验
		Course course = courseMapper.selectById(noteReq.getResource());
		if (null == course) {
			throw new ResourceNotFoundException("未找到对应的课程资源！");
		}

		String userId = UserInfoContext.getUserContext().getUserId();
		NoteAddContext addContext = BeanUtils.copy(noteReq, NoteAddContext.class);
		addContext.setUser(userId);

		NoteAddContext note = noteService.updateOrSave(addContext);
		return new BaseRsp(note);
	}
}
