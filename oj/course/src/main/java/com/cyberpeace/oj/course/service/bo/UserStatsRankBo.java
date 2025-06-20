package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 用户统计 排名 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `UserStatsRankBo` 的 Java 类，用于表示用户在不同维度上的统计数据和排名。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **导入语句**： 
 * - `io.swagger.annotations.ApiModelProperty`：用于Swagger文档注解。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok注解，自动生成getter和setter方法。 
 * - `java.io.Serializable`：表示该类实现了序列化接口。 
 * ### 类描述 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2024年11月8日 
 * ### 类属性 
 * - `lessonRecordUser`：一个字符串列表，表示学习课时的用户。 
 * - `lessonRecordCount`：一个整数列表，表示每个用户的课时学习数量。 
 * - `finishReportUser`：一个字符串列表，表示完成实验报告的用户。 
 * - `finishReportCount`：一个整数列表，表示每个用户完成的实验报告数量。 
 * - `finishPracticeUser`：一个字符串列表，表示完成课程练习的用户。 
 * - `finishPracticeCount`：一个整数列表，表示每个用户完成的课程练习数量。 
 * - `attendExamUser`：一个字符串列表，表示参加考试的用户。 
 * - `attendExamCount`：一个整数列表，表示每个用户参加的考试次数。 
 * ### 注解 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动为所有字段生成 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段添加描述信息，便于生成API文档。 
 * ### 总结 
 * 这个类主要用于封装用户在不同学习活动中的统计数据和排名信息。通过这些数据，可以进行各种统计分析和展示。使用 Lombok 注解简化了代码，并且通过 Swagger 注解使得这些数据在 API 文档中更加清晰明了。 
**/
@Getter
@Setter
public class UserStatsRankBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
     * 课程记录用户
     *
     * @see List<String>
     */
    @ApiModelProperty("课时学习用户排名")
    private List<String> lessonRecordUser;

    /**
     * 课记录计数
     *
     * @see List<Integer>
     */
    @ApiModelProperty("课时学习数量")
    private List<Integer> lessonRecordCount;

    /**
     * 完成报告用户
     *
     * @see List<String>
     */
    @ApiModelProperty("完成实验排名")
    private List<String> finishReportUser;

    /**
     * 完成报告计数
     *
     * @see List<Integer>
     */
    @ApiModelProperty("完成实验数量")
    private List<Integer> finishReportCount;

    /**
     * 完成练习用户
     *
     * @see List<String>
     */
    @ApiModelProperty("完成课程练习排名")
    private List<String> finishPracticeUser;

    /**
     * 完成实践计数
     *
     * @see List<Integer>
     */
    @ApiModelProperty("完成课程练习数量")
    private List<Integer> finishPracticeCount;

    /**
     * 参加考试用户
     *
     * @see List<String>
     */
    @ApiModelProperty("参加考试次数排名")
    private List<String> attendExamUser;

    /**
     * 参加考试计数
     *
     * @see List<Integer>
     */
    @ApiModelProperty("参加考试次数")
    private List<Integer> attendExamCount;
}

