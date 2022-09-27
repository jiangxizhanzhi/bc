package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.LessonRecordService;
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
}
