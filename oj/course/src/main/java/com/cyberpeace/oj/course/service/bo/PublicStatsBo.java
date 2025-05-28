/**
 * 这段代码定义了一个名为 `PublicStatsBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类用于表示公开统计信息，适用于课程相关的统计数据。以下是对代码的详细分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.course.service.bo; 
 * ``` 
 * - 这行代码声明了这个类属于 `com.cyberpeace.oj.course.service.bo` 包。 
 * ### 导入语句 
 * ```java 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import java.io.Serializable; 
 * ``` 
 * - 导入了 Lombok 注解 `@Getter` 和 `@Setter`，用于简化 getter 和 setter 方法的生成。 
 * - 导入了 Swagger 注解 `@ApiModelProperty`，用于生成 API 文档。 
 * - 导入了 `Serializable` 接口，使得对象可以序列化。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * public class PublicStatsBo implements Serializable { 
 * ``` 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成 getter 和 setter 方法。 
 * - 类名 `PublicStatsBo` 表示这是一个公共统计的数据传输对象（BO）。 
 * ### 字段声明 
 * ```java 
 * private Integer lessonRecordCount = 0; 
 * private Integer finishPracticeCount = 0; 
 * private Integer finishReportCount = 0; 
 * private float reportAverage = 0; 
 * private Integer finishExamCount = 0; 
 * ``` 
 * - `lessonRecordCount`：完成课时的数量，默认为 0。 
 * - `finishPracticeCount`：完成练习的数量，默认为 0。 
 * - `finishReportCount`：完成实验的数量，默认为 0。 
 * - `reportAverage`：实验平均得分，默认为 0。 
 * - `finishExamCount`：完成考试的数量，默认为 0。 
 * ### 文档注释 
 * ```java 
 * /** 
 * * 公开统计 bo 
 * * 
 * * @author SN 
 * * @version 1.0.0 
 * * @date 2024/11/08 
 * * @see Serializable 
 * * @since 1.0.0 
 * */ 
 * ``` 
 * - 文档注释说明了这个类的作用、作者、版本、日期以及继承的接口。 
 * ### 字段文档注释 
 * 每个字段都有详细的文档注释，解释了每个字段的用途和默认值。 
 * ### 总结 
 * - `PublicStatsBo` 是一个简单的数据传输对象（DTO），用于存储课程相关的统计数据。 
 * - 使用了 Lombok 来简化 getter 和 setter 方法的生成。 
 * - 使用了 Swagger 注解来帮助生成 API 文档。 
 * - 类中的字段包含了课程统计的各种信息，如课时、练习、实验和考试的完成情况以及实验的平均得分。 
 * 这个类可以被其他服务或模块使用，以获取和展示课程相关的统计数据。 
**/
package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 公开统计 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class PublicStatsBo implements Serializable {
    /**
     * 课记录计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成课时数量")
    private Integer lessonRecordCount = 0;

    /**
     * 完成实践计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成练习数量")
    private Integer finishPracticeCount = 0;

    /**
     * 完成报告计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成实验数量")
    private Integer finishReportCount = 0;

    /**
     * 报告平均值
     */
    @ApiModelProperty("实验平均得分")
    private float reportAverage = 0;

    /**
     * 完成考试计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成考试数")
    private Integer finishExamCount = 0;
}
