package com.cyberpeace.cr.crbase.service.context.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainingPeopleStatistics implements Serializable {

	@ApiModelProperty("")
	private Integer redCount;

	@ApiModelProperty("")
	private Integer blueCount;

	@ApiModelProperty("")
	private Integer rvbCount;

	@ApiModelProperty("")
	private Integer count;
}
