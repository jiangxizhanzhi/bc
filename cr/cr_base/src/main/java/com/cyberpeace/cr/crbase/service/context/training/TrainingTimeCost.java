package com.cyberpeace.cr.crbase.service.context.training;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainingTimeCost implements Serializable {

	@ApiModelProperty(value = "训练时长，单位为秒")
	private Long timeCost = 0L;

	@ApiModelProperty(value = "是否正在运行")
	private Boolean running = false;
}
