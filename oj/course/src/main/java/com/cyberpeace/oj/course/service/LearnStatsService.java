package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.service.bo.*;

import java.util.List;

public interface LearnStatsService {

    /**
     * 课程学习统计
     * @param page 分页信息
     * @param pattern 课程名称分页查询
     * @param directionId 方向id
     * @param difficulty 难度
     * @return 课程统计信息
     */
    ListRsp<CourseStatsBo> getCourseStats(Page page, String pattern, String directionId, Integer difficulty);

    /**
     * 课时学习统计
     * @param page 分页信息
     * @param lessonType 课时类型
     * @return 课时统计信息
     */
    ListRsp<LessonStatsBo> getLessonStats(Page page, String courseId, String pattern, Integer lessonType);

    /**
     * 课程学习(热度)排行
     * @return 统计信息
     */
    CourseStatsRankBo getCourseHeatRank();

    /**
     * 用户学习统计
     * @param page 分页信息
     * @param organization 所属组织查询
     * @param pattern 用户姓名模糊查询
     * @return 用户学习统计
     */
    ListRsp<UserStatsBo> getUserStats(Page page, String pattern, String organization);

    /**
     * 用户学习排行
     * @return 统计信息
     */
    UserStatsRankBo getUserLearnRank();

    /**
     * 获取用户课程学习数量统计
     * @param resourceId 用户resource_id
     * @return 统计信息
     */
    PersonalStatsDetail4CountBo getPersonalStatsDetail4Count(String resourceId);

    /**
     * 获取用户近30天学习统计
     * @param resourceId 用户resource_id
     * @return 统计信息
     */
    PersonalStatsDetail4MouthBo getPersonalStatsDetail4Mouth(String resourceId);

    /**
     * 获取用户课程学习统计
     * @param resourceId 用户resource_id
     * @return 统计信息
     */
    ListRsp<PersonalStatsDetail4CourseBo> getPersonalStatsDetail4Course(String resourceId);

}
