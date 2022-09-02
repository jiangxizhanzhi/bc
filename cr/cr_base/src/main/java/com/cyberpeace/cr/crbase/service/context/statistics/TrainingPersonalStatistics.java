package com.cyberpeace.cr.crbase.service.context.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 训练按学员统计
 */
@Data
public class TrainingPersonalStatistics implements Serializable {

	@ApiModelProperty(value = "学员id")
	private String userId;

	@ApiModelProperty(value = "学员总分")
	private Integer totalScore;
}
