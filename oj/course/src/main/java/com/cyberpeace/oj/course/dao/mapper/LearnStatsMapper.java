package com.cyberpeace.oj.course.dao.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.service.bo.PersonalStatsDetail4CourseBo;
import com.cyberpeace.oj.course.service.bo.UserStatsBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 学习统计Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LearnStatsMapper` 的接口，它继承自 MyBatis-Plus 的 `Mapper` 接口。这个接口主要用于处理与学习统计数据相关的数据库操作。以下是对该代码的简要分析： 
 * ### 主要功能 
 * - **用户统计查询**：根据用户 ID 列表、报告代号、开始时间和结束时间查询用户的学习统计数据。 
 * - **课程记录排名**：根据用户 ID 列表和限制条件查询课程记录的排名。 
 * - **报告完成排名**：根据用户 ID 列表、报告代号和限制条件查询报告完成的排名。 
 * - **练习完成排名**：根据用户 ID 列表和限制条件查询练习完成的排名。 
 * - **课程数量统计**：根据理论类型和实验类型统计课程的数量。 
 * - **学习数量统计**：根据资源 ID、理论类型和实验类型统计学习的数量。 
 * - **实践数量统计**：根据资源 ID 和纸张类型统计实践的数量。 
 * - **课程统计信息**：分页查询用户在特定时间段内的课程学习统计数据。 
 * - **类积分后统计**：根据用户 ID 列表、开始时间和结束时间统计课后积分。 
 * - **课程积分统计**：根据用户 ID 列表、开始时间和结束时间统计课程积分。 
 * ### 关键点 
 * - **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表明这是一个 Spring 的数据访问对象（DAO）。 
 * - **参数**： 
 * - 方法参数使用了 `@Param` 注解来明确参数名称，方便在 SQL 映射文件中引用。 
 * - **返回值**： 
 * - 大多数方法返回 `List<JSONObject>` 或 `IPage<PersonalStatsDetail4CourseBo>`，其中 `JSONObject` 用于灵活地返回 JSON 格式的数据，而 `IPage<PersonalStatsDetail4CourseBo>` 用于分页查询结果。 
 * ### 总结 
 * `LearnStatsMapper` 是一个用于处理学习统计数据的接口，提供了多种查询和统计的方法，涵盖了用户、课程、报告和实践等多个方面的统计数据。这些方法通过 MyBatis 框架与数据库进行交互，实现了对学习数据的高效管理和查询。 
**/
@Mapper
@Repository
public interface LearnStatsMapper {

    /**
     * 用户统计查询
     *
     * @param users      用户
     * @param reportCode 报告代号
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return {@code List<UserStatsBo> }
     * @throws
     * @see List<String>
     * @see Integer
     * @see Date
     * @see Date
     * @see List<UserStatsBo>
     */
    List<UserStatsBo> userStatsSelect(@Param("user_ids") List<String> users, @Param("report_code") Integer reportCode, @Param("start_time") Date startTime, @Param("end_time") Date endTime);

    /**
     * 课程记录排名
     *
     * @param users 用户
     * @param limit 限制
     * @return {@code List<JSONObject> }
     * @throws
     * @see List<String>
     * @see Integer
     * @see List<JSONObject>
     */
    List<JSONObject> lessonRecordRank(@Param("user_ids") List<String> users, @Param("limit") Integer limit);

    /**
     * 报告完成排名
     *
     * @param users      用户
     * @param reportCode 报告代号
     * @param limit      限制
     * @return {@code List<JSONObject> }
     * @throws
     * @see List<String>
     * @see Integer
     * @see Integer
     * @see List<JSONObject>
     */
    List<JSONObject> reportFinishRank(@Param("user_ids") List<String> users, @Param("report_code") Integer reportCode, @Param("limit") Integer limit);

    /**
     * 练习完成排名
     *
     * @param users 用户
     * @param limit 限制
     * @return {@code List<JSONObject> }
     * @throws
     * @see List<String>
     * @see Integer
     * @see List<JSONObject>
     */
    List<JSONObject> practiceFinishRank(@Param("user_ids") List<String> users, @Param("limit") Integer limit);

    /**
     * 课程数量统计
     *
     * @param theoryType 理论类型
     * @param expType    exp 类型
     * @return {@code JSONObject }
     * @throws
     * @see Integer
     * @see Integer
     * @see JSONObject
     */
    JSONObject courseNumStats(@Param("theory_type") Integer theoryType, @Param("exp_type") Integer expType);

    /**
     * 学习数量统计
     *
     * @param resourceId 资源id
     * @param theoryType 理论类型
     * @param expType    exp 类型
     * @return {@code JSONObject }
     * @throws
     * @see String
     * @see Integer
     * @see Integer
     * @see JSONObject
     */
    JSONObject learnNumStats(@Param("user_id") String resourceId, @Param("theory_type") Integer theoryType, @Param("exp_type") Integer expType);

    /**
     * 实践数量统计
     *
     * @param resourceId 资源id
     * @param paperType  纸张类型
     * @return {@code JSONObject }
     * @throws
     * @see String
     * @see Integer
     * @see JSONObject
     */
    JSONObject practiceNumStats(@Param("user_id") String resourceId, @Param("paper_type") Integer paperType);

    /**
     * 了解课程统计信息
     *
     * @param page       分页
     * @param resourceId 资源id
     * @param theoryType 理论类型
     * @param expType    exp 类型
     * @param reportCode 报告代号
     * @return {@code IPage<PersonalStatsDetail4CourseBo> }
     * @throws
     * @see Page
     * @see String
     * @see Integer
     * @see Integer
     * @see Integer
     * @see IPage<PersonalStatsDetail4CourseBo>
     */
    IPage<PersonalStatsDetail4CourseBo> learnCourseStats(Page page, @Param("user_id") String resourceId, @Param("theory_type") Integer theoryType, @Param("exp_type") Integer expType, @Param("report_code") Integer reportCode);

    /**
     * 类积分后
     *
     * @param users     用户
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code List<JSONObject> }
     * @throws
     * @see List<String>
     * @see Date
     * @see Date
     * @see List<JSONObject>
     */
    List<JSONObject> afterClassIntegral(@Param("user_ids") List<String> users, @Param("start_time") Date startTime, @Param("end_time") Date endTime);

    /**
     * 课程积分
     *
     * @param users     用户
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code List<JSONObject> }
     * @throws
     * @see List<String>
     * @see Date
     * @see Date
     * @see List<JSONObject>
     */
    List<JSONObject> lessonIntegral(@Param("user_ids") List<String> users, @Param("start_time") Date startTime, @Param("end_time") Date endTime);
}

