package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class PracticeAnswerContext implements Serializable {

	@ApiModelProperty("习题类型 0=理论基础 2=实操练习")
	private Integer practiceType;

	@ApiModelProperty("提交的答案")
	private String submitAnswer;

	@ApiModelProperty("习题编号")
	private String practiceId;

	@ApiModelProperty("是否需要判题")
	private Boolean needEvaluate = false;

	@ApiModelProperty("题型")
	private Integer questionType;

}
