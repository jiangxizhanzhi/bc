package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.context.SpecialCourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;

@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {

	IPage<Course> listByPage(Page<Course> page, @Param("courseContext") CourseContext courseContext,
			@Param("courseIds") List<String> courseIds, @Param("isPublic") Boolean isPublic);

	List<String> notDeleteIdsByIds(@Param("courseIds") List<String> courseIds);

	List<String> selectByAuth(@Param("authContext") AuthContext authContext);

	List<String> selectSpecial(@Param("courseIds") List<String> courseIds,
			@Param("specialContext") SpecialCourseContext specialContext);

	List<String> selectIdsByCategory(@Param("courseIds") List<String> courseIds,
			@Param("categoryIds") List<String> categoryIds, @Param("isPublic") Boolean isPublic);

	/**
	 * 根据名称查询课程
	 * @param name
	 * @return
	 */
    String selectByName(@Param("name") String name);

	/**
	 * 根据课程编号查询
	 * @param courseNum
	 * @return
	 */
	Course selectByCourseNum(@Param("courseNum") String courseNum);

	List<Course> selectIdsByCourseName(@Param("courseNames") List<String> courseNames);

	List<Course> selectCourseByUserId(String userId);

	List<Course> selectCourseByOrgId(String orgId);

	void updateCourseHeat(@Param("heat") Integer heat,@Param("resourceId") String resourceId);
}
