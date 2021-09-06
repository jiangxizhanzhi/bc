package com.cyberpeace.oj.course.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;

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
	private LessonConfigMapper lessonConfigMapper;

	@Autowired
	private CourseMapper courseMapper;

	@Override
	public List<LessonRecord> selectByTime(String userId, Date startTime, Date endTime) {

		List<LessonRecord> lessonRecords = lessonRecordMapper.selectByTime(userId, startTime,
				endTime);
		return lessonRecords;
	}

	@Override
	public List<String> recordBelongCourse(List<String> recordLesson, List<CourseLesson> lessons) {
		List<CourseLesson> lessonList = lessons.stream()
				.filter(courseLesson -> recordLesson.contains(courseLesson.getResourceId()))
				.collect(Collectors.toList());

		List<String> courseIds = lessonList.stream().map(CourseLesson::getCourse).distinct()
				.collect(Collectors.toList());
		return courseIds;
	}

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
		String attachments = infoBo.getAttachments();
		if (StringUtils.isNotBlank(attachments)) {
			String[] fileIds = attachments.split(",");
			List<LessonFile> files = lessonFileMapper.selectBatchIds(Arrays.asList(fileIds));
			infoBo.setLessonFile(files);
		}

		// 上节和下节课程查询
		LessonConfigBo lessonConfig = getLessonConfig(courseId, userId);
		List<LessonConfigBo> boList = lessonConfig.getSortListByTree(lessonConfig);
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
		}

		return infoBo;
	}

	@Override
	public LessonConfigBo getLessonConfig(String courseId, String userId) {
		LessonConfig firstConfig = lessonConfigMapper.getFirstNode(courseId);
		Course course = courseMapper.selectById(firstConfig.getCourse());
		if (null == course) {
			return null;
		}
		LessonConfigBo configBo = BeanUtils.copy(firstConfig, LessonConfigBo.class);
		configBo.setName(course.getName());

		List<CourseLesson> lessons = courseLessonMapper
				.listByCourses(Arrays.asList(course.getResourceId()));
		Map<String, CourseLesson> lessonMap = lessons.stream().collect(
				Collectors.toMap(CourseLesson::getResourceId, courseLesson -> courseLesson));

		List<String> lessonIds = lessons.stream().map(CourseLesson::getResourceId)
				.collect(Collectors.toList());

		List<LessonRecord> recordList = lessonRecordMapper.listByLessonAndUser(userId, lessonIds);
		Map<String, LessonRecord> recordMap = recordList.stream()
				.collect(Collectors.toMap(LessonRecord::getLesson, lessonRecord -> lessonRecord));
		assembleLessonLib(configBo, lessonMap, recordMap);
		return configBo;
	}

	/**
	 * 根据最上级节点组装课程目录 
	 * @param firstNode 最上级节点 课程节点
	 * @param lessonMap 课时map集合 传入将补全课程信息
	 * @param recordMap 传入补全是否学习状态   
	 */
	private void assembleLessonLib(LessonConfigBo firstNode, Map<String, CourseLesson> lessonMap,
			Map<String, LessonRecord> recordMap) {

		// 获取子节点
		List<LessonConfig> childrens = lessonConfigMapper.listByParent(firstNode.getResourceId());

		if (CollectionUtils.isNotEmpty(childrens)) {
			// 数据格式转换
			List<LessonConfigBo> boList = childrens.stream().map(lessonConfig -> {
				LessonConfigBo configBo = BeanUtils.copy(lessonConfig, LessonConfigBo.class);

				CourseLesson lesson = lessonMap.get(configBo.getLesson());
				if (null != lesson) {
					configBo.setName(lesson.getName());
					configBo.setType(lesson.getType());
					configBo.setRequired(
							lesson.getLessonType().intValue() == CourseLesson.IS_REQUIRED);
				}

				configBo.setLearn(null != recordMap.get(configBo.getLesson()));
				return configBo;
			}).collect(Collectors.toList());

			// 递归组装
			boList.stream().forEach(bo -> assembleLessonLib(bo, lessonMap, recordMap));
			firstNode.setLessonConfigs(boList);
		}

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
}
