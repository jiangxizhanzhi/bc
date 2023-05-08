package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticeSolvedRes implements Serializable {

	@ApiModelProperty("练习id")
	private String practiceId;

	@ApiModelProperty("答题状态")
	private Integer solved;
}
