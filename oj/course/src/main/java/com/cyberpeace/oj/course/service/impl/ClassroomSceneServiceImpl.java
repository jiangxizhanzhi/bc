package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import com.cyberpeace.oj.course.service.bo.LessonWsMessageIdentifier;
import com.cyberpeace.oj.course.service.bo.SimpleUserData;
import com.cyberpeace.oj.ojbase.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

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

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

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

		// 推送消息给课程的websocket 加载课堂场景
		toLessonSocketMessage(groupInfo.getGroupUserIds(), courseLesson, OjEvent.SCENE_CREATE_SYNC);

		return ojLocalSceneService.getSceneData(initOjLocalScene);
	}

	@Override
	public Boolean deleteScene(String groupId, AuthContext authContext) {

		// 基本数据校验
		ClassroomGroupInfo groupInfo = validateGroup(groupId);
		CourseLesson courseLesson = validateLesson(groupInfo.getLessonId());

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

		// 给前台课程websocket推送删除消息 重新加载课程场景
		toLessonSocketMessage(groupInfo.getGroupUserIds(), courseLesson, OjEvent.SCENE_DELETE_SYNC);
		return CommonConstant.TRUE;
	}

	@Override
	public List<GroupInfoBo> batchStartScene(String classroomId, AuthContext authContext)
			throws InterruptedException {

		// 基本数据校验
		Classroom classroom = validateClassroom(classroomId);
		validateLesson(classroom.getLessonId());

		// 组信息查询
		List<ClassroomGroupInfo> groupInfoList = classroomGroupInfoMapper.selectByRoom(classroomId);
		List<String> groupIds = StreamApiUtils.map(groupInfoList,
				ClassroomGroupInfo::getResourceId);

		if (CollectionUtils.isEmpty(groupIds)) {
			return Lists.newArrayList();
		}
		// 场景启动数据校验
		List<OjLocalScene> ojLocalScenes = ojLocalSceneMapper.selectByGroupIds(groupIds);
		ojLocalSceneService.checkSceneStartCount(authContext.getUserId(),
				groupInfoList.size() - ojLocalScenes.size());

		// 获取线程池对象
		ThreadPoolExecutor executor = SpringContextUtil.getBeanByClass(ThreadPoolExecutor.class);
		CountDownLatch downLatch = new CountDownLatch(groupInfoList.size());

		// 构建数据存储结构
		List<String> execFailGroupIds = (List<String>) CollectionUtils
				.synchronizedCollection(Lists.newArrayList());
		ConcurrentMap<String, JSONObject> execGroupMap = Maps.newConcurrentMap();

		// 场景启动
		for (ClassroomGroupInfo groupInfo : groupInfoList) {
			executor.execute(() -> {
				try {

					// 有用户的组才可以启动场景
					if (CollectionUtils.isEmpty(JsonUtil
							.toJsonObjectArray(groupInfo.getGroupUserIds(), String.class))) {
						JSONObject sceneData = startScene(groupInfo.getResourceId(), authContext);
						execGroupMap.put(groupInfo.getResourceId(), sceneData);
					}

				} catch (Exception e) {
					execFailGroupIds.add(groupInfo.getResourceId());
					log.error(
							"Classroom start scene failed! classroom_id=[{}], group_id=[{}], catch error:",
							classroomId, groupInfo.getResourceId(), e);
				} finally {
					downLatch.countDown();
				}

			});
		}

		boolean result = downLatch.await(CommonConstant.SIXTY, TimeUnit.MINUTES);
		if (!result) {
			log.error("Classroom start scene time out! classroom_id=[{}]", classroomId);
		}

		if (CollectionUtils.isNotEmpty(execFailGroupIds)) {
			log.error("Classroom some group start scene failed! classroom_id=[{}], group_ids=[{}]",
					classroomId, JsonUtil.arrayToString(execFailGroupIds));
		}

		return listEntityToBo(groupInfoList, execGroupMap);
	}

	@Override
	public void batchDeleteScene(String classroomId, AuthContext authContext) {

		// 基本数据校验
		validateClassroom(classroomId);

		List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);
		groupInfos.forEach(groupInfo -> deleteScene(groupInfo.getResourceId(), authContext));

	}

	/**
	 * 实体类列表数据转换 根据场景是否完成启动进行过滤
	 * @param groupInfos
	 * @param groupSceneMap
	 * @return
	 */
	private List<GroupInfoBo> listEntityToBo(List<ClassroomGroupInfo> groupInfos,
			Map<String, JSONObject> groupSceneMap) {
		return groupInfos.stream()
				.filter(groupInfo -> groupSceneMap.get(groupInfo.getResourceId()) != null)
				.map(groupInfo -> {

					// 场景数据
					GroupInfoBo groupInfoBo = BeanUtils.copy(groupInfo, GroupInfoBo.class);
					groupInfoBo.setSceneData(groupSceneMap.get(groupInfo.getResourceId()));

					// 用户数据
					List<UserInfo> userInfos = remoteUserService.getByIdList(
							JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(), String.class));
					List<SimpleUserData> simpleUserData = StreamApiUtils.map(userInfos,
							userInfo -> BeanUtils.copy(userInfo, SimpleUserData.class));
					groupInfoBo.setUserInfos(simpleUserData);

					return groupInfoBo;
				}).collect(Collectors.toList());
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
	 * 向课程websocket推送课堂场景启动消息
	 * @param groupUserIds
	 * @param courseLesson
	 */
	private void toLessonSocketMessage(String groupUserIds, CourseLesson courseLesson,
			OjEvent ojEvent) {

		List<String> userIds = JsonUtil.toJsonObjectArray(groupUserIds, String.class);
		boolean shared = courseLesson.getSceneShareMode().intValue() == SceneShare.SHARED.getCode();

		List<WebSocketMessageContext> messageContexts = getMessageContext(userIds, courseLesson,
				shared, ojEvent);

		messageContexts
				.forEach(messageContext -> myWebSocketHandler.pushMessage(messageContext, shared));
	}

	/**
	 * 课堂场景操作同步
	 * @param userIds
	 * @param lesson
	 * @param shared
	 * @param ojEvent
	 * @return
	 */
	private List<WebSocketMessageContext> getMessageContext(List<String> userIds,
			CourseLesson lesson, boolean shared, OjEvent ojEvent) {

		// 推送数据组装
		JSONObject data = new JSONObject();
		data.put(JsonKeyConstant.CODE, ojEvent.getCode());
		data.put(JsonKeyConstant.DATA, "");

		if (shared) {
			// 共享消息数据
			LessonWsMessageIdentifier messageIdentifier = LessonWsMessageIdentifier.builder()
					.lessonId(lesson.getResourceId()).fromWeb(CommonConstant.TRUE)
					.shared(lesson.getSceneShareMode()).build();

			WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
					.builder().data(data).messageIdentifier(messageIdentifier).build();

			return Lists.newArrayList(messageContext);
		} else {

			// 私有消息数据
			return StreamApiUtils.map(userIds, userId -> {
				LessonWsMessageIdentifier messageIdentifier = LessonWsMessageIdentifier.builder()
						.lessonId(lesson.getResourceId()).fromWeb(CommonConstant.TRUE)
						.shared(lesson.getSceneShareMode()).build();
				messageIdentifier.setUserId(userId);

				WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
						.builder().data(data).messageIdentifier(messageIdentifier).build();
				return messageContext;
			});
		}
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
