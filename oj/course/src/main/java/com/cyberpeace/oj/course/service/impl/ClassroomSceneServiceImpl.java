package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.SceneStatusEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomSignMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.course.service.ClassroomSignService;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import com.cyberpeace.oj.course.service.bo.GroupWsMessageIdentifier;
import com.cyberpeace.oj.course.service.bo.StartSceneBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import com.cyberpeace.oj.ojbase.service.bo.ClassroomWsMessageIdentifier;
import com.cyberpeace.oj.ojbase.service.bo.LessonWsMessageIdentifier;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 课堂场景Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see ClassroomSceneService
 * @since 1.0.0
 */
@Slf4j
@Service
public class ClassroomSceneServiceImpl implements ClassroomSceneService {

    /**
     * 课堂Mapper
     *
     * @see ClassroomMapper
     */
    @Autowired
	private ClassroomMapper classroomMapper;

    /**
     * 课堂组信息Mapper
     *
     * @see ClassroomGroupInfoMapper
     */
    @Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

    /**
     * OJ当地场景Service
     *
     * @see OjLocalSceneService
     */
    @Autowired
	private OjLocalSceneService ojLocalSceneService;

    /**
     * OJ当地场景Mapper
     *
     * @see OjLocalSceneMapper
     */
    @Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

    /**
     * 当地场景Service
     *
     * @see LocalSceneService
     */
    @Autowired
	private LocalSceneService localSceneService;

    /**
     * 当地场景Mapper
     *
     * @see LocalSceneMapper
     */
    @Autowired
	private LocalSceneMapper localSceneMapper;

    /**
     * 事务模板
     *
     * @see TransactionTemplate
     */
    @Autowired
	private TransactionTemplate transactionTemplate;

    /**
     * 远程拓扑Service
     *
     * @see RemoteTopoService
     */
    @Autowired
	private RemoteTopoService remoteTopoService;

    /**
     * 课程课时Mapper
     *
     * @see CourseLessonMapper
     */
    @Autowired
	private CourseLessonMapper courseLessonMapper;

    /**
     * 我的WebSocket处理器
     *
     * @see MyWebSocketHandler
     */
    @Autowired
	private MyWebSocketHandler myWebSocketHandler;

    /**
     * 课堂标志Mapper
     *
     * @see ClassroomSignMapper
     */
    @Autowired
	private ClassroomSignMapper classroomSignMapper;

    /**
     * 课堂标志Service
     *
     * @see ClassroomSignService
     */
    @Autowired
	private ClassroomSignService classroomSignService;

    /**
     * 推送消息处理器
     *
     * @see PushMessageHandler
     */
    @Autowired
	private PushMessageHandler pushMessageHandler;

    /**
     * 课程验证处理器
     *
     * @see CourseValidateHandler
     */
    @Autowired
	private CourseValidateHandler courseValidateHandler;

    /**
     * 批量场景映射
     *
     * @see Map<String, BatchStartSceneHandler>
     */
    private Map<String, BatchStartSceneHandler> batchSceneMap = Maps.newConcurrentMap();

    /**
     * 启动场景
     *
     * @param groupId     组id
     * @param authContext auth 上下文
     * @return {@code JSONObject }
     * @throws
     * @see String
     * @see AuthContext
     * @see JSONObject
     */
    @Override
	public JSONObject startScene(String groupId, AuthContext authContext) {

		// 数据校验
		ClassroomGroupInfo groupInfo = validateGroup(groupId);
		CourseLesson courseLesson = validateLesson(groupInfo.getLessonId());
		courseValidateHandler.validateLessonType(courseLesson.getType(),
				CourseLesson.LessonType.EXPERIMENT.getCode());

		//启动场景时，获取该课时最新的存活时间
		groupInfo.setDestroyDelay(courseLesson.getDestroyDelay());
		// 5.4.1只限制前台启动场景数量
		if (authContext.getIsWeb()) {
			ojLocalSceneService.checkSceneStartCount(authContext.getUserId(), CommonConstant.ONE);
		}

		// 不为空返回已存在场景
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(groupId,
				SceneShare.GROUP.getCode(), authContext);
		if (null != ojLocalScene) {
			JSONObject sceneData = ojLocalSceneService.getSceneData(ojLocalScene);
			BatchStartSceneHandler sceneHandler = batchSceneMap.get(groupInfo.getClassroomId());
			if (null != sceneHandler) {
				sceneHandler.finish(groupId);
			}
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
		toGroupSocketMessage(groupInfo.getResourceId(), OjEvent.SCENE_CREATE_SYNC);
		return ojLocalSceneService.getSceneData(initOjLocalScene);
	}

    /**
     * 删除场景
     *
     * @param groupId     组id
     * @param authContext auth 上下文
     * @return {@code String }
     * @throws
     * @see String
     * @see AuthContext
     * @see String
     */
    @Override
	public String deleteScene(String groupId, AuthContext authContext) {

		// 基本数据校验
		ClassroomGroupInfo groupInfo = validateGroup(groupId);
		CourseLesson courseLesson = validateLesson(groupInfo.getLessonId());

		// 本地场景数据不存在 则删除
		OjLocalScene ojLocalScene = ojLocalSceneService.selectOjLocalScene(groupId,
				SceneShare.GROUP.getCode(), authContext);
		if (null == ojLocalScene) {
			return groupId;
		}

		// 课堂批量启动场景中，手动关闭某个启动中小组场景，将小组由启动队列中移除
		BatchStartSceneHandler sceneHandler = batchSceneMap.get(groupInfo.getClassroomId());
		if (null != sceneHandler) {
			sceneHandler.finish(groupId);
		}

		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		deleteLocalScene(localScene, groupInfo);

		// 给前台课程websocket推送删除消息 重新加载课程场景
		toLessonSocketMessage(groupInfo.getGroupUserIds(), courseLesson, OjEvent.SCENE_DELETE_SYNC);
		toGroupSocketMessage(groupId, OjEvent.SCENE_DELETE_SYNC);
		pushMessageHandler.pushClassroomMessage(pushMessageHandler.getDeleteMessage(),
				ojLocalScene);
		return CommonConstant.EMPTY_STRING;
	}

    /**
     * 删除当地场景
     *
     * @param localScene 当地场景
     * @param groupInfo  组信息
     * @throws
     * @see LocalScene
     * @see ClassroomGroupInfo
     */
    @Override
	public void deleteLocalScene(LocalScene localScene, ClassroomGroupInfo groupInfo) {

		List<ClassroomSign> signList = resetNeedHelp(groupInfo);

		// 远程场景删除
		localSceneService.deleteRemoteScene(localScene);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				ojLocalSceneMapper.deleteById(localScene.getBusinessInstanceId());
				localSceneMapper.deleteById(localScene.getResourceId());
				classroomSignService.updateBatchById(signList);
			}
		});
	}

    /**
     * 重置远程协助信息
     *
     * @param groupInfo 组信息
     * @return {@code List<ClassroomSign> }
     * @throws
     * @see ClassroomGroupInfo
     * @see List<ClassroomSign>
     */
    private List<ClassroomSign> resetNeedHelp(ClassroomGroupInfo groupInfo) {
		// 查询签到的用户
		List<String> userIds = JsonUtil.toJsonObjectArray(groupInfo.getGroupUserIds(),
				String.class);
		return StreamApiUtils.map(userIds,
				userId -> resetNeedHelp(groupInfo.getClassroomId(), userId));
	}

    /**
     * 重置 需要帮助
     *
     * @param classroomId 课堂id
     * @param userId      用户id
     * @return {@code ClassroomSign }
     * @throws
     * @see String
     * @see String
     * @see ClassroomSign
     */
    private ClassroomSign resetNeedHelp(String classroomId, String userId) {
		ClassroomSign classroomSign = classroomSignMapper.selectByRoomAndUser(classroomId, userId);
		if (null == classroomSign) {
			return null;
		}

		classroomSign.setNodeId(CommonConstant.EMPTY_STRING);
		classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.NEED_HELP.getCode());
		return classroomSign;
	}

    /**
     * 给课堂组用户推送消息
     *
     * @param groupId
     * @param ojEvent OJ 活动
     * @throws
     * @see String
     * @see OjEvent
     */
    private void toGroupSocketMessage(String groupId, OjEvent ojEvent) {
		toGroupSocketMessage(groupId, ojEvent, CommonConstant.EMPTY_STRING);
	}

    /**
     * 批量启动场景
     *
     * @param classroomId 课堂id
     * @param authContext auth 上下文
     * @return {@code List<GroupInfoBo> }
     * @throws
     * @see String
     * @see AuthContext
     * @see List<GroupInfoBo>
     */
    @Override
	public List<GroupInfoBo> batchStartScene(String classroomId, AuthContext authContext) {

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
		
		if (null != batchSceneMap.get(classroomId)) {
			batchSceneMap.get(classroomId).addStartScene(UserInfoContext.getUserContext(),
					groupInfoList);
		} else {
			BatchStartSceneHandler sceneHandler = new BatchStartSceneHandler(
					UserInfoContext.getUserContext(), groupInfoList, this);
			batchSceneMap.put(classroomId, sceneHandler);
		}

		batchSceneMap.get(classroomId).batchStart();

		return Lists.newArrayList();
	}

    /**
     * 完成启动
     *
     * @param classroomId 课堂id
     * @param groupId     组id
     * @throws
     * @see String
     * @see String
     */
    @Override
	public void finishStart(String classroomId, String groupId) {
		// 未进行批量启动 直接退出
		BatchStartSceneHandler sceneHandler = batchSceneMap.get(classroomId);
		if (null == sceneHandler) {
			return;
		}

		sceneHandler.finish(groupId);
		if (sceneHandler.isFinishAll()) {
			batchSceneMap.remove(classroomId);
		}

	}

    /**
     * 自组Socket消息
     *
     * @param groupId 组id
     * @param ojEvent OJ 活动
     * @param message 消息
     * @throws
     * @see String
     * @see OjEvent
     * @see String
     */
    @Override
	public void toGroupSocketMessage(String groupId, OjEvent ojEvent, String message) {

		GroupWsMessageIdentifier identifier = new GroupWsMessageIdentifier();
		identifier.setGroupId(groupId);
		identifier.setPath(WsPath.CLASSROOM_GROUP_SCENE);

		JSONObject data = new JSONObject();
		data.put(JsonKeyConstant.CODE, ojEvent.getCode());
		data.put(JsonKeyConstant.DATA, message);

		if (StringUtils.isNotBlank(message)) {
			JSONObject messageData = new JSONObject();
			messageData.put(JsonKeyConstant.GROUP_ID, groupId);
			messageData.put(JsonKeyConstant.MESSAGE, message);
			data.put(JsonKeyConstant.DATA, messageData);
		}

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}

    /**
     * 清除批量启动场景
     *
     * @param classroomId 课堂id
     * @throws
     * @see String
     */
    @Override
	public void clearBatchStartScene(String classroomId) {
		batchSceneMap.remove(classroomId);
	}

    /**
     * 前往 Google 课堂消息
     *
     * @param sceneBo 场景Bo
     * @param ojEvent OJ 活动
     * @param message 消息
     * @throws
     * @see StartSceneBo
     * @see OjEvent
     * @see String
     */
    @Override
	public void toClassroomMessage(StartSceneBo sceneBo, OjEvent ojEvent, String message) {
		ClassroomWsMessageIdentifier identifier = new ClassroomWsMessageIdentifier();
		identifier.setUserId(sceneBo.getUserInfo().getUserId());

		identifier.setClassroomId(sceneBo.getClassroomId());
		identifier.setPath(WsPath.CLASSROOM_SCENE);

		JSONObject data = new JSONObject();
		data.put(JsonKeyConstant.CODE, ojEvent.getCode());

		JSONObject messageData = new JSONObject();
		messageData.put(JsonKeyConstant.GROUP_ID, sceneBo.getGroupId());
		messageData.put(JsonKeyConstant.MESSAGE, message);
		data.put(JsonKeyConstant.DATA, messageData);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(identifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}

    /**
     * 批量删除场景
     *
     * @param classroomId 课堂id
     * @param authContext auth 上下文
     * @throws
     * @see String
     * @see AuthContext
     */
    @Override
	public void batchDeleteScene(String classroomId, AuthContext authContext) {

		// 基本数据校验
		validateClassroom(classroomId);

		List<ClassroomGroupInfo> groupInfos = classroomGroupInfoMapper.selectByRoom(classroomId);
		batchSceneMap.remove(classroomId);
		groupInfos.forEach(groupInfo -> {
			String groupId = deleteScene(groupInfo.getResourceId(), authContext);

			if (StringUtils.isNotBlank(groupId)) {
				pushMessageHandler.pushMessageToNoSceneClassroom(
						pushMessageHandler.getDeleteMessage(), groupId);
			}
		});

	}

    /**
     * 查询监控列表
     *
     * @param classroomId 课堂id
     * @return {@code List<SceneMonitorBo> }
     * @throws
     * @see String
     * @see List<SceneMonitorBo>
     */
    @Override
	public List<SceneMonitorBo> selectMonitorList(String classroomId) {
		// 基本数据校验
		validateClassroom(classroomId);

		// 查询课堂场景信息
		List<ClassroomGroupInfo> groupInfoList = classroomGroupInfoMapper.selectByRoom(classroomId);
		List<OjLocalScene> ojLocalSceneList = StreamApiUtils.map(groupInfoList,
				groupInfo -> ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId()));

		if (CollectionUtils.isEmpty(ojLocalSceneList)) {
			return Lists.newArrayList();
		}

		return ojLocalSceneService.selectMonitorList(ojLocalSceneList);
	}

    /**
     * 更新场景状态
     *
     * @param groupId 组id
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see Boolean
     */
    @Override
	public Boolean updateSceneStatus(String groupId) {
		ClassroomGroupInfo groupInfo = validateGroup(groupId);

		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());
		if (null == ojLocalScene) {
			log.error("Group scene not found! group_id=[{}]", groupId);
			throw new BusinessException(OjBusinessErrorCode.SCENE_NOT_RUNNING);
		}

		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		if (null == localScene) {
			log.error("Group scene not found! group_id=[{}], local_scene_id=[{}]", groupId,
					ojLocalScene.getLocalSceneId());
			throw new BusinessException(OjBusinessErrorCode.SCENE_NOT_RUNNING);
		}

		if (localScene.getSceneStatus().intValue() == SceneStatusEnum.PAUSE.getCode()) {
			return localSceneService.unpauseScene(localScene);
		} else {
			return localSceneService.pauseScene(localScene);
		}
	}

    /**
     * 初始化中台场景信息
     *
     * @param groupInfo
     * @param lesson
     * @param ojLocalScene
     * @param authContext
     * @return
     * @throws
     * @see ClassroomGroupInfo
     * @see CourseLesson
     * @see OjLocalScene
     * @see AuthContext
     * @see LocalScene
     */
    private LocalScene initLocalScene(ClassroomGroupInfo groupInfo, CourseLesson lesson,
			OjLocalScene ojLocalScene, AuthContext authContext) {

		LocalScene localScene = new LocalScene();
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setUserId(authContext.getUserId());
		localScene.setBusinessInstanceId(ojLocalScene.getResourceId());
		localScene.setDestroyMode(LocalScene.DestroyMode.AUTO.getCode());
		localScene.setTopoId(groupInfo.getTopoId());

		localScene.setBusinessCode(BusinessCode.CLASSROOM.getCode());
		localScene.setSceneName(
				lesson.getName() + CommonConstant.UNDER_LINE + groupInfo.getGroupNo().toString());
		localScene.setSceneStatus(SceneStatusEnum.INIT.getCode());

		// 场景存活时间设置
		if (null != groupInfo.getDestroyDelay()
				&& groupInfo.getDestroyDelay() > CommonConstant.ZERO) {
			localScene.setSceneTtl(groupInfo.getDestroyDelay() * CommonConstant.SIXTY);
		}

		localScene.setSceneAttr(SceneStartContext.SceneAttrEnum.TEAM.getCode());

		// 设置场景类型
		JSONObject topoData = remoteTopoService.getTopoById(lesson.getTopoId());
		topoData.put(JsonKeyConstant.REFERENCE_TYPE, BusinessCode.CLASSROOM.getCode());

		String appResourceId = lesson.getCourseId() + CommonConstant.LEFT_OBLIQUE_LINE
				+ lesson.getResourceId();
		LocalSceneMeta sceneMeta = LocalSceneMeta.builder().appResourceId(appResourceId)
				.topoData(topoData).isWeb(ojLocalScene.getFromWeb()).build();

		localScene.setMeta(JsonUtil.toJsonString(sceneMeta));
		return localScene;
	}

    /**
     * 初始化组场景信息
     *
     * @param groupInfo
     * @param authContext
     * @return
     * @throws
     * @see ClassroomGroupInfo
     * @see AuthContext
     * @see OjLocalScene
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
		ojLocalScene.setBusinessType(BusinessCode.CLASSROOM.getCode());

		// 无用字段 数据库字段尽量不为空
		ojLocalScene.setFlags(JsonUtil.arrayToString(Lists.newArrayList()));

		return ojLocalScene;
	}

    /**
     * 向课程websocket推送课堂场景启动消息
     *
     * @param groupUserIds
     * @param courseLesson 课程课时
     * @param ojEvent      OJ 活动
     * @throws
     * @see String
     * @see CourseLesson
     * @see OjEvent
     */
    private void toLessonSocketMessage(String groupUserIds, CourseLesson courseLesson,
			OjEvent ojEvent) {

		List<String> userIds = JsonUtil.toJsonObjectArray(groupUserIds, String.class);
		Boolean shared = courseLesson.getSceneShareMode().intValue() == SceneShare.SHARED.getCode();

		List<WebSocketMessageContext<WsMessageIdentifier,Object>> messageContexts = getMessageContext(userIds, courseLesson,
				shared, ojEvent);

		messageContexts
				.forEach(messageContext -> myWebSocketHandler.pushMessage(messageContext, shared));
	}

    /**
     * 课堂场景操作同步
     *
     * @param userIds
     * @param lesson
     * @param sceneShare
     * @param ojEvent
     * @return
     * @throws
     * @see List<String>
     * @see CourseLesson
     * @see Boolean
     * @see OjEvent
     * @see List<WebSocketMessageContext<WsMessageIdentifier,Object>>
     */
    private List<WebSocketMessageContext<WsMessageIdentifier,Object>> getMessageContext(List<String> userIds,
			CourseLesson lesson, Boolean sceneShare, OjEvent ojEvent) {

		// 推送数据组装
		JSONObject data = new JSONObject();
		data.put(JsonKeyConstant.CODE, ojEvent.getCode());
		data.put(JsonKeyConstant.DATA, "");

		if (sceneShare) {
			// 共享消息数据
			LessonWsMessageIdentifier messageIdentifier = LessonWsMessageIdentifier.builder()
					.lessonId(lesson.getResourceId()).fromWeb(CommonConstant.TRUE)
					.shared(sceneShare).build();
			messageIdentifier.setPath(WsPath.LESSON_SCENE);

			WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
					.builder().data(data).messageIdentifier(messageIdentifier).build();

			return Lists.newArrayList(messageContext);
		} else {

			// 私有消息数据
			return StreamApiUtils.map(userIds, userId -> {
				LessonWsMessageIdentifier messageIdentifier = LessonWsMessageIdentifier.builder()
						.lessonId(lesson.getResourceId()).fromWeb(CommonConstant.TRUE)
						.shared(sceneShare).build();
				messageIdentifier.setUserId(userId);
				messageIdentifier.setPath(WsPath.LESSON_SCENE);

				return WebSocketMessageContext.builder().data(data).messageIdentifier(messageIdentifier).build();
			});
		}
	}

    /**
     * 数据存在校验
     *
     * @param classroomId
     * @return
     * @throws
     * @see String
     * @see Classroom
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
     *
     * @param groupId
     * @return
     * @throws
     * @see String
     * @see ClassroomGroupInfo
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
     * 验证课时
     *
     * @param lessonId
     * @return
     * @throws
     * @see String
     * @see CourseLesson
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
