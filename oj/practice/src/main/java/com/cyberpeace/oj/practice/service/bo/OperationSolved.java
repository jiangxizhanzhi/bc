package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OperationSolved implements Serializable {

	@ApiModelProperty("题型")
	private Integer category;

	@ApiModelProperty("解决情况")
	private Integer solved;

	@ApiModelProperty("总数")
	private Integer sum;
}
