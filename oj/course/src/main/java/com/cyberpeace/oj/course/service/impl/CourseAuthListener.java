package com.cyberpeace.oj.course.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.oj.course.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cs.service.impl.AuthMessageListener;
import com.cyberpeace.oj.course.service.CourseService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseAuthListener extends AuthMessageListener {

	@Autowired
	private CourseService courseService;

	@Autowired
	private ClassroomService classroomService;

	@Override
	public Integer declareMessageType() {
		return BusinessCode.COURSE_TRAINING.getCode();
	}

	@Override
	public Boolean onUserDelete(List<String> users) {

		// 判断删除为靶场执行时，跳过后续操作
		for (String user : users) {
			courseService.clearUserAuth(user);

		}

		return true;
	}

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
