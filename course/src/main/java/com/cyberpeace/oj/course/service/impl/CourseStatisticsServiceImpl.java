package com.cyberpeace.oj.course.service.impl;

import java.util.List;

import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;

@Service
public class CourseStatisticsServiceImpl implements CourseStatisticsService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<CourseStudyProcessBo> statisticByIds(List<String> courseIds) {


        return null;
    }
}
