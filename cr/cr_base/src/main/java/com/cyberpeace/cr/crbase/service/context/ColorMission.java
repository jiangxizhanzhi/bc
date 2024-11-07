package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务编排中间模型
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ColorMission<T> implements Serializable {

	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "色调")
	private Integer color;

	/**
	 * 方案数据
	 */
	@ApiModelProperty(value = "任务编排")
	private List<MissionSchemeObject<T>> schemeData;

	/**
	 * 模板
	 */
	@ApiModelProperty(value = "模板")
	private Integer template;

}
