package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProgressProportion implements Serializable {

	@ApiModelProperty("难度")
	private Integer difficulty;

	@ApiModelProperty("解出数量")
	private Integer count;

	@ApiModelProperty("总数")
	private Integer sum;
}
