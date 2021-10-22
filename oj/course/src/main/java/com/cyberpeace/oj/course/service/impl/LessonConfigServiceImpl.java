package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonConfigMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

@Service
public class LessonConfigServiceImpl extends ServiceImpl<LessonConfigMapper, LessonConfig> implements LessonConfigService {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	@Override
	public LessonConfigBo getLessonConfig(String courseId, String userId, Boolean isPublic) {

		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			throw new BusinessException(OjBusinessErrorCode.COURSE_NOT_FOUND);
		}
		LessonConfig firstNode = lessonConfigMapper.getFirstNode(courseId);
		List<LessonConfigBo> configBos = lessonConfigToBo(userId, courseId, isPublic);

		Map<String, List<LessonConfigBo>> boMap = configBos.stream()
				.collect(Collectors.groupingBy(LessonConfigBo::getParent));
		LessonConfigBo configBo = BeanUtils.copy(firstNode, LessonConfigBo.class);

		assembleLessonLib(configBo, boMap);
		return configBo;
	}

	@Override
	public LessonConfigBo updateLessonConfig(LessonConfigBo configBo) {

		List<LessonConfigBo> lessonConfigs = configBo.getLessonConfigs();
		int chapterIndex = 0;
		int lessonIndex = 0;

		if (CollectionUtils.isNotEmpty(lessonConfigs)) {
			for (LessonConfigBo lessonConfig : lessonConfigs) {
				if (lessonConfig.getNodeType().intValue() == LessonConfig.NODE_CHAPTER) {
					lessonConfig.setIndex(chapterIndex);
					chapterIndex += 1;
				}
				if (lessonConfig.getNodeType().intValue() == LessonConfig.NODE_LESSON) {
					lessonConfig.setIndex(lessonIndex);
					lessonIndex += 1;
				}
			}
		}

		List<LessonConfigBo> sortListByTree = configBo.getSortListByTree(configBo, true);
		List<LessonConfig> configs = sortListByTree.stream()
				.map(bo -> BeanUtils.copy(bo, LessonConfig.class)).collect(Collectors.toList());

		this.updateBatchById(configs);
		return configBo;
	}

	/**
	 * 数据格式转换
	 *
	 * @param userId
	 * @param courseId
	 * @param isPublic
	 * @return
	 */
	private List<LessonConfigBo> lessonConfigToBo(String userId, String courseId,
			Boolean isPublic) {
		// 查询课程下所有课时
		List<CourseLesson> lessons = courseLessonMapper.selectByCourse(courseId, false);
		Map<String, CourseLesson> lessonMap = lessons.stream()
				.collect(Collectors.toMap(CourseLesson::getResourceId, courseLesson -> courseLesson));

		// 查询课程目录数据
		List<LessonConfig> configs = lessonConfigMapper.selectByCourse(courseId, isPublic);
		List<LessonConfigBo> configBos = configs.stream()
				.filter(lessonConfig -> StringUtils.isNotBlank(lessonConfig.getParent()))
				.map(lessonConfig -> {
					LessonConfigBo configBo = BeanUtils.copy(lessonConfig, LessonConfigBo.class);
					if (lessonConfig.getNodeType().intValue() == LessonConfig.NODE_LESSON) {
						CourseLesson lesson = lessonMap.get(lessonConfig.getLesson());
						if (null != lesson) {
							configBo.setName(lesson.getName());
							configBo.setType(lesson.getType());
							configBo.setRequired(
									lesson.getCompulsory().intValue() == CourseLesson.IS_REQUIRED);
						}
					}
					return configBo;
				}).collect(Collectors.toList());

		// 学习记录查询
		if (isPublic) {
			List<String> lessonIds = lessons.stream().map(CourseLesson::getResourceId)
					.collect(Collectors.toList());
			List<LessonRecord> recordList = lessonRecordMapper.listByLessonAndUser(userId,
					lessonIds);
			Map<String, LessonRecord> recordMap = recordList.stream().collect(
					Collectors.toMap(LessonRecord::getLesson, lessonRecord -> lessonRecord));
			configBos.forEach(lessonConfig -> lessonConfig
					.setLearn(null != recordMap.get(lessonConfig.getLesson())));
		}

		return configBos;
	}

	/**
	 * 根据最上级节点组装课程目录
	 *
	 * @param firstNode 最上级节点 课程节点
	 * @param boMap
	 */
	private void assembleLessonLib(LessonConfigBo firstNode,
			Map<String, List<LessonConfigBo>> boMap) {

		// 获取子节点
		List<LessonConfigBo> bos = boMap.get(firstNode.getParent());

		if (CollectionUtils.isNotEmpty(bos)) {
			bos.forEach(bo -> assembleLessonLib(bo, boMap));
			firstNode.setLessonConfigs(bos);
		}

	}

}
