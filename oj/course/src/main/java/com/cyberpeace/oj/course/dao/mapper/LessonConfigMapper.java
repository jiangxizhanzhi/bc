package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课时配置Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface LessonConfigMapper extends BaseMapper<LessonConfig> {

	/**
	 * 获取课程节点
	 *
	 * @param courseId
	 * @return
	 * @throws
	 * @see String
	 * @see LessonConfig
	 */
	LessonConfig getCourseNode(@Param("courseId") String courseId);

	/**
	 * 根据父节点获取目录配置信息
	 *
	 * @param parentId
	 * @return
	 * @throws
	 * @see String
	 * @see List<LessonConfig>
	 */
	List<LessonConfig> listByParent(@Param("parentId") String parentId);

	/**
	 * 根据课程查询所有课程目录配置
	 *
	 * @param courseId
	 * @return
	 * @throws
	 * @see String
	 * @see List<LessonConfig>
	 */
	List<LessonConfig> selectByCourse(@Param("courseId") String courseId);

	/**
	 * 课程下的最后一个课时 第一级目录
	 *
	 * @param rootId
	 * @return
	 * @throws
	 * @see String
	 * @see LessonConfig
	 */
	LessonConfig selectLast(@Param("rootId") String rootId);

	/**
	 * 根据课时编号删除课时目录配置
	 *
	 * @param lessonIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see int
	 */
	int deleteByLessonIds(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 根据课时编号查询
	 *
	 * @param courseId  课程id
	 * @param lessonIds
	 * @return
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<LessonConfig>
	 */
	List<LessonConfig> selectByLessonIds(@Param("courseId") String courseId,
			@Param("lessonIds") List<String> lessonIds);

	/**
	 * 删除课程目录
	 *
	 * @param courseId 课程id
	 * @return int
	 * @throws
	 * @see String
	 * @see int
	 */
	int deleteByCourse(String courseId);

	/**
	 * 批量删除课程目录
	 *
	 * @param courseIds 课程id列表
	 * @throws
	 * @see List<String>
	 */
	void batchDelete(@Param("courseIds") List<String> courseIds);

	/**
	 * 更新课时目录
	 *
	 * @param lessonConfig 课时配置
	 * @throws
	 * @see LessonConfig
	 */
	void updateByCourseAndLesson(@Param("lessonConfig") LessonConfig lessonConfig);

	/**
	 * 根据课程和名称查询配置数据
	 *
	 * @param courseId
	 * @param name
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	String selectByCourseAndLessonName(@Param("courseId") String courseId,
			@Param("name") String name);


	/**
	 * 查询课时目录下是否存在课时
	 *
	 * @param courseId
	 * @return
	 * @throws
	 * @see String
	 * @see List<LessonConfig>
	 */
	List<LessonConfig> selectChapterLessonConflict(@Param("courseId") String courseId);

	/**
	 * 查询 同一个层次下，课时目录和章节目录同时存在的情况,  添加课时默认编排会出现该情况
	 *
	 * @param courseId
	 * @return
	 * @throws
	 * @see String
	 * @see List<LessonConfig>
	 */
	List<LessonConfig> selectIncludeLessonAndChapter(@Param("courseId") String courseId);

    List<LessonConfig> selectPublicLessonsByCourseIds(List<String> courseIds);
}
