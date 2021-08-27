package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;

import java.util.List;

public interface CourseStatisticsService {

    /**
     * 根据课程编号和用户编号查询学习情况
     * @param courseIds
     * @param userId
     * @return
     */
    List<CourseStudyProcessBo> statisticByIds(List<String> courseIds, String userId);

}
