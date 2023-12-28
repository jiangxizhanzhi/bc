package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.OptionCountProportion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProgressCountRes implements Serializable {

	@ApiModelProperty("运维题")
	private OptionCountProportion maintenance;

	@ApiModelProperty("操作题")
	private OptionCountProportion operation;

	@ApiModelProperty("总共")
	private OptionCountProportion sum;

}
