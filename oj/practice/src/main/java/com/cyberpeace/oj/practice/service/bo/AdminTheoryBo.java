package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class AdminTheoryBo implements Serializable {

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty(value = "内容")
	private String content;

	@ApiModelProperty(value = "填空题答案")
	private String blank;

//	@ApiModelProperty(value = "习题集")
//	private String practiceSetId;

	@ApiModelProperty(value = "分类")
	private String categoryId;

	@ApiModelProperty(value = "答案")
	private String answer;

	@ApiModelProperty(value = "理论基础题型 0=单选 1=多选 2=判断 3=填空 4=简单")
	private Integer theoryType;

	@ApiModelProperty("知识点")
	private String knowledge;

	@ApiModelProperty("选项 - (判断、单选、多选题型使用)")
	private String option;

	@ApiModelProperty("关键字 - (简答题题型使用)")
	private String keyword;

	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("难度")
	private Integer difficulty;

}
