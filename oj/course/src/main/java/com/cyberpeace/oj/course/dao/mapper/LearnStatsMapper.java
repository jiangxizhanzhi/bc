package com.cyberpeace.oj.course.dao.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.service.bo.CourseStatsBo;
import com.cyberpeace.oj.course.service.bo.LessonStatsBo;
import com.cyberpeace.oj.course.service.bo.UserStatsBo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface LearnStatsMapper {
    IPage<CourseStatsBo> courseStatsSelect(
            Page page,
            @Param("pattern") String pattern,
            @Param("category_id") String directionId,
            @Param("difficulty") Integer difficulty,
            @Param("report_code") Integer reportCode,
            @Param("lesson_code") Integer lessonCode
    );

    IPage<LessonStatsBo> lessonStatsSelect(
            Page page,
            @Param("course_id") String courseId,
            @Param("pattern") String pattern,
            @Param("type") Integer lessonType,
            @Param("report_code") Integer reportCode,
            @Param("lesson_code") Integer lessonCode
    );

    List<JSONObject> courseHeatRank();

    List<JSONObject> directionHeatRank();

    List<UserStatsBo> userStatsSelect(@Param("user_ids") List<String> users, @Param("report_code") Integer reportCode);

    List<JSONObject> lessonRecordRank(@Param("user_ids") List<String> users);

    List<JSONObject> reportFinishRank(@Param("user_ids") List<String> users, @Param("report_code") Integer reportCode);

    List<JSONObject> practiceFinishRank();

    @MapKey("date")
    Map<String, JSONObject> PersonalLearnCourse4Mouth(@Param("user_id") String resourceId, @Param("date_range") List<String> dateRange);

    @MapKey("date")
    Map<String, JSONObject> PersonalFinishReport4Mouth(@Param("user_id") String resourceId, @Param("date_range") List<String> dateRange, @Param("report_code") Integer reportCode);
}
