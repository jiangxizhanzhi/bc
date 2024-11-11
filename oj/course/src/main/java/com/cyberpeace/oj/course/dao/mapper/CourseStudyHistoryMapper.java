package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.CourseStudyHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程研究历史Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
public interface CourseStudyHistoryMapper extends BaseMapper<CourseStudyHistory> {

	/**
	 * 查询由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<CourseStudyHistory> }
	 */
	List<CourseStudyHistory> selectByUserId(@Param("userId") String userId);

	/**
	 * 查询由课程id和用户id
	 *
	 * @param courseId 课程id
	 * @param userId   用户id
	 * @return {@code CourseStudyHistory }
	 */
	CourseStudyHistory selectByCourseIdAndUserId(@Param("courseId") String courseId,@Param("userId") String userId);

}
