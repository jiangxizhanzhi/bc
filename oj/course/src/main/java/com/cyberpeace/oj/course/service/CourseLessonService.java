package com.cyberpeace.oj.course.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.AdminLessonListBo;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;

public interface CourseLessonService extends IService<CourseLesson> {

	/**
	 * 根据课程编号和课时编号查询课时基本信息
	 * @param courseId
	 * @param lessonId
	 * @return
	 */
	LessonInfoBo getLessonDetail(String userId, String courseId, String lessonId);

	/**
	 * 查询后台课时详情数据
	 * @param courseId
	 * @param lessonId
	 * @return
	 */
	AdminLessonBo selectByCourseAndLesson(String courseId, String lessonId);

	/**
	 * 课时新增或者修改
	 * @param courseLesson
	 * @param ojSceneConfig
	 * @param practiceList
	 * @return
	 */
	CourseLesson addOrUpdate(CourseLesson courseLesson, String ojSceneConfig, List<PaperPracticeContext> practiceList);

	/**
	 * 后台课时列表查询
	 * @param page
	 * @param context
	 * @param isWeb
	 * @return
	 */
    IPage<AdminLessonListBo> pageByContext(Page page, LessonContext context, boolean isWeb);

	/**
	 * 课后练习公开与隐藏
	 * @param resourceIds
	 * @param isPublic
	 * @return
	 */
	Integer batchPublicPractice(List<String> resourceIds, Boolean isPublic);

	/**
	 * 课时导入
	 * @param courseId
	 * @param lessonIds
	 * @return
	 */
	Boolean importLessons(String courseId, List<String> lessonIds);
}
