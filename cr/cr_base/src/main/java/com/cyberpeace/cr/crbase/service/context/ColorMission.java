package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 任务编排中间模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ColorMission<T> implements Serializable {

	@ApiModelProperty(value = "色调")
	private Integer color;

	@ApiModelProperty(value = "任务编排")
	private List<MissionSchemeObject<T>> schemeData;

	@ApiModelProperty(value = "模板")
	private Integer template;

}
