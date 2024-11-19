package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * 课程类别Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    /**
     * 查询子级由代号
     *
     * @param code 代号
     * @return {@code List<CourseCategory> }
     * @throws
     * @see String
     * @see List<CourseCategory>
     */
    List<CourseCategory> selectChildrenByCode(@Param("code") String code);

    /**
     * 查询第一水平
     *
     * @return {@code List<CourseCategory> }
     * @throws
     * @see List<CourseCategory>
     */
    List<CourseCategory> selectFirstLevel();

    /**
     * 查询全部
     *
     * @return {@code List<CourseCategory> }
     * @throws
     * @see List<CourseCategory>
     */
    List<CourseCategory> selectAll();

    /**
     * 分页由搜索
     *
     * @param page    分页
     * @param pattern 模式
     * @return {@code IPage<CourseCategory> }
     * @throws
     * @see Page<CourseCategory>
     * @see String
     * @see IPage<CourseCategory>
     */
    IPage<CourseCategory> pageBySearch(Page<CourseCategory> page, @Param("pattern") String pattern);

    /**
     * 查询由名称
     *
     * @param name 名称
     * @return {@code CourseCategory }
     * @throws
     * @see String
     * @see CourseCategory
     */
    CourseCategory selectByName(@Param("name") String name);
}
