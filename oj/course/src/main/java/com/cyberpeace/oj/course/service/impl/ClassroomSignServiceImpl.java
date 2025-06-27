package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.enums.SceneStatusEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.context.ChatWsMessageIdentifier;
import com.cyberpeace.cs.service.impl.ChatWebSocketHandler;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomSignMapper;
import com.cyberpeace.oj.course.service.ClassroomSignService;
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
 * 课堂标志Service实现
 *
 * @author chenzhuo@date 2024/11/14@see ServiceImpl
 * @version 1.0.0
 * @date 2024/11/19
 * @see ClassroomSignService
 * @since 1.0.0
 */
@Slf4j
@Service
public class ClassroomSignServiceImpl extends ServiceImpl<ClassroomSignMapper, ClassroomSign> implements ClassroomSignService {

	/**
	 * 课堂Mapper
	 *
	 * @see ClassroomMapper
	 */
	@Autowired
	private ClassroomMapper classroomMapper;

	/**
	 * 课堂标志Mapper
	 *
	 * @see ClassroomSignMapper
	 */
	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	/**
	 * 课堂组信息Mapper
	 *
	 * @see ClassroomGroupInfoMapper
	 */
	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

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
	 * 聊天WebSocket处理器
	 *
	 * @see ChatWebSocketHandler
	 */
	@Autowired
	private ChatWebSocketHandler chatWebSocketHandler;

	/**
	 * 远程场景Service
	 *
	 * @see RemoteSceneService
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 课堂验证处理器
	 *
	 * @see ClassroomValidateHandler
	 */
	@Autowired
	private ClassroomValidateHandler classroomValidateHandler;

	/**
	 * 推送消息处理器
	 *
	 * @see PushMessageHandler
	 */
	@Autowired
	private PushMessageHandler pushMessageHandler;

	/**
	 * 登录
	 *
	 * @param classroomId 课堂id
	 * @param authContext auth 上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
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
		// 在课堂时间内才可以签到
		Date currentDate = new Date();
		if (currentDate.before(classroom.getStartTime())) {
			log.error("Current time {} is not in classroom time {}! classroom_id=[{}]",
					currentDate, classroom.getStartTime(), classroomId);
			throw new BusinessException(OjBusinessErrorCode.CLASSROOM_NOT_START_SIGN_IN_TIME);
		}
		if (currentDate.after(classroom.getEndTime())) {
			log.error("Current time {} is not in classroom time {}! classroom_id=[{}]",
					currentDate, classroom.getEndTime(), classroomId);
			throw new BusinessException(OjBusinessErrorCode.CLASSROOM_ENDED_SIGN_IN_TIME);
		}

		// 仅存储该课堂组织下的用户签到信息
		if (classroom.getOrganizationIds().contains(authContext.getOrganization())) {

			// 获取该课堂组织下的用户签到信息
			ClassroomSign currentSign = classroomSignMapper.selectByRoomAndUser(classroomId,
					authContext.getUserId());

			// 如果存在则返回
			if (null != currentSign) {
				log.info("课堂 {} 中的用户 {} 重复签到", classroomId, authContext.getUserId());
				return true;
			}

			ClassroomSign sign = ClassroomSign.builder().signIn(CommonConstant.TRUE)
					.classroomId(classroomId).resourceId(IDGeneratorUtil.getID())
					.signInTime(new Date()).userId(authContext.getUserId()).build();

			return classroomSignMapper.insert(sign) > 0;

		}

		return true;
	}

	/**
	 * 需要帮助
	 *
	 * @param lessonId 课时id
	 * @param nodeId   节点id
	 * @param roomId   房间id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
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
		if (localScene.getSceneStatus().intValue() == SceneStatusEnum.PAUSE.getCode()
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
		pushChatMessage(classroom, needHelpMessage, classroom.getCreateUserId());

	}

	/**
	 * 帮助
	 *
	 * @param classroomId 课堂id
	 * @param userId      用户id
	 * @throws
	 * @see String
	 * @see String
	 */
	@Override
	public void toHelp(String classroomId, String userId) {

		// 数据校验
		classroomValidateHandler.validateClassroomExist(classroomId);
		ClassroomSign classroomSign = classroomValidateHandler.validateSign(classroomId, userId);

		// 更新状态
		if (classroomSign.getNeedHelp().intValue() == ClassroomSign.NEED_HELP.NEED_HELP.getCode()
				.intValue()) {
			classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.IN_HELP.getCode());
			classroomSignMapper.updateById(classroomSign);
		}
	}

	/**
	 * 结束帮助
	 *
	 * @param classroomId 课堂id
	 * @param userId      用户id
	 * @throws
	 * @see String
	 * @see String
	 */
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

	/**
	 * 获取帮助状态
	 *
	 * @param lessonId 课时id
	 * @param nodeId   节点id
	 * @param roomId   房间id
	 * @return {@code HelpStatusBo }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see HelpStatusBo
	 */
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

	/**
	 * 获取帮助网址
	 *
	 * @param classroomId 课堂id
	 * @param userId      用户id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
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
		if (ojLocalScene == null) {
			log.error("scene:[{}] is not running", groupInfo.getResourceId());
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
	 *
	 * @param classroom
	 * @param message   消息
	 * @param toUserId  自用户id
	 * @throws
	 * @see Classroom
	 * @see String
	 * @see String
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
	 *
	 * @param userId
	 * @param classRoomId 类房间id
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
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
