package com.cyberpeace.oj.course.service;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface CourseDumpService {

	/**
	 * 课程数据资管格式导出
	 * @param authContext 用户
	 * @param resource_id 资源id
	 * @return file
	 */
	File rMCourseDump(AuthContext authContext, String resource_id);

}
