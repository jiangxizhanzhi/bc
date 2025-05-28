/**
 * 这段代码是一个Java类，实现了名为`LessonRecordServiceImpl`的服务类，该类实现了`LessonRecordService`接口。以下是代码的主要组成部分和功能： 
 * ### 类定义和注解 
 * - `@Service`：这是一个Spring框架的注解，表明这个类是一个Spring管理的Bean。 
 * - `@Autowired`：这个注解用于自动装配依赖，这里将`LessonRecordMapper`注入到类中。 
 * ### 成员变量 
 * - `private LessonRecordMapper lessonRecordMapper;`：一个成员变量，类型为`LessonRecordMapper`，用于数据访问操作。 
 * ### 方法 
 * #### `selectByTime` 
 * - **参数**：`userId`（用户ID），`startTime`（开始时间），`endTime`（结束时间） 
 * - **返回值**：`List<LessonRecord>`，表示在指定时间段内与指定用户相关的所有课程记录。 
 * - **功能**：调用`lessonRecordMapper.selectByTime`方法从数据库中查询符合条件的课程记录，并返回结果。 
 * #### `recordBelongCourse` 
 * - **参数**：`recordLesson`（包含录制课程ID的列表）和`lessons`（包含所有课程的列表） 
 * - **返回值**：`List<String>`，表示与录制课程匹配的课程ID列表。 
 * - **功能**： 
 * - 使用Java Stream API过滤出那些录制课程ID存在于给定列表中的课程。 
 * - 提取这些课程的课程ID，并去除重复项。 
 * - 返回处理后的课程ID列表。 
 * ### 总结 
 * 这个类主要负责管理和处理与课程记录相关的一些业务逻辑，包括根据时间范围查询课程记录以及根据录制课程信息查找对应的课程。它通过使用`LessonRecordMapper`来与数据库进行交互，并且使用了Java 8的Stream API来简化数据处理过程。 
**/
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
 * 课记录Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see LessonRecordService
 * @since 1.0.0
 */
@Service
public class LessonRecordServiceImpl implements LessonRecordService {

    /**
     * 课记录Mapper
     *
     * @see LessonRecordMapper
     */
    @Autowired
    private LessonRecordMapper lessonRecordMapper;

    /**
     * 查询由时间
     *
     * @param userId    用户id
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@code List<LessonRecord> }
     * @throws
     * @see String
     * @see Date
     * @see Date
     * @see List<LessonRecord>
     */
    @Override
    public List<LessonRecord> selectByTime(String userId, Date startTime, Date endTime) {

        return lessonRecordMapper.selectByTime(userId, startTime, endTime);
    }

    /**
     * 记录 Belong 课程
     *
     * @param recordLesson 录制课程
     * @param lessons      课
     * @return {@code List<String> }
     * @throws
     * @see List<String>
     * @see List<CourseLesson>
     * @see List<String>
     */
    @Override
    public List<String> recordBelongCourse(List<String> recordLesson, List<CourseLesson> lessons) {
        List<CourseLesson> lessonList = lessons.stream()
                .filter(courseLesson -> recordLesson.contains(courseLesson.getResourceId()))
                .collect(Collectors.toList());

        return lessonList.stream().map(CourseLesson::getCourseId).distinct().collect(Collectors.toList());
    }
}
