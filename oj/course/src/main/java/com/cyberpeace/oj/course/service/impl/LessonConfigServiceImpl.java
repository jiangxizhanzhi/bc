package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.StreamApiUtils;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

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

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public LessonConfigBo getLessonConfig(String courseId, AuthContext authContext) {

		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			log.error("Course not found! course_id=[{}]", courseId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		LessonConfig firstNode = lessonConfigMapper.getCourseNode(courseId);
		List<LessonConfigBo> configBos = getConfigBo(courseId, authContext);

		Map<String, List<LessonConfigBo>> boMap = StreamApiUtils.groupByColumn(configBos,
				LessonConfigBo::getParentId);
		LessonConfigBo configBo = BeanUtils.copy(firstNode, LessonConfigBo.class);

		// 设置课时和章节索引
		setConfigTypeIndex(configBo, boMap);
		return configBo;
	}

	@Override
	public LessonConfigBo updateLessonConfig(LessonConfigBo configBo) {

		// 重新编排数据
		resetLessonConfigBo(configBo);

		// 拆解树
		List<LessonConfigBo> sortListByTree = configBo.sortListByChildren(true, true);
		List<LessonConfig> configs = StreamApiUtils.simpleMap(sortListByTree,
				bo -> BeanUtils.copy(bo, LessonConfig.class));

		// 原始目录删除 新增全新目录
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				lessonConfigMapper.deleteByCourse(configBo.getCourseId());
				saveOrUpdateBatch(configs);
			}
		});

		return configBo;
	}

	/**
	 * 重新编排目录数据 重置index
	 * @param configBo
	 */
	private void resetLessonConfigBo(LessonConfigBo configBo) {

		if (CollectionUtils.isNotEmpty(configBo.getChildren())) {
			// 设置初始数据
			int chapterIndex = 0;
			int lessonIndex = 0;

			for (LessonConfigBo lessonConfig : configBo.getChildren()) {
				// 重置章节数据
				if (lessonConfig.getNodeType().intValue() == LessonConfig.NodeType.NODE_CHAPTER
						.getCode()) {
					lessonConfig.setIndex(chapterIndex);
					chapterIndex++;
				}

				// 重置课时数据
				if (lessonConfig.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON
						.getCode()) {
					lessonConfig.setIndex(lessonIndex);
					lessonIndex++;
				}

				// 递归编排数据
				resetLessonConfigBo(lessonConfig);
			}
		}
	}

	@Override
	public LessonConfig packageLessonConfig(AddLessonConfigContext configContext) {

		LessonConfig firstNode = lessonConfigMapper.getCourseNode(configContext.getCourseId());
		if (null == firstNode) {
			LessonConfig newConfig = BeanUtils.copy(configContext, LessonConfig.class);
			newConfig.setIndex(CommonConstant.ZERO);
			return newConfig;
		}

		// 获取课程下的最后一个课时
		LessonConfig lastLessonConfig = lessonConfigMapper.selectLast(firstNode.getResourceId());

		// 组装课时信息
		LessonConfig lessonConfig = BeanUtils.copy(configContext, LessonConfig.class);
		lessonConfig.setIndex(
				lastLessonConfig != null ? lastLessonConfig.getIndex() + CommonConstant.ONE
						: CommonConstant.ZERO);
		lessonConfig.setParentId(firstNode.getResourceId());

		return lessonConfig;
	}

	/**
	 * 数据格式转换
	 * @param courseId
	 * @param authContext
	 * @return
	 */
	private List<LessonConfigBo> getConfigBo(String courseId, AuthContext authContext) {
		// 查询课程下所有课时
		List<CourseLesson> lessons = courseLessonMapper.selectByCourse(courseId,
				authContext.getIsWeb());
		Map<String, CourseLesson> lessonMap = lessons.stream().collect(
				Collectors.toMap(CourseLesson::getResourceId, courseLesson -> courseLesson));
		List<String> lessonIds = StreamApiUtils.simpleMap(lessons, CourseLesson::getResourceId);

		// 查询课程目录数据
		List<LessonConfig> configs = lessonConfigMapper.selectByCourse(courseId);

		// 过滤掉课程节点
		List<LessonConfig> lessonConfigs = StreamApiUtils.simpleFilter(configs,
				lessonConfig -> (StringUtils.isNotBlank(lessonConfig.getParentId())
						&& StringUtils.isBlank(lessonConfig.getLessonId()))
						|| (CollectionUtils.isNotEmpty(lessonIds)
								&& StringUtils.isNotBlank(lessonConfig.getLessonId())
								&& lessonIds.contains(lessonConfig.getLessonId())));

		// 数据类型转换
		List<LessonConfigBo> configBos = StreamApiUtils.simpleMap(lessonConfigs,
				lessonConfig -> configToBo(lessonConfig, lessonMap));

		// 前台掉用 查询学习记录
		if (authContext.getIsWeb()) {
			List<LessonRecord> recordList = lessonRecordMapper
					.listByLessonAndUser(authContext.getUserId(), lessonIds);
			Map<String, LessonRecord> recordMap = recordList.stream().collect(
					Collectors.toMap(LessonRecord::getLessonId, lessonRecord -> lessonRecord));
			configBos.forEach(lessonConfig -> lessonConfig
					.setLearn(null != recordMap.get(lessonConfig.getLessonId())));
		}

		return configBos;
	}

	/**
	 * 实体类数据转换
	 * @param lessonConfig
	 * @return
	 */
	private LessonConfigBo configToBo(LessonConfig lessonConfig,
			Map<String, CourseLesson> lessonMap) {

		LessonConfigBo configBo = BeanUtils.copy(lessonConfig, LessonConfigBo.class);
		if (lessonConfig.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON.getCode()) {
			CourseLesson lesson = lessonMap.get(lessonConfig.getLessonId());
			if (null != lesson) {
				configBo.setType(lesson.getType());
				configBo.setRequired(lesson.getCompulsory()
						.intValue() == CourseLesson.CompulsoryType.REQUIRED.getCode());
			}
		}

		return configBo;
	}

	/**
	 * 根据最上级节点组装课程目录
	 *
	 * @param node 最上级节点 课程节点
	 * @param boMap
	 */
	private void setConfigTypeIndex(LessonConfigBo node, Map<String, List<LessonConfigBo>> boMap) {

		// 子节点为空不进行任何操作
		if (CollectionUtils.isEmpty(boMap.get(node.getResourceId()))) {
			return;
		}

		// 设置type_index 章节
		if (CollectionUtils.isNotEmpty(boMap.get(node.getResourceId()))) {
			int chapterTypeIndex = 1;
			int lessonTypeIndex = 1;

			for (LessonConfigBo nodeChild : boMap.get(node.getResourceId())) {
				if (nodeChild.getNodeType().intValue() == LessonConfig.NodeType.NODE_CHAPTER
						.getCode().intValue()) {
					nodeChild.setTypeIndex(chapterTypeIndex);
					chapterTypeIndex++;
				}

				if (nodeChild.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON
						.getCode().intValue()) {
					nodeChild.setTypeIndex(lessonTypeIndex);
					lessonTypeIndex++;
				}

				setConfigTypeIndex(nodeChild, boMap);
			}

			node.setChildren(boMap.get(node.getResourceId()));
		}
	}

}
