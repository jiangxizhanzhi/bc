package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理课统计学结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@ApiModel
public class AdminLessonStatisticsRes implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("课时资源编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("课时名称")
	private String name;

	/**
	 * 类型
	 */
	@ApiModelProperty("课时类型")
	private String type;

	/**
	 * 难度
	 */
	@ApiModelProperty("课时难度")
	private Integer difficulty;

	/**
	 * 实践计数
	 */
	@ApiModelProperty("完成练习数")
	private Integer practiceCount;

	/**
	 * 热
	 */
	@ApiModelProperty("学习人数")
	private Integer heat;

	/**
	 * 完成报告计数
	 */
	@ApiModelProperty("提交报告数")
	private String finishReportCount;

	/**
	 * 记录
	 */
	@ApiModelProperty("答题记录")
	private String record;
}
