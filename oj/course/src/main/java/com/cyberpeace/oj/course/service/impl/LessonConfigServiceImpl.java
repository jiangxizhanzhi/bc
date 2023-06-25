package com.cyberpeace.oj.course.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.course.controller.res.WebContextInfoStatusRes;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
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
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonConfigMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.LessonConfigService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

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

	@Autowired
	private CourseValidateHandler courseValidateHandler;

	@Autowired
	private RemoteTopoService remoteTopoService;

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

	@Override
	public LessonConfigBo updateLessonConfig(LessonConfigBo configBo) {

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
	 * 重新编排目录数据 重置index
	 * @param configBo
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

	@Override
	public LessonConfigDumpBo dumpLessonConfig(String courseId, AuthContext authContext) {
		LessonConfigBo lessonConfig = getLessonConfig(courseId, authContext);
		return configToDumpBo(lessonConfig);
	}

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
	 * 将课时编排的树形数据展开
	 * @param lessonConfigBo
	 * @return
	 */
	@Override
	public List<LessonConfigBo> expandLessonConfig(LessonConfigBo lessonConfigBo) {
		List<LessonConfigBo> lessonConfigBos = new ArrayList<>();
		if(lessonConfigBo == null) {
			return lessonConfigBos;
		}

		// 不管第一层的课程数据，直接将课时找出来
		List<LessonConfigBo> lessonConfigs = lessonConfigBo.getChildren();
		if(CollectionUtils.isEmpty(lessonConfigs)) {
			return lessonConfigBos;
		}

		for(LessonConfigBo lessonConfig : lessonConfigs) {
			addLessonConfigChildren(lessonConfig, lessonConfigBos);

		}

		return lessonConfigBos;
	}

	@Override
	public WebContextInfoStatusRes lessonIdOfExpandedLessonConfigs(int navigate,
																   String lessonId,
																   List<LessonConfigBo> lessonConfigBos,
																   AuthContext authContext,
																   String startLessonId) {
		WebContextInfoStatusRes webContextInfoStatusRes = null;
		if(StringUtils.isEmpty(lessonId) || CollectionUtils.isEmpty(lessonConfigBos)) {
			return webContextInfoStatusRes;
		}

		LessonConfigBo wantedPrev = null;
		LessonConfigBo wantedNext = null;
		LessonConfigBo wanted = null;
		int i = -1;
		for(LessonConfigBo lessonConfigBo : lessonConfigBos) {
			i ++;
			String dbLessonId = lessonConfigBo.getLessonId();
			if(StringUtils.isEmpty(lessonId) || StringUtils.isEmpty(dbLessonId)) {
				continue;
			}
			if(!lessonId.equals(dbLessonId)) {
				continue;
			}
			wanted = lessonConfigBo;
			if(i != 0) {
				wantedPrev = lessonConfigBos.get(i - 1);
			}
			if(i != lessonConfigBos.size() - 1) {
				wantedNext = lessonConfigBos.get(i + 1);
			}

			break;
		}
		if(wanted == null) {
			return webContextInfoStatusRes;
		}
		webContextInfoStatusRes = new WebContextInfoStatusRes();
		if(wantedPrev != null) {
			webContextInfoStatusRes.setPrevFlg(1);
			webContextInfoStatusRes.setPrev(buildContextInfoStatus(wantedPrev, authContext));
		}
		if(wantedNext != null) {
			webContextInfoStatusRes.setNextFlg(1);
			webContextInfoStatusRes.setNext(buildContextInfoStatus(wantedNext, authContext));
		}
		webContextInfoStatusRes.setCurr(buildContextInfoStatus(wanted, authContext));

		// navigate == 1证明从上一题切到了当前题目
		if(navigate == 1) {
			// 校验题型
			assertSameLessonType(webContextInfoStatusRes.getPrev(), webContextInfoStatusRes.getCurr());
			// 校验场景
			assertSameLessonScene(webContextInfoStatusRes.getPrev(), webContextInfoStatusRes.getCurr(), startLessonId);
		}
		// navigate == -1证明从下一题切到了当前题目
		if(navigate == -1) {
			assertSameLessonType(webContextInfoStatusRes.getNext(), webContextInfoStatusRes.getCurr());
			assertSameLessonScene(webContextInfoStatusRes.getNext(), webContextInfoStatusRes.getCurr(), startLessonId);
		}


		return webContextInfoStatusRes;
	}

	/**
	 * 校验两个课时的场景是否同一个
	 * @param c1
	 * @param curr
	 */
	private void assertSameLessonScene(ContextInfoStatus c1, ContextInfoStatus curr, String startLessonId) {
		if(c1 == null || curr == null) {
			log.error("lesson configs not found!");
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// 如果切回第一课时则不需要校验
		if(c1.getCourseId().equals(startLessonId)) {
			return;
		}
		// 如果切到其他课时，必须确保其他课时的场景为空
		CourseLesson lesson = c1.getLesson();
		// 查询课时场景信息
		if (lesson.getType().intValue() == CourseLesson.LessonType.EXPERIMENT.getCode().intValue()
				&& StringUtils.isNotBlank(lesson.getTopoId())) {

			// 拓扑信息查询
			JSONObject topoData = remoteTopoService.getTopoById(lesson.getTopoId());
			if(topoData == null || topoData.isEmpty()) {
				return;
			}
			JSONObject sceneConfig = topoData.getJSONObject("scene_config");
			if(sceneConfig == null || sceneConfig.isEmpty()) {
				return;
			}
			JSONObject topology = sceneConfig.getJSONObject("topology");
			if(topology == null || topology.isEmpty()) {
				return;
			}
			JSONArray areas = topology.getJSONArray("areas");
			JSONArray connec = topology.getJSONArray("areas");
			JSONArray elements = topology.getJSONArray("elements");
			JSONArray pieces = topology.getJSONArray("pieces");
			if(CollectionUtils.isEmpty(areas) && CollectionUtils.isEmpty(connec) &&
				CollectionUtils.isEmpty(elements) && CollectionUtils.isEmpty(pieces)) {
				return;
			}

			log.error("new lesson scene used! lesson_id=[{}]", lesson.getResourceId());
			throw new BusinessException(ErrorCode.USE_NEW_SCENE);
		}
	}

	/**
	 * 校验两个课时的类型
	 * @param c1
	 * @param curr
	 */
	private void assertSameLessonType(ContextInfoStatus c1, ContextInfoStatus curr) {
		if(c1 == null || curr == null) {
			log.error("lesson configs not found!");
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		if(c1.getLessonType() != curr.getLessonType()) {
			log.error("lesson type is not same!");
			throw new BusinessException(ErrorCode.LESSON_TYPE_NOT_SAME);
		}
	}

	private ContextInfoStatus buildContextInfoStatus(LessonConfigBo lessonConfigBo, AuthContext authContext) {
		if(lessonConfigBo == null) {
			return null;
		}
		ContextInfoStatus contextInfoStatus = new ContextInfoStatus();
		contextInfoStatus.setLessonId(lessonConfigBo.getLessonId());
		contextInfoStatus.setCourseId(lessonConfigBo.getCourseId());
		contextInfoStatus.setLessonType(lessonConfigBo.getType());
		CourseLesson lesson = courseValidateHandler.validateLessonPermission(lessonConfigBo.getLessonId(), authContext);
		contextInfoStatus.setHandoutFlg(StringUtils.isEmpty(lesson.getHandoutUrl()) ? 0 : 1);
		contextInfoStatus.setLesson(lesson);
		return contextInfoStatus;
	}

	/**
	 * 递归展开子节点
	 * @param lessonConfigBo 孩子
	 * @param lessonConfigs 要组装的结果
	 */
	private void addLessonConfigChildren(LessonConfigBo lessonConfigBo, List<LessonConfigBo> lessonConfigs) {
		if(lessonConfigBo == null) {
			return;
		}
		lessonConfigs.add(lessonConfigBo);
		List<LessonConfigBo> children = lessonConfigBo.getChildren();
		if(CollectionUtils.isEmpty(children)) {
			return;
		}
		for(LessonConfigBo child : children) {
			addLessonConfigChildren(child, lessonConfigs);
		}
	}

	/**
	 * 数据转换
	 * @param configDumpBos
	 * @param courseId
	 * @param lessonNameMap
	 * @return
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
	 * @param lessonConfigBo
	 * @return
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
				configBo.setCompulsory(lesson.getCompulsory());
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

}
