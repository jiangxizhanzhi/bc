package com.cyberpeace.oj.course.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomSignMapper;
import com.cyberpeace.oj.course.service.ClassroomSignService;
import com.cyberpeace.oj.course.service.bo.HelpStatusBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;

import lombok.extern.slf4j.Slf4j;

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
	private ClassroomValidateHandler classroomValidateHandler;

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
	public void needHelp(String lessonId, String nodeId) {

		// 课堂数据校验
		Classroom classroom = classroomMapper.selectByOrgAndLesson(
				UserInfoContext.getUserContext().getOrganizationId(), lessonId);
		if (classroom == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		ClassroomSign classroomSign = classroomSignMapper.selectByRoomAndUser(
				classroom.getResourceId(), UserInfoContext.getUserContext().getUserId());
		if (null == classroomSign) {
			throw new BusinessException(OjBusinessErrorCode.CLASS_NOT_SIGN);
		}

		// 已存在相同虚拟机远程协助不管
		if (StringUtils.isNotBlank(classroomSign.getNodeId())
				&& classroomSign.getNodeId().equals(nodeId)) {
			return;
		}

		// 不能同时两台虚拟机都远程协助
		if (StringUtils.isNotBlank(classroomSign.getNodeId())
				&& !classroomSign.getNodeId().equals(nodeId)) {
			throw new BusinessException(OjBusinessErrorCode.OTHER_NODE_IN_ASSISTANCE);
		}

		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper.selectByRoomAndUser(
				classroom.getResourceId(), UserInfoContext.getUserContext().getUserId());
		if (null == groupInfo) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectGroupScene(groupInfo.getResourceId());
		if (null == ojLocalScene) {
			throw new BusinessException(OjBusinessErrorCode.SCENE_NOT_RUNNING);
		}

		LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
		if (localScene.getSceneStatus().intValue() == LocalScene.SceneStatusEnum.PAUSE.getCode()
				.intValue()) {
			throw new BusinessException(OjBusinessErrorCode.SCENE_IS_PAUSE);
		}

		// 数据填写到数据库
		classroomSign.setNodeId(nodeId);
		classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.NEED_HELP.getCode());
		classroomSignMapper.updateById(classroomSign);

	}

	@Override
	public void toHelp(String classroomId, String userId) {

		ClassroomSign classroomSign = classroomSignMapper.selectByRoomAndUser(classroomId, userId);
		if (null == classroomSign) {
			return;
		}

		classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.IN_HELP.getCode());
		classroomSignMapper.updateById(classroomSign);
	}

	@Override
	public void endHelp(String classroomId, String userId) {

		ClassroomSign classroomSign = classroomSignMapper.selectByRoomAndUser(classroomId, userId);
		if (null == classroomSign) {
			return;
		}

		classroomSign.setNodeId(CommonConstant.EMPTY_STRING);
		classroomSign.setNeedHelp(ClassroomSign.NEED_HELP.END_HELP.getCode());
		classroomSignMapper.updateById(classroomSign);

	}

	@Override
	public HelpStatusBo getHelpStatus(String lessonId, String nodeId) {

		// 课堂数据校验
		Classroom classroom = classroomMapper.selectByOrgAndLesson(
				UserInfoContext.getUserContext().getOrganizationId(), lessonId);
		if (classroom == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		ClassroomSign classroomSign = classroomSignMapper.selectByRoomAndUser(
				classroom.getResourceId(), UserInfoContext.getUserContext().getUserId());
		if (null == classroomSign) {
			throw new BusinessException(OjBusinessErrorCode.CLASS_NOT_SIGN);
		}

		// 可能部分数据没用，
		HelpStatusBo helpStatusBo = new HelpStatusBo();
		helpStatusBo.setLessonId(lessonId);
		helpStatusBo.setStatus(classroomSign.getNeedHelp());
		helpStatusBo.setUserId(classroomSign.getUserId());
		helpStatusBo.setNodeId(nodeId);
		return helpStatusBo;

	}
}
