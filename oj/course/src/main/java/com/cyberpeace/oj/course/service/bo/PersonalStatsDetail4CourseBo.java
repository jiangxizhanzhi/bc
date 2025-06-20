package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 个人统计 Detail4 课程 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PersonalStatsDetail4CourseBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示与课程相关的个人统计信息。以下是对该类的简要分析： 
 * ### 类名 
 * - **类名**：`PersonalStatsDetail4CourseBo` 
 * - **功能**：表示与课程相关的个人统计信息。 
 * ### 注释 
 * - 类和字段都有详细的注释，说明了每个字段的含义。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述字段在 API 文档中的显示方式。 
 * ### 字段 
 * - **resourceId**：课程资源ID。 
 * - **name**：课程名称。 
 * - **difficulty**：课程难度。 
 * - **directionName**：课程方向名称。 
 * - **logo**：课程图片地址。 
 * - **lessonCount**：课程课时数量。 
 * - **learnedTheory**：已学习理论课时。 
 * - **learnedExp**：已学习实验课时。 
 * - **learnedAfterClassPractice**：已完成课后练习。 
 * - **learnedExpReport**：已提交实验报告。 
 * - **lessonId**：正在学习课时的ID。 
 * - **createTime**：最近一次学习时间。 
 * - **learnedType**：课程学习类型。 
 * - **roomId**：课堂ID。 
 * ### 特性 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解，自动生成了所有字段的 getter 和 setter 方法。 
 * - 实现了 `Serializable` 接口，表明该对象可以被序列化。 
 * ### 总结 
 * 这个类是一个数据传输对象（DTO），用于封装与课程相关的个人学习统计数据。它通过 Swagger 注解增强了其在 API 文档中的可读性和可用性。同时，Lombok 注解简化了代码，使其更简洁易读。 
**/
@Getter
@Setter
public class PersonalStatsDetail4CourseBo implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
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
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("难度")
    private Integer difficulty;

    /**
     * 方向名称
     *
     * @see String
     */
    @ApiModelProperty("方向名称")
    private String directionName;

    /**
     * 图标
     *
     * @see String
     */
    @ApiModelProperty("课程图片地址")
    private String logo;

    /**
     * 课计数
     *
     * @see Integer
     */
    @ApiModelProperty("课时数量")
    private Integer lessonCount;

    /**
     * 博学理论
     *
     * @see Integer
     */
    @ApiModelProperty("已学习理论课时")
    private Integer learnedTheory;

    /**
     * 学到了经验
     *
     * @see Integer
     */
    @ApiModelProperty("已学习实验课时")
    private Integer learnedExp;

    /**
     * 课后练习
     *
     * @see Integer
     */
    @ApiModelProperty("已完成课后练习")
    private Integer learnedAfterClassPractice;

    /**
     * 学习 EXP 报告
     *
     * @see Integer
     */
    @ApiModelProperty("已提交实验报告")
    private Integer learnedExpReport;

    /**
     * 课id
     *
     * @see String
     */
    @ApiModelProperty("正在学习课时")
    private String lessonId;

    /**
     * 创建时间
     *
     * @see String
     */
    @ApiModelProperty("最近一次学习时间")
    private String createTime;

    /**
     * 学习类型
     *
     * @see Integer
     */
    @ApiModelProperty("课程学习类型")
    private Integer learnedType;

    /**
     * 房间id
     *
     * @see String
     */
    @ApiModelProperty("课堂id")
    private String roomId;
}

