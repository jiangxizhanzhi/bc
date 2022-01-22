package com.cyberpeace.oj.course.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;
import org.springframework.web.multipart.MultipartFile;

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

	/**
	 * 上传课时讲义 如果为makdown则解析 数据绑定课时
	 * @param courseLesson
	 * @param handout
	 */
	void uploadHandout(CourseLesson courseLesson, MultipartFile handout);

	/**
	 * 上传视频文件
	 * @param courseLesson
	 * @param video
	 */
	void uploadVideo(CourseLesson courseLesson, MultipartFile video);

	/**
	 * 课时批量删除
	 * @param resourceIds
	 * @param authContext
	 * @return
	 */
	Integer batchDelete(String courseId, List<String> resourceIds, AuthContext authContext);

	/**
	 * 导出课时数据
	 * @param courseLesson
	 * @param filePathMap
	 * @param destFilePath
	 * @return
	 */
	LessonDumpLoadBo handlerLessonDump(CourseLesson courseLesson, Map<String, String> filePathMap,
			String destFilePath);

	/**
	 * 加载课时数据
	 * @param courseId 课程编号
	 * @param lessonDumpLoadBo 课时信息数据
	 * @param filePath 解压后文件存储路径
	 * @param filePathMap 文件映射关系
	 * @return
	 */
	CourseLesson loadLesson(String courseId, LessonDumpLoadBo lessonDumpLoadBo, String filePath,
			Map<String, String> filePathMap);

	/**
	 * 创建远程操作日志
	 * @param lessonInfoBo
	 */
	void createMpLog(LessonInfoBo lessonInfoBo);
}
