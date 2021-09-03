package com.cyberpeace.oj.course.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.oj.course.service.CourseLessonService;
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
	public LessonInfoBo getLessonDetail(String courseId, String lessonId) {
		CourseLesson lesson = courseLessonMapper.selectById(lessonId);
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

		// TODO 获取场景信息

		return null;
	}

	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	@Override
	public LessonConfigBo getLessonConfig(String courseId, String userId) {
		LessonConfig firstConfig = lessonConfigMapper.getFirstNode(courseId);
		LessonConfigBo configBo = BeanUtils.copy(firstConfig, LessonConfigBo.class);
		assembleLessonLib(configBo);
		return configBo;
	}

	/**
	 * 根据最上级节点组装课程目录
	 * @param firstNode
	 */
	private void assembleLessonLib(LessonConfigBo firstNode) {

		List<LessonConfig> childrens = lessonConfigMapper.listByParent(firstNode.getResourceId());

		if (CollectionUtils.isNotEmpty(childrens)) {
			List<LessonConfigBo> boList = childrens.stream()
					.map(lessonConfig -> BeanUtils.copy(lessonConfig, LessonConfigBo.class))
					.collect(Collectors.toList());

			boList.stream().forEach(bo -> assembleLessonLib(bo));
			firstNode.setLessonConfigs(boList);
		}

	}

	@Override
	public List<LessonConfigBo> getSortListByTree(LessonConfigBo configBo) {
		List<LessonConfigBo> configs = configBo.getLessonConfigs();
		if (CollectionUtils.isNotEmpty(configs)) {
			List<LessonConfigBo> configBos = configs.stream()
					.map(lessonConfigBo -> getSortListByTree(lessonConfigBo))
					.flatMap(Collection::stream)
					.filter(bo -> bo.getNodeType().intValue() == LessonConfig.NODE_LESSON)
					.collect(Collectors.toList());
			return configBos;
		}
		return Lists.newArrayList();
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
