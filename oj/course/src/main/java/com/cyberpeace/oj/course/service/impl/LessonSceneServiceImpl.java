package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.cs.service.LocalSceneService;
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
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.LessonSceneService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Service
public class LessonSceneServiceImpl implements LessonSceneService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public JSONObject startScene(String resourceId, AuthContext authContext) {

		// 先查询课程表场景
		LocalScene groupScene = getGroupScene(resourceId, authContext);
		if (null != groupScene) {
			return remoteSceneService.getById(groupScene.getMpSceneId(), groupScene.getUserId());
		}

		// 无课程表场景 查询课时场景
		CourseLesson courseLesson = lessonSceneStartCheck(resourceId);
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(resourceId,
				courseLesson.getSceneShareMode(), authContext);

		if (null != ojLocalScene) {
			LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
			if (null == localScene) {
				log.error(
						"startScene: ojLocalScene exist but localScene not found! oj_local_scene_id=[{}]",
						localScene.getResourceId());
				throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
			}

			return remoteSceneService.getById(localScene.getMpSceneId(), localScene.getUserId());
		}

		// 初始化ojlocalscene
		OjLocalScene initOjLocalScene = initLessonOjLocalScene(courseLesson, authContext);
		// 初始化localScene
		LocalScene localScene = initLocalScene(courseLesson, initOjLocalScene, authContext);
		ojLocalScene.setLocalSceneId(localScene.getResourceId());

		// 场景启动
		String sceneId = localSceneService.startScene(localScene);
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

		return remoteSceneService.getById(sceneId, authContext.getUserId());
	}

	@Override
	public Boolean deleteScene(String resourceId, AuthContext authContext) {
		CourseLesson courseLesson = courseLessonMapper.selectById(resourceId);
		if (null == courseLesson) {
			log.error("deleteScene: Lesson not found! lesson_id=[{}]", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(resourceId,
				courseLesson.getSceneShareMode(), authContext);
		if (null == ojLocalScene) {
			return true;
		}

		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		localSceneService.deleteRemoteScene(localScene);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				ojLocalSceneMapper.deleteById(ojLocalScene);
				localSceneMapper.deleteById(localScene);
			}
		});

		return true;
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

	/**
	 * 节点操作前校验
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	public OjLocalScene nodeOperationCheck(String lessonId, AuthContext authContext) {

		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
		if (null == courseLesson) {
			log.error("deleteScene: Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(lessonId,
				courseLesson.getSceneShareMode(), authContext);
		if (null == ojLocalScene) {
			log.error(
					"restartNode: ojLocalScene not found! lesson_id=[{}], scene_share_mode=[{}], authContext=[{}]",
					lessonId, courseLesson.getSceneShareMode(), JsonUtil.toJsonString(authContext));
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
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
		localScene.setSceneName(courseLesson.getName());
		localScene.setBusinessCode(LocalScene.BusinessCode.LESSON.getCode());
		localScene.setTopoId(courseLesson.getTopoId());
		localScene.setSceneTtl(courseLesson.getDestroyDelay() * CommonConstant.SIXTY);
		localScene.setUserId(authContext.getUserId());

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
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		if (StringUtils.isBlank(courseLesson.getTopoId())) {
			log.error("lessonSceneStartCheck: Topo not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		return courseLesson;

	}

	private LocalScene getGroupScene(String lessonId, AuthContext authContext) {

		if (authContext.getIsWeb()) {
			// 查询课堂信息
			Classroom classroom = classroomMapper
					.selectByOrgAndLesson(authContext.getOrganization(), lessonId);
			if (null == classroom) {
				return null;
			}

			// 查询分组信息
			ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
					.selectByRoomAndUser(classroom.getResourceId(), authContext.getUserId());
			if (null == groupInfo) {
				return null;
			}

			// 查询本地场景
			OjLocalScene ojLocalScene = ojLocalSceneMapper.selectByGroup(groupInfo.getResourceId());
			if (null != ojLocalScene || StringUtils.isBlank(ojLocalScene.getLocalSceneId())) {
				return null;
			}

			// 查询远程场景信息
			return localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		}

		return null;
	}
}
