package com.cyberpeace.oj.course.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.controller.req.PracticeSubmitReq;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;

public interface CourseLessonService extends IService<CourseLesson> {

	/**
	 * 根据课程编号和课时编号查询课时基本信息
	 * @param courseId
	 * @param lessonId
	 * @return
	 */
	LessonInfoBo getLessonDetail(String courseId, String lessonId, AuthContext authContext);

	/**
	 * 查询后台课时详情数据
	 * @param courseId
	 * @param lessonId
	 * @return
	 */
	AdminLessonBo selectByCourseAndLesson(String courseId, String lessonId);

	/**
	 * 课时数据新增
	 * @param courseLesson
	 * @param ojSceneConfig
	 * @param practiceList
	 * @return
	 */
	CourseLesson addLesson(CourseLesson courseLesson, JSONObject ojSceneConfig,
			List<PaperPracticeContext> practiceList);

	/**
	 * 课时数据修改
	 * @param courseLesson
	 * @param ojSceneConfig
	 * @param practiceList
	 * @return
	 */
	CourseLesson updateLesson(CourseLesson courseLesson, JSONObject ojSceneConfig,
			List<PaperPracticeContext> practiceList);

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
	Boolean batchPublicPractice(List<String> resourceIds, Boolean isPublic);

	/**
	 * 课时导入
	 * @param courseId
	 * @param lessonIds
	 * @return
	 */
	Boolean importLessons(String courseId, List<String> lessonIds);

	/**
	 * 课后练习答案校验
	 * @param answerContext
	 * @param authContext
	 * @param answers
	 * @return
	 */
	EvaluateResult submitLessonPractice(SubmitAnswerContext answerContext,
			List<PracticeAnswerContext> answers, AuthContext authContext);

	/**
	 * 课后练习数据查询
	 * @param courseId
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	LessonPracticeBo practiceByLesson(String courseId, String lessonId, AuthContext authContext);

	/**
	 * 后台课后练习查询
	 * @param courseId
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	List<AdminLessonPracticeBo> adminLessonPractice(String courseId, String lessonId,
			AuthContext authContext);

	/**
	 * 新增和修改练习数据
	 * @param resourceId
	 * @param practiceList
	 * @return
	 */
	Boolean addLessonPractice(String resourceId, List<PaperPracticeContext> practiceList);

	/**
	 * 查询topo和场景数据信息
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	TopoSceneDataBo getTopoScene(String lessonId, AuthContext authContext);
}
