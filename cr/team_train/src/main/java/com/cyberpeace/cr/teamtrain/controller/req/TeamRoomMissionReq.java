package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.controller.req.MissionTemplateReq;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队房间任务请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see MissionTemplateReq
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TeamRoomMissionReq extends MissionTemplateReq implements Serializable {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	/**
	 * 角色资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "角色id", required = true)
	private String roleResourceId;

	/**
	 * 任务阶段
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "阶段下标", required = true)
	private Integer missionStage;

	/**
	 * 后任务id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "后一个任务id", required = true)
	private String afterMissionId;
}
