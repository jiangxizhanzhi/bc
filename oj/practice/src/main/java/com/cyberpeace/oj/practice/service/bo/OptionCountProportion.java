package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OptionCountProportion implements Serializable {

	@ApiModelProperty("解出")
	private Integer count;

	@ApiModelProperty("总数")
	private Integer sum;
}
