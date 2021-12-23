package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.oj.course.service.LessonSceneService;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.oj.ojbase.controller.req.SceneNodeReq;
import com.cyberpeace.oj.ojbase.controller.req.SceneStatusReq;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.baseinterface.Create;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.course.controller.req.*;
import com.cyberpeace.oj.course.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonConfigMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonPracticeBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.controller.PublicController;
import com.cyberpeace.oj.ojbase.controller.req.ResourcePublicReq;
import com.cyberpeace.cs.dao.entity.Attachment;
import com.cyberpeace.cs.service.AttachmentService;
import com.cyberpeace.cs.service.context.AttachmentBo;
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
	private RemoteTopoService remoteTopoService;

	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private LessonSceneService lessonSceneService;

	@ApiOperation("分页查询课时列表")
	@GetMapping("/list")
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
	public BaseRsp add(@Validated(Create.class) LessonAddReq lessonAddReq) {

		CourseLesson courseLesson = addReqToEntity(lessonAddReq);

		CourseLesson lesson = courseLessonService.addLesson(courseLesson,
				JsonUtil.toJsonObject(lessonAddReq.getOjSceneConfig(), JSONObject.class),
				JsonUtil.toJsonObjectArray(lessonAddReq.getPracticeList(),
						PaperPracticeContext.class));

		return new BaseRsp(lesson);
	}

	@ApiOperation("课时信息修改")
	@PatchMapping("")
	public BaseRsp patch(@Validated(Update.class) LessonUpdateReq lessonUpdateReq) {

		// 修改数据转换 进行视频和讲义地址是否修改判断
		CourseLesson courseLesson = updateReqToEntity(lessonUpdateReq);

		// 练习题数据空字符串则不修改 将paper_id设置进课时数据体中
		if (StringUtils.isBlank(lessonUpdateReq.getPracticeList())) {
			CourseLesson lesson = courseLessonMapper.selectById(lessonUpdateReq.getResourceId());
			courseLesson.setPaperId(lesson.getPaperId());
		}

		// 执行数据更新
		CourseLesson lesson = courseLessonService.updateLesson(courseLesson,
				JsonUtil.toJsonObject(lessonUpdateReq.getOjSceneConfig(), JSONObject.class),
				JsonUtil.toJsonObjectArray(lessonUpdateReq.getPracticeList(),
						PaperPracticeContext.class));
		return new BaseRsp(lesson);

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

			AttachmentBo uploadFile = attachmentService.uploadFile(AttachmentPath.VIDEO.getCode(),
					false, lessonUpdateReq.getVideo(), lessonUpdateReq.getCourseId());
			courseLesson.setVideoUrl(uploadFile.getFilePath());

			// 文件为空 url为空则置空
		} else if (null == lessonUpdateReq.getVideo()
				&& StringUtils.isBlank(lessonUpdateReq.getVideoUrl())) {
			courseLesson.setVideoUrl("");
		}

		// 有讲义地址并且讲义不为空 则修改文件
		if (StringUtils.isBlank(lessonUpdateReq.getHandoutUrl())
				&& null != lessonUpdateReq.getHandout()) {
			uploadHandout(courseLesson, lessonUpdateReq.getHandout());

			// 讲义为空 并且讲义地址为空 则置空
		} else if (StringUtils.isBlank(lessonUpdateReq.getHandoutUrl())
				&& null == lessonUpdateReq.getHandout()) {
			courseLesson.setHandoutUrl("");
		}

		return courseLesson;
	}

	@ApiOperation("课时信息删除")
	@DeleteMapping("/batch")
	public BaseRsp<Boolean> delete(@RequestBody @Validated BatchDeleteReq batchDeleteReq) {

		Boolean result = transactionTemplate.execute(new TransactionCallback<Boolean>() {

			@Override
			public Boolean doInTransaction(TransactionStatus status) {
				int result = courseLessonMapper.deleteBatchIds(batchDeleteReq.getResourceIds());
				int row = lessonConfigMapper.deleteByLessonIds(batchDeleteReq.getResourceIds());
				return result != 0 && row != 0;
			}
		});

		return new BaseRsp(result);
	}

	@ApiOperation("课时附件查询")
	@GetMapping("/attachment")
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
	public BaseRsp<Boolean>
			updateLessonPractice(@RequestBody @Validated AddLessonPracticeReq practiceReq) {

		Boolean result = courseLessonService.addLessonPractice(practiceReq.getResourceId(),
				practiceReq.getPracticeList());
		return new BaseRsp(result);
	}

	@ApiOperation("课时场景拓扑信息查询")
	@GetMapping("/scene_topo")
	public BaseRsp<TopoSceneDataBo> getTopoScene(@ApiParam(name = "resource_id",
			value = "资源数据编号") @RequestParam("resource_id") String lessonId) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		TopoSceneDataBo topoSceneData = courseLessonService.getTopoScene(lessonId, authContext);

		return new BaseRsp(topoSceneData);
	}

	@ApiOperation("课时监控")
	public BaseRsp getLessonMonitor() {
		// TODO
		return null;
	}

	@ApiOperation("课时练习公开与隐藏")
	@PatchMapping("/practice/batch_public")
	public BaseRsp<Boolean>
			publicPractice(@RequestBody @Validated ResourcePublicReq resourcePublicReq) {
		Boolean result = courseLessonService.batchPublicPractice(resourcePublicReq.getResourceIds(),
				resourcePublicReq.getIsPublic());
		return new BaseRsp<>(result);
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
	private CourseLesson addReqToEntity(LessonAddReq lessonAddReq) {

		// 知识点转换
		CourseLesson courseLesson = BeanUtils.copy(lessonAddReq, CourseLesson.class);
		courseLesson.setKnowledge(lessonAddReq.getKnowledge());
		courseLesson.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		if (lessonAddReq.getVideo() != null) {
			AttachmentBo attachmentBo = attachmentService.uploadFile(AttachmentPath.VIDEO.getCode(),
					false, lessonAddReq.getVideo(), lessonAddReq.getCourseId());
			courseLesson.setVideoUrl(attachmentBo.getFilePath());
		}

		if (lessonAddReq.getHandout() != null) {
			uploadHandout(courseLesson, lessonAddReq.getHandout());
		}

		return courseLesson;
	}

	/**
	 * 附件上传
	 * @param courseLesson
	 * @param handout
	 */
	private void uploadHandout(CourseLesson courseLesson, MultipartFile handout) {

		String suffix = handout.getOriginalFilename()
				.substring(handout.getOriginalFilename().lastIndexOf(".") + 1);

		// zip可能为md 或者 html
		if (suffix.equals(Attachment.FileType.ZIP.getDesc())) {
			AttachmentBo attachmentBo = attachmentService.uploadFile(
					AttachmentPath.MARKDOWN.getCode(), false, handout, courseLesson.getCourseId());
			courseLesson.setOriginMarkdownFile(attachmentBo.getFilePath());

			// 解析markdown文件
			String handoutPath = EnumUtils.getEnumDescByCode(AttachmentPath.HANDOUT.getCode(),
					AttachmentPath.class);
			String unzipPath = String.format(handoutPath, courseLesson.getCourseId())
					+ FileUtil.getFileName(attachmentBo.getFilePath(), false);
			List<String> pathList = attachmentService.unzipFile(attachmentBo.getFilePath(),
					unzipPath, Attachment.FileType.MD.getDesc());
			courseLesson.setHandoutType(CourseLesson.HandoutType.MD.getCode());

			// md文件内容作为url
			String content = MdUtil.parseMarkdown(pathList.get(CommonConstant.ZERO));
			courseLesson.setHandoutUrl(content);
		}

		// pdf讲义文件上传
		if (suffix.equals(Attachment.FileType.PDF.getDesc())) {
			AttachmentBo attachmentBo = attachmentService.uploadFile(
					AttachmentPath.HANDOUT.getCode(), false, handout, courseLesson.getCourseId());
			courseLesson.setHandoutType(CourseLesson.HandoutType.PDF.getCode());
			courseLesson.setHandoutUrl(attachmentBo.getFilePath());
		}

	}

	@ApiOperation("课时新增附件上传")
	@PostMapping("upload_attachment")
	public BaseRsp<String>
			uploadAttachment(@RequestParam("attachment") MultipartFile multipartFile) {

		AttachmentBo attachmentBo = attachmentService.uploadFile(
				AttachmentPath.ATTACHMENT.getCode(), CommonConstant.TRUE, multipartFile);
		return new BaseRsp<>(attachmentBo.getResourceId());

	}

	@ApiOperation("后台课时场景启动")
	@PostMapping("/start_scene")
	public BaseRsp<JSONObject> startScene(@RequestBody @Validated LessonSceneReq lessonSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		JSONObject scene = lessonSceneService.startScene(lessonSceneReq.getResourceId(),
				authContext);
		return new BaseRsp<>(scene);
	}

	@ApiOperation("后台课时场景暂停与恢复")
	@PatchMapping("/update_status")
	public BaseRsp<Boolean>
	updateSceneStatus(@RequestBody @Validated SceneStatusReq sceneStatusReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = lessonSceneService.updateSceneStatus(sceneStatusReq.getResourceId(),
				sceneStatusReq.getStatus(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("后台课时场景删除")
	@PostMapping("/delete_scene")
	public BaseRsp<Boolean> deleteScene(@RequestBody @Validated LessonSceneReq lessonSceneReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = lessonSceneService.deleteScene(lessonSceneReq.getResourceId(),
				authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("后台课时节点重启")
	@PostMapping("/node_restart")
	public BaseRsp<Boolean> restartNode(@RequestBody @Validated SceneNodeReq sceneNodeReq) {

		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = lessonSceneService.restartNode(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);
		return new BaseRsp<>(result);
	}

	@ApiOperation("后台课时节点重建")
	@PostMapping("/node_recreate")
	public BaseRsp<Boolean> recreateNode(@RequestBody @Validated SceneNodeReq sceneNodeReq) {
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				false);
		Boolean result = lessonSceneService.recreateNode(sceneNodeReq.getResourceId(),
				sceneNodeReq.getNodeId(), authContext);
		return new BaseRsp<>(result);
	}

}
