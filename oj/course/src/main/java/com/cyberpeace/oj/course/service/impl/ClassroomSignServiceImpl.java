package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.dao.mapper.ClassroomSignMapper;
import com.cyberpeace.oj.course.service.ClassroomSignService;

import java.util.Date;

@Slf4j
@Service
public class ClassroomSignServiceImpl extends ServiceImpl<ClassroomSignMapper, ClassroomSign> implements ClassroomSignService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	@Autowired
	private ClassroomValidateHandler classroomValidateHandler;

	@Override
	public Boolean signIn(String classroomId, AuthContext authContext) {

		Classroom classroom = classroomMapper.selectById(classroomId);
		if (null == classroom) {
			log.info("Classroom not found! classroom_id=[{}]", classroomId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		if (!classroom.getOrganizationIds().contains(authContext.getOrganization())) {
			log.error("Current user is not permission! user_org_id=[{}]",
					authContext.getOrganization());
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		ClassroomSign sign = ClassroomSign.builder().signIn(CommonConstant.TRUE)
				.classroomId(classroomId).resourceId(IDGeneratorUtil.getID()).signInTime(new Date())
				.userId(authContext.getUserId()).build();
		try {
			classroomSignMapper.insert(sign);
		} catch (DuplicateKeyException e) {
			return true;
		}

		return true;
	}

	@Override
	public void needHelp(String lessonId, String nodeId) {

		// 课堂数据校验
		Classroom classroom = classroomMapper.selectByOrgAndLesson(
				UserInfoContext.getUserContext().getOrganizationId(), lessonId);

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

		// 数据填写到数据库
		classroomSign.setNodeId(nodeId);
		classroomSignMapper.updateById(classroomSign);

	}

	@Override
	public void toHelp(String classroomId) {

		ClassroomSign classroomSign = classroomSignMapper.selectByRoomAndUser(classroomId,
				UserInfoContext.getUserContext().getUserId());
		classroomSign.setNodeId("");
		classroomSignMapper.updateById(classroomSign);
	}
}
