package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.dao.oj_entity.Lesson;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

@Service
public class CourseLessonServiceImpl implements CourseLessonService {

	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private LessonFileMapper lessonFileMapper;

	@Autowired
	private LessonCollectionMapper lessonCollectionMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private LessonMapper lessonMapper;

	@Autowired
	private LessonConfigService lessonConfigService;

	@Override
	public LessonScene getLessonScene(UserInfo user, String lessonId) {
		// 获取启动的场景 优先从课堂获取 todo
		return null;
	}

	@Override
	public LessonInfoBo getLessonDetail(String userId, String courseId, String lessonId) {
		CourseLesson lesson = courseLessonMapper.selectById(lessonId);

		// 增加热度
		lesson.setHeat(lesson.getHeat() + 1);
		courseLessonMapper.updateById(lesson);

		// 基本信息拷贝
		LessonInfoBo infoBo = BeanUtils.copy(lesson, LessonInfoBo.class);

		// 课时文件组装
		List<LessonFile> lessonFiles = lessonFileMapper.listByLesson(lessonId);
		infoBo.setLessonFile(lessonFiles);

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
	public LessonInfoBo getAdminLessonDetail(String courseId, String lessonId) {

		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			throw new BusinessException(OjBusinessErrorCode.COURSE_NOT_FOUND);
		}

		Lesson lesson = lessonMapper.selectById(lessonId);
		if (null == lesson) {
			throw new BusinessException(OjBusinessErrorCode.LESSON_NOT_FOUND);
		}

		LessonInfoBo copy = BeanUtils.copy(lesson, LessonInfoBo.class);

		// 查询习题数量

		// 查询场景名称以及场景访问权限

		return null;
	}
}
