package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 管理课统计学结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminLessonStatisticsRes` 的 Java 类，用于表示管理员查看课程统计信息的结果。以下是对该类的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.course.controller.res` 
 * - **类名**: `AdminLessonStatisticsRes` 
 * - **功能**: 用于存储和传输关于课程统计信息的数据模型。 
 * ### 注解 
 * - **`@ApiModel`**: 表示这个类是一个 API 模型。 
 * - **`@ApiModelProperty`**: 为类中的字段提供 Swagger 文档注释，说明每个字段的用途。 
 * ### 字段 
 * - **`resourceId`**: 课时资源编号，字符串类型。 
 * - **`name`**: 课时名称，字符串类型。 
 * - **`type`**: 课时类型，字符串类型。 
 * - **`difficulty`**: 课时难度，整数类型。 
 * - **`practiceCount`**: 完成练习数，整数类型。 
 * - **`heat`**: 学习人数，整数类型。 
 * - **`finishReportCount`**: 提交报告数，字符串类型。 
 * - **`record`**: 答题记录，字符串类型。 
 * ### 特性 
 * - **`@Getter` 和 `@Setter`**: Lombok 注解，自动生成所有字段的 getter 和 setter 方法。 
 * - **`Serializable`**: 接口实现，表明这个类的对象可以被序列化。 
 * ### 总结 
 * 这个类主要用于在后台管理系统中表示课程统计信息。它通过定义多个字段来存储与课程相关的各种统计数据，并使用 Lombok 注解简化了代码。Swagger 注解使得该类在生成 API 文档时更加友好和易读。 
**/
@Getter
@Setter
@ApiModel
public class AdminLessonStatisticsRes implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时资源编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String name;

	/**
	 * 类型
	 *
	 * @see String
	 */
	@ApiModelProperty("课时类型")
	private String type;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时难度")
	private Integer difficulty;

	/**
	 * 实践计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("完成练习数")
	private Integer practiceCount;

	/**
	 * 热
	 *
	 * @see Integer
	 */
	@ApiModelProperty("学习人数")
	private Integer heat;

	/**
	 * 完成报告计数
	 *
	 * @see String
	 */
	@ApiModelProperty("提交报告数")
	private String finishReportCount;

	/**
	 * 记录
	 *
	 * @see String
	 */
	@ApiModelProperty("答题记录")
	private String record;
}

