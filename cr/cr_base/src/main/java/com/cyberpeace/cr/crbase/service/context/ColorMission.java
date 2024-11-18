package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务编排中间模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ColorMission<T> implements Serializable {

	/**
	 * 颜色
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "色调")
	private Integer color;

	/**
	 * 方案数据
	 *
	 * @see List<MissionSchemeObject<T>>
	 */
	@ApiModelProperty(value = "任务编排")
	private List<MissionSchemeObject<T>> schemeData;

	/**
	 * 模板
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "模板")
	private Integer template;

}
