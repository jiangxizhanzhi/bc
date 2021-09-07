package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.dao.context.AuthContext;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {

	Course selectByCourse(@Param("courseId") String courseId);

	/**
	 * 分页查询课程信息
	 * @param page
	 * @param courseContext 筛选条件
	 * @param authContext 访问权限条件
	 * @return
	 */
	IPage<Course> listByPage(@Param("page") Page page, @Param("courseContext") CourseContext courseContext,
			@Param("authContext") AuthContext authContext);

	List<String> notDeleteIdsByIds(@NotEmpty @Param("courseIds") List<String> courseIds);
}
