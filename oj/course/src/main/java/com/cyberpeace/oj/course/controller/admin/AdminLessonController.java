package com.cyberpeace.oj.course.controller.admin;

import javax.validation.Valid;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.controller.req.*;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.ojbase.controller.req.ResourcePublicReq;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;

import java.util.List;

@Api(tags = "oj-后台课时相关api")
@RestController
@RequestMapping("/oj/admin/lesson")
@RequiresAuthentication
@Slf4j
public class AdminLessonController extends PublicController<CourseLessonService, CourseLesson> {

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private CourseService courseService;

	@ApiOperation("分页查询课时列表")
	@GetMapping("/list")
	public BaseRsp<ListRsp<AdminLessonListBo>> getLessonList(ListLessonReq lessonReq) {

		// 请求数据转换
		List<String> courseIds = courseService.selectIdsByCategory(lessonReq.getCategoryId(),
				AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), false));
		Page page = lessonReq.toPageParam();
		courseIds.remove(lessonReq.getExceptCourse());
		courseIds.add(lessonReq.getCourseId());
		LessonContext context = BeanUtils.copy(lessonReq, LessonContext.class);

		// 数据查询
		IPage<AdminLessonListBo> iPage = courseLessonService.pageByContext(page, context, false);

		ListRsp<AdminLessonListBo> rsp = PageUtil.getListResult(iPage);
		return new BaseRsp(rsp);
	}

	@ApiOperation("后台课时详情查询接口")
	@GetMapping("")
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
	public BaseRsp add(@RequestBody @Validated LessonAddReq lessonAddReq) {

		CourseLesson courseLesson = reqToEntity(lessonAddReq);
		CourseLesson lesson = courseLessonService.addLesson(courseLesson,
				lessonAddReq.getOjSceneConfig(), lessonAddReq.getPracticeList());

		return new BaseRsp(lesson);
	}

	@ApiOperation("课时信息修改")
	@PatchMapping("")
	public BaseRsp patch(@RequestBody @Valid LessonUpdateReq lessonUpdateReq) {

		CourseLesson courseLesson = reqToEntity(lessonUpdateReq);
		CourseLesson lesson = courseLessonService.updateLesson(courseLesson,
				lessonUpdateReq.getOjSceneConfig(), lessonUpdateReq.getPracticeList());
		return new BaseRsp(lesson);
	}

	@ApiOperation("课时信息删除")
	@DeleteMapping("/batch")
	public BaseRsp<Integer> delete(@RequestBody @Valid BatchDeleteReq batchDeleteReq) {
		int row = courseLessonMapper.deleteBatchIds(batchDeleteReq.getResourceIds());
		return new BaseRsp(row);
	}

	@ApiOperation("课时附件查询")
	@GetMapping("/attachment")
	public BaseRsp<List<AttachmentBo>> getAttachments(@ApiParam(name = "resource_id",
			value = "课时编号") @RequestParam("resource_id") String resourceId) {

		CourseLesson lesson = courseLessonMapper.selectById(resourceId);
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(lesson.getAttachmentIds(),
				false);
		return new BaseRsp<>(attachmentBos);
	}

	@ApiOperation("课后练习查询")
	@GetMapping("/practice")
	public BaseRsp<LessonPracticeBo> getPracticeList(@RequestParam("course_id") String courseId,
			@RequestParam("lesson_id") String lessonId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		LessonPracticeBo practiceByLesson = courseLessonService.practiceByLesson(courseId, lessonId,
				authContext);

		return new BaseRsp(practiceByLesson);
	}

	@ApiOperation("课时场景拓扑信息查询")
	@GetMapping("/scene_topo")
	public BaseRsp getTopoScene() {
		// TODO
		return null;
	}

	@ApiOperation("课时监控")
	public BaseRsp getLessonMonitor() {
		// TODO
		return null;
	}

	@ApiOperation("课时练习公开与隐藏")
	@PatchMapping("/practice/batch_public")
	public BaseRsp<Integer>
			publicPractice(@RequestBody @Valid ResourcePublicReq resourcePublicReq) {
		Integer row = courseLessonService.batchPublicPractice(resourcePublicReq.getResourceIds(),
				resourcePublicReq.getIsPublic());
		return new BaseRsp<>(row);
	}

	@ApiOperation("课时导入")
	@PostMapping("/import")
	public BaseRsp importLesson(@RequestBody ImportLessonReq importLessonReq) {
		Boolean result = courseLessonService.importLessons(importLessonReq.getCourseId(),
				importLessonReq.getLessonIds());
		return new BaseRsp(result);
	}

	/**
	 * 请求体转换为实体类
	 * @param lessonAddReq
	 * @return
	 */
	private CourseLesson reqToEntity(LessonAddReq lessonAddReq) {

		// 知识点转换
		CourseLesson courseLesson = BeanUtils.copy(lessonAddReq, CourseLesson.class);
		courseLesson.setKnowledge(JsonUtil.arrayToString(lessonAddReq.getKnowledge()));
		courseLesson.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		return courseLesson;
	}
}
