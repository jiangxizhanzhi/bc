package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.CourseName;

/**
 * @Entity com.cyberpeace.rtks.dao.entity.CourseName
 */
@Mapper
@Repository
public interface CourseNameMapper extends BaseMapper<CourseName> {

	List<String> filterId(List<String> courseIds);
}
