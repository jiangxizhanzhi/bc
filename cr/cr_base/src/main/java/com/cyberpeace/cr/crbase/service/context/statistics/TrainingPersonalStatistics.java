package com.cyberpeace.cr.crbase.service.context.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class TrainingPersonalStatistics implements Serializable {

	@ApiModelProperty(value = "学员id")
	private String userId;

	@ApiModelProperty(value = "学员总分")
	private Integer totalScore;
}
