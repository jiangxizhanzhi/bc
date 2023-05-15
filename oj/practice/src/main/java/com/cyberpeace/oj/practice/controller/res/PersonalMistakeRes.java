package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersonalMistakeRes implements Serializable {

	@ApiModelProperty("题目id")
	private String practiceId;

	@ApiModelProperty("题目名称")
	private String content;

	@ApiModelProperty("题目类型")
	private Integer theoryType;

	@ApiModelProperty("答对次数")
	private Integer correct;

	@ApiModelProperty("答错次数")
	private Integer incorrect;

	@ApiModelProperty("方向")
	private String direction;

	@ApiModelProperty("难度")
	private Integer difficulty;

}
