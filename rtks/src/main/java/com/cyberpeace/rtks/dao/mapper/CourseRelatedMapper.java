package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.CourseRelated;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
@Repository
public interface CourseRelatedMapper extends BaseMapper<CourseRelated> {

	void deleteByRelatedId(@Param("ids") List<String> ids);
}
