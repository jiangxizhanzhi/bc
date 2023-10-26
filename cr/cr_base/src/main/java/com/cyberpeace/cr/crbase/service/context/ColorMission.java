package com.cyberpeace.cr.crbase.service.context;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务编排中间模型
 */
@Data
public class ColorMission<T> {

	@ApiModelProperty(value = "色调")
	private Integer color;

	@ApiModelProperty(value = "任务编排")
	private List<MissionSchemeObject<T>> schemeData;

	@ApiModelProperty(value = "模板")
	private Integer template;

}
