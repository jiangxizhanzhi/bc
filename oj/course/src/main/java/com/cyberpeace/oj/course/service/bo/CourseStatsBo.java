/**
 * 这段代码定义了一个名为 `CourseStatsBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示课程统计信息，并且使用了 Lombok 库来简化 getter 和 setter 方法的生成。以下是代码的详细分析： 
 * ### 包声明和导入 
 * ```java 
 * package com.cyberpeace.oj.course.service.bo; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * ``` 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 表示这个类位于 `com.cyberpeace.oj.course.service.bo` 包下。 
 * - **导入**： 
 * - `io.swagger.annotations.ApiModelProperty`：用于在 Swagger 文档中添加字段描述。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`：Java 接口，表示对象可以序列化。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class CourseStatsBo implements Serializable { 
 * ``` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成所有字段的 getter 和 setter 方法。 
 * - **实现接口**：`CourseStatsBo` 实现了 `Serializable` 接口，使得对象可以被序列化。 
 * ### 字段 
 * ```java 
 * private String resourceId; 
 * private String name; 
 * private String directionName; 
 * private Integer difficulty; 
 * private Integer reportCount; 
 * private Integer lessonCount; 
 * private Integer learningCount; 
 * private String createUserId; 
 * private String createUsername; 
 * ``` 
 * - 这些字段分别表示课程的资源 ID、名称、方向名称、难度、报告数量、课时数量、学习人数以及创建用户的资源 ID 和姓名。 
 * ### 字段注释 
 * 每个字段都有详细的 Javadoc 注释，说明了字段的用途和类型。 
 * ### 总结 
 * - **功能**：`CourseStatsBo` 类用于封装课程的统计信息。 
 * - **特点**： 
 * - 使用 Lombok 简化了 getter 和 setter 方法的编写。 
 * - 实现了 `Serializable` 接口，便于对象的序列化。 
 * - 每个字段都有清晰的 Javadoc 注释，方便理解和维护。 
 * 这段代码是一个典型的业务对象（BO），适用于需要传输和存储课程统计信息的场景。 
**/
package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程统计 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class CourseStatsBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("课程resource_id")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课程名称")
    private String name;

    /**
     * 方向名称
     *
     * @see String
     */
    @ApiModelProperty("方向名称")
    private String directionName;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("课程难度")
    private Integer difficulty;

    /**
     * 报告计数
     *
     * @see Integer
     */
    @ApiModelProperty("报告数量")
    private Integer reportCount;

    /**
     * 课计数
     *
     * @see Integer
     */
    @ApiModelProperty("课时数量")
    private Integer lessonCount;

    /**
     * 学习计数
     *
     * @see Integer
     */
    @ApiModelProperty("学习人数")
    private Integer learningCount;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("创建用户resource_id")
    private String createUserId;

    /**
     * 创建用户名
     *
     * @see String
     */
    @ApiModelProperty("创建用户姓名")
    private String createUsername;
}
