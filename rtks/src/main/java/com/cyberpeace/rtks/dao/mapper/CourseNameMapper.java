package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.CourseName;

/**
 * 课程名称Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface CourseNameMapper extends BaseMapper<CourseName> {

	/**
	 * 过滤id
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<String> }
	 */
	List<String> filterId(@Param("courseIds") List<String> courseIds);
}
