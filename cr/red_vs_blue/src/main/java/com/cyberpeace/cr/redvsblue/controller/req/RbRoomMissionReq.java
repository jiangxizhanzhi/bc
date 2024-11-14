package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.controller.req.MissionTemplateReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗房间任务请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbRoomMissionReq extends MissionTemplateReq implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	/**
	 * 角色id
	 */
	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	/**
	 * 阶段下标
	 */
	@ApiModelProperty(value = "阶段下标", required = true)
	private Integer missionStage;

	/**
	 * 后一个任务id
	 */
	@ApiModelProperty(value = "后一个任务id", required = true)
	private String afterMissionId;
}
