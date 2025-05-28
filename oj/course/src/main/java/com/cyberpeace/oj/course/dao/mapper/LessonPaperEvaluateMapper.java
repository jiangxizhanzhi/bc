/**
 * 这段代码定义了一个名为 `LessonPaperEvaluateMapper` 的接口，该接口继承了 `BaseMapper<LessonPaperEvaluate>`。这是一个用于与数据库交互的 MyBatis Mapper 接口。以下是对代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.course.dao.mapper; 
 * ``` 
 * - 这个包声明表明这个类位于 `com.cyberpeace.oj.course.dao.mapper` 包下。 
 * ### 导入语句 
 * ```java 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate; 
 * import org.apache.ibatis.annotations.Mapper; 
 * import org.apache.ibatis.annotations.Param; 
 * import java.util.List; 
 * ``` 
 * - `BaseMapper`：MyBatis Plus 提供的基类，用于简化 CRUD 操作。 
 * - `LessonPaperEvaluate`：表示 `LessonPaperEvaluate` 实体类。 
 * - `@Mapper`：MyBatis 注解，表明这是一个 MyBatis 的 Mapper 类。 
 * - `@Param`：MyBatis 注解，用于指定方法参数的名称。 
 * - `List`：Java 集合框架中的接口，用于表示集合类型。 
 * ### 类声明 
 * ```java 
 * @Mapper 
 * public interface LessonPaperEvaluateMapper extends BaseMapper<LessonPaperEvaluate> { 
 * ``` 
 * - `@Mapper`：将这个接口标记为一个 MyBatis 的 Mapper。 
 * - `extends BaseMapper<LessonPaperEvaluate>`：继承了 `BaseMapper`，这样就可以使用一些默认的 CRUD 方法。 
 * ### 方法声明 
 * #### `selectByUserAndLesson` 
 * ```java 
 * LessonPaperEvaluate selectByUserAndLesson(@Param("userId") String userId, 
 * @Param("lessonId") String lessonId); 
 * ``` 
 * - 方法签名：返回一个 `LessonPaperEvaluate` 对象，接受两个参数：`userId` 和 `lessonId`。 
 * - 作用：根据用户 ID 和课程 ID 查询对应的评估记录。 
 * #### `selectByLessonAndUsers` 
 * ```java 
 * List<LessonPaperEvaluate> selectByLessonAndUsers(@Param("userIds") List<String> userIds, 
 * @Param("lessonId") String lessonId); 
 * ``` 
 * - 方法签名：返回一个 `List<LessonPaperEvaluate>`，接受两个参数：一个用户 ID 列表和一个课程 ID。 
 * - 作用：根据课程 ID 和一组用户 ID 查询对应的评估记录列表。 
 * ### 总结 
 * 这段代码定义了一个用于操作 `LessonPaperEvaluate` 实体的 MyBatis Mapper 接口。它提供了两个查询方法： 
 * 1. 根据用户 ID 和课程 ID 查询单条评估记录。 
 * 2. 根据课程 ID 和一组用户 ID 查询多条评估记录。 
 * 这些方法可以方便地进行数据库查询操作，并且由于继承了 `BaseMapper`，还可能包含一些默认的 CRUD 方法。 
**/
package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课纸评估Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface LessonPaperEvaluateMapper extends BaseMapper<LessonPaperEvaluate> {

    /**
     * 查询由用户和课
     *
     * @param userId   用户id
     * @param lessonId 课id
     * @return {@code LessonPaperEvaluate }
     * @throws
     * @see String
     * @see String
     * @see LessonPaperEvaluate
     */
    LessonPaperEvaluate selectByUserAndLesson(@Param("userId") String userId,
                                              @Param("lessonId") String lessonId);

    /**
     * 查询由课和用户
     *
     * @param userIds  用户id列表
     * @param lessonId 课id
     * @return {@code List<LessonPaperEvaluate> }
     * @throws
     * @see List<String>
     * @see String
     * @see List<LessonPaperEvaluate>
     */
    List<LessonPaperEvaluate> selectByLessonAndUsers(@Param("userIds")List<String> userIds,
                                               @Param("lessonId") String lessonId);

}
