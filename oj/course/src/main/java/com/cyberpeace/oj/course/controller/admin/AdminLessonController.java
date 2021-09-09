package com.cyberpeace.oj.course.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.ListLessonReq;
import com.cyberpeace.oj.course.controller.res.AdminLessonDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonRes;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.mapper.LessonMapper;
import com.cyberpeace.oj.course.dao.oj_entity.Lesson;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "oj-课时后台api")
@RestController
@RequestMapping("/admin/lesson")
@RequiresAuthentication
public class AdminLessonController {

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private LessonMapper lessonMapper;

	@ApiOperation("查询课程下课时列表")
	@GetMapping("/admin/list")
	public BaseRsp<ListRsp<AdminLessonRes>> getLessonList(ListLessonReq lessonReq) {

		// 请求数据转换
		Page page = lessonReq.toPageParam();
		LessonContext context = BeanUtils.copy(lessonReq, LessonContext.class);

		// 数据查询
		IPage<Lesson> iPage = lessonMapper.pageByContext(page, context);

		// 数据转换
		iPage.convert(lesson -> {
			AdminLessonRes adminLessonRes = BeanUtils.copy(lesson, AdminLessonRes.class);
			UserInfo userInfo = remoteUserService.getById(lesson.getCreateUser());
			adminLessonRes.setCreateUserName(userInfo.getUserName());
			return adminLessonRes;
		});
		ListRsp<AdminLessonRes> rsp = PageUtil.getListResult(iPage);
		return new BaseRsp(rsp);
	}

	@ApiOperation("后台课时详情查询接口")
	@GetMapping("/admin/lesson")
	@ApiImplicitParams({ @ApiImplicitParam(name = "lesson_id", value = "课时编号"),
			@ApiImplicitParam(name = "course_id", value = "课程编号") })
	public BaseRsp getAdminLessonDetail(@RequestParam("course_id") String courseId,
			@RequestParam("lesson_id") String lessonId) {

		LessonInfoBo lessonDetail = courseLessonService.getAdminLessonDetail(courseId, lessonId);
		AdminLessonDetailRes detailRes = BeanUtils.copy(lessonDetail, AdminLessonDetailRes.class);
		return new BaseRsp(detailRes);
	}

	@ApiOperation("课时删除")
	@DeleteMapping("")
	public BaseRsp<String> deleteLesson(@RequestParam("resource_id") String lessonId) {
		lessonMapper.deleteById(lessonId);
		return new BaseRsp(lessonId);
	}
}
