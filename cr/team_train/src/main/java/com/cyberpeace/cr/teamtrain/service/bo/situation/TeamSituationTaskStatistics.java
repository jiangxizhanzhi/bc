package com.cyberpeace.cr.teamtrain.service.bo.situation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class TeamSituationTaskStatistics implements Serializable {

	@ApiModelProperty(value = "成功数")
	private Integer success;

	@ApiModelProperty(value = "失败数")
	private Integer fail;

	@ApiModelProperty(value = "待审核数")
	private Integer wait;

	@ApiModelProperty(value = "未上报")
	private Integer unsubmitted;
}
