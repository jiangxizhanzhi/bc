package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.integratation.service.RemoteOrganizationService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.ClassroomSignMapper;
import com.cyberpeace.oj.course.dao.mapper.TrainPlanMapper;
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

	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	@Autowired
	private TrainPlanMapper trainPlanMapper;

	public void validatePlanExistByName(String planName, String userId) {
		TrainPlan trainPlan = trainPlanMapper.selectByName(planName, userId);
		if (null != trainPlan) {
			throw new BusinessException(OjBusinessErrorCode.TRAIN_PLAN_EXIST);
		}
	}

	/**
	 * 训练计划存在性校验
	 * @param planId
	 * @return
	 */
	public TrainPlan validatePlanExistById(String planId) {
		TrainPlan trainPlan = trainPlanMapper.selectById(planId);
		if (null == trainPlan) {
			throw new BusinessException(OjBusinessErrorCode.TRAIN_PLAN_NOT_FOUND);
		}

		return trainPlan;
	}

	/**
	 * 课堂签到校验
	 * @param classroomId
	 * @param userId
	 * @return
	 */
	public ClassroomSign validateSign(String classroomId, String userId) {
		ClassroomSign classroomSign = classroomSignMapper.selectByRoomAndUser(classroomId, userId);
		if (null == classroomSign) {
			throw new BusinessException(OjBusinessErrorCode.CLASS_NOT_SIGN);
		}

		return classroomSign;
	}

	/**
	 * 校验课时和组织相同的课时存在
	 * @param lessonId
	 * @param orgId
	 */
	public Classroom validateClassroomLessonExist(String lessonId, String orgId) {
		Classroom classroom = classroomMapper.selectByOrgAndLesson(orgId, lessonId);
		if (null != classroom) {
			throw new BusinessException(OjBusinessErrorCode.CLASS_LESSON_ORG_SAME);
		}

		return classroom;
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
	 * 校验课程表是否存在
	 * @param lessonId
	 * @param orgId
	 * @return
	 */
	public Classroom validateClassroomExist(String lessonId, String orgId) {
		Classroom classroom = classroomMapper.selectByOrgAndLesson(orgId, lessonId);
		if (classroom == null) {
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

	/**
	 * 校验是否有权限操作课堂接口
	 * @param classroomId
	 * @param authContext
	 */
	public void validateLessonPracticeAuth(String classroomId, AuthContext authContext) {

		boolean is_superuser = authContext.getIsSuperAdmin();
		if (!is_superuser) {
			Classroom classroom = classroomMapper.selectById(classroomId);
			if (null == classroom) {
				throw new BusinessException(OjBusinessErrorCode.CLASSROOM_NOT_FOUNT);
			}
			if (!classroom.getCreateUserId().equals(authContext.getUserId())) {
				log.error("User=[{}] has no permission ! classroom_id=[{}]",
						authContext.getUserId(), classroomId);
				throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
			}
		}

	}

	/**
	 * 校验训练计划下课堂表是否开始 开始则提醒
	 * @param planId
	 */
	public void validatePlanCover(String planId, String planName) {
		List<Classroom> classroomList = classroomMapper.selectByPlan(planId);
		classroomList.forEach(classroom -> {
			if (classroom.getStartTime().before(new Date())) {
				throw new BusinessException(OjBusinessErrorCode.CLASSROOM_START_NOT_COVER,
						planName);
			}
		});
	}
}
