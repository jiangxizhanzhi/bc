/**
 * 这段代码定义了一个名为 `ClassroomPracticeContext` 的 Java 类，用于表示课堂练习环境。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **类名**：`ClassroomPracticeContext` 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **实现接口**：`Serializable`，表示该对象可以被序列化。 
 * ### 注解 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - `classroomId`：字符串类型，表示课堂 ID。 
 * - `lessonId`：字符串类型，表示课 ID。 
 * - `organizationId`：字符串类型，表示组织 ID。 
 * - `isCommit`：布尔类型，默认值为 `false`，表示是否提交。 
 * - `isMark`：布尔类型，默认值为 `false`，表示是否标记。 
 * - `username`：字符串类型，表示用户名。 
 * ### 作者信息 
 * - 作者：SN 
 * - 版本：1.0.0 
 * - 创建日期：2024年11月8日 
 * ### 总结 
 * `ClassroomPracticeContext` 类主要用于封装课堂练习环境的相关信息，包括课堂 ID、课 ID、组织 ID、是否提交、是否标记以及用户名等属性。通过 Lombok 注解简化了代码量，并实现了 `Serializable` 接口，使得该对象可以被序列化。 
 * 这个类的设计简洁明了，适用于存储和传输课堂练习相关的上下文信息。 
**/
package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 课堂练习环境
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class ClassroomPracticeContext implements Serializable {

    /**
     * 课堂id
     *
     * @see String
     */
    private String classroomId;

    /**
     * 课id
     *
     * @see String
     */
    private String lessonId;

    /**
     * 组织id
     *
     * @see String
     */
    private String organizationId;

    /**
     * 是提交
     *
     * @see Boolean
     */
    private Boolean isCommit=false;

    /**
     * 是标记
     *
     * @see Boolean
     */
    private Boolean isMark=false;

    /**
     * 用户名
     *
     * @see String
     */
    private String username;

}
