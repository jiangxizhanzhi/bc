package com.cyberpeace.cr.crbase.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@TableName("cr_mission")
public class Mission extends MissionBase {

	/**
	 * 显示 Writeup
	 */
	@ApiModelProperty(value = "是否显示题解")
	private Boolean showWriteup;

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id")
	private String roomId;

	/**
	 * 任务模板id
	 */
	@ApiModelProperty(value = "任务模板id")
	private String missionTemplateId;
}
