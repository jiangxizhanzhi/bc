package com.cyberpeace.oj.course.service;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;

import java.util.List;
import java.util.Map;

public interface CourseImportService {

	/**
	 * 根据导入数据路径获取课程信息
	 * @param path
	 * @return
	 */
	Course parseRmCourse(String path);

	/**
	 * 组装资源导入的课时信息
	 * @param course
	 * @param path
	 * @param envReqs
	 * @return
	 */
	List<CourseLesson> parseRmLesson(Course course, String path, Map<String, String> envReqs,
			Map<String, String> lessonPathMap);

	/**
	 * 组装课时路径信息
	 * @param course
	 * @param path
	 * @param nameMap
	 * @return
	 */
	List<LessonConfig> parseRmLessonConfig(Course course, String path, Map<String, String> nameMap);

	/**
	 * 课程数据导入
	 * @param path 课程资源所在地址
	 * @param envReqs topo与课时绑定数据 key=课时文件夹名称 value=topo编号
	 * @return
	 */
	Boolean rMCourseImport(String path, Map<String, String> envReqs, AuthContext authContext);
}
