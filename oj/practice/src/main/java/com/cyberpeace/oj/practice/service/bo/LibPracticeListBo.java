package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class LibPracticeListBo implements Serializable {

	@ApiModelProperty("习题资源编号")
	private String resourceId;

	@ApiModelProperty("填空题空格数量")
	private Integer blankCount;

	@ApiModelProperty("习题标题")
	private String title;

	@ApiModelProperty("习题内容")
	private String content;

	@ApiModelProperty("习题分类")
	private String categoryId;

	@ApiModelProperty("分类名称")
	private String categoryName;

	@ApiModelProperty("类型类型 0=基础理论 1=实操")
	private Integer practiceType;

	@ApiModelProperty("问题类型 0=单选 1=多选 2=判断 3=填空 4=简答 5=实操")
	private Integer questionType;

	@ApiModelProperty("基础理论练习题选项")
	private String option;

	@ApiModelProperty("分值")
	private Double score;
}
