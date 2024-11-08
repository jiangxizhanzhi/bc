package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.LessonPracticeSceneService;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.ojbase.service.context.InitOjSceneContext;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperationRef;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationRefMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 课实践Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class LessonPracticeServiceImpl implements LessonPracticeSceneService {

	/**
	 * OJ当地场景Service
	 */
	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	/**
	 * 实践实操裁判Mapper
	 */
	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	/**
	 * 课程课Mapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * OJ当地场景Mapper
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	/**
	 * 当地场景Mapper
	 */
	@Autowired
	private LocalSceneMapper localSceneMapper;

	/**
	 * 远程场景Service
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 推送消息处理程序
	 */
	@Autowired
	private PushMessageHandler pushMessageHandler;

	/**
	 * 开始场景
	 *
	 * @param lessonId    课id
	 * @param practiceId  实践id
	 * @param authContext auth 上下文
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject startScene(String lessonId, String practiceId, AuthContext authContext) {

		CourseLesson lesson = validateLesson(lessonId);
		PracticeOperation operation = validateOperation(practiceId, lesson);

		// 场景启动数据校验
		ojLocalSceneService.checkSceneStartCount(authContext.getUserId(), CommonConstant.ONE);
		String businessId = getBusinessId(lesson, operation);
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(businessId,
				operation.getSceneShareMode(), authContext);
		if (null != ojLocalScene) {
			return ojLocalSceneService.getSceneData(ojLocalScene);
		}

		// 初始化ojlocalscene
		OjLocalScene initOjLocalScene = initLessonPracticeOjLocalScene(lesson, operation,
				authContext);
		// 初始化localScene
		LocalScene localScene = initLocalScene(lesson, operation, initOjLocalScene, authContext);
		initOjLocalScene.setLocalSceneId(localScene.getResourceId());

		String sceneId = localSceneService.startScene(localScene);
		localScene.setMpSceneId(sceneId);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				ojLocalSceneMapper.insert(initOjLocalScene);
				localSceneMapper.insert(localScene);
			}
		});

		return remoteSceneService.getById(sceneId, authContext.getUserId());
	}

	/**
	 * 删除场景
	 *
	 * @param lessonId    课id
	 * @param practiceId  实践id
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean deleteScene(String lessonId, String practiceId, AuthContext authContext) {

		CourseLesson courseLesson = validateLesson(lessonId);
		PracticeOperation operation = validateOperation(practiceId, courseLesson);
		if (!operation.getDynamicEnv() || StringUtils.isBlank(operation.getTopoId())) {
			log.error(
					"Lesson practice delete failed! operation not scene! continue! lesson_id=[{}], practice_id=[{}]",
					lessonId, practiceId);
			return CommonConstant.TRUE;
		}

		String businessId = getBusinessId(courseLesson, operation);
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(businessId,
				operation.getSceneShareMode(), authContext);
		ojLocalSceneService.deleteLocalAndRemoteScene(ojLocalScene);
		pushMessageHandler.pushLessonPracticeMessage(pushMessageHandler.getDeleteMessage(),
				ojLocalScene);

		return CommonConstant.TRUE;
	}

	/**
	 * 重启节点
	 *
	 * @param lessonId    课id
	 * @param practiceId  实践id
	 * @param nodeId      节点id
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean restartNode(String lessonId, String practiceId, String nodeId,
			AuthContext authContext) {
		CourseLesson courseLesson = validateLesson(lessonId);
		PracticeOperation operation = validateOperation(practiceId, courseLesson);

		LocalScene localScene = sceneNodeCheck(courseLesson, operation, authContext);
		return localSceneService.restartNode(localScene.getResourceId(), nodeId);
	}

	/**
	 * 重新创建节点
	 *
	 * @param lessonId    课id
	 * @param practiceId  实践id
	 * @param nodeId      节点id
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean recreateNode(String lessonId, String practiceId, String nodeId,
			AuthContext authContext) {
		CourseLesson courseLesson = validateLesson(lessonId);
		PracticeOperation operation = validateOperation(practiceId, courseLesson);

		LocalScene localScene = sceneNodeCheck(courseLesson, operation, authContext);
		return localSceneService.recreateNode(localScene.getResourceId(), nodeId);
	}

	/**
	 * 更新场景状态
	 *
	 * @param lessonId    课id
	 * @param practiceId  实践id
	 * @param status      状态
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean updateSceneStatus(String lessonId, String practiceId, Integer status,
			AuthContext authContext) {
		CourseLesson courseLesson = validateLesson(lessonId);
		PracticeOperation operation = validateOperation(practiceId, courseLesson);

		LocalScene localScene = sceneNodeCheck(courseLesson, operation, authContext);
		if (status.intValue() == LocalScene.SceneStatusEnum.PAUSE.getCode().intValue()) {
			return localSceneService.pauseScene(localScene.getResourceId());
		} else {
			return localSceneService.unpauseScene(localScene.getResourceId());
		}
	}

	/**
	 * 获取拓扑场景
	 *
	 * @param lessonId    课id
	 * @param practiceId  实践id
	 * @param authContext auth 上下文
	 * @return {@code TopoSceneDataBo }
	 */
	@Override
	public TopoSceneDataBo getTopoScene(String lessonId, String practiceId,
			AuthContext authContext) {

		CourseLesson courseLesson = validateLesson(lessonId);
		PracticeOperation operation = validateOperation(practiceId, courseLesson);

		String businessId = getBusinessId(courseLesson, operation);
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(businessId,
				operation.getSceneShareMode(), authContext);

		TopoSceneDataBo sceneDataBo = new TopoSceneDataBo();
		// 拓扑信息
		JSONObject topo = remoteTopoService.getTopoById(operation.getTopoId());
		sceneDataBo.setSceneConfigData(topo);

		// 场景信息
		if (null != ojLocalScene) {
			JSONObject sceneData = ojLocalSceneService.getSceneData(ojLocalScene);
			sceneDataBo.setSceneData(sceneData);
		}

		return sceneDataBo;
	}

	/**
	 * 课时数据校验
	 *
	 * @param lessonId
	 * @return
	 */
	private CourseLesson validateLesson(String lessonId) {
		CourseLesson lesson = courseLessonMapper.selectById(lessonId);
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return lesson;
	}

	/**
	 * 课后练习习题校验
	 *
	 * @param practiceId
	 * @param courseLesson 课程课程
	 * @return
	 */
	private PracticeOperation validateOperation(String practiceId, CourseLesson courseLesson) {
		PracticeOperationRef operationRef = practiceOperationRefMapper
				.selectByPracticeId(courseLesson.getPaperId(), practiceId);

		if (null == operationRef) {
			log.error("PracticeOperationRef not found! paper_id=[{}], practice_id=[{}]",
					courseLesson.getPaperId(), practiceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return JsonUtil.toJsonObject(operationRef.getSnapshot(), PracticeOperation.class);
	}

	/**
	 * 获取课后练习业务编号
	 *
	 * @param lesson
	 * @param operation
	 * @return
	 */
	private String getBusinessId(CourseLesson lesson, PracticeOperation operation) {
		return lesson.getResourceId() + CommonConstant.CODE_COMMA + operation.getResourceId();
	}

	/**
	 * 初始化课后练习场景信息
	 *
	 * @param lesson
	 * @param operation
	 * @param authContext
	 * @return
	 */
	private OjLocalScene initLessonPracticeOjLocalScene(CourseLesson lesson,
			PracticeOperation operation, AuthContext authContext) {

		String businessId = getBusinessId(lesson, operation);

		InitOjSceneContext ojSceneContext = InitOjSceneContext.builder()
				.sharedType(operation.getSceneShareMode()).dynamicFlag(operation.getDynamicFlag())
				.userId(authContext.getUserId()).fromWeb(authContext.getIsWeb())
				.businessId(businessId)
				.businessType(LocalScene.BusinessCode.LESSON_PRACTICE.getCode())
				.flags(operation.getAnswer()).build();

		return ojLocalSceneService.initOjLocalScene(ojSceneContext);
	}

	/**
	 * 初始化中台场景信息
	 *
	 * @param lesson
	 * @param operation
	 * @param ojLocalScene
	 * @param authContext
	 * @return
	 */
	private LocalScene initLocalScene(CourseLesson lesson, PracticeOperation operation,
			OjLocalScene ojLocalScene, AuthContext authContext) {

		LocalScene localScene = new LocalScene();
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setBusinessInstanceId(ojLocalScene.getResourceId());
		localScene.setDestroyMode(LocalScene.DestroyMode.AUTO.getCode());
		localScene.setBusinessCode(LocalScene.BusinessCode.LESSON_PRACTICE.getCode());

		localScene.setUserId(authContext.getUserId());
		localScene.setSceneTtl(operation.getDestroyDelay() * 60);
		localScene.setSceneName(operation.getTitle());
		localScene.setTopoId(operation.getTopoId());
		localScene.setSceneAttr(
				operation.getSceneShareMode().intValue() == SceneShare.SHARED.getCode()
						? SceneStartContext.SceneAttrEnum.NULL.getCode()
						: SceneStartContext.SceneAttrEnum.SINGLE.getCode());

		// 设置场景类型
		JSONObject topoData = remoteTopoService.getTopoById(operation.getTopoId());
		topoData.put(JsonKeyConstant.REFERENCE_TYPE,
				LocalScene.BusinessCode.LESSON_PRACTICE.getCode());

		// 动态环境flag设置
		String appResourceId = lesson.getCourseId() + CommonConstant.LEFT_OBLIQUE_LINE
				+ lesson.getResourceId() + CommonConstant.LEFT_OBLIQUE_LINE + lesson.getPaperId();
		LocalSceneMeta sceneMeta = LocalSceneMeta.builder().appResourceId(appResourceId)
				.isWeb(ojLocalScene.getFromWeb()).topoData(topoData).build();

		if (null != operation.getDynamicFlag() && operation.getDynamicFlag()
				.intValue() == PracticeOperation.DynamicFlag.DYNAMIC.getCode()) {
			JSONObject dynamicFlag = ojLocalSceneService.setDynamicFlag(ojLocalScene, topoData);
			sceneMeta.setTopoData(dynamicFlag);
		}
		localScene.setMeta(JsonUtil.toJsonString(sceneMeta));
		return localScene;

	}

	/**
	 * 节点数据校验
	 *
	 * @param lesson
	 * @param operation
	 * @param authContext auth 上下文
	 * @return
	 */
	private LocalScene sceneNodeCheck(CourseLesson lesson, PracticeOperation operation,
			AuthContext authContext) {

		String businessId = getBusinessId(lesson, operation);
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(businessId,
				operation.getSceneShareMode(), authContext);
		if (null == ojLocalScene) {
			log.error(
					"OjLocalScene not found! operation_id=[{}], shared=[{}], fromWeb=[{}], user_id=[{}]",
					operation.getResourceId(), operation.getSceneShareMode(),
					authContext.getIsWeb(), authContext.getUserId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		if (null == localScene || StringUtils.isBlank(localScene.getMpSceneId())) {
			log.error("LocalScene not found ot scene_id is null! local_scene_id=[{}]",
					ojLocalScene.getLocalSceneId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return localScene;
	}
}
