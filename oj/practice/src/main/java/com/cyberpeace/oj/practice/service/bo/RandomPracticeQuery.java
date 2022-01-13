package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RandomPracticeQuery implements Serializable {

	@ApiModelProperty("习题集编号")
	private String practiceSetId;

	@ApiModelProperty("分类编号")
	private List<String> categoryId;

	@ApiModelProperty("题型 0=单选 1-多选.....")
	private Integer questionType;

	@ApiModelProperty("模糊搜索条件")
	private String pattern;

	@ApiModelProperty("习题数量")
	private Integer count;

	@ApiModelProperty("已选择的习题集编号")
	private List<String> selectIds;
}
