package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel
public class RandomPracticeReq implements Serializable {

	@ApiModelProperty("习题集编号")
	@NotBlank
	private String practiceSetId;

	@ApiModelProperty("分类编号")
	private String categoryId;

	@ApiModelProperty("题型 0=单选 1-多选.....")
	private Integer questionType;

	@ApiModelProperty("模糊搜索条件")
	private String pattern;

	@ApiModelProperty("习题数量")
	@NotNull
	private Integer count;

	@ApiModelProperty("习题分值")
	@NotNull
	private Double score;

	public void setPractice_setId(String practiceSetId) {
		this.practiceSetId = practiceSetId;
	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setQuestion_type(Integer questionType) {
		this.questionType = questionType;
	}
}
