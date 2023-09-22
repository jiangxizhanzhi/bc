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

@Mapper
@Repository
public interface LearnStatsMapper {

    List<UserStatsBo> userStatsSelect(@Param("user_ids") List<String> users, @Param("report_code") Integer reportCode, @Param("start_time") Date startTime, @Param("end_time") Date endTime);

    List<JSONObject> lessonRecordRank(@Param("user_ids") List<String> users, @Param("limit") Integer limit);

    List<JSONObject> reportFinishRank(@Param("user_ids") List<String> users, @Param("report_code") Integer reportCode, @Param("limit") Integer limit);

    List<JSONObject> practiceFinishRank(@Param("user_ids") List<String> users, @Param("limit") Integer limit);

    JSONObject courseNumStats(@Param("theory_type") Integer theoryType, @Param("exp_type") Integer expType);

    JSONObject learnNumStats(@Param("user_id") String resourceId, @Param("theory_type") Integer theoryType, @Param("exp_type") Integer expType);

    JSONObject practiceNumStats(@Param("user_id") String resourceId, @Param("paper_type") Integer paperType);

    IPage<PersonalStatsDetail4CourseBo> learnCourseStats(Page<PersonalStatsDetail4CourseBo> page, @Param("user_id") String resourceId, @Param("theory_type") Integer theoryType, @Param("exp_type") Integer expType, @Param("report_code") Integer reportCode);

    List<JSONObject> afterClassIntegral(@Param("user_ids") List<String> users, @Param("start_time") Date startTime, @Param("end_time") Date endTime);

    List<JSONObject> lessonIntegral(@Param("user_ids") List<String> users, @Param("start_time") Date startTime, @Param("end_time") Date endTime);
}
