package com.cyberpeace.oj.course.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.SceneStatusEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomSignMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;
import com.cyberpeace.oj.course.service.ClassroomSignService;
import com.cyberpeace.oj.course.service.LessonSceneService;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LessonSceneServiceImpl implements LessonSceneService {

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private ClassroomGroupInfoService classroomGroupInfoService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	@Autowired
	private ClassroomSignService classroomSignService;

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Autowired
	private PushMessageHandler pushMessageHandler;

	@Override
	public JSONObject startScene(String lessonId, AuthContext authContext) {

		// 场景启动数据校验
		ojLocalSceneService.checkSceneStartCount(authContext.getUserId(), CommonConstant.ONE);

		// 先查询课程表场景
//		ClassroomGroupInfo groupInfo = classroomGroupInfoService.selectByLessonAndUser(lessonId, null,
//				authContext);
//		if (null != groupInfo) {
//			OjLocalScene groupScene = ojLocalSceneMapper
//					.selectGroupScene(groupInfo.getResourceId());
//			if (null != groupScene) {
//				return ojLocalSceneService.getSceneData(groupScene);
//			}
//		}

		// 无课程表场景 查询课时场景
		CourseLesson courseLesson = lessonSceneStartCheck(lessonId);
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(lessonId,
				courseLesson.getSceneShareMode(), authContext);

		if (null != ojLocalScene) {
			return ojLocalSceneService.getSceneData(ojLocalScene);
		}

		// 初始化ojlocalscene
		OjLocalScene initOjLocalScene = initLessonOjLocalScene(courseLesson, authContext);
		// 初始化localScene
		LocalScene localScene = initLocalScene(courseLesson, initOjLocalScene, authContext);
		initOjLocalScene.setLocalSceneId(localScene.getResourceId());

		// 场景启动
		String sceneId = authContext.getIsWeb() ? localSceneService.startScene(localScene)
				: localSceneService.testScene(localScene);
		if (StringUtils.isBlank(sceneId)) {
			log.error("startScene: Scene start failed! localScene=[{}]",
					JsonUtil.toJsonString(localScene));
			throw new BusinessException(OjBusinessErrorCode.SCENE_START_FAIL);
		}

		localScene.setMpSceneId(sceneId);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				localSceneMapper.insert(localScene);
				ojLocalSceneMapper.insert(initOjLocalScene);
			}
		});

		return ojLocalSceneService.getSceneData(initOjLocalScene);
	}

	@Override
	public Boolean deleteScene(String lessonId, AuthContext authContext) {
		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
		if (null == courseLesson) {
			log.error("deleteScene: Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(lessonId,
				courseLesson.getSceneShareMode(), authContext);
		ojLocalSceneService.deleteLocalAndRemoteScene(ojLocalScene);
		pushMessageHandler.pushLessonMessage(pushMessageHandler.getDeleteMessage(), ojLocalScene);

		return CommonConstant.TRUE;
	}

	@Override
	public Boolean restartNode(String lessonId, String nodeId, String roomId, AuthContext authContext) {
		OjLocalScene ojLocalScene = nodeOperationCheck(lessonId, roomId, authContext);
		localSceneService.restartNode(ojLocalScene.getLocalSceneId(), nodeId);

		// 清理远程协助信息
		clearAssistance(ojLocalScene);
		return CommonConstant.TRUE;
	}

	@Override
	public Boolean recreateNode(String lessonId, String nodeId, String roomId,  AuthContext authContext) {
		OjLocalScene ojLocalScene = nodeOperationCheck(lessonId, roomId, authContext);
		localSceneService.recreateNode(ojLocalScene.getLocalSceneId(), nodeId);

		// 清理远程协助信息
		clearAssistance(ojLocalScene);
		return CommonConstant.TRUE;
	}

	@Override
	public Boolean updateSceneStatus(String roomId, String resourceId, Integer status, AuthContext authContext) {

		OjLocalScene ojLocalScene = nodeOperationCheck(resourceId, roomId,  authContext);

		if (status.intValue() == SceneStatusEnum.PAUSE.getCode().intValue()) {

			// 场景暂停
			localSceneService.pauseScene(ojLocalScene.getLocalSceneId());
			clearAssistance(ojLocalScene);

			return CommonConstant.TRUE;
		} else {
			return localSceneService.unpauseScene(ojLocalScene.getLocalSceneId());
		}
	}

	/**
	 * 清除远程协助信息
	 * @param ojLocalScene
	 */
	private void clearAssistance(OjLocalScene ojLocalScene) {

		if (ojLocalScene.getBusinessType().intValue() != BusinessCode.CLASSROOM.getCode()
				.intValue()) {
			return;
		}

		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
				.selectById(ojLocalScene.getBusinessId());

		if (null != groupInfo && StringUtils.isNotBlank(groupInfo.getGroupUserIds())) {
			List<String> userIds = JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(),
					String.class);

			// 获取签到信息
			List<ClassroomSign> signList = StreamApiUtils.map(userIds, userId -> {
				ClassroomSign classroomSign = classroomSignMapper
						.selectByRoomAndUser(groupInfo.getClassroomId(), userId);
				if (null == classroomSign) {
					return null;
				}
				classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.NO_HELP.getCode());
				classroomSign.setNodeId(CommonConstant.EMPTY_STRING);
				return classroomSign;
			});

			// 清除远程协助数据
			classroomSignService.updateBatchById(signList);
		}
	}

	@Override
	public List<SceneMonitorBo> lessonMonitor(String lessonId) {

		// 查询前台用户启动的场景
		List<OjLocalScene> ojLocalScenes = ojLocalSceneMapper.selectByBusiness(lessonId, true);
		if (CollectionUtils.isEmpty(ojLocalScenes)) {
			return Lists.newArrayList();
		}

		return ojLocalSceneService.selectMonitorList(ojLocalScenes);
	}

	/**
	 * 节点操作前校验
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	public OjLocalScene nodeOperationCheck(String lessonId, String roomId, AuthContext authContext) {

		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
		if (null == courseLesson) {
			log.error("Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 优先查询组数据场景
		if (StringUtils.isNotBlank(roomId)) {
			ClassroomGroupInfo groupInfo = classroomGroupInfoMapper.selectByRoomAndUser(roomId,
					authContext.getUserId());
			if (null == groupInfo) {
				log.error("roomId group [{}] doesn't contains user [{}]", roomId, authContext.getUserId());
				throw new BusinessException(OjBusinessErrorCode.USER_CLASS_GROUP_NOT_FOUND);
			}
			
			OjLocalScene ojLocalScene = ojLocalSceneMapper
					.selectGroupScene(groupInfo.getResourceId());
			if (null == ojLocalScene) {
				log.error("Classroom scene is not running! group_id=[{}]",
						groupInfo.getResourceId());
				throw new BusinessException(OjBusinessErrorCode.SCENE_NOT_RUNNING);
			}
			return ojLocalScene;
		}

		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(lessonId,
				courseLesson.getSceneShareMode(), authContext);
		if (null == ojLocalScene) {
			log.error(
					"ojLocalScene not found! lesson_id=[{}], scene_share_mode=[{}], authContext=[{}]",
					lessonId, courseLesson.getSceneShareMode(), JsonUtil.toJsonString(authContext));
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return ojLocalScene;
	}

	/**
	 * 初始化ojLocalScene
	 * @param courseLesson
	 * @param authContext
	 * @return
	 */
	public OjLocalScene initLessonOjLocalScene(CourseLesson courseLesson, AuthContext authContext) {
		OjLocalScene ojLocalScene = OjLocalScene.builder().businessId(courseLesson.getResourceId())
				.businessType(BusinessCode.LESSON.getCode())
				.resourceId(IDGeneratorUtil.getID()).fromWeb(authContext.getIsWeb())
				.shared(courseLesson.getSceneShareMode().intValue() == SceneShare.SHARED.getCode()
						.intValue())
				.userId(authContext.getUserId()).build();

		return ojLocalScene;
	}

	/**
	 * 初始化localscene
	 * @param courseLesson
	 * @param ojLocalScene
	 * @param authContext
	 * @return
	 */
	public LocalScene initLocalScene(CourseLesson courseLesson, OjLocalScene ojLocalScene,
			AuthContext authContext) {
		LocalScene localScene = new LocalScene();
		// 设置场景基本信息
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setSceneName(courseLesson.getName());
		localScene.setBusinessCode(BusinessCode.LESSON.getCode());
		localScene.setTopoId(courseLesson.getTopoId());
		localScene.setSceneTtl(courseLesson.getDestroyDelay() * CommonConstant.SIXTY);
		localScene.setUserId(authContext.getUserId());
		localScene.setSceneAttr(courseLesson.getSceneShareMode().intValue() == SceneShare.SHARED
				.getCode().intValue() ? SceneStartContext.SceneAttrEnum.NULL.getCode()
						: SceneStartContext.SceneAttrEnum.SINGLE.getCode());

		// 设置初始化信息
		localScene.setSceneStatus(SceneStatusEnum.INIT.getCode());
		localScene.setDestroyMode(LocalScene.DestroyMode.AUTO.getCode());

		// 设置关联ojlocalscene信息
		localScene.setBusinessInstanceId(ojLocalScene.getResourceId());

		// 设置meta元数据值
		LocalSceneMeta localSceneMeta = new LocalSceneMeta();
		String appResourceId = courseLesson.getCourseId() + CommonConstant.LEFT_OBLIQUE_LINE
				+ courseLesson.getResourceId();
		localSceneMeta.setAppResourceId(appResourceId);
		localSceneMeta.setIsWeb(ojLocalScene.getFromWeb());

		// 设置大场景数据 部分课程为资源导入 无法设置reference_type
		JSONObject topoData = remoteTopoService.getTopoById(courseLesson.getTopoId());
		topoData.put(JsonKeyConstant.REFERENCE_TYPE, BusinessCode.LESSON.getCode());
		localSceneMeta.setTopoData(topoData);
		localScene.setMeta(JsonUtil.toJsonString(localSceneMeta));

		return localScene;
	}

	/**
	 * 场景启动前校验
	 * @param lessonId
	 */
	private CourseLesson lessonSceneStartCheck(String lessonId) {

		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
		if (null == courseLesson) {
			log.error("lessonSceneStartCheck: Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		if (StringUtils.isBlank(courseLesson.getTopoId())) {
			log.error("lessonSceneStartCheck: Topo not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return courseLesson;

	}
}
