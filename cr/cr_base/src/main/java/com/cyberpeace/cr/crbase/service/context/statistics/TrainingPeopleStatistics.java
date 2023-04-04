package com.cyberpeace.cr.crbase.service.context.statistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TrainingPeopleStatistics implements Serializable {

	@ApiModelProperty("红队训练人员参与次数")
	private Integer redCount;

	@ApiModelProperty("蓝队训练人员参与次数")
	private Integer blueCount;

	@ApiModelProperty("红蓝对抗人员参与次数")
	private Integer rvbCount;

	@ApiModelProperty("人员参与总数")
	private Integer count;
}
