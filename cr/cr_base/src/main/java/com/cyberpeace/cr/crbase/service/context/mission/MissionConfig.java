package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务配置
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class MissionConfig implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 岗位资源id
	 */
	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	/**
	 * 任务
	 */
	@ApiModelProperty(value = "任务id列表", required = true)
	private List<String> missions;
}
