package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import org.apache.commons.collections.CollectionUtils;
import org.omg.CORBA.ORB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassroomValidateHandler {

	private Integer MIN_START_TIME;

	private Integer END_MAX_TIME;

	@PostConstruct
	private void init() {
		MIN_START_TIME = Integer.parseInt(ConfigUtil.getContextProperty("oj.classroom.start.time"));
		END_MAX_TIME = Integer.parseInt(ConfigUtil.getContextProperty("oj.classroom.end.time"));
	}

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 校验课时和组织相同的课时存在
	 * @param lessonId
	 * @param orgId
	 */
	public void validateClassroomLessonExist(String lessonId, String orgId) {
		Classroom classroom = classroomMapper.selectByOrgAndLesson(orgId, lessonId);
		if (null != classroom) {
			throw new BusinessException(OjBusinessErrorCode.CLASS_LESSON_ORG_SAME);
		}
	}

	/**
	 * 校验课程表开始和结束时间
	 * @param startTime
	 * @param endTime
	 */
	public void validateClassroomStartAndEnd(Date startTime, Date endTime) {

		if (startTime.after(endTime) || startTime.getTime() == endTime.getTime()) {
			throw new BusinessException(OjBusinessErrorCode.END_GT_START_TIME);
		}

		if (startTime.getHours() < MIN_START_TIME || endTime.getHours() > END_MAX_TIME) {
			throw new BusinessException(OjBusinessErrorCode.CLASSROOM_TIME_RANGE_ERROR);
		} else if (endTime.getHours() == END_MAX_TIME
				&& (endTime.getMinutes() != CommonConstant.ZERO
						|| endTime.getSeconds() == CommonConstant.ZERO)) {
			throw new BusinessException(OjBusinessErrorCode.CLASSROOM_TIME_RANGE_ERROR);
		}
	}

	/**
	 * 校验组织下是否存在用户
	 * @param orgId
	 * @return
	 */
	public List<String> validateClassroomOrgHasUser(String orgId) {

		List<UserInfo> userInfos = remoteUserService.ListByOrgId(orgId, true);
		if (CollectionUtils.isEmpty(userInfos)) {
			throw new BusinessException(OjBusinessErrorCode.ORGANIZATION_IS_NO_USERS);
		}

		List<String> orgIds = remoteOrganizationService.childrenList(orgId, true);
		return orgIds;
	}

	/**
	 * 校验课程表是否存在
	 * @param classroomId
	 * @return
	 */
	public Classroom validateClassroomExist(String classroomId) {

		Classroom classroom = classroomMapper.selectById(classroomId);
		if (null == classroom) {
			log.error("Classroom is not found! classroom_id=[{}]", classroomId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return classroom;
	}

	/**
	 * 时间时间段是否已存在该组织的课程表
	 * @param organizationId
	 * @param startTime
	 * @param endTime
	 */
	public void validateClassroomTimeOverlap(String organizationId, Date startTime, Date endTime) {

		List<Classroom> classrooms = classroomMapper.selectByOrgId(organizationId);
		classrooms.forEach(classroom -> {
			if ((startTime.getTime() > classroom.getStartTime().getTime()
					&& startTime.getTime() < classroom.getEndTime().getTime())
					|| (endTime.getTime() > classroom.getStartTime().getTime()
							&& endTime.getTime() < classroom.getEndTime().getTime())) {
				throw new BusinessException(OjBusinessErrorCode.CLASS_IN_SCHEDULES);
			}
		});
	}

	/**
	 * 校验更新时是否已存在课程表
	 * @param classroomId
	 * @param organizationId
	 * @param startTime
	 * @param endTime
	 */
	public void validateUpdateClassroomTimeOverlap(String classroomId, String organizationId,
			Date startTime, Date endTime) {

		List<Classroom> classrooms = classroomMapper.selectByOrgId(organizationId);
		for (Classroom classroom : classrooms) {
			if (classroom.getResourceId().equals(classroomId)) {
				continue;
			}
			if ((startTime.getTime() > classroom.getStartTime().getTime()
					&& startTime.getTime() < classroom.getEndTime().getTime())
					|| (endTime.getTime() > classroom.getStartTime().getTime()
							&& endTime.getTime() < classroom.getEndTime().getTime())) {
				throw new BusinessException(OjBusinessErrorCode.CLASS_IN_SCHEDULES);
			}
		}
	}
}
