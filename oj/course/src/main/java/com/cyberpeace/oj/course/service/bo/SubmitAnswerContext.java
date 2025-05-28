/**
 * 这段代码定义了一个名为 `SubmitAnswerContext` 的 Java 类，用于表示提交答案时的上下文信息。以下是对此代码的简要分析： 
 * ### 类定义 
 * - **类名**: `SubmitAnswerContext` 
 * - **包**: `com.cyberpeace.oj.course.service.bo` 
 * - **实现接口**: `Serializable`（使得该对象可以序列化） 
 * ### 注解 
 * - 使用了 Lombok 库中的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * 1. **courseId**: 
 * - 类型: `String` 
 * - 描述: 课程 ID 
 * - 注释: `@see String` 
 * 2. **lessonId**: 
 * - 类型: `String` 
 * - 描述: 课 ID 
 * - 注释: `@see String` 
 * 3. **userId**: 
 * - 类型: `String` 
 * - 描述: 用户 ID 
 * - 注释: `@see String` 
 * ### 总结 
 * - 这个类主要用于封装提交答案时所需的信息。 
 * - 包含三个主要属性：课程 ID、课 ID 和用户 ID。 
 * - 通过 Lombok 注解简化了代码，避免了手动编写 getter 和 setter 方法。 
 * - 实现 `Serializable` 接口使得这个类的对象可以被序列化，便于在网络上传输或持久化存储。 
 * ### 适用场景 
 * - 在在线教育平台中，当用户提交某个课程或课时的答案时，可以使用这个类来传递相关的上下文信息。 
 * - 可以在服务层或控制器层中创建并填充这个对象，然后传递给其他业务逻辑或持久化操作。 
**/
package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 提交答案上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class SubmitAnswerContext implements Serializable {

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
     * 用户id
     *
     * @see String
     */
    private String userId;

}
