package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see MissionBase
 * @since 1.0.0
 */
@Data
@TableName("cr_mission")
public class Mission extends MissionBase {

	/**
	 * 显示 Writeup
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否显示题解")
	private Boolean showWriteup;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间id")
	private String roomId;

	/**
	 * 任务模板id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务模板id")
	private String missionTemplateId;
}
