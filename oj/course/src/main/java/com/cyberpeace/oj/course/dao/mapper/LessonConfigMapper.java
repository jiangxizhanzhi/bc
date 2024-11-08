package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课配置Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface LessonConfigMapper extends BaseMapper<LessonConfig> {

	/**
	 * 获取课程节点
	 *
	 * @param courseId
	 * @return
	 */
	LessonConfig getCourseNode(@Param("courseId") String courseId);

	/**
	 * 根据父节点获取目录配置信息
	 *
	 * @param parentId
	 * @return
	 */
	List<LessonConfig> listByParent(@Param("parentId") String parentId);

	/**
	 * 根据课程查询所有课程目录配置
	 *
	 * @param courseId
	 * @return
	 */
	List<LessonConfig> selectByCourse(@Param("courseId") String courseId);

	/**
	 * 课程下的最后一个课时 第一级目录
	 *
	 * @param rootId
	 * @return
	 */
	LessonConfig selectLast(@Param("rootId") String rootId);

	/**
	 * 根据课时编号删除课时目录配置
	 *
	 * @param lessonIds
	 * @return
	 */
	int deleteByLessonIds(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 根据课时编号查询
	 *
	 * @param courseId  课程id
	 * @param lessonIds
	 * @return
	 */
	List<LessonConfig> selectByLessonIds(@Param("courseId") String courseId,
			@Param("lessonIds") List<String> lessonIds);

	/**
	 * 删除课程目录
	 *
	 * @param courseId 课程id
	 * @return int
	 */
	int deleteByCourse(String courseId);

	/**
	 * 批量删除课程目录
	 *
	 * @param courseIds 课程id列表
	 */
	void batchDelete(@Param("courseIds") List<String> courseIds);

	/**
	 * 更新课时目录
	 *
	 * @param lessonConfig 课程配置
	 */
	void updateByCourseAndLesson(@Param("lessonConfig") LessonConfig lessonConfig);

	/**
	 * 根据课程和名称查询配置数据
	 *
	 * @param courseId
	 * @param name
	 * @return
	 */
	String selectByCourseAndLessonName(@Param("courseId") String courseId,
			@Param("name") String name);
}
