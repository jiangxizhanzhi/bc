package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 课后练习使用
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class TheoryLessonBo implements Serializable {

	/**
	 * 快照编号
	 */
	@ApiModelProperty("快照编号")
    private String refId;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案")
    private String answer;

	/**
	 * 填空题空格数量
	 */
	@ApiModelProperty("填空题空格数量")
    private Integer blankCount;

	/**
	 * 标题
	 */
	@ApiModelProperty("标题")
    private String title;

	/**
	 * 内容
	 */
	@ApiModelProperty("内容")
    private String content;

	/**
	 * 选项
	 */
	@ApiModelProperty("选项")
    private String option;

	/**
	 * 方向名称
	 */
	@ApiModelProperty("方向名称")
    private String directionName;

	/**
	 * 习题类型
	 */
	@ApiModelProperty("习题类型")
    private Integer practiceType;

	/**
	 * 题型
	 */
	@ApiModelProperty("题型")
    private Integer questionType;

	/**
	 * 难度
	 */
	@ApiModelProperty("难度")
    private Integer difficulty;

	/**
	 * 资源编号
	 */
	@ApiModelProperty("资源编号")
    private String resourceId;

	/**
	 * 分值
	 */
	@ApiModelProperty("分值")
    private Double score;

	/**
	 * 是否内置
	 */
	@ApiModelProperty("是否内置")
    private Boolean builtin;
}
