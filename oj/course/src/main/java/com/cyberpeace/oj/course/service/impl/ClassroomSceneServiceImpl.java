package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.ojbase.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
@Service
public class ClassroomSceneServiceImpl implements ClassroomSceneService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Override
	public JSONObject startScene(String groupId, AuthContext authContext) {

		// 数据校验
		ClassroomGroupInfo groupInfo = validateGroup(groupId);
		CourseLesson courseLesson = validateLesson(groupInfo.getLessonId());
		ojLocalSceneService.checkSceneStartCount(authContext.getUserId(), CommonConstant.ONE);

		// 不为空返回已存在场景
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(groupId,
				SceneShare.GROUP.getCode(), authContext);
		if (null != ojLocalScene) {
			JSONObject sceneData = ojLocalSceneService.getSceneData(ojLocalScene);
			return sceneData;
		}

		// 数据初始化
		OjLocalScene initOjLocalScene = initOjLocalScene(groupInfo, authContext);
		LocalScene localScene = initLocalScene(groupInfo, courseLesson, initOjLocalScene,
				authContext);
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

		return ojLocalSceneService.getSceneData(initOjLocalScene);
	}

	@Override
	public Boolean deleteScene(String groupId, AuthContext authContext) {

		// 本地场景数据不存在 则删除
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(groupId,
				SceneShare.GROUP.getCode(), authContext);
		if (null == ojLocalScene) {
			return CommonConstant.TRUE;
		}

		// 远程场景删除
		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		localSceneService.deleteRemoteScene(localScene);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				ojLocalSceneMapper.deleteById(ojLocalScene);
				localSceneMapper.deleteById(localScene);
			}
		});

		return CommonConstant.TRUE;
	}

	/**
	 * 初始化中台场景信息
	 * @param lesson
	 * @param groupInfo
	 * @param ojLocalScene
	 * @param authContext
	 * @return
	 */
	private LocalScene initLocalScene(ClassroomGroupInfo groupInfo, CourseLesson lesson,
			OjLocalScene ojLocalScene, AuthContext authContext) {

		LocalScene localScene = new LocalScene();
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setUserId(authContext.getUserId());
		localScene.setBusinessInstanceId(ojLocalScene.getResourceId());
		localScene.setDestroyMode(LocalScene.DestroyMode.AUTO.getCode());
		localScene.setTopoId(groupInfo.getTopoId());

		localScene.setBusinessCode(LocalScene.BusinessCode.CLASSROOM.getCode());
		localScene.setSceneName(
				lesson.getName() + CommonConstant.UNDER_LINE + groupInfo.getGroupNo().toString());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
		localScene.setSceneTtl(groupInfo.getDestroyDelay() * CommonConstant.SIXTY);

		// 设置场景类型
		JSONObject topoData = remoteTopoService.getTopoById(groupInfo.getTopoId());
		topoData.put(JsonKeyConstant.REFERENCE_TYPE, LocalScene.BusinessCode.CLASSROOM.getCode());

		String appResourceId = lesson.getCourseId() + CommonConstant.LEFT_OBLIQUE_LINE
				+ lesson.getResourceId();
		LocalSceneMeta sceneMeta = LocalSceneMeta.builder().appResourceId(appResourceId)
				.topoData(topoData).build();

		localScene.setMeta(JsonUtil.toJsonString(sceneMeta));
		return localScene;
	}

	/**
	 * 初始化组场景信息
	 * @param groupInfo
	 * @param authContext
	 * @return
	 */
	private OjLocalScene initOjLocalScene(ClassroomGroupInfo groupInfo, AuthContext authContext) {
		OjLocalScene ojLocalScene = new OjLocalScene();
		ojLocalScene.setResourceId(IDGeneratorUtil.getID());
		ojLocalScene.setUserId(authContext.getUserId());

		// 组内场景暂定为均为共享 无用字段
		ojLocalScene.setShared(CommonConstant.TRUE);

		// 组内场景暂定均为前台启动(组内启动场景仅前台可以看到) 无用字段
		ojLocalScene.setFromWeb(CommonConstant.TRUE);
		ojLocalScene.setBusinessId(groupInfo.getResourceId());
		ojLocalScene.setBusinessType(LocalScene.BusinessCode.CLASSROOM.getCode());

		// 无用字段 数据库字段尽量不为空
		ojLocalScene.setFlags(JsonUtil.arrayToString(Lists.newArrayList()));

		return ojLocalScene;
	}

	/**
	 * 数据存在校验
	 * @param classroomId
	 * @return
	 */
	private Classroom validateClassroom(String classroomId) {
		Classroom classroom = classroomMapper.selectById(classroomId);

		if (null == classroom) {
			log.error("Classroom not found! classroom_id=[{}]", classroomId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return classroom;
	}

	/**
	 * 数据存在校验
	 * @param groupId
	 * @return
	 */
	private ClassroomGroupInfo validateGroup(String groupId) {
		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper.selectById(groupId);

		if (null == groupInfo) {
			log.error("ClassroomGroupInfo not found! classroom_group_id=[{}]", groupId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return groupInfo;
	}

	/**
	 * 
	 * @param lessonId
	 * @return
	 */
	private CourseLesson validateLesson(String lessonId) {
		CourseLesson courseLesson = courseLessonMapper.selectById(lessonId);
		if (null == courseLesson) {
			log.error("Lesson not found! course_lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return courseLesson;
	}
}
