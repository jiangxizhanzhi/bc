package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class PaperPracticeContext implements Serializable {

	@ApiModelProperty("习题编号")
	@NotBlank
	private String resourceId;

	@ApiModelProperty("习题类型")
	@NotNull
	private Integer practiceType;

	@ApiModelProperty("课后练习分值")
	@NotNull
	private Double score;

	@ApiModelProperty("选项是否随机")
	private Boolean optionRandom;

}
