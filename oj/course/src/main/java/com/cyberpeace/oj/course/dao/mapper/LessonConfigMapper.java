package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LessonConfigMapper extends BaseMapper<LessonConfig> {

	LessonConfig getFirstNode(@Param("courseId") String courseId);

	List<LessonConfig> listByParent(@Param("parentId") String parentId);

	List<LessonConfig> selectByCourse(@Param("courseId") String courseId);

	LessonConfig selectLastByParent(@Param("courseId") String courseId);

	int deleteByLessonIds(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 根据课时编号查询
	 * @param lessonIds
	 * @return
	 */
	List<LessonConfig> selectByLessonIds(@Param("courseId") String courseId,
			@Param("lessonIds") List<String> lessonIds);
}
