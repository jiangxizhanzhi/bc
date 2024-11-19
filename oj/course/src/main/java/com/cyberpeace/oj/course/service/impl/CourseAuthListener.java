package com.cyberpeace.oj.course.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.oj.course.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cs.service.impl.AuthMessageListener;
import com.cyberpeace.oj.course.service.CourseService;

import lombok.extern.slf4j.Slf4j;

/**
 * 课程认证监听器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see AuthMessageListener
 * @since 1.0.0
 */
@Service
@Slf4j
public class CourseAuthListener extends AuthMessageListener {

	/**
	 * 课程Service
	 *
	 * @see CourseService
	 */
	@Autowired
	private CourseService courseService;

	/**
	 * 课堂Service
	 *
	 * @see ClassroomService
	 */
	@Autowired
	private ClassroomService classroomService;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 */
	@Override
	public Integer declareMessageType() {
		return BusinessCode.COURSE_TRAINING.getCode();
	}

	/**
	 * 用户删除时
	 *
	 * @param users 用户
	 * @return {@code Boolean }
	 * @throws
	 * @see List<String>
	 * @see Boolean
	 */
	@Override
	public Boolean onUserDelete(List<String> users) {

		// 判断删除为靶场执行时，跳过后续操作
		for (String user : users) {
			courseService.clearUserAuth(user);

		}

		return true;
	}

	/**
	 * 在组织删除时
	 *
	 * @param orgs 组织
	 * @return {@code Boolean }
	 * @throws
	 * @see List<String>
	 * @see Boolean
	 */
	@Override
	public Boolean onOrgDelete(List<String> orgs) {

		// 判断删除为靶场执行时，跳过后续操作
		for (String org : orgs) {
			courseService.clearOrgAuth(org);

		}
		classroomService.removeClassrooms(orgs);
		return true;
	}

}
