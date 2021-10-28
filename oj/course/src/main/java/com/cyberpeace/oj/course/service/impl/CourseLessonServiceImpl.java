package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.oj.ojbase.constant.CollectionType;
import com.cyberpeace.oj.ojbase.service.ResourceCollectionService;
import com.cyberpeace.oj.practice.constant.PaperType;
import com.cyberpeace.oj.practice.dao.entity.Paper;
import com.cyberpeace.oj.practice.dao.mapper.PaperMapper;
import com.cyberpeace.oj.practice.service.bo.AddOrUpdatePaperContext;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonCollection;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;
import com.cyberpeace.oj.practice.service.PaperService;

@Service
public class CourseLessonServiceImpl extends ServiceImpl<CourseLessonMapper, CourseLesson> implements CourseLessonService {

	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private ResourceCollectionService resourceCollectionService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private LessonConfigService lessonConfigService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private PaperService paperService;

	@Autowired
	private PaperMapper paperMapper;

	@Autowired
	@Qualifier("ojTransactionTemplate")
	private TransactionTemplate transactionTemplate;

	@Override
	public LessonInfoBo getLessonDetail(String userId, String courseId, String lessonId) {
		CourseLesson lesson = courseLessonMapper.selectById(lessonId);

		// 增加热度
		lesson.setHeat(lesson.getHeat() + 1);
		courseLessonMapper.updateById(lesson);

		// 基本信息拷贝
		LessonInfoBo infoBo = BeanUtils.copy(lesson, LessonInfoBo.class);

		// 课时文件组装
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(lesson.getAttachmentIds(),
				true);
		infoBo.setLessonFile(attachmentBos);

		// 上节和下节课程查询
		LessonConfigBo lessonConfig = lessonConfigService.getLessonConfig(courseId, userId, true);
		List<LessonConfigBo> boList = lessonConfig.getSortListByTree(lessonConfig, false);
		List<String> sortLessonIds = boList.stream().map(LessonConfigBo::getLesson)
				.collect(Collectors.toList());
		infoBo.setPreAndNext(infoBo, sortLessonIds);

		// 是否收藏
		Boolean collection = resourceCollectionService.selectByUnique(userId,
				CollectionType.LESSON.getCode(), lessonId);
		infoBo.setIsCollection(collection);

		// TODO 获取场景信息

		// 添加学习记录
		LessonRecord record = lessonRecordMapper.selectByLessonAndUser(lessonId, userId);
		if (null == record) {
			LessonRecord lessonRecord = new LessonRecord();
			lessonRecord.setLesson(lessonId);
			lessonRecord.setProgress(LessonRecord.FINISH);
			lessonRecord.setUser(userId);
			lessonRecord.setStartTime(new Date());
			lessonRecord.setEndTime(new Date());
			lessonRecord.setCourse(courseId);
		}

		return infoBo;
	}

	@Override
	public AdminLessonBo selectByCourseAndLesson(String courseId, String lessonId) {

		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			throw new BusinessException(OjBusinessErrorCode.COURSE_NOT_FOUND);
		}

		CourseLesson lesson = courseLessonMapper.selectById(lessonId);
		if (null == lesson) {
			throw new BusinessException(OjBusinessErrorCode.LESSON_NOT_FOUND);
		}

		AdminLessonBo lessonInfoBo = BeanUtils.copy(lesson, AdminLessonBo.class);
		List<AttachmentBo> attachmentBos = attachmentService.selectByIds(lesson.getAttachmentIds(),
				false);
		lessonInfoBo.setAttachments(attachmentBos);

		// 习题数据查询
		Paper paper = paperMapper.selectById(lesson.getPaperId());
		lessonInfoBo.setPracticeCount(paper.getPracticeCount());

		// TODO 查询场景名称以及场景访问权限

		return lessonInfoBo;
	}

	@Override
	public CourseLesson addOrUpdate(CourseLesson courseLesson, String ojSceneConfig,
			List<PaperPracticeContext> practiceList) {

		if (StringUtils.isBlank(courseLesson.getResourceId())) {
			courseLesson.setHeat(CommonConstant.ZERO);
			courseLesson.setBuiltin(CommonConstant.FALSE);
		}

		AddOrUpdatePaperContext paperContext = new AddOrUpdatePaperContext();
		if (StringUtils.isNotBlank(courseLesson.getResourceId())) {
			CourseLesson lesson = courseLessonMapper.selectById(courseLesson.getResourceId());
			if (null == lesson) {
				throw new BusinessException(OjBusinessErrorCode.DUPLICATE_EXCEPTION);
			}

			paperContext.setPracticeList(practiceList);
			paperContext.setResourceId(courseLesson.getPaperId());
		} else {

			paperContext.setName(courseLesson.getName());
			paperContext.setDescription(courseLesson.getName());
			paperContext.setIsPublic(CommonConstant.TRUE);
			paperContext.setPracticeList(practiceList);
			paperContext.setType(PaperType.LESSON.getCode());
		}

		// TODO 场景信息处理
		if (StringUtils.isNotBlank(ojSceneConfig)) {
			courseLesson.setTopoId("");
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {

				Paper paper = paperService.createOrUpdate(paperContext);
				courseLesson.setPaperId(paper.getResourceId());

				saveOrUpdate(courseLesson);
			}
		});

		return courseLesson;
	}

	@Override
	public IPage<AdminLessonListBo> pageByContext(Page page, LessonContext context, boolean isWeb) {

		IPage<CourseLesson> iPage = courseLessonMapper.pageByContext(page, context, isWeb);
		IPage<AdminLessonListBo> boIPage = iPage.convert(courseLesson -> {
			AdminLessonListBo adminLessonListBo = BeanUtils.copy(courseLesson,
					AdminLessonListBo.class);
			// 试卷相关
			Paper paper = paperMapper.selectById(courseLesson.getPaperId());
			adminLessonListBo.setPracticeCount(paper.getPracticeCount());
			adminLessonListBo.setPaperPublic(paper.getIsPublic());

			// 课程相关
			Course course = courseMapper.selectById(courseLesson.getCourseId());
			adminLessonListBo.setCourseName(course.getName());

			// 用户相关
			UserInfo userInfo = remoteUserService.getById(courseLesson.getCreateUserId());
			adminLessonListBo.setCreateUserName(userInfo.getUserName());
			return adminLessonListBo;
		});
		return boIPage;
	}

	@Override
	public Integer batchPublicPractice(List<String> resourceIds, Boolean isPublic) {
		List<CourseLesson> courseLessons = courseLessonMapper.selectBatchIds(resourceIds);

		List<String> paperIds = courseLessons.stream()
				.filter(courseLesson -> StringUtils.isNotBlank(courseLesson.getResourceId()))
				.map(CourseLesson::getPaperId).collect(Collectors.toList());

		// todo 调用paper模块修改公开和隐藏方法
		return null;
	}

	@Override
	public Boolean importLessons(String courseId, List<String> lessonIds) {
		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			throw new BusinessException(OjBusinessErrorCode.COURSE_NOT_FOUND);
		}

		List<CourseLesson> courseLessons = courseLessonMapper.selectBatchIds(lessonIds);
		for (CourseLesson courseLesson : courseLessons) {
			courseLesson.setCourseId(course.getResourceId());
			courseLesson.setResourceId(IDGeneratorUtil.getID());
		}

		boolean result = saveBatch(courseLessons);
		return result;
	}
}
