package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.service.HandoutVideoService;
import com.cyberpeace.cs.service.UtilService;
import com.cyberpeace.cs.service.context.video.VideoCheckPointConfig;
import com.cyberpeace.cs.service.context.video.VideoCheckPointItem;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonConfigMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.cyberpeace.oj.course.service.bo.AddLessonConfigContext;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.cyberpeace.oj.course.service.bo.LessonConfigDumpBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 课时配置Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see ServiceImpl
 * @see LessonConfigService
 * @since 1.0.0
 */
@Service
@Slf4j
public class LessonConfigServiceImpl extends ServiceImpl<LessonConfigMapper, LessonConfig> implements LessonConfigService {

	/**
	 * 课程Mapper
	 *
	 * @see CourseMapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课时配置Mapper
	 *
	 * @see LessonConfigMapper
	 */
	@Autowired
	private LessonConfigMapper lessonConfigMapper;

	/**
	 * 课程课时Mapper
	 *
	 * @see CourseLessonMapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 课时记录Mapper
	 *
	 * @see LessonRecordMapper
	 */
	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * utilService
	 *
	 * @see UtilService
	 */
	@Autowired
	private UtilService utilService;

	/**
	 * 讲义视频Service
	 *
	 * @see HandoutVideoService
	 */
	@Autowired
	private HandoutVideoService handoutVideoService;

	/**
	 * 有效冲突
	 *
	 * @param courseId 课程id
	 * @throws
	 * @see String
	 */
	@Override
	public void validConflict(String courseId) {
		List<LessonConfig> lessonConfigsConflict = lessonConfigMapper.selectChapterLessonConflict(courseId);
		if (!lessonConfigsConflict.isEmpty()) {
			final List<String> collect = lessonConfigsConflict.stream().map(LessonConfig::getName).collect(Collectors.toList());
			log.error("lesson config is conflict {}", collect.toString());
			throw new BusinessException(OjBusinessErrorCode.COURSE_LESSON_CONFLICT, collect.toString());
		}
	}

	/**
	 * 有效课时和章
	 *
	 * @param courseId 课程id
	 * @throws
	 * @see String
	 */
	@Override
	public void validLessonAndChapter(String courseId) {
		List<LessonConfig> includeLessonAndChapter = lessonConfigMapper.selectIncludeLessonAndChapter(courseId);
		if (!includeLessonAndChapter.isEmpty()) {
			final List<String> collect = includeLessonAndChapter.stream().map(LessonConfig::getName).collect(Collectors.toList());
			log.error("lesson and chapter in the same floor {}", collect.toString());
			throw new BusinessException(OjBusinessErrorCode.COURSE_LESSON_CONFLICT, collect.toString());
		}
	}

	/**
	 * 获取课时配置
	 *
	 * @param courseId    课程id
	 * @param authContext auth 上下文
	 * @return {@code LessonConfigBo }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see LessonConfigBo
	 */
	@Override
	public LessonConfigBo getLessonConfig(String courseId, AuthContext authContext) {

		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			log.error("Course not found! course_id=[{}]", courseId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		LessonConfig firstNode = lessonConfigMapper.getCourseNode(courseId);
		List<LessonConfigBo> configBos = getConfigBo(courseId, authContext);

		Map<String, List<LessonConfigBo>> boMap = StreamApiUtils.groupBy(configBos,
				LessonConfigBo::getParentId);
		LessonConfigBo configBo = BeanUtils.copy(firstNode, LessonConfigBo.class);

		// 设置课时和章节索引
		setConfigTypeIndex(configBo, boMap);
		return configBo;
	}

	/**
	 * 更新课时配置
	 *
	 * @param configBo 配置Bo
	 * @return {@code LessonConfigBo }
	 * @throws
	 * @see LessonConfigBo
	 * @see LessonConfigBo
	 */
	@Override
	public LessonConfigBo updateLessonConfig(LessonConfigBo configBo) {

		validLessonConfigBo(configBo);

		// 重新编排数据
		resetLessonConfigBo(configBo);

		// 拆解树
		List<LessonConfigBo> sortListByTree = configBo.sortListByChildren(true, true);
		List<LessonConfig> configs = StreamApiUtils.map(sortListByTree,
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
	 * 校验不合理的编排，课时下不允许出现课时和章节
	 *
	 * @param configBo 配置Bo
	 * @throws
	 * @see LessonConfigBo
	 */
	private void validLessonConfigBo(LessonConfigBo configBo) {

		if (configBo.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON.getCode()) {
			List<LessonConfigBo> children = configBo.getChildren();
			if(CollectionUtils.isNotEmpty(children) && children.size() > 0) {
				List<String> collect = children.stream().map(LessonConfigBo::getName).collect(Collectors.toList());
				log.error("lesson conflict {}", collect.toString());
				throw new BusinessException(OjBusinessErrorCode.COURSE_LESSON_CONFLICT, collect.toString());
			}
		}
		if (CollectionUtils.isNotEmpty(configBo.getChildren())) {
			for (LessonConfigBo lessonConfigBo: configBo.getChildren()) {
				validLessonConfigBo(lessonConfigBo);
			}
		}

	}

	/**
	 * 重新编排目录数据 重置index
	 *
	 * @param configBo 配置Bo
	 * @throws
	 * @see LessonConfigBo
	 */
	private void resetLessonConfigBo(LessonConfigBo configBo) {

		if (CollectionUtils.isNotEmpty(configBo.getChildren())) {
			// 设置初始数据
			int index = 0;

			for (LessonConfigBo lessonConfig : configBo.getChildren()) {
				// 重置章节数据
				if (lessonConfig.getNodeType().intValue() == LessonConfig.NodeType.NODE_CHAPTER
						.getCode()) {
					lessonConfig.setIndex(index);
					index++;
				}

				// 重置课时数据
				if (lessonConfig.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON
						.getCode()) {
					lessonConfig.setIndex(index);
					index++;
				}

				// 递归编排数据
				resetLessonConfigBo(lessonConfig);
			}
		}
	}

	/**
	 * 包课时配置
	 *
	 * @param configContext config 上下文
	 * @return {@code LessonConfig }
	 * @throws
	 * @see AddLessonConfigContext
	 * @see LessonConfig
	 */
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
	 * 导出课时配置
	 *
	 * @param courseId    课程id
	 * @param authContext auth 上下文
	 * @return {@code LessonConfigDumpBo }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see LessonConfigDumpBo
	 */
	@Override
	public LessonConfigDumpBo dumpLessonConfig(String courseId, AuthContext authContext) {
		LessonConfigBo lessonConfig = getLessonConfig(courseId, authContext);
		return configToDumpBo(lessonConfig);
	}

	/**
	 * 加载课时配置
	 *
	 * @param configDumpBo  配置导出Bo
	 * @param courseId      课程id
	 * @param courseLessons 课程课
	 * @return {@code List<LessonConfig> }
	 * @throws
	 * @see LessonConfigDumpBo
	 * @see String
	 * @see List<CourseLesson>
	 * @see List<LessonConfig>
	 */
	@Override
	public List<LessonConfig> loadLessonConfig(LessonConfigDumpBo configDumpBo, String courseId,
			List<CourseLesson> courseLessons) {

		Map<String, String> lessonNameMap = StreamApiUtils.toMap(courseLessons,
				CourseLesson::getName, CourseLesson::getResourceId);

		// 基本数据拷贝
		LessonConfig lessonConfig = BeanUtils.copy(configDumpBo, LessonConfig.class);
		LessonConfig courseNode = lessonConfigMapper.getCourseNode(courseId);
		lessonConfig.setResourceId(
				null != courseNode ? courseNode.getResourceId() : IDGeneratorUtil.getID());
		lessonConfig.setCourseId(courseId);

		List<LessonConfig> lessonConfigs = dumpBoToEntity(configDumpBo.getChildren(), courseId,
				lessonNameMap, lessonConfig.getResourceId());
		lessonConfigs.add(CommonConstant.ZERO, lessonConfig);

		return lessonConfigs;
	}

	/**
	 * 数据转换
	 *
	 * @param configDumpBos
	 * @param courseId
	 * @param lessonNameMap
	 * @param parentId      父母id
	 * @return
	 * @throws
	 * @see List<LessonConfigDumpBo>
	 * @see String
	 * @see Map<String, String>
	 * @see String
	 * @see List<LessonConfig>
	 */
	private List<LessonConfig> dumpBoToEntity(List<LessonConfigDumpBo> configDumpBos,
			String courseId, Map<String, String> lessonNameMap, String parentId) {

		// 空数据则返回空列表
		if (CollectionUtils.isEmpty(configDumpBos)) {
			return Lists.newArrayList();
		}

		// 组装数据
		List<LessonConfig> configList = Lists.newArrayList();
		configDumpBos.forEach(configDumpBo -> {
			LessonConfig lessonConfig = BeanUtils.copy(configDumpBo, LessonConfig.class);
			String existConfigId = lessonConfigMapper.selectByCourseAndLessonName(courseId,
					configDumpBo.getName());
			lessonConfig.setResourceId(StringUtils.isNotBlank(existConfigId) ? existConfigId
					: IDGeneratorUtil.getID());
			lessonConfig.setCourseId(courseId);
			lessonConfig.setParentId(parentId);

			// 课时数据设置
			if (lessonConfig.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON.getCode()
					.intValue()) {
				lessonConfig.setLessonId(lessonNameMap.get(lessonConfig.getName()));
			}
			configList.add(lessonConfig);

			// 子类数据
			List<LessonConfig> childrenList = dumpBoToEntity(configDumpBo.getChildren(), courseId,
					lessonNameMap, lessonConfig.getResourceId());
			configList.addAll(childrenList);
		});

		return configList;
	}

	/**
	 * 类型转换
	 *
	 * @param lessonConfigBo
	 * @return
	 * @throws
	 * @see LessonConfigBo
	 * @see LessonConfigDumpBo
	 */
	private LessonConfigDumpBo configToDumpBo(LessonConfigBo lessonConfigBo) {

		// 基本数据copy
		LessonConfigDumpBo configDumpBo = BeanUtils.copy(lessonConfigBo, LessonConfigDumpBo.class);

		// 子类数据处理
		List<LessonConfigBo> children = lessonConfigBo.getChildren();
		if (CollectionUtils.isNotEmpty(children)) {
			List<LessonConfigDumpBo> configDumpBos = StreamApiUtils.map(children,
					configBo -> configToDumpBo(configBo));
			configDumpBo.setChildren(configDumpBos);
		}

		return configDumpBo;

	}

	/**
	 * 数据格式转换
	 *
	 * @param courseId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see List<LessonConfigBo>
	 */
	private List<LessonConfigBo> getConfigBo(String courseId, AuthContext authContext) {
		// 查询课程下所有课时
		List<CourseLesson> lessons = courseLessonMapper.selectByCourse(courseId,
				authContext.getIsWeb());
		Map<String, CourseLesson> lessonMap = lessons.stream().collect(
				Collectors.toMap(CourseLesson::getResourceId, courseLesson -> courseLesson));
		List<String> lessonIds = StreamApiUtils.map(lessons, CourseLesson::getResourceId);

		// 查询课程目录数据
		List<LessonConfig> configs = lessonConfigMapper.selectByCourse(courseId);

		// 过滤掉课程节点
		List<LessonConfig> lessonConfigs = StreamApiUtils.filter(configs,
				lessonConfig -> (StringUtils.isNotBlank(lessonConfig.getParentId())
						&& StringUtils.isBlank(lessonConfig.getLessonId()))
						|| (CollectionUtils.isNotEmpty(lessonIds)
								&& StringUtils.isNotBlank(lessonConfig.getLessonId())
								&& lessonIds.contains(lessonConfig.getLessonId())));

		// 数据类型转换
		List<LessonConfigBo> configBos = StreamApiUtils.map(lessonConfigs,
				lessonConfig -> configToBo(lessonConfig, lessonMap));


		// 前台掉用 查询学习记录
		if (authContext.getIsWeb()) {
			List<LessonRecord> recordList = lessonRecordMapper
					.listByLessonAndUser(authContext.getUserId(), lessonIds);
			Map<String, List<LessonRecord>> recordMap = recordList.stream().collect(Collectors.groupingBy(LessonRecord::getLessonId));
			configBos.forEach(lessonConfig -> lessonConfig
					.setLearn(null == recordMap.get(lessonConfig.getLessonId()) ? 0 : 100));
			// 视频课时的学习进度等于视频进度
			Map<String, String> lessonVideoMap = StreamApiUtils.toMap(
					StreamApiUtils.filter(lessons,
							l -> StringUtils.isNotBlank(l.getVideoUrl())
									&& StringUtils.isNotBlank(l.getVideoCheckPoint())),
					CourseLesson::getResourceId, l -> utilService.handlerFileUrl(l.getVideoUrl()));
			Map<String, Set<Integer>> progressMap = handoutVideoService
					.getProgressBatch(lessonVideoMap.values(), authContext.getUserId());
			for (LessonConfigBo bo : configBos) {
				if (lessonVideoMap.containsKey(bo.getLessonId())) {
					String path = lessonVideoMap.get(bo.getLessonId());
					if (progressMap.containsKey(path)) {
						bo.setLearn(calVideoProgress(
								lessonMap.get(bo.getLessonId()).getVideoCheckPoint(),
								progressMap.get(path)));
					} else {
						bo.setLearn(0);
					}
				}
			}
		}

		return configBos;
	}

	/**
	 * 实体类数据转换
	 *
	 * @param lessonConfig
	 * @param lessonMap    课时映射
	 * @return
	 * @throws
	 * @see LessonConfig
	 * @see Map<String, CourseLesson>
	 * @see LessonConfigBo
	 */
	private LessonConfigBo configToBo(LessonConfig lessonConfig,
			Map<String, CourseLesson> lessonMap) {

		LessonConfigBo configBo = BeanUtils.copy(lessonConfig, LessonConfigBo.class);
		if (lessonConfig.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON.getCode()) {
			CourseLesson lesson = lessonMap.get(lessonConfig.getLessonId());
			if (null != lesson) {
				configBo.setType(lesson.getType());
				configBo.setCompulsory(lesson.getCompulsory());
			}
		}

		return configBo;
	}

	/**
	 * 根据最上级节点组装课程目录
	 *
	 * @param node  最上级节点 课程节点
	 * @param boMap Bo映射
	 * @throws
	 * @see LessonConfigBo
	 * @see Map<String, List<LessonConfigBo>>
	 */
	private void setConfigTypeIndex(LessonConfigBo node, Map<String, List<LessonConfigBo>> boMap) {

		// 子节点为空不进行任何操作
		if (CollectionUtils.isEmpty(boMap.get(node.getResourceId()))) {
			return;
		}

		// 设置type_index 章节
		if (CollectionUtils.isNotEmpty(boMap.get(node.getResourceId()))) {

			// 初始化章节和课时索引
			Integer chapterTypeIndex = 1;
			Integer lessonTypeIndex = 1;

			for (LessonConfigBo nodeChild : boMap.get(node.getResourceId())) {
				if (nodeChild.getNodeType().intValue() == LessonConfig.NodeType.NODE_CHAPTER
						.getCode().intValue()) {
					nodeChild.setTypeIndex(null != node.getTypeIndex()
							? node.getTypeIndex() + CommonConstant.PERIOD + chapterTypeIndex
							: chapterTypeIndex.toString());
					chapterTypeIndex++;
				}

				if (nodeChild.getNodeType().intValue() == LessonConfig.NodeType.NODE_LESSON
						.getCode().intValue()) {
					nodeChild.setTypeIndex(null != node.getTypeIndex()
							? node.getTypeIndex() + CommonConstant.PERIOD + lessonTypeIndex
							: lessonTypeIndex.toString());
					lessonTypeIndex++;
				}

				setConfigTypeIndex(nodeChild, boMap);
			}

			node.setChildren(boMap.get(node.getResourceId()));
		}
	}

	/**
	 * CAL 视频进度
	 *
	 * @param configJson    配置 JSON
	 * @param watchedPoints 观看点数
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Set<Integer>
	 * @see Integer
	 */
	private Integer calVideoProgress(String configJson, Set<Integer> watchedPoints) {
		if (StringUtils.isBlank(configJson)) {
			// not configured
			return 0;
		}
		VideoCheckPointConfig config = JsonUtil.toJsonObject(configJson,
				VideoCheckPointConfig.class);
		Set<Integer> points = Sets.newHashSet();
		for (VideoCheckPointItem point : config.getPoints()) {
			points.add(point.getTime());
		}
		for (VideoCheckPointItem customPoint : config.getCustomPoints()) {
			points.add(customPoint.getTime());
		}
		int watchedCnt = 0;
		for (Integer point : points) {
			if (watchedPoints.contains(point)) {
				watchedCnt++;
			}
		}
		return MathUtils.roundDivide(watchedCnt * 100, points.size());
	}

}
