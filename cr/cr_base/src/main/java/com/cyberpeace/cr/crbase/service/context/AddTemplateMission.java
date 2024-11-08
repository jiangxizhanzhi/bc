package com.cyberpeace.cr.crbase.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务模板简要信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class AddTemplateMission implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "任务id")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "任务标题")
	private String title;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "任务描述")
	private String description;

}
