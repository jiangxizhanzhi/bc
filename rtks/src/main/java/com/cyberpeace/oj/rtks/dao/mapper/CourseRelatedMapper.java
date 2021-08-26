package com.cyberpeace.oj.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.rtks.dao.entity.CourseRelated;

@Mapper
@Repository
public interface CourseRelatedMapper extends BaseMapper<CourseRelated> {

	void deleteByRelatedId(@Param("ids") List<String> ids);
}
