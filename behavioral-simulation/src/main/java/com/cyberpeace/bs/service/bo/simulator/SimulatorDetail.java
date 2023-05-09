package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.cyberpeace.bs.service.bo.behavior.BehaviorInstance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仪表详情
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class SimulatorDetail implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "id")
	private String resourceId;

	/**
	 * 场景名称
	 */
	@ApiModelProperty(value = "场景名称")
	private String sceneName;

	/**
	 * 设备ip地址
	 */
	@ApiModelProperty(value = "仪表ip")
	private String deviceIp;

	/**
	 * 用户数量
	 */
	@ApiModelProperty(value = "用户数")
	private Integer userCount;

	/**
	 * 访问习惯
	 */
	@ApiModelProperty(value = "访问习惯")
	private List<String> accessHabit;

	/**
	 * 用户类型
	 */
	@ApiModelProperty(value = "用户类型")
	private List<String> userTypes;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "访问开始时间")
	private LocalTime startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "访问结束时间")
	private LocalTime endTime;

	/**
	 * 设备名称
	 */
	@ApiModelProperty(value = "执行设备名称")
	private String deviceName;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "仪表状态")
	private Integer status;

	/**
	 * 行为列表
	 */
	@ApiModelProperty(value = "行为列表")
	private List<BehaviorInstance> behaviors;
}
