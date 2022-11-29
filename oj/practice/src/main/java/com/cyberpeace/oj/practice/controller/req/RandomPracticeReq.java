package com.cyberpeace.oj.practice.controller.req;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class RandomPracticeReq extends PatternBase {

	@ApiModelProperty("习题集编号")
	@NotBlank
	private String practiceSetId;

	@ApiModelProperty("分类编号")
	private String categoryId;

	@ApiModelProperty("题型 0=单选 1-多选.....")
	private Integer questionType;

	@ApiModelProperty("习题数量")
	@NotNull
	private Integer count;

	@ApiModelProperty("习题分值")
	@NotNull
	private Double score;

	@ApiModelProperty("已选择的习题编号")
	private List<String> resourceIds;

}
