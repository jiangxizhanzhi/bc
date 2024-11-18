package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务删除请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class MissionDeleteReq implements Serializable {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	/**
	 * 任务配置
	 *
	 * @see List<MissionConfig>
	 */
	@ApiModelProperty(value = "任务删除对象列表", required = true)
	private List<MissionConfig> missionConfig;

}
