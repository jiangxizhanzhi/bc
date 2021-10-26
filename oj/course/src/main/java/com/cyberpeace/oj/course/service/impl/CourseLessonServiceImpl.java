package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
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
import com.cyberpeace.oj.course.dao.mapper.LessonCollectionMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.service.AttachmentService;
import com.cyberpeace.oj.ojbase.service.bo.AttachmentBo;
import com.cyberpeace.oj.practice.service.TestPaperService;

@Service
public class CourseLessonServiceImpl extends ServiceImpl<CourseLessonMapper, CourseLesson> implements CourseLessonService {

	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private AttachmentService attachmentService;

	@Autowired
	private LessonCollectionMapper lessonCollectionMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private LessonConfigService lessonConfigService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private TestPaperService testPaperService;

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
		LessonCollection collection = lessonCollectionMapper.selectByUserAndLesson(userId,
				lessonId);
		infoBo.setIsCollection(null != collection && collection.getIsCollection());

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
	public String collectionLesson(String userId, String lessonId, Boolean isCollection) {
		LessonCollection exist = lessonCollectionMapper.selectByUserAndLesson(userId, lessonId);

		if (null == exist) {
			LessonCollection newCollection = new LessonCollection();
			newCollection.setIsCollection(isCollection);
			newCollection.setLesson(lessonId);
			newCollection.setUpdateTime(new Date());
			newCollection.setUser(userId);
			lessonCollectionMapper.insert(newCollection);
		} else {
			exist.setIsCollection(isCollection);
			lessonCollectionMapper.update(exist);
		}

		return lessonId;
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

		// TODO 查询习题数量

		// TODO 查询场景名称以及场景访问权限

		return lessonInfoBo;
	}

	@Override
	public LessonFileBo uploadLessonFile(MultipartFile handout) {
		return null;
	}

	@Override
	public CourseLesson addOrUpdate(CourseLesson courseLesson, String ojSceneConfig,
			String practiceList) {

		if (StringUtils.isBlank(courseLesson.getResourceId())) {
			courseLesson.setHeat(CommonConstant.ZERO);
			courseLesson.setBuiltin(CommonConstant.FALSE);
		}

		// TODO 习题处理 试卷存在习题数据不为空 则为修改
		if (StringUtils.isNotBlank(practiceList)) {
			if (StringUtils.isNotBlank(courseLesson.getResourceId())) {
				CourseLesson lesson = courseLessonMapper.selectById(courseLesson.getResourceId());
				if (null == lesson) {
					throw new BusinessException(OjBusinessErrorCode.DUPLICATE_EXCEPTION);
				}
			}

			courseLesson.setPaperId("");
		}

		// TODO 场景信息处理
		if (StringUtils.isNotBlank(ojSceneConfig)) {
			courseLesson.setTopoId("");
		}

		this.saveOrUpdate(courseLesson);
		return courseLesson;
	}

	@Override
	public IPage<AdminLessonListBo> pageByContext(Page page, LessonContext context, boolean isWeb) {

		IPage<CourseLesson> iPage = courseLessonMapper.pageByContext(page, context, isWeb);
		IPage<AdminLessonListBo> boIPage = iPage.convert(courseLesson -> {
			AdminLessonListBo adminLessonListBo = BeanUtils.copy(courseLesson,
					AdminLessonListBo.class);
			// todo 试卷相关
			// adminLessonListBo.setPracticeCount();
			// adminLessonListBo.setPaperPublic();

			Course course = courseMapper.selectById(courseLesson.getCourseId());
			adminLessonListBo.setCourseName(course.getName());

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
