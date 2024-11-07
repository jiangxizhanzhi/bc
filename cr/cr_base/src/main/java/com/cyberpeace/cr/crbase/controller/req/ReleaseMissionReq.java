package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 释放任务请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ReleaseMissionReq {

	/**
	 * 角色资源id
	 */
	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	/**
	 * 任务资源id
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

	/**
	 * 任务资源id列表
	 */
	@ApiModelProperty(value = "任务ids", required = true)
	private List<String> missionResourceIds;
}