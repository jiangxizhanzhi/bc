package com.cyberpeace.cr.crbase.service.context.role;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用于后台模板查询接口返回基本任务信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CrRoleConfigMission implements Serializable {

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "任务名称")
	private String title;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "任务id")
	private String resourceId;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "任务描述")
	private String description;
}
