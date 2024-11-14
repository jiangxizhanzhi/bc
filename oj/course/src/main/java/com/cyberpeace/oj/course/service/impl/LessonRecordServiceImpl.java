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

/**
 * 课时记录Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
public class LessonRecordServiceImpl implements LessonRecordService {

    /**
     * 课时记录Mapper
     */
    @Autowired
    private LessonRecordMapper lessonRecordMapper;

    /**
     * 查询由时间
     *
     * @param userId    用户id
     * @param startTime 启动时间
     * @param endTime   结束时间
     * @return {@code List<LessonRecord> }
     */
    @Override
    public List<LessonRecord> selectByTime(String userId, Date startTime, Date endTime) {

        return lessonRecordMapper.selectByTime(userId, startTime, endTime);
    }

    /**
     * 记录属于课程
     *
     * @param recordLesson 记录课时
     * @param lessons      课
     * @return {@code List<String> }
     */
    @Override
    public List<String> recordBelongCourse(List<String> recordLesson, List<CourseLesson> lessons) {
        List<CourseLesson> lessonList = lessons.stream()
                .filter(courseLesson -> recordLesson.contains(courseLesson.getResourceId()))
                .collect(Collectors.toList());

        return lessonList.stream().map(CourseLesson::getCourseId).distinct().collect(Collectors.toList());
    }
}
