package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.BatchDeleteReq;
import com.cyberpeace.oj.course.controller.req.ListLessonReq;
import com.cyberpeace.oj.course.controller.res.AdminLessonDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonRes;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import com.cyberpeace.oj.ojbase.controller.AuthAndPublicController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "oj-课时后台api")
@RestController
@RequestMapping("/oj/admin/lesson")
@RequiresAuthentication
public class AdminLessonController extends AuthAndPublicController<CourseLessonService, CourseLesson> {

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@ApiOperation("查询课程下课时列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<AdminLessonRes>> getLessonList(ListLessonReq lessonReq) {

		// 请求数据转换
		Page page = lessonReq.toPageParam();
		LessonContext context = BeanUtils.copy(lessonReq, LessonContext.class);

		// 数据查询
		IPage<CourseLesson> iPage = courseLessonMapper.pageByContext(page, context, false);

		// 数据转换
		iPage.convert(lesson -> {
			AdminLessonRes adminLessonRes = BeanUtils.copy(lesson, AdminLessonRes.class);
			UserInfo userInfo = remoteUserService.getById(lesson.getCreateUserId());
			adminLessonRes.setCreateUserName(userInfo.getUserName());
			return adminLessonRes;
		});
		ListRsp<AdminLessonRes> rsp = PageUtil.getListResult(iPage);
		return new BaseRsp(rsp);
	}

	@ApiOperation("后台课时详情查询接口")
	@GetMapping("/lesson")
	@ApiImplicitParams({ @ApiImplicitParam(name = "lesson_id", value = "课时编号"),
			@ApiImplicitParam(name = "course_id", value = "课程编号") })
	public BaseRsp<AdminLessonBo> get(@RequestParam(value = "course_id") String courseId,
			@RequestParam(value = "lesson_id") String lessonId) {

		AdminLessonBo lessonDetail = courseLessonService.selectByCourseAndLesson(courseId,
				lessonId);
		return new BaseRsp(lessonDetail);
	}

	@ApiOperation("课时信息新增")
	@PostMapping("")
	public BaseRsp add() {

		return null;
	}

	@ApiOperation("课时信息修改")
	@PatchMapping("")
	public BaseRsp patch() {

		return null;
	}

	@ApiOperation("课时信息删除")
	@DeleteMapping("/batch")
	public BaseRsp<Integer> delete(@Valid BatchDeleteReq batchDeleteReq) {
		int row = courseLessonMapper.deleteBatchIds(batchDeleteReq.getResourceIds());
		return new BaseRsp(row);
	}
}
