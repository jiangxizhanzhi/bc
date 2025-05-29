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
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseCategoryMapper` 的接口，它继承了 `BaseMapper<CourseCategory>` 接口。这个接口用于操作数据库中的 `CourseCategory` 实体类。以下是对该代码的简要分析： 
 * ### 主要组件 
 * 1. **包声明**: 
 * - `com.cyberpeace.oj.course.dao.mapper`: 这是该接口所在的包。 
 * 2. **导入的类**: 
 * - 导入了一些常用的 MyBatis 和 Spring 相关的类，例如 `IPage`, `Page`, `BaseMapper`, 等等。 
 * 3. **注解**: 
 * - `@Mapper`: 表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`: 表明这是一个数据访问层的组件。 
 * 4. **接口定义**: 
 * - `CourseCategoryMapper`: 继承自 `BaseMapper<CourseCategory>`，这是 MyBatis-Plus 提供的一个基础接口，提供了基本的 CRUD 操作。 
 * 5. **方法定义**: 
 * - `selectChildrenByCode(String code)`: 根据给定的 `code` 查询其子分类。 
 * - `selectFirstLevel()`: 查询所有顶级分类。 
 * - `selectAll()`: 查询所有分类。 
 * - `pageBySearch(Page page, String pattern)`: 根据搜索模式进行分页查询。 
 * - `selectByName(String name)`: 根据名称查询分类。 
 * ### 总结 
 * - **功能**: 该接口提供了针对 `CourseCategory` 实体类的各种查询操作，包括分页、按名称查询、按层级查询等。 
 * - **技术栈**: 使用了 MyBatis-Plus 和 Spring 框架来简化数据库操作。 
 * - **设计**: 通过继承 `BaseMapper`，减少了重复的 CRUD 代码，同时增加了特定业务需求的方法。 
 * 整体来说，这个接口是为了方便管理课程分类而设计的，通过提供多种查询方式来满足不同的业务场景。 
**/
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
     * 查询都
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
     * @see Page
     * @see String
     * @see IPage<CourseCategory>
     */
    IPage<CourseCategory> pageBySearch(Page page, @Param("pattern") String pattern);

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

