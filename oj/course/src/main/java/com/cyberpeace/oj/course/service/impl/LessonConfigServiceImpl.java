package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.constant.LessonConfigType;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.bo.AddLessonConfigContext;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
			log.error("Course not found! course_id=[{}]", courseId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		LessonConfig firstNode = lessonConfigMapper.getFirstNode(courseId);

		List<LessonConfigBo> configBos = lessonConfigToBo(userId, courseId, isPublic);

		Map<String, List<LessonConfigBo>> boMap = configBos.stream()
				.collect(Collectors.groupingBy(LessonConfigBo::getParentId));

		LessonConfigBo configBo = BeanUtils.copy(firstNode, LessonConfigBo.class);
		Course existCourse = courseMapper.selectById(firstNode.getCourseId());
		configBo.setName(existCourse.getName());

		assembleLessonLib(configBo, boMap);
		return configBo;
	}

	@Override
	public LessonConfigBo updateLessonConfig(LessonConfigBo configBo) {

		List<LessonConfigBo> lessonConfigs = configBo.getChildren();
		int chapterIndex = 0;
		int lessonIndex = 0;

		if (CollectionUtils.isNotEmpty(lessonConfigs)) {
			for (LessonConfigBo lessonConfig : lessonConfigs) {
				if (lessonConfig.getNodeType().intValue() == LessonConfigType.NODE_CHAPTER
						.getCode()) {
					lessonConfig.setIndex(chapterIndex);
					chapterIndex += CommonConstant.ONE;
				}
				if (lessonConfig.getNodeType().intValue() == LessonConfigType.NODE_LESSON
						.getCode()) {
					lessonConfig.setIndex(lessonIndex);
					lessonIndex += CommonConstant.ONE;
				}
			}
		}

		List<LessonConfigBo> sortListByTree = configBo.sortListByChildren(true, true);
		List<LessonConfig> configs = sortListByTree.stream()
				.map(bo -> BeanUtils.copy(bo, LessonConfig.class)).collect(Collectors.toList());

		this.saveOrUpdateBatch(configs);
		return configBo;
	}

	@Override
	public LessonConfig packageLessonConfig(AddLessonConfigContext configContext) {

		LessonConfig firstNode = lessonConfigMapper.getFirstNode(configContext.getCourseId());
		if (null == firstNode) {
			LessonConfig newConfig = BeanUtils.copy(configContext, LessonConfig.class);
			newConfig.setChapterName(configContext.getName());
			newConfig.setIndex(CommonConstant.ZERO);
			return newConfig;
		}

		LessonConfig lastLessonConfig = lessonConfigMapper
				.selectLastByParent(firstNode.getResourceId());

		LessonConfig lessonConfig = BeanUtils.copy(configContext, LessonConfig.class);
		lessonConfig.setChapterName(configContext.getName());
		lessonConfig.setIndex(
				lastLessonConfig != null ? lastLessonConfig.getIndex() + CommonConstant.ONE
						: CommonConstant.ZERO);
		lessonConfig.setParentId(firstNode.getResourceId());

		return lessonConfig;
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
		Map<String, CourseLesson> lessonMap = lessons.stream().collect(
				Collectors.toMap(CourseLesson::getResourceId, courseLesson -> courseLesson));
		List<String> lessonIds = StreamApiUtils.simpleMap(lessons, CourseLesson::getResourceId);

		// 查询课程目录数据
		List<LessonConfig> configs = lessonConfigMapper.selectByCourse(courseId);

		List<LessonConfigBo> configBos = configs.stream()
				.filter(lessonConfig -> (StringUtils.isNotBlank(lessonConfig.getParentId())
						&& StringUtils.isBlank(lessonConfig.getLessonId()))
						|| (CollectionUtils.isNotEmpty(lessonIds)
								&& StringUtils.isNotBlank(lessonConfig.getLessonId())
								&& lessonIds.contains(lessonConfig.getLessonId())))
				.map(lessonConfig -> {
					LessonConfigBo configBo = BeanUtils.copy(lessonConfig, LessonConfigBo.class);
					if (lessonConfig.getNodeType().intValue() == LessonConfigType.NODE_LESSON
							.getCode()) {
						CourseLesson lesson = lessonMap.get(lessonConfig.getLessonId());
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
			List<LessonRecord> recordList = lessonRecordMapper.listByLessonAndUser(userId,
					lessonIds);
			Map<String, LessonRecord> recordMap = recordList.stream().collect(
					Collectors.toMap(LessonRecord::getLesson, lessonRecord -> lessonRecord));
			configBos.forEach(lessonConfig -> lessonConfig
					.setLearn(null != recordMap.get(lessonConfig.getLessonId())));
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
		List<LessonConfigBo> bos = boMap.get(firstNode.getResourceId());
		CourseLesson courseLesson = courseLessonMapper.selectById(firstNode.getLessonId());
		if (null != courseLesson) {
			firstNode.setName(courseLesson.getName());
		}

		if (CollectionUtils.isNotEmpty(bos)) {
			for (int i = 0; i < bos.size(); i++) {
				LessonConfigBo lessonConfigBo = bos.get(i);
				lessonConfigBo.setTypeIndex(i + CommonConstant.ONE);
				assembleLessonLib(lessonConfigBo, boMap);
			}
			firstNode.setChildren(bos);
		}

	}

}
