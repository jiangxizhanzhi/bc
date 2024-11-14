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
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface LearnStatsMapper {

    /**
     * 用户统计查询
     *
     * @param users      用户
     * @param reportCode 报告代号
     * @param startTime  启动时间
     * @param endTime    结束时间
     * @return {@code List<UserStatsBo> }
     */
    List<UserStatsBo> userStatsSelect(@Param("user_ids") List<String> users, @Param("report_code") Integer reportCode, @Param("start_time") Date startTime, @Param("end_time") Date endTime);

    /**
     * 课时记录排
     *
     * @param users 用户
     * @param limit 限制
     * @return {@code List<JSONObject> }
     */
    List<JSONObject> lessonRecordRank(@Param("user_ids") List<String> users, @Param("limit") Integer limit);

    /**
     * 报告完成排名
     *
     * @param users      用户
     * @param reportCode 报告代号
     * @param limit      限制
     * @return {@code List<JSONObject> }
     */
    List<JSONObject> reportFinishRank(@Param("user_ids") List<String> users, @Param("report_code") Integer reportCode, @Param("limit") Integer limit);

    /**
     * 练习完成排
     *
     * @param users 用户
     * @param limit 限制
     * @return {@code List<JSONObject> }
     */
    List<JSONObject> practiceFinishRank(@Param("user_ids") List<String> users, @Param("limit") Integer limit);

    /**
     * 课程数量统计
     *
     * @param theoryType 理论类型
     * @param expType    exp 类型
     * @return {@code JSONObject }
     */
    JSONObject courseNumStats(@Param("theory_type") Integer theoryType, @Param("exp_type") Integer expType);

    /**
     * 学习数量统计
     *
     * @param resourceId 资源id
     * @param theoryType 理论类型
     * @param expType    exp 类型
     * @return {@code JSONObject }
     */
    JSONObject learnNumStats(@Param("user_id") String resourceId, @Param("theory_type") Integer theoryType, @Param("exp_type") Integer expType);

    /**
     * 练习数量统计
     *
     * @param resourceId 资源id
     * @param paperType  试卷类型
     * @return {@code JSONObject }
     */
    JSONObject practiceNumStats(@Param("user_id") String resourceId, @Param("paper_type") Integer paperType);

    /**
     * 学习课程统计
     *
     * @param page       分页
     * @param resourceId 资源id
     * @param theoryType 理论类型
     * @param expType    exp 类型
     * @param reportCode 报告代号
     * @return {@code IPage<PersonalStatsDetail4CourseBo> }
     */
    IPage<PersonalStatsDetail4CourseBo> learnCourseStats(Page<PersonalStatsDetail4CourseBo> page, @Param("user_id") String resourceId, @Param("theory_type") Integer theoryType, @Param("exp_type") Integer expType, @Param("report_code") Integer reportCode);

    /**
     * 类积分后
     *
     * @param users     用户
     * @param startTime 启动时间
     * @param endTime   结束时间
     * @return {@code List<JSONObject> }
     */
    List<JSONObject> afterClassIntegral(@Param("user_ids") List<String> users, @Param("start_time") Date startTime, @Param("end_time") Date endTime);

    /**
     * 课时完整
     *
     * @param users     用户
     * @param startTime 启动时间
     * @param endTime   结束时间
     * @return {@code List<JSONObject> }
     */
    List<JSONObject> lessonIntegral(@Param("user_ids") List<String> users, @Param("start_time") Date startTime, @Param("end_time") Date endTime);
}
