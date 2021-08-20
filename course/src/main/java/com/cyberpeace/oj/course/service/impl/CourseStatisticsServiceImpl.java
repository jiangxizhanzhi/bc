package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.Lesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseStatisticsServiceImpl implements CourseStatisticsService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private CourseLessonMapper courseLessonMapper;

    @Autowired
    private LessonRecordMapper lessonRecordMapper;

    @Override
    public List<CourseStudyProcessBo> statisticByIds(List<String> courseIds, String userId) {

        List<Course> courses = courseMapper.selectBatchIds(courseIds);
        List<String> ids = courses.stream().map(Course::getResourceId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }

        List<Lesson> lessons = courseLessonMapper.listByCourses(ids);
        Map<Integer, List<String>> typeMap = lessons.stream().collect(Collectors.groupingBy(Lesson::getType,
                Collectors.mapping(Lesson::getResourceId, Collectors.toList())));

        List<String> lessonRecords = lessonRecordMapper.idsByCoursesAndUser(userId, courseIds);
        CourseStudyProcessBo studyProcessBo = new CourseStudyProcessBo();

        typeMap.forEach((key, value) -> {
            if (key.intValue() == Lesson.TYPE_THEORY) {
               studyProcessBo.setTheoryCount(value.size());

            } else {
                studyProcessBo.setExerciseCount(value.size());
                if (value.size() > lessonRecords.size()) {
                    int completeExercise = value.stream().filter(lessonId -> lessonRecords.contains(lessonId)).collect(Collectors.toList()).size();
                    studyProcessBo.setCompleteExercise(completeExercise);
                } else {
//                    lessonRecords.stream().filter(le)
                }
            }
        });

        return null;
    }
}
