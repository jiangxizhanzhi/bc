package com.cyberpeace.oj.course.controller.admin;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.annoations.CallCount;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.*;
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
import com.cyberpeace.oj.course.dao.entity.Course;
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
import com.google.common.collect.Lists;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 管理课Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PublicController
 * @since 1.0.0
 */
/**
 * 这段代码是一个用于管理课程和课时的Spring Boot控制器类 `AdminLessonController`。它主要负责处理与课程和课时相关的各种操作，包括查询、添加、更新、删除等。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.admin` 
 * - **继承关系**：`AdminLessonController` 继承自 `PublicController<CourseLessonService, CourseLesson>` 
 * - **注解**： 
 * - `@RestController`：表明这是一个RESTful风格的控制器。 
 * - `@RequestMapping("/oj/admin/lesson")`：定义了控制器的基本请求路径。 
 * - `@Slf4j`：使用Lombok注解自动生成日志对象。 
 * ### 成员变量 
 * - 注入了多个服务（如 `CourseLessonService`, `CourseService`, `LessonSceneService` 等）和工具类（如 `UtilService`, `RemoteLogService` 等），以支持具体的业务逻辑。 
 * ### 方法 
 * 1. **获取课时列表** 
 * - `@GetMapping("/list")`：处理GET请求，返回分页查询的课时列表。 
 * - 使用 `Page` 对象进行分页，并根据条件查询课时信息。 
 * 2. **获取课时详情** 
 * - `@GetMapping("")`：处理GET请求，通过课程ID和课时ID查询具体的课时详情。 
 * - 返回 `AdminLessonBo` 对象。 
 * 3. **新增课时** 
 * - `@PostMapping("")`：处理POST请求，接收 `LessonAddReq` 对象并将其转换为 `CourseLesson` 对象后保存。 
 * - 同时记录日志信息。 
 * 4. **更新课时** 
 * - `@PatchMapping("")`：处理PATCH请求，接收 `LessonUpdateReq` 对象并更新对应的课时信息。 
 * - 同样会记录日志信息。 
 * 5. **删除课时** 
 * - `@DeleteMapping("/batch")`：处理DELETE请求，批量删除课时。 
 * - 返回删除结果。 
 * 6. **获取课时附件** 
 * - `@GetMapping("/attachment")`：处理GET请求，查询指定课时的附件信息。 
 * - 返回 `AttachmentBo` 对象列表。 
 * 7. **其他功能** 
 * - 包括获取课后练习、更新练习、导入课程、上传附件、启动场景、更新场景状态、删除场景、重启节点、重建节点、获取场景监控信息等。 
 * ### 总结 
 * 这个控制器类主要实现了对课程和课时的CRUD操作，同时包含了日志记录和一些辅助功能。它依赖于多个服务和工具类来完成具体的功能实现。整体结构清晰，职责明确，符合RESTful设计原则。 
**/
@Api(tags = "oj-后台课时相关api")
@RestController
@RequestMapping("/oj/admin/lesson")
@RequiresAuthentication
@Slf4j
public class AdminLessonController extends PublicController<CourseLessonService, CourseLesson> {

	/**
	 * 课程课Service
	 *
	 * @see CourseLessonService
	 */
	@Autowired
	private CourseLessonService courseLessonService;

	/**
	 * 课程课Mapper
	 *
	 * @see CourseLessonMapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 附件Service
	 *
	 * @see AttachmentService
	 */
	@Autowired
	private AttachmentService attachmentService;

	/**
	 * 课程Service
	 *
	 * @see CourseService
	 */
	@Autowired
	private CourseService courseService;

	/**
	 * 课场景Service
	 *
	 * @see LessonSceneService
	 */
	@Autowired
	private LessonSceneService lessonSceneService;

	/**
	 * 课程Mapper
	 *
	 * @see CourseMapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * utilService
	 *
	 * @see UtilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 远程日志Service
	 *
	 * @see RemoteLogService
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 获取课列表
	 *
	 * @param lessonReq 课请求体
	 * @return {@code BaseRsp<ListRsp<AdminLessonListBo>> }
	 * @throws
	 * @see ListLessonReq
	 * @see BaseRsp<ListRsp<AdminLessonListBo>>
	 */
	@ApiOperation("分页查询课时列表")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<ListRsp<AdminLessonListBo>> getLessonList(ListLessonReq lessonReq) {

		Page page = lessonReq.toPageParam();
		if (CollectionUtils.isEmpty(page.getOrders())) {
			PageUtil.setPageOrder(page, OrderNameConstant.UPDATE_TIME, false);
		}

		LessonContext context = BeanUtils.copy(lessonReq, LessonContext.class);
		context.setCourseIds(Lists.newArrayList(lessonReq.getCourseId()));

		// 导入课程小节
		if (StringUtils.isNotEmpty(lessonReq.getExceptCourse())) {

			if (StringUtils.isNotEmpty(lessonReq.getCourseId())) {
				if (lessonReq.getCourseId().equals(lessonReq.getExceptCourse())) {
					throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "导入课程小节时，请勿选择当前课程");
				}
			} else {
				LambdaQueryWrapper<Course> courseQuery = new LambdaQueryWrapper<Course>()
						.select(Course::getResourceId);
				if (StringUtils.isNotEmpty(lessonReq.getCategoryId())) {
					courseQuery.eq(Course::getCategoryId, lessonReq.getCategoryId());
				}
				List<Course> courses = courseMapper.selectList(courseQuery);
				if (CollectionUtils.isEmpty(courses)) {
					return new BaseRsp<>(new ListRsp<>(Collections.emptyList(), 0L, 0L, 0L));
				} else {
					Set<String> courseIds = courses.stream().map(Course::getResourceId).collect(Collectors.toSet());
					courseIds.remove(lessonReq.getExceptCourse());
					context.setCourseIds(new ArrayList<>(courseIds));
				}
			}

		}

		// 数据查询
		IPage<AdminLessonListBo> iPage = courseLessonService.pageByContext(page, context, false);

		return new BaseRsp(PageUtil.getListResult(iPage));
	}

	/**
	 * 获取
	 *
	 * @param courseId 课程id
	 * @param lessonId 课id
	 * @return {@code BaseRsp<AdminLessonBo> }
	 * @throws
	 * @see String
	 * @see String
	 * @see BaseRsp<AdminLessonBo>
	 */
	@ApiOperation("后台课时详情查询接口")
	@GetMapping("")
	@CallCount(CallType.THEORY)
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

	/**
	 * 新增
	 *
	 * @param lessonAddReq 课新增请求体
	 * @return {@code BaseRsp<CourseLesson> }
	 * @throws
	 * @see LessonAddReq
	 * @see BaseRsp<CourseLesson>
	 */
	@ApiOperation("课时信息新增")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<CourseLesson> add(@Validated(Create.class) LessonAddReq lessonAddReq) {

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

	/**
	 * 补丁
	 *
	 * @param lessonUpdateReq 课更新请求体
	 * @return {@code BaseRsp<CourseLesson> }
	 * @throws
	 * @see LessonUpdateReq
	 * @see BaseRsp<CourseLesson>
	 */
	@ApiOperation("课时信息修改")
	@PatchMapping("")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<CourseLesson> patch(@Validated(Update.class) LessonUpdateReq lessonUpdateReq) {
		CourseLesson old = courseLessonMapper.selectById(lessonUpdateReq.getResourceId());
		if(Boolean.TRUE.equals(old.getBuiltin())){
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
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

	/**
	 * 删除
	 *
	 * @param batchDeleteReq 批量删除请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see BatchDeleteReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("课时信息删除")
	@DeleteMapping("/batch")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> delete(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		String lessonId = batchDeleteReq.getResourceIds().get(CommonConstant.ZERO);
		CourseLesson lesson = courseLessonMapper.selectById(lessonId);

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

	/**
	 * 获取附件
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<List<AttachmentBo>> }
	 * @throws
	 * @see String
	 * @see BaseRsp<List<AttachmentBo>>
	 */
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

	/**
	 * 获取实践列表
	 *
	 * @param courseId 课程id
	 * @param lessonId 课id
	 * @return {@code BaseRsp<AdminLessonPracticeBo> }
	 * @throws
	 * @see String
	 * @see String
	 * @see BaseRsp<AdminLessonPracticeBo>
	 */
	@ApiOperation("课后练习查询")
	@GetMapping("/practice")
	public BaseRsp<AdminLessonPracticeBo> getPracticeList(
			@RequestParam("course_id") String courseId,
			@RequestParam("lesson_id") String lessonId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				CommonConstant.FALSE);

		List<AdminLessonPracticeBo> practiceByLesson = courseLessonService
				.adminLessonPractice(courseId, lessonId, authContext);

		return new BaseRsp(practiceByLesson);
	}

	/**
	 * 更新课程练习
	 *
	 * @param practiceReq 实践请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see AddLessonPracticeReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("课后练习修改")
	@PatchMapping("/practice")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean>
			updateLessonPractice(@RequestBody @Validated AddLessonPracticeReq practiceReq) {

		Boolean result = courseLessonService.addLessonPractice(practiceReq.getResourceId(),
				practiceReq.getPracticeList());
		return new BaseRsp(result);
	}

	/**
	 * 公共执业
	 *
	 * @param resourcePublicReq 资源公共请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see ResourcePublicReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("课时练习公开与隐藏")
	@PatchMapping("/practice/batch_public")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean>
			publicPractice(@RequestBody @Validated ResourcePublicReq resourcePublicReq) {
		Boolean result = courseLessonService.batchPublicPractice(resourcePublicReq.getResourceIds(),
				resourcePublicReq.getIsPublic());
		return new BaseRsp<>(result);
	}

	/**
	 * 导入课程
	 *
	 * @param importLessonReq 导入课请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see ImportLessonReq
	 * @see BaseRsp<Boolean>
	 */
	@ApiOperation("课时导入")
	@PostMapping("/import")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Boolean> importLesson(@RequestBody @Valid ImportLessonReq importLessonReq) {
		Boolean result = courseLessonService.importLessons(importLessonReq.getCourseId(),
				importLessonReq.getLessonIds());
		return new BaseRsp(result);
	}

	/**
	 * 上传附件
	 *
	 * @param multipartFile Multipart 文件
	 * @return {@code BaseRsp<String> }
	 * @throws
	 * @see MultipartFile
	 * @see BaseRsp<String>
	 */
	@ApiOperation("课时新增附件上传")
	@PostMapping("/upload_attachment")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<String>
			uploadAttachment(@RequestParam("attachment") MultipartFile multipartFile) {

		AttachmentBo attachmentBo = attachmentService
				.uploadDest(AttachmentPath.ATTACHMENT.getDesc(), true, multipartFile);
		return new BaseRsp<>(attachmentBo.getResourceId());
	}

	/**
	 * 开始场景
	 *
	 * @param lessonSceneReq 课场景请求体
	 * @return {@code BaseRsp<JSONObject> }
	 * @throws
	 * @see SceneOperationReq
	 * @see BaseRsp<JSONObject>
	 */
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

	/**
	 * 更新场景状态
	 *
	 * @param sceneStatusReq 场景状态请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SceneStatusReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 删除场景
	 *
	 * @param lessonSceneReq 课场景请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SceneOperationReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 重启节点
	 *
	 * @param sceneNodeReq 场景节点请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SceneNodeReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 重新创建节点
	 *
	 * @param sceneNodeReq 场景节点请求体
	 * @return {@code BaseRsp<Boolean> }
	 * @throws
	 * @see SceneNodeReq
	 * @see BaseRsp<Boolean>
	 */
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

	/**
	 * 课程监视器
	 *
	 * @param lessonId 课id
	 * @return {@code BaseRsp<List<SceneMonitorBo>> }
	 * @throws
	 * @see String
	 * @see BaseRsp<List<SceneMonitorBo>>
	 */
	@ApiOperation("后台课时场景监控")
	@GetMapping("/lesson_monitor")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<List<SceneMonitorBo>> lessonMonitor(@ApiParam(name = "resource_id",
			value = "课时编号") @RequestParam("resource_id") String lessonId) {

		List<SceneMonitorBo> monitorBos = lessonSceneService.lessonMonitor(lessonId);
		return new BaseRsp<>(monitorBos);

	}

	/**
	 * 获取监控刷新间隔
	 *
	 * @return {@code BaseRsp<Integer> }
	 * @throws
	 * @see BaseRsp<Integer>
	 */
	@ApiOperation("场景监控信息刷新时间")
	@GetMapping("/monitor_refresh_interval")
	@RequiresPermissions(MenuConstant.CMS_COURSES)
	public BaseRsp<Integer> getMonitorRefreshInterval() {
		String refreshInterval = ConfigUtil.getContextProperty("oj.monitor.refresh.interval");
		return new BaseRsp<>(Integer.parseInt(refreshInterval));
	}

	/**
	 * 查询都课
	 *
	 * @param courseId 课程id
	 * @return {@code BaseRsp<List<SimpleDataRes>> }
	 * @throws
	 * @see String
	 * @see BaseRsp<List<SimpleDataRes>>
	 */
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
	 * 获取拓扑场景
	 *
	 * @param lessonId 课时id
	 * @return {@code BaseRsp<TopoSceneDataBo> }
	 * @throws
	 * @see String
	 * @see BaseRsp<TopoSceneDataBo>
	 */
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

	@ApiOperation("计算视频时长")
	@GetMapping("/count_video")
	public BaseRsp<Boolean> countVideo() {
		courseLessonService.countVideo();
		return new BaseRsp<>(Boolean.TRUE);
	}

	/**
	 * 更新请求转换为实体类
	 *
	 * @param lessonUpdateReq
	 * @return
	 * @throws
	 * @see LessonUpdateReq
	 * @see CourseLesson
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
	 *
	 * @param lessonAddReq
	 * @return
	 * @throws
	 * @see LessonAddReq
	 * @see CourseLesson
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

