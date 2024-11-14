package com.cyberpeace.oj.course.service;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.service.bo.CombinedCourseImportContext;

import java.util.List;
import java.util.Map;

/**
 * 课程导入Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface CourseImportService {

	/**
	 * 根据导入数据路径获取课程信息
	 *
	 * @param path
	 * @return
	 */
	Course parseRmCourse(String path);

	/**
	 * 组装资源导入的课时信息
	 *
	 * @param course
	 * @param path
	 * @param envReqs
	 * @param nameMap 名称映射
	 * @return
	 */
	List<CourseLesson> parseRmLesson(Course course, String path, Map<String, String> envReqs,
			Map<String, String> nameMap);

	/**
	 * 组装课时路径信息
	 *
	 * @param course  课程信息
	 * @param path    课程文件地址
	 * @param nameMap key=名称 value=课时编号
	 * @return
	 */
	List<LessonConfig> parseRmLessonConfig(Course course, String path, Map<String, String> nameMap);

	/**
	 * 课程数据导入
	 *
	 * @param path             课程资源所在地址
	 * @param envReqs          topo与课时绑定数据 key=课时文件夹名称 value=topo编号
	 * @param operationEnvReqs
	 * @param authContext      auth 上下文
	 * @return
	 */
	Boolean rMCourseImport(String path, Map<String, String> envReqs, Map<String, Map<String, String>> operationEnvReqs, AuthContext authContext);

	/**
	 * 组合课程数据导入
	 *
	 * @param courseInfos 课时资源所在地址
	 * @param authContext auth 上下文
	 * @return
	 */
	Boolean rMCombinedCourseImport(List<CombinedCourseImportContext> courseInfos, AuthContext authContext);

	/**
	 * 用于课程导入失败后，将topo进行删除操作
	 *
	 * @param envReqs
	 * @param operationEnvReqs 实操环境要求
	 */
	void destroyFailedTopo(Map<String, String> envReqs, Map<String, Map<String, String>> operationEnvReqs);
}
