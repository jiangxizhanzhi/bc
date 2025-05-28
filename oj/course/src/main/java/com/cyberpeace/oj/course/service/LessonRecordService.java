/**
 * 这段代码定义了一个名为 `LessonRecordService` 的接口，用于处理与课程学习记录相关的操作。以下是对该代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.course.service; 
 * ``` 
 * - 这段代码位于 `com.cyberpeace.oj.course.service` 包中。 
 * ### 导入语句 
 * ```java 
 * import com.cyberpeace.oj.course.dao.entity.CourseLesson; 
 * import com.cyberpeace.oj.course.dao.entity.LessonRecord; 
 * import java.util.Date; 
 * import java.util.List; 
 * ``` 
 * - 导入了两个实体类：`CourseLesson` 和 `LessonRecord`。 
 * - 导入了 `Date` 和 `List` 类，分别用于表示日期和列表。 
 * ### 接口定义 
 * ```java 
 * public interface LessonRecordService { 
 * ``` 
 * - 定义了一个名为 `LessonRecordService` 的公共接口。 
 * ### 方法定义 
 * #### 1. `selectByTime` 
 * ```java 
 * List<LessonRecord> selectByTime(String userId, Date startTime, Date endTime); 
 * ``` 
 * - **功能**：查询指定用户在给定时间范围内的学习记录。 
 * - **参数**： 
 * - `userId`：用户的唯一标识符。 
 * - `startTime`：查询开始时间。 
 * - `endTime`：查询结束时间。 
 * - **返回值**：一个包含 `LessonRecord` 对象的列表。 
 * #### 2. `recordBelongCourse` 
 * ```java 
 * List<String> recordBelongCourse(List<String> recordLesson, List<CourseLesson> lessons); 
 * ``` 
 * - **功能**：根据学习记录和课程信息，计算学习记录所属的课程。 
 * - **参数**： 
 * - `recordLesson`：学习记录列表。 
 * - `lessons`：课程列表。 
 * - **返回值**：一个包含课程 ID 的字符串列表。 
 * ### 总结 
 * - `LessonRecordService` 接口提供了两个主要的方法来处理课程学习记录：查询指定时间范围内的学习记录和计算学习记录所属的课程。 
 * - 这些方法使用了 `String`、`Date` 和 `List` 等常见的 Java 数据类型，并依赖于 `CourseLesson` 和 `LessonRecord` 实体类。 
 * - 该接口的设计目的是为了提供一个清晰的 API 来处理与课程学习记录相关的核心业务逻辑。 
**/
package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;

import java.util.Date;
import java.util.List;

/**
 * 课记录Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface LessonRecordService {

    /**
     * 查询时间范围内的学习记录
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     * @throws
     * @see String
     * @see Date
     * @see Date
     * @see List<LessonRecord>
     */
    List<LessonRecord> selectByTime(String userId, Date startTime, Date endTime);

    /**
     * 计算学习记录属于那些课程
     *
     * @param recordLesson
     * @param lessons
     * @return
     * @throws
     * @see List<String>
     * @see List<CourseLesson>
     * @see List<String>
     */
    List<String> recordBelongCourse(List<String> recordLesson, List<CourseLesson> lessons);

}
