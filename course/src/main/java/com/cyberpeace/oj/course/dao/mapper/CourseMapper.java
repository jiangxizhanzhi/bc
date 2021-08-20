package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

	IPage<Course> listByPage(Page page, @Param("eBook") Integer eBook,
							  @Param("direction") String direction, @Param("difficulty") Integer difficulty,
							  @Param("pattern") String pattern);

	List<String> notDeleteIdsByIds(@NotEmpty @Param("courseIds") List<String> courseIds);
}
