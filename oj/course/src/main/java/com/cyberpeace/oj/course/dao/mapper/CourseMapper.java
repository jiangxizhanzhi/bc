package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.context.SpecialCourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {

	Course selectByCourse(@Param("courseId") String courseId);

	/**
	 * 分页查询课程信息
	 * @param page
	 * @param courseContext 筛选条件
	 * @param courseIds
	 * @return
	 */
	IPage<Course> listByPage(Page page, @Param("courseContext") CourseContext courseContext,
			@Param("courseIds") List<String> courseIds);

	List<String> notDeleteIdsByIds(@Param("courseIds") List<String> courseIds);

	/**
	 * 根据前后端权限获取课程编号
	 * @param authContext
	 * @return
	 */
	List<String> selectByAuth(@Param("authContext") AuthContext authContext);

	/**
	 * 查询我的 or 系统 or 共享课程
	 * @param courseIds
	 * @param specialContext
	 * @return
	 */
	List<String> selectSpecial(@Param("courseIds") List<String> courseIds,
			@Param("specialContext") SpecialCourseContext specialContext);
}
