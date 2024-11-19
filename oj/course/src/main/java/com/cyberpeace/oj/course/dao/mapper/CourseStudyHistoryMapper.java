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
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface CourseStudyHistoryMapper extends BaseMapper<CourseStudyHistory> {

	/**
	 * 查询由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<CourseStudyHistory> }
	 * @throws
	 * @see String
	 * @see List<CourseStudyHistory>
	 */
	List<CourseStudyHistory> selectByUserId(@Param("userId") String userId);

	/**
	 * 查询由课程id和用户id
	 *
	 * @param courseId 课程id
	 * @param userId   用户id
	 * @return {@code CourseStudyHistory }
	 * @throws
	 * @see String
	 * @see String
	 * @see CourseStudyHistory
	 */
	CourseStudyHistory selectByCourseIdAndUserId(@Param("courseId") String courseId,@Param("userId") String userId);

}
