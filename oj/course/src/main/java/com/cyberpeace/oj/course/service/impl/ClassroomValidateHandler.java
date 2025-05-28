/**
 * 这段代码定义了一个名为 `ClassroomValidateHandler` 的 Spring 服务类，用于处理与课堂相关的验证逻辑。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **初始化配置**： 
 * - `init()` 方法在类初始化时从配置中读取并设置课堂的最小开始时间和结束最大时间。 
 * 2. **数据访问对象（DAO）注入**： 
 * - 使用 `@Autowired` 注入了多个 DAO 对象，如 `ClassroomMapper`, `ClassroomSignMapper`, `TrainPlanMapper` 等，以便进行数据库操作。 
 * 3. **验证方法**： 
 * - 提供了一系列方法来验证各种课堂相关的信息，包括： 
 * - 验证训练计划是否存在 (`validatePlanExistById`) 
 * - 验证课堂签到信息 (`validateSign`) 
 * - 验证特定课程和组织是否存在 (`validateClassroomLessonExist`) 
 * - 验证课程表的开始和结束时间 (`validateClassroomStartAndEnd`) 
 * - 验证组织下是否存在用户 (`validateClassroomOrgHasUser`) 
 * - 验证课程表是否存在 (`validateClassroomExist`) 
 * - 验证课程表的时间段是否重叠 (`validateClassroomTimeOverlap`) 
 * - 验证更新课程表时的时间段是否重叠 (`validateUpdateClassroomTimeOverlap`) 
 * - 验证用户是否有权限操作课堂 (`validateLessonPracticeAuth`) 
 * ### 关键点 
 * - **日志记录**：使用 Lombok 的 `@Slf4j` 注解，使得类中自动包含了 `log` 对象，方便记录错误信息。 
 * - **异常处理**：当验证失败时，抛出 `BusinessException` 异常，并附带相应的错误码。 
 * - **依赖注入**：通过 Spring 的 `@Autowired` 注入其他服务和 DAO 对象，实现了松耦合的设计。 
 * ### 总结 
 * `ClassroomValidateHandler` 类主要负责处理课堂相关的业务逻辑验证，确保系统中的课堂数据一致性、有效性和安全性。通过一系列的方法，它能够检查各种业务规则，如训练计划的存在性、课堂签到信息的有效性、课程表时间的合理性等，并在发现问题时抛出适当的异常。 
**/
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
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Slf4j
@Service
public class ClassroomValidateHandler {

	/**
	 * 最小开始时间
	 *
	 * @see Integer
	 */
	private Integer MIN_START_TIME;

	/**
	 * 结束最大时间
	 *
	 * @see Integer
	 */
	private Integer END_MAX_TIME;

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	private void init() {
		MIN_START_TIME = Integer.parseInt(ConfigUtil.getContextProperty("oj.classroom.start.time"));
		END_MAX_TIME = Integer.parseInt(ConfigUtil.getContextProperty("oj.classroom.end.time"));
	}

	/**
	 * 课堂Mapper
	 *
	 * @see ClassroomMapper
	 */
	@Autowired
	private ClassroomMapper classroomMapper;

	/**
	 * 远程组织Service
	 *
	 * @see RemoteOrganizationService
	 */
	@Autowired
	private RemoteOrganizationService remoteOrganizationService;

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 课堂标志Mapper
	 *
	 * @see ClassroomSignMapper
	 */
	@Autowired
	private ClassroomSignMapper classroomSignMapper;

	/**
	 * 训练计划Mapper
	 *
	 * @see TrainPlanMapper
	 */
	@Autowired
	private TrainPlanMapper trainPlanMapper;

	/**
	 * 训练计划存在性校验
	 *
	 * @param planId
	 * @return
	 * @throws
	 * @see String
	 * @see TrainPlan
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
	 * @throws
	 * @see String
	 * @see String
	 * @see ClassroomSign
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
	 * @throws
	 * @see String
	 * @see String
	 * @see Classroom
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
	 * @throws
	 * @see String
	 * @see String
	 * @see Classroom
	 * @see Classroom
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
	 * @throws
	 * @see Date
	 * @see Date
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
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	public List<String> validateClassroomOrgHasUser(String orgId) {

		return remoteOrganizationService.childrenList(orgId, true);
	}

	/**
	 * 校验课程表是否存在
	 *
	 * @param classroomId
	 * @return
	 * @throws
	 * @see String
	 * @see Classroom
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
	 * @throws
	 * @see String
	 * @see String
	 * @see Classroom
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
	 * @throws
	 * @see String
	 * @see Date
	 * @see Date
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
	 * @throws
	 * @see String
	 * @see String
	 * @see Date
	 * @see Date
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
	 * @throws
	 * @see String
	 * @see AuthContext
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
