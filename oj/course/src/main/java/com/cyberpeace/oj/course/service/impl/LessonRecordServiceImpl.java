package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.LessonRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonRecordServiceImpl implements LessonRecordService {

    @Autowired
    private LessonRecordMapper lessonRecordMapper;

    @Override
    public List<LessonRecord> selectByTime(String userId, Date startTime, Date endTime) {

        return lessonRecordMapper.selectByTime(userId, startTime, endTime);
    }

    @Override
    public List<String> recordBelongCourse(List<String> recordLesson, List<CourseLesson> lessons) {
        List<CourseLesson> lessonList = lessons.stream()
                .filter(courseLesson -> recordLesson.contains(courseLesson.getResourceId()))
                .collect(Collectors.toList());

        return lessonList.stream().map(CourseLesson::getCourseId).distinct().collect(Collectors.toList());
    }

    @Override
    public List<LessonRecord> selectByUser(String userId) {
        return lessonRecordMapper.selectByUser(userId);
    }

    @Override
    public List<LessonRecord> selectByCourseAndUser(String courseId, String userId) {
        LambdaQueryWrapper<LessonRecord> queryWrapper = new LambdaQueryWrapper<LessonRecord>()
                .eq(StringUtils.isNotBlank(courseId), LessonRecord::getCourseId, courseId)
                .eq(StringUtils.isNotBlank(userId), LessonRecord::getUserId, userId);
        return lessonRecordMapper.selectList(queryWrapper);
    }
}
