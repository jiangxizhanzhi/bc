package com.cyberpeace.oj.practice.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseDirectionMapper {

    String selectByName(@Param("categoryName") String categoryName);

    void create(@Param("categoryName") String categoryName, @Param("courseCategoryId") String courseCategoryId);
}
