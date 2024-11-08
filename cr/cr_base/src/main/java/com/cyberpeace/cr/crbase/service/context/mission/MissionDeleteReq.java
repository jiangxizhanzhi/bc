package com.cyberpeace.cr.crbase.service.context.mission;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 任务删除请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class MissionDeleteReq implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	/**
	 * 任务配置
	 */
	@ApiModelProperty(value = "任务删除对象列表", required = true)
	private List<MissionConfig> missionConfig;

}
