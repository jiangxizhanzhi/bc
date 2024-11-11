package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * lib 练习列表 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class LibPracticeListBo implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("习题资源编号")
	private String resourceId;

	/**
	 * 空白计数
	 */
	@ApiModelProperty("填空题空格数量")
	private Integer blankCount;

	/**
	 * 标题
	 */
	@ApiModelProperty("习题标题")
	private String title;

	/**
	 * 内容
	 */
	@ApiModelProperty("习题内容")
	private String content;

	/**
	 * 类别id
	 */
	@ApiModelProperty("习题分类")
	private String categoryId;

	/**
	 * 类别名称
	 */
	@ApiModelProperty("分类名称")
	private String categoryName;

	/**
	 * 练习类型
	 */
	@ApiModelProperty("类型类型 0=基础理论 1=实操")
	private Integer practiceType;

	/**
	 * 问题类型
	 */
	@ApiModelProperty("问题类型 0=单选 1=多选 2=判断 3=填空 4=简答 5=实操")
	private Integer questionType;

	/**
	 * 选择
	 */
	@ApiModelProperty("基础理论练习题选项")
	private String option;

	/**
	 * 得分
	 */
	@ApiModelProperty("分值")
	private Double score;

	/**
	 * 答案
	 */
	@ApiModelProperty("答案")
	private String answer;
}
