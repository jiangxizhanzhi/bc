package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import com.cyberpeace.oj.course.service.bo.AddLessonConfigContext;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.cyberpeace.oj.course.service.bo.LessonConfigDumpBo;

import java.util.List;

/**
 * 课时配置Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface LessonConfigService extends IService<LessonConfig> {

	/**
	 * 根据课程编号用户编号查询课程目录
	 *
	 * @param courseId    课程编号
	 * @param authContext
	 * @return
	 */
	LessonConfigBo getLessonConfig(String courseId, AuthContext authContext);

	/**
	 * 更新课时配置
	 *
	 * @param configBo
	 * @return
	 */
	LessonConfigBo updateLessonConfig(LessonConfigBo configBo);

	/**
	 * 根据课时数据 组装课程目录数据
	 *
	 * @param configContext
	 * @return
	 */
	LessonConfig packageLessonConfig(AddLessonConfigContext configContext);

	/**
	 * 导出课程数据目录
	 *
	 * @param courseId
	 * @param authContext
	 * @return
	 */
	LessonConfigDumpBo dumpLessonConfig(String courseId, AuthContext authContext);

	/**
	 * 章节数据
	 *
	 * @param lessonConfig
	 * @param courseId
	 * @param courseLessons
	 * @return
	 */
	List<LessonConfig> loadLessonConfig(LessonConfigDumpBo lessonConfig, String courseId,
			List<CourseLesson> courseLessons);

	/**
	 * 有效冲突
	 *
	 * @param courseId 课程id
	 */
	void validConflict(String courseId);

	/**
	 * 有效课时和章
	 *
	 * @param courseId 课程id
	 */
	void validLessonAndChapter(String courseId);
}
