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
 * @version 1.0.0
 * @date 2024/11/08
 * @see LessonPracticeSceneService
 * @since 1.0.0
 */
/**
 * 这段代码是一个Java类`LessonPracticeServiceImpl`，实现了`LessonPracticeSceneService`接口。该类主要负责处理与课程实践相关的场景操作，包括启动、删除、重启节点、重新创建节点、更新场景状态以及获取拓扑场景等。以下是代码的主要组成部分和功能： 
 * ### 主要成员变量 
 * - `OjLocalSceneService`, `PracticeOperationRefMapper`, `CourseLessonMapper`, `RemoteTopoService`, `LocalSceneService`, `TransactionTemplate`, `OjLocalSceneMapper`, `LocalSceneMapper`, `RemoteSceneService`, `PushMessageHandler`: 这些是通过Spring注入的依赖，用于执行具体的业务逻辑。 
 * ### 主要方法 
 * 1. **startScene** 
 * - 功能：启动一个场景。 
 * - 参数：`lessonId`, `practiceId`, `authContext`。 
 * - 返回值：场景数据的JSON对象。 
 * - 流程： 
 * - 验证课程和实践操作。 
 * - 检查场景启动次数。 
 * - 初始化并保存OjLocalScene和LocalScene。 
 * - 启动场景并返回场景数据。 
 * 2. **deleteScene** 
 * - 功能：删除一个场景。 
 * - 参数：`lessonId`, `practiceId`, `authContext`。 
 * - 返回值：布尔值表示是否删除成功。 
 * - 流程： 
 * - 验证课程和实践操作。 
 * - 删除本地和远程场景，并推送删除消息。 
 * 3. **restartNode** 
 * - 功能：重启节点。 
 * - 参数：`lessonId`, `practiceId`, `nodeId`, `authContext`。 
 * - 返回值：布尔值表示是否重启成功。 
 * - 流程： 
 * - 验证课程和实践操作。 
 * - 检查场景节点并重启节点。 
 * 4. **recreateNode** 
 * - 功能：重新创建节点。 
 * - 参数：`lessonId`, `practiceId`, `nodeId`, `authContext`。 
 * - 返回值：布尔值表示是否重建成功。 
 * - 流程： 
 * - 验证课程和实践操作。 
 * - 检查场景节点并重新创建节点。 
 * 5. **updateSceneStatus** 
 * - 功能：更新场景状态（暂停或恢复）。 
 * - 参数：`lessonId`, `practiceId`, `status`, `authContext`。 
 * - 返回值：布尔值表示是否更新成功。 
 * - 流程： 
 * - 验证课程和实践操作。 
 * - 根据状态值调用相应的暂停或恢复场景方法。 
 * 6. **getTopoScene** 
 * - 功能：获取拓扑场景数据。 
 * - 参数：`lessonId`, `practiceId`, `authContext`。 
 * - 返回值：包含拓扑信息和场景数据的`TopoSceneDataBo`对象。 
 * - 流程： 
 * - 验证课程和实践操作。 
 * - 获取并构建拓扑和场景数据。 
 * ### 辅助方法 
 * - `validateLesson`: 验证课程是否存在。 
 * - `validateOperation`: 验证实践操作是否存在。 
 * - `getBusinessId`: 获取业务ID。 
 * - `initLessonPracticeOjLocalScene`: 初始化OjLocalScene。 
 * - `initLocalScene`: 初始化LocalScene。 
 * - `sceneNodeCheck`: 检查场景节点是否存在。 
 * ### 总结 
 * 这个类通过依赖注入的方式使用了多个服务和映射器来处理与课程实践相关的各种场景操作。每个主要方法都有详细的验证和数据初始化步骤，确保操作的安全性和一致性。 
**/
@Slf4j
@Service
public class LessonPracticeServiceImpl implements LessonPracticeSceneService {

	/**
	 * OJ当地场景Service
	 *
	 * @see OjLocalSceneService
	 */
	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	/**
	 * 实践实操裁判Mapper
	 *
	 * @see PracticeOperationRefMapper
	 */
	@Autowired
	private PracticeOperationRefMapper practiceOperationRefMapper;

	/**
	 * 课程课Mapper
	 *
	 * @see CourseLessonMapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 远程拓扑Service
	 *
	 * @see RemoteTopoService
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 当地场景Service
	 *
	 * @see LocalSceneService
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 事务模板
	 *
	 * @see TransactionTemplate
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * OJ当地场景Mapper
	 *
	 * @see OjLocalSceneMapper
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	/**
	 * 当地场景Mapper
	 *
	 * @see LocalSceneMapper
	 */
	@Autowired
	private LocalSceneMapper localSceneMapper;

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 推送消息处理程序
	 *
	 * @see PushMessageHandler
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
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see JSONObject
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
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
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
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
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
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
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
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see AuthContext
	 * @see Boolean
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
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see TopoSceneDataBo
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
	 * @throws
	 * @see String
	 * @see CourseLesson
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
	 * @throws
	 * @see String
	 * @see CourseLesson
	 * @see PracticeOperation
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
	 * @throws
	 * @see CourseLesson
	 * @see PracticeOperation
	 * @see String
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
	 * @throws
	 * @see CourseLesson
	 * @see PracticeOperation
	 * @see AuthContext
	 * @see OjLocalScene
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
	 * @throws
	 * @see CourseLesson
	 * @see PracticeOperation
	 * @see OjLocalScene
	 * @see AuthContext
	 * @see LocalScene
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
	 * @throws
	 * @see CourseLesson
	 * @see PracticeOperation
	 * @see AuthContext
	 * @see LocalScene
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

