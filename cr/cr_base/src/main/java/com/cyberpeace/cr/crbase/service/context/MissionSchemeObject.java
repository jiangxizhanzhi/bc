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
public class MissionSchemeObject<T> implements Serializable {

	/**
	 * 阶段
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "阶段名称")
	private String stage;

	/**
	 * 任务
	 *
	 * @see List<T>
	 */
	@ApiModelProperty(value = "任务列表")
	private List<T> missions;

	/**
	 * 动态
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "动态任务列表")
	private List<String> dynamic;
}
