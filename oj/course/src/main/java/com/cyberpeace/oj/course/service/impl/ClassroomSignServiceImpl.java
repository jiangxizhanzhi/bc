package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.context.ChatWsMessageIdentifier;
import com.cyberpeace.cs.service.impl.ChatWebSocketHandler;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.oj.course.dao.entity.*;
import com.cyberpeace.oj.course.dao.mapper.*;
import com.cyberpeace.oj.course.service.ClassroomSignService;
import com.cyberpeace.oj.course.service.bo.HelpMessage;
import com.cyberpeace.oj.course.service.bo.HelpStatusBo;
import com.cyberpeace.oj.ojbase.constant.ClassroomRole;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chenzhuo
 */
@Slf4j
@Service
public class ClassroomSignServiceImpl extends ServiceImpl<ClassroomSignMapper, ClassroomSign> implements ClassroomSignService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Autowired
	private ChatWebSocketHandler chatWebSocketHandler;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private ClassroomValidateHandler classroomValidateHandler;

	@Autowired
	private PushMessageHandler pushMessageHandler;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Override
	public Boolean signIn(String classroomId, AuthContext authContext) {

		Classroom classroom = classroomMapper.selectById(classroomId);
		if (null == classroom) {
			log.info("Classroom not found! classroom_id=[{}]", classroomId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		if (!authContext.getIsSuperAdmin()
				&& !classroom.getCreateUserId().equals(authContext.getUserId())
				&& !classroom.getOrganizationIds().contains(authContext.getOrganization())) {
			log.error("Current user is not permission! user_org_id=[{}]",
					authContext.getOrganization());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		// 仅存储该课堂组织下的用户签到信息
		if (classroom.getOrganizationIds().contains(authContext.getOrganization())) {
			ClassroomSign sign = ClassroomSign.builder().signIn(CommonConstant.TRUE)
					.classroomId(classroomId).resourceId(IDGeneratorUtil.getID())
					.signInTime(new Date()).userId(authContext.getUserId()).build();
			try {
				return classroomSignMapper.insert(sign) > 0;
			} catch (DuplicateKeyException e) {
				return true;
			}
		}

		return true;
	}

	@Override
	public void needHelp(String lessonId, String nodeId, String roomId) {

		// 课堂数据校验
		Classroom classroom = classroomMapper.selectById(roomId);
		if (classroom == null) {
			log.error("classroom with resourceId [{}] isn't exist", roomId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		ClassroomSign classroomSign = classroomSignMapper.selectByRoomAndUser(
				classroom.getResourceId(), UserInfoContext.getUserContext().getUserId());
		if (null == classroomSign) {
			log.error("classroom:[{}] userId[{}]  doesn't sign", classroom.getResourceId(), UserInfoContext.getUserContext().getUserId());
			throw new BusinessException(OjBusinessErrorCode.CLASS_NOT_SIGN);
		}

		// 不能同时两台虚拟机都远程协助
		if (StringUtils.isNotBlank(classroomSign.getNodeId())
				&& !classroomSign.getNodeId().equals(nodeId)) {
			log.error("nodeId:[{}] classroomSign-nodeId[{}] can't both assistance", classroomSign.getNodeId(), nodeId);
			throw new BusinessException(OjBusinessErrorCode.OTHER_NODE_IN_ASSISTANCE);
		}

		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper.selectByRoomAndUser(
				classroom.getResourceId(), UserInfoContext.getUserContext().getUserId());
		if (null == groupInfo) {
			log.error("classroom:[{}] user:[{}] doesn't bind classroom group", classroom.getResourceId(), UserInfoContext.getUserContext().getUserId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());
		if (null == ojLocalScene) {
			log.error("[{}] doesn't bind scene", groupInfo.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.SCENE_NOT_RUNNING);
		}

		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		if (localScene.getSceneStatus().intValue() == LocalScene.SceneStatusEnum.PAUSE.getCode()
				.intValue()) {
			log.error("[{}] is pause", localScene.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.SCENE_IS_PAUSE);
		}

		// 修改远程协助信息
		if (StringUtils.isBlank(classroomSign.getNodeId())) {
			// 数据填写到数据库
			classroomSign.setNodeId(nodeId);
			classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.NEED_HELP.getCode());
			classroomSignMapper.updateById(classroomSign);
		}

		// 请求协助消息发送
		String needHelpMessage = getNeedHelpMessage(classroomSign.getUserId(),
				classroom.getResourceId());

		HelpMessage message = new HelpMessage();
		message.setMessage(needHelpMessage);
		// 课程数据组装
		Course course = courseMapper.selectById(classroom.getCourseId());
		if (course == null) {
			log.error("Course not found! course_id=[{}]", classroom.getCourseId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 课时数据组装
		CourseLesson lesson = courseLessonMapper.selectById(classroom.getLessonId());
		if (lesson == null) {
			log.error("Lesson not found! lesson_id=[{}]", classroom.getLessonId());
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		message.setClassroom(course.getName() + "/" + lesson.getName());
		message.setStudent(UserInfoContext.getUserContext().getName());
		message.setStart(classroom.getStartTime());
		pushHelpMessage(message, classroom.getCreateUserId());

	}

	private void pushHelpMessage(HelpMessage msg, String toUserId) {
		WsMessageIdentifier messageIdentifier = new WsMessageIdentifier();
		messageIdentifier.setPath(WsPath.CLASSROOM_HELP);
		messageIdentifier.setUserId(toUserId);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(messageIdentifier).data(msg).build();
		myWebSocketHandler.pushMessage(messageContext, false);
	}

	@Override
	public void toHelp(String classroomId, String userId) {

		// 数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(classroomId);
		ClassroomSign classroomSign = classroomValidateHandler.validateSign(classroomId, userId);

		// 更新状态
		if (classroomSign.getNeedHelp().intValue() == ClassroomSign.NEED_HELP.NEED_HELP.getCode()
				.intValue()) {
			classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.IN_HELP.getCode());
			classroomSignMapper.updateById(classroomSign);
		}
	}

	@Override
	public void endHelp(String classroomId, String userId) {

		// 数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(classroomId);
		ClassroomSign classroomSign = classroomValidateHandler.validateSign(classroomId, userId);

		if (null != classroomSign) {
			// 更新远程协助状态
			classroomSign.setNodeId(CommonConstant.EMPTY_STRING);
			classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.END_HELP.getCode());
			classroomSignMapper.updateById(classroomSign);

			JSONObject endMessage = new JSONObject();
			endMessage.put(JsonKeyConstant.CODE, OjEvent.END_HELP_SYNC.getCode());
			pushMessageHandler.pushHelpMessage(endMessage, classroomId, userId);

			// 结束消息推送
			pushChatMessage(classroom, ConfigUtil.getContextProperty("end.remote.help"),
					classroomSign.getUserId());
		}

	}

	@Override
	public HelpStatusBo getHelpStatus(String lessonId, String nodeId, String roomId) {

		// 课堂数据校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(roomId);
		ClassroomSign classroomSign = classroomValidateHandler.validateSign(
				classroom.getResourceId(), UserInfoContext.getUserContext().getUserId());

		// 可能部分数据没用，
		HelpStatusBo helpStatusBo = new HelpStatusBo();
		helpStatusBo.setLessonId(lessonId);
		helpStatusBo.setStatus(classroomSign.getNeedHelp());
		helpStatusBo.setUserId(classroomSign.getUserId());
		helpStatusBo.setNodeId(nodeId);
		return helpStatusBo;

	}

	@Override
	public String getHelpUrl(String classroomId, String userId) {
		// 房间校验
		Classroom classroom = classroomValidateHandler.validateClassroomExist(classroomId);
		ClassroomSign classroomSign = classroomValidateHandler.validateSign(classroomId, userId);

		// 无节点信息 无协助状态 协助状态不符合
		if (StringUtils.isBlank(classroomSign.getNodeId()) || null == classroomSign.getNeedHelp()
				|| classroomSign.getNeedHelp().intValue() == ClassroomSign.NEED_HELP.END_HELP
						.getCode().intValue()
				|| classroomSign.getNeedHelp().intValue() == ClassroomSign.NEED_HELP.NO_HELP
						.getCode().intValue()) {
			log.error("node:[{}] no need help", classroomSign.getNodeId());
			throw new BusinessException(OjBusinessErrorCode.USER_NO_NEED_HELP);
		}

		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper.selectByRoomAndUser(classroomId,
				userId);
		if (null == groupInfo) {
			log.error("[{}] doesn't bind classroom", userId);
			throw new BusinessException(OjBusinessErrorCode.USER_CLASS_GROUP_NOT_FOUND);
		}

		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());
		if (null == ojLocalScene) {
			log.error("scene:[{}] is not running", ojLocalScene.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.SCENE_NOT_RUNNING);
		}

		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		if (null == localScene) {
			log.error("scene:[{}] is not running", ojLocalScene.getResourceId());
			throw new BusinessException(OjBusinessErrorCode.SCENE_NOT_RUNNING);
		}

		Map<String, String> assistanceMap = remoteSceneService.getAssistanceMap(
				Lists.newArrayList(localScene.getMpSceneId()),
				Lists.newArrayList(classroomSign.getNodeId()), Lists.newArrayList(userId));
		if (StringUtils.isBlank(assistanceMap.get(userId))) {
			log.error("user:[{}] is unplugged machine", userId);
			throw new BusinessException(OjBusinessErrorCode.HELP_URL_NOT_FOUND);
		}

		// 获取远程协助链接时 如果处于请求状态 则修改为协助状态
		if (classroomSign.getNeedHelp().intValue() == ClassroomSign.NEED_HELP.NEED_HELP.getCode()) {
			// 更新协助状态
			classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.IN_HELP.getCode());
			classroomSignMapper.updateById(classroomSign);

			// 聊天框消息推送
			pushChatMessage(classroom, ConfigUtil.getContextProperty("to.remote.help"),
					classroomSign.getUserId());

			// 协助开始消息推送
			JSONObject toHelpMessage = new JSONObject();
			toHelpMessage.put(JsonKeyConstant.CODE, OjEvent.TO_NEED_HELP_SYNC.getCode());
			pushMessageHandler.pushHelpMessage(toHelpMessage, classroomId, userId);
		}

		return assistanceMap.get(userId);
	}

	/**
	 * 协助状态更新消息推送
	 * @param classroom
	 * @param message
	 */
	private void pushChatMessage(Classroom classroom, String message, String toUserId) {
		ChatWsMessageIdentifier messageIdentifier = new ChatWsMessageIdentifier();
		messageIdentifier.setPath(WsPath.CLASSROOM_CHAT);
		messageIdentifier.setBusinessId(classroom.getResourceId());
		messageIdentifier.setBusinessType(ChatWebSocketHandler.CHAT_TYPE.CLASSROOM.getCode());
		messageIdentifier.setUserId(toUserId);

		chatWebSocketHandler.pushChatMessage(messageIdentifier, UserInfoContext.getUserContext(),
				message,
				classroom.getCreateUserId().equals(toUserId) ? ClassroomRole.TEACHER.getDesc()
						: ClassroomRole.STUDENT.getDesc());
	}

	/**
	 * 获取远程协助信息
	 * @param userId
	 * @return
	 */
	private String getNeedHelpMessage(String userId, String classRoomId) {

		// 拼接链接地址
		String needHelpMessage = ConfigUtil.getContextProperty("need.remote.help");
		if (StringUtils.isBlank(needHelpMessage)) {
			log.error("The remote assistance template is not configured！properties_key=[{}]",
					"need.remote.help");
			return CommonConstant.EMPTY_STRING;
		}

		return String.format(needHelpMessage, classRoomId, userId);
	}
}
