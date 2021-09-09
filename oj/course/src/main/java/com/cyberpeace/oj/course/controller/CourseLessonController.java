package com.cyberpeace.oj.course.controller;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.controller.req.CollectionReq;
import com.cyberpeace.oj.course.controller.req.ListLessonReq;
import com.cyberpeace.oj.course.controller.res.AdminLessonRes;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;

import io.swagger.annotations.*;

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

		String userId = UserInfoContext.getUserContext().getUserId();
		LessonInfoBo infoBo = courseLessonService.getLessonDetail(userId, courseId, lessonId);
		return new BaseRsp<>(infoBo);
	}

	@ApiOperation("课时收藏")
	@PostMapping("/collection")
	public BaseRsp<String> collectionLesson(@ApiParam(name = "req", value = "课时收藏信息") @RequestBody CollectionReq req) {

		String userId = UserInfoContext.getUserContext().getUserId();
		String lesson = courseLessonService.collectionLesson(userId, req.getResource(),
				req.getIsCollection());
		return new BaseRsp<>(lesson);
	}

	@ApiOperation("查询课程下课时列表")
	@GetMapping("/admin/list")
	public BaseRsp<ListRsp<AdminLessonRes>> getLessonList(ListLessonReq lessonReq) {

		// 数据转换
		Page page = lessonReq.toPageParam();
		LessonContext context = BeanUtils.copy(lessonReq, LessonContext.class);
		IPage<LessonInfoBo> iPage = courseLessonService.pageByContext(page, context);
		iPage.convert(lessonInfoBo -> BeanUtils.copy(lessonInfoBo, AdminLessonRes.class));
		ListRsp<AdminLessonRes> rsp = PageUtil.getListResult(iPage);
		return new BaseRsp(rsp);
	}

	@ApiOperation("后台课时详情查询接口")
	@GetMapping("/admin/lesson")
	public BaseRsp getAdminLessonDetail() {

		return null;
	}
}
