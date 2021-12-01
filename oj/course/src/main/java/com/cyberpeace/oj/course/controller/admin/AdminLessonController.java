package com.cyberpeace.oj.course.controller.admin;

import java.util.List;

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
import com.cyberpeace.oj.course.controller.res.TopoSceneDataRes;
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
import com.cyberpeace.oj.ojbase.dao.entity.Attachment;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;
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

		CourseLesson courseLesson = updateReqToEntity(lessonUpdateReq);

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
		if (StringUtils.isBlank(lessonUpdateReq.getVideoUrl())
				&& null != lessonUpdateReq.getVideo()) {

			AttachmentBo uploadFile = attachmentService.uploadFile(
					Attachment.AttachmentType.VIDEO.getCode(), CommonConstant.TRUE,
					lessonUpdateReq.getVideo());
			courseLesson.setVideoUrl(uploadFile.getFilePath());
		} else if (null == lessonUpdateReq.getVideo()
				&& StringUtils.isBlank(lessonUpdateReq.getVideoUrl())) {

			courseLesson.setVideoUrl("");
		}

		if (StringUtils.isBlank(lessonUpdateReq.getHandoutUrl())
				&& null != lessonUpdateReq.getHandout()) {
			uploadHandout(courseLesson, lessonUpdateReq.getHandout());

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
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
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
	public BaseRsp<TopoSceneDataRes> getTopoScene(@ApiParam(name = "resource_id",
			value = "资源数据编号") @RequestParam("resource_id") String resourceId) {

		CourseLesson courseLesson = courseLessonMapper.selectById(resourceId);
		if (null == courseLesson) {
			log.error("getTopoScene: lesson not found! lesson_id=[{}]", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		TopoSceneDataRes topoSceneDataRes = new TopoSceneDataRes();
		if (courseLesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode()
				&& StringUtils.isNotBlank(courseLesson.getTopoId())) {
			JSONObject topo = remoteTopoService.getTopoById(courseLesson.getTopoId());
			topoSceneDataRes.setSceneConfigData(topo);
		}

		// TODO 场景信息查询
		return new BaseRsp(topoSceneDataRes);
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
			AttachmentBo attachmentBo = attachmentService.uploadFile(
					Attachment.AttachmentType.VIDEO.getCode(), CommonConstant.TRUE,
					lessonAddReq.getVideo());
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
		AttachmentBo attachmentBo = attachmentService.uploadFile(
				Attachment.AttachmentType.HANDOUT.getCode(), CommonConstant.TRUE, handout);

		String suffix = attachmentBo.getFilePath()
				.substring(attachmentBo.getFilePath().lastIndexOf(".") + 1);

		if (suffix.equals(Attachment.FileType.ZIP.getDesc())) {
			courseLesson.setOriginMarkdownFile(attachmentBo.getFilePath());
			// 解析markdown文件
			String filePath = attachmentService.parseMarkdown(attachmentBo.getFilePath(),
					EnumUtils.getEnumDescByCode(Attachment.AttachmentType.HANDOUT.getCode(),
							Attachment.AttachmentType.class));

			courseLesson.setHandoutType(CourseLesson.HandoutType.MD.getCode());
			courseLesson.setHandoutUrl(filePath);
		}
		if (suffix.equals(Attachment.FileType.PDF.getDesc())) {
			courseLesson.setHandoutType(CourseLesson.HandoutType.PDF.getCode());
			courseLesson.setHandoutUrl(attachmentBo.getFilePath());
		}
		if (suffix.equals(Attachment.FileType.HTML.getDesc())) {
			courseLesson.setHandoutType(CourseLesson.HandoutType.PPT.getCode());
			courseLesson.setHandoutUrl(attachmentBo.getFilePath());
		}
	}

	@ApiOperation("课时新增附件上传")
	@PostMapping("upload_attachment")
	public BaseRsp<String>
			uploadAttachment(@RequestParam("attachment") MultipartFile multipartFile) {

		AttachmentBo attachmentBo = attachmentService.uploadFile(
				Attachment.AttachmentType.ATTACHMENT.getCode(), CommonConstant.TRUE, multipartFile);
		return new BaseRsp<>(null != attachmentBo ? attachmentBo.getResourceId() : "");

	}

}
