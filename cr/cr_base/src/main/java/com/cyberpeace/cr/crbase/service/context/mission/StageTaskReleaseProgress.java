package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StageTaskReleaseProgress extends ReleaseProgress implements Serializable {

	@ApiModelProperty(value = "阶段名称")
	private String stageName;
}
