package com.cyberpeace.cr.crbase.service.context.manual;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ManualMissionBo implements Serializable {

	@ApiModelProperty(value = "任务id")
	private String resourceId;

	@ApiModelProperty(value = "任务名称")
	private String title;

	@ApiModelProperty(value = "题解")
	private String writeUp;
}
