package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 释放任务请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ReleaseMissionReq {

	/**
	 * 角色资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	/**
	 * 任务资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

	/**
	 * 任务资源id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty(value = "任务ids", required = true)
	private List<String> missionResourceIds;
}