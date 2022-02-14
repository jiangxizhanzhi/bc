package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.SystemConfigService;
import com.cyberpeace.oj.course.service.ClassroomGroupInfoService;
import com.cyberpeace.oj.ojbase.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.service.bo.MonitorInfoBo;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.LessonSceneService;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Map;

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
	private SystemConfigService systemConfigService;

	@Override
	public JSONObject startScene(String lessonId, AuthContext authContext) {

		// 场景启动数据校验
		ojLocalSceneService.checkSceneStartCount(authContext.getUserId(), CommonConstant.ONE);

		// 先查询课程表场景
		ClassroomGroupInfo groupInfo = classroomGroupInfoService.selectByLessonAndUser(lessonId,
				authContext);
		if (null != groupInfo) {
			OjLocalScene groupScene = ojLocalSceneMapper
					.selectGroupScene(groupInfo.getResourceId());
			if (null != groupScene) {
				return ojLocalSceneService.getSceneData(groupScene);
			}
		}

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
			throw new InvalidParamException("场景启动失败！");
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
		return ojLocalSceneService.deleteLocalAndRemoteScene(ojLocalScene);
	}

	@Override
	public Boolean restartNode(String lessonId, String nodeId, AuthContext authContext) {
		OjLocalScene ojLocalScene = nodeOperationCheck(lessonId, authContext);
		return localSceneService.restartNode(ojLocalScene.getLocalSceneId(), nodeId);
	}

	@Override
	public Boolean recreateNode(String lessonId, String nodeId, AuthContext authContext) {
		OjLocalScene ojLocalScene = nodeOperationCheck(lessonId, authContext);
		return localSceneService.recreateNode(ojLocalScene.getLocalSceneId(), nodeId);
	}

	@Override
	public Boolean updateSceneStatus(String resourceId, Integer status, AuthContext authContext) {

		OjLocalScene ojLocalScene = nodeOperationCheck(resourceId, authContext);
		if (status.intValue() == LocalScene.SceneStatusEnum.PAUSE.getCode().intValue()) {
			return localSceneService.pauseScene(ojLocalScene.getLocalSceneId());
		} else {
			return localSceneService.unpauseScene(ojLocalScene.getLocalSceneId());
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
	public OjLocalScene nodeOperationCheck(String lessonId, AuthContext authContext) {

		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
		if (null == courseLesson) {
			log.error("Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 优先查询组数据场景
		ClassroomGroupInfo groupInfo = classroomGroupInfoService.selectByLessonAndUser(lessonId,
				authContext);
		if (groupInfo != null) {
			OjLocalScene ojLocalScene = ojLocalSceneMapper
					.selectGroupScene(groupInfo.getResourceId());
			if (null != ojLocalScene) {
				return ojLocalScene;
			}
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
				.businessType(LocalScene.BusinessCode.LESSON.getCode())
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
		localScene.setBusinessCode(LocalScene.BusinessCode.LESSON.getCode());
		localScene.setTopoId(courseLesson.getTopoId());
		localScene.setSceneTtl(courseLesson.getDestroyDelay() * CommonConstant.SIXTY);
		localScene.setUserId(authContext.getUserId());
		localScene.setSceneAttr(courseLesson.getSceneShareMode().intValue() == SceneShare.SHARED
				.getCode().intValue() ? SceneStartContext.SceneAttrEnum.NULL.getCode()
						: SceneStartContext.SceneAttrEnum.SINGLE.getCode());
		localScene.setRecord(Integer.parseInt(systemConfigService
				.getSystemConfig(JsonKeyConstant.SCREEN_RECORDING)) == CommonConstant.ONE);

		// 设置初始化信息
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
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
		topoData.put(JsonKeyConstant.REFERENCE_TYPE, LocalScene.BusinessCode.LESSON.getCode());
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
