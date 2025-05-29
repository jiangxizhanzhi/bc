package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 新增课配置上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AddLessonConfigContext` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示添加课程配置时的上下文信息。以下是对代码的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.service.bo` 
 * - **类名**: `AddLessonConfigContext` 
 * - **实现接口**: `Serializable` 
 * ### 注解 
 * - **Lombok 注解**: 
 * - `@Getter`: 自动生成所有属性的 getter 方法。 
 * - `@Setter`: 自动生成所有属性的 setter 方法。 
 * - `@AllArgsConstructor`: 自动生成一个包含所有属性的构造函数。 
 * - `@NoArgsConstructor`: 自动生成一个无参构造函数。 
 * - `@Builder`: 自动生成一个构建器模式的实现。 
 * ### 属性 
 * - **nodeType**: 表示节点类型，类型为 `Integer`。 
 * - **courseId**: 表示课程ID，类型为 `String`。 
 * - **lessonId**: 表示课ID，类型为 `String`。 
 * - **name**: 表示名称，类型为 `String`。 
 * ### 总结 
 * - 这个类是一个数据传输对象（DTO），用于在服务层之间传递课程配置信息。 
 * - 使用 Lombok 注解减少了样板代码，使得代码更加简洁。 
 * - 类中的属性用于存储与课程配置相关的必要信息。 
 * - 实现 `Serializable` 接口意味着这个类的对象可以被序列化，便于在网络上传输或持久化到文件中。 
**/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddLessonConfigContext implements Serializable {

    /**
     * 节点类型
     *
     * @see Integer
     */
    private Integer nodeType;

    /**
     * 课程id
     *
     * @see String
     */
    private String courseId;

    /**
     * 课id
     *
     * @see String
     */
    private String lessonId;

    /**
     * 名称
     *
     * @see String
     */
    private String name;

}

