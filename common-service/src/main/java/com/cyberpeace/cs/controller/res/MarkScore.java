package com.cyberpeace.cs.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class MarkScore {

	@ApiModelProperty("评分")
	private Integer markScore;

	@ApiModelProperty("平均评分")
	private String markAverageScore;
}
