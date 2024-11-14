package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理课时统计学结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class AdminLessonStatisticsRes implements Serializable {

	/**
	 * 课时资源编号
	 */
	@ApiModelProperty("课时资源编号")
	private String resourceId;

	/**
	 * 课时名称
	 */
	@ApiModelProperty("课时名称")
	private String name;

	/**
	 * 课时类型
	 */
	@ApiModelProperty("课时类型")
	private String type;

	/**
	 * 课时难度
	 */
	@ApiModelProperty("课时难度")
	private Integer difficulty;

	/**
	 * 完成练习数
	 */
	@ApiModelProperty("完成练习数")
	private Integer practiceCount;

	/**
	 * 学习人数
	 */
	@ApiModelProperty("学习人数")
	private Integer heat;

	/**
	 * 提交报告数
	 */
	@ApiModelProperty("提交报告数")
	private String finishReportCount;

	/**
	 * 答题记录
	 */
	@ApiModelProperty("答题记录")
	private String record;
}
