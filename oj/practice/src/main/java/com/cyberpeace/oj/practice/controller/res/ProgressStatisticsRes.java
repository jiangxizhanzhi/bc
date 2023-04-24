package com.cyberpeace.oj.practice.controller.res;

import com.cyberpeace.oj.practice.service.bo.ProgressProportion;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProgressStatisticsRes implements Serializable {

	@ApiModelProperty("简单")
	private ProgressProportion simple;

	@ApiModelProperty("中等")
	private ProgressProportion medium;

	@ApiModelProperty("困难")
	private ProgressProportion difficulty;

	@ApiModelProperty("总共")
	private ProgressProportion sum;
}
