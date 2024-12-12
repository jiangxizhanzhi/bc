package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 动态数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class DynamicData {

	/**
	 * 操作类型
	 */
	@ApiModelProperty(value = "动作类型")
	private Integer actionType;

	/**
	 * 操作时间
	 */
	@ApiModelProperty(value = "动作时间")
	private Date actionTime;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "任务名")
	private String missionName;
}
