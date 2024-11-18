package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 动态数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class DynamicData {

	/**
	 * 操作类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "动作类型")
	private Integer actionType;

	/**
	 * 操作时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "动作时间")
	private Date actionTime;

	/**
	 * 任务名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务名")
	private String missionName;
}
