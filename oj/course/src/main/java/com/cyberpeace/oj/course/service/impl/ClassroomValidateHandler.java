package com.cyberpeace.oj.course.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
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

/**
 * 课堂验证处理器
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Service
public class ClassroomValidateHandler {


	/**
	 * 初始化
	 */
	@PostConstruct
	private void init() {

	}

	/**
	 * 课堂Mapper
	 */
	@Autowired
	private ClassroomMapper classroomMapper;

	/**
	 * 远程组织Service
	 */
	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 课堂标志Mapper
	 */
	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	/**
	 * 训练计划Mapper
	 */
	@Autowired
	private TrainPlanMapper trainPlanMapper;

	/**
	 * 训练计划存在性校验
	 *
	 * @param planId
	 * @return
	 */
	public TrainPlan validatePlanExistById(String planId) {
		TrainPlan trainPlan = trainPlanMapper.selectById(planId);
		if (null == trainPlan) {
			log.error("[{}] doesn't bind a tranPlan", planId);
			throw new BusinessException(OjBusinessErrorCode.TRAIN_PLAN_NOT_FOUND);
		}

		return trainPlan;
	}

	/**
	 * 课堂签到校验
	 *
	 * @param classroomId
	 * @param userId
	 * @return
	 */
	public ClassroomSign validateSign(String classroomId, String userId) {
		ClassroomSign classroomSign = classroomSignMapper.selectByRoomAndUser(classroomId, userId);
		if (null == classroomSign) {
			log.error("classroomId:[{}] user:[{}] doesn't sign",classroomId, userId);
			throw new BusinessException(OjBusinessErrorCode.CLASS_NOT_SIGN);
		}

		return classroomSign;
	}

	/**
	 * 校验课时和组织相同的课时存在
	 *
	 * @param lessonId
	 * @param orgId    组织id
	 * @return {@code Classroom }
	 */
	public Classroom validateClassroomLessonExist(String lessonId, String orgId) {
		Classroom classroom = classroomMapper.selectByOrgAndLesson(orgId, lessonId);
		if (null != classroom) {
			log.error("lessonId:[{}]orgId:[{}] has existed", lessonId, orgId);
			throw new BusinessException(OjBusinessErrorCode.CLASS_LESSON_ORG_SAME);
		}

		return classroom;
	}

	/**
	 * 校验课时和组织相同的课时存在 并过滤自己本身
	 *
	 * @param lessonId
	 * @param orgId
	 * @param itself   本身
	 * @return {@code Classroom }
	 */
	public Classroom validateClassroomLessonExistExcludeItself(String lessonId, String orgId, Classroom itself) {
		Classroom classroom = classroomMapper.selectByOrgAndLesson(orgId, lessonId);
		if (null != classroom && !classroom.getResourceId().equals(itself.getResourceId())) {
			log.error("lessonId:[{}] orgId:[{}] has existed", lessonId, orgId);
			throw new BusinessException(OjBusinessErrorCode.CLASS_LESSON_ORG_SAME);
		}

		return classroom;
	}

	/**
	 * 校验课程表开始和结束时间
	 *
	 * @param startTime
	 * @param endTime   结束时间
	 */
	public void validateClassroomStartAndEnd(Date startTime, Date endTime) {

		if (startTime.after(endTime) || startTime.getTime() == endTime.getTime()) {
			log.error("[{}] is greater than or equal to [{}]", endTime, startTime);
			throw new BusinessException(OjBusinessErrorCode.END_GT_START_TIME);
		}
	}

	/**
	 * 校验组织下是否存在用户
	 *
	 * @param orgId
	 * @return
	 */
	public List<String> validateClassroomOrgHasUser(String orgId) {

		return remoteOrganizationService.childrenList(orgId, true);
	}

	/**
	 * 校验课程表是否存在
	 *
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
	 *
	 * @param lessonId
	 * @param orgId
	 * @return
	 */
	public Classroom validateClassroomExist(String lessonId, String orgId) {
		Classroom classroom = classroomMapper.selectByOrgAndLesson(orgId, lessonId);
		if (classroom == null) {
			log.error("lessonId:[{}] orgId:[{}] doesn't bind classroom", lessonId, orgId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return classroom;
	}

	/**
	 * 时间时间段是否已存在该组织的课程表
	 *
	 * @param organizationId
	 * @param startTime
	 * @param endTime        结束时间
	 */
	public void validateClassroomTimeOverlap(String organizationId, Date startTime, Date endTime) {

		List<Classroom> classrooms = classroomMapper.selectByOrgId(organizationId);
		classrooms.forEach(classroom -> {

			if (!(startTime.getTime() >= classroom.getEndTime().getTime()
					|| endTime.getTime() <= classroom.getStartTime().getTime())) {
				log.error("starTime:[{}]  endTime:[{}] doesn't reasonable", startTime.getTime(), endTime.getTime());
				throw new BusinessException(OjBusinessErrorCode.CLASS_IN_SCHEDULES);
			}

		});
	}

	/**
	 * 校验更新时是否已存在课程表
	 *
	 * @param classroomId
	 * @param organizationId
	 * @param startTime
	 * @param endTime        结束时间
	 */
	public void validateUpdateClassroomTimeOverlap(String classroomId, String organizationId,
			Date startTime, Date endTime) {

		List<Classroom> classrooms = classroomMapper.selectByOrgId(organizationId);
		for (Classroom classroom : classrooms) {
			if (classroom.getResourceId().equals(classroomId)) {
				continue;
			}
			if (!(startTime.getTime() >= classroom.getEndTime().getTime()
					|| endTime.getTime() <= classroom.getStartTime().getTime())) {
				log.error("starTime:[{}]  endTime:[{}] doesn't reasonable", startTime.getTime(), endTime.getTime());
				throw new BusinessException(OjBusinessErrorCode.CLASS_IN_SCHEDULES);
			}
		}
	}

	/**
	 * 校验是否有权限操作课堂接口
	 *
	 * @param classroomId
	 * @param authContext auth 上下文
	 */
	public void validateLessonPracticeAuth(String classroomId, AuthContext authContext) {

		boolean is_superuser = authContext.getIsSuperAdmin();
		if (!is_superuser) {
			Classroom classroom = classroomMapper.selectById(classroomId);
			if (null == classroom) {
				log.error("classroomId:[{}] doesn't exist", classroomId);
				throw new BusinessException(OjBusinessErrorCode.CLASSROOM_NOT_FOUNT);
			}
			if (!classroom.getCreateUserId().equals(authContext.getUserId())) {
				log.error("User=[{}] has no permission ! classroom_id=[{}]",
						authContext.getUserId(), classroomId);
				throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
			}
		}

	}
}
