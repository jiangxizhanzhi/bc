package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模板简要信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class AddTemplateMission implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务id")
	private String resourceId;

	/**
	 * 标题
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务标题")
	private String title;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务描述")
	private String description;

}
