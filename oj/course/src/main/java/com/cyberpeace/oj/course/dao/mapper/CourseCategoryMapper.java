package com.cyberpeace.oj.course.dao.mapper;

import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Mapper
@Repository
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    CourseCategory selectById(@Param("categoryId") String categoryId);

    List<CourseCategory> selectChildrenByCode(@Param("code") String code);

    List<CourseCategory> selectFirstLevel();
}
