package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;

import java.util.List;

/**
 * 课程统计学Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
public interface CourseStatisticsService {

    /**
     * 根据课程编号和用户编号查询学习情况
     *
     * @param courseIds
     * @param userId
     * @return
     * @throws
     * @see List<String>
     * @see String
     * @see List<CourseStudyProcessBo>
     */
    List<CourseStudyProcessBo> statisticByIds(List<String> courseIds, String userId);

}
