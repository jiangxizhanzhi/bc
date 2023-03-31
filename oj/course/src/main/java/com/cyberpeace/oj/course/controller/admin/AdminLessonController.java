package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import javax.validation.Valid;

import com.cyberpeace.common.annoations.PermissionOperation;
import com.cyberpeace.common.constant.*;
import com.cyberpeace.oj.course.dao.entity.Course;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.AttachmentBo;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.oj.course.controller.req.*;
import com.cyberpeace.oj.course.controller.res.SimpleDataRes;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.LessonSceneService;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonPracticeBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.ojbase.controller.req.ResourcePublicReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneNodeReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneOperationReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneStatusReq;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;

import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;

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

	@Autowired
	private LessonSceneService lessonSceneService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private UtilService utilService;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("分页查询课时列表")
	@GetMapping("/list")
	@PermissionOperation("lesson list")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<ListRsp<AdminLessonListBo>> getLessonList(ListLessonReq lessonReq) {

		// 分类课程筛选 权限数据过滤
		List<String> courseIds = courseService.selectIdsByCategory(lessonReq.getCategoryId(),
				AuthContextUtils.constructAuth(UserInfoContext.getUserContext(), false));
		if (CollectionUtils.isEmpty(courseIds)) {
			return new BaseRsp<>(PageUtil.getNullResult());
		}

		// 课程数据过滤
		if (StringUtils.isNotBlank(lessonReq.getCourseId())) {
			if (!courseIds.contains(lessonReq.getCourseId())) {
				return new BaseRsp<>(PageUtil.getNullResult());
			}
			courseIds.removeAll(courseIds);
			courseIds.add(lessonReq.getCourseId());
		}

		// 排除课程过滤
		if (StringUtils.isNotBlank(lessonReq.getExceptCourse())) {
			courseIds.remove(lessonReq.getExceptCourse());
		}

		Page page = lessonReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		LessonContext context = BeanUtils.copy(lessonReq, LessonContext.class);
		if (CollectionUtils.isEmpty(courseIds)) {
			return new BaseRsp<>(PageUtil.getNullResult());
		}
		context.setCourseIds(courseIds);

		// 数据查询
		IPage<AdminLessonListBo> iPage = courseLessonService.pageByContext(page, context, false);

		return new BaseRsp(PageUtil.getListResult(iPage));
	}

	@ApiOperation("后台课时详情查询接口")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lesson_id", value = "课时编号", dataTypeClass = String.class),
			@ApiImplicitParam(name = "course_id", value = "课程编号", dataTypeClass = String.class) })
	public BaseRsp<AdminLessonBo> get(@RequestParam(value = "course_id") String courseId,
			@RequestParam(value = "lesson_id") String lessonId) {

		AdminLessonBo lessonDetail = courseLessonService.selectByCourseAndLesson(courseId,
				lessonId);
		return new BaseRsp(lessonDetail);
	}

	@ApiOperation("课时信息新增")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<CourseLesson> add(@Validated(Create.class) LessonAddReq lessonAddReq) {
		Course course = courseMapper.selectById(lessonAddReq.getCourseId());
		// 试用账号校验
		AuthContextUtils.validateBuiltin(AuthContextUtils.checkBuiltin(course.getCreateUserId(), UserInfoContext.getUserContext().getUserId()));

		// 数据转换
		CourseLesson courseLesson = addReqToEntity(lessonAddReq);
		JSONObject sceneConfig = JsonUtil.toJsonObject(lessonAddReq.getOjSceneConfig(),
				JSONObject.class);
		List<PaperPracticeContext> practiceContextList = JsonUtil
				.toJsonObjectArray(lessonAddReq.getPracticeList(), PaperPracticeContext.class);

		// 数据新增
		CourseLesson lesson = courseLessonService.addLesson(courseLesson, sceneConfig,
				practiceContextList);

		String courseName = courseMapper.selectById(lesson.getCourseId()).getName();
		String operationTarget = courseName + "-" + lesson.getName();
		String content = LogContent.COURSE_FORMAT.format(courseName, lesson.getName());
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.COURSE,
				OperationType.CREATE, operationTarget, content);

		return new BaseRsp(lesson);
	}

	@ApiOperation("课时信息修改")
	@PatchMapping("")
	@PermissionOperation("lesson edit")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<CourseLesson> patch(@Validated(Update.class) LessonUpdateReq lessonUpdateReq) {
		Course course = courseMapper.selectById(lessonUpdateReq.getCourseId());
		// 试用账号校验
		AuthContextUtils.validateBuiltin(AuthContextUtils.checkBuiltin(course.getCreateUserId(), UserInfoContext.getUserContext().getUserId()));

		// 修改数据转换 进行视频和讲义地址是否修改判断
		CourseLesson courseLesson = updateReqToEntity(lessonUpdateReq);

		// 练习题数据空字符串则不修改 将paper_id设置进课时数据体中
		if (StringUtils.isBlank(lessonUpdateReq.getPracticeList())) {
			CourseLesson lesson = courseLessonMapper.selectById(lessonUpdateReq.getResourceId());
			courseLesson.setPaperId(lesson.getPaperId());
		}

		JSONObject topoConfig = JsonUtil.toJsonObject(lessonUpdateReq.getOjSceneConfig(),
				JSONObject.class);
		List<PaperPracticeContext> contextList = JsonUtil
				.toJsonObjectArray(lessonUpdateReq.getPracticeList(), PaperPracticeContext.class);

		// 执行数据更新
		CourseLesson lesson = courseLessonService.updateLesson(courseLesson, topoConfig,
				contextList);

		String courseName = courseMapper.selectById(lesson.getCourseId()).getName();
		String operationTarget = courseName + "-" + lesson.getName();
		String content = LogContent.COURSE_FORMAT.format(courseName, lesson.getName());
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.COURSE,
				OperationType.UPDATE, operationTarget, content);

		return new BaseRsp(lesson);

	}

	@ApiOperation("课时信息删除")
	@DeleteMapping("/batch")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> delete(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		String lessonId = batchDeleteReq.getResourceIds().get(CommonConstant.ZERO);
		CourseLesson lesson = courseLessonMapper.selectById(lessonId);

		Course course = courseMapper.selectById(lesson.getCourseId());
		// 试用账号校验
		AuthContextUtils.validateBuiltin(AuthContextUtils.checkBuiltin(course.getCreateUserId(), UserInfoContext.getUserContext().getUserId()));

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);

		String courseName = courseMapper.selectById(lesson.getCourseId()).getName();

		List<CourseLesson> courseLessonList = courseLessonService.listByIds(batchDeleteReq.getResourceIds());

		Integer result = courseLessonService.batchDelete(lesson.getCourseId(),
				batchDeleteReq.getResourceIds(), authContext);

		courseLessonList.forEach(courseLesson -> {
			if (courseLesson != null) {
				String operationTarget = courseName + "-" + courseLesson.getName();
				String content = LogContent.COURSE_FORMAT.format(courseName, courseLesson.getName());
				remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.COURSE,
						OperationType.DELETE, operationTarget, content);
			}
		});


		return new BaseRsp(result);
	}

	@ApiOperation("课时附件查询")
	@GetMapping("/attachment")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<List<AttachmentBo>> getAttachments(@ApiParam(name = "resource_id",
			value = "课时编号") @RequestParam("resource_id") String resourceId) {

		CourseLesson lesson = courseLessonMapper.selectById(resourceId);
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(lesson.getAttachmentIds(),
				false);
		return new BaseRsp<>(attachmentBos);
	}

	@ApiOperation("课后练习查询")
	@GetMapping("/practice")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<AdminLessonPracticeBo> getPracticeList(
			@RequestParam("course_id") String courseId,
			@RequestParam("lesson_id") String lessonId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		List<AdminLessonPracticeBo> practiceByLesson = courseLessonService
				.adminLessonPractice(courseId, lessonId, authContext);

		return new BaseRsp(practiceByLesson);
	}

	@ApiOperation("课后练习修改")
	@PatchMapping("/practice")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean>
			updateLessonPractice(@RequestBody @Validated AddLessonPracticeReq practiceReq) {

		Boolean result = courseLessonService.addLessonPractice(practiceReq.getResourceId(),
				practiceReq.getPracticeList());
		return new BaseRsp(result);
	}

	@ApiOperation("课时场景拓扑信息查询")
	@GetMapping("/scene_topo")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<TopoSceneDataBo> getTopoScene(@ApiParam(name = "resource_id",
			value = "资源数据编号") @RequestParam("resource_id") String lessonId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		TopoSceneDataBo topoSceneData = courseLessonService.getTopoScene(lessonId, null, authContext);

		return new BaseRsp(topoSceneData);
	}

	@ApiOperation("课时练习公开与隐藏")
	@PatchMapping("/practice/batch_public")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean>
			publicPractice(@RequestBody @Validated ResourcePublicReq resourcePublicReq) {
		Boolean result = courseLessonService.batchPublicPractice(resourcePublicReq.getResourceIds(),
				resourcePublicReq.getIsPublic());
		return new BaseRsp<>(result);
	}

	@ApiOperation("课时导入")
	@PostMapping("/import")
	@PermissionOperation(TrialButtonConstant.CMS_LESSON_IMPORT)
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> importLesson(@RequestBody @Valid ImportLessonReq importLessonReq) {
		Boolean result = courseLessonService.importLessons(importLessonReq.getCourseId(),
				importLessonReq.getLessonIds());
		return new BaseRsp(result);
	}

	@ApiOperation("课时新增附件上传")
	@PostMapping("/upload_attachment")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<String>
			uploadAttachment(@RequestParam("attachment") MultipartFile multipartFile) {

		AttachmentBo attachmentBo = attachmentService
				.uploadDest(AttachmentPath.ATTACHMENT.getDesc(), true, multipartFile);
		return new BaseRsp<>(attachmentBo.getResourceId());
	}

	@ApiOperation("后台课时场景启动")
	@PostMapping("/start_scene")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<JSONObject>
			startScene(@RequestBody @Validated SceneOperationReq lessonSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		JSONObject scene = lessonSceneService.startScene(lessonSceneReq.getResourceId(),
				authContext);
		return new BaseRsp<>(scene);
	}

	@ApiOperation("后台课时场景暂停与恢复")
	@PatchMapping("/update_status")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean>
			updateSceneStatus(@RequestBody @Validated SceneStatusReq sceneStatusReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = lessonSceneService.updateSceneStatus(sceneStatusReq.getRoomId(),
				sceneStatusReq.getResourceId(), sceneStatusReq.getStatus(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("后台课时场景删除")
	@DeleteMapping("/delete_scene")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> deleteScene(@RequestBody @Validated SceneOperationReq lessonSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = lessonSceneService.deleteScene(lessonSceneReq.getResourceId(),
				authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("后台课时节点重启")
	@PostMapping("/node_restart")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> restartNode(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = lessonSceneService.restartNode(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), sceneNodeReq.getRoomId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("后台课时节点重建")
	@PostMapping("/node_recreate")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> recreateNode(@RequestBody @Validated SceneNodeReq sceneNodeReq) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = lessonSceneService.recreateNode(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), sceneNodeReq.getRoomId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("后台课时场景监控")
	@GetMapping("/lesson_monitor")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<List<SceneMonitorBo>> lessonMonitor(@ApiParam(name = "resource_id",
			value = "课时编号") @RequestParam("resource_id") String lessonId) {

		List<SceneMonitorBo> monitorBos = lessonSceneService.lessonMonitor(lessonId);
		return new BaseRsp<>(monitorBos);

	}

	@ApiOperation("场景监控信息刷新时间")
	@GetMapping("/monitor_refresh_interval")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Integer> getMonitorRefreshInterval() {

		String refreshInterval = ConfigUtil.getContextProperty("oj.monitor.refresh.interval");
		return new BaseRsp<>(Integer.parseInt(refreshInterval));

	}

	@ApiOperation("查询课程下课时简易数据")
	@GetMapping("/simple/list")
	@RequiresPermissions(MenuConstant.CMS_CLASS_ROOM)
	public BaseRsp<List<SimpleDataRes>>
			selectAllLesson(@RequestParam("course_id") String courseId) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		List<String> enableCourseIds = courseMapper.selectByAuth(authContext);
		if (CollectionUtils.isEmpty(enableCourseIds) || !enableCourseIds.contains(courseId)) {
			log.error("Course not permission! course_id=[{}]", courseId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		// 仅查询公开课
		List<CourseLesson> lessons = courseLessonMapper.selectByCourse(courseId, true);
		List<SimpleDataRes> simpleDataRes = StreamApiUtils.map(lessons,
				courseLesson -> BeanUtils.copy(courseLesson, SimpleDataRes.class));
		return new BaseRsp<>(simpleDataRes);
	}

	/**
	 * 更新请求转换为实体类
	 * @param lessonUpdateReq
	 * @return
	 */
	private CourseLesson updateReqToEntity(LessonUpdateReq lessonUpdateReq) {
		CourseLesson courseLesson = BeanUtils.copy(lessonUpdateReq, CourseLesson.class);

		// 有文件路径 并且文件不为空 则修改文件
		if (StringUtils.isBlank(lessonUpdateReq.getVideoUrl())
				&& null != lessonUpdateReq.getVideo()) {
			courseLessonService.uploadVideo(courseLesson, lessonUpdateReq.getVideo());

		} else if (null == lessonUpdateReq.getVideo()
				&& StringUtils.isBlank(lessonUpdateReq.getVideoUrl())) {
			// 文件为空 url为空则置空
			courseLesson.setVideoUrl("");
		} else {
			courseLesson.setVideoUrl(utilService.replaceFileUrl(lessonUpdateReq.getVideoUrl()));
		}

		// 有讲义地址并且讲义不为空 则修改文件
		if (StringUtils.isBlank(lessonUpdateReq.getHandoutUrl())
				&& null != lessonUpdateReq.getHandout()) {
			courseLessonService.uploadHandout(courseLesson, lessonUpdateReq.getHandout());

			// 讲义为空 并且讲义地址为空 则置空
		} else if (StringUtils.isBlank(lessonUpdateReq.getHandoutUrl())
				&& null == lessonUpdateReq.getHandout()) {
			courseLesson.setHandoutUrl("");
		} else {
			courseLesson.setHandoutUrl(utilService.replaceFileUrl(lessonUpdateReq.getHandoutUrl()));
		}

		return courseLesson;
	}

	/**
	 * 请求体转换为实体类
	 * @param lessonAddReq
	 * @return
	 */
	private CourseLesson addReqToEntity(LessonAddReq lessonAddReq) {

		// 知识点转换
		CourseLesson courseLesson = BeanUtils.copy(lessonAddReq, CourseLesson.class);
		courseLesson.setKnowledge(lessonAddReq.getKnowledge());
		courseLesson.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		courseLessonService.uploadVideo(courseLesson, lessonAddReq.getVideo());
		courseLessonService.uploadHandout(courseLesson, lessonAddReq.getHandout());

		return courseLesson;
	}

}
