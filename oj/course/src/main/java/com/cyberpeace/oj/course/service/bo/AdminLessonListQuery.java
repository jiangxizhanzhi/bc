package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.PatternBase;

import lombok.Getter;
import lombok.Setter;

/**
 * admin 课程列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminLessonListQuery` 的 Java 类，该类用于管理员查询课程列表。以下是对代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **继承关系**：`AdminLessonListQuery` 继承自 `PatternBase` 
 * ### 注解 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * 1. **type** 
 * - 数据类型：`Integer` 
 * - 注释：表示课程类型。 
 * 2. **categoryId** 
 * - 数据类型：`String` 
 * - 注释：表示课程所属的类别 ID。 
 * 3. **courseId** 
 * - 数据类型：`String` 
 * - 注释：表示课程 ID。 
 * 4. **exceptCourse** 
 * - 数据类型：`String` 
 * - 注释：表示需要排除的课程 ID。 
 * ### 类文档 
 * - 文档注释详细说明了该类的作用是用于管理员查询课程列表，并指出了该类继承自 `PatternBase`。 
 * - 指明了作者、版本和创建日期等信息。 
 * ### 总结 
 * 这个类主要用于封装管理员查询课程列表时所需的参数。通过继承 `PatternBase` 类，可能是在某些框架或系统中实现特定的模式或行为。使用 Lombok 注解简化了代码量，提高了可读性和维护性。 
**/
@Getter
@Setter
public class AdminLessonListQuery extends PatternBase {

    /**
     * 类型
     *
     * @see Integer
     */
    private Integer type;

    /**
     * 类别id
     *
     * @see String
     */
    private String categoryId;

    /**
     * 课程id
     *
     * @see String
     */
    private String courseId;

    /**
     * 除了课程
     *
     * @see String
     */
    private String exceptCourse;

}

