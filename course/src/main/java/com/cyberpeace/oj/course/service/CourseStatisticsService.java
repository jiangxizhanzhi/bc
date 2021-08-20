package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;

import java.util.List;

public interface CourseStatisticsService {

    List<CourseStudyProcessBo> statisticByIds(List<String> courseIds, String userId);

}
