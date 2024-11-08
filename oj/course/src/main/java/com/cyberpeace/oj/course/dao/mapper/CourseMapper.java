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

/**
 * 课程Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {

	/**
	 * 列表由分页
	 *
	 * @param page          分页
	 * @param courseContext 课程背景
	 * @param courseIds     课程id列表
	 * @param isPublic      是公共
	 * @return {@code IPage<Course> }
	 */
	IPage<Course> listByPage(Page page, @Param("courseContext") CourseContext courseContext,
			@Param("courseIds") List<String> courseIds, @Param("isPublic") Boolean isPublic);

	/**
	 * 不删除id列表由id列表
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<String> }
	 */
	List<String> notDeleteIdsByIds(@Param("courseIds") List<String> courseIds);

	/**
	 * 查询由认证
	 *
	 * @param authContext auth 上下文
	 * @return {@code List<String> }
	 */
	List<String> selectByAuth(@Param("authContext") AuthContext authContext);

	/**
	 * 查询特殊
	 *
	 * @param courseIds      课程id列表
	 * @param specialContext 特殊上下文
	 * @return {@code List<String> }
	 */
	List<String> selectSpecial(@Param("courseIds") List<String> courseIds,
			@Param("specialContext") SpecialCourseContext specialContext);

	/**
	 * 查询id列表由类别
	 *
	 * @param courseIds   课程id列表
	 * @param categoryIds 类别id列表
	 * @param isPublic    是公共
	 * @return {@code List<String> }
	 */
	List<String> selectIdsByCategory(@Param("courseIds") List<String> courseIds,
			@Param("categoryIds") List<String> categoryIds, @Param("isPublic") Boolean isPublic);

	/**
	 * 根据名称查询课程
	 *
	 * @param name
	 * @return
	 */
	String selectByName(@Param("name") String name);

	/**
	 * 根据课程编号查询
	 *
	 * @param courseNum
	 * @return
	 */
	Course selectByCourseNum(@Param("courseNum") String courseNum);

	/**
	 * 查询id列表由课程名称
	 *
	 * @param courseNames 课程名称
	 * @return {@code List<Course> }
	 */
	List<Course> selectIdsByCourseName(@Param("courseNames") List<String> courseNames);

	/**
	 * 查询课程由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<Course> }
	 */
	List<Course> selectCourseByUserId(String userId);

	/**
	 * 查询课程由组织id
	 *
	 * @param orgId 组织id
	 * @return {@code List<Course> }
	 */
	List<Course> selectCourseByOrgId(String orgId);

	/**
	 * 更新 Course Heat
	 *
	 * @param heat       热
	 * @param resourceId 资源id
	 */
	void updateCourseHeat(@Param("heat") Integer heat,@Param("resourceId") String resourceId);

	/**
	 * 查询时间
	 *
	 * @return {@code List<String> }
	 */
	List<String> selectTime();
}
