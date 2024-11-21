package com.cyberpeace.oj.course.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.service.bo.*;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;
import com.cyberpeace.oj.practice.service.bo.EvaluateResult;
import com.cyberpeace.oj.practice.service.bo.PaperPracticeContext;

import java.util.List;
import java.util.Map;

/**
 * 课程课Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface CourseLessonService extends IService<CourseLesson> {

	/**
	 * 根据课程编号和课时编号查询课时基本信息
	 *
	 * @param courseId
	 * @param lessonId
	 * @param roomId      房间id
	 * @param learnedType 学习类型
	 * @param authContext auth 上下文
	 * @return
	 */
	LessonInfoBo getLessonDetail(String courseId, String lessonId, String roomId, Integer learnedType, AuthContext authContext);

	/**
	 * 查询后台课时详情数据
	 *
	 * @param courseId
	 * @param lessonId
	 * @return
	 */
	AdminLessonBo selectByCourseAndLesson(String courseId, String lessonId);

	/**
	 * 课时数据新增
	 *
	 * @param courseLesson
	 * @param ojSceneConfig
	 * @param practiceList
	 * @return
	 */
	CourseLesson addLesson(CourseLesson courseLesson, JSONObject ojSceneConfig,
			List<PaperPracticeContext> practiceList);

	/**
	 * 课时数据修改
	 *
	 * @param courseLesson
	 * @param ojSceneConfig
	 * @param practiceList
	 * @return
	 */
	CourseLesson updateLesson(CourseLesson courseLesson, JSONObject ojSceneConfig,
			List<PaperPracticeContext> practiceList);

	/**
	 * 后台课时列表查询
	 *
	 * @param page
	 * @param context
	 * @param isWeb
	 * @return
	 */
	IPage<AdminLessonListBo> pageByContext(Page page, LessonContext context, boolean isWeb);

	/**
	 * 课后练习公开与隐藏
	 *
	 * @param resourceIds
	 * @param isPublic
	 * @return
	 */
	Boolean batchPublicPractice(List<String> resourceIds, Boolean isPublic);

	/**
	 * 课时导入
	 *
	 * @param courseId
	 * @param lessonIds
	 * @return
	 */
	Boolean importLessons(String courseId, List<String> lessonIds);

	/**
	 * 课后练习答案校验
	 *
	 * @param answerContext
	 * @param answers
	 * @param authContext
	 * @return
	 */
	EvaluateResult submitLessonPractice(SubmitAnswerContext answerContext,
			List<PracticeAnswerContext> answers, AuthContext authContext);

	/**
	 * 课后练习数据查询
	 *
	 * @param courseId
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	LessonPracticeBo practiceByLesson(String courseId, String lessonId, AuthContext authContext);

	/**
	 * 后台课后练习查询
	 *
	 * @param courseId
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	List<AdminLessonPracticeBo> adminLessonPractice(String courseId, String lessonId,
			AuthContext authContext);

	/**
	 * 新增和修改练习数据
	 *
	 * @param resourceId
	 * @param practiceList
	 * @return
	 */
	Boolean addLessonPractice(String resourceId, List<PaperPracticeContext> practiceList);

	/**
	 * 课后练习评分
	 *
	 * @param resourceId
	 * @param userId
	 * @param level
	 * @param authContext
	 * @return
	 */
	Boolean markLessonPractice(String resourceId, String userId, Integer level, AuthContext authContext);


	/**
	 * 课后练习下发
	 *
	 * @param resourceId
	 * @param authId
	 * @param authContext
	 * @return
	 */
	Boolean releaseLessonPractice(String resourceId, String authId, AuthContext authContext);

	/**
	 * 上传课时讲义 如果为makdown则解析 数据绑定课时
	 *
	 * @param courseLesson
	 * @param handout      讲义
	 */
	void uploadHandout(CourseLesson courseLesson, String handout);

	/**
	 * 上传视频文件
	 *
	 * @param courseLesson
	 * @param video        视频
	 */
	void uploadVideo(CourseLesson courseLesson, String video);

	/**
	 * 课时批量删除
	 *
	 * @param courseId    课程id
	 * @param resourceIds
	 * @param authContext
	 * @return
	 */
	Integer batchDelete(String courseId, List<String> resourceIds, AuthContext authContext);

	/**
	 * 批量删除
	 *
	 * @param resourceIds 资源id列表
	 */
	void batchDelete(List<String> resourceIds);

	/**
	 * 导出课时数据
	 *
	 * @param courseLesson
	 * @param filePathMap
	 * @param destFilePath
	 * @return
	 */
	LessonDumpLoadBo handlerLessonDump(CourseLesson courseLesson, Map<String, String> filePathMap,
			String destFilePath);

	/**
	 * 加载课时数据
	 *
	 * @param courseId         课程编号
	 * @param lessonDumpLoadBo 课时信息数据
	 * @param filePath         解压后文件存储路径
	 * @param filePathMap      文件映射关系
	 * @return
	 */
	CourseLesson loadLesson(String courseId, LessonDumpLoadBo lessonDumpLoadBo, String filePath,
			Map<String, String> filePathMap);

	/**
	 * 创建远程操作日志
	 *
	 * @param lessonInfoBo 课程信息 bo
	 */
	void createMpLog(LessonInfoBo lessonInfoBo);

	/**
	 * 根据课时列表查询启动的场景
	 *
	 * @param lessonList
	 * @param authContext
	 * @return
	 */
	List<OjLocalScene> lessonSceneByLessons(List<CourseLesson> lessonList, AuthContext authContext);

	/**
	 * 根据课时查询查询课后练习场景
	 *
	 * @param lessonList  课时列表
	 * @param authContext 权限
	 * @return
	 */
	List<OjLocalScene> lessonPracticeSceneByLessons(List<CourseLesson> lessonList, AuthContext authContext);

	/**
	 * 查询课时场景
	 *
	 * @param lessonList
	 * @param authContext
	 * @return
	 */
	List<OjLocalScene> selectLessonAllScene(List<CourseLesson> lessonList, AuthContext authContext);

	/**
	 * 获取课程下所有拓扑 包含课后练习
	 *
	 * @param lessonList
	 * @return
	 */
	List<String> selectLessonAllTopo(List<CourseLesson> lessonList);

	/**
	 * 清除场景和拓扑数据
	 *
	 * @param scenes
	 * @param topos  拓扑
	 */
	void clearSceneAndTopos(List<OjLocalScene> scenes, List<String> topos);

	/**
	 * 第 2 课 管理员 bo
	 *
	 * @param lessonList 课程列表
	 * @return {@code List<AdminLessonListBo> }
	 */
	List<AdminLessonListBo> lesson2AdminBo(List<CourseLesson> lessonList);

	/**
	 * 查询topo和场景数据信息
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	TopoSceneDataBo getTopoScene(String lessonId, String roomId, AuthContext authContext);
}
