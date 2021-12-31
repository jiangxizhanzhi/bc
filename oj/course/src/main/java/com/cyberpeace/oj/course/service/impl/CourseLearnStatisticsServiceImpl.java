package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.course.controller.req.LessonStatisticsListReq;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.controller.res.HistogramRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.mapper.CourseLearnStatisticsMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.CourseLearnStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseLearnStatisticsServiceImpl implements CourseLearnStatisticsService {

    @Autowired
    private LessonRecordMapper lessonRecordMapper;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseLearnStatisticsMapper courseLearnStatisticsMapper;


    @Override
    public HistogramRes courseHistogram() {
        List<Course> courses = courseLearnStatisticsMapper.selectByHeat();
        return getHistogramRes(courses);
    }

    @Override
    public HistogramRes directionHistogram() {
        List<Course> courses = courseLearnStatisticsMapper.sumHeatByDirection();
        return getHistogramRes(courses);
    }

    private HistogramRes getHistogramRes(List<Course> courses) {
        List<String> xNames = new ArrayList<>();
        List<Integer> yValues = new ArrayList<>();
        for (Course course:courses){
            xNames.add(course.getName());
            yValues.add(course.getHeat());
        }
        HistogramRes res = new HistogramRes();
        res.setXNames(xNames);
        res.setYValues(yValues);
        return res;

    }

    @Override
    public IPage<AdminCourseStatisticsRes> courseStatisticsList(CourseContext context, Page page, AuthContext authContext) {
        List<String> courseIds = null;
        if (null != authContext) {
            courseIds = courseMapper.selectByAuth(authContext);
        }

        return courseLearnStatisticsMapper.listCourseByPage(page, context, courseIds,false );
    }

    @Override
    public IPage<AdminLessonStatisticsRes> LessonStatisticsList(LessonStatisticsListReq lessonStatisticsListReq, Page page) {
        return courseLearnStatisticsMapper.listLessonByPage(page, lessonStatisticsListReq, true);
    }

    @Override
    public IPage<AdminLessonStatisticsDetailRes> LessonStatisticsDetail(String lessonId, Page page, PageReqBase pageReq) {
        List<String> userIds = new ArrayList<>();
        courseLearnStatisticsMapper.detailLessonByPage(page,lessonId, userIds);
        return null;
    }
}
