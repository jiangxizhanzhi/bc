package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class BlankAnswer implements Serializable {

	@ApiModelProperty("权重")
	private Integer weight;

	@ApiModelProperty("答案")
	private String value;

}
