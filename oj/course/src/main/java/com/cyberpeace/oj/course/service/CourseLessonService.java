/**
 * 这段代码定义了一个名为 `CourseLessonService` 的接口，该接口扩展了 `IService<CourseLesson>` 接口。这个服务接口主要用于处理与课程课时相关的各种操作。以下是该接口方法的简要分析： 
 * ### 方法概述 
 * 1. **获取课时详细信息** 
 * - `getLessonDetail`: 根据课程编号和课时编号查询课时基本信息。 
 * 2. **查询后台课时详情数据** 
 * - `selectByCourseAndLesson`: 查询后台课时详情数据。 
 * 3. **课时数据新增** 
 * - `addLesson`: 增加新的课时数据。 
 * 4. **课时数据修改** 
 * - `updateLesson`: 修改已有的课时数据。 
 * 5. **后台课时列表查询** 
 * - `pageByContext`: 分页查询后台课时列表。 
 * 6. **课后练习公开与隐藏** 
 * - `batchPublicPractice`: 批量设置课后练习的公开或隐藏状态。 
 * 7. **课时导入** 
 * - `importLessons`: 导入新的课时数据。 
 * 8. **课后练习答案校验** 
 * - `submitLessonPractice`: 校验课后练习的答案。 
 * 9. **课后练习数据查询** 
 * - `practiceByLesson`: 查询指定课时的课后练习数据。 
 * 10. **后台课后练习查询** 
 * - `adminLessonPractice`: 查询后台课后练习数据。 
 * 11. **新增和修改练习数据** 
 * - `addLessonPractice`: 新增或修改课后练习数据。 
 * 12. **课后练习评分** 
 * - `markLessonPractice`: 给课后练习打分。 
 * 13. **课后练习下发** 
 * - `releaseLessonPractice`: 下发课后练习。 
 * 14. **上传课时讲义** 
 * - `uploadHandout`: 上传课时讲义。 
 * 15. **上传视频文件** 
 * - `uploadVideo`: 上传视频文件。 
 * 16. **课时批量删除** 
 * - `batchDelete`: 批量删除课时数据。 
 * 17. **导出课时数据** 
 * - `handlerLessonDump`: 处理课时数据的导出。 
 * 18. **加载课时数据** 
 * - `loadLesson`: 加载课时数据。 
 * 19. **创建远程操作日志** 
 * - `createMpLog`: 创建远程操作日志。 
 * 20. **根据课时列表查询启动的场景** 
 * - `lessonSceneByLessons`: 根据课时列表查询启动的场景。 
 * 21. **根据课时查询课后练习场景** 
 * - `lessonPracticeSceneByLessons`: 根据课时查询课后练习场景。 
 * 22. **查询课时场景** 
 * - `selectLessonAllScene`: 查询课时场景。 
 * 23. **获取课程下所有拓扑** 
 * - `selectLessonAllTopo`: 获取课程下所有拓扑。 
 * 24. **清除场景和拓扑数据** 
 * - `clearSceneAndTopos`: 清除场景和拓扑数据。 
 * 25. **第 2 课管理员BO** 
 * - `lesson2AdminBo`: 将课程列表转换成管理员BO。 
 * 26. **查询topo和场景数据信息** 
 * - `getTopoScene`: 查询指定课时的topo和场景数据信息。 
 * 27. **计算视频时长** 
 * - `countVideo`: 计算视频时长。 
 * ### 总结 
 * 该接口提供了丰富的功能，包括课时的增删改查、课后练习管理、视频和讲义管理以及数据导出等功能。这些方法覆盖了从课时的基本信息管理到复杂的场景管理和数据处理，是一个全面的课程课时管理服务。 
**/
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
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
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
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Integer
	 * @see AuthContext
	 * @see LessonInfoBo
	 */
	LessonInfoBo getLessonDetail(String courseId, String lessonId, String roomId, Integer learnedType, AuthContext authContext);

	/**
	 * 查询后台课时详情数据
	 *
	 * @param courseId
	 * @param lessonId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see AdminLessonBo
	 */
	AdminLessonBo selectByCourseAndLesson(String courseId, String lessonId);

	/**
	 * 课时数据新增
	 *
	 * @param courseLesson
	 * @param ojSceneConfig
	 * @param practiceList
	 * @return
	 * @throws
	 * @see CourseLesson
	 * @see JSONObject
	 * @see List<PaperPracticeContext>
	 * @see CourseLesson
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
	 * @throws
	 * @see CourseLesson
	 * @see JSONObject
	 * @see List<PaperPracticeContext>
	 * @see CourseLesson
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
	 * @throws
	 * @see Page
	 * @see LessonContext
	 * @see boolean
	 * @see IPage<AdminLessonListBo>
	 */
	IPage<AdminLessonListBo> pageByContext(Page page, LessonContext context, boolean isWeb);

	/**
	 * 课后练习公开与隐藏
	 *
	 * @param resourceIds
	 * @param isPublic
	 * @return
	 * @throws
	 * @see List<String>
	 * @see Boolean
	 * @see Boolean
	 */
	Boolean batchPublicPractice(List<String> resourceIds, Boolean isPublic);

	/**
	 * 课时导入
	 *
	 * @param courseId
	 * @param lessonIds
	 * @return
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see Boolean
	 */
	Boolean importLessons(String courseId, List<String> lessonIds);

	/**
	 * 课后练习答案校验
	 *
	 * @param answerContext
	 * @param answers
	 * @param authContext
	 * @return
	 * @throws
	 * @see SubmitAnswerContext
	 * @see List<PracticeAnswerContext>
	 * @see AuthContext
	 * @see EvaluateResult
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
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see LessonPracticeBo
	 */
	LessonPracticeBo practiceByLesson(String courseId, String lessonId, AuthContext authContext);

	/**
	 * 后台课后练习查询
	 *
	 * @param courseId
	 * @param lessonId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see List<AdminLessonPracticeBo>
	 */
	List<AdminLessonPracticeBo> adminLessonPractice(String courseId, String lessonId,
			AuthContext authContext);

	/**
	 * 新增和修改练习数据
	 *
	 * @param resourceId
	 * @param practiceList
	 * @return
	 * @throws
	 * @see String
	 * @see List<PaperPracticeContext>
	 * @see Boolean
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
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean markLessonPractice(String resourceId, String userId, Integer level, AuthContext authContext);


	/**
	 * 课后练习下发
	 *
	 * @param resourceId
	 * @param authId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean releaseLessonPractice(String resourceId, String authId, AuthContext authContext);

	/**
	 * 上传课时讲义 如果为makdown则解析 数据绑定课时
	 *
	 * @param courseLesson
	 * @param handout      讲义
	 * @throws
	 * @see CourseLesson
	 * @see String
	 */
	void uploadHandout(CourseLesson courseLesson, String handout);

	/**
	 * 上传视频文件
	 *
	 * @param courseLesson
	 * @param video        视频
	 * @throws
	 * @see CourseLesson
	 * @see String
	 */
	void uploadVideo(CourseLesson courseLesson, String video);

	/**
	 * 课时批量删除
	 *
	 * @param courseId    课程id
	 * @param resourceIds
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see AuthContext
	 * @see Integer
	 */
	Integer batchDelete(String courseId, List<String> resourceIds, AuthContext authContext);

	/**
	 * 批量删除
	 *
	 * @param resourceIds 资源id列表
	 * @throws
	 * @see List<String>
	 */
	void batchDelete(List<String> resourceIds);

	/**
	 * 导出课时数据
	 *
	 * @param courseLesson
	 * @param filePathMap
	 * @param destFilePath
	 * @return
	 * @throws
	 * @see CourseLesson
	 * @see Map<String, String>
	 * @see String
	 * @see LessonDumpLoadBo
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
	 * @throws
	 * @see String
	 * @see LessonDumpLoadBo
	 * @see String
	 * @see Map<String, String>
	 * @see CourseLesson
	 */
	CourseLesson loadLesson(String courseId, LessonDumpLoadBo lessonDumpLoadBo, String filePath,
			Map<String, String> filePathMap);

	/**
	 * 创建远程操作日志
	 *
	 * @param lessonInfoBo 课程信息 bo
	 * @throws
	 * @see LessonInfoBo
	 */
	void createMpLog(LessonInfoBo lessonInfoBo);

	/**
	 * 根据课时列表查询启动的场景
	 *
	 * @param lessonList
	 * @param authContext
	 * @return
	 * @throws
	 * @see List<CourseLesson>
	 * @see AuthContext
	 * @see List<OjLocalScene>
	 */
	List<OjLocalScene> lessonSceneByLessons(List<CourseLesson> lessonList, AuthContext authContext);

	/**
	 * 根据课时查询查询课后练习场景
	 *
	 * @param lessonList  课时列表
	 * @param authContext 权限
	 * @return
	 * @throws
	 * @see List<CourseLesson>
	 * @see AuthContext
	 * @see List<OjLocalScene>
	 */
	List<OjLocalScene> lessonPracticeSceneByLessons(List<CourseLesson> lessonList, AuthContext authContext);

	/**
	 * 查询课时场景
	 *
	 * @param lessonList
	 * @param authContext
	 * @return
	 * @throws
	 * @see List<CourseLesson>
	 * @see AuthContext
	 * @see List<OjLocalScene>
	 */
	List<OjLocalScene> selectLessonAllScene(List<CourseLesson> lessonList, AuthContext authContext);

	/**
	 * 获取课程下所有拓扑 包含课后练习
	 *
	 * @param lessonList
	 * @return
	 * @throws
	 * @see List<CourseLesson>
	 * @see List<String>
	 */
	List<String> selectLessonAllTopo(List<CourseLesson> lessonList);

	/**
	 * 清除场景和拓扑数据
	 *
	 * @param scenes
	 * @param topos  拓扑
	 * @throws
	 * @see List<OjLocalScene>
	 * @see List<String>
	 */
	void clearSceneAndTopos(List<OjLocalScene> scenes, List<String> topos);

	/**
	 * 第 2 课 管理员 bo
	 *
	 * @param lessonList 课程列表
	 * @return {@code List<AdminLessonListBo> }
	 * @throws
	 * @see List<CourseLesson>
	 * @see List<AdminLessonListBo>
	 */
	List<AdminLessonListBo> lesson2AdminBo(List<CourseLesson> lessonList);

	/**
	 * 查询topo和场景数据信息
	 *
	 * @param lessonId
	 * @param roomId      房间id
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see TopoSceneDataBo
	 */
	TopoSceneDataBo getTopoScene(String lessonId, String roomId, AuthContext authContext);

	/**
	 * 计算视频时长
	 */
	void countVideo();
}
