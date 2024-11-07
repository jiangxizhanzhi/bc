package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务配置
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionConfig implements Serializable {

	/**
	 * 角色资源id
	 */
	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	/**
	 * 任务
	 */
	@ApiModelProperty(value = "任务id列表", required = true)
	private List<String> missions;
}
