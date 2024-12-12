package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * lib 练习列表 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class LibPracticeListBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题资源编号")
	private String resourceId;

	/**
	 * 空白计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("填空题空格数量")
	private Integer blankCount;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty("习题标题")
	private String title;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("习题内容")
	private String content;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题分类")
	private String categoryId;

	/**
	 * 类别名称
	 *
	 * @see String
	 */
	@ApiModelProperty("分类名称")
	private String categoryName;

	/**
	 * 执业类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("类型类型 0=基础理论 1=实操")
	private Integer practiceType;

	/**
	 * 问题类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("问题类型 0=单选 1=多选 2=判断 3=填空 4=简答 5=实操")
	private Integer questionType;

	/**
	 * 选择
	 *
	 * @see String
	 */
	@ApiModelProperty("基础理论练习题选项")
	private String option;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("分值")
	private Double score;

	/**
	 * 答案
	 *
	 * @see String
	 */
	@ApiModelProperty("答案")
	private String answer;
}
