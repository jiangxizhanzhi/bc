package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Mapper
@Repository
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    List<CourseCategory> selectChildrenByCode(@Param("code") String code);

    List<CourseCategory> selectFirstLevel();

    List<CourseCategory> selectAll();

    IPage<CourseCategory> pageBySearch(Page<CourseCategory> page, @Param("pattern") String pattern);

    CourseCategory selectByName(@Param("name") String name);
}
